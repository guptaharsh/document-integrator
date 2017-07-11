package com.servo.session;

import com.servo.security.authentication.APSAuthentication;
import com.servo.util.SRVUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "SRVCall", urlPatterns = {"/SRVCall"})
public class SRVCall extends HttpServlet {

    String apsOdPass, apsOdUser;

//    @Override
//    public void init(ServletConfig config) throws ServletException {
//      try {
//              apsOdPass=config.getInitParameter("PASSWORD");
//              apsOdUser=config.getInitParameter("USERNAME");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//  
//}
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/xml");
        PrintWriter out = response.getWriter();
        apsOdUser = request.getServletContext().getInitParameter("APSODUSERNAME");
        apsOdPass = request.getServletContext().getInitParameter("APSODPASSWORD");
        try {
            System.out.println("INSIDE ------------ SRVCall ------------");
            SRVUtil.printOut("INSIDE ------------ SRVCall ------------");
            System.out.println("apsoduser form APSODconfig====" + apsOdUser);
            System.out.println("apsodPass form APSODconfig====" + apsOdPass);
            String sOutputXML = null;
            URLConnection urlConnection = null;
            String apsOdUserName = request.getParameter("username");
            String apsOdPassword = request.getParameter("password");
            String apsOdFlag = request.getParameter("usertypeflag");
            System.out.println("get flag=="+apsOdFlag);
            if (apsOdFlag == null) {
                apsOdFlag = "E";
            }
            System.out.println("apsOd flag==" + apsOdFlag);
            SRVUtil.printOut("apsOd flag==" + apsOdFlag);

        //get user type param
            //if vendor {
            // call to aps
            // aps false = > out print dummy for invalid 
            // aps true = > call to dms for session with config user name and password
            //}
//        else call as uasual
            String strAPI = encodeURIComponent(request.getParameter("strAPI"));
            String sInputXML = encodeURIComponent(request.getParameter("InputXml"));
            if (!apsOdFlag.equalsIgnoreCase("E")) {
                sInputXML = sInputXML.replaceAll("username", apsOdUser).replaceAll("password", apsOdPass);
            }

            if (apsOdFlag.equalsIgnoreCase("E")) {
                SRVUtil.printOut("In the Employee case======");
                String strout = omniDocsAuthentication(sInputXML, strAPI);
                out.println(strout);
                System.out.println("return xml ===" + strout);
                out.close();
            }

            if (apsOdFlag.equalsIgnoreCase("V")) {
                SRVUtil.printOut("In the Vendor case======");
                System.out.println("In the Vendor case======");
                boolean SUCCESS = false;
                APSAuthentication authApp = new APSAuthentication();
                HashMap map = new HashMap();
                map.put("STATUS", false);//default false
                try {
                    map = (HashMap) authApp.authenticate(null, apsOdUserName, apsOdPassword);
                } catch (Exception ex) {
                    //Logger.getLogger(SRVCall.class.getName()).log(Level.SEVERE, null, ex);
                    SUCCESS = true;
                    String strxml = "<?xml version=\"1.0\"?><NGOConnectCabinet_Output><Option>NGOConnectCabinet</Option><Status>-50003</Status><Error>Aps Application Authentication  Failed.</Error></NGOConnectCabinet_Output>";
                    SRVUtil.printErr(SRVUtil.getExcpStackTrace(ex));
                    System.out.println(ex);
                    throw new IllegalStateException(strxml);

                }
                Boolean thirdPartyAuthStatus = (Boolean) map.get("STATUS");
                System.out.println("thired parity flag====" + thirdPartyAuthStatus);
                SRVUtil.printOut("thired parity flag====" + thirdPartyAuthStatus);
                if (thirdPartyAuthStatus) {
                    String strout = omniDocsAuthentication(sInputXML, strAPI);
                    out.println(strout);
                    out.close();
                } else {
                    if (SUCCESS) {
                        String strxml = "<?xml version=\"1.0\"?><NGOConnectCabinet_Output><Option>NGOConnectCabinet</Option><Status>-50003</Status><Error>Aps Application Authentication  Failed.</Error></NGOConnectCabinet_Output>";
                        out.println(strxml);
                    } else {
                        String strxml = "<?xml version=\"1.0\"?><NGOConnectCabinet_Output><Option>NGOConnectCabinet</Option><Status>-50003</Status><Error>Invalid Username and Password.</Error></NGOConnectCabinet_Output>";
                        out.println(strxml);
                    }
//                String strxml="<?xml version=\"1.0\"?><NGOConnectCabinet_Output><Option>NGOConnectCabinet</Option><Status>-50003</Status><Error>Invalid Username and Password.</Error></NGOConnectCabinet_Output>";
//                throw new IllegalStateException(strxml);
                }
            }
        } catch (IllegalStateException ise) {
            out.println(ise.getMessage());

        } catch (Exception ex) {
//    out.println(ex.getMessage());
            SRVUtil.printErr("APSOD : " + SRVUtil.getExcpStackTrace(ex));

        }

    }

    public static String encodeURIComponent(String component) {
        String result1 = null;
        try {
            result1 = URLEncoder.encode(component, "UTF-8").replaceAll("\\%28", "(").replaceAll("\\%29", ")").replaceAll("\\+", "%20").replaceAll("\\%27", "'").replaceAll("\\%21", "!").replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            result1 = component;

            SRVUtil.printOut("ERROR ---------------------- >  SRVCall : encodeURIComponent \n");
            SRVUtil.printErr("ERROR ---------------------- >  SRVCall : encodeURIComponent \n" + SRVUtil.getExcpStackTrace(e));
        }

        return result1;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public static String omniDocsAuthentication(String sInputXML, String strAPI) {
        System.out.println("In omnidoc method======================");
        String strURL = SRVUtil.getSRVApplicationConfig().getServerConfig().getHttpProtocol() + "://"
                + SRVUtil.getSRVApplicationConfig().getServerConfig().getHttpServerIp() + ":"
                + SRVUtil.getSRVApplicationConfig().getServerConfig().getHttpServerPort() + "/omnidocs/servlet/GenericCallBroker?";

        System.out.println("[SRVCall]     processRequest()    Login Input XML : ----------- > \n" + strURL + "InputXML=" + sInputXML + "&JTSIp=" + SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerIp()
                + "&JTSPort=" + SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerPort());

        SRVUtil.printOut("[SRVCall]     processRequest()    Login Input XML : ----------- > \n" + strURL + "InputXML=" + sInputXML + "&JTSIp=" + SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerIp()
                + "&JTSPort=" + SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerPort());

        //String pUrl = strURL + "InputXML=" + encodeURIComponent(sInputXML) + "&JTSIp=" + SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerIp() + 
        String pUrl = strURL + "InputXML=" + sInputXML + "&JTSIp=" + SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerIp()
                + "&JTSPort=" + SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerPort();

        System.out.println("[SRVCall] processRequest()     GenericCallBroker URL ------------- > " + pUrl);
        SRVUtil.printOut("[SRVCall] processRequest()     GenericCallBroker URL ------------- > " + pUrl);

        SRVCallBroker b = new SRVCallBroker();
        System.out.println("[SRVCall] processRequest()     SRVCallBroker URL ------------- > " + pUrl);
        SRVUtil.printOut("[SRVCall] processRequest()     SRVCallBroker URL ------------- > " + pUrl);

        return b.callBroker(strAPI, pUrl);
    }

}

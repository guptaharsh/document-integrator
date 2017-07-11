/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servo.session;


import com.servo.util.SRVUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "SRVFileNomenclature", urlPatterns = {"/SRVFileNomenclature"})
public class SRVFileNomenclature extends HttpServlet {

//@Resource(name = "SRV-APSODDataSource")
DataSource ds;
    
 @Override
   public void init(ServletConfig config) throws ServletException {
         try {
                InitialContext ic = new InitialContext();
                ds = (DataSource) ic.lookup("java:comp/env/SRV-APSODDataSource");
            } catch (NamingException ex) {
                SRVUtil.printErr(SRVUtil.getExcpStackTrace(ex));
            }
   }
 
 protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        System.out.println("INSIDE  -------- SRVFileNomenclature ---------");
        SRVUtil.printOut("INSIDE  -------- SRVFileNomenclature ---------");
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
                try 
                {
                    long entryTime = System.currentTimeMillis();
                    long endTime , elapsedTime , queryTime;
                    SRVUtil.printXML("[SRVFileNomenclature]"," Entry Time : "+entryTime);

                    try
                    {
                        con = ds.getConnection();
                        endTime = System.currentTimeMillis();
                        elapsedTime = endTime - entryTime;
                        SRVUtil.printXML("[SRVFileNomenclature]" , "Time taken in getting Connection object = "+ elapsedTime);
                        SRVUtil.printTransanction("iDecisions[SRVFileNomenclature]","ConnectionTime", entryTime, endTime, 1);
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.getMessage());
                        SRVUtil.printOut("[SRVFileNomenclature] : ERROR ---------------------- >  Creating Connection");
                        SRVUtil.printErr("[SRVFileNomenclature] : ERROR ---------------------- >  Creating Connection\n"+SRVUtil.getExcpStackTrace(e));
                        
                        RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");

                        request.setAttribute("STATUS", "ERROR");
                        rd.forward(request, response);
                    }
                    
                    String Sname = "";
                    String Sname1= "";
                    String Nomenc= "";
                    String dataClass = "";
                    
//                    String applicant= request.getParameter("applicant");
//                    String parent= request.getParameter("parent");
//                    String child= request.getParameter("child");
                    //System.out.println("check= "+ applicant);
                    
                    String applicant= (String)request.getAttribute("Applicant_Type");
                    System.out.println("[SRVFileNomenclature]   processRequest()    , App --------- > "+applicant);
                    SRVUtil.printOut("[SRVFileNomenclature]   processRequest()    , App --------- > "+applicant);
                    
                    String parent= (String)request.getAttribute("Parent_Document_Id");
                    System.out.println("[SRVFileNomenclature]   processRequest()    , Parent --------- > "+parent);
                    SRVUtil.printOut("[SRVFileNomenclature]   processRequest()    , Parent --------- > "+parent);
                    
                    String child= (String)request.getAttribute("Child_Document");
                    System.out.println("[SRVFileNomenclature]   processRequest()    , Child --------- > "+child);
                    SRVUtil.printOut("[SRVFileNomenclature]   processRequest()    , Child --------- > "+child);
                    
                    //String sql = "SELECT SHORTNAME FROM SRV_APSOD_MS_APPTYPE Where NAME ='" + applicant+"'";
                    String sql = "SELECT SHORTNAME FROM SRV_APSOD_MS_APPTYPE Where NAME =?";
                    System.out.println("[SRVFileNomenclature]   processRequest() ------------- > Query : "+sql);
                    SRVUtil.printOut("[SRVFileNomenclature]   processRequest() ------------- > Query : "+sql);
                    pstmt = con.prepareStatement(sql); 
                    SRVUtil.PSTMT_SETSTRING(1, applicant, pstmt, SRVUtil.getSRVApplicationConfig().getDBConfig().getDBType());
                    queryTime = System.currentTimeMillis();
                    resultSet = pstmt.executeQuery();
                    System.out.println("[SRVFileNomenclature]   processRequest() ------------- > Query Executed: "+sql);
                    SRVUtil.printOut("[SRVFileNomenclature]   processRequest() ------------- > Query Excecuted: "+sql);
                    queryTime = queryTime - System.currentTimeMillis();
                    SRVUtil.printXML("[SRVFileNomenclature]   processRequest()" , "Query : "+sql);
                    SRVUtil.printXML("[SRVFileNomenclature]   processRequest()" , "Time Taken : "+queryTime);
                    
                    if (resultSet.next()) 
                    {
                        Sname = resultSet.getString("SHORTNAME");
                        System.out.println("[SRVFileNomenclature]   processRequest()   ApplicantType SName =========== > "+parent);
                        SRVUtil.printOut("[SRVFileNomenclature]   processRequest()  ApplicantType SName =========== > "+parent);
                    }
                    resultSet.close();
                    pstmt.close();
                    System.out.println("[SRVFileNomenclature]   processRequest()    PARENT =========== > "+parent);
                    SRVUtil.printOut("[SRVFileNomenclature]   processRequest()  PARENT =========== > "+parent);
                    
                    String sql1 = "SELECT SHORTNAME, NOMENCLATURE, DATACLASS FROM SRV_APSOD_MS_PARENTDOC Where ID =?";
                    pstmt = con.prepareStatement(sql1);
                    pstmt.setInt(1, Integer.parseInt(parent));
                    queryTime = System.currentTimeMillis();
                    resultSet = pstmt.executeQuery();
                    queryTime = queryTime - System.currentTimeMillis();
                    System.out.println("[SRVFileNomenclature]   processRequest()  Query (SName,Nomen) ------  : "+sql1);
                    SRVUtil.printOut("[SRVFileNomenclature]   processRequest()  Query (SName,Nomen) ------ : "+sql1);
                    
                   if (resultSet.next()) 
                   {
                        Sname1 = resultSet.getString("SHORTNAME");
                        Nomenc = resultSet.getString("NOMENCLATURE");
                        dataClass = resultSet.getString("DATACLASS");
                   }
                   
                   System.out.println("[SRVFileNomenclature]   processRequest()  ------------ > SName = "+Sname1+" , Nomenclature = " + Nomenc+" , DataClass = " + dataClass);
                   SRVUtil.printOut("[SRVFileNomenclature]   processRequest()  ------------ > SName = "+Sname1+" , Nomenclature = " + Nomenc+" , DataClass = " + dataClass);
                     if (resultSet != null){
                            resultSet.close();
                            resultSet = null;
                        }
                    if (pstmt != null){
                            pstmt.close();
                            pstmt = null;
                        }
                    if (con != null){
                        con.close();
                        con = null;
                    }

                    Nomenc = Nomenc.replace("{SNAPPLICANTTYPE}",Sname);
                    Nomenc = Nomenc.replace("{SNPARENTDOCUMENT}",Sname1);
                    Nomenc = Nomenc.replace("{CHILDDOCUMENT}",child);
                    
                    System.out.println("[SRVFileNomenclature]   processRequest()  ------------ > Nomenclature = "+Nomenc);
                    SRVUtil.printOut("[SRVFileNomenclature]   processRequest()  ------------ > Nomenclature = "+Nomenc);
                    //String nomen = Sname+"_"+Sname1+"_"+child;
                    RequestDispatcher rd = request.getRequestDispatcher("SRVGetDocumentFolder");
                    
                    request.setAttribute("Nomenclature", Nomenc);
                    request.setAttribute("filePath", (String)request.getAttribute("filePath"));
                    request.setAttribute("User", (String)request.getAttribute("User"));
                    request.setAttribute("UserDBId", (String)request.getAttribute("UserDBId"));
                    request.setAttribute("Parent_Document", (String)request.getAttribute("Parent_Document"));
                    request.setAttribute("Child_Document", (String)request.getAttribute("Child_Document"));
                    request.setAttribute("Applicant_Type", (String)request.getAttribute("Applicant_Type"));
                    request.setAttribute("Application_Form_No", (String)request.getAttribute("Application_Form_No"));
                    request.setAttribute("ApsId", (String)request.getAttribute("ApsId"));
                    request.setAttribute("Document_Folder_Path", (String)request.getAttribute("DOCUMENT_FOLDER"));
                    request.setAttribute("DataClass", dataClass);
      
                    rd.forward(request, response);

                }
                catch (Exception e) 
                {
                    SRVUtil.printOut("ERROR ----------- > SRVFileNomenclature");
                    SRVUtil.printErr("ERROR ----------- > SRVFileNomenclature\n"+SRVUtil.getExcpStackTrace(e));
                    RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                    request.setAttribute("STATUS", "ERROR");
                    rd.forward(request, response);
                    
                    out.println("<b>Error:</b>" + e);
                }



             finally {   
                    
                   if (resultSet != null){
                            resultSet.close();
                            resultSet = null;
                        }
                    if (pstmt != null){
                            pstmt.close();
                            pstmt = null;
                        }
                    if (con != null){
                        con.close();
                        con = null;
                    }
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SRVFileNomenclature.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SRVFileNomenclature.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}

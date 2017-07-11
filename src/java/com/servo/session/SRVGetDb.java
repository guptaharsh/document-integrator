package com.servo.session;

import com.google.gson.Gson;

import com.servo.util.SRVUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
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
@WebServlet(name = "SRVGetDb", urlPatterns = {"/SRVGetDb"})
public class SRVGetDb extends HttpServlet {

   // @Resource(name = "SRV-APSODDataSource")
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
        
        System.out.println("INSIDE ------------- SRVGetDb ---------------");
        SRVUtil.printOut("INSIDE ------------- SRVGetDb ---------------");
        
        Gson g = new Gson();
        String type = request.getParameter("type");

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try
        {
            System.out.println("[SRVGetDb]  processRequest() ----------------- > Getting Connection ");
            SRVUtil.printOut("[SRVGetDb]  processRequest() ----------------- > Getting Connection ");
            con = ds.getConnection();
        }
        catch(Exception e)
        {
            System.out.println("[SRVGetDb]  processRequest() , ERROR getting connection -------- > "+e.getMessage());
            SRVUtil.printOut("[SRVGetDb]  processRequest() , ERROR ---------------------- >  Creating Connection");
            SRVUtil.printErr("[SRVGetDb]  processRequest() , ERROR ---------------------- >  Creating Connection"+SRVUtil.getExcpStackTrace(e));
            RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");

            request.setAttribute("STATUS", "ERROR");
            rd.forward(request, response);
        }

        try {

            if (type.equalsIgnoreCase("parent")) {
                
                System.out.println("[SRVGetDb]  processRequest() -------- > IF (ParentDocument)");
                SRVUtil.printOut("[SRVGetDb]  processRequest() -------- > IF (ParentDocument)");
                
                try {
                    String sql = "SELECT ID, NAME, SHORTNAME, DATACLASS FROM SRV_APSOD_MS_PARENTDOC";
                    System.out.println("[SRVGetDb]  processRequest() , Query : ParentDocument ---------- > "+sql);
                    SRVUtil.printOut("[SRVGetDb]  processRequest() , Query : ParentDocument ---------- > "+sql);
                    pstmt = con.prepareStatement(sql);
                    System.out.println("[SRVGetDb]  processRequest() -------- > IF (PARENT) Statement Prepared");
                    SRVUtil.printOut("[SRVGetDb]  processRequest() -------- > IF (PARENT) Statement Prepared");
                    resultSet = pstmt.executeQuery();
                    System.out.println("[SRVGetDb]  processRequest() -------- > IF (PARENT) Statement Executed -------- > "+resultSet);
                    SRVUtil.printOut("[SRVGetDb]  processRequest() -------- > IF (PARENT) Statement Executed -------- > "+resultSet);
                    // List persons = new LinkedList();
                    // List Id = new LinkedList();
                    Map map = new HashMap();

                    while (resultSet.next()) {
                        
                        System.out.println("[SRVGetDb]  processRequest() , ParentDocument : Putting values in MAP");
                        System.out.println("[SRVGetDb]  processRequest() ---------- > ID = "+resultSet.getInt("ID")+" , VALUE = "+resultSet.getString("NAME")+"$"+resultSet.getString("SHORTNAME"));
                        SRVUtil.printOut("[SRVGetDb]  processRequest() , ParentDocument : Putting values in MAP");
                        SRVUtil.printOut("[SRVGetDb]  processRequest() ---------- > ID = "+resultSet.getInt("ID")+" , VALUE = "+resultSet.getString("NAME")+"$"+resultSet.getString("SHORTNAME"));
                        
                        map.put(resultSet.getInt("ID"), resultSet.getString("NAME")+"$"+resultSet.getString("SHORTNAME")+"$"+resultSet.getString("DATACLASS"));

                        // Id.add(resultSet.getString("ID"));
                        // persons.add(resultSet.getString("NAME"));
                        
                    }
                       
                    System.out.println("[SRVGetDb]  processRequest() , Doc Map ----------- > \n"+map.toString());
                    SRVUtil.printOut("[SRVGetDb]  processRequest() , Doc Map Parent ---------------------- > "+map.toString());
            
                    
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
                    
                    out.println(g.toJson(map));


                } catch (Exception e) {
                    System.out.println("[SRVGetDb]  processRequest() , ERROR ----------- > "+e.getStackTrace());
                    SRVUtil.printOut("[SRVGetDb]  processRequest() , ERROR ----------- > ");
                    SRVUtil.printErr("[SRVGetDb]  processRequest() , ERROR ----------- > "+e.getStackTrace());
                    out.println("<b>Error:</b>" + e);
                }


            } else if (type.equalsIgnoreCase("applicant")) {
                
                System.out.println("[SRVGetDb]  processRequest() -------- > IF (ApplicantType)");
                SRVUtil.printOut("[SRVGetDb]  processRequest() -------- > IF (ApplicantType)");
                
                try {

                    String sql = "SELECT NAME,SHORTNAME FROM SRV_APSOD_MS_APPTYPE";
                    System.out.println("[SRVGetDb]  processRequest() -------- > IF (ApplicantType) Preparing Statement");
                    SRVUtil.printOut("[SRVGetDb]  processRequest() -------- > IF (ApplicantType) Preparing Statement");
                    pstmt = con.prepareStatement(sql);
                    
                    System.out.println("[SRVGetDb]  processRequest() -------- > IF (APPLICANTTYPE) Statement Prepared");
                    SRVUtil.printOut("[SRVGetDb]  processRequest() -------- > IF (APPLICANTTYPE) Statement Prepared");
                    resultSet = pstmt.executeQuery();
                    
                    System.out.println("[SRVGetDb]  processRequest() -------- > IF (ApplicantType) Statement Executed -------- > "+resultSet);
                    SRVUtil.printOut("[SRVGetDb]  processRequest() -------- > IF (ApplicantType) Statement Executed -------- > "+resultSet);
                    List persons = new LinkedList();

                    while (resultSet.next()) {
                        
                        System.out.println("[SRVGetDb]  processRequest() , ApplicantType : Putting values in LIST");
                        System.out.println("[SRVGetDb]  processRequest() --------- > VALUE = "+resultSet.getString("NAME"));
                        SRVUtil.printOut("[SRVGetDb]  processRequest() , ApplicantType : Putting values in LIST");
                        SRVUtil.printOut("[SRVGetDb]  processRequest() --------- > VALUE = "+resultSet.getString("NAME"));
                        
                        persons.add(resultSet.getString("NAME")+"$"+resultSet.getString("SHORTNAME"));
                    }

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
                    
                    out.println(g.toJson(persons));
                    
                } catch (Exception e) {
                    
                    System.out.println("[SRVGetDb]  processRequest() , ERROR ----------- > "+e.getStackTrace());
                    SRVUtil.printOut("[SRVGetDb]  processRequest() , ERROR ----------- > ");
                    SRVUtil.printErr("[SRVGetDb]  processRequest() , ERROR ----------- > "+e.getStackTrace());
                    
                    out.println("<b>Error:</b>" + e);
                }

            } else if (type.equalsIgnoreCase("child")) {

                System.out.println("[SRVGetDb]  processRequest() -------- > IF (ChildDocument)");
                SRVUtil.printOut("[SRVGetDb]  processRequest() -------- > IF (ChildDocument)");
                
                try {
                    String id = request.getParameter("id");
                    System.out.println("[SRVGetDb]  processRequest() , IF(CHILD) --------- > ParentDocument ID = "+id);
                    SRVUtil.printOut("[SRVGetDb]  processRequest() , IF(CHILD) --------- > ParentDocument ID = "+id);
                    
                    String sql = "SELECT NAME FROM SRV_APSOD_MS_CHILDDOC Where PARENTID = ? ";
                    System.out.println("[SRVGetDb]  processRequest() -------- > IF (CHILD) Preparing Statement");
                    SRVUtil.printOut("[SRVGetDb]  processRequest() -------- > IF (CHILD) Preparing Statement");
                    
                    pstmt = con.prepareStatement(sql);
                    System.out.println("[SRVGetDb]  processRequest() -------- > IF (CHILD) Statement Prepared");
                    SRVUtil.printOut("[SRVGetDb]  processRequest() -------- > IF (CHILD) Statement Prepared");
                    
                    pstmt.setInt(1, Integer.parseInt(id));
                    
                    System.out.println("[SRVGetDb]  processRequest() -------- > IF (CHILD) Executing Statement");
                    SRVUtil.printOut("[SRVGetDb]  processRequest() -------- > IF (CHILD) Executing Statement");
                    resultSet = pstmt.executeQuery();
                    SRVUtil.printOut("[SRVGetDb]  processRequest() -------- > IF (CHILD) Statement Executed -------- > "+resultSet);
                    List persons = new LinkedList();

                    while (resultSet.next()) {
                        
                        System.out.println("[SRVGetDb]  processRequest() : IF(Child) , ChildDocument : Putting values in LIST");
                        System.out.println("[SRVGetDb]  processRequest() : IF(Child) -------- >  VALUE = "+resultSet.getString("NAME"));
                        SRVUtil.printOut("[SRVGetDb]  processRequest() : IF(Child) , ChildDocument : Putting values in LIST");
                        SRVUtil.printOut("[SRVGetDb]  processRequest() : IF(Child) ---------- > VALUE = "+resultSet.getString("NAME"));
                        
                        persons.add(resultSet.getString("NAME"));
                    }
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
                    
                    out.println(g.toJson(persons));
                    

                } catch (Exception e) {
                    System.out.println("[SRVGetDb]  processRequest() : IF(Child) , ERROR ---------- > "+e.getStackTrace());
                    SRVUtil.printOut("[SRVGetDb]  processRequest() : IF(Child) , ERROR ---------- > ");
                    SRVUtil.printErr("[SRVGetDb]  processRequest() : IF(Chld) , ERROR ---------- > "+e.getStackTrace());
                    out.println("<b>Error:</b>" + e);
                }

            } else if (type.equalsIgnoreCase("check")) {

                System.out.println("[SRVGetDb]  processRequest() -------- > IF (Check) Getting Short Names");
                SRVUtil.printOut("[SRVGetDb]  processRequest() -------- > IF (Check) Getting Short Names");
                
                try {

                    String Sname = "";
                    String Sname1= "";
                    String Nomenc= "";
                    String applicant= request.getParameter("applicant");
                    System.out.println("[SRVGetDb]  processRequest() : IF(Check) , ApplicantType = "+applicant);
                    SRVUtil.printOut("[SRVGetDb]  processRequest() : IF(Check) , ApplicantType = "+applicant);
                    
                    String parent= request.getParameter("parent");
                    System.out.println("[SRVGetDb]  processRequest() : IF(Check) , ParentDocument = "+parent);
                    SRVUtil.printOut("[SRVGetDb]  processRequest() : IF(Check) , ParentDocument = "+parent);
                    
                    String child= request.getParameter("child");
                    System.out.println("[SRVGetDb]  processRequest() : IF(Check) , ChildDocument = "+child);
                    SRVUtil.printOut("[SRVGetDb]  processRequest() : IF(Check) , ChildDocument = "+child);
                    //System.out.println("check= "+ applicant);
                    
                    String sql = "SELECT SHORTNAME FROM SRV_APSOD_MS_APPTYPE Where NAME =?";
                    System.out.println("[SRVGetDb]  processRequest() -------- > IF (Check) Preparing Statement");
                    SRVUtil.printOut("[SRVGetDb]  processRequest() -------- > IF (Check) Preparing Statement");
                    
                    pstmt = con.prepareStatement(sql);
                    System.out.println("[SRVGetDb]  processRequest() -------- > IF (Check) Statement Prepared");
                    SRVUtil.printOut("[SRVGetDb]  processRequest() -------- > IF (Check) Statement Prepared");
                    SRVUtil.PSTMT_SETSTRING(1, applicant, pstmt, SRVUtil.getSRVApplicationConfig().getDBConfig().getDBType());
                    
                    resultSet = pstmt.executeQuery();
                    System.out.println("[SRVGetDb]  processRequest() -------- > IF (Check) Query Executed ----- > "+resultSet);
                    SRVUtil.printOut("[SRVGetDb]  processRequest() -------- > IF (Check) Query Executed ----- > "+resultSet);
                    
                    if (resultSet.next()) {
                        
                        System.out.println("[SRVGetDb]  processRequest() : IF(check) , ShortName : "+resultSet.getString("SHORTNAME"));
                        SRVUtil.printOut("[SRVGetDb]  processRequest() : IF(check) , ShortName : "+resultSet.getString("SHORTNAME"));
                        
                        Sname = resultSet.getString("SHORTNAME");
                    }

                     if (resultSet != null){
                            resultSet.close();
                        }
                    if (pstmt != null){
                            pstmt.close();
                        }
                    
                    //out.println(Sname);
                    System.out.println("[SRVGetDb]  processRequest() : IF(check) , PARENT = > "+parent);
                    SRVUtil.printOut("[SRVGetDb]  processRequest() : IF(check) , PARENT = > "+parent);
                    
                    String sql1 = "SELECT SHORTNAME, NOMENCLATURE FROM SRV_APSOD_MS_PARENTDOC Where ID =?";
                    pstmt = con.prepareStatement(sql1);
                    pstmt.setInt(1, Integer.parseInt(parent));
                    resultSet = pstmt.executeQuery();
                    
                    if (resultSet.next()) {

                        Sname1 = resultSet.getString("SHORTNAME");
                        Nomenc = resultSet.getString("NOMENCLATURE");

                    }
                    //out.println(Sname1);
                    System.out.println("[SRVGetDb]  processRequest() -------- > IF(check) : Nomenclature = " + Nomenc+" , ParentSName");
                    SRVUtil.printOut("[SRVGetDb]  processRequest() -------- > IF(check) : Nomenclature = " + Nomenc+" , ParentSName");
                    
                    Nomenc = Nomenc.replace("{SNAPPLICANTTYPE}",Sname);
                    Nomenc = Nomenc.replace("{SNPARENTDOCUMENT}",Sname1);
                    Nomenc = Nomenc.replace("{CHILDDOCUMENT}",child);
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
                    
                    System.out.println("[SRVGetDb]  processRequest() IF(check) -------- >  Nomenclature = "+Nomenc);
                    SRVUtil.printOut("[SRVGetDb]  processRequest() IF(check) -------- >  Nomenclature = "+Nomenc);
                    out.println(Nomenc);

                } 
                catch (Exception e) {
                    System.out.println("[SRVGetDb]  processRequest() : IF(check) , ERROR -------- > "+e.getStackTrace());
                    SRVUtil.printOut("[SRVGetDb]  processRequest() : IF(check) , ERROR -------- > ");
                    SRVUtil.printErr("[SRVGetDb]  processRequest() : IF(check) , ERROR -------- > "+e.getStackTrace());
                    out.println("<b>Error:</b>" + e);
                }



            }


        } catch (Exception e) {
            System.out.println("[SRVGetDb]  processRequest() , ERROR -------- > "+e.getStackTrace());
            SRVUtil.printOut("[SRVGetDb]  processRequest() , ERROR -------- > ");
            SRVUtil.printErr("[SRVGetDb]  processRequest() , ERROR -------- > "+e.getStackTrace());
            out.println("<b>Error:</b>" + e);
        } finally {
            
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
            Logger.getLogger(SRVGetDb.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SRVGetDb.class.getName()).log(Level.SEVERE, null, ex);
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

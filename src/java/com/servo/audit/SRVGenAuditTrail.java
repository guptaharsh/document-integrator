/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servo.audit;

import com.google.gson.Gson;
import com.servo.error.SRVStatus;

import com.servo.util.SRVUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
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
@WebServlet(name = "SRVGenAuditTrail", urlPatterns = {"/SRVGenAuditTrail"})
public class SRVGenAuditTrail extends HttpServlet {

    
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
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        System.out.println("Inside Servlet ------------------------ >");
        
        Gson g =new Gson();
        Connection con = null;
        try
        {
            con = ds.getConnection();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            SRVUtil.printOut("[SRVGenAuditTrail] (processRequest) ERROR ---------------------- >  Creating Connection");
            SRVUtil.printErr("[SRVGenAuditTrail] (processRequest) ERROR ---------------------- >  Creating Connection"+SRVUtil.getExcpStackTrace(e));
            RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                                        
                    request.setAttribute("STATUS", "ERROR");
                    rd.forward(request, response);
        }
        try 
        {
            
            String requestType = "";
            if(request.getAttribute("requestType") == null)
            {
                requestType = request.getParameter("requestType");
            }
            else
            {
                requestType = (String)request.getAttribute("requestType");
            }
            
            String dbType = SRVUtil.getSRVApplicationConfig().getDBConfig().getDBType();
            
            if(requestType.equalsIgnoreCase("prepareAuditTrail"))
            {
                long startTime = System.currentTimeMillis();
                StringBuffer queryStr = null;
                PreparedStatement pstmt = null;        
                ResultSet rs = null;
                int status = SRVStatus.GENERIC_ERROR;                            
                try{
            
                    String applicationFormNo = (String)request.getAttribute("Application_Form_No");
                    String documentName = (String)request.getAttribute("DocumentName");
                    String uploadedBy = (String)request.getAttribute("UploadedBy");
                    
                    queryStr = new StringBuffer(100);
                    queryStr.append(" INSERT INTO SRV_APSOD_RU_AUDIT(DOCUMENTNAME, UPLOADEDBY, APPLICATIONFORMNO)");
                    queryStr.append(" VALUES (?,?,?) ");
                    pstmt = con.prepareStatement(queryStr.toString());
//                    PSTMT_SETSTRING(1, (String) docMap.get("DOCUMENTNAME"), pstmt, dbType);
//                    PSTMT_SETSTRING(2, (String) docMap.get("UPLOADEDBY"), pstmt, dbType);
                    SRVUtil.PSTMT_SETSTRING(1, documentName, pstmt, dbType);
                    SRVUtil.PSTMT_SETSTRING(2, uploadedBy, pstmt, dbType);
                    SRVUtil.PSTMT_SETSTRING(3, applicationFormNo, pstmt, dbType);
                    long endTime = System.currentTimeMillis();
                    int res = pstmt.executeUpdate();
                    pstmt.close();
                    if (res == 1)
                    {
                        status = SRVStatus.SUCCESS;
                        System.out.println("Insert Status is ------- > "+status);
                    }                            
                 

                }
                catch(Exception e){
                   // status = CONST_ERROR;    
                    System.out.println(SRVUtil.getExcpStackTrace(e));
                    SRVUtil.printOut("[SRVGenAuditTrail] (processRequest) ERROR ---------------------- >  prepareAuditTrail\n");
                    SRVUtil.printErr("[SRVGenAuditTrail] (processRequest) ERROR ---------------------- >  prepareAuditTrail\n"+SRVUtil.getExcpStackTrace(e));
                    RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                                        
                    request.setAttribute("STATUS", "ERROR");
                    rd.forward(request, response);
                }
                finally{
                    try{
                        if (rs != null){
                            rs.close();
                            rs = null;
                        }
                        if (pstmt != null){
                            pstmt.close();
                            pstmt = null;
                        }
                        if (con != null){
                            con.close();
                            con = null;
                        }
                    }
                    catch(Exception ignored){}
                }
//                return status;
                System.out.println("GenAuditTrail ------------- > Status : "+status);
                
                RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                if(status == 1)
                {
                    request.setAttribute("STATUS", "SUCCESS");
                }
                else
                {
                    request.setAttribute("STATUS", "ERROR");
                }
                rd.forward(request, response);
                
                //out.println(g.toJson(status));
                
                
                
            }
            else if(requestType.equalsIgnoreCase("getAuditTrail"))
            {
                long startTime = System.currentTimeMillis();
                StringBuffer queryStr = null;
                PreparedStatement pstmt = null;        
                ResultSet rs = null;
                //LinkedList dataList = new LinkedList();
                String documentName = "";
                String uploadDateTime = "";
                String applicationFomNo = "";
                
                String uploadedBy = "";
                if(request.getAttribute("UploadedBy") == null)
                {
                    uploadedBy = request.getParameter("uploadedBy");
                    
                }
                else
                {
                    uploadedBy = (String)request.getAttribute("UploadedBy");
                }
                
                applicationFomNo = request.getParameter("applicationFormNo");
                
                System.out.println("Uploaded By -------------- > "+uploadedBy);
                ArrayList arrDataList = new ArrayList();

                try{
                    queryStr = new StringBuffer(100);
                    queryStr.append(" SELECT DOCUMENTNAME, UPLOADDATETIME, UPLOADEDBY FROM SRV_APSOD_RU_AUDIT ");
                    //queryStr.append(" WHERE UPPER(UPLOADEDBY) = UPPER(?) AND APPLICATIONFORMNO = ? ORDER BY UPLOADDATETIME DESC");
                    queryStr.append(" WHERE APPLICATIONFORMNO = ? ORDER BY UPLOADDATETIME DESC");
                    pstmt = con.prepareStatement(queryStr.toString());
                    System.out.println("QUERY ---------- > "+queryStr.toString());
                    //SRVUtil.PSTMT_SETSTRING(1, uploadedBy , pstmt, dbType);
                    SRVUtil.PSTMT_SETSTRING(1, applicationFomNo , pstmt, dbType);
                    rs = pstmt.executeQuery();
                    while (rs.next()){
                        System.out.println("Inside  RS ------------- > ");
                        System.out.println("Redefining LL in every cycle  ------------- > ");
                        LinkedList dataList = new LinkedList();
                        documentName = rs.getString(1);
                        uploadDateTime = rs.getString(2);
                        uploadedBy = rs.getString(3);
                        dataList.add(documentName);
                        dataList.add(uploadDateTime);
                        dataList.add(uploadedBy);
                        arrDataList.add(dataList);
                    }
                    System.out.println("arrDataList ----- > "+arrDataList);
                    rs.close();
                    pstmt.close();

                }
                catch(Exception e){
                    System.out.println(SRVUtil.getExcpStackTrace(e));
                    
                    SRVUtil.printOut("[SRVGenAuditTrail] (processRequest) ERROR ---------------------- >  getAuditTrail\n");
                    SRVUtil.printErr("[SRVGenAuditTrail] (processRequest) ERROR ---------------------- >  getAuditTrail\n"+SRVUtil.getExcpStackTrace(e));
                    
                    arrDataList.clear();
                    
                    //RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                        
//  set status to fail in case of exception                    
                    //request.setAttribute("STATUS", "ERROR");
                    //rd.forward(request, response);
                }
                finally{
                    try{
                        if (rs != null){
                            rs.close();
                            rs = null;
                        }
                        if (pstmt != null){
                            pstmt.close();
                            pstmt = null;
                        }
                        if (con != null){
                            con.close();
                            con = null;
                        }
                        
                        
                    }
                    catch(Exception ignored){}
                }
        //        return arrDataList;
                out.println(g.toJson(arrDataList));
            }
    
        } finally {            
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
        processRequest(request, response);
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
}

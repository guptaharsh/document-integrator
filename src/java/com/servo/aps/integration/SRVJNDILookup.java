/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servo.aps.integration;

import com.google.gson.Gson;
import com.nucleus.los.bean.loanclick.LoanAtClick;
import com.nucleus.los.bean.loanclick.LoanAtClickHome;
import com.servo.aps.security.Authenticate;
import com.servo.util.SRVUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "SRVJNDILookup", urlPatterns = {"/SRVJNDILookup"})
public class SRVJNDILookup extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        LoanAtClick objLoanAtClick;

        String initailContextFactory = request.getParameter("initailContextFactory");
        System.out.println("[SRVJNDILookup]   processRequest ---------- > providerURL = " + initailContextFactory);
        SRVUtil.printOut("[SRVJNDILookup]   processRequest ---------- > providerURL = " + initailContextFactory);

        String providerURL = request.getParameter("providerURL");
        System.out.println("[SRVJNDILookup]   processRequest ---------- > providerURL = " + providerURL);
        SRVUtil.printOut("[SRVJNDILookup]   processRequest ---------- > providerURL = " + providerURL);

        String userName = request.getParameter("userName");
        System.out.println("[SRVJNDILookup]   processRequest ---------- > userName = " + userName);
        SRVUtil.printOut("[SRVJNDILookup]   processRequest ---------- > userName = " + userName);

        //String password = request.getParameter("credential");

        String log_chncd = request.getParameter("log_chncd");
        System.out.println("[SRVJNDILookup]   processRequest ---------- > log_chncd = " + log_chncd);
        SRVUtil.printOut("[SRVJNDILookup]   processRequest ---------- > log_chncd = " + log_chncd);

//        System.out.println("[SRVJNDILookup_2]   processRequest ---------- > Original Password obtained = "+password);
//        SRVUtil.printOut("[SRVJNDILookup_2]   processRequest ---------- > Original Password obtained = "+password);

        Properties jndiProps = new Properties();
        //jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, initailContextFactory);
//        //jndiProps.put(Context.PROVIDER_URL,"remote://localhost:4447");
        jndiProps.put(Context.PROVIDER_URL, providerURL);
//        // username
//        jndiProps.put(Context.SECURITY_PRINCIPAL, userName);
////        // password
//        jndiProps.put(Context.SECURITY_CREDENTIALS, password);
        // create a context passing these properties

        String username = request.getParameter("userName");
        String credential = request.getParameter("credential");
        String strEncoding = request.getParameter("encoding");

        Authenticate authenticate = new Authenticate();

        String encoded_password = authenticate.validateUser(username, credential, strEncoding);//   custom method

        System.out.println("[SRVJNDILookup]   processRequest() --------- >  encoded password is ------------ > \n" + encoded_password);
        SRVUtil.printOut("[SRVJNDILookup]   processRequest() --------- >  encoded password is == \n" + encoded_password);

        Context ctx = null;
        Object obj = null;
        LoanAtClickHome server = null;
        try {
            System.out.println("[SRVJNDILookup]   processRequest ---------- > Before creating Context");
            SRVUtil.printOut("[SRVJNDILookup]   processRequest ---------- > Before creating Context");

            ctx = new InitialContext(jndiProps);

            System.out.println("[SRVJNDILookup]   processRequest ---------- > Context created");
            SRVUtil.printOut("[SRVJNDILookup]   processRequest ---------- > Context created");

            // lookup
            obj = ctx.lookup("LoanAtClickJndi");
            System.out.println("[SRVJNDILookup]   processRequest ---------- > lookup done");
            SRVUtil.printOut("[SRVJNDILookup]   processRequest ---------- > lookup done");
            // objLoanAtClick = (LoanAtClick)obj; // commented ( 22 - AUG - 2014 ) by Arshad

            server = (LoanAtClickHome) PortableRemoteObject.narrow(obj, LoanAtClickHome.class);
            SRVUtil.printOut("[SRVJNDILookup]   processRequest ---------- > Narrowing Object is successful , Server object :" + server);

            objLoanAtClick = (LoanAtClick) PortableRemoteObject.narrow(server.create(), LoanAtClick.class);
            SRVUtil.printOut("[SRVJNDILookup]   processRequest ---------- > Remote object creation is successful :" + objLoanAtClick);

            HashMap loginDetails = new HashMap();
            loginDetails.put("LOG_ID", userName);
            loginDetails.put("LOG_PASS", encoded_password);
            loginDetails.put("LOG_CHNCD", log_chncd);

            System.out.println("[SRVJNDILookup]   processRequest ---------- > Login Details (HASHMAP) --- > " + loginDetails);
            SRVUtil.printOut("[SRVJNDILookup]   processRequest ---------- > Login Details (HASHMAP) --- > " + loginDetails);

            HashMap vfHashMap = objLoanAtClick.validateLogin(loginDetails);
            System.out.println("[SRVJNDILookup]   processRequest ---------- > Output (HASHMAP) --- > " + vfHashMap);
            SRVUtil.printOut("[SRVJNDILookup]   processRequest ---------- > Output (HASHMAP) --- > " + vfHashMap);

            Gson g = new Gson();

            out.println(g.toJson(vfHashMap));
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            System.out.println("[SRVJNDILookup]   processRequest ERROR ---------- > " + SRVUtil.getExcpStackTrace(ex));
            SRVUtil.printOut("[SRVJNDILookup]   processRequest ERROR ---------- > " + SRVUtil.getExcpStackTrace(ex));
            SRVUtil.printErr("[SRVJNDILookup]   processRequest ERROR ---------- > " + SRVUtil.getExcpStackTrace(ex));
            //e.printStackTrace();
        } catch (Error e) {
            // TODO Auto-generated catch block
            System.out.println("[SRVJNDILookup]   processRequest ERROR ---------- > " + SRVUtil.getErrorStackTrace(e));
            SRVUtil.printOut("[SRVJNDILookup]   processRequest ERROR ---------- > " + SRVUtil.getErrorStackTrace(e));
            SRVUtil.printErr("[SRVJNDILookup]   processRequest ERROR ---------- > " + SRVUtil.getErrorStackTrace(e));
            //e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(SRVJNDILookup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(SRVJNDILookup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(SRVJNDILookup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(SRVJNDILookup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(SRVJNDILookup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(SRVJNDILookup.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(SRVJNDILookup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SRVJNDILookup.class.getName()).log(Level.SEVERE, null, ex);
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
            try {
                processRequest(request, response);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(SRVJNDILookup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(SRVJNDILookup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(SRVJNDILookup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(SRVJNDILookup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(SRVJNDILookup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(SRVJNDILookup.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NamingException ex) {
            Logger.getLogger(SRVJNDILookup.class.getName()).log(Level.SEVERE, null, ex);

        } catch (Exception ex) {
            Logger.getLogger(SRVJNDILookup.class.getName()).log(Level.SEVERE, null, ex);
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

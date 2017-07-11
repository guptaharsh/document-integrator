
/***************************************************************************
 *	Product		: APS-OD
 *	
 *	File		: SRVAddDocument_05072013.java
 *
 *	Purpose:
 *  
 *
 *   Change History:
 *	Problem No      date            changed by           Description    
 *      ---------	-----------	----------          -----------------------
 *      
 *      
 *      
 *      
 *      
 ***************************************************************************/

package com.servo.session;

import ISPack.ISUtil.JPISException;
import com.servo.generateXML.SRVGenerateXML;
import com.servo.parser.SRVGetDataDefXMLHandler;
import com.servo.util.SRVUtil;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;


@WebServlet(name = "SRVAddDocument", urlPatterns = {"/SRVAddDocument"})
public class SRVAddDocument extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception, JPISException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
        int JtsPort = Integer.parseInt(SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerPort());
        short shortJtsport = (short) (JtsPort);

        String retXml = "";
        String JtsIp = SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerIp();
            
            
        String folderId = (String)request.getAttribute("ObjectIndex");
            System.out.println("[SRVAddDocument]    processRequest()  , Object Index of last folder is ========== "+folderId);
            SRVUtil.printOut("[SRVAddDocument]    processRequest()  , Object Index of last folder is ========== "+folderId);
            
    String sFilePath = (String)request.getAttribute("FilePath");            
            System.out.println("[SRVAddDocument]    processRequest()  , sFilePath ------------- > "+sFilePath);
            SRVUtil.printOut("[SRVAddDocument]    processRequest()  , sFilePath ------------- > "+sFilePath);
        String sDocName = (String)request.getAttribute("Nomenclature");  //nomen
        
            System.out.println("[SRVAddDocument]    processRequest()  , Nomenc ------------ > "+sDocName);
            SRVUtil.printOut("[SRVAddDocument]    processRequest()  , Nomenc ------------ > "+sDocName);
        
        String sComments = "";
        
        
        //String uploadFlag = request.getParameter("uploadFlag");
        //String DocIndex = request.getParameter("DocId");
        //String ISIndex = request.getParameter("ISIndex");
        //String sAddType = request.getParameter("addMode");
        //String sAttachIndex = request.getParameter("AttachIndex");
        //String sSiteId = request.getParameter("siteId");
        
        String userDBId = (String)request.getAttribute("UserDBId");
        String applicationFormNo = (String)request.getAttribute("Application_Form_No");
        String parentDocument = (String)request.getAttribute("Parent_Document");
        String childDocument = (String)request.getAttribute("Child_Document");
        String applicantType = (String)request.getAttribute("Applicant_Type");
        String apsId = (String)request.getAttribute("ApsId");
        String strDataClass = (String)request.getAttribute("DataClass");
        
        boolean scandoc = false;
        String strDocumentType = null;

            System.out.println("[SRVAddDocument]    processRequest()  , Fie Separator Char ---------- > "+File.separatorChar);
            System.out.println("[SRVAddDocument]    processRequest()  , Fie Separator ---------- > "+File.separator);
            SRVUtil.printOut("[SRVAddDocument]    processRequest()  , Fie Separator Char ---------- > "+File.separatorChar);
            SRVUtil.printOut("[SRVAddDocument]    processRequest()  , Fie Separator ---------- > "+File.separator);
        File tempDir = new File(sFilePath.substring(0, sFilePath.lastIndexOf(File.separator)));
        File f = new File(sFilePath);
        String ext=null;

        //  To be checked
        String strVolumeIndex = "0";
        if(strVolumeIndex.equals("0"))
        {
            retXml = SRVGenerateXML.getFolderProperty(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(),
                                                                            userDBId,
                                                                            folderId,
                                                                            "N");

            String outputXML = SRVCallBroker.makeCall("NGOGetFolderProperty", retXml);
            System.out.println("[SRVAddDocument]    processRequest()  , getFolderProperty OUTPUT XML ------------ > "+outputXML);
            SRVUtil.printOut("[SRVAddDocument]    processRequest()  , getFolderProperty OUTPUT XML ------------ > "+outputXML);
            HashMap outXMLMap = SRVUtil.getXMLMap(outputXML);
            
            try
            {
                int status = Integer.parseInt((String)outXMLMap.get("Status"));
                if (status != 0 ) 
                {
                    RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                    
                    if(status == -50146)
                    {
                        request.setAttribute("STATUS", "INVALIDSESSION");
                    }
                    else
                    {
                        request.setAttribute("STATUS", "ERROR");
                    }
                    rd.forward(request, response);
                    throw new Exception();
                }
            }
            
            
            catch(Exception e)
            {
                SRVUtil.printOut("[SRVAddDocument]    processRequest()  , ERROR ---------------------- >  getFolderProperty STATUS\n");
                SRVUtil.printErr("[SRVAddDocument]    processRequest()  , ERROR ---------------------- >  getFolderProperty STATUS\n"+SRVUtil.getExcpStackTrace(e));
                
                throw new NumberFormatException();
            }
           
            strVolumeIndex = (String)outXMLMap.get("ImageVolumeIndex");
        }

        //  DataClass handling
//            System.out.println("[SRVAddDocument]    processRequest()  , Data Class from configuration ------------ > "+request.getServletContext().getInitParameter("DATACLASS"));
//            SRVUtil.printOut("[SRVAddDocument]    processRequest()  , Data Class from configuration ------------ > "+request.getServletContext().getInitParameter("DATACLASS"));
            System.out.println("[SRVAddDocument]    processRequest()  , Data Class ------------ > "+strDataClass);
            SRVUtil.printOut("[SRVAddDocument]    processRequest()  , Data Class ------------ > "+strDataClass);
         retXml = SRVGenerateXML.getDataDefIdForName(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(),
                                                                            userDBId,
                                                                            //"APS_OD_DC"
                                                                            //request.getServletContext().getInitParameter("DATACLASS")
                                                                            strDataClass
                                                                            );

            String outputXML = SRVCallBroker.makeCall("NGOGetDataDefIdForName", retXml);
            System.out.println("[SRVAddDocument]    processRequest()  , getDataDefIdForName OUTPUT XML ------------ > "+outputXML);
            SRVUtil.printOut("[SRVAddDocument]    processRequest()  , getDataDefIdForName OUTPUT XML ------------ > "+outputXML);
            HashMap outXMLMap = SRVUtil.getXMLMap(outputXML);
            
            try
            {
                int status = Integer.parseInt((String)outXMLMap.get("Status"));
                if (status != 0 ) 
                {
                    RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                                        
                    if(status == -50146)
                    {
                        request.setAttribute("STATUS", "INVALIDSESSION");
                    }
                    else
                    {
                        request.setAttribute("STATUS", "ERROR");
                    }
                    rd.forward(request, response);
                        
                    throw new Exception();
                }
            }
            catch(Exception e)
            {
                SRVUtil.printOut("[SRVAddDocument]    processRequest()  , ERROR ---------------------- >  getDataDefIdForName STATUS\n");
                SRVUtil.printErr("[SRVAddDocument]    processRequest()  , ERROR ---------------------- >  getDataDefIdForName STATUS\n"+SRVUtil.getExcpStackTrace(e));
                
                RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                                        
                request.setAttribute("STATUS", "ERROR");
                rd.forward(request, response);
                throw new NumberFormatException();
            }
            
            String strDataDefIndex = (String)outXMLMap.get("DataDefIndex");
             
            retXml = SRVGenerateXML.getDataDefProperty(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(), userDBId, strDataDefIndex);
            System.out.println("[SRVAddDocument]    processRequest()  , getDataDefProperty () XML ------------- > "+retXml);
            SRVUtil.printOut("[SRVAddDocument]    processRequest()  , getDataDefProperty () XML ------------- > "+retXml);
            String outputXML_CallBroker = SRVCallBroker.makeCall("NGOGetDataDefProperty", retXml);
            System.out.println("[SRVAddDocument]    processRequest()  , OUTPUT XML DataDefProperty ------------ > "+outputXML_CallBroker);
            SRVUtil.printOut("[SRVAddDocument]    processRequest()  , OUTPUT XML DataDefProperty ------------ > "+outputXML_CallBroker);
            //HashMap outXMLMap_CallBroker = SRVUtil.getXMLMap(outputXML);
            
            SRVGetDataDefXMLHandler srvGetDataDefXMLHandler = new SRVGetDataDefXMLHandler();
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = parserFactory.newSAXParser();
            InputSource inputSource = new InputSource(new StringReader(outputXML_CallBroker));			
            saxParser.parse(inputSource, srvGetDataDefXMLHandler);
            HashMap dataDefMap = srvGetDataDefXMLHandler.getDataMap();
            
            Iterator itr = dataDefMap.entrySet().iterator();
            
            String strDataDefXML = SRVGenerateXML.getDataDefXML(strDataDefIndex, itr, apsId,applicantType, parentDocument, childDocument
                    ,applicationFormNo);
            
            System.out.println("[SRVAddDocument]    processRequest()  , strDataDefXML ------------- > "+strDataDefXML);
            SRVUtil.printOut("[SRVAddDocument]    processRequest()  , strDataDefXML ------------- > "+strDataDefXML);
//            while (itr.hasNext()) 
//            {
//                Map.Entry pairs = (Map.Entry)itr.next();
//                System.out.println(pairs.getKey() + " = " + pairs.getValue());
//                //itr.remove(); // avoids a ConcurrentModificationException
//            }
            
            System.out.println("[SRVAddDocument]    processRequest()  , HashMap dataDefMap ------------- > "+dataDefMap);
            SRVUtil.printOut("[SRVAddDocument]    processRequest()  , HashMap dataDefMap ------------- > "+dataDefMap);
            
            
        
        //if(uploadFlag.equalsIgnoreCase("Y"))
        {
           // if(sAddType.equals("new"))
            {
                    ext = f.getName().substring(f.getName().lastIndexOf(".")+1);
                    System.out.println("[SRVAddDocument]    processRequest()  , Before ADDDocument()");
                    SRVUtil.printOut("[SRVAddDocument]    processRequest()  , Before ADDDocument()");
                    
                    retXml = SRVGenerateXML.AddDocument(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(),
                                                                            userDBId ,
                                                                            "0", //maingroupid
                                                                            folderId, //parentfodlerindex
                                                                            null, //No Of Pages
                                                                            null, //strAccessType
                                                                            sDocName, //strDocumentName
                                                                            null, //strCreatedDateTime
                                                                            null, //strExpiryDateTime
                                                                            null, //strVersionFlag
                                                                            null, //strDocumentType
                                                                            null, //strDocumentSize
                                                                            null, //strCreatedByApp
                                                                            ext, //strCreatedByAppName
                                                                            null, //strIsIndex
                                                                            null, //strTextIsIndex
                                                                            null, //strODMADocumentIndex
                                                                            sComments, //strComment
                                                                            null, //strDocumentAuthor
                                                                            null, //strOwnerIndex
                                                                            null, //strEnableLog
                                                                            null, //strFTSFlag
                                                                            
                                                                            strDataDefXML, //strDataDefXML
                                                                            
                                                                            null, //strKeywords
                                                                            strVolumeIndex,
                                                                            sFilePath);

          
/*   makeCall() call in SRVDMSImpl file   */                    
                    HashMap outXmlMap = SRVUtil.getXMLMap(retXml);
                    
                    
                    try
                    {
                        int status = Integer.parseInt((String)outXmlMap.get("Status"));

                        if (status != 0 ) 
                        {
                            RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");

                            if(status == -50146)
                            {
                                request.setAttribute("STATUS", "INVALIDSESSION");
                            }
                            else
                            {
                                request.setAttribute("STATUS", "ERROR");
                            }
                            rd.forward(request, response);
                                
                        }
                    }
                    catch(Exception e)
                    {
                        SRVUtil.printOut("[SRVAddDocument]    processRequest()  , ERROR ---------------------- >  AddDocument STATUS\n");
                        SRVUtil.printErr("[SRVAddDocument]    processRequest()  , ERROR ---------------------- >  AddDocument STATUS\n"+SRVUtil.getExcpStackTrace(e));
                        
                         RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                                        
                         request.setAttribute("STATUS", "ERROR");
                         rd.forward(request, response);
                         
                        throw new NumberFormatException();
                    }
                    String strISIndex = (String)outXmlMap.get("ISIndex");

                    if (strISIndex.substring(strISIndex.length()-1 , strISIndex.length()).equals("#"))
                            strISIndex = strISIndex.substring(0 , strISIndex.length()-1);

                    
                    RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                
                    request.setAttribute("STATUS", "SUCCESS");
                
                    rd.forward(request, response);
                    
                    f.delete();
            }
        

            f.delete();
            tempDir.delete();
        }
            //out.println("</html>");
        }
        
        catch(JPISException jpise)
        {
            jpise.printStackTrace();
            System.out.println("[SRVAddDocument]    processRequest()  , ERROR JPIS STACK TRACE -------------> \n"+jpise.toString());
            
            SRVUtil.printOut("[SRVAddDocument]    processRequest()  , ERROR JPIS ---------------------- >");
            SRVUtil.printErr("[SRVAddDocument]    processRequest()  , ERROR JPIS ---------------------- > \n"+jpise.toString());
            
            RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                                        
                    request.setAttribute("STATUS", "ERROR");
                    rd.forward(request, response);
        }
        
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("STACK TRACE -------------> \n"+ex.toString());
            
            SRVUtil.printOut("ERROR processRequest() ---------------------- >  AddDocument \n");
            SRVUtil.printErr("ERROR processRequest() ---------------------- >  AddDocument \n"+SRVUtil.getExcpStackTrace(ex));
            
            RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                                        
                    request.setAttribute("STATUS", "ERROR");
                    rd.forward(request, response);
        }
        finally {            
            out.close();
        }
    }
//}

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
            try {
                processRequest(request, response);
            } catch (JPISException ex) {
                Logger.getLogger(SRVAddDocument.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(SRVAddDocument.class.getName()).log(Level.SEVERE, null, ex);
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
            } catch (JPISException ex) {
                Logger.getLogger(SRVAddDocument.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(SRVAddDocument.class.getName()).log(Level.SEVERE, null, ex);
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

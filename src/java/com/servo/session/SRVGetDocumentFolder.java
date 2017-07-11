/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servo.session;

import com.servo.generateXML.SRVGenerateXML;
import com.servo.parser.SRVGetDataDefXMLHandler;
import com.servo.util.SRVUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "SRVGetDocumentFolder", urlPatterns = {"/SRVGetDocumentFolder"})
public class SRVGetDocumentFolder extends HttpServlet {

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
  
//  SERVLET's service ()    
    
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException
	{
            System.out.println("INSIDE ----------- SRVGetDocumentFolder ---------------");
                response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
                response.setHeader("Pragma", "no-cache"); //HTTP 1.0
                response.setDateHeader("Expires", -1); //prevents caching at the proxy server
                PrintWriter out = response.getWriter();
                

		PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this, request, response, "/errorpage.jsp", true, 8192, true);
		try
		{
	            
                        //System.out.println("SRVGetDocumentFolder ---------->");
        
        
                        HttpSession session = request.getSession(false);
        
                        ResourceBundle genRSB = ResourceBundle.getBundle("SRVApsOd_en_IN");
                        
                        int JtsPort = Integer.parseInt(SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerPort());
                        short shortJtsport = (short) (JtsPort);


                        String JtsIp = SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerIp();
                        
                        //System.out.println("--------------GOT Resource Bundle-----------------");
                        
                        response.setContentType("text/html;");

                        //String SRVFolder = (String)request.getAttribute("DOCUMENT_FOLDER");
                        String SRVFolder = (String)request.getServletContext().getInitParameter("DOCUMENT_FOLDER");
                        
                        System.out.println("[SRVGetDocumentFolder]   service() ,  DOCUMENT FOLDER = "+SRVFolder);
                        SRVUtil.printOut("[SRVGetDocumentFolder]   service() ,  DOCUMENT FOLDER = "+SRVFolder);
                        
                        String[] folder = SRVFolder.split("\\\\\\\\");
                        //String[] folder = SRVFolder.split("/");
                        //System.out.println("Folder[] === "+folder);
                        String sessionId = (String)request.getAttribute("UserDBId");
                        System.out.println("[SRVGetDocumentFolder]   service() ,    UserDbId = "+sessionId);
                        SRVUtil.printOut("[SRVGetDocumentFolder]   service() ,    UserDbId = "+sessionId);
                        
                        
                        //String strFolderIndex = request.getParameter("parentFolderIndex");
                        String strFolderIndex = "0";
                        String strParentFolderIndex = "";
                        int status = -1;
                        System.out.println("[SRVGetDocumentFolder]   service() , parentFolderIndex = "+strFolderIndex);
                        SRVUtil.printOut("[SRVGetDocumentFolder]   service() , parentFolderIndex = "+strFolderIndex);
                        
                        StringBuffer retXml = new StringBuffer();
                        for(int i=0; i< folder.length;i++)
                        {
//                            System.out.println("Folder Lenght ------------ > "+folder.length);
//                            System.out.println("Current Folder ------------ > "+folder[i]);
                            retXml = SRVGenerateXML.getIdForName(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(), sessionId, folder[i], strFolderIndex);
                            System.out.println("[SRVGetDocumentFolder]   service() , getIdForName RETXml ----------- > "+retXml);
                            SRVUtil.printOut("[SRVGetDocumentFolder]   service() , getIdForName RETXml ----------- > "+retXml);
                            
                            String outputXML = SRVCallBroker.makeCall("NGOGetIdFromName", retXml.toString());
                            System.out.println("[SRVGetDocumentFolder]   service() ,getIdForName OutputXML (STRING) --------- > "+outputXML);
                            SRVUtil.printOut("[SRVGetDocumentFolder]   service() ,getIdForName OutputXML (STRING) --------- > "+outputXML);
                            
                            HashMap outXMLMap = SRVUtil.getXMLMap(outputXML);
            
                            try
                            {
                                //int status = Integer.parseInt((String)outXMLMap.get("Status"));
                                status = Integer.parseInt((String)outXMLMap.get("Status"));
                                System.out.println("[SRVGetDocumentFolder]   service() , getIdForName STATUS = "+status);
                                SRVUtil.printOut("[SRVGetDocumentFolder]   service() , getIdForName STATUS = "+status);
                                if (status == 0 ) 
                                {
                                    strFolderIndex = (String)outXMLMap.get("ObjectIndex");
                                    strParentFolderIndex = strFolderIndex;
                                }
                                else if(status == -50146){
                                    System.out.println("[SRVGetDocumentFolder]   service() getIdForName ---------- > Invalid Session");
                                    SRVUtil.printOut("[SRVGetDocumentFolder]   service() getIdForName ---------- > Invalid Session");
                                    RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                                    request.setAttribute("STATUS", "INVALIDSESSION");
                                    rd.forward(request, response);
                                }
                                else 
                                {
                                    System.out.println("[SRVGetDocumentFolder]   service() getIdForName ---------- > Folder not found");
                                    SRVUtil.printOut("[SRVGetDocumentFolder]   service() getIdForName ---------- > Folder not found");
                                    RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                                    request.setAttribute("STATUS", "FolderNotFound");
                                    rd.forward(request, response);
                                    
                                }
                           
                            }
                            catch(Exception e)
                            {
                                System.out.println("[SRVGetDocumentFolder]   service() , ERROR (getIdForName) ---------- > "+SRVUtil.getExcpStackTrace(e));
                                SRVUtil.printOut("[SRVGetDocumentFolder]   service() , ERROR (getIdForName) ---------- > ");
                                SRVUtil.printErr("[SRVGetDocumentFolder]   service() , ERROR (getIdForName) ---------- > "+SRVUtil.getExcpStackTrace(e));
                                
                                RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                                request.setAttribute("STATUS", "ERROR");
                                rd.forward(request, response);
                                throw new NumberFormatException();
                            }

                        }
                        
                        //  Search for Application Form No. folder
                            if(status == 0)
                            {
                                //  For create folder and upload document
                                String uploadFlag = (String)request.getAttribute("UploadFlag");
                                if(uploadFlag.equalsIgnoreCase("CU"))
                                {
                                    System.out.println("[SRVGetDocumentFolder]   service()  ----------- > Create Folder and Upload Document ");
                                    SRVUtil.printOut("[SRVGetDocumentFolder]   service()  ----------- > Create Folder and Upload Document ");
                                    
                                    String strFolderName = (String)request.getAttribute("Application_Form_No");
                                    retXml = SRVGenerateXML.addFolder(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(), sessionId, strFolderName, 
                                            strFolderIndex, "", 
                                            "", "", "G", "G", "UserFolder", 
                                            "Y", "0", "0", "");
                                    System.out.println("[SRVGetDocumentFolder]   service() , addFolder RETXml ----------- > "+retXml);
                                    SRVUtil.printOut("[SRVGetDocumentFolder]   service() , addFolder RETXml ----------- > "+retXml);

                                    String outputXML_ADD_FOLDER = SRVCallBroker.makeCall("NGOAddFolder", retXml.toString());
                                    System.out.println("[SRVGetDocumentFolder]   service() , outputXML_ADD_FOLDER XML (STRING) --------- > "+outputXML_ADD_FOLDER.toString());
                                    SRVUtil.printOut("[SRVGetDocumentFolder]   service() , outputXML_ADD_FOLDER XML (STRING) --------- > "+outputXML_ADD_FOLDER.toString());

                                    HashMap outXMLMap_ADD_FOLDER = SRVUtil.getXMLMap(outputXML_ADD_FOLDER);
                                    try
                                    {
                                        status = Integer.parseInt((String)outXMLMap_ADD_FOLDER.get("Status"));
                                        if (status == 0 )
                                        {
                                            strFolderIndex = (String)outXMLMap_ADD_FOLDER.get("FolderIndex");
                                            
                                            //  associating dataclass
                                            String retXml_GET_ID_FOR_NAME = SRVGenerateXML.getDataDefIdForName(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(),
                                                                            sessionId,
                                                                            request.getServletContext().getInitParameter("DATACLASS")
                                                                            );
                                            String outputXML_GET_ID_FOR_NAME = SRVCallBroker.makeCall("NGOGetDataDefIdForName", retXml_GET_ID_FOR_NAME);
                                            System.out.println("[SRVGetDocumentFolder]    processRequest()  , getDataDefIdForName OUTPUT XML ------------ > "+outputXML_GET_ID_FOR_NAME);
                                            SRVUtil.printOut("[SRVGetDocumentFolder]    processRequest()  , getDataDefIdForName OUTPUT XML ------------ > "+outputXML_GET_ID_FOR_NAME);
                                            HashMap outXMLMap_GET_ID_FOR_NAME = SRVUtil.getXMLMap(outputXML_GET_ID_FOR_NAME);
                                            
                                            try
                                            {
                                                status = Integer.parseInt((String)outXMLMap_GET_ID_FOR_NAME.get("Status"));
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
                                                SRVUtil.printOut("[SRVGetDocumentFolder]    processRequest()  , ERROR ---------------------- >  getDataDefIdForName STATUS\n");
                                                SRVUtil.printErr("[SRVGetDocumentFolder]    processRequest()  , ERROR ---------------------- >  getDataDefIdForName STATUS\n"+SRVUtil.getExcpStackTrace(e));

                                                RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");

                                                request.setAttribute("STATUS", "ERROR");
                                                rd.forward(request, response);
                                                throw new NumberFormatException();
                                            }

                                            String strDataDefIndex = (String)outXMLMap_GET_ID_FOR_NAME.get("DataDefIndex");
                                            
                                            String retXml_GET_DATA_DEF_PROPERTY = SRVGenerateXML.getDataDefProperty(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(), sessionId, strDataDefIndex);
                                            System.out.println("[SRVGetDocumentFolder]    processRequest()  , getDataDefProperty () XML ------------- > "+retXml_GET_DATA_DEF_PROPERTY);
                                            SRVUtil.printOut("[SRVGetDocumentFolder]    processRequest()  , getDataDefProperty () XML ------------- > "+retXml_GET_DATA_DEF_PROPERTY);
                                            String outputXML_CallBroker = SRVCallBroker.makeCall("NGOGetDataDefProperty", retXml_GET_DATA_DEF_PROPERTY);
                                            System.out.println("[SRVGetDocumentFolder]    processRequest()  , OUTPUT XML DataDefProperty ------------ > "+outputXML_CallBroker);
                                            SRVUtil.printOut("[SRVGetDocumentFolder]    processRequest()  , OUTPUT XML DataDefProperty ------------ > "+outputXML_CallBroker);
                                            //HashMap outXMLMap_CallBroker = SRVUtil.getXMLMap(outputXML);

                                            SRVGetDataDefXMLHandler srvGetDataDefXMLHandler = new SRVGetDataDefXMLHandler();
                                            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
                                            SAXParser saxParser = parserFactory.newSAXParser();
                                            InputSource inputSource = new InputSource(new StringReader(outputXML_CallBroker));			
                                            saxParser.parse(inputSource, srvGetDataDefXMLHandler);
                                            HashMap dataDefMap = srvGetDataDefXMLHandler.getDataMap();
                                            String strAPSId = (String)request.getAttribute("ApsId");    
                                            Iterator itr = dataDefMap.entrySet().iterator();
                                            StringBuffer retXml_CHANGE_FOLDER_PROPERTY = SRVGenerateXML.changeFolderProperty(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(), sessionId, 
                                                    strFolderIndex, "255", 
                                                    "",
                                                    "UserFolder",strDataDefIndex, itr, strFolderName, strAPSId);
                                            System.out.println("[SRVGetDocumentFolder]    processRequest()  , changeFolderProperty () XML ------------- > "+retXml_CHANGE_FOLDER_PROPERTY);
                                            SRVUtil.printOut("[SRVGetDocumentFolder]    processRequest()  , changeFolderProperty () XML ------------- > "+retXml_CHANGE_FOLDER_PROPERTY);
                                            String outputXML_CHANGE_FOLDER_PROPERTY = SRVCallBroker.makeCall("NGOChangeFolderProperty", retXml_CHANGE_FOLDER_PROPERTY.toString());
                                            System.out.println("[SRVGetDocumentFolder]    processRequest()  , OUTPUT XML ChangeFolderProperty ------------ > "+outputXML_CHANGE_FOLDER_PROPERTY);
                                            SRVUtil.printOut("[SRVGetDocumentFolder]    processRequest()  , OUTPUT XML ChangeFolderProperty ------------ > "+outputXML_CHANGE_FOLDER_PROPERTY);
                                            
                                        }
                                        else if(status == -50124 )
                                        {
                                            System.out.println("[SRVGetDocumentFolder]   service() getIdForName ---------- > Folder not created");
                                            SRVUtil.printOut("[SRVGetDocumentFolder]   service() getIdForName ---------- > Folder not created");
                                            RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                                            request.setAttribute("STATUS", "FOLDERALREADYCREATED");
                                            rd.forward(request, response);
                                        }
                                        else
                                        {
                                            System.out.println("[SRVGetDocumentFolder]   service() getIdForName ---------- > Folder not created");
                                            SRVUtil.printOut("[SRVGetDocumentFolder]   service() getIdForName ---------- > Folder not created");
                                            RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                                            request.setAttribute("STATUS", "FolderNotCreated");
                                            rd.forward(request, response);
                                        }
                                    }
                                    catch(Exception e)
                                    {
                                        System.out.println("[SRVGetDocumentFolder]   service() , ERROR ADD_FOLDER (STATUS) ---------- > "+SRVUtil.getExcpStackTrace(e));
                                        SRVUtil.printOut("[SRVGetDocumentFolder]   service() , ERROR ADD_FOLDER (STATUS) ---------- > ");
                                        SRVUtil.printErr("[SRVGetDocumentFolder]   service() , ERROR ADD_FOLDER (STATUS) ---------- > "+SRVUtil.getExcpStackTrace(e));
                                    }
                                }
                                
                                retXml = SRVGenerateXML.getIdForName(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(), sessionId, (String)request.getAttribute("Application_Form_No"), strParentFolderIndex);
                                System.out.println("[SRVGetDocumentFolder]   service() , getIdForName RETXml ----------- > "+retXml);
                                SRVUtil.printOut("[SRVGetDocumentFolder]   service() , getIdForName RETXml ----------- > "+retXml);

                                String outputXML_GET_ID_FOR_NAME = SRVCallBroker.makeCall("NGOGetIdFromName", retXml.toString());
                                System.out.println("[SRVGetDocumentFolder]   service() , Retrieved outputXML_GET_ID_FOR_NAME XML (STRING) --------- > "+outputXML_GET_ID_FOR_NAME.toString());
                                SRVUtil.printOut("[SRVGetDocumentFolder]   service() , Retrieved outputXML_GET_ID_FOR_NAME XML (STRING) --------- > "+outputXML_GET_ID_FOR_NAME.toString());
                                
                                HashMap outXMLMap = SRVUtil.getXMLMap(outputXML_GET_ID_FOR_NAME);
                                try
                                {
                                    //int status = Integer.parseInt((String)outXMLMap.get("Status"));
                                    status = Integer.parseInt((String)outXMLMap.get("Status"));
                                    System.out.println("[SRVGetDocumentFolder]   service() , Status for last folder is -------------- > "+status);
                                    SRVUtil.printOut("[SRVGetDocumentFolder]   service() , Status for last folder is -------------- > "+status);
                                    if (status == 0 ) 
                                    {
                                        strFolderIndex = (String)outXMLMap.get("ObjectIndex");
                                        
//  Set request attributes to share data with next servlet                                        
                                        RequestDispatcher rd = request.getRequestDispatcher("SRVAddDocument");
                                        
                                        request.setAttribute("FilePath", (String)request.getAttribute("filePath"));
                                        request.setAttribute("User", (String)request.getAttribute("User"));
                                        request.setAttribute("UserDBId", (String)request.getAttribute("UserDBId"));
                                        request.setAttribute("ObjectIndex", strFolderIndex); // Index of last folder
                                        request.setAttribute("Nomenclature", request.getAttribute("Nomenclature")); 
                                        request.setAttribute("Parent_Document", (String)request.getAttribute("Parent_Document"));
                                        request.setAttribute("Child_Document", (String)request.getAttribute("Child_Document"));
                                        request.setAttribute("Applicant_Type", (String)request.getAttribute("Applicant_Type"));
                                        request.setAttribute("Application_Form_No", (String)request.getAttribute("Application_Form_No"));
                                        request.setAttribute("ApsId", (String)request.getAttribute("ApsId"));
                                        
                                        rd.forward(request, response);
                                    }
                                    else
                                    {
                                        //out.println("Folder not found");
                                        System.out.println("Folder not found");
                                        RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                                        request.setAttribute("STATUS", "FolderNotFound");
                                        rd.forward(request, response);
                                        //return "Folder not found";
                                    }
                                }
                                catch(Exception e)
                                {
                                    System.out.println("[SRVGetDocumentFolder]   service() , ERROR (STATUS) ---------- > "+SRVUtil.getExcpStackTrace(e));
                                    SRVUtil.printOut("[SRVGetDocumentFolder]   service() , ERROR (STATUS) ---------- > ");
                                    SRVUtil.printErr("[SRVGetDocumentFolder]   service() , ERROR (STATUS) ---------- > "+SRVUtil.getExcpStackTrace(e));
                                    throw new NumberFormatException();
                                }
                            }
                        
//  ----- Sending the XML                        
                       // out.println(retXml.toString());
                        
                        // @ToDo Parsing of annotations to be done
                        /*StringBuffer strAnnotBuffer = new StringBuffer();
                        String strAnnotType = "";
                        String strAnnotIndex = "";
                        String strLoginUserRights = "";
                        String strFinal = strAnnotBuffer.toString();
                        if(strFinal.equals(""))
                                strFinal = "[AnnotationHeader]\nTotalAnnotations=0";

                        BufferedWriter oStream = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(),strEncoding));
                        oStream.write(strFinal, 0, strFinal.length());	
                        oStream.close();		
                        oStream = null;
                        strAnnotBuffer = null;
                        strFinal = null;
                        */

		}
		catch (Exception e)
		{
                    System.out.println("[SRVGetDocumentFolder]   service() , ERROR ---------- > "+SRVUtil.getExcpStackTrace(e));
                    SRVUtil.printOut("[SRVGetDocumentFolder]   service() , ERROR  ---------- > ");
                    SRVUtil.printErr("[SRVGetDocumentFolder]   service() , ERROR  ---------- > "+SRVUtil.getExcpStackTrace(e));
                    //SRVUtil.printErr(e.getMessage());
		}
	}
}

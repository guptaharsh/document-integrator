
package com.servo.session;

import com.google.gson.Gson;
import com.servo.generateXML.SRVGenerateXML;
import com.servo.parser.SRVErrorXMLHandler;
import com.servo.parser.SRVGetDataDefXMLHandler;
import com.servo.util.SRVUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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


@WebServlet(name = "SRVGetDataDefDetails", urlPatterns = {"/SRVGetDataDefDetails"})
public class SRVGetDataDefDetails extends HttpServlet {

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
            System.out.println("INSIDE ----------- SRVGetDataDefDetails ---------------");
                response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
                response.setHeader("Pragma", "no-cache"); //HTTP 1.0
                response.setDateHeader("Expires", -1); //prevents caching at the proxy server
                PrintWriter out = response.getWriter();
                
				Gson g = new Gson();

		PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this, request, response, "/errorpage.jsp", true, 8192, true);
		try
		{
	            
                        //System.out.println("SRVGetDataDefDetails ---------->");
        
        
                        HttpSession session = request.getSession(false);
        
                        ResourceBundle genRSB = ResourceBundle.getBundle("SRVApsOd_en_IN");
                        
                        int JtsPort = Integer.parseInt(SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerPort());
                        short shortJtsport = (short) (JtsPort);


                        String JtsIp = SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerIp();
                        
                        //System.out.println("--------------GOT Resource Bundle-----------------");
                        
                        response.setContentType("text/html;");

						String requestType = request.getParameter("requestType");
						
                        //	DataDefProperty for Folder
						//if(requestType.equalsIgnoreCase("F") )
                        //{
							String sessionId = (String)request.getParameter("userDBId");
							String strDataClass = request.getParameter("dataclass");
							System.out.println("[SRVGetDataDefDetails]   service() ,    UserDbId = "+sessionId);
							SRVUtil.printOut("[SRVGetDataDefDetails]   service() ,    UserDbId = "+sessionId);
							
							
							int status = -1;
													
							StringBuffer retXml = new StringBuffer();
									
							try
							{
								//  associating dataclass
									String retXml_GET_ID_FOR_NAME = SRVGenerateXML.getDataDefIdForName(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(),
																	sessionId,
																	strDataClass
																	);
									String outputXML_GET_ID_FOR_NAME = SRVCallBroker.makeCall("NGOGetDataDefIdForName", retXml_GET_ID_FOR_NAME);
									System.out.println("[SRVGetDataDefDetails]    processRequest()  , getDataDefIdForName OUTPUT XML ------------ > "+outputXML_GET_ID_FOR_NAME);
									SRVUtil.printOut("[SRVGetDataDefDetails]    processRequest()  , getDataDefIdForName OUTPUT XML ------------ > "+outputXML_GET_ID_FOR_NAME);
									HashMap outXMLMap_GET_ID_FOR_NAME = SRVUtil.getXMLMap(outputXML_GET_ID_FOR_NAME);
									
									try
									{
										status = Integer.parseInt((String)outXMLMap_GET_ID_FOR_NAME.get("Status"));
										if (status != 0 ) 
										{
											SRVErrorXMLHandler srvErrorXMLHandler = new SRVErrorXMLHandler();
											SAXParserFactory parserFactory = SAXParserFactory.newInstance();
											SAXParser saxParser = parserFactory.newSAXParser();
											InputSource inputSource = new InputSource(new StringReader(outputXML_GET_ID_FOR_NAME));			
											saxParser.parse(inputSource, srvErrorXMLHandler);
											HashMap dataDefMap = srvErrorXMLHandler.getDataMap();
											//Map m = new HashMap(dataDefMap);
											out.println(g.toJson(dataDefMap));		//Return output xml
											
										}
										else
										{
											String strDataDefIndex = (String)outXMLMap_GET_ID_FOR_NAME.get("DataDefIndex");
									
											String retXml_GET_DATA_DEF_PROPERTY = SRVGenerateXML.getDataDefProperty(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(), sessionId, strDataDefIndex);
											System.out.println("[SRVGetDataDefDetails]    processRequest()  , getDataDefProperty () XML ------------- > "+retXml_GET_DATA_DEF_PROPERTY);
											SRVUtil.printOut("[SRVGetDataDefDetails]    processRequest()  , getDataDefProperty () XML ------------- > "+retXml_GET_DATA_DEF_PROPERTY);
											String outputXML_GET_DATA_DEF_PROPERTY = SRVCallBroker.makeCall("NGOGetDataDefProperty", retXml_GET_DATA_DEF_PROPERTY);
											System.out.println("[SRVGetDataDefDetails]    processRequest()  , OUTPUT XML DataDefProperty ------------ > "+outputXML_GET_DATA_DEF_PROPERTY);
											SRVUtil.printOut("[SRVGetDataDefDetails]    processRequest()  , OUTPUT XML DataDefProperty ------------ > "+outputXML_GET_DATA_DEF_PROPERTY);
											
											SRVGetDataDefXMLHandler srvGetDataDefXMLHandler = new SRVGetDataDefXMLHandler();
											SAXParserFactory parserFactory = SAXParserFactory.newInstance();
											SAXParser saxParser = parserFactory.newSAXParser();
											InputSource inputSource = new InputSource(new StringReader(outputXML_GET_DATA_DEF_PROPERTY));			
											saxParser.parse(inputSource, srvGetDataDefXMLHandler);
											HashMap dataDefMap = srvGetDataDefXMLHandler.getDataMap();

											out.println(g.toJson(dataDefMap));
											
											//out.println(outputXML_DataDefXMLFolder);
										}
									}
									catch(Exception e)
									{
										SRVUtil.printOut("[SRVGetDataDefDetails]    processRequest()  , ERROR ---------------------- >  getDataDefIdForName STATUS\n");
										SRVUtil.printErr("[SRVGetDataDefDetails]    processRequest()  , ERROR ---------------------- >  getDataDefIdForName STATUS\n"+SRVUtil.getExcpStackTrace(e));
									}
							}
							catch(Exception e)
							{
								System.out.println("[SRVGetDataDefDetails]   service() , ERROR ADD_FOLDER (STATUS) ---------- > "+SRVUtil.getExcpStackTrace(e));
								SRVUtil.printOut("[SRVGetDataDefDetails]   service() , ERROR ADD_FOLDER (STATUS) ---------- > ");
								SRVUtil.printErr("[SRVGetDataDefDetails]   service() , ERROR ADD_FOLDER (STATUS) ---------- > "+SRVUtil.getExcpStackTrace(e));
							}
						//}
						
						/*
                        */
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
                    System.out.println("[SRVGetDataDefDetails]   service() , ERROR ---------- > "+SRVUtil.getExcpStackTrace(e));
                    SRVUtil.printOut("[SRVGetDataDefDetails]   service() , ERROR  ---------- > ");
                    SRVUtil.printErr("[SRVGetDataDefDetails]   service() , ERROR  ---------- > "+SRVUtil.getExcpStackTrace(e));
                    //SRVUtil.printErr(e.getMessage());
		}
		
		finally
		{
			out.close();
		}
	}
}


/***************************************************************************
 *	Product		: APS-OD
 *	
 *	File		: SRVGetDocumentProperty.java
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

package com.servo.getdocument;

import ISPack.ISUtil.JPISException;
import com.google.gson.Gson;
import com.servo.generateXML.SRVGenerateXML;
import com.servo.parser.SRVGetDocumentPropertyParser;
import com.servo.session.SRVCallBroker;
import com.servo.util.SRVUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;


@WebServlet(name = "SRVGetDocumentProperty", urlPatterns = {"/SRVGetDocumentProperty"})
public class SRVGetDocumentProperty extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception, JPISException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Gson g = new Gson();
        
        try {
            
        int JtsPort = Integer.parseInt(SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerPort());
        short shortJtsport = (short) (JtsPort);

        String retXml = "";
        String JtsIp = SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerIp();
            
        
        String strUserDBId = request.getParameter("UserDBId");
        System.out.println("[SRVGetDocumentProperty] processRequest() UserDBId ----------------- > "+strUserDBId);    
        SRVUtil.printOut("[SRVGetDocumentProperty] processRequest() UserDBId ----------------- > "+strUserDBId);    
        
        String strDocumentIndex = request.getParameter("documentIndex");
        System.out.println("[SRVGetDocumentProperty] processRequest() DocumentIndex ----------------- > "+strDocumentIndex);
        SRVUtil.printOut("[SRVGetDocumentProperty] processRequest() DocumentIndex ----------------- > "+strDocumentIndex);
        
        String strParentFolderIndex = request.getParameter("parentFolderIndex");
        System.out.println("[SRVGetDocumentProperty] processRequest() ParentFolderIndex ----------------- > "+strParentFolderIndex);
        SRVUtil.printOut("[SRVGetDocumentProperty] processRequest() ParentFolderIndex ----------------- > "+strParentFolderIndex);
        
        String strDataAlsoFlag = request.getParameter("dataAlsoFlag");
        System.out.println("[SRVGetDocumentProperty] processRequest() DataAlsoFlag ----------------- > "+strDataAlsoFlag);
        SRVUtil.printOut("[SRVGetDocumentProperty] processRequest() DataAlsoFlag ----------------- > "+strDataAlsoFlag);
        
        String strThumbNailsAlsoFlag = request.getParameter("thumbNailsAlsoFlag");
        System.out.println("[SRVGetDocumentProperty] processRequest() ThumbNailsAlsoFlag ----------------- > "+strThumbNailsAlsoFlag);
        SRVUtil.printOut("[SRVGetDocumentProperty] processRequest() ThumbNailsAlsoFlag ----------------- > "+strThumbNailsAlsoFlag);
        
        String strVersionNo = request.getParameter("versionNo");
        System.out.println("[SRVGetDocumentProperty] processRequest() Version No ----------------- > "+strThumbNailsAlsoFlag);
        SRVUtil.printOut("[SRVGetDocumentProperty] processRequest() Version No ----------------- > "+strVersionNo);
                    
                    
                    String retXmlGetDocumentProperty = SRVGenerateXML.getDocumentProperty(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(), strUserDBId, "", strDocumentIndex, strParentFolderIndex, strVersionNo, strDataAlsoFlag, strThumbNailsAlsoFlag);

                    System.out.println("[SRVGetDocumentProperty] [getDocumentProperty ()] XML ------------------ > "+retXmlGetDocumentProperty);
                    SRVUtil.printOut("[SRVGetDocumentProperty] [getDocumentProperty ()] XML ------------------ > "+retXmlGetDocumentProperty);
                    String outputXML_GetDocumentProperty = SRVCallBroker.makeCall("NGOGetDocumentProperty", retXmlGetDocumentProperty);
                    System.out.println("[SRVGetDocumentProperty] DocumentProperty OUTPUT XML ------------ > "+outputXML_GetDocumentProperty);
                    SRVUtil.printOut("[SRVGetDocumentProperty] DocumentProperty OUTPUT XML ------------ > "+outputXML_GetDocumentProperty);
                    
//  Checking status
                    HashMap outputMAP_GetDocumentProperty = new HashMap();
                    outputMAP_GetDocumentProperty = SRVUtil.getXMLMap(outputXML_GetDocumentProperty);
                    
                    /*try
                    {
                        int status = Integer.parseInt((String)outputMAP_GetDocumentProperty.get("Status"));
                        if (status != 0 ) 
                        {
                            if(status == -50146)
                            {
                                out.println("INVALIDSESSION");
                                return;
                            }

                            System.out.println("[SRVGetDocumentProperty]    service() ---------- > SITE ID = "+strSiteId);
                            SRVUtil.printOut("[SRVGetDocumentProperty]    service() ---------- > SITE ID = "+strSiteId);
                        }
                        
                    }
                    catch(Exception e)
                    {
                        System.out.println("[SRVGetDocumentProperty]    service() , ERROR (Status) ---------- > "+SRVUtil.getExcpStackTrace(e));
                        SRVUtil.printOut("[SRVGetDocumentProperty]    service() , ERROR (Status) ---------- > ");
                        SRVUtil.printErr("[SRVGetDocumentProperty]    service() , ERROR (Status) ---------- > "+SRVUtil.getExcpStackTrace(e));
                        throw new NumberFormatException();
                    }
                    */
                    
                    SRVGetDocumentPropertyParser srvGetDataDefXMLHandler = new SRVGetDocumentPropertyParser();
                    SAXParserFactory parserFactory = SAXParserFactory.newInstance();
                    SAXParser saxParser = parserFactory.newSAXParser();
                    InputSource inputSource = new InputSource(new StringReader(outputXML_GetDocumentProperty));			
                    saxParser.parse(inputSource, srvGetDataDefXMLHandler);
                    HashMap dataDefMap = srvGetDataDefXMLHandler.getDataMap();

                    Iterator itr = dataDefMap.entrySet().iterator();
                    
                    HashMap documentProperty = new HashMap();
                    
                    while (itr.hasNext()) 
                    {
                        Map.Entry pairs = (Map.Entry)itr.next();
                        //if(pairs.getKey().toString().equalsIgnoreCase("ApsId") || pairs.getKey().toString().equalsIgnoreCase("ApplicationFormNo"))
                        {
                            documentProperty.put(pairs.getKey(),pairs.getValue());
                            System.out.println("[SRVGetDocumentProperty]    documentProperty ==> "+pairs.getKey() + " = " + pairs.getValue());
                            SRVUtil.printOut("[SRVGetDocumentProperty]    documentProperty ==> "+pairs.getKey() + " = " + pairs.getValue());
                        }
                    }
                    
                    //out.println(g.toJson(dataDefMap));
                    out.println(g.toJson(documentProperty));
            
        }
        
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("ERROR : [SRVGetDocumentProperty]    STACK TRACE -------------> \n"+ex.toString());
            SRVUtil.printOut("ERROR : [SRVGetDocumentProperty]    STACK TRACE -------------> \n"+ex.toString());
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
                Logger.getLogger(SRVGetDocumentProperty.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(SRVGetDocumentProperty.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SRVGetDocumentProperty.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(SRVGetDocumentProperty.class.getName()).log(Level.SEVERE, null, ex);
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

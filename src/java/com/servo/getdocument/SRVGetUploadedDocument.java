/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servo.getdocument;

import ISPack.ISUtil.JPISException;
import com.google.gson.Gson;
import com.servo.generateXML.SRVGenerateXML;
import com.servo.parser.SRVGetDataDefXMLHandler;
import com.servo.parser.SRVSearchDocumentXMLHandler;
import com.servo.session.SRVCallBroker;
import com.servo.util.SRVUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.*;
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

/**
 *
 * @author Administrator
 */
@WebServlet(name = "SRVGetUploadedDocument", urlPatterns = {"/SRVGetUploadedDocument"})
public class SRVGetUploadedDocument extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception, JPISException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        System.out.println("INSIDE -------------- > [SRVGetUploadedDocument]");
        SRVUtil.printOut("INSIDE -------------- > [SRVGetUploadedDocument]");

        try {
            Gson g = new Gson();

            int JtsPort = Integer.parseInt(SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerPort());
            short shortJtsport = (short) (JtsPort);
            System.out.println("[SRVGetUploadedDocument]    processReuest() -------- > JTSPort = " + shortJtsport);
            SRVUtil.printOut("[SRVGetUploadedDocument]    processReuest() -------- > JTSPort = " + shortJtsport);
            String retXml_DataDefIdForName = "";
            String JtsIp = SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerIp();
            System.out.println("[SRVGetUploadedDocument]    processReuest() -------- > JTSIp = " + JtsIp);
            SRVUtil.printOut("[SRVGetUploadedDocument]    processReuest() -------- > JTSIp = " + JtsIp);


            String ApsId = request.getParameter("ApsId");
            System.out.println("[SRVGetUploadedDocument]    processReuest() -------- > APSId = " + ApsId);
            SRVUtil.printOut("[SRVGetUploadedDocument]    processReuest() -------- > APSId = " + ApsId);

            String ApplicationFormNo = request.getParameter("ApplicationFormNo");
            System.out.println("[SRVGetUploadedDocument]    processReuest() -------- > ApplicationFormNo = " + ApplicationFormNo);
            SRVUtil.printOut("[SRVGetUploadedDocument]    processReuest() -------- > ApplicationFormNo = " + ApplicationFormNo);

            String userDBId = request.getParameter("UserDbId");
            System.out.println("[SRVGetUploadedDocument]    processReuest() -------- > UserDBId = " + userDBId);
            SRVUtil.printOut("[SRVGetUploadedDocument]    processReuest() -------- > UserDBId = " + userDBId);

            String strPreviousIndex = request.getParameter("PreviousIndex");
            System.out.println("[SRVGetUploadedDocument]    processReuest() -------- > StartedFrom = " + strPreviousIndex);
            SRVUtil.printOut("[SRVGetUploadedDocument]    processReuest() -------- > StartedFrom = " + strPreviousIndex);

            String strLastSortOrderField = request.getParameter("LastSortOrderField");
            System.out.println("[SRVGetUploadedDocument]    processReuest() -------- > FiledDateTime = " + strLastSortOrderField);
            SRVUtil.printOut("[SRVGetUploadedDocument]    processReuest() -------- > FiledDateTime = " + strLastSortOrderField);

            //  Getting DataDefProperty (DataClass)
            retXml_DataDefIdForName = SRVGenerateXML.getDataDefIdForName(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(),
                    userDBId,
                    //"APS_OD_DC"
                    request.getServletContext().getInitParameter("DATACLASS"));

            String outputXML_DataDefIdForName = SRVCallBroker.makeCall("NGOGetDataDefIdForName", retXml_DataDefIdForName);
            System.out.println("[SRVGetUploadedDocument]    processReuest()  [getDataDefIdForName()]  OUTPUTXML -------- > " + outputXML_DataDefIdForName);
            SRVUtil.printOut("[SRVGetUploadedDocument]    processReuest()  [getDataDefIdForName()]  OUTPUTXML -------- > " + outputXML_DataDefIdForName);
            HashMap outXMLMap = SRVUtil.getXMLMap(outputXML_DataDefIdForName);

            try {
                int status = Integer.parseInt((String) outXMLMap.get("Status"));
                System.out.println("[SRVGetUploadedDocument]    processReuest()  [getDataDefIdForName()]  OUTPUTXML , Status = " + status);
                SRVUtil.printOut("[SRVGetUploadedDocument]    processReuest()  [getDataDefIdForName()]  OUTPUTXML , Status = " + status);
                if (status != 0) {
                    if (status == -50146) {
                        out.println("INVALIDSESSION");
                        return;
                    }
                }
            } catch (Exception e) {
                throw new NumberFormatException();
            }

            String strDataDefIndex = (String) outXMLMap.get("DataDefIndex");

            String retXml_DataDefProperty = SRVGenerateXML.getDataDefProperty(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(), userDBId, strDataDefIndex);
            System.out.println("[SRVGetUploadedDocument]    [getDataDefProperty ()] XML ------------- > " + retXml_DataDefProperty);
            SRVUtil.printOut("[SRVGetUploadedDocument]    [getDataDefProperty ()] XML -------- > " + retXml_DataDefProperty);

            String outputXML_CallBroker = SRVCallBroker.makeCall("NGOGetDataDefProperty", retXml_DataDefProperty);
            System.out.println("[SRVGetUploadedDocument]    [getDataDefProperty ()] OUTPUT XML ------------ > " + outputXML_CallBroker);
            System.out.println("[SRVGetUploadedDocument]    [getDataDefProperty ()] OUTPUT XML ------------ > " + outputXML_CallBroker);



            SRVGetDataDefXMLHandler srvGetDataDefXMLHandler = new SRVGetDataDefXMLHandler();
            SAXParserFactory parserFactoryGetDataDefXMLHandler = SAXParserFactory.newInstance();
            SAXParser saxParser = parserFactoryGetDataDefXMLHandler.newSAXParser();
            InputSource inputSource = new InputSource(new StringReader(outputXML_CallBroker));
            saxParser.parse(inputSource, srvGetDataDefXMLHandler);
            HashMap dataDefMap = srvGetDataDefXMLHandler.getDataMap();


            strDataDefIndex = (String) outXMLMap.get("DataDefIndex");
            System.out.println("[SRVGetUploadedDocument]    processRequest() XML -------- > strDataDefIndex = " + strDataDefIndex);
            SRVUtil.printOut("[SRVGetUploadedDocument]    processRequest() XML -------- > strDataDefIndex = " + strDataDefIndex);


            Iterator itr = dataDefMap.entrySet().iterator();

//                while (itr.hasNext()) 
//                {
//                    Map.Entry pairs = (Map.Entry)itr.next();
//                    System.out.println(pairs.getKey() + " = " + pairs.getValue());
//                    //String[] index = pairs.getValue().toString().split("#");
//
//                }
            //  NoOfRecords to be configurable  [DONE]
            //String retXml_SearchDocument = SRVGenerateXML.searchDocumentExt(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(), userDBId, "0", "Y", "", "", "", "N", "", "", "", "", "0", "", "N", "", "", "B", "D", "0", "1", request.getServletContext().getInitParameter("NOOFRECORDS"), "5", "5",  "", "", "Y", "", "N", "", "", "Y", "Y", "0", "NN", "N", ApsId, ApplicationFormNo, itr, strDataDefIndex);

//                String retXml_SearchDocument = SRVGenerateXML.searchDocumentExt(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(), userDBId, "0", "Y", "", "", "", "N", "", "", "", "", "0", "", "N", "", "", "B", "D", "0", strStartedFrom , request.getServletContext().getInitParameter("NOOFRECORDS"), "5", "5",  "", "", "Y", "", "N", "", "", "Y", "Y", "0", "NN", "N", ApsId, ApplicationFormNo, itr, strDataDefIndex);
//                System.out.println("[SRVGetUploadedDocument]    retXml_SearchDocument XML --------------- > "+retXml_SearchDocument);
//                SRVUtil.printOut("[SRVGetUploadedDocument]    retXml_SearchDocument XML -------- > "+retXml_SearchDocument);
            String retXml_SearchFolder = SRVGenerateXML.searchFolder(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(), userDBId, "0", "Y", "", "", "", "", "", "", "0", "", "O", "1", request.getServletContext().getInitParameter("NOOFRECORDS"), "2", "A", "Y", "G", "N", "N", "Y", ApsId, ApplicationFormNo, itr, strDataDefIndex);
            System.out.println("[SRVGetUploadedDocument]    retXml_SearchDocument XML --------------- > " + retXml_SearchFolder);
            SRVUtil.printOut("[SRVGetUploadedDocument]    retXml_SearchDocument XML -------- > " + retXml_SearchFolder);


            String outputXML_SearchFolder = SRVCallBroker.makeCall("NGOSearchFolder", retXml_SearchFolder);
            System.out.println("[SRVGetUploadedDocument]    outputXML_SearchDocument OUTPUT XML ------------ > " + outputXML_SearchFolder);
            SRVUtil.printOut("[SRVGetUploadedDocument]    outputXML_SearchDocument OUTPUT XML ------------ > " + outputXML_SearchFolder);

            HashMap outXMLMap_SearchFolder = SRVUtil.getXMLMap(outputXML_SearchFolder);

            String strFolderIndex = (String) outXMLMap_SearchFolder.get("FolderIndex");
            System.out.println("[SRVGetUploadedDocument]    strFolderIndex ------------ > " + strFolderIndex);
            SRVUtil.printOut("[SRVGetUploadedDocument]    strFolderIndex ------------ > " + strFolderIndex);


            try {
                int status = Integer.parseInt((String) outXMLMap_SearchFolder.get("Status"));
                System.out.println("[SRVGetUploadedDocument]    processReuest()  [searchFolder()]  OUTPUTXML , Status = " + status);
                SRVUtil.printOut("[SRVGetUploadedDocument]    processReuest()  [searchFolder()]  OUTPUTXML , Status = " + status);
                if (status != 0) {
                    if (status == -50146) {
                        out.println("INVALIDSESSION");
                        return;
                    }
                } else {
                    System.out.println("map >>>>>"+outXMLMap_SearchFolder);
                    int noOfRec = Integer.parseInt((String) outXMLMap_SearchFolder.get("NoOfRecordsFetched"));
                    if (noOfRec == 0) {
                        out.println("FOLDERNOTFOUND");
                        return;
                    }
                }

            } catch (Exception e) {
                throw new NumberFormatException();
            }
            
            String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            //String retXml_GetDocumentList = SRVGenerateXML.getDocumentList(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(), userDBId, "", strFolderIndex, "", strPreviousIndex, strLastSortOrderField, "0", request.getServletContext().getInitParameter("NOOFRECORDS"), "5", "A", "N", "Y", "Y", "0", "", "2", "A", request.getServletContext().getInitParameter("NOOFRECORDS"), "B", "N", "N");
            String retXml_GetDocumentList = SRVGenerateXML.getDocumentList(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(), userDBId, currentDateTime, strFolderIndex, "", strPreviousIndex, strLastSortOrderField, "0", request.getServletContext().getInitParameter("NOOFRECORDS"), "5", "D", "N", "Y", "Y", "0", "", "2", "D", request.getServletContext().getInitParameter("NOOFRECORDS"), "B", "N", "N");
            System.out.println("[SRVGetUploadedDocument]    retXml_GetDocumentList XML --------------- > " + retXml_GetDocumentList);
            SRVUtil.printOut("[SRVGetUploadedDocument]    retXml_GetDocumentList XML -------- > " + retXml_GetDocumentList);


            String outputXML_GetDocumentList = SRVCallBroker.makeCall("NGOGetDocumentListExt", retXml_GetDocumentList);
            System.out.println("[SRVGetUploadedDocument]    outputXML_GetDocumentList OUTPUT XML ------------ > " + outputXML_GetDocumentList);
            SRVUtil.printOut("[SRVGetUploadedDocument]    outputXML_GetDocumentList OUTPUT XML ------------ > " + outputXML_GetDocumentList);

////  For Batching                
//                HashMap outXMLMap_SearchDocument = SRVUtil.getXMLMap(outputXML_SearchDocument);
//                
//                try
//                {
//                    int maxHitCount = Integer.parseInt((String)outXMLMap.get("MaximumHitCount"));
//                    int noOfRecoredsFetched = Integer.parseInt((String)outXMLMap.get("NoOfRecordsFetched"));
//                    if (noOfRecoredsFetched < maxHitCount ) 
//                    {
//                        throw new Exception();
//                    }
//                }
//                catch(Exception e)
//                {
//                    throw new NumberFormatException();
//                }

            SRVSearchDocumentXMLHandler srvSearchDocuemntXMLHandler = new SRVSearchDocumentXMLHandler();
            SAXParserFactory parserFactorysrvSearchDocuemntXMLHandler = SAXParserFactory.newInstance();
            SAXParser saxParserSearchDocuemntXMLHandler = parserFactorysrvSearchDocuemntXMLHandler.newSAXParser();
            InputSource inputSourceSearchDocuemntXMLHandler = new InputSource(new StringReader(outputXML_GetDocumentList));
            saxParserSearchDocuemntXMLHandler.parse(inputSourceSearchDocuemntXMLHandler, srvSearchDocuemntXMLHandler);
            LinkedHashMap mapSearchDocuemntXMLHandler = (LinkedHashMap)srvSearchDocuemntXMLHandler.getDataMap();

            Iterator itrSearchDocuemntXMLHandler = mapSearchDocuemntXMLHandler.entrySet().iterator();
            //strDataDefIndex = (String)outXMLMap.get("DataDefIndex");
            //String strVolumeIndex = (String)outXMLMap_SearchDocument.get("ImageVolumeIndex");

            System.out.println("[SRVGetUploadedDocument]    mapSearchDocuemntXMLHandler ------------ > ");
            SRVUtil.printOut("[SRVGetUploadedDocument]    mapSearchDocuemntXMLHandler ------------ > ");

            while (itrSearchDocuemntXMLHandler.hasNext()) {
                Map.Entry pairs = (Map.Entry) itrSearchDocuemntXMLHandler.next();
                SRVUtil.printOut("[SRVGetUploadedDocument]    " + pairs.getKey() + " = " + pairs.getValue());
                System.out.println("[SRVGetUploadedDocument]    " + pairs.getKey() + " = " + pairs.getValue());
                //String[] index = pairs.getValue().toString().split("#");

            }

            out.println(g.toJson(mapSearchDocuemntXMLHandler));

//                SRVGetDataDefXMLHandler srvGetDataDefXMLHandler = new SRVGetDataDefXMLHandler();
//                SAXParserFactory parserFactory = SAXParserFactory.newInstance();
//                SAXParser saxParser = parserFactory.newSAXParser();
//                InputSource inputSource = new InputSource(new StringReader(outputXML_CallBroker));			
//                saxParser.parse(inputSource, srvGetDataDefXMLHandler);
//                HashMap dataDefMap = srvGetDataDefXMLHandler.getDataMap();
//
//                Iterator itr = dataDefMap.entrySet().iterator();
//
//                String strDataDefXML = SRVGenerateXML.getDataDefXML(strDataDefIndex, itr, request.getParameter("ApsId"),request.getParameter("ApplicantType"), request.getParameter("ParentDocument"), request.getParameter("ChildDocument")
//                        ,request.getParameter("ApplicationFormNo"));
//
//                System.out.println("strDataDefXML ------------- > "+strDataDefXML);
            //            while (itr.hasNext()) 
            //            {
            //                Map.Entry pairs = (Map.Entry)itr.next();
            //                System.out.println(pairs.getKey() + " = " + pairs.getValue());
            //                //itr.remove(); // avoids a ConcurrentModificationException
            //            }

            //System.out.println("HashMap dataDefMap ------------- > "+dataDefMap);



            //if(uploadFlag.equalsIgnoreCase("Y"))

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR : [SRVGetUploadedDocument]    STACK TRACE -------------> \n" + ex.toString());
            SRVUtil.printOut("ERROR : [SRVGetUploadedDocument]    STACK TRACE -------------> \n" + ex);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SRVGetUploadedDocument.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JPISException ex) {
            Logger.getLogger(SRVGetUploadedDocument.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (Exception ex) {
            Logger.getLogger(SRVGetUploadedDocument.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JPISException ex) {
            Logger.getLogger(SRVGetUploadedDocument.class.getName()).log(Level.SEVERE, null, ex);
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

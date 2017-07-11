package com.servo.session;

import ISPack.CPISDocumentTxn;
import ISPack.CPISReportTxn;
import ISPack.ISUtil.JPISException;
import ISPack.ISUtil.JPISIsIndex;
import Jdts.DataObject.JPDBInteger;
import Jdts.DataObject.JPDBString;
import com.newgen.niplj.fileformat.Tif6;
import com.newgen.niplj.io.RandomInputStream;
import com.servo.generateXML.SRVGenerateXML;
import com.servo.util.SRVUtil;
import java.io.*;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SRVGetDocument", urlPatterns = {"/SRVGetDocument"})
public class SRVGetDocument extends HttpServlet {

    /**
     * ******************************************************************
     * Function name : service Description : Return type : public void Argument
     * 1 : HttpServletRequest request Argument 2 : HttpServletResponse response
     * ******************************************************************
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        HttpSession session = request.getSession(true);
        ResourceBundle genRSB = ResourceBundle.getBundle("SRVApsOd_en_IN");

        //PrintWriter pw = response.getWriter();

        System.out.println("INSIDE ---------- SRVGetDocument -----------");
        SRVUtil.printOut("INSIDE ---------- SRVGetDocument -----------");

        String sOption = request.getParameter("Option");
        if (sOption == null || sOption.equalsIgnoreCase("null")) {
            sOption = "";
        }

        byte[] out = null;
        ServletOutputStream oStream = null;
        boolean callFlag = true;
        String strVolIndex = null;
        String strImgIndex = "";
        String strDocIndex = "";
        //String strDocExt = "";
        //String strPageNo = "";

        SRVUtil.printOut("[SRVGetDocument] \r\n\r\n " + new java.util.Date() + "\t DownloadFlag = " + request.getParameter("DownloadFlag") + "\t DocName = " + request.getParameter("DocumentName"));
        try {
            System.out.println("[SRVGetDocument]    service() -------------- > CallFlag = " + callFlag);
            SRVUtil.printOut("[SRVGetDocument]    service() -------------- > CallFlag = " + callFlag);
            if (sOption.equalsIgnoreCase("EXT")) {
                callFlag = false;
                String strJtsIp = request.getParameter("JTSIP");
                int nJtsPort = 3333;

                if (strJtsIp == null || strJtsIp.equals("")) {

                    strJtsIp = SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerIp();

                    nJtsPort = (short) Integer.parseInt(SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerPort());
                } else {
                    nJtsPort = Integer.parseInt(request.getParameter("JTSPORT"));
                }

                System.out.println("[SRVGetDocument]    service() -------------- > JTSIp = " + strJtsIp + " , JSTPort = " + nJtsPort);
                SRVUtil.printOut("[SRVGetDocument]    service() -------------- > JTSIp = " + strJtsIp + " , JSTPort = " + nJtsPort);

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
                StringBuffer stringBuffer = new StringBuffer();
                String sIn = null;

                while ((sIn = bufferedReader.readLine()) != null) {
                    stringBuffer.append(sIn);
                    stringBuffer.append("\n");
                }

                //String strInputXml = stringBuffer.toString();
                //System.out.println("test=" + strInputXml);

//                strImgIndex = request.getParameter("ImgIndex");
//                strVolIndex = request.getParameter("VolIndex");
//                strDocIndex = request.getParameter("DocIndex");
//                String strPageNo = request.getParameter("PageNo");
//
//                SRVGetDocument(response, strJtsIp, nJtsPort, docData.getVal("CabinetName"), docData.getVal("DocExt"),
//                 docData.getVal("DownloadFlag"), docData.getVal("PageNo"), docData.getVal("SiteId"), strVolIndex, strImgIndex, genRSB);
            } else {
                //String strEncoding = genRSB.getString("Encoding");
                String strEncoding = ("UTF-8");
                request.setCharacterEncoding(strEncoding);

                String strISIndex = request.getParameter("ISIndex");
                System.out.println("[SRVGetDocument]    service() -------------- > Isindex = " + strISIndex);
                SRVUtil.printOut("[SRVGetDocument]    service() -------------- > Isindex = " + strISIndex);
                //System.out.println("Isindex1= " + strISIndex);
                strImgIndex = strISIndex.substring(0, strISIndex.indexOf("#"));
                System.out.println("[SRVGetDocument]    service() -------------- > Imindex = " + strImgIndex);
                SRVUtil.printOut("[SRVGetDocument]    service() -------------- > Imindex = " + strImgIndex);
                //System.out.println("Imindex1= " + strImgIndex);
                strISIndex = strISIndex.substring(strISIndex.indexOf("#") + 1, strISIndex.length());
                System.out.println("[SRVGetDocument]    service() -------------- > Imindex1 = " + strISIndex);
                 SRVUtil.printOut("[SRVGetDocument]    service() -------------- > Imindex1 = " + strISIndex);
                if (strISIndex.indexOf("#") == -1) {
                    strVolIndex = strISIndex.substring(0, strISIndex.length());
                } else {
                    strVolIndex = strISIndex.substring(0, strISIndex.indexOf("#"));

                    //System.out.println("steVolindex= " + strVolIndex);
                }

                System.out.println("[SRVGetDocument]    service() -------------- > strVolindex = " + strVolIndex);
                SRVUtil.printOut("[SRVGetDocument]    service() -------------- > strVolindex = " + strVolIndex);

                String strDocExt = request.getParameter("DocExt");

//                String strPageNo = request.getParameter("PageNo");
//
//                System.out.println("Page Number is ------------ > "+strPageNo);
//                SRVUtil.printOut("Page Number is ------------ > "+strPageNo);

                String strSiteId = "1";

//  Getting SITE ID
                StringBuffer retXML = SRVGenerateXML.getVolumeAttributes(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(), strVolIndex);

                String outputXML_VolAttributes = "";
                HashMap outXMLMap = new HashMap();
                try {
                    outputXML_VolAttributes = SRVCallBroker.makeCall("NGOISGetVolumeAttributes", retXML.toString());
                    outXMLMap = SRVUtil.getXMLMap(outputXML_VolAttributes);

                } catch (Exception ex) {
                    System.out.println("[SRVGetDocument]    service() , ERROR (outXMLMap) -------------- > " + SRVUtil.getExcpStackTrace(ex));
                    SRVUtil.printOut("[SRVGetDocument]    service() , ERROR (outXMLMap) -------------- > ");
                    SRVUtil.printErr("[SRVGetDocument]    service() , ERROR (outXMLMap) -------------- > " + SRVUtil.getExcpStackTrace(ex));
                    //Logger.getLogger(SRVGetDocument.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("OUTPUT XML ------------ > " + outputXML_VolAttributes);

//  SITE ID from xml
                try {
                    int status = Integer.parseInt((String) outXMLMap.get("Status"));
                    if (status == 0) {
                        strSiteId = outXMLMap.get("PreferredSiteId").toString();

                        System.out.println("[SRVGetDocument]    service() ---------- > SITE ID = " + strSiteId);
                        SRVUtil.printOut("[SRVGetDocument]    service() ---------- > SITE ID = " + strSiteId);
                    }
//                    else if (status == -50146 )
//                    {
//                        pw.println("INVALIDSESSION");
//                        return;
//                    }
                } catch (Exception e) {
                    System.out.println("[SRVGetDocument]    service() , ERROR (Status) ---------- > " + SRVUtil.getExcpStackTrace(e));
                    SRVUtil.printOut("[SRVGetDocument]    service() , ERROR (Status) ---------- > ");
                    SRVUtil.printErr("[SRVGetDocument]    service() , ERROR (Status) ---------- > " + SRVUtil.getExcpStackTrace(e));
                    throw new NumberFormatException();
                }

                //String strPageNo = request.getParameter("PageNo");
                String strPageNo = request.getParameter("PageNo");

                System.out.println("[SRVGetDocument]    service() ---------- > Page Number = " + strPageNo);
                SRVUtil.printOut("[SRVGetDocument]    service() ---------- > Page Number = " + strPageNo);

                strDocIndex = request.getParameter("DocIndex");
                String strDownloadFlag = request.getParameter("DownloadFlag");

                if (strDownloadFlag == null || strDownloadFlag.equals("")) {
                    strDownloadFlag = "N";
                }

                response.setStatus(HttpServletResponse.SC_OK);
                response.setDateHeader("Date", System.currentTimeMillis());

                if (strDocExt.equalsIgnoreCase("zip")) {
                    strDownloadFlag = "Y";
                }

                System.out.println("[SRVGetDocument]    service() ---------- > Download Flag = " + strDownloadFlag);
                SRVUtil.printOut("[SRVGetDocument]    service() ---------- > Download Flag = " + strDownloadFlag);

                String docName = request.getParameter("DocumentName");
                System.out.println("[SRVGetDocument]    service() ---------- > Document Name = " + docName);
                SRVUtil.printOut("[SRVGetDocument]    service() ---------- > Document Name = " + docName);

                docName = (docName == null ? "" : docName);

//                String sComment = request.getParameter("Comment");
//                String encStr = "";
//                StringBuffer name = new StringBuffer(docName);
//                int pos;


//
//                docName = name.toString();
//
//                if (!(sComment == null || sComment.equals(""))) {
//                    docName = docName + "-" + sComment;
//                }

                if (!(strDocExt == null || strDocExt.equals(""))) {
                    docName = docName + "." + strDocExt;
                    System.out.println("[SRVGetDocument]    service() ---------- > DOC Name = " + docName);
                    SRVUtil.printOut("[SRVGetDocument]    service() ---------- > DOC Name = " + docName);
                }

                response.setHeader("Content-Disposition", "attachment;filename=\"" + docName + "\"");
                response.setContentType("Application/octet-stream");
                response.setHeader("Cache-Control", "private, max-age=15");//IE8 Bug: Solution  "Cache-Control", "private, max-age=15"
                //@ToDo Check whether Download flag required
//                if ("Y".equalsIgnoreCase(strDownloadFlag)) {
//
//                //    response.setHeader("Content-Disposition", "attachment;filename=\"" + docName + "\"");
//
//
//                } else {
//                   //
//                }

                if (callFlag) {

                    System.out.println("[SRVGetDocument]    service() ---------- > IF (CallFlag)");
                    SRVUtil.printOut("[SRVGetDocument]    service() ---------- > IF (CallFlag)");

                    SRVGetDocument(response, SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerIp(), Integer.parseInt(SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerPort()), SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(), strDocExt, strDownloadFlag, strPageNo, strSiteId, strVolIndex, strImgIndex, genRSB);
                } else {

                    System.out.println("[SRVGetDocument]    service() ---------- > ELSE (CallFlag)");
                    SRVUtil.printOut("[SRVGetDocument]    service() ---------- > ELSE (CallFlag)");

                    oStream = response.getOutputStream();
                    for (int i = 0; i < out.length; i++) {
                        oStream.write(out[i]);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("[SRVGetDocument]    service() , ERROR ---------- > " + SRVUtil.getExcpStackTrace(e));
            SRVUtil.printOut("[SRVGetDocument]    service() , ERROR ---------- > ");
            SRVUtil.printErr("[SRVGetDocument]    service() , ERROR ---------- > " + SRVUtil.getExcpStackTrace(e));
        } finally {
            try {
                if (!callFlag && oStream != null) {
                    oStream.flush();
                    oStream.close();
                }
            } catch (IOException ioex) {
            }
        }
    }

    public void SRVGetDocument(HttpServletResponse response, String strJtsIp, int nJtsPort, String sEngineName,
            String strDocExt, String strDownloadFlag, String strPageNo, String strSiteId, String strVolIndex, String strImgIndex, ResourceBundle genRSB) {

        System.out.println("[SRVGetDocument] INSIDE --------- SRVGetDocument() -----------");
        SRVUtil.printOut("[SRVGetDocument] INSIDE --------- SRVGetDocument() -----------");

        SRVUtil.printOut("[SRVGetDocument] \r\n\r\n " + new java.util.Date() + "\t strJtsIp = " + strJtsIp + "\t nJtsPort = " + nJtsPort);

        ServletOutputStream oStream = null;

        JPISIsIndex NewIsIndex = new JPISIsIndex();
        NewIsIndex.m_sVolumeId = Short.parseShort(strVolIndex);
        NewIsIndex.m_nDocIndex = Integer.parseInt(strImgIndex);
        JPDBString VolBlockPath = new JPDBString();
        JPDBString DiskVolBlockPath = new JPDBString();
        JPDBString ImgFilename = new JPDBString();
        JPDBInteger DocSize = new JPDBInteger();
        JPDBInteger DocOffset = new JPDBInteger();

        System.out.println("[SRVGetDocument]  SRVGetDocument() ---------- > genRSB = " + genRSB);
        SRVUtil.printOut("[SRVGetDocument]  SRVGetDocument() ---------- > genRSB = " + genRSB);

        String pnFileMessage = genRSB.getString("PN_FILE_NOT_FOUND");
        ByteArrayOutputStream bOutStream = new ByteArrayOutputStream();
        RandomInputStream ris = null;

        try {
            JPDBString siteName = new JPDBString();
            long l1, l2;

            if (!strDownloadFlag.equalsIgnoreCase("Y") && (strDocExt.equalsIgnoreCase("tiff") || strDocExt.equalsIgnoreCase("tif")) && !strPageNo.equals("0")) {
                byte[] jpgBuffer = null;
                l1 = System.currentTimeMillis();
                try {
                    System.out.println("[SRVGetDocument]   SRVGetDocument()   \r\n\r\n " + new java.util.Date() + "\t strJtsIp = " + strJtsIp + "\t nJtsPort = " + nJtsPort + "\t EngineName = " + sEngineName + "\t strSiteId = " + strSiteId + "\t strVolIndex = " + strVolIndex + "\t strImgIndex = " + strImgIndex + "\t strPageNo = " + strPageNo + "\t bOutStream = " + bOutStream);
                    SRVUtil.printOut("[SRVGetDocument]   SRVGetDocument()   \r\n\r\n " + new java.util.Date() + "\t strJtsIp = " + strJtsIp + "\t nJtsPort = " + nJtsPort + "\t EngineName = " + sEngineName + "\t strSiteId = " + strSiteId + "\t strVolIndex = " + strVolIndex + "\t strImgIndex = " + strImgIndex + "\t strPageNo = " + strPageNo + "\t bOutStream = " + bOutStream);
                    CPISDocumentTxn.GetPage_MT(null, strJtsIp, (short) nJtsPort, sEngineName, Short.parseShort(strSiteId), Short.parseShort(strVolIndex), Integer.parseInt(strImgIndex), null, Integer.parseInt(strPageNo), bOutStream);
//                    System.out.println("[SRVGetDocument]   SRVGetDocument()   ----------- > bOutStream(Array) = " + bOutStream.toByteArray());
//                    SRVUtil.printOut("[SRVGetDocument]   SRVGetDocument()   ----------- > bOutStream(Array) = " + bOutStream.toByteArray());
                    SRVUtil.printOut("[SRVGetDocument] SRVGetDocument()  , IF(txt) : bOutStream ------- > Image output Disabled");

                } catch (Exception e) {

                    System.out.println("[SRVGetDocument]   SRVGetDocument()  , ERROR ----------- > " + SRVUtil.getExcpStackTrace(e));
                    SRVUtil.printOut("[SRVGetDocument]   SRVGetDocument()  , ERROR ----------- > ");
                    SRVUtil.printErr("[SRVGetDocument]   SRVGetDocument()  , ERROR ----------- > " + SRVUtil.getExcpStackTrace(e));


                }

                l2 = System.currentTimeMillis();
                ris = new RandomInputStream(bOutStream.toByteArray());



                try {
                    System.out.println("[SRVGetDocument]   SRVGetDocument()  ----------- > ris = " + ris);
                    SRVUtil.printOut("[SRVGetDocument]   SRVGetDocument()  ----------- > ris = " + ris);

                    jpgBuffer = Tif6.isTifJpegCompressed(ris, Integer.parseInt("1"));

                    System.out.println("[SRVGetDocument]   SRVGetDocument()  ----------- >  jpgBuffer = " + jpgBuffer);
                    SRVUtil.printOut("[SRVGetDocument]   SRVGetDocument()  ----------- >  jpgBuffer = " + jpgBuffer);
                } catch (Exception e) {

                    System.out.println("[SRVGetDocument]   SRVGetDocument()  , ERROR (isTifJpegCompressed) ----------- > " + SRVUtil.getExcpStackTrace(e));
                    SRVUtil.printOut("[SRVGetDocument]   SRVGetDocument()  , ERROR (isTifJpegCompressed) ----------- > ");
                    SRVUtil.printErr("[SRVGetDocument]   SRVGetDocument()  , ERROR (isTifJpegCompressed) ----------- > " + SRVUtil.getExcpStackTrace(e));
                }

                if (jpgBuffer != null) {
                    response.setContentLength(jpgBuffer.length);

                    System.out.println("[SRVGetDocument]    SRVGetDocument()  , DocSize1 = " + jpgBuffer.length);
                    SRVUtil.printOut("[SRVGetDocument]  SRVGetDocument()  , DocSize1 = " + jpgBuffer.length);

                } else {
                    response.setContentLength(bOutStream.size());// @ToDo To be tested and uncommented
                    // response.setBufferSize(bOutStream.size());

                    System.out.println("[SRVGetDocument] SRVGetDocument()  ,DocSize2 = " + bOutStream.size());
                    SRVUtil.printOut("[SRVGetDocument]  SRVGetDocument()  , DocSize2 = " + bOutStream.size());
                }
                //ServletOutputStream oStream = null;
                oStream = response.getOutputStream();

//                System.out.println("[SRVGetDocument] SRVGetDocument()  , BOUTStream ------------ > other than text = "+bOutStream);
//                SRVUtil.printOut("[SRVGetDocument] SRVGetDocument()  , BOUTStream ------------ > other than text = "+bOutStream);
                SRVUtil.printOut("[SRVGetDocument] SRVGetDocument()  , IF(txt) : bOutStream ------- > Image output Disabled");

                if (jpgBuffer != null) {
                    System.out.println("1>>");
                    oStream.write(jpgBuffer);
                } else {
                    //PrintWriter out = response.getWriter();
                    System.out.println("2>>");
                    bOutStream.writeTo(oStream);

                }

                if (ris != null) {
                    ris.close();
                }
                if (bOutStream != null) {
                    bOutStream.close();
                }

            } else {
                if (strDocExt.equalsIgnoreCase("txt")) {
                    l1 = System.currentTimeMillis();
                    CPISDocumentTxn.GetDocInFile_MT(null, strJtsIp, (short) nJtsPort, sEngineName, Short.parseShort(strSiteId), Short.parseShort(strVolIndex), Integer.parseInt(strImgIndex), null, bOutStream, siteName);
                    l2 = System.currentTimeMillis();

//                        System.out.println("[SRVGetDocument] SRVGetDocument()  , IF(txt) : BoutStream ------------ > "+bOutStream);
//                        SRVUtil.printOut("[SRVGetDocument] SRVGetDocument()  , IF(txt) : BoutStream ------------ > "+bOutStream);
                    SRVUtil.printOut("[SRVGetDocument] SRVGetDocument()  , IF(txt) : bOutStream ------- > Image output Disabled");

                    //ByteArrayOutputStream byteArrStream = new ByteArrayOutputStream();
                    //byteArrStream = bOutStream.toByteArray();
                    // byteArrStream = bOutStream;
                    byte[] out = null;
                    oStream = response.getOutputStream();

                    // bOutStream = byteArrStream;
                    out = bOutStream.toByteArray();


//                        System.out.println("[SRVGetDocument] SRVGetDocument()  , IF(txt) : bOutStream = "+bOutStream+" \n OUT = "+out);
//                        SRVUtil.printOut("[SRVGetDocument] SRVGetDocument()  , IF(txt) : bOutStream ------- > "+bOutStream+" \n OUT = "+out);
                    SRVUtil.printOut("[SRVGetDocument] SRVGetDocument()  , IF(txt) : bOutStream ------- > Image output Disabled");

                    // response.setContentLength(out.length);

                    for (int i = 0; i < out.length; i++) {
                        oStream.write(out[i]);
                    }
                    if (bOutStream != null) {
                        bOutStream.close();
                    }
                    if (oStream != null) {
                        oStream.close();
                    }
                } else {
                    l1 = System.currentTimeMillis();
                    CPISDocumentTxn.GetDocInFile_MT(null, strJtsIp, (short) nJtsPort, sEngineName, Short.parseShort(strSiteId), Short.parseShort(strVolIndex), Integer.parseInt(strImgIndex), null, response.getOutputStream(), siteName);
                    l2 = System.currentTimeMillis();
                }
            }

            SRVUtil.printOut("[SRVGetDocument] \r\n\r\n Time Taken in fetching document = " + (l2 - l1));
        } catch (JPISException ex) {

            //ex.printStackTrace();
            System.out.println("[SRVGetDocument]   SRVGetDocument()  , ERROR (JPIS) ----------- > " + ex.getStackTrace());
            SRVUtil.printOut("[SRVGetDocument]   SRVGetDocument()  , ERROR (JPIS) ----------- > ");
            SRVUtil.printErr("[SRVGetDocument]   SRVGetDocument()  , ERROR (JPIS) ----------- > " + ex.getStackTrace());

            SRVUtil.printOut("[SRVGetDocument] \r\n In JPIS Exp = " + ex.m_strMsg);
            String chkMssg = "";
            if ((ex.m_strMsg.indexOf("Image File was not found") > 0) || (ex.m_strMsg.indexOf("Directory path was not found") > 0)) {

                System.out.println("[SRVGetDocument]   SRVGetDocument()  , IF(m_strMsg) ----------- > ");
                SRVUtil.printOut("[SRVGetDocument]   SRVGetDocument()  , IF(m_strMsg) ----------- > ");
                try {
                    CPISReportTxn.GetDocForRemoteAccess_MT(null, strJtsIp, (short) nJtsPort, sEngineName, NewIsIndex, VolBlockPath, DiskVolBlockPath, ImgFilename, DocSize, DocOffset);
                    String pnFileName = ImgFilename.value;
                    String location = DiskVolBlockPath.value;
                    chkMssg = pnFileMessage + " " + location + File.separator + pnFileName;
                } catch (JPISException jp) {

                    System.out.println("[SRVGetDocument]   SRVGetDocument()  , ERROR (CPISReportTxn.GetDocForRemoteAccess_MT) ----------- > " + jp.getStackTrace());
                    SRVUtil.printOut("[SRVGetDocument]   SRVGetDocument()  , ERROR (CPISReportTxn.GetDocForRemoteAccess_MT) ----------- > ");
                    SRVUtil.printErr("[SRVGetDocument]   SRVGetDocument()  , ERROR (CPISReportTxn.GetDocForRemoteAccess_MT) ----------- > " + jp.getStackTrace());

                }
            } else {
                chkMssg = "Error in fetching document from Image Server";
            }
            try {

                SRVUtil.printOut("[SRVGetDocument] \r\n chkMssg = " + chkMssg);
                byte[] chkMssgBytes = chkMssg.getBytes();
                byte[] b1 = {(byte) 73, (byte) 86};
                byte[] b2 = new byte[b1.length + chkMssgBytes.length];
                b2[0] = b1[0];
                b2[1] = b1[1];

                for (int i = 0; i < chkMssgBytes.length; i++) {
                    b2[i + 2] = chkMssgBytes[i];
                }

                /*    oStream = response.getOutputStream();
                 for (int i = 0; i < b2.length; i++) {
                 oStream.write(b2[i]);
                 }

                 if (bOutStream != null) {
                 bOutStream.close();
                 }

                 if (oStream != null) {
                 oStream.close();
                 }*/
            } catch (Exception e) {

                System.out.println("[SRVGetDocument]   SRVGetDocument()  , ERROR (chkMssg) ----------- > " + SRVUtil.getExcpStackTrace(e));
                SRVUtil.printOut("[SRVGetDocument]   SRVGetDocument()  , ERROR (chkMssg) ----------- > ");
                SRVUtil.printErr("[SRVGetDocument]   SRVGetDocument()  , ERROR (chkMssg) ----------- > " + SRVUtil.getExcpStackTrace(e));

            }
        } catch (UnsupportedEncodingException usex) {

            SRVUtil.printOut("[SRVGetDocument] \r\n SRVGetDocument UnsupportedEncodingException = " + usex.getMessage());
        } catch (IOException ioEx) {

            SRVUtil.printOut("[SRVGetDocument] \r\n SRVGetDocument IOException = " + ioEx.getMessage());
        } catch (NullPointerException nex) {

            SRVUtil.printOut("[SRVGetDocument] \r\n SRVGetDocument NullPointerException = " + nex.getMessage());
        } catch (NumberFormatException nfex) {

            SRVUtil.printOut("[SRVGetDocument] \r\n NumberFormatException = " + nfex.getMessage());
        } catch (Exception ex) {

            SRVUtil.printOut("[SRVGetDocument] \r\n SRVGetDocument Exception = " + ex.getMessage());
        } finally {
            try {
                if (oStream != null) {
                    oStream.flush();
                    oStream.close();
                }
            } catch (IOException ioex) {
            }
        }
    }
}

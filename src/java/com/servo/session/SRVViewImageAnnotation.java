/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servo.session;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.servo.util.SRVUtil;
import com.servo.generateXML.SRVGenerateXML;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "SRVViewImageAnnotation", urlPatterns = {"/SRVViewImageAnnotation"})
public class SRVViewImageAnnotation extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        System.out.println("INSIDE ---------- SRVViewImageAnnotation ------------");
        SRVUtil.printOut("[ApsOd]INSIDE ---------- SRVViewImageAnnotation ------------");
        response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
        response.setHeader("Pragma", "no-cache"); //HTTP 1.0
        response.setDateHeader("Expires", -1); //prevents caching at the proxy server
        //PrintWriter out = response.getWriter();

        PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this, request, response, "/errorpage.jsp", true, 8192, true);
        try {
            //viewAnnotation(pageContext,request,response);

            System.out.println("ViewAnnotation() ---------->");
            SRVUtil.printOut("ViewAnnotation() ---------->");

            HttpSession session = request.getSession(false);
            ResourceBundle genRSB = ResourceBundle.getBundle("SRVApsOd_en_IN");
            System.out.println("[SRVViewImageAnnotation]    service() ,  GOT Resource Bundle");
            SRVUtil.printOut("[SRVViewImageAnnotation]    service() ,  GOT Resource Bundle");

            String strEncoding = "UTF-8";
            //strEncoding = genRSB.getString("Encoding");
            System.out.println("[SRVViewImageAnnotation]    service()  , strEncoding = " + strEncoding);
            SRVUtil.printOut("[SRVViewImageAnnotation]    service()  , strEncoding = " + strEncoding);

            request.setCharacterEncoding(strEncoding);
            System.out.println("[SRVViewImageAnnotation]    service()  , SET character encoding");
            SRVUtil.printOut("[SRVViewImageAnnotation]    service()  , SET character encoding");
            response.setContentType("text/html;charset=" + strEncoding);

            String docId = request.getParameter("DocId");
            String pageNo = request.getParameter("PageNo");
            String userDBId = request.getParameter("UserDBId");

            String versionNo = request.getParameter("Version");
            System.out.println("[SRVViewImageAnnotation]    service()  ,    DocId = " + docId + " \n PageNo = " + pageNo + " \n UserDBId = " + userDBId + " \n VersionNo = " + versionNo);

            StringBuffer retXml = SRVGenerateXML.getAnnotations(
                    SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(),
                    userDBId,
                    docId,
                    pageNo,
                    "0",
                    versionNo,
                    "255",
                    "A",
                    "A");
            System.out.println("[SRVViewImageAnnotation]    service()  ,  getAnnotations() Retrieved XML is this --------- > " + retXml);
            SRVUtil.printOut("[SRVViewImageAnnotation]    service()  ,   getAnnotations() Retrieved XML (STRING) --------- > " + retXml.toString());
            String outputXML = SRVCallBroker.makeCall("NGOGetAnnotationGroupList", retXml.toString());
            System.out.println("[SRVViewImageAnnotation]    service()  , getAnnotations OUTPUT XML ------------ > " + outputXML);
            SRVUtil.printOut("[SRVViewImageAnnotation]    service()  , getAnnotations OUTPUT XML ------------ > " + outputXML);

//            outputXML = "<?xml version=\"1.0\"?>\r\n"
//                    + "<NGOGetAnnotationGroupList_Output>\r\n"
//                    + "<Option>NGOGetAnnotationGroupList</Option>\r\n"
//                    + "<Status>0</Status><AnnotationGroups><AnnotationGroup>"
//                    + "<AnnotationBuffer>TotalAnnotations=2\r\n"
//                    + "NoOfStamp=2\r\n"
//                    + "[SupervisorStamp1]\r\n"
//                    + "X1=0\r\n"
//                    + "Y1=0\r\n"
//                    + "X2=384\r\n"
//                    + "Y2=370\r\n"
//                    + "Color=0\r\n"
//                    + "TimeOrder=2014,11,03,15,46,21,607\r\n"
//                    + "MouseSensitivity=1\r\n"
//                    + "AnnotationGroupID=Supervisor\r\n"
//                    + "UserID=Supervisor\r\n"
//                    + "Rights=VM\r\n"
//                    + "Type=0\r\n"
//                    + "FileName=1\r\n"
//                    + "StampFileSource=STREAM\r\n"
//                    + "\r\n"
//                    + "[SupervisorStamp2]\r\n"
//                    + "X1=986\r\n"
//                    + "Y1=0\r\n"
//                    + "X2=1406\r\n"
//                    + "Y2=189\r\n"
//                    + "Color=0\r\n"
//                    + "TimeOrder=2014,11,03,15,46,28,174\r\n"
//                    + "MouseSensitivity=1\r\n"
//                    + "AnnotationGroupID=Supervisor\r\n"
//                    + "UserID=Supervisor\r\n"
//                    + "Rights=VM\r\n"
//                    + "Type=0\r\n"
//                    + "FileName=1\r\n"
//                    + "StampFileSource=STREAM</AnnotationBuffer><AnnotGroupName>Supervisor</AnnotGroupName><PageNo>1</PageNo><AnnotGroupIndex>1047</AnnotGroupIndex><AccessType>I</AccessType><AnnotationType>A</AnnotationType><CreationDateTime>2014-11-03 15:46:32</CreationDateTime><LoginUserRights>011111011</LoginUserRights><Owner>Supervisor</Owner><RevisedDateTime>2014-11-03 15:46:32</RevisedDateTime><FinalizedFlag>N</FinalizedFlag><FinalizedDateTime>2014-11-03 15:46:32</FinalizedDateTime><FinalizedBy>0</FinalizedBy></AnnotationGroup></AnnotationGroups><NoOfRecordsFetched>1</NoOfRecordsFetched><TotalNoOfRecords>1</TotalNoOfRecords></NGOGetAnnotationGroupList_Output>";
//            outputXML = "<NGOGetAnnotationGroupList_Output>\n"
//                    + "<Option>NGOGetAnnotationGroupList</Option>\n"
//                    + "<Status>0</Status><AnnotationGroups><AnnotationGroup><AnnotationBuffer>TotalAnnotations=2\n"
//                    + "NoOfStamp=2\n"
//                    + "[istreams_ODStamp1]\n"
//                    + "X1=0\r\n"
//                    + "Y1=0\n"
//                    + "X2=38\n"
//                    + "Y2=37\n"
//                    + "Color=0\n"
//                    + "TimeOrder=2015,01,21,18,09,23,616\n"
//                    + "MouseSensitivity=1\n"
//                    + "AnnotationGroupID=istreams\n"
//                    + "UserID=istreams_OD\n"
//                    + "Rights=VM\n"
//                    + "Type=0\n"
//                    + "FileName=1\n"
//                    + "StampFileSource=STREAM\n"
//                    + "\n"
//                    + "[istreams_ODStamp2]\n"
//                    + "X1=40\n"
//                    + "Y1=40\n"
//                    + "X2=148\n"
//                    + "Y2=147\n"
//                    + "Color=0\n"
//                    + "TimeOrder=2015,01,21,18,09,28,311\n"
//                    + "MouseSensitivity=1\n"
//                    + "AnnotationGroupID=istreams_OD\n"
//                    + "UserID=istreams_OD\n"
//                    + "Rights=VM\n"
//                    + "Type=0\n"
//                    + "FileName=2\n"
//                    + "StampFileSource=STREAM</AnnotationBuffer><AnnotGroupName>istreams_OD</AnnotGroupName><PageNo>1</PageNo><AnnotGroupIndex>1126</AnnotGroupIndex><AccessType>I</AccessType><AnnotationType>A</AnnotationType><CreationDateTime>2015-01-21 16:09:52</CreationDateTime><LoginUserRights>1111111111</LoginUserRights><Owner>istreams_OD</Owner><RevisedDateTime>2015-01-21 18:09:54</RevisedDateTime><FinalizedFlag>N</FinalizedFlag><FinalizedDateTime>2015-01-21 18:09:54</FinalizedDateTime><FinalizedBy>0</FinalizedBy></AnnotationGroup></AnnotationGroups><NoOfRecordsFetched>1</NoOfRecordsFetched><TotalNoOfRecords>1</TotalNoOfRecords></NGOGetAnnotationGroupList_Output>";

            String strFinal = SRVGenerateXML.getAnnotationData(outputXML);
//              String strFinal = outputXML;
            System.out.println("[SRVViewImageAnnotation]    service()  , getAnnotations OUTPUT strFinal ------------ > " + strFinal);
            SRVUtil.printOut("[SRVViewImageAnnotation]    service()  , getAnnotations OUTPUT strFinal ------------ > " + strFinal);

            BufferedWriter oStream = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), strEncoding));
            oStream.write(strFinal, 0, strFinal.length());
            oStream.close();
            oStream = null;
//            strAnnotBuffer = null;
            strFinal = null;

        } catch (Exception e) {
            System.out.println("[SRVViewImageAnnotation]   service() , ERROR ---------- > " + SRVUtil.getExcpStackTrace(e));
            SRVUtil.printOut("[SRVViewImageAnnotation]   service() , ERROR  ---------- > ");
            SRVUtil.printErr("[SRVViewImageAnnotation]   service() , ERROR  ---------- > " + SRVUtil.getExcpStackTrace(e));
            SRVUtil.printErr(e.getMessage());
        }

    }

}

<%-- 
    Document   : displayDocument
    Created on : 17 Oct, 2013, 2:34:33 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>



<%--<%@taglib uri="org.apache.taglibs.standard"  %>--%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <link rel="stylesheet" type="text/css" href="resource/jquery_min/jquery-ui.css" />
        <script src="resource/jquery_min/jquery.min.js"></script>
        <script src="resource/jquery_min/jquery-ui.min.js"></script>

        <link rel="stylesheet" type="text/css" href="resource/jquery_ui/jquery-ui.css" />
        <script src="resource/jquery_ui/jquery-1.9.1.js"></script>
        <script src="resource/jquery_ui/jquery-ui.js"></script>

        <!--        <link rel="stylesheet" href="/resources/demos/style.css" />-->

        <!--bootstrap-->
        <link href="resource/bootstrap/css/bootstrap.css" rel="stylesheet">
        <!--        <link href="resource/bootstrap/bootstrap-responsive.css" rel="stylesheet">-->
        <script src="resource/bootstrap/js/bootstrap.js"></script>

        <script src="resource/json2.js"></script>


        <style>
            *{
                font: 12px;
            }
        </style>
    </head>
    <body>
        <div style=" background-color: rgb(231,120,23); width: 100%;  padding-top: 5px; padding-bottom: 5px; height:20px ">
            <div style="margin-left: 10px">
                <font style=" font-size: 18px; position:absolute; color: #FFFFFF; margin-left: 0px; ">I-Decisions</font>
            </div>
            <div id="otherButtons" style="position: relative; display: none; width: 20%; float: right">
                <button  id="dataClass" class="btn btn-inverse btn-small" style="float: right" >Data Class</button>
                <button  id="showApplet" class="btn btn-inverse btn-small" style="display: none; float: right">Image</button>
            </div>
        </div>
        <div id="divdisplayimage">
            <%--    APPLET'S Div    --%>                            
            <div id="wdesk:ivapp" style="display: inline; overflow: auto; height: 100%"></div>
            <div id="dataClassDIV" style="display: none;">
                <table id="dataClassDetails" cellpadding="5" style="text-align: left">
                    <tr>
                        <!--                                <th class="dataClassName"></th>-->
                        <th class="documentName_DataClass" colspan="100"></th>
                    </tr>
                    <tr>
                        <th colspan="100" style="text-align: left">
                            <strong id="dataClass_DataClass"></strong>
                        </th>
                    </tr>
                    <tbody id="dataClassProperty">

                    </tbody>
                </table>
            </div>
        </div>
        <div style="display:none" id="userName"><c:out   value="${param.userName}" /></div>
        <div style="display:none" id="userDBId"><c:out   value="${param.userDBId}" /></div>
        <div style="display:none" id="documentExt"><c:out   value="${param.documentExt}" /></div>
        <div style="display:none" id="docIndex"><c:out   value="${param.docIndex}" /></div>
        <div style="display:none" id="documentName"><c:out   value="${param.documentName}" /></div>
        <div style="display:none" id="noOfPages"><c:out   value="${param.noOfPages}" /></div>
        <div style="display:none" id="ISIndex"><c:out   value="${param.ISIndex}" /></div>
        <div style="display:none" id="parentFolderIndex"><c:out   value="${param.parentFolderIndex}" /></div>


    </body>
    <script type="text/javascript">
        $("#divdisplayimage").height($(window).height()-70);
    </script>
    <script>
        $(document).ready(function(){
            var userName = $("#userName").html(); 
            var msg = "userName = "+userName;
            var userDBId =$("#userDBId").html();
            msg = msg + "userDBId = "+userDBId;
            var documentExt = $("#documentExt").html(); 
            msg = msg + "documentExt = "+documentExt;
            var docIndex =$("#docIndex").html();  
            msg = msg + "docIndex = "+docIndex;
            var documentName =  $("#documentName").html();
            msg = msg + "documentName = "+documentName;
            var noOfPages =  $("#noOfPages").html();
            msg = msg + "noOfPages = "+noOfPages;
            var IsIndex = $("#ISIndex").html();
            msg = msg + "IsIndex = "+IsIndex;
            var parentFolderIndex =$("#parentFolderIndex").html();
            msg = msg + "parentFolderIndex = "+parentFolderIndex;
            //            alert(msg)
            $(document).attr("title",documentName+"."+documentExt)
            var servletPath= "${initParam["SERVLETURL"]}"+"/SRVGetDocument";
            var url = servletPath +'?ISIndex='+IsIndex+'&DocExt='+documentExt+'&DocIndex='+docIndex+'&PageNo=1&DocumentName='+encodeURIComponent(documentName);
            //            var imageAnnotURL = "$//{initParam["SERVLETURL"]}"+"/SRVImageAnnotation?DocId="+docIndex+"&PageNo=1&UserDBId="+localStorage.servoapsOd_userDBId+"&UserName="+localStorage.servoapsOd_loggedInUser;
            //            var viewImageStampURL = "$//{initParam["SERVLETURL"]}"+"/SRVViewImageStamp?DocId="+docIndex+"&PageNo=1&UserDBId="+localStorage.servoapsOd_userDBId;
            var imageAnnotURL = "${initParam["SERVLETURL"]}"+"/SRVImageAnnotation?DocId="+docIndex+"&PageNo=1&UserDBId="+userDBId+"&UserName="+userName;
            var viewImageStampURL = "${initParam["SERVLETURL"]}"+"/SRVViewImageStamp?DocId="+docIndex+"&PageNo=1&UserDBId="+userDBId;
            //var url = servletPath +'?ISIndex='+IsIndex+'&DocExt='+documentExt+'&DocIndex='+docIndex+'&DocumentName='+encodeURIComponent(documentNameForDataClass)+'&PageNo=2';
            //alert(url)
            //@ToDo Page No and Version    
            //var annotUrl = "$//{initParam["SERVLETURL"]}"+"/SRVViewImageAnnotation?"+encodeURIComponent("DocId="+docIndex+"&PageNo=2&Version=1.0");
            var annotUrl = "${initParam["SERVLETURL"]}"+"/SRVViewImageAnnotation?"+"DocId="+encodeURIComponent(docIndex)+"&PageNo=1&Version=1.0&UserDBId="+userDBId;

            
            var appletString = //'<form name="textForm">'

            //+'<applet mayscript="" code="com.newgen.applet.AnnotationBase.EditableAnnotationApplet.class" archive="$//{initParam["APPLETLIBRARY"]}/editableannotationapplet.jar" name="IVApplet" width="100%" height="670px" codebase="$//{initParam["APPLETLIBRARY"]}/" cabbase="$//{initParam["APPLETLIBRARY"]}/editableannotationapplet.cab">'
            '<applet mayscript="" code="com.newgen.applet.AnnotationBase.EditableAnnotationApplet.class" archive="${initParam["ODLIBRARY"]}/doccab/editableannotationapplet.jar" name="IVApplet" width="100%" height="100%" codebase="${initParam["ODLIBRARY"]}/doccab/" cabbase="${initParam["ODLIBRARY"]}/doccab/editableannotationapplet.cab">'
            //+'<applet mayscript="" code="com.newgen.applet.AnnotationBase.EditableAnnotationApplet.class" archive="IVApplet.jar" name="IVApplet" width="100%" height="670px">'
                +'<param name="cache_archive" value="editableannotationapplet.jar">'
            //+'<param name="URL_Image" value="'+url+'">'
                +'<param name="URL_Image" value="'+url+'">'
                +'<param name="URL_Annotation" value="'+annotUrl+'">'
            //+'<param name="URL_AnnotCGI" value="http://192.168.1.10:8080/webdesktop/servlet/viewimageannotation?DocId=3602&PageNo=1&WD_UID=9070842216447148337">'
                +'<param name="URL_AnnotCGI" value="'+imageAnnotURL+'">'
                +'<param name="flag_Multipage" value="true">'
                +'<param name="NumberOfPages" value="'+noOfPages+'">'
                +'<param name="num_VisiblePage" value="1">'
                +'<param name="ResizePercentage" value="256">'
                +'<param name="ToolBarFlag" value="true">'
                +'<param name="AnnotationDisplay" value="true">'
                
                +'<param name="MenuBar" value="false">'
                +'<param name="codebase_lookup" value="false">'
                +'<param name="UTF8Encoding" value="true">'
                +'<param name="DynamicHideNShowToolBar" value="2">'
                +'<param name="InitialZoomLensStatus" value="false">'
                +'<param name="PrintOption" value="true">'
                +'<param name="PrintParameter" value="1">'
            //+'<param name="URL_StampINIPath" value="$//{initParam["ODLIBRARY"]}/admin/stamps/conf/stamps7905227433966239866.ini">'
                +'<param name="URL_StampINIPath" value="${initParam["STAMPINIPATH"]}">'
            //+'<param name="URL_ImageStampFile" value="http://192.168.1.10:8080/omnidocs/servlet/viewimagestamp?DocId=3464&Version=0&PageNo=1&UserDbId=750709221&CabinetName=icici&JtsIp=127.0.0.1&JtsPort=1099">'
                +'<param name="URL_ImageStampFile" value="'+viewImageStampURL+'">'
            //+'<param name="URL_toolbarPropertiesFile" value="">'
                +'<param name="URL_toolbarPropertiesFile" value="${initParam["ODLIBRARY"]}/ToolBarBean.properties">'
                +'<param name="URL_annotToolbarPropertiesFile" value="${initParam["ODLIBRARY"]}/AnnotationToolbar.properties">'
                +'<param name="CurrentUserName" value="'+userName+'">'
                +'<param name="BrightnessOption" value="true">'
            //+'<param name="DynamicText" value="servosys">'
                +'<param name="DynamicText" value="">'  //  blank
                +'<param name="TransformOption" value="true">'  
                +'<param name="ServerSupportMultiPage" value="true">'
                +'<param name="annotToolbar_Alignment" value="west">'
                +'<param name="EditAnnotationOption" value="true">'
                
                +'<param name="CroppedImageSize" value="32">'
                +'<param name="PenThickness" value="3">'
                +'</applet>'

            document.getElementById("wdesk:ivapp").innerHTML = appletString;

            //  Load applet toolbars (configurable)
            if("${initParam["APPLET_TOOLBAR"]}" == "Y")
            {
                //document.IVApplet.showNHideToolBars(true,true);
                document.IVApplet.showNHideToolBars(true,false);
            } 
            $("#otherButtons").show();
            $("#dataClass").show();
        
            $("#dataClass").click(function(){
        
                document.getElementById("wdesk:ivapp").style.display = "none";
                $("#dataClass").hide();
                $("#showApplet").show();
                $("#dataClassDIV").show();
            });
    
            $("#showApplet").click(function(){
        
                document.getElementById("wdesk:ivapp").style.display = "block";
                $("#dataClass").show();
                $("#showApplet").hide();
                $("#dataClassDIV").hide();
            });
        
            //ajax call to get document details
            $.ajax({
                type:"POST",
                //dataType :"xml",
                data: //"UserDBId="+$//{param.userDBId}
                //"UserDBId="+localStorage.servoapsOd_userDBId
                "UserDBId="+userDBId
                    +"&documentIndex="+docIndex 
                    +"&parentFolderIndex="+parentFolderIndex 
                    +"&dataAlsoFlag=Y" 
                    +"&thumbNailsAlsoFlag=N"
                    +"&versionNo=1.0",

                url :"SRVGetDocumentProperty",
                success:function(data){
                    //alert("Doc Details --------- > \n"+data)
                
                    //  empty previous document information                
                    $("#dataClassProperty").html("");
                    $(".documentName_DataClass").html("");
                    $("#dataClass_DataClass").html("");
                
                    var documentProperties = JSON.parse(data);
                    for(i in documentProperties)
                    {
                        var documentProperty = documentProperties[i];
                        //i is indexName
                        var docProp = documentProperty.split("$");
                        dataDefName = docProp[1];
                        $(".documentName_DataClass").html("Document Name : "+documentName);
                        $("#dataClass_DataClass").html("Data Class : "+dataDefName);
                        //alert(dataDefName)
                        var indexValue = docProp[0];
                        //alert(indexValue)
                    
                    
                        $("#dataClassProperty").append("<tr>"
                            +"<td>"+i+"</td>"
                            +"<td>"+indexValue+"</td>"
                            +"</tr>"
                    );
                    
                    }
                },
                error: function(jqXHR,error, errorThrown) { 
                    alert("SRVGetDocumentProperty : "+jqXHR.status+" , MSG : "+jqXHR.statusText)
                }
            });
        })
    </script>

</html>

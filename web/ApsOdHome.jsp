<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=Edge,IE=8,chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>I-Decision</title>

        <link rel="stylesheet" type="text/css" href="resource/jquery_min/jquery-ui.css" />
        <script src="resource/jquery_min/jquery.min.js"></script>
        <script src="resource/jquery_min/jquery-ui.min.js"></script>

        <link rel="stylesheet" type="text/css" href="resource/jquery_ui/jquery-ui.css" />
        <script src="resource/jquery_ui/jquery-1.9.1.js"></script>
        <script src="resource/jquery_ui/jquery-ui.js"></script>

        <!--        <link rel="stylesheet" href="/resources/demos/style.css" />-->

        <!--bootstrap-->
        <link href="resource/bootstrap/css/bootstrap.css" rel="stylesheet">
        <!--     <link href="resource/bootstrap/bootstrap-responsive.css" rel="stylesheet"> comm. due to broken link -->

        <script src="resource/bootstrap/js/bootstrap.js"></script>

        <script src="resource/json2.js"></script>
        <!-- Splitter library files -->
        <link href="resource/splitter/jqx.base.css" rel="stylesheet">
        <link href="resource/splitter/jqx.summer.css" rel="stylesheet">

        <!--        <script src="resource/splitter/jqxbuttons.js"></script>-->
        <script src="resource/splitter/jqxcore.js"></script>
        <script src="resource/splitter/jqxsplitter.js"></script>


        <style>
            *{
                font: 12px;
            }
            #bar{
                background: -webkit-linear-gradient(right,rgb(134, 68, 16),rgb(227,116,27)); /*Safari 5.1-6*/
                background: -o-linear-gradient(right,rgb(134, 68, 16),rgb(227,116,27)); /*Opera 11.1-12*/
                background: -moz-linear-gradient(right,rgb(134, 68, 16),rgb(227,116,27)); /*Fx 3.6-15*/
                background: linear-gradient(to right,rgb(134, 68, 16),rgb(227,116,27));/*Standard*/ 
                background-color: rgb(231,120,23);
                width: 100%;
                position: absolute;
                padding-top: 5px;
                padding-bottom: 5px; 
            }
        </style>


    </head>
    <body>
        <div>

            <div id="bar" style="">
                <div style="margin-left: 10px;">
                    <font style=" font-size: 18px; position:absolute; color: #FFFFFF; margin-left: 0px; ">I-Decisions</font>
                </div>
                <!--                <div id="backButton" style="position: relative; display: none; width: 20%; float: left; margin-left: 5px">-->
                <div id="backButton" style="position: relative; display: none; width: 20%; float: left; padding-right: 3px">
                    <button  id="back" class="btn btn-inverse btn-small" style="float: right" >Back</button>
                </div>

                <div id="dmsButton" style="position: relative; display: none; float: left; padding-right: 3px">
                    <button  id="dms" class="btn btn-inverse btn-small" style="" >DMS</button>
                </div>

                <div id="otherButtons" style="position: relative; display: none; width: 20%; float: left">
                    <button  id="dataClass" class="btn btn-inverse btn-small" >Data Class</button>
                    <button  id="showApplet" class="btn btn-inverse btn-small" style="display: none">Image</button>
                </div>

                <div style=" position: relative; float:right;padding-right: 5px;">
                    <button  id="embed" class="btn btn-inverse btn-small" onclick="embedWindow()" >Embed</button>
                    <button  id="logout" onclick="logout();" class="btn btn-inverse btn-small">Logout</button>
                </div>
            </div>
            <div style="width: 100%; height: 4%; background-color: rgb(209,207,187); position: absolute; margin-top: 2.25%;">
                <!--<font id="font" style="word-wrap:break-word;font-size: 12px;color: rgb(105, 105, 105);font-weight: 700;"> &nbsp;&nbsp; $//{param.user} [Last Login Time:-$//{param.loginTime}]</font>-->
                <font id="font" style="margin-left: 10px; word-wrap:break-word;font-size: 12px;color: rgb(105, 105, 105);font-weight: 700;"></font>
            </div><br>

            <div id='jqxSplitter' style="margin-top: 4%; background-color: #EEE;">
                <!--                <div id="left" style="width: $ {initParam["OD-WIDTH"]}; height: 100%; border: 1px solid; border-right: 1px solid;">-->
                <div id="left" style="border: 1px solid; border-right: 1px solid;">
                    <div style=" height: 100%; width: 100%;" id="framediv">
                        <!--                        <iframe src="OD.jsp" id="OD" style=" height: 100%; width: 100%;"></iframe>-->
                        <iframe src="" id="OD" style=" height: 100%; width: 100%;"></iframe>
                        <!--<iframe src="" id="OD1" style=" height: 100%; width: 100%; display: none;"></iframe>-->
                    </div>

                </div>
                <!--                <div id="right" style=" width: $ {initParam["APS-WIDTH"]}; height: 100%; border: 1px solid; border-left: 4px solid; float: right;">-->
                <div id="right" style="border: 1px solid; border-left: 4px solid; float: right;">
                    <div style=" height: 100%; width: 100%;">
                        <!--                        <iframe src="$//{initParam["APS-URL"]}" id="APS" style=" height: 100%; width: 100%;"></iframe>-->
                        <iframe src="" id="APS" style=" height: 100%; width: 100%;"></iframe>
                        <!--                        <iframe src="CustomLogin.jsp" id="APS" style=" height: 100%; width: 100%;"></iframe>-->
                    </div>
                </div>

                <!--<div id="left" style="overflow :scroll; width: $//{initParam["OD-WIDTH"]}; height: 100%; border: 1px solid; border-right: 1px solid;">-->

            </div>
        </div>



        <input type="hidden" id="userDBId" value="" />
        <input type="hidden" id="user" value="" />
        <input type="hidden" id="lastlogintime" value="" />
        <input type="hidden" id="dbName" value="" />
    <!--    <input type="hidden" id="userDBId" value="<%//=userDBId%>" />
        <input type="hidden" id="user" value="<%//=userName%>" />-->
        <!-- JSP ENDS here -->                    

    </body>
</html>

<script>
    //    $(document).ready(function(){
    //        if('$//{APSMANUALLOGIN}'.toUpperCase() == "Y")
    //        {
    //            $("#APS").attr("src",'$//{initParam["APS-URL"]}');
    //        }
    //        else
    //        {
    //            $("#APS").attr("src","CustomLogin.jsp");
    //        
    //        }
    //    })
</script>

<script defer>
    $(document).ready(function() {

        //  If call is from i-Streams
        if ((localStorage.servoapsOd_userDBId == null || localStorage.servoapsOd_userDBId == undefined) &&
            (localStorage.servoapsOd_loggedInUser == null || localStorage.servoapsOd_loggedInUser == undefined))
        {
            //            alert("Session ID from opener (localStorage.servobpm_dmsSessionId) = "+localStorage.servobpm_dmsSessionId)            
            //            alert("User Name from opener = "+window.opener.$.getNTUserName())            
            //alert("In APS OD")
            window.opener.$.validateDMSSession(true, function(data) {
                //alert(data + " Hello")

                //$("#userDBId").val(window.opener.$.getDMSSession())

                $("#userDBId").val(data)
                $("#user").val(window.opener.$.getNTUserName())
                $("#lastlogintime").val(window.opener.$.getLastLoginTime())

                $("#logout").hide();


                if ('${initParam["APSMANUALLOGIN"]}'.toUpperCase() == "Y")
                {
                    $("#APS").attr("src", '${initParam["APS-URL"]}');
                }
                else
                {

                    $("#APS").attr("src", "CustomLogin.jsp");
                }

    <%
        String diff = request.getParameter("differentiator");
    %>

                    $("#font").html("Welcome " + $("#user").val() + " [ Last Login Time: " + $("#lastlogintime").val() + " ]");

                    var diff = '<%=diff%>';

                    if (diff.toUpperCase() == 'H')
                    {
                        $("#OD").attr("src", "OD.jsp");
                    }
                    else if (diff.toUpperCase() == 'I')
                    {
                        $("#OD").attr("src", window.opener.dms);//    For web API
                    }
                }
                , function(jqXHR, error, errorThrown)
                {
                    alert("Error = " + jqXHR.status + " , MEssage = " + jqXHR.stautsText)
                    alert("Invalid DMS Session")
                    //window.close();
                }
            );

                //            //$("#userDBId").val(window.opener.$.getDMSSession())
                //            
                //            $("#userDBId").val(localStorage.servobpm_dmsSessionId)
                //            $("#user").val(window.opener.$.getNTUserName())
                //            $("#lastlogintime").val(window.opener.$.getLastLoginTime())
                //            //$("#dbName").val(window.opener.$.getDB())
                //            $("#logout").hide();
                //            
                //            //alert('$//{initParam["APSMANUALLOGIN"]}')
                //            if('$//{initParam["APSMANUALLOGIN"]}'.toUpperCase() == "Y")
                //            {
                //                $("#APS").attr("src",'$//{initParam["APS-URL"]}');
                //            }
                //            else
                //            {
                //                //alert('$//{initParam["EXTERNALCREDENTIALSERVLET"]}')
                //                $("#APS").attr("src","CustomLogin.jsp");
                //            }
                //            
                //            <%
    //String diff = request.getParameter("differentiator");
    %>//
            //            
            //            $("#font").html("Welcome "+ $("#user").val() +" [ Last Login Time: "+$("#lastlogintime").val()+" ]");
            //            
            //            var diff = '<%=diff%>';
            //            
            //            if(diff.toUpperCase() == 'H')
            //            {
            //                $("#OD").attr("src","OD.jsp");
            //            }
            //            else if(diff.toUpperCase() == 'I')
            //            {
            //                //alert("window.opener.dms = "+window.opener.dms)
            //                $("#OD").attr("src",window.opener.dms);//    For web API
            //            }

        }
        //  If call is from native application
        else
        {
            //            alert("Session ID (NATIVE APP) ------- > "+localStorage.servoapsOd_userDBId)
            //            alert("Logged In User (NATIVE APP) ------- > "+localStorage.servoapsOd_loggedInUser)
            $("#userDBId").val(localStorage.servoapsOd_userDBId);
            $("#user").val(localStorage.servoapsOd_loggedInUser);
            $("#lastlogintime").val(localStorage.servoapsOd_lastLoginTime);

            $("#APS").attr("src", '${initParam["APS-URL"]}');
            $("#OD").attr("src", "OD.jsp");

        }


    });
</script>

<script>
    function embedWindow()
    {
        try
        {
            var myAx = new ActiveXObject("SRVSan.clsSRVSan");
            myAx.embedWindow();
        }
        catch (err) {
        }
    }
</script>


<script type="text/javascript">
    $(document).ready(function() {

        $("#jqxSplitter").jqxSplitter({width: '100%', height: 820, panels: [{size: '${initParam["OD-WIDTH"]}'}, {size: '${initParam["APS-WIDTH"]}'}]});
        $("#jqxSplitter").css("height", "820");
        $("#jqxSplitter").css("width", "");
        //$("#left").css("width","100%");
        //$("#right").css("width","100%");


        //        $( "#left" ).resizable({handles:"e"      
        //        });
        //        //alert($(window).width())
        //        $("#left").bind("resize", function (event, ui) {
        //            var setWidth = $("#left").width();
        //            //$('#right').width(1568-setWidth);
        //            //$('#right').width(1572-setWidth);
        //            $('#right').width($(window).width()-setWidth-10);
        //            //$('.menu').width(setWidth-6);
        //        });

        //$(".ui-resizable-e").css("position","absolute");
    });
</script>
<script>
    function logout() {
        $("#OD").remove();
        $("#OD1").remove();
        //var param ='InputXml=' + encodeURIComponent('<?xml version="1.0" encoding="UTF-8"?><NGODisconnectCabinet_Input><Option>NGODisconnectCabinet</Option><CabinetName>${initParam["CABINETNAME"]}</CabinetName><UserDBId>'+ localStorage.servoapsOd_userDBId +'</UserDBId></NGODisconnectCabinet_Input>')
        var param = 'InputXml=' + encodeURIComponent('<?xml version="1.0" encoding="UTF-8"?><NGODisconnectCabinet_Input><Option>NGODisconnectCabinet</Option><CabinetName>${initParam["CABINETNAME"]}</CabinetName><UserDBId>' + $("#userDBId").val() + '</UserDBId></NGODisconnectCabinet_Input>') + '&strAPI="NGODisconnectCabinet"'

        $("#embed").hide();
        $("#logout").html("Logging out...");
        $.ajax({
            type: "POST",
            dataType: "xml",
            url: 'SRVCall',
            data: param,
            success: function(result) {
                //alert("In success"+result);
            }, error: function(jqXHR, error, errorThrown) {
                //alert("SRVCall : "+jqXHR.status+" Msg:"+jqXHR.statusText);
                var data = xhr.responseText;
                //alert("dataerr"+data)
            },
            complete: function(xhr, status) {
                var data = xhr.responseText;
                //alert("datagl"+data)

                //  Clear the localStorage
                //                localStorage.clear();

                xmlDoc = $.parseXML(data),
                $xml = $(xmlDoc),
                $title = $xml.find("Status");
                //alert("UserDBId____ = " + $title.text());

                if ($title.text() == 0) {
                    //window.location.href = 'ApsOdLogin.jsp'
                    window.location.href = sessionStorage.loginpage;

                }
                else {
                    //alert("Logout not successful.")
                    //window.location.href = 'ApsOdLogin.jsp'
                    window.location.href = sessionStorage.loginpage;
                }

            }

        });
    }
</script>
<%--

<script>
    $(document).ready(function(){
        //alert('$//{STATUS}')
        
        $("#userDBId").val(localStorage.servoapsOd_userDBId);
        $("#user").val(localStorage.servoapsOd_loggedInUser);
        
        if('${STATUS}'.toUpperCase() == 'SUCCESS')
        {
            alert("Document uploaded successfully!")
        }
        else if('${STATUS}'.toUpperCase() == 'FILETOOLARGE')
        {
            alert("File is too large to upload.")
        }
        else if('${STATUS}'.toUpperCase() == 'ERROR')
        {
            alert("Some error occured at server side.")
        }
        else if('${STATUS}'.toUpperCase() == 'FOLDERNOTFOUND')
        {
            alert("Folder not found.")
        }
        else if('${STATUS}'.toUpperCase() == 'INVALIDFILE')
        {
            alert("Invalid file uploaded.")
        }
        else if('${STATUS}'.toUpperCase() == 'INVALIDFILEFORMAT')
        {
            alert("Invalid file format.")
        }
        else if('${STATUS}'.toUpperCase() == 'INVALIDSESSION')
        {
            alert("User session expired.")
            logout();
            //window.location.href = 'ApsOdLogin.jsp';
        }
    })
</script>

<script type="text/javascript">
    $(document).ready(function() {
        $( "#left" ).resizable({handles:"e"      
        });
        $("#left").bind("resize", function (event, ui) {
            var setWidth = $("#left").width();
            //$('#right').width(1568-setWidth);
            $('#right').width(1572-setWidth);
            $('.menu').width(setWidth-6);
        });
    });
</script>


<style>
    #left{
        max-width: 80%;
        min-width: 28%;

    }

    .ui-accordion-content{
        height: 100%;
    }
</style>
<script>
    $(function() {
        $( "#tabs" ).tabs();
        
        //$("#tabs-1").css("padding","0px");
        
        $(".ui-accordion-content").css("height","100%");
        $("#main_content").css("padding","0px");
        //  main_content border in ajax call 
    });
        
</script>

<script>
    function validateForm()
    {
        
        if($("#application_form_upload").val() == "")
        {
            alert("Please fill Application Form No.")
            return false;
        }
        if($("#applicant_type").val() == "")
        {
            alert("Please select Application Type")
            return false;
        }
        if($("#parent_document_txt").val() == "")
        {
            alert("Please select Parent Document")
            return false;
        }
        if($("#child_document").val() == "")
        {
            alert("Please select Child Document")
            return false;
        }
        if($("#filePath").val() == "")
        {
            alert("Please choose a file to upload")
            return false;
        }
            
        $("#userDBId").val(localStorage.servoapsOd_userDBId);
        $("#user").val(localStorage.servoapsOd_loggedInUser);
            
    }
</script>

<script>
    function logout(){
        //var param ='InputXml=' + encodeURIComponent('<?xml version="1.0" encoding="UTF-8"?><NGODisconnectCabinet_Input><Option>NGODisconnectCabinet</Option><CabinetName>icici</CabinetName><UserDBId>'+ $//{param.userDBId} +'</UserDBId></NGODisconnectCabinet_Input>')
        var param ='InputXml=' + encodeURIComponent('<?xml version="1.0" encoding="UTF-8"?><NGODisconnectCabinet_Input><Option>NGODisconnectCabinet</Option><CabinetName>${initParam["CABINETNAME"]}</CabinetName><UserDBId>'+ localStorage.servoapsOd_userDBId +'</UserDBId></NGODisconnectCabinet_Input>')
            
        $("#embed").hide();
        $("#logout").html("Logging out...");
        $.ajax({
            type:"GET",
            dataType :"xml",
            url: 'SRVCall',
            data: param, 
            success: function(result) {
                //alert("In success"+result);
                
                
            },    error: function(jqXHR, error, errorThrown) { 
                //alert("SRVCall : "+jqXHR.status+" Msg:"+jqXHR.statusText);
                var data = xhr.responseText;
                alert("dataerr"+data)
            } ,
            complete: function (xhr, status) {
                var data = xhr.responseText;
                //alert("datagl"+data)

//  Clear the localStorage
                localStorage.clear();
                
                xmlDoc = $.parseXML( data ),
                $xml = $( xmlDoc ),
                $title = $xml.find( "Status" );
                //alert("UserDBId____ = " + $title.text());
                
                if($title.text() == 0){
                    window.location.href = 'ApsOdLogin.jsp'
                    
                }
                else{
                    //alert("Logout not successful.")
                    window.location.href = 'ApsOdLogin.jsp'
                }
                 
            }
            
        });
    }
</script>
<script>
    //var status = '${initParam["T-WIDTH"]}';
    //alert("w= "+status);
    if(!${initParam["APS_WINDOW_DISPLAY"]}){
        //alert("in if");
        $("#right").hide();
        $("#left").css("max-width", "100%")
        $("#left").css("width", "100%");
        
    }else{
        // alert("in else");
    }
</script>

<script>
    $(document).ready(function() {
         
        var param = 'type=' + "parent";
        $.ajax({            
            url: 'SRVGetDb',
            data: param,
            
            success: function(objData) {
               //alert(objData)
                var parents= JSON.parse(objData);
                
                for(i in parents){
                    var parentDetails =parents[i];
                    var parent = parentDetails.split("#")[0];
                    var snameParent = parentDetails.split("#")[1];
                    //    alert("parent = "+parent +" , snameParent = "+snameParent)
                    $("#parent_document").append("<option value="+i+" shortname='"+snameParent+"'>" +parent+ "</option>");
                }  
              
            },    error: function(jqXHR, error, errorThrown) { 
                //alert("Something went wrong , Please refresh page .["+jqXHR.status+" Msg:"+jqXHR.statusText+"]");
                var data = xhr.responseText;
                alert("SRVGetDb : \n"+data)
            } 
           
        });
        
    });
</script>

<script>
    $(document).ready(function() {
            
        var param = 'type='+ "applicant";
        $.ajax({            
            url: 'SRVGetDb',
            data: param,
            
            success: function(objData) {
               
                var applicantTypes= JSON.parse(objData);
                
                for(i in applicantTypes){
                    var applicantTypeDetails =applicantTypes[i];
                    var applicantType = applicantTypeDetails.split("#")[0];
                    var snameApplicantType = applicantTypeDetails.split("#")[1];
                    
                        
                    $("#applicant_type").append("<option value='"+applicantType+"' sname='"+snameApplicantType+"'>" +applicantType+ "</option>");
                }  
              
            },    error: function(jqXHR, error, errorThrown) { 
                //alert("Something went wrong , Please refresh page .["+jqXHR.status+" Msg:"+jqXHR.statusText+"]");
                var data = xhr.responseText;
                alert("SRVGetDb : \n"+data)
            } 
           
        });
        
    });
</script>

<script>
    $("#parent_document").select().change(function(){
        $("#child_document").empty();
       
        $("#parent_document_txt").val($("#parent_document :selected").text())
       
        var param =  'id=' + $("#parent_document").val() + '&type=' + "child";
       
        $.ajax({            
            url: 'SRVGetDb',
            data: param,
            
            success: function(objData) {
               
                var persons= JSON.parse(objData);
                //alert(objData)
                
                for(i in persons){
                    var person =persons[i];
                    //alert("data"+ person);
                        
                    $("#child_document").append("<option value='"+person+"'>" +person+ "</option>");
                }  
                
              
            },    error: function(jqXHR, error, errorThrown) { 
                //alert("Something went wrong , Please refresh page .["+jqXHR.status+" Msg:"+jqXHR.statusText+"]");
                var data = xhr.responseText;
                alert("SRVGetDb : \n"+data)
            } 
           
        });
       
    });
    
</script>

<%-- IV APPLET --%>
<%--
<script>
    $("#backButton").click(function(){
        $("#tabs").show();
        document.getElementById("wdesk:ivapp").style.display = "none";
        $("#backButton").hide();
        $("#dataClass").hide();
        $("#showApplet").hide();
        $("#dataClassDIV").hide();
    });
    
    $("#dataClass").click(function(){
        $("#tabs").hide();
        document.getElementById("wdesk:ivapp").style.display = "none";
        $("#dataClass").hide();
        $("#showApplet").show();
        $("#dataClassDIV").show();
    });
    
    $("#showApplet").click(function(){
        $("#tabs").hide();
        document.getElementById("wdesk:ivapp").style.display = "block";
        $("#dataClass").show();
        $("#showApplet").hide();
        $("#dataClassDIV").hide();
    });
</script>

<script>
    //var startedFrom = 1, maxHitCount , noOfRecordsFetched;
    
    $("#get").click(function(){
        
        startedFrom = 1, maxHitCount = 0 , noOfRecordsFetched = 0;
        
        $("#documents").html("");
        //alert("get")
        if($("#application_form_search").val() != "" || $("#aps_id_search").val() != "")
        {
        $.ajax({
            type:"POST",
            //dataType :"xml",
            data: //"UserDbId="+$//{param.userDBId}
                "UserDbId="+localStorage.servoapsOd_userDBId
                +"&ApplicationFormNo="+$("#application_form_search").val()
                +"&ApsId="+$("#aps_id_search").val()
                +"&StartedFrom="+startedFrom,
            //+"&sessionId="+ $//{Test2},
            url :"SRVGetUploadedDocument",
            success:function(data){
                
                if($.trim(data) == "INVALIDSESSION")
                {
                    alert("User session expired.")
                    logout();
                    //window.location.href = 'ApsOdLogin.jsp';
                }
                else
                {
                    uploadedDocuments = JSON.parse(data);
                    var documentDetails ;
                    //var /*isTechnical = "",*/ noOfPages, documentSize, modifiedDate, documentType;
                    for(i in uploadedDocuments)
                    {
                        if(i.toUpperCase() == "MAXIMUMHITCOUNT")
                        {
                            maxHitCount = uploadedDocuments[i];
                        }
                        else if(i.toUpperCase() == "NOOFRECORDSFETCHED")
                        {
                            noOfRecordsFetched = uploadedDocuments[i];
                            //alert("noOfrecFet = "+noOfRecordsFetched)
                            startedFrom = parseInt(startedFrom) + parseInt(noOfRecordsFetched);
                            //alert("SF = "+startedFrom)
                        }
                        else
                        {
                            documentDetails = uploadedDocuments[i];

                            documentType = documentDetails.split("$")[3];
                            noOfPages = documentDetails.split("$")[7];
                            documentSize = documentDetails.split("$")[8];
                            modifiedDate = documentDetails.split("$")[9];

                            $("#documents").append("<tr>"
                                +"<td class='documentName'><a class='document' href='#' id='"+i+"' value='"+i+"'>"+i+"</a></td>"
                                +"<td class='documentDetails'>"+documentType+"</td>"
                                +"<td class='documentDetails'>"+documentSize+"</td>"
                                +"<td class='documentDetails noofpage'>"+noOfPages+"</td>"
                                +"<td class='documentDetails'>"+modifiedDate+"</td>"
                                +"</tr>"
                            );
                        }   
                        //                    }
                        //                    else
                        //                    {
                        //                        applicantType = i.split("$")[0];
                        //                        parentDocument = i.split("$")[1];
                        //                        childDocument = i.split("$")[2];
                        //                    }

                        //                    $("#applicant_type_search").html("<option value='"+applicantType+"'>"+applicantType+"</option>");
                        //                    $("#parent_document_search").html("<option value='"+parentDocument+"'>"+parentDocument+"</option>");
                        //                    $("#child_document_search").html("<option value='"+childDocument+"'>"+childDocument+"</option>");
                        //alert("append doc list")
                        //                    $("#document_list_search").append("<option value='"+i+"'>"+i+"</option>");
                        /*$("#documents").append("<tr>"
                            +"<td class='documentName'><a class='document' href='#' id='"+i+"' value='"+i+"'>"+i+"</a></td>"
                            +"<td class='documentDetails'>"+documentType+"</td>"
                            +"<td class='documentDetails'>"+documentSize+"</td>"
                            +"<td class='documentDetails noofpage'>"+noOfPages+"</td>"
                            +"<td class='documentDetails'>"+modifiedDate+"</td>"
                            +"</tr>"
                    );*/
                                            /*$("#parent_document_search").html("<option value='"+parentDocument+"'>"+parentDocument+"</option>");
                                            $("#child_document_search").html("<option value='"+childDocument+"'>"+childDocument+"</option>");

                                            alert(applicantType+" , "+parentDocument+" , "+childDocument)
                                            alert(i+" = "+documentDetails)
                                            */
                    }

    //  Enable-Disable View More Button
                    if(startedFrom >= maxHitCount )
                    {
                        //Disable view-more button
                        $("#viewMore_DocumentList").attr("disabled",true);
                    }
                    else
                    {
                        //Enable view-more button
                        $("#viewMore_DocumentList").removeAttr("disabled");
                    }

                    $("#documents tr:even").addClass("r1");

                    //clickBinder();
                    $(".document").click(showDocument);

                    $("#documentList").show();
                    $("#viewMore").show();
                    //sendInputXML(data);
                    //localStorage.clear();                        //window.location.href = application;
                }
            },
            error: function(jqXHR,error, errorThrown) { 
                //localStorage.clear();
                //window.location.href = application;
                alert("SRVGetUploadedDocument: "+jqXHR.status+" , MSG : "+jqXHR.statusText)
            }
        });
        }
        
        else
        {
            alert("Please fill APS Id or Application Form No for search")
            return false;
        }
//        else if($("#application_form_search").val == "")
//        {
//            alert("Please fill Application ")
//            return false;
//        }
        
    });

//  View More

var obj_view_more = new Object();
var documentBatch = new Array();

$("#viewMore_DocumentList").click(function(){

        $('.document').unbind('click');
    
         $.ajax({
            type:"POST",
            //dataType :"xml",
            data: "UserDbId="+localStorage.servoapsOd_userDBId
                +"&ApplicationFormNo="+$("#application_form_search").val()
                +"&ApsId="+$("#aps_id_search").val()
                +"&StartedFrom="+startedFrom,
            url :"SRVGetUploadedDocument",
            success:function(data){
                //  Concat two objects
                
                var objCurrentBatch = new Object();
                objCurrentBatch.currentBatch = JSON.parse(data);
                documentBatch.push(objCurrentBatch)
                //alert("obj_view_more = "+JSON.stringify(documentBatch))
                /*try
                {
                    alert("varUploadedDocuments_ViewMore (B) = "+varUploadedDocuments_ViewMore)
                }
                catch(e){}
                try
                {
                    
                    varUploadedDocuments_ViewMore = varUploadedDocuments_ViewMore.concat(data);
                    alert("DATA --- TRY > "+varUploadedDocuments_ViewMore)
                }
                catch(e)
                {
                    varUploadedDocuments_ViewMore = data;
                    alert("DATA --- Catch > "+varUploadedDocuments_ViewMore)
                }
                
                alert("varUploadedDocuments_ViewMore (A) = "+varUploadedDocuments_ViewMore)*/
//                try
//                {
                //uploadedDocuments_ViewMore = JSON.parse(varUploadedDocuments_ViewMore); //  Object of concatenated documentList
//                }
//                catch(e)
//                {
//                    uploadedDocuments_ViewMore = JSON.parse(data);
//                }
                
                var documentDetails ;
                
                // (viewMore_DocList) variable to show current batch of document
                var viewMore_DocList = JSON.parse(data);
                for(i in viewMore_DocList)
                {
                    documentDetails = viewMore_DocList[i];
                    if(i.toUpperCase() == "MAXIMUMHITCOUNT")
                    {
                        maxHitCount = viewMore_DocList[i];
                    }
                    else if(i.toUpperCase() == "NOOFRECORDSFETCHED")
                    {
                        noOfRecordsFetched = viewMore_DocList[i];
                        startedFrom = parseInt(startedFrom) + parseInt(noOfRecordsFetched);
                    }
                    else
                    {
                        documentType = documentDetails.split("$")[3];
                        noOfPages = documentDetails.split("$")[7];
                        documentSize = documentDetails.split("$")[8];
                        modifiedDate = documentDetails.split("$")[9];

                        $("#documents").append("<tr>"
                            +"<td class='documentName'><a class='document' href='#' id='"+i+"' value='"+i+"'>"+i+"</a></td>"
                            +"<td class='documentDetails'>"+documentType+"</td>"
                            +"<td class='documentDetails'>"+documentSize+"</td>"
                            +"<td class='documentDetails noofpage'>"+noOfPages+"</td>"
                            +"<td class='documentDetails'>"+modifiedDate+"</td>"
                            +"</tr>"
                        );
                    }   
                }

//  Enable-Disable View More Button
                if(startedFrom > maxHitCount )
                {
                    //Disable view-more button
                    $("#viewMore_DocumentList").attr("disabled",true);
                }
                else
                {
                    //Enable view-more button
                    $("#viewMore_DocumentList").removeAttr("disabled");
                }
               
                $("#documents tr:even").addClass("r1");
               
                //clickBinder();
                $(".document").click(showDocument);
                
                //$("#documentList").show();
            },
            error: function(jqXHR,error, errorThrown) { 

                alert("SRVGetUploadedDocument_ViewMore: "+jqXHR.status+" , MSG : "+jqXHR.statusText)
            }
        });
    });
</script>     


<script>
    //function getDocument()
    //$("#document_list_search").change(function()

    function showDocument(documentNameForDataClass)
    {
        
        //  Show dataClass button
        $("#otherButtons").show();
        $("#dataClass").show();
        
        //var noOfPages = $(this).parent().parent().find(".noofpage").html();
        var noOfPages = $(this).closest("tr").find(".noofpage").html();
        //alert("noOfPages = "+noOfPages)
        
        documentNameForDataClass = $(this).attr("value");
        //alert("documentNameForDataClass = "+documentNameForDataClass);
        var documentDetails , docDetails;
        try
        {
            documentDetails = uploadedDocuments[$(this).attr("value")];
            docDetails = documentDetails.split("$");
        }
        catch(e)
        {
            for(i in documentBatch)
            {
                //alert("i is = "+i)
                var uploadedDocuments_Details = documentBatch[i];
                for(key in uploadedDocuments_Details)
                {
                    var viewMoreDocument = uploadedDocuments_Details[key];
                    for(docName in viewMoreDocument)
                    {
                        if(docName == $(this).attr("value"))
                        {
                            //alert("Doc is = "+docName)
                            documentDetails = viewMoreDocument[docName];
                            docDetails = documentDetails.split("$");
                        }
                    }
                }
            }
            
        }
        //alert(documentDetails)
        
        //alert(docDetails)
        var docIndex = docDetails[0];
        //alert(docIndex)
        var dataClass = docDetails[1];
        //alert(dataClass)
        var IsIndex = encodeURIComponent(docDetails[2]);
        //alert(IsIndex +" = "+docDetails[2])
        var documentType = docDetails[5];
        //alert(documentType)
        var documentExt = docDetails[3];
        //alert(documentExt)
        var comments = docDetails[4];

        var parentFolderIndex = docDetails[6];
        //alert(parentFolderIndex)

        //  AJAX to get Document Property
        $.ajax({
            type:"POST",
            //dataType :"xml",
            data: //"UserDBId="+$//{param.userDBId}
            "UserDBId="+localStorage.servoapsOd_userDBId
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
                    $(".documentName_DataClass").html("Document Name : "+documentNameForDataClass);
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
        
        var servletPath= "${initParam["SERVLETURL"]}"+"/SRVGetDocument";
        if(documentType.toUpperCase() == "I")
        {
            $("#tabs").hide();
            document.getElementById("wdesk:ivapp").style.display = "block";
            $("#backButton").show();

            
            //var url = servletPath +'?ISIndex=726%231&DocExt=tif&DocIndex=1912&PageNo=1';
            //@ToDo Page No and Version    
            //var url = servletPath +'?ISIndex='+IsIndex+'&DocExt='+documentExt+'&DocIndex='+docIndex+'&PageNo=1&DocumentName='+encodeURIComponent(documentNameForDataClass);
            //Working (currentPageNo)
            var url = servletPath +'?ISIndex='+IsIndex+'&DocExt='+documentExt+'&DocIndex='+docIndex+'&PageNo=1&DocumentName='+encodeURIComponent(documentNameForDataClass);
            //var url = servletPath +'?ISIndex='+IsIndex+'&DocExt='+documentExt+'&DocIndex='+docIndex+'&DocumentName='+encodeURIComponent(documentNameForDataClass)+'&PageNo=2';
            //alert(url)
            //@ToDo Page No and Version    
            //var annotUrl = "$//{initParam["SERVLETURL"]}"+"/SRVViewImageAnnotation?"+encodeURIComponent("DocId="+docIndex+"&PageNo=2&Version=1.0");
            var annotUrl = "${initParam["SERVLETURL"]}"+"/SRVViewImageAnnotation?"+"DocId="+encodeURIComponent(docIndex)+"&PageNo=1&Version=1.0";

            //alert(url+"\n"+annotUrl)
            /*******************    ADDING the Applet   ********************/    
            
//            if(typeof window.parent.frames["doctypes_bottom"].document.IVApplet == 'undefined')
//            {
            
            //alert("$//{initParam["ODLIBRARY"]}"+"/editableannotationapplet.jar")
            
            /*var url = servletPath +'/getdocument?ISIndex='+isIndex+'&DocExt='+DocExt+'&DocIndex='+DocIndex +'&PageNo=1&DocumentName='+ encode_utf8(DocType) + '&Comment='+encode_utf8(Comments);

            var writeUrl = servletPath +'/imageannotation/DocId='+DocIndex+'&Option=SaveAnnot&PageNo=1';
            var annotUrl = servletPath +'/viewimageannotation?DocId='+DocIndex+'&PageNo=1';
            var stampPath = servletRef.substring(0, servletRef.indexOf("/webdesktop")) + '/webtop/applet/stamps/' + sStampIniPath;

            var stampUrl = servletPath +'/viewimagestamp?DocId='+DocIndex+'&PageNo=1';
            var toolbarPath = servletRef.substring(0, servletRef.indexOf("/webdesktop")) + '/webtop/applet/AnnotationToolbar.properties';
            var dynamicData = "FamilyName="+userFamilyName + "&DocIndex="+DocIndex+"&DocType="+DocType;
            var propertyPath = servletRef.substring(0, servletRef.indexOf("/webdesktop")) + '/webtop/applet/';
    */
            var appletString = //'<form name="textForm">'

                //+'<applet mayscript="" code="com.newgen.applet.AnnotationBase.EditableAnnotationApplet.class" archive="$//{initParam["APPLETLIBRARY"]}/editableannotationapplet.jar" name="IVApplet" width="100%" height="670px" codebase="$//{initParam["APPLETLIBRARY"]}/" cabbase="$//{initParam["APPLETLIBRARY"]}/editableannotationapplet.cab">'
                '<applet mayscript="" code="com.newgen.applet.AnnotationBase.EditableAnnotationApplet.class" archive="${initParam["ODLIBRARY"]}/IVApplet.jar" name="IVApplet" width="100%" height="670px" codebase="${initParam["ODLIBRARY"]}/" cabbase="${initParam["ODLIBRARY"]}/IVApplet.cab">'
                //+'<applet mayscript="" code="com.newgen.applet.AnnotationBase.EditableAnnotationApplet.class" archive="IVApplet.jar" name="IVApplet" width="100%" height="670px">'
                +'<param name="cache_archive" value="IVApplet.jar">'
            
                
                
            //+'<param name="URL_Image" value="'+url+'">'
                +'<param name="URL_Image" value="'+url+'">'
                +'<param name="URL_Annotation" value="'+annotUrl+'">'
                +'<param name="URL_AnnotCGI" value="http://192.168.1.10:8080/webdesktop/servlet/viewimageannotation?DocId=3602&PageNo=1&WD_UID=9070842216447148337">'
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
                +'<param name="URL_StampINIPath" value="http://192.168.1.10:8080/omnidocs/admin/stamps/conf/stamps7905227433966239866.ini">'
                +'<param name="URL_ImageStampFile" value="http://192.168.1.10:8080/omnidocs/servlet/viewimagestamp?DocId=3464&Version=0&PageNo=1&UserDbId=750709221&CabinetName=icici&JtsIp=127.0.0.1&JtsPort=1099">'
                //+'<param name="URL_toolbarPropertiesFile" value="">'
                +'<param name="URL_toolbarPropertiesFile" value="http://192.168.1.10:8080/omnidocs/ToolBarBean.properties">'
                +'<param name="URL_annotToolbarPropertiesFile" value="http://192.168.1.10:8080/omnidocs/AnnotationToolbar.properties">'
                +'<param name="CurrentUserName" value="'+localStorage.servoapsOd_loggedInUser+'">'
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
                //+'</form>';

                document.getElementById("wdesk:ivapp").innerHTML = appletString;

                ///document.IVApplet.openNewImage(url, annotUrl, noOfPages,1,true);

                //  Load applet toolbars (configurable)
                if("${initParam["APPLET_TOOLBAR"]}" == "Y")
                {
                    //document.IVApplet.showNHideToolBars(true,true);
                    document.IVApplet.showNHideToolBars(true,true);
                }        
//            }
//            
//            else
//            {
//                document.IVApplet.openNewImage(url, annotUrl, noOfPages,1,true);
//
//                //  Load applet toolbars (configurable)
//                if("$//{initParam["APPLET_TOOLBAR"]}" == "Y")
//                {
//                    document.IVApplet.showNHideToolBars(true,true);
//                }        
//            }

            //var url;

            //var servletPath= "/ApsOd/src/java/com/servo/session/SRVGetDocument";
            //alert("in loadDoc method");
            var annotUrl_Annotations = "DocId="+encodeURIComponent(docIndex)+"&PageNo=1&Version=1.0";
            try
            {
                $.ajax({
                    type:"POST",
                    //dataType :"xml",
                    data: annotUrl_Annotations,
                    //+"&sessionId="+ $//{Test2},
                    url :"SRVViewImageAnnotation",
                    success:function(data){
                        //alert(data)
                        sendInputXML(data);
                        //localStorage.clear();                        //window.location.href = application;
                    },
                    error: function(jqXHR,error, errorThrown) { 
                        //localStorage.clear();
                        //window.location.href = application;
                        alert("SRVViewImageAnnotation : "+jqXHR.status+" , MSG : "+jqXHR.statusText)
                    }
                });

            }
            catch(e)
            {
                alert("viewImageAnnotation : "+e)
            }
        }

        else
        {
            //  If documentType is not image  
            //if(documentExt.toUpperCase() != "TXT")
            {
                try
                {
                    $("#dataClassDIV").show();
                    $("#backButton").show();

                    $("#tabs").hide();

                    $("#dataClass").hide();
                    //@ToDo Page No 
                    //var url_NonImage = servletPath +'?ISIndex='+IsIndex+'&DocExt='+documentExt+'&DocIndex='+docIndex+'&PageNo=1&DocumentName='+encodeURIComponent(documentNameForDataClass);
                    var url_NonImage = servletPath +'?ISIndex='+IsIndex+'&DocExt='+documentExt+'&DocIndex='+docIndex+'&PageNo=1&DocumentName='+encodeURIComponent(documentNameForDataClass);
                    window.parent.frames["getDocument_ID"].location = url_NonImage ;

                }
                catch(e)
                {
                    alert("MESSAGE : "+e)

                }
            }
            //  ELSE if documentType is TXT 
//            else
//            {
//                alert("!!Document is text!!")
//            }
        }
        //   });
    } 
</script>


<script>
    $(document).ready(function(){
        //         alert(localStorage.servoapsOd_loggedInUser)
        //         alert(localStorage.servoapsOd_lastLoginTime)
        $("#font").html("Welcome "+ localStorage.servoapsOd_loggedInUser +" [Last Login Time: "+localStorage.servoapsOd_lastLoginTime+ "]");
    })
</script>


<script>
    function sendInputXML(inputXML)
    {
        //alert("inside sendInputXML")
        try
        {
            $.ajax({
                type:"POST",
                dataType :"xml",
                data: "InputXml="+encodeURI(inputXML),

                url :"SRVCall",
                success:function(data){
                    //alert(data)
                    
                    //  main_content border when image loads                    
                    //$("#main_content").css("border","2px solid");
                },
                error: function(jqXHR,error, errorThrown) { 

                    alert("sendInputXML , SRVCall : "+jqXHR.status+" , MSG : "+jqXHR.statusText)
                }
            });
        }
        catch(e)
        {
            alert("sendInputXML() : "+e)
            
        }
    }
</script>

<!--<script>
    $("#doc").click(function(){
        
        //var param = 'ISIndex='+ ("726#1") + '&DocExt='+ ("tif") + '&PageNo='+ ("1") + '&DocIndex='+ ("1912");
        //var param = encodeURI('ISIndex=726#1&DocExt=tif&PageNo=1&DocIndex=1912');
        //alert(param)
        $.ajax({
            url: 'SRVGetDocument',
            data: param, 
            success: function(result) {
                //alert("In success"+result);
                
                //$("#img").append('<img src="data:image/tif;base64," alt="Red dot" />'); 
            },    error: function(jqXHR, error, errorThrown) { 
                alert("SRVGetDocument : "+jqXHR.status+" Msg:"+jqXHR.statusText+"]");
            } 
         
            
        });
    });
</script>-->

<script>
    $(".header").click(function (){
        $(".tableContent").toggle();
    })
</script>

<style>

    #documentList,  .documentName, .documentDetails,  .documentListHeading { border: 1px solid white }
    .documentListHeading { text-align: left }
    *{ font-size: 12px }
    .documentListHeading { background:#c3b091 }
    .r1{background:#f0efe4;}

</style>

<script>
    $.ajaxSetup ({
    // Disable caching of AJAX responses
    cache: false
});
</script>

<script>
    $("#auditTrail").click(function(){
        $("#tabs-3").html("");
        
        $("#tabs-3").load("SRVAuditTrail.jsp?user="+$("#user").val())
    });
</script>

--%>
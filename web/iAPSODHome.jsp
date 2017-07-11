
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>APS-OD</title>

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
        </style>


    </head>
    <body>
        <div>

            <div style=" background-color: rgb(231,120,23); width: 100%; position: absolute; padding-top: 5px; padding-bottom: 5px; ">
                <div style="margin-left: 10px;">
                    <font style=" font-size: 18px; position:absolute; color: #FFFFFF; margin-left: 0px; ">I-Decisions</font>
                </div>
<!--                <div id="backButton" style="position: relative; display: none; width: 20%; float: left; margin-left: 5px">-->
                <div id="backButton" style="position: relative; display: none; width: 20%; float: left; padding-right: 3px">
                    <button  id="back" class="btn btn-inverse btn-small" style="float: right" >Back</button>
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
                    <div style=" height: 100%; width: 100%;">
<!--                        <iframe src="OD.jsp" id="OD" style=" height: 100%; width: 100%;"></iframe>-->
                        <iframe src="" id="OD" style=" height: 100%; width: 100%;"></iframe>
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
    $(document).ready(function(){
     
        //  If call is from i-Streams
        if((localStorage.servoapsOd_userDBId == null || localStorage.servoapsOd_userDBId == undefined) &&
             (localStorage.servoapsOd_loggedInUser == null || localStorage.servoapsOd_loggedInUser == undefined))
        {
//            alert("Session ID from opener (localStorage.servobpm_dmsSessionId) = "+localStorage.servobpm_dmsSessionId)            
//            alert("User Name from opener = "+window.opener.$.getNTUserName())            
            //alert("In APS OD")
            window.opener.$.validateDMSSession(true, function(data){
                //alert(data + " Hello")
                
                //$("#userDBId").val(window.opener.$.getDMSSession())
            
                $("#userDBId").val(data)
                $("#user").val(window.opener.$.getNTUserName())
                $("#lastlogintime").val(window.opener.$.getLastLoginTime())

                $("#logout").hide();


                if('${initParam["APSMANUALLOGIN"]}'.toUpperCase() == "Y")
                {
                    $("#APS").attr("src",'${initParam["APS-URL"]}');
                }
                else
                {

                    $("#APS").attr("src","CustomLogin.jsp");
                }

                <%
                    String diff = request.getParameter("differentiator");
                %>

                $("#font").html("Welcome "+ $("#user").val() +" [ Last Login Time: "+$("#lastlogintime").val()+" ]");

                var diff = '<%=diff%>';

                if(diff.toUpperCase() == 'H')
                {
                    $("#OD").attr("src","iOD.jsp");
                }
                else if(diff.toUpperCase() == 'I')
                {
                    $("#OD").attr("src",window.opener.dms);//    For web API
                }
            }
            ,function(jqXHR,error, errorThrown)
            {
                alert("Error = "+jqXHR.status+" , MEssage = "+jqXHR.stautsText)
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
            
            $("#APS").attr("src",'${initParam["APS-URL"]}');
            $("#OD").attr("src","iOD.jsp");    
            
        }
        
        
    })
</script>

<script>
    function embedWindow()
    {
        try
        {
            var myAx = new ActiveXObject("SRVSan.clsSRVSan");
            myAx.embedWindow();
        }
        catch(err){}
    }
</script>


<script type="text/javascript">
    $(document).ready(function() {
        
        $("#jqxSplitter").jqxSplitter({ width: '100%', height: 820, panels: [{ size: '${initParam["OD-WIDTH"]}' }, { size: '${initParam["APS-WIDTH"]}'}] });
        $("#jqxSplitter").css("height","820");
        $("#jqxSplitter").css("width","");
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
    function logout(){
        
        //var param ='InputXml=' + encodeURIComponent('<?xml version="1.0" encoding="UTF-8"?><NGODisconnectCabinet_Input><Option>NGODisconnectCabinet</Option><CabinetName>${initParam["CABINETNAME"]}</CabinetName><UserDBId>'+ localStorage.servoapsOd_userDBId +'</UserDBId></NGODisconnectCabinet_Input>')
        var param ='InputXml=' + encodeURIComponent('<?xml version="1.0" encoding="UTF-8"?><NGODisconnectCabinet_Input><Option>NGODisconnectCabinet</Option><CabinetName>${initParam["CABINETNAME"]}</CabinetName><UserDBId>'+ $("#userDBId").val() +'</UserDBId></NGODisconnectCabinet_Input>')+'&strAPI="NGODisconnectCabinet"'
            
        $("#embed").hide();
        $("#logout").html("Logging out...");
        $.ajax({
            type:"POST",
            dataType :"xml",
            url: 'SRVCall',
            data: param, 
            success: function(result) {
                //alert("In success"+result);
                
                
            },    error: function(jqXHR, error, errorThrown) { 
                //alert("SRVCall : "+jqXHR.status+" Msg:"+jqXHR.statusText);
                var data = xhr.responseText;
                //alert("dataerr"+data)
            } ,
            complete: function (xhr, status) {
                var data = xhr.responseText;
                //alert("datagl"+data)

//  Clear the localStorage
//                localStorage.clear();
                
                xmlDoc = $.parseXML( data ),
                $xml = $( xmlDoc ),
                $title = $xml.find( "Status" );
                //alert("UserDBId____ = " + $title.text());
                
                if($title.text() == 0){
                    //window.location.href = 'ApsOdLogin.jsp'
                    window.location.href = sessionStorage.loginpage;
                    
                }
                else{
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

--%>
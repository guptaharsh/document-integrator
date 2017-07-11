<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<!doctype html>
<html class="no-js" lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width,initial-scale=1">
        <title>I-Decisions</title>
        <link defer rel="stylesheFet" href="css/h5bp/normalize.css">
        <link defer rel="stylesheet" href="css/sprites.css">
        <link defer rel="stylesheet" href="css/special-page.css">
        <link defer rel="stylesheet" href="css/typographics.css">
        <link defer rel="stylesheet" href="css/content.css"> <!-- Form REQUIRED: Content (we need the boxes) -->
        <link defer rel="stylesheet" href="css/sprite.forms.css"> <!--Form field SPRITE: Forms -->

        <!--        <style>
                    .special_page > .top {
                        background: -ms-linear-gradient(top, rgba(4,60,109,1) 0%,rgba(4,60,109,1) 100%);
                        background: rgb(231,120,23);
                        background: linear-gradient(top, rgba(4,60,109,1) 0%,rgba(4,60,109,1) 100%);
                        filter:null;
                        height: 80px;
                    }
                    .special_page > .top .gradient {
                        background: -ms-linear-gradient(top, rgba(255,255,255,1) 0%,rgba(255,255,255,1) 100%);
                        background: rgb(255,255,255);
                        background: linear-gradient(top, rgba(255,255,255,1) 0%,rgba(255,255,255,1) 100%);
                        filter:null;
                    }
                    .special_page h1 {
                        font-size: 26px;
                        top: 79px;

                    }
                </style>-->

    </head>
    <body class="special_page"  oncontextmenu="contextMenuStatus();"  style='overflow:hidden;font-family:ZurichBT'>
        <div style="height: 74px;width:100%">
            <!--            <img src="resource\img\icici-logo.png" width="220" height="45" style="margin-top:10px;">-->
            <img src="resource\img\icici_bank.png" width="100%" height="45" style="margin-top:0px;">
            <!--            <img src="resource\img\I-Decisions.jpg" style="margin-top:2px; float: right" width="60">-->
        </div>

        <!--        Orange Strip-->
        <!--        <div class="top">
                    <div class="gradient"></div>
                    <div class="white"></div>
                    <div class="shadow"></div>
                </div>-->
        <div class="content">
            <!--            <h1><i style="color:rgb(231,120,23);"></i>i-Decisions</h1>-->
            <h1><i style="color:rgb(231,120,23);"></i></h1>
            <div class="background" style="margin-top: -27px; display: none"></div>

            <div class="wrapper" style="top: 150px">
                <img src="resource\img\I-Decisions.jpg" style="padding-bottom: 10%; margin-left: 30%; " width="150">
                <div class="box">
                    <div class="header white">
                        <img src="img/icons/packs/fugue/16x16/lock.png" width="16" height="16">
                        <h3>Login with your NT ID and password</h3>
                    </div>

                    <form onsubmit="return false">
                        <div class="content no-padding">
                            <div class="alert warning top .generated" style="display:none;margin: 0px; border-left-style: none; border-right-style: none; border-top-left-radius: 0px; border-top-right-radius: 0px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;">
                                <span class="icon"></span>
                            </div>
                            <div class="section _100">
                                <label>
                                    Username
                                </label>
                                <div>
                                    <input name="username" class="required" autofocus="" required="" id="username" autocomplete="off" >
                                </div>
                            </div>
                            <div class="section _100">
                                <label>
                                    Password
                                </label>
                                <div>
                                    <input name="password" type="password" class="required" required="" id="password" autocomplete="off">
                                </div>
                            </div>
                            <!--<div class="section _100" style="display:none">
                                <label>
                                    Application
                                </label>
                                <div>
                                    <select name="application">
                                        <option value="WC" selected>Web Client</option>
                                        <option value="WAC" >Web Admin Console</option>
                                    </select>
                                </div>
                            </div>-->
                        </div>

                        <div class="actions">
                            <div class="actions-right">
                                <input type="submit" value="Login" onclick="login('Y');">
                            </div>
                        </div>

                    </form>

                </div>
                <div class="shadow"></div>
            </div>
            <div style="display: none">
                Copyright © 2012 SERVOSYS Solutions, all rights reserved.
            </div>
        </div>

    </body>

</html>

<!--<script src="js/webclient/ErrorHandler.js" type="text/javascript" language="javascript"></script>-->
<script src="resource/jquery.min.js"></script>


<script>
                                    $.ajaxSetup({
                                        // Disable caching of AJAX responses
                                        cache: false
                                    });


                                    manageWindowSize();
                                    function manageWindowSize() {
                                        $(".top .gradient").height($(window).height() - 440)
                                    }
                                    $(window).resize(function() {
                                        manageWindowSize();
                                    });
</script>


<script>
    localStorage.clear();
    sessionStorage.loginpage = "./";
    localStorage.servobpm_application = "client";
    localStorage.servobpm_debugMode = "${initParam.DebugMode}"
    localStorage.servobpm_activityForm_debugMode = "${initParam.ActivityForm_DebugMode}"
    localStorage.servobpm_dms = "${initParam.DMSEnableStatus}"
    localStorage.servobpm_dbName = "${initParam.Cabinet}"
    localStorage.servobpm_alert_type = "${initParam.AlertType}";
    localStorage.servobpm_notification_type = "${initParam.NotificationType}"
    var response;
    try {
        response = JSON.parse('${requestScope.response}');
        $('.box .content .alert').hide()
        if ($.trim(response.info) != "") {
            $('.box .content .alert').show().text(response.info)
        }
        $(document).keypress(function(e) {
            if (e.keyCode == 12 & e.shiftKey) { // Log l
                alert("Log : " + response.log)
                e.preventDefault();
            }
            if (e.keyCode == 5 & e.shiftKey) { // error code
                alert("Error Code : " + response.status + "   Message : " + response.info)
                e.preventDefault();
            }
        });
    } catch (e) {
    }

</script>
<script>
    var objectName = "istreams";
    var Ud;
    var parentFolderIndex = 0;
//        alert('${initParam["GENERICVALUE"]}');
    var userExists = "Y";
    var userName = $("#username").val();
    var userPassword = $("#password").val();
    
    <%
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = sdf.format(date);

    %>
    function login(userExists)
    {
//        var param ='InputXml=' + encodeURIComponent('<?xml version="1.0" encoding="UTF-8"?><NGOConnectCabinet_Input><Option>NGOConnectCabinet</Option><CabinetName>$//{initParam["CABINETNAME"]}</CabinetName><UserName> ' + $("#username").val() + ' </UserName><UserPassword> '+ $("#password").val() + '</UserPassword><UserType>U</UserType><UserExist>N</UserExist></NGOConnectCabinet_Input>');
        var param = 'InputXml=' + encodeURIComponent('<?xml version="1.0" encoding="UTF-8"?><NGOConnectCabinet_Input>'
                + '<Option>NGOConnectCabinet</Option>'
                + '<CabinetName>${initParam["CABINETNAME"]}</CabinetName>'
                + '<CurrentDateTime><%out.print(time);%></CurrentDateTime>'
                + '<UserName>' + $("#username").val() + '</UserName><UserPassword>' + $("#password").val() + '</UserPassword>'
                + '<UserType>S</UserType><UserExist>' + userExists + '</UserExist><MainGroupIndex>0</MainGroupIndex><Locale>en-in</Locale><ApplicationName>N</ApplicationName><Hook>Omnidocs Client</Hook></NGOConnectCabinet_Input>')
                + '&strAPI="NGOConnectCabinet"';
        $('[value="Login"]').fadeTo("slow", 0.5);
        $('[value="Login"]').attr("disabled", true);
        $.ajax({
            type: "POST",
            dataType: "xml",
            url: 'SRVCall',
            data: param,
            success: function(result) {
                //alert("In success : \n"+result);
                //$("body").append(result);
                $('[value="Login"]').fadeTo("slow", 1.0);
                $('[value="Login"]').attr("disabled", false);

            }, error: function(jqXHR, error, errorThrown) {
                alert("login() , SRVCall : " + jqXHR.status + " Msg:" + jqXHR.statusText + "]");
                var data = xhr.responseText;
                alert("Error : response SRVCall : " + data)
                $('[value="Login"]').fadeTo("slow", 1.0);
                $('[value="Login"]').attr("disabled", false);
            },
            complete: function(xhr, status) {
                var data = xhr.responseText;
                $('[value="Login"]').fadeTo("slow", 1.0);
                $('[value="Login"]').attr("disabled", false);
                if (status == 'error' || !xhr.responseText) {
                    handleError();
                }
                else
                {
                    var data = xhr.responseText;
                    //alert("data"+data)

                    xmlDoc = $.parseXML(data);
                    $xml = $(xmlDoc);
                    $title = $xml.find("Status");
                    var status = $title.text();

                    if (status == "0")
                    {
                        $title = $xml.find("UserDBId");
                        //alert("UserDBId = " + $title.text());
                        Ud = $title.text();

                        localStorage.servoapsOd_userDBId = Ud;

                        $title = $xml.find("LastLoginTime");
                        //alert("Last Login Time = "+$title.text())
                        var LLT = $title.text();
                        localStorage.servoapsOd_lastLoginTime = LLT;

                        //$title = $xml.find( "Owner" );
                        //alert("User = "+$title.text())
                        //var owner = $title.text();
                        var user = $("#username").val();
                        localStorage.servoapsOd_loggedInUser = user;
                        //$title = $xml.find( "Status" );

                        //window.location.href = 'OD.jsp';
                        window.location.href = 'ApsOdHome.jsp';
                    }
                    else
                    {
                        if (status != "-50167")
                        {
                            alert($xml.find("Error").text())
                        }
                        else if (status == "-50167")     //already logged in
                        {
                            var loginChoice = confirm("The specified user is already logged in on some other machine."
                                    + "Do you want to disconnect and log in again?");
                            if (loginChoice == true)
                            {
                                login("N");
                            }
                            else
                            {
                                return false;
                            }
                        }
                    }

                }
                //getDocumentInputtXML(Ud, objectName)
            }
        });
    }

</script>
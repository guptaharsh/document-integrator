<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<!--<script>
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
</script>-->
<script>
    $(".left").css("height",$(window).height()-65);
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
    
    var userExists = "Y";
    function login(userExists)
    { 
    <%
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = sdf.format(date);
       
    %>
            validateApsOdLoginFields();
            if(apsOdLoginFieldsFlag)
            {
                if($("#apsOdUserType").select().val()=="E")
                {
                    var param ='InputXml=' + encodeURIComponent('<?xml version="1.0" encoding="UTF-8"?><NGOConnectCabinet_Input>'
                        +'<Option>NGOConnectCabinet</Option>'
                        +'<CabinetName>${initParam["CABINETNAME"]}</CabinetName>'
                        +'<CurrentDateTime><%out.print(time);%></CurrentDateTime>'
                        +'<UserName>' + $("#username").val() + '</UserName><UserPassword>'+ $("#password").val() + '</UserPassword>'
                        +'<UserType>S</UserType><UserExist>'+userExists+'</UserExist><MainGroupIndex>0</MainGroupIndex><Locale>en-in</Locale><ApplicationName>N</ApplicationName><Hook>Omnidocs Client</Hook></NGOConnectCabinet_Input>')
                        +'&strAPI="NGOConnectCabinet"' + '&usertypeflag='+$("#apsOdUserType").select().val()+'';
                }
                else{
                    var param ='InputXml=' + encodeURIComponent('<?xml version="1.0" encoding="UTF-8"?><NGOConnectCabinet_Input>'
                        +'<Option>NGOConnectCabinet</Option>'
                        +'<CabinetName>${initParam["CABINETNAME"]}</CabinetName>'
                        +'<CurrentDateTime><%out.print(time);%></CurrentDateTime>'
                        +'<UserName>' + 'username' + '</UserName><UserPassword>'+ 'password' + '</UserPassword>'
                        +'<UserType>S</UserType><UserExist>'+userExists+'</UserExist><MainGroupIndex>0</MainGroupIndex><Locale>en-in</Locale><ApplicationName>N</ApplicationName><Hook>Omnidocs Client</Hook></NGOConnectCabinet_Input>')
                        +'&strAPI="NGOConnectCabinet"' + '&username='+$("#username").val() + '&password='+$("#password").val()+'&usertypeflag='+$("#apsOdUserType").select().val()+'';
                }
                $('[value="Login"]').fadeTo("slow",0.5);   
                $('[value="Login"]').attr("disabled",true);
                // alert("Para is=="+param);
                $.ajax({
                    type:"POST",
                    dataType :"xml",
                    url: 'SRVCall',
                    data: param, 
                    success: function(result) {
                        // alert("In success : \n"+result);
                        //$("body").append(result);
                        $('[value="Login"]').fadeTo("slow",1.0);   
                        $('[value="Login"]').attr("disabled",false);
                
                    },    error: function(jqXHR, error, errorThrown) { 
                        alert("login() , SRVCall : "+jqXHR.status+" Msg:"+jqXHR.statusText+"]");
                        var data = xhr.responseText;
                        alert("Error : response SRVCall : "+data)
                        $('[value="Login"]').fadeTo("slow",1.0);   
                        $('[value="Login"]').attr("disabled",false);
                    } ,
                    complete: function (xhr, status) {
                        var data = xhr.responseText;
                        $('[value="Login"]').fadeTo("slow",1.0);   
                        $('[value="Login"]').attr("disabled",false);
                        if (status == 'error' || !xhr.responseText) {
                            handleError();
                        }
                        else 
                        {
                            var data = xhr.responseText;
                            //alert("data"+data)
                            xmlDoc = $.parseXML( data );
                            $xml = $( xmlDoc );
                            $title = $xml.find( "Status" );
                            var status = $title.text();
                            if(status == "0")
                            {
                                $title = $xml.find( "UserDBId" );
                                //alert("UserDBId = " + $title.text());
                                Ud = $title.text();
                                localStorage.servoapsOd_userDBId = Ud;
                                $title = $xml.find( "LastLoginTime" );                 
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
                                //window.location.href = 'ApsOdHome.jsp';
                                window.location.href = 'ApsOdHome.jsp';
                            }
                            else
                            {
                                if(status != "-50167")
                                {
                                    alert($xml.find( "Error" ).text())
                                }
                                else if(status == "-50167")     //already logged in
                                {
                                    var loginChoice = confirm("The specified user is already logged in on some other machine."
                                        +"Do you want to disconnect and log in again?");
                                    if(loginChoice == true)
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
        }

</script>
<!--Start ApsOd Fields Authentication-->
<script>
    apsOdLoginFieldsFlag=true;
    function validateApsOdLoginFields()
    {
        if(($("#username").val().length==0) && ($("#password").val().length==0))
        {
            apsOdLoginFieldsFlag=false;
            alert("Please fill Username and Password");
        }
        else 
        {
            if($("#username").val().length==0)
            {
                alert("Please fill Username");
                apsOdLoginFieldsFlag=false;
            }
            if($("#password").val().length==0)
            {
                alert("Please fill Password");
                apsOdLoginFieldsFlag=false;
            }
            else{
                if($("#apsOdUserType").select().val()=="Select")
                {
                    alert("Please select User Type");
                    apsOdLoginFieldsFlag=false;
                }
            }
        }
    }
</script>

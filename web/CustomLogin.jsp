<%-- 
    Document   : CustomLogin
    Created on : 7 Sep, 2013, 3:35:02 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Custom Login</title>
        
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
        
    </head>
    <body>
        
    </body>
</html>

<script>
    //  AJAX to get credentials
    $(document).ready(function(){
            $.ajax({
                type:"POST",
                url: 'SRVGetUserCredential',
                data: 
                    "dbName="+parent.top.$("#dbName").val()+
                    "&sessionId="+parent.top.$("#userDBId").val(), 
                success: function(result) {
                    alert(result)
                },
                error: function(jqXHR, error, errorThrown) { 
                    alert("UserCredential : "+jqXHR.status+" Msg:"+jqXHR.statusText+"]");
                }
            });
        
    })
</script>

<script>
//    $(document).ready(function(){
//        $.ajax({
//            type:"POST",
//            url: 'SRVJNDILookup',
//            data: "initailContextFactory="+$.+
//                "&providerURL="+$("#providerURL").val()+
//                "&userName="+$("#userName").val()+
//                "&credential="+$("#password").val()+
//                "&encoding="+$("#encoding").val()+
//                "&log_chncd="+$("#log_chncd").val(), 
//            success: function(result) {
//                alert(result)
//
//            },
//            error: function(jqXHR, error, errorThrown) { 
//                alert("login() , SRVCall : "+jqXHR.status+" Msg:"+jqXHR.statusText+"]");
//                var data = xhr.responseText;
//                alert("Error : response SRVCall : "+data)
//            }
//        });
//    })
</script>

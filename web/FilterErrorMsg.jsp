

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
       	<link rel="stylesheet" href="css/960gs/fluid.css"> <!-- 960.gs Grid System -->
        <!-- The HTML5-Boilerplate CSS styling -->
        <link rel="stylesheet" href="css/h5bp/normalize.css"> <!-- RECOMMENDED: H5BP reset styles -->
        <link rel="stylesheet" href="css/h5bp/non-semantic.helper.classes.css"> <!-- RECOMMENDED: H5BP helpers (e.g. .clear or .hidden) -->
        <link rel="stylesheet" href="css/h5bp/print.styles.css"> <!-- OPTIONAL: H5BP print styles -->
        <!-- The special page's styling -->
        <link rel="stylesheet" href="css/special-page.css"> <!-- REQUIRED: Special page styling -->
        <link rel="stylesheet" href="css/sprites.css"> <!-- REQUIRED: Basic sprites (e.g. buttons, jGrowl) -->
        <link rel="stylesheet" href="css/typographics.css"> <!-- REQUIRED: Typographics -->

        <!--jquery-->
        <script src="resource/jquery.min.js"></script>
        <script src="js/webclient/ErrorHandler.js" type="text/javascript" language="javascript"></script>
        <style>
        
            #background {
                position: fixed;
                top: 0px;
                left: 0px;
                width: 100%;
                height: 50%;
                background-color: rgb(54, 111, 172);
                z-index: 1;
                color:white;
            }
        </style>
    </head>

    <body class="special_page"  oncontextmenu="contextMenuStatus();">
        <div id="background">




  
<div class="error_text" style="margin-top: 50px">
                <center>
                <font color="black">
                  <%
                    if (null != request.getAttribute("message")) {
                        out.println(request.getAttribute("message"));
                    }
                %>
                </center>
            </div>

            </div>
        </div> 
        <script>
                    $(document).keypress(function(e) {
                        if (e.keyCode == 12 & e.shiftKey) { // Log l
                            // alert("Log : "+<c:out value="${requestScope['javax.servlet.error.exception']}"/>)
                            e.preventDefault();
                        }
                        if (e.keyCode == 5 & e.shiftKey) { // error code
                            // alert(" Error : " + <c:out value="${requestScope['javax.servlet.error']}"/>)
                            e.preventDefault();
                        }
                    });
        </script>
        </div>
    </body>
</html>

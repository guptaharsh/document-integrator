
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html style="height: 100%">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=Edge,IE=8,chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OD</title>

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
            .ui-widget-header{
                background: -webkit-linear-gradient(right,rgb(134, 68, 16),rgb(227,116,27)) !important; /*Safari 5.1-6*/
                background: -o-linear-gradient(right,rgb(134, 68, 16),rgb(227,116,27)) !important; /*Opera 11.1-12*/
                background: -moz-linear-gradient(right,rgb(134, 68, 16),rgb(227,116,27)) !important; /*Fx 3.6-15*/
                background: linear-gradient(to right,rgb(134, 68, 16),rgb(227,116,27)) !important;/*Standard*/ 
                background-color: rgb(231,120,23);
            }
        </style>


    </head>
    <body style="height: 100%">
        <div id="tabs" style=" height: 98.6%;">
            <ul style="height: 37px;">
                <li><a href="#tabs-1">Search</a></li>
                <li><a href="#tabs-2" id="upload">Upload</a></li>
                <!--<li><a id="auditTrail" href="#tabs-3">Audit Trail</a></li>-->
            </ul>
            <div id="tabs-1" style="height: 50%;padding-left: 1px;margin: 0px;width: 100%;margin-top: -1%;margin-right: 5%;">
                <!--                    <br>-->
                <!--                    <div id="documentDIV">-->
                <div id="documentDIV">
                    <!--<h3>Document Details</h3>-->
                    <div style="overflow: auto">
<!--                        <div style="width: 200px; height: 50px; display: none;">
                            <img src="resource\img\search.png" style="margin-left: 5%; width: 35px; height: 35px;">
                            <font>&nbsp; Search</font>
                        </div><br>-->

                        <table cellpadding="4" style="background: #f0efe4;width:100%">
                            <tbody>
                                <tr>
                                    <td style="width:25%">
                                        APS ID
                                    </td>
                                    <td>
                                        <input type="text" id="aps_id_search" class="input-large" style=" height: 15px;"/>
                                        <br/>
                                        <span id="apsidsearcherror"></span>
                                    </td>
                                </tr>

                                <tr style=" width: 500px;">
                                    <td>
                                        Application Form No<!--<font class="mandatory">*</font>-->
                                    </td>
                                    <td>
                                        <input type="text" id="application_form_search"/><!-- class="input-large" style=" height: 15px;"/>-->
                                        <!--<span class="errorSpan"></span>-->
                                        <br/>
                                        <span id="applicationformsearcherror"></span>
                                    </td>
                                </tr>

                                <!--<div style=" width: 382px;">
                                    <input type="submit" value="Submit" id="search_submit" style=" float: right;"/>

                                </div>-->
                                <tr style=" width: 382px;">
                                    <td></td>
                                    <td>
                                        <!--<input type="button" value="GetDoc" id="getDoc" style=" float: right;"/>-->
                                        <input type="button" value="List View" id="get" style=" float: right;margin-right: 220px;margin-left: 10px;"/>
                                        <input type="button" value="Dropdown View" id="get1" style="float: right;"/>
                                    </td>

                                </tr>
                            </tbody>
                        </table>
<!--                        <br>-->
                        <!--                            <div id="uploadedDocumentList" style="overflow: auto; max-height: 300px; width: 900px;">-->
                        <div id="uploadedDocumentList" style="overflow: auto; max-height: 500px; width: 100%;">
                            <!--                                <table id="documentList" cellpadding="5" style="display:none; border-collapse: separate; width: 100%">-->
                            <table id="documentList" cellpadding="5" style="display:none; border-collapse: separate; width: 1000px">
                                <tr>
                                    <th class="documentListHeading" width="">

                                    </th>
                                    <th class="documentListHeading" width="168">
                                        Name
                                    </th>
                                    <th class="documentListHeading">
                                        Uploaded By
                                    </th>
                                    <th class="documentListHeading">
                                        Modified on
                                    </th>
                                    <th class="documentListHeading">
                                        Type
                                    </th>
                                    <th class="documentListHeading">
                                        Size
                                    </th>
                                    <th class="documentListHeading noofpage">
                                        Pages
                                    </th>
                                </tr>
                                <tbody id="documents">
                                </tbody>
                            </table>

                            <!--                                        <table id="viewMore" style="width: 100%; display: none; text-align: left;">
                                                                <tr>
                                                                    <td>
                                                                        <input type="button" id="viewMore_DocumentList" value="View More" />
                                                                    </td>
                                                                </tr>
                                                            </table>-->
                        </div>

                        <%--    View More   --%>
                        <div class="viewMore_DIV">
                            <table id="viewMore" style="width: 100%; display: none; text-align: left;">
                                <tr>
                                    <td>
                                        <input type="button" id="viewMore_DocumentList" value="View More" />
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>



                    <%-- DIV containing the APPLET --%>

                    <div id="main_content" class="SRV_NOTIFY"> 
                        <div class="content tableContent" id="processInstanceTableContent" style="padding:0px;margin-left: -1px;" >

                        </div>
                        <div class="clear"></div>
                    </div> 
                </div> 
            </div>
            <%--    APPLET DIV ENDS here   --%>


            <div id="tabs-2" style="padding-left: 6px;margin: -1%;display: block;width: 100%;">
                <!--                    <br>-->
<!--                <div style="width: 200px; height: 50px; display: none">
                    <img src="resource\img\filestore.png" style="margin-left: 5%; width: 35px; height: 35px;"> 
                    <font>&nbsp; Upload</font>
                </div><br>-->

                <form action="SRVFileUpload" onsubmit="return validateForm()" method="post" enctype="multipart/form-data">
                    <table id="uploadTable" cellpadding="4" style="width:100%" class="r1">
                        <tbody>
                            <tr>
                                <td>
                                    APS ID

                                </td>
                                <td>
                                    <input type="text" name="aps_id_upload" id="aps_id_upload" style=" height: 15px;"/>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    Application Form No<font class="mandatory">*</font>
                                </td>
                                <td>
                                    <input type="text" name="application_form_upload" id="application_form_upload" style=" height: 15px;"/>
                                    <span class="errorSpan"></span>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    Applicant Type<font class="mandatory">*</font>
                                </td>
                                <td>
                                    <select id="applicant_type" name="applicant_type_name">
                                        <option value="">Select</option>
                                    </select>
                                    <span class="errorSpan"></span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Parent Document<font class="mandatory">*</font>
                                </td>
                                <td>
                                    <select id="parent_document" name="parent_document">
                                        <option value="">Select</option>
                                    </select>
                                    <span class="errorSpan"></span>
                                    <input type="hidden" id="parent_document_txt" name="parent_document_txt" value=""/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Child Document<font class="mandatory">*</font>
                                </td>
                                <td>
                                    <select id="child_document" name="child_document_name">
                                        <option value="">Select</option>
                                    </select>
                                    <span class="errorSpan"></span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    File<font class="mandatory">*</font>
                                </td>
                                <td>
                                    <input type="file" name="file" id="filePath" />
                                    <span class="errorSpan"></span>
                                    <input type="hidden" name="userDBId" id="userDBId_OD" value=""/>
                                    <input type="hidden" name="user" id="user_OD" value=""/>
                                    <input type="hidden" name="user" id="uploadFlag_OD" value=""/>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <input type="submit" value="Upload" id="upload_submit" onclick="setUploadFlag()"/>
                                    <input type="submit" value="Create & Upload" id="created_and_upload_submit" onclick="setCreateNUploadFlag()"/>
                                    <!--<input type="button" value="Submit" id="upload_submit"><!-- style=" float: right;"/>-->
                                </td>

                            </tr>

                            <!--                            <br>-->
                        </tbody>
                    </table>

                </form>
                <!--    TARGET IFRAME for response  -->
                <!--<iframe id="upload_target_frame" src="#" style="display :none"></iframe>-->

            </div>
            <!--    Tab-3 for Audit Trail ---->                                            
            <!--<div id="tabs-3" style="overflow: auto">
            </div>-->
        </div>

        <%--    APPLET'S Div    --%>                            
        <div id="wdesk:ivapp" style="display: inline; overflow: auto; height: 100%">
            <!--<div class="appletHead"></div>-->
        </div>

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

        <div id="frameInside" style="display: none;">
            <iframe name="getDocument_ID" > <!-- Target iframe for downloadable content/file -->
            </iframe>
        </div>
        <%--  APPLET'S Div ENDS --%>

        <input type="hidden" id="dbName_OD"/>


    </body>
</html>

<!--<script>
    $(document).ready(function(){
      $("#aps_id_search").focus(function(){
         alert("OD Session Id (FOCUS) = "+parent.top.$("#userDBId").val())
        alert("OD User Name (FOCUS) = "+parent.top.$("#user").val())
        
      })
    })
</script>-->
<script>
    $(document).ready(function()
    {
        //alert('${SUPPORTEDFILEFORMATS}')
    })
</script>

<script defer>
    $(document).ready(function() {
        //alert('$//{STATUS}')

        //$("#userDBId").val(localStorage.servoapsOd_userDBId);

        if ('${STATUS}'.toUpperCase() == 'SUCCESS')
        {
            alert("Document uploaded successfully!")
        }
        else if ('${STATUS}'.toUpperCase() == 'FILETOOLARGE')
        {
            alert("File is too large to upload.")
        }
        else if ('${STATUS}'.toUpperCase() == 'ERROR')
        {
            alert("Some error occured at server side.")
        }
        else if ('${STATUS}'.toUpperCase() == 'FOLDERNOTFOUND')
        {
            alert("Folder not found.")
        }
        else if ('${STATUS}'.toUpperCase() == 'FOLDERALREADYCREATED')
        {
            alert("Folder already exists")
        }
        else if ('${STATUS}'.toUpperCase() == 'FOLDERNOTCREATED')
        {
            alert("Folder not created.")
        }
        else if ('${STATUS}'.toUpperCase() == 'INVALIDFILE')
        {
            alert("Invalid file uploaded.")
        }
        else if ('${STATUS}'.toUpperCase() == 'INVALIDFILEFORMAT')
        {
            alert("Invalid file format.")
        }
        else if ('${STATUS}'.toUpperCase() == 'INVALIDSESSION')
        {
            alert("User session expired.")
            logout();
            //window.location.href = 'ApsOdLogin.jsp';
        }
    })
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
        $("#tabs").tabs();

        //$("#tabs-1").css("padding","0px");

        $(".ui-accordion-content").css("height", "100%");
        $("#main_content").css("padding", "0px");
        //  main_content border in ajax call 
    });

</script>

<script>
    function setUploadFlag()
    {

        $("#uploadFlag_OD").val("U")

    }
    function setCreateNUploadFlag()
    {
        $("#uploadFlag_OD").val("CU")
    }
    function validateForm()
    {
        //alert("Upload flag = "+$("#uploadFlag_OD").val())

        var isFormValidated = true;

        if ($("#application_form_upload").val() == "")
        {
            $("#application_form_upload").parent().find("span").html("Field is empty");
            //$("#application_form_upload").addClass("bordered");

            isFormValidated = false;
            //return false;
        }
        else
        {
            $("#application_form_upload").parent().find("span").html("");
        }
        if ($("#applicant_type").val() == "")
        {
            //alert("Please select Application Type")
            $("#applicant_type").parent().find("span").html("Field is empty");
            //$("#applicant_type").addClass("bordered");
            isFormValidated = false;
            //return false;
        }
        else
        {
            $("#applicant_type").parent().find("span").html("");
        }
        if ($("#parent_document_txt").val() == "")
        {
            //alert("Please select Parent Document")
            //return false;
            $("#parent_document").parent().find("span").html("Field is empty");
            //$("#parent_document").addClass("bordered");
            isFormValidated = false;

        }
        else
        {
            $("#parent_document").parent().find("span").html("");
        }
        if ($("#child_document").val() == "")
        {
//            alert("Please select Child Document")
//            return false;
            $("#child_document").parent().find("span").html("Field is empty");
            //$("#child_document").addClass("bordered");
            isFormValidated = false;
        }
        else
        {
            $("#child_document").parent().find("span").html("");
        }
        if ($("#filePath").val() == "")
        {
//            alert("Please choose a file to upload")
//            return false;
            $("#filePath").parent().find("span").html("Field is empty");
            //$("#filePath").addClass("bordered");
            isFormValidated = false;
        }
        else
        {
//            alert("in else")
            $("#filePath").parent().find("span").html("");
            var strSupportedFilesFormats = '${initParam["FILEFORMATS"]}';
            //alert(strSupportedFilesFormats)
            var arrSupportedFiles = strSupportedFilesFormats.split(",");
            var uploadedFileExtension = "";
//                uploadedFileExtension = $("#filePath").val().match(/\.[^.]+$/);
            var filePath = $("#filePath").val();
            uploadedFileExtension = filePath.split('.');
            //alert(uploadedFileExtension)
//                alert("uploadedFileExtension = "+uploadedFileExtension[1])
            var isFormatSupported = false;
//                alert("isFormatSupported = "+isFormatSupported)
            for (var i = 0; i < arrSupportedFiles.length; i++)
            {
//                    alert("arrSupportedFiles = "+arrSupportedFiles[i])
                if (uploadedFileExtension[1].toUpperCase() == arrSupportedFiles[i].toUpperCase())
                {
//                        alert(uploadedFileExtension[1]+ " == "+arrSupportedFiles[i])
                    isFormatSupported = true;
                    //return false;
                }
            }
//                alert("isFormatSupported = "+isFormatSupported)
            if (isFormatSupported == false)
            {
                isFormValidated = false;
                //alert("File format not supported.")
                $("#filePath").parent().find("span").html("Invalid file format");
            }
        }
//        alert("isFormValidated = "+isFormValidated)
        if (isFormValidated == false)
        {
            return false;
        }
        if (isFormValidated == true)
        {
            $("#upload_submit").fadeTo("slow", 0.5);
            $("#upload_submit").attr("disabled", true);

            $("#created_and_upload_submit").fadeTo("slow", 0.5);
            $("#created_and_upload_submit").attr("disabled", true);
//           $("#filePath").fadeTo("slow",0.5);
//          $("#filePath").attr("disabled",true); 
        }

        $("#userDBId_OD").val(parent.top.$("#userDBId").val());
        $("#user_OD").val(parent.top.$("#user").val());
        $("#dbName_OD").val(parent.top.$("#dbName").val());
//        alert("OD Session Id (UPLOAD) = "+parent.top.$("#userDBId").val())
//        alert("OD User Name (UPLOAD) = "+parent.top.$("#user").val())


    }
</script>

<!--<script>
    function logout(){
        //var param ='InputXml=' + encodeURIComponent('<?xml version="1.0" encoding="UTF-8"?><NGODisconnectCabinet_Input><Option>NGODisconnectCabinet</Option><CabinetName>icici</CabinetName><UserDBId>'+ $//{param.userDBId} +'</UserDBId></NGODisconnectCabinet_Input>')
        var param ='InputXml=' + encodeURIComponent('<?xml version="1.0" encoding="UTF-8"?><NGODisconnectCabinet_Input><Option>NGODisconnectCabinet</Option><CabinetName>$//{initParam["CABINETNAME"]}</CabinetName><UserDBId>'+ localStorage.servoapsOd_userDBId +'</UserDBId></NGODisconnectCabinet_Input>')
            
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
</script>-->
<script>

    //var status = '$//{initParam["T-WIDTH"]}';
    //alert("w= "+status);SUPPORTEDFILEFORMATS
    if (!${initParam["APS_WINDOW_DISPLAY"]}) {
        //alert("in if");
        $("#right").hide();
        $("#left").css("max-width", "100%")
        $("#left").css("width", "100%");

    } else {
        // alert("in else");
    }
</script>

<script>
    $(document).ready(function() {

        var param = 'type=' + "parent";
        $.ajax({
            type:"POST",
            url: 'SRVGetDb',
            data: param,
            success: function(objData) {
                //alert(objData)
                var parents = JSON.parse(objData);
                for (i in parents) {
                    var parentDetails = parents[i];
                    var parent = parentDetails.split("$")[0];
                    var snameParent = parentDetails.split("$")[1];
                    //    alert("parent = "+parent +" , snameParent = "+snameParent)
                    $("#parent_document").append("<option value=" + i + " shortname='" + snameParent + "'>" + parent + "</option>");
                }
            }, error: function(jqXHR, error, errorThrown) {
                //alert("Something went wrong , Please refresh page .["+jqXHR.status+" Msg:"+jqXHR.statusText+"]");
                var data = xhr.responseText;
                alert("SRVGetDb : \n" + data)
            }

        });

    });
</script>

<script>
    $(document).ready(function() {
        var param = 'type=' + "applicant";
        $.ajax({
            type:"POST",
            url: 'SRVGetDb',
            data: param,
            success: function(objData) {
                var applicantTypes = JSON.parse(objData);
                for (i in applicantTypes) {
                    var applicantTypeDetails = applicantTypes[i];
                    var applicantType = applicantTypeDetails.split("$")[0];
                    var snameApplicantType = applicantTypeDetails.split("$")[1];
                    $("#applicant_type").append("<option value='" + applicantType + "' sname='" + snameApplicantType + "'>" + applicantType + "</option>");
                }
            }, error: function(jqXHR, error, errorThrown) {
                //alert("Something went wrong , Please refresh page .["+jqXHR.status+" Msg:"+jqXHR.statusText+"]");
                var data = xhr.responseText;
                alert("SRVGetDb : \n" + data)
            }

        });

    });
</script>

<script>
    $("#parent_document").select().change(function() {
        $("#child_document").empty();

        $("#parent_document_txt").val($("#parent_document :selected").text())

        var param = 'id=' + $("#parent_document").val() + '&type=' + "child";

        $.ajax({
            type:"POST",
            url: 'SRVGetDb',
            data: param,
            success: function(objData) {
                var persons = JSON.parse(objData);
                //alert(objData)
                for (i in persons) {
                    var person = persons[i];
                    //alert("data"+ person);
                    $("#child_document").append("<option value='" + person + "'>" + person + "</option>");
                }
            }, error: function(jqXHR, error, errorThrown) {
                //alert("Something went wrong , Please refresh page .["+jqXHR.status+" Msg:"+jqXHR.statusText+"]");
                var data = xhr.responseText;
                alert("SRVGetDb : \n" + data)
            }
        });

    });

</script>

<%-- IV APPLET --%>

<script>
    $("#backButton", window.parent.document).click(function() {
//        $("#OD1", window.parent.document).html();
//        $("#OD1",window.parent.document).attr("src", "");
        $("#OD1", window.parent.document).remove();
        $("#OD", window.parent.document).show();
        $("#tabs").show();
        document.getElementById("wdesk:ivapp").style.display = "none";
        $("#backButton", window.parent.document).hide();
        $("#dmsButton", window.parent.document).hide();
        $("#dataClass", window.parent.document).hide();
        $("#showApplet", window.parent.document).hide();
        $("#dataClassDIV").hide();
    });

    $("#dmsButton", window.parent.document).click(function() {
        debugger;
//        alert('${initParam["WEBAPIURL"]}');
        var url = '${initParam["WEBAPIURL"]}';
        url = url + "&Userdbid=" + $("#userDBId_OD").val();
        url = url + "&FolderId=" + localStorage.folderId;
//        alert(url)
//        window.open(url, "", "height=" + $(window).height() + ",width=" + $(window).width() + "");
        window.open(url, "", "height=600, width=650,left=700");

    })

    $("#dataClass", window.parent.document).click(function() {
        //$("#tabs").hide();
        document.getElementById("wdesk:ivapp").style.display = "none";
        $("#dataClass", window.parent.document).hide();
        $("#showApplet", window.parent.document).show();
        $("#dataClassDIV").show();
    });

    $("#showApplet", window.parent.document).click(function() {
        //$("#tabs").hide();
        document.getElementById("wdesk:ivapp").style.display = "block";
        $("#dataClass", window.parent.document).show();
        $("#showApplet", window.parent.document).hide();
        $("#dataClassDIV").hide();
    });
</script>

<script>
    //var startedFrom = 1, maxHitCount , noOfRecordsFetched;
    $("#aps_id_upload").keyup(function() {
        $(this).val($(this).val().replace(/[^a-zA-Z0-9]/, ''))
    });
    $("#application_form_upload").keyup(function() {
        $(this).val($(this).val().replace(/[^a-zA-Z0-9-_]/, ''))
    });
    $("#aps_id_search").keyup(function() {
        $(this).val($(this).val().replace(/[^a-zA-Z0-9]/, ''))
    });
    $("#application_form_search").keyup(function() {
        $(this).val($(this).val().replace(/[^a-zA-Z0-9-_]/, ''))
    });

    $("#get").click(function() {
        //view more buttomn look like disable when data is available 25/02/2016
//        $("#viewMore").attr("disabled", true);

        $("#userDBId_OD").val(parent.top.$("#userDBId").val())
        $("#user_OD").val(parent.top.$("#user").val())
//        alert("OD Session Id (SEARCH)= "+parent.top.$("#userDBId").val())
//        alert("OD User Name (SEARCH) = "+parent.top.$("#user").val())

        startedFrom = 1, totalNoOfRecords = 0, noOfRecordsFetched = 0, previousIndex = 0, filedDateTime = "";
        $("#uploadedDocumentList").hide();
        $(".viewMore_DIV").hide();
        $("#documents").html("");
        //alert("get")



        if ($("#application_form_search").val() != "" || $("#aps_id_search").val() != "")
        {
            $("#get").fadeTo("slow", 0.5);
            $("#get").attr("disabled", true);
            $("#application_form_search").parent().find("span").html("");
            $.ajax({
                type: "POST",
                //dataType :"xml",
                data: //"UserDbId="+$//{param.userDBId}
                        //"UserDbId="+localStorage.servoapsOd_userDBId
                        "UserDbId=" + $("#userDBId_OD").val()
                        + "&ApplicationFormNo=" + $("#application_form_search").val()
                        + "&ApsId=" + $("#aps_id_search").val()
                        //+"&StartedFrom="+startedFrom,
                        + "&PreviousIndex=" + previousIndex
                        + "&LastSortOrderField=" + filedDateTime,
                //+"&sessionId="+ $//{Test2},
                url: "SRVGetUploadedDocument",
                success: function(data) {
                    $("#get").fadeTo("slow", 1.0);
                    $("#get").attr("disabled", false);
//                    alert("data recieved >" + data);
                    if ($.trim(data) == "INVALIDSESSION")
                    {
                        alert("User session expired.")
                        logout();
                        //window.location.href = 'ApsOdLogin.jsp';
                    }
                    else if ($.trim(data) == "FOLDERNOTFOUND")
                    {

                        alert("Folder Not Found!!");
                        //logout();
                        //window.location.href = 'ApsOdLogin.jsp';
                    }
                    else if (data.length == 0)
                    {

                        alert("No document found.")
                    }
                    else
                    {
                        $("#uploadedDocumentList").show();
                        $(".viewMore_DIV").show();
                        uploadedDocuments = JSON.parse(data);
                        //alert("uploaded documents details=="+JSON.stringify(uploadedDocuments));
                        var documentDetails;
                        //var /*isTechnical = "",*/ noOfPages, documentSize, modifiedDate, documentType;
                        for (i in uploadedDocuments)
                        {
                            if (i.toUpperCase() == "TOTALNOOFRECORDS")
                            {
                                totalNoOfRecords = uploadedDocuments[i];
                            }
                            else if (i.toUpperCase() == "NOOFRECORDSFETCHED")
                            {
                                noOfRecordsFetched = uploadedDocuments[i];
                                //alert("noOfrecFet = "+noOfRecordsFetched)
                                //startedFrom = parseInt(startedFrom) + parseInt(noOfRecordsFetched);
                                //alert("SF = "+startedFrom)
                            }
                            else if (i.toUpperCase() == "PREVIOUSFOLDERINDEX")
                            {
                                previousIndex = uploadedDocuments[i];
                            }
                            else if (i.toUpperCase() == "FILEDDATETIME")
                            {
                                filedDateTime = uploadedDocuments[i];
                            }
                            else
                            {
                                documentDetails = uploadedDocuments[i];
                                // alert("here i=="+i);
//                                alert("document details==" + documentDetails.split("$")[6]);
                                localStorage.folderId = documentDetails.split("$")[6];
                                localStorage.clicked = $("#application_form_search").val() + $("#aps_id_search").val();
                                //documentIndex = documentDetails.split("$")[0];

                                documentType = documentDetails.split("$")[3];
                                noOfPages = documentDetails.split("$")[7];
                                documentSize = documentDetails.split("$")[8];
                                modifiedDate = documentDetails.split("$")[9];
                                owner = documentDetails.split("$")[10];

                                $("#documents").append("<tr>"
                                        + "<td>"
                                        + "<input type='image' class='documentpopup' value='" + i.split(".")[0] + "' indexValue='" + i + "' alt='open' src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABmJLR0QA/wD/AP+gvaeTAAACMElEQVRIib2WTWtTQRSGnzM3LVpRcCFaVPCjpnXvb7BCSZqkrbrxR6jVghWEKFVQcW3xAzdugje5mdLf4MqdNlytilorCC5KqU2Te48LDaS1uTfajxdmc87M884chjkjRCiXy3XXakHWGBkMQw6LsA9Ale8ifAK8er2jOD1d+NaKIesF0+l0D5gJYAgwUZsAQuAFhNfK5fK7WINUKjMkwjNgVwx4rRZVuWBtqdQcdFbvPHtJhEdA5z/CATpFOJtM9i34fuXlXwbpdPY86ENalK1NiQj9yWTfjO9XXtOA5XK5Q/V6+AbYvQF4sxYSCdPnuu68AQiC8GY7cFW9A9xuw2BPraZ5AMlkMkfCkFlibouITHhecRwglRq8JSLjMSaBiB41qpqNg4NcbcABrPWuq+qVGAMHTNaoSn/ULFXNl8vFu2vj1nr3RORGzNozBjgeMWfUWq8lxPOKeZDLEeuPJYD967vLgzBceTIwMLAXoKura7lQKPwEGBkZ2bm0tLQDIAhWnjpO4gDIeiXrNrSov4hedJyOH41RrdbGGrlqtTbWnGsBBxADfI044galcwZ4v3UG8sEAU1tngDXGMMXvJ3ezFYjolCmVSh9BH2+BwaTneZ+357FzXfeLKsNAsAnwOsiw67rz0NQPfL8y29t7chE4zf/3hFCVUWtLzxuB7W2ZAL5fmUkmT0wa4yjoKZBENFeXRcx91eCcteVXa7ORpWj+tqjSAxz8k5oD3tLGt+UXGALqr6jgqNMAAAAASUVORK5CYII=' />"
                                        + "</td>"
                                        + "<td class='documentName'><a class='document' href='#' id='" + i.split(".")[0] + "' value='" + i.split(".")[0] + "' indexValue='" + i + "'>" + i.split(".")[0] + "</a></td>"
                                        + "<td class='documentDetails'>" + owner + "</td>"
                                        + "<td class='documentDetails'>" + modifiedDate + "</td>"
                                        + "<td class='documentDetails'>" + documentType + "</td>"
                                        + "<td class='documentDetails'>" + documentSize + "</td>"
                                        + "<td class='documentDetails noofpage'>" + noOfPages + "</td>"


                                        + "</tr>"
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

                        changeBinder();

                        //previousIndex = documentIndex;

                        //  Enable-Disable View More Button
                        //if(startedFrom >= totalNoOfRecords )
                        if (noOfRecordsFetched >= totalNoOfRecords)
                        {
                            //Disable view-more button
                            $("#viewMore_DocumentList").attr("disabled", true);
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

                        //$("#viewMore").removeAttr("disabled");
                        $("#viewMore").show();
                        //sendInputXML(data);
                        //localStorage.clear();                        //window.location.href = application;
                    }
                },
                error: function(jqXHR, error, errorThrown) {
                    $("#get").fadeTo("slow", 1.0);
                    $("#get").attr("disabled", false);
                    //localStorage.clear();
                    //window.location.href = application;
                    alert("SRVGetUploadedDocument: " + jqXHR.status + " , MSG : " + jqXHR.statusText)
                }
            });


        }
        else {
            alert("Please fill APS Id or Application Form No for search")
            return false;
        }


//            }
//            else
//            {
//            
//            function custom_alert(output_msg, title_msg)
//            {
//        if (!title_msg)
//            title_msg = 'Message:';
//
//        if (!output_msg)
//            output_msg = 'No Message to Display.';
//
//        $("<div></div>").html(output_msg).dialog({
//            title: title_msg,
//    resizable: false,
//    //position: "top",
//    position: ['top',200],
//    modal: true,
//    buttons: {
//        "Ok": function() 
//        {
//            $( this ).dialog( "close" );
//        }
//    }
//});
//}
//            if(!patt.test(applicationFormSearch))
//            {
//            $("#application_form_search").val("");
//            $("#application_form_search").css("border-color","red");
//            }
//            else{
//             $("#application_form_search").css("border-color","");
//            }
////            if(!patt.test(apsIdSearch))
////            {
////            $("#aps_id_search").val("");
////            $("#aps_id_search").css("border-color","red");
////            }
////            else{
////            $("#aps_id_search").css("border-color","");
////            }
//            //$("#application_form_search").parent().find("span").html("Field is empty");
//            //alert("Allow only \"UnderScore\" and \"Hipen\" Special character");
//            //$("<div>Allow only \"UnderScore\" and \"Hipen\" Special character</div>").dialog();
//            custom_alert("Allow only \"UnderScore\" and \"Hipen\" Special character","Message:");
//            }
//        else if($("#application_form_search").val == "")
//        {
//            alert("Please fill Application ")
//            return false;
//        }

    });

    $("#get1").click(function() {
        $("#userDBId_OD").val(parent.top.$("#userDBId").val());
        if ($("#application_form_search").val() != "" || $("#aps_id_search").val() != "")
        {
            if (localStorage.clicked == $("#application_form_search").val() + $("#aps_id_search").val()) {
//                alert("seee" + localStorage.folderId)
                var url = '${initParam["WEBAPIURL"]}';
                url = url + "&Userdbid=" + $("#userDBId_OD").val();
                url = url + "&FolderId=" + localStorage.folderId;
                var ff = "<iframe src='' id='OD1' style=' height: 100%; width: 100%; display: none;'></iframe>";
                $("#framediv", window.parent.document).append(ff);
                $("#OD", window.parent.document).hide();
                $("#OD1", window.parent.document).show();
                $("#OD1", window.parent.document).html();
                $("#OD1", window.parent.document).attr("src", url);
                $("#backButton", window.parent.document).show();
                $("#dmsButton", window.parent.document).show();
            } else {

                debugger;
                $.ajax({
                    type: "POST",
                    data: //"UserDbId="+$//{param.userDBId}
                            //"UserDbId="+localStorage.servoapsOd_userDBId
                            "UserDbId=" + $("#userDBId_OD").val()
                            + "&ApplicationFormNo=" + $("#application_form_search").val()
                            + "&ApsId=" + $("#aps_id_search").val()
                            + "&PreviousIndex=0"
                            + "&LastSortOrderField=" + "",
                    url: "SRVGetUploadedDocument",
                    success: function(data) {
                        debugger;
                        if ($.trim(data) == "INVALIDSESSION")
                        {
                            alert("User session expired.")
                            logout();
                        }
                        else if ($.trim(data) == "FOLDERNOTFOUND")
                        {
                            alert("Folder Not Found!!");
                        }
                        else
                        {
                            uploadedDocuments = JSON.parse(data);
                            var documentDetails;
                            for (i in uploadedDocuments)
                            {
                                if (i.toUpperCase() == "TOTALNOOFRECORDS")
                                {
                                    totalNoOfRecords = uploadedDocuments[i];
                                }
                                else if (i.toUpperCase() == "NOOFRECORDSFETCHED")
                                {
                                    noOfRecordsFetched = uploadedDocuments[i];
                                    //alert("noOfrecFet = "+noOfRecordsFetched)
                                    //startedFrom = parseInt(startedFrom) + parseInt(noOfRecordsFetched);
                                    //alert("SF = "+startedFrom)
                                }
                                else if (i.toUpperCase() == "PREVIOUSFOLDERINDEX")
                                {
                                    previousIndex = uploadedDocuments[i];
                                }
                                else if (i.toUpperCase() == "FILEDDATETIME")
                                {
                                    filedDateTime = uploadedDocuments[i];
                                } else {
                                    documentDetails = uploadedDocuments[i];
//                                    alert("here i==" + documentDetails);
//                            alert("document details==" + documentDetails.split("$")[6]);
                                    localStorage.folderId = documentDetails.split("$")[6];
                                    localStorage.clicked = $("#application_form_search").val() + $("#aps_id_search").val();
//                                    alert("seee1" + localStorage.folderId)
//                                    alert("seee" + localStorage.folderId)
                                    var ff = "<iframe src='' id='OD1' style=' height: 100%; width: 100%; display: none;'></iframe>";
                                    $("#framediv", window.parent.document).append(ff);
                                    var url = '${initParam["WEBAPIURL"]}';
                                    url = url + "&Userdbid=" + $("#userDBId_OD").val();
                                    url = url + "&FolderId=" + localStorage.folderId;
                                    $("#OD", window.parent.document).hide();
                                    $("#OD1", window.parent.document).show();
                                    $("#OD1", window.parent.document).attr("src", url);
                                    $("#backButton", window.parent.document).show();
                                    $("#dmsButton", window.parent.document).show();
                                    break;
                                }
                            }
                        }
                    },
                    error: function(jqXHR, error, errorThrown) {
                        $("#get").fadeTo("slow", 1.0);
                        $("#get").attr("disabled", false);
                        //localStorage.clear();
                        //window.location.href = application;
                        alert("SRVGetUploadedDocument: " + jqXHR.status + " , MSG : " + jqXHR.statusText)
                    }
                });

            }
        } else {
            alert("Please fill APS Id or Application Form No for search")
            return false;
        }
    });

//  View More

    var obj_view_more = new Object();
    var documentBatch = new Array();

    $("#viewMore_DocumentList").click(function() {

        $('.document').unbind('click');
        $('.documentpopup').unbind('click');
        $("#viewMore_DocumentList").fadeTo("slow", 0.5);
        $("#viewMore_DocumentList").attr("disabled", true);
        $.ajax({
            type: "POST",
            //dataType :"xml",
            data:
                    //"UserDbId="+localStorage.servoapsOd_userDBId
                    "UserDbId=" + $("#userDBId_OD").val()
                    + "&ApplicationFormNo=" + $("#application_form_search").val()
                    + "&ApsId=" + $("#aps_id_search").val()
                    + "&PreviousIndex=" + previousIndex
/*
 * ------>  [ Bug :- DocList differnece in WEBAPI (DropDown View) and GENERIC CALL BROKER (LIST view)]
 * 
 * ------>  [ Passing RevisedDateTime value as LastSortOrder instead of FiledDateTime ]
 */            
//                    + "&LastSortOrderField=" + filedDateTime,  
                    + "&LastSortOrderField=" + modifiedDate,
            url: "SRVGetUploadedDocument",
            success: function(data) {
                //  Concat two objects
                // alert("comming data=="+data);
                $("#viewMore_DocumentList").fadeTo("slow", 1.0);
                $("#viewMore_DocumentList").attr("disabled", false);
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

                var documentDetails;

                // (viewMore_DocList) variable to show current batch of document
                var viewMore_DocList = JSON.parse(data);
                //alert(" vieew more details=="+JSON.stringify(viewMore_DocList));
                for (i in viewMore_DocList)
                {
                    // documentDetails = viewMore_DocList[i];
                    if (i.toUpperCase() == "TOTALNOOFRECORDS")
                    {
                        totalNoOfRecords = viewMore_DocList[i];
                    }
                    else if (i.toUpperCase() == "NOOFRECORDSFETCHED")
                    {
                        noOfRecordsFetched = viewMore_DocList[i];
                        //startedFrom = parseInt(startedFrom) + parseInt(noOfRecordsFetched);
                    }
                    else if (i.toUpperCase() == "PREVIOUSFOLDERINDEX")
                    {
                        previousIndex = viewMore_DocList[i];
                    }
                    else if (i.toUpperCase() == "FILEDDATETIME")
                    {
                        filedDateTime = viewMore_DocList[i];
                    }
                    else
                    {
                        //alert("view more i=="+i);
                        documentDetails = viewMore_DocList[i];
                        //  alert("document details==="+documentDetails);
//                        documentIndex = documentDetails.split("$")[0];
                        documentType = documentDetails.split("$")[3];
                        //alert("view more document type=="+documentType);
                        noOfPages = documentDetails.split("$")[7];
                        documentSize = documentDetails.split("$")[8];
                        modifiedDate = documentDetails.split("$")[9];
                        owner = documentDetails.split("$")[10];

                        $("#documents").append("<tr>"
                                + "<td>"
                                + "<input type='image' class='documentpopup' value='" + i.split(".")[0] + "' indexValue='" + i + "' alt='open' src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABmJLR0QA/wD/AP+gvaeTAAACMElEQVRIib2WTWtTQRSGnzM3LVpRcCFaVPCjpnXvb7BCSZqkrbrxR6jVghWEKFVQcW3xAzdugje5mdLf4MqdNlytilorCC5KqU2Te48LDaS1uTfajxdmc87M884chjkjRCiXy3XXakHWGBkMQw6LsA9Ale8ifAK8er2jOD1d+NaKIesF0+l0D5gJYAgwUZsAQuAFhNfK5fK7WINUKjMkwjNgVwx4rRZVuWBtqdQcdFbvPHtJhEdA5z/CATpFOJtM9i34fuXlXwbpdPY86ENalK1NiQj9yWTfjO9XXtOA5XK5Q/V6+AbYvQF4sxYSCdPnuu68AQiC8GY7cFW9A9xuw2BPraZ5AMlkMkfCkFlibouITHhecRwglRq8JSLjMSaBiB41qpqNg4NcbcABrPWuq+qVGAMHTNaoSn/ULFXNl8vFu2vj1nr3RORGzNozBjgeMWfUWq8lxPOKeZDLEeuPJYD967vLgzBceTIwMLAXoKura7lQKPwEGBkZ2bm0tLQDIAhWnjpO4gDIeiXrNrSov4hedJyOH41RrdbGGrlqtTbWnGsBBxADfI044galcwZ4v3UG8sEAU1tngDXGMMXvJ3ezFYjolCmVSh9BH2+BwaTneZ+357FzXfeLKsNAsAnwOsiw67rz0NQPfL8y29t7chE4zf/3hFCVUWtLzxuB7W2ZAL5fmUkmT0wa4yjoKZBENFeXRcx91eCcteVXa7ORpWj+tqjSAxz8k5oD3tLGt+UXGALqr6jgqNMAAAAASUVORK5CYII=' />"
                                + "</td>"
                                + "<td class='documentName'><a class='document' href='#' id='" + i.split(".")[0] + "' value='" + i.split(".")[0] + "' indexValue='" + i + "'>" + i.split(".")[0] + "</a></td>"
                                + "<td class='documentDetails'>" + owner + "</td>"
                                + "<td class='documentDetails'>" + modifiedDate + "</td>"
                                + "<td class='documentDetails'>" + documentType + "</td>"
                                + "<td class='documentDetails'>" + documentSize + "</td>"
                                + "<td class='documentDetails noofpage'>" + noOfPages + "</td>"


                                + "</tr>"
                                );
                        // changeBinder();
                    }
                }
                changeBinder();
                //previousIndex = documentIndex;

//  Enable-Disable View More Button
                //if(startedFrom > maxHitCount )
                if (noOfRecordsFetched >= totalNoOfRecords)
                {
                    //Disable view-more button
                    $("#viewMore_DocumentList").attr("disabled", true);
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
            error: function(jqXHR, error, errorThrown) {
                $("#viewMore_DocumentList").fadeTo("slow", 1.0);
                $("#viewMore_DocumentList").attr("disabled", false);
                alert("SRVGetUploadedDocument_ViewMore: " + jqXHR.status + " , MSG : " + jqXHR.statusText)
            }
        });
    });
</script>     


<script>
    //function getDocument()
    //$("#document_list_search").change(function()
//alert("StampPath = $//{initParam["STAMPINIPATH"]}")
    function showDocument(documentNameForDataClass)
    {

        //  Show dataClass button
        $("#otherButtons", window.parent.document).show();
        $("#dataClass", window.parent.document).show();

        //var noOfPages = $(this).parent().parent().find(".noofpage").html();
        var noOfPages = $(this).closest("tr").find(".noofpage").html();
        //alert("noOfPages = "+noOfPages)

        documentNameForDataClass = $(this).attr("value");
        //alert("documentNameForDataClass = "+documentNameForDataClass);
        var documentDetails, docDetails;
        try
        {

            //alert("index value=="+$(this).attr("indexValue"));
            documentDetails = uploadedDocuments[$(this).attr("indexValue")];
            //alert("document details=="+documentDetails);
            docDetails = documentDetails.split("$");
        }
        catch (e)
        {

            // alert("in catch block");
            for (i in documentBatch)
            {
                //  alert("i is = "+i)
                var uploadedDocuments_Details = documentBatch[i];
                //  alert("view more upload document details=="+JSON.stringify(uploadedDocuments_Details));
                for (key in uploadedDocuments_Details)
                {

                    //   alert("key is="+key);
                    var viewMoreDocument = uploadedDocuments_Details[key];
                    //  alert("view more document=="+JSON.stringify(viewMoreDocument));
                    for (docName in viewMoreDocument)
                    {

                        // alert("doc name=="+docName);
                        // alert("this value=="+$(this).attr("indexValue"));
                        if (docName == $(this).attr("indexValue"))
                        {
                            //alert("Doc is = "+docName)
                            documentDetails = viewMoreDocument[docName];
                            // alert("view more document details=="+documentDetails);
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
            type: "POST",
            //dataType :"xml",
            data: //"UserDBId="+$//{param.userDBId}
                    //"UserDBId="+localStorage.servoapsOd_userDBId
                    "UserDBId=" + $("#userDBId_OD").val()
                    + "&documentIndex=" + docIndex
                    + "&parentFolderIndex=" + parentFolderIndex
                    + "&dataAlsoFlag=Y"
                    + "&thumbNailsAlsoFlag=N"
                    + "&versionNo=1.0",
            url: "SRVGetDocumentProperty",
            success: function(data) {
                //alert("Doc Details --------- > \n"+data)

                //  empty previous document information                
                $("#dataClassProperty").html("");
                $(".documentName_DataClass").html("");
                $("#dataClass_DataClass").html("");

                var documentProperties = JSON.parse(data);
                for (i in documentProperties)
                {
                    var documentProperty = documentProperties[i];
                    //i is indexName
                    var docProp = documentProperty.split("$");
                    dataDefName = docProp[1];
                    $(".documentName_DataClass").html("Document Name : " + documentNameForDataClass);
                    $("#dataClass_DataClass").html("Data Class : " + dataDefName);
                    //alert(dataDefName)
                    var indexValue = docProp[0];
                    //alert(indexValue)


                    $("#dataClassProperty").append("<tr>"
                            + "<td>" + i + "</td>"
                            + "<td>" + indexValue + "</td>"
                            + "</tr>"
                            );

                }
            },
            error: function(jqXHR, error, errorThrown) {
                alert("SRVGetDocumentProperty : " + jqXHR.status + " , MSG : " + jqXHR.statusText)
            }
        });

        var servletPath = "${initParam["SERVLETURL"]}" + "/SRVGetDocument";
        if (documentType.toUpperCase() == "I")
        {
//            alert("ini path--"+${initParam["STAMPINIPATH"]});
            //showing search screen
            $("#tabs").hide();
            document.getElementById("wdesk:ivapp").style.display = "block";
            $("#backButton", window.parent.document).show();
            $("#dmsButton", window.parent.document).show();

            var url = servletPath + '?ISIndex=' + IsIndex + '&DocExt=' + documentExt + '&DocIndex=' + docIndex + '&PageNo=1&DocumentName=' + encodeURIComponent(documentNameForDataClass);
            var viewImageStampURL = "${initParam["SERVLETURL"]}" + "/SRVViewImageStamp?DocId=" + docIndex + "&PageNo=1&UserDBId=" + $("#userDBId_OD").val();
            //@ToDo Page No and Version    
            var annotUrl = "${initParam["SERVLETURL"]}" + "/SRVViewImageAnnotation?" + "DocId=" + encodeURIComponent(docIndex) + "&PageNo=1&Version=1.0&UserDBId=" + $("#userDBId_OD").val();


            var appletString =
                    '<applet mayscript="" code="com.newgen.applet.AnnotationBase.EditableAnnotationApplet.class" archive="${initParam["ODLIBRARY"]}/doccab/editableannotationapplet.jar" name="IVApplet" width="100%" height="100%" codebase="${initParam["ODLIBRARY"]}/doccab/" cabbase="${initParam["ODLIBRARY"]}/doccab/editableannotationapplet.cab">'
                    + '<param name="cache_archive" value="editableannotationapplet.jar">'
                    + '<param name="URL_Image" value="' + url + '">'
                    + '<param name="flag_Multipage" value="true">'
                    + '<param name="NumberOfPages" value="' + noOfPages + '">'
                    + '<param name="SpecialMultiLetterRep" value="true">'
                    + '<param name="HyperlinkAnnotation" value="true">'
                    + '<param name="ShowUsernamesWithAllAnnotation" value="false">'
                    + '<param name="num_VisiblePage" value="1">'
                    + '<param name="ResizePercentage" value="256">'
                    + '<param name="ToolBarFlag" value="true">'
                    + '<param name="AnnotationDisplay" value="true">'
                    + '<param name="URL_Annotation" value="' + annotUrl + '">'
                    + '<param name="MenuBar" value="false">'
                    + '<param name="codebase_lookup" value="true">'
                    + '<param name="UTF8Encoding" value="true">'
                    + '<param name="DynamicHideNShowToolBar" value="2">'
                    + '<param name="InitialZoomLensStatus" value="false">'
                    + '<param name="PrintOption" value="true">'
                    + '<param name="PrintParameter" value="1">'
                    + '<param name="URL_toolbarPropertiesFile" value="${initParam["ODLIBRARY"]}/ToolBarBean.properties">'
                    + '<param name="URL_annotToolbarPropertiesFile" value="${initParam["ODLIBRARY"]}/AnnotationToolbar.properties">'
                    + '<param name="CurrentUserName" value="' + $("#user_OD").val() + '">'
                    + '<param name="BrightnessOption" value="true">'
                    + '<param name="DynamicText" value="">'  //  blank
                    + '<param name="TransformOption" value="true">'
                    + '<param name="ServerSupportMultiPage" value="true">'
                    + '<param name="annotToolbar_Alignment" value="west">'
                    + '<param name="EditAnnotationOption" value="true">'
                    + '<param name="CroppedImageSize" value="32">'
                    + '<param name="PenThickness" value="3">'
                    + '<param name="URL_StampINIPath" value="${initParam["STAMPINIPATH"]}">'
                    + '<param name="URL_ImageStampFile" value="' + viewImageStampURL + '">'
                    + '</applet>';

            document.getElementById("wdesk:ivapp").innerHTML = appletString;

//            window.location.assign(viewImageStampURL);

            if ("${initParam["APPLET_TOOLBAR"]}" == "Y")
            {
                //document.IVApplet.showNHideToolBars(true,true);
                document.IVApplet.showNHideToolBars(true, false);
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
//            var annotUrl_Annotations = "DocId="+encodeURIComponent(docIndex)+"&PageNo=1&Version=1.0";
//            try
//            {
//                $.ajax({
//                    type:"POST",
//                    //dataType :"xml",
//                    data: annotUrl_Annotations,
//                    //+"&sessionId="+ $//{Test2},
//                    url :"SRVViewImageAnnotation",
//                    success:function(data){
//                        //alert(data)
//                        sendInputXML(data);
//                        //localStorage.clear();                        //window.location.href = application;
//                    },
//                    error: function(jqXHR,error, errorThrown) { 
//                        //localStorage.clear();
//                        //window.location.href = application;
//                        alert("SRVViewImageAnnotation : "+jqXHR.status+" , MSG : "+jqXHR.statusText)
//                    }
//                });
//
//            }
//            catch(e)
//            {
//                alert("viewImageAnnotation : "+e)
//            }
        }

        else
        {
            //  If documentType is not image  
            //if(documentExt.toUpperCase() != "TXT")
            {
                try
                {
                    $("#dataClassDIV").show();
                    $("#backButton", window.parent.document).show();
                    $("#dmsButton", window.parent.document).show();

                    $("#tabs").hide();

                    $("#dataClass", window.parent.document).hide();
                    //@ToDo Page No 
                    //var url_NonImage = servletPath +'?ISIndex='+IsIndex+'&DocExt='+documentExt+'&DocIndex='+docIndex+'&PageNo=1&DocumentName='+encodeURIComponent(documentNameForDataClass);
                    var url_NonImage = servletPath + '?ISIndex=' + IsIndex + '&DocExt=' + documentExt + '&DocIndex=' + docIndex + '&PageNo=1&DocumentName=' + encodeURIComponent(documentNameForDataClass);
                    //window.parent.frames["getDocument_ID"].location = url_NonImage ;
                    frames["getDocument_ID"].location = url_NonImage;

                }
                catch (e)
                {
                    alert("MESSAGE : " + e)

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

    function isImageDownloadStart() {
        return true;
    }
    function isImageDisplayed() {
        return true;
    }

</script>


<script defer>
    $(document).ready(function() {
        //         alert(localStorage.servoapsOd_loggedInUser)
        //         alert(localStorage.servoapsOd_lastLoginTime)
        //$("#font").html("Welcome "+ $("#user_OD").val() +" [Last Login Time: "+$.getLastLoginTime()+ "]");
        //parent.top.$("#font").html("Welcome "+ window.opener.$.getNTUserName() +" [ Last Login Time: "+window.opener.$.getLastLoginTime()+" ]");
        try
        {
            //alert("try")
            parent.top.$("#font").html("Welcome " + parent.top.$("#user").val() + " [ Last Login Time: " + parent.top.$("#lastlogintime").val() + " ]");
        }
        catch (e)
        {
            //alert("catch")
            $("#font").html("Welcome " + parent.top.$("#user").val() + " [ Last Login Time: " + parent.top.$("#lastlogintime").val() + " ]");
        }

    })
</script>


<script>
    function sendInputXML(inputXML)
    {
        //alert("inside sendInputXML")
        try
        {
            $.ajax({
                type: "POST",
                dataType: "xml",
                data: "InputXml=" + encodeURI(inputXML) + "&strAPI='NGOConnectCabinet'",
                url: "SRVCall",
                success: function(data) {
                    //alert(data)

                    //  main_content border when image loads                    
                    //$("#main_content").css("border","2px solid");
                },
                error: function(jqXHR, error, errorThrown) {

                    alert("sendInputXML , SRVCall : " + jqXHR.status + " , MSG : " + jqXHR.statusText)
                }
            });
        }
        catch (e)
        {
            alert("sendInputXML() : " + e)

        }
    }
</script>

<script>
    $(".header").click(function() {
        $(".tableContent").toggle();
    })
</script>

<style>

    #documentList,  .documentName, .documentDetails,  .documentListHeading { border: 1px solid white }
    .documentListHeading { text-align: left }
    *{ font-size: 12px }
    .documentListHeading { background:#c3b091 }
    .r1{background:#f0efe4;}
    .mandatory{color: red}
    .bordered{border: 1px solid red}
    .errorSpan {color: red}
</style>

<script>
    $.ajaxSetup({
        // Disable caching of AJAX responses
        cache: false
    });
</script>
<script>
    /*    $(document).ready(function(){
     //$(".documentpopup").live("click",function(){
     function changeBinder()
     {
     $(".documentpopup").click(function(){
     
     //  Show dataClass button
     $("#otherButtons",window.parent.document).show();
     $("#dataClass",window.parent.document).show();
     
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
     //"UserDBId="+localStorage.servoapsOd_userDBId
     "UserDBId="+$("#userDBId_OD").val()
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
     //showing search screen
     //$("#tabs").hide();
     //document.getElementById("wdesk:ivapp").style.display = "block";
     // $("#backButton",window.parent.document).show();
     
     
     //var url = servletPath +'?ISIndex=726%231&DocExt=tif&DocIndex=1912&PageNo=1';
     //@ToDo Page No and Version    
     //var url = servletPath +'?ISIndex='+IsIndex+'&DocExt='+documentExt+'&DocIndex='+docIndex+'&PageNo=1&DocumentName='+encodeURIComponent(documentNameForDataClass);
     //Working (currentPageNo)
     var url = servletPath +'?ISIndex='+IsIndex+'&DocExt='+documentExt+'&DocIndex='+docIndex+'&PageNo=1&DocumentName='+encodeURIComponent(documentNameForDataClass);
     //            var imageAnnotURL = "$//{initParam["SERVLETURL"]}"+"/SRVImageAnnotation?DocId="+docIndex+"&PageNo=1&UserDBId="+localStorage.servoapsOd_userDBId+"&UserName="+localStorage.servoapsOd_loggedInUser;
     //            var viewImageStampURL = "$//{initParam["SERVLETURL"]}"+"/SRVViewImageStamp?DocId="+docIndex+"&PageNo=1&UserDBId="+localStorage.servoapsOd_userDBId;
     var imageAnnotURL = "${initParam["SERVLETURL"]}"+"/SRVImageAnnotation?DocId="+docIndex+"&PageNo=1&UserDBId="+$("#userDBId_OD").val()+"&UserName="+$("#user_OD").val();
     var viewImageStampURL = "${initParam["SERVLETURL"]}"+"/SRVViewImageStamp?DocId="+docIndex+"&PageNo=1&UserDBId="+$("#userDBId_OD").val();
     //var url = servletPath +'?ISIndex='+IsIndex+'&DocExt='+documentExt+'&DocIndex='+docIndex+'&DocumentName='+encodeURIComponent(documentNameForDataClass)+'&PageNo=2';
     //alert(url)
     //@ToDo Page No and Version    
     //var annotUrl = "$//{initParam["SERVLETURL"]}"+"/SRVViewImageAnnotation?"+encodeURIComponent("DocId="+docIndex+"&PageNo=2&Version=1.0");
     var annotUrl = "${initParam["SERVLETURL"]}"+"/SRVViewImageAnnotation?"+"DocId="+encodeURIComponent(docIndex)+"&PageNo=1&Version=1.0&UserDBId="+$("#userDBId_OD").val();
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

    /*Applet displayed on new window (17/10/2013)
     *
     */
    /*            window.open("displayDocument.jsp?userName="+$("#user_OD").val()+"&userDBId="+$("#userDBId_OD").val()
     +"&documentExt="+documentExt+"&docIndex="+docIndex+"&documentName="+encodeURIComponent(documentNameForDataClass)
     +"&noOfPages="+noOfPages+"&ISIndex="+encodeURIComponent(IsIndex)+"&parentFolderIndex="+parentFolderIndex ,"","location=no,toolbar=no");
     //            var appletString = //'<form name="textForm">'
     //
     //                //+'<applet mayscript="" code="com.newgen.applet.AnnotationBase.EditableAnnotationApplet.class" archive="$//{initParam["APPLETLIBRARY"]}/editableannotationapplet.jar" name="IVApplet" width="100%" height="670px" codebase="$//{initParam["APPLETLIBRARY"]}/" cabbase="$//{initParam["APPLETLIBRARY"]}/editableannotationapplet.cab">'
     //                '<applet mayscript="" code="com.newgen.applet.AnnotationBase.EditableAnnotationApplet.class" archive="${initParam["ODLIBRARY"]}/doccab/editableannotationapplet.jar" name="IVApplet" width="100%" height="670px" codebase="${initParam["ODLIBRARY"]}/doccab/" cabbase="${initParam["ODLIBRARY"]}/doccab/editableannotationapplet.cab">'
     //                //+'<applet mayscript="" code="com.newgen.applet.AnnotationBase.EditableAnnotationApplet.class" archive="IVApplet.jar" name="IVApplet" width="100%" height="670px">'
     //                +'<param name="cache_archive" value="editableannotationapplet.jar">'
     //            //+'<param name="URL_Image" value="'+url+'">'
     //                +'<param name="URL_Image" value="'+url+'">'
     //                +'<param name="URL_Annotation" value="'+annotUrl+'">'
     //                //+'<param name="URL_AnnotCGI" value="http://192.168.1.10:8080/webdesktop/servlet/viewimageannotation?DocId=3602&PageNo=1&WD_UID=9070842216447148337">'
     //                +'<param name="URL_AnnotCGI" value="'+imageAnnotURL+'">'
     //                +'<param name="flag_Multipage" value="true">'
     //                +'<param name="NumberOfPages" value="'+noOfPages+'">'
     //                +'<param name="num_VisiblePage" value="1">'
     //                +'<param name="ResizePercentage" value="256">'
     //                +'<param name="ToolBarFlag" value="true">'
     //                +'<param name="AnnotationDisplay" value="true">'
     //                
     //                +'<param name="MenuBar" value="false">'
     //                +'<param name="codebase_lookup" value="false">'
     //                +'<param name="UTF8Encoding" value="true">'
     //                +'<param name="DynamicHideNShowToolBar" value="2">'
     //                +'<param name="InitialZoomLensStatus" value="false">'
     //                +'<param name="PrintOption" value="true">'
     //                +'<param name="PrintParameter" value="1">'
     //                //+'<param name="URL_StampINIPath" value="$//{initParam["ODLIBRARY"]}/admin/stamps/conf/stamps7905227433966239866.ini">'
     //                +'<param name="URL_StampINIPath" value="${initParam["STAMPINIPATH"]}">'
     //                //+'<param name="URL_ImageStampFile" value="http://192.168.1.10:8080/omnidocs/servlet/viewimagestamp?DocId=3464&Version=0&PageNo=1&UserDbId=750709221&CabinetName=icici&JtsIp=127.0.0.1&JtsPort=1099">'
     //                +'<param name="URL_ImageStampFile" value="'+viewImageStampURL+'">'
     //                //+'<param name="URL_toolbarPropertiesFile" value="">'
     //                +'<param name="URL_toolbarPropertiesFile" value="${initParam["ODLIBRARY"]}/ToolBarBean.properties">'
     //                +'<param name="URL_annotToolbarPropertiesFile" value="${initParam["ODLIBRARY"]}/AnnotationToolbar.properties">'
     //                +'<param name="CurrentUserName" value="'+$("#user_OD").val()+'">'
     //                +'<param name="BrightnessOption" value="true">'
     //                //+'<param name="DynamicText" value="servosys">'
     //                +'<param name="DynamicText" value="">'  //  blank
     //                +'<param name="TransformOption" value="true">'  
     //                +'<param name="ServerSupportMultiPage" value="true">'
     //                +'<param name="annotToolbar_Alignment" value="west">'
     //                +'<param name="EditAnnotationOption" value="true">'
     //                
     //                +'<param name="CroppedImageSize" value="32">'
     //                +'<param name="PenThickness" value="3">'
     //                +'</applet>'
     //                //+'</form>';
     //
     //                document.getElementById("wdesk:ivapp").innerHTML = appletString;
     //                
     //
     //                ///document.IVApplet.openNewImage(url, annotUrl, noOfPages,1,true);
     //
     //                //  Load applet toolbars (configurable)
     //                if("${initParam["APPLET_TOOLBAR"]}" == "Y")
     //                {
     //                    //document.IVApplet.showNHideToolBars(true,true);
     //                    //document.IVApplet.showNHideToolBars(true,false);
     //                }   
     //                alert("opening image in new window")
     //                //Open image in new window
     //                var w = window.open();
     //                var html = document.getElementById("wdesk:ivapp").html();
     //
     //                $(w.document.body).html(html);
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
     //            var annotUrl_Annotations = "DocId="+encodeURIComponent(docIndex)+"&PageNo=1&Version=1.0";
     //            try
     //            {
     //                $.ajax({
     //                    type:"POST",
     //                    //dataType :"xml",
     //                    data: annotUrl_Annotations,
     //                    //+"&sessionId="+ $//{Test2},
     //                    url :"SRVViewImageAnnotation",
     //                    success:function(data){
     //                        //alert(data)
     //                        sendInputXML(data);
     //                        //localStorage.clear();                        //window.location.href = application;
     //                    },
     //                    error: function(jqXHR,error, errorThrown) { 
     //                        //localStorage.clear();
     //                        //window.location.href = application;
     //                        alert("SRVViewImageAnnotation : "+jqXHR.status+" , MSG : "+jqXHR.statusText)
     //                    }
     //                });
     //
     //            }
     //            catch(e)
     //            {
     //                alert("viewImageAnnotation : "+e)
     //            }
     }
     
     else
     {
     //  If documentType is not image  
     //if(documentExt.toUpperCase() != "TXT")
     {
     try
     {
     $("#dataClassDIV").show();
     $("#backButton",window.parent.document).show();
     
     $("#tabs").hide();
     
     $("#dataClass",window.parent.document).hide();
     //@ToDo Page No 
     //var url_NonImage = servletPath +'?ISIndex='+IsIndex+'&DocExt='+documentExt+'&DocIndex='+docIndex+'&PageNo=1&DocumentName='+encodeURIComponent(documentNameForDataClass);
     var url_NonImage = servletPath +'?ISIndex='+IsIndex+'&DocExt='+documentExt+'&DocIndex='+docIndex+'&PageNo=1&DocumentName='+encodeURIComponent(documentNameForDataClass);
     //window.parent.frames["getDocument_ID"].location = url_NonImage ;
     frames["getDocument_ID"].location = url_NonImage ;
     
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
     
     }); 
     }
     });*/
</script>
<script>
    function changeBinder()
    {
//        $(".documentpopup").live("click",function(){        --- Bug (List Not Coming)
        $(".documentpopup").click(function() {
            //alert("hello hi")
            //  Show dataClass button
            //$("#otherButtons",window.parent.document).show();
            //$("#dataClass",window.parent.document).show();

            //var noOfPages = $(this).parent().parent().find(".noofpage").html();
            var noOfPages = $(this).closest("tr").find(".noofpage").html();
            //alert("noOfPages = "+noOfPages)

            documentNameForDataClass = $(this).attr("value");
            //alert("documentNameForDataClass = "+documentNameForDataClass);
            var documentDetails, docDetails;
            try
            {
                documentDetails = uploadedDocuments[$(this).attr("indexValue")];
                docDetails = documentDetails.split("$");
            }
            catch (e)
            {
                for (i in documentBatch)
                {
                    //alert("i is = "+i)
                    var uploadedDocuments_Details = documentBatch[i];
                    for (key in uploadedDocuments_Details)
                    {
                        var viewMoreDocument = uploadedDocuments_Details[key];
                        for (docName in viewMoreDocument)
                        {
                            if (docName == $(this).attr("indexValue"))
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
                type: "POST",
                //dataType :"xml",
                data: //"UserDBId="+$//{param.userDBId}
                        //"UserDBId="+localStorage.servoapsOd_userDBId
                        "UserDBId=" + $("#userDBId_OD").val()
                        + "&documentIndex=" + docIndex
                        + "&parentFolderIndex=" + parentFolderIndex
                        + "&dataAlsoFlag=Y"
                        + "&thumbNailsAlsoFlag=N"
                        + "&versionNo=1.0",
                url: "SRVGetDocumentProperty",
                success: function(data) {
                    //alert("Doc Details --------- > \n"+data)

                    //  empty previous document information                
                    $("#dataClassProperty").html("");
                    $(".documentName_DataClass").html("");
                    $("#dataClass_DataClass").html("");

                    var documentProperties = JSON.parse(data);
                    for (i in documentProperties)
                    {
                        var documentProperty = documentProperties[i];
                        //i is indexName
                        var docProp = documentProperty.split("$");
                        dataDefName = docProp[1];
                        $(".documentName_DataClass").html("Document Name : " + documentNameForDataClass);
                        $("#dataClass_DataClass").html("Data Class : " + dataDefName);
                        //alert(dataDefName)
                        var indexValue = docProp[0];
                        //alert(indexValue)


                        $("#dataClassProperty").append("<tr>"
                                + "<td>" + i + "</td>"
                                + "<td>" + indexValue + "</td>"
                                + "</tr>"
                                );

                    }
                },
                error: function(jqXHR, error, errorThrown) {
                    alert("SRVGetDocumentProperty : " + jqXHR.status + " , MSG : " + jqXHR.statusText)
                }
            });

            var servletPath = "${initParam["SERVLETURL"]}" + "/SRVGetDocument";
            if (documentType.toUpperCase() == "I")
            {
                //showing search screen
                //$("#tabs").hide();
                //document.getElementById("wdesk:ivapp").style.display = "block";
                // $("#backButton",window.parent.document).show();


                //var url = servletPath +'?ISIndex=726%231&DocExt=tif&DocIndex=1912&PageNo=1';
                //@ToDo Page No and Version    
                //var url = servletPath +'?ISIndex='+IsIndex+'&DocExt='+documentExt+'&DocIndex='+docIndex+'&PageNo=1&DocumentName='+encodeURIComponent(documentNameForDataClass);
                //Working (currentPageNo)
                var url = servletPath + '?ISIndex=' + IsIndex + '&DocExt=' + documentExt + '&DocIndex=' + docIndex + '&PageNo=1&DocumentName=' + encodeURIComponent(documentNameForDataClass);
//            var imageAnnotURL = "$//{initParam["SERVLETURL"]}"+"/SRVImageAnnotation?DocId="+docIndex+"&PageNo=1&UserDBId="+localStorage.servoapsOd_userDBId+"&UserName="+localStorage.servoapsOd_loggedInUser;
//            var viewImageStampURL = "$//{initParam["SERVLETURL"]}"+"/SRVViewImageStamp?DocId="+docIndex+"&PageNo=1&UserDBId="+localStorage.servoapsOd_userDBId;
                var imageAnnotURL = "${initParam["SERVLETURL"]}" + "/SRVImageAnnotation?DocId=" + docIndex + "&PageNo=1&UserDBId=" + $("#userDBId_OD").val() + "&UserName=" + $("#user_OD").val();
                var viewImageStampURL = "${initParam["SERVLETURL"]}" + "/SRVViewImageStamp?DocId=" + docIndex + "&PageNo=1&UserDBId=" + $("#userDBId_OD").val();
                //var url = servletPath +'?ISIndex='+IsIndex+'&DocExt='+documentExt+'&DocIndex='+docIndex+'&DocumentName='+encodeURIComponent(documentNameForDataClass)+'&PageNo=2';
                //alert(url)
                //@ToDo Page No and Version    
                //var annotUrl = "$//{initParam["SERVLETURL"]}"+"/SRVViewImageAnnotation?"+encodeURIComponent("DocId="+docIndex+"&PageNo=2&Version=1.0");
                var annotUrl = "${initParam["SERVLETURL"]}" + "/SRVViewImageAnnotation?" + "DocId=" + encodeURIComponent(docIndex) + "&PageNo=1&Version=1.0&UserDBId=" + $("#userDBId_OD").val();
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

                /*Applet displayed on new window (17/10/2013)
                 *
                 */
                window.open("displayDocument.jsp?userName=" + $("#user_OD").val() + "&userDBId=" + $("#userDBId_OD").val()
                        + "&documentExt=" + documentExt + "&docIndex=" + docIndex + "&documentName=" + encodeURIComponent(documentNameForDataClass)
                        + "&noOfPages=" + noOfPages + "&ISIndex=" + encodeURIComponent(IsIndex) + "&parentFolderIndex=" + parentFolderIndex, "", "left=0,top=40,"
                        + "resizable=yes,width=634,height=694,location=no,toolbar=no");
//            var appletString = //'<form name="textForm">'
//
//                //+'<applet mayscript="" code="com.newgen.applet.AnnotationBase.EditableAnnotationApplet.class" archive="$//{initParam["APPLETLIBRARY"]}/editableannotationapplet.jar" name="IVApplet" width="100%" height="670px" codebase="$//{initParam["APPLETLIBRARY"]}/" cabbase="$//{initParam["APPLETLIBRARY"]}/editableannotationapplet.cab">'
//                '<applet mayscript="" code="com.newgen.applet.AnnotationBase.EditableAnnotationApplet.class" archive="${initParam["ODLIBRARY"]}/doccab/editableannotationapplet.jar" name="IVApplet" width="100%" height="670px" codebase="${initParam["ODLIBRARY"]}/doccab/" cabbase="${initParam["ODLIBRARY"]}/doccab/editableannotationapplet.cab">'
//                //+'<applet mayscript="" code="com.newgen.applet.AnnotationBase.EditableAnnotationApplet.class" archive="IVApplet.jar" name="IVApplet" width="100%" height="670px">'
//                +'<param name="cache_archive" value="editableannotationapplet.jar">'
//            //+'<param name="URL_Image" value="'+url+'">'
//                +'<param name="URL_Image" value="'+url+'">'
//                +'<param name="URL_Annotation" value="'+annotUrl+'">'
//                //+'<param name="URL_AnnotCGI" value="http://192.168.1.10:8080/webdesktop/servlet/viewimageannotation?DocId=3602&PageNo=1&WD_UID=9070842216447148337">'
//                +'<param name="URL_AnnotCGI" value="'+imageAnnotURL+'">'
//                +'<param name="flag_Multipage" value="true">'
//                +'<param name="NumberOfPages" value="'+noOfPages+'">'
//                +'<param name="num_VisiblePage" value="1">'
//                +'<param name="ResizePercentage" value="256">'
//                +'<param name="ToolBarFlag" value="true">'
//                +'<param name="AnnotationDisplay" value="true">'
//                
//                +'<param name="MenuBar" value="false">'
//                +'<param name="codebase_lookup" value="false">'
//                +'<param name="UTF8Encoding" value="true">'
//                +'<param name="DynamicHideNShowToolBar" value="2">'
//                +'<param name="InitialZoomLensStatus" value="false">'
//                +'<param name="PrintOption" value="true">'
//                +'<param name="PrintParameter" value="1">'
//                //+'<param name="URL_StampINIPath" value="$//{initParam["ODLIBRARY"]}/admin/stamps/conf/stamps7905227433966239866.ini">'
//                +'<param name="URL_StampINIPath" value="${initParam["STAMPINIPATH"]}">'
//                //+'<param name="URL_ImageStampFile" value="http://192.168.1.10:8080/omnidocs/servlet/viewimagestamp?DocId=3464&Version=0&PageNo=1&UserDbId=750709221&CabinetName=icici&JtsIp=127.0.0.1&JtsPort=1099">'
//                +'<param name="URL_ImageStampFile" value="'+viewImageStampURL+'">'
//                //+'<param name="URL_toolbarPropertiesFile" value="">'
//                +'<param name="URL_toolbarPropertiesFile" value="${initParam["ODLIBRARY"]}/ToolBarBean.properties">'
//                +'<param name="URL_annotToolbarPropertiesFile" value="${initParam["ODLIBRARY"]}/AnnotationToolbar.properties">'
//                +'<param name="CurrentUserName" value="'+$("#user_OD").val()+'">'
//                +'<param name="BrightnessOption" value="true">'
//                //+'<param name="DynamicText" value="servosys">'
//                +'<param name="DynamicText" value="">'  //  blank
//                +'<param name="TransformOption" value="true">'  
//                +'<param name="ServerSupportMultiPage" value="true">'
//                +'<param name="annotToolbar_Alignment" value="west">'
//                +'<param name="EditAnnotationOption" value="true">'
//                
//                +'<param name="CroppedImageSize" value="32">'
//                +'<param name="PenThickness" value="3">'
//                +'</applet>'
//                //+'</form>';
//
//                document.getElementById("wdesk:ivapp").innerHTML = appletString;
//                
//
//                ///document.IVApplet.openNewImage(url, annotUrl, noOfPages,1,true);
//
//                //  Load applet toolbars (configurable)
//                if("${initParam["APPLET_TOOLBAR"]}" == "Y")
//                {
//                    //document.IVApplet.showNHideToolBars(true,true);
//                    //document.IVApplet.showNHideToolBars(true,false);
//                }   
//                alert("opening image in new window")
//                //Open image in new window
//                var w = window.open();
//                var html = document.getElementById("wdesk:ivapp").html();
//
//                $(w.document.body).html(html);
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
//            var annotUrl_Annotations = "DocId="+encodeURIComponent(docIndex)+"&PageNo=1&Version=1.0";
//            try
//            {
//                $.ajax({
//                    type:"POST",
//                    //dataType :"xml",
//                    data: annotUrl_Annotations,
//                    //+"&sessionId="+ $//{Test2},
//                    url :"SRVViewImageAnnotation",
//                    success:function(data){
//                        //alert(data)
//                        sendInputXML(data);
//                        //localStorage.clear();                        //window.location.href = application;
//                    },
//                    error: function(jqXHR,error, errorThrown) { 
//                        //localStorage.clear();
//                        //window.location.href = application;
//                        alert("SRVViewImageAnnotation : "+jqXHR.status+" , MSG : "+jqXHR.statusText)
//                    }
//                });
//
//            }
//            catch(e)
//            {
//                alert("viewImageAnnotation : "+e)
//            }
            }

            else
            {
                //  If documentType is not image  
                //if(documentExt.toUpperCase() != "TXT")
                {
                    try
                    {
                        $("#dataClassDIV").show();
                        $("#backButton", window.parent.document).show();
                        $("#dmsButton", window.parent.document).show();

                        $("#tabs").hide();

                        $("#dataClass", window.parent.document).hide();
                        //@ToDo Page No 
                        //var url_NonImage = servletPath +'?ISIndex='+IsIndex+'&DocExt='+documentExt+'&DocIndex='+docIndex+'&PageNo=1&DocumentName='+encodeURIComponent(documentNameForDataClass);
                        var url_NonImage = servletPath + '?ISIndex=' + IsIndex + '&DocExt=' + documentExt + '&DocIndex=' + docIndex + '&PageNo=1&DocumentName=' + encodeURIComponent(documentNameForDataClass);
                        //window.parent.frames["getDocument_ID"].location = url_NonImage ;
                        frames["getDocument_ID"].location = url_NonImage;

                    }
                    catch (e)
                    {
                        alert("MESSAGE : " + e)

                    }
                }
                //  ELSE if documentType is TXT 
//            else
//            {
//                alert("!!Document is text!!")
//            }
            }

        });
    }
</script>

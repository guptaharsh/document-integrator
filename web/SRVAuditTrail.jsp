<%-- 
    Document   : SRVAuditTrail
    Created on : 2 Jul, 2013, 3:17:04 PM
    Author     : Administrator
--%>

<!--<%--@page contentType="text/html" pageEncoding="UTF-8"--%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>-->
        <div style="height: 500px; overflow: auto">
            
            <table>
                <tr>
                    <td>
                        Application Form No
                    </td>
                    <td>
                        <input type="text" id="applicationFormNo_audittrail" value=""/>
                    </td>
                </tr>
                
                <tr>
                    <td></td>
                    <td>
                        <input type="button" id="getAuditTrail" value="Submit" />
                    </td>
                </tr>
            </table>
            <br>
            <table id="auditTrail" cellpadding="5" style="border: 1px solid black; width: 100% ">
                <tr style="background-color:#c3b091">
                    <th class="th">Document Name</th>
                    <th class="th">Uploaded By(ID)</th>
                    <th class="th">Uploaded Date-Time</th>

                </tr>
                <tbody id="auditTrailBody">
                    <!-- Audit Trail data here -->
                </tbody>
            </table>
        </div>
<!--    </body>
</html>
-->
<script>
    //$(document).ready(function(){
    $("#getAuditTrail").click(function(){
//alert("ready")
//alert($("#user").val())
$("#auditTrailBody").html("");
            $.ajax({
                url: 'SRVGenAuditTrail',
                data: "requestType=getAuditTrail"
                    +"&uploadedBy="+$("#user").val() 
                    +"&applicationFormNo="+$("#applicationFormNo_audittrail").val(), 
                success: function(result) {
                    //alert("In success"+result);
                    var auditData = JSON.parse(result);
                    
                    var documentName;
                    var uploadedTime;
                    var uploadedBy;
                    
                    //alert(auditData)  
                    
                    for(i in auditData)
                    {
                        //alert(i)
                        var auditRow = auditData[i];
                        //alert(auditRow)
//                        for(j in auditRow)
//                        {
                            
                            documentName = auditRow[0];
                            //alert(documentName)
                            uploadedTime = auditRow[2];
                            uploadedBy = auditRow[1];
                        //}
                        //alert(documentName+" , "+uploadedBy+" , "+uploadedTime)
                       $("#auditTrailBody").append("<tr>"
                                                +"<td class='td'>"+documentName+"</td>"
                                                +"<td class='td'>"+uploadedTime+"</td>"
                                                +"<td class='td'>"+uploadedBy+"</td>"
                                            +"</tr>"
                                        );

                    }
                    
                    $("#auditTrail tr:even").addClass("r1");

                //$("#img").append('<img src="data:image/tif;base64," alt="Red dot" />'); 
            },    error: function(jqXHR, error, errorThrown) { 
                    alert("Something went wrong , Please refresh page .["+jqXHR.status+" Msg:"+jqXHR.statusText+"]");
                } 
            });
    });
</script>

<style>
    .r1{background:#f0efe4;}
    .td, .th { border: 1px solid black }
    /*padding:5px 5px 5px 5px}*/
</style>


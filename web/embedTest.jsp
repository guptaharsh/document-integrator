<html>
<head>
<title>Google</title>
<script type ="text/javascript">
function myload()
{
  
try
{
//
//var myAx = new ActiveXObject("WinIE2.winiehandle");
var myAx = new ActiveXObject("SRVSan.clsSRVSan");

myAx.embedWindow();
}
catch(err)
{
//alert("Allow Control");
}
  
}
</script>
</head>
<body>

<iframe id="frame"></iframe>

<input type="button" id="button1" value="Embed" onclick="myload()"/>
 

</body>
</html>

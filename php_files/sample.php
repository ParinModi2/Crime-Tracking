
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<?php
include ("db.php");
if(isset($_POST['submit1']))
	{
		$name=$_POST['name'];
		$pass=$_POST['pass']; 
		$passdb=mysql_query("select pass from adminlogin where name='$name' ");

		$row=mysql_fetch_array($passdb);
		if($row[0]==$pass)
		{
			header("location:adminpanel.php");
		}
		else
		{
			echo "Not there";
		}
}

?>


<head >
<link rel="stylesheet" type="text/css" href="samp2.css">
</head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<script language="javascript">
function validate_name()
{
	name=document.f1.name.value;
	if(name.length==0)
	document.getElementById("name").innerHTML="*Please enter your name";
	
}
function validate_pass()
{	pass=document.f1.pass.value;
	if(pass.length==0)
	document.getElementById("pass").innerHTML="*Please enter Password";
}
</script>
</head>
<body>
<div id="wrapper">
		<div id="strip">
	&nbsp;	
	&nbsp;
	<img src="crime_tracking_images/front_page/logo.png"/>
		</div>

		<div id="header">
		
		</div>
		<div style="margin:0;padding:0">
<div style="margin:0 auto;padding:0px;height:auto">
<form name=f1 method="post" enctype="multipart/form-data">

<table align="center">
<tr>
<td colspan="2" align="center">
<div id="menu">
Welcome To Admin Panel
</div>
</td>
</tr>
<tr>
	<td>
		<br>Enter your Admin Name:<br>
	</td>
	<td>
		<br><input type=text name=name onblur="validate_name()"><br>
	</td>
	<td><div id="name" style="color:red"></div></td>
</tr>
<tr>
	<td>
		<br>Enter your Admin Password:<br>
	</td>
	<td>
		<br><input type=password name=pass onblur="validate_pass()"><br>
	</td>
	<td><div id="pass" style="color:red"></div></td>

</tr>
<tr >
	<td colspan=2 align=center>
		<br><input type=submit name=submit1 value=Login><br>
		</td>
	
</tr>
<tr >
	<td colspan=2 align=center>
		<br><a href="forgotpass.php" >Forgot your password???</a><br>
		</td>

</tr>

</table>

</form>
</div>
</div>
	<div id="footer">
	</div>
</body>
</html>
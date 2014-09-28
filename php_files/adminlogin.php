<? ob_start(); ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head >
<link rel="stylesheet" type="text/css" href="samp2.css">
</head>
<head>
<script language="javascript">
var flag1=0,flag2=0;
function validate()
{
if(flag1==1 && flag2==1 )
{
	return true;
}
else
{
alert("Enter full details");	
return false;
	
}
}


function validate_name()
{
	email=document.f1.email.value;
	if(email.length==0)
	document.getElementById("email").innerHTML="*Please enter your name";
	else
	{
	flag1=1;
	document.getElementById("email").innerHTML="";
}		
}
function validate_pass()
{	pass=document.f1.pass.value;
	if(pass.length==0)
	document.getElementById("pass").innerHTML="*Please enter Password";
	else
{
	flag2=1;
	document.getElementById("pass").innerHTML="";
}
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
<div id="header"></div>
<div style="margin:0;padding:0">
<div style="margin:0 auto;padding:0px;height:auto">
<form name=f1 method="post" enctype="multipart/form-data" onsubmit="return validate()">
<table align="center">
<tr >
<td height="40" colspan="2" align="center" background="crime_tracking_images/front_page/normal_header.png">
	
	Welcome To Admin Panel</td>
</tr>
<tr>
	<td>
		<br>Enter your Admin Name:<br>
	</td>
	<td>
		<br><input type=text name=email value="<?php echo $_COOKIE['ckname'];?>" onblur="validate_name()"><br>
	</td>
	<td><div id="email" style="color:red"></div></td>
</tr>
<tr>
	<td>
		<br>Enter your Admin Password:<br>
	</td>
	<td>
		<br><input type=password name=pass value="<? echo $_COOKIE['ckpass'];?>" onblur="validate_pass()"><br>
	</td>
	<td><div id="pass" style="color:red"></div></td>

</tr>
<tr >
	<td colspan=2 align=center>
		<br><input type=submit name=submit1 value=Login><br>
		</td>
	
</tr>
<tr>
		  <td align="center" colspan="2"><br><input 	type="checkbox" name="check" /> REMEMBER ME</td>

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
	<div  id="footer">

	</div>
</body>
</html>

<?php
include ("db.php");
//include_once 'header.php';
//mysql_connect("localhost","root",'');
//mysql_select_db("training_artoon");

if(isset($_POST['submit1']))
	{
		$email=$_POST['email'];
		$pass=$_POST['pass'];
		
		 
		$passdb=mysql_query("select pass from adminlogin where email='$email' ");

		$row=mysql_fetch_array($passdb);
		if($row[0]==$pass)
		{
			session_start();
		
			$namedb=mysql_query("select name from adminlogin where email='$email' ");
			$namedb1=mysql_fetch_array($namedb);
			$_SESSION['adname']=$namedb1[0];
	
			//echo ($_SESSION['adname']);
			header("location:adminpanel.php");
		}
		else
		{
			echo "alert:The email does not exists" ;
		}
}
if(isset($_POST['check']))
{
	$email=$_POST['email'];
	$pass=$_POST['pass'];
	setcookie("ckname",$email,time()+3600*24*30);
	setcookie("ckpass",$pass,time()+3600*24*30);

	
	}
?>
<? ob_flush(); ?>
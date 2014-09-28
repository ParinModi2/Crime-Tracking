<?php ob_start();?>
<?php
session_start();
if(isset($_SESSION['adname']))
echo "Welcome ".$_SESSION['adname'];
else
{

	header("location:adminlogin.php");
	exit(0);
}
	include("db.php");
	if(isset($_POST['submit1']))
	{
		$name=$_POST['name'];
		$email=$_POST['email'];
		$pass=$_POST['pass']; 
		$eq="insert into adminlogin values('$name','$pass','$email')";
		mysql_query("insert into adminlogin values('$name','$pass','$email')");
		$to = $email;
		$subject = "This is subject";
   		$message = "<b>You are successfully registered to the GPRS Crime Tracking Application.
Your name for the login is </b>".$name."<br><b>Your password for the login is </b></br>".$pass."<br><b>Your login id for the login is </b></br>".$email;
  		$header = "From:CrimeTrackingApplication \r\n";
		$header .= "MIME-Version: 1.0\r\n";
  		 $header .= "Content-type: text/html\r\n";
  		$retval = mail ($to,$subject,$message,$header);
  		
	}
?>

<html>
<head >
<link rel="stylesheet" type="text/css" href="menu1.css">
<link rel="stylesheet" type="text/css" href="samp2.css">

</head>

<head>
<script language=javascript>
var flag1=0,flag2=0,flag3=0,flag4=0;
function validate()
{
	if(flag1==1 && flag2==1 && flag3==1 && flag4==1)
	{
		alert("Admin User successfully registered !!Email sent to the new admin users email id!!");
	return true;
	}
	else
	{
		alert("Please fill full details");
		return false;
		}
}
function validate_name()
{	
	name=document.f1.name.value;
	if(name.length==0)
	document.getElementById("name").innerHTML="*Please enter name";
	else
	{
		document.getElementById("name").innerHTML="";
		flag1=1;
	}
}
function validate_email()
{
	email=document.f1.email.value;
	if(email.length==0)
	document.getElementById("email").innerHTML="*Please enter email";
	else
	{
		document.getElementById("email").innerHTML="";
		flag2=1;
	}
}
function validate_pass()
{	pass=document.f1.pass.value;
	if(pass.length==0)
	document.getElementById("pass").innerHTML="*Please enter Password";
	else
	{
		document.getElementById("pass").innerHTML="";
		flag3=1;
	}
}
function validate_cpass()
{	cpass=document.f1.cpass.value;
	pass=document.f1.pass.value;
	if(pass!=cpass)
	document.getElementById("cpass").innerHTML="*Password and Confirm Password should be same..";

	if(cpass.length==0)
	document.getElementById("cpass").innerHTML="*Please enter Confirm Password";
	else
	{
		document.getElementById("cpass").innerHTML="";
		flag4=1;
	}
}
</script>
</head>
<body>

<center>
<nav>
	<ul>
		<li><a href="adminpanel.php">Home</a></li>
		<li><a href="#">Admin Controls</a>
	
			<ul>
				
				<li><a href="adminreg.php">Add new admin user</a></li>
				<li><a href="policereg.php">Register Police </a></li>
				<li><a href="updateadmin.php">Update Admin </a></li>
			</ul></li>
		<li><a href="#">Show Lists</a>
			<ul>
				<li><a href="listpolice.php">Police Users</a></li>
				<li><a href="adminlist.php">Admin Users </a></li>
				<li><a href="#">Application Users</a></li>
			</ul>
		</li>
		<li><a href="#">Events</a>
			<ul>
				<li><a href="maps/phpsqlajax_map_v3.html">Map</a></li>
				<li><a href="picnamedb.php">Images</a></li>
				<li><a href="videonamedb1.php">Video</a></li>	
				<li><a href="textdb.php">Text</a></li>
				<li><a href="ping.php">Ping</a></li>

			</ul>
		</li>
		<li><a href="#">Searching</a>
			<ul>
				<li><a href="#">Region</a></li>
				<li><a href="#">Month</a></li>
				<li><a href="#">Date</a></li>

			</ul>
		</li>
		
	</ul>

</nav>
</center>
<h2 align=center>
	Register Here as Admin....

</h2>


<center>
<form name=f1 method="post" enctype="multipart/form-data" onsubmit="return validate()">

<table>
<tr>
	<td>
		<br>Enter Name:<br>
	</td>
	<td>
		<br><input type=text name=name onblur="validate_name()" ><br>
	</td>	
	<td><div id=name style="color:red"></div></td>
</tr>
<tr>
	<td>
		<br>Enter Email Id:<br>
	</td>
	<td>
		<br><input type=text name=email onblur="validate_email()" ><br>
	</td>	
	<td><div id=email style="color:red"></div></td>
</tr>

<tr>
	<td>
		<br>Enter Admin Password:<br>
	</td>
	<td>
		<br><input type=password name=pass onblur="validate_pass()" ><br>
	</td>
		<td><div id=pass style="color:red"></div></td>

</tr>
<tr>
	<td>
		<br>Confirm Admin Password:<br>
	</td>
	<td>
		<br><input type=password name=cpass onblur="validate_cpass()"><br>
	</td>
	<td><div id=cpass style="color:red"></div></td>
</tr>
<tr >
	<td colspan=2 align=center>
		<br><input type=submit name=submit1 value=Register><br>
		</td>
	</center>
</tr>
</table>
</center>
</form>
</body>
</html>
<?php ob_flush();?>
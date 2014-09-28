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
?>
<html>
<head >
<link rel="stylesheet" type="text/css" href="menu1.css">
<link rel="stylesheet" type="text/css" href="samp2.css">
</head>
<head>
<script language="javascript">
var flag1=0,flag2=0,flag3=0,flag4=0;
function validate()
{	
	//alert("validate");
	if(flag1==1 && flag2==1 && flag3==1 && flag4==1)
	{

		alert("Successfully Updated");
	return true;
	}
	else
	{
		alert("Please fill full details");
		return false;
	}
}

function validate_cpass()
{	cpass=document.f1.cpass.value;
	npass=document.f1.npass.value;
	if(npass!=cpass)
	alert("Password and Confirm Password should be same..");
	if(cpass.length==0)
	{
	document.getElementById("cpass").innerHTML="*Please enter Confirm Password";
	//alert(flag1);
	}
	else
	{
		document.getElementById("cpass").innerHTML=" ";
		flag1=1;
	}

}
function validate_name()
{
	//alert("name");
	name=document.f1.name.value;
	if(name.length==0)
	{
	document.getElementById("name").innerHTML="*Please enter name";
	//alert(flag2);	
	}
	else
	{
		document.getElementById("name").innerHTML=" ";
		flag2=1;
	}

}
function validate_pass()
{	pass=document.f1.pass.value;
	if(pass.length==0)
	{
	document.getElementById("pass").innerHTML="*Please enter Password";
	//alert(flag3);
	}
	else
	{
		document.getElementById("pass").innerHTML=" ";
		flag3=1;
	}

}
function validate_npass()
{	
	npass=document.f1.npass.value;
	if(npass.length==0)
	{
	document.getElementById("npass").innerHTML="*Please enter Password";
	//	alert(flag4);
	}
	else
	{
		document.getElementById("npas").innerHTML=" ";
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
<form name=f1 method="post" enctype="multipart/form-data" onsubmit="return validate()">
<table align="center">
<tr>
<th>Change Password</th>
</tr>
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
		<br>Enter Password:<br>
	</td>
	<td>
		<br><input type=password name=pass onblur="validate_pass()" ><br>
	</td>	
	<td><div id=pass style="color:red"></div></td>
</tr>
<tr>
	<td>
		<br>Enter New Password:<br>
	</td>
	<td>
		<br><input type=password name=npass onblur="validate_npass()" ><br>
	</td>	
	<td><div id=npass style="color:red"></div></td>
</tr>
<tr>
	<td>
		<br>Confirm your new password:<br>
	</td>
	<td>
		<br><input type=text name=cpass onblur="validate_cpass()" ><br>
	</td>	
	<td><div id=cpass style="color:red"></div></td>
</tr>
<tr>
	<td colspan="2" align="center"><input type=submit name=s1 value="Change Password"></td>
</tr>
</table>
</form>
</body>
</html>
<?php
include("db.php");
if(isset($_POST['s1']))
{
	$name=$_POST['name'];
	$pass=$_POST['pass'];
	$npass=$_POST['npass'];
	$res=mysql_query("update adminlogin set pass='$npass' where  name='$name' and pass='$pass'");
		
//	echo "Updated";		
	
}
?>

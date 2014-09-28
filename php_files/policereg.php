<? ob_start(); ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head >
<link rel="stylesheet" type="text/css" href="menu1.css">
<link rel="stylesheet" type="text/css" href="samp2.css">

</head>

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
<?php
	include("db.php");
	if(isset($_POST['submit']))
	{
		//echo "in ISSEt";
		$fname=$_POST['fname'];
		$lname=$_POST['lname'];
		$email=$_POST['email'];
		$mno=$_POST['mno'];
		$add=$_POST['add'];
		$gender=$_POST['gender'];
//		$pic=$_FILES['pic']['name'];
		$qual=$_POST['qual'];
		$pass=$_POST['pass'];
//	echo "pass:".$pass;
		$result=mysql_query("insert into police values('$fname','$lname','$email','$mno','$add','0','$gender','0','$qual','0','0','0','0','0','$pass')");
		/*mysql_query("insert into police_location values('$fname','$email','0','0','$add','0')");*/
		$to = $email;
		$subject = "This is subject";
   		$message = "<b>You are successfully registered to the GPRS Crime Tracking Application.
Your login id for the login is </b></br>".$email."<br><b>Your password for the login is </b></br>".$pass;
  		$header = "From:CrimeTrackingApplication \r\n";
		$header .= "MIME-Version: 1.0\r\n";
  		 $header .= "Content-type: text/html\r\n";
  		$retval = mail ($to,$subject,$message,$header);
  		
	
	//$path="image/police".$pic;	
 	//move_uploaded_file($_FILES['pic']['tmp_name'],$path);
	
	}
?>
<html>

<head >
<link rel="stylesheet" type="text/css" href="menu1.css">
<link rel="stylesheet" type="text/css" href="samp2.css">

</head>

<head>
<script language=javascript>
var flag1=0,flag2=0,flag3=0,flag4=0,flag5=0,flag6=0;
var flag7=0,flag8=0;



function validate()
{
	//alert("Validate");
	if(flag1==1 && flag2==1 && flag3==1 && flag4==1 && flag5==1 && flag6==1 && flag7==1 && flag8==1)
	{ 
		
		alert("Police is successfully registered!An email is sent to the respective police");
		return true;
	
	}
	else
	{
		alert("Fill Full details");
		return false;
	}

}
function validate_fname()
{
	
	var name=document.f1.fname.value;
	if(name.length==0)
	
	document.getElementById("fname").innerHTML="*Please enter your first name";
	else
	{
		flag1=1;
		document.getElementById("fname").innerHTML="";
	}
	//alert(flag1);
}
function validate_lname()
{
	var name=document.f1.lname.value;
	if(name.length==0)
	document.getElementById("lname").innerHTML="*Please enter your last name";
	else
	{
	flag2=1;
	document.getElementById("lname").innerHTML="";
	}
//alert(flag2);
}

function validate_email()
{
	var email=document.f1.email.value;
	if(email.length==0)
	document.getElementById("email").innerHTML="*Please enter your email";
	else
	{
		document.getElementById("email").innerHTML="";
		flag3=1;
	}
//alert(flag3);
}
function validate_add()
{

            var add=document.f1.add.value;
            if(add.length==0)
            {
		document.getElementById("add").innerHTML="* Please Enter Address";
            }
            else
		{	
                document.getElementById("add").innerHTML="";
			flag4=1;
		}
//	alert(flag4);
}
function validate_qual()
{
	var qual=document.f1.qual.value;
	if(qual.length==0)
	document.getElementById("qual").innerHTML="*Please enter your qualification";
	else
	{
		document.getElementById("qual").innerHTML="";
		flag5=1;
	}
//alert(flag5);
}
function validate_no()
{
            var mno=document.f1.mno.value;

            if(mno.length==0)
            {
		document.getElementById("mno1").innerHTML="* Please Enter 10 Digit Phone No";
	    }
	    else
		{
			document.getElementById("mno1").innerHTML="";

			flag6=1;       	
                if(!isNaN(mno))
                {
                    if (mno.length!=10)
                    {
			document.getElementById("mno1").innerHTML="* Please Enter  Valid 10 Digit No";
				flag6=0;
                    }
                }
                else if(isNaN(mno))
                {
                    document.getElementById("mno1").innerHTML="* Please Enter Digits";
				flag6=0;
                }
	    	else
		{
                    document.getElementById("mno1").innerHTML="";
			flag6=1;
		}
             }
//	alert(flag6);
}

function validate_pass()
{
	var pass=document.f1.pass.value;
	if(pass.length==0)
	document.getElementById("pass1").innerHTML="*Please enter your password";
	else
	{
	document.getElementById("pass1").innerHTML="";
	flag7=1;
	}
//	alert(flag7);
}
function validate_cpass()
{	
	cpass=document.f1.cpass.value;
	pass=document.f1.pass.value;
	if(pass!=cpass)
	document.getElementById("cpass").innerHTML="*Password and Confirm Password should be same..";
	if(cpass.length==0)
	document.getElementById("cpass").innerHTML="*Please enter Confirm Password";
	else
	{
		document.getElementById("cpass").innerHTML="";
		flag8=1;

	}
//alert(flag8);

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
<center>
<h2>Police Registration....</h2>
<form method="post" name=f1 enctype="multipart/form-data" onsubmit="return validate()">

<table>
<tr>
	<td height=50 width=250>First Name:</td>
	<td height=50 width=250><input type=text name="fname" onblur="validate_fname()"></td>
	<td><div id="fname" style="color:red"></div></td>

</tr>
<tr>
	<td height=50 width=250>Last Name:</td>
	<td height=50 width=250><input type=text name="lname" onblur="validate_lname()"></td>
	<td><div id=lname style="color:red"></div></td>

</tr>
<tr>
	<td height=50 width=250>Email:</td>
	<td height=50 width=250><input type=text name="email" onblur="validate_email()"></td>
	<td ><div id=email style="color:red"></div></td>

</tr>
<tr>
	<td height=50 width=250>Mobile Number:</td>
	<td height=50 width=250><input type=text name="mno" onblur="validate_no()"></td>
	<td><div id=mno1 style="color:red"></div></td>

</tr>
<tr>
	<td height=50 width=250>Address:</td>
	<td height=50 width=250><textarea name=add onblur="validate_add()"></textarea></td>
	<td><div id=add style="color:red"></div></td>

</tr>
<tr>
	<td height=50 width=250>Gender:</td>
	<td height=50 width=250>
	<input type="radio" name="gender" value="male" /> Male<br />
	<input type="radio" name="gender" value="female" /> Female<br />
	</td>
</tr>

<tr>
	<td>Qualification:</td>
	<td><input type=text name="qual" onblur="validate_qual()"</td>
	<td><div id=qual style="color:red"></div></td>

</tr>
<tr>
	<td height=50 width=250>Password:</td>
	<td height=50 width=250><input type=password name="pass" onblur="validate_pass()"></td>
	<td><div id=pass1 style="color:red"></div></td>

</tr>
<tr>
	<td> Confirm Password:</td>
	<td><input type=password name="cpass" onblur="validate_cpass()"></td>
	<td><div id=cpass style="color:red"></div></td>

</tr>

<tr>
	
	<td colspan=2 align=center height=50 width=250><input type=submit name="submit" value="register"></td>
</tr>

</table>
</center>

</form>
</body>
</html>
<?php ob_flush()?>

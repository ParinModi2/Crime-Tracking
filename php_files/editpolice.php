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
$email1=$_GET['email1'];
//echo $email1;
$row=mysql_query("select fname,lname,mno,address,qual from police where email ='$email1'");
$col=mysql_fetch_array($row);

?>
<html>
<head >
<link rel="stylesheet" type="text/css" href="menu1.css">
<link rel="stylesheet" type="text/css" href="samp2.css">

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

<form name=f1 method=post>
<center>
<table border="1">
<tr>
	<th colspan="2">Police Profile</th>
	</tr>
	<tr>
		<td > First Name:</td>
		<td> <input type="text" name="fname" value=<?php echo "$col[0]"?>></td>
	</tr>
	<tr>
		<td > Last Name:</td>

		<td> <input type="text" name="lname" value=<?php echo "$col[1]"?>></td>

		
	</tr>
	<tr>
		<td > Contact no:</td>
		<td> <input type="text" name="mno" value=<?php echo "$col[2]"?>></td>
	</tr>
	<tr>
		<td > Address:</td>
		<td> <input type="text" name="add" value=<?php echo "$col[3]"?>></td>
	</tr>
	<tr>
		<td > Qualification:</td>
		<td> <input type="text" name="qual" value=<?php echo "$col[4]"?>></td>		
	</tr>
	<tr>
		<td colspan="2" align="center"> <input type="submit" name="s1" value="Edit profile"></td>		
	</tr>

	</form>
	
</table>
</center>

</form>
</body>
</html>
<?php
if(isset($_POST['s1']))
		{
		
			$email1=$_GET['email1'];
			$fname=$_POST['fname'];
			$lname=$_POST['lname'];
			$mno=$_POST['mno'];
			$add=$_POST['add'];
			$qual=$_POST['qual'];
		
			
			mysql_query("update police set fname='$fname',lname='$lname',mno='$mno',address='$add',qual='$qual' where email='$email1' ");
			header("location:editpolice.php");
			echo "<center>";
			echo "Police Profile has been edited";
			echo "</center>";
}		
?>
<?php ob_flush();?>

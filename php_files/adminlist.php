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
<h2>Admin Users</h2></center>
<?php
include("db.php");
$result=mysql_query("select name,email from adminlogin");
echo "<form name=f1 method=post>";
echo "<table align=center border=1>";
echo "<tr>";
echo "<th>Name</th>";
echo "<th>Email Id</th>";
echo "<th>Delete</th>";
echo "</tr>";
$i=0;	
while ($row=mysql_fetch_row($result) ) 
{
	$i++; 
 	echo "<tr>";
	echo "<td height=50 width=150 >";
	echo $row[0];
	echo "</td>"; 
     	echo "<td height=50 width=250 >";
	echo $row[1];
	echo "</td>"; 
	echo "<td>";
	echo "<input type=submit value= Delete name=".$i.">";
	if(isset($_POST[$i]))
	{
		echo "in isset";
		mysql_query("delete from adminlogin where email='".$row[1]."' ");
		header("location:adminlist.php");
	}
	echo "</td>";
	echo "</tr>";


	
}
echo "</table>";
echo "</form>";
?>
</body>
</html>
<?php ob_flush();?>

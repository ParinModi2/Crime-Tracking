<head >
<link rel="stylesheet" type="text/css" href="menu1.css">
<link rel="stylesheet" type="text/css" href="samp2.css">

</head>


<html>
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
<h2>Application Users</h2></center>
<?php
include("db.php");
$result=mysql_query("select * from userinfo");
echo "<center>";
echo "<table border=1>";
echo "<tr>";
echo "<th> User Name </th>";
echo "<th> Email Id </th>";
echo "<th> Contact No</th>";
echo "<th> Details</th>";
echo "</tr>";

while($r=mysql_fetch_row($result))
{
	echo "<tr>";
	echo "<td height=50 width=100 > $r[0] </td>";
	echo "<td height=50 width=100 > $r[2] </td>";
	echo "<td height=50 width=100 > $r[5] </td>";
	echo"<td  height=90 width=200 align=center> <a href='user_details.php?email=$r[2]'><img src='http://training.artoonsolutions.com/crimetracking/crime_tracking_images/front_page/view_full.png'/></a></td>";	
		
}


?>
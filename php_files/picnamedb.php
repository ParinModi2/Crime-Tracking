<?php ob_start()?>

<html>

<head>
<link rel="stylesheet" type="text/css" href="menu1.css">
<link rel="stylesheet" type="text/css" href="samp2.css">

<script src="jquery.min.js"></script>
<script type="text/javascript">
var auto_refresh = setInterval(
function()
{
$('#block1').load('#block1');
}, 3000);
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
				<li><a href="listuser.php">Application Users</a></li>
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
 
<div id="block1">
<form method=post name=f1 enctype=multipart/form-data>

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

	echo "<table border=1 align=center>"; echo "<tr>";
	echo "<th>Image</th>";
	echo "<th>User Email Id</th>";	
	echo "<th>Details</th>";
	
	$row=mysql_query("select img,email from events ORDER BY date DESC");
	while($r=mysql_fetch_row($row))
	{
		if($r[0]!=' 0 ' && $r[0]!=null)
		{
		echo"<tr>";
		echo"<td> <img src='image/$r[0]' height=200 width=200></td>";
		
		echo"<td  height=90 width=200 align=center> $r[1]</td>";
 		echo"<td  height=90 width=200 align=center> <a href='full_details.php?img=$r[0]'><img src='http://training.artoonsolutions.com/crimetracking/crime_tracking_images/front_page/view_full.png'/></a></td>";	
		echo"</tr>";
		}
	}	
	echo "</table>";


?>
</form>
</div>
</body>
</html>
<?php ob_flush()?>

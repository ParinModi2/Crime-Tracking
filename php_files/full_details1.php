<?php ob_start()?>
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

	$video=$_GET['vdo'];
	$result=mysql_query("select email,text,address,date,time
,lat,lng from events where vdo='".$video."'"); 	

	$r=mysql_fetch_row($result);
	$email=$r[0];
	$result1=mysql_query("select fname,mno from userinfo where email='".$email."'");
	$r1=mysql_fetch_row($result1);

?>

<!DOCTYPE html>
<head >
<link rel="stylesheet" type="text/css" href="menu1.css">
<link rel="stylesheet" type="text/css" href="samp2.css">

</head>

<head>
<script
src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM&sensor=false">
</script>

<script>

function initialize()
{
	var lat=document.getElementById("lat").value;
var lng=document.getElementById("lng").value;
//alert("at");
var myCenter=new google.maps.LatLng(lat,lng);

var mapProp = {
  center: myCenter,
  zoom:14,
  mapTypeId: google.maps.MapTypeId.ROADMAP
  };

var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);

var marker = new google.maps.Marker({
  position: myCenter,
  title:'Click to Zoom'
  });

marker.setMap(map);

// Zoom to 9 when clicking on marker
google.maps.event.addListener(marker,'click',function() {
  map.setZoom(9);
  map.setCenter(marker.getPosition());
  });
}
google.maps.event.addDomListener(window, 'load', initialize);
</script>
</head>

<body >
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
<table height=200 width=400 border=1>
<tr>
<th 
colspan=2
height=40
background="http://training.artoonsolutions.com/crimetracking/crime_tracking_images/events_page/normal_header.png"
align=center>
Full Details
</th>
</tr>
<tr>

	<td id=sid><br><b>Sender Name :</b><? if($r1[0]==0) echo "------"; else echo " ".$r1[0]; ?> 
<td rowspan=3
align=center>
<video align=center height=200 width=200 controls>
 <source src="video/<? echo $video?>" type='video/mp4'>
 </video>
	</td>	
</tr>
<tr>
	<td id=email>
<br>
<b>Email Id :</b><? echo " ".$email; ?></td>


</tr>
<tr>
	<td id=cno>
<br>
<b>Contact No. :</b><? if($r1[1]==0) echo "------"; else echo " ".$r1[1]; ?></td>
</tr>
<tr>
	<td colspan=2>
<br>
<b>Location of Crime :</b><?  echo " ".$r[2]; ?></td>
</tr>
<tr>
	<td colspan=2 id=msg>
<br>
<b>Message :</b><?   if($r[1]==null) echo "------"; else echo " ".$r[1]; ?></td>
</tr>
<tr  >
	<td colspan=2 id=dtntm>
<br>
<b>Date :</b><?  echo " ".$r[3]; ?> &nbsp;&nbsp;
<b>Time :</b><?  echo " ".$r[4]; ?></td>
</td>
</tr>
<tr>
	<td colspan=2 id=dtntm>
<br>
<b>Location of Crime :</b>
	</td>
</tr>
<tr>
	<td colspan=2  >
<div id="googleMap" style="width:400px;height:200px;"></div>
</td>
</tr>
<tr>
	<td colspan=2  >
<a href="full_view_map.php?lat=<?echo urlencode($r[5]);?>&lng=<?echo urlencode($r[6]);?>">
<img src="http://training.artoonsolutions.com/crimetracking/crime_tracking_images/events_page/fullview_btn.png">
</a>

</td>
</tr>

<input type="hidden" id="lat" value=<? echo $r[5];?>>
<input type="hidden" id="lng" value=<? echo $r[6];?>>




</table>
</center>
</body>
</form>
</html>



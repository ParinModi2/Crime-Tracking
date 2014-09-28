<?php
	include("db.php");

	$image=$_GET['img'];
	$result=mysql_query("select email,text,address,date,time
,lat,lng from events where img='".$image."'"); 	

	$r=mysql_fetch_row($result);
	$email=$r[0];
	$result1=mysql_query("select fname,mno from userinfo where email='".$email."'");
	$r1=mysql_fetch_row($result1);

?>

<!DOCTYPE html>
<html>
<head>
<script
src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM&sensor=false">
</script>

<script>

function initialize()
{
	var lat=document.getElementById("lat").value;
var lng=document.getElementById("lng").value;
alert("at");
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
align=center><img 
align=center
height=200 width=200 src="image/<? echo $image?>"></img>
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
<input type="hidden" id="lat" value=<? echo $r[5];?>>
<input type="hidden" id="lng" value=<? echo $r[6];?>>




</table>
</center>
</body>
</form>
</html>

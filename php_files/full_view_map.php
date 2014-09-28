<?php
//	$image=$_GET['img'];

$lat1=$_GET['lat'];
$lng1=$_GET['lng'];
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

<body>
<center>
<div id="googleMap" style="width:1000px;height:600px;"></div>
<input type="hidden" id="lat" value=<? echo $lat1;?>>
<input type="hidden" id="lng" value=<? echo $lng1;?>>
</center>
</body>
</html>
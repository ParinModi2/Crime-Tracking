<? ob_start(); ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>

    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta name="robots" content="noindex,follow" />

    <title>Calculate driving distance with Google Maps API</title>


    <script src="http://maps.google.com/maps?file=api&v=2&key=AIzaSyBCJYaOX1k0hsI_MNwxUT2EEuI8JnK2eYI" type="text/javascript"></script>
    
<script type="text/javascript">
    var geocoder, location1, location2, gDir;



   function initialize(var1) {
	alert("in java");	
        geocoder = new GClientGeocoder();
        gDir = new GDirections();
        GEvent.addListener(gDir, "load", function() {
            var drivingDistanceMiles = gDir.getDistance().meters / 1609.344;
            var drivingDistanceKilometers = gDir.getDistance().meters / 1000;
        alert("km:"+ drivingDistanceKilometers);
diff="diff"+var1;

/*document.getElementById(diff).value = drivingDistanceKilometers;
*/ 

var xmlhttp;
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","http://training.artoonsolutions.com/crimetracking/gcm_server_php/send_message.php",true);
xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
xmlhttp.send("coo="+drivingDistanceKilometers+"&i="+var1);
        });
  showLocation(var1);
    }


    function showLocation(i) {

id1="add1"+i;
alert("id1:"+id1);
alert("Value of address1:"+document.getElementById("add1"+i).value);
        geocoder.getLocations(document.getElementById("add1"+i).value, function (response) {
            if (!response || response.Status.code != 200)
            {
	
                /*alert("Sorry, we were unable to geocode the first address");*/
            }
            else
            {
                location1 = {lat: response.Placemark[0].Point.coordinates[1], lon: response.Placemark[0].Point.coordinates[0], address: response.Placemark[0].address};
                geocoder.getLocations(document.getElementById("add2").value, function (response) {
                    if (!response || response.Status.code != 200)
                    {
                        /*alert("Sorry, we were unable to geocode the second address");*/
                    }
                    else
                    {
                        location2 = {lat: response.Placemark[0].Point.coordinates[1], lon: response.Placemark[0].Point.coordinates[0], address: response.Placemark[0].address};
                        gDir.load('from: ' + location1.address + ' to: ' + location2.address);
                    }
                });
            }
        });
    }

</script>
</head>
<body>

<form name=f1 method="post" >
<div id='myDiv'>
</div>
<?php
	include("db.php");
	$uemail=$_COOKIE['uemail'];
	$address2=$_COOKIE['address2'];
	$result=mysql_query("select * from police");
	echo '<input type=text name=add2 id=add2 value="'.$address2.'" >';
	$i=0;
	while($r=mysql_fetch_row($result))
	{
		$address1=$r[5];
	
	echo '<input type=text id=add1'.$i.' name=add1'.$i.' value="'.$address1.'" >';

	

	echo '<input type=text id=diff'.$i.' name=diff'.$i.'>';

echo '<script type="text/javascript">
initialize('.$i.');</script>';
$i++;

	}
?>
</form>
</body>
</html>
<? ob_flush(); ?>


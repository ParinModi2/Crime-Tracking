
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta name="robots" content="noindex,follow" />
    <title>Calculate driving distance with Google Maps API</title>
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>

    <script src="http://maps.google.com/maps?file=api&v=2&key=ABQIAAAA7j_Q-rshuWkc8HyFI4V2HxQYPm-xtd00hTQOC0OXpAMO40FHAxT29dNBGfxqMPq5zwdeiDSHEPL89A" type="text/javascript"></script>
    <!-- According to the Google Maps API Terms of Service you are required display a Google map when using the Google Maps API. see: http://code.google.com/apis/maps/terms.html -->
    <script type="text/javascript">

var geocoder, location1, location2, gDir, dist;

function initialize() {
    geocoder = new GClientGeocoder();
    gDir = new GDirections();
    GEvent.addListener(gDir, "load", function() {
        var drivingDistanceMiles = gDir.getDistance().meters / 1609.344;
        var drivingDistanceKilometers = gDir.getDistance().meters / 1000;
        document.getElementById('results').innerHTML = '<strong>Address 1: </strong>' + location1.address + ' (' + location1.lat + ':' + location1.lon + ')<br /><strong>Address 2: </strong>' + location2.address + ' (' + location2.lat + ':' + location2.lon + ')<br /><strong>Driving Distance: </strong>' + drivingDistanceMiles + ' miles (or ' + drivingDistanceKilometers + ' kilometers)';
		dist  = drivingDistanceKilometers;
    });
}

function showLocation() {
    geocoder.getLocations(document.forms[0].address1.value, function (response) {
        if (!response || response.Status.code != 200)
        {
            alert("Sorry, we were unable to geocode the first address");
        }
        else
        {
            location1 = {lat: response.Placemark[0].Point.coordinates[1], lon: response.Placemark[0].Point.coordinates[0], address: response.Placemark[0].address};
            geocoder.getLocations(document.forms[0].address2.value, function (response) {
                if (!response || response.Status.code != 200)
                {
                    alert("Sorry, we were unable to geocode the second address");
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

sendMsg(1);
function sendMsg(var1){

	$.ajax({
		 url : 'http://training.artoonsolutions.com/crimetracking/gcm_server_php/send_message.php',
		 method : 'POST',
		 data : {i : var1, coo: dist},
		 dataType:"json",
		 success : function(data){
			 alert('aa');
			 // showLocation(var1);
		  }});
}

   </script>
</head>

<body onload="initialize()">

    <form action="#" onsubmit="showLocation(); return false;">
        <p>
            <input type="text" name="address1" value="Address 1" />
            <input type="text" name="address2" value="Address 2" />
            <input type="submit" value="Search" />
        </p>
    </form>
    <p id="results"></p>

</body>
</html>

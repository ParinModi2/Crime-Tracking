<?php
require('db.php');
session_start();
if(isset($_SESSION['adname']))
echo "Welcome ".$_SESSION['adname'];
else
{

	header("location:adminlogin.php");
	exit(0);
}
/**
* Preparing and getting response for latest feed items.
**/
if(isset($_POST['latest_news_time'])){
	$sql = "SELECT * FROM news WHERE date1 > '".$_POST['latest_news_time']."' ORDER BY date1 DESC";
	$resource = mysql_query($sql);
	$current_time = $_POST['latest_news_time'];
	$item = mysql_fetch_assoc($resource);
	$last_news_time = $item['date1'];
	while ($last_news_time < $current_time) {
		usleep(1000); //giving some rest to CPU
		$resource = mysql_query($sql);
		$item = mysql_fetch_assoc($resource);
		$last_news_time = $item['date1'];
	}
	?>
	<li id="<?php echo $item['date1'] ?>">
		<span class="feedtext"><?php echo "<b>Crime occured at:</b> <br>".$item['address']." on <b>Date </b> ".$item['date1']."<b><br> Text alert: </b>".$item['text'] ?></span>

	</li>
	<?php
	exit;
}
/**
* Getting news Items and preparing sql query with respect to request
**/
$sql = "SELECT * FROM news ORDER BY date1 DESC LIMIT 0, 10";
if(isset($_POST['last_time'])){
	$sql = "SELECT * FROM news WHERE date1 < '".$_POST['last_time']."' ORDER BY date1 DESC LIMIT 0, 10";
}
$resource = mysql_query($sql);
$news = array();
while($row = mysql_fetch_assoc($resource)){
	$news[] = $row;
}

?>
<html>
<head>
<link rel="stylesheet" type="text/css" href="menu1.css">
<link rel="stylesheet" type="text/css" href="samp2.css">

<title>:: News Feed ::</title>

    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <title>PHP/MySQL & Google Maps Example</title>
    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
  
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js" type="text/javascript"></script>
	<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js" type="text/javascript"></script>
	<script src="js/scroll-pagination.js" type="text/javascript"></script>
	<script src="js/slimScroll.js" type="text/javascript"></script>
	<link href="styles.css" rel="stylesheet" type="text/css" />
</head>
<script type="text/javascript">
$(function(){
	/**
	* Integrating slim scroll
	**/
	$("#feeds ul").slimScroll({
        height: '520px'
    });
	/**
	* Integrating Scroll Pagination
	**/
	var feeds = $("#feeds ul");
	var last_time = feeds.children().last().attr('id');
    feeds.scrollFeedPagination({
        'contentPage': 'phpsqlajax_map_v3.php',
        'contentData': {
            'last_time' : last_time
        },
        'scrollTarget': feeds, 
        'beforeLoad': function(){
            feeds.parents('#feeds').find('.loading').fadeIn();
        },
        'afterLoad': function(elementsLoaded){
            last_time = feeds.children().last().attr('id');
            feeds.scrollFeedPagination.defaults.contentData.last_time = last_time;
            feeds.parents('#feeds').find('.loading').fadeOut();
            var i = 1;
            $(elementsLoaded).fadeInWithDelay();
        }
    });
    $.fn.fadeInWithDelay = function(){
        var delay = 0;
        return this.each(function(){
            $(this).delay(delay).animate({
                opacity:1
            }, 200);
            delay += 100;
        });
    };
	//calling the function to update news feed
    setTimeout('updateFeed()', 1000);
});
/**
* Function to update the news feed
**/
function updateFeed(){
		var id = 0;
		id = $('#feeds li :first').attr('id');
        $.ajax({
            'url' : 'phpsqlajax_map_v3.php',
            'type' : 'POST',
            'data' : {
                'latest_news_time' : id  
            },
            success : function(data){
				setTimeout('updateFeed()', 1000);
				if(id != 0){
                	$(data).prependTo("#feeds ul");
				}
            }
        }) 
	}

function load() {
      var map = new google.maps.Map(document.getElementById("map"), {
        center: new google.maps.LatLng(21.195000, 72.819400),
        zoom: 13,
        mapTypeId: 'roadmap'
      });
      var infoWindow = new google.maps.InfoWindow;

      // Change this depending on the name of your PHP file
      downloadUrl("phpsqlajax_genxml2.php", function(data) {
        var xml = data.responseXML;
        var markers = xml.documentElement.getElementsByTagName("marker");

        for (var i = 0; i < markers.length; i++) {
		 var type = markers[i].getAttribute("type");
  
	if(type=='user')
	   {
          var email = markers[i].getAttribute("email");
          var text = markers[i].getAttribute("text");
          var img = markers[i].getAttribute("img");
          var point = new google.maps.LatLng(
              parseFloat(markers[i].getAttribute("lat")),
              parseFloat(markers[i].getAttribute("lng")));
          var html = "<b>" + email + "</b> <br/>" + text+"<br><img height=100 width=100 src="+"http://training.artoonsolutions.com/crimetracking/image/"+img+" alt='There is no image'></img>";

		var marker = new google.maps.Marker({
            map: map,
           position: point,
           icon:'police_copy.png',
        	shadow: 'http://labs.google.com/ridefinder/images/mm_20_shadow.png'
     
          });
	   }
	else
	{ 
		 var email = markers[i].getAttribute("email");
		 var point = new google.maps.LatLng(
              parseFloat(markers[i].getAttribute("lat")),
              parseFloat(markers[i].getAttribute("lng")));
          var html = "<b>" + email + "</b> <br/>";

		 var marker = new google.maps.Marker({
            map: map,
            position: point,
            icon:'user_copy.png',
          	 shadow: 'http://labs.google.com/ridefinder/images/mm_20_shadow.png'
          });
	}
         bindInfoWindow(marker, map, infoWindow, html);
        }
      });
    }

    function bindInfoWindow(marker, map, infoWindow, html) {
      google.maps.event.addListener(marker, 'click', function() {
        infoWindow.setContent(html);
        infoWindow.open(map, marker);
      });
    }

    function downloadUrl(url, callback) {
      var request = window.ActiveXObject ?
          new ActiveXObject('Microsoft.XMLHTTP') :
          new XMLHttpRequest;

      request.onreadystatechange = function() {
        if (request.readyState == 4) {
          request.onreadystatechange = doNothing;
          callback(request, request.status);
        }
      };

      request.open('GET', url, true);
      request.send(null);
    }

    function doNothing() {}

    //]]>
  self.setInterval ("load()", 45000);


</script>
<body onload="load()">
	
<center>
<nav>
	<ul>
		<li><a href="#">Home</a></li>
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
<table>
<tr>
<td>
<div class="main_container" >
		<div class="feeds_container" >

			<h3 class="feed_head">News Feed</h3>
			<div id="feeds" class="feeds">
				<ul>
					<?php foreach($news as $item): ?>
					<li id="<?php echo $item['date1'] ?>">
						<span class="feedtext"><?php echo "<b>Crime occured at:</b> <br>".$item['address']." on <b>Date </b> ".$item['date1']."<b><br> Text alert: </b>".$item['text'] ?></span>

					</li>
					<?php endforeach; ?>
				</ul>&nbsp&nbsp&nbsp&nbsp
				<div class="loading">
					<img src="images/loading_transparent.gif"  alt=""/>
				</div>
			</div>
		</div>
		<div class="form">

			<form action="" id="add-news-form" method="post">
				
		</div><br><br style="clear: both">
	</div>
</td>
<td>
    <div id="map" style="z-index:-1; width:850px; height: 600px" ></div>
</td>
</tr>
</table>
</body>
</html>



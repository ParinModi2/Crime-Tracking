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
        'contentPage': 'adminpanel.php',
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
            'url' : 'adminpanel.php',
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
</script>
<body>
	
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
<td height=100px width=100px>
<div >
Are you near any crime spot?Have you seen any crime happening in front of you?Well,Gprs Crime Tracking App is just one tap away to prompt the police officials about the crime on the spot.

The user can notify the police officials about the crime happening on others as well as on him through the android smartphone. For the crime happening on others, the user can raise an alert either by sending the image , video or text to the police. For the crime happening on him, there is an option of ping button through which the current location of the user will be sent to the police. The registered police officials that will be in the range of 5 km from the crime location will get the alert in the form of push notification. The police can either accept the alert or reject the alert. On accepting the alert the police will get the driving directions from the current location of the police to the crime spot. The police can then send a feedback about the status of crime whether it was solved or unsolved.


</div>
</td>
</tr>
</table>

</body>
</html>


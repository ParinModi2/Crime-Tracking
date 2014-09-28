
<?php
require('db.php');
/*session_start();
if(isset($_SESSION['adname']))
echo "Welcome ".$_SESSION['adname'];
else
{

	header("location:adminlogin.php");
	exit(0);
}*/
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
<?
include("db.php");
$date1=$_POST['datepicker1'];
$date2=$_POST['datepicker2'];
//echo "<tr>";
//echo "<td> ";
echo "a1".$date1;
echo "d2".$date2;


if(isset($_POST['s1']))
{
	$result=mysql_query("select * from events where date between $date1 and $date2");
	
echo "a1".$date1;
echo "d2".$date2;

$re=mysql_fetch_row($result);
print_r($re);




}
?>
<html>
<head>
<link rel="stylesheet" type="text/css" href="menu1.css">
<link rel="stylesheet" type="text/css" href="samp2.css">
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css" />

	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js" type="text/javascript"></script>
	<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js" type="text/javascript"></script>
	<script src="js/scroll-pagination.js" type="text/javascript"></script>
	<script src="js/slimScroll.js" type="text/javascript"></script>
	<link href="styles.css" rel="stylesheet" type="text/css" />
</head>
<script type="text/javascript">
$(function() {
$( "#datepicker1" ).datepicker();
});
$(function() {
$( "#datepicker2" ).datepicker();
});

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
<div aligm=right>
<p>Date: <input type="text" id="datepicker1" /> to 
Date: <input type="text" id="datepicker2" /> 
<input type=submit value=Search name=s1></p>
</div>


</body>
</html>















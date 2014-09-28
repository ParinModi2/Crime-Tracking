<?php
function parseToXML($htmlStr) 
{ 
$xmlStr=str_replace('<','&lt;',$htmlStr); 
$xmlStr=str_replace('>','&gt;',$xmlStr); 
$xmlStr=str_replace('"','&quot;',$xmlStr); 
$xmlStr=str_replace("'",'&#39;',$xmlStr); 
$xmlStr=str_replace("&",'&amp;',$xmlStr); 
return $xmlStr; 
} 


$connection=mysql_connect("mysql.artoonsolutions.com","trainingartoon","artoontraining");
if (!$connection) {
  die('Not connected : ' . mysql_error());
}

$db_selected = 	mysql_select_db('training_artoon',$connection);

$event_id = mysql_query("SELECT email,text,lat,lng,img,event_id FROM events ORDER BY event_id DESC LIMIT 1");

$event_id1=$event_id;
$e_id=mysql_fetch_row($event_id);

$pemail= mysql_query("SELECT distinct pemail FROM temp_diff where inaction='1' and id='".$e_id[5]."'");


header("Content-type: text/xml");
echo '<markers>';


while($pemail1=mysql_fetch_row($pemail))
{
	
$lat_lng_rec = mysql_query("SELECT lat,lng FROM police where email='".$pemail1[0]."' ");

$lat_lng=mysql_fetch_row($lat_lng_rec);

  echo '<marker ';
  echo 'email="' . $pemail1[0] . '" ';
  echo 'lat="' . $lat_lng[0] . '" ';
  echo 'lng="' . $lat_lng[1] . '" ';
  echo 'type="police" ';
 echo '/>';
	
}

$event_id = mysql_query("SELECT email,text,lat,lng,img,event_id FROM events ORDER BY event_id DESC LIMIT 1");

while ($row = @mysql_fetch_assoc($event_id)){
  // ADD TO XML DOCUMENT NODE
  echo '<marker ';
  echo 'case_id="' . $row['event_id'] . '" ';
  echo 'email="' . $row['email'] . '" ';
  echo 'img="' . $row['img'] . '" ';
  echo 'lat="' . $row['lat'] . '" ';
  echo 'lng="' . $row['lng'] . '" ';
  echo 'text="' . $row['text'] . '" ';
 echo 'type="user" ';
 echo '/>';
}
echo '</markers> ';

?>



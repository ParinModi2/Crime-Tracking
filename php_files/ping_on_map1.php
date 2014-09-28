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

$db_selected =mysql_select_db('training_artoon',$connection);

$event_id = mysql_query("SELECT email,text,lat,lng,img,event_id FROM events ORDER BY event_id DESC LIMIT 1");

header("Content-type: text/xml");
echo '<markers>';

while ($e_id1=mysql_fetch_row($event_id)){
  // ADD TO XML DOCUMENT NODE
  echo '<marker ';
  echo 'case_id="' . $e_id1[5] . '" ';
  echo 'email="' . $e_id1[0] . '" ';
  echo 'img="' . $e_id1[4] . '" ';
  echo 'lat="' . $e_id1[2] . '" ';
  echo 'lng="' . $e_id1[3] . '" ';
  echo 'text="' . $e_id1[6] . '" ';
 echo 'type="user" ';
 echo '/>';

}

echo '</markers> ';

?>
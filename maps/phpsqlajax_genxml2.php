<?php
//require("phpsqlajax_dbinfo.php");

function parseToXML($htmlStr) 
{ 
$xmlStr=str_replace('<','&lt;',$htmlStr); 
$xmlStr=str_replace('>','&gt;',$xmlStr); 
$xmlStr=str_replace('"','&quot;',$xmlStr); 
$xmlStr=str_replace("'",'&#39;',$xmlStr); 
$xmlStr=str_replace("&",'&amp;',$xmlStr); 
return $xmlStr; 
} 

// Opens a connection to a MySQL server
$connection=mysql_connect("mysql.artoonsolutions.com","trainingartoon","artoontraining");
if (!$connection) {
  die('Not connected : ' . mysql_error());
}

// Set the active MySQL database
$db_selected = 	mysql_select_db('training_artoon',$connection);
if (!$db_selected) {
  die ('Can\'t use db : ' . mysql_error());
}

// Select all the rows in the markers table
$query = "SELECT email,text,lat,lng,img FROM events";
$result = mysql_query($query);
if (!$result) {
  die('Invalid query: ' . mysql_error());
}

// Select all the rows in the markers table
$query = "SELECT email,lat,lng FROM police";
$result1= mysql_query($query);
if (!$result1) {
  die('Invalid query: ' . mysql_error());
}


header("Content-type: text/xml");

// Start XML file, echo parent node
echo '<markers>';

while ($row1 = @mysql_fetch_assoc($result1)){
  // ADD TO XML DOCUMENT NODE
  echo '<marker ';
  echo 'email="' . $row1['email'] . '" ';
  echo 'lat="' . $row1['lat'] . '" ';
  echo 'lng="' . $row1['lng'] . '" ';
  echo 'type="police" ';
 echo '/>';
}

// Iterate through the rows, printing XML nodes for each
while ($row = @mysql_fetch_assoc($result)){
  // ADD TO XML DOCUMENT NODE
  echo '<marker ';
  echo 'email="' . $row['email'] . '" ';
  echo 'img="' . $row['img'] . '" ';
  echo 'lat="' . $row['lat'] . '" ';
  echo 'lng="' . $row['lng'] . '" ';
  echo 'text="' . $row['text'] . '" ';
 echo 'type="user" ';
 echo '/>';
}

// End XML file
echo '</markers> ';


?>

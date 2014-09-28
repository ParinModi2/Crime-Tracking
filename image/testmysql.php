<?php 
$link = mysql_connect('mysql.artoonsolutions.com','trainingartoon','artoontraining'); 
mysql_select_db('training_artoon');
if (!$link) { 
	die('Could not connect to MySQL: ' . mysql_error()); 
} 
echo 'Connection OK';
$jsonString = file_get_contents('php://input');
$jsonObj = json_decode($jsonString, true);
$ipath = $jsonObj['Image Path'];
$vpath = $jsonObj['Video Path'];
$msg = $jsonObj['Message'];
$loc = $jsonObj['Current Location'];

if($ipath)
{
	$sql=mysql_query("insert into image values ('".$ipath."' ) ");
	if(!$sql)
		echo "Error in query".mysql_error();
}
if($vpath)
{
	$sql=mysql_query("insert into video values ('".$vpath."' ) ");
	if(!$sql)
		echo "Error in query".mysql_error();
}
if($msg)
{
	$sql=mysql_query("insert into msg values ('".$msg."' ) ");
	if(!$sql)
		echo "Error in query".mysql_error();
}
if($loc)
{
	$sql=mysql_query("insert into  location values ('".$loc."' ) ");
	if(!$sql)
		echo "Error in query".mysql_error();
}
mysql_close($link); 

?>
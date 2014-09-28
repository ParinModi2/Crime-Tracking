<?php
include("db.php");
$jsonString = file_get_contents('php://input');
$jsonObj = json_decode($jsonString, true);
$lat = $jsonObj['lat'];
$long = $jsonObj['long'];
$add=$jsonObj['address'];
$email=$jsonObj['email'];


mysql_query("insert into events values('".$email."','0','0','0','".$lat."','".$long."')");
$cnt=mysql_affected_rows();
if($cnt==1)
{
	$resp=0;
	echo json_encode($resp);
}
else
{
	$resp=1;
	echo json_encode($resp);
}


?>
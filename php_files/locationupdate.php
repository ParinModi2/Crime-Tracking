<?php
// Where the file is going to be placed
include("db.php");
$jsonString = file_get_contents('php://input');
$jsonObj = json_decode($jsonString, true);
$email = $jsonObj['email'];
$lat = $jsonObj['lat'];
$long = $jsonObj['long'];
$add=$jsonObj['address'];

mysql_query("update police set lat='".$lat."',lng='".$long."', current_add='".$add."' where email='".$email."' ");
$row=mysql_affected_rows();
echo json_encode($row);
?>
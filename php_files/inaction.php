<?php
include("db.php");
$jsonString = file_get_contents('php://input');
$jsonObj = json_decode($jsonString, true);
$pemail=$jsonObj['email'];
$id=$jsonObj['event_id'];

$result=mysql_query(" update temp_diff set inaction='1' where id='".$id."' and pemail='".$pemail."' ");  

//if(($row=mysql_affected_rows())>0)
//echo "OK";
?>

<?php
include("db.php");
$jsonString = file_get_contents('php://input');
$jsonObj = json_decode($jsonString, true);

$email=$jsonObj['email'];
$caseid=$jsonObj['case_id'];
$feedback=$jsonObj['msg'];

mysql_query("update temp_diff set feedback='".$feedback."' where pemail='".$email."' and id='".$caseid."'  ");
$row=mysql_affected_rows();
echo json_encode($row);
?>
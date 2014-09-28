<?php
 include("db.php");
$jsonString = file_get_contents('php://input');
$jsonObj = json_decode($jsonString, true);
$fname = $jsonObj['fname'];
$lname = $jsonObj['lname'];
$add = $jsonObj['add'];
$city=$jsonObj['city'];
$email = $jsonObj['email'];
$mno= $jsonObj['mno'];
$gen=$jsonObj['gender'];
$dob=$jsonObj['dob'];
$profession=$jsonObj['profession'];


mysql_query("update userinfo set fname='$fname',lname='$lname',addr='$add',city='$city',profession='$profession',mno='$mno',gen='$gen',dob='$dob' where email='$email'");
 //echo "no of rows affected::::".mysql_affected_rows();
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



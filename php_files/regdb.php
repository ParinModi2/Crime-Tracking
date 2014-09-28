<?php 
include("db.php");
echo 'Connection OK';
$jsonString = file_get_contents('php://input');
$jsonObj = json_decode($jsonString, true);
$fname = $jsonObj['fname'];
$lname = $jsonObj['lname'];
$email = $jsonObj['email'];
$mno= $jsonObj['mno'];
$add = $jsonObj['add'];
$gen=$jsonObj['gender'];
$dob=$jsonObj['dob'];
$qua=$jsonObj['qualification'];
echo "First name:".$fname;
echo "insert into userinfo values('".$fname."','".$lname."','".$email."','".$mno."','".$add."','".$gen."','".$dob."','".$qua."','".$confirmationcode."','".$confirmed."')";
$sql=mysql_query("insert into userinfo values('".$fname."','".$lname."','".$email."','".$mno."','".$add."','".$gen."','".$dob."','".$qua."','".$confirmationcode."','".$confirmed."')");
if($sql)
	echo "Row Inserted..!!!!!";
?>
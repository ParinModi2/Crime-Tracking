<?php
 include("db.php");
$jsonString = file_get_contents('php://input');
$jsonObj = json_decode($jsonString, true);
$text = $jsonObj['text'];
$email = $jsonObj['email'];

$resp=0;
$resp1=1;

$query = "insert into events values('".$email."','".$text."','0','0','0','0')";
    
$result = mysql_query($query) OR die(mysql_error()); 
	if(mysql_affected_rows()>0)
	{
		echo json_encode($resp);
	}
	else
	{
		echo 	json_encode($resp1);	
	}
		
?> 




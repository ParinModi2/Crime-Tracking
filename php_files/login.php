<?php
 include("db.php");
$jsonString = file_get_contents('php://input');
$jsonObj = json_decode($jsonString, true);
$email = $jsonObj['id'];
$pass = $jsonObj['pass'];
$resp=0;
$resp1=1;
$resp2=2;
	


	$res=mysql_query("select * from userinfo where email='$email' and pass='$pass' and confirmed='1'");
	$cnt=mysql_num_rows($res);

	if($cnt==0)
	{
		//echo json_encode($resp1);//no entry in db....		
		$res1=mysql_query("select * from police where email='$email' and pass='$pass'");
		$cnt1=mysql_num_rows($res1);
		if($cnt1==0)
			echo json_encode($resp1);//no entry in db....		
		else
			echo json_encode($resp2);//entry exists..

	}
	else
	{
		echo json_encode($resp);//entry exists....
	}
?>	
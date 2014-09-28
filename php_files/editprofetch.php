<?php
include("db.php");
$jsonString = file_get_contents('php://input');
$jsonObj = json_decode($jsonString, true);
$email = $jsonObj['email'];
 
$return_arr=array();

$result=mysql_query("select fname,lname,address,gen,qual from police where email='".$email."' ");
while($r=mysql_fetch_row($result))
{
	$res=$r[0].":".$r[1].":".$r[2].":".$r[3].":".$r[4];
	$re_array['fname']=$r[0];
	$re_array['lname']=$r[1];
	$re_array['addr']=$r[2];
	$re_array['city']=$r[3];
	$re_array['pro']=$r[4];
	$re_array['gen']=$r[5];
	$re_array['dob']=$r[6];
	array_push($return_arr,$re_array);

}
echo json_encode($res);

?>
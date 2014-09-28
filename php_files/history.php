<?php
include("db.php");
//$jsonString = file_get_contents('php://input');
//$jsonObj = json_decode($jsonString, true);
$email = $_POST['email'];

/*
$File1 = "History1.txt"; 
        $Handle = fopen($File1, 'w');
fwrite($Handle, "email".$email); 
 	   	   fclose($Handle); 

*/
//$email="ankita.lachhwani10@gmail.com";
$return_arr=array();

$result=mysql_query("select id from temp_diff where pemail='".$email."' and inaction=1 and feedback='0' ");



while($r=mysql_fetch_row($result))
{

$result_email=mysql_query("select email,address from events where event_id=".$r[0]);

while($re=mysql_fetch_row($result_email))
{
	$re_array['uemail'] = $re[0];
	$re_array['add'] = $re[1];
	$re_array['case_id'] = $r[0];

array_push($return_arr,$re_array);
}
 //array('case_id' => $r[0],'uemail' => $re[0],'add' => $re[1]));

}
echo json_encode($return_arr);

?>
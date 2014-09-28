<?php
include("db.php");
$jsonString = file_get_contents('php://input');
$jsonObj = json_decode($jsonString, true);

$email = $jsonObj['email'];
//$email="parin.modi@ymail.com";
$lat = $jsonObj['lat'];
$long = $jsonObj['long'];
$add=$jsonObj['address'];
$flag=$jsonObj['flag'];
$id=$jsonObj['event_id'];
$date1=date("d-m-Y,H:i:s");

list($date,$time)=explode(",",$date1);

if(!empty($email) && $id=="")
{
	mysql_query("insert into events values(' ".$email." ',' 0 ',' 0 ',' 0 ',' ".$lat." ',' ".$long." ',' ".$add." ',' ".$date." ',' ".$time." ','0')");
	$id=mysql_insert_id();
	echo $id;
	$result=mysql_query("select email,lat,lng,reg_id from police");
$r=mysql_fetch_row($result);
while($r){
$pemail=$r[0];
$lat2=$r[1];
$lon2=$r[2];
$lon1=$long;
$lat1=$lat;
$message="#".$id."=> Emergency at ".$add." lat:".$lat." long:".$long;
$regId=$r[3];

  $theta = $lon1 - $lon2;
  $dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) +  cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * cos(deg2rad($theta));
  $dist = acos($dist);
  $dist = rad2deg($dist);
  $miles = $dist * 60 * 1.1515;
  $unit = strtoupper($unit);
    $distinkm=$miles * 1.609344;

if($distinkm<5 )
{

$result2=mysql_query("insert into temp_diff values ('".$pemail."','".$id."','0','0')");


if ($message!="" && $regId!='') {
     include("GCM.php");
    $gcm = new GCM();
 
    $registatoin_ids = array($regId);
    $message = array("price" => $message);
     $result = $gcm->send_notification($registatoin_ids, $message);
	}
}
}
}
else
{
	mysql_query("update events set lat=' ".$lat." ',lng=' ".$long." ',address=' ".$add." ',date=' ".$date." ',time=' ".$time." ' where event_id='".$id."' ");
if(mysql_affected_rows()>0)
{
	echo $id;
	$result=mysql_query("select email,lat,lng,reg_id from police");

while($r=mysql_fetch_row($result)){
$pemail=$r[0];
$lat2=$r[1];
$lon2=$r[2];
$lon1=$long;
$lat1=$lat;

$message="#".$id."=> Emergency at ".$add." lat:".$lat." long:".$long;
$regId=$r[3];

  $theta = $lon1 - $lon2;
  $dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) +  cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * cos(deg2rad($theta));
  $dist = acos($dist);
  $dist = rad2deg($dist);
  $miles = $dist * 60 * 1.1515;
  $unit = strtoupper($unit);
    $distinkm=$miles * 1.609344;

if($distinkm<5 )
{

$result2=mysql_query("insert into temp_diff values ('".$pemail."','".$id."','0','0')");


//if($result2)
//	echo "inserted";
if ($message!="" && $regId!='') {
    
     include("GCM.php");
 
    $gcm = new GCM();
 
    $registatoin_ids = array($regId);
    $message = array("price" => $message);
     $result = $gcm->send_notification($registatoin_ids, $message);
	}
}
}
}
}
?>

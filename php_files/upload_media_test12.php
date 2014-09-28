<? ob_start(); ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<?php
// Where the file is going to be placed
include("db.php");
$jsonString = file_get_contents('php://input');
$jsonObj = json_decode($jsonString, true);

$img=$jsonObj['img'];
$email = $jsonObj['email'];
$vdo=$jsonObj['video'];
$text=$jsonObj['txt'];
$lat = $jsonObj['lat'];
$add=$jsonObj['address'];
$long = $jsonObj['long'];
$date1=date("d/m/Y,H:i:s");

list($date,$time)=explode(",",$date1);

if(!empty($email) || !empty($filen)  || !empty($text))

mysql_query("insert into events values('".$email."','".$text."','".$img."','".$vdo."','".$lat."','".$long."','".$add."','".$date."','".$time."','0')");


$id=mysql_insert_id();

mysql_query("insert into news values ('".$id."','".$add."','".$text."',NOW())");

$result=mysql_query("select email,lat,lng,reg_id from police");
$i=0;

while($r=mysql_fetch_row($result)){
$pemail=$r[0];
$lat2=$r[1];
$lon2=$r[2];
$lon1=$long;
$lat1=$lat;
$message="#".$id."=> Emergency at ".$add." lat:".$lat." long:".$long;

  $theta = $lon1 - $lon2;
  $dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) +  cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * cos(deg2rad($theta));
  $dist = acos($dist);
  $dist = rad2deg($dist);
  $miles = $dist * 60 * 1.1515;
  $unit = strtoupper($unit);
    $distinkm=$miles * 1.609344;

if($distinkm<5 || $regId!='0' || $regId!='' )
{


$result2=mysql_query("insert into temp_diff values ('".$pemail."','".$id."','0','0')");


$regId[$i]=$r[3];

$i++;


}

}
if ($message!="" && $regId!='') {
    
     include("GCM.php");
 
    $gcm = new GCM();
 
    $registatoin_ids = $regId;
    $message = array("price" => $message);
     $result = $gcm->send_notification($registatoin_ids, $message);
	}
?>

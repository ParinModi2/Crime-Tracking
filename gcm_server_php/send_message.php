<? ob_start(); ?>
<?php 
//echo "value received:".$_POST['coo'];
$diff=$_POST['coo'];
$i=$_POST['i'];
$address2=$_COOKIE['address2'];
$lat=$_COOKIE['lat'];
$lng=$_COOKIE['lng'];


if(isset($_COOKIE['uemail']))
{
	$uemail=$_COOKIE['uemail'];
	echo "cookie:".$uemail;
}
else
	echo "not set";

$message="";
$regid=0;

echo "<br>i=".$i."<br>";

include('db.php');

$regid=mysql_query("select reg_id,email from police ");
$j=0;
while($r=mysql_fetch_row($regid))
	{
		if($i==$j)
		{
			$pemail=$r[1];
			$regId = $r[0];
			break;	
		}
		else
			$j++;
	}

$File = "send_msg.txt"; 
$Handle = fopen($File, 'a');
fwrite($Handle, "add:::".$pemail);
fwrite($Handle, "lat:::".$regId);
fwrite($Handle, "diff:::".$diff);
fclose($Handle);
$message="Emergency at ".$address2." lat:".$lat." long:".$lng;

$result2=mysql_query("insert into temp_diff values ('".$diff."','".$pemail."','".$uemail."','".$lat."','".$lng."')");

if($result2)
	echo "inserted";

if ($message!="" && $regId!='') {
    
     include("GCM.php");
 
    $gcm = new GCM();
 
    $registatoin_ids = array($regId);
    $message = array("price" => $message);
     $result = $gcm->send_notification($registatoin_ids, $message);
	}
?>
<? ob_flush(); ?>



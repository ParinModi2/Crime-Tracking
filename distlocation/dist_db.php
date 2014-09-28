<? ob_start(); ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<?php 
//echo "value received:".$_POST['coo'];
$diff=$_POST['coo'];
$i=$_POST['i'];
$uemail=$_COOKIE['uemail'];


$File = "dist_db.txt"; 
$Handle = fopen($File, 'w');
fwrite($Handle, $diff);
fwrite($Handle, $uemail);
fclose($Handle);

echo $uemail;
echo "<br>i=".$i."<br>";
include('db.php');
$result=mysql_query("select email from police");
$j=0;
while($r=mysql_fetch_row($result))
	{
		if($i==$j)
		{
			$pemail=$r[0];
			break;	
		}
		else
			$j++;
	}
$result=mysql_query("insert into temp_diff values ('".$diff."','".$pemail."','".$uemail."')");

if($result)
echo "inserted";
?>
<? ob_flush(); ?>

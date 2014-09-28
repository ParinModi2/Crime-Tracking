<? ob_start(); ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<?php
// Where the file is going to be placed
include("db.php");
$jsonString = file_get_contents('php://input');
$jsonObj = json_decode($jsonString, true);
$img=$jsonObj['img'];
$vdo=$jsonObj['video'];
$email = $jsonObj['email'];
//$email="parin.modi@ymail.com";

$text=$jsonObj['txt'];
$newname=$jsonObj['newname'];
$lat = $jsonObj['lat'];
$add=$jsonObj['address'];
$long = $jsonObj['long'];
$date1=date("d-m-Y,H:i:s");

list($date,$time)=explode(",",$date1);
echo $date;
echo $time;


$target_path = "image/";

/* Add the original filename to our target path.
Result is "uploads/filename.extension" */

$target_path = $target_path . basename( $_FILES['uploaded_file']['name']);

if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $target_path)) {
echo "The file ". basename( $_FILES['uploaded_file']['name']).
" has been uploaded";
} 
else{
echo "There was an error uploading the file, please try again!";
echo "filename: " . basename( $_FILES['uploaded_file']['name']);
echo "target_path: " .$target_path;
}
?>



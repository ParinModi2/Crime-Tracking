<?php
include("db.php");
  $confirmId = $_GET['c'];
  $resp=0;
  $errorMessage = "";
  $validCount = 0;
  
  $query = "SELECT confirmed FROM `userinfo` WHERE confirmationCode='$confirmId'";
  $result = mysql_query($query);
  $validCount = mysql_num_rows($result);
  
  if($result['confirmed'] == 1) $errorMessage .= "You have already confirmed this comment.<br/>";
  if($validCount == 0) $errorMessage .= "You are trying to confirm an invalid comment.<br/>";
  
  if(empty($errorMessage))
  {    
    $query = "UPDATE `userinfo` SET confirmed = 1 WHERE confirmationCode='$confirmId'";
    mysql_query($query) OR die(mysql_error()); 

    echo 'Your account has been confirmed!';
	echo json_encode($resp);
  }
  else
  {
    echo $errorMessage;
  }
?>



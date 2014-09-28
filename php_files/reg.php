<?php
 include("db.php");
$jsonString = file_get_contents('php://input');
$jsonObj = json_decode($jsonString, true);
$uname = $jsonObj['uname'];
$pass = $jsonObj['pass'];
$email = $jsonObj['email'];
$mno=$jsonObj['mno'];
$resp=0;
$resp1=1;
  $errorMessage = "";

  if(empty($uname)) 
	$errorMessage .= "You didn't enter a username.<br/>\n";
  if(empty($email))
	$errorMessage .= "You didn't enter an email address.<br/>\n";

  if(empty($errorMessage))
  {
	$res=mysql_query("select *from userinfo where email='$email'");
	$cnt=mysql_num_rows($res);
	if($cnt==0)
	{

    $confirmationCode = getRandomString();
    $query = "insert into userinfo values('0','0','".$email."','0','0','".$mno."','0','0','".$uname."','".$pass."','".$confirmationCode."','0','0')";
    $result = mysql_query($query) OR die(mysql_error()); 
	if($result)
	{
		echo json_encode($resp);

	 	$to = "";
    		$subject = "";
    		$message="";
    		$headers="";
    
    		$to  = $email;
   		 $subject = "Site Registration Confirmation";
   		 $message = '<html>
		<head>
		<title>Site Registration Confirmation</title>
		</head>

		<body style="font-family:verdana, arial; font-size: .8em;">
		You\'re receiving this email because you filled out a registration form on this website.
		<br/><br/>
		If you did not try to create an account, you can simply delete this email. No further action is required.
		<br/><br/>
		To complete confirmation and add your registration, please click on the link below:<br>
		<a title="Confirm Comment"
		href="http://training.artoonsolutions.com/crimetracking/confirm.php?c='.$confirmationCode.'">http://training.artoonsolutions.com/crimetracking/confirm.php?c='.$confirmationCode.'</a>
		<br/><br/>
		Thank you and we hope you enjoy using Website.com!<br/><br/>

		</body>
		</html>';

		/* To send HTML mail, you can set the Content-type header. */
		    $headers  = "MIME-Version: 1.0\r\n";
		    $headers .= "Content-type: text/html; charset=iso-8859-1\r\n";
    
		    /* additional headers */
		    $headers .= "From: Website <no-reply@website.com>\r\n";
    
    		/* and now mail it */
		    mail($to, $subject, $message, $headers);
  		}
	   }
	else
	{
		  //echo $errorMessage;
		  echo json_encode($resp1);

	}

		
	}
	else
	{
		  //echo $errorMessage;
		  echo json_encode($resp1);

	}
		function getRandomString()
		{
			  $length = 8;

			 $passwordRandomString = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
  
  			//initialize the new password string
  			$newPW = "";
			srand();
 			 for($x=0; $x < $length; $x++)
 			 {
 				   $newPW .= substr($passwordRandomString,rand(0,62),1);
 			 }
 	 
 			 return $newPW;
		}

		
?> 




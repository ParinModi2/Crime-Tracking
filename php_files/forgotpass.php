<html>
<form name=f1 method="post">
<center><table >
<tr>	
	<td>Enter your Email:</td>
	<td> <input type=text name=email></td>
</tr>
<tr>
	<td colspan=2 align=center><input type=submit name=submit1 value="Get Password"></td>
</tr>
</table>
</center>
</form>
</html>



<?php
	echo "In php";
	include("db.php");
	if(isset($_POST['submit1']))
	{
		$email=$_POST['email'];
		$name=mysql_query("select name from adminlogin where email='$email' ");
		$row=mysql_fetch_array($name);
		$name=$row[0];
		$str="";
		if($row[0])
		{
			for($i=0;$i<5;$i++)
			{
			$n=rand(48,122);
			if($n>=65&&$n<=90 || $n>=97&&$n<=121 || $n>=48&&$n<=57)
			{
				$arr[$i]=chr($n);
				
			}
			else
			{
				$i--;
			}
				echo $arr[$i];
				//echo "Str is:";
				$str=$str.$arr[$i];
			}
			echo $str;
			$code=$str;
			echo $code;
			$to = $email;
			$subject = "This is subject";
   			$message = "<b>Your new Password is </b>".$code;
  			 $header = "From:CrimeTrackingApplication \r\n";
		   	$header .= "MIME-Version: 1.0\r\n";
  		 	$header .= "Content-type: text/html\r\n";
  			 $retval = mail ($to,$subject,$message,$header);
  			 if( $retval == true )
 			  {
  			    	echo "Your new password has been sent to your email id successfully...";
			echo "update adminlogin set pass='$code' where name='$name' ";
				mysql_query("update adminlogin set pass='$code' where name='$name' ");
				echo "Updated";
		
  			 }
  			 else
   			{	
				echo "Message could not be sent...";
  			 }

		}
	}
?>



<?php
include ("db.php");
if(true)
{
		$email='modi.parin@gmail.com';
		//$_POST['email'];
		$pass='aaaaaa';
				 
		$passdb=mysql_query("select pass from adminlogin where email='$email' ");

		$row=mysql_fetch_array($passdb);
		if($row[0]==$pass)
		{
			//header("location:adminpanel.php");
			echo "in adminpanel.php";
		}
		else
		{
			echo "alert:The email or password is incorrect";
		}

		//session_start();
		
/*		$namedb=mysql_query("select name from adminlogin where email='$email' ");

		while($row1=mysql_fetch_array($namedb))
		{
			//echo "name".$row1[0];
			//$_SESSION['adname']=$row1[0];
		}
*/
}
if(isset($_POST['check']))
{
	$email=$_POST['email'];
	$pass=$_POST['pass'];
	setcookie("ckname",$email,time()+3600*24*30);
	setcookie("ckpass",$pass,time()+3600*24*30);	
}
?>
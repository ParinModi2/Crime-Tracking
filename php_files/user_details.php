<?php ob_start()?>
<?php
session_start();
if(isset($_SESSION['adname']))
echo "Welcome ".$_SESSION['adname'];
else
{

	header("location:adminlogin.php");
	exit(0);
}

	include("db.php");

	$email=$_GET['email'];
	$result=mysql_query("select fname,lname,city,addr,gen,mno from userinfo where email='".$email."'"); 	

	$r=mysql_fetch_row($result);
		
	
?>
<html>
<form name=f1 method=post>
<body>
<center>
<table height=200 width=400 border=1>
<tr>
<th 
colspan=2
height=40
background="http://training.artoonsolutions.com/crimetracking/crime_tracking_images/events_page/normal_header.png"
align=center>
Full Details
</th>
</tr>
<tr>

<td id=sid><br><b>First Name :</b><? if($r[0]==0) echo "------"; else echo " ".$r[0]; ?> 
</td>
</tr>
<tr>
<td id=sid1><br><b>Last Name :</b><? if($r[1]==0) echo "------"; else echo " ".$r[1]; ?> 
</td>

</tr>
<tr>
<td id=city><br><b>City :</b><? if($r[2]==0) echo "------"; else echo " ".$r[2]; ?> 
</td>

</tr>

<tr>
	<td id=email>
<br>
<b>Email Id :</b><? echo " ".$email; ?></td>


</tr>

<tr>
<td id=addr><br><b>Address :</b><? if($r[3]==0) echo "------"; else echo " ".$r[3]; ?> 
</td>

</tr>
<tr>
<td id=gen><br><b>Gender :</b><? if($r[4]==0) echo "------"; else echo " ".$r[4]; ?> 
</td>

</tr>
<tr>
<td id=mno><br><b>Contact No :</b><? if($r[5]==0) echo "------"; else echo " ".$r[5]; ?> 
</td>

</tr>
<tr>
<td>
	Events raised by the User
</td>
</tr>
<tr>
<?php
	$result1=mysql_query("select img from events where email='".$email."'");
	//$cnt=0;
	while($r=mysql_fetch_row($result1))
       {
               //if($cnt%3==0)
			if(($r[0]).length!=0)
			{
               echo "<tr>";
               echo "<td>";
			echo"<td> <img src='image/'.$r[0] height=200 width=200></td>";

			echo $r[0];
               echo "</td>";
               //$cnt++;
               //if($cnt%3==0)
               echo "</tr>";
			}	
	}
?>

</tr>	
</table>
</center>
</body>
</html>


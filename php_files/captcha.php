<?php
for($i=0;$i<5;$i++)
{
		$n=rand(48,122);
		if($n>=65&&$n<=90 || $n>=97&&$n<=121 || $n>=48&&$n<=57)
		{
			$arr[$i]=chr($n);
			echo $arr[$i];
		}
		else
			$i--;
}	
for($i=0;$i<5;$i++)
{
	$cmp[$i]=$arr[$i];
	echo "<font color='#FF0099'>".$cmp[$i]."</font>";
}
$cnt=0;
if(isset($_POST['Submit']))
{
	
	$x=$_POST['t1'];
	for($i=0;$i<5;$i++)
	{
		echo "<font color='RED'>".$x[$i];
	}
}/*echo "<font color='BLUE'>".$arr[$i]."</font>";
		if($x[$i]==$arr[$i])
			$cnt++;
		else
			$cnt=0;
	}
}
if($cnt==5)
	echo "MATCH!!!";
else
	echo "Not match";*/
?>
<form name="form1" method="post" action="">
  <input type="text" name="t1">
  <input type="submit" name="Submit" value="Submit">
</form>
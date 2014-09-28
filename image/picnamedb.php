<?php
include("db.php");
	
	echo "<table border=1 align=center>"; echo "<tr>";
	echo "<th>Image</th>";
	echo "<th>User Nmae</th>";	
	/*echo "<th>Lat</th>";
	echo "<th>Lng</th>";*/
	$row=mysql_query("select img,email from events");
	while($r=mysql_fetch_row($row))
	{
		echo"<tr>";
		echo"<td> <img src='$r[0]' height=200 width=200></td>";
		
		echo"<td  height=90 width=200 align=center> $r[1]</td>";
 		/*echo"<td  height=90 width=200 align=center> $r[2]</td>";
		echo"<td  height=90 width=200 align=center> $r[3]</td>";*/
	
		echo"</tr>";
	}	
	echo "</table>";


?>
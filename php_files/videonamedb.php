<!DOCTYPE html>
<head>
<script src="jquery.min.js"></script>
 </head>
  <body>  
  <div id="block1">
  <?php
  include("db.php");
	
	echo "<table border=1 align=center>"; echo "<tr>";
	echo "<th>Video</th>";
	echo "<th>User Name</th>";	
	echo "<th>Lat</th>";
	echo "<th>Lng</th>";
	$row=mysql_query("select vdo,email,lat,lng from events");
	while($r=mysql_fetch_row($row))
	{
		if($r[0]!=null)
		{
		echo "<tr>";
		echo "<td>";
		echo "video:video/".$r[0];
		echo "</td>";
		echo "<td>";
		echo "<a href='video/$r[0]' style='display:block;width:425px;height:300px;margin:10px auto'
    id='player'>";
		echo "Click here to play";
		echo "</a>";

		echo "</td>";
		echo"<td  height=90 width=200 align=center> $r[1]</td>";
 		echo"<td  height=90 width=200 align=center> $r[2]</td>";
		echo"<td  height=90 width=200 align=center> $r[3]</td>";
		/*echo"<td  height=90 width=200 align=center> $r[4]</td>";
		echo"<td  height=90 width=200 align=center> $r[5]</td>";*/
		echo"</tr>";
		}
	}	
	echo "</table>";


?>
  </div>
  </body>
</html>

<?php
include("db.php");
session_start();
$name=$_SESSION['adname'];
$result=mysql_query("select pass,email from adminlogin where name='$name'");
$row=mysql_fetch_row($result);
if(isset($_POST['s1'])!=null)
{
			
	$name=$_POST['name'];
	$pass=$_POST['pass'];
	$email=$_POST['email'];
	mysql_query("update adminlogin set pass='$pass',email='$email' where name='$name' ");
			
}
		
?>
<html>
<head >
<link rel="stylesheet" type="text/css" href="menu1.css">
<link rel="stylesheet" type="text/css" href="samp2.css">

</head>

<body>
<center>
<nav>
	<ul>
		<li><a href="#">Dashboard</a></li>
		<li><a href="#">Admin</a>
	
			<ul>
				<li><a href="adminlist.php">Admin User list</a></li>
				<li><a href="adminreg.php">Add new admin user</a></li>
				<li><a href="policereg.php">Register Police </a></li>
			</ul></li>
		<li><a href="#">Police</a>
			<ul>
				<li><a href="listpolice.php">Police user list</a></li>
			</ul>
		</li>
		<li><a href="#">User</a>
			<ul>
				<li><a href="#">User List</a></li>
			</ul>
		</li>
		<li><a href="#">Events</a>
			<ul>
				<li><a href="#">EventList</a></li>
			</ul>
		</li>
	</ul>
</nav>
</center>
<form name=f1 method="post">
<table align="center">
<tr>
<th colspan="2">
Update Your Profile
</th>

</tr>
<tr>
<td>Name:</td>
<td><input type="text" name="name" value=<?php echo $name?>></td>

</tr>
<tr>
<td>Password:</td>
<td><input type="password" name="pass" value=<?php echo $row[0] ?>></td>
</tr>
<tr>
<td>Email:</td>
<td><input type="text" name="email" value=<?php echo $row[1] ?>></td>
</tr>

<tr>
<td colspan="2">
<input type=submit name=s1 value="Submit">
</td>
</tr>

</table>
</form>
</body>
</html>
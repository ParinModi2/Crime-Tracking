<style type="text/css">
<!--
nav ul ul ul {
	position: absolute; left: 100%; top:0;
}


nav ul ul {
	background: #5f6975; border-radius: 0px; padding: 0;
	position: absolute; top: 100%;
}
	nav ul ul li {
		float: none; 
		border-top: 1px solid #6b727c;
		border-bottom: 1px solid #575f6a;
		position: relative;
	}
		nav ul ul li a {
			padding: 15px 40px;
			color: #fff;
		}	
			nav ul ul li a:hover {
				background: #4b545f;
			}
nav ul li {
	float: left;
}
	nav ul li:hover {
		background: #4b545f;
		background: linear-gradient(top, #4f5964 0%, #5f6975 60%);
		background: -moz-linear-gradient(top, #4f5964 0%, #5f6975 60%);
		background: -webkit-linear-gradient(top, #4f5964 0%,#5f6975 60%);
	}
		nav ul li:hover a {
			color: #fff;
		}
	
	nav ul li a {
		display: block; padding: 25px 40px;
		color: #757575; text-decoration: none;
	}

	nav ul:after {
		content: ""; clear: both; display: block;
	}
nav ul ul {
	display: none;
}

	nav ul li:hover > ul {
		display: block;
	}
nav ul {
	background: #efefef; 
	background: linear-gradient(top, #efefef 0%, #bbbbbb 100%);  
	background: -moz-linear-gradient(top, #efefef 0%, #bbbbbb 100%); 
	background: -webkit-linear-gradient(top, #efefef 0%,#bbbbbb 100%); 
	box-shadow: 0px 0px 9px rgba(0,0,0,0.15);
	padding: 0 20px;
	border-radius: 10px;  
	list-style: none;
	position: relative;
	display: inline-table;
	align:center;
}

-->
</style>
<html>
<nav>
	<ul>
		<li><a href="#">Dashboard</a></li>
		<li><a href="#">Admin</a>
	
			<ul>
				<li><a href="adminlist.php">Admin User list</a></li>
				<li><a href="adminreg.php">Add new admin user</a></li>
				<li><a href="">Register Police </a></li>
			</ul></li>
		<li><a href="#">Police</a>
			<ul>
				<li><a href="#">Police user list</a></li>
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
</html>
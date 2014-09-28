
<?php
$json = file_get_contents('php://input');
$obj = json_decode($json);
//echo "Connection ok";
//print_r($json);
echo '<pre>';
foreach($obj as $index=> $user)
{
	echo $user->screen_name."<br>";
}
echo '<pre>';
/*echo "Object";
print_r ("object image path:".$obj['Image Path']);
print_r($obj);*/
?>
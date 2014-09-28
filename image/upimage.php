<?php
//Receive the data from android
$name = $_POST['name'];
$data = $_POST['data'];

//Receive the file
$file = $_FILES['image'];
echo "Image:".$file;
echo "\n";
$path="http://
training.artoonsolutions.com/crimetracking/image/".$file;	
move_uploaded_file($_FILES['image'],$path);
echo "path:".$path;
//process the data

//return response to the server

echo json_encode(
            array(
                'result'=>'success',
                'msg'=>'Report added successfully.'
                )
            );


?>





<?php
 
// response json
$json = array();
 
/**
 * Registering a user device
 * Store reg id in users table
 */
if (isset($_POST["email"]) && isset($_POST["regId"])) {
    $name = "zil";
    $email = $_POST["email"];
    $gcm_regid = $_POST["regId"];
 $File = "reg.txt"; 
 $Handle = fopen($File, 'w');
 fwrite($Handle, $email);
 fwrite($Handle,$gcm_regid);
 fclose($Handle); 

 // GCM Registration ID*/
    // Store user details in db
    include("db_functions.php");
    include("GCM.php");
 
    $db = new DB_Functions();
    $gcm = new GCM();
 
    $res = $db->storeUser($name, $email, $gcm_regid);
 
    $registatoin_ids = array($gcm_regid);
    $message = array("price" => "Welcome to Crime tracking Application.");
 
    $result = $gcm->send_notification($registatoin_ids, $message);
 


    echo json_encode($result);
} else {
    // user details missing
}
?>
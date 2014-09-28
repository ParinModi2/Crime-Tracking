
<?php
define("GOOGLE_API_KEY","AIzaSyBrp1kIr_vunuqITMDNEgaffy0iOzrAUbU");

class GCM {
 
    //put your code here
    // constructor
    function __construct() {
 
    }
 
    /**
     * Sending Push Notification
     */
    public function send_notification($registatoin_ids, $message) {
		      
        //include ("config.php");
	$con = mysql_connect("mysql.artoonsolutions.com", "trainingartoon","artoontraining");
        // selecting database
        mysql_select_db("training_artoon");
 
 
        // Set POST variables
        $url = 'https://android.googleapis.com/gcm/send';
 	   
        $fields = array(
            'registration_ids' => $registatoin_ids,
            'data' => $message
        );
 		$File = "GCMlocal.txt"; 
 		$Handle = fopen($File, 'w');
 		fwrite($Handle, json_encode($fields)); 
 		fclose($Handle); 
        
        $headers = array(
            'Authorization: key=' . GOOGLE_API_KEY,
            'Content-Type: application/json'
        );
		$File = "GCMheared.txt"; 
 		$Handle = fopen($File, 'w');
 		fwrite($Handle, json_encode($headers)); 
 		fclose($Handle); 

        // Open connection
        $ch = curl_init();
 
        // Set the url, number of POST vars, POST data
        curl_setopt($ch, CURLOPT_URL, $url);
 
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
 	   
        // Disabling SSL Certificate support temporarly
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
 
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
 
        // Execute post
        	$result = curl_exec($ch);
		
		$File = "GCM.txt"; 
        $Handle = fopen($File, 'w');
 	   fwrite($Handle, $result); 
	   fclose($Handle); 
	
	 if ($result === FALSE) {
            die('Curl failed: ' . curl_error($ch));
        }
 
        // Close connection
         curl_close($ch);
         //echo $result;
	   
	
    }
 
}
 ?>
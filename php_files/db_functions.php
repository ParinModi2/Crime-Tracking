
<?php
 
class DB_Functions {
 
    private $db;
 
    //put your code here
    // constructor
    function __construct() {
        include("db_connect.php");
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }
 
    // destructor
    function __destruct() {
 
    }
 
    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($name, $email, $gcm_regid) {
        // insert user into database

$result=mysql_query("update police set reg_id='".$gcm_regid."' where email='".$email."' ");


/*
        $result = mysql_query("INSERT INTO gcm_users(name, email, gcm_regid, created_at) VALUES('$name', '$email', '$gcm_regid', NOW())");
*/
        // check for successful store
        if ($result) {

            // get user details

            $id = mysql_insert_id(); // last inserted id

            $result = mysql_query("SELECT * FROM police WHERE id = $id") or die(mysql_error());

            // return user details

            if (mysql_num_rows($result) > 0) {
			//$File = "lastuserinfo.txt"; 
                return mysql_fetch_array($result);
            } 
	else {
                return false;
            }
        } 
	else {
            return false;
        }
    }
 
    /**
     * Getting all users
     */
    public function getAllUsers() {
        $result = mysql_query("select * FROM police");
        return $result;
	   
    }
 
}
 
?>

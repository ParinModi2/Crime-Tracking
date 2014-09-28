<?php
 define("GOOGLE_API_KEY","AIzaSyBrp1kIr_vunuqITMDNEgaffy0iOzrAUbUm");

class DB_Connect {
 
    // constructor
    function __construct() {
 
    }
 
    // destructor
    function __destruct() {
        // $this->close();
    }
 
    // Connecting to database
    public function connect() {
       // require_once "config.php";
        // connecting to mysql
        $con = mysql_connect("mysql.artoonsolutions.com", "trainingartoon","artoontraining");
        // selecting database
        mysql_select_db("training_artoon");
 
        // return database handler
        return $con;
    }
 
    // Closing database connection
    public function close() {
        mysql_close();
    }
 
}
?>
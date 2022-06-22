<?php

require_once("Rest.inc.php");

class API extends REST {

    public $data = "";

    const servername = "http://arrive5.pcthepro.com";
    // const DB_SERVER = "localhost";
    const DB_SERVER = "localhost";
    const userimagepath = "/uploads/users/";
    const driverimagepath = "/uploads/drivers/";
    const drivervechilepath = "/uploads/vechile/";
    // const DB_USER = "root";
    const DB_USER = "pcthepro_creb";
    // const DB_PASSWORD = "root";
    const DB_PASSWORD = "creb@2018";
    const DB = "pcthepro_arrive5";
    const RANGE = 200000;

    public function __construct() {
        parent::__construct();    // Init parent contructor
        $this->dbConnect();     // Initiate Database connection
    }

    /*
     *  Database connection
     */

    private function dbConnect() {
        //$this->db = mysql_connect(self::DB_SERVER,self::DB_USER,self::DB_PASSWORD);
        $db = new mysqli(self::DB_SERVER, self::DB_USER, self::DB_PASSWORD, self::DB);
        $this->db = $db;
        /* if($this->db)
          mysql_select_db(self::DB,$this->db); */
        if ($db->connect_errno > 0) {
            die('Unable to connect to database [' . $db->connect_error . ']');
        }
    }

    /*
     * Public method for access api.
     * This method dynmically call the method based on the query string
     *
     */

    public function processApi() {
        $func = strtolower(trim(str_replace("/", "", $_REQUEST['rquest'])));
        if ((int) method_exists($this, $func) > 0)
            $this->$func();
        else
            $this->response('', 404);    // If the method not exist with in this class, response would be "Page not found".
    }

    // function for login users
    private function login() {
        $appplatform = $_REQUEST['appPlatform']; //ios,android
        $token = $_REQUEST['token'];
        $mobileno = $_REQUEST['mobileno'];
        $password = $_REQUEST['password'];

        // Input validations
        if (!empty($mobileno) && !empty($password)) {
            $sql = $this->db->query("SELECT * FROM user WHERE mobile ='$mobileno' and password = '$password'");
            if ($sql->num_rows > 0) {

                $this->db->query("update user set token = '$token', appPlatform = '$appplatform' where  mobile ='$mobileno'");

                $result = $sql->fetch_assoc();
              
                $result['password'] = '';
                $result['join_date'] = date("F Y", strtotime($result['added_on']));
                if (!empty($result['img'])) {
                    $result['image'] = self::servername . self::userimagepath . $result['img'];
                } else {
                    $result['image'] = self::servername . self::userimagepath . 'UserImage.jpg';
                }
                $success = array('status' => "true", "details" => $result, "msg" => "Successfully Login");
                $this->response($this->json($success), 200);
            } else {
                $error = array('status' => "false", "msg" => "Invalid Mobile Number or Password");
                $this->response($this->json($error), 200);
            }
        } else {
            $error = array('status' => "false", "msg" => "Invalid Parameter");
            $this->response($this->json($error), 200);
        }
    }

    public function verify_mobile() {
        $mobileno = $_REQUEST['mobileno'];

        $check_mobile = $this->db->query("select * from user where mobile='$mobileno'");
        if ($check_mobile->num_rows > 0) {
            //$select_pass=mysql_query("update findam_users set password='".md5($new_passpord)."' where id='$userid'");

            $results = $check_mobile->fetch_assoc();
            $result['id'] = $results['id'];
           //  $code = rand(1000,9999);
           //  $message = "your 4 digit code ".$code;
           // $aa =  $this->send_sms($mobileno,$message);
            $success = array("status" => "true", "details" => $result, "msg" => "Mobile number exits");
            $this->response($this->json($success), 200);
        } else {
            $code = rand(1000,9999);
            $message = "your 4 digit code ".$code;
           $aa =  $this->send_sms($mobileno,$message);
            $success = array("status" => "false", "msg" => "Mobile number not exits", "otp"=>$code);
            $this->response($this->json($success), 200);
        }
    }

    /*
      Create function for update profile password
     */

    public function change_password() {
        $id = $_REQUEST['id'];
        $password = $_REQUEST['password'];
        if (!empty($id) && !empty($password)) {
            $select_pass = $this->db->query("update user set password='" . $password . "' where id='$id'");
            if ($select_pass) {

                $success = array("status" => "true", "msg" => "Password updated.");
                $this->response($this->json($success), 200);
            } else {
                $success = array("status" => "false", "msg" => "some error occured");
                $this->response($this->json($success), 200);
            }
        } else {
            $success = array("status" => "false", "msg" => "Invalid parameter");
            $this->response($this->json($success), 200);
        }
    }

    private function signup() {        
        $firstname = $_REQUEST['firstname'];
        $lastname = $_REQUEST['lastname'];
        $emailid = $_REQUEST['emailid'];
        $mobile_no = $_REQUEST['mobileno'];
        $password = $_REQUEST['password'];
        $token = $_REQUEST['token'];
        $invite_code = $_REQUEST['code'];
        $appPlatform = $_REQUEST['appplatform'];
        if (!empty($firstname) && !empty($emailid) && !empty($mobile_no) && !empty($password)) {
            $mobilenumcheck = $this->db->query("SELECT* FROM user WHERE mobile ='$mobile_no'");
            $emailidcheck = $this->db->query("SELECT* FROM user WHERE email ='$emailid'");

            if (isset($_FILES['image'])) {
                $uploadpath = "../uploads/users/";
                //$uploadpath = "../uploads/users/";
                $file_name = $_FILES['image']['name'];
                //$file_namearr = explode('.', $file_name);
                $ext = pathinfo($_FILES['image']['name'], PATHINFO_EXTENSION);
                $filename = time() . rand(10, 100) . '.' . $ext;
                $this->fileupload($uploadpath, $filename);
            }

            if ($mobilenumcheck->num_rows == 0 && $emailidcheck->num_rows == 0) {
                $chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                $res = "";
                for ($i = 0; $i < 8; $i++) {
                    $res .= $chars[mt_rand(0, strlen($chars) - 1)];
                }
                if($invite_code!=''){
                    $check_invite_code = $this->db->query("SELECT * FROM user WHERE invite_code ='$invite_code'");
                    if($check_invite_code->num_rows >0){
                        $in_result = $check_invite_code->fetch_assoc();
                        $in_user_id= $in_result['id'];
                        $insert = $this->db->query("INSERT INTO user(`email`, `first_name`,`last_name`,`password`,`img`, `mobile`,`token`,`appPlatform`,`code`,`invite_code`,`invited_by`) VALUES ('$emailid','$firstname','$lastname','$password','$filename','$mobile_no','$token','$appPlatform','$code','$res','$in_user_id')");
                            if ($insert > 0) {
                            $lastid = $this->db->insert_id;

                            $getuserdetails = array();
                            $getdetails = $this->db->query("select *   from user where id = $lastid");
                            if ($getdetails->num_rows > 0)
                                $getuserdetails = $getdetails->fetch_assoc();
                            $getuserdetails['password'] = '';
                            $getuserdetails['join_date'] = date("F Y" ,strtotime($getuserdetails['added_on']));
                            if (!empty($getuserdetails['img'])) {
                                $getuserdetails['image'] = self::servername . self::userimagepath . $getuserdetails['img'];
                            } else {
                                $getuserdetails['image'] = self::servername . self::userimagepath . 'UserImage.jpg';
                            }
                            $code = rand(1000,9999);
                            $message = "your 4 digit code ".$code;
                           $aa =  $this->send_sms($mobile_no,$message);
                           //print_r($aa);die();
                            $errors = array("details" => $getuserdetails, 'status' => "true", "msg" => "successfully rgister");
                        } else {
                            $errors = array('status' => "false", "msg" => "There is some issue in registration");
                        }
                    }else{
                        $errors = array('status' => "false", "msg" => "Invalid invite code");
                    }
                }else{
                   $insert = $this->db->query("INSERT INTO user(`email`, `first_name`,`last_name`,`password`,`img`, `mobile`,`token`,`appPlatform`,`code`,`invite_code`,`invited_by`) VALUES ('$emailid','$firstname','$lastname','$password','$filename','$mobile_no','$token','$appPlatform','$code','$res','$in_user_id')");
                   if ($insert > 0) {
                        $lastid = $this->db->insert_id;

                        $getuserdetails = array();
                        $getdetails = $this->db->query("select * from user where id = $lastid");
                        if ($getdetails->num_rows > 0)
                            $getuserdetails = $getdetails->fetch_assoc();
                        $getuserdetails['password'] = '';
                        $getuserdetails['join_date'] = date("F Y" ,strtotime($getuserdetails['added_on']));
                        if (!empty($getuserdetails['img'])) {
                            $getuserdetails['image'] = self::servername . self::userimagepath . $getuserdetails['img'];
                        } else {
                            $getuserdetails['image'] = self::servername . self::userimagepath . 'UserImage.jpg';
                        }
                         $code = rand(1000,9999);
                            $message = "your 4 digit code ".$code;
                           //$aa =  $this->send_sms($mobile_no,$message);
                           //print_r($aa);die();
                        $errors = array("details" => $getuserdetails, 'status' => "true", "msg" => "successfully rgister");
                    } else {
                        $errors = array('status' => "false", "msg" => "There is some issue in registration");
                    } 
                }
                $this->response($this->json($errors), 200);


            } else {
                $error = array('status' => "false", "msg" => "mobile number or email id already exits");
                $this->response($this->json($error), 200);
            }
        } else {
            $error = array('status' => "false", "msg" => "Invalid Parameter");
            $this->response($this->json($error), 200);
        }
    }

    // function for login users
    private function driver_login() {

        $appplatform = $_REQUEST['appPlatform']; //ios,android
        $token = $_REQUEST['token'];
        $mobileno = $_REQUEST['mobileno'];
        $password = $_REQUEST['password'];
        //echo $mobileno; echo $password;
        // Input validations
        if (!empty($mobileno) && !empty($password)) {
            $sql = $this->db->query("SELECT * FROM driver WHERE mobile ='$mobileno' and password = '$password'");
            if ($sql->num_rows > 0) {

                $this->db->query("update driver set token = '$token', appPlatform = '$appplatform' where  mobile ='$mobileno'");

                $result = $sql->fetch_assoc();
                $result['password'] = '';
                $result['address'] = $result['address1'].' '.$result['address2'];

                if (!empty($result['img'])) {
                    $result['image'] = self::servername . self::driverimagepath . $result['img'];
                } else {
                    $result['image'] = self::servername . self::driverimagepath . 'UserImage.jpg';
                }
                if(!empty($result['insuarance_img'])){
                    $result['insuarance_img'] = self::servername . self::driverimagepath . $result['insuarance_img'];
                }
                if(!empty($result['licence_img'])){
                    $result['licence_img'] = self::servername . self::driverimagepath . $result['licence_img'];
                }
                if(!empty($result['adhar_img'])){
                    $result['adhar_img'] = self::servername . self::driverimagepath . $result['adhar_img'];
                }
                if(!empty($result['vechile_img'])){
                    $result['vechile_img'] = self::servername . self::driverimagepath . $result['vechile_img'];
                }

                $success = array('status' => "true", "details" => $result, "msg" => "Successfully Login");
                $this->response($this->json($success), 200);
            } else {
                $error = array('status' => "false", "msg" => "Invalid Mobile Number or Password");
                $this->response($this->json($error), 200);
            }
        } else {
            $error = array('status' => "false", "msg" => "Invalid Parameter");
            $this->response($this->json($error), 200);
        }
    }

    public function driverVerify_mobile() {
        $mobileno = $_REQUEST['mobileno'];

        $check_mobile = $this->db->query("select * from driver where mobile='$mobileno'");
        if ($check_mobile->num_rows > 0) {
            //$select_pass=mysql_query("update findam_users set password='".md5($new_passpord)."' where id='$userid'");

            $results = $check_mobile->fetch_assoc();
            $result['id'] = $results['id'];
            $success = array("status" => "true", "details" => $result, "msg" => "Mobile number exits");
            $this->response($this->json($success), 200);
        } else {
        	$code = rand(1000,9999);
            $message = "your 4 digit code ".$code;
           $aa =  $this->send_sms($mobileno,$message);
            $success = array("status" => "false", "msg" => "Mobile number not exits","otp"=>$code);
            $this->response($this->json($success), 200);
        }
    }

    /*
      Create function for update profile password
     */

    public function driverChange_password() {
        $id = $_REQUEST['id'];
        $password = $_REQUEST['password'];
        if (!empty($id) && !empty($password)) {
            $select_pass = $this->db->query("update driver set password='" . $password . "' where id='$id'");
            if ($select_pass) {

                $success = array("status" => "true", "msg" => "Password updated.");
                $this->response($this->json($success), 200);
            } else {
                $success = array("status" => "false", "msg" => "some error occured");
                $this->response($this->json($success), 200);
            }
        } else {
            $success = array("status" => "false", "msg" => "Invalid parameter");
            $this->response($this->json($success), 200);
        }
    }

    public function driverSignup() {
        $refcode = $_REQUEST['refcode'];
        $firstname = $_REQUEST['firstname'];
        $lastname = $_REQUEST['lastname'];
        $emailid = $_REQUEST['emailid'];
        $mobile_no = $_REQUEST['mobileno'];
        $password = $_REQUEST['password'];
        $gender = $_REQUEST['gender'];
        $city = $_REQUEST['city'];
        $country = $_REQUEST['country'];
        $lat = $_REQUEST['lat'];
        $lng = $_REQUEST['lng'];
        $token = $_REQUEST['token'];
        $appPlatform = $_REQUEST['appplatform'];
        
        $chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        $res = "";
        for ($i = 0; $i < 8; $i++) {
            $res .= $chars[mt_rand(0, strlen($chars) - 1)];
        }
        $driver_code = $res; 
        //echo $firstname; echo $emailid; echo $mobile_no; echo $password; echo $refcode; echo $city; echo $country;
        if (!empty($firstname) && !empty($emailid) && !empty($mobile_no) && !empty($password) && !empty($city) && !empty($country)) {
            $mobilenumcheck = $this->db->query("SELECT * FROM driver WHERE mobile ='$mobile_no'");

            $emailidcheck = $this->db->query("SELECT* FROM driver WHERE email ='$emailid'");

            if (isset($_FILES['image'])) {
                $uploadpath = "../uploads/drivers/";
                //$uploadpath = "../uploads/users/";
                $file_name = $_FILES['image']['name'];

                //$file_namearr = explode('.', $file_name);
                $ext = pathinfo($_FILES['image']['name'], PATHINFO_EXTENSION);
                
                $filename = time() . rand(10, 100) . '.' . $ext;
                
                $this->fileupload($uploadpath, $filename);
            }

            if ($mobilenumcheck->num_rows == 0 && $emailidcheck->num_rows == 0) {
                $insert = $this->db->query("INSERT INTO driver(`email`, `first_name`,`last_name`,`password`,`img`, `mobile`,`token`,`appPlatform`,`referal_code`,`city`,`country`,`latitude`,`longitude`,`driver_id`) VALUES ('$emailid','$firstname','$lastname','$password','$filename','$mobile_no','$token','$appPlatform','$refcode','$city','$country','$lat','$lng','$driver_code')");
                if ($insert > 0) {
                    $lastid = $this->db->insert_id;

                    $getuserdetails = array();
                    $getdetails = $this->db->query("select * from driver where id = $lastid");
                    if ($getdetails->num_rows > 0)
                        $getuserdetails = $getdetails->fetch_assoc();
                    $getuserdetails['password'] = '';
                    if (!empty($getuserdetails['img'])) {
                        $getuserdetails['image'] = self::servername . self::driverimagepath . $getuserdetails['img'];
                    } else {
                        $getuserdetails['image'] = self::servername . self::driverimagepath . 'UserImage.jpg';
                    }
                    /*$code = rand(1000,9999);
                    $message = "your 4 digit code ".$code;
                   $aa =  $this->send_sms($mobile_no,$message);*/
                    $errors = array("details" => $getuserdetails, 'status' => "true", "msg" => "successfully rgister");
                } else {
                    $errors = array('status' => "false", "msg" => "There is some issue in registration");
                }

                $this->response($this->json($errors), 200);
            } else {
                $error = array('status' => "false", "msg" => "mobile number or email id already exits");
                $this->response($this->json($error), 200);
            }
        } else {
            $error = array('status' => "false", "msg" => "Invalid Parameter");
            $this->response($this->json($error), 200);
        }
    }

    public function updateDriverinfo() {
        $driverid = $_REQUEST['driverid'];
        if (!empty($driverid)) {
            $query = "update driver set ";
            $field = 0;
            if (!empty($_REQUEST['dob'])) {
                $query .= "dob = '" . date('Y-m-d', strtotime($_REQUEST['dob'])) . "',";
                $field = 1;
              

            }
            if (!empty($_REQUEST['driving_licence'])) {
                $query .= "driving_licence = '" . $_REQUEST['driving_licence'] . "',";
                $field = 1;
            }
            if (!empty($_REQUEST['expiredate'])) {
                $query .= "expiredate = '" . date('Y-m-d', $_REQUEST['expiredate']) . "',";
                $field = 1;
            }
            if (!empty($_REQUEST['address1'])) {
                $query .= "address1 = '" . $_REQUEST['address1'] . "',";
                $field = 1;
            }
            if (!empty($_REQUEST['address2'])) {
                $query .= "address2 = '" . $_REQUEST['address2'] . "',";
                $field = 1;
            }
            if (!empty($_REQUEST['zipcode'])) {
                $query .= "zipcode = '" . $_REQUEST['zipcode'] . "',";
                $field = 1;
            }
            if (!empty($_REQUEST['social_secrityno'])) {
                $query .= "social_secrityno = '" . $_REQUEST['social_secrityno'] . "',";
                $field = 1;
            }

            if (!empty($_REQUEST['licence_plate'])) {
                $query .= "licence_plate = '" . $_REQUEST['licence_plate'] . "',";
                $field = 1;
            }

            if (!empty($_REQUEST['vechile_reg'])) {
                $query .= "vechile_reg = '" . $_REQUEST['vechile_reg'] . "',";
                $field = 1;
            }
            if (!empty($_REQUEST['vechile_reg'])) {
                $query .= "vechile_reg = '" . $_REQUEST['vechile_reg'] . "',";
                $field = 1;
            }
            if (!empty($_REQUEST['state'])) {
                $query .= "state = '" . $_REQUEST['state'] . "',";
                $field = 1;
            }
            if (!empty($_REQUEST['middle_name'])) {
                $query .= "middle_name = '" . $_REQUEST['middle_name'] . "',";
                $field = 1;
            }
            if (isset($_FILES['insuarance_img'])) {
                $uploadpath = "../uploads/drivers/";
                //$uploadpath = "../uploads/users/";
                $file_name = $_FILES['insuarance_img']['name'];
                //$file_namearr = explode('.', $file_name);
                $ext = pathinfo($_FILES['insuarance_img']['name'], PATHINFO_EXTENSION);
                $filename = time() . rand(10, 100) . '.' . $ext;
                $this->fileuploadinsuarance_img($uploadpath, $filename);
                $query .= "insuarance_img = '" . $filename . "',";
                $field = 1;
            }
            if (isset($_FILES['licence_img'])) {
                $uploadpath = "../uploads/drivers/";
                //$uploadpath = "../uploads/users/";
                $file_name = $_FILES['licence_img']['name'];
                //$file_namearr = explode('.', $file_name);
                $ext = pathinfo($_FILES['licence_img']['name'], PATHINFO_EXTENSION);
                $filename = time() . rand(10, 100) . '.' . $ext;
                $this->fileuploadlicence_img($uploadpath, $filename);
                $query .= "licence_img = '" . $filename . "',";
                $field = 1;
            }
            if (isset($_FILES['adhar_img'])) {
                $uploadpath = "../uploads/drivers/";
                //$uploadpath = "../uploads/users/";
                $file_name = $_FILES['adhar_img']['name'];
                //$file_namearr = explode('.', $file_name);
                $ext = pathinfo($_FILES['adhar_img']['name'], PATHINFO_EXTENSION);
                $filename = time() . rand(10, 100) . '.' . $ext;
                $this->fileuploadadhar_img($uploadpath, $filename);
                $query .= "adhar_img = '" . $filename . "',";
                $field = 1;
            }
            if (isset($_FILES['vechile_img'])) {
                $uploadpath = "../uploads/drivers/";
                //$uploadpath = "../uploads/users/";
                $file_name = $_FILES['vechile_img']['name'];
                //$file_namearr = explode('.', $file_name);
                $ext = pathinfo($_FILES['vechile_img']['name'], PATHINFO_EXTENSION);
                $filename = time() . rand(10, 100) . '.' . $ext;
                $this->fileuploadvechile_img($uploadpath, $filename);
                $query .= "vechile_img = '" . $filename . "',";
                $field = 1;
            }


            if ($field == 1) {
                $query = rtrim($query, ',');
                $query .= " where id = " . $driverid;
                $update = $this->db->query($query);
                if ($update) {

                    $sql = $this->db->query("SELECT * FROM driver WHERE id ='$driverid'");
                    $result = $sql->fetch_assoc();
                    $result['password'] = '';
                    $result['address'] = $result['address1'].' '.$result['address2'];
                    if (!empty($result['img'])) {
                        $result['image'] = self::servername . self::driverimagepath . $result['img'];
                    } else {
                        $result['image'] = self::servername . self::driverimagepath . 'UserImage.jpg';
                    }
                    if (!empty($result['img'])) {
                        $result['image'] = self::servername . self::driverimagepath . $result['img'];
                    } else {
                        $result['image'] = self::servername . self::driverimagepath . 'UserImage.jpg';
                    }
                    if(!empty($result['insuarance_img'])){
                        $result['insuarance_img'] = self::servername . self::driverimagepath . $result['insuarance_img'];
                    }
                    if(!empty($result['licence_img'])){
                        $result['licence_img'] = self::servername . self::driverimagepath . $result['licence_img'];
                    }
                    if(!empty($result['adhar_img'])){
                        $result['adhar_img'] = self::servername . self::driverimagepath . $result['adhar_img'];
                    }
                    if(!empty($result['vechile_img'])){
                        $result['vechile_img'] = self::servername . self::driverimagepath . $result['vechile_img'];
                    }
                    $error = array('status' => "true", "msg" => "Updated successfully","result"=>$result);
                    $this->response($this->json($error), 200);
                    } else {
                    $error = array('status' => "false", "msg" => "Some error occured" );
                    $this->response($this->json($error), 200);
                    }
            } else {
                $error = array('status' => "false", "msg" => "Please specify fields");
                $this->response($this->json($error), 200);
            }
        } else {
            $error = array('status' => "false", "msg" => "Invalid Parameter");
            $this->response($this->json($error), 200);
        }
    }

    public function getVehiclesubtype() {
        $typeid = $_REQUEST['typeid'];
        if (!empty($typeid)) {
            $getcolorres = $this->db->query("select * from vehicle_subtype_master where active_flag = 'yes' and vehicle_type_id = '$typeid'");
            $detail = array();
            while ($result = $getcolorres->fetch_assoc()) {
                $detail[] = $result;
            }
            $error = array('status' => "true", "detail" => $detail);
            $this->response($this->json($error), 200);
        } else {
            $error = array('status' => "false", "msg" => "Invalid Parameter");
            $this->response($this->json($error), 200);
        }
    }

    public function getVehicletype() {
        $getcolorres = $this->db->query("select * from vehicle_type_master where active_flag = 'yes'");
        $detail = array();
        while ($result = $getcolorres->fetch_assoc()) {
            $detail[] = $result;
        }
        $error = array('status' => "true", "detail" => $detail);
        $this->response($this->json($error), 200);
    }

    public function getColor() {
        $getcolorres = $this->db->query("select * from vehicle_colormaster");
        $detail = array();
        while ($result = $getcolorres->fetch_assoc()) {
            $detail[] = $result;
        }
        $error = array('status' => "true", "detail" => $detail);
        $this->response($this->json($error), 200);
    }

    public function getModal() {
        $getcolorres = $this->db->query("select * from vehicle_model");
        $detail = array();
        while ($result = $getcolorres->fetch_assoc()) {
            $detail[] = $result;
        }
        $error = array('status' => "true", "detail" => $detail);
        $this->response($this->json($error), 200);
    }

    public function updateDriverVechileinfo() {
        $driverid = $_REQUEST['driverid'];
        $vechile_type = $_REQUEST['vechile_type'];
        $vechile_subtype = $_REQUEST['vechile_subtype'];
        $model = $_REQUEST['modelid'];
        $color = $_REQUEST['colorid'];
        $makeyear = $_REQUEST['makeyear'];
        $noofdoor = $_REQUEST['noofdoor'];
        $noofsbelt = $_REQUEST['noofsbelt'];
        if (!empty($driverid) && !empty($model) && !empty($color) && !empty($makeyear) && !empty($noofdoor) && !empty($noofsbelt) && !empty($vechile_type) && !empty($vechile_subtype)) {

            //image update
            if (is_array($_FILES['image']['name'])) {
                $filename = 'multiple';
                $uploadpath = "../uploads/vechile/" . $userid . "/";
                foreach ($_FILES["image"]["tmp_name"] as $key => $tmp_name) {
                    $file_name = $_FILES["image"]["name"][$key];
                    $file_tmp = $_FILES["image"]["tmp_name"][$key];
                    $file_size = $_FILES["image"]["size"][$key];
                    $ext = pathinfo($file_name, PATHINFO_EXTENSION);
                    $randname = time() . rand(10, 100);
                    $newfilename = $randname . '.' . $ext;
                    $newfilename_arr[] = $newfilename;
                    if ($file_size < 88097152) {
                        move_uploaded_file($file_tmp = $_FILES["image"]["tmp_name"][$key], $uploadpath . $newfilename);
                    }
                }
                $this->db->query("delete from driver_vechileimg where driverid = $driverid");
                //image update
                foreach ($newfilename_arr as $value) {

                    $this->db->query("insert into driver_vechileimg set driverid = $driverid, img ='$value'");
                }
            }
            //echo '<pre>'; print_r($newfilename_arr); exit;

            $checkexits = $this->db->query("select id from driver_vechile where driverid = '$driverid'");
            if ($checkexits->num_rows > 0) {

                //echo "update driver_vechile set vechile_type = '$vechile_type', vechile_subtype = '$vechile_subtype', model = '$model', color = '$color', makeyear = '$makeyear', noofdoor = '$noofdoor', noofsbelt = '$noofsbelt' where driverid= $driverid";
                //update
                $update = $this->db->query("update driver_vechile set vechile_type = '$vechile_type', vechile_subtype = '$vechile_subtype', model = '$model', color = '$color', makeyear = '$makeyear', noofdoor = '$noofdoor', noofsbelt = '$noofsbelt' where driverid= $driverid");
                if ($update) {
                    $sql = $this->db->query("select * from driver_vechile where driverid = '$driverid'");
                    $result = $sql->fetch_assoc();
                    $sql = $this->db->query("select img from driver_vechileimg where driverid = '$driverid'");
                    $img=[];
                    while ($imgaes = $sql->fetch_assoc()) {
                        $img[]= self::servername . self::drivervechilepath.$imgaes['img'];
                    }
                    $result['vehicle_img'] = $img;
                    $error = array('status' => "true", "msg" => "successfully saved","result"=>$result);
                    $this->response($this->json($error), 200);
                } else {
                    $error = array('status' => "false", "msg" => "Some error occured");
                    $this->response($this->json($error), 200);
                }
            } else {
                //echo "insert into driver_vechile set vechile_type = '$vechile_type', vechile_subtype = '$vechile_subtype', model = '$model', color = '$color', makeyear = '$makeyear', noofdoor = '$noofdoor', noofsbelt = '$noofsbelt', driverid= $driverid";
                //insert
                $insert = $this->db->query("insert into driver_vechile set vechile_type = '$vechile_type', vechile_subtype = '$vechile_subtype', model = '$model', color = '$color', makeyear = '$makeyear', noofdoor = '$noofdoor', noofsbelt = '$noofsbelt', driverid= $driverid");
                if ($insert) {
                    $sql = $this->db->query("select * from driver_vechile where driverid = '$driverid'");
                    $result = $sql->fetch_assoc();
                    $sql = $this->db->query("select img from driver_vechileimg where driverid = '$driverid'");
                    $img=[];
                    while ($imgaes = $sql->fetch_assoc()) {
                        $img[]= self::servername . self::drivervechilepath.$imgaes['img'];
                    }
                    $result['vehicle_img'] = $img;
                    $error = array('status' => "true", "msg" => "successfully saved","result"=>$result);
                    $this->response($this->json($error), 200);
                } else {
                    $error = array('status' => "false", "msg" => "Some error occured");
                    $this->response($this->json($error), 200);
                }
            }
        } else {
            $error = array('status' => "false", "msg" => "Invalid parameter");
            $this->response($this->json($error), 200);
        }
    }

    public function getVehcledetails() {
        $getdetails = $this->db->query("select id,vehicle_type from vehicle_type_master where active_flag = 'yes'");
        $details = array();
        if ($getdetails->num_rows > 0) {

            while ($getvehicledetails = $getdetails->fetch_assoc()) {
                $typeid = $getvehicledetails['id'];


                
                $getvechileres = $this->db->query("select id,vehicle_model from vehicle_subtype_master where active_flag = 'yes' and vehicle_type_id = $typeid");
                while ($getvechile = $getvechileres->fetch_assoc()) {
                    $getvechile['selectimg'] = self::servername . '/uploads/select1.png';
                    $getvechile['nonselectimg'] = self::servername . '/uploads/nonSelect1.png';

                    $vechiledetal[] = $getvechile;
                }
                $getvehicledetailsArr = array(
                    'vehicleTypeId' => $getvehicledetails['id'],
                    'vehicleTypeName' => $getvehicledetails['vehicle_type'],
                    'vehicleType' => $vechiledetal
                );
//                $vechilarr['vehicleTypeName'] = $getvehicledetails['vehicle_type'];
//                $vechilarr['vehicleType'] = $vechiledetal;
                $details[] = $getvehicledetailsArr;
                $vechiledetal = '';
            }
            //echo '<pre>'; print_r($details);
            $error = array("details" => $details, 'status' => "true");
            $this->response($this->json($error), 200);
        } else {
            $error = array('status' => "false", "msg" => "No data found");
            $this->response($this->json($error), 200);
        }
    }

    public function driversearch() {
        $lat = $_REQUEST['lat'];
        $lng = $_REQUEST['lng'];

        if (!empty($lat) && !empty($lng)) {
            $range = self::RANGE;
            $searchdriverres = $this->db->query("SELECT id, first_name,last_name,latitude,longitude,
			6371 * 2 * ASIN(SQRT(POWER(SIN(RADIANS($lat - ABS(driver.latitude))), 2) + COS(RADIANS($lat)) * COS(RADIANS(ABS(driver.latitude))) * POWER(SIN(RADIANS($lng - driver.longitude)), 2))) AS distance
			FROM driver WHERE is_online = '1' AND is_available = '1' AND token != '' AND appPlatform != ''
			HAVING distance < $range
			ORDER BY distance");
            $details = array();
            if ($searchdriverres->num_rows > 0) {
                while ($driver = $searchdriverres->fetch_assoc()) {
                    $details[] = $driver;
                }
                $error = array("details" => $details, 'status' => "true");
            } else {
                $error = array('status' => "false", "msg" => "No driver found");
            }
        } else {
            $error = array('status' => "false", "msg" => "Invalid Parameter");
        }
        $this->response($this->json($error), 200);
    }

    public function locationUpdate() {
        $lat = $_REQUEST['lat'];
        $lng = $_REQUEST['lng'];
        $type = $_REQUEST['type'];
        $id = $_REQUEST['id'];
        if (!empty($lat) && !empty($lng) && !empty($type) && !empty($id)) {

            if ($type == 'driver') {
                $update = $this->db->query("update driver set latitude = $lat,longitude= $lng where id = $id");
            } else {
                $update = $this->db->query("update user set latitude = $lat,longitude= $lng where id = $id");
            }

            $getratingdetails = $this->db->query("SELECT AVG(rating) AS Averagerating FROM review_rating_user where user_id = '".$id."'");
           // print_r($getratingdetails);die();  
            $rating = $getratingdetails->fetch_assoc();


            

            if ($update)
                $error = array('status' => "true", "msg" => "location updated","avg_rating" => round($rating['Averagerating']));
            else
                $error = array('status' => "false", "msg" => "location not updated some error occured");
        }else {
            $error = array('status' => "false", "msg" => "Invalid Parameter");
        }
        $this->response($this->json($error), 200);
    }

    public function pushtestairtasker(){
        $push_array = array(
                        "alert" => 'Message',
                        "messageFrom" => 'testfrom',
                        "senderName" => 'designer',
                        "sound" => 'default'
                    );
        $this->send_push_fcm2('0A065FC6EFB23685406F5525D0F987EF6D4C0B0E59AF01EE046A97AEB6CD3C45',$push_array,'ios');
        $error  = array('test' => 'hi' );
          $this->response($this->json($error), 200);  
    }

    private function json($data) {
        if (is_array($data)) {
            return json_encode($data);
        }
    }

    public function fileupload2($path, $filename) {
        if (isset($_FILES['img'])) {
            $errors = array();
            //$file_name = $_FILES['image']['name'];
            $data = serialize($_FILES);

            //mail('kuldeep.s@parangat.com','Test file updload',$data);
            $file_size = $_FILES['img']['size'];
            $file_tmp = $_FILES['img']['tmp_name'];
            $file_type = $_FILES['img']['type'];
            $file_ext = pathinfo($_FILES['img']['name'], PATHINFO_EXTENSION);
            //$file_ext = strtolower(end(explode('.', $_FILES['img']['name'])));

            $expensions = array("jpeg", "jpg", "png", "JPEG", "JPG", "PNG");

            /* if(in_array($file_ext,$expensions)=== false){
              $errors[]="extension not allowed, please choose a JPEG or PNG file.";
              } */

            if ($file_size > 8809715255) {
                $errors[] = 'File size must be excately 88 MB';
            }

            if (empty($errors) == true) {
                move_uploaded_file($file_tmp, $path . $filename);
                return 1;
            } else {
                return $errors;
            }
        }
    }

    public function fileuploadinsuarance_img($path, $filename) {
        if (isset($_FILES['insuarance_img'])) {
            $errors = array();
            //$file_name = $_FILES['image']['name'];
            $data = serialize($_FILES);

            //mail('kuldeep.s@parangat.com','Test file updload',$data);
            $file_size = $_FILES['insuarance_img']['size'];
            $file_tmp = $_FILES['insuarance_img']['tmp_name'];
            $file_type = $_FILES['insuarance_img']['type'];
            $file_ext = strtolower(end(explode('.', $_FILES['insuarance_img']['name'])));

            $expensions = array("jpeg", "jpg", "png", "JPEG", "JPG", "PNG");

            /* if(in_array($file_ext,$expensions)=== false){
              $errors[]="extension not allowed, please choose a JPEG or PNG file.";
              } */

            if ($file_size > 88097152) {
                $errors[] = 'File size must be excately 88 MB';
            }

            if (empty($errors) == true) {
                move_uploaded_file($file_tmp, $path . $filename);
                return 1;
            } else {
                return $errors;
            }
        }
    }

    public function fileuploadlicence_img($path, $filename) {
        if (isset($_FILES['licence_img'])) {
            $errors = array();
            //$file_name = $_FILES['image']['name'];
            $data = serialize($_FILES);

            //mail('kuldeep.s@parangat.com','Test file updload',$data);
            $file_size = $_FILES['licence_img']['size'];
            $file_tmp = $_FILES['licence_img']['tmp_name'];
            $file_type = $_FILES['licence_img']['type'];
            $file_ext = strtolower(end(explode('.', $_FILES['licence_img']['name'])));

            $expensions = array("jpeg", "jpg", "png", "JPEG", "JPG", "PNG");

            /* if(in_array($file_ext,$expensions)=== false){
              $errors[]="extension not allowed, please choose a JPEG or PNG file.";
              } */

            if ($file_size > 88097152) {
                $errors[] = 'File size must be excately 88 MB';
            }

            if (empty($errors) == true) {
                move_uploaded_file($file_tmp, $path . $filename);
                return 1;
            } else {
                return $errors;
            }
        }
    }

    public function fileuploadadhar_img($path, $filename) {
        if (isset($_FILES['adhar_img'])) {
            $errors = array();
            //$file_name = $_FILES['image']['name'];
            $data = serialize($_FILES);

            //mail('kuldeep.s@parangat.com','Test file updload',$data);
            $file_size = $_FILES['adhar_img']['size'];
            $file_tmp = $_FILES['adhar_img']['tmp_name'];
            $file_type = $_FILES['adhar_img']['type'];
            $file_ext = strtolower(end(explode('.', $_FILES['adhar_img']['name'])));

            $expensions = array("jpeg", "jpg", "png", "JPEG", "JPG", "PNG");

            /* if(in_array($file_ext,$expensions)=== false){
              $errors[]="extension not allowed, please choose a JPEG or PNG file.";
              } */

            if ($file_size > 88097152) {
                $errors[] = 'File size must be excately 88 MB';
            }

            if (empty($errors) == true) {
                move_uploaded_file($file_tmp, $path . $filename);
                return 1;
            } else {
                return $errors;
            }
        }
    }

    public function fileuploadvechile_img($path, $filename) {
        if (isset($_FILES['vechile_img'])) {
            $errors = array();
            //$file_name = $_FILES['image']['name'];
            $data = serialize($_FILES);

            //mail('kuldeep.s@parangat.com','Test file updload',$data);
            $file_size = $_FILES['vechile_img']['size'];
            $file_tmp = $_FILES['vechile_img']['tmp_name'];
            $file_type = $_FILES['vechile_img']['type'];
            $file_ext = strtolower(end(explode('.', $_FILES['vechile_img']['name'])));

            $expensions = array("jpeg", "jpg", "png", "JPEG", "JPG", "PNG");

            /* if(in_array($file_ext,$expensions)=== false){
              $errors[]="extension not allowed, please choose a JPEG or PNG file.";
              } */

            if ($file_size > 88097152) {
                $errors[] = 'File size must be excately 88 MB';
            }

            if (empty($errors) == true) {
                move_uploaded_file($file_tmp, $path . $filename);
                return 1;
            } else {
                return $errors;
            }
        }
    }

    public function fileupload($path, $filename) {
        if (isset($_FILES['image'])) {

            $errors = array();
            //$file_name = $_FILES['image']['name'];
            $data = serialize($_FILES);

            //mail('kuldeep.s@parangat.com','Test file updload',$data);
            $file_size = $_FILES['image']['size'];
            $file_tmp = $_FILES['image']['tmp_name'];
            $file_type = $_FILES['image']['type'];
            $file_ext = strtolower(end(explode('.', $_FILES['image']['name'])));

            $expensions = array("jpeg", "jpg", "png", "JPEG", "JPG", "PNG");

            /* if(in_array($file_ext,$expensions)=== false){
              $errors[]="extension not allowed, please choose a JPEG or PNG file.";
              } */

            if ($file_size > 88097152) {
                $errors[] = 'File size must be excately 88 MB';
            }

            if (empty($errors) == true) {
                move_uploaded_file($file_tmp, $path . $filename);
                return 1;
            } else {
                return $errors;
            }
        }

    }

    private function send_push_fcm($token, $push_array, $for) {

        if ($for == 'android') {
            $path_to_firebase_cm = 'https://fcm.googleapis.com/fcm/send';

            $fields = array(
                'to' => $token,
                'data' => array('message' => $push_array)
            );
//            print_r($fields);
//            exit;
            $headers = array(
                'Authorization:key=AIzaSyAwP3qH6rye0p2TwhAGqOgbxb5YK3XQIzs',
                'Content-Type:application/json'
            );
            $ch = curl_init();

            curl_setopt($ch, CURLOPT_URL, $path_to_firebase_cm);
            curl_setopt($ch, CURLOPT_POST, true);
            curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
// curl_setopt($ch, CURLOPT_IPRESOLVE, CURL_IPRESOLVE_V4);
            curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));

            $result = curl_exec($ch);

            curl_close($ch);

//            print_r($result);
//            exit;
//            echo json_encode($result);
//            exit;
//mail('munesh.k@parangat.com', 'Findam push', $result);
            return $result;
        } else if ($for == "ios") {
            $payload = array();
            $payload['aps'] = $push_array;
            $payload = json_encode($payload);
            if ($PEM_MODE == '2') {
                $apnsCert = 'evolutiondevelopment.pem';
                $apnsHost = 'gateway.sandbox.push.apple.com';
            } else {
                $apnsHost = 'gateway.push.apple.com';
                $apnsCert = 'Certificates3.pem';
            }

// $production = 0; //make it 1 when use for production
// if ($production) {
// $apnsHost = 'gateway.push.apple.com';
// } else {
// $apnsHost = 'gateway.sandbox.push.apple.com';
// }
//$apnsHost = 'gateway.sandbox.push.apple.com';
            $apnsPort = 2195;

            $streamContext = stream_context_create();
            stream_context_set_option($streamContext, 'ssl', 'local_cert', $apnsCert);
            $apns = stream_socket_client('ssl://' . $apnsHost . ':' . $apnsPort, $error, $errorString, 60, STREAM_CLIENT_CONNECT, $streamContext);
            $device_token = str_replace(' ', '', trim($token));
// $apnsMessage = chr(0) . chr(0) . chr(32) . pack('H*', $device_token) . chr(0) . chr(strlen($payload)) . $payload;
            $apnsMessage = chr(0) . pack('n', 32) . pack('H*', $device_token) . pack('n', strlen($payload)) . $payload;
//echo $apnsMessage;]
            $messageMail = $apns . '----' . $apnsMessage . '' . $token . '' . $PEM_MODE;
// mail('nitesh.g@parangat.com','ios push evo',$messageMail);
            fwrite($apns, $apnsMessage);
            fclose($apns);
        }
    }

     private function send_push_fcm2($token, $push_array, $for) {

        if ($for == 'android') {
            $path_to_firebase_cm = 'https://fcm.googleapis.com/fcm/send';

            $fields = array(
                'to' => $token,
                'data' => array('message' => $push_array)
            );
//            print_r($fields);
//            exit;
            $headers = array(
                'Authorization:key=AIzaSyAwP3qH6rye0p2TwhAGqOgbxb5YK3XQIzs',
                'Content-Type:application/json'
            );
            $ch = curl_init();

            curl_setopt($ch, CURLOPT_URL, $path_to_firebase_cm);
            curl_setopt($ch, CURLOPT_POST, true);
            curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
// curl_setopt($ch, CURLOPT_IPRESOLVE, CURL_IPRESOLVE_V4);
            curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));

            $result = curl_exec($ch);

            curl_close($ch);

//            print_r($result);
//            exit;
//            echo json_encode($result);
//            exit;
//mail('munesh.k@parangat.com', 'Findam push', $result);
            return $result;
        } else if ($for == "ios") {
            $payload = array();
            $payload['aps'] = $push_array;
            $payload = json_encode($payload);
            if ($PEM_MODE == '2') {
                $apnsCert = 'airtasker.pem';
                $apnsHost = 'gateway.sandbox.push.apple.com';
            } else {
                $apnsHost = 'gateway.push.apple.com';
                $apnsCert = 'airtasker.pem';
            }
echo $apnsHost;
// $production = 0; //make it 1 when use for production
// if ($production) {
// $apnsHost = 'gateway.push.apple.com';
// } else {
// $apnsHost = 'gateway.sandbox.push.apple.com';
// }
//$apnsHost = 'gateway.sandbox.push.apple.com';
            $apnsPort = 2195;

            $streamContext = stream_context_create();
            stream_context_set_option($streamContext, 'ssl', 'local_cert', $apnsCert);
            $apns = stream_socket_client('ssl://' . $apnsHost . ':' . $apnsPort, $error, $errorString, 60, STREAM_CLIENT_CONNECT, $streamContext);
            $device_token = str_replace(' ', '', trim($token));
// $apnsMessage = chr(0) . chr(0) . chr(32) . pack('H*', $device_token) . chr(0) . chr(strlen($payload)) . $payload;
            $apnsMessage = chr(0) . pack('n', 32) . pack('H*', $device_token) . pack('n', strlen($payload)) . $payload;
//echo $apnsMessage;]
            $messageMail = $apns . '----' . $apnsMessage . '' . $token . '' . $PEM_MODE;
// mail('nitesh.g@parangat.com','ios push evo',$messageMail);
            $result = fwrite($apns, $apnsMessage);
            echo '<pre>'; print_r($apns); die;
            fclose($apns);
        }
    }

    /********************* Twillo function ***********************
Description @ function to send otp on user mobile number
@param : (string) (mobile): user mobile 
                  (otp) : otp which is send to user email or mobile phone                 
@return : (int) (numRow) : send otp on user's mobile phone
******************************************************************/
public function send_sms($mobile,$message){ 
    //echo 'sd';die();
  //
  require('../twilio-php/Services/Twilio.php'); 
  $account_sid = 'AC7c0ff5aa391cc180b4be0e6d37a4b057'; // arrive5 app
  $auth_token = 'ca9914d7b199dc9e51532397745e8ada';    //arrive5 app
  $client = new Services_Twilio($account_sid, $auth_token);
  //print_r($client);die();
 
  try {
    //echo 'sd';die;
    $message = $client->account->messages->create(array( 
    'To' => $mobile,
    //'From' => "+18632926809", //for arrive5 app
    'From' => "+18634001988", //for arrive5 app
    'Body' => $message,  
    ));
    //print_r($message);die();
    return true ;
  }
  catch (Services_Twilio_RestException $e){
    //print_r($e->getMessage());exit;
    return $e->getMessage();
  } 
}

}





// Initiiate Library

$api = new API;
$api->processApi();
?>
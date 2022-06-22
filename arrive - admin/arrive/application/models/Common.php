<?php
if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class Common extends CI_Model {

    public function query($sql = '') {
        return $this->db->query($sql)->result();
    }

    public function countQuery($sql = '') {
        $this->db->query($sql);
        return $this->db->count_all_results();
    }

    public function find($table, $select = '*', $where = array(), $resultType = 'array', $orderby = array()) {

        $this->db->select($select);
        $this->db->from($table);
        if (!empty($where)) {
            foreach ($where as $key => $val) {
                $this->db->where($key, $val);
            }
        }

        if (!empty($orderby)) {
            foreach ($orderby as $key => $val) {
                $this->db->order_by($key, $val);
            }
        }

        $query = $this->db->get();

        if ($resultType == 'objects') {
            $result = $query->first_row();
        } else {
            $result = $query->first_row('array');
        }
//        echo $this->db->last_query();
//        die();
        return $result;
    }

    public function findAll($table, $select = '*', $where = array(), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '') {
        $this->db->select($select);
        $this->db->from($table);
        if (!empty($where)) {
            foreach ($where as $key => $val) {
                $this->db->where($key, $val);
            }
        }
        if (!empty($groupby)) {
            $this->db->group_by($groupby);
        }

        if (!empty($orderby)) {
            foreach ($orderby as $key => $val) {
                $this->db->order_by($key, $val);
            }
        }

        if (!empty($limit)) {
            $this->db->limit($limit);
        }

        $query = $this->db->get();
//        echo $this->db->get_compiled_select();
//        echo $this->db->last_query();
//        die();
        if ($resultType == 'objects') {
            $result = $query->result();
        } else {
            $result = $query->result_array();
        }

        return $result;
    }
    
    
    

    public function findAllWhereIn($table, $select = '*', $wherein = array(), $orderby = array(), $resultType = 'array', $limit = '') {

        $this->db->select($select);
        $this->db->from($table);
        if (!empty($wherein)) {
            foreach ($wherein as $key => $val) {
                $this->db->where_in($key, $val);
            }
        }
        if (!empty($where)) {
            foreach ($wherein as $key => $val) {
                $this->db->where_in($key, $val);
            }
        }

        if (!empty($orderby)) {
            foreach ($orderby as $key => $val) {
                $this->db->order_by($key, $val);
            }
        }

        if (!empty($limit)) {
            $this->db->limit($limit);
        }

        $query = $this->db->get();

        if ($resultType == 'objects') {
            $result = $query->result();
        } else {
            $result = $query->result_array();
        }
//        echo $this->db->last_query();
//        die();
        return $result;
    }

    public function findAllWhereNotIn($table, $select = '*', $wherein = array(), $orderby = array(), $resultType = 'array', $limit = '') {

        $this->db->select($select);
        $this->db->from($table);
        if (!empty($wherein)) {
            foreach ($wherein as $key => $val) {
                $this->db->where_not_in($key, $val);
            }
        }

        if (!empty($orderby)) {
            foreach ($orderby as $key => $val) {
                $this->db->order_by($key, $val);
            }
        }

        if (!empty($limit)) {
            $this->db->limit($limit);
        }

        $query = $this->db->get();

        if ($resultType == 'objects') {
            $result = $query->result();
        } else {
            $result = $query->result_array();
        }
        //echo $this->db->last_query();
        return $result;
    }

    public function findAllWithJoin($table, $select = '*', $join = array(), $where = array(), $orderby = array(), $resultType = 'array', $limit = '',$groupby = '') {

        $this->db->select($select);
        $this->db->from($table);
        if (!empty($join)) {
            foreach ($join as $k => $j) {
                $this->db->join($k, $j, 'LEFT');
            }
        }
        if (!empty($where)) {
            foreach ($where as $key => $val) {
                $this->db->where($key, $val);
            }
        }
        if(!empty($groupby)){
            $this->db->group_by($groupby); 
        }
        if (!empty($orderby)) {
            foreach ($orderby as $key => $val) {
                $this->db->order_by($key, $val);
            }
        }

        if (!empty($limit)) {
            $this->db->limit($limit);
        }

        $query = $this->db->get();

//        echo $this->db->last_query();
//        exit;

        if ($resultType == 'objects') {
            $result = $query->result();
        } else {
            $result = $query->result_array();
        }

        return $result;
    }

    public function delete($tablename, $where = array()) {
        $this->db->delete($tablename, $where);
    }

    public function runQuery($query) {
        $query = $this->db->query($query);
        $result = $query->result_array();
        return $result;
    }

    public function runQuerySingle($query) {
        $query = $this->db->query($query);
        $result = $query->first_row('array');
        return $result;
    }

    public function save($tablename, $post = array()) {

        if (is_object($post)) {
            if (isset($post->id) && $post->id > 0) {
                $update = $this->update($tablename, $post, array('id' => $post->id));
                if (!$update) {
                    return 0;
                } else {
                    return $post->id;
                }
            } else {
                $insert = $this->db->insert($tablename, $post);
                if (!$insert) {
                    return 0;
                } else {
                    return $this->db->insert_id();
                }
            }
        } elseif (is_array($post)) {
            if (isset($post['id']) && $post['id'] > 0) {
                $update = $this->update($tablename, $post, array('id' => $post['id']));

                if (!$update) {
                    return 0;
                } else {
                    return $post['id'];
                }
            } else {
                $insert = $this->db->insert($tablename, $post);
//                echo $this->db->last_query();
//                die();
                if (!$insert) {
                    return 0;
                } else {
                    return $this->db->insert_id();
                }
            }
        }
    }

    public function saveBatch($tablename, $post = array()) {
        $insert = $this->db->insert_batch($tablename, $post);
        if (!$insert) {
            return 0;
        } else {
            return $this->db->insert_id();
        }
    }

    public function update($tablename, $post, $where = array()) {

        if (!empty($where)) {
            foreach ($where as $key => $val) {
                $this->db->where($key, $val);
            }
        }
        $update = $this->db->update($tablename, $post);
        if (!$update) {
            return false;
        } else {
            return true;
        }
//        echo $this->db->last_query();
//        exit;
    }

    public function generateJobCode() {
        $chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        $res = "";
        for ($i = 0; $i < 8; $i++) {
            $res .= $chars[mt_rand(0, strlen($chars) - 1)];
        }
        return $res;
    }

    public function total($tblanme, $where = array()) {
        $this->db->select('id');
        $this->db->from($tblanme);

        if (!empty($where)) {
            foreach ($where as $key => $val) {
                if (!empty($val)) {
                    $this->db->where($key, $val);
                }
            }
        }

        return $this->db->count_all_results();
    }

    public function login($email_phone, $password) {
        $query = $this->db->query("SELECT * FROM user WHERE (email = '" . $email_phone . "' OR phone = '" . $email_phone . "') AND password = '" . $password . "'");

        $result = $query->result_array();
        return $result;
    }

    public function upcomig_booking() {
        $query = $this->db->query("SELECT *,SUBTIME(schedule_time, '00:30:00') as booktime FROM booking WHERE mode = '12' AND schedule_date = CURDATE()");

        $result = $query->result_array();
        return $result;
    }

    public function get_notification($userId) {
        $query = $this->db->query("SELECT title, msg FROM admin_notification WHERE FIND_IN_SET('1', '" . $userId . "')");

        $result = $query->result_array();
        return $result;
    }

    public function booking_requested_list($driverId) {
        $query = $this->db->query("SELECT b.id AS bookingId, b.booking_id AS bookingCode, b.user_id, b.start_point, b.end_point, bd.mode, b.no_passanger, b.no_luggage, b.duration, b.distance, u.name, u.avatar_img_id FROM booking b INNER JOIN booking_driver bd ON bd.booking_id = b.id INNER JOIN user u ON u.id = b.user_id WHERE bd.driver_id = $driverId AND bd.mode = '0'");

        $result = $query->result_array();
        return $result;
    }
    
    public function booking_based_on_user_id($id){  
        //echo "Select * from booking where (user_id = '".$id."' OR driver_id = '".$id."') And mode != '18' ";die();
        $query = $this->db->query("Select * from booking where (user_id = '".$id."') And ( mode = '1' OR mode = '6' OR mode = '7' OR mode = '10' OR mode = '12') ");
        $result = $query->result_array();
        
       $driverPath = base_url() . 'uploads/drivers/';
         $userPath = base_url() . 'uploads/users/';
            if(!empty($result)){
                if(!empty($result[0]['user_id'])){
                    $userData = $this->common->find('user', 'id, first_name ,last_name, email,img', array('id' => $result[0]['user_id']), 'array');
                    $driverData = $this->common->find('driver', 'id, first_name,middle_name,last_name,licence_plate, email,img,latitude,longitude,mobile', array('id' => $result[0]['driver_id']), 'array');
                    if(!empty($driverData['vechile_img'])){
                        $result[0]['driverImg'] = ($driverData['vechile_img'])?$driverPath.$driverData['vechile_img']:'';
                    }else{
                        $result[0]['driverImg'] = ($driverData['img'])?$driverPath.$driverData['img']:'';
                    }
                    $getratingdetails = $this->avgRating($result[0]['driver_id']);
                    $vehicleSubType = $this->find('vehicle_subtype_master', $select = 'id, vehicle_model', $where = array('id' => $result[0]['vehicle_sub_type_id']), $resultType = 'array', $orderby = array());

                    $result[0]['driverName'] = $driverData['first_name'].' '.$driverData['middle_name']. ' ' .$driverData['last_name'];
                    $result[0]['driverEmail'] = $driverData['email'];
                    $result[0]['mobile'] = $driverData['mobile'];
                    $result[0]['driver_longitude'] = $driverData['longitude'];
                    $result[0]['driver_latitude'] = $driverData['latitude'];
                    $result[0]['carNo'] = $driverData['licence_plate'];
                    $result[0]['userName'] = $userData['first_name'] . ' ' . $userData['last_name'];
                    $result[0]['userImg'] = ($userData['img'])?$userPath.$userData['img']:'';
                    $result[0]['vehicleSubTypeName'] = ($vehicleSubType['vehicle_model'])?$vehicleSubType['vehicle_model']:'';
                    $result[0]['driver_rating'] = empty($getratingdetails[0]['Averagerating'])?'0':$getratingdetails[0]['Averagerating'];   
                                    }
            }else{
                $result = array();
            }
        return $result;
    }
    
    public function booking_based_on_driver_id($id){  
        $query = $this->db->query("Select * from booking where (driver_id = '".$id."') And (mode = '0' OR mode = '1' OR mode = '6' OR mode = '7' OR mode = '10' OR mode = '11') ");
                 //echo $this->db->last_query();die();

        $result = $query->result_array();
         $driverPath = base_url() . 'uploads/drivers/';
         $userPath = base_url() . 'uploads/users/';
         if(!empty($result)){
            if(($result[0]['driver_id']!='0')){
                $driverData = $this->common->find('driver', 'id, first_name,middle_name,last_name, email,img', array('id' => $result[0]['driver_id']), 'array');
                $userData = $this->common->find('user', $select = 'id, first_name, last_name, img, token, appPlatform', $where = array('id' => $result[0]['user_id']), $resultType = 'array', $orderby = array());

                if(!empty($driverData['vechile_img'])){
                    $result[0]['driverImg'] = ($driverData['vechile_img'])?$driverPath.$driverData['vechile_img']:'';

                }else{
                    $result[0]['driverImg'] = ($driverData['img'])?$driverPath.$driverData['img']:'';

                }
                $getratingdetails = $this->avgRating($result[0]['user_id']);
                
                $vehicleSubType = $this->find('vehicle_subtype_master', $select = 'id, vehicle_model', $where = array('id' => $result[0]['vehicle_sub_type_id']), $resultType = 'array', $orderby = array());

                
                
                $result[0]['driverName'] = $driverData['first_name'].' '.$driverData['middle_name']. ' ' .$driverData['last_name'];
                $result[0]['driverEmail'] = $driverData['email'];
                $result[0]['userName'] = $userData['first_name'] . ' ' . $userData['last_name'];
                $result[0]['userImg'] = ($userData['img'])?$userPath.$userData['img']:'';
                $result[0]['vehicleSubTypeName'] = ($vehicleSubType['vehicle_model'])?$vehicleSubType['vehicle_model']:'';
                $result[0]['user_rating'] = empty($getratingdetails[0]['Averagerating'])?'0':$getratingdetails[0]['Averagerating'];
                //$result[0]['drivernick_name'] = $userData['nick_name'];
                
            }else{
               $result[0]['driverEmail'] = '';
                $result[0]['driverName'] = '';
                $result[0]['driverImg'] = '';

            }

         }else{
            $result = array();

         }
         
         //print_r($result);die();

        return $result;
    }

    public function user_chat_request_list($userId) {
        $query = $this->db->query("SELECT b.id AS bookingId, b.booking_id AS bookingCode, b.user_id, b.start_point, b.end_point, bd.mode, b.no_passanger, b.no_luggage, b.duration, b.distance, u.name, bd.driver_id, u.avatar_img_id FROM booking b INNER JOIN booking_driver bd ON bd.booking_id = b.id INNER JOIN user u ON u.id = bd.driver_id WHERE b.user_id = $userId AND bd.mode = '4'");

        $result = $query->result_array();
        return $result;
    }

    public function driver_search($lat,$lng){
        
        
        $query = $this->db->query("SELECT id, first_name,last_name,latitude,longitude,
            6371 * 2 * ASIN(SQRT(POWER(SIN(RADIANS($lat - ABS(driver.latitude))), 2) + COS(RADIANS($lat)) * COS(RADIANS(ABS(driver.latitude))) * POWER(SIN(RADIANS($lng - driver.longitude)), 2))) AS distance FROM driver WHERE is_online = '1' AND is_available = '1' AND token != '' AND appPlatform != '' HAVING distance < '2000' ORDER BY distance");
        $result = $query->result_array();
       // echo $this->db->last_query();
        //die;
        return $result;

    }

    public function pre_chat($senderId, $receiverId) 
	{
        $sql = 'SELECT * FROM chat WHERE (sender_id = "' . $senderId . '" AND receiver_id = "' . $receiverId . '") OR (sender_id = "' . $receiverId . '" AND receiver_id = "' . $senderId . '")';

        $query = $this->db->query($sql);
        $result = $query->result_array();
        return $result;
    }

    public function social_media_login($socialMediaData) {

        if ($socialMediaData['fb_id'] != '') {

            $query = $this->db->query("SELECT * FROM user WHERE facebook_id = '" . $socialMediaData['fb_id'] . "'");
        } elseif ($socialMediaData['google_id'] != '') {
            $query = $this->db->query("SELECT * FROM user WHERE google_id = '" . $socialMediaData['google_id'] . "'");
        }


        $result = $query->result_array();
        return $result;
    }

    public function get_driver_in_range($lat, $long, $userId) {
        $range = $this->common->find('admin', $select = 'find_driver_range', $where = array('email_id' => 'admin@creb.com'), $resultType = 'array', $orderby = array());
        $driverFindRange = $range['find_driver_range'];

        $query = $this->db->query("select d.id , d.first_name, d.middle_name, d.last_name, d.token , d.appPlatform, d.latitude , d.longitude, d.is_available, d.is_online, ( 3959 * acos( cos( radians( '" . $lat . "' ) ) * cos( radians( d.latitude ) ) * cos( radians( d.longitude ) - radians( '" . $long . "' ) ) + sin( radians( '" . $lat . "' ) ) * sin( radians( d.latitude ) ) ) ) AS distance from driver d where d.status = '1' AND d.is_available = '1' AND d.is_online = '1' AND d.status = '1' AND d.id != '" . $userId . "' AND token != '' having distance <= '" . $driverFindRange . "' order by distance");

        $result = $query->result_array();

        return $result;
    }

    public function driversearch($lat, $lng) {

//     if (!empty($lat) && !empty($lng)) {
        $range = $this->common->find('admin', $select = 'find_driver_range', $where = array('email_id' => 'admin@arrive.com'), $resultType = 'array', $orderby = array());
        $driverFindRange = $range['find_driver_range'];
        $searchdriverres = $this->db->query("SELECT id, first_name,last_name,latitude,longitude,
			6371 * 2 * ASIN(SQRT(POWER(SIN(RADIANS($lat - ABS(driver.latitude))), 2) + COS(RADIANS($lat)) * COS(RADIANS(ABS(driver.latitude))) * POWER(SIN(RADIANS($lng - driver.longitude)), 2))) AS distance
			FROM driver WHERE is_online = '1' AND is_available = '1' AND token != '' AND appPlatform != ''
			HAVING distance < $driverFindRange
			ORDER BY distance");
        $result = $searchdriverres->result_array();

        return $result;
    }

    public function avgRating($user_id){
        $qry = $this->db->query("SELECT AVG(rating) AS Averagerating FROM review_rating_user where user_id = '".$user_id."'");
        $result = $qry->result_array();

        return $result;


    }

    public function driveravgRating($user_id){
        $qry = $this->db->query("SELECT AVG(rating) AS Averagerating FROM review_rating where driver_id = '".$user_id."'");
        $result = $qry->result_array();

        return $result;


    }

    public function user_ride($currentDate, $userId, $type) {
        $avatarPath = base_url() . 'assets/upload/avatar/';
        if ($type == 'past') {
            $query = $this->db->query("select b.id, b.booking_id, ,b.no_passanger, b.no_luggage, b.user_id, b.start_point, b.end_point, b.mode, b.schedule_date, b.schedule_time, b.booking_id, u.name, u.avatar_img_id from booking b left join user u on u.id = b.driver_id where (b.user_id = $userId OR b.driver_id = $userId) AND b.schedule_date < '$currentDate' AND b.mode = '11' order by b.id DESC limit 10");
        } elseif ($type == 'upcoming') {
            $query = $this->db->query("select b.id, b.no_passanger, b.no_luggage, b.booking_id, b.user_id, b.start_point, b.end_point, b.mode, b.schedule_date, b.schedule_time, b.booking_id, u.name, u.avatar_img_id from booking b left join user u on u.id = b.driver_id where (b.user_id = $userId OR b.driver_id = $userId) AND b.schedule_date < '$currentDate' AND b.mode != '11' order by b.id DESC limit 10");
        }
        $pastRide = $query->result_array();

        $userPastRides = array();
        foreach ($pastRide as $key => $value) {

            $avatarImg = $this->common->find('avatar', 'id, name, img', array('id' => $value['avatar_img_id']), 'array');

            $scheduleDate = date("j M Y", strtotime($value['schedule_date']));

            $userPastRidesArr = array(
                'bookingId' => $value['id'],
                'bookingCode' => $value['booking_id'],
                'no_passanger' => $value['no_passanger'],
                'no_luggage' => $value['no_luggage'],
//                'userId' => $value['user_id'],
                'name' => (($value['name'] != '') ? $value['name'] : ''),
                'img' => (($avatarImg['img'] != '') ? $avatarPath . $avatarImg['img'] : ''),
                'start_point' => $value['start_point'],
                'end_point' => $value['end_point'],
                'mode' => $value['mode'],
                'scheduleDate' => $scheduleDate,
                'scheduleTime' => $value['schedule_time']
            );
            $userPastRides[] = $userPastRidesArr;
        }
        return $userPastRides;
    }

    public function get_driving_distance($lat1, $lat2, $long1, $long2) 
	{

        trim($lat1);
        trim($lat2);
        trim($long1);
        trim($long2);

        $url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" . $lat1 . "," . $long1 . "&destinations=" . $lat2 . "," . ltrim($long2) . "&mode=driving&key=AIzaSyBZa5z5jemcwuK7hawrBiZUC5ExFWQvdk8";
       // echo $url;die();

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_URL, $url);
        $result = curl_exec($ch);
        curl_close($ch);

        $response_a = json_decode($result, true);
        //print_r($response_a);die();

        $dist = $response_a['rows'][0]['elements'][0]['distance']['text'];
        $time = $response_a['rows'][0]['elements'][0]['duration']['text'];

        return array('distance' => $dist, 'time' => $time);
    }

    public function generate_otp($digits = 4) {
        $i = 0; //counter
        $pin = ""; //our default pin is blank.
        while ($i < $digits) {
            //generate a random number between 0 and 9getVehcledetails.
            $pin .= mt_rand(0, 9);
            $i++;
        }
        return $pin;
    }


    public function getPromoCodeList($userId){
        $query = $this->db->query("SELECT id,promo_code,promo_value,promo_type_name,discount,valid_to FROM promo_code WHERE status = '1' AND CURDATE() between valid_from and valid_to AND FIND_IN_SET('$userId', user_id) <> 0;");
        $result = $query->result_array();
        return $result;

    }

    public function checkPromoCode($promo_code){
         $query = $this->db->query("SELECT * FROM promo_code WHERE status = '1' AND CURDATE() between valid_from and valid_to AND promo_code = '$promo_code'");
        $result = $query->first_row('array');
        return $result;

    }

    public function get_time_slot($schedule_time) {

        $hours = array_shift(explode(':', $schedule_time));

        if ($hours < 12) {

            return "morning";
        } elseif ($hours > 11 && $hours < 18) {

            return "afternoon";
        } elseif ($hours > 17) {

            return "evening";
        }
    }




    function send_user_push_fcm($token, $push_array, $for, $PEM_MODE = '2') {


        if ($for == 'android') 
		{

            $path_to_firebase_cm = 'https://fcm.googleapis.com/fcm/send';

            $fields = array(
                'to' => $token,
//            'notification' => array('message' => $push_array)
//                'notification' => array('body' => $push_array), // runing code
                'data' => $push_array
            );


            $headers = array(
                'Authorization:key=AIzaSyCRbml5Webcr3Z3TyIMEOzsfBTdRzt9Gps',
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
//            echo'<pre>';
//            print_r(json_encode($fields));
//            die();
            $result = curl_exec($ch);

            curl_close($ch);
            $email = "mansi.v@parangat.com";
            mail($email, "GCM Response", $result);
//            echo'<pre>';
//            print_r($result);
//            die();
            return $result;
        } 
		else if (($for == "ios") || ($for == "iphone")) 
        {
			/*$url = "https://fcm.googleapis.com/fcm/send";
			//$token = "cE_9WWYswf0:APA91bFLKwWD-1P9pMNRpOhk1PTKgz1WGZxgy1MczIEqTQ9KRAgREKowZjuIRbORSZimP6jOxYGAHI_nFofOwUEWq_V0mWGxkUOEXnFLq9rHRFswDE9K84tD15ZbWn9vBsPQwUbGd5Xf";
			$serverKey = 'AAAAUC0i0PE:APA91bGRd1d6jS1Ke4rGPrvwVoJvwFXNZ2se2xJ3rHR5TjOuSK97MiSiEruWBYbwc1T9auz_aH7PhswGxDWQpNINhyMNpdQfLtgXFK6yYem87nX3lyzyXFq_BiX3KO0oks9KL1_r9eLy';
			//$title = "this is testing";
			//$body = "Here Jitu nagar";
			$notification = $push_array;
			$arrayToSend = array('to' => $token, 'notification' => $notification,'priority'=>'high');
			$json = json_encode($arrayToSend);
			$headers = array();
			$headers[] = 'Content-Type: application/json';
			$headers[] = 'Authorization: key='. $serverKey;
			$ch = curl_init();
			curl_setopt($ch, CURLOPT_URL, $url);

			curl_setopt($ch, CURLOPT_CUSTOMREQUEST,

			"POST");
			curl_setopt($ch, CURLOPT_POSTFIELDS, $json);
			curl_setopt($ch, CURLOPT_HTTPHEADER,$headers);
			//Send the request
			return $response = curl_exec($ch);
			 //request
			curl_close($ch);*/
             $url = "https://fcm.googleapis.com/fcm/send";
            $serverKey = 'AAAAUC0i0PE:APA91bGRd1d6jS1Ke4rGPrvwVoJvwFXNZ2se2xJ3rHR5TjOuSK97MiSiEruWBYbwc1T9auz_aH7PhswGxDWQpNINhyMNpdQfLtgXFK6yYem87nX3lyzyXFq_BiX3KO0oks9KL1_r9eLy';
            $notification = $push_array;
            $arrayToSend = array('to' => $token, 'notification' => $notification,'priority'=>'high');
            $json = json_encode($arrayToSend);
            $headers = array();
            $headers[] = 'Content-Type: application/json';
            $headers[] = 'Authorization: key='. $serverKey;
            $ch = curl_init();
            curl_setopt($ch, CURLOPT_URL, $url);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_CUSTOMREQUEST,"POST");
            curl_setopt($ch, CURLOPT_POSTFIELDS, $json);
            curl_setopt($ch, CURLOPT_HTTPHEADER,$headers);
            //$response = curl_exec($ch);
            $output = curl_exec($ch);
            curl_close($ch);

        }
    }

    function send_driver_push_fcm($token, $push_array, $for, $PEM_MODE = '2') 
	{
		if ($for == 'android') 
		{
            $path_to_firebase_cm = 'https://fcm.googleapis.com/fcm/send';
            $fields = array
            (
                'to' => $token,
                'data' => $push_array
            );
            $headers = array(
                'Authorization:key=AAAAUC0i0PE:APA91bGRd1d6jS1Ke4rGPrvwVoJvwFXNZ2se2xJ3rHR5TjOuSK97MiSiEruWBYbwc1T9auz_aH7PhswGxDWQpNINhyMNpdQfLtgXFK6yYem87nX3lyzyXFq_BiX3KO0oks9KL1_r9eLy', 
                'Content-Type:application/json'
            );
            $ch = curl_init();
            curl_setopt($ch, CURLOPT_URL, $path_to_firebase_cm);
            curl_setopt($ch, CURLOPT_POST, true);
            curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
            curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
            $result = curl_exec($ch);
            curl_close($ch);
            
			$email = "jitu.maestros@gmail.com";
            mail($email, "GCM Response", $result);
           return $result;
        } 
		elseif (($for == "ios") || ($for == "iphone")) 
		{
            $url = "https://fcm.googleapis.com/fcm/send";
			$serverKey = 'AAAAUC0i0PE:APA91bGRd1d6jS1Ke4rGPrvwVoJvwFXNZ2se2xJ3rHR5TjOuSK97MiSiEruWBYbwc1T9auz_aH7PhswGxDWQpNINhyMNpdQfLtgXFK6yYem87nX3lyzyXFq_BiX3KO0oks9KL1_r9eLy';
			$notification = $push_array;
			$arrayToSend = array('to' => $token, 'notification' => $notification,'priority'=>'high');
			$json = json_encode($arrayToSend);
			$headers = array();
			$headers[] = 'Content-Type: application/json';
			$headers[] = 'Authorization: key='. $serverKey;
			$ch = curl_init();
			curl_setopt($ch, CURLOPT_URL, $url);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
			curl_setopt($ch, CURLOPT_CUSTOMREQUEST,"POST");
			curl_setopt($ch, CURLOPT_POSTFIELDS, $json);
			curl_setopt($ch, CURLOPT_HTTPHEADER,$headers);
			//$response = curl_exec($ch);
            $output = curl_exec($ch);
            curl_close($ch);
        } 
    }

    public function send_push($token, $push_array, $app_platform) {
        if ($app_platform == "iphone") {
           /* $payload = array();
            $payload['aps'] = $push_array;
            $payload = json_encode($payload);
            $apnsHost = 'gateway.push.apple.com';
            $apnsPort = 2195;
            //$apnsCert = 'ekabadiCert.pem';
            $apnsCert = 'ekabadiPushCert.pem';
            $streamContext = stream_context_create();
            stream_context_set_option($streamContext, 'ssl', 'local_cert', $apnsCert);
            $apns = stream_socket_client('ssl://' . $apnsHost . ':' . $apnsPort, $error, $errorString, 60, STREAM_CLIENT_CONNECT, $streamContext);
            $device_token = str_replace(' ', '', trim($token));
            $apnsMessage = chr(0) . chr(0) . chr(32) . pack('H*', $device_token) . chr(0) . chr(strlen($payload)) . $payload;
            fwrite($apns, $apnsMessage);
            fclose($apns);*/
			$url = "https://fcm.googleapis.com/fcm/send";
			//$token = "cE_9WWYswf0:APA91bFLKwWD-1P9pMNRpOhk1PTKgz1WGZxgy1MczIEqTQ9KRAgREKowZjuIRbORSZimP6jOxYGAHI_nFofOwUEWq_V0mWGxkUOEXnFLq9rHRFswDE9K84tD15ZbWn9vBsPQwUbGd5Xf";
			$serverKey = 'AAAAUC0i0PE:APA91bGRd1d6jS1Ke4rGPrvwVoJvwFXNZ2se2xJ3rHR5TjOuSK97MiSiEruWBYbwc1T9auz_aH7PhswGxDWQpNINhyMNpdQfLtgXFK6yYem87nX3lyzyXFq_BiX3KO0oks9KL1_r9eLy';
			//$title = "this is testing";
			//$body = "Here Jitu nagar";
			$notification = $push_array;
			$arrayToSend = array('to' => $token, 'notification' => $notification,'priority'=>'high');
			$json = json_encode($arrayToSend);
			$headers = array();
			$headers[] = 'Content-Type: application/json';
			$headers[] = 'Authorization: key='. $serverKey;
			$ch = curl_init();
			curl_setopt($ch, CURLOPT_URL, $url);

			curl_setopt($ch, CURLOPT_CUSTOMREQUEST,

			"POST");
			curl_setopt($ch, CURLOPT_POSTFIELDS, $json);
			curl_setopt($ch, CURLOPT_HTTPHEADER,$headers);
			//Send the request
			return $response = curl_exec($ch);
			 //request
			curl_close($ch);
        } else {
			
            $registatoin_ids = str_replace(' ', '', trim($token));
            $message = $push_array;
            if (!is_array($message)) {
                $message = array("message" => "Something went wrong");
                echo $message;
            }
            $url = 'https://android.googleapis.com/gcm/send';
            //$registatoin_ids
            $fields = array(
                'registration_ids' => array($registatoin_ids),
                'delay_while_idle' => false,
                //'time_to_live' => 20,
                'data' => $message
            );
            //echo implode(',',$registatoin_ids);exit;
            $headers = array(
                //'Authorization: key= AIzaSyCLPwmfGykYVDqn7_ms7i_p-HazC-tATiU',
                //'Authorization: key= AIzaSyAH3guV1145M5-NujII1x-chYuKgXI-lTM', // GOOGLE_API_KEY,
                'Authorization: key= AAAAUC0i0PE:APA91bGRd1d6jS1Ke4rGPrvwVoJvwFXNZ2se2xJ3rHR5TjOuSK97MiSiEruWBYbwc1T9auz_aH7PhswGxDWQpNINhyMNpdQfLtgXFK6yYem87nX3lyzyXFq_BiX3KO0oks9KL1_r9eLy',
                'Content-Type: application/json'
            );
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
//		echo "====>".$token."<br><br>";
            // Execute post
            $result = curl_exec($ch);
            //echo $result;
            if ($result === FALSE) {
                die('Curl failed: ' . curl_error($ch));
            }
            //echo "++++".$result;
            //die();
            // Close connection
            curl_close($ch);
            //$email = "mcashiv@gmail.com";
//            mail("nitesh.g@parangat.com", "GCM Response admin ekabadi", $result);

            return $result;
        }
    }

    public function agent_push($token, $push_array, $app_platform) {
        if ($app_platform == "iphone") 
		{
           /* $payload = array();
            $payload['aps'] = $push_array;
            $payload = json_encode($payload);
            $apnsHost = 'gateway.push.apple.com';
            $apnsPort = 2195;
            //$apnsCert = 'ekabadiCert.pem';
            $apnsCert = 'ekabadiPushCert.pem';
            $streamContext = stream_context_create();
            stream_context_set_option($streamContext, 'ssl', 'local_cert', $apnsCert);
            $apns = stream_socket_client('ssl://' . $apnsHost . ':' . $apnsPort, $error, $errorString, 60, STREAM_CLIENT_CONNECT, $streamContext);
            $device_token = str_replace(' ', '', trim($token));
            $apnsMessage = chr(0) . chr(0) . chr(32) . pack('H*', $device_token) . chr(0) . chr(strlen($payload)) . $payload;
            fwrite($apns, $apnsMessage);
//            mail("nitesh.g@parangat.com", "Iphone Response e-kabadi admin", $apnsMessage);
            fclose($apns);*/
			$url = "https://fcm.googleapis.com/fcm/send";
			//$token = "cE_9WWYswf0:APA91bFLKwWD-1P9pMNRpOhk1PTKgz1WGZxgy1MczIEqTQ9KRAgREKowZjuIRbORSZimP6jOxYGAHI_nFofOwUEWq_V0mWGxkUOEXnFLq9rHRFswDE9K84tD15ZbWn9vBsPQwUbGd5Xf";
			$serverKey = 'AAAAUC0i0PE:APA91bGRd1d6jS1Ke4rGPrvwVoJvwFXNZ2se2xJ3rHR5TjOuSK97MiSiEruWBYbwc1T9auz_aH7PhswGxDWQpNINhyMNpdQfLtgXFK6yYem87nX3lyzyXFq_BiX3KO0oks9KL1_r9eLy';
			//$title = "this is testing";
			//$body = "Here Jitu nagar";
			$notification = $push_array;
			$arrayToSend = array('to' => $token, 'notification' => $notification,'priority'=>'high');
			$json = json_encode($arrayToSend);
			$headers = array();
			$headers[] = 'Content-Type: application/json';
			$headers[] = 'Authorization: key='. $serverKey;
			$ch = curl_init();
			curl_setopt($ch, CURLOPT_URL, $url);

			curl_setopt($ch, CURLOPT_CUSTOMREQUEST,

			"POST");
			curl_setopt($ch, CURLOPT_POSTFIELDS, $json);
			curl_setopt($ch, CURLOPT_HTTPHEADER,$headers);
			//Send the request
			return $response = curl_exec($ch);
			 //request
			curl_close($ch);
        } else {
            $registatoin_ids = str_replace(' ', '', trim($token));
            $message = $push_array;
            if (!is_array($message)) {
                $message = array("message" => "Something went wrong");
                echo $message;
            }
            $url = 'https://android.googleapis.com/gcm/send';
            //$registatoin_ids
            $fields = array(
                'registration_ids' => array($registatoin_ids),
                'delay_while_idle' => false,
                //'time_to_live' => 20,
                'data' => $message
            );
            //echo implode(',',$registatoin_ids);exit;
            $headers = array(
                //'Authorization: key= AIzaSyCLPwmfGykYVDqn7_ms7i_p-HazC-tATiU',
                //'Authorization: key= AIzaSyAH3guV1145M5-NujII1x-chYuKgXI-lTM', // GOOGLE_API_KEY,
                'Authorization: key= AIzaSyCAnlDDvZhzF3vIkUY4R03cb468jj31fVw',
                'Content-Type: application/json'
            );
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
//		echo "====>".$token."<br><br>";
            // Execute post
            $result = curl_exec($ch);
            //echo $result;
            if ($result === FALSE) {
                die('Curl failed: ' . curl_error($ch));
            }
            //echo "++++".$result;
            //die();
            // Close connection
            curl_close($ch);
            //$email = "mcashiv@gmail.com";
//            mail("nitesh.g@parangat.com", "GCM Response ekabadi admin agent", $result);

            return $result;
        }
    }

    public function getMessageSendCommon($id) {
        $message = $this->find('templates', '*', array('id' => $id));
        return $message;
    }

    public function insertPushNotificationAlert($notificationdetail, $userid, $type = '5') {
        $postDataArr = array(
            'notificationdetail' => $notificationdetail,
            'userid' => $userid,
            'notificationtime' => date('Y-m-d h:i:s'),
            'type' => $type
        );
        $id = $this->save('appnotifications', $postDataArr);
        return $id;
    }

    public function tranferCashAction($operator, $number, $amount, $orderid) {
        $ch = curl_init();
        $resultR = array();
        $timeout = 60; // set to zero for no timeout"
//            $myurl = "https://www.pay2all.in/recharge/api?username=919717452447&password=Vagator123&company=".$operator."&number=".$number."&amount=".$amount."&orderid=".$orderid;

        $myurl = "https://www.pay2all.in/web-api/paynow?api_token=mBh5qj2OWv6wwH2RY9aydt3HzQkbv62uzU7A0dz7EvbyXWbvM9GIXNnp9i5s&number=" . $number . "&provider_id=" . $operator . "&amount=" . $amount . "&client_id=" . $orderid;

        curl_setopt($ch, CURLOPT_URL, $myurl);
        curl_setopt($ch, CURLOPT_HEADER, 0);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, $timeout);
        $file_contents = curl_exec($ch);
        curl_close($ch);
        $maindata = json_decode($file_contents);

        $resultR['payid'] = $maindata->payid;
        $resultR['status'] = trim($maindata->status);
        //   $transactionOperatinIDD = explode('TRNXSTATUS',$maindata[2]);
//            $resultR['transactionOperatinIDD'] = explode('TRNXSTATUS',$maindata[2]);
//            $resultR['operatortransactionid'] = trim($transactionOperatinIDD[0]);
        $resultR['transactionOperatinIDD'] = $maindata->operator_ref;
        $resultR['operatortransactionid'] = $maindata->operator_ref;

        return $resultR;
    }
    
    public function sqlGetQuery($sql) {
        $data= $this->db->query($sql);
        return $data->result_array();
    }
    
     public function sqlInsertQuery($sql) {
        $data= $this->db->query($sql);
        return $data;
    }

    function send_sms($mobile, $message) {
        /* print_r($mobile);
          print_r($message);exit;
         */
        //require(APPPATH . 'libraries/twilio-php-master/Services/Twilio.php');

        $account_sid = 'AC58b2c83bad7d39821c39d1877463b90f'; // prepaired app
        $auth_token = 'f917a2dcd0f6729818bff5fc77d305e9';    //prepaired app
        $client = new Services_Twilio($account_sid, $auth_token);

        try {
            $message = $client->account->messages->create(array(
                'To' => '+' . $mobile,
                'From' => "+447481363020",
                'Body' => $message,
            ));
            //print_r($message);exit;
            return true;
        } catch (Services_Twilio_RestException $e) {
            // print_r($e->getMessage());exit;
            return $e->getMessage();
        }
    }
    function fetchAll($select,$table,$where){
       return  $this->db->select($select) ->from($table)->where($where)->get()->result();  
    }
    function fetchSingle($select,$table,$where){
       return  $this->db->select($select)
                ->from($table)
                ->where($where)
                ->get()
                ->row_array();  
    }

    public function getHighpayingList($userId)
    {
        $query = $this->db->query("SELECT * FROM high_paying_zone WHERE status = '1'");
        $result = $query->result_array();
        return $result;

    }

     public function getTerms()
    {
        $query = $this->db->query("SELECT * FROM faq WHERE status = '1' and title='Terms & Condition'");
        $result = $query->result_array();
        return $result;

    }

     public function getCondition()
    {
        $query = $this->db->query("SELECT * FROM faq WHERE status = '1' and title='Privacy Policy'");
        $result = $query->result_row();
        return $result;

    }

      public function getDriverratingList($driver_id)
      {

    $this->db->select('AVG(rating) as rating');
    $this->db->where('driver_id',$driver_id);
    $result=$this->db->get('review_rating')->row();
    //echo($this->db->last_query());
    //die;
    return $result;
 
    }

     public function businessProfileList($userId)
    {
        $query = $this->db->query("SELECT * FROM add_business_profile WHERE status = '1' and user_id='".$userId."'");
        $result = $query->result_array();
        return $result;

    }

      public function businessProfileListdata($userId,$type_id)
    {
        $query = $this->db->query("SELECT * FROM add_business_profile WHERE status = '1' and user_id='".$userId."' and type='".$type_id."'");
        $result = $query->result_array();
        return $result;

    }


     public function saveBusinessProfile($tablename, $post = array()) {

        if (is_object($post)) {
            
                $insert = $this->db->insert($tablename, $post);
                if (!$insert) {
                    return 0;
                } else {
                    return $this->db->insert_id();
                }
            
        } elseif (is_array($post)) {
                           $insert = $this->db->insert($tablename, $post);
//                echo $this->db->last_query();
//                die();
                if (!$insert) {
                    return 0;
                } else {
                    return $this->db->insert_id();
                }
            
        }
    }


    public function updateBusinessProfile($tablename, $post = array()) {

        if (is_object($post)) {
            
                $update = $this->update($tablename, $post, array('user_id' => $post->user_id,'type' => $post->type));
                if (!$update) {
                    return 0;
                } else {
                    return $post->id;
                }
           
        } elseif (is_array($post)) {
            
                $update = $this->update($tablename, $post, array('user_id' => $post['user_id'],'type' =>$post['type']));
                 //echo $this->db->last_query();
                //die();
                if (!$update) {
                    return 0;
                } else {
                    return $post['user_id'];
                }
           
                
        }
    }


    function distanceCalculation($lat1, $long1, $lat2, $long2) 
    {


trim($lat1);
trim($lat2);
trim($long1);
trim($long2);

$url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" . $lat1 . "," . $long1 . "&destinations=" . $lat2 . "," . ltrim($long2) . "&key=AIzaSyA61ixbb477y554WAVTc-YgllCphU8vcjo";

$ch = curl_init();
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_URL, $url);
$result = curl_exec($ch);
curl_close($ch);
$response_a = json_decode($result, true);
$res = array();
// pr($response_a);
if (!isset($response_a['rows'][0]['elements'][0]['distance']['text'])) {
$dist = '';
$time = '';
$res = array('distance' => $dist, 'time' => $time);
} else {
$dist = isset($response_a['rows'][0]['elements'][0]['distance']['text']) ? $response_a['rows'][0]['elements'][0]['distance']['text'] : 0;
$time = isset($response_a['rows'][0]['elements'][0]['duration']['text']) ? $response_a['rows'][0]['elements'][0]['duration']['text'] : 0;
$res = array('distance' => $dist, 'time' => $time);
}

return $res;
}


 public function get_mypickup_data($driver_id) {
    $type="schedule later";
       
    $query = $this->db->query("SELECT * FROM `booking` WHERE `driver_id` = '".$driver_id."' AND `booking_type` = 'schedule later'  AND `status` = '1'");
        $result = $query->result_array();
       

        return $result;
    }


     public function get_availablepick_data($driver_id) {
    $type="schedule later";
    $query = $this->db->query("SELECT * FROM `booking` WHERE `driver_id` = '0' AND `booking_type` = 'schedule later'  AND `status` = '1'");
        $result = $query->result_array();
        return $result;
    }

     public function login_data($mobileno,$password) {

      $query = $this->db->query("SELECT * FROM `user` WHERE `mobile` = '".$mobileno."' AND `password` = '".$password."'");
      $result = $query->result_array();
      return $result;
    }

    public function avg_rate($id){
         $query = $this->db->query("SELECT AVG(rating) AS Averagerating FROM review_rating_user where user_id = $id");
        $result = $query->result_array();
        return $result;

    } 
    public function getStaticGmapURLForDirection($origin, $destination, $waypoints, $size = "500x250") {
    $markers = array();
    $waypoints_labels = array("A", "B");
    $waypoints_label_iter = 0;

    $markers[] = "markers=color:green" . urlencode("|") . "label:" . urlencode($waypoints_labels[$waypoints_label_iter++] . '|' . $origin);
    // foreach ($waypoints as $waypoint) {
    //     $markers[] = "markers=color:blue" . urlencode("|") . "label:" . urlencode($waypoints_labels[$waypoints_label_iter++] . '|' . $waypoint);
    // }
    $markers[] = "markers=color:red" . urlencode("|") . "label:" . urlencode($waypoints_labels[$waypoints_label_iter] . '|' . $destination);
    //print_r($markers);die();

    $url = "https://maps.googleapis.com/maps/api/directions/json?origin=$origin&destination=$destination&key=AIzaSyClkiUQxIeczQEjTrw-dy9h2fj9qjXDk_4&waypoints=" . implode($waypoints, '|');
   // print_r($url);die();

    $ch = curl_init();
    //print_r($ch);die();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POST, false);
    $result = curl_exec($ch);
    curl_close($ch);
    $googleDirection = json_decode($result, true);
    //print_r($result);die();

    $polyline = urlencode($googleDirection['routes'][0]['overview_polyline']['points']);
    $markers = implode($markers, '&');

    return "https://maps.googleapis.com/maps/api/staticmap?size=$size&maptype=roadmap&path=enc:$polyline&$markers&key=AIzaSyClkiUQxIeczQEjTrw-dy9h2fj9qjXDk_4";
}
 
    
    

public function getTotalTrip($driverId,$frm_date,$to_date) {
    
        $query = $this->db->query("SELECT count(id) as count_trip FROM `booking` WHERE `driver_id` = $driverId and mode='4' and `schedule_date` >= '".$frm_date."' AND `schedule_date` <= '".$to_date."'");

        $result = $query->result_array();
        return $result;
    }
    
    public function getTotalTripByWeek($driverId,$frm_date,$to_date) {
    
        $query = $this->db->query("SELECT schedule_date FROM `booking` WHERE `driver_id` = $driverId and mode='4' and `schedule_date` >= '".$frm_date."' AND `schedule_date` <= '".$to_date."'");

        $result = $query->result_array();
        return $result;
    }
    
    
    public function getAllDriverTrip($driverId) {
    
        $query = $this->db->query("SELECT  * ,count(id) as t_count FROM `booking` WHERE `driver_id` = $driverId and mode='4'");

        $result = $query->result_array();
        return $result;
    }
    
public function getAvgRate($booking_id)
{
    $this->db->select('AVG(rating) as avg_rate');
    $this->db->where('booking_id',$booking_id);
$result=$this->db->get('review_rating_user')->row();
  return $result;
}







}

<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

/**
 * Database helper
 *
 * @package		Helpers
 * @category            database helper
 * @author		Ankit Rajput
 * @website		http://www.tekshapers.com
 * @company             Tekshapers Inc 
 * @since		Version 1.0
 */

/**
 * is_protected
 *
 * This function check user already login or not
 * 
 * @access	public
 * @return	boolean
 */
//FIND DISTANCE BETWEEN TWO LAT LONG
//function distanceCalculation($point1_lat, $point1_long, $point2_lat, $point2_long, $unit = 'km', $decimals = 2) {
//    // Calculate the distance in degrees
//    $degrees = rad2deg(acos((sin(deg2rad($point1_lat)) * sin(deg2rad($point2_lat))) + (cos(deg2rad($point1_lat)) * cos(deg2rad($point2_lat)) * cos(deg2rad($point1_long - $point2_long)))));
//
//    // Convert the distance in degrees to the chosen unit (kilometres, miles or nautical miles)
//    switch ($unit) {
//        case 'km':
//            $distance = $degrees * 111.13384; // 1 degree = 111.13384 km, based on the average diameter of the Earth (12,735 km)
//            break;
//        case 'mi':
//            $distance = $degrees * 69.05482; // 1 degree = 69.05482 miles, based on the average diameter of the Earth (7,913.1 miles)
//            break;
//        case 'nmi':
//            $distance = $degrees * 59.97662; // 1 degree = 59.97662 nautic miles, based on the average diameter of the Earth (6,876.3 nautical miles)
//    }
//    return round($distance, $decimals);
//}

function distanceCalculation($lat1, $long1, $lat2, $long2) {

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
//    pr($response_a);
    if (!isset($response_a['rows'][0]['elements'][0]['distance']['text'])) {
        $dist = '';
        $time = '';
        $res = array('distance' => $dist, 'time' => $time);
    } else {
        $dist = isset($response_a['rows'][0]['elements'][0]['distance']['text']) ? $response_a['rows'][0]['elements'][0]['distance']['text'] : 0;
        $time = isset($response_a['rows'][0]['elements'][0]['duration']['text']) ? $response_a['rows'][0]['elements'][0]['duration']['text'] : 0;
        $res = array('distance' => $dist, 'time' => $time);
    }
//    
//    print_r($res);
//    die();
    return $res;
}

function TwoLatLongDistanceForDiscoveryCalculation($lat1, $long1, $columns, $distance, $whr, $page) {
    $CI = &get_instance();
    $offsetLimit = '';
    $res = array();
//        echo $page;die;
    if (!empty($page)) {
        $offset = $page == 1 ? 0 : ($page * 10) - 10;
        $limit = 10;
        $offsetLimit = "LIMIT $offset, $limit";
    }

    $sql = "SELECT $columns, ( 3959 * acos ( cos ( radians(" . $lat1 . ") ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(" . $long1 . ") ) + sin ( radians(" . $lat1 . ") ) * sin( radians( latitude ) ) ) ) AS distance FROM users WHERE $whr HAVING distance < $distance AND distance != '' ORDER BY distance ASC $offsetLimit";
    $query = $CI->db->query($sql);
//        last_query();die;
    if ($query->num_rows) {
        $res = $query->result_array();
    }
    return $res;
}

function getVenchaDistanceBetweenTwoLatLong($lat1, $long1, $columns, $distance, $whr, $page) {
    $CI = &get_instance();
    $offsetLimit = '';
    $res = array();
//        echo $page;die;
    if (!empty($page)) {
        $offset = $page == 1 ? 0 : ($page * 10) - 10;
        $limit = 10;
        $offsetLimit = "LIMIT $offset, $limit";
    }

    $sql = "SELECT $columns, ( 3959 * acos ( cos ( radians(" . $lat1 . ") ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(" . $long1 . ") ) + sin ( radians(" . $lat1 . ") ) * sin( radians( latitude ) ) ) ) AS distance FROM users as u left join users_checkin as uc ON uc.user_id = u.id WHERE $whr GROUP by (u.id) HAVING distance < $distance AND distance != '' ORDER BY distance ASC $offsetLimit";
    $query = $CI->db->query($sql);
//        last_query();die;
    if ($query->num_rows) {
        $res = $query->result_array();
    }
    return $res;
}

function getMatchedUserDistanceBetweenTwoLatLong($lat1, $long1, $columns, $distance, $whr, $page) {
    $CI = &get_instance();
    $offsetLimit = '';
    $res = array();
//        echo $page;die;
    if (!empty($page)) {
        $offset = $page == 1 ? 0 : ($page * 10) - 10;
        $limit = 10;
        $offsetLimit = "LIMIT $offset, $limit";
    }

    $sql = "SELECT $columns, ( 3959 * acos ( cos ( radians(" . $lat1 . ") ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(" . $long1 . ") ) + sin ( radians(" . $lat1 . ") ) * sin( radians( latitude ) ) ) ) AS distance FROM users WHERE $whr HAVING distance < $distance AND distance != '' ORDER BY distance ASC $offsetLimit";
    $query = $CI->db->query($sql);
//        last_query();die;
    if ($query->num_rows) {
        $res = $query->result_array();
    }
    return $res;
}

function TwoLatLongDistanceCalculation($lat1, $long1, $columns, $page, $distance) {
    $CI = &get_instance();
    $offsetLimit = '';
    $res = array();
//        echo $page;die;
    if (!empty($page)) {
        $offset = $page == 1 ? 0 : ($page * 10) - 10;
        $limit = 10;
        $offsetLimit = "LIMIT $offset, $limit";
    }
    $sql = "SELECT $columns, ( 3959 * acos ( cos ( radians(" . $lat1 . ") ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(" . $long1 . ") ) + sin ( radians(" . $lat1 . ") ) * sin( radians( latitude ) ) ) ) AS distance FROM venue HAVING distance < $distance AND status='1' ORDER BY distance ASC $offsetLimit";
    $query = $CI->db->query($sql);
//        last_query();die;
    if ($query->num_rows) {
        $res = $query->result_array();
    }
    return $res;
}

function getEventWithLatLongRange($lat1, $long1, $columns, $page, $whr, $distance) {
    $CI = &get_instance();
    $offsetLimit = '';
    $res = array();
//echo $page;die;
    if (!empty($page)) {
        $offset = $page == 1 ? 0 : ($page * 10) - 10;
        $limit = 10;
        $offsetLimit = "LIMIT $offset, $limit";
    }
    $sql = "SELECT $columns, ( 3959 * acos ( cos ( radians(" . $lat1 . ") ) * cos( radians( e.latitude ) ) * cos( radians( e.longitude ) - radians(" . $long1 . ") ) + sin ( radians(" . $lat1 . ") ) * sin( radians( e.latitude ) ) ) ) AS distance FROM events as e inner join venue as v ON v.id = e.venue_id WHERE $whr HAVING distance < $distance ORDER BY e.start_date ASC $offsetLimit";
    $query = $CI->db->query($sql);
//    last_query();
//    die;
    if ($query->num_rows) {
        $res = $query->result_array();
    }
    return $res;
}

//THIS FUNCTION GET LIMITED DATA 
if (!function_exists('getdata')) {

    function getdata($table = "", $attributes = "", $whr = "") {
        $CI = &get_instance();
        if (!empty($whr)) {
            $sql = "select $attributes from $table WHERE $whr ";
        } else {
            $sql = "select $attributes from $table";
        }
        $query = $CI->db->query($sql);
//        last_query();die;
        if ($query->num_rows) {
            return $query->result_array();
        }
        return false;
    }

}
//THIS FUNCTION GET LIMITED DATA 
if (!function_exists('getlimitdata')) {

    function getlimitdata($table = "", $attributes = "", $whr = "", $page = null) {
        $offsetLimit = '';
//        echo $page;die;
        if (!empty($page)) {
            $offset = $page == 1 ? 0 : ($page * 10) - 10;
            $limit = 10;
            $offsetLimit = "LIMIT $offset, $limit";
        }
        $CI = &get_instance();
        if (!empty($whr)) {
            $sql = "select $attributes from $table WHERE $whr $offsetLimit ";
        } else {
            $sql = "select $attributes from $table $offsetLimit ";
        }
        $query = $CI->db->query($sql);
//        last_query();die;
        if ($query->num_rows) {
            return $query->result_array();
        }
        return false;
    }

}
/**
 * send_user_push_fcm
 *
 * send push notification to ios and android app.
 * 
 * @access	public
 * @return	boolean
 */
//send push notification to ios and android app.
if (!function_exists('send_user_push_fcm')) {

    function send_user_push_fcm($token, $push_array, $for, $PEM_MODE = '2') {
        echo $for;
        die;

        if ($for == 'android') {
            $path_to_firebase_cm = 'https://fcm.googleapis.com/fcm/send';

            $fields = array(
                'to' => $token,
                'data' => $push_array
            );
            $headers = array(
                'Authorization:key=AIzaSyB0i5PqT81x81lOSsyV4697AzN67v-sXB4',
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
            mail('ashish.k@parangat.com', 'vencha push', $result);
            return $result;
        } else if (($for == "ios") || ($for == "iphone")) {

            $payload = array();
            $payload['aps'] = $push_array;
            $payload = json_encode($payload);

            if ($PEM_MODE == '2') {
                $apnsCert = 'venchaDevelopment.pem';
                $apnsHost = 'gateway.sandbox.push.apple.com';
            } else {
                $apnsHost = 'gateway.push.apple.com';
                $apnsCert = 'Certificates.pem';
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
            $messageMail = $apns . '----' . $apnsMessage . '' . $token . '' . $PEM_MODE;
            mail('ashish.k@parangat.com', 'ios user push', $messageMail);
            pr($messageMail);
            die;
            $result = fwrite($apns, $apnsMessage);
            fclose($apns);
        }
    }

}
//THIS FUNCTION GET DATA THROUGH ID
if (!function_exists('getdatathroughid')) {

    function getdatathroughid($table = "", $attributes = "", $id = "", $whr = "") {
        $CI = &get_instance();
        $CI->db->select($attributes);
        if (!empty($id)) {
            $CI->db->where('id', $id);
        }
//        echo $whr;
        if (empty($whr)) {
            $CI->db->where($whr);
        }
        $query = $CI->db->get($table);
//        last_query();
        if ($query->num_rows()) {
            return $query->result_array();
        }
        return false;
    }

}

if (!function_exists('upload_image')) {

    function upload_image($field_name, $img_file_name, $folder_name) {
//        pr($_FILES[]);
        $img_ectsn = explode(".", $img_file_name);
        $CI = &get_instance();
        $CI->load->library('upload');

        $config['upload_path'] = './assets/uploads/' . "$folder_name";
        $config['allowed_types'] = 'gif|jpg|png|jpeg';
//        $config['max_size'] = '30480';
        $config['overwrite'] = true;
        $config['encrypt'] = true;
        $new_name = time() . '_' . rand(3, 1000) . '.' . $img_ectsn[1];

        $config['file_name'] = $new_name;
        $dir = './assets/uploads';
        if (!is_dir($dir)) {
            mkdir($dir, 0777, TRUE);
        }
        if (!is_dir($config['upload_path'])) {
            mkdir($config['upload_path'], 0777, TRUE);
        }

        $CI->upload->initialize($config);
        $CI->load->library('upload', $config);
//pr($config);die;
        if (!$CI->upload->do_upload($field_name)) {

            return $CI->upload->display_errors();
        } else {

            $upload_data = $CI->upload->data();
            return $new_name;
        }
    }

}

function multiimage_uploads($field_name, $img_file_name, $folder_name) {
    $this->load->library('upload');
    $dataInfo = array();
    $files = $_FILES;
    $cpt = count($_FILES[$field_name]['name']);
    for ($i = 0; $i < $cpt; $i++) {
        $_FILES[$field_name]['name'] = $files[$field_name]['name'][$i];
        $_FILES[$field_name]['type'] = $files[$field_name]['type'][$i];
        $_FILES[$field_name]['tmp_name'] = $files[$field_name]['tmp_name'][$i];
        $_FILES[$field_name]['error'] = $files[$field_name]['error'][$i];
        $_FILES[$field_name]['size'] = $files[$field_name]['size'][$i];

        $this->upload->initialize($this->set_upload_options($folder_name));
        $this->upload->do_upload();
        $dataInfo = $this->upload->data();
    }
    return $dataInfo;
}

function set_upload_options($folder_name) {
    //upload an image options
    $config = array();
    $config['upload_path'] = './assets/uploads/' . "$folder_name";
    $config['allowed_types'] = 'gif|jpg|png|jpeg';
    $config['max_size'] = '0';
    $config['overwrite'] = FALSE;

    return $config;
}

if (!function_exists('mkdir_upload_image')) {

    function mkdir_userimage_upload($field_name, $img_file_name, $user_id) {
//        pr($_FILES[]);
        $img_ectsn = explode(".", $img_file_name);
        $CI = &get_instance();
        $CI->load->library('upload');
        $config['upload_path'] = './assets/uploads/';
//        $config['upload_path'] = './assets/uploads/' . $user_id;
        $config['allowed_types'] = 'gif|jpg|png|jpeg';
//      $config['max_size'] = '2048000';
        $config['overwrite'] = true;
        $config['encrypt'] = true;
        $new_name = time() . '_' . rand(3, 1000) . '.' . $img_ectsn[1];

        $config['file_name'] = $new_name;

        $dir = './assets/uploads';
        if (!is_dir($dir)) {
            mkdir($dir, 0777, TRUE);
        }
        if (!is_dir($config['upload_path'])) {
            mkdir($config['upload_path'], 0777, TRUE);
        }

        $CI->upload->initialize($config);
        $CI->load->library('upload', $config);
        if (!$CI->upload->do_upload($field_name)) {
//            pr($CI->upload->display_errors());die;

            return $CI->upload->display_errors();
        } else {
//            pr($CI->upload->data());die;

            $upload_data = $CI->upload->data();
            return $new_name;
        }
    }

}
if (!function_exists('mkdir_upload_image')) {

    function mkdir_userimage_uploads($field_name, $img_file_name, $user_id) {
//        pr($_FILES);
        $img_ectsn = explode(".", $img_file_name);
        $CI = &get_instance();
        $CI->load->library('upload');
        $config['upload_path'] = './assets/uploads/users';
        $config['allowed_types'] = 'gif|jpg|png|jpeg';
//      $config['max_size'] = '2048000';
        $config['overwrite'] = true;
        $config['encrypt'] = true;
        $new_name = time() . '_' . rand(3, 1000) . '.' . $img_ectsn[1];

        $config['file_name'] = $new_name;

        $dir = './assets/uploads';
        if (!is_dir($dir)) {
            mkdir($dir, 0777, TRUE);
        }
        if (!is_dir($config['upload_path'])) {
            mkdir($config['upload_path'], 0777, TRUE);
        }

        $CI->upload->initialize($config);
        $CI->load->library('upload', $config);
//       pr($CI->upload->data());

        if (!$CI->upload->do_upload($field_name)) {
//            pr($CI->upload->display_errors());
//            die;
            return $CI->upload->display_errors();
        } else {
//            pr($CI->upload->data());
//            die;
            $upload_data = $CI->upload->data();
            return $new_name;
        }
    }

}

if (!function_exists('mkdir_upload_image')) {

    function mkdir_upload_image($field_name, $img_file_name) {
//        pr($_FILES[]);
        $img_ectsn = explode(".", $img_file_name);
        $CI = &get_instance();
        $CI->load->library('upload');
        $config['upload_path'] = './assets/uploads/' . currUserId();
        $config['allowed_types'] = 'gif|jpg|png|jpeg';
//      $config['max_size'] = '2048000';
        $config['overwrite'] = true;
        $config['encrypt'] = true;
        $new_name = time() . '_' . rand(3, 1000) . '.' . $img_ectsn[1];

        $config['file_name'] = $new_name;

        $dir = './assets/uploads';
        if (!is_dir($dir)) {
            mkdir($dir, 0777, TRUE);
        }
        if (!is_dir($config['upload_path'])) {
            mkdir($config['upload_path'], 0777, TRUE);
        }

        $CI->upload->initialize($config);
        $CI->load->library('upload', $config);
//pr($CI->upload->data());die;
        if (!$CI->upload->do_upload($field_name)) {
            return $CI->upload->display_errors();
        } else {
            $upload_data = $CI->upload->data();
            return $new_name;
        }
    }

}

if (!function_exists('is_protected')) {

    function is_protected() {
        $CI = &get_instance();
//       echo ($CI->session->userdata('userinfo')->user_name); die();
        if ($CI->session->userdata('isLogin') != 'yes') {
            redirect(base_url());
        } else if ($CI->session->userdata('isLogin') == 'yes') {
            if ($CI->session->userdata('login_type') == 'spadmin') {
                //redirect("/dashboard");
            } else if ($CI->session->userdata('login_type') == 'user') {
                redirect("/site");
            }
        } else {
            check_permission();
        }
    }

}

function is_superadminprotected() {
    $CI = &get_instance();
    if ($CI->session->userdata('superadminLogin') != 'yes') {
        redirect(base_url());
    } else {
        check_permission();
    }
}

function is_bothprotected() {
    $CI = &get_instance();
    if ($CI->session->userdata('superadminLogin') != 'yes' && $CI->session->userdata('isLogin') != 'yes') {
        redirect(base_url());
    } else {
        check_permission();
    }
}

/* End of Function */

/**
 * @Function _layout
 * @purpose load layout page 
 * @created  2 dec 2014
 */
if (!function_exists('_layout')) {

    function _layout($data = null) {
        $CI = &get_instance();
        $CI->load->view('layout', $data);
    }

}
/* End of Function */

/**
 * set_flashdata
 *
 * This function set falsh message value
 * 
 * @access	public
 * 
 */
if (!function_exists('set_flashdata')) {

    function set_flashdata($type, $msg) {
        $CI = &get_instance();
        $CI->session->set_flashdata($type, $msg);
    }

}
/* End of Function */

/**
 * get_flashdata
 *
 * This function give custome flash message formate
 * 
 * @access	public
 * @return	html data
 */
if (!function_exists('get_flashdata')) {

    function get_flashdata() {
        $CI = &get_instance();
        $success = $CI->session->flashdata('success') ? $CI->session->flashdata('success') : '';
        $error = $CI->session->flashdata('error') ? $CI->session->flashdata('error') : '';
        $warning = $CI->session->flashdata('warning') ? $CI->session->flashdata('warning') : '';
        $msg = '';
        if ($success) {
            $msg = '<div class="alert alert-success flash-row">
                            <button class="close" data-dismiss="alert"><i class="ace-icon fa fa-times"></i></button><i class="ace-icon fa fa-check green"></i>
                            ' . $success . ' </div>';
        } elseif ($error) {
            $msg = '<div class="alert alert-danger flash-row">
			<button class="close" data-dismiss="alert"><i class="ace-icon fa fa-times"></i></button><i class="ace-icon fa fa-check green"></i>
			<strong>Error!</strong> ' . $error . ' </div>';
        } elseif ($warning) {
            $msg = '<div class="alert alert-warning flash-row">
			<button class="close" data-dismiss="alert"><i class="ace-icon fa fa-times"></i></button>
			' . $warning . '</div>';
        }
        return $msg;
    }

}
/* End of Function */


/**
 * Function for restore data
 */
if (!function_exists('restoreData')) {

    function restoreData($arr) {
        $CI = &get_instance();
        $table = $arr->table;
        $col = $arr->col;
        $whr['id'] = $arr->id;
        $upd[$col] = $arr->value;
        $upd['modified'] = date('Y-m-d H:i:s');
        $CI->db->update($table, $upd, $whr);
//        echo $CI->db->last_query(); die;
        if ($CI->db->affected_rows()) {
            $res['status'] = 'success';
            $res['message'] = null;
        } else {
            $res['status'] = 'error';
            $res['message'] = $CI->db->_error_message();
        }
        return $res;
    }

}


/* End of Function */


/**
 * isPostBack
 *
 * This function check request send by POST or  not
 * 
 * @access	public
 * @return	html data
 */
if (!function_exists('isPostBack')) {

    function isPostBack() {
        if (strtoupper($_SERVER['REQUEST_METHOD']) == 'POST')
            return true;
        else
            return false;
    }

}
/* End of Function */

/**
 * Current Date And Time
 *
 * This function get Current Date And Time
 *
 * @param	
 * @return
 */
if (!function_exists('current_date')) {

    function current_date() {
        $dateFormat = date("Y-m-d H:i:s", time());
        $timeNdate = $dateFormat;
        return $timeNdate;
    }

}
/* End of Function */

/**
 * Current User Info 
 * 
 * If user loged then returl current user info
 *
 * @access	public
 * @return	mixed	boolean or depends on what the array contains
 */
if (!function_exists('currentuserinfo')) {

    function currentuserinfo() {
        $CI = &get_instance();
//        pr($CI->session->userdata("userinfo"));die;
        return $CI->session->userdata("userinfo");
    }

}
if (!function_exists('userdetails')) {

    function userdetails($user_id, $columns) {
        $CI = &get_instance();
        $result = $CI->db->select($columns)
                ->where('id', $user_id)
                ->get('users')
                ->result_array();
        return $result[0];
    }

}
/* End of Function */

/**
 * Current User Info 
 * 
 * If user loged then returl current user Id
 *
 * @access	public
 * @return	string
 */
if (!function_exists('currUserId')) {

    function currUserId() {
        $CI = &get_instance();
        return $CI->session->userdata("userinfo")->id;
    }

}
/* End of Function */

/**
 * uri_segment
 * this function give url segment value
 * @param int 
 * @return string
 */
if (!function_exists('uri_segment')) {

    function uri_segment($val) {
        $CI = &get_instance();
        return $CI->uri->segment($val);
    }

}
/* End of Function */
/**
 * notification_message 
 * 
 * return notification message
 *
 * @access	public
 */
if (!function_exists('get_notification_query')) {

    function get_notification_query($query_type = null) {
        $CI = &get_instance();
        $query = $CI->db->select('*')
                        ->from('notification_query')
                        ->where(array("is_read" => 0, "query_type" => $query_type))
                        ->order_by('id', 'desc')->get();
        return $query->result();
    }

}

/**
 * count_new_query 
 * 
 * return count new Query
 *
 * @access	public
 */
if (!function_exists('count_new_query')) {

    function count_new_query() {
        $CI = &get_instance();
        $query = $CI->db->select('count(id) as Total')
                        ->from('notification_query')
                        ->where(array("is_read" => 0))->get();
        return $query->row();
    }

}
/* End Function */
/**
 * function car name by car category
 */
if (!function_exists('car_name_by_carCategory')) {

    function car_name_by_carCategory($car_category_id) {
        $CI = &get_instance();
        $query = $CI->db->select('car_name')
                        ->from('car')
                        ->where(array("category_id" => $car_category_id, 'status' => 'active'))->get();
        return $query->result_array();
    }

}

/* End Function */

/**
 * last_query
 * this function print last query
 * @access	public
 */
if (!function_exists('last_query')) {

    function last_query() {
        $CI = &get_instance();
        echo $CI->db->last_query();
    }

}
/* End of Function */
/**
 * pr
 * this function print data with <pre> tag
 * @access	public
 */
if (!function_exists('pr')) {

    function pr($data = null) {
        echo '<pre>';
        print_r($data);
        echo '</pre>';
    }

}
/* End of Function */

/**
 * readable_date 
 * 
 * this function give readable date formate
 *
 * @access	public
 * @return	string
 */
if (!function_exists('readable_date')) {

    function readable_date($date = null, $type = null) {
        if ($type == 'date') {
            return (!empty($date)) ? date("d-M-Y", strtotime($date)) : ' ';
        }
        return (!empty($date)) ? date("d-M-Y h:i A", strtotime($date)) : ' ';
    }

}
/* End of Function */

/**
 * is_ajax_post
 *
 * This function check request send by Ajax or not
 *
 * 	
 * @return boolean
 */
if (!function_exists('is_ajax_post')) {

    function is_ajax_post() {
        $CI = &get_instance();
        if (!$CI->input->is_ajax_request()) {
            show_error('No direct script access allowed');
            exit;
        }
    }

}
/* End of Function */


/**
 * @function formatDate
 * @purpose format the date 
 * @created 2 Apr 2015
 */
if (!function_exists('changeDateFormat')) {

    function changeDateFormat($str = null) {
        $arr = explode('/', $str); //pr($arr);
        return trim($arr[2]) . '-' . trim($arr[0]) . '-' . trim($arr[1]);
    }

}
/* End of Function */



/**
 * @function _show404
 * @purpose Display error page
 * @created 8Apr2015
 */
if (!function_exists('_show404')) {

    function _show404() {
        $CI = &get_instance();
        $data['title'] = 'Error';
        $data['subTitle'] = 'Wrong Page';
        $data['page'] = 'error';
        _layout($data);
    }

}
/* End of Function */


/**
 * lang
 *
 * This function give laguage variable value
 * @param string
 * 	
 * @return string 
 */
if (!function_exists('lang')) {

    function lang($key) {
        $CI = &get_instance();
        $line = $CI->lang->line($key);
        return $line;
    }

}
/* End of Function */



/**
 * get_language
 *
 * This function give user language name
 * 
 * 	
 * @return string 
 */
if (!function_exists('get_language')) {

    function get_language() {
        $lang = currentuserinfo() ? currentuserinfo()->language : 'english';
        return $lang;
    }

}
/* End of Function */


/**
 * custom_encryption
 *
 * This function encryt and decrypt value on the base action value
 * @param string
 * @param string
 * @param string
 * 	
 * @return string
 */
if (!function_exists('custom_encryption')) {

    function custom_encryption($string, $key, $action) {
        if ($action == 'encrypt')
            return base64_encode(mcrypt_encrypt(MCRYPT_RIJNDAEL_256, md5($key), $string, MCRYPT_MODE_CBC, md5(md5
                                            ($key))));
        elseif ($action == 'decrypt')
            return rtrim(mcrypt_decrypt(MCRYPT_RIJNDAEL_256, md5($key), base64_decode($string), MCRYPT_MODE_CBC, md5(md5($key))), "\0");
    }

}
/* End of Function */


/**
 * get_topics
 *
 * This function give  captcha image
 * 
 * @return html data
 * 	
 */
if (!function_exists('getcaptchacode')) {

    function getcaptchacode() {
        $CI = & get_instance();
        $CI->load->helper('captcha');
        $listAlpha = 'abcdefghijklmnopqrstuvwxyz0123456789';
        $numAlpha = 5;
        $captcha = substr(str_shuffle($listAlpha), 0, $numAlpha);

        $path = config_item('base_url') . 'captcha/';
        //$fontpath = config_item('base_url').'bucket/frontend/assets/fonts/TitilliumWeb-BoldItalic.ttf';
        $fontpath = 'assets/fonts/verdana.ttf';
        $vals = array(
            'word' => $captcha,
            'img_path' => './captcha/',
            'img_url' => $path,
            //'font_path'	 => 'c:/windows/fonts/verdana.ttf',
            'font_path' => $fontpath,
            'img_width' => '159',
            'img_height' => '32',
            'border' => 0,
            'expiration' => 1800
        );
        $get_captcha = create_captcha($vals); //pr($get_captcha); die;
        $CI->session->set_userdata('codecaptcha', $get_captcha['word']);
        return $get_captcha;
    }

}
/* End of Function */


/**
 * obj_to_arr
 *
 * This function convert std object array into array 
 * 
 * @return array
 * 	
 */
if (!function_exists('obj_to_arr')) {

    function obj_to_arr($obj_arr) {
        $obj_arr = (array) $obj_arr;
        $chkey = array_keys($obj_arr);
        $chval = array_values($obj_arr);
        unset($obj_arr);
        $obj_arr = array_combine($chkey, $chval);
        return $obj_arr;
    }

}
/* End of Function */

/**
 * _sendEmail
 *
 * This function send mail to the given email id 
 * @param string
 * 
 */
if (!function_exists('_sendEmail')) {

    function _sendEmail($email_data) {

        $CI = &get_instance();
        $config['protocol'] = 'sendmail';
        $config['mailpath'] = '/usr/sbin/sendmail';
        $config['charset'] = 'iso-8859-1';
        $config['wordwrap'] = true;
        $CI->email->set_mailtype("html");
        $CI->email->initialize($config);
        $CI->email->from($email_data['from'], ucwords($email_data['sender_name']));
        $CI->email->to($email_data['to']);
        if (!empty($email_data['cc'])) {
            $CI->email->cc($email_data['cc']);
        }
        if (!empty($email_data['bcc'])) {
            $CI->email->bcc($email_data['bcc']);
        }
        if (!empty($email_data['file'])) {
            $CI->email->attach($email_data['file']);
        }
        $CI->email->subject(ucfirst($email_data['subject']));
        $data['message'] = $email_data['message'];
        $msg = $CI->load->view('email_template/email', $data, true);
        $CI->email->message($msg);
        if ($CI->email->send()) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

}
/* End of Function */


/**
 * _sendEmailNew
 *
 * This function send mail to the given email id 
 * @param string
 * 	
 */
if (!function_exists('_sendEmailNew')) {

    function _sendEmailNew($email_data) {
        $CI = &get_instance();
        $CI->load->library('email');
        $CI->email->set_mailtype("html");
        $CI->email->from($email_data['from'], ucwords($email_data['sender_name']));
        $CI->email->to($email_data['to']);
        if (!empty($email_data['cc'])) {
            $CI->email->cc($email_data['cc']);
        }
        if (!empty($email_data['bcc'])) {
            $CI->email->bcc($email_data['bcc']);
        }
        if (!empty($email_data['file'])) {
            $CI->email->attach($email_data['file']);
        }
        $CI->email->subject(ucfirst($email_data['subject']));
        $data['message'] = $email_data['message'];
        $msg = $CI->load->view('email_template/email', $data, true);
        $data['message'] = $email_data['message'];
        $msg = $CI->load->view('email_template/email', $data, true);
        $CI->email->message($msg);
        if ($CI->email->send()) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

}
/* End of function */

/**
 * Id_encode
 *
 * This function to encode ID by a custom number
 * @param string
 * 	
 */
if (!function_exists('ID_encode')) {

    function ID_encode($id) {
        $encode_id = '';
        if ($id) {
            $encode_id = rand(1111, 9999) . (($id + 19)) . rand(1111, 9999);
        } else {
            $encode_id = '';
        }
        return $encode_id;
    }

}
/* End of function */

/**
 * Id_decode
 *
 * This function to decode ID by a custom number
 * @param string
 * 	
 */
if (!function_exists('ID_decode')) {

    function ID_decode($encoded_id) {
        $id = '';
        if ($encoded_id) {
            $id = substr($encoded_id, 4, strlen($encoded_id) - 8);
            $id = $id - 19;
        } else {
            $id = '';
        }
        return $id;
    }

}
/* End of function */

// ------------------------------------------------------------------------
/**
 * get all page
 */
if (!function_exists('get_all_page')) {

    function get_all_page() {
        $CI = &get_instance();
        $CI->db->select('*');
        // $CI->db->where('status','active');
        $query = $CI->db->get('pages')->result();
        return $query;
    }

}
// ------------------------------------------------------------------------
/**
 * get all social link
 */
if (!function_exists('social_link')) {

    function social_link() {
        $CI = &get_instance();
        $CI->db->select('id,link,social_name,status');
        $query = $CI->db->get('social_link_page')->result();
        return $query;
    }

}
// ------------------------------------------------------------------------
/**
 * get contact us
 */
if (!function_exists('contact_us')) {

    function contact_us() {
        $CI = &get_instance();
        $CI->db->select('description');
        $CI->db->where('id', '2');
        $query = $CI->db->get('pages')->row();
        return $query;
    }

    function to_get_time_and_distance($from, $to1) {
        $to = str_replace(' ', ',', $to1);

        $data = file_get_contents("https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=$from,IN&destinations=$to,IN&key=AIzaSyDA91tgtDJDqpGmPJKBJqFWpmJSwNia9dQ");
        $data = json_decode($data);
        $data = $data->rows[0]->elements[0];

        if ($data->status == 'OK') {
            $distance_data = $data->distance;
            $duration_data = $data->duration;
            $return_data['distance'] = $distance_data->text;
            $return_data['duration'] = $duration_data->text;
            return $return_data;
        } else {
            return false;
        }
    }

    function get_city_name_by_id($city_id) {
        $CI = &get_instance();
        $CI->db->select('city_name');
        $CI->db->where('id', $city_id);
        $query = $CI->db->get('city')->row_array();
        if (count($query) > 0) {
            return $query['city_name'];
        }
    }

    function get_area_name_by_id($id) {
        $CI = &get_instance();
        $CI->db->select('area_name');
        $CI->db->where('id', $id);
        $query = $CI->db->get('area')->row_array();
        if (count($query) > 0) {
            return $query['area_name'];
        }
    }

    function get_airport_name_by_id($id) {
        $CI = &get_instance();
        $CI->db->select('airport_name');
        $CI->db->where('id', $id);
        $query = $CI->db->get('airport')->row_array();
        if (count($query) > 0) {
            return $query['airport_name'];
        }
    }

    function get_price($charges, $approx_kms) {

        $price = $charges * $approx_kms;
        return $price;
    }

    /* function get_price_old($charges, $approx_kms)
      {
      $approx_km = explode(" ",$approx_kms);
      //echo $approx_km[1];
      //echo $approx_km[0]; die();

      if($approx_km[1]=='km')
      {
      $price = $charges* (float)str_replace(',', '', $approx_km[0]);
      }else{
      $price = ($charges*$approx_km[0])/1000;
      }
      return $price;
      } */

    function get_local_price($charge_per_hour = null, $approx_hrs = null, $car_category_id = null, $source_city = null) {
        $total_price = $charge_per_hour * $approx_hrs;
        return $total_price;
    }

    function extra_price($price = null) {
        $service_tax = $price * 5.60 / 100;
        $swach_bharat = $price * 0.2 / 100;
        $krishi_charge = $price * 0.2 / 100;

        $total_price = round($service_tax + $swach_bharat + $krishi_charge);

        return $total_price;
    }

    function get_hours($id) {
        $CI = &get_instance();
        $CI->db->select('*');
        $CI->db->from('hours_master');
        $CI->db->where('id', $id);
        $query = $CI->db->get();
        if ($query->num_rows() > 0) {
            $result = $query->row();
            return $result->hours;
        }
    }

    function download($table) {
        $CI = &get_instance();
        $filename = "$table.csv";
        /* opening file */
        $file = fopen($filename, "w");

        /* fetching table column name */
        $fieldArray = $CI->db->list_fields($table);
        fputcsv($file, $fieldArray);

        /* Write data in csv file */
        $CI->db->select("*");
        $dataArray = $CI->db->get($table)->result_array();

        foreach ($dataArray as $line) {
            fputcsv($file, $line);
        }

        fclose($file);

        header("Content-Type: application/csv");
        header("Content-Disposition: attachment;Filename=$filename");

        /* send file to browser */
        readfile($filename);
        unlink($filename);
    }

    function get_enquiry_city() {
        $CI = &get_instance();
        $CI->db->select('*');
        $CI->db->where('status', 'active');
        $query = $CI->db->get('city')->result();

        return $query;
    }

    function get_enquiry_sc() {
        $CI = &get_instance();
        $CI->db->select('*');
        $CI->db->where('status', 'active');
        $query = $CI->db->get('service_category')->result();
        return $query;
    }

    function get_offer() {
        $CI = &get_instance();
        $CI->db->select('*');
        $query = $CI->db->get('offer')->result();
        return $query;
    }

    if (!function_exists("sendMessageviaSMS")) {

        function sendMessageviaSMS($message = null, $mobilenumber = null) {
            $fullapiurl = "http://api-alerts.solutionsinfini.com/v3/?method=sms&api_key=Aebc89fa4996fdb36b2f579be150d19a0&to=" . $mobilenumber . "&sender=SIDEMO&message=" . $message . "&port=8090";
            //$fullapiurl =   "http://smsc.biz/httpapi/send?username=ankit0rajput@gmail.com&password=13071990&sender_id=SMSIND&route=T&phonenumber=".$mobilenumber."&message=your%20token%20is%20".$token.".%0AThis%20token%20is%20valid%20for%2015%20Mins.%0A%0AThanks";
            //Call API
            $ch = curl_init($fullapiurl);

            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            $result = curl_exec($ch);
            echo $result; // For Report or Code Check
            curl_close($ch);
        }

    }

    function dateDiff($d1, $d2) {
        return round(abs(strtotime($d1) - strtotime($d2)) / 86400);
    }

    function get_night_day($start_date, $start_time, $end_date, $end_time) {
        $night = 0;
        /* start date and return date is same */
        if (date("Y-m-d", strtotime($start_date)) == $end_date) {
            $night = 1;
        } else {
            $night = 1;
            $start_date = date("Y-m-d", strtotime($start_date . "+1 day"));
            dateDiff($start_date, $end_date);
            if (dateDiff($start_date, $end_date) == 0) {
                $night = $night + 1;
            } else {
                $night = $night + (dateDiff($start_date, $end_date) + 1);
            }
        }
        return $night;
    }

}
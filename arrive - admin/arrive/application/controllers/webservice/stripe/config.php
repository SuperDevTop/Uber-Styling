<?php

ini_set("session.gc_maxlifetime", 3600);
error_reporting(E_ALL ^ E_NOTICE);
ini_set('display_errors', '0');

session_start();

$configurl = "http://54.89.253.235/";

$noImage = 'no-avatar.png';
$connect = mysql_pconnect("uberwash.cq7ho20ualc6.us-east-1.rds.amazonaws.com", "uberwash", "uberwash4583");
if ($connect) {
    $db = mysql_select_db("uberwash", $connect);
    if ($db) {
        //echo "connected";
    } else {
        echo "Database not connected.";
    }
} else {
    echo "Server not connected.";
}
include('common.php');

function getPercentage($providerid) {
    $sql = "select percentage from provider where id = '$providerid' ";
    $rs = mysql_query($sql);
    $rows = mysql_fetch_array($rs);
    return $rows['percentage'];
}

function send_push($token, $push_array, $app_platform) {
    if (($app_platform == "iphone") || ($app_platform == "ios")) {
     
        $payload = array();
        $payload['aps'] = $push_array;
        $payload = json_encode($payload);

        $apnsHost = 'gateway.push.apple.com';
        $apnsCert = 'Certificates4.pem';

        $apnsPort = 2195;

        $streamContext = stream_context_create();
        stream_context_set_option($streamContext, 'ssl', 'local_cert', $apnsCert);
        $apns = stream_socket_client('ssl://' . $apnsHost . ':' . $apnsPort, $error, $errorString, 60, STREAM_CLIENT_CONNECT, $streamContext);
        $device_token = str_replace(' ', '', trim($token));
//            $apnsMessage = chr(0) . chr(0) . chr(32) . pack('H*', $device_token) . chr(0) . chr(strlen($payload)) . $payload;
        $apnsMessage = chr(0) . pack('n', 32) . pack('H*', $device_token) . pack('n', strlen($payload)) . $payload;
        //echo $apnsMessage;]
        $messageMail = $apns . '----' . $apnsMessage . '' . $token . '' . $PEM_MODE;
        mail('mansi.v@parangat.com', 'ios push carwash', $messageMail);
        fwrite($apns, $apnsMessage);
        fclose($apns);
    } else {

        //ini_set('display_errors',1);
        $path_to_firebase_cm = 'https://fcm.googleapis.com/fcm/send';
        $message = $push_array;

        $fields = array(
            'to' => $token,
            //        'notification' =>array('title'=>'yoDoctorPush', 'body'=>$message),
            'data' => array('message' => $message)
        );

        $headers = array(
            'Authorization:key=AIzaSyAOCJsdAiY2Q6fVgXQ4AyVUfSBUhgmAcbc',
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

        //echo $token;
        $result = curl_exec($ch);

        //exit;
        curl_close($ch);


//        $mailContent = serialize($fields);
//     $this->mail->addAddress('mansi.v@parangat.com');
//          $this->mail->Subject = 'Carwasher android push';
//          $this->mail->isHTML(true);
//          $this->mail->Body = $result.' '.$mailContent;
//          $this->mail->send(); 
//        print_r($result);
//       die('mansi');
        return $result;
    }
}

function send_push_partner($token, $push_array, $app_platform) {
     
    if ($app_platform == "iphone") {
        $payload = array();

        $payload['aps'] = $push_array;
        $payload = json_encode($payload);
        $apnsHost = 'gateway.push.apple.com';
        $apnsPort = 2195;
        $apnsCert = 'JamiePartnerPushCert.pem';
        $streamContext = stream_context_create();
        stream_context_set_option($streamContext, 'ssl', 'local_cert', $apnsCert);
        $apns = stream_socket_client('ssl://' . $apnsHost . ':' . $apnsPort, $error, $errorString, 60, STREAM_CLIENT_CONNECT, $streamContext);
        $device_token = str_replace(' ', '', trim($token));
        $apnsMessage = chr(0) . chr(0) . chr(32) . pack('H*', $device_token) . chr(0) . chr(strlen($payload)) . $payload;
//		echo ">>>".$apnsMessage;
        fwrite($apns, $apnsMessage);
        fclose($apns);
    } else {
        $registatoin_ids = str_replace(' ', '', trim($token));
        $message = $push_array;
        if (!is_array($message)) {
            $message = array("message" => "Something went wrong");
            echo $message;
        }
        $url = 'https://android.googleapis.com/gcm/send';
        $fields = array(
            'registration_ids' => array($registatoin_ids),
            'delay_while_idle' => false,
            'data' => $message
        );
        $headers = array(
            'Authorization: key= AIzaSyB8uqObgcNiygwoYh1wwZCui1nLsfk4Mo8',
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
        //echo "====>".$token."<br><br>";
        // Execute post
        $result = curl_exec($ch);
        //echo $result;
        if ($result === FALSE) {
            die('Curl failed: ' . curl_error($ch));
        }
        //echo $result;
        //die();
        // Close connection
        curl_close($ch);
        //$email = "niteshgarg1989@gmail.com";
        //mail($email,"GCM Response",$result);
        return $result;
    }
}

function updatelatlong($latitude, $longitude, $tablename, $wherecolumnid, $wherecolumnidvalue) {
    $sql = " update " . $tablename . " set latitude = '$latitude',longitude = '$longitude' where " . $wherecolumnid . " = '$wherecolumnidvalue'  ";
    //echo $sql;
    mysql_query($sql);
}

function getuserByWorkId($workID, $usertype) {
    $result = '';
    if ($usertype == 'provider') {
        
    } else {
        $sqluser = " select u.id,u.token,u.appPlatform,wr.providerid from user u,work_request wr where wr.id = '" . $workID . "' and wr.userid = u.id  ";
    }
    $rsuser = mysql_query($sqluser);
    if ($rows = mysql_fetch_array($rsuser)) {
        $result['token'] = $rows['token'];
        $result['appPlatform'] = $rows['appPlatform'];
        $result['userid'] = $rows['id'];
        $result['providerid'] = $rows['providerid'];
    }
    return $result;
}

function generateJobCode() {
    $chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    $res = "";
    for ($i = 0; $i < 8; $i++) {
        $res .= $chars[mt_rand(0, strlen($chars) - 1)];
    }
    return $res;
}

//function uploadImage($img, $type) {
//
//    if ($img['name'] != "") {
//        $filename = $img['name'];
//
//        $source = $img['tmp_name'];
//        $newloc = ($type == 'user' ? "../upload/user/" : "../upload/washer/");
//        $dest_dir = ($type == 'user' ? "http://" . $_SERVER['HTTP_HOST'] . "/upload/user/" : "http://" . $_SERVER['HTTP_HOST'] . "/upload/washer/");
//        $ext = substr($filename, strpos($filename, '.'), strlen($filename) - 1);
//        $img = substr('IMAGE_' . number_format(time() * rand(), 0, '', ''), 0, 25) . $ext;
//
//        if (move_uploaded_file($source, $newloc . $img)) {
//            $createdon = date("Y-m-d H:i:s");
//
//
//            //	$sql = mysql_query("insert into photos SET photoname ='$img',createdon ='$createdon',status='0', userid='$userid'");
//            /* $resize = new ResizeImage($dest_dir.$img);
//              $resize->resizeTo(150, 150, 'exact');
//              $resize->saveImage($thumb_dir.$img); */
//            $imgUploadChk = true;
//            $result = $dest_dir . $img;
//        } else {
//            $result = 0;
//        }
//    } else {
//        $result = 0;
//    }
//    return $result;
//}

function uploadImage($img) {
//echo '<pre>';
//        print_r($type);
//        die('die');
    if ($img['name'] != "") {
        $filename = $img['name'];

        $source = $img['tmp_name'];
        $newloc = "../upload/";
//        $dest_dir = ($type == 'user' ? "http://" . $_SERVER['HTTP_HOST'] . "/upload/washer/" : "http://" . $_SERVER['HTTP_HOST'] . "/upload/user/");
        $dest_dir =  "http://" . $_SERVER['HTTP_HOST'] . "/upload/";
      
        $ext = substr($filename, strpos($filename, '.'), strlen($filename) - 1);
        $img = substr('IMAGE_' . number_format(time() * rand(), 0, '', ''), 0, 25) . $ext;

        if (move_uploaded_file($source, $newloc . $img)) {
            $createdon = date("Y-m-d H:i:s");
            //	$sql = mysql_query("insert into photos SET photoname ='$img',createdon ='$createdon',status='0', userid='$userid'");
            /* $resize = new ResizeImage($dest_dir.$img);
              $resize->resizeTo(150, 150, 'exact');
              $resize->saveImage($thumb_dir.$img); */
            $imgUploadChk = true;
            $result = array(
                'imgName' => $img,
                'imgPath' => $dest_dir . $img
            );

//            $result = $dest_dir . $img;
        } else {
            $result = 0;
        }
    } else {
        $result = 0;
    }
    return $result;
}

?>

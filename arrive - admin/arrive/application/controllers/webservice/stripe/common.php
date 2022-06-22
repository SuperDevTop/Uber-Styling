<?php

function xTimeAgo($oldTime) {

    $timeCalc = abs((strtotime(date("Y-m-d H:i:s")) - strtotime($oldTime)));

    if ($timeCalc > (60 * 60 * 24)) {
        $timeCalc = round($timeCalc / 60 / 60 / 24) . " days ago";
    } else if ($timeCalc > (60 * 60)) {
        $timeCalc = round($timeCalc / 60 / 60) . " hours ago";
    } else if ($timeCalc > 60) {
        $timeCalc = round($timeCalc / 60) . " minutes ago";
    } else {
        $timeCalc .= " seconds ago";
    }

    return $timeCalc;
}

function RandomString($length) {

    $keys = range(0, 9);

    for ($i = 0; $i < $length; $i++) {

        $key .= $keys[array_rand($keys)];
    }

    return $key;
}

function RandomString2($length) {

    $alphabets = range('A', 'Z');

    $numbers = range('0', '9');

    $keys = array_merge($alphabets, $numbers);

    for ($i = 0; $i < $length; $i++) {

        $key .= $keys[array_rand($keys)];
    }

    return $key;
}

function getAllDataFromTable($tablename, $where = false, $like = false, $startlimit = '', $endlimit = '', $select = '*', $sortby = false, $groupby = false) {

    $query = "SELECT $select FROM `$tablename`";

    if (!empty($where) || !empty($like)) {

        $query.=' WHERE ';
    }

    if (!empty($where)) {

        $condition = array();

        foreach ($where as $key => $val) {

            $col = mysql_real_escape_string($key);

            $val = mysql_real_escape_string($val);

            $condition[] = "`$col` = '$val'";
            ;
        }

        $query.= implode(' AND ', $condition);
    }



    if (!empty($like)) {

        $condition = array();

        foreach ($like as $key1 => $val1) {

            $col1 = mysql_real_escape_string($key1);

            $val1 = mysql_real_escape_string($val1);

            $condition[] = "`$col1` LIKE '%$val1%'";
            ;
        }

        $query.= implode(' AND ', $condition);
    }

    if ($groupby != '') {

        $query.=" GROUP BY " . $groupby;
    }

    if ($sortby != '') {

        $query.=" ORDER BY " . $sortby;
    }



    if ($startlimit != '' && $endlimit != 0) {

        $query.=" LIMIT " . $startlimit . "," . $endlimit;
    }

//    echo $query.'<br>' ; 

    $rs = mysql_query($query);

    $result = array();

    while ($rows = mysql_fetch_assoc($rs)) {

        $result[] = $rows;
    }

    return $result;
}

function getAllRecordFromTableWithJoin($tablename, $join = array(), $where = array(), $select = '*', $sort = array(), $limit, $groupby = '') {

    $query = "SELECT $select FROM `$tablename`";

    $condition = array();

    $joincondition = array();

    if (!empty($join)) {

        foreach ($join as $key => $val) {

            $col = mysql_real_escape_string($key);

            $val = mysql_real_escape_string($val);

            $query.= "LEFT JOIN $col ON $val ";
            ;
        }
    }

    if (!empty($where)) {

        $query.=' WHERE ';

        foreach ($where as $key => $val) {

            $col = mysql_real_escape_string($key);

            $val = mysql_real_escape_string($val);

            $condition[] = "$col = '$val'";
            ;
        }

        $query.= implode(' AND ', $condition);
    }

    if ($groupby != '') {

        $query.=" GROUP BY " . $groupby;
    }

    if (!empty($sort)) {

        $query.='  ';

        foreach ($sort as $ky => $vl) {

            $cl = mysql_real_escape_string($ky);

            $vl = mysql_real_escape_string($vl);

            $query.= " ORDER BY $cl $vl";
        }
    }

    if ($limit != 0) {

        $query.=" LIMIT " . $limit;
    }



//die($query);
    //die;

    $rs = mysql_query($query);

    $result = array();

    while ($rows = mysql_fetch_assoc($rs)) {

        $result[] = $rows;
    }

    return $result;
}

function signup_mail($email) {

    $message = '<html>

        <head>

        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <title>Confirmation Email</title>

        </head>

        <body>

        <table width="100%" border="0" cellspacing="5">

	<tr style="font-family: Arial, Helvetica, sans-serif; font-size:14px">

        <td width="150"><img src="http://doctorapp.mywebregency.com/doctorapplogo.png"></td>

        <td ><p>Dear $username,</p><p>You are successfully registered by Doctor App</p><p>&nbsp;</p>		$specilzation<p>Please Use <b>' . $email . ' this email id to login with doctor app</b></p><p>&nbsp;</p><p>&nbsp;</p><p>Thanks<br>Doctor App Team</p></td></tr>

	</table>

	</body>

	</html>';

    $headers = "MIME-Version: 1.0" . "\r\n";

    $headers .= "Content-type:text/html;charset=UTF-8" . "\r\n";



    $headers .= 'From: <nitesh.g@parangat.com>' . "\r\n";



    mail($email, 'Doctor App', $message, $headers);
}

function verification_mail($email, $confirmationcode) {

    global $path;

    $message = '<html>

        <head>

        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <title>Confirmation Email</title>

        </head>

        <body>

        <table width="100%" border="0" cellspacing="5">

	<tr style="font-family: Arial, Helvetica, sans-serif; font-size:14px">

        <td width="150"><img src="http://e-kabadi.abcxyz.in/aPanel/assets/backend/logo.png" width="150"></td>

        <td ><p>Dear ' . $email . ',</p><p>You Request For changing your email for e-kabadi account.</p><p>Please use verification link to verify your email address.</p><p>Link:-&nbsp;<a href="' . $path . 'verification.php?err=' . $confirmationcode . '&email=' . $email . '">' . $path . 'verification.php?err=' . $confirmationcode . '</a></p><p>&nbsp;</p><p>&nbsp;</p><p>Thanks<br>e-kabadi Team</p></td></tr>

	</table>

	</body>

	</html>';





    //$message1 = '<p>Link:-&nbsp;<a href="'.$path.'verification.php?err='.$confirmationcode.'&email='.$email.'">'.$path.'verification.php?err='.$confirmationcode.'</a></p>';

    $message1 = $path . 'verification.php?err=' . $confirmationcode . '&email=' . $email;

    //echo  $message;
    //exit();
    // To send HTML mail, the Content-type header must be set

    $headers = 'MIME-Version: 1.0' . "\r\n";

    $headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";

// Additional headers

    $headers .= 'From:E-Kabadi <webmaster@e-kabadi.in>' . "\r\n";

//$headers .= 'Cc: birthdayarchive@example.com' . "\r\n";
//$headers .= 'Bcc: birthdaycheck@example.com' . "\r\n";

    mail($email, 'Verification Mail', $message1, $headers);
}

function forgotpw_verification_mail($email, $verificationcode) {

    global $path;
  
    $message = '<html>

        <head>

        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <title>Forgot password verfication Email</title>

        </head>

        <body>

        <table width="100%" border="0" cellspacing="5">

	<tr style="font-family: Arial, Helvetica, sans-serif; font-size:14px">

        <td width="150"><img src="http://e-kabadi.abcxyz.in/aPanel/assets/backend/logo.png" width="150"></td>

        <td ><p>Dear ' . $email . ',</p><p>You Request For changing your password.</p><p>Please use verification code to verify your email address.</p><p>' . $verificationcode . '</p><p>&nbsp;</p><p>&nbsp;</p><p>Thanks<br>CarWasher Team</p></td></tr>

	</table>

	</body>

	</html>';



//    $message1 = $path . 'verification.php?err=' . $verificationcode . '&email=' . $email;

    //    echo '<pre>';
//    print_r($message);
//    echo '<pre>';
//    print_r($message1);
//    die('diecommon');
    // To send HTML mail, the Content-type header must be set

    $headers = 'MIME-Version: 1.0' . "\r\n";

    $headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";

// Additional headers

    $headers .= 'From:E-Carwasher <webmaster@e-carwasher.in>' . "\r\n";

//$headers .= 'Cc: birthdayarchive@example.com' . "\r\n";
//$headers .= 'Bcc: birthdaycheck@example.com' . "\r\n";

    mail($email, 'Verification Mail', $message, $headers);
}

function getSingleData($tablename, $where, $id, $select) {

    if ($select == '') {

        $select = '*';
    } else {

        $select = $select;
    }

//echo "select $select from $tablename where $where = '$id'";

    $sql = mysql_query("select $select from $tablename where $where = '$id'");
 
    $ser = mysql_fetch_assoc($sql);
    
    return $ser;
}

function getTotalCount($tablename, $where, $id) {

    if ($where == '') {

        $where = '';
    } else {

        $where = "where " . $where ."='".$id."'";
    }
//    echo "select * from $tablename $where";
//    die;
    $sql = mysql_query("select * from $tablename $where");

    $ser = mysql_num_rows($sql);

    return $ser;
}

function insert_table($tablename, $array_value = false) {

    foreach ($array_value as $col => $val) {

        if ($count++ != 0)
            $fields .= ', ';

        $col = mysql_real_escape_string($col);

        $val = mysql_real_escape_string($val);

        $fields .= "`$col` = '$val'";
    }

    $query = "INSERT INTO `$tablename` SET $fields;";

//           echo '<pre>';
//        print_r($query);
//        die('die');

    mysql_query($query);

    $ins_id = mysql_insert_id();

    return $ins_id;
}

function dis_in($string) {

    echo '<pre>';

    print_r($string);

    die;
}

function update_table($tablename, $array_value = false, $where = false) {

    foreach ($array_value as $col => $val) {

        if ($count++ != 0)
            $fields .= ', ';

        $col = mysql_real_escape_string($col);

        $val = mysql_real_escape_string($val);

        $fields .= "`$col` = '$val'";
    }

    $condition = array();

    foreach ($where as $key => $val) {

        $col = mysql_real_escape_string($key);

        $val = mysql_real_escape_string($val);

        $condition[] = "`$col` = '$val'";
        ;
    }

    $where = ' WHERE ' . implode(' AND ', $condition);

    $query = "UPDATE `$tablename` SET $fields $where";

    //echo $query; die;
    //ECHO "<BR>";
    //echo json_encode($query);
    // ?user_type=1&login_id=25&name=vishakha&mobile=1234567890&sex=f&d_o_b=23-01-1992

    return mysql_query($query);
}

function check_email($table, $where = false, $select = '*') {

    $query = "SELECT $select FROM `$table`";



    if (!empty($where)) {

        $query.=' WHERE ';

        $condition = array();

        foreach ($where as $key => $val) {

            $col = mysql_real_escape_string($key);

            $val = mysql_real_escape_string($val);

            $condition[] = "$col='$val'";
            ;
        }

        $query.= implode(' AND ', $condition);
    }

    //echo $query; die;

    $sql = mysql_query($query);

    $ser = mysql_num_rows($sql);

    return $ser;
}

function getLatLong($address) {

    $address = str_replace(' ', '', $address);



    $url = "http://maps.google.com/maps/api/geocode/json?address=$address&sensor=false";

    $ch = curl_init();

    curl_setopt($ch, CURLOPT_URL, $url);

    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);

    curl_setopt($ch, CURLOPT_PROXYPORT, 3128);

    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);

    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);

    $response = curl_exec($ch);

    curl_close($ch);

    $response_a = json_decode($response);

    $lat = $response_a->results[0]->geometry->location->lat;

    $long = $response_a->results[0]->geometry->location->lng;

    $latarray = array(
        "lat" => $lat,
        "long" => $long
    );

    return $latarray;
}

function uploadImage_common() {

    if ($_FILES['user_image']['name'] != "") {

        $filename = $_FILES['user_image']['name'];

        $source = $_FILES['user_image']["tmp_name"];

        $dest_dir = "../aPanel/assets/upload/userimage/";

        $ext = substr($filename, strpos($filename, '.'), strlen($filename) - 1);

        $img = substr('requeston_' . number_format(time() * rand(), 0, '', ''), 0, 25) . $ext;



        if (move_uploaded_file($source, $dest_dir . $img)) {
            $createdon = date("Y-m-d H:i:s");

            //	$sql = mysql_query("insert into photos SET photoname ='$img',createdon ='$createdon',status='0', userid='$userid'"); 

            /* $resize = new ResizeImage($dest_dir.$img);

              $resize->resizeTo(150, 150, 'exact');

              $resize->saveImage($thumb_dir.$img); */

            $imgUploadChk = true;

            $result = $img;
        } else {
            $result = 0;
        }
    } else {
        $result = 0;
    }



    return $result;
}

function notification($detail, $userid, $requestedid, $customerid) {

    $postArr = array(
        'notificationdetail' => $detail,
        'userid' => $userid,
        'customerid' => $customerid,
        'requestid' => $requestedid,
        'notificationtime' => date('Y-m-d h:i:s'),
        'isread' => '0',
    );

    $ins = insert_table('notifications', $postArr);

    if ($ins) {

        return $ins;
    } else {

        return false;
    }
}

function getMessageSendCommon($id) {

    $message = getSingleData('templates', 'id', $id, '*');

    return $message;
}

function pushnotificationalert($notificationdetail, $userid, $type = false) {
    mysql_query("insert into appnotifications (notificationdetail,userid,notificationtime,type) values ('$notificationdetail','$userid',now(),$type) ");
}

function sendSMSCurl($mobileno, $message) {

    $url = "http://203.212.70.200/smpp/sendsms?username=ekabadi&password=del12345&to=" . $mobileno . "&from=eKBADI&udh=&text=" . urlencode($message) . "&dlr-mask=19&dlr-url";



    $curlhandle = curl_init();

    curl_setopt($curlhandle, CURLOPT_URL, $url);

    curl_setopt($curlhandle, CURLOPT_RETURNTRANSFER, 1);

    $response = curl_exec($curlhandle);

    curl_close($curlhandle);


    return true;
}

function updateUnregisteredCash($mobile, $userid) {

    $sqlcashtransfer = "select * from unregisteredcash where mobileno = '$mobile' and status = '0' ";
    $rscashtransfer = mysql_query($sqlcashtransfer);
    $numcash = mysql_num_rows($rscashtransfer);
    $totalcash = 0;
    if ($numcash > 0) {
        while ($rowscash = mysql_fetch_assoc($rscashtransfer)) {
            $totalcash = $totalcash + $rowscash['amount'];
        }
        mysql_query("update unregisteredcash set status = '1' where  mobileno = '$mobile' ");
        mysql_query("update users set walletamount = '$totalcash' where id = '$userid' ");
    }
}




?>
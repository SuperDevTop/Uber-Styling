<?php
$id = "AC7c0ff5aa391cc180b4be0e6d37a4b057";
        $token = "ca9914d7b199dc9e51532397745e8ada";
        $url = "https://api.twilio.com/2010-04-01/Accounts/$id/SMS/Messages";
        $from = "+18634001988";
        //$to = "+917840078303";
        //$body = "test";
        $data = array(
            'From' => $from,
            'To' => '+917415386167',
            'Body' => 'hello test',
        );
        $post = http_build_query($data);
        $x = curl_init($url);
        //print_r($x);die();
        curl_setopt($x, CURLOPT_POST, true);
		curl_setopt($x, CURLOPT_RETURNTRANSFER, true);
		curl_setopt($x, CURLOPT_SSL_VERIFYPEER, true);
		curl_setopt($x, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
        curl_setopt($x, CURLOPT_USERPWD, "$id:$token");
        curl_setopt($x, CURLOPT_POSTFIELDS, $post);
       echo  $y = curl_exec($x);
        curl_close($x);
        $response_a = json_decode($y, true);
       print_r($response_a);die();
        
?>
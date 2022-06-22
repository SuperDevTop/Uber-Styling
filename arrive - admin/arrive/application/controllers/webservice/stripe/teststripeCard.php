<?php
require_once('../config.php');
require_once('init.php');

// Set your secret key: remember to change this to your live secret key in production
// See your keys here https://dashboard.stripe.com/account/apikeys
\Stripe\Stripe::setApiKey("sk_test_RaaC1cGBnO3cUnydvrtQKPYc");

// Get the credit card details submitted by the form
//$token = $_REQUEST['stripeToken'];
$customerId = $_REQUEST['customerId'];
$invoiceNo = $_REQUEST['invoiceNo'];

// Create the charge on Stripe's servers - this will charge the user's card
try {
  $charge = \Stripe\Charge::create(array(
  "amount" => round($_REQUEST['amount'],'0'), # amount in cents, again
  "currency" => "usd",
  "customer" => $customerId,
  "description" => 'Payment for invoice no '.$invoiceNo)
);
	//echo "<pre>";
	//echo $charge;
	//echo"<br>================================<br>";
	//$arr = $charge->getJsonBody();
	//print_r(json_decode($arr));
  

	if(strstr($charge,'succeeded')){
		mysql_query("UPDATE `work_request` SET `stripRef` = '".$charge['id']."' WHERE `jobcode` = '".$invoiceNo."'");
		$response['result'] = true;
		$response['msg'] = 'Payment made successfully.';
	}else{
		$response['result'] = false;
	}
	echo json_encode($response);
	exit();
}catch(\Stripe\Error\Card $e) {
  // Since it's a decline, \Stripe\Error\Card will be caught
  $body = $e->getJsonBody();
  $err  = $body['error'];
  $response['result'] = false;
  $response['type'] =  $err['type'];
   $response['code'] =  $err['code'];
  $response['msg'] = $err['message'];
  echo json_encode($response);

//  print('Status is:' . $e->getHttpStatus() . "\n");
 // print('Type is:' . $err['type'] . "\n");
  //print('Code is:' . $err['code'] . "\n");
  // param is '' in this case
//  print('Param is:' . $err['param'] . "\n");
 // print('Message is:' . $err['message'] . "\n");
  
} catch (\Stripe\Error\RateLimit $e) {
   $body = $e->getJsonBody();
  $err  = $body['error'];
  $response['result'] = false;
  $response['type'] =  $err['type'];
  $response['msg'] = $err['message'];
   $response['code'] =  $err['code'];
  echo json_encode($response);

//  print('Status is:' . $e->getHttpStatus() . "\n");
 // print('Type is:' . $err['type'] . "\n");
  //print('Code is:' . $err['code'] . "\n");
  // param is '' in this case
//  print('Param is:' . $err['param'] . "\n");
 // print('Message is:' . $err['message'] . "\n");
} catch (\Stripe\Error\InvalidRequest $e) {
   $body = $e->getJsonBody();
  $err  = $body['error'];
  $response['result'] = false;
  $response['type'] =  $err['type'];
  $response['msg'] = $err['message'];
   $response['code'] =  $err['code'];
  echo json_encode($response);

//  print('Status is:' . $e->getHttpStatus() . "\n");
 // print('Type is:' . $err['type'] . "\n");
  //print('Code is:' . $err['code'] . "\n");
  // param is '' in this case
//  print('Param is:' . $err['param'] . "\n");
 // print('Message is:' . $err['message'] . "\n");
} catch (\Stripe\Error\Authentication $e) {
  $body = $e->getJsonBody();
  $err  = $body['error'];
  $response['result'] = false;
  $response['type'] =  $err['type'];
  $response['msg'] = $err['message'];
   $response['code'] =  $err['code'];
  echo json_encode($response);

//  print('Status is:' . $e->getHttpStatus() . "\n");
 // print('Type is:' . $err['type'] . "\n");
  //print('Code is:' . $err['code'] . "\n");
  // param is '' in this case
//  print('Param is:' . $err['param'] . "\n");
 // print('Message is:' . $err['message'] . "\n");
} catch (\Stripe\Error\ApiConnection $e) {
   $body = $e->getJsonBody();
  $err  = $body['error'];
  $response['result'] = false;
  $response['type'] =  $err['type'];
  $response['msg'] = $err['message'];
   $response['code'] =  $err['code'];
  echo json_encode($response);
} catch (\Stripe\Error\Base $e) {
  $body = $e->getJsonBody();
  $err  = $body['error'];
  $response['result'] = false;
  $response['type'] =  $err['type'];
  $response['msg'] = $err['message'];
   $response['code'] =  $err['code'];
  echo json_encode($response);

//  print('Status is:' . $e->getHttpStatus() . "\n");
 // print('Type is:' . $err['type'] . "\n");
  //print('Code is:' . $err['code'] . "\n");
  // param is '' in this case
//  print('Param is:' . $err['param'] . "\n");
 // print('Message is:' . $err['message'] . "\n");
} catch (Exception $e) {
  $body = $e->getJsonBody();
  $err  = $body['error'];
  $response['result'] = false;
  $response['type'] =  $err['type'];
  $response['msg'] = $err['message'];
   $response['code'] =  $err['code'];
  echo json_encode($response);

//  print('Status is:' . $e->getHttpStatus() . "\n");
 // print('Type is:' . $err['type'] . "\n");
  //print('Code is:' . $err['code'] . "\n");
  // param is '' in this case
//  print('Param is:' . $err['param'] . "\n");
 // print('Message is:' . $err['message'] . "\n");
}


?>
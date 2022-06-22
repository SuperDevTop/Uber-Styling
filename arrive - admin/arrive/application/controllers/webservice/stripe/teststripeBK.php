<?php
require_once('init.php');

// Set your secret key: remember to change this to your live secret key in production
// See your keys here https://dashboard.stripe.com/account/apikeys
\Stripe\Stripe::setApiKey("sk_test_RaaC1cGBnO3cUnydvrtQKPYc");

// Get the credit card details submitted by the form
//$token = $_REQUEST['stripeToken'];
$token = 'tok_171cWPEkDpXIsekvQmTHLm73';

// Create the charge on Stripe's servers - this will charge the user's card
try {
  $charge = \Stripe\Charge::create(array(
    "amount" => 1000, // amount in cents, again
    "currency" => "usd",
    "source" => $token,
    "description" => "Example charge"
    ));
	//echo "<pre>";
	echo $charge;
	//print_r(json_decode($charge));
	exit();
}catch(\Stripe\Error\Card $e) {
  // Since it's a decline, \Stripe\Error\Card will be caught
  $body = $e->getJsonBody();
  $err  = $body['error'];
  
  echo json_encode($err);

//  print('Status is:' . $e->getHttpStatus() . "\n");
 // print('Type is:' . $err['type'] . "\n");
  //print('Code is:' . $err['code'] . "\n");
  // param is '' in this case
//  print('Param is:' . $err['param'] . "\n");
 // print('Message is:' . $err['message'] . "\n");
  
} catch (\Stripe\Error\RateLimit $e) {
  $body = $e->getJsonBody();
  $err  = $body['error'];

 $body = $e->getJsonBody();
  $err  = $body['error'];
  
  echo json_encode($err);

//  print('Status is:' . $e->getHttpStatus() . "\n");
 // print('Type is:' . $err['type'] . "\n");
  //print('Code is:' . $err['code'] . "\n");
  // param is '' in this case
//  print('Param is:' . $err['param'] . "\n");
 // print('Message is:' . $err['message'] . "\n");
} catch (\Stripe\Error\InvalidRequest $e) {
  $body = $e->getJsonBody();
  $err  = $body['error'];

  $body = $e->getJsonBody();
  $err  = $body['error'];
  
  echo json_encode($err);

//  print('Status is:' . $e->getHttpStatus() . "\n");
 // print('Type is:' . $err['type'] . "\n");
  //print('Code is:' . $err['code'] . "\n");
  // param is '' in this case
//  print('Param is:' . $err['param'] . "\n");
 // print('Message is:' . $err['message'] . "\n");
} catch (\Stripe\Error\Authentication $e) {
  $body = $e->getJsonBody();
  $err  = $body['error'];

 $body = $e->getJsonBody();
  $err  = $body['error'];
  
  echo json_encode($err);

//  print('Status is:' . $e->getHttpStatus() . "\n");
 // print('Type is:' . $err['type'] . "\n");
  //print('Code is:' . $err['code'] . "\n");
  // param is '' in this case
//  print('Param is:' . $err['param'] . "\n");
 // print('Message is:' . $err['message'] . "\n");
} catch (\Stripe\Error\ApiConnection $e) {
  $body = $e->getJsonBody();
  $err  = $body['error'];

  print('Status is:' . $e->getHttpStatus() . "\n");
  print('Type is:' . $err['type'] . "\n");
  print('Code is:' . $err['code'] . "\n");
  // param is '' in this case
  print('Param is:' . $err['param'] . "\n");
  print('Message is:' . $err['message'] . "\n");
} catch (\Stripe\Error\Base $e) {
 $body = $e->getJsonBody();
  $err  = $body['error'];

$body = $e->getJsonBody();
  $err  = $body['error'];
  
  echo json_encode($err);

//  print('Status is:' . $e->getHttpStatus() . "\n");
 // print('Type is:' . $err['type'] . "\n");
  //print('Code is:' . $err['code'] . "\n");
  // param is '' in this case
//  print('Param is:' . $err['param'] . "\n");
 // print('Message is:' . $err['message'] . "\n");
} catch (Exception $e) {
  $body = $e->getJsonBody();
  $err  = $body['error'];

 $body = $e->getJsonBody();
  $err  = $body['error'];
  
  echo json_encode($err);

//  print('Status is:' . $e->getHttpStatus() . "\n");
 // print('Type is:' . $err['type'] . "\n");
  //print('Code is:' . $err['code'] . "\n");
  // param is '' in this case
//  print('Param is:' . $err['param'] . "\n");
 // print('Message is:' . $err['message'] . "\n");
}


?>
<?php
require_once('../config.php');
require_once('init.php');

// Set your secret key: remember to change this to your live secret key in production
// See your keys here https://dashboard.stripe.com/account/apikeys
//\Stripe\Stripe::setApiKey("sk_test_Ujsx9qPrzI4YOZag3jwV4K6p");
//\Stripe\Stripe::setApiKey("sk_test_OltkRZeQLPuEP8fHQ6xqY54B");
\Stripe\Stripe::setApiKey("sk_live_mAC1gBKutFtJW0nzkvvbdUhk");

// Get the credit card details submitted by the form
$token1 = $_REQUEST['stripeToken'];
$userId = $_REQUEST['user_id'];
$discount = $_REQUEST['discount'];
// Create the charge on Stripe's servers - this will charge the user's card
try {
  // Create a Customer
      
        $exp_month=$_POST['exp_month'];
        $exp_year=$_POST['exp_year'];
        $number=$_POST['number'];
        $cvc=$_POST['cvc'];
        
 $token= \Stripe\Token::create(array(
    "card" => array(
    "number" => $number,
    "exp_month" =>$exp_month,
    "exp_year" => $exp_year,
    "cvc" => $cvc
  )
));

  $charge = \Stripe\Charge::create(array(
            "amount" =>ceil($_REQUEST['amount']*100),
            "currency" => "usd",
            "source" => $token['id'], // obtained with Stripe.js
            "description" =>  'Payment for User Id ' . $userId
          ));
    $finalResponse = $charge->jsonSerialize();

    $chargeId = $finalResponse['id'];
    if ($finalResponse['status'] == 'succeeded') {
        $transactionCode = generateJobCode();
        $sqlInsertQuery = "INSERT INTO transaction SET transaction_id = '" . $transactionCode . "' , bookinng_id = '' , user_id = '" . $userId . "' , customer_id = '' , card_id = '' , stripe_charge_id = '" . $chargeId . "' , amount = '" . $_REQUEST['amount'] . "' , discount = '".$_REQUEST['discount']."', added_on = '" . date('Y-m-d H:i:s') . "'";
       // echo $sqlInsertQuery; die;
        mysql_query($sqlInsertQuery);
        $insertId = mysql_insert_id();
        $response['tran_id'] = $insertId;
        $response['result'] = true;
        $response['msg'] = 'Payment made successfully.';
    } else {
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
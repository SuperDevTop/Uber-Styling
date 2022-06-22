<?php

require_once('config.php');
require_once('init.php');

// Set your secret key: remember to change this to your live secret key in production
// See your keys here https://dashboard.stripe.com/account/apikeys
//\Stripe\Stripe::setApiKey("sk_test_OltkRZeQLPuEP8fHQ6xqY54B");


\Stripe\Stripe::setApiKey("sk_live_mAC1gBKutFtJW0nzkvvbdUhk");

// Get the credit card details submitted by the form
$token1 = $_REQUEST['stripe_token'];
$userId = $_REQUEST['user_id'];
$email = $_POST['email'];
$name = $_POST['name'];
$description = $_POST['description'];

// Create the charge on Stripe's servers - this will charge the user's card
try {
    // Create a Customer

    $e = \Stripe\Customer::create(array(
               //"name" => $name,
                "email" => $email,
                "description" => $description,
                "source" => "tok_visa" // obtained with Stripe.js
    ));
    $customerStripeId = $e['id'];
    mysql_query("UPDATE `user` SET stripe_user_id = '" . $customerStripeId . "' WHERE `id` = '" . $userId . "'");
    $response['result'] = "true";
    $response['msg'] = 'Customer created successfully';
    $response['stripe_user_id'] = $customerStripeId;

    echo json_encode($response);
    exit();
} catch (\Stripe\Error\Card $e) {
    // Since it's a decline, \Stripe\Error\Card will be caught
    $body = $e->getJsonBody();
    $err = $body['error'];
    $response['result'] = "false";
    $response['type'] = $err['type'];
    $response['code'] = $err['code'];
    $response['msg'] = $err['message'];
    echo json_encode($response);
} catch (\Stripe\Error\RateLimit $e) {
    $body = $e->getJsonBody();
    $err = $body['error'];
    $response['result'] = "false";
    $response['type'] = $err['type'];
    $response['msg'] = $err['message'];
    $response['code'] = $err['code'];
    echo json_encode($response);
} catch (\Stripe\Error\InvalidRequest $e) {
    $body = $e->getJsonBody();
    $err = $body['error'];
    $response['result'] = "false";
    $response['type'] = $err['type'];
    $response['msg'] = $err['message'];
    $response['code'] = $err['code'];
    echo json_encode($response);
} catch (\Stripe\Error\Authentication $e) {
    $body = $e->getJsonBody();
    $err = $body['error'];
    $response['result'] = "false";
    $response['type'] = $err['type'];
    $response['msg'] = $err['message'];
    $response['code'] = $err['code'];
    echo json_encode($response);
} catch (\Stripe\Error\ApiConnection $e) {
    $body = $e->getJsonBody();
    $err = $body['error'];
    $response['result'] = "false";
    $response['type'] = $err['type'];
    $response['msg'] = $err['message'];
    $response['code'] = $err['code'];
    echo json_encode($response);
} catch (\Stripe\Error\Base $e) {
    $body = $e->getJsonBody();
    $err = $body['error'];
    $response['result'] = "false";
    $response['type'] = $err['type'];
    $response['msg'] = $err['message'];
    $response['code'] = $err['code'];
    echo json_encode($response);
} catch (Exception $e) {
    $body = $e->getJsonBody();
    $err = $body['error'];
    $response['result'] = "false";
    $response['type'] = $err['type'];
    $response['msg'] = $err['message'];
    $response['code'] = $err['code'];
    echo json_encode($response);
}
?>
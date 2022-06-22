<?php

require_once('config.php');
require_once('init.php');

// Set your secret key: remember to change this to your live secret key in production
// See your keys here https://dashboard.stripe.com/account/apikeys
//\Stripe\Stripe::setApiKey("sk_test_OltkRZeQLPuEP8fHQ6xqY54B");
\Stripe\Stripe::setApiKey("sk_live_mAC1gBKutFtJW0nzkvvbdUhk");
// Get the credit card details submitted by the form
$token = $_REQUEST['stripeToken'];
$customerId = $_POST['customer_id'];

try {

    $e = \Stripe\Customer::retrieve($customerId)->sources->all(array('object' => 'card'));
    $error['status'] = 'true';
    $error['msg'] = $e->data;

    echo json_encode($error);
    exit();
} catch (\Stripe\Error\Card $e) {
    // Since it's a decline, \Stripe\Error\Card will be caught
    $body = $e->getJsonBody();
    $err = $body['error'];
    $response['result'] = false;
    $response['type'] = $err['type'];
    $response['code'] = $err['code'];
    $response['msg'] = $err['message'];
    echo json_encode($response);
} catch (\Stripe\Error\RateLimit $e) {
    $body = $e->getJsonBody();
    $err = $body['error'];
    $response['result'] = false;
    $response['type'] = $err['type'];
    $response['msg'] = $err['message'];
    $response['code'] = $err['code'];
    echo json_encode($response);
} catch (\Stripe\Error\InvalidRequest $e) {
    $body = $e->getJsonBody();
    $err = $body['error'];
    $response['result'] = false;
    $response['type'] = $err['type'];
    $response['msg'] = $err['message'];
    $response['code'] = $err['code'];
    echo json_encode($response);
} catch (\Stripe\Error\Authentication $e) {
    $body = $e->getJsonBody();
    $err = $body['error'];
    $response['result'] = false;
    $response['type'] = $err['type'];
    $response['msg'] = $err['message'];
    $response['code'] = $err['code'];
    echo json_encode($response);
} catch (\Stripe\Error\ApiConnection $e) {
    $body = $e->getJsonBody();
    $err = $body['error'];
    $response['result'] = false;
    $response['type'] = $err['type'];
    $response['msg'] = $err['message'];
    $response['code'] = $err['code'];
    echo json_encode($response);
} catch (\Stripe\Error\Base $e) {
    $body = $e->getJsonBody();
    $err = $body['error'];
    $response['result'] = false;
    $response['type'] = $err['type'];
    $response['msg'] = $err['message'];
    $response['code'] = $err['code'];
    echo json_encode($response);
} catch (Exception $e) {
    $body = $e->getJsonBody();
    $err = $body['error'];
    $response['result'] = false;
    $response['type'] = $err['type'];
    $response['msg'] = $err['message'];
    $response['code'] = $err['code'];
    echo json_encode($response);
}
?>
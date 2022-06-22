<?php

require_once('config.php');
require_once('init.php');

// Set your secret key: remember to change this to your live secret key in production
// See your keys here https://dashboard.stripe.com/account/apikeys
//\Stripe\Stripe::setApiKey("sk_test_Ujsx9qPrzI4YOZag3jwV4K6p");
//\Stripe\Stripe::setApiKey("sk_test_OltkRZeQLPuEP8fHQ6xqY54B");

\Stripe\Stripe::setApiKey("sk_live_mAC1gBKutFtJW0nzkvvbdUhk");
// Get the credit card details submitted by the form
// Create the charge on Stripe's servers - this will charge the user's card
//print_r($_REQUEST);
//die;
try {
    // Create a Customer
    $customer_id = $_POST['customer_id'];
    $exp_month = $_POST['exp_month'];
    $exp_year = $_POST['exp_year'];
    $number = $_POST['number'];
    $cvc = $_POST['cvc'];

    $userId = $_REQUEST['user_id'];
    $email = $_POST['email'];
    $description = $_POST['email'];
    $name = $_POST['name'];

    if (empty($customer_id)) {

        $token = \Stripe\Token::create(array(
                    "card" => array(
                        "number" => $number,
                        "exp_month" => $exp_month,
                        "exp_year" => $exp_year,
                        "cvc" => $cvc
                    )
        ));

        $e = \Stripe\Customer::create(array(
                    //"name" => $name,
                    "email" => $email,
                    "description" => $description,
                    "source" => $token['id'] // obtained with Stripe.js
        ));
        $customer_id = $e['id'];
//         print_r($customerStripeId);
//        die;
        mysql_query("UPDATE `user` SET stripe_user_id = '" . $customer_id . "' WHERE `id` = '" . $userId . "'");
    } else {





        $customer = \Stripe\Customer::retrieve($customer_id);
        $token = \Stripe\Token::create(array(
                    "card" => array(
                        "number" => $number,
                        "exp_month" => $exp_month,
                        "exp_year" => $exp_year,
                        "cvc" => $cvc
                    )
        ));

        $e = $customer->sources->create(array("source" => $token));
    }
    $response['result'] = "true";
    $response['msg'] = 'Card save successfully';
    $response['stripe_user_id'] = $customer_id;
    echo json_encode($response);
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
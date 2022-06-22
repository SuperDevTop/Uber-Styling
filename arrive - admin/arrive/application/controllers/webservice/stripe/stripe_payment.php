so<?php

require_once('config.php');
require_once('init.php');

// Set your secret key: remember to change this to your live secret key in production
// See your keys here https://dashboard.stripe.com/account/apikeys
\Stripe\Stripe::setApiKey("sk_test_OltkRZeQLPuEP8fHQ6xqY54B");

//\Stripe\Stripe::setApiKey("sk_live_mAC1gBKutFtJW0nzkvvbdUhk");

// Get the credit card details submitted by the form
$token = $_REQUEST['stripeToken'];
$bookingId = $_REQUEST['booking_id'];
$userId = $_REQUEST['user_id'];
$customerId = $_REQUEST['customer_id'];
$cardId = $_REQUEST['card_id'];
$discount = $_REQUEST['discount'];
$tip = $_REQUEST['tip'];
/*echo "<pre>";print_r($bookingId);echo '1';
echo "<pre>";print_r($userId);echo '2';
echo "<pre>";print_r($customerId);echo '3';
echo "<pre>";print_r($cardId);echo '4';
echo "<pre>";print_r($discount);echo '5';
echo "<pre>";print_r($tip);die();*/


// Create the charge on Stripe's servers - this will charge the user's card

try {
}
?>
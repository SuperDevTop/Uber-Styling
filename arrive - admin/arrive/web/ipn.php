<?php
// Check to see there are posted variables coming into the script
if ($_SERVER['REQUEST_METHOD'] != "POST")

// Initialize the $req variable and add CMD key value pair
$req = 'cmd=_notify-validate';
// Read the post from PayPal
foreach ($_POST as $key => $value) {
    $value = urlencode(stripslashes($value));
    $req .= "&$key=$value";
}
// Now Post all of that back to PayPal's server using curl, and validate everything with PayPal
// We will use CURL instead of PHP for this for a more universally operable script (fsockopen has issues on some environments)
$url = "https://www.paypal.com/cgi-bin/webscr";
//$url = "https://www.paypal.com/cgi-bin/webscr";
$curl_result = $curl_err = '';
$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, $url);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
curl_setopt($ch, CURLOPT_POST, 1);
curl_setopt($ch, CURLOPT_POSTFIELDS, $req);
curl_setopt($ch, CURLOPT_HTTPHEADER, array("Content-Type: application/x-www-form-urlencoded", "Content-Length: " . strlen($req)));
curl_setopt($ch, CURLOPT_HEADER, 0);
curl_setopt($ch, CURLOPT_VERBOSE, 1);
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);
curl_setopt($ch, CURLOPT_TIMEOUT, 30);
$curl_result = @curl_exec($ch);
$curl_err = curl_error($ch);
curl_close($ch);

$req = str_replace("&", "\n", $req);  // Make it a nice list in case we want to email it to ourselves for reporting
// Check that the result verifies
if (strpos($curl_result, "VERIFIED") !== false) {
    $req .= "\n\nPaypal Verified OK";
} else {
    $req .= "\n\nData NOT verified from Paypal!";
    mail("jitenpj74@gmail.com", "IPN interaction not verified", "$req", "From: you@youremail.com");
    exit();
}

/* CHECK THESE 4 THINGS BEFORE PROCESSING THE TRANSACTION, HANDLE THEM AS YOU WISH
  1. Make sure that business email returned is your business email
  2. Make sure that the transaction�s payment status is �completed�
  3. Make sure there are no duplicate txn_id
  4. Make sure the payment amount matches what you charge for items. (Defeat Price-Jacking) */

// Check Number 1 ------------------------------------------------------------------------------------------------------------
$receiver_email = $_POST['receiver_email'];
if ($receiver_email != "jitu.maestros@gmail.com") {
//handle the wrong business url
    exit(); // exit script
}
// Check number 2 ------------------------------------------------------------------------------------------------------------
if ($_POST['payment_status'] != "Completed") {
    // Handle how you think you should if a payment is not complete yet, a few scenarios can cause a transaction to be incomplete
}

// Check number 3 ------------------------------------------------------------------------------------------------------------
$this_txn = $_POST['txn_id'];
//check for duplicate txn_ids in the database
// Check number 4 ------------------------------------------------------------------------------------------------------------
$product_id_string = $_POST['custom'];
$product_id_string = rtrim($product_id_string, ","); // remove last comma
// Explode the string, make it an array, then query all the prices out, add them up, and make sure they match the payment_gross amount
// END ALL SECURITY CHECKS NOW IN THE DATABASE IT GOES ------------------------------------
////////////////////////////////////////////////////
// Homework - Examples of assigning local variables from the POST variables
$txn_id = $_POST['txn_id'];
$payer_email = $_POST['payer_email'];
$custom = $_POST['custom'];
// Place the transaction into the database
// Mail yourself the details
mail("jitu.maestros@gmail.com", "NORMAL IPN RESULT YAY MONEY!", $req, "From: jitu");
?>
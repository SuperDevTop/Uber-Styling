<?php
 
require_once('config.php');
require_once('init.php');

$trid = $_REQUEST['tid'];
$sql = "SELECT stripe_charge_id from transaction  where id = '".$trid."' "; 
$row = mysql_fetch_row(mysql_query($sql));


\Stripe\Stripe::setApiKey("sk_live_mAC1gBKutFtJW0nzkvvbdUhk");

$res = \Stripe\Refund::create(array(
  "charge" => $row[0]
));
 
if($res['status'] == 'succeeded'){
	
	$sql = "UPDATE transaction SET refund= '1' where id = '".$trid."' "; 
	$row = mysql_query($sql);
}
?>
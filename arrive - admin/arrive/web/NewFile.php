<?php
include('../common/config.php');
include('../common/select.php');
$users=new select_class($conn,'lc_users');
$session_user=$users->select(Array('condition'=>Array('ID'=>$_SESSION['user_id']),'type'=>'single'));
if(count($_SESSION['cart'])>0)
{
	$cart=$_SESSION['cart'];
	$s=0;
	foreach($cart as $carts)
	{
		$s+=$carts['price']*$carts['qty'];
	}
	$orders=array();
	$orders['user_id']=$_SESSION['user_id'];
	$orders['payer_email']=$_POST['payer_email'];
	$orders['payer_id']=$_POST['payer_id'];
	$orders['payer_status']=$_POST['payer_status'];
	$orders['first_name']=$_POST['first_name'];
	$orders['last_name']=$_POST['last_name'];
	$orders['address_name']=$_POST['address_name'];
	$orders['address_street']=$_POST['address_street'];
	$orders['address_city']=$_POST['address_city'];
	$orders['address_state']=$_POST['address_state'];
	$orders['address_country_code']=$_POST['address_country_code'];
	$orders['address_zip']=$_POST['address_zip'];
	$orders['txn_id']=$_POST['txn_id'];
	$orders['payment_status']=$_POST['payment_status'];
	$orders['pending_reason']=$_POST['pending_reason'];
	$order=mysqli_query($conn,"INSERT INTO `order_details`(`user_id`,`mod_of_pay`, `total`,`txn_id`,`user_details`,`shipping`,`tax`,`coupon_code`,`discount`) VALUES ('".$_SESSION['user_id']."','paypal','".$s."','".$_POST['txn_id']."','".json_encode($orders)."','10','1.15','','10')");
	$order_id=mysqli_insert_id($conn);

	$qu="INSERT INTO `ordered_item`(`order_id`, `product_id`, `user_id`, `price`, `qty`) VALUES";
	$que=array();
	if($order_id>0)
	{
		 foreach($cart as $carts)
		{
			 
			$que[]="('".$order_id."','".$carts['product_id']."','".$_SESSION['user_id']."','".$carts['price']."','".$carts['qty']."')";
		 }
		 $qu .=implode(",",$que);
		 $sucess=mysqli_query($conn,$qu);
		 if( $sucess>0)
		 {
			 unset($_POST);
			 unset($_SESSION['cart']);
			 echo "<h1>Your Payment is successfully done</h1>";
			 echo "<a href='review_edit.php'>click here</a> to review your order";
		 }
	}
}else
{
	 echo "<h1>Make a New Order Your Last order is successfully placed</h1>";
}
?>
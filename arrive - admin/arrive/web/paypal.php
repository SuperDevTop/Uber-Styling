<?php include('../common/config.php');
$s=0;
$url="https://www.sandbox.paypal.com/cgi-bin/webscr";
$url.="?cmd=_cart&upload=1";
$url.="&business=info@projectlikes.it";
$url.="&lc=ITA";
/*$html='<form  action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post" id="paymentpaypal" name="paymentpaypal" ><input type="hidden" name="cmd" value="_cart">
            <input type="hidden" name="upload" value="1">
            <input type="hidden" name="business" value="jitenpj74@gmail.com">
            <input type="hidden" name="lc" value="IN">';*/
            
    $sqli=mysqli_query($conn,"SELECT * FROM `orders` where order_id='".$_GET['order_id']."'");
     $no=1;
    while($fetchcart=mysqli_fetch_assoc($sqli)){
        $prono=$no++;
        $prodif=mysqli_fetch_assoc(mysqli_query($conn,"SELECT * FROM `cart` where id='".$fetchcart['cart_id']."'"));
        $profetch=mysqli_fetch_assoc(mysqli_query($conn,"SELECT * FROM `products` Where id='".$prodif['prod_id']."'"));
        $url.="&item_name_".$prono."=".$profetch['name'];
		$url.="&amount_".$prono."=".$profetch['sale_price'];
		$url.="&item_number_".$prono."=".$prodif['prod_id'];
		$url.="&quantity_".$prono."=1";
    }
	
	
		/*$html .=' <input type="hidden" name="item_name_'.$prono.'" value="'.$carts['name'].'">';
		$html .='  <input type="hidden" name="item_number_'.$prono.'" value="skyeno-00'.$carts['product_id'].'"> ';
		$html .='<input type="hidden" name="amount_'.$prono.'" value="'.$carts['price'].'">';
		$html .='<input type="hidden" name="quantity_'.$prono.'" id="quantity" min="1" value="'.$carts['qty'].'" class="form-control">';*/
	
		
	
		/*$url.="&orderid=".$_GET['order_id'];
		$url.="&amount_=".$_GET['amt'];*/
	
		/*$html .=' <input type="hidden" name="item_name_'.$prono.'" value="'.$carts['name'].'">';
		$html .='  <input type="hidden" name="item_number_'.$prono.'" value="skyeno-00'.$carts['product_id'].'"> ';
		$html .='<input type="hidden" name="amount_'.$prono.'" value="'.$carts['price'].'">';
		$html .='<input type="hidden" name="quantity_'.$prono.'" id="quantity" min="1" value="'.$carts['qty'].'" class="form-control">';*/
	
	$url.="&button_subtype=products";
	$url.="&no_note=0&tx=TransactionID";
	$url.="&notify_url=https://maestrosinfotech.com/arrive/web/ipn.php";
	$url.="&return=https://maestrosinfotech.com/arrive/web/success.php";
	$url.="&cancel=https://maestrosinfotech.com/arrive/web/checkout.php";
	$url.="&no_shipping=1&rm=2&cbt=Return to Skyeno";
	$url.="&discount_amount_1=10&shipping_1=10";
	$url.="&tax_1=1.15";
	$url.="&currency_code=EUR";
	header('location:'.$url);
	/*$html .='<input type="hidden" name="button_subtype" value="products"> <input type="hidden" name="no_note" value="0"><input type="hidden" name="tx" value="TransactionID"><input type="hidden" name="notify_url" value="http://developer-deepika.tk/skyeno/pages/ipn.php"><input type="hidden" name="return" value="http://developer-deepika.tk/skyeno/pages/success.php">
 		<input type="hidden" name="cancel" value="'.$basurl.'pages/checkout.php">
	<input type="hidden" name="no_shipping" value="0"><input type="hidden" name="rm" value="2">
    <input type="hidden" name="cbt" value="Return to Skyeno"><input type="hidden" name="discount_amount_1" value="10">
    <input type="hidden" name="shipping_1" value="10.00">
    <input type="hidden" name="tax_1" value="1.15"><input type="hidden" name="currency_code" value="USD"></form><script type="text/javascript">
			document.paymentpaypal.submit();
		</script>';
		echo $html ;*/

?>
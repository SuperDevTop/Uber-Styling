<?php include_once("../common/config.php");
//Set variables for paypal form
$paypal_url = 'https://www.paypal.com/cgi-bin/webscr'; 
//Test PayPal API URL
$paypal_email = 'maurysavio@libero.it';
?>
<title> Paypal Integration in PHP</title>
<div class="container">
	<div class="col-lg-12">
	<div class="row">
	
			
			<div class="col-sm-4 col-lg-4 col-md-4" style="width:300px;height:300px;border:2px solid red;float:left;margin-left:20px">
			<div class="thumbnail"> 
			
								


			<form action="<?php echo $paypal_url; ?>" method="post">			
			<!-- Paypal business test account email id so that you can collect the payments. -->
			<input type="hidden" name="business" value="<?php echo $paypal_email; ?>">			
			<!-- Buy Now button. -->
			<input type="hidden" name="cmd" value="_xclick">			
			<!-- Details about the item that buyers will purchase. -->
			<input type="hidden" name="item_name" value="<?php echo $row['order_id']; ?>">
		<!--	<input type="hidden" name="item_number" value="<?php echo $row['id']; ?>">-->
			<input type="hidden" name="amount" value="<?php echo $row['amt']; ?>">
				<input type="hidden" name="currency_code" value="USD">			
			<!-- URLs -->
			<input type='hidden' name='cancel_return' value='https://www.projectlikes.it/PayPal/cancel.php'>
			<input type='hidden' name='return' value='https://www.projectlikes.it/PayPal/success.php'>						
			<!-- payment button. -->
			<input type="image" name="submit" border="0"
			src="https://www.paypalobjects.com/en_US/i/btn/btn_buynow_LG.gif" alt="PayPal - The safer, easier way to pay online">
			<img alt="" border="0" width="1" height="1" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" >    
			</form>
			</div>
			</div>

		</div>		
	</div>	
		
</div>
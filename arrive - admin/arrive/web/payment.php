<?php include('../common/config.php');
extract($_REQUEST);
if($user_id=='')
{
    echo "Enter user_id";			
}
elseif($booking_id=='')
{
    echo "Enter booking_id";			
}
else if($customer_id=='')
{
    echo "Enter customer_id";			
}
else if($amount=='')
{
    echo "Enter amount";           
}
$tripid = "Arive".rand(1000,9999);

$bookingId = $_REQUEST['booking_id'];
$userId = $_REQUEST['user_id'];
$customerId = $_REQUEST['customer_id'];
$amount = $_REQUEST['amount'];
?>
<form action="https://www.sandbox.paypal.com/cgi-bin/webscr" name="app1" method="post" target="_top">
    
    <input type='hidden' name='business' value='jitenpj74@gmail.com'> 
          <input type='hidden' name='lc' value='IN'> 
          <input type="hidden" name="upload" value="1">
            <input type='hidden' name='item_name' value="<?php echo $booking_id;?>_<?php echo $user_id; ?>_<?php echo $customer_id; ?>"> 
             

            <input type='hidden'name='item_number' value='ARRVe#N<?php echo $booking_id;?>'> 
            <input type='hidden' name='amount' value="<?php echo $amount; ?>"> 
            <input type='hidden' name='no_shipping' value='1'> 
            
            <input type='hidden' name='currency_code' value='USD'> 
            <input type="hidden" name="no_note" value="0">
             <input type="hidden" name="tx" value="TransactionID">
            <input type='hidden'name='notify_url' value='https://maestrosinfotech.com/arrive/web/notify.php'>
            <input type='hidden' name='cancel_return'value='https://maestrosinfotech.com/arrive/web/cancel.php'>
            <input type='hidden' name='return' value='https://maestrosinfotech.com/arrive/web/successful.php?booking_id=<?php echo $booking_id;?>&tripid=<?php echo $tripid;?>'>
            <input type="hidden" name="cmd" value="_xclick"> 
            <p class="parbtn"><button type="submit" name="pay_now" id="pay_now" class="btnprimary" Value="Pay Now" >Pay Now</button></p>
        </form>

<script type="text/javascript">

            //document.app1.submit();

        </script>
<style>
    p.parbtn {
    width: 100%;
    height: 100vh;
    position: relative;
}
    button#pay_now {
  padding: 14px 33px;
    font-size: 50px;
    text-transform: capitalize;
    background: #1e8c23;
    color: white;
    border: none;
    border-radius: 2px;
    position: absolute;
    left: 0;
    bottom: 0px;
    right: 0px;
    top: 0px;
    height: 200px;
    width: 550px;
    margin: auto;
    }
    
</style>
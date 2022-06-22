<?php include('../common/config.php');
extract($_POST);

$order=mysqli_fetch_array(mysqli_query($conn,"select * from booking where booking_id='".$_GET['booking_id']."'"));

 $tripid=$_GET['tripid'];

	 $strtotime=strtotime('now');
$update_Sts=mysqli_query($conn, "UPDATE `booking` SET `mode`='4' AND `transaction_id`='$tripid'  where booking_id='".$_GET['booking_id']."'");
echo "Payment Successfully";

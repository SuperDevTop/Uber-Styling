<?php include('../common/config.php');
extract($_POST);
 $price=($_GET['amounts']*5)/100;
$update_Sts=mysqli_query($conn, "UPDATE `orders` set `payment`='1' where user_id='".$_GET['id']."' and order_id='".$_GET['order_id']."'");

$check=mysqli_query($conn, "SELECT * FROM `referal` where `use_user_id`='".$_GET['id']."'");
$fetch=mysqli_fetch_array($check);
    $fetch['status'];
    if ($fetch['status']=='0') {
        
       $updaterfer=mysqli_query($conn, "UPDATE `referal` SET `status`='1', `price`='$price',`order_id`='".$_GET['order_id']."' where `use_user_id`='".$fetch['use_user_id']."'");
    }
    


$user_info= mysqli_query($conn,"SELECT * from `signup` where id='".$_GET['id']."' "); 
$userI = mysqli_fetch_array($user_info);
$email = $userI['email'];
$name = $userI['fname'];
$subject = "Order Complete Confirmation";

$messagetrrt = '<div class="ordersucessful">
        <div class="container">
            <div class="ordersucessful_blog">
                <img src="http://maestrosinfotech.org/Projectlikes/images/payment_successful.gif">
                <h1>Order Complete Successfully!</h1>
                <p>Thank you for ordering. we received your order and wil being processing it soon. Your Order information appears below.</p>
                
            </div>
        </div>
    </div>
    <style type="text/css">
        .ordersucessful_blog {
    background: #fff;
    border: 1px solid #ddd;
    width: 500px;
    margin: 0 auto;
    padding: 22px 21px;
}
.ordersucessful {
    padding: 40px 0;
}
.ordersucessful_blog img {
    width: 150px;
    margin: 0 auto;
    display: block;
}
.ordersucessful_blog a {
    background: #C23426;
    color: #fff;
    padding: 10px 30px;
    display: inline-block;
    border-radius: 2px;
    box-shadow: 2px 3px 3px #0000004a;
    margin: 10px 0;
}
.ordersucessful_blog p {
    font-size: 14px;
    color: gray;
}
.ordersucessful_blog h1 {
    font-size: 20px;
    color: #000;
    font-weight: bold;
    margin: 25px 0 15px;
}
.ordersucessful_blog {
    background: #fff;
    border: 1px solid #ddd;
    width: 500px;
    margin: 0 auto;
    padding: 22px 21px;
    text-align: center;
} 
    </style>';

mailds($email,$name,$subject, $messagetrrt,'');

if($update_Sts) {
    $_SESSION['user_id']=$_GET['id'];
    echo '<script>window.location="../ordersuccessful.php"; </script>';
}

 ?>
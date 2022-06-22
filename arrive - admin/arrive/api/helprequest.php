<!DOCTYPE html>
<html lang="en">
<head>
  <title>Confirmation</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<?php 
ini_set("display_errors", 1);
$requestid = $_REQUEST['request'];
 $db = mysqli_connect('mbuhjudeohiodbinstance.c2noxm7dqd9t.us-east-2.rds.amazonaws.com','mbuhjude','passw0rd4583','findam_api')  or die('Error connecting to MySQL server.');

if(isset($_POST['requestresult'])){
  // 
	$requestid = $_POST['req_id'];
	$decryptid = base64_decode($requestid);
	$stratus =  $_POST['requestresult'];
	mysqli_query($db,"update findam_helpalert_req set status = '$stratus' where id = '$decryptid'");

	$result = mysqli_query($db,"select circle_id,mobile_number from findam_helpalert_req where id = '$decryptid'");
	$row = mysqli_fetch_assoc($result);
	$circleid = $row['circle_id'];
	$mobile_number = $row['mobile_number'];

	$checkreq = mysqli_num_rows(mysqli_query($db,"select * from findam_help_contact where mobile = '$mobile_number' and circle_id = '$circleid' and contact_status = '0'"));
	if($checkreq>0){
		$res = mysqli_query($db,"update findam_help_contact set contact_status = '$stratus' where mobile = '$mobile_number' and circle_id = '$circleid'");
		if($res){
			$statusreq = $stratus ? 'Accepted' : 'Rejected';
			echo 'You have successfully '.$statusreq;
		}else{
			echo 'Some error occurred';
		}
	}else{
		echo 'You have already submitted your response';
	}

}else if(isset($requestid) && $requestid!=''){
	$decryptid = base64_decode($requestid);
	//check in database that user is already rejected or accepted this request
	 $checkreq = mysqli_num_rows(mysqli_query($db,"select * from findam_helpalert_req where id = '$decryptid' and status = '0'"));
	if($checkreq>0){
		?>
		<div class="container">
		  <form method="post">
		    <input type="hidden" value="<?php echo $requestid; ?>" name="req_id">
		    <button type="submit" class="btn btn-success" name="requestresult" value="1">Accept</button>
		    <button type="submit" class="btn btn-danger" name="requestresult" value="0">Reject</button>
		  </form>
		</div>
		<?php 
	}else{
       echo "Request expired";
		
	}
	
} 
?>

</body>
</html>

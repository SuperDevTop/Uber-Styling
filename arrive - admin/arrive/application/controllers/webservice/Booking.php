<?php
defined('BASEPATH') OR exit('No direct script access allowed');
error_reporting(0);

class Booking extends CI_Controller {

    public function __construct() {
        parent::__construct();
    }

    public function booking() 
	{
        $postData = $this->input->post();
        $startPointLat = $postData['start_point_lat'];
        $startPointLong = $postData['start_point_long'];
        $endPointLat = $postData['end_point_lat'];
        $endPointLong = $postData['end_point_long'];
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('start_point', 'Start Point', 'required|xss_clean');
        $this->form_validation->set_rules('end_point', 'End point', 'required|xss_clean');
        $this->form_validation->set_rules('start_point_lat', 'Start point lat', 'required|xss_clean');
        $this->form_validation->set_rules('start_point_long', 'Start point long', 'required|xss_clean');
        $this->form_validation->set_rules('end_point_lat', 'End point lat', 'required|xss_clean');
        $this->form_validation->set_rules('end_point_long', 'End point long', 'required|xss_clean');
        $this->form_validation->set_rules('no_passanger', 'Number of passanger', 'required|xss_clean');
       //$this->form_validation->set_rules('promocode', 'Promocode', 'required|xss_clean');
        $this->form_validation->set_rules('vehicle_type_id', 'vehicle type Id', 'required|xss_clean');
        $this->form_validation->set_rules('vehicle_sub_type_id', 'vehicle sub type Id', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        $userPath = "https://maestrosinfotech.com/arrive/uploads/users/";
        $defaultPath = "https://maestrosinfotech.com/arrive/uploads/users/UserImage.jpg";
        if ($this->form_validation->run()) 
		{
            $bookingRandId = $this->common->generateJobCode();
            $distanceDuration = $this->common->get_driving_distance($startPointLat, $endPointLat, $startPointLong, $endPointLong);
           $vehicleSubType = $this->common->find('vehicle_subtype_master', $select = 'id, vehicle_model,base_price', $where = array('id' => $postData['vehicle_sub_type_id']), $resultType = 'array', $orderby = array());
           $promoCodeData = $this->common->find('promo_code', $select = '*', $where = array('id' => $postData['promocode']), $resultType = 'array', $orderby = array());
           $amount = $vehicleSubType['base_price']*$distanceDuration['distance'];
           if($promoCodeData['promo_type'] == '1')
		   {
               $deduct_amt = $amount - $promoCodeData['discount'];
           }else{
               
               
               $deduct_amt = $amount - ($amount *($promoCodeData['discount']/100));
               
           }
           
        //           print_r($vehicleSubType);echo "<pre>";
        //           print_r($distanceDuration);echo "<pre>";die();
        // print_r($distanceDuration);die();
           // $otp = '0000';
            $otp = rand(1000, 9999);
            if(empty($distanceDuration['time']))
			{
                $response['message'] = 'Booking not available between these points.';
                $response['status'] = 'false';
                echo json_encode($response);
                exit;
            }
            $post = array(
                'booking_id' => $bookingRandId,
                'user_id' => $postData['user_id'],
                'start_point' => $postData['start_point'],
                'end_point' => $postData['end_point'],
                'start_point_lat' => $postData['start_point_lat'],
                'start_point_long' => $postData['start_point_long'],
                'end_point_lat' => $postData['end_point_lat'],
                'end_point_long' => $postData['end_point_long'],
                'no_passanger' => $postData['no_passanger'],
                //'no_luggage' => $postData['no_luggage'],
                'vehicle_type_id' => $postData['vehicle_type_id'],
                'vehicle_sub_type_id' => $postData['vehicle_sub_type_id'],
                'promocode' => $postData['promocode'],
                'duration' => $distanceDuration['time'],
                'distance' => $distanceDuration['distance'] . '' . 'km',
                'otp'=>$otp,
                'amount'=>$deduct_amt,
                'schedule_date' => date("Y-m-d"),
                'schedule_time' => date("H:i:s"),
            );
            // print_r($post);exit;
            $userData = $this->common->find('user', $select = 'id, first_name, last_name, img, token, appPlatform', $where = array('id' => $postData['user_id']), $resultType = 'array', $orderby = array());
            
            //$whereUser = array('user_id'=>$postData['user_id']);
           // $getratingdetails = $this->common->findAll('review_rating_user', $select = '*', $whereUser, $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
            $getratingdetails = $this->common->avgRating($postData['user_id']);
            //print_r($getratingdetails);die();


            $userToken = $userData['token'];
            $userAppPlatform = $userData['appPlatform'];
            $bookingId = $this->common->save('booking', $post); 
            //echo $this->db->last_query();die();
            $drivers = $this->common->driversearch($postData['start_point_lat'], $postData['start_point_long']);
           // echo $this->db->last_query();die();
            if (!empty($drivers)) 
			{
                //send push one near by driver not all driver in range
                $driverId = $drivers[0]['id'];
                $driverData = $this->common->find('driver', $select = 'appPlatform, token', $where = array('id' => $driverId), $resultType = 'array', $orderby = array());
                $postBookingDriver = array(
                    'driver_id' => $driverId,
                    'booking_id' => $bookingId,
                    'mode' => '0'
                );
                $bookingDriverId = $this->common->save('booking_driver', $postBookingDriver);
                $token = $driverData['token'];
                $for = $driverData['appPlatform'];
                $push_message = 'You have a booking';
                $push_array = array(
                    "alert" => $push_message,
                    "booking_id" => $bookingId,
					"booking_rand" =>$bookingRandId, 
                    "userName" => $userData['first_name'] . ' ' . $userData['last_name'],
                    "userImg" => (($userData['img'] != '') ? $userPath . $userData['img'] : $defaultPath),
                    "start_point" => $postData['start_point'],
                    "end_point" => $postData['end_point'],
                    "user_rating" => empty($getratingdetails[0]['Averagerating'])?'':$getratingdetails[0]['Averagerating'],
                    "user_id" => $postData['user_id'],
                    'duration' => $distanceDuration['time'],
                    'vehicleSubTypeName' => $vehicleSubType['vehicle_model'],
                    'amount'=>$amount,
                    'deduct_amt'=>$deduct_amt,
                    "sound" => 'default',
					"title"=>'New Booking',
                    "content-available" => 1,
                    "push_tag" => "booking",
                    //"avgRating"=>$getratingdetails[0]['Averagerating']
                );
                //UPDATE DRIVER ID
                
                $postBookingDriverId = array(
                    'driver_id' => $driverId
                );
                $this->common->update('booking', $postBookingDriverId, $where = array('id' => $bookingId));
                
                
                
                $notification= $this->common->send_driver_push_fcm($token, $push_array, $for, $PEM_MODE = '2');

                $bookingData = $this->common->find('booking', $select = 'id, booking_id as bookingCode, start_point, end_point, start_point_lat, start_point_long, end_point_lat, end_point_long, no_passanger, duration, distance,amount as deduct_amt', $where = array('id' => $bookingId), $resultType = 'array', $orderby = array());
               $bookingData['amount'] = $amount;
			   $response['todriver']=$notification;
                $response['result'] = ($bookingData)?$bookingData:array();
                $response['message'] = 'Booking is Successfull.';
                $response['status'] = 'true';
            } else
			{
                $push_message = 'No driver available';
                $push_array = array(
                    "alert" => $push_message,
                    "sound" => 'default',
					"title"=>'New Booking',
                    "content-available" => 1,
                    "push_tag" => "no_driver"
                );
                $this->common->send_user_push_fcm($userToken, $push_array, $userAppPlatform, $PEM_MODE = '2');
                $response['message'] = 'No driver available';
                $response['status'] = 'false';
            }
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }

        echo json_encode($response);
    }
    public function get_driving_distances()
	{
		$postData = $this->input->post();
        $startPointLat = $postData['start_point_lat'];
        $startPointLong = $postData['start_point_long'];
        $endPointLat = $postData['end_point_lat'];
        $endPointLong = $postData['end_point_long'];
        $this->form_validation->set_rules('start_point_lat', 'Start point lat', 'required|xss_clean');
        $this->form_validation->set_rules('start_point_long', 'Start point long', 'required|xss_clean');
        $this->form_validation->set_rules('end_point_lat', 'End point lat', 'required|xss_clean');
        $this->form_validation->set_rules('end_point_long', 'End point long', 'required|xss_clean');
		$distanceDuration = $this->common->get_driving_distance($startPointLat, $endPointLat, $startPointLong, $endPointLong);
		if($distanceDuration>0)
		{
			    if($distanceDuration['distance']==null || $distanceDuration['time']==null)
				{
					$response['message'] = 'no driver available';
                	$response['status'] = 'false';
				}else
				{
                $response['message'] = $distanceDuration;
                $response['status'] = 'true';
				}
		}else
		{
			$response['message'] = validation_errors();
            $response['status'] = 'false';
		}
		 echo json_encode($response);
	}
    public function accept_reject() 
    {
        $post = $this->input->post();
        $defaultPath = "https://maestrosinfotech.com/arrive/uploads/users/UserImage.jpg";
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_rules('booking_id', 'Booking Id', 'required|xss_clean');
        $this->form_validation->set_rules('mode', 'Mode', 'required|xss_clean');  //accept/reject
        $driverPath = base_url() . '/uploads/drivers/';
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) 
        {
            $driver_id = $this->input->post('driver_id');
            $booking_id = $this->input->post('booking_id');
            if ($post['mode'] == 'accept') 
			{
                $userData = $this->common->findAllWithJoin('booking', $select = 'user.id as userId, user.token, user.appPlatform,booking.mode', $join = array('user' => 'user.id = booking.user_id'), $where = array('booking.id' => $post['booking_id']), $orderby = array(), $resultType = 'array', $limit = '');
                //echo $this->db->last_query();die();

                $token = $userData[0]['token'];
                $for = $userData[0]['appPlatform'];
                $driverData = $this->common->find('driver', $select = 'id, first_name, middle_name, last_name, driver_id, img, mobile, latitude, longitude,licence_plate', $where = array('id' => $post['driver_id']), $resultType = 'array', $orderby = array());
                $bookingData = $this->common->find('booking', $select = 'id, booking_id, duration, distance, amount, end_point, start_point, start_point_lat, start_point_long, end_point_lat,end_point_long,otp,vehicle_sub_type_id', $where = array('id' => $post['booking_id']), $resultType = 'array', $orderby = array());
                $vehical_type = $this->common->find('vehicle_subtype_master',$select='id,vehicle_type_id,vehicle_model,base_price',$where = array('id'=>$bookingData['vehicle_sub_type_id']),$resultType = 'array',$orderby = array());
                
                /*$push_array = array(
                    "alert" => $push_message,
                    "driverId" => $post['driver_id'],
                    "driverName" => $driverData['first_name'] . ' ' . $driverData['middle_name'] . ' ' . $driverData['last_name'],
                    'driverImg' => (($driverData['img'] != '') ? $driverPath . $driverData['img'] : $defaultPath),
                    "sound" => 'default',
                    "content-available" => 1,
                    "push_tag" => "accept"
                );*/
                $userStartLat = $bookingData['start_point_lat'];
                $userStartLong = $bookingData['start_point_long'];
                $userEndLat = $bookingData['end_point_lat'];
                $userEndLong = $bookingData['end_point_long'];

                $driver_id_rating=$driverData['id'];
                $driverdata_rating = $this->common->getDriverratingList($driver_id_rating);
                //print_r($driverdata_rating->rating);
                //die;
                $driverdata_rating_val='0';
                if(empty($driverdata_rating))
                {
                    $driverdata_rating_val=0;

                }
                else
                {
                   $driverdata_rating_val=$driverdata_rating->rating;
                }
                //print_r($userData);die();
                if($userData[0]['mode'] == 5)
                {
                    $push_message = 'Driver accept your schedule booking request';
                    $push_array = array(
                    "alert" => $push_message,
                    "bookingId" => $post['booking_id'],
                    "driverName" => $driverData['first_name'] . ' ' . $driverData['middle_name'] . ' ' . $driverData['last_name'],
                    "driverId" => $driverData['id'],
                    "driverUniqueId" => $driverData['driver_id'],
                    "driverPhone" => (($driverData['mobile'] != '') ? $driverData['mobile'] : ''),
                    "driverImg" => (($driverData['img'] != '') ? $driverPath . $driverData['img'] : $defaultPath),
                    "destination" => $bookingData['end_point'],
                    "bookingId" => $bookingData['id'],
                    "bookingUniqueId" => $bookingData['booking_id'],
                    "start_point" => $bookingData['start_point'],
                    "end_point" => $bookingData['end_point'], //double time same as destination
                    "distance" => $bookingData['distance'] . ' km',
                    "duration" => $bookingData['duration'],
                    "amount" => $bookingData['amount'],
                    "start_point_lat" => trim($userStartLat),
                    "start_point_lat" => trim($userStartLong),
                    "end_point_lat" => trim($userEndLat),
                    "end_point_long" => trim($userEndLong),
                    "driverLat" => $driverData['latitude'],
                    "driverLong" => $driverData['longitude'],
                    "carType" => $vehical_type['vehicle_model'],
                    "carNo" => $driverData['licence_plate'],
                    "otp" => $bookingData['otp'],
                    "rating" => $driverdata_rating_val,
                    //"carNo" => 'cab1234',
                    "sound" => 'default',
                    "content-available" => 1,
                    "push_tag" => "schedule_later_accept",
                    "title"=>"Driver accept your schedule booking request"
                );
                $this->common->send_user_push_fcm($token, $push_array, $for, $PEM_MODE = '2');

                }else
                {
                    $push_message = 'Driver accept your request';
                    $push_array = array(
                    "alert" => $push_message,
                    "bookingId" => $post['booking_id'],
                    "driverName" => $driverData['first_name'] . ' ' . $driverData['middle_name'] . ' ' . $driverData['last_name'],
                    "driverId" => $driverData['id'],
                    "driverUniqueId" => $driverData['driver_id'],
                    "driverPhone" => (($driverData['mobile'] != '') ? $driverData['mobile'] : ''),
                    "driverImg" => (($driverData['img'] != '') ? $driverPath . $driverData['img'] : $defaultPath),
                    "destination" => $bookingData['end_point'],
                    "bookingId" => $bookingData['id'],
                    "bookingUniqueId" => $bookingData['booking_id'],
                    "start_point" => $bookingData['start_point'],
                    "end_point" => $bookingData['end_point'], //double time same as destination
                    "distance" => $bookingData['distance'] . ' km',
                    "duration" => $bookingData['duration'],
                    "amount" => $bookingData['amount'],
                    "start_point_lat" => trim($userStartLat),
                    "start_point_lat" => trim($userStartLong),
                    "end_point_lat" => trim($userEndLat),
                    "end_point_long" => trim($userEndLong),
                    "driverLat" => $driverData['latitude'],
                    "driverLong" => $driverData['longitude'],
                    "carType" => $vehical_type['vehicle_model'],
                    "carNo" => $driverData['licence_plate'],
                    "otp" => $bookingData['otp'],
                    "rating" => $driverdata_rating_val,
                    //"carNo" => 'cab1234',
                    "sound" => 'default',
                    "content-available" => 1,
                    "push_tag" => "accept",
                    "title"=>"Driver accept your request",
                    "body"=>'your Estimated time '.$bookingData['duration']
                );
                $this->common->send_user_push_fcm($token, $push_array, $for, $PEM_MODE = '2');

                }
                
                $postBookingAccept = array(
                    'mode' => '1',
                    'driver_id' => $post['driver_id']
                );
                $this->common->update('booking', $postBookingAccept, $where = array('id' => $post['booking_id']));
                $this->common->update('booking_driver', $postBookingAccept, $where = array('booking_id' => $post['booking_id'], 'driver_id' => $post['driver_id']));

                $response['message'] = "Driver accept booking request";
                $response['status'] = 'true';
            } elseif ($post['mode'] == 'reject') 
            {
                //echo 'ds';die();
                
                $sqlQuery = "update booking set reject_by_driver=concat(reject_by_driver,'$driver_id,') where id='$booking_id'";
                $this->common->sqlInsertQuery($sqlQuery);
                $sqlQuery = "select * from booking where id='$booking_id'";
                $bookingData = $this->common->sqlGetQuery($sqlQuery);
                
                if($bookingData[0]['driver_id'] == $post['driver_id']){
                    $userData = $this->common->findAllWithJoin('booking', $select = 'user.id as userId, user.token, user.appPlatform,booking.mode', $join = array('user' => 'user.id = booking.user_id'), $where = array('booking.id' => $post['booking_id']), $orderby = array(), $resultType = 'array', $limit = '');
                    $driverData = $this->common->find('driver', $select = 'id, first_name, middle_name, last_name, driver_id, img, mobile, latitude, longitude,licence_plate', $where = array('id' => $post['driver_id']), $resultType = 'array', $orderby = array());
                    $token = $userData[0]['token'];
                    $for = $userData[0]['appPlatform'];
                    $push_message = 'Driver reject your request';
                    $push_array = array(
                        "alert" => $push_message,
                        "bookingId" => $post['booking_id'],
                        "driverName" => $driverData['first_name'] . ' ' . $driverData['middle_name'] . ' ' . $driverData['last_name'],
                        "driverId" => $driverData['id'],
                        "driverUniqueId" => $driverData['driver_id'],
                        "driverPhone" => (($driverData['mobile'] != '') ? $driverData['mobile'] : ''),
                        "driverImg" => (($driverData['img'] != '') ? $driverPath . $driverData['img'] : $defaultPath),
                        "destination" => $bookingData['end_point'],
                        "bookingId" => $bookingData['id'],
                        "bookingUniqueId" => $bookingData['booking_id'],
                        "start_point" => $bookingData['start_point'],
                        "end_point" => $bookingData['end_point'], //double time same as destination
                        "distance" => $bookingData['distance'] . ' km',
                        "duration" => $bookingData['duration'],
                        "amount" => $bookingData['amount'],
                        "driverLat" => $driverData['latitude'],
                        "driverLong" => $driverData['longitude'],
                        "carNo" => $driverData['licence_plate'],
                        "otp" => $bookingData['otp'],
                        "sound" => 'default',
                        "content-available" => 1,
                        "push_tag" => "reject_by_driver",
                        "title"=>"Driver reject your request"
                    );
                    
                    
                    $this->common->send_user_push_fcm($token, $push_array, $for, $PEM_MODE = '2');   
                }
                
                $sqlQuery = "SELECT * from driver where latitude <>'' AND longitude<>'' AND status = '1'";
                $data = $this->common->sqlGetQuery($sqlQuery);   
                if (!empty($bookingData[0]['reject_by_driver'])) 
                {
                    $rejectDeriverId = explode(",", $bookingData[0]['reject_by_driver']);
                    for ($i = 0; $i < sizeof($rejectDeriverId); $i++) {
                        for ($j = 0; $j < sizeof($data); $j++) {
                            if ($data[$j]['id'] == $rejectDeriverId[$i]) {
                                unset($data[$j]);
                                break;
                            }
                        }
                        $data = array_values($data);
                    }
                }
                if (empty($data)) 
                {
                    $sqlQuery = "update booking set driver_id='0' where id='$booking_id'";
                    $id = $this->common->sqlInsertQuery($sqlQuery);
                    $result['result'] = (object) [];
                    $result['status'] = 'false';
                    $result['message'] = 'No Driver found.';
                    echo json_encode($result);
                    exit;
                }
                $temp = 100000.00;
                $keyData = '';
                foreach ($data as $key => $value) 
                {
                    $resDistance = distanceCalculation($value['latitude'], $value['longitude'], $bookingData[0]['start_point_lat'], $bookingData[0]['end_point_lat']);
                    $resDistance['distance'] = (float) str_replace('km', '', $resDistance['distance']);
                    $resDistance['distance'] = (float) str_replace('m', '', $resDistance['distance']);
                    if ($temp > $resDistance['distance']) {
                        $temp = $resDistance['distance'];
                        $keyData = $key;
                    }
                    $data[$key]['time&distance'] = $resDistance;
                }   
                $data = $data[$keyData];
                //print_R($data);
                $sqlQuery = "update booking set driver_id='" . $data['id'] . "' where id='$booking_id'";
                $id = $this->common->sqlInsertQuery($sqlQuery);
                
                $sqlQuery = "select b.*,u.first_name,u.last_name,u.img,b.start_point,b.end_point from booking as b left join user as u on b.user_id-u.id  where b.id=(select max(id) from booking)";
                $finalData = $this->common->sqlGetQuery($sqlQuery);
                $finalData = ($finalData[0]);

                $sqlQuery = "select token,appPlatform,id from driver where id=" . $finalData['driver_id'];
                $userData = $this->common->sqlGetQuery($sqlQuery);
                $token = $userData['0']['token'];
                $for = $userData['0']['appPlatform'];
                $push_message = 'You have a booking';
        //                $push_array = array(
        //                    "alert" => $push_message,
        //                    "booking_id" => $finalData['id'],
        //                    "user_id" => $finalData['user_id'],
        //                    "sound" => 'default',
        //                    "content-available" => 1,
        //                    "push_tag" => "DriverConfirmBooking"
        //                );
                $getratingdetails = $this->common->avgRating($finalData['user_id']);
                $vehicleSubType = $this->common->find('vehicle_subtype_master', $select = 'id, vehicle_model', $where = array('id' => $bookingData[0]['vehicle_sub_type_id']), $resultType = 'array', $orderby = array());

                $push_array = array(
                    "alert" => $push_message,
                    "booking_id" => $finalData['id'],
                    "userName" => $finalData['first_name'] . ' ' . $finalData['last_name'],
                    "userImg" => (($finalData['img'] != '') ? $userPath . $finalData['img'] : $defaultPath),
                    "start_point" => $finalData['start_point'],
                    "end_point" => $finalData['end_point'],
                    "user_rating" => empty($getratingdetails[0]['Averagerating'])?'':$getratingdetails[0]['Averagerating'],
                    "user_id" => $finalData['user_id'],
                    'duration' => $data['time&distance'],
                    'vehicleSubTypeName' => $vehicleSubType['vehicle_model'],
                    "sound" => 'default',
                    "content-available" => 1,
                    "push_tag" => "booking",
                    "title"=>$push_message,
                    //"avgRating"=>$getratingdetails[0]['Averagerating']
                );
                
                
        //                $finalData['booking_id'] = empty($finalData['booking_id']) ? '' : $finalData['booking_id'];
        //                $finalData['user_name'] = $finalData['first_name'] . ' ' . $finalData['last_name'];
                //print_r($finalData);die();
                //$push_array = array_merge($push_array, $finalData);
                $this->common->send_driver_push_fcm($token, $push_array, $for, 'You have a new booking!');
                $response['result'] = (object) [];
                $response['status'] = 'true';
                $response['message'] = 'Driver reject booking request.';
      
            }else
            {
                echo '22';
            }
        } else 
        {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function arrived() 
	{
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_rules('booking_id', 'Booking Id', 'required|xss_clean');
        $postData = $this->input->post();
        $userId = $postData['user_id'];
        $driverId = $postData['driver_id'];
        $driverPath = "https://maestrosinfotech.com/arrive/uploads/drivers/";
        if ($this->form_validation->run()) 
		{
            $post = array('mode' => '7');
            $updateBookingStatus = $this->common->update('booking', $post, $where = array('id' => $postData['booking_id']));
            //Send push to user
            $userData = $this->common->find('user', $select = 'id, token, appPlatform', $where = array('id' => $userId), $resultType = 'array', $orderby = array());
            $driverData = $this->common->find('driver', $select = 'id, first_name, middle_name, last_name, driver_id, img, mobile, latitude, longitude,licence_plate', $where = array('id' => $driverId), $resultType = 'array', $orderby = array());
        //            $driverRating = $this->common->find('rating', $select = 'rating', $where = array('driver_id' => $driverId, 'rate_for' => 'driver'), $resultType = 'array', $orderby = array());
            $bookingData = $this->common->find('booking', $select = 'id, booking_id, duration, distance, amount, end_point, start_point, start_point_lat, start_point_long, end_point_lat, end_point_long,otp,vehicle_sub_type_id', $where = array('id' => $postData['booking_id']), $resultType = 'array', $orderby = array());
            $vehical_type = $this->common->find('vehicle_subtype_master',$select='id,vehicle_type_id,vehicle_model,base_price',$where = array('id'=>$bookingData['vehicle_sub_type_id']),$resultType = 'array',$orderby = array());
            $driver_id_rating=$driverData['id'];
            $driverdata_rating = $this->common->getDriverratingList($driver_id_rating);
			$driverdata_rating_val='0';
			if(empty($driverdata_rating))
			{
				$driverdata_rating_val=0;

			}
			else
			{
			   $driverdata_rating_val=$driverdata_rating->rating;
			}
               // print_r($driverData);die();
            $userStartLat = $bookingData['start_point_lat'];
            $userStartLong = $bookingData['start_point_long'];
            $userEndLat = $bookingData['end_point_lat'];
            $userEndLong = $bookingData['end_point_long'];
            //  $distanceTime = $this->common->get_driving_distance($driverData['latitude'], $userStartLat, $driverData['longitude'], $userStartLong);
            $token = $userData['token'];
            $for = $userData['appPlatform'];
            $push_message = 'your driver has arrived at you pickup point';
            $push_array = array(
                "alert" => $push_message,
                "bookingId" => $postData['booking_id'],
                "driverName" => $driverData['first_name'] . ' ' . $driverData['middle_name'] . ' ' . $driverData['last_name'],
                "driverId" => $driverData['id'],
                "driverUniqueId" => $driverData['driver_id'],
                "driverPhone" => (($driverData['mobile'] != '') ? $driverData['mobile'] : ''),
                "driverImg" => (($driverData['img'] != '') ? $driverPath . $driverData['img'] : ''),
        //                "driverRating" => (($driverRating['rating'] != '') ? $driverRating['rating'] : ''),
                "destination" => $bookingData['end_point'],
                "bookingId" => $bookingData['id'],
                "bookingUniqueId" => $bookingData['booking_id'],
                "start_point" => $bookingData['start_point'],
                "end_point" => $bookingData['end_point'], //double time same as destination
                "distance" => $bookingData['distance'] . ' km',
                "duration" => $bookingData['duration'],
                "amount" => $bookingData['amount'],
                "start_point_lat" => trim($userStartLat),
                "start_point_lat" => trim($userStartLong),
                "end_point_lat" => trim($userEndLat),
                "end_point_long" => trim($userEndLong),
                "driverLat" => $driverData['latitude'],
                "driverLong" => $driverData['longitude'],
                "carType" => $vehical_type['vehicle_model'],
                    "carNo" => $driverData['licence_plate'],
                    "otp" => $bookingData['otp'],
                    "rating" => $driverdata_rating_val,
        //                "pickupArrivingTime" => $distanceTime['time'],
                "sound" => 'default',
                "content-available" => 1,
                "push_tag" => "arrived"
            );
            $this->common->send_user_push_fcm($token, $push_array, $for, $PEM_MODE = '2');
            //$otp = $this->common->generate_otp();
            // $otp = '0000';
            // $postOtp = array('otp' => $otp);
            // $this->common->update('booking', $postOtp, $where = array('id' => $postData['booking_id']));
           // $response['result'] = $otp;
            $response['message'] = 'Arrived notification send to user';
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function schedule_later_booking() 
	{
        $postData = $this->input->post();
        $startPointLat = $postData['start_point_lat'];
        $startPointLong = $postData['start_point_long'];
        $endPointLat = $postData['end_point_lat'];
        $endPointLong = $postData['end_point_long'];
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'xss_clean');
        $this->form_validation->set_rules('start_point', 'Start Point', 'required|xss_clean');
        $this->form_validation->set_rules('end_point', 'End point', 'required|xss_clean');
        $this->form_validation->set_rules('start_point_lat', 'Start point lat', 'required|xss_clean');
        $this->form_validation->set_rules('start_point_long', 'Start point long', 'required|xss_clean');
        $this->form_validation->set_rules('end_point_lat', 'End point lat', 'required|xss_clean');
        $this->form_validation->set_rules('end_point_long', 'End point long', 'required|xss_clean');
        $this->form_validation->set_rules('vehicle_sub_type_id', 'vehicle sub type Id', 'required|xss_clean');
        $this->form_validation->set_rules('schedule_date', 'booking date ', 'required|xss_clean');
        $this->form_validation->set_rules('schedule_time', 'booking time', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        $userPath = "https://maestrosinfotech.com/arrive/uploads/users/";
        $defaultPath = "https://maestrosinfotech.com/arrive/uploads/users/UserImage.jpg";
        if ($this->form_validation->run()) 
		{
            $bookingRandId = $this->common->generateJobCode();
            $distanceDuration = $this->common->get_driving_distance($startPointLat, $endPointLat, $startPointLong, $endPointLong);
            //$otp = '0000';
            $otp = rand(1000, 9999);
            $schedule_time='';
            $schedule_date='';
             if(!empty($postData['schedule_date']))
             {
                $schedule_date=$postData['schedule_date'];
             }
             if(!empty($postData['schedule_time']))
             {
                $schedule_time=date('H:i:s',strtotime($postData['schedule_time']));
             }
            $post = array(
                'booking_id' => $bookingRandId,
                'user_id' => $postData['user_id'],
                'driver_id' => $postData['driver_id'],
                'start_point' => $postData['start_point'],
                'end_point' => $postData['end_point'],
                'amount' => $postData['amount'],
                'start_point_lat' => $postData['start_point_lat'],
                'start_point_long' => $postData['start_point_long'],
                'end_point_lat' => $postData['end_point_lat'],
                'end_point_long' => $postData['end_point_long'],
                'vehicle_sub_type_id' => $postData['vehicle_sub_type_id'],
                'schedule_time' => $schedule_time,
                'schedule_date' => $schedule_date,
                'duration' => $distanceDuration['time'],
                'distance' => $distanceDuration['distance'] . '' . 'km',
                'otp'=>$otp,
                'mode'=>'5',
                'booking_type'=>'schedule later',
               // 'schedule_date' => date("Y-m-d"),
                //'schedule_time' => date("H:i:s"),
            );
            $bookingId = $this->common->save('booking', $post);

            if(!empty($postData['driver_id']))
			{
				$getUserData=$this->common->find('user','*',array('id'=>$postData['user_id']));
                $getDriverData = $this->common->find('driver','*',array('id'=>$postData['driver_id']));
                $token = $getDriverData['token'];
                $for = $getDriverData['appPlatform'];
                $push_message = 'A booking schedule for You';
                $getratingdetails = $this->common->avgRating($postData['user_id']);
                $vehicleSubType = $this->common->find('vehicle_subtype_master', $select = 'id, vehicle_model', $where = array('id' => $postData['vehicle_sub_type_id']), $resultType = 'array', $orderby = array());

                $push_array = array(
                    "alert" => $push_message,
                    'booking_id'=>$bookingId,
                    "start_point" => $postData['start_point'],
                    "end_point" => $postData['end_point'],
                    "user_rating" => empty($getratingdetails[0]['Averagerating'])?'':$getratingdetails[0]['Averagerating'],
                    'duration' => $distanceDuration['time'],
                    'vehicleSubTypeName' => $vehicleSubType['vehicle_model'],
                    "sound" => 'default',
                    "content-available" => 1,
                    "push_tag" => "Schedule Booking",
                    "title"=>'Your booking schedule for late',
                );
                $notification=$this->common->send_driver_push_fcm($token, $push_array, $for, $PEM_MODE = '2');
				$response['indriverpart']=$notification;
            }
			$subject = "Your booking schedule for later";
			$headers = "From:Arrive<arrive@maestrosinfotech.com> \r\n";
			$headers .= "MIME-Version: 1.0\r\n";
			$headers .= "Content-type: text/html\r\n";
			$headers .= 'X-Mailer: PHP/' . phpversion();
			$headers .= "X-Priority: 1\n"; // Urgent message!
			$headers .= "Return-Path: jitu.maestros@gmail.com\n"; // Return path for errors
			$headers .= "MIME-Version: 1.0\r\n";
			$headers .= "Content-Type: text/html; charset=iso-8859-1\n";
        	mail($getUserData['email'], $subject, 'Your ride have been schedule at '.$schedule_time.' from Source to destination  with driver name:'.$getDriverData['first_name'].' '.$getDriverData['last_name'].' and mobile:'.$getDriverData['mobile'].'. Thankyou for using our services', $headers);
			
			mail($getDriverData['email'], $subject, 'You have Upcoming ride at  '.$schedule_time.' from Source to destination  with user  name:'.$getUserData['first_name'].' '.$getUserData['last_name'].' and mobile:'.$getUserData['mobile'].'. Thankyou for using our services', $headers);
            $response['message'] = 'Your booking schedule for later';
            $response['status'] = 'true';
            $response['result'] = $bookingId;
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }

        echo json_encode($response);
    }

    public function fare_estimation() 
	{
        $postData = $this->input->post();
        $startPointLat = $postData['start_point_lat'];
        $startPointLong = $postData['start_point_long'];
        $endPointLat = $postData['end_point_lat'];
        $endPointLong = $postData['end_point_long'];
        $vehicle_sub_type_id = $postData['vehicle_sub_type_id'];
        //print_r($vehicle_sub_type_id);die();
        $this->form_validation->set_rules('start_point_lat', 'Start point lat', 'required|xss_clean');
        $this->form_validation->set_rules('start_point_long', 'Start point long', 'required|xss_clean');
        $this->form_validation->set_rules('end_point_lat', 'End point lat', 'required|xss_clean');
        $this->form_validation->set_rules('end_point_long', 'End point long', 'required|xss_clean');
        $this->form_validation->set_rules('vehicle_sub_type_id', 'vehicle sub type Id', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) 
		{
            $distanceDuration = $this->common->get_driving_distance($startPointLat, $endPointLat, $startPointLong, $endPointLong);
           // print_r($distanceDuration);die();
            $vehicleSubTypeData = $this->common->find('vehicle_subtype_master', '*', $where = array('id' => $vehicle_sub_type_id), $resultType = 'array', $orderby = array());

            $amt =   $vehicleSubTypeData['base_price'];
            $finalamt = str_replace('$','', $amt);
            //print_r($aa);die();
            $fare_amt = $distanceDuration['distance'] * $finalamt;
            // $post = array(

            // );
            // $bookingId = $this->common->save('booking', $post);
            $response['message'] = 'Your estimated price .';
            $response['status'] = 'true';
            $response['result'] = round($fare_amt);


        } else {

            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }

        echo json_encode($response);
    }


    public function getVehcledetails() {
        $postData = $this->input->post();
        $startPointLat = $postData['start_point_lat'];
        $startPointLong = $postData['start_point_long'];
        $endPointLat = $postData['end_point_lat'];
        $endPointLong = $postData['end_point_long'];
        //print_r($vehicle_sub_type_id);die();
        $this->form_validation->set_rules('start_point_lat', 'Start point lat', 'required|xss_clean');
        $this->form_validation->set_rules('start_point_long', 'Start point long', 'required|xss_clean');
        $this->form_validation->set_rules('end_point_lat', 'End point lat', 'required|xss_clean');
        $this->form_validation->set_rules('end_point_long', 'End point long', 'required|xss_clean');

        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {

            $where = array(
                'active_flag' => 'yes',
            );
            $getdetails = $this->common->findAll('vehicle_type_master', $select = '*', $where, $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');

            $searchdriverres = $this->common->driver_search($startPointLat,$startPointLong);
        //print_r($searchdriverres);die();
            if(!empty($searchdriverres)){
                $driverlat = $searchdriverres[0]['latitude'];
                $driverlng = $searchdriverres[0]['longitude'];
                $driverdistance = $searchdriverres[0]['distance'];

            }

            $distanceDurationnnn = $this->common->get_driving_distance($startPointLat, $driverlat, $startPointLong, $driverlng);
        //print_r($distanceDuration);die();

       // print_r($distanceDuration);die();''
            $finaltime = $distanceDurationnnn['time'];
            // $time1=explode(' ', $finaltime);
            // $finaltime=$time1[0].':'.$time1[2];
            // $finaltime=(string)$finaltime;
            //echo $finaltime;exit;

      //   print_r($time);
            // //$replace=['min','mins','s'];
            // $finaltime = str_replace($replace, '', $time);
            //echo $finaltime;die();


            $details = array();
            foreach ($getdetails as $key => $value) {
                $typeid = $value['id'];
                $con = array(
                    'active_flag' => 'yes',
                    'vehicle_type_id' => $typeid

                );
                $getvechileres = $this->common->findAll('vehicle_subtype_master', $select = '*', $con, $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
                $vechiledetal=array();
                foreach ($getvechileres as $keyyy => $valueee) 
                {
                    $amt =   $valueee['base_price'];
                    $finalamt = str_replace('$','', $amt);
                    $distanceDuration = $this->common->get_driving_distance($startPointLat, $endPointLat, $startPointLong, $endPointLong);
                    $valueee['fare_amt'] = round($distanceDuration['distance'] * $finalamt);
                    $valueee['drivertaketimetoreach'] = ($finaltime)?$finaltime:"";
                    $valueee['driverdistancefronuser'] = ($distanceDurationnnn['distance'])?$distanceDurationnnn['distance']:'';
                    $valueee['selectimg'] = base_url() . '/uploads/select1.png';
                    $valueee['nonselectimg'] = base_url()  . '/uploads/nonSelect1.png';
                    //$vechiledetal[] = $valueee;
                    array_push($vechiledetal,$valueee);
                }
                $getvehicledetailsArr = array(
                    'vehicleTypeId' => $value['id'],
                    'vehicleTypeName' => $value['vehicle_type'],
                    'vehicleType' => $vechiledetal
                );
                $details[] = $getvehicledetailsArr;
                $vechiledetal = '';
            }
            $response['message'] = "Details fetch Successfully.";
            $response['result'] = $details;
            $response['status'] = 'true';



           //  $distanceDuration = $this->common->get_driving_distance($startPointLat, $endPointLat, $startPointLong, $endPointLong);
           // // print_r($distanceDuration);die();
           //  $vehicleSubTypeData = $this->common->find('vehicle_subtype_master', '*', $where = array('id' => $vehicle_sub_type_id), $resultType = 'array', $orderby = array());

           //  $amt =   $vehicleSubTypeData['base_price'];
           //  $finalamt = str_replace('$','', $amt);
           //  //print_r($aa);die();
           //  $fare_amt = $distanceDuration['distance'] * $finalamt;
           //  // $post = array(

           //  // );
           //  // $bookingId = $this->common->save('booking', $post);
           //  $response['message'] = 'Your estimated price .';
           //  $response['status'] = 'true';
           //  $response['result'] = round($fare_amt);


        } else {

            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }

        echo json_encode($response);
    }


   

    public function no_driver_accept() {
        $currentTime = date('H:i:s');
        $cenvertedTime = date("H:i:s", time() + 20);
        $where = array(
            'schedule_time' => $cenvertedTime,
        //            'schedule_time' => '16:38:44',
            'mode' => '0',
        );
        $booking = $this->common->findAll('booking', $select = '*', $where, $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
        if (!empty($booking)) {
            foreach ($booking as $key => $value) {
                $bookingId = $value['id'];

                $post = array(
                    'mode' => '8'
                );
                $where = array(
                    'id' => $bookingId
                );
                $this->common->update('booking', $post, $where);
            }
            $response['message'] = "Booking rejected no driver acceptd ";
            $response['status'] = 'true';
        } else {
            $response['message'] = 'No booking find';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function get_cancel_reason() {
        $this->form_validation->set_rules('type', 'Type', 'required|xss_clean');
        $post = $this->input->post();
        if ($this->form_validation->run()) {
            if ($post['type'] == 'user') {
                $table = 'cancel_reason_user';
            } elseif ($post['type'] == 'driver') {
                $table = 'cancel_reason_driver';
            }
            $cancelReasonResult = $this->common->findAll($table, $select = 'id, cancel_reason', $where = array(), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');

            if (!empty($cancelReasonResult)) {
                $cancelReasonArr = array();
                foreach ($cancelReasonResult as $key => $value) {

                    $cancelReasonArr[] = $value;
                }

                $response['message'] = "Cancel reason";
                $response['result'] = $cancelReasonArr;
                $response['status'] = 'true';
            } else {
                $response['message'] = 'No cancel reason';
                $response['status'] = 'false';
            }
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }

        echo json_encode($response);
    }

    public function cancel_booking() {
        $postData = $this->input->post();
        $this->form_validation->set_rules('booking_id', 'Booking Id', 'required|xss_clean');
        $this->form_validation->set_rules('cancel_reason', 'Cancel Reason', 'required|xss_clean');
        $this->form_validation->set_rules('type', 'Type', 'required|xss_clean');
        if ($this->form_validation->run()) {
            $where = array(
                'id' => $postData['booking_id']
            );
            $whereBookingDriver = array(
                'booking_id' => $postData['booking_id']
            );
            if ($postData['type'] == 'user') {
                $post = array(
                    'cancel_reason' => $postData['cancel_reason'],
                    'mode' => '3',
                );
            } elseif ($postData['type'] == 'driver') {
                $post = array(
                    'cancel_reason' => $postData['cancel_reason'],
                    'mode' => '9',
                );
                $postBookingDriver = array(
                    'mode' => '9'
                );
            }
            $this->common->update('booking', $post, $where);
            if ($postData['type'] == 'user') {
                $driverData = $this->common->findAllWithJoin('booking', $select = 'booking.id AS bookingId, driver.id AS driverId, driver.first_name, driver.middle_name, driver.last_name, driver.token, driver.appPlatform', $join = array('driver' => 'driver.id = booking.driver_id'), $where = array('booking.id' => $postData['booking_id']), $orderby = array(), $resultType = 'array', $limit = '');
                $token = $driverData[0]['token'];
                $for = $driverData[0]['appPlatform'];
                $push_message = 'User cancel your booking';
                $push_array = array(
                    "alert" => $push_message,
                    "sound" => 'default',
                    "content-available" => 1,
                    "push_tag" => "user_cancel_booking"
                );
                $this->common->send_driver_push_fcm($token, $push_array, $for, $PEM_MODE = '2');
            } elseif ($postData['type'] == 'driver') {
                $userData = $this->common->findAllWithJoin('booking', $select = 'booking.id AS bookingId, user.id AS userId, user.first_name,  user.last_name, user.token, user.appPlatform', $join = array('user' => 'user.id = booking.user_id'), $where = array('booking.id' => $postData['booking_id']), $orderby = array(), $resultType = 'array', $limit = '');
                $token = $userData[0]['token'];
                $for = $userData[0]['appPlatform'];
                $push_message = 'Driver cancel your booking';
                $push_array = array(
                    "alert" => $push_message,
                    "sound" => 'default',
                    "content-available" => 1,
                    "push_tag" => "driver_cancel_booking"
                );
                $this->common->send_user_push_fcm($token, $push_array, $for, $PEM_MODE = '2');
            }
            $response['message'] = 'Request is cancel';
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function finish_ride() {
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_rules('booking_id', 'Booking Id', 'required|xss_clean');
        $post = $this->input->post();
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $where = array(
                'id' => $post['booking_id']
            );
            $postData = array(
                'mode' => '10',
            );
            $this->common->update('booking', $postData, $where);
            $this->common->update('booking_driver', $postData, $where = array('booking_id' => $post['booking_id'], 'driver_id' => $post['driver_id']));
            $updateDriverAvbl = array(
                'is_available' => '1'
            );
            $this->common->update('driver', $updateDriverAvbl, $where = array('id' => $post['driver_id']));
            $userData = $this->common->find('user', $select = 'id, token, appPlatform', $where = array('id' => $post['user_id']), $resultType = 'array', $orderby = array());
            $token = $userData['token'];
            $for = $userData['appPlatform'];
            $push_message = 'Hurrah! You have successfully completed your trip.';
            $push_array = array(
                "alert" => $push_message,
                "sound" => 'default',
                "content-available" => 1,
                "push_tag" => "finish_ride"
            );
            $this->common->send_user_push_fcm($token, $push_array, $for, $PEM_MODE = '2');
            $response['message'] = 'You have successfully completed your trip';
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function otp_verfication() {
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_rules('booking_id', 'Booking Id', 'required|xss_clean');
        $this->form_validation->set_rules('otp', 'OTP', 'required|xss_clean');
        $post = $this->input->post();
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $bookingOtp = $this->common->find('booking', $select = 'otp', $where = array('id' => $post['booking_id']), $resultType = 'array', $orderby = array());
            if ($bookingOtp['otp'] == $post['otp']) {
                $response['message'] = 'OTP verified';
                $response['status'] = 'true';
            } else {
                $response['message'] = 'Invalid OTP';
                $response['status'] = 'false';
            }
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function get_rating_comment() {
        $arr = array(1,2,3,4,5);
        foreach ($arr as $key => $value) {
            $ratingComment[$value][] = $this->common->findAll('rating_comment_driver', $select = 'id, comment', $where = array('rating'=>$value), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
        }
        if (!empty($ratingComment)) {
            $ratingCommentArr = array();
            $response['message'] = "Rating reason";
            $response['result'] = $ratingComment;
            $response['status'] = 'true';
        } else {
            $response['message'] = 'No reason';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function get_rating_comment_user() {
        //$ratingComment = $this->common->findAll('rating_comment_user', $select = 'id, comment', $where = array(), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');

        $arr = array(1,2,3,4,5);
        foreach ($arr as $key => $value) {
            $ratingComment[$value][] = $this->common->findAll('rating_comment_user', $select = 'id, comment', $where = array('rating'=>$value), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
        }
        if (!empty($ratingComment)) {
            $ratingCommentArr = array();
            $response['message'] = "Rating reason";
            $response['result'] = $ratingComment;
            $response['status'] = 'true';
        } else {
            $response['message'] = 'No reason';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

        //    public function rating() {
        //        $postData = $this->input->post();
        //
        //        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean|');
        //        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean|');
        //        $this->form_validation->set_rules('rate_for', 'Rate for', 'required|xss_clean|');
        //        $this->form_validation->set_rules('rating', 'Rating', 'required|xss_clean|');
        //
        ////        if ($postData['rate_for'] == 'driver') {
        ////            $this->form_validation->set_rules('feedback', 'Feedback', 'required|xss_clean|');
        ////        }
        //        $this->form_validation->set_error_delimiters('', '');
        //
        //        if ($this->form_validation->run()) {
        //
        //            $where = array(
        //                'user_id' => $postData['user_id'],
        //                'driver_id' => $postData['driver_id'],
        //                'rate_for' => $postData['rate_for']
        //            );
        //            $preRating = $this->common->find('rating', '*', $where, 'array');
        //
        //            if (empty($preRating)) {
        //                $post = array(
        //                    'user_id' => $postData['user_id'],
        //                    'driver_id' => $postData['driver_id'],
        //                    'rating' => $postData['rating'],
        //                    'rate_for' => $postData['rate_for'],
        //                    'feedback' => $postData['feedback']
        //                );
        //                $this->common->save('rating', $post);
        //            } else {
        //                $update = array(
        //                    'rating' => $postData['rating'],
        //                    'feedback' => $postData['feedback']
        //                );
        //                $updateWhere = array(
        //                    'user_id' => $postData['user_id'],
        //                    'driver_id' => $postData['driver_id'],
        //                    'rate_for' => $postData['rate_for']
        //                );
        //                $this->common->update('rating', $update, $updateWhere);
        //            }
        //
        //            $response['message'] = 'Rate Successfully';
        //            $response['status'] = 'true';
        //        } else {
        //            $response['message'] = validation_errors();
        //            $response['status'] = 'false';
        //        }
        //        echo json_encode($response);
        //    }

    public function billing_detail() {
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_rules('booking_id', 'Booking Id', 'required|xss_clean');
        $this->form_validation->set_rules('amount', 'Amount Id', 'required|xss_clean');
        $this->form_validation->set_rules('rating', 'Rating', 'required|xss_clean');
        $this->form_validation->set_rules('review', 'Review', 'required|xss_clean');
        $this->form_validation->set_rules('rate_for', 'Rate for', 'required|xss_clean');
        $post = $this->input->post();
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $where = array(
                'user_id' => $post['user_id'],
                'driver_id' => $post['driver_id'],
                'rate_for' => $post['rate_for'],
                'booking_id' => $post['booking_id'],
            );
            $preRating = $this->common->find('rating', '*', $where, 'array');
            if (empty($preRating)) {
                $post = array(
                    'user_id' => $post['user_id'],
                    'driver_id' => $post['driver_id'],
                    'rating' => $post['rating'],
                    'rate_for' => $post['rate_for'],
                    'feedback' => $post['feedback']
                );
                $this->common->save('rating', $post);
            } else {
                $update = array(
                    'rating' => $post['rating'],
                    'feedback' => $post['feedback']
                );
                $updateWhere = array(
                    'user_id' => $post['user_id'],
                    'driver_id' => $post['driver_id'],
                    'rate_for' => $post['rate_for']
                );
                $this->common->update('rating', $update, $updateWhere);
            }

            $response['message'] = 'Rate Successfully';
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    // CREB code
    public function vehicle_list() {
        $vehicleList = $this->common->findAll('vehicle', $select = 'id, name , img', $where = array('status' => 1), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
        $vehicle = array();
        foreach ($vehicleList as $key => $value) {
            $vehicleArr = array(
                'id' => $value['id'],
                'name' => $value['name'],
                'img' => (($value['img'] != '') ? $value['img'] : '')
            );
            $vehicle[] = $vehicleArr;
        }
        $response['result'] = $vehicle;
        $response['status'] = 'true';
        echo json_encode($response);
    }


    // CREB code
    public function notification_list() {
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $postData = $this->input->post();
        $notiList = $this->common->findAll('notification', $select = 'driver_id, booking_id , send_to,notification_msg,added_on', $where = array('user_id' => $postData['user_id']), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
        $notification = array();
        foreach ($notiList as $key => $value) {
            $timestamp =  $value['added_on'];
            $splitTimeStamp = explode(" ",$timestamp);
            $date = $splitTimeStamp[0];
            $time = $splitTimeStamp[1];
            $notiListArr = array(
                'notification_msg' => $value['notification_msg'],
                'notification_date' => $date,
                'notification_time' =>$time ,
                //'name' => $value['name'],
                //'img' => (($value['img'] != '') ? $value['img'] : '')
            );
            $notification[] = $notiListArr;
        }
        $response['result'] = $notification;
        $response['status'] = 'true';
        echo json_encode($response);
    }

    public function booking_list() 
	{
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('type', 'Type', 'required|xss_clean');
        $postData = $this->input->post();
        if($postData['type'] == 'past')
		{
            //$bookingList = $this->common->findAll('booking', $select = '*', $where = array('user_id' => $postData['user_id'],'booking_type!='=>'schedule later'), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
            $bookingList = $this->common->findAllWithJoin('booking', $select = 'booking.*, vehicle_subtype_master.vehicle_model,driver.first_name,driver.last_name, driver.img,driver.licence_plate', $join = array('driver' => 'driver.id = booking.driver_id','vehicle_subtype_master'=>'vehicle_subtype_master.id = booking.vehicle_sub_type_id'), $where = array('user_id' => $postData['user_id'],'booking_type!='=>'schedule later'), $orderby = array(), $resultType = 'array', $limit = '10');
        }else
		{
            //$bookingList = $this->common->findAll('booking', $select = '*', $where = array('user_id' => $postData['user_id'],'booking_type'=>'schedule later'), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
            $bookingList = $this->common->findAllWithJoin('booking', $select = 'booking.*, vehicle_subtype_master.vehicle_model,driver.first_name,driver.last_name, driver.img,driver.licence_plate', $join = array('driver' => 'driver.id = booking.driver_id','vehicle_subtype_master'=>'vehicle_subtype_master.id = booking.vehicle_sub_type_id'), $where = array('user_id' => $postData['user_id'],'booking_type'=>'schedule later'), $orderby = array(), $resultType = 'array', $limit = '10');

        }
        $finalArray =  array();
        //$bookingList['tip'] = '10';
        //$bookingList['carNo'] = 'abc123';
        $userPath = "https://maestrosinfotech.com/arrive/uploads/users/";
        $driverPath="https://maestrosinfotech.com/arrive/uploads/drivers/";
        $defaultPath = "https://maestrosinfotech.com/arrive/uploads/users/UserImage.jpg";
        foreach ($bookingList as $key => $value) 
		{
            $bookList['driverName']=$value['first_name'].' '.$value['last_name'];
            $bookList['booking_id']=$value['booking_id'];
            $bookList['start_point_lat']=$value['start_point_lat'];
            $bookList['start_point_long']=$value['start_point_long'];
            $bookList['end_point_lat']=$value['end_point_lat'];
            $bookList['end_point_long']=$value['end_point_long'];
            $bookList['start_point']=$value['start_point'];
            $bookList['end_point']=$value['end_point'];
            $bookList['amount']=$value['amount'];
            $bookList['schedule_date']=$value['schedule_date'];
            $bookList['schedule_time']=$value['schedule_time'];
            $bookList['status']=$value['status'];
            $bookList['vehicle_model']=$value['vehicle_model'];
            $bookList['tip']='10';
            if ($value['licence_plate']!=NULL) 
			{
                $bookList['carNo']=$value['licence_plate'];
            }
            else
			{
                $bookList['carNo']='';
            }
            $array1 = array('start_point_lat'=>$value['start_point_lat'],'start_point_long'=>$value['start_point_long']);
            $array2 = array('end_point_lat'=>$value['end_point_lat'],'end_point_long'=>$value['end_point_long']);
            $origin = implode(',', $array1);
            $destination = implode(',', $array2);
            
            $url =  $this->common->getStaticGmapURLForDirection($origin,$destination,array(),$size = "500x250"); 
        //print_R($url);echo "<pre>";//die();
            $bookList['userImg'] = (($value['img'] != '') ? $driverPath . $value['img'] : $defaultPath);
            $bookList['duration']=$value['duration'];
            $bookList['distance']=$value['distance'];
            $bookList['url']=$url;
            $finalArray[] = $bookList;

        }//die();
        //print_R($bookingList);die();
        if(!empty($bookingList))
		{
            $response['status'] = 'true';
            $response['message'] = 'booking list';
            $response['result'] = ($finalArray)?$finalArray:'';

        }else
		{
            $response['status'] = 'true';
            $response['message'] = 'No booking found';
            $response['result']  = array();

        }


        echo json_encode($response);
    }
    public function booking_detail() 
    {
        $this->form_validation->set_rules('id', 'Booking id', 'required|xss_clean');
        
        $postData = $this->input->post();
        if($postData['type'] == 'past')
        {
            //$bookingList = $this->common->findAll('booking', $select = '*', $where = array('user_id' => $postData['user_id'],'booking_type!='=>'schedule later'), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
            $bookingList = $this->common->findAllWithJoin('booking', $select = 'booking.*, vehicle_subtype_master.vehicle_model,driver.first_name,driver.last_name, driver.img,driver.licence_plate', $join = array('driver' => 'driver.id = booking.driver_id','vehicle_subtype_master'=>'vehicle_subtype_master.id = booking.vehicle_sub_type_id'), $where = array('booking.booking_id' => $postData['id']), $orderby = array(), $resultType = 'array', $limit = '1');
        }else
        {
            //$bookingList = $this->common->findAll('booking', $select = '*', $where = array('user_id' => $postData['user_id'],'booking_type'=>'schedule later'), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
            $bookingList = $this->common->findAllWithJoin('booking', $select = 'booking.*, vehicle_subtype_master.vehicle_model,driver.first_name,driver.last_name, driver.img,driver.licence_plate', $join = array('driver' => 'driver.id = booking.driver_id','vehicle_subtype_master'=>'vehicle_subtype_master.id = booking.vehicle_sub_type_id'), $where = array('booking.booking_id' => $postData['id']), $orderby = array(), $resultType = 'array', $limit = '1');

        }
        $finalArray =  array();
        //$bookingList['tip'] = '10';
        //$bookingList['carNo'] = 'abc123';
        $userPath = "https://maestrosinfotech.com/arrive/uploads/users/";
        $driverPath="https://maestrosinfotech.com/arrive/uploads/drivers/";
        $defaultPath = "https://maestrosinfotech.com/arrive/uploads/users/UserImage.jpg";
        foreach ($bookingList as $key => $value) 
        {
            $bookList['driverName']=$value['first_name'].' '.$value['last_name'];
            $bookList['booking_id']=$value['booking_id'];
            $bookList['start_point_lat']=$value['start_point_lat'];
            $bookList['start_point_long']=$value['start_point_long'];
            $bookList['end_point_lat']=$value['end_point_lat'];
            $bookList['end_point_long']=$value['end_point_long'];
            $bookList['start_point']=$value['start_point'];
            $bookList['end_point']=$value['end_point'];
            $bookList['amount']=$value['amount'];
            $bookList['schedule_date']=$value['schedule_date'];
            $bookList['schedule_time']=$value['schedule_time'];
            $bookList['status']=$value['status'];
            $bookList['vehicle_model']=$value['vehicle_model'];
            $bookList['tip']='10';
            if ($value['licence_plate']!=NULL) 
            {
                $bookList['carNo']=$value['licence_plate'];
            }
            else
            {
                $bookList['carNo']='';
            }
            $array1 = array('start_point_lat'=>$value['start_point_lat'],'start_point_long'=>$value['start_point_long']);
            $array2 = array('end_point_lat'=>$value['end_point_lat'],'end_point_long'=>$value['end_point_long']);
            $origin = implode(',', $array1);
            $destination = implode(',', $array2);
            
            $url =  $this->common->getStaticGmapURLForDirection($origin,$destination,array(),$size = "500x250"); 
        //print_R($url);echo "<pre>";//die();
            $bookList['userImg'] = (($value['img'] != '') ? $driverPath . $value['img'] : $defaultPath);
            $bookList['duration']=$value['duration'];
            $bookList['distance']=$value['distance'];
            $bookList['url']=$url;
            $finalArray[] = $bookList;

        }//die();
        //print_R($bookingList);die();
        if(!empty($bookingList))
        {
            $response['status'] = 'true';
            $response['message'] = 'booking list';
            $response['result'] = ($finalArray)?$finalArray:'';

        }else
        {
            $response['status'] = 'true';
            $response['message'] = 'No booking found';
            $response['result']  = array();

        }


        echo json_encode($response);
    }
    public function user_last_trip() {
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $postData = $this->input->post();
        $bookingList = $this->common->findAll('booking', $select = '*', $where = array('user_id' => $postData['user_id'],'booking_type'=>''), $orderby = array('id'=>'desc'), $resultType = 'array', $limit = '1', $groupby = '');
        //print_r($bookingList);die();
        
        //print_r($bookingList);die();

        if(!empty($bookingList)){
            $vehicle_sub_type_id =  $bookingList[0]['vehicle_sub_type_id'];
            $vehicle_sub_type_id_data = $this->common->find('vehicle_subtype_master', $select = '*', $where = array('id' => $vehicle_sub_type_id), $resultType = 'array', $orderby = array());
            $driverData = $this->common->find('driver', $select = '*', $where = array('id' => $bookingList[0]['driver_id']), $resultType = 'array', $orderby = array());
            $bookingList[0]['vehicle_sub_type_name'] = $vehicle_sub_type_id_data['vehicle_model'];
            if ($driverData['licence_plate']!=NULL) {
                $bookingList[0]['carNo.'] = $driverData['licence_plate'];
            }
            else{
                $bookingList[0]['carNo.'] = '';
            }
            $response['result'] = $bookingList;
            $response['status'] = 'true';
            $response['message'] = 'last trip';
        }else{
            $response['status'] = 'true';
            $response['message'] = 'No booking found';
            $response['result'] = array();

        }


        echo json_encode($response);
    }

    public function rate() {
        $postData = $this->input->post();
        $this->form_validation->set_rules('driver_id', 'driver Id', 'required|xss_clean');
        $this->form_validation->set_rules('booking_id', 'Booking id', 'required|xss_clean');
        $this->form_validation->set_rules('rate', 'rate', 'required|xss_clean');
        $this->form_validation->set_rules('comment', 'comment', 'xss_clean');
        $this->form_validation->set_rules('review', 'review', 'xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            if(empty($postData['review'])){
                $response['message'] = 'please select any one reason.';
                $response['status'] = 'false';
                echo json_encode($response);
                return ;
                
            }

            $post = array(
                'booking_id' => $postData['booking_id'],
                'driver_id' => $postData['driver_id'],
                'rating' => $postData['rate'],   
                'comment' => $postData['comment'],
                'review' => $postData['review']                
            );

            $rateId = $this->common->save('review_rating', $post);
            
            $where = array(
                'id' => $postData['booking_id']
            );
            
            $getbook =  $this->common->find('booking','*',array('id'=>$postData['booking_id']));
            
            if($getbook['mode'] == '11' || $getbook['mode'] == '12'){
                $postData = array(
                'mode' => '4',
                );
                $this->common->update('booking', $postData, $where);
            }else{
                $postData = array(
                'mode' => '12',
                );
                $this->common->update('booking', $postData, $where);
                
            }

            if (!empty($rateId)) {
                $response['message'] = 'Driver rate sucessfully';
                $response['result'] = $rateId;
                $response['status'] = 'true';
            } 
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }

        echo json_encode($response);
    }


    public function rate_user() {
        $postData = $this->input->post();
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('booking_id', 'Booking id', 'required|xss_clean');
        $this->form_validation->set_rules('rate', 'rate', 'required|xss_clean');
        $this->form_validation->set_rules('comment', 'comment', 'xss_clean');
        $this->form_validation->set_rules('review', 'review', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {

            $post = array(
                'booking_id' => $postData['booking_id'],
                'user_id' => $postData['user_id'],
                'rating' => $postData['rate'],
                'comment' => $postData['comment'],
                'review' => $postData['review']                
            );

            $rateId = $this->common->save('review_rating_user', $post);
            
            $where = array(
                'id' => $postData['booking_id']
            );
            $getbook =  $this->common->find('booking','*',array('id'=>$postData['booking_id']));
            
            if($getbook['mode'] == '11' || $getbook['mode'] == '12'){
                $postData = array(
                'mode' => '4',
                );
                $this->common->update('booking', $postData, $where);
            }else{
                $postData = array(
                'mode' => '11',
                );
                $this->common->update('booking', $postData, $where);
                
            }

            if (!empty($rateId)) {
                $response['message'] = 'User rate sucessfully';
                $response['result'] = $rateId;
                $response['status'] = 'true';
            } 
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }

        echo json_encode($response);
    }



    public function driver_list() {
        $postData = $this->input->post();
        $startPointLat = $postData['start_point_lat'];
        $startPointLong = $postData['start_point_long'];
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('start_point_lat', 'Start point lat', 'required|xss_clean');
        $this->form_validation->set_rules('start_point_long', 'Start point long', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $drivers = $this->common->get_driver_in_range($postData['start_point_lat'], $postData['start_point_long'], $postData['user_id']);
            if (!empty($drivers)) {
                $response['message'] = 'Driver list:';
                $response['result'] = $drivers;
                $response['status'] = 'true';
            } else {
                $response['message'] = 'No driver found';
                $response['status'] = 'false';
            }
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }

        echo json_encode($response);
    }

    public function track_route() {
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_rules('booking_id', 'Booking Id', 'required|xss_clean');
        $this->form_validation->set_rules('driver_current_lat', 'Driver current Latitude', 'required|xss_clean');
        $this->form_validation->set_rules('driver_current_long', 'Driver current Longitude', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $post = $this->input->post();
            $driverCurrentLat = $post['driver_current_lat'];
            $driverCurrentLong = $post['driver_current_long'];
            $postArr = array(
                'latitude' => $driverCurrentLat,
                'longitude' => $driverCurrentLong
            );
            $update = $this->common->update('user', $postArr, $where = array('id' => $post['driver_id']));

        //            $userEndLatLong = $this->common->find('booking', $select = 'end_point_lat, end_point_long', $where = array('id' => $post['booking_id']), $resultType = 'array', $orderby = array());
        //
        //$userEndLat = $userEndLatLong['end_point_lat'];
        //$userEndLong = $userEndLatLong['end_point_long'];
        //
        //            $distanceDuration = $this->common->get_driving_distance($driverCurrentLat, $userEndLat, $driverCurrentLong, $userEndLong);

            $result = array(
                'driverCurrentLat' => $post['driver_current_lat'],
                'driverCurrentLong' => $post['driver_current_long']
            );

            $response['result'] = $result;
            $response['message'] = 'Driver lat long updated successfully';
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function booking_requested_list() {
        $postData = $this->input->post();
        $driverId = $postData['driver_id'];
        $userPath = base_url() . 'assets/upload/avatar/';
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $bookingData = $this->common->booking_requested_list($driverId);
            if (!empty($bookingData)) {
                $userImg = $this->common->find('avatar', $select = 'img as userImg', $where = array('id' => $bookingData[0]['avatar_img_id']), $resultType = 'array', $orderby = array());

                $finalArray = array(
                    'bookingId' => $bookingData[0]['bookingId'],
                    'bookingCode' => $bookingData[0]['bookingCode'],
                    'user_id' => $bookingData[0]['user_id'],
                    'userName' => $bookingData[0]['name'],
                    'userImg' => (($userImg['userImg'] != '') ? $userPath . $userImg['userImg'] : ''),
                    'mode' => $bookingData[0]['mode'],
                    'start_point' => $bookingData[0]['start_point'],
                    'end_point' => $bookingData[0]['end_point'],
                    'no_passanger' => $bookingData[0]['no_passanger'],
                    'no_luggage' => $bookingData[0]['no_luggage'],
                    'duration' => $bookingData[0]['duration'],
                    'distance' => $bookingData[0]['distance']
                );
                $response['message'] = "Booking requested list:";
                $response['result'] = $finalArray;
                $response['status'] = 'true';
            } else {
                $response['message'] = 'No booking request';
                $response['status'] = 'false';
            }
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function user_chat_request_list() {
        $postData = $this->input->post();
        $userId = $postData['user_id'];
        $userPath = base_url() . 'assets/upload/avatar/';
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $chatData = $this->common->user_chat_request_list($userId);
//            $result = array();
//            if (!empty($chatData)) {
//
//
//                foreach ($chatData as $key => $value) {
            $userImg = $this->common->find('avatar', $select = 'img as userImg', $where = array('id' => $chatData[0]['avatar_img_id']), $resultType = 'array', $orderby = array());
            $finalArray = array(
                'bookingId' => $chatData[0]['bookingId'],
                'bookingCode' => $chatData[0]['bookingCode'],
                'driver_id' => $chatData[0]['driver_id'],
                'driverName' => $chatData[0]['name'],
                'driverImg' => (($userImg['userImg'] != '') ? $userPath . $userImg['userImg'] : ''),
                'mode' => $chatData[0]['mode'],
                'start_point' => $chatData[0]['start_point'],
                'end_point' => $chatData[0]['end_point'],
                'no_passanger' => $chatData[0]['no_passanger'],
                'no_luggage' => $chatData[0]['no_luggage'],
                'duration' => $chatData[0]['duration'],
                'distance' => $chatData[0]['distance']
            );
//                    $result[] = $finalArray;
//                }
            $response['message'] = "Chat request list:";
            $response['result'] = $finalArray;
            $response['status'] = 'true';
//        } else {
//            $response['message'] = 'No chat request';
//            $response['status'] = 'false';
//        }
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function final_accept_reject() {
        $post = $this->input->post();
        $this->form_validation->set_rules('action_by', 'Action by', 'required|xss_clean');
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_rules('booking_id', 'Booking Id', 'required|xss_clean');
        $this->form_validation->set_rules('mode', 'Mode', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            if ($post['mode'] == 'accept') {
                $updatefinal = array(
                    'mode' => '7',
                    'driver_id' => $post['driver_id']
                );
                $updatefinalAccept = array(
                    'mode' => '7'
                );
                $this->common->update('booking', $updatefinal, $where = array('id' => $post['booking_id']));
                $this->common->update('booking_driver', $updatefinalAccept, $where = array('booking_id' => $post['booking_id'], 'driver_id' => $post['driver_id']));

                $updateDriverAvbl = array(
                    'is_driver_available' => '0'
                );

                $this->common->update('user', $updateDriverAvbl, $where = array('id' => $post['driver_id']));
                $allHoldDriver = $this->common->findAll('booking_driver', $select = 'driver_id', $where = array('booking_id' => $post['booking_id'], 'driver_id !=' => $post['driver_id']), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
                foreach ($allHoldDriver as $key => $value) {
                    $holdDriverId = $value['driver_id'];
                    $postDriverHold = array(
                        'is_driver_available' => '1'
                    );
                    $this->common->update('user', $postDriverHold, $where = array('id' => $holdDriverId));
                }
                if ($post['action_by'] == 'user') {
                    $driverData = $this->common->find('user', $select = 'token, appPlatform', $where = array('id' => $post['driver_id']), $resultType = 'array', $orderby = array());

                    $token = $driverData['token'];
                    $for = $driverData['appPlatform'];
                    $push_message = 'User accept booking';
                    $push_array = array(
                        "alert" => $push_message,
                        "sound" => 'default',
                        "content-available" => 1,
                        "push_tag" => "final_accept"
                    );

                    $this->common->send_user_push_fcm($token, $push_array, $for, $PEM_MODE = '2');
                } elseif ($post['action_by'] == 'driver') {
                    $userData = $this->common->find('user', $select = 'token, appPlatform', $where = array('id' => $post['user_id']), $resultType = 'array', $orderby = array());

                    $token = $userData['token'];
                    $for = $userData['appPlatform'];


                    $push_message = 'Driver accept booking';

                    $push_array = array(
                        "alert" => $push_message,
                        "sound" => 'default',
                        "content-available" => 1,
                        "push_tag" => "final_accept"
                    );

                    $this->common->send_user_push_fcm($token, $push_array, $for, $PEM_MODE = '2');
                }

                $response['message'] = 'Accept your request';
                $response['status'] = 'true';
            } elseif ($post['mode'] == 'reject') {
                $updatefinalReject = array(
                    'mode' => '8'
                );
                $this->common->update('booking', $updatefinalReject, $where = array('id' => $post['booking_id']));
                $this->common->update('booking_driver', $updatefinalReject, $where = array('booking_id' => $post['booking_id'], 'driver_id' => $post['driver_id']));


                $updateDriverAvbl = array(
                    'is_driver_available' => '1'
                );

                $this->common->update('user', $updateDriverAvbl, $where = array('id' => $post['driver_id']));

                $allHoldDriver = $this->common->findAll('booking_driver', $select = 'driver_id', $where = array('booking_id' => $post['booking_id'], 'driver_id !=' => $post['driver_id']), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');


                foreach ($allHoldDriver as $key => $value) {
                    $holdDriverId = $value['driver_id'];
                    $postDriverHold = array(
                        'is_driver_available' => '1'
                    );

                    $holdedDriver = $this->common->update('user', $postDriverHold, $where = array('id' => $holdDriverId));
                }
                if ($post['action_by'] == 'user') {
                    $driverData = $this->common->find('user', $select = 'token, appPlatform', $where = array('id' => $post['driver_id']), $resultType = 'array', $orderby = array());

                    $token = $driverData['token'];
                    $for = $driverData['appPlatform'];


                    $push_message = 'User reject booking';

                    $push_array = array(
                        "alert" => $push_message,
                        "sound" => 'default',
                        "content-available" => 1,
                        "push_tag" => "final_reject"
                    );

                    $this->common->send_user_push_fcm($token, $push_array, $for, $PEM_MODE = '2');
                } elseif ($post['action_by'] == 'driver') {
                    $userData = $this->common->find('user', $select = 'token, appPlatform', $where = array('id' => $post['user_id']), $resultType = 'array', $orderby = array());

                    $token = $userData['token'];
                    $for = $userData['appPlatform'];


                    $push_message = 'Driver reject booking';

                    $push_array = array(
                        "alert" => $push_message,
                        "sound" => 'default',
                        "content-available" => 1,
                        "push_tag" => "final_reject"
                    );

                    $this->common->send_user_push_fcm($token, $push_array, $for, $PEM_MODE = '2');
                }
                $response['message'] = 'Reject your request';
                $response['status'] = 'false';
            }
        } else {

            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function start_ride() {
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_rules('booking_id', 'Booking Id', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        $post = $this->input->post();
        $userId = $post['user_id'];
        $driverId = $post['driver_id'];
        $bookingId = $post['booking_id'];
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $where = array(
                'id' => $post['booking_id']
            );
            $post = array(
                'mode' => '10',
            );

            $this->common->update('booking', $post, $where);
            $this->common->update('booking_driver', $post, $where = array('booking_id' => $bookingId, 'driver_id' => $driverId));


            $userData = $this->common->find('user', $select = 'id, token, appPlatform', $where = array('id' => $userId), $resultType = 'array', $orderby = array());
            $token = $userData['token'];
            $for = $userData['appPlatform'];
            $push_message = 'Your ride has been started';

            $push_array = array(
                "alert" => $push_message,
                "sound" => 'default',
                "content-available" => 1,
                "push_tag" => "start_ride"
            );


            $this->common->send_user_push_fcm($token, $push_array, $for, $PEM_MODE = '2');



            $response['message'] = 'Driver start ride';
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function schedule_booking() 
	{
        $postData = $this->input->post();

        $startPointLat = $postData['start_point_lat'];
        $startPointLong = $postData['start_point_long'];
        $endPointLat = $postData['end_point_lat'];
        $endPointLong = $postData['end_point_long'];

        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('start_point', 'Start Point', 'required|xss_clean');
        $this->form_validation->set_rules('end_point', 'End point', 'required|xss_clean');
        $this->form_validation->set_rules('start_point_lat', 'Start point lat', 'required|xss_clean');
        $this->form_validation->set_rules('start_point_long', 'Start point long', 'required|xss_clean');
        $this->form_validation->set_rules('end_point_lat', 'End point lat', 'required|xss_clean');
        $this->form_validation->set_rules('end_point_long', 'End point long', 'required|xss_clean');
        $this->form_validation->set_rules('schedule_date', 'Schedule date', 'required|xss_clean');
        $this->form_validation->set_rules('schedule_time', 'Schedule time', 'required|xss_clean');
        $this->form_validation->set_rules('no_passanger', 'Number of passanger', 'required|xss_clean');
        $this->form_validation->set_rules('no_luggage', 'Number of luggage', 'required|xss_clean');
        $this->form_validation->set_rules('vehicle_id', 'vehicle Id', 'required|xss_clean');

        if ($this->form_validation->run()) 
		{


            $bookingRandId = $this->common->generateJobCode();

            $distanceDuration = $this->common->get_driving_distance($startPointLat, $endPointLat, $startPointLong, $endPointLong);

            $post = array(
                'booking_id' => $bookingRandId,
                'user_id' => $postData['user_id'],
                'start_point' => $postData['start_point'],
                'end_point' => $postData['end_point'],
                'start_point_lat' => $postData['start_point_lat'],
                'start_point_long' => $postData['start_point_long'],
                'end_point_lat' => $postData['end_point_lat'],
                'end_point_long' => $postData['end_point_long'],
                'no_passanger' => $postData['no_passanger'],
                'no_luggage' => $postData['no_luggage'],
                'vehicle_id' => $postData['vehicle_id'],
                'duration' => $distanceDuration['time'],
                'distance' => $distanceDuration['distance'],
                'schedule_date' => $postData['schedule_date'],
                'schedule_time' => $postData['schedule_time'],
                'mode' => '12',
            );

            $bookingId = $this->common->save('booking', $post);

            $response['bookingId'] = $bookingId;
            $response['message'] = 'Your booking is schedule for future.';
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

//Cron for schedule booking
    public function upcoming_booking() {
        $schedule_booking = $this->common->upcomig_booking();


        if (!empty($schedule_booking)) {

            foreach ($schedule_booking as $key => $value) {

                $bookingId = $value['id'];
                $bookingCode = $value['booking_id'];
                $userId = $value['user_id'];
                $startPointLat = $value['start_point_lat'];
                $startPointLong = $value['start_point_long'];


                $userData = $this->common->find('user', $select = 'id, name, token, appPlatform', $where = array('id' => $userId), $resultType = 'array', $orderby = array());

                $userToken = $userData['token'];
                $userAppPlatform = $userData['appPlatform'];

                $drivers = $this->common->get_driver_in_range($startPointLat, $startPointLong);


                if (!empty($drivers)) {
                    foreach ($drivers as $key => $value) {

                        $driverId = $value['id'];
                        $driverData = $this->common->find('user', $select = 'appPlatform, token', $where = array('id' => $driverId), $resultType = 'array', $orderby = array());


                        $token = $driverData['token'];
                        $for = $driverData['appPlatform'];


                        $push_message = 'You have a booking';

                        $push_array = array(
                            "alert" => $push_message,
                            "userId" => $userData['id'],
                            "bookingId" => $bookingId,
                            "userName" => $userData['name'],
                            "sound" => 'default',
                            "content-available" => 1,
                            "push_tag" => "booking"
                        );

                        $this->common->send_user_push_fcm($token, $push_array, $for, $PEM_MODE = '2');
                    }


                    $response['message'] = 'Push notification send to all available driver';
                    $response['status'] = 'true';
                } else {
                    $push_message = 'No driver available';

                    $push_array = array(
                        "alert" => $push_message,
                        "sound" => 'default',
                        "content-available" => 1,
                        "push_tag" => "no_driver"
                    );

                    $this->common->send_user_push_fcm($userToken, $push_array, $userAppPlatform, $PEM_MODE = '2');


                    $response['message'] = 'No driver available';
                    $response['status'] = 'false';
                }
            }
        } else {
            $response['message'] = 'No schedule booking';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function your_rides() {
        $this->form_validation->set_rules('id', 'Id', 'required|xss_clean');
        $this->form_validation->set_rules('type', 'Type', 'required|xss_clean');
        $post = $this->input->post();
        $this->form_validation->set_error_delimiters('', '');

        if ($this->form_validation->run()) {
            $currentDate = date("Y-m-d");

            $userRides = $this->common->user_ride($currentDate, $post['id'], $post['type']);

            $response['result'] = $userRides;
            $response['status'] = 'true';
        } else {
            $response['message'] = 'No schedule booking';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function allPromo(){
        $post = $this->input->post();

        if(isset($post['user_id'])  ){
            $user_id =$post['user_id'];
            $date=date('Y-m-d');
            $where="valid_from <= '$date' and valid_to >= '$date' and status ='1' and find_in_set($user_id,user_id)";
            $promo = $this->common->fetchAll('promo_name,promo_code,discount,valid_from,valid_to','promo',$where);     
        }else{
            $where=[
                'valid_from <='  =>  date('Y-m-d'),
                'valid_to >='    =>  date('Y-m-d'),
                'status'         =>  '1',        
            ];
            $promo = $this->common->findAll('promo','promo_name,promo_code,valid_from,valid_to,discount',$where);        
        }
        echo json_encode(['result'=>$promo, 'status' =>'true']);
    }
    public function checkPromo(){
        $this->form_validation->set_rules('id', 'Id', 'required|xss_clean');
        $this->form_validation->set_rules('promo', 'Promo', 'required|xss_clean');
        $post = $this->input->post();
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $currentDate = date("Y-m-d");
            $user_id=$post['id'];
            $date=date('Y-m-d');

            $where="valid_from <= '$date' and valid_to >= '$date' and status ='1' and promo_code='$post[promo]' and find_in_set($user_id,user_id)";
            $promo = $this->common->fetchAll('promo_name,promo_code,discount,valid_from,valid_to','promo',$where);

            if(!empty($promo)){
                $response['result'] = $promo;
                $response['message'] ="Promo code apply successfully";
                $response['status'] = 'true';
            }else{
                $where="status ='1' and promo_code='$post[promo]' and find_in_set($user_id,user_id)";
                $check_msg = $this->common->fetchSingle('valid_from,valid_to','promo',$where);
                //if(!empty($check_msg)){
                if(($check_msg['valid_to'])<$date){
                    $response['message'] ="Promo code expired";
                    $response['status'] = 'false';
                }else{
                    $response['message'] ="Invalid Promocode";
                    $response['status'] = 'false';
                }
                //}else{
                //  $response['message'] ="Invalid Promocod";
                  //  $response['status'] = 'false';
                //}
            }

        } else {
            $response['message'] =  validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }
    public function reviewList(){
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        $postData = $this->input->post();
        if ($this->form_validation->run()) {

            $select = 'rt.*,d.first_name as dname, d.email as demail,b.start_point,b.end_point,d.img';
            $join = array(
                'driver d'  =>  'd.id = rt.driver_id',
                'booking b' =>  'b.id=rt.booking_id'
            );
            $where = array(
                'b.user_id' => $postData['user_id'],
                'b.mode '   =>'4'
            );

            $raview_list = $this->common->findAllWithJoin('review_rating rt', $select, $join, $where, $orderby = array(), $resultType = 'array', $limit = '');

            $data=[];
            $i = 0;
            foreach ($raview_list as $key => $value) {
                $data[$i]['id']=$value['id'];
                $data[$i]['driver_id']=$value['driver_id'];
                $data[$i]['booking_id']=$value['booking_id'];
                $data[$i]['rating']=$value['rating'];
                $data[$i]['comment']=$value['comment'];
                $data[$i]['added_on']=$value['added_on'];
                $data[$i]['dname']=$value['dname'];
                $data[$i]['demail']=$value['demail'];
                $data[$i]['start_point']=$value['start_point'];
                $data[$i]['end_point']=$value['end_point'];
                $rating_comment_driver_id = $value['review'];
                if($value['img']==''){
                    $data[$i]['user_img']=base_url()."/uploads/users/UserImage.jpg";
                }else{                       
                    $data[$i]['user_img'] =base_url()."/uploads/users/".$value['img']; 
                }
                $comment_data = $this->common->fetchAll('comment','rating_comment_driver',"id in($rating_comment_driver_id)");
                for($y=0;$y<sizeof($comment_data); $y++){
                    $data[$i]['comment_list'][$y]=$comment_data[$y]->comment;
                }
                $i++;
            }

            $response['message'] =  $data;
            $response['status'] = 'true';
        }else{
            $response['message'] =  validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }


    public function driverreviewList()
	{
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        $postData = $this->input->post();
        if ($this->form_validation->run()) 
		{

            $select = 'rt.*,u.first_name as dname, u.email as demail,b.start_point,b.end_point,u.img';
            $join = array(
                'user u'  =>  'u.id = rt.user_id',
                'booking b' =>  'b.id=rt.booking_id'
            );
            $where = array(
                'b.driver_id' => $postData['driver_id'],
                'b.mode '   =>'4'
            );

            $raview_list = $this->common->findAllWithJoin('review_rating_user rt', $select, $join, $where, $orderby = array(), $resultType = 'array', $limit = '');
            //echo $this->db->last_query();die();

            $data=[];
            $i = 0;
            foreach ($raview_list as $key => $value) 
			{
                $data[$i]['id']=$value['id'];
                $data[$i]['user_id']=$value['user_id'];
                $data[$i]['booking_id']=$value['booking_id'];
                $data[$i]['rating']=$value['rating'];
                $data[$i]['comment']=$value['comment'];
                $data[$i]['added_on']=$value['added_on'];
                $data[$i]['dname']=$value['dname'];
                $data[$i]['demail']=$value['demail'];
                $data[$i]['start_point']=$value['start_point'];
                $data[$i]['end_point']=$value['end_point'];
                $rating_comment_driver_id = $value['user_id'];
                if($value['img']=='')
				{
                    $data[$i]['user_img']=base_url()."/uploads/users/UserImage.jpg";
                }else
				{                       
                    $data[$i]['user_img'] =base_url()."/uploads/users/".$value['img']; 
                }
                $comment_data = $this->common->fetchAll('comment','rating_comment_user',"id in(".$postData['driver_id'].")");
                //echo $this->db->last_query();
                for($y=0;$y<sizeof($comment_data); $y++){
                    $data[$i]['comment_list'][$y]=$comment_data[$y]->comment;
                }
                $i++;
            }

            $response['message'] =  $data;
            $response['status'] = 'true';
        }else{
            $response['message'] =  validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }


    public function ourServices(){
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_error_delimiters("" , "");
        $postData = $this->input->post();
        if ($this->form_validation->run()) {
            $driverReport=$this->common->findAll("booking", $select = 'user_id,id,start_point,end_point,start_point_lat,start_point_long,end_point_lat,end_point_long,schedule_date,schedule_time,mode', $where = array("driver_id"=> $postData['driver_id']));
            $mode =[
                "sent", 
                "accept",
                "reject",
                "cancel by user", 
                "finish",
                "scheduleLater",
                "start ride", 
                "arrived",
                "booking automatic rejected",
                "cancel by driver"
            ];

            if(!empty($driverReport)){
                $i=0;
                foreach ($driverReport as $key => $value) {
                    $driverData[$i]['user_id'] =$value['user_id'];
                    $driverData[$i]['booking_id'] =$value['id'];
                    $driverData[$i]['start_point'] =$value['start_point'];
                    $driverData[$i]['end_point'] =$value['end_point'];
                    $driverData[$i]['start_point_lat'] =$value['start_point_lat'];
                    $driverData[$i]['start_point_long'] =$value['start_point_long'];
                    $driverData[$i]['end_point_lat'] =$value['end_point_lat'];
                    $driverData[$i]['end_point_long'] =$value['end_point_long'];
                    $driverData[$i]['schedule_date'] =$value['schedule_date'];
                    $driverData[$i]['schedule_time'] =$value['schedule_time'];
                    $driverData[$i]['mode'] =$value['mode'];
                    $driverData[$i]['mode_text'] =$mode[$value['mode']];
                    $user_id = $value['user_id'];
                    $userData = $this->common->find('user', $select = 'id, first_name, last_name, img', $where = array('id' => $user_id), $resultType = 'array', $orderby = array());
                    $driverData[$i]['user_name'] =$userData['first_name'].' '.$userData['last_name'];
                    if($userData['img']==''){
                        $driverData[$i]['user_img']=base_url()."/uploads/users/UserImage.jpg";
                    }else{                       
                        $driverData[$i]['user_img'] =base_url()."/uploads/users/".$userData['img']; 
                    }

                    $i++;
                }
                $response['result'] = $driverData;
                $response['message'] ="Driver booking records";
                $response['status'] = 'true';
            }else{
                $response['result'] = [];
                $response['message'] ="No Recod Found" ;
                $response['status'] = 'true';
            }

        } else {
            $response['message'] =  validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }


            public function getVehcletollpricedetails() 
            {
				$postData = $this->input->post();
				$startPointLat = $postData['start_point_lat'];
				$startPointLong = $postData['start_point_long'];
				$endPointLat = $postData['end_point_lat'];
				$endPointLong = $postData['end_point_long'];
				$this->form_validation->set_rules('start_point_lat', 'Start point lat', 'required|xss_clean');
				$this->form_validation->set_rules('start_point_long', 'Start point long', 'required|xss_clean');
				$this->form_validation->set_rules('end_point_lat', 'End point lat', 'required|xss_clean');
				$this->form_validation->set_rules('end_point_long', 'End point long', 'required|xss_clean');

            	$this->form_validation->set_error_delimiters('', '');
                if ($this->form_validation->run()) 
                {
					$where = array(
                    'active_flag' => 'yes',
                    );
                    $getdetails = $this->common->findAll('vehicle_type_master', $select = '*', $where, $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');

                    $searchdriverres = $this->common->driver_search($startPointLat,$startPointLong);
                    if(!empty($searchdriverres))
					{
						$driverlat = $searchdriverres[0]['latitude'];
						$driverlng = $searchdriverres[0]['longitude'];
						$driverdistance = $searchdriverres[0]['distance'];

                	}
		
					$distanceDurationnnn = $this->common->get_driving_distance($startPointLat, $driverlat, $startPointLong, $driverlng);
		
					$finaltime = $distanceDurationnnn['time'];
					$details = array();
            foreach ($getdetails as $key => $value) 
            {
                //print_r($value);
                $get_id='';
                $vehicle_type_val='';
                if(!empty($value['id']))
                {
                    $get_id=$value['id'];

                }
                if(!empty($value['vehicle_type']))
                {
                    $vehicle_type_val=$value['vehicle_type'];

                }
                //print_r($get_id);
                //print_r($vehicle_type_val);
                    $typeid = $value['id'];
                    $con = array(
                        'active_flag' => 'yes',
                        'vehicle_type_id' => $typeid

                    );
                    //start
                     $url ='https://api.coord.co/v1/search/tolling/gps_trace?access_key=Hy9gOVGTCxvcFRUznPodo_UaPDTj3y3ph7UBR_Unj4w';
                    $ch = curl_init($url);


                    $data['locations'][0]=['lat'=>(double)$startPointLat,'lng'=>(double)$startPointLong,'timestamp'=>''];
                    $data['locations'][1]=['lat'=>(double)$endPointLat,'lng'=>(double)$endPointLong,'timestamp'=>''];
                    $data['vehicle']=['axles'=>(int)$typeid];
                    $payload =json_encode($data);

                    curl_setopt($ch, CURLOPT_POSTFIELDS, $payload);
                    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type:application/json'));
                    // curl_setopt($ch, CURLOPT_HTTPHEADER, array('Accept:application/json'));
                    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
                    $result = curl_exec($ch);
                    $getdataval_toll =json_decode($result);

                      

                    //echo $toll_amount;

                    //finish
                    $toll_amount='';
                    $getvechileres = $this->common->findAll('vehicle_subtype_master', $select = '*', $con, $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
                    if(!empty($getvechileres))
                    {
                        $vechiledetal=array();
                        foreach ($getvechileres as $keyyy => $valueee) 
                        {
                            	//print_r($valueee);
                                $amt =   $valueee['base_price'];
                                $finalamt = str_replace('$','', $amt);
                                $distanceDuration = $this->common->get_driving_distance($startPointLat, $endPointLat, $startPointLong, $endPointLong);

                                $valueee['fare_amt'] = round($distanceDuration['distance'] * $finalamt);
                                if(!empty($getdataval_toll))
                                {
									foreach($getdataval_toll as $vals)
									{
										$prices_val=$vals->prices;
										
										foreach ($prices_val as $value) 
										{
	
											$toll_amount=$value->price->amount;
											
	
										
										}
									}
                             	}
                                
                                $valueee['toll_amount'] = $toll_amount;
                                $valueee['total_toll_amount'] = $toll_amount+ $valueee['fare_amt'];
                                
                                $valueee['drivertaketimetoreach'] = ($finaltime)?$finaltime:"";
                                $valueee['driverdistancefronuser'] = ($distanceDurationnnn['distance'])?$distanceDurationnnn['distance']:'';
                                $valueee['selectimg'] = base_url() . '/uploads/select.png';
                                $valueee['nonselectimg'] = base_url()  . '/uploads/unselect.png';
                               // $vechiledetal[] = $valueee;
                                array_push($vechiledetal, $valueee);
                        }

                    }else
					
					{
						 $vechiledetal=array();
					}

                    // $newData[]=[
                    // 	'vehicleTypeId'=>$value['id'],
                    // 	'vehicleTypeName'=>$value['vehicle_type'],
                    // 	'vehicleType'=>$vechiledetal,
                    // ]
                    //     // $getvehicledetailsArr = array(
                    //     //     'vehicleTypeId' => $value['id'],
                    //     //     'vehicleTypeName' => $value['vehicle_type'],
                    //     //     'vehicleType' => $vechiledetal
                    //     // );
                    //     print_r($newData);
                    //     echo 'ss';exit;



                
                 $data_vals=array();
                 $data_vals=array(
                    'vehicleTypeId'=>$get_id,
                    'vehicleTypeName'=>$vehicle_type_val,
                    'vehicleType' => $vechiledetal,


                 );
                $details[] = $data_vals;
                $vechiledetal = '';
                    
            }
            //print_r($details);exit;
            $response['message'] = "Details fetch Successfully.";
            $response['result'] = $details;
            $response['status'] = 'true';

            }
             else 
             {

            $response['message'] = validation_errors();
            $response['status'] = 'false';
            }

            echo json_encode($response);
            }



 public function getSchedulePickupList($driver_id=''){
        
        $postData = $this->input->post();
        $driver_id=$postData['driver_id'];
        $type_pick_up=$postData['type_pick_up'];
        //die;
        if(!empty($driver_id))
        {
           $driver_id=$driver_id;
        }
        else
        {
            $driver_id='';

        }
        if(!empty($type_pick_up))
        {
           $type_pick_up=$type_pick_up;
        }
        else
        {
            $type_pick_up='';

        }
        if($type_pick_up==1)
        {
        $pick_up_data=$this->common->get_availablepick_data($driver_id);
        }
        if($type_pick_up==2)
        {
        $pick_up_data=$this->common->get_mypickup_data($driver_id);
        }
        
        if(!empty($pick_up_data))
        {

            $response['scheduled_pickups'] =  $pick_up_data;
            $response['msg']='Scheduled Pickups List';
            $response['status'] = 'true';

        }else{
            $response['scheduled_pickups'] = [] ;
            $response['msg']='No Data Found';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }


    public function shareride()
    {
        $id=$this->uri->segment('4');
     
        //$id=$_REQUEST['id'];
        //print_r($id);
        if(!empty($id))
        {
            $data['share_data'] = $this->common->find('booking', '*', $where = array('id' => $id), $resultType = 'array', $orderby = array());
             $lat=$share_data['start_point_lat'];
             $long=$share_data['start_point_long'];
             $this->load->view('share_ride',$data);
  
        }

    }

      public function update_details()
    {
       $id=$_REQUEST['id'];
        $l_lat=$_REQUEST['l_lat'];
        $l_long=$_REQUEST['l_long'];
        $lat_lng=array(
            'l_lat'=>$l_lat,
            'l_long'=>$l_long,
        );
        if(!empty($lat_lng))
        {
        $this->common->update('booking', $lat_lng, $where = array('id' => $id));
          $response['message'] = 'update';
          $response['status'] = 'true';
        

    }
    else
    {
         $response['message'] = 'no';
        $response['status'] = 'true';

    }
    echo json_encode($response);
    }


    public function addToMyPickup()
    {
        $post = $this->input->post();
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_rules('booking_id', 'Booking Id', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $post = $this->input->post();
            $driver_id = $post['driver_id'];
            $booking_id = $post['booking_id'];
           $getbook =  $this->common->find('booking','*',array('id'=>$booking_id));
           if(!empty($getbook)){
                $updateDriverId = $this->common->update('booking',array('driver_id'=>$driver_id),array('id'=>$booking_id));
                if(!empty($updateDriverId)){
                    $response['message'] = 'inforamtion update successfully';
                    $response['status'] = 'true';
                }else{
                    $response['message'] = 'something went wrong';
                    $response['status'] = 'false';
                }
            }else{
            $response['message'] = 'no Booking Found';
            $response['status'] = 'false';

           }
        }else{
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function removeFromMyPickup()
    {
        $post = $this->input->post();
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_rules('booking_id', 'Booking Id', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $post = $this->input->post();
            $driver_id = $post['driver_id'];
            $booking_id = $post['booking_id'];
           $getbook =  $this->common->find('booking','*',array('id'=>$booking_id,'driver_id'=>$driver_id));
           if(!empty($getbook)){
                $updateDriverId = $this->common->update('booking',array('driver_id'=>''),array('id'=>$booking_id));
                if(!empty($updateDriverId)){
                    $response['message'] = 'inforamtion update successfully';
                    $response['status'] = 'true';
                }else{
                    $response['message'] = 'something went wrong';
                    $response['status'] = 'false';
                }
            }else{
            $response['message'] = 'no Booking Found';
            $response['status'] = 'false';

           }
        }else{
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }
    
    public function getCurrentBookingForDriver(){
        $this->form_validation->set_rules('driver_id','driver id','required');
        if ($this->form_validation->run()) {
            $post = $this->input->post();
            $getcurrentBookings = $this->common->booking_based_on_driver_id($post['driver_id']);
            //print_R()
            
            //print_r($getcurrentBookings);die();
            if(!empty($getcurrentBookings)){
                if($getcurrentBookings[0]['mode'] == 0){
                    $getcurrentBookings[0]['push_tag'] = 'booking';
                }else{
                    $getcurrentBookings[0]['push_tag'] = '';

                }
                $response['message'] = 'booking list fetch successfully';
                $response['status'] = 'true';
                $response['booking'] = $getcurrentBookings;
            }else{
                $response['message'] = 'no Booking Found';
                $response['status'] = 'false';
            }
        }else{
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }
    
    public function getCurrentBookingForUser(){
        $this->form_validation->set_rules('user_id','user id','required');
        if ($this->form_validation->run()) {
            $post = $this->input->post();
            $getcurrentBookings = $this->common->booking_based_on_user_id($post['user_id']);
            if(!empty($getcurrentBookings)){
                if($getcurrentBookings[0]['mode'] == 1){
                    $getcurrentBookings[0]['push_tag'] = 'accept';
                }
                else if ($getcurrentBookings[0]['mode'] == 7) {
					$getcurrentBookings[0]['push_tag'] = 'accept';    
				}
                else{
                    $getcurrentBookings[0]['push_tag'] = '';
                }
                $response['message'] = 'booking list fetch successfully';
                $response['status'] = 'true';
                $response['booking'] = $getcurrentBookings;
            }else{
                $response['message'] = 'no Booking Found';
                $response['status'] = 'false';
            }
        }else{
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }
   public function testniti()
   {
        $push_array = array(
            "title" =>'Hello',
                    "alert" => 'hello testing',
                    "sound" => 'default',
                    "content-available" => 1,
                    "push_tag" => "no_driver");
       echo $this->common->send_user_push_fcm('eZCJLzfYxVw:APA91bE6Fl-nT2uuSBMz5o5zVMQuUL4Byv3rgJFwHPzxKaa5x-8m6YFdsP6nhfYFghZj7xPwhj4T4yjGVchowx4UDa0WjbV1Uhn_aHyJvXDuY4VAZfmWHuhB1hVUlo3DvD8qFB-qDtFT', $push_array, 'ios', $PEM_MODE = '2');
   }

     public function gethighprice(){

     }



}


?>
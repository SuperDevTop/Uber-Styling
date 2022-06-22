<?php

defined('BASEPATH') OR exit('No direct script access allowed');

//define('servername','https://maestrosinfotech.com/arrive');
//define('userimagepath','/uploads/users/');
class User extends CI_Controller 
{

    public function __construct()
	 {
        parent::__construct();
        require_once APPPATH . "third_party/stripe/init.php";
        //set api key
        $stripe = array(
            "secret_key" => "sk_test_mdSVOWuJr33Ahm4NN37D1GxY",
            "publishable_key" => "pk_test_h2IyoYlgfWAuKq0mWWD4hIYi"
        );
        \Stripe\Stripe::setApiKey($stripe['secret_key']);
    }

    public function update_driver_online_status() 
    {
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_rules('is_online', 'Driver is online', 'required|xss_clean'); //0 OR 1
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) 
        {
            $post = $this->input->post();
            $postArr = array(
                'is_online' => $post['is_online']
            );
            $update = $this->common->update('driver', $postArr, $where = array('id' => $post['driver_id']));
            $onLineStatus = $this->common->find('driver', $select = 'is_online', $where = array('id' => $post['driver_id']), $resultType = 'array', $orderby = array());
            $response['onLineStatus'] = $onLineStatus['is_online'];
            $response['message'] = 'Updated successfully';
            $response['status'] = 'true';
        } else 
        {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function update_driver_lat_long() {
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_rules('lat', 'Latitude', 'required|xss_clean');
        $this->form_validation->set_rules('long', 'Longitude', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $post = $this->input->post();
            $postArr = array(
                'latitude' => $post['lat'],
                'longitude' => $post['long']
            );
            $where = array(
                'id' => $post['driver_id'],
//                'is_online' => '1'
            );
            $update = $this->common->update('driver', $postArr, $where);
            $getratingdetails = $this->common->driveravgRating($post['driver_id']);
            $response['message'] = 'Driver lat long updated successfully';
            $response['status'] = 'true';
            $response['avg_rating'] = round($getratingdetails[0]['Averagerating']);
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function logout() {
        $this->form_validation->set_rules('id', 'Id', 'required|xss_clean');
        $this->form_validation->set_rules('type', 'Type', 'required|xss_clean'); //user/driver
        $this->form_validation->set_error_delimiters('', '');
        $post = $this->input->post();
        if ($this->form_validation->run()) {
            $where = array(
                'id' => $post['id']
            );
            $postArr = array(
                'appPlatform' => '',
                'token' => '',
            );
            if ($post['type'] == 'user') {
                $this->common->update('user', $postArr, $where);
            } elseif ($post['type'] == 'driver') {
                $this->common->update('driver', $postArr, $where);
            }
            $response['message'] = 'Logout Successfully';
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function user_exist() {
        $postData = $this->input->post();
        $this->form_validation->set_rules('phone', 'Mobile', 'xss_clean|required');
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $detail = $this->common->find('user', $select = 'id, status', $where = array('phone' => $postData['phone']), $resultType = 'array', $orderby = array());
            if (!empty($detail)) {
                if ($detail['status'] == '1') {
                    $response['message'] = 'User exist';
                    $response['result'] = $detail['id'];
                    $response['status'] = 'true';
                } else {
                    $response['message'] = 'Your account is deactivate please contact to admin';
                    $response['status'] = 'false';
                }
            } else {
                $response['message'] = 'User not exist';
                $response['status'] = 'false';
            }
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function forgot_password() {
        $post = $this->input->post();
        $this->form_validation->set_rules('id', 'Id', 'required|xss_clean');
        $this->form_validation->set_rules('password', 'Password', 'trim|required|xss_clean|min_length[6]');
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $postData = array(
                'password' => $post['password']
            );
            $where = array(
                'id' => $post['id']
            );
            $update = $this->common->update('user', $postData, $where);
            if ($update == '1') {
                $response['message'] = 'Password updated successfully';
                $response['status'] = 'true';
            } else {
                $response['message'] = 'Problem while updating password';
                $response['status'] = 'false';
            }
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function get_language() {
        $language = $this->common->findAll('language', $select = 'id, language');
        $response['result'] = $language;
        $response['status'] = 'true';
        echo json_encode($response);
    }

    public function content() {
        $post = $this->input->post();
        $this->form_validation->set_rules('type', 'Type', 'required');
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $type = $post['type'];
            if ($type == 'Call us') {
                $detail = $this->common->find('admin', $select = 'email_id, phone');
            } else {
                $detail = $this->common->find('en_content', $select = 'title, description', $where = array('title' => $type));
            }
            $response['result'] = $detail;
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function faq() {
        $post = $this->input->post();
        $this->form_validation->set_rules('type', 'Type', 'required');
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $type = $post['type'];
            if ($type == 'My Account') {
                $detail = $this->common->findAll('en_faq', $select = 'question, answer', $where = array('title' => $type));
            } elseif ($type == 'Driver Option') {
                $detail = $this->common->findAll('en_faq', $select = 'question, answer', $where = array('title' => $type));
            } elseif ($type == 'Payment Option') {
                $detail = $this->common->findAll('en_faq', $select = 'question, answer', $where = array('title' => $type));
            } elseif ($type == 'Fares and Charges') {
                $detail = $this->common->findAll('en_faq', $select = 'question, answer', $where = array('title' => $type));
            } elseif ($type == 'Rantal') {
                $detail = $this->common->findAll('en_faq', $select = 'question, answer', $where = array('title' => $type));
            }
            $response['result'] = $detail;
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function get_notification() {
        $this->form_validation->set_rules('user_id', 'User Id', 'required');
        $this->form_validation->set_error_delimiters('', '');
        $postData = $this->input->post();
        if ($this->form_validation->run()) {
            $notification = $this->common->get_notification($postData['user_id']);
            $response['result'] = $notification;
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function edit_profile() {
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('name', 'Name', 'required|xss_clean');
        $this->form_validation->set_rules('phone', 'Mobile number', 'required|xss_clean');
        $this->form_validation->set_rules('email', 'Email', 'required|xss_clean');
        $this->form_validation->set_rules('lang_id', 'Language Id', 'required|xss_clean');
        $this->form_validation->set_rules('avatar_img_id', 'Avatar Imgae Id', 'required|xss_clean');
        $postData = $this->input->post();
        $avatarPath = base_url() . 'assets/upload/avatar/';
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $userData = array(
                'name' => $postData['name'],
                'phone' => $postData['phone'],
                'email' => $postData['email'],
                'language_id' => $postData['lang_id'],
                'avatar_img_id' => $postData['avatar_img_id'],
            );
            $this->common->update('user', $userData, $where = array('id' => $postData['user_id']));
            $result = $this->common->find('user', '*', array('id' => $postData['user_id']), 'array');
            $avatarImg = $this->common->find('avatar', 'id, name, img', array('id' => $postData['avatar_img_id']), 'array');
            $finalArray = array(
                "id" => $result['id'],
                "name" => $result['name'],
                "email" => $result['email'],
                "phone" => $result['phone'],
                "language_id" => $result['language_id'],
                "img" => (($avatarImg['img'] != '') ? $avatarPath . $avatarImg['img'] : '')
            );
            $response['result'] = $finalArray;
            $response['message'] = 'Profile updated Successfully';
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function avatar() {
        $avatarPath = base_url() . 'assets/upload/avatar/';
        $avatar = $this->common->findAll('avatar', $select = 'id, name, img', $where = array('status' => '1'));
        $avatarArr = array();
        foreach ($avatar as $key => $value) {
            $array = array(
                'id' => $value['id'],
                'name' => $value['name'],
                'img' => (($value['img'] == '') ? '' : $avatarPath . $value['img']),
            );
            $avatarArr[] = $array;
        }
        $response['result'] = $avatarArr;
        $response['status'] = 'true';
        echo json_encode($response);
    }

    public function chat() 
	{
        $this->form_validation->set_rules('booking_id', 'Booking Id', 'required|xss_clean');
        $this->form_validation->set_rules('sender_id', 'Sender Id', 'required|xss_clean');
        $this->form_validation->set_rules('receiver_id', 'Receiver Id', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        $post = $this->input->post();
        $userPath = base_url() . 'assets/upload/avatar/';
        $chatFilePath = base_url() . 'assets/upload/chat/';
        $postData = array(
            'booking_id' => $post['booking_id'],
            'sender_id' => $post['sender_id'],
            'receiver_id' => $post['receiver_id'],
            'msg' => $post['msg'],
        );
        if ($this->form_validation->run()) 
		{
            $config['file_name'] = '_chat_' . time();
            $config['upload_path'] = './assets/upload/chat';
            $config['allowed_types'] = 'gif|jpg|png|jpeg|PNG|JPG|JPEG|GIF';
            $config['max_size'] = '100000';
            $config['max_width'] = '2000000';
            $config['max_height'] = '2000000';
            $config['remove_spaces'] = TRUE;
            $config['encrypt_name'] = TRUE;
            $this->load->library('upload', $config);
            if (!empty($_FILES)) 
			{
                if (($_FILES['file']['error']) == 0) 
				{
                    $this->upload->do_upload('file');
                    $imgData = array('upload_data' => $this->upload->data());
                    $postData['file'] = trim($imgData['upload_data']['file_name']);
                } else 
				{
                    $error = array('error' => $this->upload->display_errors());
                    echo $error['error'];
                }
            }
            // $record = $this->common->find('chat', $select = '*', $where = array('sender_id' => $post['sender_id'], 'receiver_id' => $post['receiver_id']), $resultType = 'array', $orderby = array());
            $record = $this->common->pre_chat($post['sender_id'], $post['receiver_id']);
            $msgId = $this->common->save('chat', $postData);
            if (!empty($record)) 
			{
                $this->common->update('chat', $postUpdate = array('parent_id' => $record[0]['id']), $where = array('id' => $msgId));
            } else {
                $this->common->update('chat', $postUpdate = array('parent_id' => $msgId), $where = array('id' => $msgId));
            }
            $mssgg = serialize($_FILES);
            $msgData = $this->common->find('chat', $select = 'booking_id as bookingId, msg, file, parent_id, added_on', $where = array('id' => $msgId), $resultType = 'array', $orderby = array());
            $datetime = explode(' ', $msgData['added_on']);
            $msgDate = date("j M Y", strtotime($datetime[0]));
            //$time = explode(' ', $msgData['added_on']);
            $receiverData = $this->common->find('user', $select = 'name, avatar_img_id, token, appPlatform', $where = array('id' => $post['receiver_id']), $resultType = 'array', $orderby = array());
            $receiverAvatarImg = $this->common->find('avatar', 'id, name, img', array('id' => $receiverData['avatar_img_id']), 'array');
            $senderData = $this->common->find('user', $select = 'id as senderId, name, avatar_img_id', $where = array('id' => $post['sender_id']), $resultType = 'array', $orderby = array());
            $senderAvatarImg = $this->common->find('avatar', 'id, name, img', array('id' => $senderData['avatar_img_id']), 'array');
            $receiverDataArr = array(
                'name' => $receiverData['name'],
                'img' => (($receiverAvatarImg['img'] != '') ? $userPath . $receiverAvatarImg['img'] : '')
            );
            $senderDataArr = array(
                'name' => $senderData['name'],
                'img' => (($senderAvatarImg['img'] != '') ? $userPath . $senderAvatarImg['img'] : ''),
            );
            $result = array(
                'msg' => $msgData['msg'],
                'msgTime' => $msgDate . ' ' . $datetime[1],
                'chatFile' => (($msgData['file'] != '') ? $chatFilePath . $msgData['file'] : ''),
                'receiverData' => $receiverDataArr,
                'senderData' => $senderDataArr, //Send in push
            );
            $token = $receiverData['token'];
            $for = $receiverData['appPlatform'];
            $push_message = 'You have a message';
            $push_array = array(
                "senderId" => $senderData['senderId'],
                "senderName" => $senderData['name'],
                "senderImg" => (($senderAvatarImg['img'] != '') ? $userPath . $senderAvatarImg['img'] : ''),
                'parent_id' => $msgData['parent_id'],
                // 'taskId' => $msgData['taskId'],
                'booking_id' => $post['booking_id'],
                'msg' => $msgData['msg'],
                'msgTime' => $msgDate . ' ' . $datetime[1],
                'chatFile' => (($msgData['file'] != '') ? $chatFilePath . $msgData['file'] : ''),
                "alert" => $push_message,
                "sound" => 'default',
                "content-available" => 1,
                "push_tag" => "chat"
            );
            $this->common->send_user_push_fcm($token, $push_array, $for, $PEM_MODE = '2');
            $response['message'] = 'Message send successfully';
            $response['result'] = $result;
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function open_chat() {
        $this->form_validation->set_rules('parent_id', 'Parent Id', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        $post = $this->input->post();
        $userPath = base_url() . 'assets/upload/avatar/';
        $chatFilePath = base_url() . 'assets/upload/chat/';
        if ($this->form_validation->run()) {
            $where = array(
                'parent_id' => $post['parent_id'],
            );
            $chating = $this->common->findAll('chat', $select = 'id as chatId, msg, file, parent_id, sender_id, receiver_id, booking_id, added_on', $where, $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');

            $result = array();
            foreach ($chating as $key => $value) {
                $datetime = explode(' ', $value['added_on']);
                $msgDate = date("j M Y", strtotime($datetime[0]));
                /* $taskData = $this->common->find('en_task', $select = 'title', $where = array('id' => $value['task_id']), $resultType = 'array', $orderby = array());
                  if ($post['user_id'] == $value['sender_id']) {

                  $where = array('id' => $value['receiver_id']);
                  } elseif ($post['user_id'] == $value['receiver_id']) {

                  $where = array('id' => $value['sender_id']);
                  } */
                $userData = $this->common->find('user', $select = 'name, avatar_img_id', $where = array('id' => $value['sender_id']), $resultType = 'array', $orderby = array());
                $userImg = $this->common->find('avatar', 'id, name, img', array('id' => $userData['avatar_img_id']), 'array');
                $finalArr = array(
                    'chatId' => $value['chatId'],
                    'booking_id' => $value['booking_id'],
                    'parent_id' => $value['parent_id'],
                    //'taskName' => $taskData['title'],
                    'msg' => $value['msg'],
                    'file' => (($value['file'] != '') ? $chatFilePath . $value['file'] : ''),
                    'sender_id' => $value['sender_id'],
                    'receiver_id' => $value['receiver_id'],
                    'userName' => $userData['name'],
                    'userImg' => (($userImg['img'] != '') ? $userPath . $userImg['img'] : ''),
                    'dateTime' => $msgDate . ' ' . $datetime[1]
                );
                $result[] = $finalArr;
            }
            $response['result'] = $result;
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function rating() {
        $this->form_validation->set_rules('booking_id', 'Booking Id', 'required');
        $this->form_validation->set_rules('user_id', 'User Id', 'required');
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required');
        $this->form_validation->set_rules('rate_for', 'Rate for', 'required');
        $this->form_validation->set_rules('rating', 'Rating', 'required');
        $this->form_validation->set_error_delimiters('', '');
        $postData = $this->input->post();

        if ($this->form_validation->run()) {
            $post = array(
                'booking_id' => $postData['booking_id'],
                'user_id' => $postData['user_id'],
                'driver_id' => $postData['driver_id'],
                'rate_for' => $postData['rate_for'],
                'rating' => $postData['rating'],
                'review' => $postData['review']
            );
            $this->common->save('rating', $post);
            $response['message'] = 'Rating added successfully';
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function rating_list() {
        $this->form_validation->set_rules('id', 'Id', 'required|xss_clean');
        $this->form_validation->set_rules('type', 'Type', 'required|xss_clean');
        $post = $this->input->post();
        $this->form_validation->set_error_delimiters('', '');
        $userImgPath = base_url() . 'assets/upload/avatar/';
        if ($this->form_validation->run()) {
            if ($post['type'] == 'as_driver') {
                $rating = $this->common->findAllWithJoin('rating', $select = 'rating.id AS ratingId, rating.rating, rating.review, rating.added_on, user.name, user.avatar_img_id, booking.id AS bookingId, booking.booking_id AS bookingCode', $join = array('booking' => 'booking.id = rating.booking_id', 'user' => 'user.id = rating.user_id'), $where = array('rating.driver_id' => $post['id'], 'rating.rate_for' => 'driver'), $orderby = array(), $resultType = 'array', $limit = '');
            } elseif ($post['type'] == 'as_user') {
                $rating = $this->common->findAllWithJoin('rating', $select = 'rating.id AS ratingId, rating.rating, rating.review, rating.added_on, user.name, user.avatar_img_id, booking.id AS bookingId, booking.booking_id AS bookingCode', $join = array('booking' => 'booking.id = rating.booking_id', 'user' => 'user.id = rating.user_id'), $where = array('rating.user_id' => $post['id'], 'rating.rate_for' => 'user'), $orderby = array(), $resultType = 'array', $limit = '');
            }
            $result = array();
            foreach ($rating as $key => $value) {
                $datetime = explode(' ', $value['added_on']);
                $ratingDate = date("j M Y", strtotime($datetime[0]));
                $ratePersonImg = $this->common->find('avatar', 'id, name, img', array('id' => $value['avatar_img_id']), 'array');
                $array = array(
                    'ratingId' => $value['ratingId'],
                    'rating' => $value['rating'],
                    'review' => $value['review'],
                    'date' => $ratingDate,
                    'name' => $value['name'],
                    'ratePersonImg' => (($ratePersonImg['img'] == '') ? '' : $userImgPath . $ratePersonImg['img']),
                    'bookingCode' => $value['bookingCode'],
                );
                $result[] = $array;
            }
            $response['result'] = $result;
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function fare_review_question_list() {
        //$avatarPath = base_url() . 'assets/upload/avatar/';
        $fare = $this->common->findAll('fare_review_question', $select = 'id, type', $where = array('status' => '1'));
        $response['result'] = $fare;
        $response['status'] = 'true';
        $response['message'] = 'fare review question list fetch successfully';
        echo json_encode($response);
    }

    public function fare_review() {

        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required');
        $this->form_validation->set_rules('fare_question_id', 'Fare Review Reason', 'required');
        $this->form_validation->set_rules('review', 'Review');
        $this->form_validation->set_error_delimiters('', '');
        $postData = $this->input->post();
        if ($this->form_validation->run()) {
            $post = array(
                //'booking_id' => $postData['booking_id'],
                'fare_review_id' => $postData['fare_question_id'],
                'driver_id' => $postData['driver_id'],
                'review' => $postData['review']
            );
            $this->common->save('fare_review', $post);
            $response['message'] = 'review added successfully';
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function driversearch() {
        $this->form_validation->set_rules('lat', 'lat', 'required|xss_clean');
        $this->form_validation->set_rules('lng', 'lng', 'required|xss_clean');
        $post = $this->input->post();
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $searchdriverres = $this->common->driver_search($post['lat'], $post['lng']);
            $result = array();
            foreach ($searchdriverres as $key => $value) {
                $distanceDuration = $this->common->get_driving_distance($post['lat'], $value['latitude'], $post['lng'], $value['longitude']);
                $res['time'] = $distanceDuration['time'];
                $res['distance'] = $distanceDuration['distance'] . '' . 'km';
                $res['first_name'] = $value['first_name'];
                $res['last_name'] = $value['last_name'];
                $res['latitude'] = $value['latitude'];
                $res['longitude'] = $value['longitude'];
                $result[] = $res;
            }
            if (!empty($searchdriverres)) {
                $response['result'] = $result;
                $response['status'] = 'true';
                $response['message'] = 'driver list fetch successfully';
            } else {
                $response['result'] = [];
                $response['message'] = "No Recod Found";
                $response['status'] = 'true';
            }
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function user_unique_phone($phone) {
        $phoneIsExist = $this->common->findAll('user', $select = 'phone', $where = array('phone' => $phone), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
        if (!empty($phoneIsExist)) {
            $this->form_validation->set_message('user_unique_phone', 'Mobile number should be unique');
            return FALSE;
        } else {
            return TRUE;
        }
    }

    // testing of the sms  
    public function testmsm() {
        $id = "AC7c0ff5aa391cc180b4be0e6d37a4b057";
        $token = "ca9914d7b199dc9e51532397745e8ada";
        $url = "https://api.twilio.com/2010-04-01/Accounts/$id/SMS/Messages";
        $from = "+18632926809";
        $to = "+917840078303";
        $body = "test";
        $data = array(
            'From' => $from,
            'To' => $to,
            'Body' => $body,
        );
        $post = http_build_query($data);
        $x = curl_init($url);
        curl_setopt($x, CURLOPT_POST, true);
        curl_setopt($x, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($x, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($x, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
        curl_setopt($x, CURLOPT_USERPWD, "$id:$token");
        curl_setopt($x, CURLOPT_POSTFIELDS, $post);
        $y = curl_exec($x);
        curl_close($x);
        $response_a = json_decode($y, true);
    }

// notifacation to the user 
    public function notifacationToDriver() {
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required');
        $this->form_validation->set_error_delimiters('', '');
        $postData = $this->input->post();
        if ($this->form_validation->run()) {
            $notification = $this->common->findAll('notification', 'notification_msg,SUBSTRING_INDEX(added_on, " " , 1) as notification_date,SUBSTRING_INDEX(added_on, " ", -1) as notification_time', array('driver_id' => $postData['driver_id']), ['id' => 'DESC'], 'array');
            $response['result'] = $notification;
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function bookedDriverList() 
	{
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        $postData = $this->input->post();
        if ($this->form_validation->run()) 
		{
            $userDriver = $this->common->findAllWithJoin("booking b", $select = 'concat(d.first_name," " ,d.middle_name ," ", d.last_name) as name, d.img, b.driver_id,d.driver_id,d.id', $join = array("driver d" => "d.id=b.driver_id"), $where = array("b.user_id" => $postData['user_id'], "b.driver_id !=" => 0), $orderby = array(), $resultType = 'array', $limit = '', $groupby = "b.driver_id");
            //echo $this->db->last_query();die();
            $i = 0;
            //print_r($userDriver);die();
            foreach ($userDriver as $key => $value) {
                $data[$i]['name'] = $value['name'];
                $data[$i]['driver_code'] = $value['driver_id'];
                $data[$i]['driver_id'] = $value['id'];
                if ($value['img'] == '') {
                    $data[$i]['driver_img'] = base_url() . "/uploads/users/UserImage.jpg";
                } else {
                    $data[$i]['driver_img'] = base_url() . "/uploads/users/" . $value['img'];
                }
                $i++;
            }
            if (sizeof($userDriver) > 0) {
                $response['result'] = $data;
                $response['status'] = 'true';
            } else {
                $response['result'] = [];
                $response['message'] = "No Recod Found";
                $response['status'] = 'true';
            }
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function reportReasonList() {
        $this->form_validation->set_rules('type', 'Type', 'required|xss_clean');
        $postData = $this->input->post();
        if ($this->form_validation->run()) {
            $report = $this->common->findAll('report_reason', 'id,report_reason as reason', ['status' => '1', "type" => $postData['type']]);
            $response['result'] = $report;
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function submitReason() {
        $this->form_validation->set_rules('booking_id', 'Booking Id', 'required|xss_clean');
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('reason_id', 'Reason Id', 'required|xss_clean');
        $this->form_validation->set_rules('type', 'Type', 'required|xss_clean');
        $this->form_validation->set_error_delimiters("", "");
        $postData = $this->input->post();
        if ($this->form_validation->run()) {
            if ((!empty($_FILES['image']['name']))) {
                $config['file_name'] = '_img_' . time();
                $config['upload_path'] = './assets/upload/reason_img/';
                $config['allowed_types'] = '*';                                      // Allow all type of file
                $config['max_size'] = '100000';
                $config['max_width'] = '2000000';
                $config['max_height'] = '2000000';
                $config['remove_spaces'] = TRUE;
                $config['encrypt_name'] = TRUE;
                $this->load->library('upload', $config);
                $this->upload->initialize($config);
                if (($_FILES['image']['error']) == 0) {
                    $this->upload->do_upload('image');
                    $fileData = array('upload_data' => $this->upload->data());
                    $userReson['image'] = trim($fileData['upload_data']['file_name']);
                } else {
                    $error = array('error' => $this->upload->display_errors());
                    echo $error['error'];
                }
            }
            $userReson["booking_id"] = $postData['booking_id'];
            $userReson["driver_id"] = $postData['driver_id'];
            $userReson["user_id"] = $postData['user_id'];
            $userReson["reason_id"] = $postData['reason_id'];
            $userReson["type"] = $postData['type'];
            $userReson["comment"] = $postData['comment'];
            $msgId = $this->common->save('users_reason', $userReson);
            $response['message'] = "Reason data saved";
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function editUserPofile() {
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('first_name', 'First Name', 'xss_clean');
        $this->form_validation->set_rules('last_name', 'Last Name', 'xss_clean');
        $this->form_validation->set_rules('city', 'City', 'xss_clean');
        $this->form_validation->set_rules('fav_music', 'Fav Music', 'xss_clean');
        $this->form_validation->set_rules('about_me', 'About Me', 'xss_clean');
        $postData = $this->input->post();
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $userData = array(
                'first_name' => $postData['first_name'],
                'last_name' => $postData['last_name'],
                'city' => $postData['city'],
                'fav_music' => $postData['fav_music'],
                'about_me' => $postData['about_me'],
            );
            if ((!empty($_FILES['image']['name']))) {
                $config['file_name'] = '_img_' . time();
                $config['upload_path'] = './uploads/users/';
                $config['allowed_types'] = '*';                                      // Allow all type of file
                $config['max_size'] = '100000';
                $config['max_width'] = '2000000';
                $config['max_height'] = '2000000';
                $config['remove_spaces'] = TRUE;
                $config['encrypt_name'] = TRUE;
                $this->load->library('upload', $config);
                $this->upload->initialize($config);
                if (($_FILES['image']['error']) == 0) {
                    $this->upload->do_upload('image');
                    $fileData = array('upload_data' => $this->upload->data());
                    $userData['img'] = trim($fileData['upload_data']['file_name']);
                   
                } else {
                    $error = array('error' => $this->upload->display_errors());
                    echo $error['error'];
                }
            }
            
            $this->common->update('user', $userData, $where = array('id' => $postData['user_id']));
            $result = $this->common->find('user', '*', array('id' => $postData['user_id']), 'array');
            $result['password'] = '';
            $result['join_date'] = date("F Y", strtotime($result['added_on']));
            if ($result['img'] == '') {
                $result['img'] = base_url() . "/uploads/users/UserImage.jpg";
            } else {
                $result['img'] = base_url() . "/uploads/users/" . $result['img'];
            }
          
            $response['result'] = $result;
            $response['message'] = 'Profile updated Successfully';
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }
    

    public function editDriverPofile() {
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_rules('first_name', 'First Name', 'xss_clean');
        $this->form_validation->set_rules('middle_name', 'Middel Name', 'xss_clean');
        $this->form_validation->set_rules('last_name', 'Last Name', 'xss_clean');
        $this->form_validation->set_rules('email', 'Email', 'xss_clean');
        $this->form_validation->set_rules('gender', 'Gender', 'xss_clean');
        $this->form_validation->set_rules('address1', 'Address1', 'xss_clean');
        $this->form_validation->set_rules('city', 'City', 'xss_clean');
        $this->form_validation->set_rules('country', 'Country', 'xss_clean');
        $postData = $this->input->post();
        $this->form_validation->set_error_delimiters('', '');
        if ($this->form_validation->run()) {
            $userData = array(
                'first_name' => $postData['first_name'],
                'middle_name' => $postData['middle_name'],
                'last_name' => $postData['last_name'],
                'email' => $postData['email'],
                'gender' => $postData['gender'],
                'address1' => $postData['address1'],
                //'address2' => '',
                'city' => $postData['city'],
                //'state' => '',
                'country' => $postData['country'],
            );
            if ((!empty($_FILES['image']['name']))) {
                $config['file_name'] = '_img_' . time();
                $config['upload_path'] = './uploads/drivers/';
                $config['allowed_types'] = '*';                                      // Allow all type of file
                $config['max_size'] = '100000';
                $config['max_width'] = '2000000';
                $config['max_height'] = '2000000';
                $config['remove_spaces'] = TRUE;
                $config['encrypt_name'] = TRUE;
                $this->load->library('upload', $config);
                $this->upload->initialize($config);
                if (($_FILES['image']['error']) == 0) {
                    $this->upload->do_upload('image');
                    $fileData = array('upload_data' => $this->upload->data());
                    $userData['img'] = trim($fileData['upload_data']['file_name']);
                } else {
                    $error = array('error' => $this->upload->display_errors());
                    echo $error['error'];
                }
            }
            $this->common->update('driver', $userData, $where = array('id' => $postData['driver_id']));
            $result = $this->common->find('driver', '*', array('id' => $postData['driver_id']), 'array');
            $result['password'] = '';
            $result['address'] = $result['address1'] . ' ' . $result['address2'];
            if (!empty($result['img'])) {
                $result['image'] = base_url() . "/uploads/drivers/" . $result['img'];
            } else {
                $result['image'] = base_url() . "/uploads/drivers/" . 'UserImage.jpg';
            }
            $result['insuarance_img'] = base_url() . "/uploads/drivers/" . $result['insuarance_img'];
            $result['licence_img'] = base_url() . "/uploads/drivers/" . $result['licence_img'];
            $result['adhar_img'] = base_url() . "/uploads/drivers/" . $result['adhar_img'];
            $result['vechile_img'] = base_url() . "/uploads/drivers/" . $result['vechile_img'];
            $response['result'] = $result;
            $response['message'] = 'Profile updated Successfully';
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function driverEarningFilter() {
        $dropDownArray = [["type" => "Today", "id" => 0], ["type" => "Week", "id" => 1], ["type" => "Month", "id" => 2], ["type" => "Overall", "id" => 3]];
        $response['result'] = $dropDownArray;
        $response['status'] = "true";
        echo json_encode($response);
    }

    public function driverEarning() {
        $this->form_validation->set_rules('driver_id', 'Driver Id', 'required|xss_clean');
        $this->form_validation->set_rules('time_period', 'Time Period', 'xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        $postData = $this->input->post();
        if ($this->form_validation->run()) {
            $time = $postData['time_period'];
            $driver_id = $postData['driver_id'];
            if ($time == '' || $time == 0) {
                $where = [
                    "b.driver_id" => $driver_id,
                    "b.mode" => '4',
                    "date(b.schedule_date)" => date("Y-m-d"),
                ];
            } elseif ($time == 1) {
                $week = date("W") - 1;
                $where = [
                    "b.driver_id" => $driver_id,
                    "b.mode" => '4',
                    "WEEK(DATE(b.schedule_date))" => $week,
                ];
            } elseif ($time == 2) {
                $where = [
                    "b.driver_id" => $driver_id,
                    "b.mode" => '4',
                    "Month(b.schedule_date)" => date("m"),
                ];
            } elseif ($time == 3) {
                $where = [
                    "b.driver_id" => $driver_id,
                    "b.mode" => '4',
                ];
            }
            $join = [
                "user u" => "u.id=b.user_id",
            ];
            $bookingData = $this->common->findAllWithJoin('booking b', $select = 'Concat(u.first_name," ",u.last_name) as name,u.img ,b.*', $join, $where, $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
            $resultArray = [];
            $amount = 0;
            $time = 0;
            $resultArray['bookinglist'] = [];
            for ($i = 0; $i < sizeof($bookingData); $i++) {
                $resultArray['bookinglist'][$i]['name'] = $bookingData[$i]['name'];
                $resultArray['bookinglist'][$i]['start_point'] = $bookingData[$i]['start_point'];
                $resultArray['bookinglist'][$i]['end_point'] = $bookingData[$i]['end_point'];
                $resultArray['bookinglist'][$i]['amount'] = $bookingData[$i]['amount'];
                if ($bookingData[$i]['img'] == '') {
                    $resultArray['bookinglist'][$i]['image'] = base_url() . "/uploads/users/UserImage.jpg";
                } else {
                    $resultArray['bookinglist'][$i]['image'] = base_url() . "/uploads/users/" . $bookingData[$i]['img'];
                }
                $amount += $bookingData[$i]['amount'];
                $time += explode(" ", $bookingData[$i]['duration'])[0];
            }

            $resultArray['totalride'] = sizeof($bookingData);
            $resultArray['totalearnamount'] = $amount;
            $resultArray['totaltime'] = strlen(floor($time / 60)) == 1 ? '0' . floor($time / 60) . ':' . ($time - floor($time / 60) * 60) : floor($time / 60) . ':' . ($time - floor($time / 60) * 60);
            $response['result'] = $resultArray;
            $response['status'] = "true";
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }

        echo json_encode($response);
    }

    public function promoCode() {
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        $postData = $this->input->post();
        if ($this->form_validation->run()) {
            $userId = $postData['user_id'];
            $promo = $this->common->getPromoCodeList($userId);
            if (!empty($promo)) {
                $response['message'] = "Promo Code list fetch successfully.";
                $response['status'] = 'true';
                $response['promoCode'] = $promo;
            } else {
                $response['message'] = 'No Promo Code find';
                $response['status'] = 'false';
            }
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function Promo_code_check() {
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('promo_code', 'Promo Code', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        $postData = $this->input->post();
        if ($this->form_validation->run()) {
            $promo_code = $postData['promo_code'];
            $user_id = $postData['user_id'];
            $promodata = $this->common->findAll('promo_code', $select = '*', $where = array('promo_code' => $promo_code, 'status' => '1'), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
            if (empty($promodata)) {
                $response['message'] = 'Invalid Promo code';
                $response['status'] = 'false';
            } else {
                $promo = $this->common->checkPromoCode($promo_code);
                if (!empty($promo)) {
                    $userIds = $promo['user_id'];
                    if (strpos($userIds, $user_id) !== false) {
                        $con = array('promo_code' => $promo_code);
                        $promodetails = $this->common->find('promo_code', $select = '*', $where = array('promo_code' => $promo_code), $resultType = 'array', $orderby = array());
                        if ($promodetails['promo_type_name'] == 'Percentage') {
                            $detail = array(
                                'promo_type_name' => $promodetails['promo_type_name'],
                                'promoValue' => $promodetails['promo_value'],
                                'discount' => $promodetails['discount'],
                                'maxDiscount' => $promodetails['maxium_discount'],
                                'minimumPurchaseValue' => $promodetails['minimum_purchase_value'],
                                'promoCode' => $promodetails['promo_code'],
                                'id' => $promodetails['id'],
                            );
                            $response = [
                                'success' => true,
                                'message' => 'promo code applied.',
                                'result' => $detail
                            ];
                        } else {
                            $detail = array(
                                'promo_type_name' => $promodetails['promo_type_name'],
                                'promoValue' => $promodetails['promo_value'],
                                'discount' => $promodetails['discount'],
                                'minimumPurchaseValue' => $promodetails['minimum_purchase_value'],
                                'promoCode' => $promodetails['promo_code'],
                                'id' => $promodetails['id'],
                            );
                            $response = [
                                'success' => true,
                                'message' => 'promo code applied.',
                                'result' => $detail
                            ];
                        }
                    } else {
                        $response['message'] = 'Invalid user id.';
                        $response['status'] = 'false';
                    }
                } else {
                    $response['message'] = 'Your promocode is expire';
                    $response['status'] = 'false';
                }
            }
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function highZone() {
        $postData = $this->input->post();
        $userId = '';
        $highpaying = $this->common->getHighpayingList($userId);
        if (!empty($highpaying)) {
            $response['message'] = "High Paying Zone list fetch successfully.";
            $response['status'] = 'true';
            $response['highpaying'] = $highpaying;
        } else {
            $response['message'] = 'No High Paying Zone find';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function add_business_profile_bk() {
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('type', 'type', 'required|xss_clean');
        $this->form_validation->set_rules('email', 'Email', 'required|xss_clean|valid_email');
        $this->form_validation->set_rules('payment_method_type', 'payment method type', 'required|xss_clean');
        $this->form_validation->set_rules('report_status', 'report status', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        $post = $this->input->post();
        if ($this->form_validation->run()) {
            $postData = array(
                'user_id' => $post['user_id'],
                'email' => $post['email'],
                'type' => $post['type'],
                'payment_method_type' => $post['payment_method_type'],
                'report_status' => $post['report_status'],
                'status' => '1',
                'created_at' => date("Y-m-d H:i:s"));
            $userId = $post['user_id'];
            $type_id = $post['type'];
            $user_val = '';
            $type = '';
            if (!empty($userId)) {
                $business_profile_list = $this->common->businessProfileListdata($userId, $type_id);
                if (!empty($business_profile_list)) {
                    foreach ($business_profile_list as $vals) {
                        $user_val = $vals['user_id'];
                        $type = $vals['type'];
                        // if(($user_val==$userId)&&($type!=$type_id))
                        //    {
                        //        //echo"save";
                        //        $promo = $this->common->saveBusinessProfile('add_business_profile', $postData);
                        //    }
                        if (($user_val == $userId) && ($type == $type_id)) {
                            $promo = $this->common->updateBusinessProfile('add_business_profile', $postData);
                        }
                    }
                } else {
                    $promo = $this->common->saveBusinessProfile('add_business_profile', $postData);
                }
            }
            if (!empty($promo)) {
                $response['message'] = "Add business profile successfully.";
                $response['status'] = 'true';
                $response['add_business_profile'] = $promo;
            } else {
                $response['message'] = 'No business profile add';
                $response['status'] = 'false';
            }
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function add_business_profile() {
        $this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_rules('type', 'type', 'required|xss_clean');
        $this->form_validation->set_rules('email', 'Email', 'required|xss_clean|valid_email');
        $this->form_validation->set_rules('payment_method_type', 'payment method type', 'required|xss_clean');
        $this->form_validation->set_rules('report_status', 'report status', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        $post = $this->input->post();
        if ($this->form_validation->run()) {
            $postData = array(
                'user_id' => $post['user_id'],
                'email' => $post['email'],
                'type' => $post['type'],
                'payment_method_type' => $post['payment_method_type'],
                'report_status' => $post['report_status'],
                'status' => '1',
                'created_at' => date("Y-m-d H:i:s")
            );
            $userId = $post['user_id'];
            if (!empty($userId)) {
                $promo = $this->common->saveBusinessProfile('add_business_profile', $postData);
            }
            if (!empty($promo)) {
                $response['message'] = "Add business profile successfully.";
                $response['status'] = 'true';
                $response['add_business_profile'] = $promo;
            } else {
                $response['message'] = 'No business profile add';
                $response['status'] = 'false';
            }
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function businessProfileList() {
        $postData = $this->input->post();
        $userId = $postData['user_id'];
        $business_profile = array();
        $business_profile_list = $this->common->businessProfileList($userId);
        $payment_trans = $this->common->findAll('payment_trans', $select = '*', $where = array('user_id' => $userId));

        // $business_profile_list = $business_profile_list[0];
        //$business_profile_list['pay_detail'] = $payment_trans;
        //sakshi modify
        //print_r($business_profile_list);die();
        foreach ($business_profile_list as $key => $value) {
            if ($value['payment_method_type'] == '1') {
                $u_id = $value['user_id'];
                $get_cust_id = $this->common->find('user', $select = '*', $where = array('id' => $u_id), $resultType = 'objects', $orderby = array());
                //print_r($get_cust_id);die();
                $customerId = $get_cust_id->stripe_user_id;
                //print_r($customerId);die();
                if (!empty($customerId)) {
                    $e = \Stripe\Customer::retrieve($customerId)->sources->all(array('object' => 'card'));
                    //$error['msg'] = $e->data;
                    $business_profile_list[$key]['cardList'] = $e->data;
                }
            } else {
                $business_profile_list[$key]['cardList'] = array();
            }


            //$business_profile_list[$key]['pay']= $payment_trans;
        }
        if (!empty($business_profile_list)) {
            $response['message'] = "Business profile list fetch successfully.";
            $response['status'] = 'true';
            $response['business_profile_list'] = $business_profile_list;
        } else {
            $response['message'] = 'No Business profile find';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function getTerms() {
        //$get_terms = $this->common->getTerms(); 
        $get_terms = $this->common->find('faq', $select = 'title,description', $where = array('status' => '1', 'id' => '6'), $resultType = 'objects', $orderby = array());
        if (!empty($get_terms)) {
            $response['message'] = "Terms & conditions fetch successfully.";
            $response['status'] = 'true';
            $response['get_terms'] = $get_terms;
        } else {
            $response['message'] = 'No Terms & conditions find';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function getPrivacyPolicy() {
        $privacy = $this->common->find('faq', $select = 'title,description', $where = array('status' => '1', 'id' => '7'), $resultType = 'objects', $orderby = array());
        //$get_condition = $this->common->getCondition();
        if (!empty($privacy)) {
            $response['message'] = "Privacy policy fetch successfully.";
            $response['status'] = 'true';
            $response['get_data'] = $privacy;
        } else {
            $response['message'] = 'No Privacy policy found';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    // function for login users
    public function login() {
        $appplatform = $_REQUEST['appPlatform']; //ios,android
        $token = $_REQUEST['token'];
        $mobileno = $_REQUEST['mobileno'];
        $password = $_REQUEST['password'];
        $postArr = array(
            'token' => $_REQUEST['token'],
            'mobile' => $_REQUEST['mobileno'],
            'password' => $_REQUEST['password'],
            'appPlatform' => $_REQUEST['appPlatform'],
        );
        if (!empty($mobileno) && !empty($password)) {
            //$result = $this->common->login_data($mobileno,$password);
            $result = $this->common->find('user', $select = '*', $where = array('mobile' => $mobileno, 'password' => $password), $resultType = 'array', $orderby = array());
            if (!empty($result)) {
                $update = $this->common->update('user', $postArr, $where = array('mobile' => $mobileno));
                $result['password'] = '';
                $result['join_date'] = date("F Y", strtotime($result['added_on']));
                $url = "https://maestrosinfotech.com/arrive/uploads/users/";
                if (!empty($result['img'])) {
                    $result['image'] = $url . $result['img'];
                } else {
                    $result['image'] = $url . 'UserImage.jpg';
                }
                $response['msg'] = "Successfully Login.";
                $response['status'] = 'true';
                $response['details'] = $result;
            } else {
                $response['msg'] = 'Invalid Mobile Number or Password';
                $response['status'] = 'false';
            }
        } else {
            $response['msg'] = 'Invalid Parameter';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function send_sms_varify($mobile, $message) 
	
	{
        $id = "AC7c0ff5aa391cc180b4be0e6d37a4b057";
        $token = "ca9914d7b199dc9e51532397745e8ada";
        $url = "https://api.twilio.com/2010-04-01/Accounts/$id/SMS/Messages";
        $from = "+18634001988";
        //$to = "+917840078303";
        //$body = "test";
        $data = array(
            'From' => $from,
            'To' => $mobile,
            'Body' => $message,
        );
        $post = http_build_query($data);
        $x = curl_init($url);
        //print_r($x);die();
        curl_setopt($x, CURLOPT_POST, true);
        curl_setopt($x, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($x, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($x, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
        curl_setopt($x, CURLOPT_USERPWD, "$id:$token");
        curl_setopt($x, CURLOPT_POSTFIELDS, $post);
        $y = curl_exec($x);
        curl_close($x);
        $response_a = json_decode($y, true);
        //print_r($response_a);die();
        $message = "<pre>" . json_encode(array(
                    'Mobile' => $mobile,
                    'Message' => $message,
                    'API_RES' => json_decode($response_a)
                        ), JSON_PRETTY_PRINT) . "</pre>";
        $subject = "arrive5: SMS API Response";
        $headers = "From:Burdy<system@burdy.com> \r\n";
        $headers .= "MIME-Version: 1.0\r\n";
        $headers .= "Content-type: text/html\r\n";
        $headers .= 'X-Mailer: PHP/' . phpversion();
        $headers .= "X-Priority: 1\n"; // Urgent message!
        $headers .= "Return-Path: sakshi.s@parangat.com\n"; // Return path for errors
        $headers .= "MIME-Version: 1.0\r\n";
        $headers .= "Content-Type: text/html; charset=iso-8859-1\n";
        mail('sakshi.s@parangat.com', $subject, $message, $headers);
    }

    public function verify_mobile() 
	{
        $mobileno = $_REQUEST['mobileno'];
        $check_mobile = $this->common->find('user', $select = '*', $where = array('mobile' => $mobileno), $resultType = 'array', 	 $orderby = array());
		$code = rand(1000, 9999);
        if (!empty($check_mobile)) 
		{
			
			/*$message = "your 4 digit code " . $code;
            $aa = $this->send_sms_varify($mobileno, $message);*/
            $check_mobile['id'] = $check_mobile['id'];
            $check_mobileid['id'] = $check_mobile['id'];
           // $response['message'] = 'Your Otp On '.$mobileno;
            $response['message'] = 'Mobile number  exits';
            $response['status'] = 'true';
            // $response['otp'] = $code;
            $response['details'] = $check_mobileid;
           
        } else {
           
            // = '0000';
            $message = "your 4 digit code " . $code;
            $aa = $this->send_sms_varify($mobileno, $message);
            $response['message'] = 'Mobile number not exits';
            $response['status'] = 'false';
            $response['otp'] = $code;
        }
        echo json_encode($response);
    }

    public function verify_mobile_forgot() {
        $mobileno = $_REQUEST['mobileno'];
        $check_mobile = $this->common->find('user', $select = '*', $where = array('mobile' => $mobileno), $resultType = 'array', $orderby = array());
        if (!empty($check_mobile)) {

            //$code = '0000';
            $code = rand(1000, 9999);
            $message = "your 4 digit code " . $code;
            $check_mobileid['id'] = $check_mobile['id'];
            $aa = $this->send_sms_varify($mobileno, $message);
            $response['message'] = 'Mobile number exits';
            $response['status'] = 'true';
            $response['otp'] = $code;
            $response['details'] = $check_mobileid;
        } else {

            $response['message'] = 'Mobile number not exits';
            $response['status'] = 'false';
            // $response['otp'] = $code;
        }
        echo json_encode($response);
    }

    public function change_password() {
        $id = $_REQUEST['id'];
        $password = $_REQUEST['password'];
        if (!empty($id) && !empty($password)) {
            $select_pass = $this->common->update('user', $postUpdate = array('password' => $password), $where = array('id' => $id));
            if ($select_pass) {
                $response['msg'] = 'Password updated';
                $response['status'] = 'true';
            } else {
                $response['msg'] = 'some error occured';
                $response['status'] = 'false';
            }
        } else {
            $response['msg'] = 'Invalid parameter';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function signup() 
	{
        $firstname = $_REQUEST['firstname'];
        $lastname = $_REQUEST['lastname'];
        $emailid = $_REQUEST['emailid'];
        $mobile_no = $_REQUEST['mobileno'];
        $password = $_REQUEST['password'];
        $token = $_REQUEST['token'];
        $invite_code = $_REQUEST['code'];
        $appPlatform = $_REQUEST['appplatform'];
        $credit_card_no = $_REQUEST['credit_card_no'];
        $cvv_no = $_REQUEST['cvv_no'];
        $paypal_email = $_REQUEST['paypal_email'];
        $paypal_password = $_REQUEST['paypal_password'];
        $card_valid_month = $_REQUEST['card_valid_month'];
        $card_valid_year = $_REQUEST['card_valid_year'];
        if (!empty($firstname) && !empty($emailid) && !empty($mobile_no) && !empty($password)) 
		{
            $data_check = $this->common->find('user', $select = '*', $where = array('mobile' => $mobile_no, 'email' => $emailid), $resultType = 'array', $orderby = array());
            $filename = '';
            if ((!empty($_FILES['image']['name']))) 
			{
                $config['file_name'] = '_img_' . time();
                $config['upload_path'] = './uploads/users/';
                $config['allowed_types'] = '*';                                      // Allow all type of file
                $config['max_size'] = '100000';
                $config['max_width'] = '2000000';
                $config['max_height'] = '2000000';
                $config['remove_spaces'] = TRUE;
                $config['encrypt_name'] = TRUE;
                $this->load->library('upload', $config);
                $this->upload->initialize($config);
                if (($_FILES['image']['error']) == 0) 
				{
                    $this->upload->do_upload('image');
                    $fileData = array('upload_data' => $this->upload->data());
                    $filename = trim($fileData['upload_data']['file_name']);
                } else 
				{
                    $error = array('error' => $this->upload->display_errors());
                    echo $error['error'];
                }
            }
           if(!empty($data_check))
           {
				$data['num_rows_mobile'] = count($data_check['mobile']);
				$data['num_rows_email'] = count($data_check['email']);
           }
            if (empty($data['num_rows_mobile']) && empty($data['num_rows_email'])) 
            {
                $chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                $res = "";
                for ($i = 0; $i < 8; $i++) 
				{
                    $res .= $chars[mt_rand(0, strlen($chars) - 1)];
                }
                if ($invite_code != '') 
                {
                    $data_invite_code = $this->common->find('user', $select = '*', $where = array('invite_code' => $invite_code), $resultType = 'array', $orderby = array());
                    $data['num_rows_invite_code'] = count($data_invite_code['invite_code']);
                    if ($data['num_rows_invite_code'] > 0) 
                    {
                        $in_user_id = $data_invite_code['id'];
                        $code = rand(1000, 9999);
                        //$code = '0000';
                        $message = "your 4 digit code " . $code;
                        $postData = array(
                            'email' => $emailid,
                            'first_name' => $firstname,
                            'last_name' => $lastname,
                            'password' => $password,
                            'img' => $filename,
                            'mobile' => $mobile_no,
                            'token' => $token,
                            'appPlatform' => $appPlatform,
                            'code' => $code,
                            'invite_code' => $res,
                            'invited_by' => $in_user_id,
                            'credit_card_no' => $credit_card_no,
                            'cvv_no' => $cvv_no,
                            'paypal_email' => $paypal_email,
                            'paypal_password' => $paypal_password,
                            'card_valid_month' => $card_valid_month,
                            'card_valid_year' => $card_valid_year,
                        );

                        $save_data = $this->common->save('user', $postData);
                        if ($save_data > 0) 
						{
                            $l_id = $save_data;
                            $personal_data = array(
                                'user_id' => $l_id,
                                'email' => $emailid,
                                'payment_method_type' => '2',
                                'report_status' => '1',
                                'type' => '1',
                                'status' => '1',
                                'created_at' => date('Y-m-d H:i:s'),
                            );
                            $save_personal_profile_data = $this->common->save('add_business_profile', $personal_data);
                            $data_all_id_data = $this->common->find('user', $select = '*', $where = array('id' => $l_id), $resultType = 'array', $orderby = array());
                            $data_all_id_data['password'] = '';
                            $data_all_id_data['join_date'] = date("F Y", strtotime($data_all_id_data['added_on']));
                            $url = "https://maestrosinfotech.com/arrive/uploads/users/";
                            if (!empty($data_all_id_data['img'])) {
                                $data_all_id_data['image'] = $url . $data_all_id_data['img'];
                            } else {
                                $data_all_id_data['image'] = $url . 'UserImage.jpg';
                            }
                            $aa = $this->send_sms_varify($mobile_no, $message);
                            $response['msg'] = 'successfully registration';
                            $response['details'] = $data_all_id_data;
                            $response['status'] = 'true';
                        } else 
                        {
                            $response['msg'] = 'There is some issue in registration';
                            $response['status'] = 'false';
                        }
                    } else 
                    {
                        $response['msg'] = 'Invalid invite code';
                        $response['status'] = 'false';
                    }
                } else 
				{
                    $code = rand(1000, 9999);
                    //$code = '0000';
                    $message = "your 4 digit code " . $code;
                    $postData = array(
                        'email' => $emailid,
                        'first_name' => $firstname,
                        'last_name' => $lastname,
                        'password' => $password,
                        'img' => $filename,
                        'mobile' => $mobile_no,
                        'token' => $token,
                        'appPlatform' => $appPlatform,
                        'code' => $code,
                        'invite_code' => $res,
                        'invited_by' => '',
                        'credit_card_no' => $credit_card_no,
                        'cvv_no' => $cvv_no,
                        'paypal_email' => $paypal_email,
                        'paypal_password' => $paypal_password,
                        'card_valid_month' => $card_valid_month,
                        'card_valid_year' => $card_valid_year,
                    );
                    $save_data = $this->common->save('user', $postData);
                    if ($save_data) 
					{
                        $l_id = $save_data;
                        $personal_data = array(
                            'user_id' => $l_id,
                            'email' => $emailid,
                            'payment_method_type' => '2',
                            'report_status' => '1',
                            'type' => '1',
                            'status' => '1',
                            'created_at' => date('Y-m-d H:i:s'),
                        );
                        $save_personal_profile_data = $this->common->save('add_business_profile', $personal_data);
                        $data_all_id_data = $this->common->find('user', $select = '*', $where = array('id' => $l_id), $resultType = 'array', $orderby = array());
                        $data_all_id_data['password'] = '';
                        $data_all_id_data['join_date'] = date("F Y", strtotime($data_all_id_data['added_on']));
                        $url = "https://maestrosinfotech.com/arrive/uploads/users/";
                        if (!empty($data_all_id_data['img'])) 
						{
                            $data_all_id_data['image'] = $url . $data_all_id_data['img'];
                        } else 
						{
                            $data_all_id_data['image'] = $url . 'UserImage.jpg';
                        }
                        $code = rand(1000, 9999);
                        //$code = '0000';
                        $message = "your 4 digit code " . $code;
                        //$aa =  $this->send_sms_varify($mobile_no,$message);
                        $response['msg'] = 'successfully registration';
                        $response['details'] = $data_all_id_data;
                        $response['status'] = 'true';
                    } else 
					{
                        $response['msg'] = 'There is some issue in registration';
                        $response['status'] = 'false';
                    }
                }
            } else 
			{
                $response['msg'] = 'mobile number or email id already exits';
                $response['status'] = 'false';
            }
        } else 
		{
            $response['msg'] = 'Invalid Parameter';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    // function for login users
    public function driver_login() {
        $appplatform = $_REQUEST['appPlatform']; //ios,android
        $token = $_REQUEST['token'];
        $mobileno = $_REQUEST['mobileno'];
        $password = $_REQUEST['password'];
        // Input validations
        if (!empty($mobileno) && !empty($password)) {
            $data_all_driver = $this->common->find('driver', $select = '*', $where = array('mobile' => $mobileno, 'password' => $password), $resultType = 'array', $orderby = array());

            $data['count_num_rows'] = count($data_all_driver);
            if ($data['count_num_rows'] > 0) {
                $update_token = $this->common->update('driver', $postUpdate = array('token' => $token, 'appPlatform' => $appplatform), $where = array('mobile' => $mobileno));
                $data_all_driver['password'] = '';
                $data_all_driver['address'] = $data_all_driver['address1'] . ' ' . $data_all_driver['address2'];
                $url = "https://maestrosinfotech.com/arrive/uploads/drivers/";
                if (!empty($data_all_driver['img'])) {
                    $data_all_driver['image'] = $url . $data_all_driver['img'];
                } else {
                    $data_all_driver['image'] = $url . 'UserImage.jpg';
                }
                if (!empty($data_all_driver['insuarance_img'])) {
                    $data_all_driver['insuarance_img'] = $url . $data_all_driver['insuarance_img'];
                }
                if (!empty($data_all_driver['licence_img'])) {
                    $data_all_driver['licence_img'] = $url . $data_all_driver['licence_img'];
                }
                if (!empty($data_all_driver['adhar_img'])) {
                    $data_all_driver['adhar_img'] = $url . $data_all_driver['adhar_img'];
                }
                if (!empty($data_all_driver['vechile_img'])) {
                    $data_all_driver['vechile_img'] = $url . $data_all_driver['vechile_img'];
                }
                //print_r($data_all_driver);die();
                //get vechile info 

                $sql_data = $this->common->find('driver_vechile', '*', array('driverid' => $data_all_driver['id']), 'array');
                $sql_data1 = $this->common->findAll('driver_vechileimg', $select = 'img', $where = array('driverid' => $data_all_driver['id']));

                //echo $this->db->last_query();die();

                $img = array();
                foreach ($sql_data1 as $imgss) {
                    $url = "https://maestrosinfotech.com/arrive/uploads/vechile/";
                    if (!empty($imgss['img'])) {
                        $img[] = $url . $imgss['img'];
                    }
                }

                $vehicle_type_data = $this->common->find('vehicle_type_master', '*', array('id' => $sql_data['vechile_type']), 'array');
                $model_type_data = $this->common->find('vehicle_model', '*', array('id' => $sql_data['model']), 'array');
                $color_type_data = $this->common->find('vehicle_colormaster', '*', array('id' => $sql_data['color']), 'array');
                $vechile_subtype_type_data = $this->common->find('vehicle_subtype_master', '*', array('id' => $sql_data['vechile_subtype']), 'array');

                //$data_all_driver=$sql_data;
                $data_all_driver['vechile_type'] = $sql_data['vechile_type'];
                $data_all_driver['vechile_type_name'] = $vehicle_type_data['vehicle_type'];
                $data_all_driver['vechile_subtype'] = $sql_data['vechile_subtype'];
                $data_all_driver['vechile_subtype_name'] = $vechile_subtype_type_data['vehicle_model'];
                $data_all_driver['model'] = $sql_data['model'];
                $data_all_driver['model_name'] = $model_type_data['modelname'];

                $data_all_driver['color'] = $sql_data['color'];
                $data_all_driver['color_name'] = $color_type_data['color_name'];

                $data_all_driver['make'] = $sql_data['make'];
                $data_all_driver['year'] = $sql_data['year'];
                $data_all_driver['noofdoor'] = $sql_data['noofdoor'];
                $data_all_driver['noofsbelt'] = $sql_data['noofsbelt'];
                $data_all_driver['vehicle_images'] = $img;




                $response['msg'] = "Successfully Login";
                $response['details'] = $data_all_driver;
                $response['status'] = "true";
            } else {
                $response['msg'] = "Invalid Mobile Number or Password";
                $response['status'] = "false";
            }
        } else {
            $response['msg'] = "Invalid Parameter";
            $response['status'] = "false";
        }
        echo json_encode($response);
    }

    public function driverVerify_mobile() 
	{
        $mobileno = $_REQUEST['mobileno'];
        $check_mobile = $this->common->find('driver', $select = '*', $where = array('mobile' => $mobileno), $resultType = 'array', $orderby = array());
        if (!empty($check_mobile)) {
            $check_mobile['id'] = $check_mobile['id'];
            $response['message'] = "Mobile number exits.";
            $response['status'] = 'true';
            $response['details'] = $check_mobile;
        } else {
            $code = rand(1000, 9999);
            //$code = '0000';
            $message = "your 4 digit code " . $code;
            $aa = $this->send_sms_varify($mobileno, $message);
            $response['message'] = 'Mobile number not exits';
            $response['status'] = 'false';
            $response['otp'] = $code;
        }
        echo json_encode($response);
    }

    public function driverVerifyForgot_mobile() {
        $mobileno = $_REQUEST['mobileno'];
        $check_mobile = $this->common->find('driver', $select = '*', $where = array('mobile' => $mobileno), $resultType = 'array', $orderby = array());
        if (!empty($check_mobile)) {
            $check_mobile['id'] = $check_mobile['id'];
            $code = rand(1000, 9999);
            //$code = '0000';
            $message = "your 4 digit code " . $code;
            $aa = $this->send_sms_varify($mobileno, $message);
            $response['message'] = "Mobile number exits.";
            $response['status'] = 'true';
            $response['details'] = $check_mobile;
            $response['otp'] = $code;
        } else {
            $response['message'] = 'Mobile number not exits';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function driverChange_password() {
        $id = $_REQUEST['id'];
        $password = $_REQUEST['password'];
        if (!empty($id) && !empty($password)) {
            $select_pass = $this->common->update('driver', $postUpdate = array('password' => $password), $where = array('id' => $id));
            if ($select_pass) {
                $response['msg'] = 'Password updated';
                $response['status'] = 'true';
            } else {
                $response['msg'] = 'some error occured';
                $response['status'] = 'false';
            }
        } else {
            $response['msg'] = 'Invalid parameter';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function driverSignup() 
    {
        
        $refcode = $this->input->post('refcode');
        $firstname = $this->input->post('firstname');
        $lastname = $this->input->post('lastname');
        $emailid = $this->input->post('emailid');
        $mobile_no = $this->input->post('mobileno');
        $password = $this->input->post('password');
        $gender = $this->input->post('gender');
        $city = $this->input->post('city');
        $country = $this->input->post('country');
        $lat = $this->input->post('lat');
        $lng = $this->input->post('lng');
        $token = $this->input->post('token');
        $appPlatform = $this->input->post('appplatform');
        
        //$refcode = $_REQUEST['refcode'];
        /*$firstname = $_REQUEST['firstname'];
        $lastname = $_REQUEST['lastname'];
        $emailid = $_REQUEST['emailid'];
        $mobile_no = $_REQUEST['mobileno'];
        $password = $_REQUEST['password'];
        $gender = $_REQUEST['gender'];
        $city = $_REQUEST['city'];
        $country = $_REQUEST['country'];
        $lat = $_REQUEST['lat'];
        $lng = $_REQUEST['lng'];
        $token = $_REQUEST['token'];
        $appPlatform = $_REQUEST['appplatform'];*/
        $chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        $res = "";
        for ($i = 0; $i < 8; $i++) {
            $res .= $chars[mt_rand(0, strlen($chars) - 1)];
        }
        $driver_code = $res;
        if (!empty($firstname) && !empty($emailid) && !empty($mobile_no) && !empty($password) && !empty($city) && !empty($country)) 
        {
            $data_check = $this->common->find('driver', $select = '*', $where = array('mobile' => $mobile_no, 'email' => $emailid), $resultType = 'array', $orderby = array());
            if($data_check>0)
            {
            $data['num_rows_mobile'] =$data_check['mobile'];
            $data['num_rows_email'] = $data_check['email'];
            }else
            {
                $data['num_rows_mobile'] ="";
                $data['num_rows_email'] = "";
            }
            $filename = '';
            if ((!empty($_FILES['image']['name']))) 
			{
                $config['file_name'] = '_img_' . time();
                $config['upload_path'] = './uploads/drivers/';
                $config['allowed_types'] = '*';                                      // Allow all type of file
                $config['max_size'] = '100000';
                $config['max_width'] = '2000000';
                $config['max_height'] = '2000000';
                $config['remove_spaces'] = TRUE;
                $config['encrypt_name'] = TRUE;
                $this->load->library('upload', $config);
                $this->upload->initialize($config);
                if (($_FILES['image']['error']) == 0) 
                {
                    $this->upload->do_upload('image');
                    $fileData = array('upload_data' => $this->upload->data());
                    $filename = trim($fileData['upload_data']['file_name']);
                } else {
                    $error = array('error' => $this->upload->display_errors());
                    echo $error['error'];
                }
            }
            if ($data['num_rows_mobile'] == 0 && $data['num_rows_email'] == 0) {
                $postData = array(
                    'email' => $emailid,
                    'first_name' => $firstname,
                    /*'middle_name' => $middle_name,*/
                    'last_name' => $lastname,
                    'password' => $password,
                    'img' => $filename,
                    'mobile' => $mobile_no,
                    'token' => $token,
                    'appPlatform' => $appPlatform,
                    'referal_code' => $refcode,
                    'city' => $city,
                    /*'address2' => $address2,*/
                    'country' => $country,
                    'latitude' => $lat,
                    'longitude' => $lng,
                    'driver_id' => $driver_code,
                );
                $save_data = $this->common->save('driver', $postData);
                if ($save_data > 0) {
                    $lastid = $save_data;
                    $data_all_id_data = $this->common->find('driver', $select = '*', $where = array('id' => $lastid), $resultType = 'array', $orderby = array());
                    $data_all_id_data['password'] = '';
                    $url = "https://maestrosinfotech.com/arrive/uploads/drivers/";
                    if (!empty($data_all_id_data['img'])) {
                        $data_all_id_data['image'] = $url . $data_all_id_data['img'];
                    } else {
                        $data_all_id_data['image'] = $url . 'UserImage.jpg';
                    }
                    $response['msg'] = "successfully rgister";
                    $response['details'] = $data_all_id_data;
                    $response['status'] = "true";
                } else {
                    $response['msg'] = "There is some issue in registration";
                    $response['status'] = "false";
                }
            } else {
                $response['msg'] = "mobile number or email id already exits";
                $response['status'] = "false";
            }
        } else {
            $response['msg'] = "Invalid Parameter";
            $response['status'] = "false";
        }
        echo json_encode($response);
    }

   

    public function getVehiclesubtype() {
        $this->form_validation->set_rules("typeid", "Type Id", 'required|xss_clean');
        $postData = $this->input->post();
        if ($this->form_validation->run()) {
            $getcolorres = $this->common->findAll('vehicle_subtype_master', $select = '*', $where = array('active_flag' => 'yes', 'vehicle_type_id' => $postData['typeid']));
            $response['detail'] = $getcolorres;
            $response['status'] = 'true';
        } else {
            $response['msg'] = 'Invalid Parameter';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function getVehicletype() 
	{
        $getVehicletype = $this->common->findAll('vehicle_type_master', $select = '*', $where = array('active_flag' => 'yes'));
        if (!empty($getVehicletype)) {
            $response['detail'] = $getVehicletype;
           // $response['detail']['inserttime_mysql']=date("Y-m-d H:i:s",strtotime($getVehicletype['inserttime_mysql']));
            $response['msg'] = "successfull";
            $response['status'] = 'true';
        } else {
            $response['msg'] = "no data";
            $response['status'] = 'true';
        }
        echo json_encode($response);
    }

    public function getColor() {
        $getcolorres = $this->common->findAll('vehicle_colormaster', $select = '*');
        if (!empty($getcolorres)) {
            $response['detail'] = $getcolorres;
            $response['msg'] = "successfull";
            $response['status'] = 'true';
        } else {
            $response['msg'] = "no data";
            $response['status'] = 'true';
        }
        echo json_encode($response);
    }

    public function getModal() {
        $getmodal = $this->common->findAll('vehicle_model', $select = '*');
        if (!empty($getmodal)) {
            $response['detail'] = $getmodal;
            $response['msg'] = "successfull";
            $response['status'] = 'true';
        } else {
            $response['msg'] = "no data";
            $response['status'] = 'true';
        }
        echo json_encode($response);
    }

    public function updateDriverVechileinfo() 
    {
        $driverid = $_REQUEST['driverid'];
        $vechile_type = $_REQUEST['vechile_type'];
        $vechile_subtype = $_REQUEST['vechile_subtype'];
        $model = $_REQUEST['modelid'];
        $color = $_REQUEST['colorid'];
        $make = $_REQUEST['make'];
        $year = $_REQUEST['year'];
        $noofdoor = $_REQUEST['noofdoor'];
        $noofsbelt = $_REQUEST['noofsbelt'];
        if (!empty($driverid) && !empty($model) && !empty($color) && !empty($make) && !empty($noofdoor) && !empty($noofsbelt) && !empty($vechile_type) && !empty($vechile_subtype) && !empty($year)) {
            if (!empty($_FILES['image']['name'])) {
                $filesCount = count($_FILES['image']['name']);
                for ($i = 0; $i < $filesCount; $i++) {
                    $_FILES['file']['name'] = $_FILES['image']['name'][$i];
                    $_FILES['file']['type'] = $_FILES['image']['type'][$i];
                    $_FILES['file']['tmp_name'] = $_FILES['image']['tmp_name'][$i];
                    $_FILES['file']['error'] = $_FILES['image']['error'][$i];
                    $_FILES['file']['size'] = $_FILES['image']['size'][$i];
                    // File upload configuration
                    $config['upload_path'] = "./uploads/vechile/";
                    $config['allowed_types'] = 'jpg|jpeg|png|gif';
                    // Load and initialize upload library
                    $this->load->library('upload', $config);
                    $this->upload->initialize($config);
                    // Upload file to server
                    if ($this->upload->do_upload('file')) {
                        // Uploaded file data
                        $fileData = $this->upload->data();
                        $filename[$i]['file_name'] = $fileData['file_name'];
                        $filename[$i]['uploaded_on'] = date("Y-m-d H:i:s");
                    }
                }
                if (!empty($filename)) {
                    $this->common->delete('driver_vechileimg', $where = array('driverid' => $driverid));
                    foreach ($filename as $vals_img) {
                        $img_array = array('img' => $vals_img['file_name'], 'driverid' => $driverid);
                        $this->common->save('driver_vechileimg', $img_array);
                    }
                }
            }
            $checkexits = $this->common->find('driver_vechile', 'id', array('driverid' => $driverid), 'array');
            $checkexits_count = count($checkexits);
            if ($checkexits_count > 0) {
                $driver_update = array(
                    'vechile_type' => $vechile_type,
                    'vechile_subtype' => $vechile_subtype,
                    'model' => $model,
                    'color' => $color,
                    'make' => $make,
                    'year' => $year,
                    'noofdoor' => $noofdoor,
                    'noofsbelt' => $noofsbelt
                );
                $update = $this->common->update('driver_vechile', $driver_update, $where = array('driverid' => $driverid));
                if (!empty($update)) 
                {
                    $sql_data = $this->common->find('driver_vechile', '*', array('driverid' => $driverid), 'array');
                    $result = $sql_data;
                    $sql_data = $this->common->findAll('driver_vechileimg', $select = 'img', $where = array('driverid' => $driverid));
                    $img = array();
                    foreach ($sql_data as $imgss) 
                    {
                        $url = "https://maestrosinfotech.com/arrive/uploads/vechile/";
                        if (!empty($imgss['img'])) 
                        {
                            $img[] = $url . $imgss['img'];
                        }
                    }

                    $vehicle_type_data = $this->common->find('vehicle_type_master', '*', array('id' => $vechile_type), 'array');
                    $model_type_data = $this->common->find('vehicle_model', '*', array('id' => $model), 'array');
                    $color_type_data = $this->common->find('vehicle_colormaster', '*', array('id' => $color), 'array');
                    $vechile_subtype_type_data = $this->common->find('vehicle_subtype_master', '*', array('id' => $vechile_subtype), 'array');

                    $data_all_driver=$sql_data;
                   $result['vechile_type_name'] = $vehicle_type_data['vehicle_type'];
                   $result['vechile_subtype_name'] = $vechile_subtype_type_data['vehicle_model'];
                   $result['model_name'] = $model_type_data['modelname'];

                    $result['color_name'] = $color_type_data['color_name'];
                    $result['vehicle_images'] = $img;
                    $response['result'] = $result;
                    $response['msg'] = "successfully saved";
                    $response['status'] = 'true';
                } else {
                    $response['msg'] = "Some error occured1";
                    $response['status'] = 'false';
                }
            } else {
                $driver_insert = array(
                    'vechile_type' => $vechile_type,
                    'vechile_subtype' => $vechile_subtype,
                    'model' => $model,
                    'color' => $color,
                    'make' => $make,
                    'year' => $year,
                    'noofdoor' => $noofdoor,
                    'noofsbelt' => $noofsbelt,
                    'driverid' => $driverid,
                );
                $insert = $this->common->save('driver_vechile', $driver_insert);
                if (!empty($insert)) {
                    $sql_data = $this->common->find('driver_vechile', '*', array('driverid' => $driverid), 'array');
                    $result = $sql_data;
                    $sql_data = $this->common->findAll('driver_vechileimg', $select = 'img', $where = array('driverid' => $driverid));
                    $img = array();
                    foreach ($sql_data as $imgss) {
                        $url = "https://maestrosinfotech.com/arrive/uploads/vechile/";
                        if (!empty($imgss['img'])) {
                            $img[] = $url . $imgss['img'];
                        }
                    }
                    $vehicle_type_data = $this->common->find('vehicle_type_master', '*', array('id' => $vechile_type), 'array');
                    $model_type_data = $this->common->find('vehicle_model', '*', array('id' => $model), 'array');
                    $color_type_data = $this->common->find('vehicle_colormaster', '*', array('id' => $color), 'array');
                    $vechile_subtype_type_data = $this->common->find('vehicle_subtype_master', '*', array('id' => $vechile_subtype), 'array');

                    //$data_all_driver=$sql_data;
                    $result['vechile_type_name'] = $vehicle_type_data['vehicle_type'];
                    $result['vechile_subtype_name'] = $vechile_subtype_type_data['vehicle_model'];
                    $result['model_name'] = $model_type_data['modelname'];

                    $result['color_name'] = $color_type_data['color_name'];
                    $result['vehicle_images'] = $img;
                    $response['msg'] = "successfully saved";
                    $response['result'] = $result;
                    $response['status'] = 'true';
                } else {
                    $response['msg'] = "Some error occured2";
                    $response['status'] = 'false';
                }
            }
        } else {
            $response['msg'] = "Invalid parameter";
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function getVehcledetails() 
    {
        $details = array();
        $getdetails = $this->common->findAll('vehicle_type_master', $select = 'id,vehicle_type', $where = array('active_flag' => 'yes'), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
        if (!empty($getdetails)) {
            foreach ($getdetails as $getvehicledetails) {
                $typeid = $getvehicledetails['id'];
                $getvechileres = $this->common->findAll('vehicle_subtype_master', $select = 'id,vehicle_model', $where = array('active_flag' => 'yes', 'vehicle_type_id' => $typeid), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
                foreach ($getvechileres as $getvechile) {
                    $url = "https://maestrosinfotech.com/arrive";
                    $getvechile['selectimg'] = $url . '/uploads/select1.png';
                    $getvechile['nonselectimg'] = $url . '/uploads/nonSelect1.png';
                    $vechiledetal[] = $getvechile;
                }
                $getvehicledetailsArr = array(
                    'vehicleTypeId' => $getvehicledetails['id'],
                    'vehicleTypeName' => $getvehicledetails['vehicle_type'],
                    'vehicleType' => $vechiledetal
                );
                $details[] = $getvehicledetailsArr;
                $vechiledetal = '';
            }
            $response['msg'] = "successfully saved";
            $response['details'] = $details;
            $response['status'] = 'true';
        } else {
            $response['msg'] = "no data Found";
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function locationUpdate() 
    {
        $this->form_validation->set_rules("lat", "Lat", 'required|xss_clean');
        $this->form_validation->set_rules("lng", "Lng", 'required|xss_clean');
        $this->form_validation->set_rules("type", "Type", 'required|xss_clean');
        $this->form_validation->set_rules("id", "Id", 'required|xss_clean');
        $postData = $this->input->post();
        if ($this->form_validation->run()) {
            if ($postData['type'] == 'driver') {
                $update_data = array(
                    'latitude' => $postData['lat'],
                    'longitude' => $postData['lng'],
                );
                $update = $this->common->update('driver', $update_data, $where = array('id' => $postData['id']));
            } else {
                $update_data = array(
                    'latitude' => $postData['lat'],
                    'longitude' => $postData['lng'],
                );
                $update = $this->common->update('user', $update_data, $where = array('id' => $postData['id']));
            }
            $rating = $this->common->avg_rate($postData['id']);
            if ($update) {
                $response['status'] = 'true';
                $response['msg'] = 'Location Updated';
                $response['avg_rating'] = round($rating[0]['Averagerating']);
            } else {
                $response['status'] = 'false';
                $response['msg'] = 'location not updated some error occured';
            }
        } else {
            $response['status'] = 'false';
            $response['msg'] = 'Invalid Parameter';
        }
        echo json_encode($response);
    }

    public function contactList() {
        $contactInfo = $_POST['contact_info'];
        $contactData = json_decode($contactInfo);
        $contactDb = $this->common->findAll('user', $select = 'mobile');
        $contactLIst = array();
        foreach ($contactDb as $key => $value) {
            $contactLIst[] = $value['mobile'];
        }
        $contactNumber = array();
        foreach ($contactData as $key => $value) {
            $contact = $value->contactNumber;
            $aa = in_array($contact, $contactLIst);
            if ($aa == 1) {
                $contactData = $this->common->find('user', $select = '*', $where = array('mobile' => $contact), $resultType = 'array', $orderby = array());
                $contactNumber[] = $contactData;
            }
        }
        if (!empty($contactNumber)) {
            $response['contact_list'] = ($contactNumber) ? $contactNumber : '';
            $response['status'] = 'true';
            $response['msg'] = 'contact list fetch successfully.';
        } else {
            $response['contact_list'] = array();
            $response['status'] = 'false';
            $response['msg'] = 'no contact exist';
        }
        echo json_encode($response);
    }

    public function getVehicleYear() 
	{
       // $getVehicleYear = $this->common->findAll('makeModel', $select = 'DISTINCT(year)');
	   $getVehicleYear =array();
	   for($i=1990;$i<2025;$i++)
	   {
		   $jitu['year']=$i;
		   array_push($getVehicleYear,$jitu);
	   }
        if (!empty($getVehicleYear)) 
		{
            $response['detail'] = $getVehicleYear;
            $response['msg'] = "successful";
            $response['status'] = 'true';
        } else {
            $response['msg'] = "no data";
            $response['status'] = 'true';
        }
        echo json_encode($response);
    }
	
    public function getVehicleModel() {
        $this->form_validation->set_rules("makeid", "Make Id", 'required|xss_clean');
        //$this->form_validation->set_rules("yearid", "Year Id", 'required|xss_clean');
        $postData = $this->input->post();
        if ($this->form_validation->run()) 
		{
          //  $getcolorres = $this->common->findAll('makeModel', $select = 'id,model', $where = array('make' => $postData['makeid'], 'year' => $postData['yearid']));
		   $getcolorres = $this->common->findAll('vehicle_model', $select = 'id,modelname', $where = array('make_id' => $postData['makeid']));
			if( $getcolorres)
			{
				$response['detail'] = $getcolorres;
				$response['status'] = 'true';
			}else
			{
				$response['detail'] = $getcolorres;
				$response['status'] = 'false';
			}
        } else 
		{
            $response['msg'] = 'Invalid Parameter';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function getVehicleMake() 
	{
        //$this->form_validation->set_rules("yearid", "Year Id", 'required|xss_clean');
       // $postData = $this->input->post();
        //if ($this->form_validation->run()) 
		//{
            //$getcolorres = $this->common->findAll('makeModel', $select = 'DISTINCT(make)', $where = array('year' => $postData['yearid']));
			$getcolorres = $this->common->findAll('makeModel', $select = 'make,id');
			if($getcolorres)
			{
				$response['detail'] = $getcolorres;
				$response['status'] = 'true';
			}else
			{
				$response['detail'] = $getcolorres;
				$response['status'] = 'false';
			}
        //} else {
         //   $response['msg'] = 'Invalid Parameter';
          //  $response['status'] = 'false';
        //}
        echo json_encode($response);
    }

    public function getDriverRating() {
        $this->form_validation->set_rules("driver_id", "Id", 'required');
        $postData = $this->input->post();
        if ($this->form_validation->run()) {

            $result = [];
            $get_driver_data = $this->common->findAll('driver', $select = '*', $where = array('id' => $postData['driver_id']));
            if (!empty($get_driver_data[0]['added_on'])) {


                $effectiveDate = $get_driver_data[0]['added_on'];
                $date_d_m_y = date('Y-m-d', strtotime($effectiveDate));
                $date = date('Y-m-d', strtotime("+2 months", strtotime($effectiveDate)));
                $date_for_4months = date('Y-m-d', strtotime("+4 months", strtotime($effectiveDate)));
                $date_for_6months = date('Y-m-d', strtotime("+6 months", strtotime($effectiveDate)));
                $date_for_12months = date('Y-m-d', strtotime("+12 months", strtotime($effectiveDate)));



                $add_total_week = 0;
                $week_data = $this->common->getTotalTripByWeek($postData['driver_id'], $date_d_m_y, $date_for_6months);
                foreach ($week_data as $week_vals) {

                    $add_days = $week_vals['schedule_date'];
                    $newDate = date("Y-m-d", strtotime($add_days . "+7 day"));
                    $every_week_data = $this->common->getTotalTrip($postData['driver_id'], $date_d_m_y, $newDate);
                    $total_week_trip = $every_week_data[0]['count_trip'];
                    $add_total_week += $total_week_trip;
                }

                $add_total_six_week = 0;
                $sixweek_data = $this->common->getTotalTripByWeek($postData['driver_id'], $date_d_m_y, $date_for_12months);
                foreach ($sixweek_data as $sixweek_vals) {

                    $six_add_days = $sixweek_vals['schedule_date'];
                    $six_newDate = date("Y-m-d", strtotime($six_add_days . "+7 day"));
                    $every_week_data = $this->common->getTotalTrip($postData['driver_id'], $date_d_m_y, $six_newDate);
                    $total_week6_trip = $every_week_data[0]['count_trip'];
                    $add_total_six_week += $total_week6_trip;
                }


                $get_driver_booking_data = $this->common->getTotalTrip($postData['driver_id'], $date_d_m_y, $date);
                $get_driver_booking__for4_data = $this->common->getTotalTrip($postData['driver_id'], $date_d_m_y, $date_for_4months);
                $get_driver_booking__for6_data = $this->common->getTotalTrip($postData['driver_id'], $date_d_m_y, $date_for_6months);
                if (!empty($get_driver_booking_data[0]['count_trip'])) {

                    $total_trip = $get_driver_booking_data[0]['count_trip'];
                }
                if (!empty($get_driver_booking__for4_data[0]['count_trip'])) {

                    $total_trip_4month = $get_driver_booking_data[0]['count_trip'];
                }
                if (!empty($get_driver_booking__for6_data[0]['count_trip'])) {

                    $total_trip_6month = $get_driver_booking__for6_data[0]['count_trip'];
                }
                $get_all_data = $this->common->getAllDriverTrip($postData['driver_id']);


                $average = 0;
                foreach ($get_all_data as $vals) {

                    if (!empty($vals['id'])) {
                        $count = $vals['t_count'];
                        $get_avg = $this->common->getAvgRate($vals['id']);

                        $average += $get_avg->avg_rate;
                    }

                    if (($average > 4.7) && ($total_trip >= 200)) {

                        $result['rating'] = "Associate";
                    }
                    if (($average > 4.7) && ($total_trip_4month >= 500)) {
                        $result['rating'] = "Senior associate";
                    }
                    if (($average > 4.7) && ($total_week_trip >= 50)) {
                        $result['rating'] = "Bronze\Silver associate";
                    }
                    if (($average > 4.7) && ($total_week6_trip >= 50)) {
                        $result['rating'] = "Gold associate";
                    } else {
                        $result['rating'] = "Associate";
                    }
                }
            }


            $response['detail'] = $result;
            $response['status'] = 'true';
        } else {
            $response['msg'] = 'Invalid Parameter';
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }

    public function splitFareContactList() {

        $contactInfo = $_POST['contact_info'];
        $contactData = json_decode($contactInfo);

        $contactDb = $this->common->findAll('user', $select = 'mobile');

        $contactLIst = array();
        foreach ($contactDb as $key => $value) {

            $contactLIst[] = $value['mobile'];
        }

        $contactNumber = array();
        foreach ($contactData as $key => $value) {

            $contact = $value->contactNumber;
            $aa = in_array($contact, $contactLIst);
            if ($aa == 1) {
                $contactData = $this->common->find('user', $select = '*', $where = array('mobile' => $contact), $resultType = 'array', $orderby = array());
                $contactNumber[] = $contactData;
            }
        }
        if (!empty($contactNumber)) {
            $response['contact_list'] = ($contactNumber) ? $contactNumber : '';
            $response['status'] = 'true';
            $response['msg'] = 'contact list fetch successfully.';
        } else {
            $response['contact_list'] = array();
            $response['status'] = 'false';
            $response['msg'] = 'no contact exist';
        }
        echo json_encode($response);
    }

    public function splitFare() {
        $mob = $_POST['mobile'];
        $booking_id = $_POST['booking_id'];


        $this->form_validation->set_rules('booking_id', 'Booking ID', 'required');
        $this->form_validation->set_rules('mobile', 'Mobile', 'required');
        $details = [];
        if ((!empty($booking_id)) && (!empty($mob))) {
            $mobile = explode(',', $mob);
            // $userData = $this->common->find('booking', $select = 'mode, amount', $where = array('user_id' => $user_id, 'schedule_date' => $date, 'mode' => '1', 'schedule_time' => $schedule_time), $resultType = 'array', $orderby = array());
            $userData = $this->common->find('booking', $select = 'user_id,mode, amount', $where = array('id' => $booking_id), $resultType = 'array', $orderby = array());

            if ($userData['amount'] > 0) {
                $amt = $userData['amount'];
                $user = $userData['user_id'];
            }



            foreach ($mobile as $mb_vals) {



                $userData = $this->common->find('user', $select = 'id,mobile', $where = array('mobile' => $mb_vals), $resultType = 'array', $orderby = array());
                $details[] = $userData;

                if (!empty($userData)) {
                    $tot_count = count($details) + 1;


                    // $response['result'] =$details;
                }
            }
            $tot = $tot_count;
            $split = $amt / $tot;
            $split_decimal = number_format($split, 2);

            if (!empty($details)) {
                foreach ($details as $details_vals) {
                    if (!empty($details_vals)) {
                        $post = array('total_amount' => $amt, 'split_amount' => $split_decimal,
                            'share_split_user_id' => $user,
                            'total_split_users' => $tot,
                            'user_id' => $details_vals['id'],
                            'number' => $details_vals['mobile']
                        );

                       // $split_add = $this->common->save('split_fare_deatils', $post);
                        $userData_id = $this->common->find('user', $select = 'first_name,last_name,id, token, appPlatform', $where = array('id' => $details_vals['id']), $resultType = 'array', $orderby = array());
                        $name=$userData_id['first_name'].' '.$userData_id['last_name'];
                        $token = $userData_id['token'];
                        $for = $userData_id['appPlatform'];
                        $push_message = 'Split Fare';
                        $push_array = array(
                            "name"=>$name,                          
                            "bookingId"=>$booking_id,
                            "alert" => $push_message,
                            "sound" => 'default',
                            "content-available" => 1,
                            "amount" => $split_decimal,
                            "push_tag" => "Split Fare"
                        );
                      
                        $this->common->send_user_push_fcm($token, $push_array, $for, $PEM_MODE = '2');
                    }
                }
            }
            
            $response['split_fair'] = $split_decimal;
            $response['message'] = 'Split Fare';
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
    }
    
    
    
    public function saveSplitFare()
    {
        
        $booking_id=$this->input->post('booking_id');
        $user_id=$this->input->post('user_id');
        $type=$this->input->post('type');
       
        $split_amount=$this->input->post('split_amount');
           
        $this->form_validation->set_rules('booking_id', 'Booking ID', 'required');
        $this->form_validation->set_rules('user_id', 'User ID', 'required');
        $this->form_validation->set_rules('type', 'Type', 'required');
        $this->form_validation->set_rules('split_amount', 'Split Amount', 'required');
        
       if ($this->form_validation->run()) {
         $userData= $this->common->find('booking', $select = 'user_id,amount', $where = array('id' => $booking_id), $resultType = 'array', $orderby = array());
         if(!empty($userData))
         {
         $amount=$userData['amount'];
         $booking_user_id=$userData['user_id'];
         }
         else
         { $booking_user_id=$userData['user_id'];
            $amount=0;  
         }
         if(($type=="reject")||($type=="Reject"))
         {
             
             $pay_booking_users_amount=$split_amount+$split_amount;
         }
         else
         {
             $pay_booking_users_amount=$split_amount;
             
             
         }
         
        $post=array("booking_id"=>$booking_id,"user_id"=>$booking_user_id,"type"=>$type,"total_amount"=>$amount,"share_split_user_id"=>$user_id,"pay_booking_users_amount"=>$pay_booking_users_amount,"split_amount"=>$split_amount);
         $split_fare_data = $this->common->find('split_fare_deatils', $select = '*', $where = array('user_id' => $booking_user_id,'booking_id'=>$booking_id), $resultType = 'array', $orderby = array());
            if(!empty($split_fare_data))
            {
                $this->common->update('split_fare_deatils',$post,array('user_id' => $booking_user_id,'booking_id'=>$booking_id));

            }
            else
            {
                 $split_add = $this->common->save('split_fare_deatils', $post);
            }
        
       
        
          $userData_id = $this->common->find('user', $select = 'id, token, appPlatform', $where = array('id' => $booking_user_id), $resultType = 'array', $orderby = array());
                        
                        $token = $userData_id['token'];
                        $for = $userData_id['appPlatform'];
                        if($type=='accept')
                        {
                            $push_message = 'Your friend has accepted your fare split request';  
                        }
                        else
                        {
                              $push_message = 'Your friend has rejected your fare split request';  
                        }
                      
                        $push_array = array(
                            "type"=>$type,
                            "alert" => $push_message,
                            "sound" => 'default',
                            "content-available" => 1,
                            "push_tag" => "accept_reject"
                        );
        
        $this->common->send_user_push_fcm($token, $push_array, $for, $PEM_MODE = '2');
      
            $response['message'] = 'Add successfully';
            $response['status'] = 'true';
        } else {
            $response['message'] = validation_errors();
            $response['status'] = 'false';
        }
        echo json_encode($response);
        
        
    }
    
     public function updateDriverinfo() {
       $this->form_validation->set_rules('driverid', 'driver Id', 'required');
         if ($this->form_validation->run()) 
             {
              $driverData = $this->common->find('driver', $select = '*', $where = array('id' => $this->input->post('driverid')), $resultType = 'array', $orderby = array());
              
              if (!empty($_REQUEST['dob'])) {
                $dob = date('Y-m-d', strtotime($_REQUEST['dob']));
            }
            else
            {
                 $dob=$driverData['dob'];
            }
            if (!empty($_REQUEST['driving_licence'])) {
                $driving_licence = $_REQUEST['driving_licence'];
            }
            else
            {
                 $driving_licence = $driverData['driving_licence'];
            }
            if (!empty($_REQUEST['expiredate'])) {
                $expiredate = date('Y-m-d', strtotime($_REQUEST['expiredate']));
            }
            else
            {
                 $expiredate=$driverData['expiredate'];
            }
            if (!empty($_REQUEST['address1'])) {
                $address1 = $_REQUEST['address1'];
            }
            else
            {
               $address1 =$driverData['address1']; 
            }
            if (!empty($_REQUEST['address2'])) {
                $address2 = $_REQUEST['address2'];
            }
             else
            {
               $address2 =$driverData['address2']; 
            }
            if (!empty($_REQUEST['zipcode'])) {
                $zipcode = $_REQUEST['zipcode'];
            }
             else
            {
               $zipcode =$driverData['zipcode']; 
            }
            if (!empty($_REQUEST['social_secrityno'])) {
                $social_secrityno = $_REQUEST['social_secrityno'];
            }
             else
            {
               $social_secrityno =$driverData['social_secrityno']; 
            }
            if (!empty($_REQUEST['licence_plate'])) {
                $licence_plate = $_REQUEST['licence_plate'];
            }
              else
            {
               $licence_plate =$driverData['licence_plate']; 
            }
            if (!empty($_REQUEST['vechile_reg'])) {
                $vechile_reg = $_REQUEST['vechile_reg'];
            }
               else
            {
               $vechile_reg =$driverData['vechile_reg']; 
            }
            if (!empty($_REQUEST['state'])) {
                $state = $_REQUEST['state'];
            }
               else
            {
               $state =$driverData['state']; 
            }
            if (!empty($_REQUEST['middle_name'])) {
                $middle_name = $_REQUEST['middle_name'];
            }
               else
            {
               $middle_name =$driverData['middle_name']; 
            }
             if ((!empty($_FILES['insuarance_img']['name']))) {
                $config['file_name'] = '_img_' . time();
                $config['upload_path'] = './uploads/drivers/';
                $config['allowed_types'] = '*';                                      // Allow all type of file
                $config['max_size'] = '100000';
                $config['max_width'] = '2000000';
                $config['max_height'] = '2000000';
                $config['remove_spaces'] = TRUE;
                $config['encrypt_name'] = TRUE;
                $this->load->library('upload', $config);
                $this->upload->initialize($config);
                if (($_FILES['insuarance_img']['error']) == 0) {
                    $this->upload->do_upload('insuarance_img');
                    $fileData = array('upload_data' => $this->upload->data());
                    $filename_insuarance_img = trim($fileData['upload_data']['file_name']);
                    $field = 1;
                } else {
                    $error = array('error' => $this->upload->display_errors());
                    echo $error['error'];
                }
            }
             else
            {
                 $filename_insuarance_img =$driverData['insuarance_img'];
            }
            if ((!empty($_FILES['licence_img']['name']))) {
                $config['file_name'] = '_img_' . time();
                $config['upload_path'] = './uploads/drivers/';
                $config['allowed_types'] = '*';                                      // Allow all type of file
                $config['max_size'] = '100000';
                $config['max_width'] = '2000000';
                $config['max_height'] = '2000000';
                $config['remove_spaces'] = TRUE;
                $config['encrypt_name'] = TRUE;
                $this->load->library('upload', $config);
                $this->upload->initialize($config);
                if (($_FILES['licence_img']['error']) == 0) {
                    $this->upload->do_upload('licence_img');
                    $fileData = array('upload_data' => $this->upload->data());
                    $filename_licence_img = trim($fileData['upload_data']['file_name']);
                    $field = 1;
                } else {
                    $error = array('error' => $this->upload->display_errors());
                    echo $error['error'];
                }
            }
             else
            {
                 $filename_licence_img =$driverData['licence_img'];
            }

            if ((!empty($_FILES['adhar_img']['name']))) {
                $config['file_name'] = '_img_' . time();
                $config['upload_path'] = './uploads/drivers/';
                $config['allowed_types'] = '*';                                      // Allow all type of file
                $config['max_size'] = '100000';
                $config['max_width'] = '2000000';
                $config['max_height'] = '2000000';
                $config['remove_spaces'] = TRUE;
                $config['encrypt_name'] = TRUE;
                $this->load->library('upload', $config);
                $this->upload->initialize($config);
                if (($_FILES['adhar_img']['error']) == 0) {
                    $this->upload->do_upload('adhar_img');
                    $fileData = array('upload_data' => $this->upload->data());
                    $filename_adhar_img = trim($fileData['upload_data']['file_name']);
                    $field = 1;
                } else {
                    $error = array('error' => $this->upload->display_errors());
                    echo $error['error'];
                }
            }
             else
            {
                 $filename_adhar_img =$driverData['adhar_img'];
            }

            if ((!empty($_FILES['vechile_img']['name']))) {
                $config['file_name'] = '_img_' . time();
                $config['upload_path'] = './uploads/drivers/';
                $config['allowed_types'] = '*';                                      // Allow all type of file
                $config['max_size'] = '100000';
                $config['max_width'] = '2000000';
                $config['max_height'] = '2000000';
                $config['remove_spaces'] = TRUE;
                $config['encrypt_name'] = TRUE;
                $this->load->library('upload', $config);
                $this->upload->initialize($config);
                if (($_FILES['vechile_img']['error']) == 0) {
                    $this->upload->do_upload('vechile_img');
                    $fileData = array('upload_data' => $this->upload->data());
                    $filename_vechile_img = trim($fileData['upload_data']['file_name']);
                    $field = 1;
                } else {
                    $error = array('error' => $this->upload->display_errors());
                    echo $error['error'];
                }
            }
            else
            {
                 $filename_vechile_img =$driverData['vechile_img'];
            }
            
            $postUpdate = array(
                'dob' => $dob,
                'driving_licence' => $driving_licence,
                'expiredate' => $expiredate,
                'address1' => $address1,
                'address2' => $address2,
                'zipcode' => $zipcode,
                'social_secrityno' => $social_secrityno,
                'licence_plate' => $licence_plate,
                'vechile_reg' => $vechile_reg,
                'state' => $state,
                'middle_name' => $middle_name,
                'insuarance_img' => $filename_insuarance_img,
                'licence_img' => $filename_licence_img,
                'adhar_img' => $filename_adhar_img,
                'vechile_img' => $filename_vechile_img,
            );
            
          
            
             if (!empty($postUpdate)) {
                $select_pass = $this->common->update('driver', $postUpdate, $where = array('id' => $this->input->post('driverid')));
                if ($select_pass) {
                    $result = $this->common->find('driver', $select = '*', $where = array('id' => $this->input->post('driverid')), $resultType = 'array', $orderby = array());

                    $result['password'] = '';
                    $result['address'] = $result['address1'] . ' ' . $result['address2'];
                    $url = "https://maestrosinfotech.com/arrive/uploads/drivers/";
                    if (!empty($result['img'])) {
                        $result['image'] = $url . $result['img'];
                    } else {
                        $result['image'] = $url . 'UserImage.jpg';
                    }
                    if (!empty($result['insuarance_img'])) {
                        $result['insuarance_img'] = $url . $result['insuarance_img'];
                    }
                    if (!empty($result['licence_img'])) {
                        $result['licence_img'] = $url . $result['licence_img'];
                    }
                    if (!empty($result['adhar_img'])) {
                        $result['adhar_img'] = $url . $result['adhar_img'];
                    }
                    if (!empty($result['vechile_img'])) {
                        $result['vechile_img'] = $url . $result['vechile_img'];
                    }
                    $response['msg'] = "Updated successfully";
                    $response['result'] = $result;
                    $response['status'] = "true";
                } else {
                    $response['msg'] = "Some error occured";
                    $response['status'] = "false";
                }
            } else {
                $response['msg'] = "Please specify fields";
                $response['status'] = "false";
            }
             }
             else
             {
                  $response['msg'] = validation_errors();
            $response['status'] = "false";
         }
        echo json_encode($response);
    }

}

?>
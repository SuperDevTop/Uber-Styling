<?php
defined('BASEPATH') OR exit('No direct script access allowed');
error_reporting(E_ALL);

class Stripe extends CI_Controller {

    public function __construct() {
        parent::__construct();
        //include Stripe PHP library
        require_once APPPATH."third_party/stripe/init.php";
        //set api key
       /*$stripe = array(
          "secret_key"      => "sk_live_bahHaOprTcXVoNWZqgpukBST",
          "publishable_key" => "pk_live_Kj3jfNSfhdRNcqHwaEHT9GD4"
        );*/
		$stripe = array(
          "secret_key"      => "sk_test_Q7yv7oqNY0oyjpxUz0dxOLPJ",
          "publishable_key" => "pk_test_ihH0ZJS1mPxvcs2AdI2oIddA"
        );
		
		


        \Stripe\Stripe::setApiKey($stripe['secret_key']);
       // $this->load->libraries('stripe');    
    }


    public function withoutCardSavePayment()
    {
       
        try {
            //get token, card and user info from the form
            $name = $_POST['name'];
            $email = $_POST['email'];
            $card_num = $_POST['card_num'];
            $card_cvc = $_POST['cvc'];
            $card_exp_month = $_POST['exp_month'];
            $card_exp_year = $_POST['exp_year'];
            $amount = $_POST['amount'];
            $userId = $_REQUEST['user_id'];
            $booking_id=$_POST['booking_id']

            //add token
            $token= \Stripe\Token::create(array(
                    "card" => array(
                    "number" => $card_num,
                    "exp_month" =>$card_exp_month,
                    "exp_year" => $card_exp_year,
                    "cvc" => $card_cvc
                )
            ));
            //print_r($token);die();

            $charge = \Stripe\Charge::create(array(
                "amount" =>ceil($amount*100),
                "currency" => "usd",
                "source" => $token['id'], // obtained with Stripe.js
                "description" =>  'Payment for User Id ' . $userId
            ));
            //print_r($charge);die();
            //retrieve charge details
            $finalResponse = $charge->jsonSerialize();
            $chargeId = $finalResponse['id'];

            if ($finalResponse['status'] == 'succeeded') {
                $transactionCode = $this->common->generateJobCode();
                $postArr = array(
                    'transaction_id'=>$transactionCode,
                    'bookinng_id'=>$booking_id,
                    'user_id'=>$userId,
                    'customer_id'=>'',
                    'card_id'=>'',
                    'stripe_charge_id'=>$chargeId,
                    'amount'=>$amount,
                    'added_on'=>date('Y-m-d H:i:s')
                );
                $insert_id = $this->common->save('transaction',$postArr);
                $response['tran_id'] = $insert_id;
                $response['result'] = true;
                $response['msg'] = 'Payment made successfully.';
            } else {
                $response['result'] = false;
            }
            echo json_encode($response);
            exit();
        }catch(\Stripe\Error\Card $e) {
                       // echo '2';die();

            // Since it's a decline, \Stripe\Error\Card will be caught
          $body = $e->getJsonBody();
          $err  = $body['error'];
          $response['result'] = false;
          $response['type'] =  $err['type'];
           $response['code'] =  $err['code'];
          $response['msg'] = $err['message'];
          echo json_encode($response);
          
        } catch (\Stripe\Error\RateLimit $e) {
             echo '3';die();
           $body = $e->getJsonBody();
          $err  = $body['error'];
          $response['result'] = false;
          $response['type'] =  $err['type'];
          $response['msg'] = $err['message'];
           $response['code'] =  $err['code'];
          echo json_encode($response);
        } catch (\Stripe\Error\InvalidRequest $e) {
             //echo '4';die();
           $body = $e->getJsonBody();
          $err  = $body['error'];
          $response['result'] = false;
          $response['type'] =  $err['type'];
          $response['msg'] = $err['message'];
           $response['code'] =  $err['code'];
          echo json_encode($response);
        } catch (\Stripe\Error\Authentication $e) {
            // echo '5';die();
          $body = $e->getJsonBody();
          $err  = $body['error'];
          $response['result'] = false;
          $response['type'] =  $err['type'];
          $response['msg'] = $err['message'];
           $response['code'] =  $err['code'];
          echo json_encode($response);
        } catch (\Stripe\Error\ApiConnection $e) {
            // echo '6';die();
           $body = $e->getJsonBody();
          $err  = $body['error'];
          $response['result'] = false;
          $response['type'] =  $err['type'];
          $response['msg'] = $err['message'];
           $response['code'] =  $err['code'];
          echo json_encode($response);
        } catch (\Stripe\Error\Base $e) {
             echo '7';die();
          $body = $e->getJsonBody();
          $err  = $body['error'];
          $response['result'] = false;
          $response['type'] =  $err['type'];
          $response['msg'] = $err['message'];
           $response['code'] =  $err['code'];
          echo json_encode($response);
        } catch (Exception $e) {
            // echo '8';die();
          $body = $e->getJsonBody();
          $err  = $body['error'];
          $response['result'] = false;
          $response['type'] =  $err['type'];
          $response['msg'] = $err['message'];
           $response['code'] =  $err['code'];
          echo json_encode($response);
        }
    }


    public function saveCard()
    {
        try 
        {
            // Create a Customer
            $customer_id = $_POST['customer_id'];
            $exp_month = $_POST['exp_month'];
            $exp_year = $_POST['exp_year'];
            $number = $_POST['number'];
            $cvc = $_POST['cvc'];
            $userId = $_REQUEST['user_id'];
            $email = $_POST['email'];
            $description = $_POST['description'];
            $name = $_POST['name'];

            if (empty($customer_id)) {
                $token = \Stripe\Token::create(array(
                            "card" => array(
                                "number" => $number,
                                "exp_month" => $exp_month,
                                "exp_year" => $exp_year,
                                "cvc" => $cvc
                            )
                    ));

                $e = \Stripe\Customer::create(array(
                            "email" => $email,
                            "description" => $description,
                            "source" => $token['id'] // obtained with Stripe.js
                ));
                $customer_id = $e['id'];
                $postArr = array('stripe_user_id'=>$customer_id);
                $whr = array('id'=>$userId);
                $this->common->update('user',$postArr,$whr);
                
            } else {
                $customer = \Stripe\Customer::retrieve($customer_id);
                $token = \Stripe\Token::create(array(
                            "card" => array(
                                "number" => $number,
                                "exp_month" => $exp_month,
                                "exp_year" => $exp_year,
                                "cvc" => $cvc
                            )
                ));

                $e = $customer->sources->create(array("source" => $token));
            }
            $response['result'] = "true";
            $response['msg'] = 'Card save successfully';
            $response['stripe_user_id'] = $customer_id;
            echo json_encode($response);
            exit();
        } catch (\Stripe\Error\Card $e) {
            // Since it's a decline, \Stripe\Error\Card will be caught
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['code'] = $err['code'];
            $response['msg'] = $err['message'];
            echo json_encode($response);
        } catch (\Stripe\Error\RateLimit $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (\Stripe\Error\InvalidRequest $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (\Stripe\Error\Authentication $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (\Stripe\Error\ApiConnection $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (\Stripe\Error\Base $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (Exception $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        }
    }

    public function cardList(){
        $customerId = $_POST['customer_id'];
        try {
            $e = \Stripe\Customer::retrieve($customerId)->sources->all(array('object' => 'card'));
            //print_r($e->data[0]);die();

            $error['status'] = 'true';
            $error['msg'] = $e->data;

            echo json_encode($error);
            exit();
        } catch (\Stripe\Error\Card $e) {
            // Since it's a decline, \Stripe\Error\Card will be caught
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['code'] = $err['code'];
            $response['msg'] = $err['message'];
            echo json_encode($response);
        } catch (\Stripe\Error\RateLimit $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (\Stripe\Error\InvalidRequest $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (\Stripe\Error\Authentication $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (\Stripe\Error\ApiConnection $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (\Stripe\Error\Base $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (Exception $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        }
    }

    public function customer(){
        
        $userId = $_REQUEST['user_id'];
        $email = $_POST['email'];
        $description = $_POST['description'];
        try {
            // Create a Customer
            $e = \Stripe\Customer::create(array(
                       //"name" => $name,
                        "email" => $email,
                        "description" => $description,
                        //"source" => "tok_visa" // obtained with Stripe.js
            ));
            $customerStripeId = $e['id'];
            $postArr = array('stripe_user_id'=>$customerStripeId);
            $whr = array('id'=>$userId);
            $this->common->update('user',$postArr,$whr);
            $response['result'] = "true";
            $response['msg'] = 'Customer created successfully';
            $response['stripe_user_id'] = $customerStripeId;

            echo json_encode($response);
            exit();
        } catch (\Stripe\Error\Card $e) {
            // Since it's a decline, \Stripe\Error\Card will be caught
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = "false";
            $response['type'] = $err['type'];
            $response['code'] = $err['code'];
            $response['msg'] = $err['message'];
            echo json_encode($response);
        } catch (\Stripe\Error\RateLimit $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = "false";
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (\Stripe\Error\InvalidRequest $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = "false";
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (\Stripe\Error\Authentication $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = "false";
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (\Stripe\Error\ApiConnection $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = "false";
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (\Stripe\Error\Base $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = "false";
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (Exception $e) {
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = "false";
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        }
    }

    public function stripe_payment()
    {
        $bookingId = $_REQUEST['booking_id'];
        $userId = $_REQUEST['user_id'];
        $customerId = $_REQUEST['customer_id'];
        $cardId = $_REQUEST['card_id'];
        $amount = $_REQUEST['amount'];
        try{
            $charge = \Stripe\Charge::create(array(
                "amount" => ceil($_REQUEST['amount']*100), # amount in cents, again
                "currency" => "usd",
                "customer" => $customerId,
                "card" => $cardId,
                "description" => 'Payment for User Id ' . $userId)
            );
            $finalResponse = $charge->jsonSerialize();
            $chargeId = $finalResponse['id'];
            if ($finalResponse['status'] == 'succeeded') {

                $transactionCode = $this->common->generateJobCode();
                $postArr = array(
                    'transaction_id'=>$transactionCode,
                    'bookinng_id'=>$bookingId,
                    'user_id'=>$userId,
                    'customer_id'=>$customerId,
                    'card_id'=>$cardId,
                    'stripe_charge_id'=>$chargeId,
                    'amount'=>$amount,
                    'added_on'=>date('Y-m-d H:i:s')
                );
                $insert_id = $this->common->save('transaction',$postArr);
                $response['tran_id'] = $insert_id;
                $response['result'] = true;
                $response['msg'] = 'Payment made successfully.';
            } else {
                $response['result'] = false;
            }
            echo json_encode($response);
            exit();
        }catch (\Stripe\Error\Card $e) {
        // Since it's a decline, \Stripe\Error\Card will be caught
        $body = $e->getJsonBody();
        $err = $body['error'];
        $response['result'] = false;
        $response['type'] = $err['type'];
        $response['code'] = $err['code'];
        $response['msg'] = $err['message'];
        echo json_encode($response);
        } catch (\Stripe\Error\RateLimit $e) {
             //echo '2';die();
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (\Stripe\Error\InvalidRequest $e) {
             //echo '3';die();
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (\Stripe\Error\Authentication $e) {
             //echo '4';die();
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (\Stripe\Error\ApiConnection $e) {
             //echo '5';die();
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (\Stripe\Error\Base $e) {
             //echo '6';die();
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        } catch (Exception $e) {
             //echo '7';die();
            $body = $e->getJsonBody();
            $err = $body['error'];
            $response['result'] = false;
            $response['type'] = $err['type'];
            $response['msg'] = $err['message'];
            $response['code'] = $err['code'];
            echo json_encode($response);
        }


    }





}
?>
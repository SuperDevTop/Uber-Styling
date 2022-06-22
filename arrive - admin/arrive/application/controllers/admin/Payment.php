<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Payment extends CI_Controller {

    public function __construct() {
        parent::__construct();
    }

    public function index(){
        


    }

    public function getPayment($user_id='',$amount='') {

         $user_id='';
         $amount=0;
         $user_id=$_GET['user_id'];
         $amount=$_GET['amount'];

        if(!empty($user_id))
        {
            $user_id=$_GET['user_id'];
        }
        else
        {
            $user_id='';
        }
        if(!empty($amount))
        {
            $amount=$_GET['amount'];
        }
        else
        {
            $amount=0;
        }

       
       

     $post=$this->input->post();
            

                     
        $this->load->library('form_validation');
        $this->load->library('authorize_net');
        $this->form_validation->set_rules('x_card_num', 'Card No.', 'trim|required|numeric|max_length[16]');
        $this->form_validation->set_rules('x_exp_date', 'exp date', 'trim|required');
        $this->form_validation->set_rules('expire_y', 'exp year', 'trim|required');
        $this->form_validation->set_rules('x_card_code', 'card code', 'trim|required');
    
        $this->form_validation->set_error_delimiters('<div class="text-danger">', '</div>');
       
     if ($this->form_validation->run() !== FALSE) {
        
        $expire_y=$this->input->post('expire_y');
        $x_exp_date=$this->input->post('x_exp_date');
        $date_year=$x_exp_date."/".$expire_y;
        
        $auth_net = array(
            'x_card_num'            => $this->input->post('x_card_num'), // Visa
            'x_exp_date'            => $date_year,
            'x_card_code'           => $this->input->post('x_card_code'),
            'x_amount'              => $amount,
            //'x_first_name'          => 'shalini',
            'x_customer_ip'         => $this->input->ip_address()
            );

        $this->authorize_net->setData($auth_net);



        if($this->authorize_net->authorizeAndCapture())
        { 
           //echo "<pre>"; print_r($this->authorize_net->authorizeAndCapture());die(); 
//echo 'dsds';die();
           $t_id=$this->authorize_net->getTransactionId();
           $card_num=$this->authorize_net->getcardNumber();
           $card_type=$this->authorize_net->getcardType();
           $created_at = date('Y-m-d H:i:s');


               $t_data = array(
                 't_id'  => $t_id,
                 'user_id'=>$user_id,
                 'card_type'=>$card_type,
                 'card_number'=>$card_num,
                 'created_at'=>$created_at

                );
               //print_r($t_data);die();
               

                 if(!empty($t_data))
                {
                   $final_data = array(
                            't_id'=>$t_id,

                            'message'=>'Success',
                            'status'=>'true'
                    );
                } 
                else 
                {
                    $final_data = array(
                            't_id'=>'',
                            'message'=>'unsuccess',
                            'status'=>'false'
                    );
                } 

              $data['payment_trans'] = $this->common->save('payment_trans', $t_data);
        }
        else
        {
             $final_data = array(
                            't_id'=>'',
                            'message'=>'Fail',
                            'status'=>'false'
                    );

        }
        //$this->do_user_payment($final_data);
        $this->load->view('payment/do_pay',['final_data'=>$final_data]); 

    }

    $this->load->view('payment/form'); 

    }


    function do_user_payment()
    {

    // print_r($final_data);die();
        $t_id =  $_POST['t_id'];
        $message =     $_POST['message'];
        $status =   $_POST['status'];
        $final_data = array(
                't_id'=>$t_id,
                'message'=>$message,
                'status'=>$status 
        );
        
         $this->load->view('payment/pay_msg',['final_data'=>$final_data]); 
     // Authorize.net lib

}
    


}



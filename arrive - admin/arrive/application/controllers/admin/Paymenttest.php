<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Paymenttest extends MY_Controller {

    public function __construct() {
        parent::__construct();
    }

    public function getDetails(){
        if(isset($_GET['user_id'])){
            echo $_GET['user_id'];
            
        }
        if(isset($_GET['amount'])){
          echo $_GET['amount'];
        }

    }

    public function index() {
         
         
         $data = array(
            'main_view' => 'payment/form_bkp'
        );
                     
        $this->load->library('form_validation');
        $this->load->library('authorize_net');
                 $this->form_validation->set_rules('x_card_num', 'Card No.', 'trim|required');
        $this->form_validation->set_rules('x_exp_date', 'exp date', 'trim|required');
        $this->form_validation->set_rules('x_card_code', 'card code', 'trim|required');
        $this->form_validation->set_rules('x_description', 'description', 'trim|required');
        $this->form_validation->set_rules('x_amount', 'amount', 'trim|required');
        $this->form_validation->set_rules('x_first_name', 'name', 'trim|required');
        $this->form_validation->set_rules('x_address', 'address', 'trim|required');
        $this->form_validation->set_rules('x_city', 'city', 'trim|required');
        $this->form_validation->set_rules('x_state', 'state', 'trim|required');
        $this->form_validation->set_rules('x_zip', 'zip', 'trim|required');
        $this->form_validation->set_rules('x_country', 'country', 'trim|required');
        $this->form_validation->set_rules('x_phone', 'phone', 'trim|required');
        $this->form_validation->set_rules('x_email', 'email', 'trim|required');
        $this->form_validation->set_error_delimiters('<div class="text-danger">', '</div>');
        $post=$this->input->post();

     if ($this->form_validation->run() !== FALSE) {
        

        $auth_net = array(
            'x_card_num'            => $this->input->post('x_card_num'), // Visa
            'x_exp_date'            => $this->input->post('x_exp_date'),
            'x_card_code'           => $this->input->post('x_card_code'),
            'x_description'         => $this->input->post('x_description'),
            'x_amount'              => $this->input->post('x_amount'),
            'x_first_name'          => $this->input->post('x_first_name'),
            'x_last_name'           => $this->input->post('x_last_name'),
            'x_address'             => $this->input->post('x_address'),
            'x_city'                => $this->input->post('x_city'),
            'x_state'               => $this->input->post('x_state'),
            'x_zip'                 => $this->input->post('x_zip'),
            'x_country'             => $this->input->post('x_country'),
            'x_phone'               => $this->input->post('x_phone'),
            'x_email'               => $this->input->post('x_email'),
            'x_customer_ip'         => $this->input->ip_address(),
            );

         // $auth_net = array(
         //    'x_card_num'            => '4111111111111111', // Visa
         //    'x_exp_date'            => '12/19',
         //    'x_card_code'           => '123',
         //    'x_description'         => 'A test transaction',
         //    'x_amount'              => '20',
         //    'x_first_name'          => 'John',
         //    'x_last_name'           => 'Doe',
         //    'x_address'             => '123 Green St.',
         //    'x_city'                => 'Lexington',
         //    'x_state'               => 'KY',
         //    'x_zip'                 => '40502',
         //    'x_country'             => 'US',
         //    'x_phone'               => '555-123-4567',
         //    'x_email'               => 'test@example.com',
         //    'x_customer_ip'         => $this->input->ip_address(),
         //    );
        $this->authorize_net->setData($auth_net);
        // Try to AUTH_CAPTURE
        if( $this->authorize_net->authorizeAndCapture() )
        {
             //redirect('http://arrive5.pcthepro.com/admin/payment');

           //echo '<p>Transaction ID: ' . $this->authorize_net->getTransactionId() . '</p>';
            echo '<p>Approval Code: ' . $this->authorize_net->getApprovalCode() . '</p>';
            //die;
            //echo '<p>Approval Code: ' . $this->authorize_net->createToken() . '</p>';

           // ;
        }
        else
        {
            echo '<h2>Fail!</h2>';
            // Get error
            echo '<p>' . $this->authorize_net->getError() . '</p>';
            // Show debug data
            $this->authorize_net->debug();

        }
    }

        $this->load->view('layout/main', $data);
    }


    function do_user_payment()
    {
        echo"hii";
     // Authorize.net lib

}
    


}



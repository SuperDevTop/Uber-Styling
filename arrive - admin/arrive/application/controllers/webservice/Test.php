<?php
defined('BASEPATH') OR exit('No direct script access allowed');
error_reporting(0);

class Test extends CI_Controller {

    public function __construct() {
        parent::__construct();
    }

    public function highPayingMatch()
    {
        $post = $this->input->post();
        $this->form_validation->set_rules('start_point', 'Start Point', 'required|xss_clean');
        $this->form_validation->set_rules('end_point', 'End Point', 'required|xss_clean');
        $start_point=$post['start_point'];
        $end_point=$post['end_point'];
        //print_r($post);
        if ($this->form_validation->run()) {

        
         $user_data = $this->common->findAll('high_paying_zone', $select = '*', $where, $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
       if(!empty($user_data))
       {
       foreach ($user_data as  $user_data_vals) 
       {
        //print_r($user_data_vals);
        $latitude['lat'] = array($user_data_vals['latitude1'],$user_data_vals['latitude2'],$user_data_vals['latitude3'],$user_data_vals['latitude4']);
        $longitude['long'] = array($user_data_vals['longitude1'],$user_data_vals['longitude2'],$user_data_vals['longitude3'],$user_data_vals['longitude4']);
        if(!empty($latitude['lat'])&&(!empty($longitude['long'])))
        {
        foreach($latitude['lat'] as $key => $latitude_value) 
        {
            echo"fff1";echo"<pre>";print_r($latitude_value);
            if(in_array(47.4830331, range(40.4830331,50.293400)))
            { 
           echo"fff";print_r($latitude_value);
            die;
           }
           
        }
        //die;
        foreach($longitude['long'] as $key => $longitude_value) 
        {
            echo"fff2";print_r($longitude_value);
            if(in_array($longitude_value, range($end_point,$end_point)))
            { 
               echo"ddd";
            print_r($longitude_value);
            //sdie;
           }
            
        }
        die;
    }
    else
    {
        $response['message'] = "no data find";
        $response['status'] = 'false';

    }
        //print_r($latitude['lat']);
        //print_r($longitude['long']);
           

       }
        //$response['res'] = $user_data_vals;
        $response['message'] = 'get data';
        $response['status'] = 'true';
        }

        else
        {
            $response['message'] = "no data";
            $response['status'] = 'false';

        }
    }
        else {
        $response['message'] = validation_errors();
        $response['status'] = 'false';
        }

        echo json_encode($response);

    }

}
?>
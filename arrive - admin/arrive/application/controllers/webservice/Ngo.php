<?php
if (!defined('BASEPATH'))
    exit('No direct script access allowed');
/**
 * 
 */
class Ngo extends CI_Controller{

	public function ngoDonation(){
		$this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        $post=$this->input->post();
        if ($this->form_validation->run()) {
        	$ngo_data = $this->common->find('ngo','*',['status'=>'1']);

        	$field = [
        		'ngo_id'=>$ngo_data['id'],
        		'user_id'=>$post['user_id'],
        		'donation_amount'=>$ngo_data['amount'],
        		'donation_date'=>date('Y-m-d H:i:s'),
        	];
			if($this->common->save('user_donation_detail',$field)){
				$response['message'] = "You have successfully donated";
		    	$response['status'] = 'true';
			}else{
				$response['message'] = "Something went wrong";
		    	$response['status'] = 'false';
			}
				
        }else{
        	$response['message'] = validation_errors();
            $response['status'] = 'false';
        }	
		echo json_encode($response);
	}
	
	public function ngoList(){
			$ngo_data = $this->common->findAll('ngo','id,name,title,description,amount,account_no,address,added_on',['status'=>'1']);
			$response['result'] = $ngo_data;
       		$response['status'] = 'true';
        	echo json_encode($response);
		}	

	//rtrim(strtr(base64_encode($str), '+/', '-_'), '=');
	//$pkid= base64_decode(str_pad(strtr($br[2], '-_', '+/'), strlen($br[2]) % 4, '=', STR_PAD_RIGHT));
	

}
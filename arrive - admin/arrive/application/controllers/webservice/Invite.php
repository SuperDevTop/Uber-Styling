<?php
if (!defined('BASEPATH'))
    exit('No direct script access allowed');
/**
 * 
 */
class Invite extends CI_Controller{

	public function getUserInviteCode(){
		$this->form_validation->set_rules('user_id', 'User Id', 'required|xss_clean');
        $this->form_validation->set_error_delimiters('', '');
        $post=$this->input->post();
        if ($this->form_validation->run()) {
			//$str = substr(base_convert(sha1(uniqid(mt_rand())), 16, 36), 0, 8);
			$str = $this->common->generateJobCode();
			if($this->common->save('user',['invite_code'=>$str,'id'=>$post['user_id']])){
				$response['invite_code'] = $str;
		    	$response['status'] = 'true';
			}else{
				$response['invite_code'] = "please refresh";
		    	$response['status'] = 'false';
			}
				
        }else{
        	$response['message'] = validation_errors();
            $response['status'] = 'false';
        }	
		echo json_encode($response);
	}
	

	//rtrim(strtr(base64_encode($str), '+/', '-_'), '=');
	//$pkid= base64_decode(str_pad(strtr($br[2], '-_', '+/'), strlen($br[2]) % 4, '=', STR_PAD_RIGHT));
	

}
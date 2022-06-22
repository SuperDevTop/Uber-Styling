<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class MY_Controller extends CI_Controller {

    function __construct() {
        parent::__construct();
//print_r($this->session->userdata());die;
        if (!$this->is_login()) {
            redirect('admin/login/index');
        }
    }

    public function is_login() {
        $log = $this->session->userdata('email_id');
        if (!empty($log)) {
            return true;
        } else {
            return false;
        }
    }

}

?>

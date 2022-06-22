<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Login extends CI_Controller {

    public function index() {

        $this->load->view('login/login');
    }

    public function log() {

        $email = $this->input->post('email');
        $password = $this->input->post('password');

        $admin = $this->common->find('admin', 'password', array('email_id' => $email));
//        echo'<pre>';
//        print_r($admin);
//        die();
        if ($admin != '') {

            $data = $this->common->find('admin', '*', array('email_id' => $email, 'password' => $password));

            if ((!empty($data))) {
                $newdata = array(
                    'email_id' => $data['email_id'],
                    'logged_in' => TRUE
                );
                $this->session->set_userdata($newdata);

                redirect(base_url('admin/admin/dashboard'));
            }
        }
        $this->session->set_flashdata('error', 'Invalid Email or Paassword. Please try again');

        $this->load->view('login/login');
    }

    public function logout() {
        $this->session->sess_destroy();
        $this->load->view('login/login');
//        redirect(base_url('/login'));
    }

    public function sub_admin() {

        $this->session->sess_destroy();
        $this->load->view('login/sublogin');
//        redirect(base_url('/login'));
    }

    public function users() {
        $this->load->view('users');
    }

}

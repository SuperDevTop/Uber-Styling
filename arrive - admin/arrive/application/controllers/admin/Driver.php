<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class driver extends MY_Controller {

    public function __construct() {
        parent::__construct();
    }

    public function index() {
        $data = array(
            'main_view' => 'driver/index',
            'drivers' => $this->common->findAll('driver', '*', $where = array(), $orderby = array('id' => 'DESC'))
        );

        $this->load->view('layout/main', $data);
    }

    public function form($id = false) {
        $data = array(
            'main_view' => 'driver/form'
        );
        $this->load->library('form_validation');
        $this->form_validation->set_rules('first_name', 'First name', 'trim|required');
        $this->form_validation->set_rules('last_name', 'Last name', 'trim|required');
        $this->form_validation->set_rules('mobile', 'Mobile', 'trim|required');
        $this->form_validation->set_rules('email', 'Email', 'trim|required');
        $this->form_validation->set_rules('password', 'password', 'trim|required');

        if ($id > 0) {
            $data['driver'] = $this->common->find('driver', '*', array('id' => $id));
        }

        $this->form_validation->set_error_delimiters('<div class="text-danger">', '</div>');

        $userPost = $this->input->post();

        if ($this->form_validation->run() !== FALSE) {

            $userData = array(
                'first_name' => $userPost['first_name'],
                'last_name' => $userPost['last_name'],
                'mobile' => $userPost['mobile'],
                'email' => $userPost['email'],
                'password' => $userPost['password']
            );


            if (!empty($data['driver'])) {
                $userData['id'] = $data['driver']['id'];
            }


            $this->common->save('driver', $userData);
            $this->session->set_flashdata('message', 'User has been saved');
            redirect(base_url('admin/driver'));
        }

        $this->load->view('layout/main', $data);
    }

    public function view($id) {
        $data = array(
            'main_view' => 'driver/view',
            'driver' => $this->common->find('driver', $select = '*', $where = array('id' => $id), $resultType = 'array', $orderby = array()),
        );

        $this->load->view('layout/main', $data);
    }

    public function active($id) {

        $data = array('status' => '1');

        $this->db->where('id', $id);
        $this->db->update('driver', $data);

        redirect(base_url('admin/driver'));
    }

    public function deactive($id) {
        $data = array('status' => '0');
        $this->db->where('id', $id);
        $this->db->update('driver', $data);

        redirect(base_url('admin/driver'));
    }

}

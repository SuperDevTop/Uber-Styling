<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Avatar extends MY_Controller {

    public function __construct() {
        parent::__construct();
    }

    public function index() {
        $data = array(
            'main_view' => 'avatar/index',
            'avatar' => $this->common->findAll('avatar', '*', $where = array(), $orderby = array('id' => 'DESC'))
        );

        $this->load->view('layout/main', $data);
    }

    public function form($id = false) {
        $data = array(
            'main_view' => 'avatar/form'
        );
        $this->load->library('form_validation');
        $this->form_validation->set_rules('name', 'Avatar Name', 'trim|required');
        if (empty($_FILES['img']['name'])) {
            $this->form_validation->set_rules('img', 'Avatar Image', 'required');
        }
        $data['avatar'] = $this->common->findAll('avatar', '*');
        if ($id > 0) {
            $data['avatar'] = $this->common->find('avatar', '*', array('id =' => $id));
        }

        $this->form_validation->set_error_delimiters('<div class="text-danger">', '</div>');

        $avatar = $this->input->post();

        if ($this->form_validation->run() !== FALSE) {

            $avatarData = array(
                'name' => $avatar['name']
            );


            $config['file_name'] = '_avatar_' . time();
            $config['upload_path'] = './assets/upload/avatar';
            $config['allowed_types'] = 'gif|jpg|png|jpeg|PNG|JPG|JPEG|GIF';
            $config['max_size'] = '100000';
            $config['max_width'] = '2000000';
            $config['max_height'] = '2000000';
            $config['remove_spaces'] = TRUE;
            $config['encrypt_name'] = TRUE;

            $this->load->library('upload', $config);

            if (($_FILES['img']['error']) == 0) {
                $this->upload->do_upload('img');
                $imgData = array('upload_data' => $this->upload->data());

                $avatarData['img'] = trim($imgData['upload_data']['file_name']);
            } else {
                $error = array('error' => $this->upload->display_errors());
                echo $error['error'];
            }

            if (!empty($data['avatar'])) {
                $avatarData['id'] = $data['avatar']['id'];
            }


            $this->common->save('avatar', $avatarData);
            $this->session->set_flashdata('message', 'Avatar has been saved');
            redirect(base_url('admin/avatar'));
        }

        $this->load->view('layout/main', $data);
    }

    public function active($id) {

        $data = array('status' => '1');

        $this->db->where('id', $id);
        $this->db->update('avatar', $data);

        redirect(base_url('admin/avatar'));
    }

    public function deactive($id) {
        $data = array('status' => '0');
        $this->db->where('id', $id);
        $this->db->update('avatar', $data);

        redirect(base_url('admin/avatar'));
    }

}

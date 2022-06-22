<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Highpayingzone extends MY_Controller {

   public function index() {
        $data = array(
            'main_view' => 'high_paying_zone/index',
            'users' => $this->common->findAll('high_paying_zone', '*', $where = array(), $orderby = array('id' => 'DESC'))
        );

        $this->load->view('layout/main', $data);
    }

    public function form($id = false) {
        $data = array(
            'main_view' => 'high_paying_zone/form'
        );
        $this->load->library('form_validation');
        $this->form_validation->set_rules('zone_name', 'zone name', 'trim|required');
       $this->form_validation->set_rules('latitude1', 'latitude1', 'trim|required');
       $this->form_validation->set_rules('longitude1', 'longitude1', 'trim|required');
       $this->form_validation->set_rules('latitude2', 'latitude2', 'trim|required');
       $this->form_validation->set_rules('longitude2', 'longitude2', 'trim|required');
       $this->form_validation->set_rules('latitude3', 'latitude3', 'trim|required');
       $this->form_validation->set_rules('longitude3', 'longitude3', 'trim|required');
       $this->form_validation->set_rules('latitude4', 'latitude4', 'trim|required');
       $this->form_validation->set_rules('longitude4', 'longitude4', 'trim|required');
$this->form_validation->set_rules('high_by', 'high by', 'trim|required');
        if ($id > 0) {
            $data['user'] = $this->common->find('high_paying_zone', '*', array('id' => $id));
        }

        $this->form_validation->set_error_delimiters('<div class="text-danger">', '</div>');

        $userPost = $this->input->post();

        if ($this->form_validation->run() !== FALSE) {

            $userData = array(
                'zone_name' => $userPost['zone_name'],
                'latitude1' => $userPost['latitude1'],
                'longitude1' => $userPost['longitude1'],
                'latitude2' => $userPost['latitude2'],
                'longitude2' => $userPost['longitude2'],
                 'latitude3' => $userPost['latitude3'],
                'longitude3' => $userPost['longitude3'],
                'latitude4' => $userPost['latitude4'],
                'longitude4' => $userPost['longitude4'],
                'high_by' => $userPost['high_by']
                
            );


            if (!empty($data['user'])) {
                $userData['id'] = $data['user']['id'];
            }


            $this->common->save('high_paying_zone', $userData);
            $this->session->set_flashdata('message', 'high paying zone has been saved');
            redirect(base_url('admin/highpayingzone'));
        }

        $this->load->view('layout/main', $data);
    }

    public function view($id) {
        $data = array(
            'main_view' => 'high_paying_zone/view',
            'user' => $this->common->find('high_paying_zone', $select = '*', $where = array('id' => $id), $resultType = 'array', $orderby = array()),
        );

        $this->load->view('layout/main', $data);
    }

    public function active($id) {

        $data = array('status' => '1');

        $this->db->where('id', $id);
        $this->db->update('high_paying_zone', $data);

        redirect(base_url('admin/highpayingzone'));
    }

    public function deactive($id) {
        $data = array('status' => '0');
        $this->db->where('id', $id);
        $this->db->update('high_paying_zone', $data);

        redirect(base_url('admin/highpayingzone'));
    }

     public function getAllHighzone() {
        $data = $this->db->query("SELECT 
                                        *
                                    FROM
                                        high_paying_zone
                                    WHERE status ='1'")->result_array();
        header('content-type:application/json');
        echo $data != NULL && count($data) > 0 ? json_encode($data) : json_encode([]);
    }




}

?>
<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Subscriptionplan extends MY_Controller {

    public function index() {

        $data = array(
            'main_view' => 'subscriptionplan/index',
            'subscriptionplan' => $this->common->findAll('membership', '*')
        );

        $this->load->view('layout/main', $data);
    }

    public function form($id = false) {
        $data = array(
            'main_view' => 'subscriptionplan/form'
        );

        if ($id > 0) {
            $data['membership'] = $this->common->find('membership', '*', array('id' => $id));
        }
        $this->load->library('form_validation');
        $this->form_validation->set_rules('membership_name', 'Membership Name', 'trim|required');
        $this->form_validation->set_rules('description', 'Membership Description', 'trim|required');
        $this->form_validation->set_rules('purchase_amount', 'Membership Purchase Amount', 'trim|required');
        $this->form_validation->set_error_delimiters('<div class="text-danger">', '</div>');

        if ($this->form_validation->run() !== FALSE) {
            //print_r($templates);exit;
            $templates = $this->input->post();

            $templateData = array(
                'membership_name' => $templates['membership_name'],
                'description' => $templates['description'],
                'purchase_amount' => $templates['purchase_amount'],
            );
            if (!empty($data['membership'])) {
                $templateData['id'] = $data['membership']['id'];
            }

            $this->common->save('membership', $templateData);
            $this->session->set_flashdata('message', 'Subscription plan has been saved');
            redirect(base_url('admin/subscriptionplan'));
        }
        $this->load->view('layout/main', $data);
    }

    public function view($id) {
        $data = array(
            'main_view' => 'subscriptionplan/view',
            'subscriptionplan' => $this->common->find('membership', $select = '*', $where = array('id' => $id), $resultType = 'array', $orderby = array()),
        );

        $this->load->view('layout/main', $data);
    }

    public function active($id) {

        $data = array('status' => '1');

        $this->db->where('id', $id);
        $this->db->update('membership', $data);

        redirect(base_url('admin/subscriptionplan'));
    }

    public function deactive($id) {
        $data = array('status' => '0');
        $this->db->where('id', $id);
        $this->db->update('membership', $data);

        redirect(base_url('admin/subscriptionplan'));
    }

}

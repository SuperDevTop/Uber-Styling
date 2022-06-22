<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Credit extends MY_Controller {

    public function __construct() {
        parent::__construct();
    }

    public function index() {
        $data = array(
            'main_view' => 'credit/index',
            'credit' => $this->common->findAll('credit', '*', $where = array(), $orderby = array('id' => 'DESC'))
        );

        $this->load->view('layout/main', $data);
    }

    public function form($id = false) {
        $data = array(
            'main_view' => 'credit/form'
        );
        $this->load->library('form_validation');
        $this->form_validation->set_rules('credits', 'Credits', 'trim|required');
        $this->form_validation->set_rules('amount', 'Amount', 'trim|required');
        $this->form_validation->set_rules('validity', 'Validity', 'trim|required');

//        $data['credit'] = $this->common->findAll('credit', '*');
        if ($id > 0) {
            $data['credit'] = $this->common->find('credit', '*', array('id' => $id));
        }

        $this->form_validation->set_error_delimiters('<div class="text-danger">', '</div>');

        $creditPost = $this->input->post();

        if ($this->form_validation->run() !== FALSE) {

            $creditData = array(
                'credits' => $creditPost['credits'],
                'amount' => $creditPost['amount'],
                'validity' => $creditPost['validity']
            );


            if (!empty($data['credit'])) {
                $creditData['id'] = $data['credit']['id'];
            }


            $this->common->save('credit', $creditData);
            $this->session->set_flashdata('message', 'Credit has been saved');
            redirect(base_url('admin/credit'));
        }

        $this->load->view('layout/main', $data);
    }

    public function active($id) {

        $data = array('status' => '1');

        $this->db->where('id', $id);
        $this->db->update('credit', $data);

        redirect(base_url('admin/credit'));
    }

    public function deactive($id) {
        $data = array('status' => '0');
        $this->db->where('id', $id);
        $this->db->update('credit', $data);

        redirect(base_url('admin/credit'));
    }

}

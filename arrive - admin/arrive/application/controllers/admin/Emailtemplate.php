<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Emailtemplate extends MY_Controller {

    public function __construct() {
        parent::__construct();
    }

    public function index() {
        $data = array(
            'main_view' => 'emailtemplate/index',
            'emailtemplate' => $this->common->findAll('en_email_template', '*')
        );

        $this->load->view('layout/main', $data);
    }

    public function form($id = false) {
        $data = array(
            'main_view' => 'emailtemplate/form'
        );

        if ($id > 0) {
            $data['en_email_template'] = $this->common->find('en_email_template', '*', array('id' => $id));
        }
        $this->load->library('form_validation');
        $this->form_validation->set_rules('template_name', 'Template Name', 'trim|required');
        $this->form_validation->set_rules('template_subject', 'Template Subject', 'trim|required');
        $this->form_validation->set_rules('template_description', 'Template description', 'trim|required');
        $this->form_validation->set_error_delimiters('<div class="text-danger">', '</div>');

        if ($this->form_validation->run() !== FALSE) {
            //print_r($templates);exit;
            $templates = $this->input->post();

            $templateData = array(
                'template_name' => $templates['template_name'],
                'template_subject' => $templates['template_subject'],
                'template_description' => $templates['template_description'],
            );
            if (!empty($data['en_email_template'])) {
                $templateData['id'] = $data['en_email_template']['id'];
            }

            $this->common->save('en_email_template', $templateData);
            $this->session->set_flashdata('message', 'Email Templates has been saved');
            redirect(base_url('admin/emailtemplate'));
        }
        $this->load->view('layout/main', $data);
    }

    public function view($id) {
        $data = array(
            'main_view' => 'emailtemplate/view',
            'en_email_template' => $this->common->find('en_email_template', $select = '*', $where = array('id' => $id), $resultType = 'array', $orderby = array()),
        );

        $this->load->view('layout/main', $data);
    }

}

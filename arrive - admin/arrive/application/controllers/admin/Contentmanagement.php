
<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Contentmanagement extends MY_Controller {

    public function __construct() {
        parent::__construct();
    }

    public function index() {
        $data = array(
            'main_view' => 'contentmanagement/index',
            'content' => $this->common->findAll('en_content', '*')
        );

        $this->load->view('layout/main', $data);
    }

    public function form($id = false) {
        $data = array(
            'main_view' => 'contentmanagement/form'
        );

        if ($id > 0) {
            $data['content'] = $this->common->find('en_content', '*', array('id' => $id));
        }
        $this->load->library('form_validation');
        $this->form_validation->set_rules('title', 'Title', 'trim|required');
        $this->form_validation->set_rules('description', 'Description', 'trim|required');
        $this->form_validation->set_error_delimiters('<div class="text-danger">', '</div>');

        if ($this->form_validation->run() !== FALSE) {
            //print_r($templates);exit;
            $content = $this->input->post();

            $contentData = array(
                'title' => $content['title'],
                'description' => $content['description']
            );
            if (!empty($data['content'])) {
                $contentData['id'] = $data['content']['id'];
            }

            $this->common->save('en_content', $contentData);
            $this->session->set_flashdata('message', 'Content has been saved');
            redirect(base_url('admin/contentmanagement'));
        }
        $this->load->view('layout/main', $data);
    }

    public function view($id) {
        $data = array(
            'main_view' => 'contentmanagement/view',
            'content' => $this->common->find('en_content', $select = '*', $where = array('id' => $id), $resultType = 'array', $orderby = array()),
        );

        $this->load->view('layout/main', $data);
    }

}

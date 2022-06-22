<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Task extends MY_Controller {

    public function __construct() {
        parent::__construct();
    }

    public function index() {
        $data = array(
            'main_view' => 'task/index',
            'task' => $this->common->findAll('en_task', '*')
        );

        $this->load->view('layout/main', $data);
    }

    public function active($id) {

        $data = array('status' => '1');

        $this->db->where('id', $id);
        $this->db->update('en_task', $data);

        redirect(base_url('admin/task'));
    }

    public function deactive($id) {
        $data = array('status' => '0');
        $this->db->where('id', $id);
        $this->db->update('en_task', $data);

        redirect(base_url('admin/task'));
    }

    public function view($id) {
        $data = array(
            'main_view' => 'task/view',
            'task' => $this->common->find('en_task', $select = '*', $where = array('id' => $id), $resultType = 'array', $orderby = array())
        );

        $data['taskCategory'] = $this->common->find('en_task_category', $select = 'cat_name', $where = array('id' => $data['task']['task_cat_id']), $resultType = 'array', $orderby = array());
        $data['taskPoster'] = $this->common->find('en_user', $select = 'first_name,last_name', $where = array('id' => $data['task']['poster_id']), $resultType = 'array', $orderby = array());

        $this->load->view('layout/main', $data);
    }

}

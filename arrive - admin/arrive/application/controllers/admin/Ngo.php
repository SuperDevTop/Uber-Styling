<?php
defined('BASEPATH') OR exit('No direct script access allowed');
/**
 * 
 */
class Ngo extends MY_Controller {

    public function __construct() {
        parent::__construct();
    }

    public function index(){
		$data = array(
            'main_view' => 'ngo/index',
            'ngo' => $this->common->findAll('ngo', '*', $where = array(), $orderby = array('id' => 'DESC'))
        );

        $this->load->view('layout/main', $data);	
    }

	public function create($id = false){
		$data['main_view'] = 'ngo/create';
        //$data['users'] = $this->common->findAll('user', 'email,id,CONCAT(first_name," ",last_name) as name', $where = array(), $orderby = array('id' => 'DESC'));

        $this->load->library('form_validation');
        $this->form_validation->set_rules('ngo_name', 'Ngo Name', 'trim|required');
        $this->form_validation->set_rules('ngo_title', 'Ngo Title', 'trim|required');
        $this->form_validation->set_rules('description', 'Description', 'trim|required');
        $this->form_validation->set_rules('amount', 'Amount', 'trim|required|numeric|max_length[5]');
        $this->form_validation->set_rules('account_no', 'Account No', 'trim|required|numeric|max_length[25]');
        $this->form_validation->set_rules('status', 'Status', 'required');

        if($id>0){
        	$data['ngo'] = $this->common->find('ngo', '*', array('id' => $id));
        }
        $userPost = $this->input->post();
         if ($this->form_validation->run() !== FALSE) {

            $userData = array(
                'name' => $userPost['ngo_name'],
                'title' => $userPost['ngo_title'],
                'description' => $userPost['description'],
                'amount' => $userPost['amount'],
                'account_no' => $userPost['account_no'],
                'status' => $userPost['status'],
                'added_on' => date("Y-m-d H:i:s"),
            );
            if (!empty($data['ngo'])) {
                $userData['id'] = $data['ngo']['id'];
                $userData['modified_on'] = date("Y-m-d H:i:s");
            }
            $this->common->save('ngo', $userData);
            $this->session->set_flashdata('message', 'Ngo has been saved');
            redirect(base_url('admin/ngo/create'));
        }
        $this->load->view('layout/main',$data);
	}

	public function view($id) {
        $data = array(
            'main_view' => 'ngo/view',
            'ngo' => $this->common->find('ngo', $select = '*', $where = array('id' => $id), $resultType = 'array', $orderby = array()),
        );

        $this->load->view('layout/main', $data);
    }

    public function active($id) {

        $data = array('status' => '1');

        $this->db->where('id', $id);
        $this->db->update('ngo', $data);

        redirect(base_url('admin/ngo'));
    }

    public function deactive($id) {
        $data = array('status' => '0');
        $this->db->where('id', $id);
        $this->db->update('ngo', $data);

        redirect(base_url('admin/ngo'));
    }
}
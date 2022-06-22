<?php 
/**
 * 
 */
class Promo extends MY_Controller{

	public function index() {
        $data = array(
            'main_view' => 'promo/index',
            'promo' => $this->common->findAll('promo', '*', $where = array(), $orderby = array('id' => 'DESC'))
        );

        $this->load->view('layout/main', $data);
    }

	public function create($id = false)
	{
		$data['main_view'] = 'promo/create';
        $data['users'] = $this->common->findAll('user', 'email,id,CONCAT(first_name," ",last_name) as name', $where = array(), $orderby = array('id' => 'DESC'));

        $this->load->library('form_validation');
        $this->form_validation->set_rules('user_list[]', 'Select User', 'required');
        $this->form_validation->set_rules('promo_name', 'Promo Name', 'trim|required');
        $this->form_validation->set_rules('promo_code', 'Promo Code', 'trim|required|min_length[8]|alpha_numeric|is_unique[promo.promo_code]');
        $this->form_validation->set_rules('valid_form', 'Valid Form', 'required');
        $this->form_validation->set_rules('valid_to', 'Valid To', 'required');
        $this->form_validation->set_rules('status', 'Status', 'required');
        $this->form_validation->set_rules('discount', 'Discount', 'trim|required|numeric|max_length[4]');

        if($id>0){
        	$data['promo'] = $this->common->find('promo', '*', array('id' => $id));
             $this->form_validation->set_rules('promo_code', 'Promo Code', 'trim|required|min_length[8]|alpha_numeric');
        }else{
            $this->form_validation->set_rules('promo_code', 'Promo Code', 'trim|required|min_length[8]|alpha_numeric|is_unique[promo.promo_code]');
        }
        $userPost = $this->input->post();
         if ($this->form_validation->run() !== FALSE) {

            $userData = array(
                'user_id' => implode(",", $userPost['user_list']),
                'promo_name' => $userPost['promo_name'],
                'promo_code' => $userPost['promo_code'],
                'valid_from' => date("Y-m-d H:i:s",strtotime($userPost['valid_form'])),
                'valid_to' => date("Y-m-d H:i:s",strtotime($userPost['valid_to'])),
                'status' => $userPost['status'],
                'discount' => $userPost['discount'],
                'added_on' => date("Y-m-d H:i:s"),
            );
            if (!empty($data['promo'])) {
                $userData['id'] = $data['promo']['id'];
            }
            $this->common->save('promo', $userData);
            $this->session->set_flashdata('message', 'Promo Code has been saved');
            redirect(base_url('admin/promo/create'));
        }
        $this->load->view('layout/main',$data);
	}
      public function active($id) {

        $data = array('status' => '1');

        $this->db->where('id', $id);
        $this->db->update('promo', $data);

        redirect(base_url('admin/promo'));
    }
    public function deactive($id) {
        $data = array('status' => '0');
        $this->db->where('id', $id);
        $this->db->update('promo', $data);

        redirect(base_url('admin/promo'));
    }
    public function view($id) {
        $data = array(
            'main_view' => 'promo/view',
            'promo' => $this->common->find('promo', $select = '*', $where = array('id' => $id), $resultType = 'array', $orderby = array()),
        );

        $this->load->view('layout/main', $data);
    }
}
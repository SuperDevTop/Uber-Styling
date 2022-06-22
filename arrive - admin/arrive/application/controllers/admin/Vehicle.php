<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Vehicle extends MY_Controller {

    public function __construct() {
        parent::__construct();
    }

    public function index() 
	{
        $data = array(
            'main_view' => 'vehicle/index',
            'vehicle' => $this->common->findAll('vehicle_model', '*', $where = array(), $orderby = array('id' => 'DESC'))
        );

        $this->load->view('layout/main', $data);
    }
	public function type() 
	{
        $data = array(
            'main_view' => 'vehicle/type',
            'vehicle' => $this->common->findAll('vehicle_type_master', '*', $where = array(), $orderby = array('id' => 'DESC'))
        );

        $this->load->view('layout/main', $data);
    }
	public function zipfarewall($id=false)
	{
		if(empty($id))
		{
			$data = array(
            'main_view' => 'vehicle/zipfirewel',
            'vehicle' => $this->common->findAll('zipfarewall', '*', $where = array(), $orderby = array('id' => 'DESC'))
        );
		}else
		{
        	$data = array(
            'main_view' => 'vehicle/zipfirewel',
            'vehicle' => $this->common->findAll('zipfarewall', '*', $where = array('vehicle_model_id'=>$id), 
			$orderby = array('id' => 'DESC')));
		}
        $this->load->view('layout/main', $data);
	}
	public function classtype($id= false) 
	{
		if(empty($id))
		{
			$data = array(
            'main_view' => 'vehicle/classtype',
            'vehicle' => $this->common->findAll('vehicle_subtype_master', '*', $where = array(), $orderby = array('id' => 'DESC'))
        );
		}else
		{
        $data = array(
            'main_view' => 'vehicle/classtype',
            'vehicle' => $this->common->findAll('vehicle_subtype_master', '*', $where = array('vehicle_type_id'=>$id), $orderby = array('id' => 'DESC'))
        );
		}
        $this->load->view('layout/main', $data);
    }
	
	 public function classform($id = false) 
	{
        $data = array(
            'main_view' => 'vehicle/classform'
        );
        $this->load->library('form_validation');
        $this->form_validation->set_rules('vehicle_model', 'Vehicle Model', 'trim|required');
		$this->form_validation->set_rules('base_price', 'Base Price', 'trim|required');
		$this->form_validation->set_rules('booking_fare', 'Booking Fare', 'trim|required');
		$this->form_validation->set_rules('minimum_fare', 'Minimum Fare', 'trim|required');
		$this->form_validation->set_rules('charge_per_min', 'Charge/Minut', 'trim|required');
		$this->form_validation->set_rules('vehicle_capacity', 'Vehicle Capacity', 'trim|required');
		$this->form_validation->set_rules('vehicle_door', 'Vehicle Door', 'trim|required');
		$this->form_validation->set_rules('charge_per_mile', 'Charge/Mil', 'trim|required');
         
        $data['vehicle'] = $this->common->findAll('vehicle_subtype_master', '*');
        if ($id > 0) {
            $data['vehicle'] = $this->common->find('vehicle_subtype_master', '*', array('id =' => $id));
        }

        $this->form_validation->set_error_delimiters('<div class="text-danger">', '</div>');

        $vehicle = $this->input->post();

        if ($this->form_validation->run() !== FALSE) 
		{

            $vehicleData = array(
                'vehicle_model' => $vehicle['vehicle_model'],
				'base_price' => $vehicle['base_price'],
				'booking_fare' => $vehicle['booking_fare'],
				'minimum_fare' => $vehicle['minimum_fare'],
				'charge_per_min' => $vehicle['charge_per_min'],
				'vehicle_capacity' => $vehicle['vehicle_capacity'],
				'vehicle_door' => $vehicle['vehicle_door'],
				'charge_per_mile' => $vehicle['charge_per_mile'],
				'id' => $id,
            );
            $this->common->save('vehicle_subtype_master', $vehicleData);
            $this->session->set_flashdata('message', 'Vehicle Class has been saved');
            redirect(base_url('admin/vehicle/classtype'));
        }

        $this->load->view('layout/main', $data);
    }
    public function form($id = false) 
	{
        $data = array(
            'main_view' => 'vehicle/form'
        );
        $this->load->library('form_validation');
        $this->form_validation->set_rules('name', 'Vehicle Name', 'trim|required');
        if (empty($_FILES['img']['name'])) {
            $this->form_validation->set_rules('img', 'Vehicle Image', 'required');
        }
        $data['vehicle'] = $this->common->findAll('vehicle', '*');
        if ($id > 0) {
            $data['vehicle'] = $this->common->find('vehicle', '*', array('id =' => $id));
        }

        $this->form_validation->set_error_delimiters('<div class="text-danger">', '</div>');

        $vehicle = $this->input->post();

        if ($this->form_validation->run() !== FALSE) {

            $vehicleData = array(
                'name' => $vehicle['name']
            );


            $config['file_name'] = '_vehicle_' . time();
            $config['upload_path'] = './assets/upload/vehicle';
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

                $vehicleData['img'] = trim($imgData['upload_data']['file_name']);
            } else {
                $error = array('error' => $this->upload->display_errors());
                echo $error['error'];
            }

            if (!empty($data['vehicle'])) {
                $vehicleData['id'] = $data['vehicle']['id'];
            }


            $this->common->save('vehicle', $vehicleData);
            $this->session->set_flashdata('message', 'Vehicle has been saved');
            redirect(base_url('admin/vehicle'));
        }

        $this->load->view('layout/main', $data);
    }
	public function zipform($id = false) 
	{
        $data = array(
            'main_view' => 'vehicle/zipform'
        );
        $this->load->library('form_validation');
        $this->form_validation->set_rules('name', 'Vehicle Name', 'trim|required');
        if (empty($_FILES['img']['name'])) {
            $this->form_validation->set_rules('img', 'Vehicle Image', 'required');
        }
        $data['vehicle'] = $this->common->findAll('zipfarewall', '*');
        if ($id > 0) {
            $data['vehicle'] = $this->common->find('zipfarewall', '*', array('id =' => $id));
        }

        $this->form_validation->set_error_delimiters('<div class="text-danger">', '</div>');

        $vehicle = $this->input->post();

        if ($this->form_validation->run() !== FALSE) {

            $vehicleData = array(
                'name' => $vehicle['name']
            );


            $config['file_name'] = '_vehicle_' . time();
            $config['upload_path'] = './assets/upload/vehicle';
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

                $vehicleData['img'] = trim($imgData['upload_data']['file_name']);
            } else {
                $error = array('error' => $this->upload->display_errors());
                echo $error['error'];
            }

            if (!empty($data['vehicle'])) {
                $vehicleData['id'] = $data['vehicle']['id'];
            }


            $this->common->save('zipfarewall', $vehicleData);
            $this->session->set_flashdata('message', 'Vehicle has been saved');
            redirect(base_url('admin/vehicle'));
        }

        $this->load->view('layout/main', $data);
    }

    public function active($id) {

        $data = array('status' => '1');

        $this->db->where('id', $id);
        $this->db->update('vehicle_model', $data);

        redirect(base_url('admin/vehicle'));
    }

    public function deactive($id) {
        $data = array('status' => '0');
        $this->db->where('id', $id);
        $this->db->update('vehicle_model', $data);

        redirect(base_url('admin/vehicle'));
    }

}

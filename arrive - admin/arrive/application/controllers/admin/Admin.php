<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Admin extends MY_Controller {

    public function dashboard() 
	{

        $data['userCount'] = $this->common->find('user', $select = 'COUNT(*) AS userCount', $where = array());
        $data['driverCount'] = $this->common->find('driver', $select = 'COUNT(*) AS driverCount', $where = array());
//        $data['bookingCount'] = $this->common->find('booking', $select = 'COUNT(*) AS bookingCount', $where = array());
//        $data['avatarCount'] = $this->common->find('avatar', $select = 'COUNT(*) AS avatarCount', $where = array());
//        $data['vehicleCount'] = $this->common->find('vehicle', $select = 'COUNT(*) AS vehicleCount', $where = array());
        $data['main_view'] = 'dashboard';
        $this->load->view('layout/main', $data);
    }
    public function users() 
	{
        $this->load->view('users');
    }


    public function getAllDrivers() 
	{
        $data = $this->db->query("SELECT * FROM  driver WHERE is_online = '1' AND is_available = '1' AND status = '1' AND token != ''")->result_array();
        header('content-type:application/json');
        echo $data != NULL && count($data) > 0 ? json_encode($data) : json_encode([]);
    }
}

?>
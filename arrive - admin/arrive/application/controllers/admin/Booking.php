<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Booking extends MY_Controller {

    public function __construct() {
        parent::__construct();
    }

    public function index() {
        $data = array(
            'main_view' => 'booking/index',
            'booking' => $this->common->findAllWithJoin('booking', $select = 'booking.id AS bookingId, booking.schedule_date, booking.user_id, booking.schedule_time, booking.mode, user.name', $join = array('user' => 'user.id = booking.user_id'), $where = array(), $orderby = array('booking.id' => 'DESC'), $resultType = 'array', $limit = '')
        );

        $this->load->view('layout/main', $data);
    }

    public function view($id) {
        $data = array(
            'main_view' => 'booking/view',
            'booking' => $this->common->findAllWithJoin('booking', $select = 'booking.id AS bookingId, booking.schedule_date, booking.schedule_time, booking.start_point, booking.end_point, booking.driver_id, booking.mode, user.id AS userId, user.name, user.email, user.country_code, user.phone', $join = array('user' => 'user.id = booking.user_id'), $where = array('booking.id' => $id), $orderby = array('booking.id' => 'DESC'), $resultType = 'array', $limit = '')
        );

        $driverId = $data['booking'][0]['driver_id'];
        $data['driverData'] = $this->common->find('user', $select = 'id AS driverId, name, email, country_code, phone', $where = array('id' => $driverId), $resultType = 'array', $orderby = array());
        $this->load->view('layout/main', $data);
    }

    public function active($id) {

        $data = array('status' => '1');

        $this->db->where('id', $id);
        $this->db->update('user', $data);

        redirect(base_url('admin/user'));
    }

    public function deactive($id) {
        $data = array('status' => '0');
        $this->db->where('id', $id);
        $this->db->update('user', $data);

        redirect(base_url('admin/user'));
    }

}

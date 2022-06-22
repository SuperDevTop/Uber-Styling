<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Report extends MY_Controller {

    public function __construct() {
        parent::__construct();
    }

    public function index() {

        $join = array(
            'en_user AS posterUser' => 'posterUser.id = en_task.poster_id',
            'en_user AS workerUser' => 'workerUser.id = en_task.worker_id'
        );

        $data = array(
            'main_view' => 'report/index',
            'taskList' => $this->common->findAllWithJoin('en_task', $select = 'posterUser.first_name AS posterFirstName, posterUser.last_name AS posterLastName, workerUser.first_name AS workerFirstName, workerUser.last_name AS workerLastName, en_task.title, en_task.due_date', $join, $where = array(), $orderby = array(), $resultType = 'array', $limit = '')
        );

        $this->load->view('layout/main', $data);
    }

    public function get_report() {

        $connection = mysqli_connect('localhost', 'pcthepro_nitesh', 'project@2017', 'pcthepro_airen_tasker');
        $PostValue = $this->input->post();


        if ($PostValue['poster'] != '') {
            $pExpd = explode(' ', $PostValue['poster']);
            $pFirstName = trim($pExpd[0]);
            $pLastName = trim($pExpd[1]);
        } else {
            $pFirstName = '';
            $pLastName = '';
        }
        if ($PostValue['worker'] != '') {
            $wExpd = explode(' ', $PostValue['worker']);
            $wFirstName = trim($wExpd[0]);
            $wLastName = trim($wExpd[1]);
        } else {
            $wFirstName = '';
            $wLastName = '';
        }
        $fromDate = $PostValue['fromDate'];
        $toDate = $PostValue['toDate'];



        if ($fromDate != '' && $toDate != '' && $pFirstName != '' && $pLastName != '' && $wFirstName != '' && $wLastName != '') {

            $report = mysqli_query($connection, "SELECT en_task.id, posterUser.first_name AS posterFirstName, posterUser.last_name AS posterLastName, workerUser.first_name AS workerFirstName, workerUser.last_name AS workerLastName, en_task.title, en_task.due_date FROM en_task LEFT JOIN en_user AS posterUser ON posterUser.id = en_task.poster_id LEFT JOIN en_user AS workerUser ON workerUser.id = en_task.worker_id WHERE posterUser.first_name = '$pFirstName' AND posterUser.LAST_name = '$pLastName' AND workerUser.first_name = '$wFirstName' AND workerUser.last_name = '$wLastName' AND en_task.due_date BETWEEN '" . $fromDate . "' AND '" . $toDate . "' order by en_task.id DESC");
        } elseif ($fromDate != '' && $toDate != '' && $pFirstName == '' && $pLastName == '' && $wFirstName == '' && $wLastName == '') {


            $report = mysqli_query($connection, "SELECT en_task.id, posterUser.first_name AS posterFirstName, posterUser.last_name AS posterLastName, workerUser.first_name AS workerFirstName, workerUser.last_name AS workerLastName, en_task.title, en_task.due_date FROM en_task LEFT JOIN en_user AS posterUser ON posterUser.id = en_task.poster_id LEFT JOIN en_user AS workerUser ON workerUser.id = en_task.worker_id WHERE en_task.due_date BETWEEN '" . $fromDate . "' AND '" . $toDate . "' order by en_task.id DESC");
        } elseif ($fromDate == '' && $toDate == '' && $pFirstName != '' && $pLastName != '' && $wFirstName == '' && $wLastName == '') {


            $report = mysqli_query($connection, "SELECT en_task.id, posterUser.first_name AS posterFirstName, posterUser.last_name AS posterLastName, workerUser.first_name AS workerFirstName, workerUser.last_name AS workerLastName, en_task.title, en_task.due_date FROM en_task LEFT JOIN en_user AS posterUser ON posterUser.id = en_task.poster_id LEFT JOIN en_user AS workerUser ON workerUser.id = en_task.worker_id WHERE posterUser.first_name = '$pFirstName' AND posterUser.LAST_name = '$pLastName' order by en_task.id DESC");
        } elseif ($fromDate == '' && $toDate == '' && $pFirstName == '' && $pLastName == '' && $wFirstName != '' && $wLastName != '') {

            $report = mysqli_query($connection, "SELECT en_task.id, posterUser.first_name AS posterFirstName, posterUser.last_name AS posterLastName, workerUser.first_name AS workerFirstName, workerUser.last_name AS workerLastName, en_task.title, en_task.due_date FROM en_task LEFT JOIN en_user AS posterUser ON posterUser.id = en_task.poster_id LEFT JOIN en_user AS workerUser ON workerUser.id = en_task.worker_id WHERE workerUser.first_name = '$wFirstName' AND workerUser.last_name = '$wLastName' order by en_task.id DESC");
        } elseif ($fromDate == '' && $toDate == '' && $pFirstName != '' && $pLastName != '' && $wFirstName != '' && $wLastName != '') {

            $report = mysqli_query($connection, "SELECT en_task.id, posterUser.first_name AS posterFirstName, posterUser.last_name AS posterLastName, workerUser.first_name AS workerFirstName, workerUser.last_name AS workerLastName, en_task.title, en_task.due_date FROM en_task LEFT JOIN en_user AS posterUser ON posterUser.id = en_task.poster_id LEFT JOIN en_user AS workerUser ON workerUser.id = en_task.worker_id WHERE posterUser.first_name = '$pFirstName' AND posterUser.LAST_name = '$pLastName' AND workerUser.first_name = '$wFirstName' AND workerUser.last_name = '$wLastName' order by en_task.id DESC");
        } elseif ($fromDate != '' && $toDate != '' && $pFirstName != '' && $pLastName != '' && $wFirstName == '' && $wLastName == '') {

            $report = mysqli_query($connection, "SELECT en_task.id, posterUser.first_name AS posterFirstName, posterUser.last_name AS posterLastName, workerUser.first_name AS workerFirstName, workerUser.last_name AS workerLastName, en_task.title, en_task.due_date FROM en_task LEFT JOIN en_user AS posterUser ON posterUser.id = en_task.poster_id LEFT JOIN en_user AS workerUser ON workerUser.id = en_task.worker_id WHERE posterUser.first_name = '$pFirstName' AND posterUser.LAST_name = '$pLastName' AND en_task.due_date BETWEEN '" . $fromDate . "' AND '" . $toDate . "' order by en_task.id DESC");
        } elseif ($fromDate != '' && $toDate != '' && $pFirstName == '' && $pLastName == '' && $wFirstName != '' && $wLastName != '') {

            $report = mysqli_query($connection, "SELECT en_task.id, posterUser.first_name AS posterFirstName, posterUser.last_name AS posterLastName, workerUser.first_name AS workerFirstName, workerUser.last_name AS workerLastName, en_task.title, en_task.due_date FROM en_task LEFT JOIN en_user AS posterUser ON posterUser.id = en_task.poster_id LEFT JOIN en_user AS workerUser ON workerUser.id = en_task.worker_id WHERE workerUser.first_name = '$wFirstName' AND workerUser.last_name = '$wLastName' AND en_task.due_date BETWEEN '" . $fromDate . "' AND '" . $toDate . "' order by en_task.id DESC");
        }


        $html = '';

        while ($list = mysqli_fetch_assoc($report)) {

            if (!empty($list)) {
                $html .= '<tr><td>' . $list['title'] . '</td><td>' . $list['posterFirstName'] . ' ' . $list['posterLastName'] . '</td><td>' . $list['workerFirstName'] . ' ' . $list['workerLastName'] . '</td><td>' . $list['due_date'] . '</td>'
                        . '</tr>';
            } else {

                $html .= 'No Record Found.';
            }
        }

        $finalArray = array('html_data' => $html);

        echo json_encode($finalArray);
    }

    public function toExcel($fromDate = false, $toDate = false, $poster = false, $worker = false) {
        $connection = mysqli_connect('localhost', 'pcthepro_nitesh', 'project@2017', 'pcthepro_airen_tasker');
        if ($poster == 'pn') {
            $poster = '';
        }

        if ($fromDate == 'fd') {
            $fromDate = '';
        }

        if ($toDate == 'td') {
            $toDate = '';
        }

        if ($worker == 'wn') {
            $worker = '';
        }

        $poster = str_replace("%20", " ", $poster);
        $worker = str_replace("%20", " ", $worker);

        if ($poster != '') {
            $pExpd = explode(' ', $poster);
            $pFirstName = trim($pExpd[0]);
            $pLastName = trim($pExpd[1]);
        } else {
            $pFirstName = '';
            $pLastName = '';
        }
        if ($worker != '') {
            $wExpd = explode(' ', $worker);
            $wFirstName = trim($wExpd[0]);
            $wLastName = trim($wExpd[1]);
        } else {
            $wFirstName = '';
            $wLastName = '';
        }

        if ($fromDate != '' && $toDate != '' && $pFirstName != '' && $pLastName != '' && $wFirstName != '' && $wLastName != '') {

            $report = mysqli_query($connection, "SELECT en_task.id, posterUser.first_name AS posterFirstName, posterUser.last_name AS posterLastName, workerUser.first_name AS workerFirstName, workerUser.last_name AS workerLastName, en_task.title, en_task.due_date FROM en_task LEFT JOIN en_user AS posterUser ON posterUser.id = en_task.poster_id LEFT JOIN en_user AS workerUser ON workerUser.id = en_task.worker_id WHERE posterUser.first_name = '$pFirstName' AND posterUser.LAST_name = '$pLastName' AND workerUser.first_name = '$wFirstName' AND workerUser.last_name = '$wLastName' AND en_task.due_date BETWEEN '" . $fromDate . "' AND '" . $toDate . "' order by en_task.id DESC");
        } elseif ($fromDate != '' && $toDate != '' && $pFirstName == '' && $pLastName == '' && $wFirstName == '' && $wLastName == '') {


            $report = mysqli_query($connection, "SELECT en_task.id, posterUser.first_name AS posterFirstName, posterUser.last_name AS posterLastName, workerUser.first_name AS workerFirstName, workerUser.last_name AS workerLastName, en_task.title, en_task.due_date FROM en_task LEFT JOIN en_user AS posterUser ON posterUser.id = en_task.poster_id LEFT JOIN en_user AS workerUser ON workerUser.id = en_task.worker_id WHERE en_task.due_date BETWEEN '" . $fromDate . "' AND '" . $toDate . "' order by en_task.id DESC");
        } elseif ($fromDate == '' && $toDate == '' && $pFirstName != '' && $pLastName != '' && $wFirstName == '' && $wLastName == '') {


            $report = mysqli_query($connection, "SELECT en_task.id, posterUser.first_name AS posterFirstName, posterUser.last_name AS posterLastName, workerUser.first_name AS workerFirstName, workerUser.last_name AS workerLastName, en_task.title, en_task.due_date FROM en_task LEFT JOIN en_user AS posterUser ON posterUser.id = en_task.poster_id LEFT JOIN en_user AS workerUser ON workerUser.id = en_task.worker_id WHERE posterUser.first_name = '$pFirstName' AND posterUser.LAST_name = '$pLastName' order by en_task.id DESC");
        } elseif ($fromDate == '' && $toDate == '' && $pFirstName == '' && $pLastName == '' && $wFirstName != '' && $wLastName != '') {

            $report = mysqli_query($connection, "SELECT en_task.id, posterUser.first_name AS posterFirstName, posterUser.last_name AS posterLastName, workerUser.first_name AS workerFirstName, workerUser.last_name AS workerLastName, en_task.title, en_task.due_date FROM en_task LEFT JOIN en_user AS posterUser ON posterUser.id = en_task.poster_id LEFT JOIN en_user AS workerUser ON workerUser.id = en_task.worker_id WHERE workerUser.first_name = '$wFirstName' AND workerUser.last_name = '$wLastName' order by en_task.id DESC");
        } elseif ($fromDate == '' && $toDate == '' && $pFirstName != '' && $pLastName != '' && $wFirstName != '' && $wLastName != '') {

            $report = mysqli_query($connection, "SELECT en_task.id, posterUser.first_name AS posterFirstName, posterUser.last_name AS posterLastName, workerUser.first_name AS workerFirstName, workerUser.last_name AS workerLastName, en_task.title, en_task.due_date FROM en_task LEFT JOIN en_user AS posterUser ON posterUser.id = en_task.poster_id LEFT JOIN en_user AS workerUser ON workerUser.id = en_task.worker_id WHERE posterUser.first_name = '$pFirstName' AND posterUser.LAST_name = '$pLastName' AND workerUser.first_name = '$wFirstName' AND workerUser.last_name = '$wLastName' order by en_task.id DESC");
        } elseif ($fromDate != '' && $toDate != '' && $pFirstName != '' && $pLastName != '' && $wFirstName == '' && $wLastName == '') {

            $report = mysqli_query($connection, "SELECT en_task.id, posterUser.first_name AS posterFirstName, posterUser.last_name AS posterLastName, workerUser.first_name AS workerFirstName, workerUser.last_name AS workerLastName, en_task.title, en_task.due_date FROM en_task LEFT JOIN en_user AS posterUser ON posterUser.id = en_task.poster_id LEFT JOIN en_user AS workerUser ON workerUser.id = en_task.worker_id WHERE posterUser.first_name = '$pFirstName' AND posterUser.LAST_name = '$pLastName' AND en_task.due_date BETWEEN '" . $fromDate . "' AND '" . $toDate . "' order by en_task.id DESC");
        } elseif ($fromDate != '' && $toDate != '' && $pFirstName == '' && $pLastName == '' && $wFirstName != '' && $wLastName != '') {

            $report = mysqli_query($connection, "SELECT en_task.id, posterUser.first_name AS posterFirstName, posterUser.last_name AS posterLastName, workerUser.first_name AS workerFirstName, workerUser.last_name AS workerLastName, en_task.title, en_task.due_date FROM en_task LEFT JOIN en_user AS posterUser ON posterUser.id = en_task.poster_id LEFT JOIN en_user AS workerUser ON workerUser.id = en_task.worker_id WHERE workerUser.first_name = '$wFirstName' AND workerUser.last_name = '$wLastName' AND en_task.due_date BETWEEN '" . $fromDate . "' AND '" . $toDate . "' order by en_task.id DESC");
        }
        while ($list = mysqli_fetch_assoc($report)) {

            $dataaa['title'] = $list['title'];
            $dataaa['posterName'] = $list['posterFirstName'];
            $dataaa['workerName'] = $list['workerFirstName'];
            $dataaa['due_date'] = $list['due_date'];

            $datainfo[] = $dataaa;
        }
//        die('die');
        $info['allRecord'] = $datainfo;



        $this->load->view('spreadsheet_view', $info);
    }

}

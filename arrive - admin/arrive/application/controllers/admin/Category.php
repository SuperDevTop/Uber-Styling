<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Category extends MY_Controller {

    public function __construct() {
        parent::__construct();
    }

    public function index() {
        $data = array(
            'main_view' => 'category/index',
            'category' => $this->common->findAll('en_task_category', '*')
        );

        $categories = $data['category'];
//        $parentCat = array();
        foreach ($categories as $key => $value) {
            $parentId = $value['parent_id'];
            if ($parentId != 0) {
                $parent = $this->common->find('en_task_category', $select = 'cat_name', $where = array('id' => $parentId), $resultType = 'array', $orderby = array());
                $data['category'][$key]['parentName'] = $parent['cat_name'];
            } else {
                $data['category'][$key]['parentName'] = 'No Parent Category';
            }
        }

        $this->load->view('layout/main', $data);
    }

    public function form($id = false) {
        $data = array(
            'main_view' => 'category/form'
        );
        $this->load->library('form_validation');
        $data['all_en_category'] = $this->common->findAll('en_task_category', '*');
        if ($id > 0) {
            $data['all_en_category'] = $this->common->findAll('en_task_category', '*', array('id !=' => $id));
            $data['en_category'] = $this->common->find('en_task_category', '*', array('id' => $id));
            $data['hn_category'] = $this->common->find('hn_task_category', '*', array('id' => $id));
            $data['parent_cat'] = $this->common->find('en_task_category', 'id, cat_name', array('id' => $data['en_category']['parent_id']));
            $this->form_validation->set_rules('en_cat_name', 'English Category Name', 'trim|required');
            $this->form_validation->set_rules('hn_cat_name', 'Hindi Category Name', 'trim|required');

//            $this->form_validation->set_rules('cat_img', 'Category Image', 'required');
        } else {
            $this->form_validation->set_rules('en_cat_name', 'English Category Name', 'trim|required|callback_unique_category');
            $this->form_validation->set_rules('hn_cat_name', 'Hindi Category Name', 'trim|required');
//            $this->form_validation->set_rules('cat_img', 'Category Image', 'required');
            if (empty($_FILES['cat_img']['name'])) {
                $this->form_validation->set_rules('cat_img', 'Category Image', 'required');
            }
        }


        $this->form_validation->set_error_delimiters('<div class="text-danger">', '</div>');


        if ($this->form_validation->run() !== FALSE) {

            $category = $this->input->post();


            $enCategoryData = array(
                'cat_name' => $category['en_cat_name'],
                'parent_id' => $category['en_cat_name_drop'],
                'cat_header' => (($category['cat_header'] != '') ? $category['cat_header'] : '')
            );


            $hnCategoryData = array(
                'cat_name' => $category['hn_cat_name'],
                'parent_id' => $category['en_cat_name_drop'],
                'cat_header' => (($category['cat_header'] != '') ? $category['cat_header'] : '')
            );

            $config['file_name'] = '_category_' . time();
            $config['upload_path'] = './assets/upload/category';
            $config['allowed_types'] = 'gif|jpg|png|jpeg|PNG|JPG|JPEG|GIF';
            $config['max_size'] = '100000';
            $config['max_width'] = '2000000';
            $config['max_height'] = '2000000';
            $config['remove_spaces'] = TRUE;
            $config['encrypt_name'] = TRUE;

            $this->load->library('upload', $config);

            if (($_FILES['cat_img']['error']) == 0) {
                $this->upload->do_upload('cat_img');
                $imgData = array('upload_data' => $this->upload->data());

                $enCategoryData['cat_img'] = trim($imgData['upload_data']['file_name']);
                $hnCategoryData['cat_img'] = trim($imgData['upload_data']['file_name']);
            } else {
                $error = array('error' => $this->upload->display_errors());
                echo $error['error'];
            }

            if (!empty($data['en_category'])) {
                $enCategoryData['id'] = $data['en_category']['id'];
            }
            if (!empty($data['hn_category'])) {
                $hnCategoryData['id'] = $data['hn_category']['id'];
            }

            $this->common->save('en_task_category', $enCategoryData);
            $this->common->save('hn_task_category', $hnCategoryData);
            $this->session->set_flashdata('message', 'CategoryData has been saved');
            redirect(base_url('admin/category'));
        }

        $this->load->view('layout/main', $data);
    }

    public function view($id) {
        $data = array(
            'main_view' => 'category/view',
            'category' => $this->common->find('en_task_category', $select = '*', $where = array('id' => $id), $resultType = 'array', $orderby = array()),
        );
        $data['subCategory'] = $this->common->find('en_task_category', $select = 'cat_name', $where = array('id' => $data['category']['parent_id']), $resultType = 'array', $orderby = array());
        $this->load->view('layout/main', $data);
    }

    public function active($id) {

        $data = array('status' => '1');

        $this->db->where('id', $id);
        $this->db->update('en_task_category', $data);
        $this->db->where('id', $id);
        $this->db->update('hn_task_category', $data);

        redirect(base_url('admin/category'));
    }

    public function deactive($id) {
        $data = array('status' => '0');
        $this->db->where('id', $id);
        $this->db->update('en_task_category', $data);
        $this->db->where('id', $id);
        $this->db->update('hn_task_category', $data);

        redirect(base_url('admin/category'));
    }

    public function unique_category($en_cat_name) {

        $categoryIsExist = $this->common->findAll('en_task_category', $select = 'cat_name', $where = array('cat_name' => $en_cat_name), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');

        if (!empty($categoryIsExist)) {
            $this->form_validation->set_message('unique_category', 'Category is already exist.');
            return FALSE;
        } else {
            return TRUE;
        }
    }

}

?>

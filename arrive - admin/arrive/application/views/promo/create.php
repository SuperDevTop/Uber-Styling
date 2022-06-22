
<!DOCTYPE html>
<html class=" no-touch">
<link rel="stylesheet" href="<?php echo base_url('assets/') ?>css/example-styles.css">

    <body class="with-side-menu chrome-browser">
        <div class="page-content">
            <div class="container-fluid">
                <header class="section-header">
                    <div class="tbl">
                        <div class="tbl-row">
                            <div class="tbl-cell">
                                <?php
                                echo isset($promo) ? '<h3>Edit promo</h3>' : '<h3>Add Promo</h3>';
                                ?>
                            </div>
                        </div>
                    </div>
                </header>
                <section class="card">
                    <div class="card-block">
                        <div class="row">
                            <div class="col-md-6">

                                <?php
                                $attributes = array('id' => 'form', 'class' => 'form-horizontal');
                                echo form_open_multipart('', $attributes);
                                ?>

                                <fieldset class="form-group">
                                    <label class="form-label">Select User*</label>
                                    <?php
                                    
                                    foreach ($users as $key => $value) {
                                        $options[$value['id']]=$value['name'].' '.$value['email'];
                                    }
                                                                               
                                    echo form_multiselect('user_list[]',$options,'','class="form-control" id="user"');
                                    ?>
                                    <?php echo form_error('user_list[]'); ?>
                                </fieldset>
                           

                                <fieldset class="form-group">
                                    <label class="form-label">Promo Name*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'promo_name',
                                        'class' => 'form-control',
                                        'value' => isset($promo['promo_name']) ? $promo['promo_name'] : set_value('promo_name')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('promo_name'); ?>
                                </fieldset>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">


                                 <fieldset class="form-group">
                                    <label class="form-label">Promo Code*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'promo_code',
                                        'class' => 'form-control',
                                        'value' => isset($promo['promo_code']) ? $promo['promo_code'] : set_value('promo_code'),
                                        isset($promo['promo_code']) ? "readonly" : '' => ''
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('promo_code'); ?>
                                </fieldset>

                                <fieldset class="form-group">
                                    <label class="form-label">Discount*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'discount',
                                        'class' => 'form-control',
                                        'value' => isset($promo['discount']) ? $promo['discount'] : set_value('discount'),                                        
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('discount'); ?>
                                </fieldset>
                         
                              
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <fieldset class="form-group">
                                    <label class="form-label">Valid Form*</label>
                                    <?php

                                    $data = array(
                                        'name' => 'valid_form',
                                        'id' => 'valid_form',
                                        'type'=>'text',
                                        'class' => 'form-control datepicker',
                                        'value' => isset($promo['valid_from']) ? date("d/m/Y",strtotime($promo['valid_from'])) : set_value('valid_from'),
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('valid_form'); ?>
                                </fieldset>

                                <fieldset class="form-group">
                                    <label class="form-label">Valid To*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'valid_to',
                                        'id' => 'valid_to',
                                        'type'=>'text',
                                        'class' => 'form-control datepicker',
                                        'value' => isset($promo['valid_to']) ? date("d/m/Y",strtotime($promo['valid_to'])) : set_value('valid_to')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('valid_to'); ?>
                                    </fieldset><fieldset class="form-group">
                                        <label class="form-label">Status*</label>
                                        <?php
                                       
                                        $data_radio1 = array(
                                            'name' => 'status',
                                            'value' => '0',
                                            'checked' => isset($promo['status']) ? ($promo['status']==0 ? true : false) : set_value('status')
                                        );

                                        $data_radio2 = array(
                                            'name' => 'status',
                                            'value' => '1',
                                            'checked'=>isset($promo['status']) ? ($promo['status']==1 ? true : false) : set_value('status')
                                        );
                                        echo form_radio($data_radio1);
                                        echo form_label('Inactive','status',['style'=>'display:inline;margin-right:5px']);
                                        echo form_radio($data_radio2);
                                        echo form_label('Active','status',['style'=>'display:inline;margin-right:5px']  );
                                        ?>
                                        <?php echo form_error('status'); ?>
                                    </fieldset>
                            </div>


                        </div>

                        <fieldset class="form-group">
                            <?php
                            $link = site_url('/admin/promo');
                            echo isset($promo) ? ' <button type="submit" class="btn">Edit</button> <a class="btn" href= "' . $link . '" >Cancel</a>' : ' <button type="submit" class="btn">Add</button>';
                            ?>

                        </fieldset>

                        <?php echo form_close(); ?>

                    </div>
                </section>
            </div><!--.container-fluid-->
        </div><!--.page-content-->

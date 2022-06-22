
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
                                echo isset($ngo) ? '<h3>Edit ngo</h3>' : '<h3>Add ngo</h3>';
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
                                    <label class="form-label">Ngo Name*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'ngo_name',
                                        'class' => 'form-control',
                                        'value' => isset($ngo['name']) ? $ngo['name'] : set_value('name')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('ngo_name'); ?>
                                </fieldset>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">


                                 <fieldset class="form-group">
                                    <label class="form-label">Ngo Title*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'ngo_title',
                                        'class' => 'form-control',
                                        'value' => isset($ngo['title']) ? $ngo['title'] : set_value('title'),
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('ngo_title'); ?>
                                </fieldset>

                                <fieldset class="form-group">
                                    <label class="form-label">Amount*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'amount',
                                        'class' => 'form-control',
                                        'value' => isset($ngo['amount']) ? $ngo['amount'] : set_value('amount'),                                        
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('amount'); ?>
                                </fieldset>
                         
                              
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <fieldset class="form-group">
                                    <label class="form-label">Account No*</label>
                                    <?php

                                    $data = array(
                                        'name' => 'account_no',
                                        'class' => 'form-control',
                                        'value' => isset($ngo['account_no']) ? $ngo['account_no'] : set_value('account_no'),  
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('account_no'); ?>
                                </fieldset>

                                <fieldset class="form-group">
                                    <label class="form-label">Description*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'description',
                                        'class' => 'form-control',
                                        'value' => isset($ngo['description']) ? $ngo['description'] : set_value('description'),
                                    );
                                    echo form_textarea($data);
                                    ?>
                                    <?php echo form_error('description'); ?>
                                    </fieldset><fieldset class="form-group">
                                        <label class="form-label">Status*</label>
                                        <?php
                                       
                                        $data_radio1 = array(
                                            'name' => 'status',
                                            'value' => '0',
                                            'checked' => isset($ngo['status']) ? ($ngo['status']==0 ? true : false) : set_value('status')
                                        );

                                        $data_radio2 = array(
                                            'name' => 'status',
                                            'value' => '1',
                                            'checked'=>isset($ngo['status']) ? ($ngo['status']==1 ? true : false) : set_value('status')
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
                            $link = site_url('/admin/ngo');
                            echo isset($ngo) ? ' <button type="submit" class="btn">Edit</button> <a class="btn" href= "' . $link . '" >Cancel</a>' : ' <button type="submit" class="btn">Add</button>';
                            ?>

                        </fieldset>

                        <?php echo form_close(); ?>

                    </div>
                </section>
            </div><!--.container-fluid-->
        </div><!--.page-content-->

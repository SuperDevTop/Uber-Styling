<?php
//echo'<pre>';
//print_r($credit);
//die();
?>
<!DOCTYPE html>
<html class=" no-touch">


    <body class="with-side-menu chrome-browser">

        <div class="page-content">
            <div class="container-fluid">
                <header class="section-header">
                    <div class="tbl">
                        <div class="tbl-row">
                            <div class="tbl-cell">
                                <?php
                                echo isset($user) ? '<h3>Edit High paying zone</h3>' : '<h3>Add High paying zone</h3>';
                                ?>


                            </div>
                        </div>
                    </div>

                     <div class="tbl">
                        <div class="tbl-row">
                            <div class="tbl-cell">
                               


                            </div>
                        </div>
                    </div>
                </header>

                <section class="card">
                    <div class="card-block">
                        <div class="row">
                             <div class="col-md-7">
                            <div class="col-md-6">

                                <?php
                                $attributes = array('id' => 'form', 'class' => 'form-horizontal');
                                echo form_open_multipart('', $attributes);
                                ?>

                                <fieldset class="form-group">
                                    <label class="form-label">Zone name*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'zone_name',
                                        'id' => 'zone_name',
                                        'class' => 'form-control',
                                        'value' => isset($user['zone_name']) ? $user['zone_name'] : set_value('zone_name')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('zone_name'); ?>
                                </fieldset>
                            </div>
                            <div class="col-md-6">


                                <fieldset class="form-group">
                                    <label class="form-label">High By*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'high_by',
                                        'id' => 'high_by',
                                        'class' => 'form-control',
                                        'value' => isset($user['high_by']) ? $user['high_by'] : set_value('high_by')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('high_by'); ?>
                                </fieldset>
                            </div>

                            



                       
                             <div class="col-md-6">

                                <fieldset class="form-group">
                                    <label class="form-label">Latitude A*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'latitude1',
                                        'id' => 'latitude1',
                                        'class' => 'form-control',
                                        'value' => isset($user['latitude1']) ? $user['latitude1'] : set_value('latitude1')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('latitude1'); ?>
                                </fieldset>
                            </div>
                            <div class="col-md-6">


                                <fieldset class="form-group">
                                    <label class="form-label">longitude A*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'longitude1',
                                        'id' => 'longitude1',
                                        'class' => 'form-control',
                                        'value' => isset($user['longitude1']) ? $user['longitude1'] : set_value('longitude1')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('longitude1'); ?>
                                </fieldset>
                            </div>
                            

                        

                        
                            <div class="col-md-6">

                                <fieldset class="form-group">
                                    <label class="form-label">latitude B*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'latitude2',
                                        'id' => 'latitude2',
                                        'class' => 'form-control',
                                        'value' => isset($user['latitude2']) ? $user['latitude2'] : set_value('latitude2')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('latitude2'); ?>
                                </fieldset>
                            </div>
                            <div class="col-md-6">


                                <fieldset class="form-group">
                                    <label class="form-label">longitude B*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'longitude2',
                                        'id' => 'longitude2',
                                        'class' => 'form-control',
                                        'value' => isset($user['longitude2']) ? $user['longitude2'] : set_value('longitude2')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('longitude2'); ?>
                                </fieldset>
                            </div>
                            

                        
                             <div class="col-md-6">

                                <fieldset class="form-group">
                                    <label class="form-label">latitude C*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'latitude3',
                                        'id' => 'latitude3',
                                        'class' => 'form-control',
                                        'value' => isset($user['latitude3']) ? $user['latitude3'] : set_value('latitude3')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('latitude3'); ?>
                                </fieldset>
                            </div>
                            <div class="col-md-6">


                                <fieldset class="form-group">
                                    <label class="form-label">longitude C*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'longitude3',
                                        'id' => 'longitude3',
                                        'class' => 'form-control',
                                        'value' => isset($user['longitude3']) ? $user['longitude3'] : set_value('longitude3')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('longitude3'); ?>
                                </fieldset>
                            </div>
                            

                     
                             <div class="col-md-6">

                                <fieldset class="form-group">
                                    <label class="form-label">latitude D*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'latitude4',
                                        'id' => 'latitude4',
                                        'class' => 'form-control',
                                        'value' => isset($user['latitude4']) ? $user['latitude4'] : set_value('latitude4')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('latitude4'); ?>
                                </fieldset>
                            </div>
                            <div class="col-md-6">


                                <fieldset class="form-group">
                                    <label class="form-label">longitude D*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'longitude4',
                                        'id' => 'longitude4',
                                        'class' => 'form-control',
                                        'value' => isset($user['longitude4']) ? $user['longitude4'] : set_value('longitude4')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('longitude4'); ?>
                                </fieldset>
                            </div>
                        </div>
                        <div class="col-md-5">
                            <img src="<?PHP echo base_url("/assets/img/Rectangle.PNG")?>" height="400" width="400">
                        </div>

                            

                       
                       
                       

                        

                        <fieldset class="form-group">
                            <?php
                            $link = site_url('/admin/highpayingzone');
                            echo isset($user) ? ' <button type="submit" class="btn">Edit</button> <button type="submit" class="btn" href= "' . $link . '" >Cancel</button>' : ' <button type="submit" class="btn">Add</button>';
                            ?>

                        </fieldset>

                        <?php echo form_close(); ?>

                    </div>
                </section>
            </div><!--.container-fluid-->
        </div><!--.page-content-->


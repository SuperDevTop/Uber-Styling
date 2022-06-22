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
                                echo isset($driver) ? '<h3>Edit Driver</h3>' : '<h3>Add Driver</h3>';
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
                                    <label class="form-label">First name*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'first_name',
                                        'id' => 'first_name',
                                        'class' => 'form-control',
                                        'value' => isset($driver['first_name']) ? $driver['first_name'] : set_value('first_name')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('first_name'); ?>
                                </fieldset>
                            </div>
                            <div class="col-md-6">

                                <fieldset class="form-group">
                                    <label class="form-label">Middle name*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'middle_name',
                                        'id' => 'middle_name',
                                        'class' => 'form-control',
                                        'value' => isset($driver['middle_name']) ? $driver['middle_name'] : set_value('middle_name')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('middle_name'); ?>
                                </fieldset>
                            </div>




                        </div>


                        <div class="row">
                            <div class="col-md-6">

                                <fieldset class="form-group">
                                    <label class="form-label">Last name*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'last_name',
                                        'id' => 'last_name',
                                        'class' => 'form-control',
                                        'value' => isset($driver['last_name']) ? $driver['last_name'] : set_value('last_name')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('last_name'); ?>
                                </fieldset>
                            </div>
                            <div class="col-md-6">


                                <fieldset class="form-group">
                                    <label class="form-label">Email*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'email',
                                        'id' => 'email',
                                        'class' => 'form-control',
                                        'value' => isset($driver['email']) ? $driver['email'] : set_value('email')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('email'); ?>
                                </fieldset>
                            </div>


                        </div>

                        <div class="row">
                            <div class="col-md-6">

                                <fieldset class="form-group">
                                    <label class="form-label">Password*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'password',
                                        'id' => 'password',
                                        'class' => 'form-control',
                                        'value' => isset($driver['password']) ? $driver['password'] : set_value('password')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('password'); ?>
                                </fieldset>
                            </div>
                            <div class="col-md-6">


                                <fieldset class="form-group">
                                    <label class="form-label">Mobile*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'mobile',
                                        'id' => 'mobile',
                                        'class' => 'form-control',
                                        'value' => isset($driver['mobile']) ? $driver['mobile'] : set_value('mobile')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('mobile'); ?>
                                </fieldset>
                            </div>


                        </div>

                        <fieldset class="form-group">
                            <?php
                            $link = site_url('/admin/user');
                            echo isset($driver) ? ' <button type="submit" class="btn">Edit</button> <button type="submit" class="btn" href= "' . $link . '" >Cancel</button>' : ' <button type="submit" class="btn">Add</button>';
                            ?>

                        </fieldset>

                        <?php echo form_close(); ?>

                    </div>
                </section>
            </div><!--.container-fluid-->
        </div><!--.page-content-->


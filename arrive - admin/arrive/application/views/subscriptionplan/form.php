<?php
//echo'<pre>';
//print_r($membership);
//die('die');
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
                                echo isset($membership) ? '<h3>Edit Subscription plan</h3>' : '<h3>Add Subscription plan</h3>';
                                ?>


                            </div>
                        </div>
                    </div>
                </header>

                <section class="card">
                    <div class="card-block">
                        <div class="row">
                            <div class="col-md-6">
                                <?php //print_r($category);   ?>
                                <?php
                                $attributes = array('id' => 'form', 'class' => 'form-horizontal');
                                echo form_open_multipart('', $attributes);
                                ?>

                                <fieldset class="form-group">
                                    <label class="form-label">Membership Name</label>
                                    <?php
                                    $data = array(
                                        'name' => 'membership_name',
                                        'id' => 'membership_name',
                                        'class' => 'form-control',
                                        'value' => isset($membership['membership_name']) ? $membership['membership_name'] : set_value('membership_name')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('membership_name'); ?>
                                </fieldset>
                            </div>
                            <div class="col-md-6">
                                <fieldset class="form-group">
                                    <label class="form-label">Membership Purchase Amount</label>
                                    <?php
                                    $data = array(
                                        'name' => 'purchase_amount',
                                        'id' => 'purchase_amount',
                                        'class' => 'form-control',
                                        'value' => isset($membership['purchase_amount']) ? $membership['purchase_amount'] : set_value('purchase_amount')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('purchase_amount'); ?>
                                </fieldset>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <fieldset class="form-group">
                                    <label class="form-label">Membership Description</label>

                                    <div class="controls col-md-14">

                                        <textarea class="summernote" name="description" style="width: 100px;"><?php echo isset($membership['description']) ? $membership['description'] : set_value('description'); ?> </textarea>
                                    </div>

                                    <?php echo form_error('description'); ?>
                                </fieldset>






                                <fieldset class="form-group">
                                    <?php
                                    echo isset($membership) ? ' <button type="submit" class="btn">Edit</button>' : ' <button type="submit" class="btn">Add</button>';
                                    ?>

                                </fieldset>
                                </form>
                                <?php echo form_close(); ?>
                            </div>

                        </div><!--.row-->
                    </div>
                </section>
            </div><!--.container-fluid-->
        </div><!--.page-content-->



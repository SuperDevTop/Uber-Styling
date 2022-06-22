<?php
//echo'<pre>';
//print_r($en_email_template);
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
                                echo isset($en_email_template) ? '<h3>Edit Email Template</h3>' : '<h3>Add Email Template</h3>';
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
                                    <label class="form-label">Template Name</label>
                                    <?php
                                    $data = array(
                                        'name' => 'template_name',
                                        'id' => 'template_name',
                                        'class' => 'form-control',
                                        'value' => isset($en_email_template['template_name']) ? $en_email_template['template_name'] : set_value('template_name')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('template_name'); ?>
                                </fieldset>
                            </div>
                            <div class="col-md-6">
                                <fieldset class="form-group">
                                    <label class="form-label">Template Subject</label>
                                    <?php
                                    $data = array(
                                        'name' => 'template_subject',
                                        'id' => 'template_subject',
                                        'class' => 'form-control',
                                        'value' => isset($en_email_template['template_subject']) ? $en_email_template['template_subject'] : set_value('template_subject')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('template_subject'); ?>
                                </fieldset>
                            </div>
                        </div>
                        <div class="row">

                            <div class="col-md-6">
                                <fieldset class="form-group">
                                    <label class="form-label">Template Description</label>

                                    <div class="controls col-md-14">

                                        <textarea class="summernote" name="template_description" style="width: 100px;"><?php echo isset($en_email_template['template_description']) ? $en_email_template['template_description'] : set_value('template_description'); ?> </textarea>
                                    </div>

                                    <?php echo form_error('template_description'); ?>
                                </fieldset>



                                <fieldset class="form-group">
                                    <?php
                                    echo isset($en_email_template) ? ' <button type="submit" class="btn">Edit</button>' : ' <button type="submit" class="btn">Add</button>';
                                    ?>

                                </fieldset>
                                </form>
                                <?php echo form_close(); ?>


                            </div><!--.row-->
                        </div>
                </section>
            </div><!--.container-fluid-->
        </div><!--.page-content-->



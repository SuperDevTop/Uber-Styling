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
                                echo isset($content) ? '<h3>Edit Content</h3>' : '<h3>Add Content</h3>';
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
                                    <label class="form-label">Title</label>
                                    <?php
                                    $data = array(
                                        'name' => 'title',
                                        'id' => 'title',
                                        'class' => 'form-control',
                                        'value' => isset($content['title']) ? $content['title'] : set_value('title')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('title'); ?>
                                </fieldset>


                                <fieldset class="form-group">
                                    <label class="form-label">Description</label>

                                    <div class="controls col-md-14">

                                        <textarea class="summernote" name="description" style="width: 100px;"><?php echo isset($content['description']) ? $content['description'] : set_value('description'); ?> </textarea>
                                    </div>

                                    <?php echo form_error('description'); ?>
                                </fieldset>



                                <fieldset class="form-group">
                                    <?php
                                    echo isset($content) ? ' <button type="submit" class="btn">Edit</button>' : ' <button type="submit" class="btn">Add</button>';
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



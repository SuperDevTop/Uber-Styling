<?php
//foreach ($all_en_category as $key => $value) {
//    echo'<pre>';
//    print_r($value);
//}
//echo'<pre>';
//print_r($en_category);
//echo'<pre>';
//print_r($parent_cat);
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
                                echo isset($en_category) ? '<h3>Edit Category</h3>' : '<h3>Add Category</h3>';
                                ?>


                            </div>
                        </div>
                    </div>
                </header>

                <section class="card">
                    <div class="card-block">
                        <div class="row">
                            <div class="col-md-6">
                                <?php //print_r($category);     ?>
                                <?php
                                $attributes = array('id' => 'form', 'class' => 'form-horizontal');
                                echo form_open_multipart('', $attributes);
                                ?>

                                <!--                                <fieldset class="form-group">
                                                                    <label class="form-label">Parent Category(English)</label>
                                <?php
//                                    $data = array(
//                                        'name' => 'en_cat_name',
//                                        'id' => 'en_cat_name',
//                                        'class' => 'form-control',
//                                        'value' => isset($en_category['cat_name']) ? $en_category['cat_name'] : set_value('cat_name')
//                                    );
//                                    echo form_input($data);
                                ?>
                                                                </fieldset>-->
                                <fieldset class="form-group">
                                    <label class="form-label">Category(English)</label>
                                    <?php
                                    $data = array(
                                        'name' => 'en_cat_name',
                                        'id' => 'en_cat_name',
                                        'class' => 'form-control',
                                        'value' => isset($en_category['cat_name']) ? $en_category['cat_name'] : set_value('cat_name')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('en_cat_name'); ?>
                                </fieldset>
                            </div>
                            <div class="col-md-6">
                                <fieldset class="form-group">
                                    <label class="form-label">Parent Category</label>

                                    <select name="en_cat_name_drop" id="en_cat_name_drop" class="form-control">

                                        <option value="">-- None --</option>

                                        <?php
                                        foreach ($all_en_category as $key => $value) {
                                            ?>
                                            <option value="<?php echo $value['id']; ?>"
                                            <?php
                                            if (isset($parent_cat['id'])) {
                                                if ($parent_cat['id'] == $value['id']) {
                                                    echo "selected";
                                                }
                                            }
                                            ?>>
                                                <?php echo $value['cat_name']; ?></option>
                                        <?php }
                                        ?>
                                    </select>



                                </fieldset>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-6">



                                <fieldset class="form-group">
                                    <label class="form-label">Category(Hindi)</label>
                                    <?php
                                    $data = array(
                                        'name' => 'hn_cat_name',
                                        'id' => 'hn_cat_name',
                                        'class' => 'form-control',
                                        'value' => isset($hn_category['cat_name']) ? $hn_category['cat_name'] : set_value('cat_name')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('hn_cat_name'); ?>
                                </fieldset>
                            </div>
                            <div class="col-md-6">
                                <fieldset class="form-group">
                                    <label class="form-label">Image</label>
                                    <input name="cat_img" type="file" class="form-control" value="">
                                    <?php
                                    if (isset($en_category)) {
                                        if (!empty($en_category['cat_img'])) {
                                            ?>
                                            <img src="<?php echo base_url() . 'assets/upload/category/' . $en_category['cat_img']; ?>" width="150">
        <!--                                            <span class="panel-control-icon glyphicon glyphicon-remove" onclick="removeImg()"></span>-->

                                            <a data-func="close" data-tooltip="Close"><i class="panel-control-icon glyphicon glyphicon-remove"></i><span class="control-title"></span></a>
                                            <?php
                                        }
                                    }
//                                    else {
//                                        echo form_error('cat_img');
//                                    }
                                    ?>

                                </fieldset>

                            </div>

                        </div><!--.row-->





                        <div class="row">
                            <div class="col-md-6">

                                <fieldset class="form-group">
                                    <label class="form-label">Category Header</label>
                                    <?php
                                    $data = array(
                                        'name' => 'cat_header',
                                        'id' => 'cat_header',
                                        'class' => 'form-control',
                                        'value' => isset($en_category['cat_header']) ? $en_category['cat_header'] : set_value('cat_header')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('cat_header'); ?>
                                </fieldset>
                            </div>


                        </div><!--.row-->
                        <fieldset class="form-group">
                            <?php
                            $link = site_url('/admin/category');
                            echo isset($en_category) ? ' <button type="submit" class="btn">Edit</button> <button type="submit" class="btn" href= "' . $link . '" >Cancel</button>' : ' <button type="submit" class="btn">Add</button>';
                            ?>

                        </fieldset>

                        <?php echo form_close(); ?>

                    </div>
                </section>
            </div><!--.container-fluid-->
        </div><!--.page-content-->

        <script>
            function removeImg() {
                alert();
            }
        </script>
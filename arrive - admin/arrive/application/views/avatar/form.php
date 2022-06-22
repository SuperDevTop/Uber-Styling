<?php
//echo'<pre>';
//print_r($avatar);
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
                                echo isset($avatar) ? '<h3>Edit Avatar</h3>' : '<h3>Add Avatar</h3>';
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
                                    <label class="form-label">Name*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'name',
                                        'id' => 'name',
                                        'class' => 'form-control',
                                        'value' => isset($avatar['name']) ? $avatar['name'] : set_value('name')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('name'); ?>
                                </fieldset>
                            </div>
                            <div class="col-md-6">
                                <fieldset class="form-group">
                                    <label class="form-label">Image*</label>
                                    <input name="img" type="file" class="form-control" value="">
                                    <?php
                                    if (isset($avatar)) {
                                        if (!empty($avatar['img'])) {
                                            ?>
                                            <div id = "removeDiv">
                                                <img src="<?php echo base_url() . 'assets/upload/avatar/' . $avatar['img']; ?>" width="150">

                        <!--                                                <a href="javascript:void(0)" onclick="RemoveImg(<?php echo $avatar['id']; ?>)" >Remove</a>-->

                                            </div>
                                            <?php
                                        }
                                    } else {
                                        echo form_error('img');
                                    }
                                    ?>

                                </fieldset>

                            </div>

                        </div>




                        <fieldset class="form-group">
                            <?php
                            $link = site_url('/admin/avatar');
                            echo isset($avatar) ? ' <button type="submit" class="btn">Edit</button> <button type="submit" class="btn" href= "' . $link . '" >Cancel</button>' : ' <button type="submit" class="btn">Add</button>';
                            ?>

                        </fieldset>

                        <?php echo form_close(); ?>

                    </div>
                </section>
            </div><!--.container-fluid-->
        </div><!--.page-content-->

        <script>
            function RemoveImg(avatarId) {
//                alert();
//                var divData = '#removeDiv' + divId;

                var dataString = 'avatarId=' + avatarId;
//                $(divData).hide()


                $('#removeDiv').hide();
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "<?php echo site_url('admin/avatar/remove_img/' . $avatar['id']) ?>/",
                    data: dataString,
                    cache: false,
                    async: false,
                    success: function (result) {
                        console.log(result);


                    }
                });



            }
        </script>
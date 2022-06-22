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
                                echo isset($credit) ? '<h3>Edit Credit</h3>' : '<h3>Add Credit</h3>';
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
                                    <label class="form-label">Number of credit*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'credits',
                                        'id' => 'credits',
                                        'class' => 'form-control',
                                        'value' => isset($credit['credits']) ? $credit['credits'] : set_value('credits')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('name'); ?>
                                </fieldset>
                            </div>

                            <div class="col-md-6">


                                <fieldset class="form-group">
                                    <label class="form-label">Amount*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'amount',
                                        'id' => 'amount',
                                        'class' => 'form-control',
                                        'value' => isset($credit['amount']) ? $credit['amount'] : set_value('amount')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('name'); ?>
                                </fieldset>
                            </div>
                        </div>


                        <div class="row">
                            <div class="col-md-6">

                                <fieldset class="form-group">
                                    <label class="form-label">Validity*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'validity',
                                        'id' => 'validity',
                                        'class' => 'form-control',
                                        'value' => isset($credit['validity']) ? $credit['validity'] : set_value('validity')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('name'); ?>
                                </fieldset>
                            </div>


                        </div>

                        <fieldset class="form-group">
                            <?php
                            $link = site_url('/admin/credit');
                            echo isset($credit) ? ' <button type="submit" class="btn">Edit</button> <button type="submit" class="btn" href= "' . $link . '" >Cancel</button>' : ' <button type="submit" class="btn">Add</button>';
                            ?>

                        </fieldset>

                        <?php echo form_close(); ?>

                    </div>
                </section>
            </div><!--.container-fluid-->
        </div><!--.page-content-->

        <script>
            function RemoveImg(creditId) {
//                alert();
//                var divData = '#removeDiv' + divId;

                var dataString = 'creditId=' + creditId;
//                $(divData).hide()


                $('#removeDiv').hide();
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "<?php echo site_url('admin/credit/remove_img/' . $credit['id']) ?>/",
                    data: dataString,
                    cache: false,
                    async: false,
                    success: function (result) {
                        console.log(result);


                    }
                });



            }
        </script>
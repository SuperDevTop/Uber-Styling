<?php
//echo'<pre>';
//print_r($vehicle);
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
                                echo  '<h3>Edit Vehicle Class</h3>';
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
                                $attributes = array('id' => 'classform', 'class' => 'form-horizontal');
								//print_r($vehicle);
                                echo form_open_multipart('', $attributes);
								
                                ?>

                                <fieldset class="form-group">
                                    <label class="form-label">Vehicle Model*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'vehicle_model',
                                        'id' => 'vehicle_model',
                                        'class' => 'form-control',
										'readonly' => 'readonly',
                                        'value' => isset($vehicle['vehicle_model']) ? $vehicle['vehicle_model'] : set_value('vehicle_model')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('vehicle_model'); ?>
                                </fieldset>
                                
                                <fieldset class="form-group">
                                    <label class="form-label">Base Price*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'base_price',
                                        'id' => 'base_price',
                                        'class' => 'form-control',
										'type'=>'number',
                                        'value' => isset($vehicle['base_price']) ? $vehicle['base_price'] : set_value('base_price')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('base_price'); ?>
                                </fieldset>
                                <fieldset class="form-group">
                                    <label class="form-label">Booking Fare Price*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'booking_fare',
                                        'id' => 'booking_fare',
                                        'class' => 'form-control',
										'type'=>'number',
                                        'value' => isset($vehicle['booking_fare']) ? $vehicle['booking_fare'] : set_value('booking_fare')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('booking_fare'); ?>
                                </fieldset>
                                <fieldset class="form-group">
                                    <label class="form-label">Minimum Fare Price*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'minimum_fare',
                                        'id' => 'minimum_fare',
                                        'class' => 'form-control',
										'type'=>'number',
                                        'value' => isset($vehicle['minimum_fare']) ? $vehicle['minimum_fare'] : set_value('minimum_fare')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('minimum_fare'); ?>
                                </fieldset>
                                <fieldset class="form-group">
                                    <label class="form-label">Charge/Minut Price*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'charge_per_min',
                                        'id' => 'charge_per_min',
                                        'class' => 'form-control',
										'type'=>'number',
                                        'value' => isset($vehicle['charge_per_min']) ? $vehicle['charge_per_min'] : set_value('charge_per_min')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('charge_per_min'); ?>
                                </fieldset>
                                <fieldset class="form-group">
                                    <label class="form-label">Charge/Mil Price*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'charge_per_mile',
                                        'id' => 'charge_per_mile',
                                        'class' => 'form-control',
										'type'=>'number',
                                        'value' => isset($vehicle['charge_per_mile']) ? $vehicle['charge_per_mile'] : set_value('charge_per_mile')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('charge_per_mile'); ?>
                                </fieldset>
                                <fieldset class="form-group">
                                    <label class="form-label">Capacity*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'vehicle_capacity',
                                        'id' => 'vehicle_capacity',
                                        'class' => 'form-control',
										'type'=>'number',
                                        'value' => isset($vehicle['vehicle_capacity']) ? $vehicle['vehicle_capacity'] : set_value('vehicle_capacity')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('vehicle_capacity'); ?>
                                </fieldset>
                                <fieldset class="form-group">
                                    <label class="form-label">Door*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'vehicle_door',
                                        'id' => 'vehicle_door',
                                        'class' => 'form-control',
										'type'=>'number',
                                        'value' => isset($vehicle['vehicle_door']) ? $vehicle['vehicle_door'] : set_value('vehicle_door')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('vehicle_door'); ?>
                                </fieldset>
                               
                            </div>
                        </div>




                        <fieldset class="form-group">
                            <?php
                           
                            echo' <button type="submit" class="btn">Edit</button>';
                            ?>

                        </fieldset>

                        <?php echo form_close(); ?>

                    </div>
                </section>
            </div><!--.container-fluid-->
        </div><!--.page-content-->

        <script>
            function RemoveImg(vehicleId) {
//                alert();
//                var divData = '#removeDiv' + divId;

                var dataString = 'vehicleId=' + vehicleId;
//                $(divData).hide()


                $('#removeDiv').hide();
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "<?php echo site_url('admin/vehicle/remove_img/' . $vehicle['id']) ?>/",
                    data: dataString,
                    cache: false,
                    async: false,
                    success: function (result) {
                        console.log(result);


                    }
                });



            }
        </script>
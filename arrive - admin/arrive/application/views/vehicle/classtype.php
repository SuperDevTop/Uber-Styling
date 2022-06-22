<?php
//echo'<pre>';
//print_r($category);
//die();
?>
<style type="text/css">
    /*    .control-panel .page-content {
        padding-right: 12px;
    }*/
    .sortings::before,.sortings::after{
        content:"" !important;
    }
    .adjest{display: flex; flex-direction: row;}
    .adjest a{margin: 5px;}
</style>

<!DOCTYPE html>

<div class="page-content">
    <div class="container-fluid">
        <header class="mgb">
            <!--<header class="section-header mgb">-->
            <div class="tbl">
                <div class="tbl-row">
                    <div class="tbl-cell">
                        <h4>Vehicle
<!--                            <button class="btn btn-rounded pull-right" type="button" onclick="location.href = '<?php echo base_url(); ?>admin/vehicle/form'">Add</button>-->
                        </h4>
                    </div>
                </div>
            </div>
        </header>
        <section class="card">
            <div class="card-block">

                <table id="example" class="display table table-bordered" cellspacing="0" width="100%">
                    <thead>
                        <tr>
                            <th>Model</th>
                            <th>Base Price</th>
                            <th>Booking Fare</th>
                            <th>Min Fare</th>
                            <th>Charge/Minut</th>
                            <th>Charge/Mil</th>
                            <th>Capcity</th>
                            <th>Door</th>
                            <th>Status</th>
                            <th class="sortings">Action</th>
                        </tr>
                    </thead>

                    <tbody>
                        <?php
//                        $catDetail = site_url('category/catdetail');
                        foreach ($vehicle as $key => $value) {
                            ?>

                            <tr>
                                <td><?php echo ucfirst($value['vehicle_model']); ?></td>
                                <td><?php echo $value['base_price']; ?></td>
                                <td><?php echo $value['booking_fare']; ?></td>
                                <td><?php echo $value['minimum_fare']; ?></td>
                                <td><?php echo $value['charge_per_min']; ?></td>
                                <td><?php echo $value['charge_per_mile']; ?></td>
                                <td><?php echo $value['vehicle_capacity']; ?></td>
                                <td><?php echo $value['vehicle_door']; ?></td>
                                 <td><?php echo $value['active_flag']; ?></td>

                                <td class="adjest">
                                       <a class="btn btn-info" href="<?php echo base_url('admin/vehicle/classform/' . $value['id']); ?>" title="Edit"><i class="fa fa-pencil"></i></a> 

                                </td>

                            </tr>
                        <?php }
                        ?>


                    </tbody>
                </table>
            </div>
        </section>
    </div><!--.container-fluid-->
</div><!--.page-content-->

<script>

    $(function () {
        $('#example').DataTable({
            responsive: true
        });
    });




</script>

<?php
//echo'<pre>';
//print_r($users);
//die('die');
?>
<style>
    .sortings::before,.sortings::after{
        content:"" !important;
    }
</style>
<div class="page-content">
    <div class="container-fluid">
        <header class="mgb">
            <div class="tbl">
                <div class="tbl-row">
                    <div class="tbl-cell">
                        <h4>Booking
<!--                            <button class="btn btn-rounded pull-right" type="button" onclick="location.href = '<?php // echo base_url();                                              ?>admin/category/form'">Add</button>-->
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
                            <th>User Name</th>
                            <th>Schedule Date</th>
                            <th>Schedule Time</th>
                            <th>Booking Status</th>
                            <th class="sortings">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php
                        foreach ($booking as $key => $value) {
//                            echo'<pre>';
//                            print_r($value);
//                            die();
                            ?>

                            <tr>
                                <td><a href="<?php echo base_url('admin/user/view/' . $value['user_id']); ?>"><?php echo ucfirst($value['name']); ?></a></td>
                                <td><?php echo $value['schedule_date']; ?></td>
                                <td><?php echo $value['schedule_time']; ?></td>
                                <td><?php
                                    if ($value['mode'] == 0) {
                                        echo 'Sent request';
                                    } elseif ($value['mode'] == 1) {
                                        echo 'Driver accept booking request';
                                    } elseif ($value['mode'] == 2) {
                                        echo 'Driver reject booking request';
                                    } elseif ($value['mode'] == 3) {
                                        echo 'Process another driver';
                                    } elseif ($value['mode'] == 4) {
                                        echo 'Driver send chat request';
                                    } elseif ($value['mode'] == 5) {
                                        echo 'User accept chat request';
                                    } elseif ($value['mode'] == 6) {
                                        echo 'User reject chat request';
                                    } elseif ($value['mode'] == 7) {
                                        echo 'Booking accepted by user';
                                    } elseif ($value['mode'] == 8) {
                                        echo 'Booking reject user';
                                    } elseif ($value['mode'] == 9) {
                                        echo 'Booking confirm';
                                    } elseif ($value['mode'] == 10) {
                                        echo 'Ride start';
                                    } elseif ($value['mode'] == 11) {
                                        echo 'Ride finish';
                                    } elseif ($value['mode'] == 12) {
                                        echo 'Booking schedule for later';
                                    }
                                    ?></td>
                                <td>
                                    <a class="btn btn-info" href="<?php echo base_url('admin/booking/view/' . $value['bookingId']); ?>" title="View"><i class="fa fa-eye"></i></a>

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
//            responsive: true
            "scrollX": true
        });
    });
</script>

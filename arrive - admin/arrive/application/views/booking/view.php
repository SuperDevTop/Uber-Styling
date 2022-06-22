
<?php
//echo'<pre>';
//print_r($driverData);
//die;
?>


<div class="page-content">
    <div class="container-fluid">
        <section class="card">
            <div class="card-block">
                <table id="example" class="display table table-bordered" cellspacing="0" width="100%">

                    <tbody>
                        <tr>
                            <th>User Name</th>
                            <td><a href="<?php echo base_url('admin/user/view/' . $booking[0]['userId']); ?>"><?php echo ucfirst($booking[0]['name']); ?></a></td>
                        </tr>
                        <tr>
                            <th>User Email</th>
                            <td><?php echo $booking[0]['email']; ?></td>
                        </tr>
                        <tr>
                            <th>User Phone No.</th>
                            <?php if ($booking[0]['phone'] != '') { ?>
                                <td><?php echo $booking[0]['country_code'] . '-' . $booking[0]['phone']; ?></td>
                            <?php } else { ?>
                                <td><?php echo 'No phone number'; ?></td>
                            <?php } ?>
                        </tr>
                        <tr>
                            <th>Start point</th>
                            <td><?php echo $booking[0]['start_point']; ?></td>
                        </tr>
                        <tr>
                            <th>End point</th>
                            <td><?php echo $booking[0]['end_point']; ?></td>
                        </tr>
                        <tr>
                            <th>Schedule Date</th>
                            <td><?php echo $booking[0]['schedule_date']; ?></td>
                        </tr>
                        <tr>
                            <th>Schedule Time</th>
                            <td><?php echo $booking[0]['schedule_time']; ?></td>
                        </tr>
                        <?php if (!empty($driverData)) { ?>
                            <tr>
                                <th>Driver Name</th>
                                <td><a href="<?php echo base_url('admin/user/view/' . $driverData['driverId']); ?>"><?php echo ucfirst($driverData['name']); ?></a></td>
                            </tr>
                            <tr>
                                <th>Driver Email</th>
                                <td><?php echo $driverData['email']; ?></td>
                            </tr>
                            <tr>
                                <th>Driver Phone No</th>
                                <?php if ($driverData['phone'] != '') { ?>
                                    <td><?php echo $driverData['country_code'] . '-' . $driverData['phone']; ?></td>
                                <?php } else { ?>
                                    <td><?php echo 'No phone number'; ?></td>
                                <?php } ?>
                            </tr>
                        <?php } ?>
                        <tr>
                            <th>Status</th>
                            <td><?php
                                if ($booking[0]['mode'] == 0) {
                                    echo 'Sent request';
                                } elseif ($booking[0]['mode'] == 1) {
                                    echo 'Driver accept booking request';
                                } elseif ($booking[0]['mode'] == 2) {
                                    echo 'Driver reject booking request';
                                } elseif ($booking[0]['mode'] == 3) {
                                    echo 'Process another driver';
                                } elseif ($booking[0]['mode'] == 4) {
                                    echo 'Driver send chat request';
                                } elseif ($booking[0]['mode'] == 5) {
                                    echo 'User accept chat request';
                                } elseif ($booking[0]['mode'] == 6) {
                                    echo 'User reject chat request';
                                } elseif ($booking[0]['mode'] == 7) {
                                    echo 'Booking accepted by user';
                                } elseif ($booking[0]['mode'] == 8) {
                                    echo 'Booking reject user';
                                } elseif ($booking[0]['mode'] == 9) {
                                    echo 'Booking confirm';
                                } elseif ($booking[0]['mode'] == 10) {
                                    echo 'Ride start';
                                } elseif ($booking[0]['mode'] == 11) {
                                    echo 'Ride finish';
                                } elseif ($booking[0]['mode'] == 12) {
                                    echo 'Booking schedule for later';
                                }
                                ?></td>
                            <td>
                        </tr>


                    </tbody>
                </table>
            </div>
        </section>
    </div><!--.container-fluid-->
</div><!--.page-content-->


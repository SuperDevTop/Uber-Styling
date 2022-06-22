
<?php
//echo'<pre>';
//print_r($user);
?>


<div class="page-content">
    <div class="container-fluid">
        <section class="card">
            <div class="card-block">
                <table id="example" class="display table table-bordered" cellspacing="0" width="100%">

                    <tbody>
                        <tr>
                            <th>Name</th>
                            <td><?php echo ucfirst($driver['first_name']) . ' ' . $driver['last_name']; ?></td>
                        </tr>
                        <tr>
                            <th>Email</th>
                            <td><?php echo $driver['email']; ?></td>
                        </tr>
                        <tr>
                            <th>Mobile No.</th>
                            <?php if ($driver['mobile'] != '') { ?>
                                <td><?php echo $driver['mobile']; ?></td>
                            <?php } else { ?>
                                <td><?php echo 'No mobile number'; ?></td>
                            <?php } ?>
                        </tr>
                        <tr>
                            <th>Country</th>
                            <td><?php echo $driver['country']; ?></td>
                        </tr>

                        <tr>
                            <th>City</th>
                            <td><?php echo $driver['city']; ?></td>
                        </tr>
                        <tr>
                            <th>State</th>
                            <td><?php echo $driver['state']; ?></td>
                        </tr>
                        <tr>
                            <th>Dob</th>
                            <td><?php echo $driver['dob']; ?></td>
                        </tr>
                        <tr>
                            <th>Gender</th>
                            <td><?php echo $driver['gender']; ?></td>
                        </tr>
                        <tr>
                            <th>Image</th>
                            <td><?php if (!empty($driver['img'])) { ?><img src='<?php echo base_url(); ?>assets/upload/driver/<?php echo $driver['img']; ?>' height="42" width="42"><?php
                                } else {
                                    echo "No Image";
                                }
                                ?></td>

                        </tr>
                        <tr>
                            <th>Status</th>

                            <td><?php
                                if ($driver['status'] == 1) {
                                    echo 'Active';
                                } else {
                                    echo 'Deactive';
                                }
                                ?></td>

                        </tr>


                    </tbody>
                </table>
            </div>
        </section>
    </div><!--.container-fluid-->
</div><!--.page-content-->


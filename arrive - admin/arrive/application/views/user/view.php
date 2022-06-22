
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
                            <th>User Name</th>
                            <td><?php echo ucfirst($user['first_name']) . ' ' . $user['last_name']; ?></td>
                        </tr>
                        <tr>
                            <th>User Email</th>
                            <td><?php echo $user['email']; ?></td>
                        </tr>
                        <tr>
                            <th>User Mobile No.</th>
                            <?php if ($user['mobile'] != '') { ?>
                                <td><?php echo $user['mobile']; ?></td>
                            <?php } else { ?>
                                <td><?php echo 'No mobile number'; ?></td>
                            <?php } ?>
                        </tr>


                        <tr>
                            <th>User Image</th>
                            <td><?php if (!empty($user['img'])) { ?><img src='<?php echo base_url(); ?>assets/upload/user/<?php echo $user['img']; ?>' height="42" width="42"><?php
                                } else {
                                    echo "No Image";
                                }
                                ?></td>

                        </tr>
                        <tr>
                            <th>User Status</th>

                            <td><?php
                                if ($user['status'] == 1) {
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


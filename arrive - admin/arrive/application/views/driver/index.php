
<?php
//echo'<pre>';
//print_r($users);
//die('die');
?>
<style type="text/css">

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
                        <h4>Driver
                            <button class="btn btn-rounded pull-right" type="button" onclick="location.href = '<?php echo base_url(); ?>admin/driver/form'">Add</button>
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
                            <th>Name</th>
                            <th>Email</th>
                            <th>Mobile No.</th>
                            <th>Status</th>
                            <th class="sortings">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php
                        foreach ($drivers as $key => $value) {
                            ?>

                            <tr>
                                <td><?php echo ucfirst($value['first_name']) . ' ' . $value['middle_name'] . ' ' . $value['last_name']; ?></td>
                                <td><?php echo $value['email']; ?></td>
                                <?php if ($value['mobile'] != '') { ?>
                                    <td><?php echo $value['mobile']; ?></td>
                                <?php } else { ?>
                                    <td><?php echo 'No phone number'; ?></td>
                                <?php } ?>
                                <td><?php
                                    if ($value['status'] == 1) {
                                        echo 'Active';
                                    } else {
                                        echo 'Deactive';
                                    }
                                    ?></td>
                                <td>
                                    <a class="btn btn-info" href="<?php echo base_url('admin/driver/view/' . $value['id']); ?>" title="View"><i class="fa fa-eye"></i></a>
                                    <a class="btn btn-info" href="<?php echo base_url('admin/driver/form/' . $value['id']); ?>" title="Edit"><i class="fa fa-pencil"></i></a>
                                    <?php if ($value['status'] == '1') { ?>

                                        <a class="btn btn-info" onclick="return confirm('Are you sure want to Deactive status')" href="<?php echo base_url('admin/driver/deactive/' . $value['id']); ?>" title="Deactive"><i class="fa fa-remove"></i></a>
                                    <?php } else { ?>

                                        <a class="btn btn-info" onclick="return confirm('Are you sure want to Active status')" href="<?php echo base_url('admin/driver/active/' . $value['id']); ?>" title="Active"><i class="fa fa-check"></i></a>
                                        <?php } ?>
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

<?php // die();       ?>



<div class="page-content">
    <div class="container-fluid">
        <header class="mgb">
            <div class="tbl">
                <div class="tbl-row">
                    <div class="tbl-cell">
                        <h4>Task
<!--                            <button class="btn btn-rounded pull-right" type="button" onclick="location.href = '<?php // echo base_url();     ?>admin/task/form'">Add</button>-->
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
                            <th>Task Title</th>
                            <th>Task Location</th>
                            <th>Task Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php
//                        $catDetail = site_url('category/catdetail');
                        foreach ($task as $key => $value) {
                            ?>

                            <tr>
                                <td><?php echo $value['title']; ?></td>
                                <td><?php echo $value['location']; ?></td>

                                <td><?php
                                    if ($value['status'] == 1) {
                                        echo 'Active';
                                    } else {
                                        echo 'Deactive';
                                    }
                                    ?></td>

                                <td>
                                    <!--<a class="btn btn-info" href="<?php // echo base_url('admin/task/form/' . $value['id']);    ?>" title="Edit"><i class="fa fa-pencil"></i></a>-->
                                    <a class="btn btn-info" href="<?php echo base_url('admin/task/view/' . $value['id']); ?>" title="View"><i class="fa fa-eye"></i></a>
                                    <?php if ($value['status'] == '1') { ?>
                                        <a class="btn btn-info" onclick="return confirm('Are you sure want to Deactive status')" href="<?php echo base_url('admin/task/deactive/' . $value['id']); ?>" title="Deactive"><i class="fa fa-remove"></i></a>



                                    <?php } else { ?>

                                        <a class="btn btn-info" onclick="return confirm('Are you sure want to Active status')" href="<?php echo base_url('admin/task/active/' . $value['id']); ?>" title="Active"><i class="fa fa-check"></i></a>
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
            responsive: true
        });
    });
</script>
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


    .sortings::before,.sortings::after{
        content:"" !important;
    }

</style>

<!DOCTYPE html>

<div class="page-content">
    <div class="container-fluid">
        <header class="mgb">
            <!--<header class="section-header mgb">-->
            <div class="tbl">
                <div class="tbl-row">
                    <div class="tbl-cell">
                        <h4>Credit
                            <button class="btn btn-rounded pull-right" type="button" onclick="location.href = '<?php echo base_url(); ?>admin/credit/form'">Add</button>
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
                            <th>Number of Credits</th>
                            <th>Credit Amount</th>
                            <th>Credit Validity</th>
                            <th>Status</th>
                            <th class="sortings">Action</th>
                        </tr>
                    </thead>

                    <tbody>
                        <?php
//                        $catDetail = site_url('category/catdetail');
                        foreach ($credit as $key => $value) {
                            ?>

                            <tr>
                                <td><?php echo ucfirst($value['credits']); ?></td>
                                <td><?php echo ucfirst($value['amount']); ?></td>
                                <td><?php echo ucfirst($value['validity']); ?></td>

                                <td>
                                    <?php
                                    if ($value['status'] == 1) {
                                        echo 'Active';
                                    } else {
                                        echo 'Deactive';
                                    }
                                    ?>
                                </td>

                                <td class="adjest">
                                    <a class="btn btn-info" href="<?php echo base_url('admin/credit/form/' . $value['id']); ?>" title="Edit"><i class="fa fa-pencil"></i></a>
                                    <?php if ($value['status'] == '1') { ?>

                                        <a class="btn btn-info" onclick="return confirm('Are you sure want to Deactive status')" href="<?php echo base_url('admin/credit/deactive/' . $value['id']); ?>" title="Deactive"><i class="fa fa-remove"></i></a>
                                    <?php } else { ?>
                                        <a class="btn btn-info" onclick="return confirm('Are you sure want to Active status')" href="<?php echo base_url('admin/credit/active/' . $value['id']); ?>" title="Active"><i class="fa fa-check"></i></a>
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
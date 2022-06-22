
<?php
//echo'<pre>';
//print_r($subCategory);
//die('die');
?>
<style>
    .mgb{
        margin-bottom:0px !important
    }
</style>

<div class="page-content">
    <div class="container-fluid">
        <header class="mgb">
            <!--<header class="section-header mgb">-->
            <div class="tbl">
                <div class="tbl-row">
                    <div class="tbl-cell">
                        <h4>Category
                            <button class="btn btn-rounded pull-right" type="button" onclick="location.href = '<?php echo base_url(); ?>admin/category/form'">Add</button>
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
                            <th>Category Name</th>
                            <th>Parent Category</th>
                            <th>Category Image</th>
                            <th>Category Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php
//                        $catDetail = site_url('category/catdetail');
                        foreach ($category as $key => $value) {
                            ?>

                            <tr>
                                <td><?php echo ucfirst($value['cat_name']); ?></td>
                                <td><?php echo ucfirst($value['parentName']); ?></td>
                                <td><?php if (!empty($value['cat_img'])) { ?>


                                        <a id="single_image" onclick="viewImage()"><img src='<?php echo base_url(); ?>assets/upload/category/<?php echo $value['cat_img']; ?>' height="42" width="42" /></a>

                                                                                                                                                                <!--                                   <a id="single_image" href="image_big.jpg"><img src="image_small.jpg" alt=""/></a>-->

                                        <?php
                                    } else {
                                        echo "No Image";
                                    }
                                    ?></td>
                                <td><?php
                                    if ($value['status'] == 1) {
                                        echo 'Active';
                                    } else {
                                        echo 'Deactive';
                                    }
                                    ?></td>

                                <td>
                                    <a class="btn btn-info" href="<?php echo base_url('admin/category/form/' . $value['id']); ?>" title="Edit"><i class="fa fa-pencil"></i></a>
                                    <a class="btn btn-info" href="<?php echo base_url('admin/category/view/' . $value['id']); ?>" title="View"><i class="fa fa-eye"></i></a>
                                    <?php if ($value['status'] == '1') { ?>
                                                                                                                                                              <!--                                        <a class="btn btn-info" href="<?php // echo base_url('admin/category/deactive/' . $value['id']);                                        ?>" title="Deactive"><i class="fa fa-remove"></i></a>-->


                                        <a class="btn btn-info" onclick="return confirm('Are you sure want to Deactive status')" href="<?php echo base_url('admin/category/deactive/' . $value['id']); ?>" title="Deactive"><i class="fa fa-remove"></i></a>



                                    <?php } else { ?>
                                                                                                                                                                                                                                                                                                        <!--                                        <a class="btn btn-info" href="<?php // echo base_url('admin/category/active/' . $value['id']);                                      ?>" title="Active"><i class="fa fa-check"></i></a>-->
                                        <a class="btn btn-info" onclick="return confirm('Are you sure want to Active status')" href="<?php echo base_url('admin/category/active/' . $value['id']); ?>" title="Active"><i class="fa fa-check"></i></a>
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
//    $(document).ready(function () {
//
//        $("#single_image").fancybox;
//    });
    function viewImage() {

        /* This is basic - uses default settings */

        $("#single_image").fancybox();
    }
</script>

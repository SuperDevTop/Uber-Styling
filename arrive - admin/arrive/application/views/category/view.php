
<?php
//echo'<pre>';
//print_r($category);
//die();
?>



<div class="page-content">
    <div class="container-fluid">
        <section class="card">
            <div class="card-block">
                <table id="example" class="display table table-bordered" cellspacing="0" width="100%">

                    <tbody>
                        <tr>
                            <th>Category Name</th>
                            <td><?php echo ucfirst($category['cat_name']); ?></td>
                        </tr>
                        <tr>
                            <th>Parent Category Name</th>
                            <td><?php echo (($subCategory['cat_name'] != '') ? ucfirst($subCategory['cat_name']) : 'No Parent Category'); ?></td>

                        </tr>

                    <th>User Image</th>
                    <td><?php if (!empty($category['cat_img'])) { ?><img src='<?php echo base_url(); ?>assets/upload/category/<?php echo $category['cat_img']; ?>' height="42" width="42"><?php
                        } else {
                            echo "No Image";
                        }
                        ?></td>

                    </tr>
                    <tr>
                        <th>User Status</th>

                        <td><?php
                            if ($category['status'] == 1) {
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


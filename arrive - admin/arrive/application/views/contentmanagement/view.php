
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
                            <th>Title</th>
                            <td><?php echo ucfirst($content['title']); ?></td>
                        </tr>
                        <tr>
                            <th>Description</th>
                            <td><?php echo $content['description']; ?></td>
                        </tr>

                    <th>Status</th>

                    <td><?php
                        if ($content['status'] == 1) {
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


<div class="page-content">
    <div class="container-fluid">
        <section class="card">
            <div class="card-block">
                <table id="example" class="display table table-bordered" cellspacing="0" width="100%">

                    <tbody>
                        <tr>
                            <th>Promo Name</th>
                            <td><?php echo $promo['promo_name'] ?></td>
                        </tr>
                        <tr>
                            <th>Promo Code</th>
                            <td><?php echo $promo['promo_code']; ?></td>
                        </tr>
                        <tr>
                            <th>Valid From</th>
                            <td><?=date("M d, Y",strtotime($promo['valid_from']))   ?></td>
                        </tr>
                        <tr>
                           <th>Valid To</th>
                           <td><?=date("M d, Y",strtotime($promo['valid_to']))   ?></td>
                        </tr>
                        <tr>
                            <th>User Status</th>

                            <td><?php
                                if ($promo['status'] == 1) {
                                    echo 'Active';
                                } else {
                                    echo 'Deactive';
                                }
                                ?></td>

                        </tr>
                    </tbody>
                </table>
                <a class="btn" href="<?=base_url()?>admin/promo">Back</a>
            </div>
        </section>
    </div><!--.container-fluid-->
</div><!--.page-content-->


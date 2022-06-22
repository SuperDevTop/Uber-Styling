<div class="page-content">
    <div class="container-fluid">
        <section class="card">
            <div class="card-block">
                <table id="example" class="display table table-bordered" cellspacing="0" width="100%">

                    <tbody>
                        <tr>
                            <th>Ngo Name</th>
                            <td><?php echo $ngo['name'] ?></td>
                        </tr>
                        <tr>
                            <th>Title</th>
                            <td><?php echo $ngo['title']; ?></td>
                        </tr>
                        <tr>
                            <th>Description</th>
                            <td><?=$ngo['description'];   ?></td>
                        </tr>
                        <tr>
                           <th>Amount</th>
                           <td><?=$ngo['amount']   ?></td>
                        </tr>
                        <tr>
                           <th>Account No</th>
                           <td><?=$ngo['account_no']   ?></td>
                        </tr>
                        <tr>
                            <th>User Status</th>

                            <td><?php
                                if ($ngo['status'] == 1) {
                                    echo 'Active';
                                } else {
                                    echo 'Deactive';
                                }
                                ?></td>

                        </tr>
                    </tbody>
                </table>
                <a class="btn" href="<?=base_url()?>admin/ngo">Back</a>
            </div>
        </section>
    </div><!--.container-fluid-->
</div><!--.page-content-->


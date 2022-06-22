
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
                            <th>Membership Name</th>
                            <td><?php echo ucfirst($subscriptionplan['membership_name']); ?></td>
                        </tr>
                        <tr>
                            <th>Membership Description</th>
                            <td><?php echo $subscriptionplan['description']; ?></td>
                        </tr>
                        <tr>
                            <th>Membership Purchase Amount</th>
                            <td><?php echo $subscriptionplan['purchase_amount']; ?></td>
                        </tr>
                        <tr>
                            <th>Membership Status</th>

                            <td><?php
                                if ($subscriptionplan['status'] == 1) {
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


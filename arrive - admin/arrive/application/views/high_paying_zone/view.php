
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
                            <th>Zone Name</th>
                            <td><?php echo ucfirst($user['zone_name']); ?></td>
                        </tr>
                        <tr>
                            <th>Latitude A</th>
                            <td><?php echo $user['latitude1']; ?></td>
                        </tr>
                         <tr>
                            <th>Longitude A</th>
                            <td><?php echo $user['longitude1']; ?></td>
                        </tr>
                         <tr>
                            <th>Latitude B</th>
                            <td><?php echo $user['latitude2']; ?></td>
                        </tr>
                         <tr>
                            <th>Longitude B</th>
                            <td><?php echo $user['longitude2']; ?></td>
                        </tr>
                        <tr>
                            <th>Latitude C</th>
                            <td><?php echo $user['latitude3']; ?></td>
                        </tr>
                         <tr>
                            <th>Longitude C</th>
                            <td><?php echo $user['longitude3']; ?></td>
                        </tr>
                        <tr>
                            <th>Latitude D</th>
                            <td><?php echo $user['latitude4']; ?></td>
                        </tr>
                         <tr>
                            <th>Longitude D</th>
                            <td><?php echo $user['longitude4']; ?></td>
                        </tr>
                       


                        
                        <tr>
                            <th> Status</th>

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


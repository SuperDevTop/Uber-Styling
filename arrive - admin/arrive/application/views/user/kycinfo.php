<?php
//echo'<pre>';
//print_r($kyc);
//die('kyc');
?>



<div class="page-content">
    <div class="container-fluid">
        <section class="card">
            <div class="card-block">
                <table id="example" class="display table table-bordered" cellspacing="0" width="100%">

                    <tbody>
                        <tr>
                            <th>Pan card No:</th>
                            <td><?php echo $kyc['pan_card_number']; ?></td>
                            <?php if ((!empty($kyc)) && ($kyc['pan_card_number'] != '' )) { ?>
                                <td>

                                    <?php
                                    if ($kyc['pan_card_number'] != '') {

                                        if ($kyc['pan_card_status'] == '0') {
//                                        echo '<a href="#"><button class="btn btn-success" type="button" value="approve" name="templateBtn[btn]"> Approve</button></a>';
                                            ?>
                                            <a href="<?php echo site_url('admin/user/pan_card_approve/' . $kyc['id']) ?>"><button class="btn btn-success" type="button" value="approve" name="templateBtn[btn]"> Approve</button></a>


                                            <?php
                                        } elseif ($kyc['pan_card_status'] == '2') {

                                            echo 'Rejected: ';
                                        }
                                    } else {
                                        echo 'No pan card';
                                    }
                                    ?>

                                    <?php if ($kyc['pan_card_status'] == '0') {
                                        ?>

                                        <button value="reject" name="templateBtn[btn]" type="button" class="btn btn-success" data-toggle="modal" data-target="#myModal">Reject</button>
                                        </a>
                                        <?php
                                    } elseif ($kyc['pan_card_status'] == '2') {

                                        echo $kyc['pan_card_rejection_reason'];
                                    }
                                    ?>

                                </td>
                            <?php } else { ?>
                                <td>No Pan card</td>
                            <?php } ?>
                        </tr>
                        <tr>
                            <th>Adhar card No:</th>
                            <td><?php echo $kyc['adhar_card_number']; ?></td>
                            <?php if ((!empty($kyc)) && ($kyc['adhar_card_number'] != '')) { ?>
                                <td>

                                    <?php
                                    if ($kyc['adhar_card_number'] != '') {

                                        if ($kyc['adhar_card_status'] == '0') {
//                                        echo '<a href="#"><button class="btn btn-success" type="button" value="approve" name="templateBtn[btn]"> Approve</button></a>';
                                            ?>
                                            <a href="<?php echo site_url('admin/user/adhar_card_approve/' . $kyc['id']) ?>"><button class="btn btn-success" type="button" value="approve" name="templateBtn[btn]"> Approve</button></a>


                                            <?php
                                        } elseif ($kyc['adhar_card_status'] == '2') {

                                            echo 'Rejected: ';
                                        }
                                    } else {
                                        echo 'No adhar card';
                                    }
                                    ?>

                                    <?php if ($kyc['adhar_card_status'] == '0') {
                                        ?>

                                        <button value="reject" name="templateBtn[btn]" type="button" class="btn btn-success" data-toggle="modal" data-target="#pvModal">Reject</button>

                                        </a>
                                        <?php
                                    } elseif ($kyc['adhar_card_status'] == '2') {

                                        echo $kyc['adhar_card_rejection_reason'];
                                    }
                                    ?>

                                </td>
                            <?php } else { ?>
                                <td>No Adhar card</td>
                            <?php } ?>
                        </tr>


                    </tbody>
                </table>
            </div>
        </section>
    </div><!--.container-fluid-->
</div><!--.page-content-->
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content popup_boder">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3>Reason</h3>
            </div>
            <div class="modal-body">
                <form id = "complaint" name  ="complaint" class = "complaint">

                    <div class="form-group">
                        <label for="inputComment">Description</label>
                        <textarea class="form-control" id="description" name  = "description" rows="4"></textarea>
                    </div>
                    <div class="btn_wash_later_now">
                        <!--                        <button type="button" name ="file_complation_submit" id = "file_complation_submit" class="btn btn-wash_now center-block">SUBMIT</button>-->
                        <input type="submit" name="insert" id="insert" value="Submit" class="btn btn-success" />
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="pvModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content popup_boder">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3>Reason</h3>
            </div>
            <div class="modal-body">
                <form id = "reason" name  ="complaint" class = "complaint">

                    <div class="form-group">
                        <label for="inputComment">Description</label>
                        <textarea class="form-control" id="des" name  = "des" rows="4"></textarea>
                    </div>
                    <div class="btn_wash_later_now">
                        <!--                        <button type="button" name ="file_complation_submit" id = "file_complation_submit" class="btn btn-wash_now center-block">SUBMIT</button>-->
                        <input type="submit" name="save" id="save" value="Submit" class="btn btn-success" />
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<script>
    $(document).ready(function () {
        $('#complaint').on("submit", function (event) {

            event.preventDefault();
            if ($('#description').val() == "")
            {
                alert("Description is required");
            } else

            {
                $.ajax({
//                    url: "<?php // echo site_url('index.php/book/book_delete')                                                                            ?>/" + id,
                    url: "<?php echo site_url('admin/user/pan_card_reject_reason/' . $kyc['id']) ?>/",
                    method: "POST",
                    data: $('#complaint').serialize(),

                    success: function (data) {
//                        return false;
                        //if success close modal and reload ajax table
                        $('#myModal').modal('hide');

                        location.reload();// for reload a page
//                        window.location.reload();
                        $("#description").val("");
                    }
                });
            }
            ;
        });



    });

    $(document).ready(function () {
        $('#reason').on("submit", function (event) {

            event.preventDefault();
            if ($('#des').val() == "")
            {
                alert("Description is required");
            } else

            {
                $.ajax({
//                    url: "<?php // echo site_url('index.php/book/book_delete')                                                                            ?>/" + id,
                    url: "<?php echo site_url('admin/user/adhar_card_rejection_reason/' . $kyc['id']) ?>/",
                    method: "POST",
                    data: $('#reason').serialize(),

                    success: function (data) {
//                        return false;
                        //if success close modal and reload ajax table
                        $('#pvModal').modal('hide');

                        location.reload();// for reload a page
//                        window.location.reload();
                        $("#des").val("");
                    }

                });
            }
            ;
        });



    });
</script>
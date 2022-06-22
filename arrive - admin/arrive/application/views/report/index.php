
<?php
//echo'<pre>';
//print_r($taskList);
//die('die');
?>

<style type="text/css">
    .w_100{
        width: 100%;
    }
    .halfw {
        display: flex;
        justify-content: space-between;
        background: #e0e0e0;
        padding: 20px;
        position: relative;
        top: -10px;
    }
    .mgn {
        margin: 5px;
    }
</style>

<div class="page-content">
    <div class="container-fluid">
        <header class="mgb">
            <div class="tbl">
                <div class="tbl-row">
                    <div class="tbl-cell">
                        <h4>Report</h4>
                        <div class="w_100">
                            <div class="halfw">
                                <input type="text" placeholder="From" class="form-control"
                                       id="startDatePicker">
                                <input type="text" placeholder="To" class="form-control" id="endDatePicker">

                                <input type="text"  placeholder="Poster" class="form-control" id="poster">
                                <input type="text"  placeholder="Worker" class="form-control" id="worker">
                                <button class="btn btn-rounded pull-right mgn" type="button" onClick="getReport()">Go</button>
                                <button class="btn btn-rounded pull-right mgn" type="button" onClick="getExportData()">Export</button>
                            </div>


                        </div>


                    </div>
                </div>
            </div>
        </header>

        <section class="card">
<!--            add this id here id="userTblTr"-->
            <div class="card-block" id="userTblTr">

                <table id="example" class="display table table-bordered" cellspacing="0" width="100%">
                    <thead>
                        <tr>
                            <th>Task</th>
                            <th>Poster</th>
                            <th>Worker</th>
                            <th>Schedule Date</th>
                            <!--<th>Action</th>-->
                        </tr>
                    </thead>
                    <tbody>
                        <?php
//                        $catDetail = site_url('category/catdetail');
                        foreach ($taskList as $key => $value) {
                            ?>

                            <tr>
                                <td><?php echo ucfirst($value['title']); ?></td>
                                <td><?php echo ucfirst($value['posterFirstName']) . ' ' . $value['posterLastName']; ?></td>
                                <td><?php
                                    if ($value['workerFirstName'] != '' && $value['workerLastName'] != '') {
                                        echo ucfirst($value['workerFirstName']) . ' ' . $value['workerLastName'];
                                    } else {
                                        echo 'No worker assigned';
                                    }
                                    ?></td>
                                <td><?php echo $value['due_date']; ?></td>



                                                                                                            <!--                                <td>

                                                                                                                                                <a class="btn btn-info" href="<?php echo base_url('admin/category/view/' . $value['id']); ?>" title="View"><i class="fa fa-eye"></i></a>

                                                                                                                                            </td>-->

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

    $(document).ready(function () {
        $('#startDatePicker').datepicker({
            format: 'yyyy-mm-dd'
        })
        $('#endDatePicker')
                .datepicker({
                    format: 'yyyy-mm-dd'
                })


    });
    function getReport() {

        var fromDate = $('#startDatePicker').val();
        var toDate = $('#endDatePicker').val();
        var poster = $('#poster').val();
        var worker = $('#worker').val();
        $.ajax({
            type: "POST",
            url: "<?php echo base_url('admin/report/get_report'); ?>",
            dataType: 'json',
            data: {fromDate: fromDate, toDate: toDate, poster: poster, worker: worker},
            success: function (res) {

//                alert(res);
//                $('#userTblTr').html(res);
//                $('#example').html(res['html_data']);
                $('#userTblTr').html(res['html_data']); //right
//                $('#totalBookId').html('Total Bookings:- ' + res['total_data']);
            }
        });
    }

    function getExportData() {
        var fromDate = $('#startDatePicker').val();
        var toDate = $('#endDatePicker').val();
        var poster = $('#poster').val();
        var worker = $('#worker').val();

        if (poster == '') {
            var poster = 'pn';
        }
        if (fromDate == '') {
            var fromDate = 'fd';
        }
        if (toDate == '') {
            var toDate = 'td';
        }
        if (worker == '') {
            var worker = 'wn';
        }
        var MakeUrl = "<?php echo base_url('admin/report/toExcel'); ?>" + "/" + fromDate + "/" + toDate + "/" + poster + "/" + worker;
        window.location = MakeUrl;
//        alert(MakeUrl);

        return false;
    }
</script>
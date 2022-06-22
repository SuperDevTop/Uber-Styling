
<?php
//echo'<pre>';
//print_r($users);
//die('die');
?>
<style type="text/css">

    .sortings::before,.sortings::after{
        content:"" !important;
    }
</style>

<div class="page-content">
    <div class="container-fluid">
        <header class="mgb">
            <div class="tbl">
                <div class="tbl-row">
                    <div class="tbl-cell">
                        <h4>Users
                            <button class="btn btn-rounded pull-right" type="button" onclick="location.href = '<?php echo base_url(); ?>admin/user/form'">Add</button>
                        </h4>
                    </div>
                </div>
            </div>
        </header>

        <section class="card">
            <div class="card-block">

                <table id="example" class="display table table-bordered" cellspacing="0" width="100%">
                  
                </table>
            </div>
        </section>
    </div><!--.container-fluid-->
</div><!--.page-content-->
<script>
    $(function () {
        $('#example').DataTable({
//            responsive: true
            "scrollX": true
        });
    });
</script>

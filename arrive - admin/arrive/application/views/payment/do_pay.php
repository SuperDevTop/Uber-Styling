<?php
// echo'<pre>';
// print_r($final_data);
// die();
$t_id = isset($final_data['t_id'])?$final_data['t_id']:'';
//print_r($t_id);die();
?>
<!DOCTYPE html>
<html class=" no-touch">
<section class="card">
<div class="card-block">
    <form id='pay' method="post" action="do_user_payment">
        <input type="hidden" name="t_id" value="<?= $t_id?>">
        <input type="hidden" name="message" value="<?= $final_data['message']?>">
        <input type="hidden" name="status" value="<?= $final_data['status']?>">
        <input type="submit" id="sbmt" >
    </form>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript">
        
        $(document).ready(function(){
            $('#sbmt').trigger('click');
        });
    </script>
   

</div>
</section>



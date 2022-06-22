<?php
//echo'<pre>';
//print_r($final_data);
//die();
?>

    
    <h1>Your transction id is <?php echo isset($final_data['t_id'])?$final_data['t_id']:''?></h1>
    <h1>status = <?php echo $final_data['status']?></h1>
    <h1> message = <?php echo $final_data['message']?></h1>





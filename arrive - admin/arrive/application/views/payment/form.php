<?php
//echo'<pre>';
//print_r($credit);
//die();
?>
<!DOCTYPE html>
<html class=" no-touch">


    <body class="with-side-menu chrome-browser">

        <div class="page-content">
            <div class="container-fluid">
                <header class="section-header">
                    <div class="tbl">
                        <div class="tbl-row">
                            <div class="tbl-cell">
                               
                             <h3>Customer Information </h3>

                            </div>
                        </div>
                    </div>
                </header>
<?php
//echo'<pre>';print_r($_SERVER);die;
//echo base_url("admin/payment/getPayment");
?>
                <section class="card">
                    <div class="card-block">
                        <form class="form-horizontal" method="post" id='form' action="<?php echo $_SERVER['REDIRECT_URL'].'?'.$_SERVER['QUERY_STRING']?>">
                        


                        <div class="row">
                             <div class="col-md-6">

                                <fieldset class="form-group">
                                    <label class="form-label">Card Number*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'x_card_num',
                                        'id' => 'x_card_num',
                                        'class' => 'form-control',
                                        'maxlength'=>'16',
                                       // 'value' => isset($driver['middle_name']) ? $driver['middle_name'] : set_value('middle_name')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('x_card_num'); ?>
                                </fieldset>
                            </div>
                                                        

                        </div>
                         <div class="row">
                            <div class="col-md-6">
                        <label class="form-label">Exp Date*</label>

                         <select name='x_exp_date' id='x_exp_date'>
    <option value=''>Month</option>
    <option value='01'>January</option>
    <option value='02'>February</option>
    <option value='03'>March</option>
    <option value='04'>April</option>
    <option value='05'>May</option>
    <option value='06'>June</option>
    <option value='07'>July</option>
    <option value='08'>August</option>
    <option value='09'>September</option>
    <option value='10'>October</option>
    <option value='11'>November</option>
    <option value='12'>December</option>
</select> 
<?php echo form_error('x_exp_date'); ?>

<select name='expire_y' id='expire_y'>
    <option value=''>Year</option>
   <?php
$firstYear = (int)date('Y');
$lastYear = $firstYear + 10;
for($i=$firstYear;$i<=$lastYear;$i++)
{
    echo '<option value='.$i.'>'.$i.'</option>';
}
?>




</select>
<?php echo form_error('expire_y'); ?>

 </div></div>
                        <div class="row">
                            <div class="col-md-6">


                                <fieldset class="form-group">
                                    <label class="form-label">Card Code*</label>
                                    <?php
                                    $data = array(
                                        'name' => 'x_card_code',
                                        'id' => 'x_card_code',
                                        'class' => 'form-control',
                                        'type'=>'password'

                                        
                                        //'value' => isset($driver['email']) ? $driver['email'] : set_value('email')
                                    );
                                    echo form_input($data);
                                    ?>
                                    <?php echo form_error('x_card_code'); ?>
                                </fieldset>
                            </div>

                            
                            


                        </div>
                        


                         <div class="row">
                             <div class="col-md-6">

                                <fieldset class="form-group">
                                    <label class="form-label"></label>
                                    <?php
                                    $data = array(
                                        'name' => 'user_id',
                                        'id' => 'user_id',
                                        'class' => 'form-control',
                                        'type'=>'hidden'
                                       // 'value' => isset($driver['middle_name']) ? $driver['middle_name'] : set_value('middle_name')
                                    );
                                    echo form_input($data);
                                    ?>
                                  
                                </fieldset>
                            </div>
                            <div class="col-md-6">

                                <fieldset class="form-group">
                                    <label class="form-label"></label>
                                    <?php
                                    $data = array(
                                        'name' => 'amount',
                                        'id' => 'amount',
                                        'class' => 'form-control',
                                       'type'=>'hidden'
                                        //'value' => isset($driver['last_name']) ? $driver['last_name'] : set_value('last_name')
                                    );
                                    echo form_input($data);
                                    ?>
                                 
                                </fieldset>
                            </div>
                            

                        </div>
                        <fieldset class="form-group">
                            <button type="submit" class="btn" ><i class="fa fa-credit-card"" aria-hidden="true"></i> Pay</button>

                        </fieldset>
</form>

                    </div>
                </section>
            </div><!--.container-fluid-->
        </div><!--.page-content-->


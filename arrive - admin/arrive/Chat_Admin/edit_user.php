<?php include('header.php');?>
<div class="page-wrapper">
    <div class="page-body">

         <div class="dspage__title">
            <div class="row">
                <div class="col--first col-lg-6">
                    <span class="page__icon">
                        <i class="fa fa-home"></i>
                    </span>
                    <h5> Dashboard   </h5>
                    <ul class="breadcrumb ">
                        <li> <a href="#"> Home </a> </li>
                        <li> <a href="add_branch.php"> Edit User </a> </li> 
                    </ul>
                </div>
            </div>
        </div>


         <div class="content">
            <div class="animated fadeIn">
                <div class="row">

                    <div class="col-md-12">
                        <div class="card new_cart">
                            <div class="card-header">
                                <strong class="card-title">Edit User</strong>
                            </div>
                            <div class="card-body">
                               <div class="form_block_my">
                                   
                                  <form method="post" id="add_branch"> 
                                   <div class="row">
                                       <div class="col-sm-6">
                                            <div class="form-group">
                                                <label for="cc-payment" class="control-label mb-1">User Name</label>
                                                <input type="text" class="form-control" aria-required="true" aria-invalid="false" name="name" value="dummy user" readonly="">
                                            </div>
                                       </div>

                                       <div class="col-sm-6">
                                            <div class="form-group">
                                                <label for="cc-payment" class="control-label mb-1">Address</label>
                                                <input type="text" name="address" required class="form-control" aria-required="true" aria-invalid="false" value="lorem text dummy 123" readonly="">
                                            </div>
                                       </div>

                                   </div>

                                   <div class="row">
                                       <div class="col-sm-6">
                                            <div class="form-group">
                                                <label for="cc-payment" class="control-label mb-1">Mobile Number</label>
                                                <input type="text" name="phone_number" class="form-control" aria-required="true" aria-invalid="false" required value="0123456789" readonly="">
                                            </div>
                                       </div>

                                       <div class="col-sm-6">
                                            <div class="form-group">
                                                <label for="cc-payment" class="control-label mb-1">Email Adress</label>
                                                <input type="text" name="phone_number" class="form-control" aria-required="true" aria-invalid="false" required value="dummy@gmail.com" readonly="">
                                            </div>
                                       </div>

                                   </div>

                                   <div class="row">
                                       <div class="col-sm-12">
                                            <div class="form-group">
                                                <label for="cc-payment" class="control-label mb-1">Description</label>
                                                <textarea class="form-control" rows="7" readonly=""> Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod</textarea>
                                            </div>
                                       </div>
                                   </div>

                                    <div class="row">
                                       <div class="col-sm-12">
                                            <div class="button_block">
                                                <button class="btn btn-primary" name="submit" type="submit">Save</button>
                                            </div>
                                       </div>
                                   </div>
                                   </form>
                               </div>
                            </div>
                        </div>
                    </div>


                </div>
            </div><!-- .animated -->
        </div><!-- .content -->
    </div>
</div>
<?php include ('footer.php'); ?>
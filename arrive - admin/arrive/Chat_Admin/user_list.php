<?php include('header.php'); ?>

<div class="page-wrapper"> 
    <div class="dspage__title">
        <div class="row">
            <div class="col--first col-lg-6">
                <span class="page__icon">
                    <i class="fa fa-home"></i>
                </span>
                <h5> Manage User </h5>
                <ul class="breadcrumb ">
                    <li> <a href="#"> Home </a> </li>
                    <li> User  </li> 
                </ul>
            </div>
        </div>
    </div>
      
    <!-- <div class="search_acc_block">
        <button class="custome_accordion">
            <h2> Search...</h2>
            <span> <i class="feather icon-search"></i> </span>
        </button>
        <div class="panel">
            <div class="inner_panel_block">
                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group custom_form_block">
                            <label> Name Or Email   </label>
                            <input type="text" class="form-control">
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group custom_form_block">
                            <label> Active Users   </label> 
                            <select  class="form-control">
                                <option value="-1" selected="selected">Does Not Matter</option>
                                <option value="1">Active</option>
                                <option value="0">In-active</option>
                            </select>
                        </div>
                    </div>

                    <div class="col-sm-6">
                        <div class="form-group custom_form_block">
                            <label> Email Verified   </label> 
                            <select  class="form-control"> 
                                <option value="1">Does not matter</option>
                                <option value="0"> Yes</option>
                                <option value="0"> No </option>
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group custom_form_block">
                            <label> User Type  </label> 
                            <select  class="form-control"> 
                                <option value="1">Does not matter</option>
                                <option value="0"> Yes</option>
                                <option value="0"> No </option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group custom_form_block">
                            <label> Reg. Date From  </label> 
                            <input type="text" class="form-control"> 

                        </div>
                    </div>

                    <div class="col-sm-6">
                        <div class="form-group custom_form_block">
                            <label> Reg. Date To </label>
                            <input type="text" class="form-control"> 
                        </div>
                    </div> 
                </div>

                <div class="row">  
                    <div class="col-sm-6">
                        <div class="form-group custom_form_block">  
                            <div class="m-t-10">
                                <button type="button" class="btn btn-primary waves-effect waves-light m-r-10">Search
                                </button>
                                <button type="button" class="btn btn-warning waves-effect waves-light">Clear Search
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>  -->
       

    <div class="page-body ds_body_block">
        <div class="card">
            <div class="card-header">
                <h5> Branch   </h5>  
                <a href="add_user.php"><button>Add New</button></a>
            </div>
            <div class="card-block ">
                <div class="talabat_table_menu">
                
                    <div class="dt-responsive table-responsive">
                           <table id="example" class="table table-striped nowrap talabat_table"> 
                            <thead>
                                <tr> 
                                    <th>Sr. No</th>
                                    <th>User id</th>
                                    <th>Name   </th>
                                    <th>Mobile Number </th>
                                    <th>Email     </th>   
                                    <th>View     </th>   
                                </tr>
                            </thead>
                            <tbody> 
     
                               <tr>
                                    <td>1</td>
                                    <td>user_1223</td>
                                    <td> Dummy User  </td>
                                    <td> 123456789 </td>
                                    <td> Example@gmail.com   </td> 
                                    <td>
                                        <a href="edit_user.php" class="view_more">
                                            <i class="fa fa-pencil-square-o" aria-hidden="true" style="color:blue"> </i>
                                        </a>

                                        <a href="#" class="view_more">
                                            <i class="fa fa-trash-o" style="color:red" aria-hidden="true"></i> 
                                        </a>
                                    </td>
                               </tr> 
                               <tr>
                                    <td>2</td>
                                    <td>user_122345</td>
                                    <td> Dummy User1  </td>
                                    <td> 123456789 </td>
                                    <td> Example@gmail.com   </td> 
                                    <td>
                                        <a href="edit_user.php" class="view_more">
                                            <i class="fa fa-pencil-square-o" aria-hidden="true" style="color:blue"> </i>
                                        </a>

                                        <a href="#" class="view_more">
                                            <i class="fa fa-trash-o" style="color:red" aria-hidden="true"></i> 
                                        </a>
                                    </td> 
                               </tr> 

                               <tr>
                                    <td>3</td>
                                    <td>user_12212345</td>
                                    <td> Dummy User2  </td>
                                    <td> 123456789 </td>
                                    <td> Example@gmail.com   </td> 
                                    <td>
                                        <a href="edit_user.php" class="view_more">
                                            <i class="fa fa-pencil-square-o" aria-hidden="true" style="color:blue"> </i>
                                        </a>

                                        <a href="#" class="view_more">
                                            <i class="fa fa-trash-o" style="color:red" aria-hidden="true"></i> 
                                        </a>
                                    </td>
                               </tr> 

                               <tr>
                                    <td>4</td>
                                    <td>user_128882345</td>
                                    <td> Dummy User4  </td>
                                    <td> 123456789 </td>
                                    <td> Example@gmail.com   </td> 
                                    <td>
                                        <a href="edit_user.php" class="view_more">
                                            <i class="fa fa-pencil-square-o" aria-hidden="true" style="color:blue"> </i>
                                        </a>

                                        <a href="#" class="view_more">
                                            <i class="fa fa-trash-o" style="color:red" aria-hidden="true"></i> 
                                        </a>
                                    </td>
                               </tr> 

                               <tr>
                                    <td>5</td>
                                    <td>user_122345345</td>
                                    <td> Dummy User5  </td>
                                    <td> 123456789 </td>
                                    <td> Example@gmail.com   </td> 
                                    <td>
                                        <a href="edit_user.php" class="view_more">
                                            <i class="fa fa-pencil-square-o" aria-hidden="true" style="color:blue"> </i>
                                        </a>

                                        <a href="#" class="view_more">
                                            <i class="fa fa-trash-o" style="color:red" aria-hidden="true"></i> 
                                        </a>
                                    </td>
                               </tr> 
                           </tbody> 
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div> 


<?php include('footer.php') ?>


<script type="text/javascript">
    
$(document).ready(function() {
    $('#example').DataTable();
     $("#user").addClass("orange");

} );

</script>

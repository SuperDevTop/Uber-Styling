<?php
if($this->uri->segment(3)!='payment')
{
$sessionData = $this->session->userdata();
$userCount = $this->common->findAll('user', $select = 'COUNT(*) AS userCount', $where = array(), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
$driverCount = $this->common->findAll('driver', $select = 'COUNT(*) AS driverCount', $where = array(), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
$vehicleCount = $this->common->findAll('vehicle_model', $select = 'COUNT(*) AS vehicleCount', $where = array(), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
$promoCount = $this->common->findAll('promo', $select = 'COUNT(*) AS promoCount', $where = array(), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
$ngoCount = $this->common->findAll('ngo', $select = 'COUNT(*) AS ngoCount', $where = array(), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
//$bookingCount = $this->common->findAll('booking', $select = 'COUNT(*) AS bookingCount', $where = array(), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
//$avatarCount = $this->common->findAll('avatar', $select = 'COUNT(*) AS avatarCount', $where = array(), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
//$vehicleCount = $this->common->findAll('vehicle', $select = 'COUNT(*) AS vehicleCount', $where = array(), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
//$creditCount = $this->common->findAll('credit', $select = 'COUNT(*) AS creditCount', $where = array(), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
//$contentCount = $this->common->findAll('en_content', $select = 'COUNT(*) AS contentCount', $where = array(), $orderby = array(), $resultType = 'array', $limit = '', $groupby = '');
?>

<div class="mobile-menu-left-overlay"></div>
<nav class="side-menu">
    <ul class="side-menu-list">

        <li class="grey with-sub">
            <a href="<?php echo site_url('admin/admin/dashboard') ?>">
                <span>
                    <i class="font-icon font-icon-dashboard"></i>
                    <span class="lbl">Dashboard</span>
                </span>
            </a>

        </li>

        <li class="brown with-sub"><a href="<?php echo site_url('/admin/user') ?>">
                <span>
                    <i class="font-icon font-icon-user"></i>
                    <span class="lbl">Users<?php echo' ' . '(' . $userCount[0]['userCount'] . ')' ?></span>
                </span>
            </a>
        </li>
        <li class="brown with-sub"><a href="<?php echo site_url('/admin/driver') ?>">
                <span>
                    <i class="font-icon font-icon-user"></i>
                    <span class="lbl">Drivers<?php echo' ' . '(' . $driverCount[0]['driverCount'] . ')' ?></span>
                </span>
            </a>
        </li>

        <li class="brown with-sub"><a href="<?php echo site_url('/admin/vehicle') ?>">
                <span>
                    <i class="font-icon font-icon-widget"></i>
                    <span class="lbl">Vehicle<?php echo' ' . '(' . $vehicleCount[0]['vehicleCount'] . ')' ?></span>
                </span>
            </a>
        </li>   
         <li class="brown with-sub"><a href="<?php echo site_url('/admin/vehicle/type') ?>">
                <span>
                    <i class="font-icon font-icon-widget"></i>
                    <span class="lbl">Vehicle Class<?php // echo' ' . '(' . $vehicleCount[0]['vehicleCount'] . ')' ?></span>
                </span>
            </a>
        </li>  
         <li class="brown with-sub"><a href="<?php echo site_url('/admin/vehicle/classtype') ?>">
                <span>
                    <i class="font-icon font-icon-widget"></i>
                    <span class="lbl">Vehicle Class type<?php // echo' ' . '(' . $vehicleCount[0]['vehicleCount'] . ')' ?></span>
                </span>
            </a>
        </li>   
        <li class="brown with-sub"><a href="<?php echo site_url('/admin/vehicle/zipfirewall') ?>">
                <span>
                    <i class="font-icon glyphicon glyphicon-paperclip"></i>
                    <span class="lbl">Vehicle Zip Firewall</span>
                </span>
            </a>
        </li>
        <li class="brown with-sub"><a href="<?php echo site_url('/admin/promo') ?>">
                <span>
                    <i class="font-icon font-icon-widget"></i>
                    <span class="lbl">Promo<?php echo' ' . '(' . $promoCount[0]['promoCount'] . ')' ?></span>
                </span>
            </a>
        </li>
        <li class="brown with-sub"><a href="<?php echo site_url('/admin/ngo') ?>">
                <span>
                    <i class="font-icon font-icon-widget"></i>
                    <span class="lbl">Ngo<?php echo' ' . '(' . $ngoCount[0]['ngoCount'] . ')' ?></span>
                </span>
            </a>
        </li>
         <li class="brown with-sub"><a href="<?php echo site_url('/admin/highpayingzone') ?>">
                <span>
                    <i class="font-icon font-icon-widget"></i>
                    <span class="lbl">High Paying Zone</span>
                </span>
            </a>
        </li>
        <li class="brown with-sub"><a href="<?php echo site_url('/admin/tollpoints') ?>">
                <span>
                    <i class="font-icon font-icon-widget"></i>
                    <span class="lbl">Tolls Points</span>
                </span>
            </a>
        </li>

<!--       <li class="brown with-sub"><a href="<?php echo site_url('/admin/booking') ?>">
                <span>
                    <i class="font-icon font-icon-widget"></i>
                    <span class="lbl">Booking<?php echo' ' . '(' . $bookingCount[0]['bookingCount'] . ')' ?></span>
                </span>
            </a>
        </li>-->

<!--        <li class="brown with-sub"><a href="<?php echo site_url('/admin/avatar') ?>">
                <span>
                    <i class="font-icon font-icon-notebook"></i>
                    <span class="lbl">Avatar<?php echo' ' . '(' . $avatarCount[0]['avatarCount'] . ')' ?></span>
                </span>
            </a>
        </li>
        


        <li class="brown with-sub"><a href="<?php echo site_url('/admin/credit') ?>">
                <span>
                    <i class="font-icon glyphicon glyphicon-send"></i>
                    <span class="lbl">Credit<?php echo' ' . '(' . $creditCount[0]['creditCount'] . ')' ?></span>
                </span>
            </a>
        </li>
        <li class="brown with-sub"><a href="<?php echo site_url('/admin/contentmanagement') ?>">
                <span>
                    <i class="glyphicon glyphicon-list-alt"></i>
                    <span class="lbl">Content Management<?php echo' ' . '(' . $contentCount[0]['contentCount'] . ')' ?></span>
                </span>
            </a>
        </li>-->



    </ul>

</nav><!--.side-menu-->
<?php
}
?>

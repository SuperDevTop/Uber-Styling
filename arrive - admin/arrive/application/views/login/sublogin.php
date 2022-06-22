<?php
//echo'<pre>';
//print_r(base_url());
//die();
?>
<!DOCTYPE html>
<html>
    <head lang="en">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Sub Admin Login</title>

        <link href="<?php echo base_url() . ASSETS_IMG; ?>favicon.144x144.png" rel="apple-touch-icon" type="image/png" sizes="144x144">
        <link href="<?php echo base_url() . ASSETS_IMG; ?>favicon.114x114.png" rel="apple-touch-icon" type="image/png" sizes="114x114">
        <link href="<?php echo base_url() . ASSETS_IMG; ?>favicon.72x72.png" rel="apple-touch-icon" type="image/png" sizes="72x72">
        <link href="<?php echo base_url() . ASSETS_IMG; ?>favicon.57x57.png" rel="apple-touch-icon" type="image/png">
        <link href="<?php echo base_url() . ASSETS_IMG; ?>favicon.png" rel="icon" type="image/png">
        <link href="<?php echo base_url() . ASSETS_IMG; ?>favicon.ico" rel="shortcut icon">

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <link rel="stylesheet" href="<?php echo base_url() . ASSETS_CSS; ?>separate/pages/login.min.css">
        <link rel="stylesheet" href="<?php echo base_url() . ASSETS_CSS; ?>lib/font-awesome/font-awesome.min.css">
        <link rel="stylesheet" href="<?php echo base_url() . ASSETS_CSS; ?>lib/bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="<?php echo base_url() . ASSETS_CSS; ?>main.css">
    </head>
    <body>

        <div class="page-center">
            <div class="page-center-in">
                <div class="container-fluid">
                    <!--<form class="sign-box" action="admin/login">-->
                    <!--<form class="sign-box">-->
                    <?php
                    $attributes = array('class' => 'sign-box', 'id' => 'login');
                    echo form_open_multipart('admin/login/log', $attributes);
                    ?>
                    <div class="sign-avatar">
                        <img src="<?php echo base_url() . ASSETS_IMG; ?>avatar-sign.png" alt="">
                    </div>
                    <header class="sign-title">Sub Admin Sign In</header>
                    <div class="form-group">
                        <input type="text" name="email" class="form-control" placeholder="E-Mail"/>
                    </div>
                    <div class="form-group">
                        <input type="password" name="password" class="form-control" placeholder="Password"/>
                    </div>
                    <!--                    <div class="form-group">
                                            <div class="checkbox float-left">
                                                <input type="checkbox" id="signed-in"/>
                                                <label for="signed-in">Remember Me</label>
                                            </div>
                                            <div class="float-right reset">
                                                <a href="reset-password.html">Reset Password</a>
                                            </div>
                                        </div>-->
                    <p><a href="<?php echo site_url('admin/login/log') ?>">Login with Admin?</a></p>
                    <button type="submit" class="btn btn-rounded">Sign in</button>
<!--                    <button class="btn btn-rounded" type="submit" onclick="location.href = '<?php echo base_url(); ?> admin/category/form'">Sign in</button>-->

<!--<p class="sign-note">New to our website? <a href="sign-up.html">Sign up</a></p>-->
                    <!--<button type="button" class="close">
                        <span aria-hidden="true">&times;</span>
                    </button>-->
                    <!--</form>-->
                    <?php echo form_close(); ?>
                </div>
            </div>
        </div><!--.page-center-->


        <script src="<?php echo base_url() . ASSETS_JS; ?>lib/jquery/jquery-3.2.1.min.js"></script>
        <script src="<?php echo base_url() . ASSETS_JS; ?>lib/popper/popper.min.js"></script>
        <script src="<?php echo base_url() . ASSETS_JS; ?>lib/tether/tether.min.js"></script>
        <script src="<?php echo base_url() . ASSETS_JS; ?>lib/bootstrap/bootstrap.min.js"></script>
        <script src="<?php echo base_url() . ASSETS_JS; ?>plugins.js"></script>
        <script type="text/javascript" src="<?php echo base_url() . ASSETS_JS; ?>lib/match-height/jquery.matchHeight.min.js"></script>
        <script>
            $(function () {
                $('.page-center').matchHeight({
                    target: $('html')
                });

                $(window).resize(function () {
                    setTimeout(function () {
                        $('.page-center').matchHeight({remove: true});
                        $('.page-center').matchHeight({
                            target: $('html')
                        });
                    }, 100);
                });
            });
        </script>
        <script src="<?php echo base_url() . ASSETS_JS; ?>app.js"></script>
    </body>
</html>
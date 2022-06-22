
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
            
        </header>

        <section class="card">
            <div class="card-block">

                <div class="row col-lg-12 card " style="width: 100%; min-height: 400px;padding: 0;margin: 0;">
            <h3 class="card-header primary-color white-text">Online Drivers</h3>
            <div class="card-body" id="map" style="width: 100%; height: 375px;">

            </div>
        </div> 
            </div>
        </section>
    </div><!--.container-fluid-->
</div><!--.page-content-->
<script src="https://maps.googleapis.com/maps/api/js?key=<?php echo $this->config->item('google_api_key') ?>"
async defer></script>
<script>
    var map;
    window.eqfeed_callback = function (results) {
        results.forEach(function (element) {
            var coords = [
                parseFloat(element.latitude), parseFloat(element.longitude)
            ];
            var latlng = new google.maps.LatLng(
                    parseFloat(coords[0]),
                    parseFloat(coords[1]));
            var image = 'http://13.127.113.110/assets/Sedan_sport.png';

            var marker = new google.maps.Marker({
                position: latlng,
                map: map,
                icon: image,
                size: new google.maps.Size(20, 32),
//                label: element.first_name.charAt(0).toUpperCase() + ' ' + element.last_name.charAt(0).toUpperCase(),
                animation: google.maps.Animation.DROP,
                title: element.first_name + ' ' + element.last_name + ' | ' + element.vechile_model_no + ' ' + element.vechile_no
            });

            var infowindow = new google.maps.InfoWindow({
                content: '<b>'+element.first_name.toUpperCase() + ' ' + element.last_name.toUpperCase() + '</b> | <b><i>' + element.vechile_model_no + ' ' + element.vechile_no + '</i></b>'
            });
            marker.addListener('click', function () {

                infowindow.open(map, this);
                if (marker.getAnimation() !== null) {
                    marker.setAnimation(null);
                } else {
                    marker.setAnimation(google.maps.Animation.BOUNCE);
                }
            });
        });
    }
    function toggleBounce() {
        if (marker.getAnimation() !== null) {
            marker.setAnimation(null);
        } else {
            marker.setAnimation(google.maps.Animation.BOUNCE);
        }
    }

    function getDrivers() {
        $.ajax({
            url: "<?php echo base_url() . 'admin/admin/getAllDrivers' ?>",
            type: "post",
            data: {

            },
            success: function (d) { 
                var myLatLng = {lat: 28.58103, lng: 77.3152901};
                map = new google.maps.Map(document.getElementById('map'), {
                    zoom: 15,
                    center: myLatLng,
                    mapTypeId: 'roadmap'
                });
                eqfeed_callback(d);
            },
            error: function (e) {
                $('#notification').text(JSON.stringify(e));
            }
        });
    }

    $(document).ready(function () {
//        google.load('visualization', '1.0', {'packages': ['corechart'], 'callback': drawCharts});
        setInterval(function () {
            getDrivers();
        }, 60000);
        getDrivers();
    });
</script>

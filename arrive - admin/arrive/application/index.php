
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
<style>
      /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
      #map {
        height: 100%;
      }
      /* Optional: Makes the sample page fill the window. */
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
    </style>

<div class="page-content">
    <div class="container-fluid">
        <header class="mgb">
            <div class="tbl">
                <div class="tbl-row">
                    <div class="tbl-cell">
                        <h4>High Paying Zone
                            <button class="btn btn-rounded pull-right" type="button" onclick="location.href = '<?php echo base_url(); ?>admin/highpayingzone/form'">Add</button>
                        </h4>
                    </div>
                </div>
            </div>
        </header>


                <div class="row col-lg-12 card " style="width: 100%; min-height: 400px;padding: 0;margin: 0;">
            <h3 class="card-header primary-color white-text">High paying zone</h3>
            <div class="card-body" id="map" style="width: 100%; height: 375px;">

            </div>
        </div> 

        <section class="card">
            <div class="card-block">

                <table id="example" class="display table table-bordered" cellspacing="0" width="100%">
                    <thead>
                        <tr>
                            <th>Zone Name</th>
                            <th>High By</th>
                           
                            <th>Status</th>
                            <th class="sortings">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php
                        foreach ($users as $key => $value) {
                            ?>

                            <tr>
                                <td><?php echo ucfirst($value['zone_name']) ?></td>
                                <td><?php echo $value['high_by']; ?></td>
                               
                                <td><?php
                                    if ($value['status'] == 1) 
                                    {
                                        echo 'Active';
                                    } else {
                                        echo 'Deactive';
                                    }
                                    ?></td>
                                <td>
                                    <a class="btn btn-info" href="<?php echo base_url('admin/highpayingzone/view/' . $value['id']); ?>" title="View"><i class="fa fa-eye"></i></a>
                                    <a class="btn btn-info" href="<?php echo base_url('admin/highpayingzone/form/' . $value['id']); ?>" title="Edit"><i class="fa fa-pencil"></i></a>
                                    <?php if ($value['status'] == '1') { ?>

                                        <a class="btn btn-info" onclick="return confirm('Are you sure want to Deactive status')" href="<?php echo base_url('admin/highpayingzone/deactive/' . $value['id']); ?>" title="Deactive"><i class="fa fa-remove"></i></a>
                                    <?php } else { ?>

                                        <a class="btn btn-info" onclick="return confirm('Are you sure want to Active status')" href="<?php echo base_url('admin/highpayingzone/active/' . $value['id']); ?>" title="Active"><i class="fa fa-check"></i></a>
                                        <?php } ?>
                                </td>

                            </tr>
                        <?php }
                        ?>


                    </tbody>
                </table>
            </div>
        </section>
    </div><!--.container-fluid-->
</div><!--.page-content-->
<script async defer
    src="https://maps.googleapis.com/maps/api/js?key=<?php echo $this->config->item('google_api_key') ?>&callback=getHighzone">
   
</script><script>
    

var map;
var myBounds;
var name;
    window.eqfeed_callback = function (results) {
        results.forEach(function (element) {

            var coords = [
                parseFloat(element.latitude1), parseFloat(element.longitude1)

            ];
             var coords2 = [
                parseFloat(element.latitude2), parseFloat(element.longitude2)

            ];
            var coords3 = [
                parseFloat(element.latitude3), parseFloat(element.longitude3)

            ];
             var coords4 = [
                parseFloat(element.latitude4), parseFloat(element.longitude4)

            ];

            var latlng1=parseFloat(coords[0])-parseFloat(coords[1]);
            var latlng2=parseFloat(coords2[0])-parseFloat(coords2[1]);
            var latlng3=parseFloat(coords3[0])-parseFloat(coords3[1]);
            var latlng4=parseFloat(coords4[0])-parseFloat(coords4[1]);
            var name=element.zone_name;

            
            var myBounds= new google.maps.LatLngBounds(
            new google.maps.LatLng(latlng1, latlng3),
            new google.maps.LatLng(latlng2, latlng4));
           // var image = 'http://13.127.113.110/assets/Sedan_sport.png';

                   var marker = new google.maps.Marker({
                map: map,
                size: new google.maps.Size(20, 32),
                animation: google.maps.Animation.DROP,
                title: element.zone_name + ' ' + element.high_by
            });

            var infowindow = new google.maps.InfoWindow({
                content: '<b><i>' + element.zone_name + ' ' + element.high_by + '</i></b>'
            });

            marker.addListener('click', function () {

                infowindow.open(map, this);
                if (marker.getAnimation() !== null) {
                    marker.setAnimation(null);
                } else {
                    marker.setAnimation(google.maps.Animation.BOUNCE);
                }
            });

            //var center = bounds.getCenter();
            

          
       
           alert(myBounds);
           //getHighzone();
            


        });
    }


    ///////////get highzone
function toggleBounce() {
        if (marker.getAnimation() !== null) {
            marker.setAnimation(null);
        } else {
            marker.setAnimation(google.maps.Animation.BOUNCE);
        }
    }

      function getHighzone() {
         //eqfeed_callback();
        $.ajax({
            url: "<?php echo base_url() . 'admin/highpayingzone/getAllHighzone' ?>",
            type: "post",
            data: {

            },
            success: function (d) { 
            //d.forEach(function (element) {
             

           

        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 11,
          center: {lat: 33.678, lng: -116.243},
          mapTypeId: 'terrain'
        });
        var rectangle = new google.maps.Rectangle({
          strokeColor: '#FF0000',
          strokeOpacity: 0.8,
          strokeWeight: 2,
          fillColor: '#FF0000',
          fillOpacity: 0.35,
          map: map,
          bounds: myBounds
          
        });

         //});
             eqfeed_callback(d);

      

    }
});
      }



        $(document).ready(function () {
        setInterval(function () {
            getHighzone();
        }, 60000);
        getHighzone();
    });

google.maps.event.addDomListener(window, 'load', getHighzone);
</script>
<script>
    $(function () {
        $('#example').DataTable({
//            responsive: true
            "scrollX": true
        });
    });
</script>

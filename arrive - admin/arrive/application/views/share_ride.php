<!DOCTYPE html>
<html>
  <head>
    <title>Google Maps</title>

     <script src="https://cdn.pubnub.com/sdk/javascript/pubnub.4.19.0.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" />
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  </head>
  <body>
       <?php 
       $id='';
       $late='';
       $long='';
        $start_point_lat='';
       $start_point_long='';
        $end_point_lat='';
       $end_point_long='';
        $la_lat='';
       $la_long='';
if(!empty($share_data))
{
   $late=$share_data['start_point_lat'];
   $long=$share_data['start_point_long'];
   $id=$share_data['id'];
   $start_point_lat=$share_data['start_point_lat'];
   $start_point_long=$share_data['start_point_long'];
   $end_point_lat=$share_data['end_point_lat'];
   $end_point_long=$share_data['end_point_long'];
   
   
   if(!empty($share_data['l_lat']))
   {
    $la_lat=$share_data['l_lat'];
   }
   else
   {
    $la_lat=$share_data['start_point_lat'];
   }
   if(!empty($share_data['l_long']))
   {
   $la_long=$share_data['l_long'];
   }
   else
   {
    $la_long=$share_data['start_point_long'];
   }
   
  
 }
    ?>
    <div class="container">
      <h1>Google Maps</h1>
      <div id="map-canvas" style="width:600px;height:500px"></div>
      <form method="post">
      <input type="hidden" id="ids" name='id' value="<?php echo $id;?>">
      <input type="hidden" id="l_long" name='l_long' value="">
      <input type="hidden" id="l_lat" name='l_lat' value="">
      <form>
    </div>
 
   
    <script>

var l_t=<?php echo $start_point_lat?>;
var l_ln=<?php echo $start_point_long?>;
    window.lat = l_t;
    window.lng =l_ln;

    var lat1=<?php echo $start_point_lat;?>;
    var lng1=<?php echo $start_point_long;?>;
     var lat2=<?php echo $end_point_lat;?>;
      var lng2=<?php echo $end_point_long;?>;

       var lat3=<?php echo $la_lat?>;
        var lng3=<?php echo $la_long?>;

function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(updatePosition);
    }
    return null;
}
function updatePosition(position) {
  if (position) {
    window.lat = position.coords.latitude;
    window.lng = position.coords.longitude;
  }
}
setInterval(function(){updatePosition(getLocation());}, 10000);
function currentLocation() {
  return {lat:window.lat, lng:window.lng};
}

        var map;
    var mark;
    var initialize = function() {
      map  = new google.maps.Map(document.getElementById('map-canvas'), {center:{lat:lat,lng:lng},zoom:12});
      mark = new google.maps.Marker({position:{lat:lat, lng:lng}, map:map});
    };
    window.initialize = initialize;

        var redraw = function(payload) {
      lat = payload.message.lat;
      lng = payload.message.lng;
    var id = document.getElementById('ids').value;

      var lats=document.getElementById("l_lat").value = lat;
      var longs= document.getElementById("l_long").value = lng;
       var dataString='id=' + id + '&l_lat=' + lats + '&l_long=' + longs;
       var url="<?php echo base_url()?>/webservice/booking/update_details";
       
      $.ajax({
type: "POST",
url: url,
data: dataString,
cache: false,
success: function(res) {

}
});
      map.setCenter({lat:lat, lng:lng, alt:0});
      mark.setPosition({lat:lat, lng:lng, alt:0});
    };

        var pnChannel = "map2-channel";
   
    var pubnub = new PubNub({
      publishKey:   'pub-c-17c26e82-7add-408c-8b4a-a6117ad9711e',
      subscribeKey: 'sub-c-79ab682a-d80d-11e8-b2a2-120394cbc50a'
    });

    pubnub.subscribe({channels: [pnChannel]});
    pubnub.addListener({message:redraw});
   


    setInterval(function() {
      pubnub.publish({channel:pnChannel, message:currentLocation()});
   
    var myTrip=new Array();
    
    myTrip.push(new google.maps.LatLng(lat1,lng1));
    myTrip.push(new google.maps.LatLng(lat3,lng3));
    myTrip.push(new google.maps.LatLng(lat2,lng2));

    var flightPath=new google.maps.Polyline({
        path:myTrip,
        strokeColor:"#0000FF",
        strokeOpacity:0.8,
        strokeWeight:2,

    });
       for ( var i = 0; i < flightPath.getPath().getLength(); i++ ) {
    var marker = new google.maps.Marker( {
       icon     : {
           // use whatever icon you want for the "dots"
           url     : "https://maps.gstatic.com/intl/en_us/mapfiles/markers2/measle_blue.png",
           size    : new google.maps.Size( 7, 7 ),
           anchor  : new google.maps.Point( 4, 4 )
       },
       title    : flightPath.getPath().getAt( i ),
       position : flightPath.getPath().getAt( i ),
       map      : map
    } );
}
    flightPath.setMap(map);
   
     
    }, 5000);


    </script>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&key=<?php echo $this->config->item('google_api_key') ?>&callback=initialize"></script>
  </body>
</html>
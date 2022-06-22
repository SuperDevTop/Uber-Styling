<!DOCTYPE html>
<html>
  <head>
    <title>Tolls API Example</title>
    <link rel="stylesheet" type="text/css" href="https://coord.co/styles/fonts.css">
    <link rel="stylesheet" type="text/css" href="https://coord.co/toll-example/style.css">
    <meta name="viewport" content="initial-scale=1.0">
    <meta charset="utf-8">
  </head>
  <body>
     <header class="mgb">
            <div class="tbl">
                <div class="tbl-row">
                    <div class="tbl-cell">
                        <h4>
                            <button class="btn btn-rounded pull-right" type="button" onclick="location.href = '<?php echo base_url(); ?>admin/toolpoints/form'">Add</button>
                        </h4>
                    </div>
                </div>
            </div>
        </header>
    <div id="map" style="width: 100%; height: 575px;"></div>
    <div id="info" style="width: 20%; min-height: 300px;padding: 0;margin: 0;">
      <div id="headline">Loading...</div>
      <div id="body" style="overflow-y: scroll; max-height:200px;"></div>
      <div id="warning"></div>
      <a id="clear" href="javascript:void(0)" style="margin-left: 16px" onclick="reset()">Clear</a>
      <hr>
      <img id="toll-icon" src="https://coord.co/assets/toll-point.png"> Toll Points
    </div>
    <script type="text/javascript">
// *** Constants ***

// Your Coord API key.
var COORD_API_KEY = 'Hy9gOVGTCxvcFRUznPodo_UaPDTj3y3ph7UBR_Unj4w';
// Settings for the kind of vehicle to query toll data for.
// Change these to get results for trucks, motorcycles, or carpools.


var AXLES = 2;
var MOTORCYCLE = false;
var PASSENGERS = 1;


// *** Globals ***
var map;                        // google.maps.Map object we render on.
var origin = null;              // google.maps.LatLng object representing start of route.
var destination = null;         // google.maps.LatLng object representing end of route.
var directionsService = null;   // google.maps.DirectionsService object for getting route polyline.
var directionsRenderer = null;  // google.maps.DirectionsRenderer object for showing route on map.

var headline = document.getElementById('headline');
var body = document.getElementById('body');
var warning = document.getElementById('warning');
var clearElement = document.getElementById('clear');

/** Initialize the map (called on load). */
function initMap() {
  // Show the New York area on the map.
  map = new google.maps.Map(document.getElementById('map'), {
    center: {lat: 40.751501, lng: -73.977834},
    zoom: 10,
    styles: customMapStyles,
    fullscreenControl: false,
    mapTypeControl: false,
  });
  map.addListener('click', handleClick);

  // Style toll locations (we override below for origin, destination, and selected tolls).
  map.data.setStyle({
    zIndex: 0,
    icon: {
      url:"https://coord.co/assets/toll-point.png",
      scaledSize: new google.maps.Size(12, 12),
    },
  });

  // Initialize the directions rendering objects.
  directionsService = new google.maps.DirectionsService();
  directionsRenderer = new google.maps.DirectionsRenderer({suppressMarkers: true});

  fetchAllTollLocations().then(renderTollLocations);
  // Initialize globals and UI.
  reset();
}

/** Handle a click on the map. */
function handleClick(evt) {
  
  clearElement.style.display = 'block';
  if (!origin) {
    // If there's no origin yet, set the origin at the click point.
    origin = evt.latLng;
   
    var originFeature = new google.maps.Data.Feature({geometry: origin, id: 'origin'});
    map.data.add(originFeature);
    map.data.overrideStyle(originFeature, {
      zIndex: 2,
      icon: {
        url: 'https://coord.co/assets/pin-origin.png',
        scaledSize: new google.maps.Size(32, 48),
      }});
    headline.innerHTML = 'Click on the map to choose your destination.';
  } else if (!destination) {
    // If there's no destination, set the destination at the click point.
    destination = evt.latLng;

    var destinationFeature = new google.maps.Data.Feature({geometry: destination, id: 'destination'});
    map.data.add(destinationFeature);
    map.data.overrideStyle(destinationFeature, {
      zIndex: 2,
      icon: {
        url: 'https://coord.co/assets/pin-destination.png',
        scaledSize: new google.maps.Size(32, 48),
      }});

    // Start computing!
    computeRouteAndTolls();
  }
}

/** Compute the route and tolls based on the current origin and destination. */
function computeRouteAndTolls() {
  headline.innerHTML = 'Loading...';

  // Call the directions service.
  directionsService.route(
    {origin: origin, destination: destination, travelMode: 'DRIVING'},
    function(response, status) {
      if (response.routes.length == 0) {
        headline.innerHTML = 'Clear and select again.';
        warning.innerHTML = 'No route found.';
        warning.style.display = 'block';
        return
      }
      // When the directions service returns, fetch tolls...
      fetchTolls(response)
      // ...then render the response and the route.
      .then(function (tollResponse) {
        renderTollResponse(tollResponse);
        directionsRenderer.setDirections(response);
        directionsRenderer.setMap(map);
      });
    });
}

/**
 * Call the tolls API to get tolls for a route we get from Google.
 * Returns a Promise with the response.
 */
function fetchTolls(directionsResponse) {
  if (directionsResponse.routes.length == 0) {
      return Promise.resolve([]);
  }
  // Because the tolls API understands Google's encoded polyline format, the request is mostly a
  // cut-down version of what we got back from Google's Directions Service.
  var requestBody = {
    departure_time: new Date().toISOString(),
    steps: directionsResponse.routes[0].legs[0].steps.map(function(step) {
      return {
        encoded_polyline: step.polyline.points,
        road_name: step.instructions,
        duration: step.duration.value,
      };
    }),

    // Add vehicle information so we only get applicable toll rates.
    vehicle: {axles: AXLES, motorcycle: MOTORCYCLE, min_occupancy: PASSENGERS},
  };

  return fetch(
    'https://api.coord.co/v1/search/tolling/route?access_key=' + COORD_API_KEY,
    {method: 'POST',
     headers: {'Content-Type': 'application/json'},
     body: JSON.stringify(requestBody)}
  )
  .then(function(response) { return response.json(); });
}

/**
 * Pick which toll price to report for a given toll.
 * Even though we only return prices for the selected kind of vehicle, there can still be different
 * prices (for instance, for using a transponder vs. paying cash).
 *
 * We try to find the rate for people with no transponder. This is the cash rate if there is one,
 * but for roads without a cash rate, we find the rate with type of 'License Plate' that costs the
 * most, since this is what you usually get charged unless you have an account or some other special
 * deal.
 */
function pickPrice(toll) {
    var licensePlatePrice = 0;
    for (var i = 0; i < toll.prices.length; i++) {
        var price = toll.prices[i];
//alert(price.price.amount);
        // If details is set, this rate is for specific kinds of vehicles (e.g., above a certain weight)
        // and we should ignore it.
        if (price.vehicle.details) {
            continue;
        }

        // Get the actual amount (we convert it into dollars from cents).
        var payment = price.payment;
        var amount = price.price.amount / 100;

        // Return it right away if it is a cash amount.
        if (payment.type === 'Cash') {
            return amount;
        }
        // Record it in licensePlatePrice if it is a license plate amount.
        if (payment.type === 'License Plate' && amount > licensePlatePrice) {
            licensePlatePrice = amount;
        }
    }
    return licensePlatePrice;
}

/** Render a toll response in the 'info' div. */
function renderTollResponse(tollResponse) {
    // Go through all the tolls in the response to compute total cost.
    var totalCost = 0;
    var costs = [];
    tollResponse.forEach(function(toll) {
        var cost = pickPrice(toll);
        //alert(cost);
        costs.push('$' + cost.toFixed(2) + ' (' + toll.estimated_cross_time.substring(11,16) + ') - ' + toll.name);
        totalCost += cost;
        toll.checkpoints.forEach(function(checkpoint, i) {
          var feature = map.data.getFeatureById('toll-' + checkpoint.end.lat + '-' + checkpoint.end.lng);

          if (feature) {
            map.data.overrideStyle(feature, {
              zIndex: 1,
              icon: {
                url:'https://coord.co/assets/toll-point-selected.png',
                scaledSize: new google.maps.Size(12, 12),
              },
            });
          }
        });
    });
    headline.innerHTML = 'Total Cost: $' + totalCost.toFixed(2);
    body.innerHTML = costs.join('<br>');
    body.style.display = 'block';
}

/** Fetch toll locations within 50km of New York from the Coord API, returning a Promise. */
function fetchAllTollLocations() {
    return fetch(
        'https://api.coord.co/v1/search/tolling/toll?latitude=40.723855&' +
        'longitude=-73.996021&radius_km=50&access_key=' + COORD_API_KEY)
        .then(function(response) {
            console.log(response);
          return response.json(); 
       });
}

/** Render toll locations. */
function renderTollLocations(tolls) {
    tolls.forEach(function(toll) {
        toll.segments.forEach(function(segment) {
            segment.checkpoints.forEach(function(checkpoint) {
                toll['checkpoints'][checkpoint].vectors.forEach(function (vector) {
                    map.data.add(new google.maps.Data.Feature({
                        geometry: new google.maps.Data.Point(vector.end),
                        id: "toll-" + vector.end.lat + "-" + vector.end.lng,
                    }));
                })
            })
        })
    });
}

/** Set the UI and global variables to their initial state. */
function reset() {
  origin = null;
  destination = null;
  // Remove all features except tolls.
  map.data.forEach(function(feature) {
      if (!feature.getId().includes('toll')) {
          map.data.remove(feature);
      }
  });

  // Remove all style overrides.
  map.data.revertStyle();

  // Reset the info window.
  headline.innerHTML = 'Click on the map to choose where to start.';
  body.style.display = 'none';
  warning.style.display = 'none';
  clearElement.style.display = 'none';

  // Remove the rendered driving path.
  directionsRenderer.setMap(null);
}
    </script>
    <script src="https://coord.co/toll-example/map-style.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fetch/2.0.3/fetch.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=<?php echo $this->config->item('google_api_key') ?>&callback=initMap"
    async defer></script>
  </body>
</html>

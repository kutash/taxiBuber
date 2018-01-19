window.onload = function () {
    var begin = document.getElementById("begin");
    begin.addEventListener('click',function () {
        $('#modal-message').modal("hide");
        var id = document.getElementById("tripId").value;
        $.ajax({
            type:"GET",
            url: "ajaxController?command=start_trip&tripId="+id,
            contentType: 'application/json'
        }).done(function(results){
            console.log(results);
        });

        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                var pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                };
                latitude = position.coords.latitude;
                longitude = position.coords.longitude;
                var latLng = new google.maps.LatLng(latitude, longitude);
                var directionsService = new google.maps.DirectionsService();
                var directionsDisplay = new google.maps.DirectionsRenderer({'draggable': false});
                directionsDisplay.setMap(map);
                calculateAndDisplayRoute(directionsService, directionsDisplay, latLng);
            }, function () {
                handleLocationError(true, infoWindow, map.getCenter());
            });
        } else {
            handleLocationError(false, infoWindow, map.getCenter());
        }
    });
};


function getNewOrder() {
    $.ajax({
        type:"GET",
        url: "ajaxController?command=new_order",
        contentType: 'application/json'
    }).done(function(result){
        console.log(result);
        if(result !== 'no trips'){
            var modalMessage = $('#modal-message');
            modalMessage.modal('show');
            document.getElementById("tripId").value = result.id;
            document.getElementById("start").value = result.departure.address;
            document.getElementById("end").value = result.destination.address;
        }
    });
}

setInterval(getNewOrder, 3000);

var latitude;
var longitude;
var map;

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: -34.397, lng: 150.644},
        zoom: 15
    });
    var infoWindow = new google.maps.InfoWindow();
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var pos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            latitude = position.coords.latitude;
            longitude = position.coords.longitude;
            var geocoder = new google.maps.Geocoder;
            var latLng = new google.maps.LatLng(latitude,longitude);

            geocodeLatLng(geocoder,map,infoWindow,latLng);
            map.setCenter(pos);
        }, function() {
            handleLocationError(true, infoWindow, map.getCenter());
        });
    } else {
        handleLocationError(false, infoWindow, map.getCenter());
    }
}

function geocodeAddress(geocoder) {
    var address = document.getElementById('start').value;
    geocoder.geocode({'address': address}, function(results, status) {
        if (status === 'OK') {
            latitude = results[0].geometry.location.lat();
            longitude = results[0].geometry.location.lng();
            map.setCenter({lat: latitude, lng: longitude});
        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }
    });
}

function geocodeLatLng(geocoder, map, infowindow, latLng) {
    geocoder.geocode({'location': latLng}, function(results, status) {
        if (status === 'OK') {
            if (results[1]) {
                var marker = new google.maps.Marker({
                    position: latLng,
                    map: map
                });
                infowindow.setContent(results[0].formatted_address);
                infowindow.open(map, marker);

            } else {
                window.alert('No results found');
            }
        } else {
            window.alert('Geocoder failed due to: ' + status);
        }
    });
}

function calculateAndDisplayRoute(directionsService, directionsDisplay, start) {
    source = document.getElementById("start").value;
    destination = document.getElementById("end").value;
    var waypts = [];
    waypts.push({
        location: source,
        stopover: true
    });
    var request2 = {
        origin: start,
        destination: destination,
        waypoints: waypts,
        optimizeWaypoints: true,
        travelMode: google.maps.TravelMode.DRIVING
    };
    directionsService.route(request2, function (response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplay.setDirections(response);
        }
    });
    var service = new google.maps.DistanceMatrixService();
    service.getDistanceMatrix({
        origins: [start],
        destinations: [source],
        travelMode: google.maps.TravelMode.DRIVING,
        unitSystem: google.maps.UnitSystem.METRIC,
        avoidHighways: false,
        avoidTolls: false
    }, function (response, status) {
        if (status == google.maps.DistanceMatrixStatus.OK && response.rows[0].elements[0].status != "ZERO_RESULTS") {
            var distance = response.rows[0].elements[0].distance.text;
            var duration = response.rows[0].elements[0].duration.text;
            var distanceVal = response.rows[0].elements[0].distance.value;
            var durationVal = response.rows[0].elements[0].duration.value;
            var dvDistance = document.getElementById("divDistance-driver");
            dvDistance.style.display = 'block';
            var distanceSpan = document.getElementById('distance');
            console.log(distance);
            distanceSpan.innerHTML = ''+(distanceVal/1000);
            var durationSpan = document.getElementById('duration');
            durationSpan.innerHTML = duration;
        } else {
            alert("Unable to find the distance via road.");
        }
    });
}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
    infoWindow.setPosition(pos);
    infoWindow.setContent(browserHasGeolocation ?
        'Error: The Geolocation service failed.' :
        'Error: Your browser doesn\'t support geolocation.');
}
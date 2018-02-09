var ws = new WebSocket("ws://localhost:8080/socket");
ws.onmessage = function(event) {
    if(event.data === 'timeout'){
        ws.close();
        window.location.href = "http://localhost:8080/index.jsp";
    }else {
        var modalMessage = $('#modal-order');
        modalMessage.modal('show');
        document.getElementById('message-driver').style.display = 'block';
        var trip = JSON.parse(event.data);
        document.getElementById("tripId").value = trip.id;
        document.getElementById("start").value = trip.departure.address;
        document.getElementById("end").value = trip.destination.address;
    }
};

ws.onerror = function(event){
    console.log("Error ", event)
};

ws.onclose = function() {
    console.log("connection closed");
};

window.onload = function () {

    window.onbeforeunload = function () {
        ws.close();
    };

    var begin = document.getElementById("begin");
    begin.addEventListener('click',function () {
        document.getElementById('work').setAttribute("disabled", "true");
        $('#modal-order').modal("hide");
        var id = document.getElementById("tripId").value;
        $.ajax({
            type:"GET",
            url: "ajaxController?command=start_trip&tripId="+id,
            contentType: 'application/json'
        }).done(function(results){
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
                    var stepDisplay = new google.maps.InfoWindow;
                    calculateAndDisplayRoute(directionsService, directionsDisplay, latLng, stepDisplay);
                }, function () {
                    handleLocationError(true, infoWindow, map.getCenter());
                });
            } else {
                handleLocationError(false, infoWindow, map.getCenter());
            }
        });
    });

    var complete = document.getElementById("complete");
    complete.addEventListener('click',function () {
        document.getElementById('trips').style.display = 'none';
        var infoWindow = new google.maps.InfoWindow();
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                var pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                };
                latitude = position.coords.latitude;
                longitude = position.coords.longitude;
                var tripId = document.getElementById('tripId').value;
                $.ajax({
                    type: "GET",
                    url: "ajaxController?command=complete_trip&tripId="+tripId,
                    contentType: "application/json",
                    success: function(response) {
                        if(response === 'OK'){
                            var dvDistance = document.getElementById("divDistance-driver");
                            dvDistance.style.display = 'none';
                            document.getElementById("tripId").value = '';
                            document.getElementById("start").value = '';
                            document.getElementById("end").value = '';
                            document.getElementById('work').removeAttribute("disabled");
                            $('#modal-continue').modal("show");

                            $('.yes').on('click',function () {
                                $('#modal-continue').modal('hide');
                                setCarCoordinates(latitude,longitude);
                                setAvailable(true);
                            });

                            $('.no').on('click',function () {
                                $('#modal-continue').modal('hide');
                                setAvailable(false);
                            })
                        }
                    }
                });
            }, function () {
                handleLocationError(true, infoWindow, map.getCenter());
            });
        } else {
            handleLocationError(false, infoWindow, map.getCenter());
        }
    });

    document.getElementById('work').addEventListener('change',function () {
        if(this.checked) {
            setAvailable(true);
        }else {
            setAvailable(false);
        }
    })
};

function setAvailable(isAvailable) {
    var carId = document.getElementById('car-id').value;
    var data = {
        carId: carId,
        isAvailable: isAvailable
    };
    $.ajax({
        type: "POST",
        url: "ajaxController?command=set_available",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(response) {
            console.log(response);
            if(response === 'no car'){
                document.getElementById('no-car').style.display = 'block';
                document.getElementById('start-work').style.display = 'block';
                document.getElementById('stop-work').style.display = 'none';
                document.getElementById('work').checked = false;
            }else if(response === 'true'){
                document.getElementById('start-work').style.display = 'none';
                document.getElementById('stop-work').style.display = 'block';
                document.getElementById('work').checked = true;
                setCarCoordinates(latitude,longitude);
            }else if (response === 'false'){
                document.getElementById('start-work').style.display = 'block';
                document.getElementById('stop-work').style.display = 'none';
                document.getElementById('work').checked = false;
            }else if (response === 'trips'){
                document.getElementById('trips').style.display = 'block';
                document.getElementById('start-work').style.display = 'none';
                document.getElementById('stop-work').style.display = 'block';
                document.getElementById('work').checked = true;
            }
        }
    });
}

function setCarCoordinates(latitude,longitude) {
    var carId = document.getElementById('car-id').value;
    var data = {
        carId: carId,
        latitude: latitude,
        longitude: longitude
    };
    $.ajax({
        type: "POST",
        url: "ajaxController?command=set_coordinates",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(response) {

        }
    });
}

var latitude;
var longitude;
var map;
var markerArray = [];


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
            setCarCoordinates(latitude,longitude);
            var geocoder = new google.maps.Geocoder;
            var latLng = new google.maps.LatLng(latitude,longitude);
            geocodeLatLng(geocoder,map,infoWindow,latLng);
            map.setCenter(pos);
            if(document.getElementById("tripId").value != ''){
                var directionsService = new google.maps.DirectionsService();
                var directionsDisplay = new google.maps.DirectionsRenderer({'draggable': false});
                var stepDisplay = new google.maps.InfoWindow;
                directionsDisplay.setMap(map);
                calculateAndDisplayRoute(directionsService,directionsDisplay,latLng,stepDisplay);
            }
        }, function() {
            handleLocationError(true, infoWindow, map.getCenter());
        });
    } else {
        handleLocationError(false, infoWindow, map.getCenter());
    }
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

function calculateAndDisplayRoute(directionsService, directionsDisplay, start,stepDisplay) {
    for (var i = 0; i < markerArray.length; i++) {
        markerArray[i].setMap(null);
    }
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
            showSteps(response, markerArray, stepDisplay, map);
        }
    });
    var service = new google.maps.DistanceMatrixService();
    service.getDistanceMatrix({
        origins: [start],
        destinations: [destination],
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

function showSteps(directionResult, markerArray, stepDisplay, map) {
    // For each step, place a marker, and add the text to the marker's infowindow.
    // Also attach the marker to an array so we can keep track of it and remove it
    // when calculating new routes.
    console.log(directionResult);

    var myRoute = directionResult.routes[0].legs[0];
    for (var i = 0; i < myRoute.steps.length; i++) {
        var marker = markerArray[i] = markerArray[i] || new google.maps.Marker;
        marker.setMap(map);
        marker.setPosition(myRoute.steps[i].start_location);
        attachInstructionText(
            stepDisplay, marker, myRoute.steps[i].instructions, map);
    }
}

function attachInstructionText(stepDisplay, marker, text, map) {
    google.maps.event.addListener(marker, 'click', function() {
        // Open an info window when the marker is clicked on, containing the text
        // of the step.
        stepDisplay.setContent(text);
        stepDisplay.open(map, marker);
    });
}
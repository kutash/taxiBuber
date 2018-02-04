var latitude;
var longitude;
var map;
var isWorking = false;

setInterval(getNewOrder, 3000);

window.onload = function () {

    if(document.getElementById('work').checked && document.getElementById("tripId").value === ''){
        isWorking = true;
    }

    var begin = document.getElementById("begin");
    begin.addEventListener('click',function () {
        document.getElementById('work').setAttribute("disabled", "true");
        isWorking = false;
        $('#modal-message').modal("hide");
        var id = document.getElementById("tripId").value;
        $.ajax({
            type:"GET",
            url: "ajaxController?command=start_trip&tripId="+id,
            contentType: 'application/json'
        }).done(function(results){

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
            if(response === 'no car'){
                document.getElementById('no-car').style.display = 'block';
                document.getElementById('start-work').style.display = 'block';
                document.getElementById('stop-work').style.display = 'none';
                document.getElementById('work').checked = false;
                isWorking = false;
            }else if(response === 'true'){
                document.getElementById('start-work').style.display = 'none';
                document.getElementById('stop-work').style.display = 'block';
                setCarCoordinates(latitude,longitude);
                isWorking = true;
            }else if (response === 'false'){
                document.getElementById('start-work').style.display = 'block';
                document.getElementById('stop-work').style.display = 'none';
                document.getElementById('work').checked = false;
                isWorking = false;
            }else if (response === 'trips'){
                document.getElementById('trips').style.display = 'block';
                document.getElementById('start-work').style.display = 'none';
                document.getElementById('stop-work').style.display = 'block';
                document.getElementById('work').checked = true;
                isWorking = false;
            }
        }
    });

    /*$.ajax({
        type:"GET",
        url: "ajaxController?command=set_available&carId="+carId+"&isAvailable="+isAvailable,
        contentType: 'application/json'
    }).done(function(result){
        if(result === 'no car'){
            document.getElementById('no-car').style.display = 'block';
            document.getElementById('start-work').style.display = 'block';
            document.getElementById('stop-work').style.display = 'none';
            document.getElementById('work').checked = false;
        }else {
            setCarCoordinates(latitude,longitude);
            isWorking = true;
        }
    });*/
}

function getNewOrder() {
    var carId = document.getElementById('car-id').value;
    if(isWorking && carId > 0) {
        $.ajax({
            type: "GET",
            url: "ajaxController?command=new_order&carId="+carId,
            contentType: 'application/json'
        }).done(function (result) {
            if (result !== 'no trips') {
                isWorking = false;
                var modalMessage = $('#modal-message');
                modalMessage.modal('show');
                document.getElementById("tripId").value = result.id;
                document.getElementById("start").value = result.departure.address;
                document.getElementById("end").value = result.destination.address;
            }
        });
    }
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

    /*$.ajax({
        type:"GET",
        url: "ajaxController?command=set_coordinates&carId="+carId+"&latitude="+latitude+"&longitude="+longitude,
        contentType: 'application/json'
    }).done(function(result){
        console.log(result);
    });*/
}

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
                directionsDisplay.setMap(map);
                calculateAndDisplayRoute(directionsService,directionsDisplay,latLng)
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
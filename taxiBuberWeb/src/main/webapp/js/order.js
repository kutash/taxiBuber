setInterval(getAvailableCars, 10000);

var latitude;
var longitude;
var map;
var source, destination;
var markers = [];
var distances = [];
var changeZoom = true;

function initMap() {
    var directionsService = new google.maps.DirectionsService();
    var directionsDisplay = new google.maps.DirectionsRenderer({ 'draggable': false });
    google.maps.event.addDomListener(window, 'load', function () {
        new google.maps.places.SearchBox(document.getElementById('start'));
        new google.maps.places.SearchBox(document.getElementById('end'));

    });
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: -34.397, lng: 150.644},
        zoom: 15
    });
    var infoWindow = new google.maps.InfoWindow({map: map});

    directionsDisplay.setMap(map);
    directionsDisplay.setPanel(document.getElementById('right-panel'));

    document.getElementById('submit').addEventListener('click', function() {
        calculateAndDisplayRoute(directionsService, directionsDisplay);
    });

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var pos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            latitude = position.coords.latitude;
            longitude = position.coords.longitude;

            document.getElementById('start').value = latitude+','+longitude;

            infoWindow.setPosition(pos);
            infoWindow.setContent('Location found.');
            map.setCenter(pos);
        }, function() {
            handleLocationError(true, infoWindow, map.getCenter());
        });
    } else {
        handleLocationError(false, infoWindow, map.getCenter());
    }
}

function calculateAndDisplayRoute(directionsService, directionsDisplay) {
    changeZoom = false;
    source = document.getElementById("start").value;
    destination = document.getElementById("end").value;

    var request = {
        origin: source,
        destination: destination,
        travelMode: google.maps.TravelMode.DRIVING
    };
    directionsService.route(request, function (response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplay.setDirections(response);
        }
    });

    var service = new google.maps.DistanceMatrixService();
    service.getDistanceMatrix({
        origins: [source],
        destinations: [destination],
        travelMode: google.maps.TravelMode.DRIVING,
        unitSystem: google.maps.UnitSystem.METRIC,
        avoidHighways: false,
        avoidTolls: false
    }, function (response, status) {
        if (status == google.maps.DistanceMatrixStatus.OK && response.rows[0].elements[0].status != "ZERO_RESULTS") {
            var distance = response.rows[0].elements[0].distance.text;
            var duration = response.rows[0].elements[0].duration.text;
            var bodyType = document.getElementById('body-type').value;

            $.ajax({
                type:"GET",
                url: "buber?command=price&bodyType="+bodyType+"&distance="+distance,
                contentType: 'application/json'
            }).done(function(result){
                /*var dvDistance = document.getElementById("dvDistance");
                dvDistance.style.display='block';
                var distanceSpan = document.getElementById('distance');
                distanceSpan.innerHTML = distance + '<br />';
                var durationSpan = document.getElementById('duration');
                durationSpan.innerHTML = duration + '<br />';*/
                document.getElementById('distance').value = distance;
                document.getElementById('duration').value = duration;
                document.getElementById('price').value = result;
                /*var costSpan = document.getElementById('cost');
                costSpan.innerHTML = ''+result ;*/
            }).fail(function(xhr, textStatus, error){
                console.log(xhr);
            });
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

function getAvailableCars() {
    var bodyType = document.getElementById('body-type').value;
    $.ajax({
        type:"GET",
        url: "buber?command=free_cars&latitude="+latitude+"&longitude="+longitude+"&bodyType="+bodyType,
        contentType: 'application/json'
    }).done(function(results){
        if (results.length === 0){
            deleteMarkers();
            var infoWindow = new google.maps.InfoWindow({map: map});
            var pos = {
                lat: latitude,
                lng: longitude
            };
            infoWindow.setPosition(pos);
            infoWindow.setContent('В данный момент нет машин по вашему запросу. Попробуйте изменить запрос или повторите его через несколько минут');
        }

        for (var i = 0; i < results.length; i++) {
            deleteMarkers();
            setMarkers(results[i]);
        }
        distances.sort(function compareNumeric(a, b) {
            return a - b;
        });
        if (changeZoom) {
            if (distances[0] > 5.0) {
                map.setZoom(13);
            } else if (distances[0] > 7.0) {
                map.setZoom(12);
            } else if (distances[0] > 10.0) {
                map.setZoom(11);
            }
        }
        distances = [];
    }).fail(function(xhr, textStatus, error){
        console.log(xhr);
    });
}

function setMarkers(result) {
    var lat = result.latitude;
    var long = result.longitude;
    var brand = result.brand.name;
    var model = result.model;
    var latLng = new google.maps.LatLng(lat, long);
    var latLngClient = new google.maps.LatLng(latitude, longitude);
    var service = new google.maps.DistanceMatrixService();
    var contentString;
    service.getDistanceMatrix({
        origins: [latLngClient],
        destinations: [latLng],
        travelMode: google.maps.TravelMode.DRIVING,
        unitSystem: google.maps.UnitSystem.METRIC,
        avoidHighways: false,
        avoidTolls: false
    }, function (response, status) {
        if (status == google.maps.DistanceMatrixStatus.OK && response.rows[0].elements[0].status != "ZERO_RESULTS") {
            var distance = response.rows[0].elements[0].distance.text;
            var dist = distance.split(' ',1);
            var res = dist[0].replace(',','.');
            distances.push(parseFloat(res));
            var duration = response.rows[0].elements[0].duration.text;
            contentString = '<div>Расстояние:'+distance+'<br/> Время подачи машины:'+duration+'</div><div>'+brand+' '+model+'</div><div><a href="#">Подробнее</a></div>';
        } else {
            contentString = '<div>Can not define distance</div><div>'+brand+' '+model+'</div><div><a href="javascript{}" onclick="chooseCar()">Выбрать</a></div>';
        }
        var marker = new google.maps.Marker({
            position: latLng,
            icon: "/images/car48x48.png",
            map: map
        });
        attachSecretMessage(marker, contentString);
    });
}

function attachSecretMessage(marker, content) {
    var infowindow = new google.maps.InfoWindow({
        content: content
    });

    marker.addListener('click', function() {
        infowindow.open(marker.get('map'), marker);
    });
    markers.push(marker);
}

function deleteMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

function chooseCar() {

}


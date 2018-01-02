window.onload = function () {
    var addresses = document.querySelectorAll('.address-link');
    for (var i = 0; i < addresses.length; i++) {
        addresses[i].addEventListener('click', function (event) {
            event.preventDefault();
            document.getElementById('end').value = this.innerHTML;
            var ev = new KeyboardEvent('keyup', {
                key: 'Enter'
            });
            document.getElementById('end').dispatchEvent(ev);
        })
    }

    document.getElementById('order-button').addEventListener('click', function (event) {
        event.preventDefault();
        document.getElementById('order-form').submit();
    })
};


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
        var start = new google.maps.places.SearchBox(document.getElementById('start'));
        start.addListener('places_changed', function() {
            var places = start.getPlaces();
            if (places.length == 0) {
                return;
            }
            calculateAndDisplayRoute(directionsService, directionsDisplay);
        });

        var end = new google.maps.places.SearchBox(document.getElementById('end'));
        end.addListener('places_changed', function() {
            var places = end.getPlaces();
            if (places.length == 0) {
                return;
            }
            calculateAndDisplayRoute(directionsService, directionsDisplay);
        });
    });


    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: -34.397, lng: 150.644},
        zoom: 15
    });
    directionsDisplay.setMap(map);
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
                document.getElementById('start').value = results[0].formatted_address;
            } else {
                window.alert('No results found');
            }
        } else {
            window.alert('Geocoder failed due to: ' + status);
        }
    });
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
                url: "ajaxController?command=price&bodyType="+bodyType+"&distance="+distance,
                contentType: 'application/json'
            }).done(function(result){
                var dvDistance = document.getElementById("dvDistance");
                dvDistance.style.display='block';
                var distanceSpan = document.getElementById('distance');
                distanceSpan.innerHTML = distance + '<br />';
                var durationSpan = document.getElementById('duration');
                durationSpan.innerHTML = duration + '<br />';
                var costSpan = document.getElementById('cost');
                costSpan.innerHTML = ''+result ;
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
        url: "ajaxController?command=free_cars&latitude="+latitude+"&longitude="+longitude+"&bodyType="+bodyType,
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
    var photo = result.photoPath;
    var id = result.userId;
    var driver = result.driverFullName;
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
            contentString = '<div>'+brand+' '+model+'</div><div><img src="controller?command=photo&amp;photo='+photo+'" width="70px" height="70px"/></div><div>Driver:<a data-toggle="modal" data-target="#myModal" href="" onclick="show('+id+')">'+driver+'</a><br/>Расстояние:'+distance+'<br/>Время подачи машины:'+duration+'</div>';
        } else {
            contentString = '<div>Can not define distance</div><div>'+brand+' '+model+'</div><div><a href="#">Выбрать</a></div>';
        }
        var marker = new google.maps.Marker({
            position: latLng,
            icon: "/images/car48x48.png",
            map: map
        });
        attachSecretMessage(marker, contentString,result);
    });
}

function attachSecretMessage(marker, content, result) {
    var infowindow = new google.maps.InfoWindow({
        content: content
    });

    marker.addListener('mouseover', function() {
        infowindow.open(marker.get('map'), marker);
    });

    marker.addListener('click', function() {
        infowindow.close(marker.get('map'), marker);
        document.getElementById('car').value = result.brand.name+' '+result.model;
        document.getElementById('carId').value = result.id;
    });
    markers.push(marker);
}

function deleteMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

function show(id) {
    $.ajax({
        type:"GET",
        url: "ajaxController?command=user_info&userId="+id,
        contentType: 'application/json'
    }).done(function(result){
        document.getElementById('tbody').innerHTML='';
        console.log(result);
        document.getElementById('driver').innerHTML = result.surname+' '+result.name+' '+result.patronymic;
        document.getElementById('rating-order').innerHTML = result.rating;
        var per = 100*result.rating/5;
        document.getElementById('str').style.width = per+'%';
        var photoPath;
        if (result.photoPath === undefined){
            photoPath = '';
        }  else {
            photoPath = result.photoPath;
        }
        document.getElementById('blah').src ='controller?command=photo&photo='+photoPath;

        var comments = result.comments;
        if(comments.length === 0){
            document.getElementById('tbody').innerHTML='У водителя пока нету комментариев';
        }
        for (var i=0;i<comments.length;i++){
            var mark = comments[i].mark*20;
            document.getElementById('tbody').innerHTML+='<tr><td class="col-md-2"><img src="/controller?command=photo&photo='+comments[i].reviewerPhoto+'" width="50" height="50" class="comment-photo"/><br/>'+comments[i].reviewerName+'</td><td class="col-md-10">'+comments[i].text+'<br><div style="display: inline-flex"><span class="comment-mark">Оценка:</span><div class="productRate-order" id="rating-div"><div class="productRate-div" style="width:'+mark+'%"></div></div></div><span id="date-span">'+comments[i].date+'</span></td></tr>';
        }
    })
}



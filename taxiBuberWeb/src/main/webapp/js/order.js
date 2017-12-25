/*$(document).ready(function () {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var latitude = position.coords.latitude;
            var longitude = position.coords.longitude;
            $("#latitude").val(position.coords.latitude);
            $("#longitude").val(position.coords.longitude);
        });
    }
});*/

/*setInterval(getAvailableCars, 4000);*/

function getAvailableCars() {
    $.get("buber", function(responseJson) {
            $.each(responseJson, function(index, car) {
                $("#somediv").html(car.id+','+car.bodyType+','+car.model+'<br/>')
            });
    });

    /*jQuery.ajax({
        url: "buber",
        type : 'get',
        success: function(html){
            jQuery("#somediv").html(html);
        }
    });*/
}

$(document).ready(function () {
    var userRole = $('.before-form').text();
    if (userRole == 'ADMIN') {
        $('input#name, input#surname, input#patronymic, input#email, input#birthday, input#phone, select#role').attr('disabled', true);
        $('div#div-pwd, div#div-pwd-repeat, div#submit-button').css('display','none');
    }

});

$(document).ready(function () {
    var rating = $('#rating').text();
    var per = 100*rating/5;
    $('#str').css({'width' : per+'%'});
});

$(document).ready(function () {
    $("#img").click(function() {
        $("#imgInp").click();
    });

    $('#imgInp').change(function () {
        previewFile(this);
    })
});

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function(e) {
            $('#blah').attr('src', e.target.result);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

function previewFile(input) {
    var preview = $('#blah');
    var file    = input.files[0];
    var reader  = new FileReader();

    reader.onload = function (e) {
        preview.attr('src', e.target.result);
    };

    if (file) {
        reader.readAsDataURL(file);
    } else {
        preview.attr('src','${pageContext.request.contextPath}/my-servlet?command=photo');
    }
}




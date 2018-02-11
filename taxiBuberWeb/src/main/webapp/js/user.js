var ws = new WebSocket("ws://localhost:8080/socket");
ws.onmessage = function(event) {
    if(event.data === 'started') {
        $('#modal-order').modal('show');
        $('#message-success').css('display','block');
        setTimeout(function () {
            $('#modal-order').modal("hide");
            $('#message-success').css('display','none');
        }, 2000);
    }else if(event.data === 'timeout'){
        ws.close();
        window.location.href = "http://localhost:8080/index.jsp";
    }else {
        if ($('#current-user').text() === 'DRIVER') {
            $('#modal-order').modal('show');
            $('#message-driver').css('display','block');
            $('#begin').on('click',function () {
                var trip = JSON.parse(event.data);
                var id = trip.id;
                $.ajax({
                    type:"GET",
                    url: "ajaxController?command=start_trip&tripId="+id,
                    contentType: 'application/json'
                }).done(function(results){
                    ws.close();
                    window.location.href = "http://localhost:8080/controller?command=main";
                });
            });
        }
    }
};

ws.onerror = function(event){
    console.log("Error ", event)
};

ws.onclose = function() {
    console.log("connection closed");
};

$(document).ready(function () {

    $(window).on('beforeunload', function () {
        ws.close();
    });

    var userRole = $('.before-form').text();
    if (userRole === 'ADMIN') {
        $('div.form-group').removeClass('required');
    }

    ($('.err2').each(function () {
            if($(this).text() !== ''){
            $(this).parent().children('.form-control').addClass('error-input');
        }
    }));

    if ($('#update-password').text() !== '' || $('#update-user').text() !== ''){
        var modalMessage = $('#modal-message');
        modalMessage.modal('show');
        setTimeout(function(){
            modalMessage.modal("hide");
        }, 2000);
    }

    if ($('#is-password').val() === 'true'){
        var modalPassword = $('#myModal');
        modalPassword.modal('show');
    }

    $('input#name, input#surname, input#patronymic, input#phone, input#birthday').on("keyup", function(){
        var id = $(this).attr('id');
        var val = $(this).val();
        var rv_name = /^[a-zA-Zа-яА-Я]*[a-zA-Zа-яА-Я-\s]{3,44}$/;
        var rv_phone = /^(\s*)?(\+)?([- _():=+]?\d[- _():=+]?){10,14}(\s*)?$/;
        var rv_date = /(19|20)\d\d-((0[1-9]|1[012])-(0[1-9]|[12]\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)/;

        switch(id) {
            case 'name':
                if (val === '') {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input-user');
                    $('#error-name').css('display', 'none');
                    $('#blank-name').css('display', 'block');
                    $(this).parent().children('.err2').css('display', 'none');
                } else if (!rv_name.test(val)) {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input-user');
                    $('#error-name').css('display', 'block');
                    $('#blank-name').css('display', 'none');
                    $(this).parent().children('.err2').css('display', 'none');
                } else {
                    $(this).removeClass('error-input-user').addClass('not-error');
                    $('#error-name').css('display', 'none');
                    $('#blank-name').css('display', 'none');
                    $(this).parent().children('.err2').css('display', 'none');
                    removeDisabledUser();
                }
                break;

            case 'surname':
                if (val === '') {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input-user');
                    $('#error-surname').css('display', 'none');
                    $('#blank-surname').css('display', 'block');
                    $(this).parent().children('.err2').css('display', 'none');
                } else if (!rv_name.test(val)) {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input-user');
                    $('#error-surname').css('display', 'block');
                    $('#blank-surname').css('display', 'none');
                    $(this).parent().children('.err2').css('display', 'none');
                } else {
                    $(this).removeClass('error-input-user').addClass('not-error');
                    $('#error-surname').css('display', 'none');
                    $('#blank-surname').css('display', 'none');
                    $(this).parent().children('.err2').css('display', 'none');
                    removeDisabledUser();
                }
                break;

            case 'patronymic':
                if(val === '' || rv_name.test(val)) {
                    $(this).removeClass('error-input-user').addClass('not-error');
                    $('#error-patronymic').css('display', 'none');
                    $(this).parent().children('.err2').css('display', 'none');
                    removeDisabledUser();
                } else {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input-user');
                    $('#error-patronymic').css('display', 'block');
                    $(this).parent().children('.err2').css('display', 'none');
                }
                break;

            case 'phone':
                if(val === '' || rv_phone.test(val)) {
                    $(this).removeClass('error-input-user').addClass('not-error');
                    $('#error-phone').css('display', 'none');
                    $(this).parent().children('.err2').css('display', 'none');
                    removeDisabledUser();
                } else {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input-user');
                    $('#error-phone').css('display', 'block');
                    $(this).parent().children('.err2').css('display', 'none');
                }
                break;

            case 'birthday':
                if(val === '' || rv_date.test(val)) {
                    $(this).removeClass('error-input-user').addClass('not-error');
                    $('#error-birthday').css('display', 'none');
                    $(this).parent().children('.err2').css('display', 'none');
                    removeDisabledUser();
                } else {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input-user');
                    $('#error-birthday').css('display', 'block');
                    $(this).parent().children('.err2').css('display', 'none');
                }
                break;
        }

    });

    $('#cancel-button').on('click',function () {
        var errorsFromServer = 0;
        $('.err2').each(function () {
            if($(this).text() !== ''){
                errorsFromServer++;
            }
        });
        if(errorsFromServer > 0) {
            $('#cancelUserForm').submit();
        }else {
            $('#saveUserForm').trigger( 'reset' );
            $('.err').css('display', 'none');
            $('input[type=text]').removeClass('error-input-user not-error');
            $('#save-button').attr('disabled', 'disabled');
            var photoPath = $('#user-photo').val();
            var id = $('#user-id').val();
            $('#blah').attr('src','/ajaxController?command=photo&photo='+photoPath+'&userId='+id);
        }
    });

    $('input#password, input#repeat, input#old-password').on("keyup", function(){
        var id = $(this).attr('id');
        var val = $(this).val();
        var rv_psw = /^[а-яА-Яa-zA-Z0-9_-]{6,40}$/;

        switch(id) {
            case 'old-password':
                if (val === '') {
                    $('#change-password').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-old').css('display', 'none');
                    $('#blank-old').css('display', 'block');
                    $(this).parent().children('.err2').css('display', 'none');
                } else if (!rv_psw.test(val)) {
                    $('#change-password').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-old').css('display', 'block');
                    $('#blank-old').css('display', 'none');
                    $(this).parent().children('.err2').css('display', 'none');
                } else {
                    $(this).removeClass('error-input').addClass('not-error');
                    $('#error-old').css('display', 'none');
                    $('#blank-old').css('display', 'none');
                    $(this).parent().children('.err2').css('display', 'none');
                    removeDisabledPassw();
                }
                break;

            case 'password':
                if (val === '') {
                    $('#change-password').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-password').css('display', 'none');
                    $('#blank-password').css('display', 'block');
                    $(this).parent().children('.err2').css('display', 'none');
                } else if (!rv_psw.test(val)) {
                    $('#change-password').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-password').css('display', 'block');
                    $('#blank-password').css('display', 'none');
                    $(this).parent().children('.err2').css('display', 'none');
                } else {
                    $(this).removeClass('error-input').addClass('not-error');
                    $('#error-password').css('display', 'none');
                    $('#blank-password').css('display', 'none');
                    $(this).parent().children('.err2').css('display', 'none');
                    removeDisabledPassw();
                }
                break;

            case 'repeat':
                if (val !== $('#password').val()) {
                    $('#change-password').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#repeat-password').css('display', 'block');
                    $(this).parent().children('.err2').css('display', 'none');
                } else {
                    $(this).removeClass('error-input').addClass('not-error');
                    $('#repeat-password').css('display', 'none');
                    $(this).parent().children('.err2').css('display', 'none');
                    removeDisabledPassw();
                }
                break;
        }
    });

    $('.cancel').on('click',function () {
        $('#changePasswordForm').trigger( 'reset' );
        $('.err').css('display', 'none');
        $('.err2').css('display', 'none');
        $('input[type=password]').removeClass('error-input not-error');
        $('#change-password').attr('disabled', 'disabled');
    });

    $('.delete-address').on('click',function () {
        var id = $(this).attr('id');
        $.ajax({
            type:"POST",
            url: "ajaxController?command=delete_address&addressId="+id,
            contentType: 'application/json'
        }).done(function(result){
            console.log(result);
            if(result === 'deleted'){
                $('#li'+id).remove();
                if($('#list').find('li').length === 0){
                    $('#addresses').remove();
                }
            }
        })
    });

    var rating = $('#rating').text();
    var per = 100*rating/5;
    $('#str').css({'width' : per+'%'});

    $("#blah").click(function() {
        $("#imgInp").click();
    });

    $('#imgInp').change(function () {
        previewFile(this);
    })
});

function previewFile(input) {
    console.log("previe");
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

function removeDisabledPassw() {
    if ($('.error-input').length === 0 && $('input#old-password').val() !=='' && $('input#password').val() !=='' && $('input#repeat').val() !==''){
        $('#change-password').removeAttr('disabled');
    }
}

function removeDisabledUser() {
    if ($('.error-input-user').length === 0 && $('input#name').val() !=='' && $('input#surname').val() !=='' ){
        $('#save-button').removeAttr('disabled');
    }
}




function setNewBackground() {
    var urls = [
        "/images/3.jpg",
        "images/4.jpg",
        "/images/6.jpg",
        "/images/7.jpg",
        "/images/9.jpg",
        "/images/10.jpg",
        "/images/11.jpg",
        "/images/12.jpg",
        "/images/13.jpg",
        "/images/14.jpg",
        "/images/15.jpg",
        "/images/17.jpg",
        "/images/18.jpg",
        "/images/19.jpg",
        "/images/20.jpg",
        "/images/21.jpg"
    ];
    var num = Math.floor(Math.random() * (urls.length));
    $('#login-content').fadeTo('slow', 0.17, function () {
        $(this).css('background-image', 'url(' + urls[num] + ')');
    }).fadeTo('slow', 1);
}
setInterval(setNewBackground, 4000);



$(document).ready(function () {
    var error_login = $('#error-login');
    var message = error_login.text();
    console.log(message);
    if (message === 'Incorrect email or password!') {
        error_login.css("margin-left", "120px");
        $('#myModal').modal('show');
    }else if (message === 'Неверный адрес электронной почты или пароль!'){
        error_login.css("margin-left", "28px");
        $('#myModal').modal('show');
    }
});

$(document).ready(function() {

    $('input#username, input#psw').on("keyup", function(){
        var id = $(this).attr('id');
        var val = $(this).val();
        var rv_name = /^[a-zA-Z0-9_-]*$/;

        switch(id) {
            case 'username':
                var rv_email = /^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
                if (val.length > 90) {
                    $('#submit-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#invalid-email').css('display', 'none');
                    $('#blank-email').css('display', 'none');
                    $('#max-email').css('display', 'block');
                } else if (val === '') {
                    $('#submit-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#invalid-email').css('display', 'none');
                    $('#blank-email').css('display', 'block');
                    $('#max-email').css('display', 'none');
                } else if (!rv_email.test(val)) {
                    $('#submit-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#invalid-email').css('display', 'block');
                    $('#blank-email').css('display', 'none');
                    $('#max-email').css('display', 'none');
                } else {
                    $(this).removeClass('error-input').addClass('not-error');
                    $('#invalid-email').css('display', 'none');
                    $('#blank-email').css('display', 'none');
                    $('#max-email').css('display', 'none');
                    if ($('.error-input').length === 0 && $('#psw').val() !==''){
                        $('#submit-button').removeAttr('disabled');
                    }
                }
                break;

            case 'psw':
                if (val === '') {
                    $('#submit-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#invalid-psw').css('display', 'none');
                    $('#blank-psw').css('display', 'block');
                    $('#max-psw').css('display', 'none');
                } else if (!rv_name.test(val)) {
                    $('#submit-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#invalid-psw').css('display', 'block');
                    $('#blank-psw').css('display', 'none');
                    $('#max-psw').css('display', 'none');
                } else if (val.length > 50 || val.length < 4) {
                    $('#submit-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#invalid-psw').css('display', 'none');
                    $('#blank-psw').css('display', 'none');
                    $('#max-psw').css('display', 'block');
                } else {
                    $(this).removeClass('error-input').addClass('not-error');
                    $('#invalid-psw').css('display', 'none');
                    $('#blank-psw').css('display', 'none');
                    $('#max-psw').css('display', 'none');
                    if ($('.error-input').length === 0 && $('#username').val() !==''){
                        $('#submit-button').removeAttr('disabled');
                    }
                }
                break;
        }
    });
    
    $('.cancel').on('click',function () {
        $('#jform').trigger( 'reset' );
        $('#invalid-email').css('display', 'none');
        $('#blank-email').css('display', 'none');
        $('#max-email').css('display', 'none');
        $('#invalid-psw').css('display', 'none');
        $('#blank-psw').css('display', 'none');
        $('#max-psw').css('display', 'none');
        $('#username').removeClass('error-input not-error');
        $('#psw').removeClass('error-input not-error');
        $('#submit-button').attr('disabled', 'disabled');
    });

});




    




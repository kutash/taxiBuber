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
    if (message === 'Incorrect email or password!') {
        error_login.css("margin-left", "120px");
        $('#myModal').modal('show');
    }else if (message === 'Неверный адрес электронной почты или пароль!'){
        error_login.css("margin-left", "28px");
        $('#myModal').modal('show');
    }

    if ($('#is-errors').val() === 'true'){
        $('#modal-signup').modal('show');
    }

    ($('.err2').each(function () {
            if($(this).text() !== ''){
                $(this).parent().children('.form-control').addClass('error-input');
            }
    }));

});

$(document).ready(function () {
    $("#blah").click(function() {
        $("#imgInp").click();
    });

    $('#imgInp').change(function () {
        previewFile(this);
    })
});

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
        preview.attr('src','/controller?command=photo');
    }
}

$(document).ready(function() {

    $('input#username, input#psw').on("keyup", function(){
        var id = $(this).attr('id');
        var val = $(this).val();
        var rv_name = /^[a-zA-Z0-9_-]*$/;
        var rv_email = /^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;

        switch(id) {
            case 'username':
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
        $('.err').css('display', 'none');
        $('#username').removeClass('error-input not-error');
        $('#psw').removeClass('error-input not-error');
        $('#submit-button').attr('disabled', 'disabled');
    });


    $('input#name, input#surname, input#email, input#patronymic, input#phone, input#birthday, input#password, input#repeat').on("keyup", function(){
        var id = $(this).attr('id');
        var val = $(this).val();
        var rv_name = /^[a-zA-Zа-яА-Я]*[a-zA-Zа-яА-Я-\s]{3,44}$/;
        var rv_email = /^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
        var rv_phone = /^(\s*)?(\+)?([- _():=+]?\d[- _():=+]?){10,14}(\s*)?$/;
        var rv_date = /(19|20)\d\d-((0[1-9]|1[012])-(0[1-9]|[12]\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)/;
        var rv_psw = /^[а-яА-Яa-zA-Z0-9_-]{6,40}$/;

        switch(id) {
            case 'name':
                if (val === '') {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-name').css('display', 'none');
                    $('#blank-name').css('display', 'block');
                } else if (!rv_name.test(val)) {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-name').css('display', 'block');
                    $('#blank-name').css('display', 'none');
                } else {
                    $(this).removeClass('error-input').addClass('not-error');
                    $('#error-name').css('display', 'none');
                    $('#blank-name').css('display', 'none');
                    removeDisabled();
                }
                break;

            case 'surname':
                if (val === '') {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-surname').css('display', 'none');
                    $('#blank-surname').css('display', 'block');
                } else if (!rv_name.test(val)) {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-surname').css('display', 'block');
                    $('#blank-surname').css('display', 'none');
                } else {
                    $(this).removeClass('error-input').addClass('not-error');
                    $('#error-surname').css('display', 'none');
                    $('#blank-surname').css('display', 'none');
                    removeDisabled();
                }
                break;

            case 'patronymic':
                if(val === '' || rv_name.test(val)) {
                    $(this).removeClass('error-input').addClass('not-error');
                    $('#error-patronymic').css('display', 'none');
                    removeDisabled();
                } else {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-patronymic').css('display', 'block');
                }
                break;

            case 'email':
                if (val === '') {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-email').css('display', 'none');
                    $('#email-blank').css('display', 'block');
                    $('#email-size').css('display', 'none');
                }else if (val.length > 90) {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-email').css('display', 'none');
                    $('#email-blank').css('display', 'none');
                    $('#email-size').css('display', 'block');
                } else if (!rv_email.test(val)) {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-email').css('display', 'block');
                    $('#email-blank').css('display', 'none');
                    $('#email-size').css('display', 'none');
                } else {
                    $(this).removeClass('error-input').addClass('not-error');
                    $('#error-email').css('display', 'none');
                    $('#email-size').css('display', 'none');
                    $('#email-blank').css('display', 'none');
                    removeDisabled();
                }
                break;

            case 'phone':
                if(val === '' || rv_phone.test(val)) {
                    $(this).removeClass('error-input').addClass('not-error');
                    $('#error-phone').css('display', 'none');
                    removeDisabled();
                } else {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-phone').css('display', 'block');
                }
                break;

            case 'birthday':
                if(val === '' || rv_date.test(val)) {
                    $(this).removeClass('error-input').addClass('not-error');
                    $('#error-birthday').css('display', 'none');
                    removeDisabled();
                } else {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-birthday').css('display', 'block');
                }
                break;

            case 'password':
                if (val === '') {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-password').css('display', 'none');
                    $('#blank-password').css('display', 'block');
                } else if (!rv_psw.test(val)) {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-password').css('display', 'block');
                    $('#blank-password').css('display', 'none');
                } else {
                    $(this).removeClass('error-input').addClass('not-error');
                    $('#error-password').css('display', 'none');
                    $('#blank-password').css('display', 'none');
                    removeDisabled();
                }
                break;

            case 'repeat':
                if (val !== $('#password').val()) {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#repeat-password').css('display', 'block');
                } else {
                    $(this).removeClass('error-input').addClass('not-error');
                    $('#repeat-password').css('display', 'none');
                    removeDisabled();
                }
                break;
        }

    });

    $('#role').on('change',function () {
        if ($('#role').val() === ''){
            $('#save-button').attr('disabled', 'disabled');
        }else {
            removeDisabled();
        }
    });

    $('.cancel-signup').on('click',function () {
        $('#saveUserForm').trigger( 'reset' );
        $('.err').css('display', 'none');
        $('input[type=text], input[type=email], input[type=password]').removeClass('error-input not-error');
        $('#save-button').attr('disabled', 'disabled');
        $('#blah').attr('src','/ajaxController?command=photo');
    });
});

function removeDisabled() {
    if ($('.error-input').length === 0 && $('input#name').val() !=='' && $('input#surname').val() !=='' && $('input#email').val() !=='' && $('#role').val() !=='' && $('input#password').val() !=='' && $('input#repeat').val() !==''){
        $('#save-button').removeAttr('disabled');
    }
}




    




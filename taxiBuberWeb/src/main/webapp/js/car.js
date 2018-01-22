$(document).ready(function () {

    if ($('#is-car').val() === 'true'){
        $('#modal-car').modal('show');
    }

    var update = $('#message-update');
    if (update.val() !== ''){
        $('#modal-message2').modal('show');
        update.css('display', 'block');
    }
    var create = $('#message-create');
    if (create.val() !== ''){
        $('#modal-message2').modal('show');
        create.css('display', 'block');
    }

    $("#blah").click(function() {
        $("#imgInp").click();
    });
    $('#imgInp').change(function () {
        previewFile(this);
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

    $('.cancel-signup').on('click',function () {
        $('#saveUserForm').trigger( 'reset' );
        $('.err').css('display', 'none');
        $('input[type=text], input[type=email], input[type=password]').removeClass('error-input not-error');
        $('#submit-button').attr('disabled', 'disabled');
    });

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

function removeDisabled() {
    if ($('.error-input').length === 0 && $('input#name').val() !=='' && $('input#surname').val() !=='' && $('input#email').val() !=='' && $('#role').val() !=='' && $('input#password').val() !=='' && $('input#repeat').val() !==''){
        $('#save-button').removeAttr('disabled');
    }
}


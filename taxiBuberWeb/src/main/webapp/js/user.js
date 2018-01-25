$(document).ready(function () {
    var userRole = $('.before-form').text();
    if (userRole === 'ADMIN') {
        $('input#name, input#surname, input#patronymic, input#email, input#birthday, input#phone, select#role, input#imgInp').attr('disabled', true);
        $('div#save-button').css('display','none');
        $('div#cancel-button').css('display','none');
        $('input#name, input#surname').removeClass('required')
    }else if (userRole === 'DRIVER' || userRole === 'CLIENT'){
        $('input#email').attr('disabled', true);
        $('select#role').css('display','none');
        $('#label-role').css('display','none');
    }

    ($('.err2').each(function () {
            if($(this).text() !== ''){
            $(this).parent().children('.form-control').addClass('error-input');
        }
    }));

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
        }

    });

    $('#cancel-button').on('click',function () {
        $('#saveUserForm').trigger( 'reset' );
        $('.err').css('display', 'none');
        $('input[type=text]').removeClass('error-input not-error');
        $('#save-button').attr('disabled', 'disabled');
        var photoPath = $('#user-photo').val();
        var id = $('#user-id').val();
        $('#blah').attr('src','/ajaxController?command=photo&photo='+photoPath+'&userId='+id);
    });


});

$(document).ready(function () {
    var rating = $('#rating').text();
    var per = 100*rating/5;
    $('#str').css({'width' : per+'%'});
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
        preview.attr('src','${pageContext.request.contextPath}/my-servlet?command=photo');
    }
}

function removeDisabled() {
    if ($('.error-input').length === 0 && $('input#name').val() !=='' && $('input#surname').val() !=='' ){
        $('#save-button').removeAttr('disabled');
    }
}





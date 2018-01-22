$(document).ready(function () {

    if ($('#is-car').val() === 'true'){
        $('#modal-car').modal('show');
    }

    var modalMessage = $('#modal-message2');
    var update = $('#message-update');
    console.log(update.text());
    if (update.text() !== ''){
        modalMessage.modal('show');
        update.css('display', 'block');
        setTimeout(function(){
            modalMessage.modal("hide");
            update.css('display', 'none');
        }, 2000);
    }
    var create = $('#message-create');
    console.log(create.text());
    if (create.text() !== ''){
        modalMessage.modal('show');
        create.css('display', 'block');
        setTimeout(function(){
            modalMessage.modal("hide");
            create.css('display', 'none');
        }, 2000);
    }

    $("#blah").click(function() {
        $("#imgInp").click();
    });
    $('#imgInp').change(function () {
        previewFile(this);
    });

    $('input#number, input#model').on("keyup", function(){
        var id = $(this).attr('id');
        var val = $(this).val();
        var rv_number = /\d{4}[A-Z]{2}-\d/;
        var rv_model = /^[а-яА-Яa-zA-Z0-9-\s]{4,40}$/;

        switch(id) {
            case 'number':
                if (val === '') {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-number').css('display', 'none');
                    $('#blank-number').css('display', 'block');
                } else if (!rv_number.test(val)) {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-number').css('display', 'block');
                    $('#blank-number').css('display', 'none');
                } else {
                    $(this).removeClass('error-input').addClass('not-error');
                    $('#error-number').css('display', 'none');
                    $('#blank-number').css('display', 'none');
                    removeDisabled();
                }
                break;

            case 'model':
                if (val === '') {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-model').css('display', 'none');
                    $('#blank-model').css('display', 'block');
                } else if (!rv_model.test(val)) {
                    $('#save-button').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-model').css('display', 'block');
                    $('#blank-model').css('display', 'none');
                } else {
                    $(this).removeClass('error-input').addClass('not-error');
                    $('#error-model').css('display', 'none');
                    $('#blank-model').css('display', 'none');
                    removeDisabled();
                }
                break;
        }

    });

    $('#brand').on('change',function () {
        if ($('#brand').val() === ''){
            $('#save-button').attr('disabled', 'disabled');
        }else {
            removeDisabled();
        }
    });

    $('#capacity').on('change',function () {
        if ($('#capacity').val() === ''){
            $('#save-button').attr('disabled', 'disabled');
        }else {
            removeDisabled();
        }
    });

    $('.cancel-car').on('click',function () {
        $('#saveCarForm').trigger( 'reset' );
        $('.err').css('display', 'none');
        $('input[type=text]').removeClass('error-input not-error');
        $('#save-button').attr('disabled', 'disabled');
        var photoPath = $('#car-photo').val();
        var id = $('#user-id').val();
        $('#blah').attr('src','/controller?command=photo&photo='+photoPath+'&userId='+id);
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
    if ($('.error-input').length === 0 && $('input#number').val() !=='' && $('input#model').val() !=='' && $('#brand').val() !=='' && $('#capacity').val()){
        $('#save-button').removeAttr('disabled');
    }
}


$(document).ready(function () {

    if ($('#is-car').val() === 'true'){
        $('#modal-car').modal('show');
    }
    var count = 0;
    var modalMessage = $('#modal-message2');
    var update = $('#message-update');
    if (update.text() !== ''){
        modalMessage.modal('show');
        update.css('margin','12%');
        update.css('display', 'block');
        setTimeout(function(){
            modalMessage.modal("hide");
            update.css('display', 'none');
        }, 3000);
    }
    var create = $('#message-create');
    if (create.text() !== ''){
        modalMessage.modal('show');
        create.css('margin','12%');
        create.css('display', 'block');
        setTimeout(function(){
            modalMessage.modal("hide");
            create.css('display', 'none');
        }, 3000);
    }

    var deleted = $('#message-deleted');
    if (deleted.text() !== ''){
        modalMessage.modal('show');
        deleted.css('margin','12%');
        deleted.css('display', 'block');
        setTimeout(function(){
            modalMessage.modal("hide");
            deleted.css('display', 'none');
        }, 3000);
    }

    ($('.err2').each(function () {
        if($(this).text() !== ''){
            $(this).parent().children('.form-control').addClass('error-input');
        }
    }));

    $("#car-img").click(function() {
        $("#file-car").click();
    });
    $('#file-car').change(function () {
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
                    $('#save-car').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-number').css('display', 'none');
                    $('#blank-number').css('display', 'block');
                } else if (!rv_number.test(val)) {
                    $('#save-car').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-number').css('display', 'block');
                    $('#blank-number').css('display', 'none');
                } else {
                    $(this).removeClass('error-input').addClass('not-error');
                    $('#error-number').css('display', 'none');
                    $('#blank-number').css('display', 'none');
                    $(this).parent().children('.err2').css('display', 'none');
                    removeDisabled();
                }
                break;

            case 'model':
                if (val === '') {
                    $('#save-car').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-model').css('display', 'none');
                    $('#blank-model').css('display', 'block');
                } else if (!rv_model.test(val)) {
                    $('#save-car').attr('disabled', 'disabled');
                    $(this).removeClass('not-error').addClass('error-input');
                    $('#error-model').css('display', 'block');
                    $('#blank-model').css('display', 'none');
                } else {
                    $(this).removeClass('error-input').addClass('not-error');
                    $('#error-model').css('display', 'none');
                    $('#blank-model').css('display', 'none');
                    $(this).parent().children('.err2').css('display', 'none');
                    removeDisabled();
                }
                break;
        }

    });

    $('#brand').on('change',function () {
        if ($('#brand').val() === ''){
            $('#save-car').attr('disabled', 'disabled');
        }else {
            $(this).removeClass('error-input').addClass('not-error');
            $(this).parent().children('.err2').css('display', 'none');
            removeDisabled();
        }
    });

    $('#capacity').on('change',function () {
        if ($('#capacity').val() === ''){
            $('#save-car').attr('disabled', 'disabled');
        }else {
            $(this).removeClass('error-input').addClass('not-error');
            $(this).parent().children('.err2').css('display', 'none');
            removeDisabled();
        }
    });

    $('.cancel-car').on('click',function () {
        var errorsFromServer = 0;
        $('.err2').each(function () {
            if($(this).text() !== ''){
                errorsFromServer++;
            }
        });
        if(errorsFromServer > 0) {
            $('#cancelCarForm').submit();
        }else {
            $('#saveCarForm').trigger('reset');
            $('.err').css('display', 'none');
            $('input[type=text]').removeClass('error-input not-error');
            $('#save-car').attr('disabled', 'disabled');
            var photoPath = $('#car-photo').val();
            var id = $('#user-id').val();
            $('#car-img').attr('src', '/ajaxController?command=photo&photo=' + photoPath + '&userId=' + id);
        }
    });

    $('#delete-button').on('click',function () {
        var modalMessage = $('#modal-confirm');
        modalMessage.modal('show');
    });

    $('.yes').on('click',function () {
        $('#modal-confirm').modal('hide');
        $('#deleteCarForm').submit();
    });

    $('.no').on('click',function () {
        $('#modal-confirm').modal('hide');
    })

});

function previewFile(input) {
    var preview = $('#car-img');
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
        $('#save-car').removeAttr('disabled');
    }
}


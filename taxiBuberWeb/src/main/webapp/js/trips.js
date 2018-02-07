$(document).ready(function(){

    /* 1. Visualizing things on Hover - See next part for action on click */
    $('#stars li').on('mouseover', function(){
        var onStar = parseInt($(this).data('value'), 10); // The star currently mouse on

        // Now highlight all the stars that's not after the current hovered star
        $(this).parent().children('li.star').each(function(e){
            if (e < onStar) {
                $(this).addClass('hover');
            }
            else {
                $(this).removeClass('hover');
            }
        });

    }).on('mouseout', function(){
        $(this).parent().children('li.star').each(function(e){
            $(this).removeClass('hover');
        });
    });


    /* 2. Action to perform on click */
    $('#stars li').on('click', function(){
        var onStar = parseInt($(this).data('value'), 10); // The star currently selected
        var stars = $(this).parent().children('li.star');

        for (i = 0; i < stars.length; i++) {
            $(stars[i]).removeClass('selected');
        }

        for (i = 0; i < onStar; i++) {
            $(stars[i]).addClass('selected');
        }

        // JUST RESPONSE (Not needed)
        var ratingValue = parseInt($('#stars li.selected').last().data('value'), 10);
        $('#valuation').val(ratingValue);

    });

    if ($('#error-comment').text() !== ''){
        $('#myModal').modal('show');
    }

    if ($('#is-created').val() === 'true'){
        var modalMessage = $('#modal-message');
        modalMessage.modal('show');
        setTimeout(function(){
            modalMessage.modal("hide");
        }, 2000);
    }

    $('.comment-link').on('click',function () {
        var id = $(this).attr('id');
        $('#user-id').val(id);
        $('#myModal').modal('show');
    });

    $('.star').on('click',function () {
        if($('#comment').val() !== ''){
            $('#submit-button').removeAttr('disabled');
        }
    });

    $('textarea#comment').on("keyup", function(){
        var val = $(this).val();
        if (val.length > 1000) {
            $('#submit-button').attr('disabled', 'disabled');
            $(this).removeClass('not-error').addClass('error-input');
            //$('#invalid-comment').css('display', 'none');
            $('#blank-comment').css('display', 'none');
            $('#max-comment').css('display', 'block');
        } else if (val === '') {
            $('#submit-button').attr('disabled', 'disabled');
            $(this).removeClass('not-error').addClass('error-input');
            //$('#invalid-comment').css('display', 'none');
            $('#blank-comment').css('display', 'block');
            $('#max-comment').css('display', 'none');
        } else {
            $(this).removeClass('error-input').addClass('not-error');
            //$('#invalid-comment').css('display', 'none');
            $('#blank-comment').css('display', 'none');
            $('#max-comment').css('display', 'none');
            if ($('.error-input').length === 0 && $('#valuation').val() !==''){
                $('#submit-button').removeAttr('disabled');
            }
        }

    });

    $('.cancel').on('click',function () {
        $('#comment-form').trigger( 'reset' );
        $('.err').css('display', 'none');
        $('#comment').removeClass('error-input not-error');
        $('.star').removeClass('selected');
        $('#submit-button').attr('disabled', 'disabled');
    });

    $("#search-input").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        var parameter = $('#parameter').val();
        if (parameter === ''){
            $("#content").find('tr').filter(function () {
                $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
            });
        }else {
            $("#content").find('tr').children('.' + parameter).filter(function () {
                console.log($(this).text());
                if(($(this).text().toLowerCase().indexOf(value) === -1)){
                    if ($(this).parent().has('.'+ parameter + '2')) {
                        $(this).parent().children('.' + parameter + '2').filter(function () {
                            console.log($(this).text());
                            $(this).parent().toggle($(this).text().toLowerCase().indexOf(value) > -1)
                        });
                    }else {
                        $(this).parent().toggle(false)
                    }
                }else {
                    $(this).parent().toggle(true)
                }
            });
        }
    });

    $(".input-group-btn .dropdown-menu li a").click(function(){
        var search = $(this).children('.label-icon').html();
        var selText = $(this).html();
        if(search === 'Search By Name' || search === 'Поиск по Имени') {
            $('#parameter').val('name');
        }else if(search === 'Search By Address' || search === 'Поиск по Адресу') {
            $('#parameter').val('address');
        }else if(search === 'Search By Date' || search === 'Поиск по Дате') {
            $('#parameter').val('date');
        }
        $(this).parents('.input-group-btn').find('.btn-search').html(selText);
    });

});




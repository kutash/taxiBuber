var ws = new WebSocket("ws://localhost:8080/socket");
ws.onmessage = function(event) {
    if(event.data === 'timeout'){
        ws.close();
        window.location.href = "http://localhost:8080/index.jsp";
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
        console.log('end');
        ws.close();
    });

    $('.ban-link').on('click',function () {
        var id = $(this).attr('id');
        $.ajax({
            type:"POST",
            url: "ajaxController?command=ban&userId="+id,
            contentType: 'application/json'
        }).done(function(result){
            var tr = $('#tr'+id);
            var a = tr.find('a');
            if(result === 'banned') {
                tr.addClass('banned');
                a.find('i').addClass('banned');
                tr.find('a').addClass('banned');
            }else if(result === 'unbanned'){
                tr.removeClass('banned');
                a.find('i').removeClass('banned');
                tr.find('a').removeClass('banned');
            }

        });
    });

    var idDel;

    $('.callConfirm').on('click',function () {
        idDel = $(this).attr('id');
        var modalMessage = $('#modal-message2');
        modalMessage.modal('show');
    });

    $('.yes').on('click',function () {
        $('#modal-message2').modal('hide');
        $('#tr'+idDel).remove();
        $.ajax({
            type:"POST",
            url: "ajaxController?command=delete&userId="+idDel,
            contentType: 'application/json'
        }).done(function(result){
            if(result === 'ok'){
                var modalMessage = $('#modal-message');
                var update = $('#message-deleted');
                modalMessage.modal('show');
                setTimeout(function(){
                    modalMessage.modal("hide");
                }, 2000);
            }

        });
    });

    $('.no').on('click',function () {
        $('#modal-message2').modal('hide');
    });

    $('.address-link').on('click',function () {
        $('#search').text($(this).text());
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
                $(this).parent().toggle($(this).text().toLowerCase().indexOf(value) > -1)
            });
        }
    });

    $(".input-group-btn .dropdown-menu li a").click(function(){
        var search = $(this).children('.label-icon').html();
        var selText = $(this).html();
        if(search === 'Search By Name' || search === 'Поиск по Имени') {
            $('#parameter').val('name');
        }else if(search === 'Search By Email' || search === 'Поиск по Email') {
            $('#parameter').val('email');
        }else if(search === 'Search By Date' || search === 'Поиск по Дате') {
            $('#parameter').val('date');
        }
        $(this).parents('.input-group-btn').find('.btn-search').html(selText);
    });
});



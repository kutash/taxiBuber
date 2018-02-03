'use strict';
function paginate(contentId,pagerId,perPage) {
	var content = document.getElementById(contentId);
	var k = 0;
	var page = 1;
	window.elements = {};
	function createPageLink(page) {
		var pagerLink = document.createElement('a');

		pagerLink.setAttribute('href','#');
		pagerLink.setAttribute('id','pager-link-'+page);
		pagerLink.setAttribute('class','pager-link');
		pagerLink.innerHTML = page;
		document.getElementById(pagerId).appendChild(pagerLink);
		document.getElementById('pager-link-'+page).onclick = function() {
			content.innerHTML = '';
			for (var j = 0; j < elements[page].length; j++) {
				content.appendChild(elements[page][j]);
			}
			for (var i = 0; i < document.getElementsByClassName('pager-link').length; i++) {
				document.getElementsByClassName('pager-link')[i].setAttribute('class','pager-link');
			}
			document.getElementById('pager-link-'+page).setAttribute('class','pager-link pager-link-selected');
			return false;
		}
	}
	createPageLink(1);
	for (var i = 0; i < content.childNodes.length; i++) {
		if (content.childNodes[i].tagName) {
			if (elements[page] && elements[page].length === perPage) {
				page++;
				createPageLink(page);
			}
			if (!elements[page]) {
				elements[page] = [];
			}
			elements[page].push(content.childNodes[i]);
		}
	}
	if (page === 1) {
		document.getElementById(pagerId).innerHTML = '';
	}

	content.innerHTML = '';
	for (var i = 0; i < elements[1].length; i++) {
		content.appendChild(elements[1][i]);
	}
	for (var i = 0; i < document.getElementsByClassName('pager-link').length; i++) {
		document.getElementsByClassName('pager-link')[i].setAttribute('class','pager-link');
	}
	document.getElementById('pager-link-1').setAttribute('class','pager-link pager-link-selected');
	return false;
}

$(document).ready(function () {
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
            console.log(result);
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
        console.log($(this).text());
        $('#search').text($(this).text());
    });

    $("#end").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#users-table").find('tr').children('.'+$('#search').text()).filter(function() {
            console.log($(this).text());
            $(this).parent().toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});


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

    var modalMessage = $('#modal-message2');
    var update = $('#message-deleted');
    if (update.text() !== '') {
        modalMessage.modal('show');
        update.css('margin','12%');
        update.css('display', 'block');
        setTimeout(function(){
         modalMessage.modal("hide");
         update.css('display', 'none');
         }, 2000);
    }

});

function deleteUser(id) {

    $('.callConfirm').on('click',function () {
        var modalMessage = $('#modal-message2');
        $('#message-delete').css('display','block');
        $('#delete-div').css('display','block');
        modalMessage.modal('show');
    });

    $('.yes').on('click',function () {
        $('#delete'+id).submit();
    });

    $('.no').on('click',function () {
        $('#modal-message2').modal('hide');
    })
}



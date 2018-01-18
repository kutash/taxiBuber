function getNewOrder() {
    $.ajax({
        type:"GET",
        url: "ajaxController?command=new_order",
        contentType: 'application/json'
    }).done(function(results){
        if(results.length === 0){

        }
    });
}

setInterval(getNewOrder, 3000);

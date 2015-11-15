$(function() {
    $('.add-to-cart').click(function() {
        var group = $(this).attr('data-group');
        
        var picture_id = $('input[data-group=' + group + '][name=picture_id]').
                attr('value');
        var product_type_id = $('input[data-group=' + group + '][name=product_type_id]').
                attr('value');
        var amount = $('input[data-group=' + group + '][name=amount]').
                attr('value');
        
        $.post( "/app/customers/cart/ajax/add", 
            JSON.stringify({
                "picture_id": picture_id, 
                "product_type_id": product_type_id ,
                "amount": amount
            }),
            function( data ) {
                console.log('success');
            });
    });
});
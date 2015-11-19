$(function() {
    $('.add-to-cart').click(function() {        
        var picture_id = $('form#add_to_cart').
                find('input[name="picture_id"]').val();
        var product_type_id = $('form#add_to_cart').
                find('.product-display.selected').
                find('input[name="product_id"]').val();
        var amount = $('form#add_to_cart input#amount').val();
        var color = $('form#add_to_cart').
                find('.color-display.selected').
                find('input[name="color"]').val();
        
        $.post( "/app/customers/cart/ajax/add", 
            JSON.stringify({
                "picture_id": picture_id, 
                "product_type_id": product_type_id,
                "amount": amount,
                "color": color
            }),
            function( data ) {
                window.location.replace("/app/customers/cart");
            });
    });
    
    $('.product-display').click(function() {
        $(this).siblings('.product-display').removeClass('selected');
        $(this).addClass('selected');
    });
    
    $('.color-display').click(function() {
        $(this).siblings('.color-display').removeClass('selected');
        $(this).addClass('selected');
    });
    
    $('.cart-product-amount').change(function() {
        var amount = $(this).val();
        var entry_id = $(this).siblings('input[name="entry_id"]').val();
        console.log(entry_id, amount);
    });
});
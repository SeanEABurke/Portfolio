$(document).ready(function() {
    $('.overlay').fadeOut(200);
    $(".fade").hide(0).delay(500).fadeIn(1000).slideDown();
    $(".fadeslower").hide(0).delay(500).fadeIn(2000);
    
    $('a').click(function(e) {
        e.preventDefault(); // Prevent the default link behavior
        
        var newPageUrl = $(this).attr('href'); // Get the URL of the new page
        
        // Fade out the current page
        $('.fade, .fadeslower').fadeOut(500, function() {
          // Redirect to the new page'
            
                window.location.href = newPageUrl;
                $(".fade, .fadeslower").css("display", "none");
        });
    });

    $("button").on("touchsart mousedown", function () {
        $(this).addClass("clicked");
    });
    
    $("button").on("touchend mouseup", function () {
        $(this).removeClass("clicked");
        $(this).blur();
    });

    $("#submit").on("touchsart mousedown", function () {
        $(this).addClass("clicked");
    });
    
    $("#submit").on("touchend mouseup", function () {
        $(this).removeClass("clicked");
        $(this).blur();
    });
    
    
});
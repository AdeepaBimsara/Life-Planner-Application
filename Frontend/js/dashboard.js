$(document).ready(function(){

    // Sidebar active effect
    $(".nav-item").click(function(){
        $(".nav-item").removeClass("active");
        $(this).addClass("active");
    });

    // Bar animation
    $(".bar").hover(function(){
        $(this).css("opacity", "0.7");
    }, function(){
        $(this).css("opacity", "1");
    });

});

// smooth hover effect
$(".card-box").hover(function(){
    $(this).css("transform", "translateY(-10px) scale(1.05)");
}, function(){
    $(this).css("transform", "none");
});
$(document).ready(function(){

    // Image upload + preview
    $("#imgInput").on("change", function(){
        const file = this.files[0];

        if(file){
            const reader = new FileReader();

            reader.onload = function(e){
                $("#profileImg").attr("src", e.target.result);
            }

            reader.readAsDataURL(file);
        }
    });

    // Get token
    const token = localStorage.getItem("token");

    // AJAX call to backend
    $.ajax({
        url: "http://localhost:8080/api/v1/profile/me",
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token
        },
        success: function(res){
            console.log(res)

            let user = res.data;

            console.log(user.name)
            console.log(user.email)

            $("#userName").text(user.name)
            $("#userEmail").text(user.email)

            let firstLetter = user.name.charAt(0).toUpperCase();

            $('#avater').text(firstLetter)

            console.log(firstLetter)
        },
        error: function(err){
            console.error(err);
            console.log("error")
        }
    });

});
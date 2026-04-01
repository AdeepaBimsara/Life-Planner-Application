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

    connectWebSocket()

});

//notification
let stompClient = null;

function connectWebSocket() {
    const socket = new SockJS('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        console.log("Connected: " + frame);

        stompClient.subscribe('/topic/notifications', function(message) {
            const notification = JSON.parse(message.body);

            addNotificationToUI(notification);
        });
    });
}

function addNotificationToUI(notification) {
    const statusClass = notification.status === "Started"
        ? "status-started"
        : "status-ended";

    alert("you have notification")

    $('#notificationTable tbody').append(`
        <tr class="new-notification">
            <td>${notification.task.name}</td>
            <td>${new Date(notification.notificationTime).toLocaleTimeString()}</td>
            <td class="${statusClass}">${notification.status}</td>
        </tr>
    `);
}

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

        $("#name").text(user.name)

        let firstLetter = user.name.charAt(0).toUpperCase();

        $('#avetar').text(firstLetter)

        console.log(firstLetter)
    },
    error: function(err){
        console.error(err);
        console.log("error")
    }
});



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

    alert("New Notification "+notification.task.name)

    $('#notificationTable tbody').append(`
        <tr class="new-notification">
            <td>${notification.task.name}</td>
            <td>${new Date(notification.notificationTime).toLocaleTimeString()}</td>
            <td class="${statusClass}">${notification.status}</td>
        </tr>
    `);

}

function loadNotifications() {

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/v1/notification/getAll",
        success: function (response) {

            const notifications = response.data;
            const table = $('#notificationTable tbody');

            table.empty();

            notifications.forEach(n => {

                const statusClass = n.status === "Started"
                    ? "status-started"
                    : "status-ended";

                const row = `
                    <tr>
                        <td>${n.task?.name || n.taskName}</td>
                        <td>${new Date(n.notificationTime).toLocaleTimeString()}</td>
                        <td class="${statusClass}">${n.status}</td>
                        <td><button class="btn btn-danger btn-sm" onclick="deleteNotifi(${n.id})">Delete</button></td>

                    </tr>
                `;

                table.append(row);
            });
        }
    });
}

function deleteNotifi(id){
    console.log("Delete id" , id);
    if (confirm("Are you Sure delete this notification")){
        $.ajax({
            type: "DELETE",
            url:"http://localhost:8080/api/v1/notification/delete/"+id,
            success:function (response){
                alert(response.message)
                loadNotifications()
            },
            error:function (response){
                console.log(response.responseJSON.message)
            }
        })
    }
}

// connect on page load
$(document).ready(function() {
    connectWebSocket();
    loadNotifications();
});
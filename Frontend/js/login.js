// Get container for panel animation
const container = document.getElementById('container');

// Panel Switch Buttons
$('#signUp').click(function () {
    container.classList.add("right-panel-active");
});

$('#signIn').click(function () {
    container.classList.remove("right-panel-active");
});

// SIGN UP REQUEST
$('#sign-up').click(function (e) {
    e.preventDefault(); // Prevent default form submit (page reload)

    console.log("Clicked SIGN UP");

    let name = $('#name').val();
    let email = $('#email').val();
    let password = $('#password').val();

    $.ajax({
        url: 'http://localhost:8080/api/v1/auth/signup',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            name: name,
            email: email,
            password: password,
            role: "USER"
        }),
        success: function (res) {
            console.log("Signup Response:", res);
            if (res.code === 200) {
                alert("Registered successfully!");
                // Switch to Sign-In panel after signup
                container.classList.remove("right-panel-active");
            } else {
                alert(res.data || "Signup failed");
            }
        },
        error: function (err) {
            console.log("Signup Error:", err);
            alert("Signup request failed. Check console for details.");
        }
    });
});

// SIGN IN REQUEST
$('#sign-in').click(function (e) {
    e.preventDefault(); // Prevent default form submit (page reload)

    console.log("Clicked SIGN IN");

    let email = $('#email-signin').val();
    let password = $('#password-signin').val();

    $.ajax({
        url: 'http://localhost:8080/api/v1/auth/signin',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            email: email,
            password: password
        }),
        success: function (res) {
            console.log("Signin Response:", res);
            if (res.code === 200) {
                // Save token to localStorage
                localStorage.setItem('token', res.data.accessToken);
                alert("Successfully signed in!");
                // Redirect to dashboard page
                window.location.href = '../pages/dashboard.html';
            } else {
                alert(res.data || "Signin failed");
            }
        },
        error: function (err) {
            console.log("Signin Error:", err);
            alert("Signin request failed. Check console for details.");
        }
    });
});
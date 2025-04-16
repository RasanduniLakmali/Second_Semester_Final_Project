$(document).ready(function () {
    function isTokenValid(token) {
        try {
            const payload = JSON.parse(atob(token.split(".")[1]));
            const expiry = payload.exp * 1000;
            return Date.now() < expiry;
        } catch (error) {
            return false;
        }
    }

    const token = localStorage.getItem("jwtToken");

    if (token && isTokenValid(token)) {
        $(".dashboardLoginBtn").hide();
    } else {
        $(".dashboardLoginBtn").show();
        localStorage.removeItem("jwtToken");
    }
});



$("#loginBtn").on("click", function () {
    var role = $("#userRole").val().toUpperCase();

    localStorage.setItem("userRole", role);

    var email = role === "ADMIN" ? $("#adminEmail").val().trim() : $("#otherEmail").val().trim();
    var password = role === "ADMIN" ? $("#adminPassword").val().trim() : "";
    var username = role === "ADMIN" ? "" : $("#username").val().trim();

    if (!email || (role === "ADMIN" && !password) || (role !== "ADMIN" && !username)) {
        alert("Please fill in all required fields.");
        return;
    }

    $.ajax({
        url: 'http://localhost:8080/api/v1/auth/authenticate',
        method: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
            email: email,
            password: password,
            name: username,
            role: role
        }),

        success: function (response) {
            console.log("Login successful:", response);

            localStorage.setItem("jwtToken", response.data.token);

            localStorage.setItem("loggedInEmail", response.data.email);


            if (response && response.data && response.data.token) {
                console.log("Token received:", response.data.token);

                onLoginSuccess(response.data);

                if (role === "ADMIN") {
                    Swal.fire({
                        icon: 'success',
                        title: 'Success!',
                        text: "Admin Login Successful!",
                        confirmButtonColor: '#3085d6',
                        timer: 2000,
                        timerProgressBar: true,
                        showConfirmButton: false
                    }).then(() => {
                        window.location.href = "admin_dashboard.html";
                    });

                } else if (role === "STUDENT") {

                    Swal.fire({
                        icon: 'success',
                        title: 'Success!',
                        text: "Student Login Successful!",
                        confirmButtonColor: '#3085d6',
                        timer: 2000,
                        timerProgressBar: true,
                        showConfirmButton: false
                    }).then(() => {
                        window.location.href = "index.html";
                    });
                    console.log(response.data.email);
                } else if (role === "INSTRUCTOR") {

                    Swal.fire({
                        icon: 'success',
                        title: 'Success!',
                        text: "Instructor Login Successful!",
                        confirmButtonColor: '#3085d6',
                        timer: 2000,
                        timerProgressBar: true,
                        showConfirmButton: false
                    }).then(() => {
                        window.location.href = "index.html";
                    });
                }
            } else {
                alert("Login successful, but token is missing. Please try again.");
            }
        },

        error: function (xhr) {

            let errorMessage = "Login failed. Please check your credentials!";
            if (xhr.responseJSON && xhr.responseJSON.message) {
                errorMessage = xhr.responseJSON.message;
            }

            Swal.fire({
                icon: 'error',
                title: 'Error!',
                text: errorMessage,
                confirmButtonColor: '#d33'
            });
        }
    });
});



function onLoginSuccess(response) {

    localStorage.setItem("jwtToken", response.token);
    const token = localStorage.getItem("jwtToken");
    console.log("Token after saving:", token);
}


$(document).ready(function () {
    const token = localStorage.getItem("jwtToken");
    const email = localStorage.getItem("loggedInEmail");

    if (token && email) {
        $.ajax({
            url: `http://localhost:8080/api/v1/admin/get-by-email/${email}`,
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            },
            success: function (data) {
                console.log("Admin data loaded:", data);


                $("#adminName").val(data.name);
                $("#adminEmail").val(data.email);
                $("#mobile").val(data.mobile);
            },
            error: function (xhr) {
                console.error("Failed to load admin profile:", xhr);
            }
        });
    }
});


$("#updateProfile").on("click", function () {

    const adminName = $("#adminName").val();
    const adminEmail = $("#adminEmail").val();
    const mobile = $("#mobile").val();

    $.ajax({
        url: `http://localhost:8080/api/v1/admin/updateProfile`,
        method: 'PUT',

        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
            name: adminName,
            email: adminEmail,
            mobile: mobile,
        }),
        success: function (response) {
            Swal.fire({
                icon: 'success',
                title: 'Updated!',
                text: response.message,
                confirmButtonColor: '#3085d6'
            });
        },
        error: function (xhr) {
            Swal.fire({
                icon: 'error',
                title: 'Error!',
                text: xhr.message,
                confirmButtonColor: '#d33'
            });
        }
    })
})



$("#changePasswordBtn").on("click", function () {
    const currentPassword = $("#currentPassword").val().trim();
    const newPassword = $("#newPassword").val().trim();
    const confirmPassword = $("#confirmPassword").val().trim();
    const email = localStorage.getItem("loggedInEmail");
    const token = localStorage.getItem("jwtToken");

    if (!currentPassword || !newPassword || !confirmPassword) {
        Swal.fire("Error", "Please fill in all password fields.", "error");
        return;
    }

    if (newPassword !== confirmPassword) {
        Swal.fire("Mismatch", "New password and confirm password do not match.", "warning");
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/admin/change-password",
        method: "POST",
        contentType: "application/json",
        headers: {
            "Authorization": `Bearer ${token}`
        },
        data: JSON.stringify({
            email: email,
            currentPassword: currentPassword,
            newPassword: newPassword
        }),
        success: function () {
            Swal.fire("Success", "Password changed successfully!", "success");
            $("#currentPassword").val("");
            $("#newPassword").val("");
            $("#confirmPassword").val("");
        },
        error: function (xhr) {
            let errorMessage = "Password change failed.";
            if (xhr.responseJSON && xhr.responseJSON.message) {
                errorMessage = xhr.responseJSON.message;
            }
            Swal.fire("Error", errorMessage, "error");
        }
    });
});

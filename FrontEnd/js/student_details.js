$(document).ready(function () {
    loadAdmins();
    fetchStudentData();

})


/*---------------------------Load admins---------------------------*/

function loadAdmins(){

    const token = localStorage.getItem("jwtToken");  // Get the JWT token from localStorage
    console.log(token)
    if (!token) {
        alert("Token is missing. Please log in again.");
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/admin/getAdId",
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token
        },
        success: function(data){
            let cmbAdmin = $("#adminId");
            let cmbAdmin2 = $("#adminId2");
            console.log(data);
            cmbAdmin.empty();
            cmbAdmin2.empty();

            cmbAdmin.append('<option value="">Select Admin Id</option>');
            cmbAdmin2.append('<option value="">Select Admin Id</option>');

            // Assuming data is a list of admin IDs
            data.forEach(admin => {
                cmbAdmin.append(`<option value="${admin}">${admin}</option>`);
                cmbAdmin2.append(`<option value="${admin}">${admin}</option>`);
            });
        },
        error: function () {
            alert("Error loading admin ids.");
        }
    });
}



/*-----------------------------Save student----------------------------*/

$('#saveBtn').click(function () {
    console.log("save button clicked");

    const token = localStorage.getItem("jwtToken");
    console.log(token);
    if (!token) {
        alert("Token is missing. Please log in again.");
        return;
    }

    const formData = new FormData();
    formData.append("student_name", $('#studentName').val());
    formData.append("age", $('#age').val());
    formData.append("email", $('#email').val());
    formData.append("phone", $('#phone').val());
    formData.append("address", $('#address').val());
    formData.append("school", $('#school').val());
    formData.append("image", $('#image')[0].files[0]);
    formData.append("adminId", $('#adminId').val());

    $.ajax({
        url: "http://localhost:8080/api/v1/student/save",
        method: "POST",
        processData: false,
        contentType: false,
        data: formData,
        headers: {
            "Authorization": "Bearer " + token
        },
        success: function (data) {
            console.log("Student saved:", data);

            if (data.userId) {
                localStorage.setItem("userId", data.userId);
            }

            Swal.fire({
                icon: "success",
                title: "Success!",
                text: "Student saved successfully.",
                showConfirmButton: false,
                timer: 2000
            });

            fetchStudentData();
            clearFields();
        },

        error: function (xhr) {
            console.log("Error Response:", xhr);

            if (xhr.status === 400 && Array.isArray(xhr.responseJSON)) {
                let messages = xhr.responseJSON;

                let messageHtml = "<ul>";
                messages.forEach(msg => {
                    messageHtml += `<li>${msg}</li>`;
                });
                messageHtml += "</ul>";

                Swal.fire({
                    icon: "error",
                    title: "Validation Errors",
                    html: messageHtml
                });

            } else if (xhr.responseJSON && xhr.responseJSON.message) {
                Swal.fire({
                    icon: "error",
                    title: "Error",
                    text: xhr.responseJSON.message
                });
            } else {
                // Swal.fire({
                //     icon: "error",
                //     title: "Error",
                //     text: "An unexpected error occurred."
                // });
            }
        }

    });
});


/*------------------------------Load student data--------------------------*/

const fetchStudentData = () => {

    const token = localStorage.getItem("jwtToken");  // Get the JWT token from localStorage

    if (!token) {
        alert("Token is missing. Please log in again.");
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/student/getAll",
        method: "GET",

        success: (res) => {
            $('#studentTableBody').empty();

            res.forEach(student => {
                $('#studentTableBody').append(`
                    <tr>
                        <td>${student.student_name}</td>
                        <td>${student.address}</td>
                        <td>${student.phone}</td>
                        <td>
                            <img src="http://localhost:8080/uploads/${student.image}" width="50" height="50" class="rounded-circle"/>
                        </td>
                        <td>
                            <button class="btn btn-warning btn-sm edit-btn" id="editBtn">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn btn-success btn-sm view-btn" data-phone="${student.phone}">
                                <i class="bi bi-eye-fill"></i>
                            </button>
                            <button class="btn btn-danger btn-sm delete-btn" id="deleteBtn" data-phone="${student.phone}">
                                <i class="fas fa-trash"></i>
                            </button>
                        </td>
                    </tr>
                `);
            });


            $(".view-btn").click(function () {
                let phone = $(this).data("phone");

                $.ajax({
                    url: `http://localhost:8080/api/v1/student/getStudent/${phone}`,
                    method: "GET",
                    success: (data) => {
                        $("#studentPhoto").attr("src", `http://localhost:8080/uploads/${data.image}`);
                        $("#studentName3").text(data.student_name);
                        $("#studentPhone3").text(data.phone);
                        $("#studentAddress3").text(data.address);
                        $("#studentEmail3").text(data.email);
                        $("#studentAge3").text(data.age);
                        $("#studentSchool3").text(data.school);
                        $("#adminId3").text(data.admin_id);

                        // Show the modal
                        $("#studentDetailsModal").modal("show");
                    },
                    error: (err) => {
                        console.error("Error fetching student details", err);
                    }
                });
            });
        },
        error: (err) => {
            console.error(err);
        }
    });
};


/*------------------------Show update form-------------------------*/

$(document).on("click", "#editBtn", function () {

    loadAdmins();


    let row = $(this).closest('tr');

    // Get data from the row cells
    let name = row.find('td').eq(0).text();
    let address = row.find('td').eq(1).text();
    let contact = row.find('td').eq(2).text();
    let image = row.find('td').eq(3).text();

    // Set the values in the modal
    $("#studentName2").val(name);
    $("#studentAddress").val(address);
    $("#studentPhone").val(contact);
    // $("#studentImage").val(image);

    const token = localStorage.getItem("jwtToken");  // Get the JWT token from localStorage
    console.log(token)
    if (!token) {
        alert("Token is missing. Please log in again.");
        return;
    }

    $.ajax({

        url: `http://localhost:8080/api/v1/student/getStudent/${contact}`,
        method: "GET",

        headers: {
            "Authorization": "Bearer " + token
        },

        success: function (data) {

            console.log(data);

            $("#studentAge").val(data.age);
            $("#studentEmail").val(data.email);
            $("#studentSchool").val(data.school);
            $("#adminId2").val(data.admin_id).change();
            $("#imageUrl").val(data.image);
        },
        error: function (xhr) {
            console.log("Error Response:", xhr);

            let errorMessage = "Error fetching student details!";
            if (xhr.responseJSON && xhr.responseJSON.message) {
                errorMessage = xhr.responseJSON.message;
            }

            alert(errorMessage);
        }
    })


    $("#editStudentModal").modal("show");
});



/*-------------------------------Update student----------------------------*/

$("#updateBtn").click(function () {

    const token = localStorage.getItem("jwtToken");
    console.log(token)

    if (!token) {
        alert("Token is missing. Please log in again.");
        return;
    }

    const formData = new FormData();

    formData.append("student_name", $('#studentName2').val());
    formData.append("age", $('#studentAge').val());
    formData.append("email", $('#studentEmail').val());
    formData.append("phone", $('#studentPhone').val());
    formData.append("address", $('#studentAddress').val());
    formData.append("school", $('#studentSchool').val());

    let imageFile = $('#studentImage')[0].files[0];
    if (imageFile) {
        formData.append("image", imageFile);
    }

    formData.append("adminId", $('#adminId2').val());

    $.ajax({
        url: "http://localhost:8080/api/v1/student/update",
        method: "PUT",
        headers: {
            "Authorization": "Bearer " + token
        },

        processData: false,
        contentType: false,
        data: formData,

        success: function (data) {
            console.log("Success:", data);
            alert("Student updated successfully.");
            fetchStudentData();
            clearFields();
        },
        error: function (xhr) {
            console.log("Error Response:", xhr);

            let errorMessage = "Error updating student.";
            if (xhr.responseJSON && xhr.responseJSON.message) {
                errorMessage = xhr.responseJSON.message;
            }

            alert(errorMessage);
        }
    });
});



/*-----------------------------ClearFields----------------------------*/

function clearFields(){
    $('#studentName').val("");
    $('#studentAddress').val("");
    $('#age').val("");
    $('#email').val("");
    $('#address').val("");
    $('#phone').val("");
    $('#adminId').val("");
    $('#adminId2').val("");
    $('#studentName2').val("");
    $('#studentImage').val("");
    $('#studentSchool').val("");
    $('#studentPhone').val("");
    $('#studentEmail').val("");
    $('#studentAge').val("");
    $('#image').val("");
}



$(document).on("click", "#deleteBtn", function () {

    let row = $(this).closest('tr');
    let contact = row.find('td').eq(2).text();

    if (confirm("Are you sure ?")) {

        $.ajax({
            url: `http://localhost:8080/api/v1/student/delete/${contact}`,
            method: "DELETE",

            success: (res) =>{
                alert("Student deleted successfully!")
                fetchStudentData();
            },
            error: (err) =>{
                alert("Student not deleted!")
            }
        })
    }

})


$(document).ready(function () {
    $("#searchInput").on("keyup", function () {
        const inputValue = $(this).val().trim().toLowerCase();

        $("#studentTableBody tr").each(function () {
            const subjectName = $(this).find("td").eq(0).text().trim().toLowerCase();

            if (subjectName.includes(inputValue)) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    });

});



$(document).ready(function () {
    loadAdmins();
    fetchSubjectData();

    console.log(localStorage.getItem("token"), "meka awe local eken");
})

/*---------------------------Load admins---------------------------*/

function loadAdmins(){

    const token = localStorage.getItem("jwtToken");
    console.log(token)

    if (!token) {
        alert("Token is missing. Please log in again.");
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/admin/getAdId",
        method: "GET",

        headers: {
            "Authorization": "Bearer " + token  // Include the Bearer token in the header
        },

        success: function(data){
            let cmbAdmin = $("#adminId");
            let cmbAdmin2 = $("#adminId2");
            console.log(data);
            cmbAdmin.empty();
            cmbAdmin2.empty();

            cmbAdmin.append('<option value="">Select Admin Id</option>');

            data.forEach(admin => {
                cmbAdmin.append(`<option value="${admin}">${admin}</option>`); // Assuming data is [101, 102, 103]
            });

            data.forEach(admin => {
                cmbAdmin2.append(`<option value="${admin}">${admin}</option>`);
            })
        },

        error: function () {
            alert("Error loading admin ids.");
        }
    });
}


/*------------------------------Save subject---------------------------------*/

$("#saveBtn").click(function () {


    console.log("save button clicked");

    const token = localStorage.getItem("jwtToken");  // Get the JWT token from localStorage
    console.log(token)

    if (!token) {
        alert("Token is missing. Please log in again.");
        return;
    }

    const subjectName = $("#subjectName").val();
    const subjectCode = $("#code").val();
    const adminId = $("#adminId").val();
    const gradeRange = $("#gradeRange").val();
    const timeDuration = $("#time1").val();
    const fees = $("#fee").val();

    $.ajax({
          url: "http://localhost:8080/api/v1/subject/save",
          method: "POST",

          contentType: "application/json",
          dataType: "json",
          data: JSON.stringify({

              subject_name: subjectName,
              subject_code: subjectCode,
              admin_id: adminId,
              grade_range: gradeRange,
              time_duration: timeDuration,
              fees: fees
          }),

          success: function (data) {
              console.log(data);
              alert("Subject saved successfully.");
              fetchSubjectData();
              clearFields()
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
                Swal.fire({
                    icon: "error",
                    title: "Error",
                    text: "An unexpected error occurred."
                });
            }
        }
    })
})


const fetchSubjectData = () => {


    $.ajax({
        url: "http://localhost:8080/api/v1/subject/getAll",
        method: "GET",
        success: (res) => {
            console.log("Fetched Subjects:", res);  // Debugging

            $("#subjectTableBody").empty();

            res.forEach(subject => {
                $("#subjectTableBody").append(`
                <tr>
                    <td>${subject.subject_name}</td>
                    <td>${subject.subject_code}</td>
                    <td>${subject.grade_range}</td>
                    <td>${subject.time_duration}</td>
                    <td>${subject.fees}</td>
                    <td>${subject.admin_id ? subject.admin_id : 'N/A'}</td> <!-- Ensure it displays correctly -->
                    <td>
                        <button class="btn btn-warning btn-sm edit-btn" id="editBtn">
                            <i class="fas fa-edit"></i>
                        </button>
                        <button class="btn btn-success btn-sm view-btn" id="viewBtn">
                            <i class="bi bi-eye-fill"></i>
                        </button>
                        <button class="btn btn-danger btn-sm delete-btn" id="deleteBtn">
                            <i class="fas fa-trash"></i>
                        </button>
                    </td>
                </tr>
            `);
            });

            $(".view-btn").click(function () {
                let row = $(this).closest('tr');

                // Get data from the row cells
                let subjectName = row.find('td').eq(0).text();
                let code = row.find('td').eq(1).text();
                let gradeRange = row.find('td').eq(2).text();
                let timeDuration = row.find('td').eq(3).text();
                let fees = row.find('td').eq(4).text();
                let adminId = row.find('td').eq(5).text();

                $("#subjectName3").text(subjectName);
                $("#subjectCode3").text(code);
                $('#gradeRange3').text(gradeRange);
                $("#time3").text(timeDuration);
                $("#fees3").text(fees);
                $("#adminId3").text(adminId);

                $("#subjectDetailsModal").modal("show");
            });
        },
        error: function () {
            alert("Error loading subject data");
        }

    })
}


/*------------------------Show update form-------------------------*/

$(document).on("click", "#editBtn", function () {

    let row = $(this).closest('tr');

    // Get data from the row cells
    let subjectName = row.find('td').eq(0).text();
    let code = row.find('td').eq(1).text();
    let gradeRange = row.find('td').eq(2).text();
    let timeDuration = row.find('td').eq(3).text();
    let fees = row.find('td').eq(4).text();
    let adminId = row.find('td').eq(5).text();


    $("#subjectName2").val(subjectName);
    $("#code2").val(code);
    $("#adminId2").val(adminId);
    $('#gradeRange2').val(gradeRange);
    $("#fee2").val(fees);
    $('#time').val(timeDuration);


    // Show the modal
    $("#editSubjectModal").modal("show");
});



/*--------------------------------------Update subject--------------------------*/

$("#updateBtn").click(function () {

    const token = localStorage.getItem("jwtToken");  // Get the JWT token from localStorage
    console.log(token)

    if (!token) {
        alert("Token is missing. Please log in again.");
        return;
    }

    const subjectName = $("#subjectName2").val();
    const subjectCode = $("#code2").val();
    const adminId = $("#adminId2").val();
    const gradeRange = $("#gradeRange2").val();
    const timeDuration = $("#time").val();
    const fees = $("#fee2").val();

    $.ajax({
        url: "http://localhost:8080/api/v1/subject/update",
        method: "PUT",

        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({

            subject_name: subjectName,
            subject_code: subjectCode,
            admin_id: adminId,
            grade_range: gradeRange,
            time_duration: timeDuration,
            fees: fees
        }),

        success: function (data) {
            console.log(data);
            alert("Subject updated successfully.");
            fetchSubjectData();
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
                Swal.fire({
                    icon: "error",
                    title: "Error",
                    text: "An unexpected error occurred."
                });
            }
        }
    })
})


$(document).on("click", "#deleteBtn", function () {

    let row = $(this).closest('tr');
    let subjectName = row.find('td').eq(0).text();

    if (confirm("Are you sure ?")) {

        $.ajax({
            url: `http://localhost:8080/api/v1/subject/delete/${subjectName}`,
            method: "DELETE",

            success: (res) =>{
                alert("Subject deleted successfully!")
                fetchStudentData();
                clearFields();
            },
            error: (err) =>{
                alert("Subject not deleted!")
            }
        })
    }

})


$(document).ready(function () {
    $("#searchInput").on("keyup", function () {
        const inputValue = $(this).val().trim().toLowerCase();

        $("#subjectTableBody tr").each(function () {
            const subjectName = $(this).find("td").eq(0).text().trim().toLowerCase();

            if (subjectName.includes(inputValue)) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    });

});



function clearFields() {
    $("#subjectName").val("");
    $("#subjectName2").val("");
    $("#subjectName3").val("");
    $("#code").val("");
    $("#code2").val("");
    $("#subjectCode3").val("");
    $("#adminId").val("");
    $('#adminId2').val("");
    $("#adminId3").val("");
    $("#gradeRange").val("");
    $("#gradeRange2").val("");
    $("#gradeRange3").val("");
}
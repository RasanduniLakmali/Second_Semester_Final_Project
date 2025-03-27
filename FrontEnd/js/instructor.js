
$(document).ready(function(){
    loadAdmins();
    loadSubjectCodes();
    fetchInstructorData();
    fetchInstructorNames();
})


$("#saveBtn").click(function () {

    const formData = new FormData();

    formData.append("instructor_name", $("#instructorName").val());
    formData.append("image", $('#image')[0].files[0]);
    formData.append("address", $('#address').val());
    formData.append("phone", $('#mobile').val());
    formData.append("email", $('#email').val());
    formData.append("qualification", $('#qualification').val());
    formData.append("subject_code", $('#subjectCode').val());
    formData.append("subject_id",$('#subjectId').val());
    formData.append("admin_id",$('#adminId').val());

    $.ajax({
        url: "http://localhost:8080/api/v1/instructor/save",
        method: "POST",

        processData: false,
        contentType: false,
        data: formData,

        success: function (data) {
            console.log(data);
            alert("Instructor saved successfully.");
            fetchInstructorData();
        },
        error: function () {
            alert("Instructor not saved.");
        }
    })
})


const fetchInstructorData = () => {

    $.ajax({
        url: "http://localhost:8080/api/v1/instructor/getAll",
        method: "GET",
        success: (res) => {
            $("#instructorTableBody").empty();

            res.forEach(instructor => {
                $("#instructorTableBody").append(`
                    <tr>
                        <td>${instructor.instructor_name}</td>
                        <td>${instructor.phone}</td>
                        <td>${instructor.email}</td>
                        <td>${instructor.subject_code}</td>
                        <td>
                            <img src="http://localhost:8080/uploads/${instructor.image}" width="50" height="50" class="rounded-circle"/>
                        </td>
                        <td>
                            <button class="btn btn-warning btn-sm edit-btn" id="editBtn" data-bs-target="#editSubjectModal">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn btn-success btn-sm view-btn" id="viewBtn">
                                <i class="bi bi-eye-fill"></i>
                            </button>
                            <button class="btn btn-danger btn-sm delete-btn" id="deleteBtn" >
                                <i class="fas fa-trash"></i>
                            </button>
                        </td>
                    </tr>
                `);
            });

            $(".view-btn").click(function () {
                let row = $(this).closest('tr');

                let instructorName = row.find('td').eq(0).text();
                let phone = row.find('td').eq(1).text();
                let email = row.find('td').eq(2).text();
                let subjectCode = row.find('td').eq(3).text();

                $("#instructorName3").text(instructorName);
                $("#phone3").text(phone);
                $("#email3").text(email);
                $("#subjectCode3").text(subjectCode);


                $.ajax({
                    url: `http://localhost:8080/api/v1/instructor/getInstructor/${instructorName}`,
                    method: "GET",

                    success: function (data) {
                        console.log(data);
                        $("#qualification3").text(data.qualification);
                        $("#address3").text(data.address);
                        $("#subjectId3").text(data.subject_id);
                        $("#adminId3").text(data.admin_id);

                        $("#instructorDetailsModal").modal("show");
                    },
                })

            });

        },
        error: function () {
            alert("Error loading instructor data");
        }
    })

}

function loadAdmins(){

    $.ajax({
        url: `http://localhost:8080/api/v1/admin/getAdName`,
        method: 'GET',

        success: function(data){
            const adminName = $("#adminName");
            const adminName2 = $("#adminName2");

            adminName.empty();
            adminName2.empty();

            adminName.append('<option value="">Select Admin</option>');
            adminName2.append('<option value="">Select Admin</option>');

            data.forEach(admin => {
                adminName.append(`<option value="${admin}">${admin}</option>`); // Assuming data is [101, 102, 103]
            });

            data.forEach(admin => {
                adminName2.append(`<option value="${admin}">${admin}</option>`); // Assuming data is [101, 102, 103]
            });
        },
        error: function () {
            alert("Error loading admin names.");
        }
    })
}


function loadSubjectCodes(){
    $.ajax({
        url: `http://localhost:8080/api/v1/subject/getCodes`,
        method: "GET",

        success: function(data){
            const subjectCode = $("#subjectCode");
            const subjectCode2 = $("#subjectCode2");

            subjectCode.empty();
            subjectCode2.empty();

            subjectCode.append('<option value="">Select Subject Code</option>');
            subjectCode2.append('<option value="">Select Subject Code</option>');

            data.forEach(subject => {
                subjectCode.append(`<option value="${subject}">${subject}</option>`); // Assuming data is [101, 102, 103]
            });

            data.forEach(subject => {
                subjectCode2.append(`<option value="${subject}">${subject}</option>`); // Assuming data is [101, 102, 103]
            });

        },
        error: function () {
            alert("Error loading subject codes.");
        }
    })
}

$("#subjectCode").change(function () {
    const subjectCode = $(this).val();

    if (!subjectCode)return;

    $.ajax({
        url: `http://localhost:8080/api/v1/subject/getId/${subjectCode}`,
        method: "GET",

        success: function (data) {
            $("#subjectId").val(data);
        },
        error: function (){
            alert("Subject Id not found!");
        }
    })
})


$("#subjectCode2").change(function () {
    const subjectCode = $(this).val();

    if (!subjectCode)return;

    $.ajax({
        url: `http://localhost:8080/api/v1/subject/getId/${subjectCode}`,
        method: "GET",

        success: function (data) {
            $("#subjectId2").val(data);
        },
        error: function (){
            alert("Subject Id not found!");
        }
    })
})


$('#adminName').change(function () {
    const adminName = $(this).val();

    if (!adminName) return;

    $.ajax({
        url: `http://localhost:8080/api/v1/admin/getAdmin/${adminName}`,
        method: "GET",

        success: function (data) {
            $("#adminId").val(data);
        },

        error: function (xhr) {
            if (xhr.status === 404) {
                alert("Admin not found!");
                $("#adminId").val('');
            } else {
                alert("Error loading admin ID.");
            }
        }
    });
});


/*------------------------Show update form-------------------------*/

$(document).on("click", "#editBtn", function () {

    loadAdmins();


    // Get the parent row of the clicked edit button
    let row = $(this).closest('tr');

    // Get data from the row cells
    let instructorName = row.find('td').eq(0).text();
    let phone = row.find('td').eq(1).text();
    let email = row.find('td').eq(2).text();
    let subjectCode = row.find('td').eq(3).text();

    // Set the values in the modal
    $("#instructorName2").val(instructorName);
    $("#mobile2").val(phone);
    $("#email2").val(email);
    $("#subjectCode2").val(subjectCode).change();

    $.ajax({
        url: `http://localhost:8080/api/v1/instructor/getInstructor/${instructorName}`,
        method: "GET",
        success: function (data) {
            $("#address2").val(data.address);
            $("#qualification2").val(data.qualification);
            $("#subjectId2").val(data.subject_id);
            $("#adminId2").val(data.admin_id);
            $("#imageUrl").val(data.image)

            // Get Admin Name and Set It
            $.ajax({
                url: `http://localhost:8080/api/v1/admin/getAdminName/${data.admin_id}`,
                method: "GET",
                success: function (adminData) {
                    $("#adminName2").val(adminData).change(); // Ensure dropdown updates
                },
                error: function () {
                    console.log("Error fetching admin name.");
                }
            });

            $("#editInstructorModal").modal("show");
        },
        error: function () {
            alert("Error loading instructor details.");
        }
    });

});


$("#updateBtn").click(function () {

    const formData = new FormData();

    formData.append("instructor_name", $("#instructorName2").val());

    let imageFile = $('#instructorImage')[0].files[0];
    if (imageFile) {
        formData.append("image", imageFile);
    }

    formData.append("address", $('#address2').val());
    formData.append("phone", $('#mobile2').val());
    formData.append("email", $('#email2').val());
    formData.append("qualification", $('#qualification2').val());
    formData.append("subject_code", $('#subjectCode2').val());
    formData.append("subject_id",$('#subjectId2').val());
    formData.append("admin_id",$('#adminId2').val());

    $.ajax({
        url: "http://localhost:8080/api/v1/instructor/update",
        method: "PUT",

        processData: false,
        contentType: false,
        data: formData,

        success: function (data) {
            console.log(data);
            alert("Instructor updated successfully.");
            fetchInstructorData();
            clearFields();
        },
        error: function () {
            alert("Instructor not updated.");
        }
    })
})


$(document).on("click", "#deleteBtn", function () {

    let row = $(this).closest('tr');
    let email = row.find('td').eq(2).text();

    if (confirm("Are you sure ?")) {

        $.ajax({
            url: `http://localhost:8080/api/v1/instructor/delete/${email}`,
            method: "DELETE",

            success: (res) =>{
                alert("Instructor deleted successfully!")
                fetchInstructorData();
                clearFields();
            },
            error: (err) =>{
                alert("Instructor not deleted!")
            }
        })
    }

})


function clearFields(){

    $("#instructorName").val("");
    $("#address").val("");
    $("#mobile").val("");
    $("#email").val("");
    $("#qualification").val("");
    $("#subjectCode").val("");
    $("#subjectId").val("")
    $("#adminId").val("");
    $("#instructorName2").val("");
    $("#address2").val("");
    $("#mobile2").val("");
    $("#email2").val("");
    $("#qualification2").val("");
    $("#subjectCode2").val("");
    $("#subjectId2").val("")
    $("#adminId2").val("");
}

function fetchInstructorNames(){

    $.ajax({
        url: "http://localhost:8080/api/v1/instructor/getInstName",
        method: "GET",

        success: function(data){
            let instructorName = $("#instructorName4");


            console.log(data);
            instructorName.empty();


            instructorName.append('<option value="">Select Instructor Name</option>');

            data.forEach(instructor => {
                instructorName.append(`<option value="${instructor}">${instructor}</option>`);
            });


            },

        error: function () {
            alert("Error loading instructor names.");
        }
    });
}

$("#assignBtn").click(function () {
    const instructorName = $("#instructorName4").val();
    const grade6 = $("#grade6").val();
    const grade7 = $("#grade7").val();
    const grade8 = $("#grade8").val();
    const grade9 = $("#grade9").val();
    const grade10 = $("#grade10").val();
    const grade11 = $("#grade11").val();
    const al = $("#al").val();

    if (!instructorName || !grade6 && !grade7 && !grade8 && !grade9 && !grade10 && !grade11 && !al) {
        alert("Please select instructor and at least one grade.");
        return;
    }

    // Get instructor ID from backend
    $.ajax({
        url: `http://localhost:8080/api/v1/instructor/getId/${instructorName}`,
        method: "GET",
        success: function (instructorId) {

            // Create a list of selected grades and their names
            const selectedGrades = [];
            if (grade6) selectedGrades.push({ gradeId: "Grade 6" });
            if (grade7) selectedGrades.push({ gradeId: "Grade 7" });
            if (grade8) selectedGrades.push({ gradeId: "Grade 8" });
            if (grade9) selectedGrades.push({ gradeId: "Grade 9" });
            if (grade10) selectedGrades.push({ gradeId: "Grade 10" });
            if (grade11) selectedGrades.push({ gradeId: "Grade 11" });
            if (al) selectedGrades.push({ gradeId: "A/L" });

            // For each selected grade, get the classId from backend and then send class-instructor data
            selectedGrades.forEach(function (grade) {
                $.ajax({
                    url: `http://localhost:8080/api/v1/class/getClId/${grade.gradeId}`,
                    method: "GET",
                    success: function (classId) {
                        // Prepare the data to save in the database
                        const classInstructorData = {
                            class_id: classId,  // Make sure classId is passed correctly
                            class_name: grade.gradeId,  // The grade name
                            instructor_id: instructorId,  // The instructor ID
                            instructor_name: instructorName  // The instructor name
                        };

                        console.log(classInstructorData); // Check the data being sent

                        // Send the data to the backend to save in the database
                        $.ajax({
                            url: 'http://localhost:8080/api/v1/clInstructor/save',
                            method: 'POST',
                            contentType: 'application/json',
                            data: JSON.stringify(classInstructorData),  // Ensure data is in JSON format
                            success: function (response) {
                                alert('Classes assigned successfully!');
                                // Optionally, close the modal or reset the form
                                $('#addClassModal').modal('hide');
                            },
                            error: function (error) {
                                console.error('Error while saving:', error);
                                alert('Error while assigning classes. Please try again.');
                            }
                        });
                    },
                    error: function (error) {
                        console.error('Error while fetching class ID for ' + grade.gradeId, error);
                        alert('Error while fetching class ID.');
                    }
                });
            });
        },
        error: function (error) {
            console.error('Error while fetching instructor ID.', error);
            alert('Error while fetching instructor ID.');
        }
    });
});


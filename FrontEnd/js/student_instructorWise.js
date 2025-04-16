$(document).ready(function () {
    loadClasses();
    fetchInstructorNames();


    $("#instructorSelect, #classSelect").change(function () {
        let instructorName = $("#instructorSelect").val();
        let className = $("#classSelect").val();
        let classId = null;

        $.ajax({

            url: `http://localhost:8080/api/v1/class/getClId/${className}`,
            type: "GET",

            success: function (data) {
                classId = data;

                if (!instructorName || !classId) {
                    $("#studentTableBody2").html("");
                    return;
                }

                $.ajax({
                    type: "GET",
                    url: `http://localhost:8080/api/v1/student/getStudents?instructorName=${instructorName}&classId=${classId}`,
                    contentType: "application/json",

                    success: function (response) {

                        $("#studentTableBody2").html("");

                        if (response.length === 0) {
                            $("#studentTableBody2").append("<tr><td colspan='6'>No students found</td></tr>");
                            return;
                        }

                        response.forEach(student => {
                            let row = `<tr>
                        <td>${student.student_name}</td>
                        <td>${student.age}</td>
                        <td>${student.phone}</td>
                        <td>${student.email}</td>
                        <td>${student.address}</td>
                        <td>${student.school}</td>
                    </tr>`;
                            $("#studentTableBody2").append(row);
                        });
                    },
                    error: function (xhr, status, error) {
                        console.error("AJAX Error:", error);
                        alert("Failed to fetch students.");
                    }
                });
            }
        })

    });
});


function fetchInstructorNames(){

    $.ajax({
        url: "http://localhost:8080/api/v1/instructor/getInstName",
        method: "GET",

        success: function(data){

            let instructorName = $("#instructorSelect")


            console.log(data);

            instructorName.empty()

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

function loadClasses(){

    $.ajax({
        url: `http://localhost:8080/api/v1/class/getName`,
        method: 'GET',

        success: function (data) {
            const className = $("#classSelect");

            className.empty();

            className.append('<option value="">Select Class</option>');


            data.forEach(classEntity => {
                className.append(`<option value="${classEntity}">${classEntity}</option>`); // Assuming data is [101, 102, 103]
            });


        },
        error: function () {
            alert("Error loading class names.");
        }
    })
}
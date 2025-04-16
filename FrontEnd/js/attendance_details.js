$(document).ready(function () {
    loadStudents();
    loadSubjects();
    fetchAttendanceData();
})

function loadStudents(){

    $.ajax({
        url: `http://localhost:8080/api/v1/student/getStName`,
        method: 'GET',

        success: function (data) {
            const studentName = $("#studentName");
            const studentName2 = $("#studentName2");

            studentName.empty();
            studentName2.empty();

            studentName.append('<option value="">Select Student</option>');
            studentName2.append('<option value="">Select Student</option>');


            data.forEach(student => {
                studentName.append(`<option value="${student}">${student}</option>`);
            });

            data.forEach(student => {
                studentName2.append(`<option value="${student}">${student}</option>`);
            });


        },
        error: function () {
            alert("Error loading student names.");
        }
    })
}


function loadSubjects(){

    $.ajax({
        url: `http://localhost:8080/api/v1/subject/getSubName`,
        method: 'GET',

        success: function (data) {
            const subjectName = $("#subjectName");
            const subjectName2 = $("#subjectName2");

            subjectName.empty();
            subjectName2.empty();

            subjectName.append('<option value="">Select Subject</option>');
            subjectName2.append('<option value="">Select Subject</option>');

            data.forEach(subject => {
                subjectName.append(`<option value="${subject}">${subject}</option>`); // Assuming data is [101, 102, 103]
            });

            data.forEach(subject => {
                subjectName2.append(`<option value="${subject}">${subject}</option>`); // Assuming data is [101, 102, 103]
            });


        },
        error: function () {
            alert("Error loading subject names.");
        }
    })
}

$("#saveBtn").click(function () {

    const studentName = $("#studentName").val();
    const subjectName = $("#subjectName").val();
    const className = $("#className").val();
    const date = $("#attendanceDate").val();
    const formattedDate = date.split("/").reverse().join("-");
    const status = $("#status").val();
    let studentId = null;
    let subjectId = null;

    console.log(status);

    $.ajax({
        url: `http://localhost:8080/api/v1/student/getStId/${studentName}`,
        method: "GET",

        success: function (data) {
            console.log(data);
            studentId = data;

            $.ajax({

                url: `http://localhost:8080/api/v1/subject/getSubId/${subjectName}`,
                method: "GET",

                success: function (data) {
                    console.log(data);
                    subjectId=data;

                    $.ajax({
                        url: `http://localhost:8080/api/v1/attendance/save`,
                        method: 'POST',
                        contentType: "application/json",
                        dataType: "json",

                        data: JSON.stringify({
                            attendance_date:formattedDate,
                            status: status,
                            student_name: studentName,
                            class_name: className,
                            subject_name: subjectName,
                            student_id: studentId,
                            subject_id: subjectId
                        }),
                        success: function (data) {
                            Swal.fire({
                                icon: 'success',
                                title: 'Updated!',
                                text: data.message,
                                confirmButtonColor: '#3085d6'
                            });
                            clearFields();
                            fetchAttendanceData();
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
                },
                error: function (xhr) {
                    alert("Error loading subject id.");
                }
            })
        },
        error: function (xhr) {
            alert("Error loading student id.");
        }
    })

})


$('#studentName').change(function () {
    const studentName = $(this).val();

    if (!studentName) return;

    $.ajax({
        url: `http://localhost:8080/api/v1/stClass/getClass/${studentName}`,
        method: "GET",

        success: function (data) {
            console.log(data);

            $("#className").val(data);
        },

        error: function (xhr) {
           alert("Error loading class name!");
        }
    });
});


$('#studentName2').change(function () {
    const studentName = $(this).val();

    if (!studentName) return;

    $.ajax({
        url: `http://localhost:8080/api/v1/stClass/getClass/${studentName}`,
        method: "GET",

        success: function (data) {
            console.log(data);

            $("#className2").val(data);
        },

        error: function (xhr) {
            alert("Error loading class name!");
        }
    });
});


function fetchAttendanceData(){

    $.ajax({
        url: "http://localhost:8080/api/v1/attendance/getAll",
        method: "GET",
        success: (res) => {
            $("#attendanceTableBody").empty();

            res.forEach((attendance) => {
                $("#attendanceTableBody").append(`
                    <tr>
                        <td>${attendance.student_name}</td>
                        <td>${attendance.class_name}</td>
                        <td>${attendance.subject_name}</td>
                        <td>${attendance.attendance_date}</td>
                        <td>${attendance.status}</td>
                      
                       
                        <td>
                            <button class="btn btn-warning btn-sm edit-btn" id="editBtn" data-bs-target="#editAttendanceModal">
                                <i class="fas fa-edit"></i>
                            </button>
                        
                            <button class="btn btn-danger btn-sm delete-btn" id="deleteBtn" >
                                <i class="fas fa-trash"></i>
                            </button>
                        </td>
                    </tr>
                `);
            });
        }
    })
}



$(document).on("click", "#editBtn", function () {


    let row = $(this).closest('tr');


    let studentName = row.find('td').eq(0).text();
    let className = row.find('td').eq(1).text();
    let subjectName = row.find('td').eq(2).text();
    let date = row.find('td').eq(3).text();
    let status = row.find('td').eq(4).text();


    $("#studentName2").val(studentName).change();
    $("#className2").val(className);
    $("#subjectName2").val(subjectName).change();
    $("#attendanceDate2").val(date);
    $("#status2").val(status);


    // Show the modal
    $("#editAttendanceModal").modal("show");
});


$("#updateBtn").click(function () {

    const studentName = $("#studentName2").val();
    const subjectName = $("#subjectName2").val();
    const className = $("#className2").val();
    const date = $("#attendanceDate2").val();
    const formattedDate = date.split("/").reverse().join("-");
    const status = $("#status2").val();
    let studentId = null;
    let subjectId = null;

    console.log(status);

    $.ajax({
        url: `http://localhost:8080/api/v1/student/getStId/${studentName}`,
        method: "GET",

        success: function (data) {
            console.log(data);
            studentId = data;

            $.ajax({

                url: `http://localhost:8080/api/v1/subject/getSubId/${subjectName}`,
                method: "GET",

                success: function (data) {
                    console.log(data);
                    subjectId=data;

                    $.ajax({
                        url: `http://localhost:8080/api/v1/attendance/update`,
                        method: 'PUT',
                        contentType: "application/json",
                        dataType: "json",

                        data: JSON.stringify({
                            attendance_date:formattedDate,
                            status: status,
                            student_name: studentName,
                            class_name: className,
                            subject_name: subjectName,
                            student_id: studentId,
                            subject_id: subjectId
                        }),
                        success: function (data) {
                            alert(data.msg);
                            clearFields();
                            fetchAttendanceData();
                        },
                        error: function (xhr) {
                            alert(xhr.msg);
                        }
                    })
                },
                error: function (xhr) {
                    alert("Error loading subject id.");
                }
            })
        },
        error: function (xhr) {
            alert("Error loading student id.");
        }
    })

})


$(document).ready(function () {
    $("#searchInput").on("keyup", function () {
        const inputValue = $(this).val().trim().toLowerCase();

        $("#attendanceTableBody tr").each(function () {
            const studentName = $(this).find("td").eq(0).text().trim().toLowerCase();

            if (studentName.includes(inputValue)) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    });

});



function clearFields(){
    $("#studentName").val("");
    $("#subjectName").val("");
    $("#className").val("");
    $("#attendanceDate").val("");
    $("#status").val("");
    $("#studentName2").val("");
    $("#subjectName2").val("");
    $("#className2").val("");
    $("#attendanceDate2").val("");
    $("#status2").val("");
}


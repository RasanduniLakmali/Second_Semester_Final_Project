$(document).ready(function () {
    loadStudents();
    loadClasses();
    loadSubjects();
    fetchInstructorNames();
    fetchStSubjectData();
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


function loadClasses(){

    $.ajax({
        url: `http://localhost:8080/api/v1/class/getName`,
        method: 'GET',

        success: function (data) {
            const className = $("#className");
            const className2 = $("#className2");

            className.empty();
            className2.empty();

            className.append('<option value="">Select Class</option>');
            className2.append('<option value="">Select Class</option>');

            data.forEach(classEntity => {
                className.append(`<option value="${classEntity}">${classEntity}</option>`); // Assuming data is [101, 102, 103]
            });

            data.forEach(classEntity => {
                className2.append(`<option value="${classEntity}">${classEntity}</option>`); // Assuming data is [101, 102, 103]
            });


        },
        error: function () {
            alert("Error loading class names.");
        }
    })
}

function loadSubjects(){

    $.ajax({
        url: `http://localhost:8080/api/v1/subject/getSubName`,
        method: 'GET',

        success: function (data) {
            const subjectName1 = $("#sub1");
            const subjectName2 = $("#sub2");
            const subjectName3 = $("#sub3");
            const subjectName4 = $("#sub4");
            const subjectName5 = $("#sub5");

            const subjectName6 = $("#sub6");
            const subjectName7 = $("#sub7");
            const subjectName8 = $("#sub8");
            const subjectName9 = $("#sub9");
            const subjectName10 = $("#sub10");

            subjectName1.empty();
            subjectName2.empty();
            subjectName3.empty();
            subjectName4.empty();
            subjectName5.empty();

            subjectName6.empty();
            subjectName7.empty();
            subjectName8.empty();
            subjectName9.empty();
            subjectName10.empty();

            subjectName1.append('<option value="">Select Subject 1</option>');
            subjectName2.append('<option value="">Select Subject 2</option>');
            subjectName3.append('<option value="">Select Subject 3</option>');
            subjectName4.append('<option value="">Select Subject 4</option>');
            subjectName5.append('<option value="">Select Subject 5</option>');

            subjectName6.append('<option value="">Select Subject 1</option>');
            subjectName7.append('<option value="">Select Subject 2</option>');
            subjectName8.append('<option value="">Select Subject 3</option>');
            subjectName9.append('<option value="">Select Subject 4</option>');
            subjectName10.append('<option value="">Select Subject 5</option>');

            data.forEach(subject => {
                subjectName1.append(`<option value="${subject}">${subject}</option>`); // Assuming data is [101, 102, 103]
            });

            data.forEach(subject => {
                subjectName2.append(`<option value="${subject}">${subject}</option>`); // Assuming data is [101, 102, 103]
            });
            data.forEach(subject => {
                subjectName3.append(`<option value="${subject}">${subject}</option>`); // Assuming data is [101, 102, 103]
            });
            data.forEach(subject => {
                subjectName4.append(`<option value="${subject}">${subject}</option>`); // Assuming data is [101, 102, 103]
            });
            data.forEach(subject => {
                subjectName5.append(`<option value="${subject}">${subject}</option>`); // Assuming data is [101, 102, 103]
            });


            data.forEach(subject => {
                subjectName6.append(`<option value="${subject}">${subject}</option>`); // Assuming data is [101, 102, 103]
            });

            data.forEach(subject => {
                subjectName7.append(`<option value="${subject}">${subject}</option>`); // Assuming data is [101, 102, 103]
            });
            data.forEach(subject => {
                subjectName8.append(`<option value="${subject}">${subject}</option>`); // Assuming data is [101, 102, 103]
            });
            data.forEach(subject => {
                subjectName9.append(`<option value="${subject}">${subject}</option>`); // Assuming data is [101, 102, 103]
            });
            data.forEach(subject => {
                subjectName10.append(`<option value="${subject}">${subject}</option>`); // Assuming data is [101, 102, 103]
            });


        },
        error: function () {
            alert("Error loading subject names.");
        }
    })
}


$("#saveBtn").on("click", function () {

    const studentName = $("#studentName").val();
    const className = $("#className").val();
    let studentId = null;
    let classId = null;
    let subjects = [];


    // Get Student ID
    $.ajax({
        url: `http://localhost:8080/api/v1/student/getStId/${studentName}`,
        method: 'GET',
        success: function (data) {
            studentId = data;
            console.log("Retrieved studentId:", studentId);

            // Get Class ID
            $.ajax({
                url: `http://localhost:8080/api/v1/class/getClId/${className}`,
                method: 'GET',
                success: function (res) {
                    classId = res;

                    // Save Student-Class Data
                    $.ajax({
                        url: `http://localhost:8080/api/v1/stClass/save`,
                        method: 'POST',
                        contentType: "application/json",
                        dataType: "json",
                        data: JSON.stringify({
                            class_id: classId,
                            student_id: studentId,
                            class_name: className,
                            student_name: studentName,
                        }),
                        success: function () {
                            console.log("Student-Class saved.");
                            saveStudentSubjects(studentId, studentName);
                        },
                        error: function () {
                            alert("Failed to save Student-Class.");
                        }
                    });
                },
                error: function () {
                    alert("Error fetching class ID.");
                }
            });
        },
        error: function () {
            alert("Error fetching student ID.");
        }
    });
});


function saveStudentSubjects(studentId, studentName) {

    console.log(studentId,studentName);

    let subjects = [];

    $(".subjectSelect").each(function () {
        let subjectName = $(this).val();
        let instructorName = $(this).closest(".row").find(".instructorSelect").val(); // Get the corresponding instructor


        console.log("Subject:", subjectName, "Instructor:", instructorName);


        if (!subjectName || subjectName.trim() === "" || !instructorName || instructorName.trim() === "") {
            return; // Continue to the next iteration if empty
        }

        console.log("Processing Subject:", subjectName, "Instructor:", instructorName);

        let promise = $.ajax({
            url: `http://localhost:8080/api/v1/subject/getSubId/${encodeURIComponent(subjectName)}`,
            method: 'GET'
        }).then(function (subjectId) {
            console.log(" Retrieved subjectId:", subjectId);

            return $.ajax({
                url: `http://localhost:8080/api/v1/stSubject/save`,
                method: 'POST',
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify({
                    student_id: studentId,
                    subject_id: subjectId,
                    student_name: studentName,
                    subject_name: subjectName,
                    instructor_name: instructorName
                }),
                success: function () {
                    console.log(` Saved: ${studentName} - ${subjectName} (ID: ${subjectId}) - Instructor: ${instructorName}`);
                },
                error: function () {
                    console.error(` Failed to save: ${studentName} - ${subjectName} - Instructor: ${instructorName}`);
                }
            });
        });

        subjects.push(promise);
    });

    if (subjects.length === 0) {
        alert("No subjects selected.");
        return;
    }

    $.when.apply($, subjects).done(function () {
        alert("All selected subjects with instructors saved successfully.");
    }).fail(function () {
        alert("Failed to save student-subject-instructor data.");
    });
}


$('#studentName').change(function () {
    const studentName = $(this).val();

    const token = localStorage.getItem("jwtToken");  // Get the JWT token from localStorage

    if (!token) {
        alert("Token is missing. Please log in again.");
        return;
    }

    if (!studentName) return;

    $.ajax({
        url: `http://localhost:8080/api/v1/stClass/getClass/${studentName}`,
        method: "GET",

        headers: {
            "Authorization": "Bearer " + token
        },

        success: function (className) {
            $("#className").val(className);


            $.ajax({
                url: `http://localhost:8080/api/v1/stSubject/getSubject/${studentName}`,
                method: "GET",

                headers: {
                    "Authorization": "Bearer " + token
                },

                success: function (subjects) {
                    $(".subjectSelect").each(function (index) {
                        if (subjects[index]) {
                            $(this).val(subjects[index]);
                        }
                    });
                }
            });
        },
        error: function (xhr) {
            if (xhr.status === 404) {
                alert("Student not found!");
                $("#className").val('');
            } else {
                alert("Error loading student class.");
            }
        }
    });
});



$("#updateBtn").on("click", function () {
    const studentName = $("#studentName2").val();
    const className = $("#className2").val();
    const subjectName = $("#sub6").val();
    const instructorName = $("#instName6").val();
    let studentId = null;
    let classId = null;
    let subjectId = null;
    let subjects = [];


    // Get Student ID
    $.ajax({
        url: `http://localhost:8080/api/v1/student/getStId/${studentName}`,
        method: 'GET',
        success: function (data) {
            studentId = data;
            console.log("Retrieved studentId:", studentId);

            // Get Class ID
            $.ajax({
                url: `http://localhost:8080/api/v1/class/getClId/${className}`,
                method: 'GET',
                success: function (res) {
                    classId = res;

                    //Get subject ID
                    $.ajax({
                        url: `http://localhost:8080/api/v1/subject/getSubId/${subjectName}`,
                        method: "GET",

                        success: function (data) {
                            subjectId=data;
                            console.log(data);

                            $.ajax({
                                url: `http://localhost:8080/api/v1/stClass/update`,
                                method: 'PUT',
                                contentType: "application/json",
                                dataType: "json",
                                data: JSON.stringify({
                                    class_id: classId,
                                    student_id: studentId,
                                    class_name: className,
                                    student_name: studentName,
                                }),
                                success: function () {
                                    console.log("Student-Class updated.");
                                    // updateStudentSubjects(studentId, studentName);

                                    $.ajax({
                                        url: `http://localhost:8080/api/v1/stSubject/update`,
                                        method: 'PUT',

                                        contentType: "application/json",
                                        dataType: "json",
                                        data: JSON.stringify({
                                            student_id: studentId,
                                            subject_id: subjectId,
                                            student_name: studentName,
                                            subject_name: subjectName,
                                            instructor_name: instructorName
                                        }),

                                        success: function (data) {
                                            alert(data.message);
                                            fetchStSubjectData();
                                        },
                                        error: function (xhr) {
                                            alert(xhr.message);
                                        }
                                    })
                                },
                                error: function () {
                                    alert("Failed to update Student-Class.");
                                }
                            });
                        }
                    })

                },
                error: function () {
                    alert("Error fetching class ID.");
                }
            });
        },
        error: function () {
            alert("Error fetching student ID.");
        }
    });
});


function updateStudentSubjects(studentId, studentName) {
    let subjects = [];

    $(".subjectSelect").each(function () {
        let subjectName = $(this).val();


        if (!subjectName || subjectName.trim() === "") {
            return;
        }

        console.log("Processing Subject:", subjectName);

        let promise = $.ajax({
            url: `http://localhost:8080/api/v1/subject/getSubId/${encodeURIComponent(subjectName)}`,
            method: 'GET'
        }).then(function (subjectId) {
            console.log(" Retrieved subjectId:", subjectId);

            return $.ajax({
                url: `http://localhost:8080/api/v1/stSubject/update`,
                method: 'PUT',
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify({
                    student_id: studentId,
                    subject_id: subjectId,
                    student_name: studentName,
                    subject_name: subjectName,
                }),

                success: function () {
                    console.log(` Saved: ${studentName} - ${subjectName} (ID: ${subjectId})`);
                },
                error: function () {
                    console.error(` Failed to update: ${studentName} - ${subjectName}`);
                }

            });
        });

        subjects.push(promise);
    });


    if (subjects.length === 0) {
        alert("No subjects selected.");
        return;
    }


    $.when.apply($, subjects).done(function () {
        alert("All selected subjects updated successfully.");
    }).fail(function () {
        alert("Failed to update student-subject data.");
    });
}


function fetchInstructorNames(){

    $.ajax({
        url: "http://localhost:8080/api/v1/instructor/getInstName",
        method: "GET",

        success: function(data){
            let instructorName1 = $("#instName1");
            let instructorName2 = $("#instName2");
            let instructorName3 = $("#instName3");
            let instructorName4 = $("#instName4");
            let instructorName5 = $("#instName5");
            let instructorName = $("#instName")
            let instructorName6 = $("#instName6");
            let instructorName7 = $("#instName7");
            let instructorName8 = $("#instName8");
            let instructorName9 = $("#instName9");
            let instructorName10 = $("#instName10");


            console.log(data);
            instructorName1.empty();
            instructorName2.empty();
            instructorName3.empty();
            instructorName4.empty();
            instructorName5.empty();
            instructorName.empty()

            instructorName6.empty();
            instructorName7.empty();
            instructorName8.empty();
            instructorName9.empty();
            instructorName10.empty();

            instructorName1.append('<option value="">Select Instructor Name</option>');
            instructorName2.append('<option value="">Select Instructor Name</option>');
            instructorName3.append('<option value="">Select Instructor Name</option>');
            instructorName4.append('<option value="">Select Instructor Name</option>');
            instructorName5.append('<option value="">Select Instructor Name</option>');
            instructorName.append('<option value="">Select Instructor Name</option>');

            instructorName6.append('<option value="">Select Instructor Name</option>');
            instructorName7.append('<option value="">Select Instructor Name</option>');
            instructorName8.append('<option value="">Select Instructor Name</option>');
            instructorName9.append('<option value="">Select Instructor Name</option>');
            instructorName10.append('<option value="">Select Instructor Name</option>');


            data.forEach(instructor => {
                instructorName1.append(`<option value="${instructor}">${instructor}</option>`);
            });

            data.forEach(instructor => {
                instructorName2.append(`<option value="${instructor}">${instructor}</option>`);
            });

            data.forEach(instructor => {
                instructorName3.append(`<option value="${instructor}">${instructor}</option>`);
            });

            data.forEach(instructor => {
                instructorName4.append(`<option value="${instructor}">${instructor}</option>`);
            });

            data.forEach(instructor => {
                instructorName5.append(`<option value="${instructor}">${instructor}</option>`);
            });

            data.forEach(instructor => {
                instructorName.append(`<option value="${instructor}">${instructor}</option>`);
            });

            data.forEach(instructor => {
                instructorName6.append(`<option value="${instructor}">${instructor}</option>`);
            });

            data.forEach(instructor => {
                instructorName7.append(`<option value="${instructor}">${instructor}</option>`);
            });

            data.forEach(instructor => {
                instructorName8.append(`<option value="${instructor}">${instructor}</option>`);
            });

            data.forEach(instructor => {
                instructorName9.append(`<option value="${instructor}">${instructor}</option>`);
            });

            data.forEach(instructor => {
                instructorName10.append(`<option value="${instructor}">${instructor}</option>`);
            });


            },

        error: function () {
            alert("Error loading instructor names.");
        }
    });
}



const fetchStSubjectData = () => {

    $.ajax({
        url: "http://localhost:8080/api/v1/stSubject/getAll",
        method: "GET",
        success: (res) => {
            $("#studentSubjectTableBody").empty();

            res.forEach((studentSubject) => {
                $("#studentSubjectTableBody").append(`
                    <tr>
                        <td>${studentSubject.student_name}</td>
                        <td>${studentSubject.subject_name}</td>
                        <td>${studentSubject.instructor_name}</td>
                        
                       
                        <td>
                            <button class="btn btn-warning btn-sm edit-btn" id="editBtn" data-bs-target="#editStSubjectModal">
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
    let subjectName = row.find('td').eq(1).text();
    let instructorName = row.find('td').eq(2).text();

    $.ajax({
        url: `http://localhost:8080/api/v1/class/getClassName/${studentName}`,
        method: "GET",

        success: (res) => {
            $("#className2").val(res.data).change();
            console.log(res.data);
            console.log(res.message)

        },
        error: function (error) {
            console.log(error.message)
        }


    })

    $("#studentName2").val(studentName).change();
    $("#sub6").val(subjectName).change();
    $("#instName6").val(instructorName).change();


    $("#editStSubjectModal").modal("show");
});


$(document).ready(function () {
    $("#searchInput").on("keyup", function () {
        const inputValue = $(this).val().trim().toLowerCase();

        $("#studentSubjectTableBody tr").each(function () {
            const subjectName = $(this).find("td").eq(0).text().trim().toLowerCase();

            if (subjectName.includes(inputValue)) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    });

});
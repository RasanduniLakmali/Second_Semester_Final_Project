$(document).ready(function () {
    loadStudents();
    loadClasses();
    loadSubjects();
    fetchInstructorNames();
})


function loadStudents(){

    $.ajax({
        url: `http://localhost:8080/api/v1/student/getStName`,
        method: 'GET',

        success: function (data) {
            const studentName = $("#studentName");


            studentName.empty();

            studentName.append('<option value="">Select Student</option>');


            data.forEach(student => {
                studentName.append(`<option value="${student}">${student}</option>`); // Assuming data is [101, 102, 103]
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

            subjectName1.empty();
            subjectName2.empty();
            subjectName3.empty();
            subjectName4.empty();
            subjectName5.empty();

            subjectName1.append('<option value="">Select Subject 1</option>');
            subjectName2.append('<option value="">Select Subject 2</option>');
            subjectName3.append('<option value="">Select Subject 3</option>');
            subjectName4.append('<option value="">Select Subject 4</option>');
            subjectName5.append('<option value="">Select Subject 5</option>');

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

    if (!studentName) return;

    $.ajax({
        url: `http://localhost:8080/api/v1/stClass/getClass/${studentName}`,
        method: "GET",
        success: function (className) {
            $("#className").val(className);


            $.ajax({
                url: `http://localhost:8080/api/v1/stSubject/getSubject/${studentName}`,
                method: "GET",
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
                            updateStudentSubjects(studentId, studentName);
                        },
                        error: function () {
                            alert("Failed to update Student-Class.");
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
                    subject_name: subjectName
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


            console.log(data);
            instructorName1.empty();
            instructorName2.empty();
            instructorName3.empty();
            instructorName4.empty();
            instructorName5.empty();
            instructorName.empty()

            instructorName1.append('<option value="">Select Instructor Name</option>');
            instructorName2.append('<option value="">Select Instructor Name</option>');
            instructorName3.append('<option value="">Select Instructor Name</option>');
            instructorName4.append('<option value="">Select Instructor Name</option>');
            instructorName5.append('<option value="">Select Instructor Name</option>');
            instructorName.append('<option value="">Select Instructor Name</option>');


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


        },

        error: function () {
            alert("Error loading instructor names.");
        }
    });
}




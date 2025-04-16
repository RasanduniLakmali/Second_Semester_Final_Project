$(document).ready(function () {
    fetchStudentId();

    setTimeout(fetchStudentDetails, 1000);
});


function fetchStudentId() {
    var email = localStorage.getItem("loggedInEmail");

    console.log(localStorage.getItem("loggedInEmail"));

    if (!email) {
        console.error("No email found in local storage!");
        return;
    }

    $.ajax({
        url: `http://localhost:8080/api/v1/student/getStudentId/${email}`,
        method: "GET",

        success: function (response) {

            localStorage.setItem("loggedInStudentId", response.data);
            console.log(response.data);
        },
        error: function (error) {
            console.error("Error fetching student ID:", error.responseJSON.message);
        }
    });
}


function fetchStudentDetails() {
    var studentId = localStorage.getItem("loggedInStudentId");

    if (!studentId) {
        console.error("No student ID found in local storage!");
        return;
    }

    $.ajax({
        url: `http://localhost:8080/api/v1/student/details/${studentId}`,
        method: "GET",

        success: function (response) {

                console.log("Student Details:", response.data);


                $("#subjectName2").text(response.data.subjectName);
                $("#scheduleDate2").text(response.data.scheduleDay);
                $("#time2").text(response.data.startTime + " " + response.data.endTime);
                $("#hallNumber2").text(response.data.hallNumber);
                $("#className2").text(response.data.className);

        },
        error: function (error) {
            console.error("Error fetching student details:", error.responseJSON.message);
        }
    });
}



$(document).ready(function () {

    $("#viewDetailBtn").click(function (event) {
        event.preventDefault();

        const className = $("#className2").text().trim();
        localStorage.setItem("selectedClassName", className);

        const scheduleDate = $("#scheduleDate2").text().trim();
        localStorage.setItem("scheduleDate", scheduleDate);

        const subjectName = $("#subjectName2").text().trim();
        localStorage.setItem("selectedSubjectName",subjectName);

        console.log(localStorage.getItem("selectedClassName"));

        window.location.href = "classWise_details.html";
    });

});



$(document).ready(function () {
    const className = localStorage.getItem("selectedClassName");
    console.log("Selected Class:", className);

    const scheduleDate = localStorage.getItem("scheduleDate");
    console.log("Schedule Date:", scheduleDate);

    if (!className) {
        alert("No class selected!");
        return;
    }


    $.ajax({
        url: `http://localhost:8080/api/v1/class/getClId/${encodeURIComponent(className)}`,
        type: "GET",
        dataType: "json",
        success: function (classId) {
            console.log("Retrieved Class ID:", classId);


            $.ajax({
                url: `http://localhost:8080/api/v1/class/getClass/${classId}/${scheduleDate}`,
                method: "GET",
                dataType: "json",
                success: function (schedules) {
                    console.log("Schedules Received:", schedules);

                    if (!Array.isArray(schedules) || schedules.length === 0) {
                        alert("No schedules found for this class.");
                        return;
                    }


                    const firstSchedule = schedules[0];
                    $("#subjectHeading").text(firstSchedule.subjectName || "Unknown Subject");
                    $("#subjectName").text(firstSchedule.subjectName || "N/A");
                    $("#instructorName").text(firstSchedule.instructorName || "N/A");
                    $("#hallNumber").text(firstSchedule.hallNumber || "N/A");
                    $("#className").text(firstSchedule.className || "N/A");


                    const scheduleContainer = $(".schedule-list");
                    scheduleContainer.empty();


                    schedules.forEach(schedule => {
                        console.log("Processing Schedule:", schedule);

                        const scheduleHTML = `
                            <div class="schedule-item">
                                <i class="far fa-calendar-alt"></i>
                                <div><strong>Date:</strong> ${schedule.scheduleDay || "N/A"}</div>
                                <div><strong>Time:</strong> ${schedule.startTime || "N/A"} - ${schedule.endTime || "N/A"}</div>
                            </div>
                            <hr>
                        `;
                        scheduleContainer.append(scheduleHTML);
                    });
                },
                error: function (xhr, status, error) {
                    console.error("Error fetching schedules:", error);
                    alert("Failed to load schedules.");
                }
            });
        },
        error: function (xhr, status, error) {
            console.error("Error fetching class ID:", error);
            alert("Failed to load class details.");
        }
    });
});


$(document).ready(function () {
    $("#contactInstBtn").click(function (event) {
        event.preventDefault();

        const instructorName = $("#instructorName").text().trim();

        localStorage.setItem("selectedInstructorName", instructorName);

        if (!instructorName) {
            alert("Instructor name not found!");
            return;
        }

        console.log("Selected Instructor:", instructorName);

        // Fetch instructor email using instructor name
        $.ajax({
            url: `http://localhost:8080/api/v1/instructor/getEmail/${instructorName}`,
            type: "GET",
            success: function (instructorEmail) {
                console.log("Instructor Email:", instructorEmail);

                if (!instructorEmail) {
                    alert("Instructor email not found!");
                    return;
                }

                // Prompt user for message
                const subject = "Inquiry about Your Class"; // Default subject
                const message = prompt("Enter your message for the instructor:");

                if (!message || message.trim() === "") {
                    alert("Message cannot be empty!");
                    return;
                }

                // Send email request
                $.ajax({
                    url: "http://localhost:8080/api/v1/email/send",
                    method: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({
                        to: instructorEmail,
                        subject: subject,
                        message: message
                    }),
                    success: function (response) {
                        alert("Email sent successfully!");
                    },
                    error: function () {
                        alert("Failed to send email!");
                    }
                });
            },
            error: function () {
                alert("Failed to fetch instructor email!");
            }
        });
    });
});


$(document).ready(function () {
    $("#viewMarkBtn").click(function (event) {
        event.preventDefault();

        var studentId = localStorage.getItem("loggedInStudentId");
        var subjectName = localStorage.getItem("selectedSubjectName");

        if (!studentId) {
            console.error("No student ID found in local storage!");
            return;
        }

        // Save studentId & subjectName to localStorage before navigating
        localStorage.setItem("viewMarksStudentId", studentId);
        localStorage.setItem("viewMarksSubject", subjectName);

        // Navigate to student_marks.html (Do this LAST)
        window.location.href = "student_marks.html";
    });
});


$(document).ready(function () {
    var studentId = localStorage.getItem("viewMarksStudentId");
    var subjectName = localStorage.getItem("viewMarksSubject");

    console.log(localStorage.getItem("viewMarksSubject"));


    if (!studentId || !subjectName) {
        console.error("Missing studentId or subjectName!");
        return;
    }

    // Fetch student name
    $.ajax({
        url: `http://localhost:8080/api/v1/student/getName/${studentId}`,
        method: "GET",
        success: function (response) {
            console.log("Student Response:", response);

            // Fixing the student name issue
            if (response && response.student_name) {
                $("#stName").html(`<strong>Student Name:</strong> ${response.student_name}`);
            }
            else {
                $("#stName").html(`<strong>Student Name:</strong> N/A`);
            }

            $("#instName").html(`<strong>Instructor:</strong> ${localStorage.getItem("selectedInstructorName") || "N/A"}`);
            $("#grade").html(`<strong>Grade:</strong> ${localStorage.getItem("selectedClassName") || "N/A"}`);
        }
    });

    // Fetch marks
    $.ajax({
        url: `http://localhost:8080/api/v1/marks/getDetail/${studentId}/${subjectName}`,
        method: "GET",
        success: function (marks) {
            console.log("Received marks:", marks);

            $("h3.mb-0").html(`Student Marks - ${subjectName}`);


            if (marks.length > 0) {
                const assesment = marks[0].term_name || "N/A"; // Get term name from the first record
                $("#term_name").html(`<strong>Year/Term:</strong> ${assesment}`);
            } else {
                $("#term_name").html(`<strong>Year/Term:</strong> N/A`);
            }


            const marksTableBody = $("#marksTableBody");
            marksTableBody.empty();

            if (marks.length > 0) {
                marks.forEach(mark => {
                    const rowHTML = `
                        <tr>
                            <td>${mark.paper_number || "N/A"}</td>
                            <td>100</td> 
                            <td>${mark.mark || "N/A"}</td>
                        </tr>
                    `;
                    marksTableBody.append(rowHTML);
                });
            } else {
                marksTableBody.append(`<tr><td colspan="3" class="text-center">No Marks Available</td></tr>`);
            }
        },
        error: function () {
            alert("Failed to load student marks!");
        }
    });
});

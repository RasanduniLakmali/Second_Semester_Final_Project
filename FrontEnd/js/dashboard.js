$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8080/api/v1/student/count",
        type: "GET",
        success: function (data) {
            $("#studentCount").text(data);
        },
        error: function (xhr, status, error) {
            console.error("Error fetching student count:", error);
            $("#studentCount").text("Error");
        }
    });

    $.ajax({
        url: "http://localhost:8080/api/v1/instructor/count",
        type: "GET",
        success: function (data) {
            $("#instructorCount").text(data);
        },
        error: function (xhr, status, error) {
            console.error("Error fetching instructor count:", error);
            $("#instructorCount").text("Error");
        }
    });

    $.ajax({
        url: "http://localhost:8080/api/v1/class/count",
        type: "GET",
        success: function (data) {
            $("#classesCount").text(data);
        },
        error: function (xhr, status, error) {
            console.error("Error fetching classes count:", error);
            $("#classesCount").text("Error");
        }
    });

    const grade = "Grade 12";

    $.ajax({

        url: `http://localhost:8080/api/v1/schedule/getDetails/${grade}`,
        type: "GET",
        success: function (schedule) {
            let tbody = $("#dashboardTable");
            tbody.empty();

            if (schedule) {
                let date = new Date(schedule.schedule_date).toDateString().slice(4);
                let startTime = schedule.start_time.substring(0, 5);
                let endTime = schedule.end_time.substring(0, 5);

                let row = `
                <tr>
                    <td>${schedule.subject_name}</td>
                    <td>${schedule.instructor_name}</td>
                    <td>${date}</td>
                    <td>${startTime} - ${endTime}</td>
                    <td>
                        <button class="btn btn-primary btn-sm"><i class="fas fa-edit"></i></button>
                        <button class="btn btn-danger btn-sm"><i class="fas fa-trash"></i></button>
                    </td>
                </tr>
            `;
                tbody.append(row);
            } else {
                tbody.append('<tr><td colspan="5" class="text-center">No upcoming class found.</td></tr>');
            }
        },
        error: function (xhr, status, error) {
            console.error("Error loading schedule data:", error);
        }
    });

});
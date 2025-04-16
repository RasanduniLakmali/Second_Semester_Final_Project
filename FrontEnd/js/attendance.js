$(document).ready(function () {
    loadSubjects();


});

function loadSubjects() {
    $.ajax({
        url: `http://localhost:8080/api/v1/subject/getSubName`,
        method: 'GET',
        success: function (data) {
            const subjectSelect = $("#subjectSelect");
            subjectSelect.empty();
            subjectSelect.append('<option value="">Select a course</option>');
            data.forEach(subject => {
                subjectSelect.append(`<option value="${subject}">${subject}</option>`);
            });
        },
        error: function () {
            alert("Error loading subject names.");
        }
    });
}

$(document).ready(function () {
    $("#filterBtn").click(function () {
        const subject = $("#subjectSelect").val();
        const month = $("#monthSelect").val();

        if (!subject || !month) {
            alert("Please select both subject and month.");
            return;
        }

        $.ajax({
            url: `http://localhost:8080/api/v1/attendance/summary?subject=${subject}&month=${month}`,
            method: "GET",
            success: function (data) {
                const tbody = $("table tbody");
                tbody.empty();

                if (data.length === 0) {
                    tbody.append(`<tr><td colspan="7" class="text-center text-danger">No attendance records found for selected subject and month.</td></tr>`);
                } else {
                    data.forEach(record => {
                        tbody.append(`
                            <tr>
                                <td>${record.studentId}</td>
                                <td>${record.studentName}</td>
                                <td>${record.totalClasses}</td>
                                <td>${record.presentCount}</td>
                                <td>${record.absentCount}</td>
                                <td>${record.attendancePercentage}%</td>
                                <td><span class="badge bg-${getStatusColor(record.status)}">${record.status}</span></td>
                            </tr>
                        `);
                    });
                }
            },
            error: function () {
                alert("Error loading attendance summary.");
            }
        });
    });

    function getStatusColor(status) {
        switch (status) {
            case 'Good': return 'success';
            case 'Warning': return 'warning';
            case 'At Risk': return 'danger';
            default: return 'secondary';
        }
    }
});

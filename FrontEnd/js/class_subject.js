$(document).ready(function () {
    loadClasses();
    loadSubjects();
    fetchClassSubjectData();

})

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
            const subjectName = $("#subName");
            const subjectName2 = $("#subName2");

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

$("#saveBtn").on("click", function () {
    const className = $("#className").val();
    const subjectName = $("#subName").val();
    const timeDuration = $("#time").val();
    let classId = null;
    let subjectId = null;

    $.ajax({
        url: `http://localhost:8080/api/v1/class/getClId/${className}`,
        method: 'GET',
        success: function (res) {
            classId = res;

            $.ajax({
                url: `http://localhost:8080/api/v1/subject/getSubId/${subjectName}`,
                method: "GET",


                success: function (data) {
                    subjectId = data;

                    $.ajax({
                        url: `http://localhost:8080/api/v1/sbClass/save`,
                        method: "POST",


                        contentType: "application/json",
                        dataType: "json",
                        data: JSON.stringify({
                            class_id: classId,
                            subject_id: subjectId,
                            subject_name: subjectName,
                            class_name: className,
                            time_duration: timeDuration
                        }),

                        success: function (data) {
                            alert("Details Saved successfully!");
                            clearFields();
                            fetchClassSubjectData();
                        },
                        error: function () {
                            alert("Details not Saved!")
                        }
                    })
                },
                error: function () {
                    alert("Error loading subjects Ids");
                }
            })
        },
        error: function () {
            alert("Error loading class Ids");
        }
    })
})


const fetchClassSubjectData = () => {

    $.ajax({
        url: "http://localhost:8080/api/v1/sbClass/getAll",
        method: "GET",


        success: (res) => {
            $("#classSubjectTableBody").empty();

            res.forEach(classSubject => {
                $("#classSubjectTableBody").append(`
                    <tr>
                        <td>${classSubject.class_name}</td>
                        <td>${classSubject.subject_name}</td>
                        <td>${classSubject.time_duration}</td>
                        
                       
                         <td>
                            <button class="btn btn-warning btn-sm edit-btn" id="editBtn" data-bs-target="#editClassSubjectModal">
                                <i class="fas fa-edit"></i>
                            </button>
                           
                            <button class="btn btn-danger btn-sm delete-btn" id="deleteBtn" >
                                <i class="fas fa-trash"></i>
                            </button>
                        </td>
                    </tr>
                `);
            });

        },
        error: function () {
            alert("Error loading subject data");
        }

    })
}


$(document).on("click", "#editBtn", function () {


    let row = $(this).closest('tr');

    let className = row.find('td').eq(0).text();
    let subjectName = row.find('td').eq(1).text();
    let timeDuration = row.find('td').eq(2).text();


    $("#className2").val(className).change();
    $("#subName2").val(subjectName).change();
    $("#time2").val(timeDuration);


    $("#editClassSubjectModal").modal("show");

});


$("#updateBtn").click(function () {

    const className = $("#className2").val();
    const subjectName = $("#subName2").val();
    const timeDuration = $("#time2").val();
    let classId = null;
    let subjectId = null;

    $.ajax({
        url: `http://localhost:8080/api/v1/class/getClId/${className}`,
        method: 'GET',

        headers: {
            "Authorization": "Bearer " + localStorage.getItem('token')
        },

        success: function (res) {
            classId = res;

            $.ajax({
                url: `http://localhost:8080/api/v1/subject/getSubId/${subjectName}`,
                method: "GET",

                success: function (data) {
                    subjectId = data;

                    $.ajax({
                        url: `http://localhost:8080/api/v1/sbClass/update`,
                        method: "PUT",

                        contentType: "application/json",
                        dataType: "json",
                        data: JSON.stringify({
                            class_id: classId,
                            subject_id: subjectId,
                            subject_name: subjectName,
                            class_name: className,
                            time_duration: timeDuration
                        }),

                        success: function (data) {
                            alert("Details Updated successfully!");
                            clearFields();
                            fetchClassSubjectData();
                        },
                        error: function () {
                            alert("Details not Updated!")
                        }
                    })
                },
                error: function () {
                    alert("Error loading subjects Ids");
                }
            })
        },
        error: function () {
            alert("Error loading class Ids");
        }
    })
})


$(document).on("click", "#deleteBtn", function () {

    let row = $(this).closest('tr');

    let className = row.find('td').eq(0).text();
    let subjectName = row.find('td').eq(1).text();

    if (confirm("Are you sure ?")) {

        $.ajax({
            url: `http://localhost:8080/api/v1/sbClass/delete/${className}/${subjectName}`,
            method: "DELETE",

            headers: {
                "Authorization": "Bearer " + localStorage.getItem('token')
            },

            success: (res) =>{
                alert("Class Subject deleted successfully!");
                clearFields();
                fetchClassSubjectData();

            },
            error: (err) =>{
                alert("Class Subject not deleted!")
            }
        })
    }

})


$(document).ready(function () {
    $("#searchInput").on("keyup", function () {
        const inputValue = $(this).val().trim().toLowerCase();

        $("#classSubjectTableBody tr").each(function () {
            const subjectName = $(this).find("td").eq(0).text().trim().toLowerCase();

            if (subjectName.includes(inputValue)) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    });

});


function clearFields(){
    $("#className").val("");
    $("#subName").val("");
    $("#time").val("");
    $("#className2").val("");
    $("#subName2").val("");
    $("#time2").val("");
}


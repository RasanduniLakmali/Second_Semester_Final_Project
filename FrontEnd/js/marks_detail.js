
$(document).ready(function(){
    loadSubjects();
    loadStudents();
    fetchMarksData();
})

function loadSubjects(){

    $.ajax({
        url: `http://localhost:8080/api/v1/subject/getSubName`,
        method: 'GET',

        success: function (data) {
            const subjectName = $("#subjectName");
            const subjectName2 = $("#subjectName2");

            subjectName.empty();

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
                studentName.append(`<option value="${student}">${student}</option>`); // Assuming data is [101, 102, 103]
            });

            data.forEach(student => {
                studentName2.append(`<option value="${student}">${student}</option>`); // Assuming data is [101, 102, 103]
            });


        },
        error: function () {
            alert("Error loading student names.");
        }
    })
}


/*------------------------------Save Marks---------------------------------*/

$("#saveBtn").click(function () {
    console.log("save button clicked");

    const studentName = $("#studentName").val();
    const subjectName = $("#subjectName").val();
    const paperNumber = $("#paperNumber").val();
    const marks = $("#marks").val();
    const term = $("#term").val();
    let studentId = null;
    let subjectId = null;

    $.ajax({
        url: `http://localhost:8080/api/v1/student/getStId/${studentName}`,
        method: "GET",

        success: function (data) {
           studentId = data;

           $.ajax({
               url: `http://localhost:8080/api/v1/subject/getSubId/${subjectName}`,
               method: "GET",

               success: function (data) {
                   subjectId = data;

                   $.ajax({
                       url: `http://localhost:8080/api/v1/marks/save`,
                       method: "POST",

                       contentType: "application/json",
                       dataType: "json",
                       data: JSON.stringify({

                           student_id:  studentId,
                           subject_id: subjectId,
                           student_name: studentName,
                           subject_name: subjectName,
                           paper_number: paperNumber,
                           mark: marks,
                           term_name: term
                       }),
                       success: function (data) {
                           alert("Marks saved successfully! ");
                           fetchMarksData();
                       },
                       error: function () {
                           alert("Marks not saved! ");
                       }
                   })
               },
               error: function () {
                   alert("Error loading in subject id!");
               }
           })
        },
        error: function () {
            alert("Error loading in student id!")
        }
    })
})


function fetchMarksData() {

    $.ajax({
        url: "http://localhost:8080/api/v1/marks/getAll",
        method: "GET",
        success: (res) => {
            $("#marksTableBody").empty();

            res.forEach(paperMark => {
                $("#marksTableBody").append(`
                    <tr>
                        <td>${paperMark.student_name}</td>
                        <td>${paperMark.subject_name}</td>
                        <td>${paperMark.paper_number}</td>
                        <td>${paperMark.term_name}</td>
                        <td>${paperMark.mark}</td>
                        <td>
                            <button class="btn btn-warning btn-sm edit-btn" id="editBtn" data-bs-target="#editClassModal">
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
            alert("Error loading marks data");
        }
    })
}

$(document).on("click", "#editBtn", function () {


    let row = $(this).closest('tr');

    // Get data from the row cells
    let studentName = row.find('td').eq(0).text();
    let subjectName = row.find('td').eq(1).text();
    let paperNumber = row.find('td').eq(2).text();
    let termName = row.find('td').eq(3).text();
    let marks = row.find('td').eq(4).text();


    $("#studentName2").val(studentName).change();
    $("#subjectName2").val(subjectName).change();
    $("#paperNumber2").val(paperNumber);
    $("#term2").val(termName);
    $("#marks2").val(marks).change();


    $("#editMarksModal").modal("show");
});


$("#updateBtn").click(function () {

    console.log("update button clicked");

    const studentName = $("#studentName2").val();
    const subjectName = $("#subjectName2").val();
    const paperNumber = $("#paperNumber2").val();
    const marks = $("#marks2").val();
    const term = $("#term2").val();
    let studentId2 = null;
    let subjectId2 = null;

    $.ajax({
        url: `http://localhost:8080/api/v1/student/getStId/${studentName}`,
        method: "GET",

        success: function (data) {
            studentId2 = data;

            $.ajax({
                url: `http://localhost:8080/api/v1/subject/getSubId/${subjectName}`,
                method: "GET",

                success: function (data) {
                    subjectId2 = data;

                    $.ajax({
                        url: `http://localhost:8080/api/v1/marks/update`,
                        method: "PUT",

                        contentType: "application/json",
                        dataType: "json",
                        data: JSON.stringify({

                            student_id:  studentId2,
                            subject_id: subjectId2,
                            student_name: studentName,
                            subject_name: subjectName,
                            paper_number: paperNumber,
                            mark: marks,
                            term_name: term
                        }),
                        success: function (data) {
                            alert("Marks updated successfully! ");
                            fetchMarksData();
                        },
                        error: function () {
                            alert("Marks not updated! ");
                        }
                    })
                },
                error: function () {
                    alert("Error loading in subject id!");
                }
            })
        },
        error: function () {
            alert("Error loading in student id!")
        }
    })
});


$(document).on("click", "#deleteBtn", function () {

    let row = $(this).closest('tr');
    let studentName = row.find('td').eq(0).text();
    let subjectName = row.find('td').eq(1).text();

    if (confirm("Are you sure ?")) {

        $.ajax({
            url: `http://localhost:8080/api/v1/marks/delete/${studentName}/${subjectName}`,
            method: "DELETE",

            success: (res) =>{
                alert("Marks deleted successfully!")
                fetchMarksData();
            },
            error: (err) =>{
                alert("Marks not deleted!")
            }
        })
    }

})


$(document).ready(function () {
    $("#searchInput").on("keyup", function () {
        const inputValue = $(this).val().trim().toLowerCase();

        $("#marksTableBody tr").each(function () {
            const subjectName = $(this).find("td").eq(0).text().trim().toLowerCase();

            if (subjectName.includes(inputValue)) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    });

});


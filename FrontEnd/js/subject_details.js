$(document).ready(function () {
    loadAdmins();
    fetchSubjectData();
})

/*---------------------------Load admins---------------------------*/

function loadAdmins(){

    $.ajax({
        url: "http://localhost:8080/api/v1/admin/getAdId",
        method: "GET",
        success: function(data){
            let cmbAdmin = $("#adminId");
            let cmbAdmin2 = $("#adminId2");
            console.log(data);
            cmbAdmin.empty();
            cmbAdmin2.empty();

            cmbAdmin.append('<option value="">Select Admin Id</option>');

            data.forEach(admin => {
                cmbAdmin.append(`<option value="${admin}">${admin}</option>`); // Assuming data is [101, 102, 103]
            });

            data.forEach(admin => {
                cmbAdmin2.append(`<option value="${admin}">${admin}</option>`);
            })
        },

        error: function () {
            alert("Error loading admin ids.");
        }
    });
}


/*------------------------------Save subject---------------------------------*/

$("#saveBtn").click(function () {
    console.log("save button clicked");

    const subjectName = $("#subjectName").val();
    const subjectCode = $("#code").val();
    const adminId = $("#adminId").val();
    const gradeRange = $("#gradeRange").val();

    $.ajax({
          url: "http://localhost:8080/api/v1/subject/save",
          method: "POST",

          contentType: "application/json",
          dataType: "json",
          data: JSON.stringify({

              subject_name: subjectName,
              subject_code: subjectCode,
              admin_id: adminId,
              grade_range: gradeRange
          }),

          success: function (data) {
              console.log(data);
              alert("Subject saved successfully.");
              fetchSubjectData();
              clearFields()
          },
          error: function () {
              alert("Subject not saved.");
          }
    })
})


const fetchSubjectData = () => {
    $.ajax({
        url: "http://localhost:8080/api/v1/subject/getAll",
        method: "GET",
        success: (res) => {
            $("#subjectTableBody").empty();

            res.forEach(subject => {
                $("#subjectTableBody").append(`
                    <tr>
                        <td>${subject.subject_name}</td>
                        <td>${subject.subject_code}</td>
                        <td>${subject.grade_range}</td>
                        <td>${subject.admin_id}</td>
                       
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

                // Get data from the row cells
                let subjectName = row.find('td').eq(0).text();
                let code = row.find('td').eq(1).text();
                let gradeRange = row.find('td').eq(2).text();
                let adminId = row.find('td').eq(3).text();

                $("#subjectName3").text(subjectName);
                $("#subjectCode3").text(code);
                $('#gradeRange3').text(gradeRange);
                $("#adminId3").text(adminId);

                $("#subjectDetailsModal").modal("show");
            });
        },
        error: function () {
            alert("Error loading subject data");
        }

    })
}


/*------------------------Show update form-------------------------*/

$(document).on("click", "#editBtn", function () {

    loadAdmins();

    // Get the parent row of the clicked edit button
    let row = $(this).closest('tr');

    // Get data from the row cells
    let subjectName = row.find('td').eq(0).text();
    let code = row.find('td').eq(1).text();
    let gradeRange = row.find('td').eq(2).text();
    let adminId = row.find('td').eq(3).text();



    // Set the values in the modal
    $("#subjectName2").val(subjectName);
    $("#code2").val(code);
    $("#adminId2").val(adminId);
    $('#gradeRange2').val(gradeRange);


    // Show the modal
    $("#editSubjectModal").modal("show");
});



/*--------------------------------------Update subject--------------------------*/

$("#updateBtn").click(function () {

    const subjectName = $("#subjectName2").val();
    const subjectCode = $("#code2").val();
    const adminId = $("#adminId2").val();
    const gradeRange = $("#gradeRange2").val();

    $.ajax({
        url: "http://localhost:8080/api/v1/subject/update",
        method: "PUT",

        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({

            subject_name: subjectName,
            subject_code: subjectCode,
            admin_id: adminId,
            grade_range: gradeRange
        }),

        success: function (data) {
            console.log(data);
            alert("Subject updated successfully.");
            fetchSubjectData();
            clearFields();
        },
        error: function () {
            alert("Subject not updated.");
        }
    })
})


$(document).on("click", "#deleteBtn", function () {

    let row = $(this).closest('tr');
    let subjectName = row.find('td').eq(0).text();

    if (confirm("Are you sure ?")) {

        $.ajax({
            url: `http://localhost:8080/api/v1/subject/delete/${subjectName}`,
            method: "DELETE",

            success: (res) =>{
                alert("Subject deleted successfully!")
                fetchStudentData();
                clearFields();
            },
            error: (err) =>{
                alert("Subject not deleted!")
            }
        })
    }

})

function clearFields() {
    $("#subjectName").val("");
    $("#subjectName2").val("");
    $("#subjectName3").val("");
    $("#code").val("");
    $("#code2").val("");
    $("#subjectCode3").val("");
    $("#adminId").val("");
    $('#adminId2').val("");
    $("#adminId3").val("");
    $("#gradeRange").val("");
    $("#gradeRange2").val("");
    $("#gradeRange3").val("");
}
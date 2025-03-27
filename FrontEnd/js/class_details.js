$(document).ready(function(){
    loadAdmins();
    fetchClassData();
})

function loadAdmins(){

    $.ajax({
        url: `http://localhost:8080/api/v1/admin/getAdName`,
        method: 'GET',

        success: function(data){
            const adminName = $("#adminName");
            const adminName2 = $("#adminName2");

            adminName.empty();
            adminName2.empty();

            adminName.append('<option value="">Select Admin</option>');
            adminName2.append('<option value="">Select Admin</option>');

            data.forEach(admin => {
                adminName.append(`<option value="${admin}">${admin}</option>`); // Assuming data is [101, 102, 103]
            });

            data.forEach(admin => {
                adminName2.append(`<option value="${admin}">${admin}</option>`); // Assuming data is [101, 102, 103]
            });
        },
        error: function () {
            alert("Error loading admin names.");
        }
    })
}


/*------------------------------Save class---------------------------------*/

$("#saveBtn").click(function () {
    console.log("save button clicked");

    const className = $("#className").val();
    const capacity = $("#capacity").val();
    const hallNumber = $("#hallNumber").val();
    const adminName = $("#adminName").val();
    const adminId = $("#adminId").val();

    $.ajax({
        url: "http://localhost:8080/api/v1/class/save",
        method: "POST",

        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({

            class_name:  className,
            capacity: capacity,
            hall_number: hallNumber,
            admin_id: adminId,
            admin_name: adminName
        }),

        success: function (data) {
            console.log(data);
            alert("Class saved successfully.");
            fetchClassData();
            clearFields();
        },
        error: function () {
            alert("Class not saved.");
        }
    })
})


$('#adminName').change(function () {
    const adminName = $(this).val();

    if (!adminName) return;

    $.ajax({
        url: `http://localhost:8080/api/v1/admin/getAdmin/${adminName}`,
        method: "GET",

        success: function (data) {
            $("#adminId").val(data);
        },

        error: function (xhr) {
            if (xhr.status === 404) {
                alert("Admin not found!");
                $("#adminId").val('');
            } else {
                alert("Error loading admin ID.");
            }
        }
    });
});


$('#adminName2').change(function () {
    const adminName2 = $(this).val();

    if (!adminName2) return;

    $.ajax({
        url: `http://localhost:8080/api/v1/admin/getAdmin/${adminName2}`,
        method: "GET",

        success: function (data) {
            $("#adminId2").val(data);
        },

        error: function (xhr) {
            if (xhr.status === 404) {
                alert("Admin not found!");
                $("#adminId2").val('');
            } else {
                alert("Error loading admin ID.");
            }
        }
    });
});



function fetchClassData() {

    $.ajax({
        url: "http://localhost:8080/api/v1/class/getAll",
        method: "GET",
        success: (res) => {
            $("#classTableBody").empty();

            res.forEach(classEntity => {
                $("#classTableBody").append(`
                    <tr>
                        <td>${classEntity.class_name}</td>
                        <td>${classEntity.capacity}</td>
                        <td>${classEntity.hall_number}</td>
                       <td>${classEntity.admin_name}</td>
                        <td>
                            <button class="btn btn-warning btn-sm edit-btn" id="editBtn" data-bs-target="#editClassModal">
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

                let className = row.find('td').eq(0).text();
                let capacity = row.find('td').eq(1).text();
                let hallNumber = row.find('td').eq(2).text();
                let adminName = row.find('td').eq(3).text();

                $("#className3").text(className);
                $("#capacity3").text(capacity);
                $("#hallNumber3").text(hallNumber);
                $("#adminName3").text(adminName);


                $.ajax({
                    url: `http://localhost:8080/api/v1/admin/getAdmin/${adminName}`,
                    method: "GET",

                    success: function (data) {
                        console.log(data);
                        $("#adminId3").text(data);

                        $("#classDetailsModal").modal("show");
                    },
                })

            });

        },
        error: function () {
            alert("Error loading class data");
        }
    })
}


$(document).on("click", "#editBtn", function () {


    let row = $(this).closest('tr');

    // Get data from the row cells
    let className = row.find('td').eq(0).text();
    let capacity = row.find('td').eq(1).text();
    let hallNumber = row.find('td').eq(2).text();
    let adminName = row.find('td').eq(3).text();


    // Set the values in the modal
    $("#className2").val(className);
    $("#capacity2").val(capacity);
    $("#hallNumber2").val(hallNumber);
    $("#adminName2").val(adminName).change();


    // Show the modal
    $("#editClassModal").modal("show");
});



/*---------------------------------Update class------------------------------*/

$("#updateBtn").click(function () {

    const className = $("#className2").val();
    const capacity = $("#capacity2").val();
    const hallNumber = $("#hallNumber2").val();
    const adminName = $("#adminName2").val();
    const adminId = $("#adminId2").val();

    $.ajax({
        url: "http://localhost:8080/api/v1/class/update",
        method: "PUT",

        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({

            class_name:  className,
            capacity: capacity,
            hall_number: hallNumber,
            admin_id: adminId,
            admin_name: adminName
        }),

        success: function (data) {
            console.log(data);
            alert("Class updated successfully.");
            fetchClassData();
            clearFields();
        },
        error: function () {
            alert("Class not updated.");
        }
    })
})


$(document).on("click", "#deleteBtn", function () {

    let row = $(this).closest('tr');
    let className = row.find('td').eq(0).text();

    if (confirm("Are you sure ?")) {

        $.ajax({
            url: `http://localhost:8080/api/v1/class/delete/${className}`,
            method: "DELETE",

            success: (res) =>{
                alert("Class deleted successfully!")
                fetchClassData();
                clearFields();
            },
            error: (err) =>{
                alert("Class not deleted!")
            }
        })
    }

})


function clearFields(){
    $("#className").val("");
    $("#capacity").val("");
    $("#hallNumber").val("");
    $("#adminName").val("");
    $("#adminId").val("");
    $("#className2").val("");
    $("#capacity2").val("");
    $("#hallNumber2").val("");
    $("#adminName2").val("");
    $("#adminId2").val("");
    $("#className3").val("");
    $("#capacity3").val("");
    $("#hallNumber3").val("");
    $("#adminName3").val("");
    $("#adminId3").val("");
}

$(document).ready(function(){

    var email = localStorage.getItem("loggedInEmail");

    $.ajax({
        url: `http://localhost:8080/api/v1/student/getStudentProfile/${email}`,
        type: "GET",

        success: function(data){
            console.log(data);

           $("#fullName").val(data.student_name);
           $("#mobile").val(data.phone);
           $("#address").val(data.address);
           $("#email").val(data.email);
           $("#school").val(data.school);
           $("#age").val(data.age);
            $(".profile-image").attr("src", `http://localhost:8080/uploads/${data.image}`);


            const studentName = data.student_name;

                $.ajax({
                  url: `http://localhost:8080/api/v1/stClass/getClass/${studentName}`,
                    method: "GET",

                    success: function (data) {
                      $("#grade").val(data);


                    },
                    error: function (xhr) {
                      alert("Error loading class name!");
                    }
           })
        },
        error: function (xhr) {
            alert("Error loading student details.");
        }
    })
})


$("#updateProfile").click(function () {
    const formData = new FormData();

    formData.append("student_name", $('#fullName').val());
    formData.append("age", $('#age').val());
    formData.append("email", $('#email').val());
    formData.append("phone", $('#mobile').val());
    formData.append("address", $('#address').val());
    formData.append("school", $('#school').val());

    $.ajax({
        url: "http://localhost:8080/api/v1/student/updateProfile",
        method: "PUT",

        processData: false,
        contentType: false,
        data: formData,

        success: function (data) {
            console.log("Success:", data);
            alert("Details updated successfully.");
            fetchStudentData();
            clearFields();
        },
        error: function (xhr) {
            console.log("Error Response:", xhr);

            let errorMessage = "Error updating student.";
            if (xhr.responseJSON && xhr.responseJSON.message) {
                errorMessage = xhr.responseJSON.message;
            }

            alert(errorMessage);
        }
    });
})
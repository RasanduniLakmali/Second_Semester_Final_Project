$(document).ready(function () {
    loadSubjects();

    fetchDetails();
})

function loadSubjects(){

    $.ajax({
        url: `http://localhost:8080/api/v1/subject/getSubName`,
        method: 'GET',

        success: function (data) {
            const subjectName = $("#subjectSelect");


            subjectName.empty();


            subjectName.append('<option value="">Select Subject</option>');


            data.forEach(subject => {
                subjectName.append(`<option value="${subject}">${subject}</option>`);
            });



        },
        error: function () {
            alert("Error loading subject names.");
        }
    })
}


// function loadStudents(){
//
//     $.ajax({
//         url: `http://localhost:8080/api/v1/student/getStName`,
//         method: 'GET',
//
//         success: function (data) {
//             const studentName = $("#nameSelect");
//
//
//             studentName.empty();
//
//             studentName.append('<option value="">Select Student</option>');
//
//
//             data.forEach(student => {
//                 studentName.append(`<option value="${student}">${student}</option>`); // Assuming data is [101, 102, 103]
//             });
//
//
//
//         },
//         error: function () {
//             alert("Error loading student names.");
//         }
//     })
// }
//


$("#gradeSelect").change(function () {

    const className = $(this).val();


    $.ajax({
        url: `http://localhost:8080/api/v1/class/getFee/${className}`,
        method: 'GET',

        success: function (data) {
            console.log(data);

            $("#feeDisplay").text(data);

        },
        error: function () {
            alert("Error loading class names.");

        }
    })
})


function fetchDetails(){

    var studentId = localStorage.getItem("loggedInStudentId");

    var email = localStorage.getItem("loggedInEmail");

    $("#email").val(email);

    let studentName = null;
    let className = null;

    $.ajax({
        url: `http://localhost:8080/api/v1/student/getName/${studentId}`,
        method: 'GET',

        success: function (response) {
            console.log(response);

            studentName = response.data;

            $("#studentName").val(studentName);

            $.ajax({

                url: `http://localhost:8080/api/v1/stClass/getClass/${studentName}`,
                method: "GET",

                success: function (data) {
                    console.log(data);

                    className = data;

                    $("#gradeSelect").val(data);

                    $.ajax({

                        url: `http://localhost:8080/api/v1/class/getFee/${className}`,
                        method: 'GET',

                        success: function (data) {

                            console.log(data);

                            $("#feeDisplay").text(data);

                        }
                    })
                }
            })
        }
    })

}
$("#payBtn").click(function () {
    var studentId = localStorage.getItem("loggedInStudentId");

    const studentName = $("#studentName").val();
    const className = $("#gradeSelect").val();
    const amount = $("#feeDisplay").text();
    const month = $("#monthSelect").val();
    const cardNumber = $("#cardNumber").val();
    const cardName = $("#cardName").val();
    const createdAt = new Date().toISOString();
    const email = $("#email").val();

    let classId = null;

    $.ajax({
        url: `http://localhost:8080/api/v1/class/getClId/${className}`,
        method: 'GET',
        success: function (data) {
            classId = data;

            $.ajax({
                url: `http://localhost:8080/api/v1/payment/save`,
                method: 'POST',
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify({
                    student_id: studentId,
                    class_id: classId,
                    total_amount: amount,
                    month: month,
                    card_number: cardNumber,
                    card_name: cardName,
                    createdAt: createdAt,
                    student_name: studentName,
                    class_name: className
                }),
                success: function (response) {
                    console.log("Payment saved!", response);

                    // ✅ Update payment status
                    $.ajax({
                        url: `http://localhost:8080/api/v1/payment/updateStatus/${response.data.payment_id}`,
                        method: 'PUT',
                        contentType: "application/json",
                        data: JSON.stringify({ status: "Success" }),
                        success: function () {
                            console.log("Payment status updated to Success.");

                            // ✅ Show SweetAlert for payment success
                            Swal.fire({
                                icon: 'success',
                                title: 'Payment Successful!',
                                text: 'Your payment has been processed successfully!',
                                confirmButtonText: 'OK'
                            });

                            // ✅ Send email confirmation
                            const emailData = {
                                to: email,
                                subject: "Payment Confirmation",
                                message: `Dear ${studentName},\n\nYour payment of Rs.${amount} for ${className} class (Month: ${month}) was successful.\n\nThank you!`
                            };

                            $.ajax({
                                url: "http://localhost:8080/api/v1/email/send",
                                method: "POST",
                                contentType: "application/json",
                                data: JSON.stringify(emailData),
                                success: function () {
                                    console.log("Confirmation email sent!");

                                    // ✅ SweetAlert for email success
                                    Swal.fire({
                                        icon: 'info',
                                        title: 'Email Sent!',
                                        text: 'A confirmation email has been sent to your inbox.',
                                        confirmButtonText: 'OK'
                                    });
                                },
                                error: function () {
                                    console.error("Failed to send confirmation email.");
                                    Swal.fire({
                                        icon: 'error',
                                        title: 'Email Failed!',
                                        text: 'There was a problem sending the confirmation email.',
                                        confirmButtonText: 'OK'
                                    });
                                }
                            });
                        }
                    });
                },
                error: function () {
                    Swal.fire({
                        icon: 'error',
                        title: 'Payment Failed!',
                        text: 'Something went wrong while processing your payment.',
                        confirmButtonText: 'Try Again'
                    });
                }
            });
        },
        error: function () {
            Swal.fire({
                icon: 'error',
                title: 'Class Error!',
                text: 'Unable to load class details.',
                confirmButtonText: 'OK'
            });
        }
    });
});



function clearFields(){
   $("#studentName").val("");
   $("#gradeSelect").val("");
   $("#feeDisplay").text("");
   $("#monthSelect").val("");
   $("#cardNumber").val("");
   $("#cardName").val("");
   $("#email").val("");
}
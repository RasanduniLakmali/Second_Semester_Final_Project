$(document).ready(function () {
    getSubjects();
})

function getSubjects() {
    $.ajax({
        url: "http://localhost:8080/api/v1/sbClass/all",
        method: 'GET',

        success: function (data) {
            $("#subjectCardsContainer").empty();

            let row;
            data.forEach((item, index) => {
                if (index % 4 === 0) {
                    row = $('<div class="row mb-3"></div>');
                    $("#subjectCardsContainer").append(row);
                }

                const cardHtml = `
                <div class="col-lg-3 col-md-4 col-sm-6 wow fadeInUp" data-wow-delay="0.1s">
                    <div class="course-item bg-light">
                        <div class="position-relative overflow-hidden">
                            <img class="img-fluid" src="img/course-1.jpg" alt="">
                            <div class="w-100 d-flex justify-content-center position-absolute bottom-0 start-0 mb-4">
                                <a href="#" class="flex-shrink-0 btn btn-sm btn-primary px-3 border-end">Read More</a>
                                <a href="" class="flex-shrink-0 btn btn-sm btn-primary px-3" data-bs-target="#joinClassModal" id="joinNowBtn">Join Now</a>
                            </div>
                        </div>
                        <div class="text-center p-4 pb-0">
                            <h4 class="mb-0">${item.subjectName}</h4>
                            <h5 class="mb-4">${item.gradeRange}</h5>
                        </div>
                        <div class="d-flex border-top">
                            <small class="flex-fill text-center border-end py-2">
                                <i class="fa fa-user-tie text-primary me-2"></i>${item.instructorName}
                            </small>
                            <small class="flex-fill text-center border-end py-2">
                                <i class="fa fa-clock text-primary me-2"></i>${item.timeDuration}
                            </small>
                        </div>
                    </div>
                </div>`;

                row.append(cardHtml);
            });
        },
        error: function (error) {
            console.log("Error fetching subjects", error);
        }
    });
}


$(document).ready(function() {

    $(document).on('click', '.btn-primary:contains("Join Now")', function(e) {
        e.preventDefault();

        // Get the subject and grade information from the parent card
        const cardElement = $(this).closest('.course-item');
        const subjectName = cardElement.find('h4').text();
        const gradeRange = cardElement.find('h5').text();

        // Set the values in the hidden fields
        $('#selectedSubject').val(subjectName);
        $('#selectedGrade').val(gradeRange);

        // const loggedEmail = localStorage.getItem("loggedInEmail");
        // let studentId = null;
        //
        // $.ajax({
        //     url: `http://localhost:8080/api/v1/student/getStudentId/${loggedEmail}`,
        //     method: 'GET',
        //
        //     success: function (response) {
        //         studentId = response.data;
        //
        //         if (studentId != null){
        //
        //         }
        //     }
        // })

        // Show the modal
        $('#joinClassModal').modal('show');
    });

    // Handle form submission
    $('#joinClassForm').on('submit', function(e) {
        e.preventDefault();


        const subjectName = $("#selectedSubject").val();
        const className = $("#selectedGrade").val();
        const studentName = $("#studentName").val();
        const email = $("#studentEmail").val();
        const phone = $("#studentPhone").val();
        const message = $("#message").val();



        $.ajax({
            url: "http://localhost:8080/api/v1/student/saveNew",
            method: 'POST',
            contentType: 'application/json',
            dataType: "json",
            data: JSON.stringify({
                name: studentName,
                class_name: className,
                subject_name: subjectName,
                email: email,
                phone: phone,
                message: message,
            }),

            success: function(response) {

                alert("Details Saved successfully!");

                const subject = "Join For The Class";

                $.ajax({

                    url: "http://localhost:8080/api/v1/email/send",
                    method: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({
                        to: email,
                        subject: subject,
                        message: message
                    }),
                    success: function (response) {
                        alert("Your request has been submitted successfully. We'll contact you soon!");
                        $('#joinClassModal').modal('hide');
                        $('#joinClassForm')[0].reset();
                    },
                    error: function () {
                        console.error("Error sending join request");
                        alert("There was an error submitting your request. Please try again later.");
                    }

                })

            },
            error: function(error) {
                alert("Details Not Saved!");
            }
        });
    });
});

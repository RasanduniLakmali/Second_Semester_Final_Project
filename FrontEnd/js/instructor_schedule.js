$(document).ready(function(){
    fetchInstructorNames();
    loadSubjects();
})

function fetchInstructorNames(){

    $.ajax({
        url: "http://localhost:8080/api/v1/instructor/getInstName",
        method: "GET",

        success: function(data){

            let instructorName = $("#instructorSelect")


            console.log(data);

            instructorName.empty()

            instructorName.append('<option value="">Select Instructor Name</option>');



            data.forEach(instructor => {
                instructorName.append(`<option value="${instructor}">${instructor}</option>`);
            });


        },

        error: function () {
            alert("Error loading instructor names.");
        }
    });
}


function loadSubjects(){


    $.ajax({
        url: `http://localhost:8080/api/v1/subject/getSubName`,
        method: 'GET',

        success: function (data) {
            const subjectName = $("#subjectSelect");


            subjectName.empty();


            subjectName.append('<option value="">Select Subject</option>');


            data.forEach(subject => {
                subjectName.append(`<option value="${subject}">${subject}</option>`); // Assuming data is [101, 102, 103]
            });


        },
        error: function () {
            alert("Error loading subject names.");
        }
    })
}

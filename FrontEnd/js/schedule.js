$(document).ready(function () {
    fetchClassNames();
    fetchSubjectNames();
    fetchInstructorNames();
    fetchScheduleData();
})

function fetchClassNames(){

    $.ajax({
        url: "http://localhost:8080/api/v1/schedule/getNames",
        method: "GET",

        success: function(data){
            let className = $("#className");
            let className2 = $("#className2");


            console.log(data);
            className.empty();
            className2.empty();

            className.append('<option value="">Select Class Name</option>');


            data.forEach(classEntity => {
                className.append(`<option value="${classEntity}">${classEntity}</option>`);
            });

            data.forEach(classEntity => {
                className2.append(`<option value="${classEntity}">${classEntity}</option>`);
            });


        },

        error: function () {
            alert("Error loading class names.");
        }
    });
}

function fetchSubjectNames(){

    $.ajax({
        url: "http://localhost:8080/api/v1/subject/getSubName",
        method: "GET",

        success: function(data){
            let subjectName = $("#subName");
            let subjectName2 = $("#subName2");

            console.log(data);
            subjectName.empty();
            subjectName2.empty();

            subjectName.append('<option value="">Select Subject Name</option>');

            data.forEach(subject => {
                subjectName.append(`<option value="${subject}">${subject}</option>`);
            });

            data.forEach(subject => {
                subjectName2.append(`<option value="${subject}">${subject}</option>`);
            });


        },

        error: function () {
            alert("Error loading subject names.");
        }
    });

}


function fetchInstructorNames(){

    $.ajax({
        url: "http://localhost:8080/api/v1/instructor/getInstName",
        method: "GET",

        success: function(data){
            let instructorName = $("#instName");
            let instructorName2 = $("#instName2");


            console.log(data);
            instructorName.empty();
            instructorName2.empty();

            instructorName.append('<option value="">Select Instructor Name</option>');


            data.forEach(instructor => {
                instructorName.append(`<option value="${instructor}">${instructor}</option>`);
            });

            data.forEach(instructor => {
                instructorName2.append(`<option value="${instructor}">${instructor}</option>`);
            });


        },

        error: function () {
            alert("Error loading instructor names.");
        }
    });
}


$("#saveBtn").click(function () {

      const className = $("#className").val();
      const subjectName = $("#subName").val();
      const instructorName = $("#instName").val();
      const scheduleDate = $("#scheduleDate").val();
      const formattedDate = scheduleDate.split("/").reverse().join("-");
      const startTime = $("#startTime").val() + ":00";
      const endTime = $("#endTime").val() + ":00";
      let classId = null;
      let subjectId = null;
      let instructorId =  null;
      let scheduleId;

      $.ajax({
          url: `http://localhost:8080/api/v1/class/getClId/${className}`,
          method: "GET",

          success: function (data) {
              classId = data;
              console.log("Retrieved classId:", classId);

              $.ajax({
                  url: `http://localhost:8080/api/v1/subject/getSubId/${subjectName}`,
                  method: "GET",

                  success: function (data) {
                      subjectId = data;
                      console.log("Retrieved subjectId:", subjectId);

                      $.ajax({
                          url: `http://localhost:8080/api/v1/instructor/getId/${instructorName}`,
                          method: "GET",

                          success: function (data) {
                              instructorId = data;
                              console.log("Retrieved instId:", instructorId)
                              console.log(scheduleDate,startTime)
                              $.ajax({
                                  url: `http://localhost:8080/api/v1/schedule/save`,
                                  method: "POST",

                                  contentType: "application/json",
                                  dataType: "json",

                                  data: JSON.stringify({
                                      schedule_date: formattedDate,
                                      start_time: startTime,
                                      end_time: endTime,
                                      class_id: classId,
                                      class_name: className,
                                      instructor_name: instructorName,
                                      subject_name: subjectName
                                  }),

                                  success: function (data) {
                                         alert("Schedule saved successfully.");
                                         fetchScheduleData();
                                         clearFields();

                                         $.ajax({
                                             url: `http://localhost:8080/api/v1/schedule/getID/${formattedDate}/${className}/${instructorName}`,
                                             method: "GET",

                                             success: function (data) {
                                                 scheduleId = data;

                                                 $.ajax({
                                                     url: `http://localhost:8080/api/v1/sbSchedule/save`,
                                                     method: "POST",
                                                     contentType: "application/json",
                                                     dataType: "json",
                                                     data: JSON.stringify({
                                                         subject_id: subjectId,
                                                         schedule_id: scheduleId,
                                                         subject_name: subjectName,
                                                     }),

                                                     success: function (data) {
                                                         alert("Subject schedule saved successfully.");

                                                         $.ajax({
                                                             url: `http://localhost:8080/api/v1/instSchedule/save`,
                                                             method: "POST",

                                                             contentType: "application/json",
                                                             dataType: "json",
                                                             data: JSON.stringify({
                                                                 instructor_id: instructorId,
                                                                 schedule_id: scheduleId,
                                                                 instructor_name: instructorName,
                                                             }),

                                                             success: function (data) {
                                                                 alert("Instructor schedule saved successfully.");
                                                             },
                                                             error: function () {
                                                                 alert("Instructor schedule not saved!");
                                                             }
                                                         })
                                                     },
                                                     error: function () {
                                                         alert("Subject schedule not saved!!");
                                                     }
                                                 })
                                             },
                                             error: function () {
                                                 alert("Error loading in schedule id!");
                                             }
                                         })
                                  },
                                  error: function () {
                                      alert("Schedule not saved!");
                                  }
                              })
                          },
                          error: function () {
                              alert("Error loading in instructor id!");
                          }
                      })


                  },
                  error: function () {
                      alert("Error loading in subject id!");
                  }
              })
          },
          error: function () {
              alert("Error loading in class id!");
          }
      })
})



const fetchScheduleData = () => {

    $.ajax({
        url: "http://localhost:8080/api/v1/schedule/getAll",
        method: "GET",
        success: (res) => {
            $("#scheduleTableBody").empty();

            res.forEach((schedule) => {
                $("#scheduleTableBody").append(`
                    <tr>
                        <td>${schedule.class_name}</td>
                        <td>${schedule.subject_name}</td>
                        <td>${schedule.instructor_name}</td>
                        <td>${schedule.schedule_date}</td>
                        <td>${schedule.start_time}</td>
                        <td>${schedule.end_time}</td>
                       
                        <td>
                            <button class="btn btn-warning btn-sm edit-btn" id="editBtn" data-bs-target="#editScheduleModal">
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
        }
    })
}

$(document).on("click", "#editBtn", function () {


    let row = $(this).closest('tr');


    let className = row.find('td').eq(0).text();
    let subjectName = row.find('td').eq(1).text();
    let instructorName = row.find('td').eq(2).text();
    let scheduleDate = row.find('td').eq(3).text();
    let startTime = row.find('td').eq(4).text();
    let endTime = row.find('td').eq(5).text();


    $("#className2").val(className).change();
    $("#subName2").val(subjectName).change();
    $("#instName2").val(instructorName).change();
    $("#scheduleDate2").val(scheduleDate);
    $("#startTime2").val(startTime);
    $("#endTime2").val(endTime);

    // Show the modal
    $("#editScheduleModal").modal("show");
});


$("#updateBtn").click(function () {

    const className = $("#className2").val();
    const subjectName = $("#subName2").val();
    const instructorName = $("#instName2").val();
    const scheduleDate = $("#scheduleDate2").val();
    const formattedDate = scheduleDate.split("/").reverse().join("-");
    const startTime = $("#startTime2").val().length === 5 ? $("#startTime2").val() + ":00" : $("#startTime2").val();
    const endTime = $("#endTime2").val().length === 5 ? $("#endTime2").val() + ":00" : $("#endTime2").val();
    let classId = null;
    let subjectId = null;
    let instructorId =  null;
    let scheduleId;

    $.ajax({
        url: `http://localhost:8080/api/v1/class/getClId/${className}`,
        method: "GET",

        success: function (data) {
            classId = data;
            console.log("Retrieved classId:", classId);

            $.ajax({
                url: `http://localhost:8080/api/v1/subject/getSubId/${subjectName}`,
                method: "GET",

                success: function (data) {
                    subjectId = data;
                    console.log("Retrieved subjectId:", subjectId);

                    $.ajax({
                        url: `http://localhost:8080/api/v1/instructor/getId/${instructorName}`,
                        method: "GET",

                        success: function (data) {
                            instructorId = data;
                            console.log("Retrieved instId:", instructorId)
                            console.log(scheduleDate,startTime)
                            $.ajax({
                                url: `http://localhost:8080/api/v1/schedule/update`,
                                method: "PUT",

                                contentType: "application/json",
                                dataType: "json",

                                data: JSON.stringify({
                                    schedule_date: formattedDate,
                                    start_time: startTime,
                                    end_time: endTime,
                                    class_id: classId,
                                    class_name: className,
                                    instructor_name: instructorName,
                                    subject_name: subjectName
                                }),

                                success: function (data) {
                                    alert("Schedule updated successfully.");
                                    fetchScheduleData();
                                    clearFields();

                                    $.ajax({
                                        url: `http://localhost:8080/api/v1/schedule/getID/${formattedDate}/${className}/${instructorName}`,
                                        method: "GET",

                                        success: function (data) {
                                            scheduleId = data;

                                            $.ajax({
                                                url: `http://localhost:8080/api/v1/sbSchedule/update`,
                                                method: "PUT",
                                                contentType: "application/json",
                                                dataType: "json",
                                                data: JSON.stringify({
                                                    subject_id: subjectId,
                                                    schedule_id: scheduleId,
                                                    subject_name: subjectName,
                                                }),

                                                success: function (data) {
                                                    alert("Subject schedule updated successfully.");

                                                    $.ajax({
                                                        url: `http://localhost:8080/api/v1/instSchedule/update`,
                                                        method: "PUT",

                                                        contentType: "application/json",
                                                        dataType: "json",
                                                        data: JSON.stringify({
                                                            instructor_id: instructorId,
                                                            schedule_id: scheduleId,
                                                            instructor_name: instructorName,
                                                        }),

                                                        success: function (data) {
                                                            alert("Instructor schedule updated successfully.");
                                                        },
                                                        error: function () {
                                                            alert("Instructor schedule not updated!");
                                                        }
                                                    })
                                                },
                                                error: function () {
                                                    alert("Subject schedule not updated!!");
                                                }
                                            })
                                        },
                                        error: function () {
                                            alert("Error loading in schedule id!");
                                        }
                                    })
                                },
                                error: function () {
                                    alert("Schedule not updated!");
                                }
                            })
                        },
                        error: function () {
                            alert("Error loading in instructor id!");
                        }
                    })


                },
                error: function () {
                    alert("Error loading in subject id!");
                }
            })
        },
        error: function () {
            alert("Error loading in class id!");
        }
    })
})


$(document).on("click", "#deleteBtn", function () {

    let row = $(this).closest('tr');
    let className = row.find('td').eq(0).text();
    let instructorName = row.find('td').eq(2).text();
    let date = row.find('td').eq(3).text();

    if (confirm("Are you sure?")) {

        // Get Schedule ID first
        $.ajax({
            url: `http://localhost:8080/api/v1/schedule/getID/${date}/${className}/${instructorName}`,
            method: "GET",
            success: function (scheduleId) {
                console.log("Schedule ID:", scheduleId);

                // Delete Instructor Schedule First
                $.ajax({
                    url: `http://localhost:8080/api/v1/instSchedule/delete/${scheduleId}`,
                    method: "DELETE",
                    success: function () {
                        console.log("Instructor Schedule deleted successfully!");

                        // Delete Subject Schedule Next
                        $.ajax({
                            url: `http://localhost:8080/api/v1/sbSchedule/delete/${scheduleId}`,
                            method: "DELETE",
                            success: function () {
                                console.log("Subject Schedule deleted successfully!");


                                $.ajax({
                                    url: `http://localhost:8080/api/v1/schedule/delete/${date}/${className}/${instructorName}`,
                                    method: "DELETE",
                                    success: function () {
                                        alert("Schedule deleted successfully!");
                                        row.remove();
                                    },
                                    error: function () {
                                        alert("Schedule not deleted!");
                                    }
                                });
                            },
                            error: function () {
                                alert("Subject Schedule not deleted!");
                            }
                        });
                    },
                    error: function () {
                        alert("Instructor Schedule not deleted!");
                    }
                });
            },
            error: function () {
                alert("Error loading schedule ID!");
            }
        });
    }
});


function clearFields(){
    $("#className").val("");
    $("#subName").val("");
    $("#instName").val("");
    $("#scheduleDate").val("");
    $("#startTime").val("");
    $("#endTime").val("");
    $("#className2").val("");
    $("#subName2").val("");
    $("#instName2").val("");
    $("#scheduleDate2").val("");
    $("#startTime2").val("");
    $("#endTime2").val("");
}

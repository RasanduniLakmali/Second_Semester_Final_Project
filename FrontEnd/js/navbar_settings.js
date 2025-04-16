
document.addEventListener("DOMContentLoaded", function () {
    const userRole = localStorage.getItem("userRole");

    console.log(userRole);

    if (userRole === "STUDENT") {
        const studentNavItem = document.getElementById("studentNavItem");
        const pageDown = document.getElementById("pageDown");
        const instProfile = document.getElementById("instructorProfile");
        const attendance = document.getElementById("attendanceNavItem");

        if (studentNavItem && pageDown && instProfile) {
            studentNavItem.style.display = "none";
            pageDown.style.display = "none";
            instProfile.style.display = "none";
            attendance.style.display = "none";
        }
    }else if (userRole === "INSTRUCTOR"){
        const pageDown = document.getElementById("pageDown");
        const studentProfile = document.getElementById("studentProfile");
        const classNavItem = document.getElementById("classNavItem");
        const payment = document.getElementById("paymentNavItem");

        if (pageDown && studentProfile && classNavItem) {
            pageDown.style.display = "none";
            studentProfile.style.display = "none";
            classNavItem.style.display = "none";
            payment.style.display = "none";
        }
    }
});



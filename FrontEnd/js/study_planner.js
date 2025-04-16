

function generatePlanner() {
    const token = localStorage.getItem("jwtToken");
    if (!token) {
        alert("Token is missing. Please log in again.");
        return;
    }

    const freeHours = document.getElementById("freeHours").value;
    const email = localStorage.getItem("loggedInEmail");

    if (!freeHours || freeHours <= 0) {
        alert("Please enter valid free hours.");
        return;
    }

    // Show loading spinner
    document.getElementById("plannerLoading").style.display = "block";
    document.getElementById("plannerResult").innerHTML = "";

    fetch("http://localhost:8080/api/v1/planner/generate", {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: email,
            freeHours: freeHours,
            includeActivities: true
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            // Hide loading spinner
            document.getElementById("plannerLoading").style.display = "none";

            // Group by subjects
            const subjects = {};
            data.plan.forEach(item => {
                if (!subjects[item.subject]) {
                    subjects[item.subject] = [];
                }
                subjects[item.subject].push(item);
            });

            let plannerHTML = `
        <div class="mt-4">
            <h4 class="mb-4 text-center">Today's Personalized Study Schedule</h4>
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th scope="col">Subject</th>
                            <th scope="col">Activity</th>
                            <th scope="col">Duration</th>
                            <th scope="col">Progress</th>
                        </tr>
                    </thead>
                    <tbody>`;

            // Color mapping for different activities
            const activityColors = {
                "Studying": "primary",
                "Papers": "success",
                "Practice": "success",
                "Revision": "info",
                "Quiz": "warning",
                "Project": "secondary"
            };

            // Default color if activity type doesn't match
            const defaultColor = "dark";

            // Display each subject with its activities
            let subjectIndex = 0;
            for (const [subject, activities] of Object.entries(subjects)) {
                const rowspan = activities.length;
                const totalSubjectHours = activities.reduce((sum, a) => sum + parseFloat(a.duration), 0);

                let activityIndex = 0;
                activities.forEach((activity, index) => {
                    const activityType = activity.activityType || "Studying";
                    const color = activityColors[activityType] || defaultColor;

                    plannerHTML += `<tr>`;

                    // Only show subject in first row of the group
                    if (index === 0) {
                        plannerHTML += `<td rowspan="${rowspan}" class="align-middle fw-bold">${subject} <span class="badge bg-dark">${totalSubjectHours} hrs</span></td>`;
                    }

                    plannerHTML += `
                    <td><span class="badge bg-${color}">${activityType}</span></td>
                    <td>${activity.duration} hour(s)</td>
                    <td>
                        <div class="progress-container d-flex align-items-center">
                            <div class="progress flex-grow-1 me-2" style="height: 20px;">
                                <div id="progress-${subjectIndex}-${activityIndex}" 
                                     class="progress-bar bg-${color}" role="progressbar" 
                                     style="width: 0%" aria-valuenow="0" 
                                     aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                            <div class="progress-controls">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" 
                                           id="complete-${subjectIndex}-${activityIndex}" 
                                           onchange="this.checked ? updateProgress(${subjectIndex}, ${activityIndex}, 100) : updateProgress(${subjectIndex}, ${activityIndex}, 0)">
                                    <label class="form-check-label" for="complete-${subjectIndex}-${activityIndex}">
                                        Complete
                                    </label>
                                </div>
                                <div class="btn-group btn-group-sm mt-1" role="group">
                                    <button type="button" class="btn btn-outline-secondary" 
                                            onclick="updateProgress(${subjectIndex}, ${activityIndex}, 25)">25%</button>
                                    <button type="button" class="btn btn-outline-secondary" 
                                            onclick="updateProgress(${subjectIndex}, ${activityIndex}, 50)">50%</button>
                                    <button type="button" class="btn btn-outline-secondary" 
                                            onclick="updateProgress(${subjectIndex}, ${activityIndex}, 75)">75%</button>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>`;

                    activityIndex++;
                });

                subjectIndex++;
            }

            // Add a timeline visualization
            plannerHTML += `</tbody></table></div>`;

            // Add summary section
            const totalHours = data.plan.reduce((sum, item) => sum + parseFloat(item.duration), 0);

            plannerHTML += `
        <div class="card mt-4 bg-light">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-6">
                        <h5>Summary</h5>
                        <p><strong>Total study time:</strong> ${totalHours} hours</p>
                        <p><strong>Subjects covered:</strong> ${Object.keys(subjects).length}</p>
                    </div>
                    <div class="col-md-6">
                        <h5>Activity Breakdown</h5>
                        <div class="d-flex flex-wrap">`;

            // Calculate percentage for each activity type
            const activityTotals = {};
            data.plan.forEach(item => {
                const type = item.activityType || "Studying";
                activityTotals[type] = (activityTotals[type] || 0) + parseFloat(item.duration);
            });

            for (const [activity, hours] of Object.entries(activityTotals)) {
                const percent = Math.round((hours / totalHours) * 100);
                const color = activityColors[activity] || defaultColor;

                plannerHTML += `
                <div class="me-3 mb-2">
                    <span class="badge bg-${color}">${activity}: ${percent}%</span>
                </div>`;
            }

            plannerHTML += `
                        </div>
                    </div>
                </div>
            </div>
        </div>`;

            // Add tips section
            plannerHTML += `
        <div class="card mt-4 border-primary">
            <div class="card-header bg-primary text-white">
                Study Tips
            </div>
            <div class="card-body">
                <ul class="mb-0">
                    <li>Take short 5-minute breaks every 25 minutes (Pomodoro technique)</li>
                    <li>Stay hydrated throughout your study sessions</li>
                    <li>Mark your progress in the progress bars as you complete each task</li>
                </ul>
            </div>
        </div>`;

            document.getElementById("plannerResult").innerHTML = plannerHTML;

            // Load saved progress after a short delay to ensure all elements are rendered
            setTimeout(() => {
                loadSavedProgress();
            }, 500);
        })
        .catch(error => {
            console.error("Error generating planner:", error);
            document.getElementById("plannerLoading").style.display = "none";
            document.getElementById("plannerResult").innerHTML =
                `<div class="alert alert-danger">Error generating planner: ${error.message}</div>`;
        });
}

// Function to update progress for a specific activity
function updateProgress(subjectIndex, activityIndex, progressValue) {
    // Get the progress bar element
    const progressBar = document.querySelector(`#progress-${subjectIndex}-${activityIndex}`);

    // Update the progress bar
    progressBar.style.width = `${progressValue}%`;
    progressBar.setAttribute("aria-valuenow", progressValue);

    // Change background color when completed
    if (progressValue >= 100) {
        progressBar.classList.remove("bg-primary", "bg-success", "bg-info", "bg-warning", "bg-secondary", "bg-dark");
        progressBar.classList.add("bg-success");

        // Check the checkbox
        document.querySelector(`#complete-${subjectIndex}-${activityIndex}`).checked = true;
    }

    // Save progress to localStorage
    const progressKey = `study-progress-${new Date().toDateString()}`;
    let savedProgress = JSON.parse(localStorage.getItem(progressKey) || "{}");
    savedProgress[`${subjectIndex}-${activityIndex}`] = progressValue;
    localStorage.setItem(progressKey, JSON.stringify(savedProgress));

    // Update overall progress
    updateOverallProgress();
}

// Function to load saved progress
function loadSavedProgress() {
    const progressKey = `study-progress-${new Date().toDateString()}`;
    const savedProgress = JSON.parse(localStorage.getItem(progressKey) || "{}");

    // Apply saved progress to all progress bars
    for (const [key, value] of Object.entries(savedProgress)) {
        const [subjectIndex, activityIndex] = key.split('-');
        const progressBar = document.querySelector(`#progress-${subjectIndex}-${activityIndex}`);

        if (progressBar) {
            progressBar.style.width = `${value}%`;
            progressBar.setAttribute("aria-valuenow", value);

            if (value >= 100) {
                progressBar.classList.remove("bg-primary", "bg-success", "bg-info", "bg-warning", "bg-secondary", "bg-dark");
                progressBar.classList.add("bg-success");
                document.querySelector(`#complete-${subjectIndex}-${activityIndex}`).checked = true;
            }
        }
    }

    // Update overall progress
    updateOverallProgress();
}

// Function to update overall progress
function updateOverallProgress() {
    const progressKey = `study-progress-${new Date().toDateString()}`;
    const savedProgress = JSON.parse(localStorage.getItem(progressKey) || "{}");

    // Count total activities and completed activities
    const progressBars = document.querySelectorAll('.progress-bar[id^="progress-"]');
    let totalActivities = progressBars.length;
    let completedActivities = 0;
    let totalProgress = 0;

    for (const value of Object.values(savedProgress)) {
        totalProgress += value;
        if (value >= 100) {
            completedActivities++;
        }
    }

    // Calculate overall percentage
    const overallPercent = totalActivities > 0 ?
        Math.round(totalProgress / (totalActivities * 100) * 100) : 0;

    // Add overall progress indicator if it doesn't exist yet
    if (!document.getElementById('overall-progress') && totalActivities > 0) {
        const summaryCard = document.querySelector('.card.mt-4.bg-light .card-body .row .col-md-6:first-child');
        if (summaryCard) {
            const progressHTML = `
                <div id="overall-progress" class="mt-3">
                    <h6>Overall Progress: ${overallPercent}%</h6>
                    <div class="progress" style="height: 25px;">
                        <div class="progress-bar bg-primary" role="progressbar" 
                             style="width: ${overallPercent}%" aria-valuenow="${overallPercent}" 
                             aria-valuemin="0" aria-valuemax="100">
                             ${overallPercent}%
                        </div>
                    </div>
                    <p class="mt-2 small text-muted">${completedActivities} of ${totalActivities} activities completed</p>
                </div>
            `;
            summaryCard.insertAdjacentHTML('beforeend', progressHTML);
        }
    } else if (document.getElementById('overall-progress')) {
        // Update existing overall progress
        const overallProgressBar = document.querySelector('#overall-progress .progress-bar');
        const overallProgressText = document.querySelector('#overall-progress h6');
        const completionText = document.querySelector('#overall-progress p.small');

        if (overallProgressBar && overallProgressText && completionText) {
            overallProgressBar.style.width = `${overallPercent}%`;
            overallProgressBar.setAttribute('aria-valuenow', overallPercent);
            overallProgressBar.textContent = `${overallPercent}%`;
            overallProgressText.textContent = `Overall Progress: ${overallPercent}%`;
            completionText.textContent = `${completedActivities} of ${totalActivities} activities completed`;
        }
    }
}
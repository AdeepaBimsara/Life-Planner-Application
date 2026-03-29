let planType = '';
let category = '';
let timeData = {};
let tasks = [];

$(document).ready(function () {
    $('#step1').show();
    $('.step').not('#step1').hide();

});

function nextStep(type) {
    planType = type;
    $('#step1').hide();
    $('#step2').show();
}

function nextStepCategory(cat) {
    category = cat;
    $('#step2').hide();
    $('#step3').show();
    renderTimeInputs();
}

function renderTimeInputs() {
    const container = $('#timeInputs');
    container.html('');

    if (planType === 'Day') {
        container.html(`
            <label>Date:</label><br>
            <input type="date" id="planDate"><br>
        `);
    } else if (planType === 'Week') {
        container.html(`
            <label>Select Week Start Date:</label><br>
            <input type="date" id="planWeek"><br>
        `);
    } else if (planType === 'Month') {
        container.html(`
            <label>Select Month:</label><br>
            <input type="month" id="planMonth"><br>
        `);
    } else if (planType === 'Year') {
        container.html(`
            <label>Select Year:</label><br>
            <input type="number" id="planYear" min="2000" max="2100"><br>
        `);
    }
}

function nextStepTime() {
    if (planType === 'Day') {
        timeData = {
            date: $('#planDate').val(),
            start: $('#planStart').val(),
            end: $('#planEnd').val()
        };
    } else if (planType === 'Week') {
        timeData = { weekStart: $('#planWeek').val() };
    } else if (planType === 'Month') {
        timeData = { month: $('#planMonth').val() };
    } else if (planType === 'Year') {
        timeData = { year: $('#planYear').val() };
    }

    $('#step3').hide();
    $('#head-title').hide()
    $('#step4').show();


}

function addTask() {

    const name = $('#taskName').val();
    const start = $('#taskStart').val();
    const end = $('#taskEnd').val();

    if (name && start && end) {
        tasks.push({
            name:name,
            startTime:start,
            endTime:end
        });

        //table
        $('#taskList').html(
        tasks.map((t, i) => `
    <li onclick="selectTask(${i})" style="cursor:pointer;">
        ${t.name} (${t.startTime} - ${t.endTime})
    </li>
`).join(''))
        console.log(name,start,end)
        $('#taskName').val('');
        $('#taskStart').val('');
        $('#taskEnd').val('');
    } else {
        alert('Please fill all task fields!');
    }
}

function savePlan() {
    $('#step4').hide();
    $('#step5').show();



    const planData = {
        planType:planType,
        category:category,
        ...timeData,
        tasks:tasks
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/plan/save",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(planData),

        success: function (res) {
            alert("Plan saved successfully");
            console.log(res);
            getAllPlan();
        },

        error: function (err) {
            console.log(err);
        }
    });

    console.log(JSON.stringify(planData, null, 2));
}

function getAllPlan(){

    $.ajax({
        type:"GET",
        url: "http://localhost:8080/api/v1/plan/getAll",
        success:function (response){
            const plans = response.data;
            const planTbl = $('#tblSummary')
            planTbl.empty();
            plans.forEach(plan =>{
                const row =`<tr>
                      <td>${plan.planType}</td>
                      <td>${plan.category}</td>
                      <td>${plan.tasks.map(t=>`${t.name}`)}</td>
                      <td>${plan.tasks.map(t=>`(${t.startTime} - ${t.endTime})`)}</td>
                </tr>`;
                planTbl.append(row)
            })
        }
    })
}

let selectedIndex = null;

function selectTask(index) {
    selectedIndex = index
    const task = tasks[index];

    $('#taskName').val(task.name);
    $('#taskStart').val(task.startTime);
    $('#taskEnd').val(task.endTime);
}

function editTask() {
    console.log("selectedIndex:", selectedIndex);
    console.log("tasks array:", tasks);

    if (selectedIndex === null);

    const task = tasks[selectedIndex];

    task.name=$('#taskName').val()
    task.startTime = $('#taskStart').val()
    task.endTime=$('#taskEnd').val()

    $('#taskList').html(
        tasks.map((t, i) => `
    <li onclick="selectTask(${i})" style="cursor:pointer;">
        ${t.name} (${t.startTime} - ${t.endTime})
    </li>
`).join(''))

    selectedIndex = null;

    // toggle buttons
    $('#addBtn').hide();
    $('#updateBtn').show();
}

function deleteTask(index) {
    tasks.splice(index, 1); // remove task
    refreshTasks();          // update UI
}

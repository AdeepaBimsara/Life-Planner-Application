$(document).ready(function (){
    getAllPlan();
})

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
                      <td><button class="btn btn-danger btn-sm" onclick="deletePlan(${plan.id})">Delete</button></td>
                </tr>`;
                planTbl.append(row)
            })
        }
    })
}

function deletePlan(id){
    console.log("Deleting ID:", id);
    if (confirm("Are you sure delete this plan")){
        $.ajax({
            type: "DELETE",
            url:"http://localhost:8080/api/v1/plan/delete/"+id,
            success:function (response){
                alert(response.message)
                getAllPlan()
            },
            error:function (response){
                console.log(response.reponseJSON.message)
            }
        })
    }
}
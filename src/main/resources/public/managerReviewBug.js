import {giveFeedback} from "/feeback.js"

window.addEventListener("load", () => {
    let submitBtn=document.getElementById("update-input");
    submitBtn.addEventListener("click",updateBug);
});

async function updateBug(){
    let updateServerity = document.getElementById("update-severity");
    let updateUrgency = document.getElementById("update-urgency");
    let assignedTo = document.getElementById("assign-to");
    let updateStatus = document.getElementById("update-status");
    let feedback=document.getElementById("feedback");

    let loginInfo=localStorage.getItem("login")
    let jsonLoginInfo=JSON.parse(loginInfo)

    let severityValue = updateServerity.value
    let urgencyValue = updateUrgency.value
    let assignValue = assignedTo.value
    let updateStatusValue = updateStatus.value

    if (isNaN(severityValue)) {
        severityValue = null;
    }
    if (isNaN(urgencyValue)) {
        urgencyValue = null;
    }
    if (isNaN(assignValue)) {
        assignValue = null;
    }
    if (isNaN(updateStatusValue)) {
        updateStatusValue = null;
    }


    let result=await fetch("/bug",{
        headers:{
            'Content-Type': 'application/json',
            'Authorization': jsonLoginInfo.token
        },
        method: 'PATCH',
        body: JSON.stringify({
            "bug_id":bugId,
            "severity":severityValue,
            "urgency":urgencyValue,
            "assigned_to":assignValue,
            "status": updateStatusValue
        })
    }).then(result =>{
            return result.json();
        }).then(jsonObj=>{
            console.log(jsonObj)
            return jsonObj;
        }).catch(exception=>{
            console.log(exception)
        })
    ;
    let message;
    console.log(typeof result);
    if(typeof result!="undefined" && typeof result!="string" && result.hasOwnProperty("bugs")){
        message=giveFeedback(200);
        message.appendChild(document.createTextNode("Update Success"));
    }
    else {
        message=giveFeedback(200);
        message.appendChild(document.createTextNode("Update Failed"));
    }
    feedback.appendChild(message);

}

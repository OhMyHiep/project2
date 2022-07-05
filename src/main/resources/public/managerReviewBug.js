import {giveFeedback} from "/feeback.js"

window.addEventListener("load", () => {
    let submitBtn=document.getElementById("update");
    submitBtn.addEventListener("click",updateBug);
});

async function updateBug(){
    let updateServerity = document.getElementById("update-severity");
    let updateUrgency = document.getElementById("update-urgency");
    // let assignedTo = document.getElementById("assign-to");
    let updateStatus = document.getElementById("update-status");
    let feedback=document.getElementById("feedback");

    let assignedTo=1;
    let bugId=5;

    let result=await fetch("/bug",{
        headers:{
            'Content-Type': 'application/json'
        },
        method: 'PATCH',
        body: JSON.stringify({
            "bug_id":bugId,
            "severity":updateServerity.value,
            "urgency":updateUrgency.value,
            "assigned_to":assignedTo,
            "status": updateStatus.value
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

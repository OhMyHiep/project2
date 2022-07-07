window.addEventListener("load", () => {
    let submitBtn=document.getElementById("submit");
    submitBtn.addEventListener("click",submitBug);
});
import {giveFeedback} from "/feeback.js";
async function submitBug(){
    let creatorId=1
    let title =document.getElementById("title");
    let severity=document.getElementById("severity");
    let urgency=document.getElementById("urgency");
    let description=document.getElementById("description");

    console.log(title.value);
    console.log(severity.value);
    console.log(urgency.value);
    console.log(description.value);
    let message;
    if(!title.value || !severity.value || !urgency.value || !description.value ){
        message=giveFeedback(400);
        message.appendChild(document.createTextNode("Fill Out All Fields"));
        console.log("message here "+message.innerText);
    }
    else {
        let loginInfo = localStorage.getItem('login')
        let parsedJson = JSON.parse(loginInfo)
        const result =await fetch("/bug",{
            headers:{
                'Content-Type': 'application/json',
                'Authorization': parsedJson.token
            },
            method: 'POST',
            body: JSON.stringify({
                "title": title.value,
                "severity":severity.value,
                "urgency":urgency.value,
                "description":description.value,
                "creator_id":creatorId
            })
        });
        if(result.status !=200 ){
            message=giveFeedback(result.status);
            message.appendChild(document.createTextNode("Failed to submit bug"));
        }
        else{
            message=giveFeedback(result.status);
            message.appendChild(document.createTextNode("Success"));
        }
    }
    let feedback=document.getElementById("feedback");
    feedback.appendChild(message)
}


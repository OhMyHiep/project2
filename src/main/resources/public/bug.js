
let bugId=5;

window.addEventListener("load", () => {
    onLoadDisplay();
});

async function getBug(){
    const res = await fetch(`bug/${bugId}`,{
        headers:{
        'Content-Type': 'application/json',
        'Authorization': jsonLoginInfo.token
        },
        method: "GET"})
        .then(result =>{
            return result.json();
        }).then(jsonObj=>{
            console.log(jsonObj)
            return jsonObj;
        }).catch(exception=>{
         console.log(exception)
         })
        ;
    return res;

}

function displayBug(bug){
    let title =document.getElementById("title");
    let creatorName=document.getElementById("creator-name");
    let severity=document.getElementById("severity");
    let urgency=document.getElementById("urgency");
    let status=document.getElementById("status");
    let assignedTo=document.getElementById("assigned-to");
    let closeDate=document.getElementById("close-date");
    let issueDate=document.getElementById("issue-date");
    let description=document.getElementById("description");
    // console.log(bug);
    title.innerText=bug["title"];
    creatorName.innerText=bug["creator"]["firstname"]+" "+bug["creator"]["lastname"];
    severity.innerText="Severity: "+bug["severity"];
    urgency.innerText="Urgency: "+bug["urgency"];
    status.innerText="Status: "+bug["status"];
    closeDate.innerText=bug["closeDate"];
    description.innerText=bug["description"]

    let issueDateConverted=new Date(bug["issueDate"])
    issueDate.innerText=issueDateConverted.getMonth()+"/"+issueDateConverted.getDate()+"/"+issueDateConverted.getFullYear();
    if (bug["assigned_to"]!=null){
        assignedTo.innerText="Assigned To: "+bug["assigned_to"]["firstname"]+" "+bug["assigned_to"]["lastname"];
    }
}

async function onLoadDisplay(){
   let bug= await getBug();
    displayBug(bug);

}


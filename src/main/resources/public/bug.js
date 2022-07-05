let bugId;


window.addEventListener("load", () => {
    let qString = window.location.search;
    const urlParams = new URLSearchParams(qString);
    bugId=urlParams.get('id');
    loadAssignees();
    onLoadDisplay();
    techLeadView();
});

async function getBug(){
    let loginInfo=localStorage.getItem("login")
    let jsonLoginInfo=JSON.parse(loginInfo)
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

async function loadAssignees(){
    let users= await getUsers()
        .then(result=>{
            for(let u of result){
                let array=u.url.split("/");
                console.log(array);
                let id=array[2];
                let option=document.createElement("option");
                option.text=id;
                option.value=id;
                let assignSelect=document.getElementById("assign-to");
                assignSelect.appendChild(option)
            }
        });
}


async function getUsers(){
    let loginInfo=localStorage.getItem("login")
    let jsonLoginInfo=JSON.parse(loginInfo)
    const res = await fetch(`/user`,{
        headers:{
            'Content-Type': 'application/json',
            'Authorization': jsonLoginInfo.token
        },
        method: "GET"})
        .then(result =>{
            return result.json();
        }).then(jsonObj=>{
            console.log(jsonObj.users)
            return jsonObj.users;
        }).catch(exception=>{
            console.log(exception)
        })
    ;
    return res;

}


function techLeadView(){
    let loginInfo=localStorage.getItem("login");
    let jsonLoginInfo=JSON.parse(loginInfo);
    let roles= jsonLoginInfo.user.roles;
    for(let r of roles){
        if(r.roleTitle=="TechLead") {
            let doc=document.getElementById("update-section");
            doc.setAttribute("class","col-md-6 col-lg-6 visible")
        }
    }


}


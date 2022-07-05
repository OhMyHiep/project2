let body =document.querySelector("body")
//getAssignedBugs(2)
getAllBugs()
async function getAllBugs(){
    let url="/bug"
    try{
        let loginInfo=localStorage.getItem("login")
        let jsonLoginInfo=JSON.parse(loginInfo)
        console.log(jsonLoginInfo)
        let response_body = await fetch(url,{
        headers:{
               'Content-Type': 'application/json',
               'Authorization': jsonLoginInfo.token
        },
        method: "GET"
        })
        let data = await response_body.json()
        console.log(data)
        var todayDate = new Date().toISOString().slice(0, 10);
        console.log(todayDate);
        if(data != null){
            let targetUrl
            for(let bugs in data)
                for(let bug in data[bugs])
                {
                    targetUrl= data[bugs][bug].url
                     displayBugs(data[bugs][bug])
                }
        }
    }catch(e){
        console.log(e)
        }
}

async function getAssignedBugs(currentUser_id){
    let url= `/bug/assign/${currentUser_id}`
    try{
        let response_body = await fetch(url,{
        method: "GET"
        })
        let data = await response_body.json()
        console.log(data)
        if(data != null){
            let targetUrl
            for(let bugs in data)
                for(let bug in data[bugs])
                    {
                        //targetUrl= data[bugs][bug].url
                        displayBugs(data[bugs][bug])
                    }
        }
    }catch(e){
        console.log(e)
        }
}


let bugInfo=``
let subString
function displayBugs(data)
{
                console.log(data)
                console.log(data.url)
                subString = data.url.split("/")
                let bug_id=subString[2]
                var issueDate = new Date(data.issueDate);
                bugInfoHolder=document.querySelector("#bugViewer");
                bugInfo+=
                    `<div class="card">
                    <div class="card-body">
                        <h6 class="card-subtitle mb-2 text-muted">Title: ${data.title} Issue Date: ${issueDate.getMonth()+1}/${issueDate.getDate()}/${issueDate.getFullYear()} Status: ${data.status} Urgency: ${data.urgency} Severity: ${data.severity}</h6>
                    </div>
                    <div>
                        <a class="btn btn-primary viewAllBtn" href="/detailBug?id=${bug_id}">View Full Details</a><br>
                    </div>
                </div><br>`

                bugInfoHolder.innerHTML=bugInfo
}




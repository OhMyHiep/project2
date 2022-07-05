let body =document.querySelector("body")
//getAssignedBugs(2)
getAllBugs()
async function getAllBugs(){
    let url="/bug"
    try{
        let response_body = await fetch(url,{
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
                        targetUrl= data[bugs][bug].url
                        getBugInfo(targetUrl)
                    }
        }
    }catch(e){
        console.log(e)
        }
}

async function getBugInfo(url){
     try{
            let response_body = await fetch(url,{
            method: "GET"
            })
            let data = await response_body.json()
            displayBugs(data)
        }catch(e){
            console.log(e)
            }
}
let bugInfo=``
function displayBugs(data)
{
                console.log(data)
                console.log(data.status)
                var issueDate = new Date(data.issueDate);
                bugInfoHolder=document.querySelector("#bugViewer");
                bugInfo+=
                    `<div class="card">
                    <div class="card-body">
                        <h6 class="card-subtitle mb-2 text-muted">Created By: ${data.creator.firstname} ${data.creator.lastname} Issue Date: ${issueDate.getMonth()+1}/${issueDate.getDate()}/${issueDate.getFullYear()} Status: ${data.status} Urgency: ${data.urgency} Severity: ${data.severity}</h6>
                        <p class="card-text">Description: ${data.description}</p>
                    </div>
                    <div>
                        <button type="button" class="btn btn-primary" id="viewAllBtn" name=${data.bug_id} onclick=generatePopup(${data.bug_id})>View Full Details</button>
                        <button type="button" class="btn btn-primary" id ="viewCommentsBtn" name=${data.bug_id}> View Comments</button><br>
                    </div>
                </div><br>`

                bugInfoHolder.innerHTML=bugInfo
}


function generatePopup(bug_id)
{
 console.log(bug_id)
}


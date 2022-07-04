let body =document.querySelector("body")

getAllBugs()
//getAllComments()
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
        //getAllComments(data.bug_id)
    }catch(e){
        console.log(e)
        }
}

async function getAllComments(bug_id){
    let url=`http://localhost:8080/comments/${bug_id}`
    try{
        let response_body = await fetch(url,{
        method: "GET"
        })
        let data = await response_body.json()
        console.log(data)
    }catch(e){
        console.log(e)
        }
}

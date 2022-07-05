
//this function create elements that display error/success message
//import and append this to where you want to display the error message
function giveFeedback(status){
    let message=document.createElement("div");
    if(status!=200){
        message.setAttribute("class","alert alert-danger alert-dismissable fade show")
    }
    else {
        message.setAttribute("class","alert alert-success alert-dismissable fade show")
    }
    message.setAttribute("role","alert");
    message.setAttribute("id","feedback-msg")

    let closeBtn=document.createElement("button");
    closeBtn.setAttribute("type","button");
    closeBtn.setAttribute("class","close");
    closeBtn.setAttribute("data-dismiss","alert");

    let xClose=document.createElement("SPAN");
    xClose.setAttribute("aria-hidden","true");
    xClose.innerText="x";

    closeBtn.appendChild(xClose);
    message.appendChild(closeBtn);

    return message;
}

export {giveFeedback}
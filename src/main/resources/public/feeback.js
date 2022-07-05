function giveFeedback(status){
    let message=document.createElement("div");
    if(status!=200){
        message.setAttribute("class","alert alert-danger alert-dismissable fade show")
    }
    else {
        message.setAttribute("class","alert alert-success alert-dismissable fade show")
    }
    message.setAttribute("role","alert");

    let closeBtn=document.createElement("button");
    closeBtn.setAttribute("type","button");
    closeBtn.setAttribute("class","close");
    closeBtn.setAttribute("data-dismiss","alert");

    let xClose=document.createElement("SPAN");
    xClose.setAttribute("aria-hidden","true");
    xClose.innerText="x";

    console.log(xClose);
    console.log(closeBtn);

    closeBtn.appendChild(xClose);
    message.appendChild(closeBtn);

    console.log(message);
    return message;
}

export {giveFeedback}
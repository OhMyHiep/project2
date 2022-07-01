let bugId = 3

// DISPLAY COMMENTS START
function convertDate(date) {
    let dateConvert = new Date();
    dateConvert.setTime(date)
    const options = { weekday:'long', year:'numeric', month:'long', day:'numeric'}
    dateString = dateConvert.toLocaleDateString(undefined, options)

    return dateString
}

function create_comment_block(item) {
    // Element creation
    let colElement = document.createElement("div")
    colElement.setAttribute("class", "col-12 m-1")

    let cardElement = document.createElement("div")
    cardElement.setAttribute("class", "card")

    let cardBodyElement = document.createElement("div")
    cardBodyElement.setAttribute("class", "card-body")

    let commenterNameElement = document.createElement("h6")
    commenterNameElement.setAttribute("class", "card-subtitle mb-2 text-muted") 
    commenterNameElement.textContent = item.userUrl.firstname + " " + item.userUrl.lastname
    
    let commentTextElement = document.createElement("p")
    commentTextElement.setAttribute("class", "card-text")
    commentTextElement.textContent = item.commentText

    let commentDateElement = document.createElement("p")
    commentDateElement.setAttribute("class", "card-subtitle text-muted text-end")
    commentDateElement.textContent = convertDate(item.commentDate)

    //Element append
    cardBodyElement.append(commenterNameElement, commentTextElement, commentDateElement)
    cardElement.append(cardBodyElement)
    colElement.append(cardElement)

    return colElement
}

function create_no_comment() {
    let noCommentMessage = document.createElement("p")
    noCommentMessage.setAttribute("id", "no-comment")
    noCommentMessage.textContent = "No comments yet"

    return noCommentMessage
}

function populate_comments(commentList) {
    let commentsDiv = document.querySelector('#comment-section-list');
    if (commentList.length > 0) {
        let commentElements = []
        for (let comment of commentList) {
            let commentBlock = create_comment_block(comment)
            commentElements.push(commentBlock)
        }

        for (let element of commentElements) {
            commentsDiv.append(element)
        }
    }
    else {
        let message = create_no_comment()
        commentsDiv.append(message)
    }
}

function create_error_message(errorMessage) {
    if (!document.querySelector('.alert')) {
        let commentErrorDiv = document.querySelector('#comment-error-div');

        let alertElement = document.createElement("div")
        alertElement.setAttribute("class", "alert alert-danger")
        alertElement.setAttribute("role", "alert")
        alertElement.textContent = errorMessage
    
        commentErrorDiv.append(alertElement);
    }
}

async function get_comments_by_id(){
    try {
        // let bugIdUrl = window.location.pathname;
        // let start = bugId.indexOf('/');
        // let end = bugId.lastIndexOf('/');
        // let bugId = bugIdUrl.slice(start, end)

        const res = await fetch(`bug/${bugId}/comments`)
        if (res.status != 200){
            create_error_message("No comments to be found. Bug doesn't exist!")
            const message = `Couldn't obtain requests! An error occured: ${res.status} ${res.statusText}`
            throw message
        }
        else {
            const commentJson = await res.json()
            populate_comments(commentJson)
        }
    }
    catch(err) {
        console.log(err)
    }     
}

get_comments_by_id()

// DISPLAY COMMENTS END



// SUBMIT COMMENT START
function remove_no_comment_message() {
    let removeMessage = document.querySelector('#no-comment') 
    
    removeMessage.remove()
}

function append_new_comment(item) {
    if (document.querySelector('#no-comment')) {
        remove_no_comment_message()
    }  
    let commentsDiv = document.querySelector('#comment-section-list');
    let commentsFirstChild = commentsDiv.firstElementChild;
    let block = create_comment_block(item)
    commentsFirstChild.parentNode.insertBefore(block, commentsFirstChild.nextSibling)
}

function disable_comment_button() {
    let button = document.querySelector('#submit-comment-button')
    button.setAttribute('disabled', '')
}

function enable_comment_button() {
    let button = document.querySelector('#submit-comment-button')
    button.removeAttribute('disabled')
}

function check_for_error_message() {
    let alertBox = document.querySelector('.alert')
    if (alertBox) {
        let errorDiv = document.querySelector('#comment-error-div')
        errorDiv.removeChild(alertBox)
    }
}

async function submit_comment() {
    try {
        disable_comment_button()
        let textForComment = document.querySelector('#comment-textarea')
        check_for_error_message()
        // let bugIdUrl = window.location.pathname;
        // let start = bugId.indexOf('/');
        // let end = bugId.lastIndexOf('/');
        // let bugId = bugIdUrl.slice(start, end)

        const res = await fetch(`bug/${bugId}/comments`, {
            headers: {
                'Content-Type': 'application/json'
            },
            method: 'POST',
            body: JSON.stringify({
                'bugId': bugId, //need to remove hard code once understand jwt implementation
                'commenterUserId': '1',
                'commentText': textForComment.value
            })
        })
        if (res.status != 200){
            create_error_message("Make sure your comment is between 10 and 1000 characters!")
            enable_comment_button()
            const message = `Couldn't create comment! An error occured: ${res.status}`
            throw message
        }
        else {
            const commentJson = await res.json()
            append_new_comment(commentJson)
            enable_comment_button()
            textForComment.value = ''
        }
    }
    catch (err) {
        console.log(err)
    }
}
// SUBMIT COMMENT END

// FUNCTION CALL
get_comments_by_id()

let submitCommentButton = document.querySelector('#submit-comment-button')
submitCommentButton.addEventListener("click", submit_comment)

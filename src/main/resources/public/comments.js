
function populate_comments(commentList) {
    let commentsDiv = document.querySelector('#comment-section-list');
    console.log(JSON.stringify(commentList))
}

async function get_comments_by_id(){
    try {
        // let bugIdUrl = window.location.pathname;
        // let start = bugId.indexOf('/');
        // let end = bugId.lastIndexOf('/');
        // let bugId = bugIdUrl.slice(start, end)

        // const res = await fetch(`bug/${bugId}/comments`)
        const res = await fetch(`bug/2/comments`)
        const commentJson = await res.json()
        console.log(commentJson)
        if (res.status != 200){
            const message = `Couldn't obtain requests! An error occured: ${res.status}`
            throw message
        }
        populate_comments(commentJson)
    }
    catch(err) {
        console.log(err)
    }     
}
get_comments_by_id()
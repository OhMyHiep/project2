function validateLogin(username, password) {
    if (username.length > 0 && password.length > 0) {
        return true
    }
    return false
}

function createAlert(errorMessage) {
    if (!document.querySelector('.alert')) {
        let ErrorDiv = document.querySelector('#login-error-div');

        let alertElement = document.createElement("div")
        alertElement.setAttribute("class", "alert alert-danger")
        alertElement.setAttribute("role", "alert")
        alertElement.textContent = errorMessage
    
        ErrorDiv.append(alertElement);
    }
}

function removeAlert() {
    if (document.querySelector('.alert')) {
        let alert = document.querySelector('.alert')
        alert.remove()
    }
}

async function login() {
    let usernameText = document.querySelector("#username").value;
    let passwordText = document.querySelector("#password").value;

    validateLogin(usernameText, passwordText)

    const response = await fetch('/login', {
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify({
            'username': usernameText,
            'password': passwordText
        })
    })
    if (!response.ok) {
        createAlert("Invalid login!")
    }
    else {
        const loginJson = await response.json()
        console.log(JSON.stringify(loginJson));
    }
}

let button = document.addEventListener("click", login)
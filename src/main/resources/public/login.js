function validateLogin(username, password) {
    if (username.length > 0 && password.length > 0) {
        return true
    }
    return false
}

function createAlert(errorMessage) {
    if (!document.querySelector('.alert')) {
        let errorDiv = document.querySelector('#login-error-div');

        let alertElement = document.createElement("div")
        alertElement.setAttribute("class", "alert alert-danger")
        alertElement.setAttribute("role", "alert")
        alertElement.textContent = errorMessage
    
        errorDiv.append(alertElement);
    }
    else if (document.querySelector('.alert').textContent !== errorMessage) {
        let alertElement = document.querySelector('.alert')
        alertElement.textContent = errorMessage
    }
}

function removeAlert() {
    if (document.querySelector('.alert')) {
        let alert = document.querySelector('.alert')
        alert.remove()
    }
}

function saveAndRedirect(json) {
    localStorage.setItem('login', JSON.stringify(json))
    window.location.replace("http://localhost:8080/view")
}

async function login() {
    let usernameText = document.querySelector("#username").value;
    let passwordText = document.querySelector("#password").value;

    if (validateLogin(usernameText, passwordText)) {
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
            removeAlert()
            const loginJson = await response.json()
            console.log(JSON.stringify(loginJson))
            saveAndRedirect(loginJson)
        }
    }
    else {
        createAlert("Must not be empty!")
    }


}

document.querySelector("#submit").addEventListener("click", login)
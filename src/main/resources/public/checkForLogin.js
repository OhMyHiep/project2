
function logOut() {
    if (localStorage.getItem("login")) {
        localStorage.removeItem("login")
    }
}

function checkLogin() {
    if (localStorage.getItem("login") === null) {
        window.location.replace("http://localhost:8000/")
    }
    else if (localStorage.getItem("login") && window.location.href !== "http://localhost:8000/") {
        window.location.replace("http://localhost:8000/bugView")
    }
}

function checkPage() {
    if (window.location.href !== "http://localhost:8000/") {
        checkLogin()
    }
}

checkPage()

let logoutButton = document.querySelector("#logout").addEventListener("click", logOut)
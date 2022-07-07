
function logOut() {
    if (localStorage.getItem("login")) {
        localStorage.removeItem("login")
        window.location.replace('http://localhost:8080/')
    }
}

function checkLogin() {
    if (localStorage.getItem("login") === null) {
        window.location.replace("http://localhost:8080/")
    }
    else if (localStorage.getItem("login") && window.location.href === "http://localhost:8080/") {
        window.location.replace("http://localhost:8080/view")
    }
}

function checkPage() {
    if (window.location.href !== "http://localhost:8080/") {
        checkLogin()
    }
}

checkPage()

let logoutButton = document.querySelector("#logout").addEventListener("click", logOut)
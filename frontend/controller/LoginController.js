import { user, setToken, setUser, setUserRole, userRole } from "../db/data.js";

let isRegisterState = false;

$("main").hide();
// change register | login
let rotationDegree = 360
$(".register-login").click(function () {
    isRegisterState = !isRegisterState;
    if (isRegisterState) {
        $(".register-login").text("LOGIN");
        $(".log-reg-text").text("REGISTER");
        $('#login-desc').html("Already registered?")
    } else {
        $(".register-login").text("REGISTER");
        $(".log-reg-text").text("LOGIN");
        $('#login-desc').html("Don't have an account?")
    }
    $(".login-pane form")[0].reset();
    $(".login-pane").css("transform", `rotateY(${rotationDegree}deg)`)
    rotationDegree += 360;
})

$("#loginBtn").click(function () {

    // validate data
    let username = $("#loginUser").val();
    let password = $("#loginPassward").val();

    if (!username || !password) {
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Please provide valid creditiols!",
        });
        return;
    }

    if (isRegisterState) {
        registerUser(username, password);
    } else {
        loginUser(username, password);
    }
})

const loginUser = (username, password) => {
    const settings = {
        "url": "http://localhost:8080/api/v1/auth",
        "method": "PUT",
        "timeout": 0,
        "headers": {
            "Content-Type": "application/json"
        },
        "data": JSON.stringify({
            "email": encode(username),
            "password": encode(password)
        }),
    };

    $.ajax(settings).done(function (response) {
        handleUserLoginToSystem(response);
    });
}

const registerUser = (username, password) => {
    const settings = {
        "url": "http://localhost:8080/api/v1/auth",
        "method": "POST",
        "timeout": 0,
        "headers": {
            "Content-Type": "application/json"
        },
        "data": JSON.stringify({
            "email": encode(username),
            "password": encode(password)
        }),
    };

    $.ajax(settings).done(function (response) {
        handleUserLoginToSystem(response);
    });
}

// handle user login
const handleUserLoginToSystem = (response) => {
    setTimeout(() => {
        adminViewHandle(response.user.role);

        // hide login pane
        $("#loginPage").hide();
        $("main").show();
    }, 1000)

    // is user checked remember me
    if ($("#rememberMe").is(":checked")) {
        localStorage.setItem("user", JSON.stringify(response.user.employee));
        localStorage.setItem("role", response.user.role);
    } else {
        localStorage.removeItem("user");
        localStorage.removeItem("role");
    }

    // set user data
    setToken(response.token);
    setUser(response.user.employee);
    setUserRole(response.user.role);
    $(".userName").text(user.name);
    $(".userEmail").text(user.email);
    $(".user-img").css('background-image', `url(data:image/jpeg;base64,${user.profilePic})`);
}

// manage admin view
const adminViewHandle = (role) => {
    if (role === "ADMIN") {
        $("#dashboardBtn").click();
        $(".admin-view").show();
    } else {
        $("#salesBtn").click();
        $(".admin-view").hide();
    }
}

const encode = (text) => {
    // for(let i=0; i<10; i++){
    //     text = btoa(text);
    // }
    return text;
}
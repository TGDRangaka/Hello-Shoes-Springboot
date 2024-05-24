import { user, setToken, setUser, setUserRole, userRole, token } from "../db/data.js";
import { decode, encode, saveAlert, showErrorAlert, showSuccessAlert } from "../util/UtilMatter.js";

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
            "email": encode(username, 0),
            "password": encode(password, 0)
        }),
    };

    $.ajax(settings).done(function (response) {
        handleUserLoginToSystem(response);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showErrorAlert("Please provide a valid username and password");
        console.error("Error details:", textStatus, errorThrown, jqXHR);
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
            "email": encode(username, 0),
            "password": encode(password, 0)
        }),
    };

    $.ajax(settings).done(function (response) {
        handleUserLoginToSystem(response);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showErrorAlert("An error occurred while registering");
        console.error("Error details:", textStatus, errorThrown, jqXHR);
    });
}

// handle user login
const handleUserLoginToSystem = (response) => {
    showSuccessAlert("Logged in successfully!");

    setTimeout(() => {
        adminViewHandle(response.user.role);
        // hide login pane
        $("#loginPage").hide();
        $("main").show();
    }, 300)

    // is user checked remember me
    if ($("#rememberMe").is(":checked")) {
        localStorage.setItem("email", response.user.employee.email);
        localStorage.setItem("credentials", encode($("#loginPassward").val(), 3));
    }

    // set user data
    setToken(response.token);
    setUser(response.user.employee);
    setUserRole(response.user.role);
    $(".userName").text(user.name);
    $(".userEmail").text(user.email);
    $(".user-img").css('background-image', `url(data:image/jpeg;base64,${user.profilePic})`);

    // save login record
    saveAlert(`${response.user.employee.name} logged in`, 'INFO');
}

// manage admin view
const adminViewHandle = (role) => {
    if (role === "ADMIN") {
        $("#inventoryBtn").click();
        $(".admin-view").show();
    } else {
        $("#salesBtn").click();
        $(".admin-view").hide();
    }
}

// logout
$("#logoutBtn").click(() => {
    // clear user information from local storage

    Swal.fire({
        title: "Are you sure?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Log Out"
    }).then((result) => {
        if (result.isConfirmed) {
            location.reload();
            saveAlert(user.name + " Logged out", "INFO");
            localStorage.removeItem("email");
            localStorage.removeItem("credentials");
        }
    });

})

// check local storage have been stored
if (localStorage.getItem("email") && localStorage.getItem("credentials")) {
    loginUser(localStorage.getItem("email"), decode(localStorage.getItem("credentials"), 3));
}

// loginUser('rangaka@gmail.com', 'dilshan1234');
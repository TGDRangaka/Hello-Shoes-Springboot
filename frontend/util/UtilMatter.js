import { addAlert, token } from "../db/data.js";

// Name regex
const nameRegex = /^[A-Za-z.'-]+(?: [A-Za-z.'-]+)?$/;

// Address regex
const addressRegex = /^[A-Za-z0-9\/\s.,#'-]+$/;

// Phone number regex
const phoneRegex = /^(?:\+?94|0)?(?:\d{9})$/;

// Email regex
const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

// Money amount regex
const moneyRegex = /^\d+(\.\d{2})?$/;

export const getRegex = (field) => {
    switch (field) {
        case 'name':
            return nameRegex;
        case 'address':
            return addressRegex;
        case 'phone':
            return phoneRegex;
        case 'email':
            return emailRegex;
        case 'money':
            return moneyRegex;
        default:
            return null;
    }
}

// Validations set
let validIcon = '<i class="fa-solid fa-circle-check"></i>';
let invalidIcon = '<i class="fa-solid fa-circle-exclamation"></i>';

export const setAsValid = (element, message) => {
    $(element).parent().find(".validation").html(validIcon + message);
    $(element).parent().find(".validation").css("color", "rgb(12, 177, 12)");
    return true;
}

export const setAsInvalid = (element, message) => {
    $(element).parent().find(".validation").html(invalidIcon + message);
    $(element).parent().find(".validation").css("color", "rgb(255, 87, 87)");
    return false;
}

export const clearValidations = (form) => {
    $(form).find(".validation").html("");
}

export const getCategory = value => {
    switch (value) {
        case 'S': return 'Shoe';
        case 'FF': return 'Flip Flop';
        case 'H': return 'Heel';
        case 'F': return 'Flats';
        case 'W': return 'Wedges';
        case 'SL': return 'Slippers';
        case 'SD': return 'Sandals';
        case 'ACC': return 'Accessory';
        default: return null;
    }
}

// save alert and load alerts to notification panel
export const saveAlert = (message, type) => {

    if (type.toLowerCase() === 'warning') {
        setTimeout(() => {
            Swal.fire({
                icon: "warning",
                title: message
            });
        }, 2000)

    }

    var settings = {
        "url": "http://localhost:8080/api/v1/admin-panel/alert",
        "method": "POST",
        "timeout": 0,
        "headers": {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        "data": JSON.stringify({
            "message": message,
            "type": type.toUpperCase()
        }),
    };

    $.ajax(settings).done(function (response) {
        console.log(response);
        addAlert(response);
        $(".notifications").empty();
        for (let i = 0; i < response.length || i < 100; i++) {
            let alert = response[i];
            $(".notifications").append(`
            <div class="notification">
                <i class="fa-solid fa-${alert.type === 'GENERAL' ? 'thumbs-up'
                    : alert.type === 'WARNING' ? 'triangle-exclamation'
                        : 'info'
                } notifi-${alert.type.toLowerCase()}"></i>
                <div class="body">
                    <h6>${alert.message}</h6>
                    <span>${alert.date} - ${alert.time.substring(0, 8)}</span>
                </div>
            </div>
            `);
        }
    });
}

export const showSuccessAlert = (message) => {
    Swal.fire({
        position: "top-end",
        icon: "success",
        title: message,
        showConfirmButton: false,
        timer: 2000
    });
}

export const showErrorAlert = (message) => {
    Swal.fire({
        icon: "error",
        title: "Oops...",
        text: message
    });
}

export const showWarningAlert = (message) => {
    Swal.fire({
        icon: "warning",
        title: "Oops...",
        text: message
    });
}

// password
let pwHidden = 'fa-regular fa-eye-slash';
let pwShow = 'fa-regular fa-eye';

$(".password-input").append('<i class="fa-regular fa-eye"></i>');

$(".password-input").on('click', 'i', function () {
    if ($(this).hasClass(pwHidden)) {
        $(this).removeClass(pwHidden);
        $(this).addClass(pwShow);
        $(this).parent().find('input').attr('type', 'password');
    } else {
        $(this).removeClass(pwShow);
        $(this).addClass(pwHidden);
        $(this).parent().find('input').attr('type', 'text');
    }
})

// copy text
$("body").on('click', '.fa-copy', function () {
    let text = $(this).parent().text().trim();

    // Create a temporary textarea element
    let tempInput = $("<textarea>");
    $("body").append(tempInput);
    tempInput.val(text).select();

    try {
        document.execCommand("copy");
        // alert("Copied the text: " + text);
        Swal.fire({
            position: "top-end",
            icon: "success",
            title: "Copied the text: " + text,
            showConfirmButton: false,
            timer: 1500
        });
    } catch (err) {
        console.error("Failed to copy text: ", err);
        // alert("Failed to copy text");
        Swal.fire({
            position: "top-end",
            icon: "error",
            title: "Failed to copy text",
            showConfirmButton: false,
            timer: 1000
        });
    }

    // Remove the temporary element
    tempInput.remove();
});

export function encode(text, depth){
    while(depth-- > 0){
        text = btoa(text);
    }
    return text;
}

export function decode(text, depth){
    while(depth-- > 0){
        text = atob(text);
    }
    return text;
}
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
        Swal.fire({
            position: "top-end",
            icon: "warning",
            title: message,
            showConfirmButton: false,
            timer: 2500
        });
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
        response.map(alert => {
            $(".notifications").append(`
            <div class="notification">
                <i class="fa-solid fa-${alert.type === 'GENERAL' ? 'thumbs-up'
                    : alert.type === 'WARNING' ? 'triangle-exclamation'
                        : 'info'
                } notifi-${alert.type.toLowerCase()}"></i>
                <div class="body">
                    <h6>${alert.message}</h6>
                    <span>${alert.date}, ${alert.time}</span>
                </div>
            </div>
            `);
        });
    });
}

// export const getAllAlerts = () => {
//     var settings = {
//         "url": "http://localhost:8080/api/v1/admin-panel/alert",
//         "method": "GET",
//         "timeout": 0,
//         "headers": {
//           "Authorization": "Bearer " + token
//         },
//       };

//       $.ajax(settings).done(function (response) {
//         console.log(response);
//       });
// }

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
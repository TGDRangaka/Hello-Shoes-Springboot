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
    switch(field){
        case 'name':
            return nameRegex;
        case 'address':
            return addressRegex;
        case 'phone':
            return phoneRegex;
        case 'email':
            return emailRegex;
        case'money':
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
    switch(value){
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
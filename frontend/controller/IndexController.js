
import { token, setToken } from "../db/data.js";

$("#dashboardBtn").on('click', function(){
    setBtnActive("#dashboard", this);
})

$("#salesBtn").on('click', function(){
    setBtnActive("#sales", this);
})

$("#salesBtn").dblclick(function() {
    setBtnActive("#salesHistory", this);
})

$("#inventoryBtn").on('click', function(){
    setBtnActive("#inventory", this);
})

$("#inventoryBtn").dblclick(function() {
    setBtnActive("#addProduct", this);
})

$("#customerBtn").on('click', function(){
    setBtnActive("#customer", this);
})

$("#employeesBtn").on('click', function(){
    setBtnActive("#employee", this);
})

$("#suppliersBtn").on('click', function(){
    setBtnActive("#supplier", this);
})

$("#notificationsBtn").on('click', function(){
    setBtnActive("#notification", this);
})


$("#addNewProductBtn").on('click', ()=>{
    setBtnActive("#addProduct", "#inventoryBtn")
})

const hideAllSections = () => {
    $("section").hide();
    $(".nav-buttons > button").css({
        "color": "rgba(255, 255, 255, 0.7)",
        "scale": "1"
    })
}

const setBtnActive = (element, btn) => {
    hideAllSections();
    $(btn).css({
        "color": "white",
        "scale": "1.2"
    })
    $(element).show();
}

$("#employeesBtn").click();

$("#loginBtn").click(()=>{
    const settings = {
        "url": "http://localhost:8080/api/v1/auth",
        "method": "PUT",
        "timeout": 0,
        "headers": {
            "Content-Type": "application/json"
        },
        "data": JSON.stringify({
            "email": "dilshan@example.com",
            "password": "dilshan1234"
        }),
    };
      
    $.ajax(settings).done(function (response) {
        setToken(response.token);
        console.log(token);
    });
})
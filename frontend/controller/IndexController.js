
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

$("#customerBtn").dblclick(function() {
    setBtnActive("#addCustomer", this);
})

$("#employeesBtn").on('click', function(){
    setBtnActive("#employee", this);
})

$("#employeesBtn").on('dblclick', function(){
    setBtnActive("#addEmployee", this);
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
    // $(".nav-buttons > button").css({
    //     "color": "#929392",
    //     "scale": "1"
    // })
}

const setBtnActive = (element, btn) => {
    hideAllSections();
    // $(btn).css({
    //     "color": "#929392",
    //     "scale": "1.2"
    // })
    $(element).show();
}

$("#inventoryBtn").dblclick();
// $("#addProductBtn").click();

$("#loginBtn").click(()=>{
    const settings = {
        "url": "http://localhost:8080/api/v1/auth",
        "method": "PUT",
        "timeout": 0,
        "headers": {
            "Content-Type": "application/json"
        },
        "data": JSON.stringify({
            "email": encode("dilshan@gmail.com"),
            "password": encode("dilshan1234")
        }),
    };
      
    $.ajax(settings).done(function (response) {
        if(response.token) alert("Token has been received!")
        setToken(response.token);
        let img = response.user.profilePic;
        let imgObj = new Image();

        // imgObj.src = img;
        // // imgObj.src = "data:image;base64," + img;

        // imgObj.onload = function() {
        //     console.log("Image loaded successfully");
        //     // Access image properties and perform actions as needed
        //     console.log("Image width: " + this.width);
        //     console.log("Image height: " + this.height);
        // };
        // imgObj.onerror = function() {
        //     console.error("Error loading image");
        // };
    });
})

const encode = text =>{
    // for(let i=0; i<10; i++){
    //     text = btoa(text);
    // }
    return text;
}
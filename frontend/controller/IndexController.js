
import { user, setUser, setToken } from "../db/data.js";

$("#dashboardBtn").on('click', function(){
    setBtnActive("#dashboard", this);
    $("#sectionTitle").text("Dashboard")
})

$("#salesBtn").on('click', function(){
    setBtnActive("#sales", this);
    $("#sectionTitle").text("New Purchase")
})

$("#salesHistoryBtn").click(function() {
    setBtnActive("#salesHistory", this);
    $("#sectionTitle").text("Sales History")
})

$("#inventoryBtn").on('click', function(){
    setBtnActive("#inventory", this);
    $("#sectionTitle").text("Inventory")
})

$("#inventoryFormBtn").click(function() {
    setBtnActive("#addProduct", this);
    $("#sectionTitle").text("New Product")
})

$("#customerBtn").on('click', function(){
    setBtnActive("#customer", this);
    $("#sectionTitle").text("Customers")
})

$("#customerFormBtn").on('click', function() {
    setBtnActive("#customer", this);
    $("#sectionTitle").text("Customer Form")
})

$("#employeesBtn").on('click', function(){
    setBtnActive("#employee", this);
    $("#sectionTitle").text("Employees")
})

$("#employeeFormBtn").on('click', function(){
    setBtnActive("#addEmployee", this);
    $("#sectionTitle").text("Employee Form")
})

$("#suppliersBtn").on('click', function(){
    setBtnActive("#supplier", this);
    $("#sectionTitle").text("Supplier Form")
})

$("#supplierFormBtn").on('click', function(){
    setBtnActive("#supplierForm", this);
    $("#sectionTitle").text("Supplier Form")
})

$("#resupplysBtn").on('click', function(){
    setBtnActive("#resupply", this);
    $("#sectionTitle").text("Resupplies")
})

$("#resupplysBtn").on('dblclick', function(){
    setBtnActive("#resupplyData", this);
    $("#sectionTitle").text("Resupply History")
})

$("#notificationsBtn").on('click', function(){
    setBtnActive("#notification", this);
    $("#sectionTitle").text("Notifications")
})

$("#addNewProductBtn").on('click', ()=>{
    setBtnActive("#addProduct", "#inventoryBtn")
    $("#sectionTitle").text("New Product")
})

$("#refundBtn").on('click', ()=>{
    setBtnActive("#refund", "#refundBtn")
    $("#sectionTitle").text("Refund Item")
})

$("#loginBtn").click(()=>{
    setBtnActive("#profile", "#loginBtn")
    $("#sectionTitle").text("Profile")
})

const hideAllSections = () => {
    $("section").hide();
    $(".nav-buttons button").addClass('nav-button-inactive')
}

const setBtnActive = (element, btn) => {
    hideAllSections();
    $(btn).removeClass('nav-button-inactive'); 
    $(btn).addClass('nav-button-active');
    $(element).show();
}

// $("#inventoryBtn").dblclick();
$("#employeesBtn").click();

const encode = text =>{
    // for(let i=0; i<10; i++){
    //     text = btoa(text);
    // }
    return text;
}

const settings = {
    "url": "http://localhost:8080/api/v1/auth",
    "method": "PUT",
    "timeout": 0,
    "headers": {
        "Content-Type": "application/json"
    },
    "data": JSON.stringify({
        "email": encode("rangaka@gmail.com"),
        "password": encode("dilshan1234")
    }),
};
  
$.ajax(settings).done(function (response) {
    if(response.token) console.log("Token has been received!")
    setToken(response.token);
    setUser(response.user.employee);
    $(".userName").text(user.name);
    $(".userEmail").text(user.email);
    // let profilePic = user.profilePic.replace(/(\r\n|\n|\r)/gm, "")
    $(".user-img").css('background-image', `url(data:image/jpeg;base64,${user.profilePic})`);
});

let isBtnsDropdowned = false;
$("#btnsDropdown").click(function(){
    if(!isBtnsDropdowned){
        $(".formBtnsDown .body").css({
            'height': '200px',
            'opacity': '1',
        })
        $(this).find('.down').css('rotate', '180deg')
        isBtnsDropdowned = true;
    }else{
        $(".formBtnsDown .body").css({
            'height': '0px',
            'opacity': '0',
        })
        $(this).find('.down').css('rotate', '0deg')
        isBtnsDropdowned = false;
    }
})

// $(".notification-pane").hide();
$("#notificationBtn").click(()=>{
    $(".notification-pane").toggle();
})

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

$("#salesBtn").click();
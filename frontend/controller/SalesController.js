$("#paymentPanel").hide();
let isPaymentPanelOpened = false;
let paymentMethod = "cash";

$("#payBtn").on("click", ()=>{
    paymentMethod = $('input[name="paymentMethod"]:checked').val();
    if(paymentMethod !== 'card' && !isPaymentPanelOpened) return;
    if(!isPaymentPanelOpened){
        $("#cardPaymentPopup").css("display", "flex");
        isPaymentPanelOpened = true;
    }
})

// $("#payBtn").click();

$("#cardPayment, #cashPayment").on("click", ()=>{
    paymentMethod = $('input[name="paymentMethod"]:checked').val();
    if (paymentMethod === "cash") {
        $("#cashAndBalance").show();
        isPaymentPanelOpened && $("#payBtn").click();
    } else {
        $("#cashAndBalance").hide();
    }
});

$("#saleConfirmBtn").on("click", ()=> {
    isPaymentPanelOpened && $("#cardPaymentPopup").css("display", "none");
    isPaymentPanelOpened = false;
})
import {token} from '../db/data.js';
import {Sale} from '../model/Sale.js';

$("#paymentPanel").hide();
let isPaymentPanelOpened = false;
let paymentMethod = "cash";
let inventoryItems = [];
let allCustomers = [];
let selectedItems = [];
let allSoldItems = [];
let subTotal = 0;

// on section load
$("#salesBtn").click(function(){
    getAllItems();
    getAllCustomers();
    getSoldItems();
})

// api
const getAllItems = ()=>{
    var settings = {
        "url": "http://localhost:8080/api/v1/inventory/available",
        "method": "GET",
        "timeout": 0,
        "headers": {
          "Authorization": "Bearer " + token
        },
      };
      
      $.ajax(settings).done(function (response) {
        inventoryItems = response;
        inventoryItems.map(item => {
            let img = new Image();
            img.src = item.itemImage.image
            item.itemImage = img;
        })
      });
}

// api
const getAllCustomers = ()=>{
    var settings = {
        "url": "http://localhost:8080/api/v1/customer",
        "method": "GET",
        "timeout": 0,
        "headers": {
          "Authorization": "Bearer " + token
        },
      };
      
      $.ajax(settings).done(function (response) {
        console.log(response);
        allCustomers = response;
        allCustomers.map(customer => {
            $("#customerDataList").append(`
                <option value="${customer.name}">${customer.email}</option>
            `)
        })
      });
}

// payment api call
$("#salePayBtn, #saleConfirmBtn").click(()=>{
    let sale = new Sale(
        "", subTotal, paymentMethod.toUpperCase(),
        subTotal >= 800 ? 1 : 0, null,
        {name: $("#saleCustomerName").val(), email: $("#saleCustomerEmail").val()},
        []
    )
    selectedItems.map(item => {
        sale.saleItems.push({
            saleItemId: {item: {inventoryCode: item.inventoryCode}},
            qty: item.qty,
            unitPrice: item.item.unitPriceSale
        });
    })

    let settings = {
        "url": "http://localhost:8080/api/v1/sale",
        "method": "POST",
        "timeout": 0,
        "headers": {
          "Authorization": "Bearer " + token,
          "Content-Type": "application/json"
        },
        "data": JSON.stringify(sale)
    };

    $.ajax(settings).done(function (response){
        console.log(response);
    }).fail((error)=>{
        console.log(error);
    })

})

const getSoldItems = () => {
    var settings = {
        "url": "http://localhost:8080/api/v1/sale",
        "method": "GET",
        "timeout": 0,
        "headers": {
          "Authorization": "Bearer " + token
        },
      };
      
      $.ajax(settings).done(function (response) {
        console.log(response);
        allSoldItems = response;
        loadSoldItemsTable(allSoldItems);
      });
}

const loadSoldItemsTable = (soldItems) => {
    $("#saleItemsAccordion").empty();

    soldItems.map((sale, i) => {
        let saleItemsRows = "";
        sale.saleItems.map((saleItem, i) => {
            saleItemsRows += `
                <tr class="align-middle">
                    <td>${i+1}</td>
                    <td>${saleItem.saleItemId.item.inventoryCode}</td>
                    <td>${saleItem.unitPrice}</td>
                    <td>${saleItem.qty}</td>
                    <td>${saleItem.qty * saleItem.unitPrice}</td>
                </tr>
            `
        })

        // ToDo
        $("#saleItemsAccordion").append(`
        <div class="accordion-item">
            <h2 class="accordion-header">
            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseSale${i}" aria-expanded="false" aria-controls="collapseSale${i}">
                
                <div class="sale-accordion container-fluid w-100">
                    <div class="row">
                        <label class="col-1">${i+1}</label>
                        <label class="col-2">${sale.orderDate}</label>
                        <label class="col-2">${sale.paymentMethod}</label>
                        <label class="col-2">${sale.totalPrice}</label>
                        <label class="col-2">${sale.customer.name}</label>
                        <label class="col-2">${sale.employee.name}</label>
                        <label class="col-1">${sale.saleItems.length}</label>
                    </div>
                </div>

            </button>
            </h2>
            <div id="collapseSale${i}" class="accordion-collapse collapse" data-bs-parent="#accordionFlushExample">
            <div class="accordion-body container-fluid">
                <table class="table table-bordered bg-light">
                    <thead>
                        <th class="text-center">#</th>
                        <th>Item Name</th>
                        <th>Unit Price</th>
                        <th>Qty</th>
                        <th>Total</th>
                    </thead>
                    <tbody>
                        ${saleItemsRows}
                    </tbody>
                </table>
            </div>
            </div>
        </div>
        `)
    })
}

$("#payBtn").on("click", ()=>{
    paymentMethod = $('input[name="paymentMethod"]:checked').val();
    if(paymentMethod !== 'card' && !isPaymentPanelOpened) return;
    if(!isPaymentPanelOpened){
        $("#cardPaymentPopup").css("display", "flex");
        isPaymentPanelOpened = true;
    }
})

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

$("#saleItemsDetails").on('change', '#saleCustomerName', function(){
    let input = $(this).val();
    console.log(input);
    allCustomers.filter(customer => {
        if(customer.name === input){
            $("#saleCustomerEmail").val(customer.email);
        }
    })
})


$("#saleItemCode").on('input',function(){
    let input = $(this).val().toUpperCase();
        $("#suggest-items-list").empty();
    if(input.length > 1){
        $("#suggest-items-list").show();

        inventoryItems.map(item => {
            (item.item.itemCode.indexOf(input) >= 0) && addSuggestItemToList(item);
        })

    }else{
        $("#suggest-items-list").hide();
    }
})

$("#saleCash").on('input', function() {
    let cash = parseFloat($(this).val());
    $("#saleCashBalance").text("Rs." + parseFloat(cash - subTotal));
})

const addSuggestItemToList = (item) => {
    $("#suggest-items-list").append(`
    <div id=${item.inventoryCode} class="suggest-item-card d-flex justify-content-between align-items-center">
        <div class="d-flex h-100 gap-3">
            <img src="${item.itemImage.src}" alt="shoe" class="h-100">
            <div class="d-flex flex-column justify-content-center quicksand-thin">
                <h5 class="quicksand-bold m-0">${item.item.itemCode}</h5>
                <label>C: ${item.colors}</label>
                <label>S: ${item.size}</label>
            </div>
        </div>
        <div class="h-100 d-flex flex-column align-items-end justify-content-center quicksand-thin">
            <h5>Rs.${item.item.unitPriceSale}</h5>
            <label>Stock: ${item.currentQty}</label>
        </div>
    </div>
    `)
}

$("#suggest-items-list").on("click", ".suggest-item-card", function(){
    let selectedItemId = $(this).attr('id')
    $("#suggest-items-list").empty();
    $("#suggest-items-list").hide();
    $("#saleItemCode").val(selectedItemId)
})

$("#addSuggestItemBtn").click(()=> {
    let itemCode = $("#saleItemCode").val();
    let itemsQty = parseInt($("#itemsQty").val());
    // if(!itemCode || !itemsQty || itemsQty <= 0) return;

    inventoryItems.filter(item => {
        if(item.inventoryCode === itemCode) {
            selectedItems.push({...item, qty: itemsQty});
            $("#saleItemCode").val("");
            $("#itemsQty").val(1);
        }
    });

    loadTable();
})

// load table
const loadTable = () => {
    subTotal = 0;
    $("#saleItemTableBody").empty();
    selectedItems.map((item, i) => {
        $("#saleItemTableBody").append(`
            <tr id="${item.inventoryCode}Tr" class="align-middle">
                <td>
                    <div class="table-img">
                        <img src="${item.itemImage.src}" alt="shoe">
                    </div>
                </td>
                <td>
                    <div class="">
                        <label class="quicksand-bold m-0 d-block">${item.item.description}</label>
                        <label class="label">${item.inventoryCode}</label>
                    </div>
                </td>
                <td>${item.item.unitPriceSale}</td>
                <td>${item.qty}</td>
                <td>${item.item.unitPriceSale * item.qty}</td>
                <td class="text-center"><i data-index="${i}" class="fa-solid fa-trash col-1 text-center"></i></td>
            </tr>
        `)
        subTotal += item.item.unitPriceSale * item.qty;
        $("#saleSubTotal").text(subTotal.toFixed(2));
    })
}

// remove row by deleting
$("#saleItemTableBody").on('click', 'i', function(){
    let index = $(this).data('index');
    selectedItems.splice(index, 1);
    loadTable();
})
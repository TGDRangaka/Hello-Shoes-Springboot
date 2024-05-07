import { token, setToken } from "../db/data.js";
import { Refund } from "../model/Refund.js";

let allRefunds = [];
let orderItems = [];
let selectedOrderItems = [];
let refundSubTotal = 0;
let orderId = null;

$("#refundsBtn").on('click', function(){
})

$("#searchOrderBtn").click(()=> {
    orderId = $("#searchOrderId").val();
    console.log(orderId);

    orderItems = [];
    selectedOrderItems = [];
    refundSubTotal = 0;
    $("#refundSubTotal").text(refundSubTotal);
    $("#refundItems").empty();
    $("#refundOrderItemsTbody").empty();
    getOrderItems(orderId);
})

$("#refundSubmitBtn").click(()=> {
    let refunds = [];
    selectedOrderItems.map(refundItem => {
        let refund = new Refund();
        refund.description = refundItem.description;
        refund.qty = refundItem.refundQty;
        refund.refundTotal = refundItem.total;
        refund.saleItem = {
            saleItemId: {
                sale: {orderId: orderId},
                item: {inventoryCode: refundItem.inventoryCode}
            }
        }
        refunds.push(refund);
    })

    submitRefund(refunds);
})

const submitRefund = (refunds) => {
    var settings = {
        "url": "http://localhost:8080/api/v1/refund",
        "method": "POST",
        "timeout": 0,
        "headers": {
          "Content-Type": "application/json",
          "Authorization": "Bearer " + token
        },
        "data": JSON.stringify(refunds),
      };
      
      $.ajax(settings).done(function (response) {
        console.log(response);
        $("#refundCancelBtn").click();
      });
}

const getOrderItems = (orderId) => {
    // get api
    var settings = {
        "url": "http://localhost:8080/api/v1/refund/" + orderId,
        "method": "GET",
        "timeout": 0,
        "headers": {
          "Authorization": "Bearer " + token
        },
      };
      
      $.ajax(settings).done(function (response) {
        console.log(response);
    // load Order items to table
        orderItems = response;
        selectedOrderItems = [];
        refundSubTotal = 0;
        addOrderItemsToTable(orderItems);
      });
}

const addOrderItemsToTable = (orderItems) => {
    $("#refundItems").empty();
    $("#refundOrderItemsTbody").empty();

    orderItems.map((orderItem, i) => {
        $("#refundOrderItemsTbody").append(`
        <tr class="bg-danger">
            <td class="text-center">${i+1}</td>
            <td class="table-img">
                <img src="${orderItem.saleItemId.item.itemImage.image}" alt="" class="img-fluid">
            </td>
            <td>${orderItem.saleItemId.item.inventoryCode}</td>
            <td class="text-center">${orderItem.qty}</td>
            <td class="text-center">${orderItem.unitPrice}</td>
            <td class="text-center">
                <input data-index="${i}" type="checkbox" class="form-check-input">
            </td>
        </tr>
        `);
    })
}

$("#refundOrderItemsTbody").on('change', 'input:checkbox', function(){
    let index = $(this).data('index');
    let isChecked = $(this).prop('checked');
    console.log(index, isChecked);

    // add to selectedOrderItems array if true, otherwise remove
    // addRefundItemInput();
    if(isChecked){
        let saleItem = orderItems[index];
        let refundItem = {
            inventoryCode: saleItem.saleItemId.item.inventoryCode,
            description: "",
            refundQty: 1,
            maxQty: saleItem.qty,
            unitPrice: saleItem.unitPrice,
            total: saleItem.unitPrice
        }
        selectedOrderItems.push(refundItem);
        addRefundItemInput(refundItem, selectedOrderItems.length -1);
    }else{
        let refundItem = selectedOrderItems.find(item => item.inventoryCode === orderItems[index].saleItemId.item.inventoryCode);
        selectedOrderItems = selectedOrderItems.filter(item => item.inventoryCode!== refundItem.inventoryCode);
        
        $("#refundItems").empty();
        if(selectedOrderItems.length > 0){
                selectedOrderItems.map((item, i) => {
                console.log(item);
                addRefundItemInput(item, i);
            })
        }else{
            refundSubTotal = 0;
            $("#refundSubTotal").text(refundSubTotal);
        }
    }
})

const addRefundItemInput = (refundItem, i) => {
    calculateRefundSubTotal();
    console.log(refundItem);

    $("#refundItems").append(`
    <div class="row mb-3">
        <h5>Refund Product #${refundItem.inventoryCode}</h5>
        <div class="col-4 d-flex flex-column justify-content-around">
            <p>Please briefly describe reason for refund.</p>
            <p>Enter the number of items being returned.</p>
        </div>
        <div class="col mb-3">
            <div class="input">
                <label for="refundDescription${i}">Description</label>
                <textarea data-index="${i}" name="Description" id="refundDescription${i}">${refundItem.description}</textarea>
            </div>
            <div class="row mt-3">
                <div class="col input">
                        <label for="refundQty">Qty</label>
                        <input data-index="${i}" class="refundQty" type="number" name="refundQty" id="refundQty" min="1" max="${refundItem.maxQty}" value="${refundItem.refundQty}">
                </div>
                <div class="col input">
                    <label>Sold Price (Rs)</label>
                    <input type="number" id="refundItemUnitPrice${i}" value="${refundItem.unitPrice}" disabled>
                </div>
                <div class="col input">
                    <label>Total (Rs)</label>
                    <input type="number" name="" id="refundItemTotal${i}" value="${refundItem.total}" disabled>
                </div>
            </div>
        </div>
        <hr>
    </div>
    `);
}

$("#refundItems").on("input", 'textarea', function(){
    let index = $(this).data('index');
    let val = $(this).val();
    selectedOrderItems[index].description = val;
})

$("#refundItems").on('input', '.refundQty', function(){
    let index = $(this).data('index');
    let newQty = parseInt($(this).val());
    let unitPrice = parseInt($("#refundItemUnitPrice" + index).val());
    let total = unitPrice * newQty;
    parseInt($("#refundItemTotal" + index).val(total));
    selectedOrderItems[index].refundQty = newQty;
    selectedOrderItems[index].total = total;

    calculateRefundSubTotal();
})

const calculateRefundSubTotal = () => {
    refundSubTotal = 0;
    selectedOrderItems.map(refundItem => {
        refundSubTotal += refundItem.total;
    })
    $("#refundSubTotal").text(refundSubTotal);
}

$("#refundCancelBtn").click(()=>{
    orderItems = [];
    selectedOrderItems = [];
    refundSubTotal = 0;
    $("#refundSubTotal").text(refundSubTotal);
    $("#refundItems").empty();
    $("#refundOrderItemsTbody").empty();
})
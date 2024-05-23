import { token, setToken, user } from "../db/data.js";
import { Refund } from "../model/Refund.js";
import { clearValidations, setAsInvalid, setAsValid, showErrorAlert, showSuccessAlert, showWarningAlert } from "../util/UtilMatter.js";

let allRefunds = [];
let orderItems = [];
let selectedOrderItems = [];
let refundSubTotal = 0;
let orderId = null;

$("#refundsBtn").on('click', function () {
})

$("#refundHistoryBtn").click(function () {
    getAllRefunds();
})

$("#searchOrderBtn").click(() => {
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

$("#refundSubmitBtn").click(() => {
    $("#verificationPopUp").show();
})

$("#verificationCancelBtn").click(() => {
    $("#verificationPopUp").hide();
    clearValidations("#verificationPopUp form");
    $("#verificationUsername").val("");
    $("#verificationPassword").val("");
})

$("#verificationSubmitBtn").click(() => {
    let username = $("#verificationUsername").val();
    let password = $("#verificationPassword").val();
    if ((username ? setAsValid("#verificationUsername", 'Looks Good!') : setAsInvalid("#verificationUsername", 'Please enter your username'))
        & (password ? setAsValid("#verificationPassword", 'Looks Good!') : setAsInvalid("#verificationPassword", 'Please enter your password'))) {
        var settings = {
            "url": `http://localhost:8080/api/v1/employee/validate/${username}/${password}`,
            "method": "GET",
            "timeout": 0,
            "headers": {
                "Authorization": "Bearer " + token
            },
        };

        $.ajax(settings).done(function (response) {
            // console.log(response);

            $("#verificationUsername").val("");
            $("#verificationPassword").val("");
            if(response){
                $("#verificationPopUp").hide();
                clearValidations("#verificationPopUp form");
                let refunds = getRefundDetails();
                refunds.length > 0 && submitRefund(refunds);
            }
        });

    }
})

const getRefundDetails = () => {
    let refunds = [];
    selectedOrderItems.map(refundItem => {
        let refund = new Refund();
        if(!refundItem.description){
            showWarningAlert("Please provide a description for order item: " + refundItem.inventoryCode);
            return [];
        }
        refund.description = refundItem.description;
        refund.qty = refundItem.refundQty;
        refund.refundTotal = refundItem.total;
        refund.saleItem = {
            saleItemId: {
                sale: { orderId: orderId },
                item: { inventoryCode: refundItem.inventoryCode }
            }
        }
        refunds.push(refund);
    })
    return refunds;
}

const getAllRefunds = () => {
    var settings = {
        "url": "http://localhost:8080/api/v1/refund",
        "method": "GET",
        "timeout": 0,
        "headers": {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
    };

    $.ajax(settings).done(function (response) {
        console.log(response);
        allRefunds = response;
        loadAllRefunds(allRefunds);
    });
}

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
        showSuccessAlert("Refund recorded successfully")
        $("#refundCancelBtn").click();
        $("#refundHistoryBtn").click();
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showErrorAlert("An error occurred while refunding the items");
        console.error("Error details:", textStatus, errorThrown, jqXHR);
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
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showErrorAlert("An error occurred while searching for order");
        console.error("Error details:", textStatus, errorThrown, jqXHR);
    });
}

const loadAllRefunds = (allRefunds) => {
    // sort
    if ($("#refundSortSelect").val() !== 'NONE') {
        sortRefundTable();
    }

    $("#refundsTableBody").empty();
    let rowCount = 0;
    allRefunds.map((refund, i) => {
        // filter
        if (!isInSearchedKeyword(refund)) return;

        $("#refundsTableBody").append(`
        <tr class="align-middle">
            <td>${++rowCount}</td>
            <td class="table-img">
                <div class="bg-img" style="background-image: url(${refund.saleItem.saleItemId.item.itemImage.image})"></div>
            </td>
            <td><i class="fa-regular fa-copy"></i>${refund.saleItem.saleItemId.sale.orderId}</td>
            <td><i class="fa-regular fa-copy"></i>${refund.saleItem.saleItemId.item.inventoryCode}</td>
            <td>${refund.qty}</td>
            <td>${refund.description}</td>
            <td class="text-end">${refund.refundTotal}</td>
            <td class="text-end">${refund.refundDate}</td>
        </tr>
        `);
    })
}

const addOrderItemsToTable = (orderItems) => {
    $("#refundItems").empty();
    $("#refundOrderItemsTbody").empty();

    orderItems.map((orderItem, i) => {
        $("#refundOrderItemsTbody").append(`
        <tr class="bg-danger">
            <td class="text-center">${i + 1}</td>
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

$("#refundOrderItemsTbody").on('change', 'input:checkbox', function () {
    let index = $(this).data('index');
    let isChecked = $(this).prop('checked');
    console.log(index, isChecked);

    // add to selectedOrderItems array if true, otherwise remove
    // addRefundItemInput();
    if (isChecked) {
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
        addRefundItemInput(refundItem, selectedOrderItems.length - 1);
    } else {
        let refundItem = selectedOrderItems.find(item => item.inventoryCode === orderItems[index].saleItemId.item.inventoryCode);
        selectedOrderItems = selectedOrderItems.filter(item => item.inventoryCode !== refundItem.inventoryCode);

        $("#refundItems").empty();
        if (selectedOrderItems.length > 0) {
            selectedOrderItems.map((item, i) => {
                console.log(item);
                addRefundItemInput(item, i);
            })
        } else {
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
                <textarea class="p-2" data-index="${i}" name="Description" id="refundDescription${i}">${refundItem.description}</textarea>
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

$("#refundItems").on("input", 'textarea', function () {
    let index = $(this).data('index');
    let val = $(this).val();
    selectedOrderItems[index].description = val;
})

$("#refundItems").on('input', '.refundQty', function () {
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

$("#refundCancelBtn").click(() => {
    orderItems = [];
    selectedOrderItems = [];
    refundSubTotal = 0;
    $("#refundSubTotal").text(refundSubTotal);
    $("#refundItems").empty();
    $("#refundOrderItemsTbody").empty();
})

$("#refundFormBtn").click(() => {
    $("#refundBtn").click();
})

// sort
$("#refundHistory header select").on('change', () => {
    loadAllRefunds(allRefunds);
})

const sortRefundTable = () => {
    let sortBy = $("#refundSortSelect").val();

    if (sortBy === 'LOW') {
        allRefunds.sort((a, b) => a.refundTotal - b.refundTotal);
    } else if (sortBy === 'HIGH') {
        allRefunds.sort((a, b) => b.refundTotal - a.refundTotal);
    } else if (sortBy === 'Latest') {
        allRefunds.sort((a, b) => new Date(b.refundDate) - new Date(a.refundDate));
    } else if (sortBy === 'Oldest') {
        allRefunds.sort((a, b) => new Date(a.refundDate) - new Date(b.refundDate));
    }
}

// search
$("#refundSearchBtn").click(() => {
    loadAllRefunds(allRefunds);
})

const isInSearchedKeyword = (refund) => {
    let keyword = $("#refundSearchInput").val().toLowerCase();

    return keyword === '' ? true
        : refund.saleItem.saleItemId.item.inventoryCode.toLowerCase().includes(keyword)
        || refund.saleItem.saleItemId.sale.orderId.toLowerCase().includes(keyword);
}
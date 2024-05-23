import { token } from '../db/data.js';
import { Sale } from '../model/Sale.js';
import { setAsInvalid, setAsValid, clearValidations, saveAlert, showSuccessAlert, showErrorAlert } from '../util/UtilMatter.js';

$("#paymentPanel").hide();
let isPaymentPanelOpened = false;
let paymentMethod = "cash";
let inventoryItems = [];
let allCustomers = [];
let allSoldItems = [];
let subTotal = 0;
let selectedItems = [];
let selectedItemId = null;
let selectedItemByColors = [];

// on section load
$("#salesBtn").click(function () {
    getAllItems();
    getAllCustomers();
})

$("#salesHistoryBtn").click(() => {
    getSoldItems();
})

// api
const getAllItems = () => {
    var settings = {
        "url": "http://localhost:8080/api/v1/item",
        "method": "GET",
        "timeout": 0,
        "headers": {
            "Authorization": "Bearer " + token
        },
    };

    $.ajax(settings).done(function (response) {
        inventoryItems = response;
        $(".choosItems").empty();
        inventoryItems.map(item => addSuggestItemToList(item));
    });
}

// api
const getAllCustomers = () => {
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
        $("#customerDataList").empty();
        allCustomers.map((customer, i) => {
            $("#customerDataList").append(`
                <option value="${customer.email}">${customer.name}</option>
            `)
        })
    });
}

const saveSale = () => {
    let sale = new Sale(
        "", subTotal, paymentMethod.toUpperCase(),
        subTotal >= 800 ? 1 : 0, null,
        { name: $("#saleCustomerName").val(), email: $("#saleCustomerSearchInput").val() },
        []
    )
    selectedItems.map(item => {
        sale.saleItems.push({
            saleItemId: { item: { inventoryCode: item.inventoryCode } },
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

    $.ajax(settings).done(function (response) {
        showSuccessAlert("Order saved successfully")
        for (const [itemName, precentage] of Object.entries(response)) {
            // console.log(itemName + " --- " + precentage);
            precentage < 51 && saveAlert(`Item: ${itemName} is stock low!(${precentage}%)`, 'warning');
        }
        $("#saleCancelBtn").click();
        $("#salesHistoryBtn").click();
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showErrorAlert("An error occurred while saving purchase");
        console.error("Error details:", textStatus, errorThrown, jqXHR);
    });
}

$("#salePayBtn").click(() => {

    if (selectedItemByColors.length == 0) {
        showErrorAlert("Please add items to order.")
        return;
    }

    if (paymentMethod === 'card') {
        $("#cardPaymentPopup").css("display", "flex");
        isPaymentPanelOpened = true;
        return;
    }

    saveSale();
})

$("#saleCancelBtn").click(()=> {
    clearCustomerFields();
    subTotal = 0;
    selectedItems = [];
    selectedItemId = null;
    selectedItemByColors = [];
    $(".saleItems").empty();
})

$("#saleConfirmBtn").click(() => {
    if (!(($("#cardLasDigits").val().length === 4) ? setAsValid("#cardLasDigits", "Looks Good!") : setAsInvalid("#cardLasDigits", "Please enter card last 4 digits!"))
        | !($("#cardBankName").val() ? setAsValid("#cardBankName", "Looks Good!") : setAsInvalid("#cardBankName", "Please provide card bank name"))) {
        return;
    }

    $("#cardLasDigits").val("");
    $("#cardBankName").val("");
    clearValidations("#cardPaymentPopup")
    isPaymentPanelOpened && $("#cardPaymentPopup").css("display", "none");
    isPaymentPanelOpened = false;

    saveSale();
})

$("#saleCancelBtn").click(() => {
    $("#cardLasDigits").val("");
    $("#cardBankName").val("");
    clearValidations("#cardPaymentPopup")
    isPaymentPanelOpened && $("#cardPaymentPopup").css("display", "none");
    isPaymentPanelOpened = false;
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
    // sort
    if ($("#sortSalesSelect").val() !== 'NONE') {
        sortSoldTable();
    }

    $("#saleItemsAccordion").empty();
    let rowCount = 0;
    soldItems.map((sale, i) => {

        // filter & search
        if (!isInSelectedPayementMethod(sale)) return;
        if (!isInSearchedKeyword(sale)) return;

        let saleItemsRows = "";
        sale.saleItems.map((saleItem, i) => {
            saleItemsRows += `
                <tr class="align-middle">
                    <td class="text-center">${i + 1}</td>
                    <td><i class="fa-regular fa-copy"></i>${saleItem.saleItemId.item.inventoryCode}</td>
                    <td>${saleItem.unitPrice}</td>
                    <td class="text-center">${saleItem.qty}</td>
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
                        <label class="col-1">${++rowCount}</label>
                        <label class="col-2"><i class="fa-regular fa-copy"></i>${sale.orderId}</label>
                        <label class="col-2">${sale.orderDate}</label>
                        <label class="col-2">${sale.paymentMethod}</label>
                        <label class="col-1">${sale.totalPrice}</label>
                        <label class="col-2">${sale.customer ? sale.customer.name : 'Non Loyalty customer'}</label>
                        <label class="col-2">${sale.employee.name}</label>
                    </div>
                </div>

            </button>
            </h2>
            <div id="collapseSale${i}" class="accordion-collapse collapse" data-bs-parent="#accordionFlushExample">
            <div class="accordion-body container-fluid">
                <table class="table table-bordered border-dark">
                    <thead>
                        <th class="text-center">#</th>
                        <th>Item Name</th>
                        <th>Unit Price</th>
                        <th class="text-center">Qty</th>
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

$("#payBtn").on("click", () => {
    paymentMethod = $('input[name="paymentMethod"]:checked').val();
    if (paymentMethod !== 'card' && !isPaymentPanelOpened) return;
    if (!isPaymentPanelOpened) {
        $("#cardPaymentPopup").css("display", "flex");
        isPaymentPanelOpened = true;
    }
})

$("#cardPayment, #cashPayment").on("click", () => {
    paymentMethod = $('input[name="paymentMethod"]:checked').val();
    if (paymentMethod === "cash") {
        $("#cashAndBalance").show();
        isPaymentPanelOpened && $("#payBtn").click();
    } else {
        $("#cashAndBalance").hide();
    }
});

$("#saleCustomerSearchBtn").click(() => {
    let input = $("#saleCustomerSearchInput").val();
    allCustomers.filter(customer => {
        if (customer.email === input) {
            $("#saleCustomerName").val(customer.name);
            setCustomerDetails(customer);
        }
    })
})

$("#customerDetails .input").on('change', '#saleCustomerName', function () {
    let email = $(this).val();

    console.log(email);

    for (let i = 0; i < allCustomers.length; i++) {
        if (allCustomers[i].email === email) {
            $("#saleCustomerName").val(allCustomers[i].name);
            $("#saleCustomerSearchInput").val(allCustomers[i].email);
            setCustomerDetails(allCustomers[i]);
            return;
        }
    }
})

function setCustomerDetails(customer) {
    $("#saleCusLevel").text(customer.level);
    $("#saleCusPoints").text(customer.totalPoints);
    $("#saleCusJoinedDate").text(customer.joinedDateAsLoyalty);
    $("#saleCusRecendPurchase").text(customer.recentPurchaseDateTime ? customer.recentPurchaseDateTime : 'Not Purchased yet');
}

function clearCustomerFields(){
    $("#saleCusLevel").text("");
    $("#saleCusPoints").text("");
    $("#saleCusJoinedDate").text("");
    $("#saleCusRecendPurchase").text("");
}


$("#saleItemCode").on('input', function () {
    let input = $(this).val().toUpperCase();
    $(".choosItems").empty();
    if (input == "") {
        inventoryItems.map(item => {
            addSuggestItemToList(item);
        })
        return;
    }

    if (input.length > 1) {
        inventoryItems.map(item => {
            (item.itemCode.indexOf(input) >= 0) && addSuggestItemToList(item);
        })
    }
})

$("#saleCash").on('input', function () {
    let cash = parseFloat($(this).val());
    $("#saleCashBalance").text("Rs." + parseFloat(cash - subTotal));
})

const addSuggestItemToList = (item) => {
    $(".choosItems").append(`
    <div id=${item.itemCode} class="chooseItemCard">
        <div class="img" style="background-image: url(${item.inventoryItems[0].itemImage.image})"></div>
        <div class="body p-1 gap-2 flex-grow d-flex flex-column">
            <h4>${item.itemCode}</h4>
            <label class="flex-grow">${item.description}</label>
            <h3>Rs.${item.unitPriceSale}</h3>
        </div>
    </div>
    `)
}

$(".choosItems").on("click", ".chooseItemCard", function () {
    selectedItemId = $(this).attr('id')
    // 
    selectedItemByColors = getItemByColors(selectedItemId);
    $("#selectSize").empty();
    $("#selectSize").append('<option value="" selected>Select</option>');
    $("#saleItemStock").html(0);
    $("#selectColor").empty();
    $("#selectColor").append('<option value="" selected>Select color</option>');
    selectedItemByColors.forEach(color => {
        let itemQtyTot = 0;
        color.items.map(item => itemQtyTot += item.currentQty);
        $("#selectColor").append(`<option value="${color.color}" ${itemQtyTot == 0 ? 'disabled' : ''}>${color.color}</option>`)
    })
    $("#selectSaleItemPane").show();
})

$("#selectSaleItemPane .body").on('change', '#selectColor', function () {
    let selectedColor = $(this).val();
    selectedItemByColors.map(color => {
        if (color.color === selectedColor) {
            $("#selectSize").empty();
            $("#selectSize").append('<option value="" selected>Select</option>');
            $("#saleItemStock").html(0);
            color.items.map(itm => {
                $("#selectSize").append(`<option value="${itm.size}" ${itm.currentQty == 0 ? 'disabled' : ''}>${itm.size.substring(5)}</option>`)
            })
        }
    })
});

$("#selectSaleItemPane .body").on('change', '#selectSize', function () {
    let selectedSize = $('#selectSize').val();
    selectedItemByColors.filter(color => {
        if (color.color === $('#selectColor').val()) {
            color.items.map(itm => {
                if (itm.size === selectedSize) {
                    $("#saleItemStock").html(itm.currentQty);
                }
            })
        }
    })
});

// load table
const loadTable = () => {
    subTotal = 0;
    $(".saleItems").empty();
    selectedItems.map((item, i) => {
        $(".saleItems").append(`
            <div class="saleItemCard">
                <div class="img" style="background-image: url(${item.itemImage.image})"></div>
                <div class="body flex-grow">
                    <h6 class="m-0 quicksand-bold">${item.inventoryCode}</h6>
                    <label class="flex-grow mb-1 label p-0">${item.item.description}</label>
                    <div class="qtyPrices">
                        <span class="me-3">${item.item.unitPriceSale}</span>
                        <span class="flex-grow">x${item.qty}</span>
                        <span class="quicksand-bold">Rs.${item.item.unitPriceSale * item.qty}</span>
                    </div>
                    <i data-index="${i}" class="fa-solid fa-trash"></i>
                </div>
            </div>
        `)
        subTotal += item.item.unitPriceSale * item.qty;
        $("#saleSubTotal").text(subTotal.toFixed(2));
    })
}

// remove row by deleting
$(".saleItems").on('click', 'i', function () {
    let index = $(this).data('index');
    selectedItems.splice(index, 1);
    loadTable();
})

$("#selectSaleItemPane").hide();
$("#selectSaleItemPane button:first-child").click(() => {
    clearValidations("#selectSaleItemPane");
    $("#selectSaleItemPane").hide();
})

$("#selectSaleItemPane button:nth-child(2)").click(() => {

    // validations
    let isOk = itemSelectingValidations();
    if (isOk) {
        clearValidations("#selectSaleItemPane form")
        let itemCode = selectedItemId + '_' + $("#selectSize").val() + '_' + $("#selectColor").val();
        let itemsQty = parseInt($("#saleItemQty").val());
        console.log(itemCode, itemsQty);

        let item = inventoryItems.find(item => item.itemCode === selectedItemId);
        let inventoryItem = item.inventoryItems.find(item => item.inventoryCode === itemCode);

        let index = selectedItems.findIndex(item => item.inventoryCode === itemCode);
        if (index >= 0) {
            selectedItems[index].qty += itemsQty;
        } else {
            selectedItems.push({ ...inventoryItem, qty: itemsQty, item: item });
        }
        console.log(selectedItems);
        loadTable();
    }


})

$("#addSaleBtn").click(()=>{
    $("#salesBtn").click();
});

// filter item by colors
const getItemByColors = selectedItemCode => {
    let item = inventoryItems.find(item => item.itemCode === selectedItemCode);

    let colors = []
    item.inventoryItems.map(inv => colors.push(inv.colors))
    colors = [...new Set(colors)];
    let colorItems = []
    colors.map(color => {
        colorItems.push({
            color: color,
            items: item.inventoryItems.filter(item => item.colors === color)
        })
    })

    return colorItems;
}

// check validations
const itemSelectingValidations = () => {
    return ($("#selectColor").val() ? setAsValid("#selectColor", "Color Selected") : setAsInvalid("#selectColor", "Select a Color"))
        &
        ($("#selectSize").val() ? setAsValid("#selectSize", "Size Selected") : setAsInvalid("#selectSize", "Select a Size"))
        &
        (
            ($("#saleItemQty").val() > 0 && ($("#saleItemQty").val() < parseInt($("#saleItemStock").html())))
                ? setAsValid("#saleItemQty", "Looks Good!") : setAsInvalid("#saleItemQty", "Please select valid quantity")
        );
}

// sort
$("#salesHistory header select").on('change', function () {
    loadSoldItemsTable(allSoldItems);
})

const sortSoldTable = () => {
    let sortType = $("#sortSalesSelect").val();
    if (sortType === "LOW-HIGH") {
        allSoldItems.sort((a, b) => a.totalPrice - b.totalPrice);
    } else if (sortType === "HIGH-LOW") {
        allSoldItems.sort((a, b) => b.totalPrice - a.totalPrice);
    } else if (sortType === "Latest") {
        allSoldItems.sort((a, b) => new Date(b.orderDate) - new Date(a.orderDate));
    } else if (sortType === "Oldest") {
        allSoldItems.sort((a, b) => new Date(a.orderDate) - new Date(b.orderDate));
    }
}

// filter
const isInSelectedPayementMethod = (sale) => {
    let value = $("#salePaymentSelect").val();

    return value === 'ALL' ? true : sale.paymentMethod === value;
}

// search
$("#saleSearchBtn").click(() => {
    loadSoldItemsTable(allSoldItems);
})

const isInSearchedKeyword = (sale) => {
    let keyword = $("#saleSearchInput").val().toLowerCase();
    return keyword === '' ? true
        : sale.orderId.toLowerCase().includes(keyword)
        || sale.customer.name.toLowerCase().includes(keyword)
        || sale.customer.email.toLowerCase().includes(keyword)
        || sale.employee.name.toLowerCase().includes(keyword);
}
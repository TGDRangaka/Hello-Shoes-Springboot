import { token } from "../db/data.js";
import { Resupply } from "../model/Resupply.js";
import { ResupplyItem } from "../model/ResupplyItem.js"
import { getCategory, saveAlert, showErrorAlert, showSuccessAlert } from "../util/UtilMatter.js";

let allResupplies = [];
let allItems = [];
let allSuppliers = [];
let itemsAvailableBrands = [];
let selectedSupplier = null;
let selectedItems = [];

$("#resupplysBtn").click(() => {
    // getAllResupplies();
    getAllResupplies();
})

$("#restockBtn").click(() => {
    getAllItems();
    getAllSuppliers();
})

const getAllSuppliers = () => {
    var settings = {
        "url": "http://localhost:8080/api/v1/supplier",
        "method": "GET",
        "timeout": 0,
        "headers": {
            "Authorization": "Bearer " + token
        },
    };

    $.ajax(settings).done(function (response) {
        allSuppliers = response;
    });
}

$("#resupplySupplier").on('change', function () {
    selectedSupplier = $(this).val();
    if (selectedSupplier !== '-') {
        $("#resupplyItem").prop('disabled', false);
        setItemDataList(selectedSupplier);
    } else {
        $("#resupplyItem").prop('disabled', true);
    }
})

const saveResupplieDetails = (resupply) => {
    console.log(resupply);
    // return;
    var settings = {
        "url": "http://localhost:8080/api/v1/resupply",
        "method": "POST",
        "timeout": 0,
        "headers": {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        "data": JSON.stringify(resupply),
    };

    $.ajax(settings).done(function (response) {
        showSuccessAlert('Successfully saved resupply details');
        saveAlert(`Item: ${resupply.resupplyItems[0].resupplyItemId.inventory.inventoryCode.split('_')[0]} is been resupplied!`, 'general')
        $("#resupplyCancelBtn").click();
        $("#resupplysBtn").click();
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showErrorAlert("An error occurred while saving resupply details");
        console.error("Error details:", textStatus, errorThrown, jqXHR);
    });
}

const getAllResupplies = () => {
    var settings = {
        "url": "http://localhost:8080/api/v1/resupply",
        "method": "GET",
        "timeout": 0,
        "headers": {
            "Authorization": "Bearer " + token
        },
    };

    $.ajax(settings).done(function (response) {
        // console.log(response);
        allResupplies = response;
        loadAllResupplies(allResupplies);
    });
}

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
        allItems = response;
        allItems.filter(item => item.inventoryItems.length > 0)
        itemsAvailableBrands = allItems.map(item => item.supplierName);
        itemsAvailableBrands = [...new Set(itemsAvailableBrands)];

        // set supplier select options
        $("#resupplySupplier").empty();
        $("#resupplySupplier").append('<option value="-" selected>Select Supplier</option>');
        itemsAvailableBrands.map(supplier => {
            $("#resupplySupplier").append(`
            <option value="${supplier}">${supplier}</option>
            `)
        })
    });
}

const loadAllResupplies = (resupplies) => {
    // sort
    if ($("#resupplySortSelect").val() !== 'NONE') {
        sortResupplyTable();
    }

    $("#resupplyItemsAccordion").empty();
    let rowCount = 0;
    resupplies.map((resupply, i) => {
        // filter
        if (!isInSelectedSupplier(resupply)) return;
        if (!isInSearchedKeyword(resupply)) return;

        let resupplyItemRows = '';
        let itemCode = null;
        let itemName = null;
        resupply.resupplyItems.map((resupplyItem, i) => {
            itemName = resupplyItem.resupplyItemId.inventory.item.description;
            itemCode = resupplyItem.resupplyItemId.inventory.item.itemCode;
            resupplyItemRows += `
            <tr>
                <td class="text-center">${i + 1}</td>
                <td>
                    <img src="${resupplyItem.resupplyItemId.inventory.itemImage.image}" alt="" class="img-fluid">
                </td>
                <td>${resupplyItem.resupplyItemId.inventory.inventoryCode}</td>
                <td class="text-center">${resupplyItem.suppliedQty}</td>
                <td class="text-center">${resupplyItem.resupplyItemId.inventory.currentQty}</td>
            </tr>
            `
        })

        $("#resupplyItemsAccordion").append(`
        <div class="accordion-item">
            <h2 class="accordion-header">
            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#resupply${i}" aria-expanded="false" aria-controls="resupply${i}">
                
                <div class="container-fluid w-100">
                    <div class="row">
                        <label class="col-1">${++rowCount}</label>
                        <label class="col">${itemCode}</label>
                        <label class="col">${itemName}</label>
                        <label class="col">${resupply.supplier.name}</label>
                        <label class="col-2">${resupply.totalQty}</label>
                        <label class="col-2">${resupply.suppliedDate}</label>
                    </div>
                </div>

            </button>
            </h2>
            <div id="resupply${i}" class="accordion-collapse collapse" data-bs-parent="#accordionFlushExample">
            <div class="accordion-body container-fluid p-2">
                <table class="table table-bordered border-dark align-middle">
                    <thead>
                        <th class="text-center">#</th>
                        <th class="table-img">Image</th>
                        <th>Sub Item</th>
                        <th class="text-center">Supplied Qty</th>
                        <th class="text-center">Current Qty</th>
                    </thead>
                    <tbody>
                        ${resupplyItemRows}
                    </tbody>
                </table>
            </div>
            </div>
        </div>
        `);
    })
}

$("#resupplySubmitBtn").click(() => {
    let resupply = collectResupplieData();
    resupply != null && saveResupplieDetails(resupply);
})

const collectResupplieData = () => {
    let supplierId = allSuppliers.find(supplier => supplier.name.toLowerCase() === selectedSupplier.toLowerCase()).code;

    let resupply = new Resupply("", "", 0, { code: supplierId }, null);
    let resupplyItems = [];

    selectedItems.map(resupplyItem => {
        let qty = parseInt($(`#${resupplyItem.inventoryCode}`).val());
        if (qty > 0) {
            resupply.totalQty += qty
            resupplyItems.push(new ResupplyItem(
                {
                    inventory: {
                        inventoryCode: resupplyItem.inventoryCode
                    },
                    resupply: {
                        supplyId: ""
                    }
                },
                qty
            ));
        }
    })
    if(resupplyItems.length == 0){
        showErrorAlert("Please enter a quantity for minimum one item")
        return null;
    }
    resupply.resupplyItems = resupplyItems;
    return resupply;
}

const setItemDataList = (supplier) => {
    let items = allItems.filter(item => item.supplierName === supplier);
    $(".supplier-items").empty();
    items.map(item => {
        let img = item.inventoryItems[0].itemImage.image;
        $(".supplier-items").append(`
        <div data-code="${item.itemCode}" class="supplier-item-card">
            <div class="img" style="background-image: url(${img})"></div>
            <div class="info">
                <h5 class="m-0">${item.itemCode}</h5>
                <label class="m-0">${item.description}</label>
                <span>${getCategory(item.category)}</span>
            </div>
        </div>
        `);
    })
    // $("#resupplyItem").append('<option value="-" selected>Select Item</option>');
    // items.map(item => {
    //     $("#resupplyItemsList").append(`
    //     <option value="${item.itemCode}">${item.description}</option>
    //     `)
    // })
}

$(".supplier-items").on('click', '.supplier-item-card', function () {
    let itemCode = $(this).data("code");
    $(".supplier-item-card").removeClass("supplier-item-card-selected");
    $(this).addClass("supplier-item-card-selected");
    getSelectedItemData(itemCode);
})

const getSelectedItemData = selectedItemCode => {
    allItems.map(item => { 
        if (item.itemCode === selectedItemCode) {
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


            $(".resupplyInputs").empty();
            selectedItems = [];
            colorItems.forEach(colorItem => {
                addResupplyComponent(colorItem)
                selectedItems.push(...colorItem.items);
            });
        }
    })
}

const addResupplyComponent = (colorItems) => {
    // console.log("resupply items",colorItems);
    // return;
    let theads = '';
    let currentQtys = '';
    let inputs = '';
    colorItems.items.map(item => {
        theads += `<th class="table-input">${item.size}</th>`;
        let perQty = item.currentQty !== 0 ? item.currentQty / item.originalQty * 100 : 0
        currentQtys += `<td>${item.currentQty}<span class="bg-${perQty > 50 ? 'success' : perQty > 0 ? 'warning' : 'danger'} p-1 rounded text-white bg-opacity-75 ms-1">${parseInt(perQty)}%</span></td>`;
        inputs += `<td><input type="number" name="qty" id="${item.inventoryCode}" placeholder="qty"></td>`;
    })

    $(".resupplyInputs").append(`
    <table id="resupplyFormTable${colorItems.color}" class="table table-bordered border-dark w-100 text-center align-middle">
        <thead>
            <th colspan="8" class="text-center">
                <h4 class="m-0 quicksand-bold">${colorItems.color}</h4>
            </th>
        </thead>
        <tbody>
            <tr>
                <th class="text-start">Sizes</th>
                ${theads}
            </tr>
            <tr>
                <th class="text-start">Current Qty.</th>
                ${currentQtys}
            </tr>
            <tr>
                <th class="text-start">Resupply Qty.</th>
                ${inputs}
            </tr>
        </tbody>
    </table>
    `);
}

$("#resupplyItemBtn").click(() => {
    $("#restockBtn").click();
})

$("#resupplyCancelBtn").click(() => {
    $(".supplier-items").empty();
    $(".resupplyInputs").html('<h5 class="text-center">Not Selected Item.</h5>');
    selectedSupplier = null;
    selectedItems = [];
})

// sorting
$("#resupplyData header select").on('change', () => {
    loadAllResupplies(allResupplies);
})

const sortResupplyTable = () => {
    let soryValue = $("#resupplySortSelect").val();

    if (soryValue === 'A-Z') {
        allResupplies = allResupplies.sort((a, b) => a.supplier.name.localeCompare(b.supplier.name));
    } else if (soryValue === 'Z-A') {
        allResupplies = allResupplies.sort((a, b) => b.supplier.name.localeCompare(a.supplier.name));
    } else if (soryValue === 'Latest') {
        allResupplies = allResupplies.sort((a, b) => new Date(b.suppliedDate) - new Date(a.suppliedDate));
    } else if (soryValue === 'Oldest') {
        allResupplies = allResupplies.sort((a, b) => new Date(a.suppliedDate) - new Date(b.suppliedDate));
    }
}

// filter
const isInSelectedSupplier = (resupply) => {
    let value = $("#resupplySupplierSelect").val();
    return (value === 'ALL') ? true : resupply.supplier.name === value;
}

// search
$("#resupplySearchBtn").click(() => {
    loadAllResupplies(allResupplies);
})
const isInSearchedKeyword = (resupply) => {
    let value = $("#resupplySearchInput").val().toLowerCase();
    return (value === '') ? true
        : resupply.supplier.name.toLowerCase().includes(value)
        || resupply.resupplyItems[0].resupplyItemId.inventory.item.description.toLowerCase().includes(value)
        || resupply.resupplyItems[0].resupplyItemId.inventory.item.itemCode.toLowerCase().includes(value);
}
import { token } from "../db/data.js";
import { Resupply } from "../model/Resupply.js";
import { ResupplyItem } from "../model/ResupplyItem.js"

let allResupplies = [];
let allItems = [];
let allSuppliers = [];
let itemsAvailableBrands = [];
let selectedSupplier = null;
let selectedItems = [];

$("#resupplysBtn").click(()=>{
    // getAllResupplies();
    getAllItems();
    getAllSuppliers();
})

const getAllSuppliers = ()=> {
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

$("#resupplySupplier").on('change',function(){
    selectedSupplier = $(this).val();
    if(selectedSupplier !== '-'){
        $("#resupplyItem").prop('disabled',false);
        setItemDataList(selectedSupplier);
    }else{
        $("#resupplyItem").prop('disabled',true);
    }
})

const saveResupplieDetails = (resupply) => {
    console.log(resupply);
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
        console.log(response);
      });
}

const getAllResupplies = () => {
    // call api
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

$("#resupplySubmitBtn").click(()=> {
    let resupply = collectResupplieData();
    saveResupplieDetails(resupply);
})

const collectResupplieData = () => {
    let supplierId = allSuppliers.filter(supplier => supplier.name === selectedSupplier)[0].code;

    let resupply = new Resupply("","", 0, {code: supplierId}, null);
    let resupplyItems = [];

    selectedItems.map(resupplyItem => {
        let qty = parseInt($(`#${resupplyItem.inventoryCode}`).val());
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
    })
    resupply.resupplyItems = resupplyItems;
    return resupply;
}

const setItemDataList = (supplier) => {
    let items = allItems.filter(item => item.supplierName === supplier);
    $("#resupplyItemsList").empty();
    $("#resupplyItem").append('<option value="-" selected>Select Item</option>');
    items.map(item => {
        console.log(item);
        $("#resupplyItemsList").append(`
        <option value="${item.itemCode}">${item.description}</option>
        `)
    })
}

$("#resupplyItem").on('change',function(){
    let selectedItem = $(this).val();
    console.log("selected item",selectedItem);
    allItems.map(item => {
        if(item.itemCode === selectedItem) {
            let colors = []
            item.inventoryItems.map(inv => colors.push(inv.colors))
            colors = [...new Set(colors)];
            let colorItems = []
            console.log(colors);
            colors.map(color => {
                colorItems.push({
                    color: color, 
                    items: item.inventoryItems.filter(item => item.colors === color)
                })
            })

            
            $("#resupplyItems").empty();
            selectedItems = [];
            colorItems.forEach(colorItem => {
                addResupplyComponent(colorItem)
                selectedItems.push(...colorItem.items);
            });
            // console.log(colorItems);
        }
    })
})

const addResupplyComponent = (colorItems) => {
    // console.log("resupply items",colorItems);
    let theads = '';
    let currentQtys = '';
    let inputs = '';
    colorItems.items.map(item => {
        theads += `<th class="table-input">${item.size}</th>`;
        let perQty = item.currentQty !== 0 ? item.currentQty / item.originalQty * 100 : 0
        currentQtys += `<td>${item.currentQty}<span class="bg-${perQty > 50? 'success' : perQty > 0 ? 'warning' : 'danger'} p-1 rounded text-white bg-opacity-75 ms-1">${parseInt(perQty)}%</span></td>`;
        inputs += `<td><input type="number" name="qty" id="${item.inventoryCode}" placeholder="qty"></td>`;
    })

    $("#resupplyItems").append(`
    <div id="ResupplyItem${colorItems.color}" class="row">
        <div class="col-3">
            <img class="w-100 rounded" src="${colorItems.items[0].itemImage.image}" alt="shoe img">
        </div>
        <div class="col d-flex flex-column justify-content-around">
            <hr>
            <h3>${colorItems.color}</h3>
            <table class="table text-center table-bordered">
                <thead>
                    <th class="text-start">Size</th>
                    ${theads}
                </thead>
                <tbody>
                    <tr>
                        <th class="text-start">Current Qty</th>
                        ${currentQtys}
                    </tr>
                    <tr>
                        <th class="text-start">Supply Qty</th>
                        ${inputs}
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    `);
}
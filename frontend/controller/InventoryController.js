import {Item} from '../model/Item.js'
import {Inventory} from '../model/Inventory.js'
import {token} from '../db/data.js'

let otherBtnCount = 0;
let isNewProduct = true;
let allItems = [];

$("#inventoryBtn").click(()=>{
    getAllItems();
})

const getAllItems = ()=>{
    var settings = {
        "url": "http://localhost:8080/api/v1/inventory",
        "method": "GET",
        "timeout": 0,
        "headers": {
          "Authorization": "Bearer " + token
        },
      };
      
      $.ajax(settings).done(function (response) {
        allItems = response;
        allItems.map(item => {
            let img = new Image();
            img.src = item.itemImage.image
            item.itemImage = img;
        })
        loadItemTable();
      });
}

const loadItemTable = () => {
    $("#inventoryItems").empty();
    allItems.map(item => {
        $("#inventoryItems").append(`
            <div class="accordion-item ">
                <div class="accordion-header">
                    <button type="button" class="accordion-button p-2" data-bs-toggle="collapse" data-bs-target="#${item.inventoryCode}" aria-expanded="true" aria-controls="${item.inventoryCode}">
                        <div class="inventory-item-header row w-100 text-theme">
                            <!-- <img class="h-100 col-2" src="${item.itemImage.src}" alt="shoe image"> -->
                            <div class="col-1 d-flex justify-content-center">
                                <div class="img"></div>
                            </div>
                            <label class="col-2">${item.inventoryCode}</label>
                            <label class="col-3">${item.item.description}</label>
                            <label class="col-2">${item.size}</label>
                            <label class="col-2">Rs.${item.item.unitPriceSale}</label>
                            <div class="col-2 d-flex flex-column justify-content-center align-items-center">
                                <h4>${item.currentQty}</h4>
                                <label class="stock stock-${getFirstLaters(item.status)}">${item.status}</label>
                            </div>
                        </div>
                    </button>
                </div>
                <div id="${item.inventoryCode}" class="accordion-collapse collapse w-100" data-bs-parent="#inventoryItems">
                    <div class="accordion-body m-2 w-100">
                        <div class="inventory-item-body container-fluid row">
                            <div class="col-4 img"></div>
                            <div class="col-8 row">
                                <div class="inventory-body-detail col-6">
                                    <label class="label">Product Name</label>
                                    <h4>${item.item.description}</h4>
                                </div>
                                <div class="inventory-body-detail col-3">
                                    <label class="label">Product ID</label>
                                    <h4>${item.item.itemCode}</h4>
                                </div>
                                <div class="inventory-body-detail col-3">
                                    <label class="label">Size</label>
                                    <h4>${item.size}</h4>
                                </div>
        
                                <div class="inventory-body-detail col-3">
                                    <label class="label">Category</label>
                                    <h4>${item.item.category}</h4>
                                </div>
                                <div class="inventory-body-detail col-3">
                                    <label class="label">Supplier</label>
                                    <h4>${item.item.supplierName}</h4>
                                </div>
                                <div class="inventory-body-detail col-3">
                                    <label class="label">Colour</label>
                                    <h4>${item.colors}</h4>
                                </div>
                                <div class="inventory-body-detail col-3">
                                    <label class="label">Stock</label>
                                    <h4>${item.currentQty}</h4>
                                </div>
        
                                <div class="inventory-body-detail col-3">
                                    <label class="label">Sale Price</label>
                                    <h4>${item.item.unitPriceSale}</h4>
                                </div>
                                <div class="inventory-body-detail col-3">
                                    <label class="label">Buy Price</label>
                                    <h4>${item.item.unitPriceBuy}</h4>
                                </div>
                                <div class="inventory-body-detail col-3">
                                    <label class="label">Profit Margin</label>
                                    <h4>${item.item.profitMargin}%</h4>
                                </div>
                                <div class="inventory-body-detail col-3">
                                    <label class="label">Expected Profit</label>
                                    <h4>${item.item.expectedProfit}</h4>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `);
    })
}

let product = 'new';
$("#existingProductDetails").hide();

$("#newProductRbtn, #existProductRbtn").click(()=>{
    product = $('input[name="productRbtn"]:checked').val();
    if(product === 'new'){
        isNewProduct = true;
        $("#newProductDetails").show();
        $("#existingProductDetails").hide();
    } else {
        isNewProduct = false;
        $("#newProductDetails").hide();
        $("#existingProductDetails").show();
    }
})

$('#cbColors .form-check-input').click(function() {
    var selectedColors = [];

    $('#cbColors .form-check-input').each(function() {
        if ($(this).is(':checked')) {
            selectedColors.push($(this).val());
        }
    });

    if(selectedColors){
        otherBtnCount = 0;
        $("#colorInputs").empty();
        selectedColors.map(color => {
            $("#colorInputs").append(getColorFieldsComponent(color))
        })
    }
});

const getColorFieldsComponent = color =>{
    return `
    <div id="${color}Inputs" class="mb-3">
        <label for="" class="label ms-1 me-3 quicksand-bold">Black</label>
        <div class="mt-2">
            <label class="label">Image: </label>
            <input class="form-control mb-2 import" id="product${color}Image" name="product${color}Image" type="file">
        </div>
    </div>
    <hr>
    `
}

$("#otherColorBtn").click(function() {
    otherBtnCount++;
    let id = "Other" + otherBtnCount;
    $("#otherColorInputs").append(`
    <div id="${id}Inputs" class="mb-3 otherColorInputs">
        <label for="" class="label ms-1 me-3">#${id} :</label><br>
        <div class="mt-2">
            <label class="label">Color Name: </label>
            <input class="form-control import" id="product${id}Name" name="product${id}Name" type="text" placeholder="Colour/Pattern Name*">
        </div>
        <div class="mt-2">
            <label class="label">Image: </label>
            <input class="form-control mb-2 import" id="product${id}Image" name="product${id}Image" type="file">
        </div>
    </div>
    <hr>
    `)
})

$("#addProductBtn").click(async ()=>{
    let typeByOccasion = getValue("typesByOccasion")
    let gender = getValue("typesByGender")
    let allProducts = [];
    let item = new Item(
        typeByOccasion + gender,
        getValue("productName"),
        getValue("productByCategory"),
        getValue("productBySupplier"),
        null,
        getValue("productSellPrice"),
        getValue("productBuyPrice"),
        getValue("productExpectProfit"),
        getValue("productProfitMargin"),
        null
    );

    let selectedColors = [];
    $('#cbColors .form-check-input').each(function() {
        if ($(this).is(':checked')) {
            selectedColors.push($(this).val());
        }
    });
    for(let i = 1; i <= otherBtnCount; i++){
        let id = "Other" + i;
        // console.log($(`#${id}Inputs`));
        selectedColors.push(id);
    }
    console.log(selectedColors);

    for(let color of selectedColors){
        let id = color;
        let img = $(`#product${id}Image`).prop('files')[0];
        let colorName = id.includes("Other") ? $(`#product${id}Name`).val() : color;
        console.log(colorName);
        let reader = new FileReader();
        
        let sizes = [5,6,7,8,9,10,11];
        let inventoryItems = [];

        // for(let i = 5; i < 12; i++){
        //     $(`#cb${id}Size${i}`).is(':checked') && sizes.push(i);;
        // }

        for(let i = 0; i < sizes.length; i++){
            let size = sizes[i];
            let inventoryItem = new Inventory("",`SIZE_${size}`, colorName,null,null,null,null,{id:"",image:""},[],[]);
            inventoryItems.push(inventoryItem);
        }

        let base64Image = await new Promise((resolve, reject) => {
            reader.onloadend = function(){
                resolve(reader.result);
            }
            reader.readAsDataURL(img);
        });

        (inventoryItems.length > 0) && (inventoryItems[0].itemImage = {id:"", image:base64Image});
        allProducts.push(...inventoryItems);
    }

    item.inventoryItems = allProducts;
    console.log(item);
    saveProduct(item);
})

const getValue = id => {
    return $("#" + id).val();
}

const saveProduct = (item) => {
    var settings = {
        "url": "http://localhost:8080/api/v1/item",
        "method": "POST",
        "timeout": 0,
        "headers": {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
        },
        "data": JSON.stringify(item),
        // "data": JSON.stringify(item),
      };
      
      $.ajax(settings).done(function (response) {
        console.log(response);
      });
}

$("#productByCategory").on('change', function (){
    let val = $(this).val();
    if(val === 'F' || val === 'H' || val === 'W'){
        $("#typesByGender").prop('disabled', true);
        $("#typesByGender").val('W');
    }else{
        $("#typesByGender").prop('disabled', false);
    }
    /^(S)$/.test(val) ? $("#is, #ss").prop('disabled', false)
    : $("#is, #ss").prop('disabled', true);

    /^(FF|SL)$/.test(val) ? $("#typesByOccasion").prop('disabled', true)
    : $("#typesByOccasion").prop('disabled', false);
})

const getFirstLaters = text => {
    let ar = text.split(' ');
    let s = '';
    ar.map(t => {
        s+=t.charAt(0);
    })
    return s;
}
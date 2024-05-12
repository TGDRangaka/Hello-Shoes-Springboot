import {Item} from '../model/Item.js'
import {Inventory} from '../model/Inventory.js'
import {token} from '../db/data.js'

let otherBtnCount = 0;
let allItems = [];
let selectedItem = null;
let isItemSelected = false;
let shoeColors = ['Black', 'White', 'Red', 'Blue', 'Brown', 'Gray'];
// let socksColors = ['Black', 'White', 'Brown'];

$("#inventoryBtn").click(()=>{
    getAllItems();
})

const getAllItems = ()=>{
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
        // allItems.map(item => {
        //     let img = new Image();
        //     img.src = item.itemImage.image
        //     item.itemImage = img;
        // })
        loadItemTable(allItems);
        // console.log(allItems);
      });
}

const loadItemTable = (allItems) => {

    $("#inventoryItems").empty();
    allItems.map((item, i) => {

        let category = item.category;
        let colors = [];
        let imgsByColors = '';
        let itemsByColors = [];
        let rows = '';
        if(category !== 'ACC'){
            // get inventory items by colors
            item.inventoryItems.map(inventory => {
                if(!colors.includes(inventory.colors)){
                    colors.push(inventory.colors);
                    imgsByColors += `
                    <div class="swiper-slide">
                        <img src="${inventory.itemImage.image}" class="img-fluid" alt="">
                    </div>
                    `
                }
            })
            colors.map((color) => {
                let tempItems = [];
                item.inventoryItems.map(inventory => (inventory.colors === color) && tempItems.push(inventory))
                itemsByColors.push({color: color, items: tempItems});
            })

            // set table row through loop
            itemsByColors.map((colorItem, i) => {
                let row = `
                <tr>
                    <td>${i+1}</td>
                    <td>
                    <img src="${colorItem.items[0].itemImage.image}" class="img-fluid" alt="">
                    </td>
                    <td class="text-start">${colorItem.color}</td>`;
                // colorItem.items.map(item => {
                //     row += `
                //     <td>${item.currentQty}<span class="bg-success p-1 rounded text-white bg-opacity-75 ms-1">75%</span></td>
                //     `;
                // })
                colorItem.items.sort((a, b) => parseInt(a.size.substring(5)) - parseInt(b.size.substring(5)))
                // console.log(colorItem.items);
                let totQty = 0;
                for(let j = 0,k = 0; j < 7; j++) {
                    let itm = colorItem.items[k];
                    if(`SIZE_${j+5}` === itm.size){
                        totQty += itm.currentQty;
                        let per = itm.currentQty == 0 ? 0 : itm.currentQty / itm.originalQty * 100;
                        row += `
                        <td>${itm.currentQty}<span class="bg-${per >= 50? 'success' : per > 0 ? 'warning' : 'danger'} p-1 rounded text-white bg-opacity-75 ms-1">${per.toFixed(0)}%</span></td>`
                        k++;
                        (k == colorItem.items.length) && k--;
                    }else{
                        row += `
                        <td>0<span class="bg-danger p-1 rounded text-white bg-opacity-75 ms-1">0%</span></td>`
                    }
                }
                row += `
                    <td>${totQty}</td>
                </tr>`;
                rows += row;
                // console.log(colorItem.color, row);
            })
        }else{
            let accessory = item.inventoryItems[0]
            var accessoriesData = `
                <div class="input col-3">
                    <label class="label">Colour</label>
                    <h4>${accessory.colors}</h4>
                </div>
                <div class="input col-3">
                    <label class="label">Size</label>
                    <h4>${accessory.size}</h4>
                </div>
                <div class="input col-3">
                    <label class="label">Quantity</label>
                    <h4>${accessory.currentQty}</h4>
                </div>
                <div class="input col-3">
                    <label class="label">Stock Percentage</label>
                    <h4>${accessory.status}</h4>
                </div>
            `
        }
        // <h4>${accessory.currentQty == 0 ? 0 : (accessory.currentQty / accessory.originalQty * 100).toFixed(0)}%</h4>


        // get a image
        let img = item.inventoryItems[0].itemImage.image;
        let header = `
        <div class="row align-items-center">
            <div class="col-1 d-flex justify-content-center align-items-center gap-3">
                <span class="">${i+1}</span>
                <img src="${img}" class="img-fluid table-img" alt="">
            </div>
            <div class="col">${item.description}</div>
            <div class="col">${item.category}</div>
            <div class="col">${item.supplierName}</div>
            <div class="col-1">${item.unitPriceSale}</div>
            <div class="col-1">${item.unitPriceBuy}</div>
            <div class="col-1"><i data-index="${i}" class="fa-solid fa-pen itemEdit"></i></div>
        </div>
        `;

        $("#inventoryItems").append(`
        <div class="accordion-item">
            <h2 class="accordion-header">
            <button class="accordion-button collapsed p-1" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapse${i}" aria-expanded="false" aria-controls="flush-collapse${i}">
                <div class="sale-accordion container-fluid">
                    ${header}
                </div>
            </button>
            </h2>
            <div id="flush-collapse${i}" class="accordion-collapse collapse" data-bs-parent="#accordionFlushExample">
            <div class="accordion-body container-fluid">
                <div class="row">
                    <div class="col-3 d-flex justify-content-center align-items-center">
                        <img src="${img}" alt="item image" />
                    </div>
                    <div class="col row">
                        <div class="input col-12">
                            <label class="label">Product Name</label>
                            <h4>Nike Air 2</h4>
                        </div>

                        <div class="input col-4">
                            <label class="label">Product ID</label>
                            <h4>FSW00045</h4>
                        </div>
                        <div class="input col-4">
                            <label class="label">Category</label>
                            <h4>Shoe</h4>
                        </div>
                        <div class="input col-4">
                            <label class="label">Supplier</label>
                            <h4>Nike</h4>
                        </div>

                        <div class="input col-3">
                            <label class="label">Sale Price</label>
                            <h4>Rs.2000</h4>
                        </div>
                        <div class="input col-3">
                            <label class="label">Buy Price</label>
                            <h4>Rs.1800</h4>
                        </div>
                        <div class="input col-3">
                            <label class="label">Profit Margin</label>
                            <h4>10%</h4>
                        </div>
                        <div class="input col-3">
                            <label class="label">Expected Profit</label>
                            <h4>Rs.200</h4>
                        </div>
                        ${category === 'ACC' ? accessoriesData : ''}
                    </div>
                </div>
                ${category !== 'ACC' ? `<div class="row mt-3 p-3">
                    <table class="table col table-bordered border-dark">
                        <thead class="align-middle">
                            <tr>
                                <th rowspan="2" class="table-action text-center">#</th>
                                <th rowspan="2" class="table-img">Image</th>
                                <th rowspan="2">Color</th>
                                <th colspan="7" class="text-center">Sizes Qty</th>
                                <th rowspan="2" class="text-center">Total Qty</th>
                            </tr>
                            <tr class="text-center">
                                <th>5</th>
                                <th>6</th>
                                <th>7</th>
                                <th>8</th>
                                <th>9</th>
                                <th>10</th>
                                <th>11</th>
                            </tr>
                        </thead>
                        <tbody class="align-middle text-center">
                            ${rows}
                        </tbody>
                    </table>
                </div>` : ''}

            </div>
            </div>
        </div>
        `);
    })
}

$('#cbColors').on('click', '.form-check-input',function() {
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
        <label for="" class="label ms-1 me-3 quicksand-bold">${color}</label>
        <div class="mt-2">
            <label class="label">Image: </label>
            <input class="form-control mb-2 import" id="product${color}Image" name="product${color}Image" type="file">
        </div>
    </div>
    <hr>
    `
}

const getOtherColorFieldsComponent = (id) => {
    return `
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
    `;
}

$('#cbColors').on('click', '#otherColorBtn',function() {
    otherBtnCount++;
    let id = "Other" + otherBtnCount;
    $("#otherColorInputs").append(getOtherColorFieldsComponent(id));
})

$("#addProductBtn").click(async ()=>{
    // let typeByOccasion = getValue("typesByOccasion")
    // let category = getValue("productByCategory");
    // let verities = getValue("typesByVerities");
    // let gender = getValue("typesByGender")

    // let item = new Item(
    //     category === 'ACC' ? verities : typeByOccasion + gender,
    //     getValue("productName"),
    //     category,
    //     getValue("productBySupplier"),
    //     null,
    //     getValue("productSellPrice"),
    //     getValue("productBuyPrice"),
    //     getValue("productExpectProfit"),
    //     getValue("productProfitMargin"),
    //     []
    // );

    // let selectedColors = [];
    // if(category === 'ACC'){
    //     if(verities === 'POLB'){
    //         selectedColors.push("Black");
    //     }else if(verities === 'POLBR'){
    //         selectedColors.push("Brown");
    //     }else if(verities === 'POLDBR'){
    //         selectedColors.push("Dark Brown");
    //     }else{
    //         selectedColors.push("No-Colour");
    //     }
    // }else{
    //     $('#cbColors .form-check-input').each(function() {
    //         if ($(this).is(':checked')) {
    //             selectedColors.push($(this).val());
    //         }
    //     });
    //     for(let i = 1; i <= otherBtnCount; i++){
    //         let id = "Other" + i;
    //         selectedColors.push(id);
    //     }
    // }
    
    // console.log(selectedColors);

    // for(let color of selectedColors){
    //     let id = color;
    //     let img = (category === 'ACC') ? $(`#shampooNoClrImage`).prop('files')[0] : $(`#product${id}Image`).prop('files')[0];
    //     let colorName = id.includes("Other") ? $(`#product${id}Name`).val() : color;
    //     console.log(colorName);
    //     let reader = new FileReader();

    //     let base64Image = await new Promise((resolve, reject) => {
    //         reader.onloadend = function(){
    //             resolve(reader.result);
    //         }
    //         reader.readAsDataURL(img);
    //     });
    //     let inventoryItm = new Inventory();
    //     inventoryItm.size = (category !== 'ACC') ? 'SIZE_5' : (['SHMP', 'POLB', 'POLBR', 'POLDBR'].includes(verities)) ? 'NOT_APPLICABLE' : verities === 'SOF' ? 'FULL' : verities === 'SOH' ? 'HALF' : 'NOT_APPLICABLE';
    //     inventoryItm.colors = colorName;
    //     inventoryItm.itemImage = {id:"", image:base64Image};
    //     inventoryItm.resupplyItems = [];
    //     inventoryItm.saleItems = [];

    //     item.inventoryItems.push(inventoryItm);
    // }
    // console.log(item);
    isItemSelected ? updateProduct() : saveProduct();
    // saveProduct(item);
})

const getValue = id => {
    return $("#" + id).val();
}

const saveProduct = async () => {
    let typeByOccasion = getValue("typesByOccasion")
    let category = getValue("productByCategory");
    let verities = getValue("typesByVerities");
    let gender = getValue("typesByGender")

    let item = new Item(
        category === 'ACC' ? verities : typeByOccasion + gender,
        getValue("productName"),
        category,
        getValue("productBySupplier"),
        null,
        getValue("productSellPrice"),
        getValue("productBuyPrice"),
        getValue("productExpectProfit"),
        getValue("productProfitMargin"),
        []
    );

    let selectedColors = [];
    if(category === 'ACC'){
        if(verities === 'POLB'){
            selectedColors.push("Black");
        }else if(verities === 'POLBR'){
            selectedColors.push("Brown");
        }else if(verities === 'POLDBR'){
            selectedColors.push("Dark Brown");
        }else{
            selectedColors.push("No-Colour");
        }
    }else{
        $('#cbColors .form-check-input').each(function() {
            if ($(this).is(':checked')) {
                selectedColors.push($(this).val());
            }
        });
        for(let i = 1; i <= otherBtnCount; i++){
            let id = "Other" + i;
            selectedColors.push(id);
        }
    }
    
    console.log(selectedColors);

    for(let color of selectedColors){
        let id = color;
        let img = (category === 'ACC') ? $(`#shampooNoClrImage`).prop('files')[0] : $(`#product${id}Image`).prop('files')[0];
        let colorName = id.includes("Other") ? $(`#product${id}Name`).val() : color;

        let inventoryItm = new Inventory();
        inventoryItm.size = (category !== 'ACC') ? 'SIZE_5' : (['SHMP', 'POLB', 'POLBR', 'POLDBR'].includes(verities)) ? 'NOT_APPLICABLE' : verities === 'SOF' ? 'FULL' : verities === 'SOH' ? 'HALF' : 'NOT_APPLICABLE';
        inventoryItm.colors = colorName;
        inventoryItm.itemImage = {id:"", image: await getFileToBase64(img)};
        inventoryItm.resupplyItems = [];
        inventoryItm.saleItems = [];

        item.inventoryItems.push(inventoryItm);
    }
    console.log(item);

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

const updateProduct = async () => {
    let typeByOccasion = getValue("typesByOccasion")
    let category = getValue("productByCategory");
    let verities = getValue("typesByVerities");
    let gender = getValue("typesByGender")

    let item = new Item(
        category === 'ACC' ? verities : typeByOccasion + gender,
        getValue("productName"),
        category,
        getValue("productBySupplier"),
        null,
        getValue("productSellPrice"),
        getValue("productBuyPrice"),
        getValue("productExpectProfit"),
        getValue("productProfitMargin"),
        []
    );

    let selectedColors = [];
    if(category === 'ACC'){
        if(verities === 'POLB'){
            selectedColors.push("Black");
        }else if(verities === 'POLBR'){
            selectedColors.push("Brown");
        }else if(verities === 'POLDBR'){
            selectedColors.push("Dark Brown");
        }else{
            selectedColors.push("No-Colour");
        }
    }else{
        $('#cbColors .form-check-input').each(function() {
            if ($(this).is(':checked')) {
                selectedColors.push($(this).val());
            }
        });
        for(let i = 1; i <= otherBtnCount; i++){
            let id = "Other" + i;
            selectedColors.push(id);
        }
    }
    
    console.log('selectedColors',selectedColors);
    let availableColors = [];
    selectedItem.inventoryItems.map(itm => {
        !availableColors.includes(itm.colors) && availableColors.push(itm.colors);
    })

    for(let color of selectedColors){
        let id = color;
        let inventoryItm = new Inventory();
        let colorName = id.includes("Other") ? $(`#product${id}Name`).val() : color;
        inventoryItm.size = (category !== 'ACC') ? 'SIZE_5' : (['SHMP', 'POLB', 'POLBR', 'POLDBR'].includes(verities)) ? 'NOT_APPLICABLE' : verities === 'SOF' ? 'FULL' : verities === 'SOH' ? 'HALF' : 'NOT_APPLICABLE';
        inventoryItm.colors = colorName;
        inventoryItm.resupplyItems = [];
        inventoryItm.saleItems = [];

        // process inventory images
        try {
            
        } catch (error) {
            
        }
        let img = (category === 'ACC') ? $(`#shampooNoClrImage`).prop('files')[0] : $(`#product${id}Image`).prop('files')[0];
        if(availableColors.includes(colorName)){
            let currentImage = selectedItem.inventoryItems.find(itm => itm.colors === colorName).itemImage;
            if(img){
                console.log('new image ', colorName);
                inventoryItm.itemImage = {id: currentImage.id, image: await getFileToBase64(img)};
            }else{
                console.log('current image ', colorName);
                inventoryItm.itemImage = currentImage;
            }
        }else{
            console.log('new image & new item ', colorName);
            inventoryItm.itemImage = {id:"", image: await getFileToBase64(img)};
            // set file input validations
        }

        item.inventoryItems.push(inventoryItm);
    }
    console.log(item);

    var settings = {
        "url": "http://localhost:8080/api/v1/item/" + selectedItem.itemCode,
        "method": "PUT",
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

const getFileToBase64 = async file => {
    let reader = new FileReader();
    return new Promise((resolve, reject) => {
        reader.onloadend = function(){
            resolve(reader.result);
        }
        reader.readAsDataURL(file);
    });
}

// inventory category validations

$("#accessoriesAdditional").hide();
$("#accessoriesImage").hide();
$("#productByCategory").on('change', function (){
    let val = $(this).val();

    // set gender women for women items
    if(val === 'F' || val === 'H' || val === 'W'){
        $("#typesByGender").prop('disabled', true);
        $("#typesByGender").val('W');
    }else{
        $("#typesByGender").prop('disabled', false);
    }

    // if category shoes enable occasion types(industry, sport)
    /^(S)$/.test(val) ? $("#is, #ss").prop('disabled', false)
    : $("#is, #ss").prop('disabled', true);

    // if category flip-flops/slippers, then disable occasion all types
    /^(FF|SL)$/.test(val) ? $("#typesByOccasion").prop('disabled', true)
    : $("#typesByOccasion").prop('disabled', false);

    if(val === 'ACC'){
        $("#shoesAdditional").hide();
        $("#accessoriesAdditional").show();
        $("#shoeColors").hide();
        $("#accessoriesImage").show();
    }else{
        $("#shoesAdditional").show();
        $("#accessoriesAdditional").hide();
        $("#shoeColors").show();
        $("#accessoriesImage").hide();
        setShoeColorButtons();
    }
})

const getFirstLaters = text => {
    let ar = text.split(' ');
    let s = '';
    ar.map(t => {
        s+=t.charAt(0);
    })
    return s;
}

$("#inventoryItems").on('click', '.itemEdit', function(){
    let index = $(this).data('index');
    console.log(index);
    selectedItem = allItems[index];
    isItemSelected = true;
    $("#inventory").toggle();
    $("#addProduct").toggle();
    setItemData(selectedItem);
})

const setItemData = item => {
    // General
    $("#productName").val(item.description);
    $("#productByCategory").val(item.category);
    $("#productByCategory").prop('disabled', true);
    $("#productBySupplier").val(item.supplierName);
    $("#productBySupplier").prop('disabled', true);

    // Additional
    let letters = item.itemCode.substring(0, item.itemCode.length - 5);
    if(item.category !== 'ACC'){
        let gender = letters.charAt(letters.length - 1);
        $("#typesByGender").prop('disabled', true);
        $("#typesByGender").val(gender);
        $("#typesByOccasion").prop('disabled', true);
        $("#typesByOccasion").val(item.itemCode.charAt(0) + 'S');
    }else{
        $("#shoesAdditional").hide();
        $("#accessoriesAdditional").show();
        $("#typesByVerities").prop('disabled', true);
        $("#typesByVerities").val(letters);
    }

    // Prices
    $("#productSellPrice").val(item.unitPriceSale);
    $("#productBuyPrice").val(item.unitPriceBuy);
    $("#productExpectProfit").val(item.expectedProfit);
    $("#productProfitMargin").val(item.profitMargin);

    // Colors/Images
    if(item.category !== 'ACC'){
        let colors = []
        item.inventoryItems.map(inventory => {
            !colors.includes(inventory.colors) && colors.push(inventory.colors);
        })
        console.log(colors);
        $("#cbColors").empty();
        $("#colorInputs").empty();
        $("#otherColorInputs").empty();
        // let colorCount = 0;
        shoeColors.map(color => {
            $("#cbColors").append(`
            <div class="cb-input">
                <input class="form-check-input" type="checkbox" id="cbColor${color}" value="${color}" ${colors.includes(color) ? 'checked disabled' : ''}>
                <label class="label" for="cbColor${color}">${color}</label>
            </div>
            `)
            if(colors.includes(color)){
                // colorCount++;
                $("#colorInputs").append(getColorFieldsComponent(color))
                colors.splice(colors.indexOf(color), 1);
            }
        })
        $("#cbColors").append(`
        <button id="otherColorBtn" type="button" class="btn btn-primary"><i class="fa-solid fa-plus me-2"></i>Other</button>
        `);
        otherBtnCount = 0;
        colors.map(color =>{
            otherBtnCount++;
            let id = 'Other' + otherBtnCount;
            $("#otherColorInputs").append(`
            <div id="${id}Inputs" class="mb-3 otherColorInputs">
                <label for="" class="label ms-1 me-3">#${id} :</label><br>
                <div class="mt-2">
                    <label class="label">Color Name: </label>
                    <input class="form-control import" id="product${id}Name" name="product${id}Name" type="text" value="${color}" disabled>
                </div>
                <div class="mt-2">
                    <label class="label">Image: </label>
                    <input class="form-control mb-2 import" id="product${id}Image" name="product${id}Image" type="file">
                </div>
            </div>
            <hr>
            `)
        })
        $("#shoeColors").show();
        $("#accessoriesImage").hide();
    }else{
        $("#shoeColors").hide();
        $("#accessoriesImage").show();
    }
}


const setColorButtons = colors => {
    $("#cbColors").empty();
    $("#colorInputs").empty();
    $("#otherColorInputs").empty();
    colors.map(color => {
        $("#cbColors").append(`
        <div class="cb-input">
            <input class="form-check-input" type="checkbox" id="cbColor${color}" value="${color}">
            <label class="label" for="cbColor${color}">${color}</label>
        </div>
        `)
    })
}
const setShoeColorButtons = () => {
    setColorButtons(shoeColors);
    $("#cbColors").append(`
    <button id="otherColorBtn" type="button" class="btn btn-primary"><i class="fa-solid fa-plus me-2"></i>Other</button>
    `);
}
setShoeColorButtons();

function dataURLtoFile(dataurl, filename) {
    var arr = dataurl.split(','),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[arr.length - 1]), 
        n = bstr.length, 
        u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, {type:mime});
}
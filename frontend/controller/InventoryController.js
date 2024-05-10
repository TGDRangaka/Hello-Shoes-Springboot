import {Item} from '../model/Item.js'
import {Inventory} from '../model/Inventory.js'
import {token} from '../db/data.js'

let otherBtnCount = 0;
let allItems = [];
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
        // get a image
        let img = item.inventoryItems[0].itemImage.image;
        // get inventory items by colors
        let colors = [];
        let imgsByColors = '';
        let itemsByColors = [];
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
        // console.log(itemsByColors);
        // return;
        // set table row through loop
                    // <img src="${colorItem.items[0].itemImage.image}" class="img-fluid" alt="">
        let rows = '';
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

        $("#inventoryItems").append(`
        <div class="accordion-item">
            <h2 class="accordion-header">
            <button class="accordion-button collapsed p-1" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapse${i}" aria-expanded="false" aria-controls="flush-collapse${i}">
                <div class="sale-accordion container-fluid">
                    <div class="row align-items-center">
                        <div class="col-1 d-flex justify-content-center align-items-center gap-3">
                            <span class="">${i+1}</span>
                            <img src="${img}" class="img-fluid table-img" alt="">
                        </div>
                        <div class="col">${item.description}</div>
                        <div class="col">${item.category}</div>
                        <div class="col">${item.supplierName}</div>
                        <div class="col-1">${colors.length}</div>
                        <div class="col-1">${item.unitPriceSale}</div>
                        <div class="col-1">${item.unitPriceBuy}</div>
                    </div>
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
                    </div>
                </div>
                <div class="row mt-3 p-3">
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
                </div>

            </div>
            </div>
        </div>
        `);
    })
}

let product = 'new';
$("#existingProductDetails").hide();

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

$('#cbColors').on('click', '#otherColorBtn',function() {
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
    let category = getValue("productByCategory");
    let verities = getValue("typesByVerities");
    let gender = getValue("typesByGender")
    let allProducts = [];

    let item = new Item(
        typeByOccasion + gender,
        getValue("productName"),
        category,
        getValue("productBySupplier"),
        null,
        getValue("productSellPrice"),
        getValue("productBuyPrice"),
        getValue("productExpectProfit"),
        getValue("productProfitMargin"),
        null
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
            // console.log($(`#${id}Inputs`));
            selectedColors.push(id);
        }
    }
    
    console.log(selectedColors);

    for(let color of selectedColors){
        let id = color;
        let img = (category === 'ACC') ? $(`#shampooNoClrImage`).prop('files')[0] : $(`#product${id}Image`).prop('files')[0];
        let colorName = id.includes("Other") ? $(`#product${id}Name`).val() : color;
        console.log(colorName);
        let reader = new FileReader();
        
        let inventoryItems = [];

        if(category !== 'ACC'){
            let sizes = [5,6,7,8,9,10,11];
            for(let i = 0; i < sizes.length; i++){
                let size = sizes[i];
                let inventoryItem = new Inventory("",`SIZE_${size}`, colorName,null,null,null,null,{id:"",image:""},[],[]);
                inventoryItems.push(inventoryItem);
            }
        }else{
            item.itemCode = verities;
            let size = (['SHMP', 'POLB', 'POLBR', 'POLDBR'].includes(verities)) ? 'NOT_APPLICABLE' : verities === 'SOF' ? 'FULL' : verities === 'SOH' ? 'HALF' : 'NOT_APPLICABLE';
            let inventoryItem = new Inventory("", size, colorName,null,null,null,null,{id:"",image:""},[],[]);
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

$("#typesByVerities").on('change', function(){
    // let val = $(this).val();
    // if(['SHMP', 'POLB', 'POLBR', 'POLDBR'].includes(val)){
    //     $("#shoeColors").hide();
    //     $("#accessoriesImage").show();
    // }else if(val === 'SOF' || val === 'SOH'){
    //     $("#shoeColors").show();
    //     $("#accessoriesImage").hide();
    //     setColorButtons(socksColors);
    // }
})

const getFirstLaters = text => {
    let ar = text.split(' ');
    let s = '';
    ar.map(t => {
        s+=t.charAt(0);
    })
    return s;
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
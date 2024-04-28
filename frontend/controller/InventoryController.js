import {Item} from '../model/Item.js'
import {Inventory} from '../model/Inventory.js'

let otherBtnCount = 0;
let isNewProduct = true;

const addInventoryItems = () => {
    for(let i = 0; i < 13; i++){
        let id = 'inventoryItem' + i;
    $("#inventoryItems").append(`
    <div class="accordion-item ">
        <div class="accordion-header">
            <button type="button" class="accordion-button p-2" data-bs-toggle="collapse" data-bs-target="#${id}" aria-expanded="true" aria-controls="${id}">
                <div class="inventory-item-header row w-100 text-theme">
                    <!-- <img class="h-100 col-2" src="assets/imgs/shoe1.png" alt=""> -->
                    <div class="col-1 d-flex justify-content-center">
                        <div class="img"></div>
                    </div>
                    <label class="col-2">FSM000234</label>
                    <label class="col-3">Nike Sneakers Blue</label>
                    <label class="col-2">SIZE 6</label>
                    <label class="col-2">Rs.3400</label>
                    <label class="col-2">39</label>
                </div>
            </button>
        </div>
        <div id="${id}" class="accordion-collapse collapse w-100" data-bs-parent="#inventoryItems">
            <div class="accordion-body m-2 w-100">
                <div class="inventory-item-body container-fluid row">
                    <div class="col-4 img"></div>
                    <div class="col-8 row">
                        <div class="inventory-body-detail col-6">
                            <label class="label">Product Name</label>
                            <h4>Nike Sneakers Blue</h4>
                        </div>
                        <div class="inventory-body-detail col-3">
                            <label class="label">Product ID</label>
                            <h4>FSM000234</h4>
                        </div>
                        <div class="inventory-body-detail col-3">
                            <label class="label">Size</label>
                            <h4>6</h4>
                        </div>

                        <div class="inventory-body-detail col-3">
                            <label class="label">Category</label>
                            <h4>Shoe</h4>
                        </div>
                        <div class="inventory-body-detail col-3">
                            <label class="label">Supplier</label>
                            <h4>Nike</h4>
                        </div>
                        <div class="inventory-body-detail col-3">
                            <label class="label">Colour</label>
                            <h4>Blue</h4>
                        </div>
                        <div class="inventory-body-detail col-3">
                            <label class="label">Stock</label>
                            <h4>39</h4>
                        </div>

                        <div class="inventory-body-detail col-3">
                            <label class="label">Sale Price</label>
                            <h4>3400</h4>
                        </div>
                        <div class="inventory-body-detail col-3">
                            <label class="label">Buy Price</label>
                            <h4>3090</h4>
                        </div>
                        <div class="inventory-body-detail col-3">
                            <label class="label">Profit Margin</label>
                            <h4>10%</h4>
                        </div>
                        <div class="inventory-body-detail col-3">
                            <label class="label">Expected Price</label>
                            <h4>3400</h4>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    `);
}
}

addInventoryItems();

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
        $("#colorImagesInputs").empty();
        selectedColors.map(color => {
            $("#colorImagesInputs").append(`
                <div id="${color}Inputs" class="mb-3">
                    <label for="" class="label ms-1 me-3 quicksand-bold">${color}</label>
                    <input class="form-control form-control-sm mb-2 import" id="product${color}Image" name="product${color}Image" type="file">
                    <div id="cb${color}Sizes">
                        <label class="label ms-1 import" for="">Sizes: </label>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="cb${color}Size5" value="SIZE 5">
                            <label class="label" for="cb${color}Size5">5</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="cb${color}Size6" value="SIZE 6">
                            <label class="label" for="cb${color}Size6">6</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="cb${color}Size7" value="SIZE 7">
                            <label class="label" for="cb${color}Size7">7</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="cb${color}Size8" value="SIZE 8">
                            <label class="label" for="cb${color}Size8">8</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="cb${color}Size9" value="SIZE 9">
                            <label class="label" for="cb${color}Size9">9</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="cb${color}Size10" value="SIZE 10">
                            <label class="label" for="cb${color}Size10">10</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="cb${color}Size11" value="SIZE 11">
                            <label class="label" for="cb${color}Size11">11</label>
                        </div>
                    </div>
                </div>
                <hr>
            `)
        })
    }
});

{/* <div id="cb${color}Gender">
<label class="label ms-1 import" for="">Gender: </label>
<div class="form-check form-check-inline">
    <input class="form-check-input" type="checkbox" id="cb${color}Men" value="MEN">
    <label class="label" for="cb${color}Men">Men</label>
</div>
<div class="form-check form-check-inline">
    <input class="form-check-input" type="checkbox" id="cb${color}Women" value="WOMEN">
    <label class="label" for="cb${color}Women">Women</label>
</div>
</div> */}

$("#otherColorBtn").click(function() {
    otherBtnCount++;
    let id = "Other" + otherBtnCount;
    $("#colorImagesInputs").append(`
        <div id="${id}Inputs" class="mb-3">
            <label for="" class="label ms-1 me-3">#${id} :</label><br>
            <input class="form-control form-control-sm import" id="product${id}Image" name="product${id}Image" type="file">
            <input class="form-control form-control-sm import" id="product${id}Name" name="product${id}Name" type="text" placeholder="Colour/Pattern Name*">
            <div id="cb${id}Sizes" class="">
                <label class="label ms-1 import">Sizes: </label>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="cb${id}Size5" value="SIZE 5">
                    <label class="label" for="cb${id}Size5">5</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="cb${id}Size6" value="SIZE 6">
                    <label class="label" for="cb${id}Size6">6</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="cb${id}Size7" value="SIZE 7">
                    <label class="label" for="cb${id}Size7">7</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="cb${id}Size8" value="SIZE 8">
                    <label class="label" for="cb${id}Size8">8</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="cb${id}Size9" value="SIZE 9">
                    <label class="label" for="cb${id}Size9">9</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="cb${id}Size10" value="SIZE 10">
                    <label class="label" for="cb${id}Size10">10</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="cb${id}Size11" value="SIZE 11">
                    <label class="label" for="cb${id}Size11">11</label>
                </div>
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

    console.log(item);

    let selectedColors = [];
    $('#cbColors .form-check-input').each(function() {
        if ($(this).is(':checked')) {
            selectedColors.push($(this).val());
        }
    });

    for(let color of selectedColors){
        let img = $(`#product${color}Image`).prop('files')[0];
        let reader = new FileReader();
        
        let sizes = [];
        let inventoryItems = [];

        for(let i = 5; i < 12; i++){
            $(`#cb${color}Size${i}`).is(':checked') && sizes.push(i);;
        }
        console.log(sizes);

        for(let i = 0; i < sizes.length; i++){
            let size = sizes[i];
            let inventoryItem = new Inventory(null,size, color,null,null,null,null,null);
            inventoryItems.push(inventoryItem);
        }

        let base64Image = await new Promise((resolve, reject) => {
            reader.onloadend = function(){
                resolve(reader.result);
            }
            reader.readAsDataURL(img);
        });

        (inventoryItems.length > 0) && (inventoryItems[0].itemImage = base64Image);
        allProducts.push(...inventoryItems);
    }

    console.log(allProducts);
    item.inventoryItems = allProducts;
})

const getValue = id => {
    return $("#" + id).val();
}
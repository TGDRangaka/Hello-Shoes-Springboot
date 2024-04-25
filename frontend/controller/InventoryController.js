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

$('#cbSizes').click(function() {
    var selectedSizes = [];

    $('#cbSizes .form-check-input').each(function() {
        if ($(this).is(':checked')) {
            selectedSizes.push($(this).val());
        }
    });

    console.log("Selected sizes: ", selectedSizes);
});

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
                <div class="mb-3">
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
                    <div id="cb${color}Gender">
                        <label class="label ms-1 import" for="">Gender: </label>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="cb${color}Men" value="MEN">
                            <label class="label" for="cb${color}Men">Men</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="cb${color}Women" value="WOMEN">
                            <label class="label" for="cb${color}Women">Women</label>
                        </div>
                    </div>
                </div>
                <hr>
            `)
        })
    }
});

$("#otherColorBtn").click(function() {
    otherBtnCount++;
    $("#colorImagesInputs").append(`
        <div class="mb-3">
            <label for="" class="label ms-1 me-3">Other #${otherBtnCount} :</label><br>
            <input class="form-control form-control-sm import" id="productOther${otherBtnCount}Image" name="productOther${otherBtnCount}Image" type="file">
            <input class="form-control form-control-sm import" id="productOther${otherBtnCount}Name" name="productOther${otherBtnCount}Name" type="text" placeholder="Colour/Pattern Name*">
            <div id="cbOther${otherBtnCount}Sizes" class="">
                <label class="label ms-1 import">Sizes: </label>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="cbOther${otherBtnCount}Size5" value="SIZE 5">
                    <label class="label" for="cbOther${otherBtnCount}Size5">5</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="cbOther${otherBtnCount}Size6" value="SIZE 6">
                    <label class="label" for="cbOther${otherBtnCount}Size6">6</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="cbOther${otherBtnCount}Size7" value="SIZE 7">
                    <label class="label" for="cbOther${otherBtnCount}Size7">7</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="cbOther${otherBtnCount}Size8" value="SIZE 8">
                    <label class="label" for="cbOther${otherBtnCount}Size8">8</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="cbOther${otherBtnCount}Size9" value="SIZE 9">
                    <label class="label" for="cbOther${otherBtnCount}Size9">9</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="cbOther${otherBtnCount}Size10" value="SIZE 10">
                    <label class="label" for="cbOther${otherBtnCount}Size10">10</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="cbOther${otherBtnCount}Size11" value="SIZE 11">
                    <label class="label" for="cbOther${otherBtnCount}Size11">11</label>
                </div>
            </div>
            <div id="cbOther${otherBtnCount}Gender">
                <label class="label ms-1 import" for="">Gender: </label>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="cbOther${otherBtnCount}men" value="MEN">
                    <label class="label" for="cbOther${otherBtnCount}men">Men</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="cbOther${otherBtnCount}women" value="WOMEN">
                    <label class="label" for="cbOther${otherBtnCount}women">Women</label>
                </div>
            </div>
        </div>
        <hr>
    `)
})
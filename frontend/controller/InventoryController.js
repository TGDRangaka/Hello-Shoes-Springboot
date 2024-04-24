const addInventoryItems = () => {
    for(let i = 0; i < 6; i++){
        let id = 'inventoryItem' + i;
    $("#inventoryItems").append(`
    <div class="accordion-item ">
    <div class="accordion-header">
        <button type="button" class="accordion-button p-2" data-bs-toggle="collapse" data-bs-target="#${id}" aria-expanded="true" aria-controls="${id}">
            <div class="inventory-item-header row w-100">
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

// addInventoryItems();
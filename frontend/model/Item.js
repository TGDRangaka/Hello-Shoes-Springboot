export class Item {
    constructor(itemCode, description, category, supplierName, supplier, unitPriceSale, unitPriceBuy, expectedProfit, profitMargin, inventoryItems){
        this.itemCode = itemCode;
        this.description = description;
        this.category = category;
        this.supplierName = supplierName;
        this.supplier = supplier;
        this.unitPriceSale = unitPriceSale;
        this.unitPriceBuy = unitPriceBuy;
        this.expectedProfit = expectedProfit;
        this.profitMargin = profitMargin;
        this.inventoryItems = inventoryItems;
    }
}
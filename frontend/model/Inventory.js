export class Inventory {
    constructor(inventoryCode, size, colors, originalQty, currentQty, status, item, itemImage, resupplyItems, saleItems){
        this.inventoryCode = inventoryCode;
        this.size = size;
        this.colors = colors;
        this.originalQty = originalQty;
        this.currentQty = currentQty;
        this.status = status;
        this.item = item;
        this.itemImage = itemImage;
        this.resupplyItems = resupplyItems;
        this.saleItems = saleItems;
    }
}
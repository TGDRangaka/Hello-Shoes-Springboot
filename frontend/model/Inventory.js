export class Inventory {
    constructor(inventoryCode, size, color, originalQty, currentQty, status, item, itemImage){
        this.inventoryCode = inventoryCode;
        this.size = size;
        this.color = color;
        this.originalQty = originalQty;
        this.currentQty = currentQty;
        this.status = status;
        this.item = item;
        this.itemImage = itemImage;
    }
}
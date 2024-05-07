export class Refund{
    constructor(refundId, description, refundDate, qty, refundTotal, employee, saleItem){
        this.refundId = refundId;
        this.description = description;
        this.refundDate = refundDate;
        this.qty = qty;
        this.refundTotal = refundTotal;
        this.employee = employee;
        this.saleItem = saleItem;
    }
}
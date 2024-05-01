export class Sale {
    constructor(orderId, totalPrice, paymentMethod, addedPoints, employee, customer, saleItems){
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.addedPoints = addedPoints;
        this.employee = employee;
        this.customer = customer;
        this.saleItems = saleItems;
    }
}
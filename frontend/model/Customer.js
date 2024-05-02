export class Customer {
    constructor(customerCode, name, gender, joinedDateAsLoyalty, level, totalPoints, dob,
    addressNo, addressLane, addressCity, addressState, postalCode, email, phone, recentPurchaseDateTime){
        this.customerCode = customerCode;
        this.name = name;
        this.gender = gender;
        this.joinedDateAsLoyalty = joinedDateAsLoyalty;
        this.level = level;
        this.totalPoints = totalPoints;
        this.dob = dob;
        this.addressNo = addressNo;
        this.addressLane = addressLane;
        this.addressCity = addressCity;
        this.addressState = addressState;
        this.postalCode = postalCode;
        this.email = email;
        this.phone = phone;
        this.recentPurchaseDateTime = recentPurchaseDateTime;
    }
}
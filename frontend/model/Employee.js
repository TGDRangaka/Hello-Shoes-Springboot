export class Employee {
    constructor(employeeCode, name, profilePic, gender, status, designation, dob, joinedDate, branch, addressNo, addressLane, addressCity, addressState, postalCode, email, phone, guardianOrNominatedPerson, emergencyContact) {
        this.employeeCode = employeeCode;
        this.name = name;
        this.profilePic = profilePic;
        this.gender = gender;
        this.status = status;
        this.designation = designation;
        this.dob = dob;
        this.joinedDate = joinedDate;
        this.branch = branch;
        this.addressNo = addressNo;
        this.addressLane = addressLane;
        this.addressCity = addressCity;
        this.addressState = addressState;
        this.postalCode = postalCode;
        this.email = email;
        this.phone = phone;
        this.guardianOrNominatedPerson = guardianOrNominatedPerson;
        this.emergencyContact = emergencyContact;
    }
}

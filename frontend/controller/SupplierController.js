import { token } from "../db/data.js";
import { Supplier } from "../model/Supplier.js";

let allSuppliers = [];

$("#suppliersBtn").click(()=>{
    // getAllSuppliers();
})

const saveSupplier = (supplier) => {
    var settings = {
        "url": "http://localhost:8080/api/v1/supplier",
        "method": "POST",
        "timeout": 0,
        "headers": {
          "Content-Type": "application/json",
          "Authorization": "Bearer " + token
        },
        "data": JSON.stringify(supplier),
      };
      
      $.ajax(settings).done(function (response) {
        // console.log(response);
      });
}

const getSupplierData = () => {
    let supplierData = new Supplier();

    // Collect identity data
    supplierData.name = $('#supplierName').val();
    supplierData.category = $('#supplierCategory').val();

    // Collect address data
    supplierData.addressNo = $('#supplierAddressNo').val();
    supplierData.addressLane = $('#supplierAddressLane').val();
    supplierData.addressCity = $('#supplierAddressCity').val();
    supplierData.addressState = $('#supplierAddressState').val();
    supplierData.postalCode = $('#supplierAddressPostalCode').val();
    supplierData.originCountry = $('#supplierAddressCountry').val();

    // Collect contact data
    supplierData.contactNo1 = $('#supplierContactNo1').val();
    supplierData.contactNo2 = $('#supplierContactNo2').val();
    supplierData.email = $('#supplierEmail').val();

    return supplierData;
}

$("#submitSupplierBtn").click(()=> {
    let supplierData = getSupplierData();
    saveSupplier(supplierData);
})
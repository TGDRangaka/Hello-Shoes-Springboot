import { token } from "../db/data.js";
import { Supplier } from "../model/Supplier.js";

let allSuppliers = [];
let selectedSupplier = null;
let isSupplierSelected = false;

$("#suppliersBtn").click(()=>{
    getAllSuppliers();
})

const getAllSuppliers = ()=>{
  if(token){
    var settings = {
      "url": "http://localhost:8080/api/v1/supplier",
      "method": "GET",
      "timeout": 0,
      "headers": {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + token
      },
    };
    
    $.ajax(settings).done(function (response) {
      // console.log(response);
      allSuppliers = response;
      loadTable(allSuppliers);
    });
  }
}

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

const updateSupplier = (supplier) => {
  var settings = {
    "url": "http://localhost:8080/api/v1/supplier/" + selectedSupplier.code,
    "method": "PUT",
    "timeout": 0,
    "headers": {
      "Content-Type": "application/json",
      "Authorization": "Bearer " + token
    },
    "data": JSON.stringify(supplier),
  };
  
  $.ajax(settings).done(function (response) {
    console.log(response);
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

const setSupplierData = (supplier) => {
  $('#supplierName').val(supplier.name);
  $('#supplierCategory').val(supplier.category);
  $('#supplierAddressNo').val(supplier.addressNo);
  $('#supplierAddressLane').val(supplier.addressLane);
  $('#supplierAddressCity').val(supplier.addressCity);
  $('#supplierAddressState').val(supplier.addressState);
  $('#supplierAddressPostalCode').val(supplier.postalCode);
  $('#supplierAddressCountry').val(supplier.originCountry);
  $('#supplierContactNo1').val(supplier.contactNo1);
  $('#supplierContactNo2').val(supplier.contactNo2);
  $('#supplierEmail').val(supplier.email);
}

const loadTable = (suppliers) => {
  $("#supplierTbody").empty();
  suppliers.map((supplier, i) => {
    $("#supplierTbody").append(`
    <tr>
        <td>${i + 1}</td>
        <td class="text-start">${supplier.name}</td>
        <td>${supplier.category}</td>
        <td>${supplier.email}</td>
        <td>${supplier.contactNo1}</td>
        <td>${supplier.contactNo2}</td>
        <td>${supplier.originCountry}</td>
        <td class="table-action"><button data-index="${i}" class="btn"><i class="fa-solid fa-pen"></i></button></i></td>
    </tr>
    `)
  })
}

$("#submitSupplierBtn").click(()=> {
    let supplierData = getSupplierData();
    !isSupplierSelected ? saveSupplier(supplierData) : updateSupplier(supplierData);
})

$("#supplierTbody").on('click', 'button', function(){
  let index = $(this).data('index');
  isSupplierSelected = true;
  selectedSupplier = allSuppliers[index];
  $("#supplier").toggle();
  $("#supplierForm").toggle();
  setSupplierData(selectedSupplier);
})
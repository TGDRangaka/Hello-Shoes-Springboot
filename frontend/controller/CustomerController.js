import {token, setToken} from '../db/data.js';
import { Customer } from '../model/Customer.js';

let allCustomers = [];
let customerData = new Customer();
let isCustomerSelected = false;

$("#customerBtn").click(()=>{
    getAllCustomers();
})

const getAllCustomers = ()=>{
    if(token){
        var settings = {
            "url": "http://localhost:8080/api/v1/customer",
            "method": "GET",
            "timeout": 0,
            "headers": {
              "Authorization": `Bearer ${token}`
            },
          };
          
          $.ajax(settings).done(function (response) {
            allCustomers = response;
            loadCustomerTable(allCustomers);
          });
    }
}

const saveCustomer = (customer) => {
    var settings = {
        "url": "http://localhost:8080/api/v1/customer",
        "method": "POST",
        "timeout": 0,
        "headers": {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        },
        "data": JSON.stringify(customer),
      };
      
      $.ajax(settings).done(function (response) {
        console.log(response);
        $("#customerFormBtn").click();
      });
}

const updateCustomer = (customer) => {
    var settings = {
        "url": "http://localhost:8080/api/v1/customer/" + customer.customerCode,
        "method": "PUT",
        "timeout": 0,
        "headers": {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        },
        "data": JSON.stringify(customer),
      };
      
      $.ajax(settings).done(function (response) {
        console.log(response);
        $("#customerFormBtn").click();
      });
}

$("#customerSubmitBtn").click(()=>{
    let customer = collectCustomerData();
    if(isCustomerSelected){
        customer.customerCode = customerData.customerCode;
        updateCustomer(customer);
    }else{
        saveCustomer(customer);
    }
})

const loadCustomerTable = customers => {
    $("#customersTBody").empty();
    const getData = data =>{
        return data ? data : 'N/A';
    }
    customers.map((cus,i) => {
        $("#customersTBody").append(`
        <tr class="text-center align-middle">
            <td class="d-flex flex-column align-items-center">
                <img src="assets/icons/${cus.level}-level.png" alt="" class="">
                <label class="label">${cus.totalPoints}</label>
            </td>
            <td>${cus.name}</td>
            <td>${getData(cus.phone)}</td>
            <td>${getData(cus.email)}</td>
            <td>${getData(cus.dob)}</td>
            <td>${getData(cus.joinedDateAsLoyalty)}</td>
            <td>${getData(cus.gender)}</td>
            <td>${getData(cus.recentPurchaseDateTime)}</td>
            <td class="table-action"><button data-index=${i} class="btn"><i class="fa-solid fa-pen"></i></button></i></td>
        </tr>
        `)
    })
}

function collectCustomerData() {

    // Collect identity data
    customerData.name = $('#customerName').val();
    customerData.dob = $('#customerDOB').val();
    customerData.gender = $('#customerGender').val();

    // Collect address data
    customerData.addressNo = $('#customerAddressNo').val();
    customerData.addressLane = $('#customerAddressLane').val();
    customerData.addressCity = $('#customerAddressCity').val();
    customerData.addressState = $('#customerAddressState').val();
    customerData.postalCode = $('#customerAddressPostcode').val();

    // Collect contact data
    customerData.phone = $('#customerContactNo').val();
    customerData.email = $('#customerEmail').val();

    return customerData;
}

const setCustomerData = customer => {
    // Set identity data
    $('#customerName').val(customer.name);
    $('#customerDOB').val(customer.dob);
    $('#customerGender').val(customer.gender);

    // Set address data
    $('#customerAddressNo').val(customer.addressNo);
    $('#customerAddressLane').val(customer.addressLane);
    $('#customerAddressCity').val(customer.addressCity);
    $('#customerAddressState').val(customer.addressState);
    $('#customerAddressPostcode').val(customer.postalCode);

    // Set contact data
    $('#customerContactNo').val(customer.phone);
    $('#customerEmail').val(customer.email);
}

$("#customersTBody").on('click', '.btn', function() {
    let index = $(this).data('index');
    let customer = allCustomers[index];
    
    customerData = customer;
    isCustomerSelected = true;
    setCustomerData(customer);
    $("#customerFormBtn").click();
})

$("#customerFormBtn").click(function(){
    $("#customer").toggle();
    $("#addCustomer").toggle();
})

$("#customerCancelBtn").click(function(){
    customerData = new Customer();
    isCustomerSelected = false;
})
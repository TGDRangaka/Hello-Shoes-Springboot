import {token} from '../db/data.js';
import { getRegex, setAsValid, setAsInvalid } from '../util/UtilMatter.js';
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
    let isValidData = checkValidations(customer);
    if(isValidData){
        if(isCustomerSelected){
            updateCustomer(customer);
        }else{
            saveCustomer(customer);
        }
    }
})

const loadCustomerTable = customers => {

    // sort customer table if have a value
    if($("#customerSortSelect").val() !== 'NONE'){
        sortCustomerTable();
    }

    $("#customersTBody").empty();
    const getData = data =>{
        return data ? data : 'N/A';
    }
    customers.map((cus,i) => {

        // filter customer if have a value
        if(!isInSelectedLevel(cus)) return;
        // search
        if(!isInSearchedKeyword(cus)) return;

        $("#customersTBody").append(`
        <tr class="align-middle">
            <td class="customer-points level-${cus.level.toLowerCase().charAt(0)}"><div>${cus.totalPoints}</div></td>
            <td>${cus.name}</td>
            <td>${getData(cus.phone)}</td>
            <td>${getData(cus.email)}</td>
            <td>${getData(cus.dob)}</td>
            <td>${getData(cus.joinedDateAsLoyalty)}</td>
            <td>${getData(cus.gender)}</td>
            <td class="text-center">${getData(cus.recentPurchaseDateTime)}</td>
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

const checkValidations = (customer) => {
    
    // General
    return (getRegex('name').test(customer.name) ? setAsValid("#customerName", 'Looks Good!')
    : setAsInvalid("#customerName", 'Name is required!'))
    &
    (!isNaN(Date.parse(customer.dob)) ? setAsValid("#customerDOB", 'Looks Good!')
    : setAsInvalid("#customerDOB", 'Please select a valid date.'))
    &
    (customer.gender ? setAsValid("#customerGender", 'Looks Good!')
    : setAsInvalid("#customerGender", 'Please select a gender.'))
    &
    // Address
    (getRegex('address').test(customer.addressNo) ? setAsValid("#customerAddressNo", 'Looks Good!')
    : setAsInvalid("#customerAddressNo", 'Please enter valid address no.'))
    &
    (getRegex('address').test(customer.addressLane) ? setAsValid("#customerAddressLane", 'Looks Good!')
    : setAsInvalid("#customerAddressLane", 'Please enter valid address lane'))
    &
    (getRegex('address').test(customer.addressCity) ? setAsValid("#customerAddressCity", 'Looks Good!')
    : setAsInvalid("#customerAddressCity", 'Please enter valid address city'))
    &
    (getRegex('address').test(customer.addressState) ? setAsValid("#customerAddressState", 'Looks Good!')
    : setAsInvalid("#customerAddressState", 'Please enter valid address state'))
    &
    (Number.isInteger(parseInt(customer.postalCode)) ? setAsValid("#customerAddressPostcode", 'Looks Good!')
    : setAsInvalid("#customerAddressPostcode", 'Please enter valid address postalcode'))
    &
    // Contacts
    (getRegex('phone').test(customer.phone) ? setAsValid("#customerContactNo", 'Looks Good!')
    : setAsInvalid("#customerContactNo", 'Please enter valid number'))
    &
    (getRegex('email').test(customer.email) ? setAsValid("#customerEmail", 'Looks Good!')
    : setAsInvalid("#customerEmail", 'Please enter valid email'));
}

// sorting
$("#customer header select").on('change', function(){
    loadCustomerTable(allCustomers);
})
const sortCustomerTable = () => {
    let sortType = $("#customerSortSelect").val();

    if(sortType === 'A-Z'){
        allCustomers.sort((a,b) => a.name.localeCompare(b.name));
    }else if(sortType === 'Z-A'){
        allCustomers.sort((a,b) => b.name.localeCompare(a.name));
    }else if(sortType === 'LOW-HIGH'){
        allCustomers.sort((a,b) => a.totalPoints - b.totalPoints);
    }else if(sortType === 'HIGH-LOW'){
        allCustomers.sort((a,b) => b.totalPoints - a.totalPoints);
    }else if(sortType === 'Latest-JOINED'){
        allCustomers.sort((a,b) => new Date(b.joinedDateAsLoyalty) - new Date(a.joinedDateAsLoyalty));
    }else if(sortType === 'Oldest-JOINED'){
        allCustomers.sort((a,b) => new Date(a.joinedDateAsLoyalty) - new Date(b.joinedDateAsLoyalty));
    }
}

// filter out
const isInSelectedLevel = (customer) => {
    let selectedLevel = $("#customerLeevlSelect").val();
    if(selectedLevel === 'ALL'){
        return true;
    }
    return customer.level === selectedLevel;
}

// search
$("#customerSearchBtn").click(function(){
    loadCustomerTable(allCustomers);
});
const isInSearchedKeyword = (customer) => {
    let keyword = $("#customerSearchInput").val();
    keyword = keyword.toLowerCase();
    if(keyword === ''){
        return true;
    }
    return customer.name.toLowerCase().includes(keyword) || 
    customer.phone.toLowerCase().includes(keyword) || 
    customer.email.toLowerCase().includes(keyword) || 
    customer.addressNo.toLowerCase().includes(keyword) || 
    customer.addressLane.toLowerCase().includes(keyword) || 
    customer.addressCity.toLowerCase().includes(keyword);
}
import {token, user, setUser} from '../db/data.js';
import {Employee} from '../model/Employee.js'

let allEmployees = [];
let employeeData = new Employee();
let isEmployeeSelected = false;

$("#employeesBtn").click(()=>{
    getAllEmployees();
})

const getAllEmployees = () => {
    let settings = {
      "url": "http://localhost:8080/api/v1/employee",
      "method": "GET",
      "timeout": 0,
      "headers": {
        "Authorization": `Bearer ${token}`
      },
    };
    
    $.ajax(settings).done(function (response) {
        allEmployees = response;
        // allEmployees.map(emp => {
        //     let img = new Image();
        //     img.src = 'data:image/png;base64,'+emp.profilePic;
        //     emp.profilePic = img;
        // })
        console.log(allEmployees);
        loadEmployeeTable(allEmployees);
    });
}

const loadEmployeeTable = (employees) => {
    $("#employeesTBody").empty();
    employees.map((employee, i) => {
        let img = employee.profilePic;
        $("#employeesTBody").append(`
        <tr class="align-middle">
            <td class="table-img">
                <img src="data:image;base64,${img}" alt="profilePic">
            </td>
            <td class="ps-4">
                <div class="d-flex flex-column text-start">
                    <label class="quicksand-bold">${employee.name}</label>
                    <h6 class="label quicksand-thin">${employee.designation}</h6>
                </div>
            </td>
            <td>${employee.phone}</td>
            <td>${employee.email}</td>
            <td>${employee.dob.substring(0, 10)}</td>
            <td>${employee.joinedDate.substring(0, 10)}</td>
            <td>${employee.gender}</td>
            <td>${employee.guardianOrNominatedPerson}</td>
            <td>${employee.emergencyContact}</td>
            <td class="table-action"><button data-index=${i} class="btn edit"><i class="fa-solid fa-pen"></i></button></i></td>
            <td class="table-action"><button data-index=${i} class="btn delete"><i class="fa-solid fa-pen"></i></button></i></td>
        </tr>
        `);
    })
}

function saveEmployee() {
    let formData = collectEmployeeData();

    var settings = {
        "url": "http://localhost:8080/api/v1/employee",
        "method": "POST",
        "timeout": 0,
        "headers": {
            "Authorization": `Bearer ${token}`
          },
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": formData
      };
      
      $.ajax(settings).done(function (response) {
        console.log(response);
      });
}

function updateEmployee(formData) {
    // return;
    var settings = {
        "url": "http://localhost:8080/api/v1/employee/" + employeeData.employeeCode,
        "method": "PUT",
        "timeout": 0,
        "headers": {
            "Authorization": `Bearer ${token}`
          },
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": formData
      };
      
      $.ajax(settings).done(function (response) {
        console.log(JSON.parse(response));
        setUser(JSON.parse(response));
      });
}

const collectEmployeeData = ()=>{
    let formData = new FormData();

    // Identity section
    formData.append('profilePic', $('#employeeProfilePic')[0].files[0]);
    formData.append('name', $('#employeeName').val());
    formData.append('gender', $('#employeeGender').val());
    formData.append('dob', $('#employeeDob').val());
    formData.append('status', $('#employeeStatus').val());

    // Store section
    formData.append('designation', $('#employeeDesignation').val());
    formData.append('branch', $('#employeeBranch').val());
    formData.append('joinedDate', $('#employeeJoinedDate').val());
    formData.append('guardian', $('#employeeGurdian').val());
    formData.append('emergencyNumber', $('#employeeEmergencyNumber').val());

    // Address section
    formData.append('addressNo', $('#employeeAddressNo').val());
    formData.append('addressLane', $('#employeeAddressLane').val());
    formData.append('addressCity', $('#employeeAddressCity').val());
    formData.append('addressState', $('#employeeAddressState').val());
    formData.append('addressPostalCode', $('#employeeAddressPostcode').val());

    // Contacts section
    formData.append('email', $('#email').val());
    formData.append('phone', $('#phone').val());
    return formData;
}

const setEmployeeData = employee => {
    // Identity section
// console.log(employee.profilePic);
    $(".employee-img").css('background-image', `url(data:image/jpeg;base64,${user.profilePic})`);
    $('#employeeProfilePic')[0].files[0];
    $('#employeeName').val(employee.name);
    $('#employeeGender').val(employee.gender);
    $('#employeeDob').val(employee.dob);
    $('#employeeStatus').val(employee.status);

    // Store section
    $('#employeeDesignation').val(employee.designation);
    $('#employeeBranch').val(employee.branch);
    $('#employeeJoinedDate').val(employee.joinedDate);
    $('#employeeGurdian').val(employee.guardianOrNominatedPerson);
    $('#employeeEmergencyNumber').val(employee.emergencyContact);

    // Address section
    $('#employeeAddressNo').val(employee.addressNo);
    $('#employeeAddressLane').val(employee.addressLane);
    $('#employeeAddressCity').val(employee.addressCity);
    $('#employeeAddressState').val(employee.addressState);
    $('#employeeAddressPostcode').val(employee.postalCode);

    // Contacts section
    $('#email').val(employee.email);
    $('#phone').val(employee.phone);
}

const setProfileData = employee => {
    // console.log(employee);
    // Set the profile picture
    if (employee.profilePic) {
        $(".profile-img").css('background-image', `url(data:image/jpeg;base64,${employee.profilePic})`);
    }
    
    // Set other user data
    $("#profileEmail").val(employee.email);
    $("#userPhone").val(employee.phone);
    $("#userEmergencyNumber").val(employee.emergencyContact);
    $("#userAddressNo").val(employee.addressNo);
    $("#userAddressLane").val(employee.addressLane);
    $("#userAddressCity").val(employee.addressCity);
    $("#userAddressState").val(employee.addressState);
    $("#userAddressPostcode").val(employee.postalCode);
}

const collectProfileData = () => {
        employeeData = user;
        let formData = new FormData();
        console.log(employeeData);

        // Identity section
        formData.append('profilePic', $('#userProfilePic')[0].files[0]);    //
        formData.append('name', employeeData.name);
        formData.append('gender', employeeData.gender);
        formData.append('dob', employeeData.dob);
        formData.append('status', employeeData.status);
    
        // Store section
        formData.append('designation', employeeData.designation);
        formData.append('branch', employeeData.branch);
        formData.append('joinedDate', employeeData.joinedDate);
        formData.append('guardian', employeeData.guardianOrNominatedPerson);
        formData.append('emergencyNumber', $("#userEmergencyNumber").val());    //
    
        // Address section
        formData.append('addressNo', $('#userAddressNo').val());    //
        formData.append('addressLane', $('#userAddressLane').val());    //
        formData.append('addressCity', $('#userAddressCity').val());    //
        formData.append('addressState', $('#userAddressState').val());    //
        formData.append('addressPostalCode', $('#userAddressPostcode').val());    //
    
        // Contacts section
        formData.append('email', $('#profileEmail').val());    //
        formData.append('phone', $('#userPhone').val());    //

        return formData;
}

$('#userProfilePic').on('input', () => {
    let img = $('#userProfilePic')[0].files[0];
    let imgUrl = URL.createObjectURL(img);
    $(".profile-img").css('background-image', `url(${imgUrl})`);
;})

$('#submitEmployeeBtn').on('click', ()=>{
    if(isEmployeeSelected){
        let formData = collectEmployeeData();
        if(!$('#employeeProfilePic')[0].files[0]){
            formData.append('profilePic', dataURLtoFile("data:image/png;base64,"+employeeData.profilePic, 'profilePic'))
            console.log('set prev img')
        }
        updateEmployee(formData);
    }
});

$('#submitProfileBtn').on('click', ()=>{
    if(isEmployeeSelected){
        let formData = collectProfileData();
        if(!$('#userProfilePic')[0].files[0]){
            formData.append('profilePic', dataURLtoFile("data:image/png;base64,"+employeeData.profilePic, 'profilePic'))
            console.log('set prev img')
        }
        updateEmployee(formData);
    }else{
        saveEmployee();
    }
});

$("#employeesTBody").on('click', '.edit', function(){
    let index = $(this).data('index');
    let employee = allEmployees[index];
    console.log(employee);
    employeeData = employee;
    isEmployeeSelected = true;
    setEmployeeData(employee);
    $("#registerEmployeeBtn").click();
})

$("#registerEmployeeBtn").click(()=>{
    $("#employee").toggle();
    $("#addEmployee").toggle();
})

function dataURLtoFile(dataurl, filename) {
    var arr = dataurl.split(','),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[arr.length - 1]), 
        n = bstr.length, 
        u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, {type:mime});
}

//Usage example:
// var file = dataURLtoFile('data:text/plain;base64,aGVsbG8=','hello.txt');
// console.log(file);

$("#loginBtn").click(()=>{
    console.log(user);
    isEmployeeSelected = true;
    employeeData = user;
    setProfileData(user);
    $("section").hide();
    $("#profile").show();
})
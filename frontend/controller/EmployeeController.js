import {token} from '../db/data.js';
import {Employee} from '../model/Employee.js'

let allEmployees = [];

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
        $("#employeesTBody").append(`
        <tr class="text-center align-middle">
            <td class="table-img">
                <img src="data:image/png;base64,${employee.profilePic}" alt="profilePic">
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
            <td class="table-action"><button data-index=${i} class="btn"><i class="fa-solid fa-pen"></i></button></i></td>
            <td class="table-action"><button data-index=${i} class="btn"><i class="fa-solid fa-pen"></i></button></i></td>
        </tr>
        `);
    })
}

function submitEmployeeForm() {
    // Create a new FormData object to store form data
    var formData = new FormData();

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
    formData.append('guardian', $('#employeeGuardian').val());
    formData.append('emergencyNumber', $('#employeeEmergencyNumber').val());

    // Address section
    formData.append('addressNo', $('#employeeAddressNo').val());
    formData.append('addressLane', $('#employeeAddressLane').val());
    formData.append('addressCity', $('#employeeAddressCity').val());
    formData.append('addressState', $('#employeeAddressState').val());
    formData.append('addressPostcode', $('#employeeAddressPostcode').val());

    // Contacts section
    formData.append('email', $('#email').val());
    formData.append('phone', $('#phone').val());

    // Send the form data to the server using jQuery Ajax
    var settings = {
        "url": "http://localhost:8080/api/v1/employee",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": formData
      };
      
      $.ajax(settings).done(function (response) {
        console.log(response);
      });
}

// Add event listener to the submit button
$('button.btn-primary').on('click', submitEmployeeForm);

import { token, user, setUser, userRole } from '../db/data.js';
import { Employee } from '../model/Employee.js'
import { getRegex, setAsInvalid, setAsValid, clearValidations, showSuccessAlert, showErrorAlert } from '../util/UtilMatter.js';

let allEmployees = [];
let employeeData = new Employee();
let isEmployeeSelected = false;

$("#employeesBtn").click(() => {
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
    // sort employees if have value
    if ($("#employeeSortSelect").val() !== 'NONE') {
        sortEmployeeTable();
    }

    $("#employeesTBody").empty();
    employees.map((employee, i) => {

        // filter employees if have value
        if (!isInSearchedKeyword(employee)) return;

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
            ${userRole != 'ADMIN' ? '' : `<td class="table-action"><button data-index=${i} class="btn edit"><i class="fa-solid fa-pen"></i></button></i></td>`}
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
        showSuccessAlert("Employee saved successfully");
        clearValidations("#addEmployee form");
        $("#employeesBtn").click();
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showErrorAlert("An error occurred while saving the employee");
        console.error("Error details:", textStatus, errorThrown, jqXHR);
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
        showSuccessAlert("Employee updated successfully")
        console.log(JSON.parse(response));
        clearValidations("#addEmployee form");
        $("#employeesBtn").click();
    })
        .fail(function (jqXHR, textStatus, errorThrown) {
            showErrorAlert("An error occurred while updating the employee");
            console.error("Error details:", textStatus, errorThrown, jqXHR);
        });
}

const collectEmployeeData = () => {
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
    $(".employee-img").css('background-image', `url(data:image/jpeg;base64,${employee.profilePic})`);
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

const checkValidations = () => {
    // General
    return ($('#employeeName').val().length > 4 ? setAsValid("#employeeName", 'Looks Good!')
        : setAsInvalid("#employeeName", 'Name is required!'))
        &
        (!isNaN(Date.parse($('#employeeDob').val())) ? setAsValid("#employeeDob", 'Looks Good!')
            : setAsInvalid("#employeeDob", 'Please select a valid date.'))
        &
        ($('#employeeGender').val() ? setAsValid("#employeeGender", 'Looks Good!')
            : setAsInvalid("#employeeGender", 'Please select a gender.'))
        &
        ($('#employeeStatus').val() ? setAsValid("#employeeStatus", 'Looks Good!')
            : setAsInvalid("#employeeStatus", 'Please enter status of employee'))
        &

        // Store
        ($('#employeeDesignation').val().length > 4 ? setAsValid("#employeeDesignation", 'Looks Good!')
            : setAsInvalid("#employeeDesignation", 'Please enter designation.'))
        &
        (getRegex('name').test($('#employeeBranch').val()) ? setAsValid("#employeeBranch", 'Looks Good!')
            : setAsInvalid("#employeeBranch", 'Please enter branch name.'))
        &
        (!isNaN(Date.parse($('#employeeJoinedDate').val())) ? setAsValid("#employeeJoinedDate", 'Looks Good!')
            : setAsInvalid("#employeeJoinedDate", 'Please select a valid date.'))
        &
        ($('#employeeGurdian').val().length > 4 ? setAsValid("#employeeGurdian", 'Looks Good!')
            : setAsInvalid("#employeeGurdian", 'Please enter gurdian or nominated person'))
        &
        (getRegex('phone').test($('#employeeEmergencyNumber').val()) ? setAsValid("#employeeEmergencyNumber", 'Looks Good!')
            : setAsInvalid("#employeeEmergencyNumber", 'Please enter emergency number'))
        &

        // Address
        (getRegex('address').test($('#employeeAddressNo').val()) ? setAsValid("#employeeAddressNo", 'Looks Good!')
            : setAsInvalid("#employeeAddressNo", 'Please enter valid address no.'))
        &
        (getRegex('address').test($('#employeeAddressLane').val()) ? setAsValid("#employeeAddressLane", 'Looks Good!')
            : setAsInvalid("#employeeAddressLane", 'Please enter valid address lane'))
        &
        (getRegex('address').test($('#employeeAddressCity').val()) ? setAsValid("#employeeAddressCity", 'Looks Good!')
            : setAsInvalid("#employeeAddressCity", 'Please enter valid address city'))
        &
        (getRegex('address').test($('#employeeAddressState').val()) ? setAsValid("#employeeAddressState", 'Looks Good!')
            : setAsInvalid("#employeeAddressState", 'Please enter valid address state'))
        &
        (Number.isInteger(parseInt($('#employeeAddressPostcode').val())) ? setAsValid("#employeeAddressPostcode", 'Looks Good!')
            : setAsInvalid("#employeeAddressPostcode", 'Please enter valid address postalcode'))
        &

        // Contacts
        (getRegex('phone').test($('#phone').val()) ? setAsValid("#phone", 'Looks Good!')
            : setAsInvalid("#phone", 'Please enter valid number'))
        &
        (getRegex('email').test($('#email').val()) ? setAsValid("#email", 'Looks Good!')
            : setAsInvalid("#email", 'Please enter valid email'));
}

const checkProfileValidations = () => {
    return (getRegex('email').test($('#profileEmail').val()) ? setAsValid("#profileEmail", 'Looks Good!')
        : setAsInvalid("#profileEmail", 'Email is required!'))
        &
        (getRegex('phone').test($('#userPhone').val()) ? setAsValid("#userPhone", 'Looks Good!')
            : setAsInvalid("#userPhone", 'Please enter valid number'))
        &
        (getRegex('phone').test($('#userEmergencyNumber').val()) ? setAsValid("#userEmergencyNumber", 'Looks Good!')
            : setAsInvalid("#userEmergencyNumber", 'Please enter emergency number'))
        &

        // Address
        (getRegex('address').test($('#userAddressNo').val()) ? setAsValid("#userAddressNo", 'Looks Good!')
            : setAsInvalid("#userAddressNo", 'Please enter valid address no.'))
        &
        (getRegex('address').test($('#userAddressLane').val()) ? setAsValid("#userAddressLane", 'Looks Good!')
            : setAsInvalid("#userAddressLane", 'Please enter valid address lane'))
        &
        (getRegex('address').test($('#userAddressCity').val()) ? setAsValid("#userAddressCity", 'Looks Good!')
            : setAsInvalid("#userAddressCity", 'Please enter valid address city'))
        &
        (getRegex('address').test($('#userAddressState').val()) ? setAsValid("#userAddressState", 'Looks Good!')
            : setAsInvalid("#userAddressState", 'Please enter valid address state'))
        &
        (Number.isInteger(parseInt($('#userAddressPostcode').val())) ? setAsValid("#userAddressPostcode", 'Looks Good!')
            : setAsInvalid("#userAddressPostcode", 'Please enter valid address postalcode'));
}

$('#userProfilePic').on('input', () => {
    let img = $('#userProfilePic')[0].files[0];
    let imgUrl = URL.createObjectURL(img);
    $(".profile-img").css('background-image', `url(${imgUrl})`);
    ;
})

$('#employeeProfilePic').on('input', () => {
    let img = $('#employeeProfilePic')[0].files[0];
    let imgUrl = URL.createObjectURL(img);
    $(".employee-img").css('background-image', `url(${imgUrl})`);
    ;
})

$('#submitEmployeeBtn').on('click', () => {
    if (checkValidations()) {
        let formData = collectEmployeeData();
        if (isEmployeeSelected) {
            if (!$('#employeeProfilePic')[0].files[0]) {
                formData.append('profilePic', dataURLtoFile("data:image/png;base64," + employeeData.profilePic, 'profilePic'))
                console.log('set prev img')
            }
            updateEmployee(formData);
        } else {
            if ($('#employeeProfilePic')[0].files[0]) {
                saveEmployee(formData);
            } else {
                setAsInvalid("#employeeProfilePic", 'Please select employee profile picture.')
            }
        }
    }
});

$("#cancelEmployeeBtn").click(() => {
    employeeData = new Employee();
    isEmployeeSelected = false;
    clearValidations("#addEmployee form");
});

$('#submitProfileBtn').on('click', () => {
    if (checkProfileValidations() && isEmployeeSelected) {
        let formData = collectProfileData();
        if (!$('#userProfilePic')[0].files[0]) {
            formData.append('profilePic', dataURLtoFile("data:image/png;base64," + employeeData.profilePic, 'profilePic'))
            console.log('set prev img')
        }
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
            showSuccessAlert("Profile updated successfully")
            console.log(JSON.parse(response));
            setUser(JSON.parse(response));
            clearValidations("#profile form");
            $(".userName").text(user.name);
            $(".userEmail").text(user.email);
            localStorage.setItem("email", user.email);
            $(".user-img").css('background-image', `url(data:image/jpeg;base64,${user.profilePic})`);
        });
    }
});

$("#employeesTBody").on('click', '.edit', function () {
    let index = $(this).data('index');
    let employee = allEmployees[index];
    console.log(employee);
    employeeData = employee;
    isEmployeeSelected = true;
    setEmployeeData(employee);
    $("#registerEmployeeBtn").click();
})

$("#registerEmployeeBtn").click(() => {
    $("#employeeFormBtn").click();
})

function dataURLtoFile(dataurl, filename) {
    var arr = dataurl.split(','),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[arr.length - 1]),
        n = bstr.length,
        u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, { type: mime });
}

//Usage example:
// var file = dataURLtoFile('data:text/plain;base64,aGVsbG8=','hello.txt');
// console.log(file);

$(".user-img").click(() => {
    console.log(user);
    isEmployeeSelected = true;
    employeeData = user;
    setProfileData(user);
    $("section").hide();
    $("#profile").show();
})

// sorting
$("#employee header select").on('change', function () {
    loadEmployeeTable(allEmployees);
})

const sortEmployeeTable = () => {
    let sortBy = $("#employeeSortSelect").val();
    if (sortBy === 'A-Z') {
        allEmployees.sort((a, b) => a.name.localeCompare(b.name));
    } else if (sortBy === 'Z-A') {
        allEmployees.sort((a, b) => b.name.localeCompare(a.name));
    } else if (sortBy === 'Latest-JOINED') {
        allEmployees.sort((a, b) => new Date(b.joinedDate) - new Date(a.joinedDate));
    } else if (sortBy === 'Oldest-JOINED') {
        allEmployees.sort((a, b) => new Date(a.joinedDate) - new Date(b.joinedDate));
    }
}

$("#employeeSearchBtn").click(() => {
    loadEmployeeTable(allEmployees);
})
const isInSearchedKeyword = (employee) => {
    let keyword = $("#employeeSearchInput").val().toLowerCase();
    return employee.name.toLowerCase().includes(keyword)
        || employee.email.toLowerCase().includes(keyword)
        || employee.phone.toLowerCase().includes(keyword)
        || employee.emergencyContact.toLowerCase().includes(keyword)
        || employee.designation.toLowerCase().includes(keyword)
        || employee.gender.toLowerCase() === (keyword)
        || employee.guardianOrNominatedPerson.toLowerCase().includes(keyword)
        || employee.addressLane.toLowerCase().includes(keyword)
        || employee.addressCity.toLowerCase().includes(keyword)
        || employee.addressState.toLowerCase().includes(keyword);
}
import {token, setToken} from '../db/data.js';


$("#customerBtn").click(()=>{
    getAllCustomers();
})

const appendCustomersTBody = cus => {
    $("#customersTBody").append(`
    <tr class="text-center align-middle">
        <td class="d-flex flex-column align-items-center">
            <img src="assets/icons/${cus.level}-level.png" alt="" class="">
            <label class="label">${cus.totalPoints}</label>
        </td>
        <td>${cus.name}</td>
        <td>${cus.phone}</td>
        <td>${cus.email}</td>
        <td>${cus.dob}</td>
        <td>${cus.joinedDateAsLoyalty}</td>
        <td>${cus.gender}</td>
        <td>${cus.recentPurchaseDateTime}</td>
        <td class="table-action"><button class="btn"><i class="fa-solid fa-pen"></i></button></i></td>
    </tr>
    `)
}

const getAllCustomers = ()=>{
    if(token){
        var settings = {
            "url": "http://localhost:8080/customer",
            "method": "GET",
            "timeout": 0,
            "headers": {
              "Authorization": `Bearer ${token}`
            },
          };
          
          $.ajax(settings).done(function (response) {
            response.map(cus => appendCustomersTBody(cus))
          });
    }
}

$("#customerList").append(`
<option value="Dilshan">0770531993</option>
                        <option value="Hasith">0770531993</option>
                        <option value="Krishan">0770531993</option>
                        <option value="Dasun">0770531993</option>
                        <option value="Ishan">0770531993</option>
`)

$(".input").on('input', 'input', function (){
    let val = $(this).val()
    $("#customerList option").each(function (){
        if(val === $(this).val()){
            // To Do
        }
    })
});
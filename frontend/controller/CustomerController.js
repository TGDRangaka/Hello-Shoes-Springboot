import {token, setToken} from '../db/data.js';


$("#customerBtn").click(()=>{
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
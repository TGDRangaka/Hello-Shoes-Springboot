for(let i = 0; i < 8; i++){
    $("#employeesTBody").append(`
        <tr id="row${i}" class="text-center align-middle">
            <td class="table-img">
                <img src="assets/imgs/user_img.jpg" alt="">
            </td>
            <td class="ps-4">
                <div class="d-flex flex-column text-start">
                    <label class="quicksand-bold">Dilshan Rangaka</label>
                    <h6 class="label quicksand-thin">Manager</h6>
                </div>
            </td>
            <td>0770531993</td>
            <td>dilshan@gmail.com</td>
            <td>21-08-2002</td>
            <td>17-04-2024</td>
            <td>Male</td>
            <td>Mr.Perera</td>
            <td>0770341938</td>
            <td class="table-action"><button class="btn"><i class="fa-solid fa-pen"></i></button></i></td>
        </tr>
    `)
}

$("#employeesTBody").on('click', 'tr', function(){
    console.log($(this).attr('id'));
})
import { token } from "../db/data.js";

let allResupplies = [];

$("#resuppliesBtn").click(()=>{
    // getAllResupplies();
})

const saveResupplieDetails = (resupply) => {
    // call api
}

const getAllResupplies = () => {
    // call api
}

const collectResupplieData = () => {

}

const addResupplyComponent = (color) => {
    $("#resupplyItems").append(`
    <div id="ResupplyItem${color}" class="row">
        <div class="col-3">
            <img class="w-100 rounded" src="assets/imgs/shoe1.png" alt="">
        </div>
        <div class="col d-flex flex-column justify-content-around">
            <h3>${color}</h3>
            <hr>
            <table class="table text-center table-bordered">
                <thead>
                    <th class="text-start">Size</th>
                    <th class="table-input">5</th>
                    <th class="table-input">6</th>
                    <th class="table-input">7</th>
                    <th class="table-input">8</th>
                    <th class="table-input">9</th>
                    <th class="table-input">10</th>
                    <th class="table-input">11</th>
                </thead>
                <tbody>
                    <tr>
                        <th class="text-start">Current Qty</th>
                        <td>20<span class="bg-success p-1 rounded text-white bg-opacity-75 ms-1">75%</span></td>
                        <td>20<span class="bg-warning p-1 rounded text-white bg-opacity-75 ms-1">38%</span></td>
                        <td>20<span class="bg-success p-1 rounded text-white bg-opacity-75 ms-1">75%</span></td>
                        <td>0<span class="bg-danger p-1 rounded text-white bg-opacity-75 ms-1">0%</span></td>
                        <td>20<span class="bg-success p-1 rounded text-white bg-opacity-75 ms-1">75%</span></td>
                        <td>20<span class="bg-success p-1 rounded text-white bg-opacity-75 ms-1">75%</span></td>
                        <td>20<span class="bg-success p-1 rounded text-white bg-opacity-75 ms-1">75%</span></td>
                    </tr>
                    <tr>
                        <th class="text-start">Supply Qty</th>
                        <td><input type="number" name="qty" id="" placeholder="qty"></td>
                        <td><input type="number" name="qty" id="" placeholder="qty"></td>
                        <td><input type="number" name="qty" id="" placeholder="qty"></td>
                        <td><input type="number" name="qty" id="" placeholder="qty"></td>
                        <td><input type="number" name="qty" id="" placeholder="qty"></td>
                        <td><input type="number" name="qty" id="" placeholder="qty"></td>
                        <td><input type="number" name="qty" id="" placeholder="qty"></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    `);
}
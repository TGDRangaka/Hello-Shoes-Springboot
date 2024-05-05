import { token } from "../db/data.js";

var currentDate = new Date();
let currentDay = currentDate.getDate();
let currentMonth = currentDate.getMonth() + 1; 
let currentYear = currentDate.getFullYear();

$("#dashboardBtn").click(()=>{
    getAdminPanelData(`${currentYear}-${currentMonth}-${currentDay}`)
})

// getAdminPanelData(`${currentYear}-${currentMonth}-${currentDay}`)

const getAdminPanelData = (date)=>{
    var settings = {
        "url": "http://localhost:8080/api/v1/admin-panel/" + date,
        "method": "GET",
        "timeout": 0,
        "headers": {
          "Authorization": "Bearer " + token
        },
      };
      
      $.ajax(settings).done(function (response) {
        console.log(response);
        displayData(response);
      });
}

const displayData = (data) => {
    console.log(data);
    $("#totalSalesCount").val(data.totalSalesCount) // total sales count
    $("#totalProfitAmount").val(data.totalProfitAmount) // total profit amount
    $("#totalQtySold").val(data.totalQtySold)   // total qty sold

    setBarChart(data.dailySales);
}

console.log("Current Date: " + currentDay);
console.log("Current Month: " + currentMonth);
console.log("Current Year: " + currentYear);

setCalender(currentYear, currentMonth, currentDay);

function setCalender(year, month, day) {
    if((month != currentDate.getMonth()+1) || (year < currentDate.getFullYear())) day = 32;
    if((month > currentDate.getMonth()+1) || (year > currentDate.getFullYear())) day = 0;
    console.log(day, year, month);
    var dates = [];
    var daysInMonth = new Date(year, month, 0).getDate();

    for (let day = 1; day <= daysInMonth; day++) {
        let date = new Date(year, month - 1, day).toString().split(' ');
        date = date.splice(0, 4)
        dates.push(date);
    }

    let firstDay = ['Mon','Tue','Wed','Thu','Fri','Sat','Sun'].indexOf(dates[0][0])
    $(".month").text(dates[0][1])
    $(".year").text(dates[0][3])


    $("#dates").empty();
    for(let i = 0; i < firstDay; i++){
        $("#dates").append(`
        <button type="button" class="btn"><span></span></button>
    `)
    }

    dates.forEach(function(date) {
        $("#dates").append(`
            <button id="${year}-${month}-${date[2]}" type="button" class="btn btn-date 
            ${(date[2] == day) ? ' btn-date-current' : ''}" 
            ${(date[2] > day) ? 'disabled' : ''}
            >${date[2]}</button>
        `)
    });

    return dates;
}

$("#prevMonthBtn").click(()=>{
    currentMonth--;
    if(currentMonth == 0){
        currentMonth = 12;
        currentYear--;
    }
    setCalender(currentYear, currentMonth, currentDay);
})

$("#nextMonthBtn").click(()=>{
    currentMonth++;
    if(currentMonth == 13){
        currentMonth = 1;
        currentYear++;
    }
    setCalender(currentYear, currentMonth, currentDay);
})

$("#dates").on('click', '.btn-date', function(){
    let date = $(this).attr('id')

    getAdminPanelData(date);
    // ToDo : get dashboard data
})

// Charts 
const setBarChart = data => {
    const ctx = document.getElementById('salesWeeklyBarChart');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: [data[0][0], data[1][0], data[2][0], data[3][0], data[4][0], data[5][0], data[6][0]],
            datasets: [
            {
            label: 'Sales',
            data: [data[0][1], data[1][1], data[2][1], data[3][1], data[4][1], data[5][1], data[6][1]],
            borderWidth: 1,
            borderColor: 'rgba(142, 255, 142, 1)',
            backgroundColor: 'rgba(142, 255, 142, .6)',
            }
            // ,
            // {
            // label: 'Profits',
            // data: [12, 19, 3, 5, 2, 3],
            // borderWidth: 1,
            // borderColor: 'rgba(142, 255, 142, 1)',
            // backgroundColor: 'rgba(22, 34, 50, .9)',
            // }
        ]
        },
        options: {
            scales: {
            y: {
                beginAtZero: true
            }
            }
        }
    });
}


const pieChart = document.getElementById('stockPieChart');
new Chart(pieChart, {
    type: 'doughnut',
    data: {
        labels: [
          'Red',
          'Blue',
          'Blue',
          'Blue',
          'Yellow'
        ],
        datasets: [{
          label: 'My First Dataset',
          data: [300, 50, 100,10,40],
          backgroundColor: [
            'rgba(22, 34, 50, 1)',
            'rgba(22, 34, 50, .8)',
            'rgba(22, 34, 50, .6)',
            'rgba(22, 34, 50, .4)',
            'rgba(22, 34, 50, .2)'
          ],
          hoverOffset: 4
        }]
      }
})


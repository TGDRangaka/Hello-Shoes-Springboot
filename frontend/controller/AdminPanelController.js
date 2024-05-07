import { token } from "../db/data.js";

var currentDate = new Date();
let currentDay = currentDate.getDate();
let currentMonth = currentDate.getMonth() + 1; 
let currentYear = currentDate.getFullYear();
const barChart = document.getElementById('salesWeeklyBarChart');

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
    $("#totalSalesCount").text(data.totalSalesCount) // total sales count
    $("#totalProfitAmount").text(data.totalProfitAmount) // total profit amount
    $("#totalQtySold").text(data.totalQtySold)   // total qty sold

    // set bar chart
    let barLabels = [];
    let barData = [];
    data.dailySales.map(dailySale => {
        barLabels.push(dailySale.date)
        barData.push(dailySale.totalSales)
    })
    barChartData.labels = barLabels;
    barChartData.datasets[0].data = barData;
    bc.update();
}

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

$("#dates").on('click', '.btn-date', function(){
    let date = $(this).attr('id')

    getAdminPanelData(date);
    // ToDo : get dashboard data
})

let barChartData = {
    labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
    datasets: [{
        label: '# of Votes',
        data: [12, 19, 3, 5, 2, 3],
        backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(153, 102, 255, 0.2)',
            'rgba(255, 159, 64, 0.2)'
        ],
        borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(153, 102, 255, 1)',
            'rgba(255, 159, 64, 1)'
        ],
        borderWidth: 1
    }]
};
// Charts 
let bc = new Chart(barChart, {
    type: 'bar',
    data: barChartData,
    options: {
        scales: {
        y: {
            beginAtZero: true
        }
        }
    }
});

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
          label: 'Profit',
          data: [300, 50, 100,10,40],
          backgroundColor: [
            '#392467',
            '#614BC3',
            '#9400FF',
            '#BC7AF9',
            '#D0A2F7'
          ],
          hoverOffset: 4,
          borderWidth: 1
        }]
      }
})

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


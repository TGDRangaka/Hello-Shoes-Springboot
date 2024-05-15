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
    labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange', 'Yellow'],
    datasets: [{
        label: '# of Votes',
        data: [12, 19, 3, 5, 2, 3, 15],
        backgroundColor: ['rgba(153, 102, 255, 0.4)'],
        borderColor: ['rgba(153, 102, 255, 1)'],
        borderWidth: 1
    }],
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

// Line Chart
const data = {
    labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
    datasets: [{
        label: 'Sales',
        backgroundColor: 'rgba(153, 102, 255, 0.5)',
        borderColor: 'rgba(153, 102, 255, 1)',
        borderWidth: 1,
        data: [3500, 2000, 1700, 6000, 4500, 2300, 5600]
    }]
};

const options = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
        yAxes: [{
        ticks: {
            beginAtZero: true
        }
        }]
    },
};

// Get the canvas element
const lineChart = document.getElementById('lineChart');

// Create the line chart
const lc = new Chart(lineChart, {
    type: 'line',
    data: data,
    options: options
});

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


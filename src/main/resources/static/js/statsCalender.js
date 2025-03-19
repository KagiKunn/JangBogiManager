let date;
let total = 0;
document.addEventListener('DOMContentLoaded', function () {
    const ledgerId = document.getElementById('ledgerId').value;
    date = new Date().toISOString().split('T')[0];
    document.getElementById("todayDate").innerText = date;




    Calender(ledgerId);
    expensesDetail(ledgerId, date);
});

function allExpenses(){
    console.log('allexpenses');
    document.querySelectorAll(".fc-event-title").forEach(i => {
        console.log(i.textContent);
    })
}

async function Calender(ledgerId){
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale: 'ko',
        events: function (fetchInfo, successCallback, failureCallback) {
            // Ajax 요청을 사용하여 데이터 가져오기
            fetch(`/api/stats/ledger?ledgerId=${ledgerId}`)
                .then(response => response.json())
                .then(data => {
                    let prices = data.forEach(d => {
                        console.log(d.price);
                        total += d.price;
                    })

                    successCallback(data);
                })
                .catch(error => failureCallback(error));
        },
        dateClick: function (info) {
            // 날짜 클릭 시 alert에 클릭한 날짜 출력
            document.getElementById("todayDate").innerText = info.dateStr;
            expensesDetail(ledgerId, info.dateStr);
        },
    });
    calendar.render();
}

async function expensesDetail(ledgerId, date){
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    fetch('/api/stats/detail', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN' : csrfToken
        },
        body: JSON.stringify({ ledgerId, date })
    })
        .then(response => response.json())
        .then(data => {
            console.log(data)

            const tbody = document.querySelector("#detailTable tbody");
            tbody.innerHTML = ""; // 기존 데이터 초기화
            //내용/가격/카테고리/완료자/완료일
            data.forEach(item => {
                const row =
                    `<tr class="text-center">
                       <td>${item.name}</td>
                       <td>${item.price}원</td>
                       <td>${item.category}</td>
                       <td>${item.completeMember}</td>
                       <td>${item.completeAt}</td>
                    </tr>`;
                tbody.innerHTML += row;

        })
    })
        .catch(error => console.error('Error:', error));
}

document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    const ledgerId = document.getElementById('ledgerId').value;
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale : 'ko',
        events : function(fetchInfo, successCallback, failureCallback) {
            // Ajax 요청을 사용하여 데이터 가져오기
            fetch(`/api/stats/ledger?ledgerId=${ledgerId}`)
                .then(response => response.json())
                .then(data => successCallback(data))
                .catch(error => failureCallback(error));
        },
        dateClick: function(info) {
            alert("이벤트: " + info.event.extendedProps.name + "\n가격: " + info.event.extendedProps.price + "원");
        },
    });
    calendar.render();
});


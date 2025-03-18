document.addEventListener('DOMContentLoaded', function () {
    var ledgerId = document.getElementById('ledgerId').value;
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale: 'ko',
        events: function (fetchInfo, successCallback, failureCallback) {
            // Ajax 요청을 사용하여 데이터 가져오기
            fetch(`/api/stats/ledger?ledgerId=${ledgerId}`)
                .then(response => response.json())
                .then(data => successCallback(data))
                .catch(error => failureCallback(error));
        },
        dateClick: function (info) {
            // 날짜 클릭 시 alert에 클릭한 날짜 출력
            alert('클릭한 날짜: ' + info.dateStr);
            const f = document.createElement('form');
            f.setAttribute('method', 'GET');
            f.setAttribute('action', '/stats/detail');
            const i = document.createElement('input');
            i.setAttribute('type', 'hidden');
            i.setAttribute('name', 'date');
            i.setAttribute('value', info.dateStr);
            f.appendChild(i);
            l.setAttribute('type', 'hidden');
            l.setAttribute('name', 'ledgerId');
            l.setAttribute('value', ledgerId);
            f.appendChild(l);
            document.body.appendChild(f);
            f.submit();

        },

    });
    calendar.render();
});
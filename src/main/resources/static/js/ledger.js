function ledgerDetail(no){
    location.href='/ledger/detail/'+no;
}
function registNewItem(no){
    location.href='/ledger/detail/'+no;
}
function deleteJItem(id){
    const no = document.getElementById('lno').value;
    const f = document.createElement('form');
    f.setAttribute('method', 'POST');
    f.setAttribute('action', '/ledger/deletedetail');
    const i = document.createElement('input');
    i.setAttribute('type', 'hidden');
    i.setAttribute('name', 'id');
    i.setAttribute('value', id);
    f.appendChild(i);
    const l = document.createElement('input');
    l.setAttribute('type', 'hidden');
    l.setAttribute('name', 'no');
    l.setAttribute('value', no);
    f.appendChild(l);
    csrfToken(f);
    document.body.appendChild(f);
    f.submit();
}
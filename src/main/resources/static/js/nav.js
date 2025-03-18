function logout(){
    const f = document.createElement('form');
    f.setAttribute('method', 'POST');
    f.setAttribute('action', '/logout');
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfTokenInput = document.createElement("input");
    csrfTokenInput.type = "hidden";
    csrfTokenInput.name = "_csrf";
    csrfTokenInput.value = csrfToken;
    f.appendChild(csrfTokenInput);
    document.body.appendChild(f);
    f.submit();
}
function csrfToken(f) {
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfTokenInput = document.createElement("input");
    csrfTokenInput.type = "hidden";
    csrfTokenInput.name = "_csrf";
    csrfTokenInput.value = csrfToken;
    f.appendChild(csrfTokenInput);
}
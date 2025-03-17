function signupCheck(){
    const inputs = document.querySelectorAll('input');
    inputs.forEach(input => {
        if(input.value==null||input.value===""){
            return false;
        }
    })
}
function validateInput(input) {
    const regex = /^[a-z0-9]*$/;
    input.value = input.value.replace(/[^a-z0-9]/g, '');  // 영소문자, 숫자가 아닌 문자를 제거
}
function spaceDetector() {
    if(event.keyCode === 32) event.returnValue = false;
}

window.addEventListener("DOMContentLoaded", (event) => {
    const inputs = document.querySelectorAll('input')
    inputs.forEach(input => {
        if(input.name === 'name') return;
        input.setAttribute('onkeydown', 'spaceDetector()');
        input.setAttribute('oninput', 'validateInput(this)');
    })
});
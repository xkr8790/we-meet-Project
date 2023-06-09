const loginForm = document.getElementById('loginForm');
const showIcon = document.getElementById('showIcon');
const hideIcon = document.getElementById('hideIcon');
const passwordObject = document.querySelector('.passwordObject');


// 아이디와 비밀번호를 입력하지 않은체 로그인 버튼을 누른다면 화면에 아이디와 비밀번호를 입력하라는 숨겨진 문구가 보일수 있도록 하는 함수를 만든다.
loginForm.emailWarning = loginForm.querySelector('[rel="emailWarning"]');
loginForm.emailWarning.show = (text) => {
    loginForm.emailWarning.innerText = text;
    loginForm.emailWarning.classList.add('visible');
};
loginForm.emailWarning.hide = () => loginForm.emailWarning.classList.remove('visible');

loginForm.passwordWarning = loginForm.querySelector('[rel="passwordWarning"]');
loginForm.passwordWarning.show = (text) => {
    loginForm.passwordWarning.innerText = text;
    loginForm.passwordWarning.classList.add('visible');
};
loginForm.passwordWarning.hide = () => loginForm.passwordWarning.classList.remove('visible');

loginForm.loginWarning = loginForm.querySelector('[rel="loginWarning"]');
loginForm.loginWarning.show = (text) => {
    loginForm.loginWarning.innerText = text;
    loginForm.loginWarning.classList.add('visible');
};
loginForm.loginWarning.hide = () => loginForm.loginWarning.classList.remove('visible');

// 아이콘 클릭시 input태그의 타입이 password와 text로 바뀌는 코드
showIcon.addEventListener('click', function (){
    showIcon.style.display='none';
    hideIcon.style.display='block';
    passwordObject.type = 'password';
});
hideIcon.addEventListener('click', function (){
    showIcon.style.display='block';
    hideIcon.style.display='none';
    passwordObject.type = 'text';
})


loginForm.onsubmit = () => {
 if(loginForm['email'].value=''){

 }
 if(loginForm['password'].value=''){

 }
}



const loginform = document.getElementById('loginform');
const showIcon = document.getElementById('showIcon');
const hideIcon = document.getElementById('hideIcon');
const passwordObject = document.querySelector('.passwordObject');


// 아이디와 비밀번호를 입력하지 않은체 로그인 버튼을 누른다면 화면에 아이디와 비밀번호를 입력하라는 숨겨진 문구가 보일수 있도록 하는 함수를 만든다.
loginform.emailWarning = loginform.querySelector('[rel="emailWarning"]');
loginform.emailWarning.show = (text) => {
    loginform.emailWarning.innerText = text;
    loginform.emailWarning.classList.add('visible');
};
loginform.emailWarning.hide = () => loginform.emailWarning.classList.remove('visible');

loginform.passwordWarning = loginform.querySelector('[rel="passwordWarning"]');
loginform.passwordWarning.show = (text) => {
    loginform.passwordWarning.innerText = text;
    loginform.passwordWarning.classList.add('visible');
};
loginform.passwordWarning.hide = () => loginform.passwordWarning.classList.remove('visible');

loginform.loginWarning = loginform.querySelector('[rel="loginWarning"]');
loginform.loginWarning.show = (text) => {
    loginform.loginWarning.innerText = text;
    loginform.loginWarning.classList.add('visible');
};
loginform.loginWarning.hide = () => loginform.loginWarning.classList.remove('visible');

// 아이콘 클릭시 input태그의 타입이 password와 text로 바뀌는 코드
showIcon.addEventListener('click', function () {
    showIcon.style.display = 'none';
    hideIcon.style.display = 'block';
    passwordObject.type = 'password';
});
hideIcon.addEventListener('click', function () {
    showIcon.style.display = 'block';
    hideIcon.style.display = 'none';
    passwordObject.type = 'text';
})


loginform.onsubmit = e => {
    e.preventDefault()
    loginform.emailWarning.hide();
    loginform.passwordWarning.hide();
    loginform.loginWarning.hide();
    if (loginform['email'].value === '') {
        loginform['email'].focus();
        loginform.emailWarning.show('이메일을 입력해 주세요.');
        return false;
    }
    if (!new RegExp('^(?=.{10,50}$)([\\da-zA-Z\\-_\\.]{5,25})@([\\da-z][\\da-z\\-]*[\\da-z]\\.)?([\\da-z][\\da-z\\-]*[\\da-z])\\.([a-z]{2,15})(\\.[a-z]{2})?$').test(loginform['email'].value)) {
        loginform['email'].focus();
        loginform['email'].select();
        loginform.emailWarning.show('올바른 이메일을 입력해 주세요.');
        return false;
    }
    if (loginform['password'].value === '') {
        loginform['password'].focus();
        loginform.passwordWarning.show();
        return false;
    }
    if (!new RegExp('^([\\da-zA-Z`~!@#$%^&*()\\-_=+\\[{\\]};:\'",<.>/?]{8,50})$').test(loginform['password'].value)) {
        loginform['password'].focus();
        loginform['password'].select();
        loginform.passwordWarning.show('올바른 비밀번호를 입력해 주세요.');
        return false;
    }
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('email', loginform['email'].value);
    formData.append('password', loginform['password'].value);
    xhr.open('POST', `/user/login`);
    xhr.onreadystatechange = () => {
        // readyState메서드는 XMLHttpRequest객체의 상태를나타낸다.
        // 아래의 코드는 readyState프로퍼티의 값이 변경될때마다 readystatechange이벤트가 실행되는데
        // 이때 해당 이벤트에서 readyState프로퍼티의 값을 확인하는것이 좋다.
        //  그중 XMLHttpRequest.DONE은 요청이 완료되었다는 뜻을 가진다.
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure':
                        loginform.loginWarning.show('이메일 혹은 비밀번호가 올바르지 않습니다.');
                        loginform['email'].focus();
                        loginform['email'].select();
                        break;
                    case 'success':
                        //  어떻게 메인 페이지로 넘어가는 코드를 만들어야 하는지 조사하기
                        location.href += '';
                        break;
                    default:
                        loginform.loginWarning.show('서버가 알 수 없는 응답을 반환했습니다. 관리자에게 문의해 주세요.');
                }
            } else {
                loginform.loginWarning.show('서버가 알 수 없는 응답을 반환했습니다. 관리자에게 문의해 주세요.');

            }
        }
    };
    xhr.send(formData);
};



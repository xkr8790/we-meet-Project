const recoverform = document.getElementById('recoverForm');
const emailOption = document.querySelector('.option.email');
const passwordOption = document.querySelector('.option.password');
const emailContainer = document.getElementById('email-container');
const passwordContainer = document.getElementById('password-container');
const contactSend = document.getElementById('contactSend');
const emailSend = document.getElementById('emailSend');

// 이메일, 비밀번호 option에 맞게 아래의 container가 나타나게 하는 코드
emailOption.addEventListener('change', function () {
    if (this.checked) {
        emailContainer.style.display = 'flex';
        passwordContainer.style.display = 'none';
        contactSend.style.display = 'block';
        emailSend.style.display = 'none';

    }
});
passwordOption.addEventListener('change', function () {
    if (this.checked) {
        emailContainer.style.display = 'none';
        passwordContainer.style.display = 'block';
        contactSend.style.display = 'none';
        emailSend.style.display = 'block';
    }
});

//  입력값들이(연락처, 인증번호, 이메일) 이상할때 나타내기 위한 코드작업
recoverform.contactWarning = recoverform.querySelector('[rel="contactWarning"]');
recoverform.contactWarning.show = (text) => {
    recoverform.contactWarning.innerText = text;
    recoverform.contactWarning.classList.add('visible');
};
recoverform.contactWarning.hide = () => recoverform.contactWarning.classList.remove('visible');

recoverform.eContactWarning = recoverform.querySelector('[rel="eContactWarning"]');
recoverform.eContactWarning.show = (text) => {
    recoverform.eContactWarning.innerText = text;
    recoverform.eContactWarning.classList.add('visible');
};
recoverform.eContactWarning.hide = () => recoverform.eContactWarning.classList.remove('visible');

recoverform.emailWarning = recoverform.querySelector('[rel="emailWarning"]');
recoverform.emailWarning.show = (text) => {
    recoverform.emailWarning.innerText = text;
    recoverform.emailWarning.classList.add('visible');
};
recoverform.emailWarning.hide = () => recoverform.emailWarning.classList.remove('visible');

recoverform.cNotification = recoverform.querySelector('[rel="cNotification"]');
recoverform.cNotification.show = (text) => {
    recoverform.cNotification.innerText = text;
    recoverform.cNotification.classList.add('visible');
};
recoverform.cNotification.hide = () => recoverform.cNotification.classList.remove('visible');

recoverform.eNameWarning = recoverform.querySelector('[rel="eNameWarning"]');
recoverform.eNameWarning.show = (text) => {
    recoverform.eNameWarning.innerText = text;
    recoverform.eNameWarning.classList.add('visible');
};
recoverform.eNameWarning.hide = () => recoverform.eNameWarning.classList.remove('visible');


//  아이디 찾기 과정에서 연락처 인증버튼을 누르면 인증버튼은 더이상 클릭하지 못하고 클릭하지 못하는 인증번호 인증 버튼이 클릭할수 있게 되는코드
recoverform['eContactSend'].onclick = () => {
    if (recoverform['eName'].value === '') {
        recoverform.eNameWarning.show('이름을 입력해 주세요.');
        recoverform['eName'].focus();
        return;
    }
    if (recoverform['eContact'].value === '') {
        recoverform.contactWarning.show('연락처를 입력해 주세요.');
        recoverform['eContact'].focus();
        return;
    }
    if (!new RegExp('^(010\\d{8})$').test(recoverform['eContact'].value)) {
        recoverform.contactWarning.show('올바른 연락처를 입력해 주세요.');
        recoverform['eContact'].focus();
        return;
    }

    const xhr = new XMLHttpRequest();
    xhr.open('GET',`/recoverAccount/contactCodeRec?name=${recoverform['eName'].value}&contact=${recoverform['eContact'].value}`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure':
                        recoverform.contactWarning.show('일치하는 회원을 찾을 수 없습니다.');
                        recoverform['eContact'].select();
                        break;
                    case 'success':
                        recoverform.cNotification.show('입력하신 연락처로 인증번호를 전송하였습니다. 5분 이내에 인증을 완료해 주세요.');
                        recoverform['eContact'].setAttribute('disabled', 'disabled');
                        recoverform['eContactSend'].setAttribute('disabled', 'disabled');
                        recoverform['eContactCode'].removeAttribute('disabled');
                        recoverform['eContactVerify'].removeAttribute('disabled');
                        recoverform['eContactCode'].focus();
                        recoverform['eContactSalt'].value = responseObject.salt;
                        break;
                    default:
                        recoverform.contactWarning.show('서버가 알 수 없는 응답을 반환하였습니다. 잠시 후 다시 시도해 주세요.');
                }
            } else {
                recoverform.contactWarning.show('서버와 통신하지 못하였습니다. 잠시후 다시 시도해 주세요.')
            }
        }
    };
    xhr.send();
}

//  6자리 인증번호 누른후 
recoverform['eContactVerify'].onclick = () => {
    if (recoverform['eContactCode'] === '') {
        recoverform.eContactWarning.show('인증번호를 입력해주세요');
        recoverform['eContactCode'].focus();
        return;
    }
    if (!new RegExp('^(\\d{6})$').test(recoverform['eContactCode'].value)) {
        recoverform.eContactWarning.show('올바른 인증번호를 입력해 주세요.');
        recoverform['eContactCode'].focus();
        return;
    }

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('contact', recoverform['eContact'].value);
    formData.append('code', recoverform['eContactCode'].value);
    formData.append('salt', recoverform['eContactSalt'].value);
    xhr.open('PATCH', `/recoverAccount/contactCodeRec`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure':
                        recoverform.eContactWarning.show('인증번호가 올바르지 않습니다. 다시 한번 확인해 주세요.');
                        recoverform['eContactCode'].select();
                        break;
                    case 'failure_expired':
                        recoverform.eContactWarning.show('해당 인증번호가 만료되었습니다.');
                        break;
                    case 'success':
                        recoverform.cNotification.show('인증이 완료되었습니다.');
                        recoverform['eContactCode'].setAttribute('disabled', 'disabled');
                        recoverform['eContactVerify'].setAttribute('disabled', 'disabled');
                        break;
                    default:
                        recoverform.eContactWarning.show('서버가 알 수 없는 응답을 반환하였습니다. 잠시 후 다시 시도해 주세요.');
                }
            } else {
                recoverform.eContactWarning.show('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.')
            }
        }
    };
    xhr.send(formData);
}


// 연락처 인증 혹은 이메일 입력 다한 후 최종적인 다음으로 넘어가는 버튼을 눌렀을때
recoverform['contactSend'].onsubmit = e => {
    e.preventDefault();
    if (recoverform['eName'].value === '') {
        recoverform.eNameWarning.show('이름을 입력해주세요')
        recoverform.eNameWarning.focus();
        return;
    }
    if (recoverform['eContact'].value === '') {
        recoverform.contactWarning.show('연락처를 입력해주세요.');
        recoverform['eContact'].focus();
        return;
    }
    if (recoverform['eContactCode'].value === '') {
        recoverform.eContactWarning.show('인증번호를 입력해주세요');
        recoverform['eContactCode'].focus();
        return;
    }
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('name', recoverform['eName'].value);
    formData.append('contact', recoverform['eContact'].value);
    formData.append('code', recoverform['eContactCode'].value);
    formData.append('salt', recoverform['eContactSalt'].value);
    xhr.open('POST', `/recoverAccount`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'success':
                        location.href += '';
                        break;
                    default:
                        recoverform.eContactWarning.show('서버가 알수 없는 응답을 반환하였습니다. 잠시 후 다시 시도해 주세요.');
                }
            } else {
                recoverform.eContactWarning.show('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
            }
        }
    };
    xhr.send(formData);
}














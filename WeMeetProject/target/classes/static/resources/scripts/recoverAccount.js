const recoverForm = document.getElementById('recoverForm');
const emailOption = document.querySelector('.option.email');
const passwordOption = document.querySelector('.option.password');
const emailContainer = document.getElementById('email-container');
const passwordContainer = document.getElementById('password-container');
const contactSend = document.getElementById('contactSend');
const emailSend = document.getElementById('emailSend');

// 이메일, 비밀번호 option에 맞게 아래의 container가 나타나게 하는 코드
emailOption.addEventListener('change', function() {
    if (this.checked) {
        emailContainer.style.display = 'flex';
        passwordContainer.style.display = 'none';
        contactSend.style.display ='block';
        emailSend.style.display = 'none';

    }
});
passwordOption.addEventListener('change', function() {
    if (this.checked) {
        emailContainer.style.display = 'none';
        passwordContainer.style.display = 'block';
        contactSend.style.display = 'none';
        emailSend.style.display = 'block';
    }
});

//  입력값들이(연락처, 인증번호, 이메일) 이상할때 나타내기 위한 코드작업
recoverForm.contactWarning = recoverForm.querySelector('[rel="contactWarning"]');
recoverForm.contactWarning.show = (text) => {
    recoverForm.contactWarning.innerText = text;
    recoverForm.contactWarning.classList.add('visible');
};
recoverForm.contactWarning.hide = () => recoverForm.contactWarning.classList.remove('visible');

recoverForm.eContactWarning = recoverForm.querySelector('[rel="eContactWarning"]');
recoverForm.eContactWarning.show = (text) => {
    recoverForm.eContactWarning.innerText = text;
    recoverForm.eContactWarning.classList.add('visible');
};
recoverForm.eContactWarning.hide = () => recoverForm.eContactWarning.classList.remove('visible');

recoverForm.emailWarning = recoverForm.querySelector('[rel="emailWarning"]');
recoverForm.emailWarning.show = (text) => {
    recoverForm.emailWarning.innerText = text;
    recoverForm.emailWarning.classList.add('visible');
};
recoverForm.emailWarning.hide = () => recoverForm.emailWarning.classList.remove('visible');

//  아이디 찾기 과정에서 연락처 인증버튼을 누르면 인증버튼은 더이상 클릭하지 못하고 클릭하지 못하는 인증번호 인증 버튼이 클릭할수 있게 되는코드

recoverForm['eContactSend'].onclick = () => {
    if (recoverForm['eContact'].value === '') {
        recoverForm.contactWarning.show('연락처를 입력해 주세요.');
        recoverForm['eContact'].focus();
        return;
    }
    if (!new RegExp('^(010\\d{8})$').test(recoverForm['eContact'].value)) {
        recoverForm.contactWarning.show('올바른 연락처를 입력해 주세요.');
        recoverForm['eContact'].focus();
        return;
    }

    const xhr = new XMLHttpRequest();
    xhr.open(, );
    xhr.onreadystatechange = () => {
        if(xhr.readyState === XMLHttpRequest.DONE){
        if(xhr.status >=200 && xhr.status<300){

        }else{

        }
     }
    };
    xhr.send();
}





// 연락처 인증 혹은 이메일 입력 다한 후 최종적인 다음으로 넘어가는 버튼을 눌렀을때
recoverForm['contactSend'].onsubmit = e =>{
    e.preventDefault();
    if(recoverForm['eContact'].value === ''){
        recoverForm.contactWarning.show('연락처를 입력해주세요.');
        recoverForm['eContact'].focus();
        return;
    }
    if(recoverForm['eContactCode'].value === ''){
        recoverForm.eContactWarning.show('인증번호를 입력해주세요');
        recoverForm['eContactCode'].focus();
        return;
    }
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    xhr.open(, );
    xhr.onreadystatechange = () => {
        if(xhr.readyState === XMLHttpRequest.DONE){
        if(xhr.status >=200 && xhr.status<300){

        }else{

        }
     }
    };
    xhr.send(formData);
}


recoverForm['emailSend'].onsubmit = e => {
    e.preventDefault();
    if(recoverForm['pEmail'].value ===''){
        recoverForm.emailWarning.show('이메일을 입력해 주세요');
        recoverForm['pEmail'].focus();
        return;
    }

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    xhr.open(, );
    xhr.onreadystatechange = () => {
        if(xhr.readyState === XMLHttpRequest.DONE){
        if(xhr.status >=200 && xhr.status<300){

        }else{

        }
     }
    };
    xhr.send(formData);
}












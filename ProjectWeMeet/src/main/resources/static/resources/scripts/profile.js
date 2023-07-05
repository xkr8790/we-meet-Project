const settingButton = document.getElementById('setting');
const popup = document.getElementById('popup');
const step1 = document.querySelector('.step-1');
const saveButton = document.querySelector('.save');
const passwordInputStep1 = step1.querySelector('._object-input');
const checkPasswordButton = step1.querySelector('.checkPassword');
const profile = document.getElementById('mainContainer');

settingButton.addEventListener('click', () => {
    popup.classList.add('step-1');
    popup['_object-input'].value = '';
    popup.style.display = 'block';
    step1.style.display = 'block';
    popup['_object-input'].focus();
});

saveButton.addEventListener('click', () => {
    popup.style.display = 'none';
});

HTMLInputElement.prototype.focusAndSelect = function () {
    this.focus();
    this.select();
};


popup.onsubmit = e => {
    e.preventDefault();

    const password = passwordInputStep1.value;

    if (password === '') {
        alert('비밀번호를 입력해 주세요.');
        return false;
    }
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('checkPassword', popup['checkPassword'].value);
    xhr.open('POST', `/profile/checkPassword`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure' :
                        alert('비밀번호가 일치하지 않습니다.');
                        break;
                    case 'success':
                        alert('확인되었습니다.');
                        popup.classList.remove('step-1');
                        popup.classList.add('step-2');
                        break;
                    default:
                        alert('서버가 알 수 없는 응답을 반환했습니다.');
                }
            } else {
                alert('서버에 연결할 수 없습니다. 다시 시도해 주세요.');
            }
        }
    };
    xhr.send(formData);

    // if (password === '1234') {
    //     popup.classList.remove('step-1');
    //     popup.classList.add('step-2');
    // } else {
    //     alert('비밀번호가 올바르지 않습니다.');
    //     popup['_object-input'].value = '';
    //     popup['_object-input'].focus();
    // }
};

popup['save'].addEventListener('click', () => {
    popup.classList.remove('step-2');
    popup.style.display = 'none';
    alert('변경된 사항이 저장되었습니다.');
});

// profile.profilePreview = profile.querySelector('[rel="profilePreview"]');
// profile.profile1 = profile.querySelector('[rel="profile1"]');
//
// profile['profile'].onchange = function (e) {
//     e.preventDefault();
//
//     if (profile['profile'].files.length === 0) {
//         return;
//     }
//     const fileReader = new FileReader();
//     fileReader.onload = function (data) {
//         profile['profile1'].src = '';
//         profile['profile1'].style.backgroundImage = `url("${data.target.result}")`;
//     }
//     fileReader.readAsDataURL(profile['profile'].files[0]);
//     alert('프로필 사진이 변경되었습니다.');
//
//     const xhr = new XMLHttpRequest();
//     const formData = new FormData();
//
//     formData.append('thumbnailMultipart', profile['profile'].files[0]);
//
//     xhr.open('POST', '/profile/profileImage');
//     xhr.onreadystatechange = () => {
//         if (xhr.readyState === XMLHttpRequest.DONE) {
//             if (xhr.status >= 200 && xhr.status < 300) {
//                 alert('섹');
//             } else {
//                 alert('ㅋㅋ');
//             }
//         }
//     };
//     xhr.send(formData);
// };
//

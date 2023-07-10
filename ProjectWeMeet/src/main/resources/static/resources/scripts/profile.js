const settingButton = document.getElementById('setting');
const popup = document.getElementById('popup');
const step1 = document.querySelector('.step-1');
const saveButton = document.querySelector('.save');
const passwordInputStep1 = step1.querySelector('._object-input');
const checkPasswordButton = step1.querySelector('.checkPassword');

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

// const Dialog = {
//     createButton: function (text, onclick) {
//         return {
//             text: text,
//             onclick: onclick
//         }
//     },
//     Stack: [],
//     Type: {
//         Error: '_error',
//         Default: '_default',
//         Information: '_information',
//         Warning: '_warning'
//     },
//     create: function (params) {
//         params.type ??= Dialog.Type.Default;
//         params.buttons ??= [];
//     }
// };



popup.onsubmit = e => {
    e.preventDefault();

    const password = passwordInputStep1.value;

    if (password === '') {
        alert('비밀번호를 입력해 주세요.');
        return false;
    }
    // const xhr = new XMLHttpRequest();
    // const formData = new FormData();
    // formData.append('checkPassword', popup['checkPassword'].value);
    // xhr.open('POST', `/checkPassword`);
    // xhr.onreadystatechange = () => {
    //     if (xhr.readyState === XMLHttpRequest.DONE) {
    //         if (xhr.status >= 200 && xhr.status < 300) {
    //             const responseObject = JSON.parse(xhr.responseText);
    //             switch (responseObject.result) {
    //                 case 'failure' :
    //                     alert('비밀번호가 일치하지 않습니다.');
    //                     break;
    //                 case 'success':
    //                     alert('확인되었습니다.');
    //                     popup.classList.remove('step-1');
    //                     popup.classList.add('step-2');
    //                     break;
    //                 default:
    //                     alert('서버가 알 수 없는 응답을 반환했습니다.');
    //             }
    //         } else {
    //             alert('서버에 연결할 수 없습니다. 다시 시도해 주세요.');
    //         }
    //     }
    // };
    // xhr.send(formData);

    if (password === '1234') {
        popup.classList.remove('step-1');
        popup.classList.add('step-2');
    } else {
        alert('비밀번호가 올바르지 않습니다.');
        popup['_object-input'].value = '';
        popup['_object-input'].focus();
    }
};

popup['close'].addEventListener('click', () => {
    var r = confirm("취소하시겠습니까?\n저장되지 않은 모든 내용은 유실됩니다.");
    if (r == true) {
        alert("취소되었습니다!");
        popup.classList.remove('step-2');
        popup.style.display = 'none';
    } else {
        alert("수정 페이지로 돌아갑니다!");
    }
})

popup['save'].addEventListener('click', () => {
    var r = confirm("저장하시겠습니까?");
    if (r == true) {
        alert("저장되었습니다!");
        popup.classList.remove('step-2');
    } else {
        alert("취소하였습니다!");
        popup.style.display = 'block';
    }
})


var changeProfile =document.querySelector('.change_profile');
const profileF = popup.querySelector('[rel="profileF"]');

changeProfile.addEventListener('change', function (event) {
    event.preventDefault();

    popup['change_profile'].files[0] = event.target.files[0];
    if (popup['change_profile'].files.length === 0) {
        return;
    }
    var fileReader = new FileReader();
    fileReader.onload = function (data) {
        profileF.src = '';
        profileF.style.backgroundImage = `url("${data.target.result}")`;
    }
    fileReader.readAsDataURL(popup['change_profile'].files[0]);
    alert('프로필 사진이 변경되었습니다.');
});

const profileDelete = popup.querySelector('[rel="profileDelete"]');

profileDelete.addEventListener('click', function (event) {
    event.preventDefault();

    profileF.style.backgroundImage = `none`;
    profileF.src = '/resources/images/profileImages/icons8-male-user-96.png';

    alert('프로필 사진이 삭제되었습니다.');
});

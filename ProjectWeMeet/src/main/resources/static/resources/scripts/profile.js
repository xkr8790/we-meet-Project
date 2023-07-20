const settingButton = document.getElementById('setting');
const popup = document.getElementById('popup');
const step1 = document.querySelector('.step-1');
const saveButton = document.querySelector('.save');
const passwordInputStep1 = step1.querySelector('._object-input');
const checkPasswordButton = step1.querySelector('.checkPassword');
const addressLayer = document.getElementById('addressLayerP');
const dialogCover = document.getElementById('dialogCoverP');


//설정변경 눌렀을때 창띄우는 코드
settingButton.addEventListener('click', () => {
    popup.classList.add('step-1');
    popup['_object-input'].value = '';
    popup.style.display = 'block';
    step1.style.display = 'block';
    popup['_object-input'].focus();
});


//??

//??
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


//step-1 인증
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

//취소 버튼
popup['close'].addEventListener('click', () => {
    var r = confirm("취소하시겠습니까?\n저장되지 않은 모든 내용은 유실됩니다.");
    if (r == true) {
        alert("취소되었습니다!");
        popup.classList.remove('step-2');
        popup.style.display = 'none';
    } else {
        alert("수정 페이지로 돌아갑니다!");
    }
});


//프로필 사진 변경
var changeProfile = document.querySelector('.change_profile');
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

//프로필 사진 삭제
profileDelete.addEventListener('click', function (event) {
    event.preventDefault();

    profileF.style.backgroundImage = `none`;
    profileF.src = '/resources/images/profileImages/icons8-male-user-96.png';

    alert('프로필 사진이 삭제되었습니다.');
});

//인증번호 전송
popup.cNotification = popup.querySelector('[rel="cNotification"]');
popup.cNotification.show = (text) => {
    popup.cNotification.innerText = text;
    popup.cNotification.classList.add('visible');
};
popup.cNotification.hide = () => popup.cNotification.classList.remove('visible');
popup['infoContactSend'].addEventListener('click', () => {
    if (popup['infoContact'].value === '') {
        alert('연락처를 입력해주세요.');
        popup['infoContact'].focus();
        return;
    }
    if (!new RegExp('^(010)(\\d{8})$').test(popup['infoContact'].value)) {
        alert('올바른 연락처를 입력해 주세요.');
        popup['infoContact'].focus();
        popup['infoContact'].select();
        return;
    }
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `/profile/contactCodeRec?contact=${popup['infoContact'].value}`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure':
                        alert('해당 연락처는 이미 사용중입니다.');
                        popup['infoContact'].focus();
                        break;
                    case 'success' :
                        popup.cNotification.show('입력하신 연락처로 인증번호를 전송하였습니다. 5분 이내로 인증을 완료해 주세요.');
                        popup['infoContact'].setAttribute('disabled', 'disabled');
                        popup['infoContactSend'].setAttribute('disabled', 'disabled');
                        popup['infoContactCode'].removeAttribute('disabled');
                        popup['infoContactVerify'].removeAttribute('disabled');
                        popup['infoContactCode'].focus();
                        popup['infoContactSalt'].value = responseObject.salt;
                        break;
                    default:
                        alert('서버가 알 수 없는 응답을 반환하였습니다. 잠시 후 다시 시도해 주세요.');
                }
            } else {
                alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
            }
        }
    };
    xhr.send();
})

//인증번호 인증
popup['infoContactVerify'].onclick = () => {
    if (popup['infoContactCode'].value === '') {
        alert('인증번호를 입력해 주세요.');
        popup['infoContactCode'].focus();
        return false;
    }
    if (!new RegExp('^(\\d{6})$').test(popup['infoContactCode'].value)) {
        alert('올바른 인증번호를 입력해 주세요.');
        popup['infoContactCode'].focus();
        return false;
    }

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('contact', popup['infoContact'].value);
    formData.append('code', popup['infoContactCode'].value);
    formData.append('salt', popup['infoContactSalt'].value);
    xhr.open('PATCH', `/profile/contactCodeRec`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure_expired' :
                        alert('해당 인증번호는 만료되었습니다. 처음부터 다시 진행해 주세요.');
                        break;
                    case 'success' :
                        alert('인증이 완료되었습니다.');
                        popup['infoContactCode'].setAttribute('disabled', 'disabled');
                        popup['infoContactVerify'].setAttribute('disabled', 'disabled');
                        popup['changeContact'].removeAttribute('disabled');
                        break;
                    default:
                        alert('서버가 알 수 없는 응답을 반환하였습니다.');
                }
            } else {
                alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
            }
        }
    };
    xhr.send(formData);
}

popup['changeContact'].onclick = e => {
    e.preventDefault();
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('infoContact', popup['infoContact'].value);
    xhr.open('PATCH', `./resetContact`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure' :
                        alert('이전 번호와 일치합니다. 다시 입력해 주세요.');
                        break;
                    case 'success' :
                        alert('연락처가 변경되었습니다.');
                        popup['changeContact'].setAttribute('disabled', 'disabled');
                        break;
                    default :
                        alert('서버가 알 수 없는 응답을 가져왔습니다. 잠시 후 다시 시도해 주세요.');
                }
            } else {
                alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
            }
        }
    };
    xhr.send(formData);
}


//주소 변경 코드
// popup['ingoAddressFind'].onclick = function () {
//     new daum.Postcode({
//         width: '100%',
//         height: '100%',
//         oncomplete: function (data) {
//             popup.setAttribute('data-mz-step', 'info');
//             popup['infoAddressPostal'].value = data['zonecode'];
//             popup['infoAddressPrimary'].value = data['address'];
//             popup['infoAddressSecondary'].focusAndSelect();
//         }
//     }).embed(popup.querySelector('[data-mz-step="address"]'));
//     popup.setAttribute('data-mz-step', 'address');
// }
addressLayer.show = () => {
    new daum.Postcode({
        oncomplete: (data) => {
            popup['infoAddressPostal'].value = data.zonecode;
            popup['infoAddressPrimary'].value = data.address;
            popup['infoAddressSecondary'].value = '';
            popup['infoAddressSecondary'].focus();
            dialogCover.hide();
            addressLayer.hide();
        }
    }).embed(addressLayer);
    addressLayer.classList.add('visible');
};
addressLayer.hide = () => addressLayer.classList.remove('visible');
//우편찾기
popup['infoAddressFind'].onclick = () => {
    dialogCover.show();
    addressLayer.show();
};

popup['changeAddress'].onclick = e => {
    e.preventDefault();
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('infoAddressPostal', popup['infoAddressPostal'].value);
    formData.append('infoAddressPrimary', popup['infoAddressPrimary'].value);
    formData.append('infoAddressSecondary', popup['infoAddressSecondary'].value);
    xhr.open('PATCH', `./resetAddress`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure':
                        alert('이전 주소와 일치합니다. 다시 입력해 주세요.');
                        break;
                    case 'success':
                        alert('주소가 변경되었습니다.');
                        popup['infoAddressPostal'].setAttribute('disabled', 'disabled');
                        popup['infoAddressFind'].setAttribute('disabled', 'disabled');
                        popup['infoAddressPrimary'].setAttribute('disabled', 'disabled');
                        popup['infoAddressSecondary'].setAttribute('disabled', 'disabled');
                        break;
                    default:
                        alert('서버가 알 수 없는 응답을 가져왔습니다. 잠시 후 다시 시도해 주세요.');
                }
            } else {
                alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
            }
        }
    };
    xhr.send(formData);
}

popup['changePassword'].onclick = e => {
    e.preventDefault();

    if (popup['infoPassword'].value === '') {
        alert('비밀번호를 입력해 주세요.');
        return;
    } else if (popup['infoPasswordCheck'].value === '') {
        alert('비밀번호를 재입력해 주세요.');
        return;
    } else if (popup['infoPassword'].value !== popup['infoPasswordCheck'].value) {
        alert('비밀번호가 일치하지 않습니다. 다시 입력해 주세요.');
        return;
    }

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('infoPassword', popup['infoPassword'].value);
    xhr.open('PATCH', `./modifyPassword`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure':
                        alert('이전 비밀번호와 일치합니다. 다시 입력해 주세요.');
                        break;
                    case 'success':
                        alert('비밀번호가 변경되었습니다.');
                        popup['infoPassword'].setAttribute('disabled', 'disabled');
                        popup['infoPasswordCheck'].setAttribute('disabled', 'disabled');
                        break;
                    default:
                        alert('서버가 알 수 없는 응답을 반환하였습니다. 잠시 후 다시 시도해 주세요.');
                }
            } else {
                alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
            }
        }
    };
    xhr.send(formData);
}

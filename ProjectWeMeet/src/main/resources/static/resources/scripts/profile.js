const settingButton = document.getElementById('setting');
const popup = document.getElementById('popup');
const step1 = document.querySelector('.step-1');
const addressLayer = document.getElementById('addressLayerP');
const dialogCover = document.getElementById('dialogCoverP');


const abuse = ['ㅅㅂ','ㅆㅂ','시발','씨발','10새','10새기','10새리','10세리','10쉐이','10쉑','10스','10쌔',
    '10쌔기','10쎄','10알','10창','10탱','18것','18넘','18년','18노','18놈',
    '18뇬','18럼','18롬','18새','18새끼','18색','18세끼','18세리','18섹','18쉑','18스','18아',
    'c파','c팔','fuck', 'ㄱㅐ','ㄲㅏ','ㄲㅑ','ㄲㅣ','ㅅㅂㄹㅁ','ㅅㅐ','ㅆㅂㄹㅁ','ㅆㅍ','ㅆㅣ','ㅆ앙','ㅍㅏ','凸',
    '갈보','갈보년','강아지','같은년','같은뇬','개같은','개구라','개년','개놈',
    '개뇬','개대중','개독','개돼중','개랄','개보지','개뻥','개뿔','개새','개새기','개새끼',
    '개새키','개색기','개색끼','개색키','개색히','개섀끼','개세','개세끼','개세이','개소리','개쑈',
    '개쇳기','개수작','개쉐','개쉐리','개쉐이','개쉑','개쉽','개스끼','개시키','개십새기',
    '개십새끼','개쐑','개씹','개아들','개자슥','개자지','개접','개좆','개좌식','개허접','걔새',
    '걔수작','걔시끼','걔시키','걔썌','걸레','게색기','게색끼','광뇬','구녕','구라','구멍',
    '그년','그새끼','냄비','놈현','뇬','눈깔','뉘미럴','니귀미','니기미','니미','니미랄','니미럴',
    '니미씹','니아배','니아베','니아비','니어매','니어메','니어미','닝기리','닝기미','대가리',
    '뎡신','도라이','돈놈','돌아이','돌은놈','되질래','뒈져','뒈져라','뒈진','뒈진다','뒈질',
    '뒤질래','등신','디져라','디진다','디질래','딩시','따식','때놈','또라이','똘아이','똘아이',
    '뙈놈','뙤놈','뙨넘','뙨놈','뚜쟁','띠바','띠발','띠불','띠팔','메친넘','메친놈','미췬',
    '미췬','미친','미친넘','미친년','미친놈','미친새끼','미친스까이','미틴','미틴넘','미틴년',
    '미틴놈','바랄년','병자','뱅마','뱅신','벼엉신','병쉰','병신','부랄','부럴','불알','불할','붕가',
    '붙어먹','뷰웅','븅','븅신','빌어먹','빙시','빙신','빠가','빠구리','빠굴','빠큐','뻐큐',
    '뻑큐','뽁큐','상넘이','상놈을','상놈의','상놈이','새갸','새꺄','새끼','새새끼','새키',
    '색끼','생쑈','세갸','세꺄','세끼','섹스','쇼하네','쉐','쉐기','쉐끼','쉐리','쉐에기',
    '쉐키','쉑','쉣','쉨','쉬발','쉬밸','쉬벌','쉬뻘','쉬펄','쉽알','스패킹','스팽','시궁창','시끼',
    '시댕','시뎅','시랄','시발','시벌','시부랄','시부럴','시부리','시불','시브랄','시팍',
    '시팔','시펄','신발끈','심발끈','심탱','십8','십라','십새','십새끼','십세','십쉐','십쉐이','십스키',
    '십쌔','십창','십탱','싶알','싸가지','싹아지','쌉년','쌍넘','쌍년','쌍놈','쌍뇬','쌔끼',
    '쌕','쌩쑈','쌴년','썅','썅년','썅놈','썡쇼','써벌','썩을년','썩을놈','쎄꺄','쎄엑',
    '쒸벌','쒸뻘','쒸팔','쒸펄','쓰바','쓰박','쓰발','쓰벌','쓰팔','씁새','씁얼','씌파','씨8',
    '씨끼','씨댕','씨뎅','씨바','씨바랄','씨박','씨발','씨방','씨방새','씨방세','씨밸','씨뱅',
    '씨벌','씨벨','씨봉','씨봉알','씨부랄','씨부럴','씨부렁','씨부리','씨불','씨붕','씨브랄',
    '씨빠','씨빨','씨뽀랄','씨앙','씨파','씨팍','씨팔','씨펄','씸년','씸뇬','씸새끼','씹같','씹년',
    '씹뇬','씹보지','씹새','씹새기','씹새끼','씹새리','씹세','씹쉐','씹스키','씹쌔','씹이','씹자지',
    '씹질','씹창','씹탱','씹퇭','씹팔','씹할','씹헐','아가리','아갈','아갈이','아갈통',
    '아구창','아구통','아굴','얌마','양넘','양년','양놈','엄창','엠병','여물통','염병','엿같','옘병',
    '옘빙','오입','왜년','왜놈','욤병','육갑','은년','을년','이년','이새끼','이새키','이스끼',
    '이스키','임마','자슥','잡것','잡넘','잡년','잡놈','저년','저새끼','접년','젖밥','조까',
    '조까치','조낸','조또','조랭','조빠','조쟁이','조지냐','조진다','조찐','조질래','존나','존나게','존니','존만',
    '존만한','좀물','좁년','좁밥','좃까','좃또','좃만','좃밥','좃이','좃찐','좆같','좆까','좆나',
    '좆또','좆만','좆밥','좆이','좆찐','좇같','좇이','좌식','주글','주글래','주데이','주뎅',
    '주뎅이','주둥아리','주둥이','주접','주접떨','죽고잡','죽을래','죽통','쥐랄','쥐롤',
    '쥬디','지랄','지럴','지롤','지미랄','짜식','짜아식','쪼다','쫍빱','찌랄','창녀','캐년',
    '캐놈','캐스끼','캐스키','캐시키','탱구','팔럼','퍽큐','호로','호로놈','호로새끼',
    '호로색','호로쉑','호로스까이','호로스키','후라들','후래자식','후레']; //욕설 전역 배열



//설정변경 눌렀을때 창띄우는 코드
settingButton.addEventListener('click', () => {
    popup.classList.add('step-1');
    popup['_object-input'].value = '';
    popup.style.display = 'block';
    step1.style.display = 'block';
    popup['_object-input'].focus();
});

// 팝업 창 없애기
popup['cancelButton'].onclick = e => {
    e.preventDefault();
    alert('취소되었습니다.');
    popup.classList.remove('step-1');
    popup.style.display = 'none';
}

//팝업 창 나가기
popup['close'].addEventListener('click', () => {
    var r = confirm("나가시겠습니까?");
    if (r == true) {
        popup.classList.remove('step-2');
        popup.style.display = 'none';
        if(popup['infoNickname'].getAttribute('disable') === 'disable') {
            window.location.href = '/profile/?nickname=' + popup['infoNickname'].value;
        } else {
            location.href = '';
        }
    } else {
        alert("수정 페이지로 돌아갑니다!");
    }
});



//??
// HTMLInputElement.prototype.focusAndSelect = function () {
//     this.focus();
//     this.select();
// };

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
popup['1checkPassword'].addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        checkPassword(); // 이 코드 블록을 호출하거나 함수로 묶은 뒤 호출하세요.
    }
});

popup['checkPasswordButton'].onclick = checkPassword;

function checkPassword() {
    if (popup['1checkPassword'].value === '') {
        alert('비밀번호를 입력해 주세요.');
        popup['1checkPassword'].focus();
        return;
    }

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('checkPassword', popup['1checkPassword'].value);
    xhr.open('POST', `./checkPassword`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure':
                        alert('비밀번호가 올바르지 않습니다. 다시 입력해 주세요.');
                        popup['1checkPassword'].value = '';
                        popup['1checkPassword'].focus();
                        break;
                    case 'success':
                        alert('확인되었습니다.');
                        popup.classList.remove('step-1');
                        popup.classList.add('step-2');
                        break;
                    default:
                        alert('서버가 알 수 없는 응답을 반환했습니다. 잠시 후 다시 시도해 주세요.');
                }
            } else {
                alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
            }
        }
    };
    xhr.send(formData);
}






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


// 연락처 변경
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
//우편 찾기
popup['infoAddressFind'].onclick = () => {
    dialogCover.show();
    addressLayer.show();
};


// 주소 변경
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


//별명 변경
popup['changeNickname'].onclick = e => {
    e.preventDefault();

    if (popup['infoNickname'].value === '') {
        alert('변경할 별명을 입력해 주세요.');
        popup['infoNickname'].focus();
        return;
    }

    if (abuse.some(x => popup['infoNickname'].value.indexOf(x) > -1)) {
        alert('별명에 욕설이 포함되어있습니다. 다시 입력해 주세요.');
        popup['infoNickname'].focus();
        popup['infoNickname'].select();
        return;
    }

    if (!new RegExp('^([가-힣]{2,10})$').test(popup['infoNickname'].value)) {
        alert('올바른 별명을 입력해주세요.');
        // 별명의 형식 2글자 이상 영어 대소문자,한글
        popup['infoNickname'].focus();
        popup['infoNickname'].select();
        return;
    }
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('infoNickname', popup['infoNickname'].value);
    xhr.open('PATCH', `./resetNickname`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure':
                        alert('이전 별명와 일치합니다. 다시 입력해 주세요.');
                        break;
                    case 'success':
                        alert('별명이 변경되었습니다.');
                        popup['infoNickname'].setAttribute('disable', 'disable');
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


// 비밀번호 변경
popup['changePassword'].onclick = e => {
    e.preventDefault();

    if (!new RegExp('^([\\da-zA-Z`~!@#$%^&*()\\-_=+\\[{\\]};:\'",<.>/?]{8,50})$').test(popup['infoPassword'].value)) {
        alert('올바른 비밀번호를 입력해 주세요.');
        // 비밀번호 형식 특수문자 포함 영어대소문자 8글자~50글자
        popup['infoPassword'].focus();
        registerForm['infoPassword'].select();
        return;
    }
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


// 회원 탈퇴
popup['deleteUser'].onclick = e => {
    e.preventDefault();
    if (!confirm("정말로 탈퇴하시겠습니까?")) {
        alert('취소하였습니다.');
    } else {
        const xhr = new XMLHttpRequest();
        const formData = new FormData();
        formData.append('deleteUser', popup['deleteUser'].value);
        xhr.open('DELETE', `./deleteUser`);
        xhr.onreadystatechange = () => {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status >= 200 && xhr.status < 300) {
                    const responseObject = JSON.parse(xhr.responseText);
                    switch (responseObject.result) {
                        case 'failure' :
                            alert('탈퇴에 실패하였습니다. 다시 시도해 주세요.');
                            break;
                        case 'success':
                            alert('탈퇴하셨습니다.');
                            location.href = `/`;
                            break;
                    }
                } else {
                    alert('서버와 통신할 수 없습니다. 잠시 후 다시 시도해 주세요.');
                }
            }
        };
        xhr.send(formData);
    }
}


//프로필 사진 저장
popup['saveProfile'].onclick = e => {
    e.preventDefault();

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append("thumbnailMultipart", popup['change_profile'].files[0]);
    xhr.open('PATCH', './profileImage');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseText = xhr.responseText;
                if (responseText === 'true') {
                    alert('변경완료');
                    return;
                } else {
                    alert('변경 실패. 잠시 후 다시 시도해 주세요.');
                    return;
                }
            } else {
                alert('서버가 알 수 없는 응답을 가져왔습니다. 잠시 후 다시 시도해 주세요.');
                return;
            }
        }
    };
    xhr.send(formData);
};


// 소개글 변경
popup['changeContent'].onclick = e => {
    e.preventDefault();

    if (abuse.some(x => popup['infoContent'].value.indexOf(x) > -1)) {
        alert('소개글에 욕설이 포함되어 있습니다. 다시 입력해 주세요.');
        popup['infoContent'].focus();
        popup['infoContent'].select();
        return;
    }


    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('infoContent', popup['infoContent'].value);
    xhr.open('PATCH', `./resetContent`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseText = xhr.responseText;
                if (responseText === 'true') {
                    alert('소개글 변경 완료.');
                    return;
                } else {
                    alert('변경 실패. 잠시 후 다시 시도해 주세요.');
                    return;
                }
            } else {
                alert('서버가 알 수 없는 응답을 가져왔습니다. 잠시 후 다시 시도해 주세요.');
                return;
            }
        }
    };
    xhr.send(formData);
}



// 프로필 사진 삭제
popup['deleteThumbnail'].onclick = e => {
    e.preventDefault();

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('deleteThumbnail', popup['deleteThumbnail'].value)
    xhr.open('PATCH', `./deleteThumbnail`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure':
                        alert('삭제 실패했습니다. 잠시 후 다시 시도해 주세요.');
                        break;
                    case 'success':
                        alert('프로필 이미지를 삭제했습니다.');
                        profileF.style.backgroundImage = `none`;
                        profileF.src = '/resources/images/profileImages/icons8-male-user-96.png';
                        break;
                    default:
                        alert('서버가 알 수 없는 응답을 가져왔습니다. 잠시 후 다시 시도해 주세요.');
                }
            } else {
                alert('서버와 통신할 수 없습니다. 잠시 후 다시 시도해 주세요.');
            }
        }
    };
    xhr.send(formData);
}


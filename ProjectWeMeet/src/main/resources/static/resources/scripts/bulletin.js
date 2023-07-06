const list = document.querySelector('.container');
const listScrollWidth = list.scrollWidth;
const listClientWidth = list.clientWidth;

const bulletinForm = document.getElementById('bulletinForm');

let startX = 0;
let nowX = 0;
let endX = 0;
let listX = 0;
let isDragging = false;

const getClientX = (e) => {
    const isTouches = e.touches ? true : false;
    return isTouches ? e.touches[0].clientX : e.clientX;
};

const getTranslateX = () => {
    return parseInt(getComputedStyle(list).transform.split(/[^\-0-9]+/g)[5]);
};

const setTranslateX = (x) => {
    list.style.transform = `translateX(${x}px)`;
};

const onScrollStart = (e) => {
    startX = getClientX(e);
    isDragging = true;
    document.addEventListener('mousemove', onScrollMove);
    document.addEventListener('touchmove', onScrollMove);
    document.addEventListener('mouseup', onScrollEnd);
    document.addEventListener('touchend', onScrollEnd);
};

const onScrollMove = (e) => {
    if (!isDragging) return;
    nowX = getClientX(e);
    const v = listX + nowX - startX;
    if (v > 0) return;
    if (v <  -(227* list.childElementCount)) return;
    console.log(v);
    setTranslateX(v);
};

const bindEvents = () => {
    list.addEventListener('mousedown', onScrollStart);
    list.addEventListener('touchstart', onScrollStart);
};

const onScrollEnd = (e) => {
    if (!isDragging) return;
    isDragging = false;
    endX = getClientX(e);
    listX = getTranslateX();
    document.removeEventListener('mousemove', onScrollMove);
    document.removeEventListener('touchmove', onScrollMove);
    document.removeEventListener('mouseup', onScrollEnd);
    document.removeEventListener('touchend', onScrollEnd);


};

bindEvents();


const ParticipateButton = bulletinForm.querySelector('[name="Participate"]');
const deleteButton = bulletinForm.querySelector('[name="delete"]');
const patchButton = bulletinForm.querySelector('[name="patch"]');


ParticipateButton.addEventListener('click', e => {
e.preventDefault();
    const index = ParticipateButton.dataset.index;

    const xhr = new XMLHttpRequest();
    xhr.open('POST', `./Participate?index=${index}`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'success':
                       alert('참여되었습니다');
                       location.href = `/article/read?index=`+index;
                        break;
                    case 'failure':
                        alert('제한인원을 초과할수 없습니다');
                        break;
                    default:
                      alert('무슨오류일까?');
                }
            } else {
                alert('서버오류입니다');
            }
        }
    };
    xhr.send();
});


// ParticipateButton.addEventListener('click', e => {
//     e.preventDefault();
//
//     const index = ParticipateButton.dataset.index;
//
//     const xhr = new XMLHttpRequest();
//     xhr.open('DELETE', `./Delete?index=${index}`);
//     xhr.onreadystatechange = () => {
//         if (xhr.readyState === XMLHttpRequest.DONE) {
//             if (xhr.status >= 200 && xhr.status < 300) {
//                 const responseText = xhr.responseText; // 'true' | 'false'
//                 if (responseText === 'true') {
//                     const confirmResult = confirm('참여를 취소하시겠습니까?');
//                     if (confirmResult === true) {
//                         alert('취소되었습니다');
//                     } else {
//                         alert('삭제를 취소합니다');
//                     }
//                 } else {
//                     alert('작성한 사용자가 아니므로 삭제하지 못합니다');
//                 }
//             } else {
//                 alert('서버와 통신하지 못하였습니다.\n\n잠시 후 다시 시도해 주세요.');
//             }
//         }
//     };
//     xhr.send();
// });




deleteButton.addEventListener('click', e => {
    e.preventDefault();

    const index = deleteButton.dataset.index;

    const xhr = new XMLHttpRequest();
    xhr.open('DELETE', `./read?index=${index}`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseText = xhr.responseText; // 'true' | 'false'
                if (responseText === 'true') {
                    const confirmResult = confirm('삭제하시겠습니까?');
                    if (confirmResult === true) {
                        alert('삭제되었습니다');
                        location.href = '/article';
                    } else {
                        alert('삭제를 취소합니다');
                    }
                } else {
                    alert('작성한 사용자가 아니므로 삭제하지 못합니다');
                }
            } else {
                alert('서버와 통신하지 못하였습니다.\n\n잠시 후 다시 시도해 주세요.');
            }
        }
    };
    xhr.send();
}); //게시판 삭제

patchButton.addEventListener('click', e => {
    e.preventDefault();

    const index = patchButton.dataset.index;

    const xhr = new XMLHttpRequest();
    xhr.open('GET', `./patch?index=${index}`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                location.href = `/article/patch?index=${index}`
            } else {
                alert('작성한 사용자가 아니라 수정이 불가능합니다.');
            }
        }
    };
    xhr.send();
}); //게시판 수정




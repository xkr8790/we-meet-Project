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



bulletinForm['delete'].addEventListener('click', function(e) {
    e.preventDefault();

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    xhr.open('DELETE',`delete/?index=${bulletinForm['index'].value}`);
    xhr.onreadystatechange = () => {
     if(xhr.readyState === XMLHttpRequest.DONE){
        if(xhr.status >= 200 && xhr.status<300) {
            alert('삭제 성공');
            location.href = '/';
        }else {
            alert('삭제 실패');
        }
       }
    };
    xhr.send();
});


//         e.preventDefault();
//
//         xhr.open('DELETE', `./?index=${index}`);
//         xhr.onreadystatechange = () => {
//             if (xhr.readyState === XMLHttpRequest.DONE) {
//                 if (xhr.status >= 200 && xhr.status < 300) {
//                     const responseText = xhr.responseText; // 'true' | 'false'
//                     if (responseText === 'true') {
//                         location.href += '';
//                     } else {
//                         alert('알 수 없는 이유로 삭제하지 못하였습니다.\n\n이미 삭제된 메모일 수도 있습니다.');
//                     }
//                 } else {
//                     alert('서버와 통신하지 못하였습니다.\n\n잠시 후 다시 시도해 주세요.');
//                 }
//             }
//         };
//         xhr.send();
//     });
// });
//
//

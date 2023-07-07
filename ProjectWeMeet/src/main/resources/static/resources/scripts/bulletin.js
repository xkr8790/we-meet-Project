//  preview 이동 자바스크립트
const list = document.querySelector('.container');
// const listScrollWidth = list.scrollWidth;
// const listClientWidth = list.clientWidth;

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

//  댓글 insert

// const bulletinForm = document.getElementById('bulletinForm');

function postComment(content, commentIndex, toFocus, refreshCommentAfter){

    refreshCommentAfter ??= true; // 댓글을 달고난후 새로고침에 대한 코드

    const articleIndex = bulletinForm['articleIndex'].value;
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    // 어느 게시글에 어느 게시글내용을을 사용하겠다.
    //  뒤의 코드는 7번라인, 6번라인의 매개변수를 의미한다.
    formData.append('articleIndex', articleIndex);
    formData.append('content', content);
    if (commentIndex !== null && commentIndex !== undefined) {
        formData.append('commentIndex', commentIndex)
    }
    xhr.open('POST', `/bulletin`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                if (xhr.responseText === 'true') {
                    if (toFocus) {
                        toFocus.value = '';
                        toFocus.focus();
                    }
                    if (refreshCommentAfter === true) {

                        location.href='';
                    }
                } else {
                    alert('댓글을 작성하지 못하였습니다. 잠시후 다시 시도해 주세요');
                }
            } else {
                alert('서버와 통신하지 못하였습니다. 잠시후 다시 시도해 주세요.')
            }
        }
    };
    xhr.send(formData);

}

bulletinForm.onsubmit = (e) => {
    e.preventDefault();

    if (bulletinForm['content'].value === '') {
        alert('댓글을 입력해 주세요');
        bulletinForm['content'].focus();
        return;
    }
    postComment(bulletinForm['content'].value, undefined, bulletinForm['content']);

};

const commentContainer = document.getElementById('commentContainer');

function refreshComment(){

}











const ParticipateButton = bulletinForm.querySelector('[name="Participate"]');
const deleteButton = bulletinForm.querySelector('[name="delete"]');
const patchButton = bulletinForm.querySelector('[name="patch"]');
const finishButton = bulletinForm.querySelector('[name="finish"]');

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






finishButton.addEventListener('click', e => {
    e.preventDefault();
    const index = finishButton.dataset.index;
    const xhr = new XMLHttpRequest();
    xhr.open('GET',`/article/review?index=${index}`);
    xhr.onreadystatechange = () => {
        if(xhr.readyState === XMLHttpRequest.DONE){
        if(xhr.status >=200 && xhr.status<300){
            location.href = `/article/review?index=${index}`
        }else{
            alert('작성한 사용자가 아니라 수정이 불가능합니다.');
        }
     }
    };
    xhr.send();
})

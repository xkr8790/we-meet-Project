const list = document.querySelector('.container');
const listScrollWidth = list.scrollWidth;
const listClientWidth = list.clientWidth;

const bulletinForm = document.getElementById('bulletinForm');
const commentContainer = document.getElementById('commentContainer');


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
    if (v < -(227 * list.childElementCount)) return;
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
    xhr.open('PATCH', `./Participate?index=${index}`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                alert('참여되었습니다');
            } else {
                alert('작성한 사용자가 아니므로 삭제하지 못합니다');
            }
        }
    };
    xhr.send();
}); //인원 참여


// deleteButton.addEventListener('click', e => {
//     e.preventDefault();
//
//     const index = deleteButton.dataset.index;
//
//     const xhr = new XMLHttpRequest();
//     xhr.open('DELETE', `./read?index=${index}`);
//     xhr.onreadystatechange = () => {
//         if (xhr.readyState === XMLHttpRequest.DONE) {
//             if (xhr.status >= 200 && xhr.status < 300) {
//                 const responseText = xhr.responseText; // 'true' | 'false'
//                 if (responseText === 'true') {
//                     const confirmResult = confirm('삭제하시겠습니까?');
//                     if (confirmResult === true) {
//                         alert('삭제되었습니다');
//                         location.href = '/article';
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

// patchButton.addEventListener('click', e => {
//     e.preventDefault();
//
//     const index = patchButton.dataset.index;
//
//     const xhr = new XMLHttpRequest();
//     xhr.open('GET', `./patch?index=${index}`);
//     xhr.onreadystatechange = () => {
//         if (xhr.readyState === XMLHttpRequest.DONE) {
//             if (xhr.status >= 200 && xhr.status < 300) {
//                 location.href = `/article/patch?index=${index}`
//             } else {
//                 alert('작성한 사용자가 아니라 수정이 불가능합니다.');
//             }
//         }
//     };
//     xhr.send();
// });


// 댓글

// function postComment(content, commentIndex, toFocus, refreshCommentAfter){
//     refreshCommentAfter ??= true;
//     const articleIndex = bulletinForm['articleIndex'].value;
//     const xhr = new XMLHttpRequest();
//     const formData = new FormData();
//     formData.append('articleIndex', articleIndex);
//     formData.append('content', content);
//     if (commentIndex) { //대댓글이 아닌 첫번째 댓글일 경우 commentIndex가 없다
//         formData.append('commentIndex', commentIndex);
//     }
//     xhr.open('POST', '/comment');
//     xhr.onreadystatechange = () => {
//         if (xhr.readyState === XMLHttpRequest.DONE) {
//             if (xhr.status >= 200 && xhr.status < 300) {
//                 if (xhr.responseText === 'true') {
//                     if (toFocus) {
//                         toFocus.value = '';
//                         toFocus.focus();
//                     }
//                     if (refreshCommentAfter === true) {
//                         refreshComment();
//                     }
//                 } else {
//                     alert('댓글을 작성하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
//                 }
//             } else {
//                 alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
//             }
//         }
//     };
//     xhr.send(formData);
// }

function postComment(content, commentIndex, toFocus, refreshCommentAfter) {
    refreshCommentAfter ??= true;
    const articleIndex = bulletinForm['articleIndex'].value;
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('articleIndex', articleIndex);
    formData.append('content', content);
    if (commentIndex) {
        formData.append('commentIndex', commentIndex);
    }
    xhr.open('POST', '/comment');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure':
                        alert('댓글을 작성하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
                        break;
                    case 'failure_not_login':
                        alert('로그인 상태가 아닙니다. 로그인해주세요.');
                        break;
                    case 'success':
                        if (toFocus) {
                            toFocus.value = '';
                            toFocus.focus();
                        }
                        if (refreshCommentAfter === true) {
                            refreshComment();
                        }
                        break;
                    case 'success_same':
                        if (toFocus) {
                            toFocus.value = '';
                            toFocus.focus();
                        }
                        if (refreshCommentAfter === true) {
                            refreshComment();
                        }
                        break;
                    default:
                        alert('서버가 알 수 없는 응답을 반환하였습니다.');
                }
            } else {
                alert('서버와 통신하지 못하였습니다.');
            }
        }
    };
    xhr.send(formData);
}


function refreshComment() {
    commentContainer.innerHTML = ''; // Clear existing comments
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `/comment?articleIndex=${bulletinForm['articleIndex'].value}`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const comments = JSON.parse(xhr.responseText);
                for (const comment of comments) {
                    if (comment['deleted'] === false) {
                        const div = document.createElement('div');
                        div.classList.add('comment-left');

                        const headDiv = document.createElement('div');
                        headDiv.classList.add('comment-head');
                        const bodyDiv = document.createElement('div');
                        bodyDiv.classList.add('comment-body');

                        const dtDate = comment['createdAt'].split('T')[0];
                        const dtTime = comment['createdAt'].split('T')[1].split('.')[0];
                        headDiv.innerText = `${dtDate} ${dtTime}`;

                        const editButton = document.createElement('button');
                        editButton.classList.add('edit-button');
                        editButton.innerText = '수정';
                        editButton.addEventListener('click', () => {
                            editComment(comment);
                        });

                        const deleteButton = document.createElement('button');
                        deleteButton.classList.add('delete-button');
                        deleteButton.innerText = '삭제';
                        deleteButton.addEventListener('click', () => {
                            deleteComment(comment);
                        });

                        bodyDiv.innerText = comment['content'];

                        div.append(headDiv, editButton, deleteButton, bodyDiv);
                        commentContainer.appendChild(div);
                    }
                }
            } else {
                alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
            }
        }
    };
    xhr.send();
}

bulletinForm.onsubmit = function (e) {
    e.preventDefault();
    if (bulletinForm['content'].value == '') {
        alert('댓글을 입력해 주세요.');
        bulletinForm['content'].focus();
        return;
    }
    postComment(bulletinForm['content'].value, undefined, bulletinForm['content']);
}

document.addEventListener('DOMContentLoaded', () => {
    refreshComment();
});

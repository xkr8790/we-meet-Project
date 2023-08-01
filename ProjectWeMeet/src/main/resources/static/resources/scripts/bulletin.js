const list = document.querySelector('.container');
const listScrollWidth = list.scrollWidth;
const listClientWidth = list.clientWidth;

const bulletinForm = document.getElementById('bulletinForm');
const commentContainer = document.getElementById('commentContainer');
const commentButton = bulletinForm.querySelector('[name="commentButton"]');


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










// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트//



//
function postComment(content, commentIndex, toFocus, refreshCommentAfter) {
    refreshCommentAfter ??= true;
    const articleIndex = bulletinForm['articleIndex'].value;
    const articleEmail = bulletinForm['articleEmail'].value;
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('articleIndex', articleIndex);
    formData.append('content', content);
    formData.append('articleEmail', articleEmail);
    formData.append('nickname',bulletinForm['nickname'].value);
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

// 댓글의 시간수정
function formatDate(date) {
    const options = {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
    };
    return new Date(date).toLocaleString('en-US', options);
}
function refreshComment() {
    const articleIndex = bulletinForm['articleIndex'].value;
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `/comment?articleIndex=${articleIndex}`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const comments = JSON.parse(xhr.responseText);
                commentContainer.innerHTML = ''; // Clear existing comments

                for (const comment of comments) {
                    const div = document.createElement('div');
                    let commentClass = 'comment-left';

                    if (comment.same === true) {
                        commentClass = 'comment-right';
                    }
                    div.classList.add(commentClass);

                    const nicknameDiv = document.createElement('div');
                    nicknameDiv.classList.add('comment-nickname');
                    nicknameDiv.innerText = comment['nickname'];

                    const headDiv = document.createElement('div');
                    headDiv.classList.add('comment-head');
                    const formattedDate = formatDate(comment['createdAt']);
                    headDiv.innerText = formattedDate;


                    const bodyDiv = document.createElement('div');
                    bodyDiv.classList.add('comment-body');



                    const deleteButton = document.createElement('button');
                    deleteButton.classList.add('delete-button');
                    deleteButton.innerText = '삭제';
                    deleteButton.dataset.commentIndex = comment.index;
                    deleteButton.addEventListener('click', (e) => {
                        e.preventDefault();
                        if (!confirm('정말로 해당 댓글을 삭제할까요?')) {
                            return;
                        }
                        const xhr = new XMLHttpRequest();
                        const formData = new FormData();
                        xhr.open('DELETE', '/comment?index=' + e.target.dataset.commentIndex);
                        xhr.onreadystatechange = () => {
                            if (xhr.readyState === XMLHttpRequest.DONE) {
                                if (xhr.status >= 200 && xhr.status < 300) {
                                    const responseObject = JSON.parse(xhr.responseText);
                                    switch (responseObject.result) {
                                        case 'failure':
                                            alert('알 수 없는 이유로 댓글을 삭제하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
                                            break;
                                        case 'failure_deleted':
                                            alert('이미 삭제된 댓글 입니다. 새로고침 후 확인해 주세요.');
                                            break;
                                        case 'failure_no_authority':
                                            alert('삭제할 수 있는 권한이 없습니다.');
                                            break;
                                        case 'failure_not_login':
                                            alert('로그인 후 다시 시도해 주세요');
                                            break;
                                        case 'success':
                                            refreshComment();
                                            break;
                                        default:
                                            alert('서버가 알 수 없는 응답을 반환 했습니다.');
                                    }
                                } else {
                                    alert('서버와 통신할 수 없습니다.');
                                }
                            }
                        };
                        xhr.send(formData);
                    });

                    if (comment['deleted']) {
                        bodyDiv.innerText = '삭제된 댓글입니다.';
                        bodyDiv.style.color = '#a0a0a0';
                        bodyDiv.style.fontStyle = 'italic';
                        bodyDiv.classList.add('del');
                        div.appendChild(nicknameDiv);
                        div.appendChild(bodyDiv);
                    } else {
                        bodyDiv.innerText = comment['content'];
                        const commentBox = document.createElement('div');
                        commentBox.classList.add('comment-box');

                        commentBox.append(headDiv, deleteButton, bodyDiv);
                        div.append(nicknameDiv, commentBox);
                    }

                    commentContainer.appendChild(div);
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
};

document.addEventListener('DOMContentLoaded', () => {
    refreshComment();
});


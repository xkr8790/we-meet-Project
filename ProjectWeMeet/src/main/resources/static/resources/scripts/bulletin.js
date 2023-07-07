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
});

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
});



// 댓글

function postComment(content, commentIndex, toFocus, refreshCommentAfter){
    refreshCommentAfter ??= true;
    const articleIndex = bulletinForm['articleIndex'].value;
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('articleIndex', articleIndex);
    formData.append('content', content);
    if (commentIndex) { //대댓글이 아닌 첫번째 댓글일 경우 commentIndex가 없다
        formData.append('commentIndex', commentIndex);
    }
    xhr.open('POST', '/comment');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                if (xhr.responseText === 'true') {
                    if (toFocus) {
                        toFocus.value = '';
                        toFocus.focus();
                    }
                    if (refreshCommentAfter === true) {
                        refreshComment();
                    }
                } else {
                    alert('댓글을 작성하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
                }
            } else {
                alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
            }
        }
    };
    xhr.send(formData);
}


function refreshComment() {
    commentContainer.innerHTML = ''; //댓글초기화시 중복삭제
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `/comment?articleIndex=${bulletinForm['articleIndex'].value}`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const createComment = function (comment, indent) {
                    const tr = document.createElement('tr');
                    const td = document.createElement('td');
                    const headDiv = document.createElement('div'); //날짜
                    const bodyDiv = document.createElement('div'); //내용
                    const dtDate = comment['createdAt'].split('T')[0]; //2023-05-26
                    const dtTime = comment['createdAt'].split('T')[1].split('.')[0]; // 02:31:32
                    headDiv.innerText = `${dtDate} ${dtTime} `;
                    if (comment['deleted'] === false) {
                        const deleteAnchor = document.createElement('a');
                        deleteAnchor.setAttribute('href', '#');
                        deleteAnchor.innerText = '삭제';
                        deleteAnchor.onclick = function(e) {
                            e.preventDefault();
                            if (!confirm('정말로 해당 댓글을 삭제할까요?')) {
                                return;
                            }
                            const xhr = new XMLHttpRequest();
                            const formData = new FormData();
                            formData.append('index', comment['index']);
                            xhr.open('DELETE', '/article/comment');
                            xhr.onreadystatechange = () => {
                                if (xhr.readyState === XMLHttpRequest.DONE) {
                                    if (xhr.status >= 200 && xhr.status < 300) {
                                        if (xhr.responseText === 'true') {
                                            refreshComment();
                                        } else {
                                            alert('알 수 없는 이유로 댓글을 삭제하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
                                        }
                                    } else {
                                        alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
                                    }
                                }
                            };
                            xhr.send(formData);
                        };
                        headDiv.append(deleteAnchor);
                        bodyDiv.innerText = comment['content'];
                    } else {
                        bodyDiv.innerText = '삭제된 댓글입니다.';
                        bodyDiv.style.color = '#a0a0a0';
                        bodyDiv.style.fontStyle = 'italic';
                    }
                    td.append(headDiv, bodyDiv); //td 의 자식으로 두 개의 인자를 넣음

                    if (comment['deleted'] === false) {

                        const replyForm = document.createElement('form');
                        const replyTopHr = document.createElement('hr');
                        const replyBottomHr = document.createElement('hr');
                        const replyCommentIndexInput = document.createElement('input');
                        const replyContentInput = document.createElement('input');
                        const replySubmitInput = document.createElement('input');
                        replyCommentIndexInput.setAttribute('type', 'hidden');
                        replyCommentIndexInput.setAttribute('name', 'commentIndex');
                        replyCommentIndexInput.value = comment['index'];
                        replyContentInput.setAttribute('type', 'text');
                        replyContentInput.setAttribute('maxlength', '100');
                        replyContentInput.setAttribute('name', 'content');
                        replySubmitInput.setAttribute('type', 'submit');
                        replySubmitInput.value = '작성';
                        replyForm.style.display = 'none';
                        replyForm.style.marginLeft = '1rem';
                        replyForm.onsubmit = function (e) {
                            e.preventDefault();
                            if (replyForm['content'].value === '') {
                                alert('내용을 입력해 주세요.');
                                replyForm['content'].focus();
                                return;
                            }
                            postComment(replyForm['content'].value, replyForm['commentIndex'].value, replyForm['content']);
                        };
                        replyForm.append(replyTopHr, replyCommentIndexInput, replyContentInput, replySubmitInput, replyBottomHr);

                        const replyHeadDiv = document.createElement('div');
                        const replyToggleAnchor = document.createElement('a');
                        replyToggleAnchor.setAttribute('href', '#');
                        replyToggleAnchor.innerText = '답글 달기';
                        replyToggleAnchor.dataset.toggled = 'false';
                        replyToggleAnchor.onclick = function (e) {
                            e.preventDefault();
                            if (replyToggleAnchor.dataset.toggled === 'false') {
                                replyToggleAnchor.innerText = '취소';
                                replyToggleAnchor.dataset.toggled = 'true';
                                replyForm.style.display = 'block';
                                replyForm['content'].value = '';
                                replyForm['content'].focus();
                            } else {
                                replyToggleAnchor.innerText = '답글 달기';
                                replyToggleAnchor.dataset.toggled = 'false';
                                replyForm.style.display = 'none';
                            }
                        };
                        replyHeadDiv.append(replyToggleAnchor);
                        td.append(replyHeadDiv, replyForm);
                    }

                    const hr = document.createElement('hr');
                    td.append(hr);

                    tr.style.position = 'relative';
                    tr.style.left = `${indent}px`;
                    tr.append(td);
                    return tr;
                };
                const handleSubComments = function (commentCollection, subComments, level = 0) {
                    const indentFactor = 50;
                    const indent = level * indentFactor;
                    for (const subComment of subComments) {
                        const tr = createComment(subComment, indent);
                        commentContainer.append(tr);

                        const subSubComments = commentCollection.filter(x => x['commentIndex'] === subComment['index']);
                        handleSubComments(commentCollection, subSubComments, level + 1);
                    }
                };
                const comments = JSON.parse(xhr.responseText);
                const parentComments = comments.filter(x => x['commentIndex'] === null);
                handleSubComments(comments, parentComments);

                // for (const comment of comments.filter(x => x['commentIndex'] === null)) {
                //     const tr = createComment(comment);
                //     commentContainer.append(tr);
                //
                //     const subComments = comments.filter(x => x['commentIndex'] === comment['index']);
                //     handleSubComments(comments, subComments);
                // }
            } else {
                alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
            }
        }
    };
    xhr.send();
}


bulletinForm.onsubmit = function (e){
    e.preventDefault();
    if (bulletinForm['content'].value==''){
        alert('댓글을 입력해 주세요.');
        bulletinForm['content'].focus();
        return;
    }
    postComment(bulletinForm['content'].value, undefined, bulletinForm['content']);
}

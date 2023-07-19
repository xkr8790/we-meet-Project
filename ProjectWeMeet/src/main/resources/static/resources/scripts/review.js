const reviewForm = document.getElementById('reviewForm');
const deleteButtons = reviewForm.querySelectorAll('[rel="delete"]');

deleteButtons.forEach(deleteButton => {
    deleteButton.addEventListener('click', e => {
        e.preventDefault();

        const index = deleteButton.dataset.index;
        const xhr = new XMLHttpRequest();
        xhr.open('DELETE', `review/delete/?index=${index}`);
        xhr.onreadystatechange = () => {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status >= 200 && xhr.status < 300) {
                    const responseText = xhr.responseText; // 'true' | 'false'
                    if (responseText === 'true') {
                        location.href += '';
                    } else {
                        alert('알 수 없는 이유로 삭제하지 못하였습니다.\n\n이미 삭제된 메모일 수도 있습니다.');
                    }
                } else {
                    alert('서버와 통신하지 못하였습니다.\n\n잠시 후 다시 시도해 주세요.');
                }
            }
        };
        xhr.send();
    });
});



function postReview(content, toFocus, refreshCommentAfter) {

    refreshCommentAfter ??= true;

    const articleIndex = reviewForm['articleIndex'].value;
    const reviewStar = reviewForm['reviewStar'].value;
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('articleIndex', articleIndex);
    formData.append('content', content);
    formData.append('reviewStar', reviewStar);
    // if (reviewIndex !== null && reviewIndex !== undefined) {
    //     formData.append('reviewIndex', reviewIndex)
    // }
    xhr.open('POST', `review/index`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                if (toFocus) {
                    toFocus.value = '';
                    toFocus.focus();
                }
                if (refreshCommentAfter === true) {
                    location.href += '';
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



// reviewForm.onsubmit = e => {
//     e.preventDefault();
//
//     if (reviewForm['content'].value === '') {
//         alert('댓글을 입력해 주세요');
//         reviewForm['content'].focus();
//         return;
//     }
//     postReview(reviewForm['content'].value);
// };


submitForm = reviewForm.querySelector('[name="submit"]')

submitForm.addEventListener('click', e=>{
    e.preventDefault();
    if (reviewForm['content'].value === '') {
        alert('댓글을 입력해 주세요');
        reviewForm['content'].focus();
        return;
    }

    postReview(reviewForm['content'].value);
})

const writeButton = reviewForm.querySelector('[name="write"]');

writeButton.addEventListener('click', e => {
    e.preventDefault();
    window.location.href ="/write";
})






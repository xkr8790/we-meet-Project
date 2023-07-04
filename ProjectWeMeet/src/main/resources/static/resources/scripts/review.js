const reviewForm = document.getElementById('reviewForm');

// var selectedStar = document.querySelector('input[name="reviewStar"]:checked').value;

function postReview(content, toFocus, refreshCommentAfter){

    refreshCommentAfter ??= true;

    const articleIndex = reviewForm['articleIndex'].value;
    const reviewStar = reviewForm['reviewStar'].value;
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('articleIndex', articleIndex);
    formData.append('content', content);
    formData.append('reviewStar', reviewStar);
    xhr.open('POST',`/review`);
    xhr.onreadystatechange = () => {
        if(xhr.readyState === XMLHttpRequest.DONE){
        if(xhr.status >=200 && xhr.status<300){
            if(toFocus){
                toFocus.value='';
                toFocus.focus();
            }
            if(refreshCommentAfter === true){
                location.href='';
            }
        }else{
            alert('댓글을 작성하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
        }
     } else{
            alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
        }
    };
    xhr.send(formData);


}

reviewForm.onsubmit = function (e) {
    e.preventDefault();

    if (reviewForm['content'].value === '') {
        alert('댓글을 입력해 주세요');
        reviewForm['content'].focus();
        return;
    }
    postReview(reviewForm['content'].value, undefined, reviewForm['content']);

};



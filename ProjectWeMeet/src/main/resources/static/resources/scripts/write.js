const thumnail = document.querySelector('.thumbnail');

ArticleForm.onsubmit = e =>{
    e.preventDefault();
    if(thumnail === null){
        alert('썸네일을 올려주세요');
        return;
    }

    if(ArticleForm['ArticleTitle'].value === ''){
        alert('제목을 입력해주세요');
        return;
    }
    if(ArticleForm['content'].value === ''){
        alert('내용을 입력해주세요');
        return;
    }

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    xhr.open('POST','/write');
    xhr.onreadystatechange = () => {
        if(xhr.readyState === XMLHttpRequest.DONE){
            if(xhr.status >= 200 && xhr.status<300) {
                alert('아아아');
            }else {
                alert('실패패');
            }
        }
    };
    xhr.send();
}
const thumnail = document.querySelector('.thumbnail');

articleForm.onsubmit = e =>{
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
    formData.append('email', writeForm['place'].value);
    formData.append('content',articleForm['content'].value);
    xhr.open('POST','/write');
    xhr.onreadystatechange = () => {
        if(xhr.readyState === XMLHttpRequest.DONE){
            if(xhr.status >= 200 && xhr.status<300) {
                alert('성공');
            }else {
                alert('실패');
            }
        }
    };
    xhr.send();
}
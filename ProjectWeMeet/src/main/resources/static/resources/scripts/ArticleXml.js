const time = document.querySelector('.time');
const participants = document.querySelector('.participants');
const category = document.querySelector('.category');


ArticleForm.onsubmit = e => {
    e.preventDefault();
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    xhr.open('POST', 'write');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                alert('성공');
            } else {
                alert('실패');
            }
        }
    };
    xhr.send();
}
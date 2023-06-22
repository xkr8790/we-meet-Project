ClassicEditor
    .create(document.querySelector('#editor'), {
        language: 'ko', //언어설정
        toolbar: ['heading', '|', 'Bold', 'Italic', '|', 'link', 'bulletedList', 'numberedList', '|', 'Undo', 'Redo'] //내가 넣고싶은 툴바 설정
    })
    .catch(error => {
        console.error(error);
    });

const ArticleWrite = document.getElementById('Article-Write');

ArticleWrite.onsubmit = e => {
    e.preventDefault();
    if (ArticleWrite['text'].value === ''){
        alert('내용이 비었다');
    }

}

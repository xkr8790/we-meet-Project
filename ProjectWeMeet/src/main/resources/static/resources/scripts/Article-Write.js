const ArticleWrite = document.getElementById('Article-Write');
ClassicEditor
    .create(document.querySelector('#editor'), {
        language: 'ko', //언어설정
        toolbar: ['heading', '|', 'Bold', 'Italic', '|', 'link', 'bulletedList', 'numberedList', '|', 'Undo', 'Redo'] //내가 넣고싶은 툴바 설정
    })
    .catch(error => {
        console.error(error);
    });

const ArticleTag = document.querySelector('.article-tag');

let tagCounter = 0; //전역변수 태그카운터

ArticleTag.addEventListener('click', function () {
    if (tagCounter >= 7) {
        return; // 최대 태그 제한(7개)에 도달했을 경우 클릭해도 동작하지 않음
    }

    const existingTags = ArticleTag.querySelectorAll('.tag');
    for (let i = 0; i < existingTags.length; i++) {
        const tag = existingTags[i];
        if (tag.textContent.trim() === '#') {
            return; // 이미 생성된 태그가 있고, 해당 태그의 내용이 비어있으면 클릭해도 동작하지 않음
        }
    }

    const Tag = document.createElement('div');
    Tag.textContent = '#'; //처음 생성시 #추가
    Tag.classList.add('tag'); //처음생성시 tag클래스 추가
    Tag.contentEditable = true; //div 글자입력 가능
    ArticleTag.appendChild(Tag); //article-tag의 자식으로 들어감

    tagCounter++; // 태그 생성 횟수 증가
});

ArticleWrite.onsubmit = e => {
    e.preventDefault();
    if(ArticleWrite['ArticleTitle'].value === ''){
        alert('제목이 비어있습니다');
        return;
    }
    if(ArticleWrite['content'].value === ''){
        alert('내용이 비어있습니다');
        return;
    }
    if(tagCounter === 0){
        alert('태그가 비어있습니다');
        return;
    }
}

// 파일 선택 시 미리보기 기능 구현
const photoUpload = document.getElementById('photo-upload');
const thumbnailPreview = document.getElementById('thumbnail-preview');

photoUpload.addEventListener('change', function () {
    const file = photoUpload.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            thumbnailPreview.src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
});

const ArticleForm = document.getElementById('Article-Form');
ClassicEditor
    .create(document.querySelector('#editor'), {
        language: 'ko', //언어설정
        toolbar: ['heading', '|', 'Bold', 'Italic', '|', 'link', 'bulletedList', 'numberedList', '|', 'Undo', 'Redo'] //내가 넣고싶은 툴바 설정
    })
    .catch(error => {
        console.error(error);
    });

const ArticleTag = document.querySelector('.article-tag'); //tag를 담을 부모
const explainTag = document.querySelector('.explainTag'); //설명

let tagCounter = 0; //전역변수 태그카운터

ArticleTag.addEventListener('click', function () {
    explainTag.style.display = 'none'; // 처음에 태그 몇 개 적는지 설명하는 div
    if (tagCounter >= 5) {
        return; // 최대 태그 제한(5개) 그 이상 클릭해도 작동X
    }
    const existingTags = ArticleTag.querySelectorAll('.tag');
    for (let i = 0; i < existingTags.length; i++) {
        const tag = existingTags[i];
        if (tag.textContent.trim() === '#') {
            return; // 이미 생성된 태그가 있고, 해당 태그의 내용이 비어있으면 클릭해도 동작하지 않음
        }
    }

    const Tag = document.createElement('div');
    Tag.textContent = '#'; // 처음 생성시 # 추가
    Tag.classList.add('tag'); // 처음 생성시 tag 클래스 추가
    Tag.contentEditable = true; // div 글자 입력 가능
    ArticleTag.appendChild(Tag); // article-tag의 자식으로 들어감

    tagCounter++; // 태그 생성 횟수 증가

    Tag.addEventListener('keydown', function(event) {
        if (event.key === '#') {
            event.preventDefault();
        } else if (event.key === 'Backspace' && Tag.textContent === '#') {
            event.preventDefault();
        } else if (event.key === 'Enter') {
            event.preventDefault(); // Prevent line break on Enter key
        } else if (Tag.innerHTML.length >= 11) {
            event.preventDefault();
        }
    });
    // keyDonw즉 사용자가 Tag(div)안에 무언갈 쓸때 일어나는 이벤트이다.
    //결론적으로 #은 태그의 시작이니 지우거나 쓰지를 못하면서
    // Enter 줄바꿈 방지하고 태그를 많이쓰는걸방지하기 위해 11자만 쓰는게 가능

    Tag.addEventListener('click', function() {
        // Tag.style.color = '#979797'; // 클릭시 색상을 빨간색으로 변경
    });
});



ArticleForm.onsubmit = e => {
    e.preventDefault();
    if(ArticleForm['ArticleTitle'].value === ''){
        alert('제목이 비어있습니다');
        return;
    }
    if(ArticleForm['content'].value === ''){
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

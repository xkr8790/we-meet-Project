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
let tags = [];

ArticleTag.addEventListener('click', function () {
    if (event.target.classList.contains('tag')) {
        return; // 이미 생성된 태그를 클릭한 경우 생성 코드 실행하지 않음
    }

    explainTag.addEventListener('click', function () {
        explainTag.style.display = 'none';
    })

    explainTag.style.display = 'none'; // 처음에 태그 몇 개 적는지 설명하는 div
    if (tagCounter >= 6) {
        return; // 최대 태그 제한(6개) 그 이상 클릭해도 작동X
    }

    const existingTags = ArticleTag.querySelectorAll('.tag');
    for (let i = 0; i < existingTags.length; i++) {
        const tag = existingTags[i];
        if (tag.value.trim() === '#') {
            return; // 이미 생성된 태그가 있고, 해당 태그의 내용이 비어있으면 클릭해도 동작하지 않음
        }
    }

    const Tag = document.createElement('input');
    Tag.setAttribute('type', 'text');
    Tag.value = '#'; // 처음 생성시 # 추가
    Tag.classList.add('tag'); // 처음 생성시 tag 클래스 추가
    Tag.maxLength = 20;

    Tag.focus();

    Tag.addEventListener('keydown', function (event) {
        const trimmedText = Tag.value.trim();
        if (event.key === 'Backspace' && trimmedText === '#') {
            event.preventDefault();
        } else if (event.key === 'Enter' || event.key === 'Delete') {
            event.preventDefault();
        }
    });

    Tag.addEventListener('input', function (event) {
        const trimmedText = Tag.value.trim();
        if (trimmedText.length > 19) {
            Tag.value = trimmedText.slice(0, 20);
        }
    });


    Tag.addEventListener('mousedown', function(event) {
        event.preventDefault();
        Tag.focus();
    }); //드래그를 막으면서 input 쓰기 가능하게함

    Tag.addEventListener('input', function (event) {
        const trimmedText = Tag.value.trim();
        if (trimmedText.length === 0) {
            Tag.value = '#';
        }
    });

    if (tags.length > 0) {
        const previousTag = tags[tags.length - 1];
        previousTag.disabled = true;
    }
    tags.push(Tag);
    tagCounter++; // 태그 생성 횟수 증가
});


const photoUpload = document.getElementById('photo-upload');
const thumbnailPreview = document.getElementById('thumbnail-preview');
const thumbnailUpload = document.querySelector('.thumbnail-upload');

photoUpload.addEventListener('change', function() {
    const file = photoUpload.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            thumbnailPreview.src = e.target.result;
            thumbnailUpload.textContent = '썸네일 변경';
        };
        reader.readAsDataURL(file);
    }
});



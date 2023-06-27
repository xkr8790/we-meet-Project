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


    explainTag.addEventListener('click', function () {
        explainTag.style.display = 'none';
    })

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
    Tag.style.userSelect = 'none'; // 일반적인 방식
    ArticleTag.appendChild(Tag); // article-tag의 자식으로 들어감

    Tag.addEventListener('mousedown', function(event) {
        event.preventDefault();
    });

    if (Tag.textContent.charAt(0) === '#') {
        Tag.focus();
        const selection = window.getSelection();
        const range = document.createRange();
        range.setStart(Tag.firstChild, 1); // # 다음 문자에 커서 설정
        range.collapse(true);
        selection.removeAllRanges();
        selection.addRange(range);
    }


    tagCounter++; // 태그 생성 횟수 증가


    Tag.addEventListener('click', function (event) {
        if (Tag !== event.target) {
            event.stopPropagation();
            return;
        }
        const trimmedText = Tag.textContent.trim().replace(/\s+/g, '');
        if (trimmedText.length > 1) {
            event.stopPropagation(); // 내용이 있는 경우 클릭 이벤트 전파 중지
        } else if (Tag.classList.contains('TA')) {
            Tag.contentEditable = true; // 새로운 태그를 클릭했을 때 입력 가능하도록 설정
        } else {
            event.stopPropagation(); // 기존 태그의 클릭 이벤트 전파 중지
        }
    });

    function getCaretPosition(element) {
        const selection = window.getSelection();
        const range = document.createRange();
        range.setStart(element.firstChild, 0);
        range.setEnd(selection.focusNode, selection.focusOffset);
        return range.toString().length;
    }


    Tag.addEventListener('keydown', function (event) {
        const key = event.key;
        const trimmedText = Tag.textContent.trim().replace(/\s+/g, '');

        if (key === 'ArrowLeft') {
            const caretPosition = getCaretPosition(Tag);
            if (caretPosition <= 1 && trimmedText.charAt(0) === '#') {
                event.preventDefault();
            }
        } else if (key === 'ArrowRight') {
            const caretPosition = getCaretPosition(Tag);
            if (caretPosition === trimmedText.length && trimmedText.charAt(0) === '#') {
                event.preventDefault();
            }
        } else if (key === 'Backspace') {
            if (trimmedText === '#') {
                event.preventDefault();
            }
        } else if (key === '#') {
            event.preventDefault();
        } else if (key === 'Enter') {
            event.preventDefault();
        }
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

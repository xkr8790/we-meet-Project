const ArticleWrite = document.getElementById('Article-Write');
ClassicEditor
    .create(document.querySelector('#editor'), {
        language: 'ko', //언어설정
        toolbar: ['heading', '|', 'Bold', 'Italic', '|', 'link', 'bulletedList', 'numberedList', '|', 'Undo', 'Redo'] //내가 넣고싶은 툴바 설정
    })
    .catch(error => {
        console.error(error);
    });

const articleTag = document.querySelector('.article-tag');
let Tag = document.querySelector('.tag');


articleTag.addEventListener('click', function () {
    const existingTags = document.querySelectorAll('.tag');

    if (existingTags.length < 6) {
        const newTag = Tag.cloneNode(true);
        newTag.style.background = '#eff0f2';
        newTag.style.borderRadius = '0.2rem';
        newTag.style.fontSize = '0.8rem';
        newTag.style.height = '30px';
        newTag.style.display = 'flex';
        newTag.style.alignItems = 'center';
        newTag.style.padding = '0 9px';
        newTag.style.marginLeft = '0.5rem';

        articleTag.appendChild(newTag);

        newTag.focus();
    }
});


ArticleWrite.onsubmit = e => {
    e.preventDefault();
    if (ArticleWrite['ArticleTitle'].value === '') {
        alert('제목이 비었다');
        ArticleWrite['ArticleTitle'].focus();
        ArticleWrite['ArticleTitle'].select();
        return;
    }
    if (ArticleWrite['content'].value === '') {
        alert('내용이 비었다');
        ArticleWrite['content'].focus();
        ArticleWrite['content'].select();
        return;
    }
    if (ArticleWrite['tag'].value === '') {
        alert('태그가 비어있습니다 태그를 적어주세요');
        ArticleWrite['tag'].focus();
        return;
    }
}


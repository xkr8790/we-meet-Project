const Tagg = document.querySelectorAll('.tag');
const values = [];
let hasValue = false; // 값이 있는지 여부를 나타내는 플래그

ArticleForm.onsubmit = e => {
    e.preventDefault();

    if (ArticleForm['ArticleTitle'].value === '') {
        alert('제목을 입력해주새요');
        return;
    }//게시판 제목이 없을 경우

    if (ArticleForm['content'].value === '') {
        alert('내용을 입력해주세요')
        return;
    }//게시판 내용이 없다면

    if (Tags.childElementCount === 0) {
        alert('태그를 생성해주세요');
        return;
    } //태그가 생성되지 않았을때

}
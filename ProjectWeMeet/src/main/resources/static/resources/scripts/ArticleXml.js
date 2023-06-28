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


    for (let i = 0; i < Tagg.length; i++) {
        const input = Tagg[i];
        const value = input.value.trim(); // 값의 앞뒤 공백 제거
        if (value !== '') {
            values.push(value); // 값이 비어 있지 않으면 배열에 추가
            hasValue = true; // 값이 있는 경우 플래그를 true로 설정
        }
    }
    if (!hasValue || (hasValue && values.length === 1 && values[0] === '#')) {
        alert('태그를 입력해주세요');
    } else {
        alert(values);
    }
}
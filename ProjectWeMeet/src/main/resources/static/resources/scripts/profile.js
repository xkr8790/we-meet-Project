const settingButton = document.getElementById('setting');
const popup = document.getElementById('popup'); // 나타낼 팝업 창 요소의 ID

settingButton.addEventListener('click', () => {
    popup.style.display = 'block';
});

// 팝업 창 외부를 클릭하면 창이 닫히도록 설정
window.addEventListener('click', (event) => {
    if (event.target === popup) {
        popup.style.display = 'none';
    }
});

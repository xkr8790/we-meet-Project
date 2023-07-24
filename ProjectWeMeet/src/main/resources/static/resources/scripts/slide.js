const body = document.getElementById('body');

window.onload = function() {
   document.body.style.transitionDuration = '600ms'; // 애니메이션 지속 시간 설정
    document.body.style.transitionTimingFunction = 'ease'; // 타이밍 함수 설정
    document.body.style.transitionProperty = 'opacity'; // 타이밍 함수 설정
    document.body.style.opacity = '1';
}


document.addEventListener("DOMContentLoaded", function () {
    let sliderIndex = 0;
    const slideWidth = document.querySelector('.slide').clientWidth;
    const slider = document.querySelector('.slider');
    const navigationButtons = document.querySelectorAll('.navigation-button');

    // 슬라이드를 옆으로 이동하는 함수
    function slideTo(index) {
        sliderIndex = index;
        slider.style.transform = `translateX(${-sliderIndex * slideWidth}px)`;

        // 활성화된 버튼 표시
        navigationButtons.forEach((button, buttonIndex) => {
            button.classList.toggle('active', buttonIndex === sliderIndex);
        });
    }

    // 네비게이션 버튼 클릭 시 해당 페이지로 이동
    navigationButtons.forEach((button, index) => {
        button.addEventListener('click', () => {
            slideTo(index);
        });
    });

    setInterval(() => {
        sliderIndex++;
        if (sliderIndex >= navigationButtons.length) {
            sliderIndex = 0;
        }
        slideTo(sliderIndex);
    }, 4400);
});

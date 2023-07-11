const Article = document.getElementById('Article');
const category = Article.querySelectorAll('.category-item');



//페이지 로드시 시작되는 함수
document.addEventListener("DOMContentLoaded", function () {
    let sliderIndex = 0;
    const slideWidth = document.querySelector('.article-container').clientWidth;
    const slider = document.querySelector('.slider');
    const navigationButtons = document.querySelectorAll('.category-item');

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

});


// URL에 category 매개변수가 있는지 확인합니다.

document.addEventListener('DOMContentLoaded', function () {
    const links = document.querySelectorAll('.category-item');
    for (let i = 0; i < links.length; i++) {
        links[0].classList.add('underline');
        links[i].addEventListener('click', function (e) {
            links.forEach(function (link) {
                link.classList.remove('underline');
            });
            // 클릭된 링크에만 밑줄 출력
            this.classList.add('underline');
        });
    }
});



const boardList = document.getElementsByClassName("article-list");

const boards = Array.from(boardList);

boards.forEach(function (board) {
    let images = board.getElementsByClassName("Profile-Picture");
    if (images.length >= 4) {
        const cover = document.createElement("img");
        cover.src = "resources/images/mainImages/cover.png";
        cover.classList.add("cover");
        // images[3]의 자식으로 cover 추가
        images[3].appendChild(cover);
        for (let i = 4; i < images.length; i++) {
            images[i].style.display = "none";
        }
    }
});

//
// const categoryItems = document.querySelectorAll('.category-item');
// const slider = document.querySelector('.slider');
// const slide = document.querySelector('.slide');
//
// categoryItems.forEach(() => {
//     const Slide = slide.cloneNode(true); //복제한다
//     slider.appendChild(Slide);
// });



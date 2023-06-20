
//페이지 로드시 시작되는 함수
// 슬라이드 이벤트
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

// 페이지 로드시 시작되는 함수
document.addEventListener('DOMContentLoaded', function () {
    const links = document.querySelectorAll('.category-item');

    for (let i = 0; i < links.length; i++) {
        links[i].addEventListener('click', function () {
            // 모든 링크에서 밑줄 스타일 제거
            links.forEach(function (link) {
                link.classList.remove('underline');
            }); //link갯수만큼 for문 반복

            // 클릭된 링크에만 밑줄 스타일 적용
            this.classList.add('underline');

            //link를 가리킨다 즉, 선택된 링크에만 밑줄을 추가한다.
        });
    }
});

let boardList = document.getElementsByClassName("article-list");
//article-list라는 클래스를 전부 불러와서 변수 boardList에 할당

let boards = Array.from(boardList);
//boardList를 배열로 변환해 boards라는 새로운 배열을 생성하는 역할을 합니다.

boards.forEach(function (board) {
    let images = board.getElementsByClassName("Profile-Picture");
    //참여하는 유저에 따라 사람수가 달라질수 있으므로 let으로 선언한다.

    //images(즉, 프로필)이 4개 이상이 되면
    if (images.length >= 4) {
        const cover = document.createElement("img"); //가상의 img 클래스 생성
        cover.src = "resources/images/mainImages/cover.png"; //cover 이미지 가지고 온다
        cover.classList.add("cover");

        images[3].appendChild(cover);
        // images[3]의 자식으로 cover 추가

        for (let i = 4; i < images.length; i++) {
            images[i].style.display = "none";
        }
    }
});
//images 배열은 인덱스 4부터 끝까지의 이미지 요소를 화면에서 숨기는 역할을 수행합니다.
//즉 나타내는 프로필은 4명으로 정하고 4명째는 커버라는 img를 씌운다



// const categoryItems = document.querySelectorAll('.category-item');
// const slider = document.querySelector('.slider');
// const slide = document.querySelector('.slide');
//
// categoryItems.forEach(() => {
//     const Slide = slide.cloneNode(true); //복제한다
//     slider.appendChild(Slide);
// });


// 현재 페이지의 URL에서 쿼리 매개변수 값 읽기
const queryParams = new URLSearchParams(window.location.search);
const category = queryParams.get('category');

// 값이 선택되면 해당 요소에 클래스 추가
if (category) {
    const categoryItems = document.getElementsByClassName('category-item');

    for (let i = 0; i < categoryItems.length; i++) {
        const item = categoryItems[i];

        // 선택된 카테고리와 일치하는 경우 클래스 추가
        if (item.textContent.trim() === category) {
            item.classList.add('underline');
        }
    }
}


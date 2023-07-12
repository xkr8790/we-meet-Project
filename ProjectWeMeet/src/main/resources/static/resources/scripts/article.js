const Article = document.getElementById('Article');
const category = Article.querySelectorAll('.category-item');
const articleLink = Article.querySelectorAll('.articleLink');
let url = window.location.href;

if (url.includes("%EC%98%81%ED%99%94")) {
    articleLink[1].classList.add('underline');
    //영화일때 밑줄 추가
} else if (url.includes("%EA%B2%8C%EC%9E%84")) {
    articleLink[2].classList.add('underline');
    //게임일때 밑줄 추가
} else if (url.includes("%EC%9A%B4%EB%8F%99")) {
    articleLink[3].classList.add('underline');
    //운동일때 밑줄 추가
} else if (url.includes("%EC%82%B0%EC%B1%85")) {
    articleLink[4].classList.add('underline');
    //산책일때 밑줄 추가
} else if (url.includes("%EC%8B%9D%EC%82%AC")) {
    articleLink[5].classList.add('underline');
    //식사일때 밑줄 추가
} else if (url.includes("%EB%A7%8C%EB%82%A8")) {
    articleLink[6].classList.add('underline');
    //만남일때 밑줄 추가
} else if (url.includes("%EC%99%84%EB%A3%8C")) {
    articleLink[7].classList.add('underline');
    //완료일때 밑줄 추가
}





// 페이지 로드시 시작되는 함수
// document.addEventListener("DOMContentLoaded", function () {
//     let sliderIndex = 0;
//     const slideWidth = document.querySelector('.article-container').clientWidth;
//     const slider = document.querySelector('.slider');
//     const navigationButtons = document.querySelectorAll('.category-item');
//
//     // 슬라이드를 옆으로 이동하는 함수
//     function slideTo(index) {
//         sliderIndex = index;
//         slider.style.transform = `translateX(${-sliderIndex * slideWidth}px)`;
//
//         // 활성화된 버튼 표시
//         navigationButtons.forEach((button, buttonIndex) => {
//             button.classList.toggle('active', buttonIndex === sliderIndex);
//         });
//     }
//
//     // 네비게이션 버튼 클릭 시 해당 페이지로 이동
//     navigationButtons.forEach((button, index) => {
//         button.addEventListener('click', () => {
//             slideTo(index);
//         });
//     });
//
// });


// document.addEventListener('DOMContentLoaded', function () {
//
//     const links = document.querySelectorAll('.category-item');
//     if(url === 'http://localhost:6796/article?p=1&category=%EC%98%81%ED%99%94'){
//         links[1].classList.add('underline');
//     }else if(url=== 'http://localhost:6796/article?p=1&category=%EA%B2%8C%EC%9E%84'){
//         links[2].classList.add('underline');
//     }
//
// });



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



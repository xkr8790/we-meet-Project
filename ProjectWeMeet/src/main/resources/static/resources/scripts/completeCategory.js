const Articles = document.getElementById('Article');
const categories = Articles.querySelectorAll('.category-item');
const articleLinks = Articles.querySelectorAll('.articleLink');

let url = window.location.href; //현재 컴퓨터가 실행되고있는 URL을 가져온다.

var categoriesParam = new URL(url).searchParams.get("category"); //url에서 category라는 매개변수의 값을 가져온다

if(!categoriesParam){ //전체
    articleLinks[0].classList.add('underline');
}else if(categoriesParam === '영화'){
    articleLinks[1].classList.add('underline');
}else if(categoriesParam === '게임'){
    articleLinks[2].classList.add('underline');
}else if(categoriesParam === '운동'){
    articleLinks[3].classList.add('underline');
}else if(categoriesParam === '산책'){
    articleLinks[4].classList.add('underline');
}else if(categoriesParam === '식사'){
    articleLinks[5].classList.add('underline');
}else if(categoriesParam === '만남'){
    articleLinks[6].classList.add('underline');
}else if(categoriesParam === '완료'){
    articleLinks[7].classList.add('underline');
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

// const categoryItems = document.querySelectorAll('.category-item');
// const slider = document.querySelector('.slider');
// const slide = document.querySelector('.slide');
//
// categoryItems.forEach(() => {
//     const Slide = slide.cloneNode(true); //복제한다
//     slider.appendChild(Slide);
// });




const boardLists = document.getElementsByClassName("article-list");

const board = Array.from(boardLists);

board.forEach(function (board) {
    let images = board.getElementsByClassName("Profile-Picture");
    if (images.length >= 4) {
        const covers = document.createElement("img");
        covers.src = "resources/images/mainImages/cover.png";
        covers.classList.add("cover");
        // images[3]의 자식으로 cover 추가
        images[3].appendChild(covers);
        for (let i = 4; i < images.length; i++) {
            images[i].style.display = "none";
        }
    }
});



const Article = document.getElementById('Article');
const category = Article.querySelectorAll('.category-item');
const articleLink = Article.querySelectorAll('.articleLink');

let url = window.location.href; //현재 컴퓨터가 실행되고있는 URL을 가져온다.

var categoryParam = new URL(url).searchParams.get("category"); //url에서 category라는 매개변수의 값을 가져온다

if(!categoryParam){ //전체
    articleLink[0].classList.add('underline');
}else if(categoryParam === '영화'){
    articleLink[1].classList.add('underline');
}else if(categoryParam === '게임'){
    articleLink[2].classList.add('underline');
}else if(categoryParam === '운동'){
    articleLink[3].classList.add('underline');
}else if(categoryParam === '산책'){
    articleLink[4].classList.add('underline');
}else if(categoryParam === '식사'){
    articleLink[5].classList.add('underline');
}else if(categoryParam === '만남'){
    articleLink[6].classList.add('underline');
}else if(categoryParam === '완료'){
    articleLink[7].classList.add('underline');
}

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



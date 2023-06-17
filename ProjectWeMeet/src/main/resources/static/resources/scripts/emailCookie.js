//  쿠키를 사용하여 어떤 값을 키:값 형태로 브라우저에 저장해서 사용하는 방법

//  1. 쿠키의 함수를 정의한다.
// 1-1. setCookie는 쿠키 이름과 값, 유효날짜를 파라미터로 받아서 쿠키를 설정한다.
// 1-2. deleteCooke는 쿠키이름만 받아서 그 쿠키의 유효기간을 이용하여 유효하지 않게 만들어서 삭제한다.
// 1-3. getCookie는 쿠키 이름을 받아서 그 쿠키에 들어있는 값을 반환한다.


setCookie = (cookieName, value, exdays) => {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var cookieValue = escape(value) + ((exdays ==null)? "":";expires="+exdate.toGMTString());
    document.cookie = cookieName + "=" + cookieValue;

}

deleteCookie = (cookieName) => {
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate()-1);
    document.cookie = cookieName + "=" +"; expires =" + expireDate.toGMTString();
}

getCookie = (cookieName) =>{
    cookieName = cookieName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cookieName);
    var cookieValue ='';
    if(start != -1){
        start +=cookieName.length;
        var end = cookieData.indexOf(';', start);
    }
    return unescape(cookieValue);
}
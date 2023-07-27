const article = document.getElementById('article');

for (let i = 0; i < 6; i++) {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `/profile/${article['index'].value}`); // 서버로 GET 요청을 보냄
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            // 요청이 완료되었을 때 수행할 동작
            if (xhr.status === 200) {
                // 성공적으로 처리된 경우의 동작
                const response = xhr.responseText;
                console.log(response); // 서버에서 전달받은 응답 처리
            } else {
                // 요청이 실패한 경우의 동작
                console.error('Request failed.');
            }
        }
    };
    xhr.send(); // 요청 전송
}



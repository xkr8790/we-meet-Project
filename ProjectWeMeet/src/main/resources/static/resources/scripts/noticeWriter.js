const noticeWriteForm = document.getElementById('notice-writer');

ClassicEditor.create(noticeWriteForm['content'], {
    simpleUpload: {
        uploadUrl: '/uploadImage'
    }
});


noticeWriteForm.warning = noticeWriteForm.querySelector('[rel="warning"]');
noticeWriteForm.warning.show = (text) => {
    noticeWriteForm.warning.innerText = text;
    noticeWriteForm.warning.classList.add('visible');
}
noticeWriteForm.warning.hide = () => {
    noticeWriteForm.warning.classList.remove('visible');
}


noticeWriteForm.onsubmit = e => {
    noticeWriteForm.warning.hide();
    if (noticeWriteForm['title'].value == '') {
        noticeWriteForm['title'].focus();
        noticeWriteForm.warning.show('제목을 입력해 주세요');
        return false;
    }
    if (noticeWriteForm['content'].value == '') {
        noticeWriteForm['content'].focus();
        noticeWriteForm.warning.show('내용을 입력해 주세요');
        return false;
    }
}

    const deleteButton = reviewForm.querySelectorAll('[rel="delete"]');

    deleteButton.addEventListener('click', e => {
        e.preventDefault();
        const index = deleteButton.dataset.index;
        const xhr = new XMLHttpRequest();
        xhr.open('DELETE', `noticeView?index=${index}`);
        xhr.onreadystatechange = () => {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status >= 200 && xhr.status < 300) {
                    const responseText = xhr.responseText; // 'true' | 'false'
                    if (responseText === 'true') {
                        location.href += 'noticeWriter';
                    } else {
                        alert('알 수 없는 이유로 삭제하지 못하였습니다.\n\n이미 삭제된 공지사항일 수도 있습니다..');
                    }
                } else {
                    alert('서버와 통신하지 못하였습니다.\n\n잠시 후 다시 시도해 주세요.');
                }
            }
        };
        xhr.send();
    });
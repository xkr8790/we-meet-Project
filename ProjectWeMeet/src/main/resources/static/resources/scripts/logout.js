document.body.querySelectorAll('[data-action]').forEach(element => {
    element.addEventListener('click', e => {

        const action = element.dataset.action;
        switch (action) {
            case 'logout':
                e.preventDefault();
                //  e.preventDefault()를 통해 submit되는걸 막을수 있다. 즉 로그아웃 기능을 사용할때 버튼 타입으로 하거나 submit때는
                //  e.preventDefault()를 사용하면 된다.
                location.href = '/logout';
                break;
        }
    });
});







document.body.querySelectorAll('[data-action]').forEach(element => {
    element.addEventListener('click', e => {
        const action = element.dataset.action;
        switch (action) {
            case 'logout':
                // window.location.replace('/logout');
                location.href = '/logout';
                break;
        }
    });
});







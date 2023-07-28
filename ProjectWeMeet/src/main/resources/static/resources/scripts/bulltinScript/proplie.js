document.addEventListener('DOMContentLoaded', function () {
    var indexInput = document.querySelector('input[name="index"]');
    var index = indexInput.value;
    var targetElement = document.querySelector('.human');
    loadUserProfileImage(index, targetElement);
});

function loadUserProfileImage(index, targetElement) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', './Participate/profiles?index=' + index, true);
    xhr.responseType = 'json';
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            var data = xhr.response;
            var profileThumbnail = data.profileThumbnail;
            var profileThumbnailMime = data.profileThumbnailMime;

            var img = document.createElement('img');
            img.className = 'human-image';
            img.src = 'data:' + profileThumbnailMime + ';base64,' + profileThumbnail;
            img.alt = 'Profile Image';
            targetElement.appendChild(img);
        } else {
            console.error('Error loading user profile image:', xhr.status, xhr.statusText);
        }
    };
    xhr.onerror = function () {
        console.error('Error loading user profile image:', xhr.status, xhr.statusText);
    };
    xhr.send();
}
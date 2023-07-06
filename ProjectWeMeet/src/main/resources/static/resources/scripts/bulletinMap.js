var mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(bulletinForm['lat'].value,bulletinForm['lng'].value),
        level: 3
    };

var map = new kakao.maps.Map(mapContainer, mapOption);


var markerPosition  = new kakao.maps.LatLng(bulletinForm['lat'].value,bulletinForm['lng'].value);


var marker = new kakao.maps.Marker({
    position: markerPosition
});


marker.setMap(map);


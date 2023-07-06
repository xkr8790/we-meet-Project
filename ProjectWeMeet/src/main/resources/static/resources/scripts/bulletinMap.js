const mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(bulletinForm['lat'].value,bulletinForm['lng'].value),
        level: 3
    };

const map = new kakao.maps.Map(mapContainer, mapOption);


const markerPosition  = new kakao.maps.LatLng(bulletinForm['lat'].value,bulletinForm['lng'].value);


const marker = new kakao.maps.Marker({
    position: markerPosition
});


marker.setMap(map);


<%-- 
    Document   : trends
    Created on : Nov 25, 2016, 5:29:55 PM
    Author     : bwamie
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<ul>
  <c:forEach var="trend" items="${trends}">
       <li><a href="#">${trend.countryName}</a></li>
       </c:forEach>
</ul>--%>
<div>
    <div class="contentmenu">
        <div><h3>Trends</h3></div>
    </div>
    <div id="trendsdiv"></div>
</div>
<div id="map"></div>
<script>

    function initMap() {
        var uluru = {lat: 41.00688, lng: -91.967137};
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 12,
            center: uluru
        });
        var marker = new google.maps.Marker({
            position: map.getCenter(),
            map: map,
            draggable: true,
            title: 'Click to zoom',
            scrollwheel: true
        });

        google.maps.event.addListener(marker, 'dragend', function (event) {
            var latx = this.getPosition().lat();
            var lonx = this.getPosition().lng();
            getTrends(latx, lonx);
            //alert(latx +"========"+lonx);
        });

        var infoWindow = new google.maps.InfoWindow({map: map});

        // Try HTML5 geolocation.
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {

                var pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                };

                infoWindow.setPosition(pos);
                infoWindow.setContent('Location found.');
                //console.log(infoWindow.getOwnPropertyDescriptor(pos));
                map.setCenter(pos);
                getTrends(pos.lat, pos.lng);
            }, function () {
                handleLocationError(true, infoWindow, map.getCenter());
            });
        } else {
            // Browser doesn't support Geolocation
            handleLocationError(false, infoWindow, map.getCenter());
        }
    }

    function handleLocationError(browserHasGeolocation, infoWindow, pos) {
        infoWindow.setPosition(pos);
        infoWindow.setContent(browserHasGeolocation ?
                'Error: The Geolocation service failed.' :
                'Error: Your browser doesn\'t support geolocation.');
    }


</script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAnbipguKqT8-5Gy2kzQU9eGqFtgMycv8M&callback=initMap">
</script>


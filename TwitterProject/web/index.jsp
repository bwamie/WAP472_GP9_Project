<%-- 
    Document   : index
    Created on : Nov 21, 2016, 10:40:31 AM
    Author     : bwamie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CS472 Twitter App</title>
        <script src="static/js/jquery.min.js" type="text/javascript"></script>
        <script src="static/js/mytwitter.js" type="text/javascript"></script>
        <link rel="stylesheet" href="static/css/layout.css" />
    </head>
    <body>

        <header>
            <div id="leftfloat">
                <span class="htittle">CS472</span> TwitterBar
            </div>
            <div id="rightfloat">
                <a href="#" class="loadtweets topmenu"><span>Tweets</span></a>
                <a href="#" class="loadtrends topmenu"><span>Trends</span></a>
            </div>
            <div class="clearright"></div>
        </header>

        <section id="content">
            <section class="banner">
                <img src="static/images/slide.png" />
                <p><span>CS472</span> TwitterBanner</p>
            </section>
            <section class="content" id="maincontent">

            </section>
            <section>
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

                                //infoWindow.setPosition(pos);
                                //infoWindow.setContent('Location found.');
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
            </section>
        </section>

        <footer>
            <p>&copy;Copyright Edwin, Faheem & Emanuel.</p>
        </footer>

    </body>
</html>

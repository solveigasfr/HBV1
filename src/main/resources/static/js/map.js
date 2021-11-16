function initMap() {
// Version 1
// Prepare to see some javascript! I can't find a way to access this from /js/map.js

// Getting the access token from Ingimar's mapbox account.********************
    mapboxgl.accessToken = 'pk.eyJ1IjoiMW5naW1hciIsImEiOiJja3Z6Nzk3eWUwemVxMnZsY2phdHZtdXp2In0.L1V3WRUm5' +
        'vifm5xlES46wQ';
//end ************************************************************************

// Creating the map.**********************************************************
    const map = new mapboxgl.Map({
        container: 'map',
        style: 'mapbox://styles/mapbox/streets-v11', //we can change the style if we want
        center: [-21.876231161865178, 64.12097869457965], //somewhat center of RVK
        zoom: 11
    });
//end ************************************************************************

// set the bounds of the map to only surrounding neighbourhood of RVK.**********
    const bounds = [
        [-22.23, 64.04],
        [-21.52, 64.20]
    ];
    map.setMaxBounds(bounds);
//end ************************************************************************

// Add the control to the map. (search bar and pinpoint marker after search)**
    map.addControl(
        new MapboxGeocoder({
            accessToken: mapboxgl.accessToken,
            //mapboxgl: mapboxgl
            // TODO take out marker after a search is made. This is an ugly fix for that!
            // (the ugly fix is commenting the line above the TODO line)
        })
    );
//end ************************************************************************

// Displaying a marker after a mouseclick on the map.*************************
    var marker = new mapboxgl.Marker()
    map.on('click', (e) => {
        const coordinates = e.lngLat //getting the coordinates of mouse pointer
        marker.remove(); // If there is an old marker then we remove it
        marker = new mapboxgl.Marker({
            draggable: true // why not?
        })
            .setLngLat(coordinates)
            .addTo(map)
    });
//end ************************************************************************

// Adding zoom and rotation controls to the map.******************************
    map.addControl(new mapboxgl.NavigationControl());
//end ************************************************************************
}
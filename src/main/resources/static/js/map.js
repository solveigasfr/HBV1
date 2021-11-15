// Getting the access token from Ingimar's mapbox account.******
mapboxgl.accessToken = 'pk.eyJ1IjoiMW5naW1hciIsImEiOiJja3Z6Nzk3eWUwemVxMnZsY2phdHZtdXp2In0.L1V3WRUm5vifm5xlES46wQ';
//**************************************************************

// Creating the map.********************************************
const map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v11', //we can change the style if we want
    center: [-21.876231161865178, 64.12097869457965], //somewhat center of RVK
    zoom: 11
});
//**************************************************************

// set the bounds of the map to only surrounding neighbourhood of RVK.**********
const bounds = [
    [-22.23, 64.04],
    [-21.52, 64.20]
];
map.setMaxBounds(bounds);
//******************************************************************************

// Add the control to the map. (search bar and pinpoint marker after search)****
map.addControl(
    new MapboxGeocoder({
        accessToken: mapboxgl.accessToken,
        mapboxgl: mapboxgl
    })
);
//*******************************************************************************

// Displaying a marker after a mouseclick on the map.****************************
let marker = new mapboxgl.Marker();
map.on('click', (e) => {
    const coordinates = e.lngLat //getting the coordinates of mouse pointer
    marker.remove(); // If there is an old marker then we remove it
    marker = new mapboxgl.Marker()
        .setLngLat(coordinates)
        .addTo(map)
});
//*******************************************************************************

// Adding zoom and rotation controls to the map.*********************************
map.addControl(new mapboxgl.NavigationControl());
//*******************************************************************************
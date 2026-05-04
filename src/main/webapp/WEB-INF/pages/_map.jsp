<link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
<style>
    #bikeMap { height: 400px; width: 100%; border-radius: var(--radius-lg); z-index: 10; }
    .map-container { margin-bottom: 24px; box-shadow: var(--shadow-sm); border-radius: var(--radius-lg); border: 1px solid var(--clr-border-light); background: #fff; padding: 12px; }
</style>

<div class="map-container">
    <div style="margin-bottom:12px; font-weight:700; display:flex; justify-content:space-between;">
        <span><i data-lucide="map"></i> Live Fleet Tracker (Nepal)</span>
        <span class="badge badge-success" style="font-size:0.7rem;">LIVE</span>
    </div>
    <div id="bikeMap"></div>
</div>

<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        let map = L.map('bikeMap').setView([27.7172, 85.3240], 12); // Centered on Kathmandu
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '© OpenStreetMap'
        }).addTo(map);

        let markers = {};

        function fetchBikeLocations() {
            fetch('${pageContext.request.contextPath}/api/bicycle-locations')
                .then(res => res.json())
                .then(data => {
                    data.forEach(bike => {
                        let lat = bike.latitude || 27.7172;
                        let lng = bike.longitude || 85.3240;
                        let color = '#7a9682'; // muted default
                        if (bike.status === 'AVAILABLE') color = '#3dba6f'; // success
                        else if (bike.status === 'BORROWED') color = '#ffbe3c'; // warning
                        else if (bike.status === 'MAINTENANCE') color = '#c0392b'; // danger
                        
                        let popupContent = `<strong>${bike.name}</strong> (ID: ${bike.id})<br>
                        Type: ${bike.type}<br>
                        Status: ${bike.status}<br>
                        City: ${bike.cityName}<br>
                        <span style="font-size:0.75rem; color:#888;">Updated: ${bike.updatedAt}</span>`;
                        
                        if (markers[bike.id]) {
                            markers[bike.id].setLatLng([lat, lng]);
                            markers[bike.id].setPopupContent(popupContent);
                        } else {
                            let marker = L.circleMarker([lat, lng], {
                                radius: 7, fillColor: color, color: '#fff', weight: 2, opacity: 1, fillOpacity: 0.9
                            }).addTo(map).bindPopup(popupContent);
                            markers[bike.id] = marker;
                        }
                    });
                })
                .catch(err => console.error("Error fetching map locations:", err));
        }

        fetchBikeLocations();
        setInterval(fetchBikeLocations, 5000); // 5 sec interval
    });
</script>

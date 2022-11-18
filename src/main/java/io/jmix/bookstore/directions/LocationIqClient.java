package io.jmix.bookstore.directions;

import com.fasterxml.jackson.databind.JsonNode;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * A custom HTTP API Client to interact with the LocationIQ API Service.
 * It is possible to create a free account for personal / experimental usage.
 * See: <a href="https://locationiq.com/">LocationIQ</a>
 */
@Component
public class LocationIqClient {
    private static final Logger log = LoggerFactory.getLogger(LocationIqClient.class);

    private final LocationIqProperties locationIqProperties;
    private final RestTemplate restTemplate;


    public LocationIqClient(LocationIqProperties locationIqProperties, RestTemplate restTemplate) {
        this.locationIqProperties = locationIqProperties;
        this.restTemplate = restTemplate;
    }

    /**
     * Calculating a route based on two points.
     * See: <a href="https://locationiq.com/docs-html/index.html#directions">LocationIQ - Directions API</a>
     *
     * @param start the starting point of the route
     * @param end the end point of the route
     * @return a LineString representing the route if possible to calculate
     */
    public Optional<LineString> calculateRoute(Point start, Point end) {

        log.info("Calculating route between start: '{}' and end: '{}' via LocationIQ API", start, end);

        try {
            DirectionsApiResponse response = restTemplate.getForObject(
                    directionsApiUrl(start, end),
                    DirectionsApiResponse.class
            );

            return toOptionalLineString(response);
        } catch (Exception e) {
            log.warn("Error loading route information from API", e);
            return Optional.empty();
        }
    }

    private Optional<LineString> toOptionalLineString(DirectionsApiResponse response) throws ParseException {
        if (CollectionUtils.isEmpty(response.routes())) {
            return Optional.empty();
        }

        String json = response.routes.get(0).geometry().toString();
        return Optional.of((LineString) new GeoJsonReader().read(json));
    }

    private URI directionsApiUrl(Point start, Point end) {
        return locationIqBaseUrl()
                .path("/v1/directions/driving/{coordinates}")
                .queryParam("geometries", "geojson")
                .buildAndExpand(coordinatesQueryParameter(start, end))
                .toUri();
    }

    private UriComponentsBuilder locationIqBaseUrl() {
        return UriComponentsBuilder.fromUriString(locationIqProperties.getBaseUrl())
                .queryParam("key", locationIqProperties.getApiKey());
    }

    private String coordinatesQueryParameter(Point start, Point end) {
        return String.join(";", List.of(toPointString(start), toPointString(end)));
    }

    private  String toPointString(Point position) {
        return String.join(",", "" + position.getX(), "" + position.getY());
    }

    /**
     * Record to represent the JSON response of the LocationIQ Directions API with geojson geometry format.
     * Example JSON response (displayed only relevant parts)
     * <pre>
     * {
     *   "code": "Ok",
     *   "routes": [
     *     {
     *       "geometry": {
     *         "coordinates": [
     *           [
     *             -0.161136,
     *             51.523832
     *           ],
     *           [
     *             -0.160984,
     *             51.523516
     *           ],
     *           [
     *             -0.161606,
     *             51.522557
     *           ]
     *         ],
     *         "type": "LineString"
     *       },
     *       "duration": 138.8,
     *       "distance": 844.5
     *     }
     *   ]
     * }
     * </pre>
     */
    record DirectionsApiResponse(List<Route> routes) {
        record Route(JsonNode geometry) { }
    }

}

package io.jmix.bookstore.directions;

import org.locationtech.jts.geom.Point;

import java.util.Optional;

public interface DirectionsProvider {

    String NAME = "bookstore_DirectionsProvider";

    /**
     * Calculating a route based on two points.
     *
     * @param start the starting point of the route
     * @param end the end point of the route
     * @return a CalculatedRoute representing the route if possible to calculate
     */
    Optional<CalculatedRoute> calculateRoute(Point start, Point end);

    /**
     * Calculating a route based on two points against the LocationIQ API.
     * See: <a href="https://locationiq.com/docs-html/index.html#directions">LocationIQ - Directions API</a>
     *
     * @param start the starting point of the route
     * @param end the end point of the route
     * @param accuracy the calculation accuracy which is considered when calculating the route
     * @return a CalculatedRoute representing the route if possible to calculate
     */
    Optional<CalculatedRoute> calculateRoute(Point start, Point end, RouteAccuracy accuracy);

    /**
     * Performs a Forward Geocoding Operation (Address -> Point).
     * @param addressInformation the address information to locate
     * @return the Point of the address, if found
     */
    Optional<Point> forwardGeocoding(AddressInformation addressInformation);
}

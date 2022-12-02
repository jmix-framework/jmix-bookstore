package io.jmix.bookstore.directions;

import org.locationtech.jts.geom.LineString;

import java.util.Optional;

public record CalculatedRoute(LineString lineString, float durationInSeconds, float distanceInMeters) {

    public static Optional<CalculatedRoute> fromValues(LineString lineString, float duration, float distance) {
        return Optional.of(new CalculatedRoute(
                lineString,
                duration,
                distance
        ));
    }

    public RouteDuration duration() {
        return RouteDuration.fromSeconds(durationInSeconds);
    }

    public RouteDistance distance() {
        return RouteDistance.fromMeters(distanceInMeters);
    }
}

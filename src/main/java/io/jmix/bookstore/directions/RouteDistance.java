package io.jmix.bookstore.directions;

public record RouteDistance(long distanceInMeters) {
    public static RouteDistance fromMeters(float distanceInMeters) {
        return new RouteDistance((long) distanceInMeters);
    }

    public String prettyPrint() {
        return "%s km".formatted(distanceInKilometers());
    }

    private long distanceInKilometers() {
        return distanceInMeters / 1000;
    }

    private long distanceInMiles() {
        double conversionFactor = 1.609344;
        return (long) (distanceInKilometers() / conversionFactor);
    }

}

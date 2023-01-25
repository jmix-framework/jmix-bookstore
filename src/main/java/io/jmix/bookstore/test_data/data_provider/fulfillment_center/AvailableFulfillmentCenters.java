package io.jmix.bookstore.test_data.data_provider.fulfillment_center;

import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import io.jmix.bookstore.test_data.data_provider.RandomValues;
import io.jmix.bookstore.test_data.data_provider.region.AvailableRegions;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public record AvailableFulfillmentCenters(List<FulfillmentCenter> fulfillmentCenters) {
    public static Stream<Entry> all() {
        return Arrays.stream(Entry.values());
    }

    public int size() {
        return fulfillmentCenters().size();
    }

    public FulfillmentCenter find(Entry entry) {
        return fulfillmentCenters.stream()
                .filter(fulfillmentCenter -> entry.getName().equals(fulfillmentCenter.getName()))
                .findFirst()
                .orElseThrow();
    }

    public Optional<FulfillmentCenter> findByRegion(Region region) {
        return fulfillmentCenters.stream()
                .filter(fulfillmentCenter -> fulfillmentCenter.getRegion().equals(region))
                .findFirst();
    }

    public FulfillmentCenter random() {
        return RandomValues.randomOfList(fulfillmentCenters);
    }


    public enum Entry {
        DALLAS(
                "Dallas",
                "1301 Chalk Hill Rd",
                "75211",
                "Dallas",
                point(
                        coordinate(-96.902184767956, 32.760904410002176)
                ),
                AvailableRegions.Entry.US_SOUTH
        ),
        MINNEAPOLIS(
                "Minneapolis",
                "2601 4th Ave E",
                "55379",
                "Shakopee",
                point(coordinate(-93.48865500885567, 44.80059945)),
                AvailableRegions.Entry.US_NORTH
        ),
        NEW_YORK(
                "New York",
                "6 W 35th St",
                "10001",
                "New York",
                point(coordinate(-73.9850074, 40.7493827)),
                AvailableRegions.Entry.US_EAST
        ),
        SACRAMENTO(
                "Sacramento",
                "4900 W Elkhorn Blvd",
                "95835",
                "Sacramento",
                point(coordinate(-121.57283568244628, 38.67925645151088)),
                AvailableRegions.Entry.US_WEST
        );

        private final String name;
        private final String street;
        private final String postalCode;
        private final String city;
        private final Point point;
        private final AvailableRegions.Entry region;

        Entry(String name, String street, String postalCode, String city, Point point, AvailableRegions.Entry region) {
            this.name = name;
            this.street = street;
            this.postalCode = postalCode;
            this.city = city;
            this.point = point;
            this.region = region;
        }

        public String getName() {
            return name;
        }

        public Point getPoint() {
            return point;
        }

        public AvailableRegions.Entry getRegion() {
            return region;
        }

        public String getStreet() {
            return street;
        }

        public String getPostCode() {
            return postalCode;
        }

        public String getCity() {
            return city;
        }
    }

    private static Coordinate coordinate(double x, double y) {
        return new Coordinate(x, y);
    }

    private static Point point(Coordinate coordinate) {
        return new GeometryFactory().createPoint(
                coordinate
        );
    }
}

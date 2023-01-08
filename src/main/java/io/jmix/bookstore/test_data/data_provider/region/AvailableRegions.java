package io.jmix.bookstore.test_data.data_provider.region;

import io.jmix.bookstore.employee.Region;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public record AvailableRegions(List<Region> regions) {
    public static Stream<Entry> all() {
        return Arrays.stream(Entry.values());
    }

    public int size() {
        return regions().size();
    }

    public Region find(Entry entry) {
        return regions.stream()
                .filter(region -> entry.getName().equals(region.getName()))
                .findFirst()
                .orElseThrow();
    }


    public enum Entry {
        US_SOUTH("US-South"),
        US_WEST("US-West"),
        US_EAST("US-East"),
        US_NORTH("US-North");

        private final String name;

        Entry(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}

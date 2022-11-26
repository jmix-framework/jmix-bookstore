package io.jmix.bookstore.employee.test_support;

import io.jmix.bookstore.employee.Region;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.locationtech.jts.geom.Polygon;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TerritoryData {
    String name;
    Region region;
    Polygon geographicalArea;
}

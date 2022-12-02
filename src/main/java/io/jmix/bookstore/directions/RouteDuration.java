package io.jmix.bookstore.directions;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public record RouteDuration(Duration duration) {
    public static RouteDuration fromSeconds(float durationInSeconds) {
        return new RouteDuration(Duration.ofSeconds((int) durationInSeconds));
    }

    public String prettyPrint() {
        return prettyPrintedDuration();
    }

    private String prettyPrintedDuration() {
        return durationParts().stream()
                .filter(DurationParts::isPresent)
                .map(DurationParts::prettyPrint)
                .collect(Collectors.joining(" "));
    }

    private List<DurationParts> durationParts() {

        return List.of(
                new DurationParts((int) duration.toDaysPart(), "d"),
                new DurationParts(duration.toHoursPart(), "h"),
                new DurationParts(duration.toMinutesPart(), "m")
        );
    }

    private record DurationParts(int amount, String suffix) {
        public boolean isPresent() {
            return amount > 0;
        }
        public String prettyPrint() {
            return String.format("%s%s", amount, suffix);
        }
    }
}

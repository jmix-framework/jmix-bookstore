package io.jmix.bookstore.directions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RouteDurationTest {

    public static Stream<Arguments> routeDurationPermutations() {
        return Stream.of(
                Arguments.of(duration(1, 5, 10), "1d 5h 10m"),
                Arguments.of(duration(2, 1, 5), "2d 1h 5m"),
                Arguments.of(duration(3, 1, 5), "3d 1h 5m"),
                Arguments.of(duration(0, 1, 15), "1h 15m"),
                Arguments.of(duration(0, 1, 0), "1h")
        );
    }

    private static Duration duration(int days, int hours, int minutes) {
        return Duration.ofDays(days).plusHours(hours).plusMinutes(minutes);
    }

    @ParameterizedTest
    @MethodSource("routeDurationPermutations")
    void routeDuration_isCorrectlyRendered(
            Duration duration,
            String expectedPrettyPrint) {

        RouteDuration routeDuration = new RouteDuration(duration);

        assertThat(routeDuration.prettyPrint())
                .isEqualTo(expectedPrettyPrint);
    }
}

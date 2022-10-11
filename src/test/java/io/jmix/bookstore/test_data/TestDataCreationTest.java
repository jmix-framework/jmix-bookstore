package io.jmix.bookstore.test_data;

import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.product.test_support.ProductCategories;
import io.jmix.bookstore.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.core.TimeSource;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.*;
import java.util.List;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
class TestDataCreationTest {

    /**
     * NOW is freezed to some wednesday in order to not accidentally hit the weekend limitation
     * of the visit test data generation
     */
    private static final LocalDate TODAY = LocalDate.now().with(DayOfWeek.WEDNESDAY);
    private static final LocalDate TOMORROW = TODAY.plusDays(1);
    private static final LocalDateTime TOMORROW_MORNING = TOMORROW.atStartOfDay();
    private static final ZonedDateTime NOW = TODAY.atStartOfDay(ZoneId.of("Europe/Berlin")).plusHours(8);

    @Autowired
    TestDataCreation testDataCreation;

    @Autowired
    TimeSource timeSource;
    @Autowired
    DataManager dataManager;
    @Autowired
    DatabaseCleanup databaseCleanup;
    @Autowired
    ProductCategories productCategories;


    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities();
    }

    @Nested
    class CreateProducts {

        @Test
        void when_generateProducts_thenProductsAreStored() {

            // given:
            assertThat(dbProducts())
                    .isEmpty();

            // and:
            ProductCategory productCategory1 = productCategories.saveDefault();
            ProductCategory productCategory2 = productCategories.saveDefault();


            // when:
            List<Product> products = testDataCreation.generateProducts(1_000, List.of(
                    productCategory1,
                    productCategory2
            ));

            // then:
            assertThat(products.size())
                    .isLessThanOrEqualTo(1_000);
            // and:
            assertThat(dbProducts().size())
                    .isLessThanOrEqualTo(1_000);
        }


        @Test
        void when_generateProductCategories_thenProductCategoriesAreStored() {

            // given:
            assertThat(dbProductCategories())
                    .isEmpty();


            // when:
            List<ProductCategory> categories = testDataCreation.generateProductCategories(100);

            // then:
            assertThat(categories.size())
                    .isLessThanOrEqualTo(100);

            // and:
            assertThat(dbProductCategories().size())
                    .isLessThanOrEqualTo(100);
        }
    }

    @NotNull
    private List<Product> dbProducts() {
        return allEntitiesOf(Product.class);
    }
    @NotNull
    private <T> List<T> allEntitiesOf(Class<T> entityClass) {
        return dataManager.load(entityClass).all().list();
    }

    private List<ProductCategory> dbProductCategories() {
        return allEntitiesOf(ProductCategory.class);
    }

//
//    @Test
//    void given_daysInFutureShouldBeGenerated_when_generateVisits_then_amountOfVisitsIsDecreasingIntoTheFuture() {
//
//        // given:
//        daysInPastToGenerateFor(1);
//        daysInFutureToGenerateFor(30);
//        visitAmountPerDay(10);
//
//        possibleDescriptions(of("Fever", "Disease"));
//
//        possiblePets(
//                data.pet("Pikachu"),
//                data.pet("Garchomp")
//        );
//
//        possibleNurses(of(data.nurse("Joy")));
//
//        // when:
//        List<Visit> visits = testDataCreation.createVisits();
//
//        // then: the more far into the future, the less amount of visits are generated
//        assertThat(amountOfVisitsForDate(visits, TOMORROW))
//                .isLessThan(10);
//        assertThat(amountOfVisitsForDate(visits, TOMORROW.plusDays(7)))
//                .isLessThan(8);
//        assertThat(amountOfVisitsForDate(visits, TOMORROW.plusDays(14)))
//                .isLessThan(6);
//        assertThat(amountOfVisitsForDate(visits, TOMORROW.plusDays(21)))
//                .isLessThan(4);
//    }
//
//
//    @Test
//    void given_10VisitsPerDays_when_generateVisits_then_sizeIs10() {
//
//        // given:
//        daysInPastToGenerateFor(1);
//        daysInFutureToGenerateFor(0);
//        visitAmountPerDay(10);
//
//        possibleDescriptions(of("Fever", "Disease"));
//
//        possiblePets(
//                data.pet("Pikachu"),
//                data.pet("Garchomp")
//        );
//
//        possibleNurses(of(data.nurse("Joy")));
//
//        // when:
//        List<Visit> visits = testDataCreation.createVisits();
//
//        // then:
//        assertThat(visits.size())
//                .isEqualTo(10);
//    }
//
//    private void possiblePets(Pet... pets) {
//        FluentLoader<Pet> petLoaderMock = mock(FluentLoader.class);
//        FluentLoader.ByCondition<Pet> allPets = mock(FluentLoader.ByCondition.class);
//
//        when(dataManager.load(Pet.class))
//                .thenReturn(petLoaderMock);
//
//        when(petLoaderMock.all())
//                .thenReturn(allPets);
//
//        when(allPets.list())
//                .thenReturn(asList(pets));
//    }
//
//    private int amountOfVisitsForDate(List<Visit> visits, LocalDate date) {
//        return futureVisits(visits)
//                .stream()
//                .collect(Collectors.groupingBy(visit -> visit.getVisitStart().toLocalDate()))
//                .get(date)
//                .size();
//    }
//
//    private List<Visit> futureVisits(List<Visit> visits) {
//        return visits.stream()
//                .filter(visit -> visit.getVisitStart().isAfter(TOMORROW_MORNING))
//                .collect(Collectors.toList());
//    }
//
//    private void possibleNurses(List<User> nurses) {
//        when(employeeRepository.findAllNurses())
//                .thenReturn(nurses);
//    }
//
//    private void daysInPastToGenerateFor(int days) {
//        when(testdataProperties.getVisitStartAmountPastDays())
//                .thenReturn(days);
//    }
//
//    private void daysInFutureToGenerateFor(int days) {
//        when(testdataProperties.getVisitStartAmountFutureDays())
//                .thenReturn(days);
//    }
//
//    private void visitAmountPerDay(int amount) {
//        when(testdataProperties.getAmountPerDay())
//                .thenReturn(amount);
//    }
//
//    private void possibleDescriptions(List<String> descriptions) {
//        lenient().when(testdataProperties.getDescriptionOptions())
//                .thenReturn(descriptions);
//    }

}

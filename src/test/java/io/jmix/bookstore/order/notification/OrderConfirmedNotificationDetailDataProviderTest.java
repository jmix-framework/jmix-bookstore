package io.jmix.bookstore.order.notification;

import io.jmix.bookstore.customer.test_support.Customers;
import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.employee.test_support.Employees;
import io.jmix.bookstore.employee.test_support.Positions;
import io.jmix.bookstore.employee.test_support.Regions;
import io.jmix.bookstore.employee.test_support.Territories;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.entity.test_support.Users;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.order.event.OrderConfirmedEvent;
import io.jmix.bookstore.order.test_support.Orders;
import io.jmix.bookstore.test_data.DatabaseCleanup;
import io.jmix.bookstore.test_data.data_provider.employee.EmployeePositions;
import io.jmix.bookstore.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@ActiveProfiles("integration-test")
@ExtendWith(AuthenticatedAsAdmin.class)
class OrderConfirmedNotificationDetailDataProviderTest {

    @Autowired
    DataManager dataManager;
    @Autowired
    DatabaseCleanup databaseCleanup;
    @Autowired
    Customers customers;
    @Autowired
    Employees employees;
    @Autowired
    Territories territories;
    @Autowired
    Regions regions;
    @Autowired
    Orders orders;
    @Autowired
    Positions positions;
    @Autowired
    Users users;
    @Autowired
    OrderConfirmedNotificationDetailDataProvider dataProvider;

    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities();
    }

    @Nested
    class Recipients {
        private Position salesRepresentativePosition;
        private Region region;

        @BeforeEach
        void setUp() {

            salesRepresentativePosition = positions.save(
                    positions.defaultData()
                            .name(EmployeePositions.AvailablePosition.SALES_REPRESENTATIVE.getCode())
                            .code(EmployeePositions.AvailablePosition.SALES_REPRESENTATIVE.getCode())
                            .build()
            );

            region = regions.saveDefault();
        }

        @Nested
        class SalesRepresentativeFiltering {

            private Position orderFulfillmentSpecialistPosition;
            @BeforeEach
            void setUp() {

                orderFulfillmentSpecialistPosition = positions.save(
                        positions.defaultData()
                                .name(EmployeePositions.AvailablePosition.ORDER_FULFILLMENT_SPECIALIST.getCode())
                                .code(EmployeePositions.AvailablePosition.ORDER_FULFILLMENT_SPECIALIST.getCode())
                                .build()
                );

            }

            @Test
            void given_salesRepresentativeEmployee_expect_salesRepresentativeInRecipientList() {

                // given:
                Territory territory = territory();

                // and:
                var salesRepresentative = territoryEmployee(territory, salesRepresentativePosition);

                // and:
                Order order = orderInTerritory(territory);

                // when:
                var notificationDetailData = dataProvider.provideData(new OrderConfirmedEvent(order));

                // then:
                assertThat(notificationDetailData.recipients())
                        .containsExactly(usernameOf(salesRepresentative));
            }

            @Test
            void given_orderFulfillmentSpecialist_expect_orderFulfillmentSpecialistInRecipientList() {

                // given:
                Territory territory = territory();

                // and:
                var orderFulfillmentSpecialist = territoryEmployee(territory, orderFulfillmentSpecialistPosition);

                // and:
                Order order = orderInTerritory(territory);

                // when:
                var notificationDetailData = dataProvider.provideData(new OrderConfirmedEvent(order));

                // then:
                assertThat(notificationDetailData.recipients())
                        .doesNotContain(usernameOf(orderFulfillmentSpecialist));
            }
        }

        @Nested
        class ShippingAddressTerritoryLocation {

            @Test
            void given_orderAddressInsideTerritory_expect_salesRepresentativeOfTerritoryInRecipientList() {

                // given:
                Territory territory = territoryWithGeographicalArea(geographicalArea(5, 5));

                // and:
                var salesRepresentative = createSalesEmployeeAndUser(territory);

                // and:
                Order order = orderWithShippingAddressLocation(location(1, 1));

                // when:
                var notificationDetailData = dataProvider.provideData(new OrderConfirmedEvent(order));

                // then:
                assertThat(notificationDetailData.recipients())
                        .containsExactly(usernameOf(salesRepresentative));

            }

            @Test
            void given_orderAddressOutsideTerritory_expect_salesRepresentativeOfTerritoryNotInRecipientList() {

                // given:
                Territory territory = territoryWithGeographicalArea(geographicalArea(5, 5));

                // and:
                var salesRepresentative = createSalesEmployeeAndUser(territory);

                // and:
                Order order = orderWithShippingAddressLocation(location(20, 10));

                // when:
                var notificationDetailData = dataProvider.provideData(new OrderConfirmedEvent(order));

                // then:
                assertThat(notificationDetailData.recipients())
                        .doesNotContain(usernameOf(salesRepresentative));
            }

        }

        private Order orderWithShippingAddressLocation(Point position) {
            return orders.create(
                    orders.defaultData()
                            .shippingAddress(shippingAddressWithPosition(position))
                            .build()
            );
        }

        private Order orderInTerritory(Territory territory) {
            return orders.create(
                    orders.defaultData()
                            .shippingAddress(shippingAddressWithPosition(territory.getGeographicalArea().getCentroid()))
                            .build()
            );
        }

        private Employee createSalesEmployeeAndUser(Territory territory) {
            User salesUser = users.saveDefault();

            return employees.save(
                    employees.defaultData()
                            .position(salesRepresentativePosition)
                            .territories(List.of(territory))
                            .user(salesUser)
                            .build()
            );
        }
        private Employee territoryEmployee(Territory territory, Position position) {
            User user = users.saveDefault();

            return employees.save(
                    employees.defaultData()
                            .position(position)
                            .territories(List.of(territory))
                            .user(user)
                            .build()
            );
        }

        private Territory territoryWithGeographicalArea(Polygon geographicalArea) {
            return territories.save(
                    territories.defaultData()
                            .geographicalArea(geographicalArea)
                            .region(region)
                            .build()

            );
        }

        private Territory territory() {
            return territories.save(
                    territories.defaultData()
                            .geographicalArea(geographicalArea(5, 5))
                            .region(region)
                            .build()

            );
        }

        @NotNull
        private Address shippingAddressWithPosition(Point position) {
            Address shippingAddress = dataManager.create(Address.class);
            shippingAddress.setPosition(position);
            return shippingAddress;
        }

        private Point location(int x, int y) {
            return new GeometryFactory().createPoint(new Coordinate(x, y));
        }

        private Polygon geographicalArea(int length, int height) {
            return new GeometryFactory().createPolygon(
                    Stream.of(
                            new Coordinate(0, 0),
                            new Coordinate(length, 0),
                            new Coordinate(length, height),
                            new Coordinate(0, height),
                            new Coordinate(0, 0)

                    ).toArray(Coordinate[]::new)
            );
        }
    }

    private static String usernameOf(Employee employee) {
        return employee.getUser().getUsername();
    }

}

package io.jmix.bookstore.product.supplier.notification;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.supplier.SupplierOrderRequest;
import io.jmix.bookstore.product.supplier.event.SupplierOrderRequestCreatedEvent;
import io.jmix.bookstore.security.ProcurementSpecialistRole;
import io.jmix.bookstore.security.SalesRepresentativeRole;
import io.jmix.core.Messages;
import io.jmix.security.role.assignment.RoleAssignment;
import io.jmix.security.role.assignment.RoleAssignmentRepository;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


/**
 * This test checks SupplierOrderCreatedNotificationDetailDataProvider implementation as a unit test.
 * As no @SpringBootTest annotation is present, no Spring context is started for this test case.
 * Following things are not present:
 * - dependency injection (@Autowired) in production code
 * - dependency injection (@Autowired) in test code
 * - database
 *
 * Instead, Mockito is used to mock interactions with dependencies.
 */
class SupplierOrderRequestCreatedNotificationDetailDataProviderTest {

    /**
     * @Autowired is missing as the class is manually instantiated
     */
    SupplierOrderRequestCreatedNotificationDetailDataProvider dataProvider;

    /**
     * Messages API from Jmix is mocked
     */
    private final Messages messages = mock(Messages.class);

    /**
     * RoleAssignmentRepository from Jmix is mocked to return specified values per test case
     */
    private final RoleAssignmentRepository roleAssignmentRepository = mock(RoleAssignmentRepository.class);

    @BeforeEach
    void setUp() {


        // class under test is manually created and mocks pased in via the constructor
        dataProvider = new SupplierOrderRequestCreatedNotificationDetailDataProvider(
                roleAssignmentRepository,
                messages
        );
    }

    @Nested
    class Recipients {

        @Test
        void given_userHasProcurementSpecialistRoleAssignment_expect_isPartOfRecipients() {

            // given:
            roleAssignmentExists("procurementSpecialistUser", ProcurementSpecialistRole.CODE);

            // when:
            var notificationDetailData = dataProvider.provideData(
                    supplierOrderRequestCreatedEvent()
            );

            // then:
            assertThat(notificationDetailData.recipients())
                    .containsExactly("procurementSpecialistUser");
        }

        private void roleAssignmentExists(String procurementSpecialistUser, String code) {
            when(roleAssignmentRepository.getAllAssignments())
                    .thenReturn(List.of(new RoleAssignment(procurementSpecialistUser, code, RoleAssignmentRoleType.RESOURCE)));
        }

        @Test
        void given_userHasOtherRoleAssignment_expect_isPartOfRecipients() {

            // given:
            roleAssignmentExists("salesRepresentativeUser", SalesRepresentativeRole.CODE);

            // when:
            var notificationDetailData = dataProvider.provideData(
                    supplierOrderRequestCreatedEvent()
            );

            // then:
            assertThat(notificationDetailData.recipients())
                    .doesNotContain("salesRepresentativeUser");
        }

        @NotNull
        private SupplierOrderRequestCreatedEvent supplierOrderRequestCreatedEvent() {
            SupplierOrderRequest request = new SupplierOrderRequest();
            Product product = new Product();
            request.setProduct(product);
            User requestedBy = new User();
            request.setRequestedBy(requestedBy);

            return new SupplierOrderRequestCreatedEvent(request);
        }
    }

}

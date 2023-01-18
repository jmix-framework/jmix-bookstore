package io.jmix.bookstore.product.supplier.creation;

import io.jmix.bookstore.multitenancy.test_environment.TenantCreation;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.product.supplier.*;
import io.jmix.bookstore.product.supplier.test_support.SupplierOrders;
import io.jmix.bookstore.product.test_support.*;
import io.jmix.bookstore.security.DatabaseUserRepository;
import io.jmix.bookstore.test_data.DatabaseCleanup;
import io.jmix.bookstore.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static io.jmix.bookstore.entity.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("integration-test")
@ExtendWith(AuthenticatedAsAdmin.class)
class SupplierOrderDraftCreationTest {

    @Autowired
    SupplierOrderDraftCreation supplierOrderDraftCreation;

    @Autowired
    DataManager dataManager;

    @Autowired
    SupplierOrders supplierOrders;

    @Autowired
    SupplierOrderRequests supplierOrderRequests;
    @Autowired
    Suppliers suppliers;
    @Autowired
    Products products;
    @Autowired
    ProductCategories productCategories;

    @Autowired
    TenantCreation tenantCreation;

    @Autowired
    SystemAuthenticator systemAuthenticator;
    @Autowired
    DatabaseCleanup databaseCleanup;
    @Autowired
    DatabaseUserRepository databaseUserRepository;
    private Product harryPotterPart4;
    private Product harryPotterPart7;


    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities();

        ProductCategory fantasy = productCategories.saveDefault();

        Supplier supplier1 = suppliers.saveDefault();

        harryPotterPart7 = products.save(
                products.defaultData()
                        .name("Harry Potter and the Deathly Hallows")
                        .category(fantasy)
                        .supplier(supplier1)
                        .build()
        );
        harryPotterPart4 = products.save(
                products.defaultData()
                        .name("Harry Potter and the Goblet of Fire")
                        .category(fantasy)
                        .supplier(supplier1)
                        .build()

        );
    }

    private void assertOrderLineHasQuantity(SupplierOrder createdSupplierOrder, Product product, int expectedQuantity) {
        assertThat(createdSupplierOrder.getOrderLines())
                .filteredOn(supplierOrderLine -> supplierOrderLine.getProduct().equals(product))
                .extracting(SupplierOrderLine::getQuantity)
                .containsExactly(expectedQuantity);
    }

    @NotNull
    private Optional<SupplierOrder> supplierOrderFromDb() {
        return dataManager.load(SupplierOrder.class)
                .all()
                .optional();
    }

    @Test
    void given_oneNewSupplierOrderRequest_expect_oneNewSupplierOrderWithStatusDraft() {

        // given:
        supplierOrderRequests.save(
                supplierOrderRequest(harryPotterPart4, 200)
        );

        // when:
        supplierOrderDraftCreation.createDraftSupplierOrders();

        // then:
        Optional<SupplierOrder> potentialSupplierOrder = supplierOrderFromDb();

        assertThat(potentialSupplierOrder)
                .isPresent();

        // and:
        SupplierOrder createdSupplierOrder = potentialSupplierOrder.get();

        assertThat(createdSupplierOrder)
                .hasStatus(SupplierOrderStatus.DRAFT);
    }

    private SupplierOrderRequestData supplierOrderRequest(Product product, int requestedAmount) {
        return supplierOrderRequests.defaultData()
                .requestedBy(databaseUserRepository.loadUserByUsername("admin"))
                .product(product)
                .requestedAmount(requestedAmount)
                .comment("Please order new books, they still sell like crazy...")
                .status(SupplierOrderRequestStatus.NEW)
                .build();
    }

    @Test
    void given_twoNewSupplierOrderRequests_expect_oneNewSupplierOrderWithOneSupplierOrderLineForEachRequest() {

        // given:
        supplierOrderRequests.save(
                supplierOrderRequest(harryPotterPart4, 200)
        );

        supplierOrderRequests.save(
                supplierOrderRequest(harryPotterPart7, 50)
        );

        // when:
        supplierOrderDraftCreation.createDraftSupplierOrders();

        // then:
        Optional<SupplierOrder> potentialSupplierOrder = supplierOrderFromDb();

        assertThat(potentialSupplierOrder)
                .isPresent();

        // and:
        SupplierOrder createdSupplierOrder = potentialSupplierOrder.get();

        assertThat(createdSupplierOrder.getOrderLines())
                .hasSize(2);

        assertOrderLineHasQuantity(createdSupplierOrder, harryPotterPart4, 200);
        assertOrderLineHasQuantity(createdSupplierOrder, harryPotterPart7, 50);
    }

}

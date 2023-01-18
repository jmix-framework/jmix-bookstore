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
class SupplierOrderDraftCreationBpmTest {

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
    @Autowired
    RuntimeService runtimeService;
    private Product harryPotterPart4;

    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities();

        ProductCategory productCategory = productCategories.saveDefault();

        Supplier supplier = suppliers.saveDefault();

        harryPotterPart4 = products.save(
                products.defaultData()
                        .name("Harry Potter and the Goblet of Fire")
                        .category(productCategory)
                        .supplier(supplier)
                        .build()

        );
    }

    @NotNull
    private Optional<SupplierOrder> supplierOrderFromDb() {
        return dataManager.load(SupplierOrder.class)
                .all()
                .optional();
    }

    @Test
    void given_draftSupplierOrder_expect_performSupplierOrderProcessStarted() {

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
        List<ProcessInstance> performSupplierOrderProcessInstances = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey("perform-supplier-order")
                .list();

        assertThat(performSupplierOrderProcessInstances)
                .hasSize(1);

        // and:
        String processInstanceId = performSupplierOrderProcessInstances.get(0).getProcessInstanceId();

        assertThat(runtimeService.getVariable(processInstanceId, "supplierOrder"))
                .isEqualTo(potentialSupplierOrder.get());
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
}

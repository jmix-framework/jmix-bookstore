package io.jmix.bookstore.test_data;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.order.Order;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.bookstore.test_data.data_provider.*;
import io.jmix.bookstore.test_data.data_provider.bpm.BpmUserGroupDataProvider;
import io.jmix.bookstore.test_data.data_provider.employee.OrderFulfillmentEmployeeDataProvider;
import io.jmix.bookstore.test_data.data_provider.employee.OrderFulfillmentSupervisorDataProvider;
import io.jmix.bookstore.test_data.data_provider.employee.ProcurementEmployeeDataProvider;
import io.jmix.bookstore.test_data.data_provider.employee.ProcurementSupervisorDataProvider;
import io.jmix.bpm.entity.UserGroup;
import io.jmix.core.DataManager;
import io.jmix.core.TimeSource;
import net.datafaker.Faker;
import net.datafaker.Number;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component("bookstore_TestDataCreation")
public class TestDataCreation {
    private static final Logger log = LoggerFactory.getLogger(TestDataCreation.class);

    protected final TimeSource timeSource;
    protected final DataManager dataManager;
    protected final ProductDataProvider productDataProvider;
    protected final ProductCategoryDataProvider productCategoryDataProvider;
    protected final SupplierDataProvider supplierDataProvider;
    protected final RegionDataProvider regionDataProvider;
    protected final TerritoryDataProvider territoryDataProvider;
    protected final CustomerDataProvider customerDataProvider;
    protected final OrderDataProvider orderDataProvider;
    protected final ProcurementSupervisorDataProvider procurementSupervisorDataProvider;

    protected final DatabaseCleanup databaseCleanup;
    private final ProcurementEmployeeDataProvider procurementEmployeeDataProvider;
    private final OrderFulfillmentEmployeeDataProvider orderFulfillmentEmployeeDataProvider;
    private final OrderFulfillmentSupervisorDataProvider orderFulfillmentSupervisorDataProvider;
    private final BpmUserGroupDataProvider bpmUserGroupDataProvider;

    public TestDataCreation(
            TimeSource timeSource,
            DataManager dataManager,
            ProductDataProvider productDataProvider,
            ProductCategoryDataProvider productCategoryDataProvider,
            SupplierDataProvider supplierDataProvider,
            RegionDataProvider regionDataProvider,
            TerritoryDataProvider territoryDataProvider,
            CustomerDataProvider customerDataProvider,
            OrderDataProvider orderDataProvider,
            ProcurementSupervisorDataProvider procurementSupervisorDataProvider,
            DatabaseCleanup databaseCleanup,
            ProcurementEmployeeDataProvider procurementEmployeeDataProvider,
            OrderFulfillmentEmployeeDataProvider orderFulfillmentEmployeeDataProvider,
            OrderFulfillmentSupervisorDataProvider orderFulfillmentSupervisorDataProvider,
            BpmUserGroupDataProvider bpmUserGroupDataProvider) {
        this.timeSource = timeSource;
        this.dataManager = dataManager;
        this.productDataProvider = productDataProvider;
        this.productCategoryDataProvider = productCategoryDataProvider;
        this.supplierDataProvider = supplierDataProvider;
        this.regionDataProvider = regionDataProvider;
        this.territoryDataProvider = territoryDataProvider;
        this.customerDataProvider = customerDataProvider;
        this.orderDataProvider = orderDataProvider;
        this.procurementSupervisorDataProvider = procurementSupervisorDataProvider;
        this.databaseCleanup = databaseCleanup;
        this.procurementEmployeeDataProvider = procurementEmployeeDataProvider;
        this.orderFulfillmentEmployeeDataProvider = orderFulfillmentEmployeeDataProvider;
        this.orderFulfillmentSupervisorDataProvider = orderFulfillmentSupervisorDataProvider;
        this.bpmUserGroupDataProvider = bpmUserGroupDataProvider;
    }

    public void createData() {

        databaseCleanup.removeAllEntities();
        databaseCleanup.removeNonAdminUsers();

        log.info("No Data found in the DB. Test data will be created...");

        Number number = new Faker().number();

        List<UserGroup> bpmUserGroups = generateBpmUserGroups();
        log.info("{} BPM User Groups created", bpmUserGroups.size());

        List<Employee> employees = generateEmployees();
        log.info("{} Employees created", employees.size());


        List<ProductCategory> productCategories = generateProductCategories(number.numberBetween(50, 200));
        log.info("{} Product Categories created", productCategories.size());

        List<Supplier> suppliers = generateSuppliers(number.numberBetween(50, 200));
        log.info("{} Suppliers created", suppliers.size());

        List<Product> products = generateProducts(number.numberBetween(500, 1000), productCategories, suppliers);
        log.info("{} Products created", products.size());

        List<Region> regions = generateRegions(number.numberBetween(50, 200));
        log.info("{} Regions created", regions.size());


        List<Territory> territories = generateTerritories(number.numberBetween(50, 200), regions);
        log.info("{} Territories created", territories.size());


        List<Customer> customers = generateCustomers(number.numberBetween(1_000, 2_000));
        log.info("{} Customers created", customers.size());


        List<Order> orders = generateOrders(number.numberBetween(2_000, 5_000), customers, products);
        log.info("{} Orders created", orders.size());


        log.info("Data created");

    }

    private List<Order> generateOrders(int amount, List<Customer> customers, List<Product> products) {
        log.info("Trying to create a random amount of {} Orders", amount);
        return orderDataProvider.create(new OrderDataProvider.DataContext(amount, customers, products));
    }

    private List<Customer> generateCustomers(int amount) {
        log.info("Trying to create a random amount of {} Customers", amount);
        return customerDataProvider.create(new CustomerDataProvider.DataContext(amount));
    }

    private List<Territory> generateTerritories(int amount, List<Region> regions) {
        log.info("Trying to create a random amount of {} Territories", amount);
        return territoryDataProvider.create(new TerritoryDataProvider.DataContext(amount, regions));
    }

    private List<Region> generateRegions(int amount) {
        log.info("Trying to create a random amount of {} Regions", amount);
        return regionDataProvider.create(new RegionDataProvider.DataContext(amount));
    }

    private List<Supplier> generateSuppliers(int amount) {
        log.info("Trying to create a random amount of {} Suppliers", amount);
        return supplierDataProvider.create(new SupplierDataProvider.DataContext(amount));
    }

    public List<Product> generateProducts(int amount, List<ProductCategory> productCategories, List<Supplier> suppliers) {
        log.info("Trying to create a random amount of {} Products", amount);
        return productDataProvider.create(new ProductDataProvider.DataContext(amount, productCategories, suppliers));
    }

    public List<ProductCategory> generateProductCategories(int amount) {
        log.info("Trying to create a random amount of {} Product Categories", amount);
        return productCategoryDataProvider.create(new ProductCategoryDataProvider.DataContext(amount));
    }

    public List<UserGroup> generateBpmUserGroups() {
        log.info("Trying to create pre-defined BPM User Groups");
        return bpmUserGroupDataProvider.create(new BpmUserGroupDataProvider.DataContext());
    }

    public List<Employee> generateEmployees() {
        log.info("Trying to create pre-defined Employees");
        List<Employee> procurementSupervisors = procurementSupervisorDataProvider.create(new ProcurementSupervisorDataProvider.DataContext());
        List<Employee> procurementEmployees = procurementEmployeeDataProvider.create(new ProcurementEmployeeDataProvider.DataContext(procurementSupervisors));


        List<Employee> orderFulfillmentSupervisors = orderFulfillmentSupervisorDataProvider.create(new OrderFulfillmentSupervisorDataProvider.DataContext());
        List<Employee> orderFulfillmentEmployees = orderFulfillmentEmployeeDataProvider.create(new OrderFulfillmentEmployeeDataProvider.DataContext(orderFulfillmentSupervisors));

        return joinLists(
                procurementSupervisors,
                procurementEmployees,
                orderFulfillmentSupervisors,
                orderFulfillmentEmployees
        );
    }

    public <T> List<T> joinLists(List<T>... lists) {
        return Arrays.stream(lists).flatMap(Collection::stream).collect(Collectors.toList());
    }
}

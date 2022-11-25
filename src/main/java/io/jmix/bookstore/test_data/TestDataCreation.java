package io.jmix.bookstore.test_data;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import io.jmix.bookstore.order.Order;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.bookstore.test_data.data_provider.*;
import io.jmix.bookstore.test_data.data_provider.bpm.BpmUserGroupDataProvider;
import io.jmix.bookstore.test_data.data_provider.employee.*;
import io.jmix.bpm.entity.UserGroup;
import io.jmix.core.DataManager;
import io.jmix.core.TimeSource;
import io.jmix.reports.entity.Report;
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
    protected final CustomerDataProvider customerDataProvider;
    protected final OrderDataProvider orderDataProvider;
    protected final ProcurementManagerDataProvider procurementManagerDataProvider;

    protected final DatabaseCleanup databaseCleanup;
    private final ItAdministratorEmployeeDataProvider itAdministratorEmployeeDataProvider;
    private final ProcurementSpecialistDataProvider procurementSpecialistDataProvider;
    private final OrderFulfillmentSpecialistDataProvider orderFulfillmentSpecialistDataProvider;
    private final OrderFulfillmentManagerDataProvider orderFulfillmentManagerDataProvider;
    private final EmployeePositionDataProvider employeePositionDataProvider;
    private final BpmUserGroupDataProvider bpmUserGroupDataProvider;
    private final ReportDataProvider reportDataProvider;

    public TestDataCreation(
            TimeSource timeSource,
            DataManager dataManager,
            ProductDataProvider productDataProvider,
            ProductCategoryDataProvider productCategoryDataProvider,
            SupplierDataProvider supplierDataProvider,
            CustomerDataProvider customerDataProvider,
            OrderDataProvider orderDataProvider,
            ProcurementManagerDataProvider procurementManagerDataProvider,
            DatabaseCleanup databaseCleanup,
            ItAdministratorEmployeeDataProvider itAdministratorEmployeeDataProvider, ProcurementSpecialistDataProvider procurementSpecialistDataProvider,
            OrderFulfillmentSpecialistDataProvider orderFulfillmentSpecialistDataProvider,
            OrderFulfillmentManagerDataProvider orderFulfillmentManagerDataProvider,
            EmployeePositionDataProvider employeePositionDataProvider, BpmUserGroupDataProvider bpmUserGroupDataProvider, ReportDataProvider reportDataProvider) {
        this.timeSource = timeSource;
        this.dataManager = dataManager;
        this.productDataProvider = productDataProvider;
        this.productCategoryDataProvider = productCategoryDataProvider;
        this.supplierDataProvider = supplierDataProvider;
        this.customerDataProvider = customerDataProvider;
        this.orderDataProvider = orderDataProvider;
        this.procurementManagerDataProvider = procurementManagerDataProvider;
        this.databaseCleanup = databaseCleanup;
        this.itAdministratorEmployeeDataProvider = itAdministratorEmployeeDataProvider;
        this.procurementSpecialistDataProvider = procurementSpecialistDataProvider;
        this.orderFulfillmentSpecialistDataProvider = orderFulfillmentSpecialistDataProvider;
        this.orderFulfillmentManagerDataProvider = orderFulfillmentManagerDataProvider;
        this.employeePositionDataProvider = employeePositionDataProvider;
        this.bpmUserGroupDataProvider = bpmUserGroupDataProvider;
        this.reportDataProvider = reportDataProvider;
    }

    public void createData() {

        databaseCleanup.removeAllEntities();
        databaseCleanup.removeNonAdminUsers();

        log.info("No Data found in the DB. Test data will be created...");

        Number number = new Faker().number();

        List<UserGroup> bpmUserGroups = generateBpmUserGroups();
        log.info("{} BPM User Groups created", bpmUserGroups.size());

        List<Report> reports = importReports();
        log.info("{} Reports imported", reports.size());

        EmployeePositions employeePositions = generateEmployeePositions();
        log.info("{} Employee Positions created", employeePositions.size());

        List<Employee> employees = generateEmployees(employeePositions);
        log.info("{} Employees created", employees.size());


        List<ProductCategory> productCategories = generateProductCategories(number.numberBetween(50, 200));
        log.info("{} Product Categories created", productCategories.size());

        List<Supplier> suppliers = generateSuppliers(number.numberBetween(50, 200));
        log.info("{} Suppliers created", suppliers.size());

        List<Product> products = generateProducts(number.numberBetween(500, 1000), productCategories, suppliers);
        log.info("{} Products created", products.size());

        List<Customer> customers = generateCustomers(number.numberBetween(1_000, 2_000));
        log.info("{} Customers created", customers.size());

        List<FulfillmentCenter> fulfillmentCenters = dataManager.load(FulfillmentCenter.class).all().list();
        List<Territory> territories = dataManager.load(Territory.class).all().list();
        List<Region> regions = dataManager.load(Region.class).all().list();
        List<Order> orders = generateOrders(number.numberBetween(2_000, 5_000), customers, products, fulfillmentCenters, territories, regions);
        log.info("{} Orders created", orders.size());

        log.info("Data created");

    }

    private List<Report> importReports() {
        log.info("Trying to import Report: 'supplier-order-form.zip'");
        return reportDataProvider.create(new ReportDataProvider.DataContext("supplier-order-form.zip"));
    }

    private List<Order> generateOrders(int amount, List<Customer> customers, List<Product> products, List<FulfillmentCenter> fulfillmentCenters, List<Territory> territories, List<Region> regions) {
        log.info("Trying to create a random amount of {} Orders", amount);
        return orderDataProvider.create(new OrderDataProvider.DataContext(amount, customers, products, fulfillmentCenters, territories, regions));
    }

    private List<Customer> generateCustomers(int amount) {
        log.info("Trying to create a random amount of {} Customers", amount);
        return customerDataProvider.create(new CustomerDataProvider.DataContext(amount, "classpath:test-data/addresses-us-all.json"));
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

    public EmployeePositions generateEmployeePositions() {
        log.info("Trying to create pre-defined Employee Positions");
        return new EmployeePositions(employeePositionDataProvider.create(new EmployeePositionDataProvider.DataContext()));
    }

    public List<Employee> generateEmployees(EmployeePositions employeePositions) {
        log.info("Trying to create pre-defined Employees");
        List<Employee> itEmployees = itAdministratorEmployeeDataProvider.create(new ItAdministratorEmployeeDataProvider.DataContext(employeePositions));
        List<Employee> procurementManagers = procurementManagerDataProvider.create(new ProcurementManagerDataProvider.DataContext(employeePositions));
        List<Employee> procurementSpecialists = procurementSpecialistDataProvider.create(new ProcurementSpecialistDataProvider.DataContext(procurementManagers, employeePositions));


        List<Employee> orderFulfillmentManagers = orderFulfillmentManagerDataProvider.create(new OrderFulfillmentManagerDataProvider.DataContext(employeePositions));
        List<Employee> orderFulfillmentEmployees = orderFulfillmentSpecialistDataProvider.create(new OrderFulfillmentSpecialistDataProvider.DataContext(orderFulfillmentManagers, employeePositions));

        return joinLists(
                itEmployees,
                procurementManagers,
                procurementSpecialists,
                orderFulfillmentManagers,
                orderFulfillmentEmployees
        );
    }

    public <T> List<T> joinLists(List<T>... lists) {
        return Arrays.stream(lists).flatMap(Collection::stream).collect(Collectors.toList());
    }
}

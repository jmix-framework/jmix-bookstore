package io.jmix.bookstore.test_data;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.bookstore.test_data.data_provider.*;
import io.jmix.bookstore.test_data.data_provider.bpm.BpmProcessDefinitionDataProvider;
import io.jmix.bookstore.test_data.data_provider.bpm.DmnTableDataProvider;
import io.jmix.bookstore.test_data.data_provider.employee.*;
import io.jmix.bookstore.test_data.data_provider.region.AvailableRegions;
import io.jmix.bookstore.test_data.data_provider.region.RegionDataProvider;
import io.jmix.bookstore.test_data.data_provider.territory.AvailableTerritories;
import io.jmix.bookstore.test_data.data_provider.bpm.BpmUserGroupDataProvider;
import io.jmix.bookstore.test_data.data_provider.fulfillment_center.AvailableFulfillmentCenters;
import io.jmix.bookstore.test_data.data_provider.fulfillment_center.FulfillmentCenterDataProvider;
import io.jmix.bookstore.test_data.data_provider.territory.TerritoryDataProvider;
import io.jmix.bpm.entity.UserGroup;
import io.jmix.core.DataManager;
import io.jmix.core.TimeSource;
import io.jmix.multitenancy.core.TenantProvider;
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
    private final SalesRepresentativeDataProvider salesRepresentativeDataProvider;
    private final OrderFulfillmentManagerDataProvider orderFulfillmentManagerDataProvider;
    private final RegionDataProvider regionDataProvider;
    private final TerritoryDataProvider territoryDataProvider;
    private final FulfillmentCenterDataProvider fulfillmentCenterDataProvider;
    private final EmployeePositionDataProvider employeePositionDataProvider;
    private final BpmUserGroupDataProvider bpmUserGroupDataProvider;
    private final BpmProcessDefinitionDataProvider bpmProcessDefinitionDataProvider;
    private final DmnTableDataProvider dmnTableDataProvider;
    private final TenantReportDataProvider tenantReportDataProvider;
    private final ReportDataProvider reportDataProvider;
    private final TenantProvider tenantProvider;

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
            ItAdministratorEmployeeDataProvider itAdministratorEmployeeDataProvider,
            ProcurementSpecialistDataProvider procurementSpecialistDataProvider,
            OrderFulfillmentSpecialistDataProvider orderFulfillmentSpecialistDataProvider,
            SalesRepresentativeDataProvider salesRepresentativeDataProvider,
            OrderFulfillmentManagerDataProvider orderFulfillmentManagerDataProvider,
            RegionDataProvider regionDataProvider,
            TerritoryDataProvider territoryDataProvider,
            FulfillmentCenterDataProvider fulfillmentCenterDataProvider,
            EmployeePositionDataProvider employeePositionDataProvider,
            BpmUserGroupDataProvider bpmUserGroupDataProvider,
            BpmProcessDefinitionDataProvider bpmProcessDefinitionDataProvider, DmnTableDataProvider dmnTableDataProvider, TenantReportDataProvider tenantReportDataProvider, ReportDataProvider reportDataProvider,
            TenantProvider tenantProvider) {
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
        this.salesRepresentativeDataProvider = salesRepresentativeDataProvider;
        this.orderFulfillmentManagerDataProvider = orderFulfillmentManagerDataProvider;
        this.regionDataProvider = regionDataProvider;
        this.territoryDataProvider = territoryDataProvider;
        this.fulfillmentCenterDataProvider = fulfillmentCenterDataProvider;
        this.employeePositionDataProvider = employeePositionDataProvider;
        this.bpmUserGroupDataProvider = bpmUserGroupDataProvider;
        this.bpmProcessDefinitionDataProvider = bpmProcessDefinitionDataProvider;
        this.dmnTableDataProvider = dmnTableDataProvider;
        this.tenantReportDataProvider = tenantReportDataProvider;
        this.reportDataProvider = reportDataProvider;
        this.tenantProvider = tenantProvider;
    }

    public enum TestdataAmount {
        SMALL(1),
        MEDIUM(5),
        LARGE(10);

        private final int factor;

        TestdataAmount(int factor) {
            this.factor = factor;
        }

        public int getFactor() {
            return factor;
        }
    }

    /**
     * Generates full set of test data for the current tenant.
     * A random number of customers, orders and products and suppliers are generated
     */
    public void generateRandomTestdataForTenant(TestdataAmount amount) {

        String tenantId = tenantProvider.getCurrentUserTenantId();
        log.info("Random test data will be generated for Tenant '{}'...", tenantId);

        Number number = new Faker().number();

        AvailableRegions regions = findAvailableRegions();

        AvailableTerritories territories = findAvailableTerritories();

        AvailableFulfillmentCenters fulfillmentCenters = generateFulfillmentCenters(regions);

        createBpmUserGroups();
        importBpmProcesses(tenantId);
        importDmnTables(tenantId);
        importTenantReport(tenantId);


        List<ProductCategory> productCategories = generateProductCategories(randomAmount(amount.getFactor(), number, 5, 20));

        List<Supplier> suppliers = generateSuppliers(randomAmount(amount.getFactor(), number, 10 * amount.getFactor(), 70));

        List<Product> products = generateProducts(randomAmount(amount.getFactor(), number, 100 * amount.getFactor(), 200 * amount.getFactor()), productCategories, suppliers);

        List<Customer> customers = generateCustomers(randomAmount(amount.getFactor(), number, 100, 200), territories);

        generateOrders(randomAmount(amount.getFactor(), number, 500, 800), customers, products, fulfillmentCenters, territories);

        log.info("Random test data generation finished");
    }

    private static int randomAmount(int factor, Number number, int min, int max) {
        return number.numberBetween(min * factor, max * factor);
    }

    /**
     * Tenant gets initialised with the bare minimum of data, that is required to finish the login process.
     * Mainly employees entries are required as well as associated reference data (territories, regions).
     *
     * @param tenantId the tenant id to initialise
     */
    public void initialiseTenantWithData(String tenantId) {

        AvailableRegions regions = generateRegions();

        AvailableTerritories territories = generateTerritories(regions);

        EmployeePositions employeePositions = generateEmployeePositions();

        generateEmployees(employeePositions, territories, tenantId);

    }


    private AvailableRegions findAvailableRegions() {
        return new AvailableRegions(dataManager.load(Region.class).all().list());
    }
    private AvailableTerritories findAvailableTerritories() {
        return new AvailableTerritories(dataManager.load(Territory.class).all().list());
    }


    private void importBpmProcesses(String tenantId) {
        log.info("Trying to import BPM Process Definition: 'supplier-order-form.zip'");
        bpmProcessDefinitionDataProvider.create(new BpmProcessDefinitionDataProvider.DataContext("perform-supplier-order.bpmn20.xml", tenantId));
        log.info("{} BPM Process Definitions imported", 1);

    }
    private void importDmnTables(String tenantId) {
        log.info("Trying to import DMN Table: 'supplier-order-approval-required.dmn.xml'");
        dmnTableDataProvider.create(new DmnTableDataProvider.DataContext("supplier-order-approval-required.dmn.xml", tenantId));
        log.info("{} DMN Tables imported", 1);

    }
    private void importTenantReport(String tenantId) {
        log.info("Trying to create Tenant Report: 'supplier-order-approval-required.dmn.xml'");
        tenantReportDataProvider.create(new TenantReportDataProvider.DataContext("supplier-order-form", tenantId));
        log.info("{} DMN Tables imported", 1);

    }

    private List<Order> generateOrders(int amount, List<Customer> customers, List<Product> products, AvailableFulfillmentCenters fulfillmentCenters, AvailableTerritories territories) {
        log.info("Trying to create a random amount of {} Orders", amount);
        List<Order> orders = orderDataProvider.create(new OrderDataProvider.DataContext(amount, customers, products, fulfillmentCenters, territories));
        log.info("{} Orders created", orders.size());
        return orders;
    }

    private List<Customer> generateCustomers(int amount, AvailableTerritories territories) {
        log.info("Trying to create a random amount of {} Customers", amount);
        List<Customer> customers = customerDataProvider.create(new CustomerDataProvider.DataContext(amount, "classpath:test-data/addresses-us-all.json", territories));
        log.info("{} Customers created", customers.size());
        return customers;
    }

    private List<Supplier> generateSuppliers(int amount) {
        log.info("Trying to create a random amount of {} Suppliers", amount);
        List<Supplier> suppliers = supplierDataProvider.create(new SupplierDataProvider.DataContext(amount));
        log.info("{} Suppliers created", suppliers.size());
        return suppliers;
    }

    public List<Product> generateProducts(int amount, List<ProductCategory> productCategories, List<Supplier> suppliers) {
        log.info("Trying to create a random amount of {} Products", amount);
        List<Product> products = productDataProvider.create(new ProductDataProvider.DataContext(amount, productCategories, suppliers));
        log.info("{} Products created", products.size());
        return products;
    }

    public List<ProductCategory> generateProductCategories(int amount) {
        log.info("Trying to create a random amount of {} Product Categories", amount);
        List<ProductCategory> productCategories = productCategoryDataProvider.create(new ProductCategoryDataProvider.DataContext(amount));
        log.info("{} Product Categories created", productCategories.size());
        return productCategories;
    }

    private List<UserGroup> createBpmUserGroups() {
        log.info("Trying to create pre-defined BPM User Groups");
        List<UserGroup> userGroups = bpmUserGroupDataProvider.create(new BpmUserGroupDataProvider.DataContext());
        log.info("{} BPM User Groups created", userGroups.size());
        return userGroups;
    }

    private EmployeePositions generateEmployeePositions() {
        log.info("Trying to create pre-defined Employee Positions");
        EmployeePositions employeePositions = new EmployeePositions(employeePositionDataProvider.create(new EmployeePositionDataProvider.DataContext()));
        log.info("{} Employee Positions created", employeePositions.size());
        return employeePositions;
    }
    private AvailableRegions generateRegions() {
        log.info("Trying to create pre-defined Regions");
        AvailableRegions regions = new AvailableRegions(regionDataProvider.create(new RegionDataProvider.DataContext()));
        log.info("{} Regions created", regions.size());
        return regions;
    }

    private AvailableFulfillmentCenters generateFulfillmentCenters(AvailableRegions regions) {
        log.info("Trying to create pre-defined Fulfillment Centers");
        AvailableFulfillmentCenters fulfillmentCenters = new AvailableFulfillmentCenters(fulfillmentCenterDataProvider.create(new FulfillmentCenterDataProvider.DataContext(regions)));
        log.info("{} Fulfillment Centers created", fulfillmentCenters.size());
        return fulfillmentCenters;
    }
    private AvailableTerritories generateTerritories(AvailableRegions regions) {
        log.info("Trying to create pre-defined Territories");
        AvailableTerritories territories = new AvailableTerritories(territoryDataProvider.create(new TerritoryDataProvider.DataContext(regions)));
        log.info("{} Territories created", territories.size());
        return territories;
    }
    private List<Employee> generateEmployees(EmployeePositions employeePositions, AvailableTerritories territories, String tenantId) {
        log.info("Trying to create pre-defined Employees");
        List<Employee> itEmployees = itAdministratorEmployeeDataProvider.create(new ItAdministratorEmployeeDataProvider.DataContext(employeePositions, tenantId));
        List<Employee> procurementManagers = procurementManagerDataProvider.create(new ProcurementManagerDataProvider.DataContext(employeePositions, tenantId));
        List<Employee> procurementSpecialists = procurementSpecialistDataProvider.create(new ProcurementSpecialistDataProvider.DataContext(procurementManagers, employeePositions, tenantId));


        List<Employee> orderFulfillmentManagers = orderFulfillmentManagerDataProvider.create(new OrderFulfillmentManagerDataProvider.DataContext(employeePositions, territories, tenantId));
        List<Employee> orderFulfillmentEmployees = orderFulfillmentSpecialistDataProvider.create(new OrderFulfillmentSpecialistDataProvider.DataContext(orderFulfillmentManagers, employeePositions, territories, tenantId));
        List<Employee> salesRepresentatives = salesRepresentativeDataProvider.create(new SalesRepresentativeDataProvider.DataContext(employeePositions, territories, tenantId));

        List<Employee> employees = joinLists(
                itEmployees,
                procurementManagers,
                procurementSpecialists,
                orderFulfillmentManagers,
                orderFulfillmentEmployees,
                salesRepresentatives
        );
        log.info("{} Employees created", employees.size());
        return employees;
    }

    private  <T> List<T> joinLists(List<T>... lists) {
        return Arrays.stream(lists).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public void importInitialReport() {
        log.info("Trying to import Report: 'supplier-order-form.zip'");
        List<Report> reports = reportDataProvider.create(new ReportDataProvider.DataContext("supplier-order-form.zip"));
        log.info("{} Reports imported", reports.size());
    }
}

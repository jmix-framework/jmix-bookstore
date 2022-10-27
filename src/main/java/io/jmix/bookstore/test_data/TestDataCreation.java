package io.jmix.bookstore.test_data;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.order.Order;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.bookstore.test_data.data_provider.*;
import io.jmix.core.DataManager;
import io.jmix.core.TimeSource;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import net.datafaker.Faker;
import net.datafaker.Number;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    protected final UserDataProvider userDataProvider;
    protected final DatabaseCleanup databaseCleanup;

    public TestDataCreation(
            TimeSource timeSource,
            DataManager dataManager, ProductDataProvider productDataProvider, ProductCategoryDataProvider productCategoryDataProvider, SupplierDataProvider supplierDataProvider, RegionDataProvider regionDataProvider, TerritoryDataProvider territoryDataProvider, CustomerDataProvider customerDataProvider, OrderDataProvider orderDataProvider, UserDataProvider userDataProvider, DatabaseCleanup databaseCleanup) {
        this.timeSource = timeSource;
        this.dataManager = dataManager;
        this.productDataProvider = productDataProvider;
        this.productCategoryDataProvider = productCategoryDataProvider;
        this.supplierDataProvider = supplierDataProvider;
        this.regionDataProvider = regionDataProvider;
        this.territoryDataProvider = territoryDataProvider;
        this.customerDataProvider = customerDataProvider;
        this.orderDataProvider = orderDataProvider;
        this.userDataProvider = userDataProvider;
        this.databaseCleanup = databaseCleanup;
    }

    public void createData() {

        databaseCleanup.removeAllEntities();
        databaseCleanup.removeNonAdminUsers();

        log.info("No Data found in the DB. Test data will be created...");


        Number number = new Faker().number();


        List<User> users = generateUsers();
        log.info("{} Users created", users.size());


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
        return orderDataProvider.create(amount, new OrderDataProvider.Dependencies(customers, products));
    }

    private List<Customer> generateCustomers(int amount) {
        log.info("Trying to create a random amount of {} Customers", amount);
        return customerDataProvider.create(amount, new CustomerDataProvider.Dependencies());
    }

    private List<Territory> generateTerritories(int amount, List<Region> regions) {
        log.info("Trying to create a random amount of {} Territories", amount);
        return territoryDataProvider.create(amount, new TerritoryDataProvider.Dependencies(regions));
    }

    private List<Region> generateRegions(int amount) {
        log.info("Trying to create a random amount of {} Regions", amount);
        return regionDataProvider.create(amount, new RegionDataProvider.Dependencies());
    }

    private List<Supplier> generateSuppliers(int amount) {
        log.info("Trying to create a random amount of {} Suppliers", amount);
        return supplierDataProvider.create(amount, new SupplierDataProvider.Dependencies());
    }

    public List<Product> generateProducts(int amount, List<ProductCategory> productCategories, List<Supplier> suppliers) {
        log.info("Trying to create a random amount of {} Products", amount);
        return productDataProvider.create(amount, new ProductDataProvider.Dependencies(productCategories, suppliers));
    }

    public List<ProductCategory> generateProductCategories(int amount) {
        log.info("Trying to create a random amount of {} Product Categories", amount);
        return productCategoryDataProvider.create(amount, new ProductCategoryDataProvider.Dependencies());
    }
    public List<User> generateUsers() {
        log.info("Trying to create pre-defined Users");
        return userDataProvider.create(0, new UserDataProvider.Dependencies());
    }

}

package io.jmix.bookstore.test_data;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.order.Order;
import io.jmix.bookstore.order.OrderLine;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.bookstore.test_data.data_provider.*;
import io.jmix.core.DataManager;
import io.jmix.core.TimeSource;
import net.datafaker.Faker;
import net.datafaker.Number;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

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
    protected final DatabaseCleanup databaseCleanup;

    public TestDataCreation(
            TimeSource timeSource,
            DataManager dataManager, ProductDataProvider productDataProvider, ProductCategoryDataProvider productCategoryDataProvider, SupplierDataProvider supplierDataProvider, RegionDataProvider regionDataProvider, TerritoryDataProvider territoryDataProvider, CustomerDataProvider customerDataProvider, OrderDataProvider orderDataProvider, DatabaseCleanup databaseCleanup) {
        this.timeSource = timeSource;
        this.dataManager = dataManager;
        this.productDataProvider = productDataProvider;
        this.productCategoryDataProvider = productCategoryDataProvider;
        this.supplierDataProvider = supplierDataProvider;
        this.regionDataProvider = regionDataProvider;
        this.territoryDataProvider = territoryDataProvider;
        this.customerDataProvider = customerDataProvider;
        this.orderDataProvider = orderDataProvider;
        this.databaseCleanup = databaseCleanup;
    }

    public void createData() {

        databaseCleanup.removeAllEntities(Territory.class);
        databaseCleanup.removeAllEntities(Region.class);

        databaseCleanup.removeAllEntities(OrderLine.class);
        databaseCleanup.removeAllEntities(Order.class);

        databaseCleanup.removeAllEntities(Product.class);
        databaseCleanup.removeAllEntities(Customer.class);


        databaseCleanup.removeAllEntities(ProductCategory.class);

        log.info("No Data found in the DB. Test data will be created...");


        Number number = new Faker().number();


        List<ProductCategory> productCategories = generateProductCategories(number.numberBetween(50, 200));
        log.info("{} Product Categories created", productCategories.size());


        List<Product> products = generateProducts(number.numberBetween(500, 1000), productCategories);
        log.info("{} Products created", products.size());

        List<Supplier> suppliers = generateSuppliers(number.numberBetween(50, 200));
        log.info("{} Suppliers created", suppliers.size());


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

    public List<Product> generateProducts(int amount, List<ProductCategory> productCategories) {
        log.info("Trying to create a random amount of {} Products", amount);
        return productDataProvider.create(amount, new ProductDataProvider.Dependencies(productCategories));
    }

    public List<ProductCategory> generateProductCategories(int amount) {
        log.info("Trying to create a random amount of {} Product Categories", amount);
        return productCategoryDataProvider.create(amount, new ProductCategoryDataProvider.Dependencies());
    }

}

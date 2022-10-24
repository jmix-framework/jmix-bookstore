package io.jmix.bookstore.test_data;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.order.Order;
import io.jmix.bookstore.order.OrderLine;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.bookstore.product.supplier.SupplierOrderLine;
import io.jmix.bookstore.product.supplier.SupplierOrderRequest;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.core.MetadataTools;
import io.jmix.core.SaveContext;
import io.jmix.core.security.SystemAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;


@Component
public class DatabaseCleanup {

    @Autowired
    DataManager dataManager;

    @Autowired
    Metadata metadata;
    @Autowired
    MetadataTools metadataTools;
    @Autowired
    DataSource dataSource;
    @Autowired
    SystemAuthenticator systemAuthenticator;

    public <T> void removeAllEntities(Class<T> entityClass) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        performDeletion(entityClass, jdbcTemplate);
    }

    private <T> void performDeletion(Class<T> entityClass, JdbcTemplate jdbcTemplate) {
        String tableName = metadataTools.getDatabaseTable(metadata.getClass(entityClass));

        jdbcTemplate.update("DELETE FROM " + tableName);
    }

    public void removeAllEntities() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);


        performDeletion(Employee.class, jdbcTemplate);
        performDeletion(OrderLine.class, jdbcTemplate);
        performDeletion(Order.class, jdbcTemplate);
        performDeletion(Customer.class, jdbcTemplate);
        performDeletion(SupplierOrderLine.class, jdbcTemplate);
        performDeletion(SupplierOrder.class, jdbcTemplate);
        performDeletion(SupplierOrderRequest.class, jdbcTemplate);
        performDeletion(Product.class, jdbcTemplate);
        performDeletion(ProductCategory.class, jdbcTemplate);
        performDeletion(Supplier.class, jdbcTemplate);
        performDeletion(Territory.class, jdbcTemplate);
        performDeletion(Region.class, jdbcTemplate);
    }
}

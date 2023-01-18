package io.jmix.bookstore.test_data;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.order.entity.OrderLine;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.bookstore.product.supplier.SupplierOrderLine;
import io.jmix.bookstore.product.supplier.SupplierOrderRequest;
import io.jmix.bpm.entity.UserGroup;
import io.jmix.bpm.entity.UserGroupRole;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.core.MetadataTools;
import io.jmix.core.SaveContext;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.multitenancy.entity.Tenant;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class DatabaseCleanup {

    @Autowired
    DataManager dataManager;
    @Autowired
    RuntimeService runtimeService;

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
        performDeletion(tableName(entityClass), jdbcTemplate);
    }
    private <T> void performDeletion(String tableName, JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("DELETE FROM " + tableName);
    }

    private <T> void performDeletionWhere(Class<T> entityClass, String whereCondition, JdbcTemplate jdbcTemplate) {
        performDeletionWhere(tableName(entityClass), whereCondition, jdbcTemplate);
    }

    private void performDeletionWhere(String tableName, String whereCondition, JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("DELETE FROM %s WHERE %s".formatted(tableName, whereCondition));
    }

    private <T> String tableName(Class<T> entityClass) {
        return metadataTools.getDatabaseTable(metadata.getClass(entityClass));
    }
    public void removeAllEntities() {

        deleteBpmProcessInstancesFor("perform-supplier-order");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        performDeletion("BOOKSTORE_EMPLOYEE_TERRITORIES", jdbcTemplate);
        performDeletion(Employee.class, jdbcTemplate);

        performDeletion(UserGroupRole.class, jdbcTemplate);
        performDeletion(UserGroup.class, jdbcTemplate);
        performDeletion(Position.class, jdbcTemplate);
        performDeletion(OrderLine.class, jdbcTemplate);
        performDeletion(Order.class, jdbcTemplate);
        performDeletion(Customer.class, jdbcTemplate);
        performDeletion(SupplierOrderLine.class, jdbcTemplate);
        performDeletion(SupplierOrder.class, jdbcTemplate);
        performDeletion(SupplierOrderRequest.class, jdbcTemplate);
        performDeletion(Product.class, jdbcTemplate);
        performDeletion(ProductCategory.class, jdbcTemplate);
        performDeletion(Supplier.class, jdbcTemplate);
    }

    private void deleteBpmProcessInstancesFor(String processDefinitionKey) {
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .list();
        processInstances.forEach(processInstance ->
            runtimeService.deleteProcessInstance(processInstance.getProcessInstanceId(), "test data cleanup")
        );
    }

    public void removeAllEntities(List<?> entities) {
        SaveContext entitiesToRemove = new SaveContext();
        entities.forEach(entitiesToRemove::removing);
        dataManager.save(entitiesToRemove);
    }


    public <T> void removeAllEntitiesWithoutSoftDeletion(Class<T> entityClass, Set<UUID> entityUuids) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        entityUuids.forEach(entityUuid ->
                performDeletionWhere(tableName(entityClass), "ID='%s'".formatted(entityUuid), jdbcTemplate)
        );
    }

    public void removeNonAdminUsers() {
        PropertyCondition notAdmin = PropertyCondition.notEqual("username", "admin");
        List<RoleAssignmentEntity> allRoleAssignmentExceptAdmin = dataManager.load(RoleAssignmentEntity.class).condition(notAdmin).list();
        List<User> allUsersExceptAdmin = dataManager.load(User.class).condition(notAdmin).list();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        allUsersExceptAdmin.forEach(user -> performDeletionWhere("BOOKSTORE_USER_REGION_LINK", "USER_ID='%s'".formatted(user.getId()), jdbcTemplate));

        removeAllEntities(Stream.concat(allRoleAssignmentExceptAdmin.stream(), allUsersExceptAdmin.stream()).collect(Collectors.toList()));
    }

    public void removeAllEntitiesForTenant(Tenant tenant) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // TODO: fix removing entries from mapping table for tenant
        performDeletionWhere("BOOKSTORE_EMPLOYEE_TERRITORIES", "1=1", jdbcTemplate);
        performDeletionWhere(Employee.class,  tenantEquals("TENANT", tenant), jdbcTemplate);

        performDeletionWhere(
                "BOOKSTORE_USER_REGION_LINK",
                "USER_ID IN (SELECT USER_ID FROM BOOKSTORE_USER WHERE TENANT='%s')".formatted(tenant.getTenantId()),
                jdbcTemplate
        );

        performDeletionWhere(User.class,  tenantEquals("TENANT", tenant), jdbcTemplate);
        performDeletionWhere(RoleAssignmentEntity.class,  usernameStartsWith(tenant), jdbcTemplate);
        performDeletionWhere(UserGroupRole.class,  tenantEquals("SYS_TENANT_ID", tenant), jdbcTemplate);
        performDeletionWhere(UserGroup.class, tenantEquals("SYS_TENANT_ID", tenant), jdbcTemplate);
        performDeletionWhere(Position.class, tenantEquals("TENANT", tenant), jdbcTemplate);
        performDeletionWhere(OrderLine.class, tenantEquals("TENANT", tenant), jdbcTemplate);
        performDeletionWhere(Order.class, tenantEquals("TENANT", tenant), jdbcTemplate);
        performDeletionWhere(Customer.class, tenantEquals("TENANT", tenant), jdbcTemplate);
        performDeletionWhere(SupplierOrderLine.class, tenantEquals("TENANT", tenant), jdbcTemplate);
        performDeletionWhere(SupplierOrder.class, tenantEquals("TENANT", tenant), jdbcTemplate);
        performDeletionWhere(SupplierOrderRequest.class, tenantEquals("TENANT", tenant), jdbcTemplate);
        performDeletionWhere(Product.class, tenantEquals("TENANT", tenant), jdbcTemplate);
        performDeletionWhere(ProductCategory.class, tenantEquals("TENANT", tenant), jdbcTemplate);
        performDeletionWhere(Supplier.class, tenantEquals("TENANT", tenant), jdbcTemplate);
    }

    private static String tenantEquals(String columnName, Tenant tenant) {
        return "%s='%s'".formatted(columnName, tenant.getTenantId());
    }
    private static String usernameStartsWith(Tenant tenant) {
        return "username like '%s|'".formatted(tenant.getTenantId());
    }

}

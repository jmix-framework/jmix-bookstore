package io.jmix.bookstore.multitenancy.test_environment;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.multitenancy.TestEnvironmentTenant;
import io.jmix.bookstore.security.FullAccessRole;
import io.jmix.bookstore.test_data.TestDataCreation;
import io.jmix.bookstore.test_data.data_provider.RandomValues;
import io.jmix.core.DataManager;
import io.jmix.core.TimeSource;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import net.datafaker.Faker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("bookstore_TenantCreation")
public class TenantCreation {

    protected final PasswordEncoder passwordEncoder;
    private final DataManager dataManager;
    private final SystemAuthenticator systemAuthenticator;
    private final TestDataCreation testDataCreation;
    private final TimeSource timeSource;

    public TenantCreation(DataManager dataManager, PasswordEncoder passwordEncoder, SystemAuthenticator systemAuthenticator, TestDataCreation testDataCreation, TimeSource timeSource) {
        this.dataManager = dataManager;
        this.passwordEncoder = passwordEncoder;
        this.systemAuthenticator = systemAuthenticator;
        this.testDataCreation = testDataCreation;
        this.timeSource = timeSource;
    }

    public void createTenant(String tenantId, String adminUsername) {

        TestEnvironmentTenant tenant = dataManager.create(TestEnvironmentTenant.class);
        tenant.setTenantId(tenantId);
        tenant.setName(tenantId);

        tenant.setLastLogin(timeSource.now().toOffsetDateTime());
        tenant.setTestdataInitialised(false);

        User tenantAdmin = dataManager.create(User.class);

        tenantAdmin.setUsername(adminUsername);
        tenantAdmin.setPassword(passwordEncoder.encode("admin"));
        tenantAdmin.setFirstName("Mike");
        tenantAdmin.setLastName("Holloway");
        tenantAdmin.setTenant(tenantId);

        RoleAssignmentEntity tenantAdminRole = toRoleAssignment(tenantAdmin, FullAccessRole.CODE, RoleAssignmentRoleType.RESOURCE);

        dataManager.save(tenant, tenantAdmin, tenantAdminRole);

        systemAuthenticator.runWithUser(tenantAdmin.getUsername(), () -> testDataCreation.initialiseTenantWithData(tenantId));
    }


    private RoleAssignmentEntity toRoleAssignment(User user, String roleCode, String roleType) {
        RoleAssignmentEntity userRole = dataManager.create(RoleAssignmentEntity.class);
        userRole.setUsername(user.getUsername());
        userRole.setRoleType(roleType);
        userRole.setRoleCode(roleCode);
        return userRole;
    }

    public String generateRandomTenantId() {
        return "test-environment-%s".formatted(
                new Faker().hashing().md5().substring(0,6).toLowerCase()
        );
    }

}

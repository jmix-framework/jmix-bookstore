package io.jmix.bookstore.multitenancy;

import io.jmix.core.security.Authenticated;

import java.time.OffsetDateTime;

public interface TestEnvironmentTenants {

    /**
     * tracks a new user login for the tenant
     * @param tenantId the tenant ID to track
     * @param lastLogin the point in time, when the login happened
     */
    void trackUserLoginForTenant(String tenantId, OffsetDateTime lastLogin);

    /**
     * checks if a given tenant is present in the database
     * @param tenantId the tenant ID of the tenant to check
     * @return true, if present. Otherwise false
     */
    @Authenticated
    boolean isPresent(String tenantId);


    /**
     * checks if a given tenant has already generated testdata
     * @param tenantId the tenant ID of the tenant to check
     * @return true, if present. Otherwise false
     */
    boolean hasGeneratedTestdata(String tenantId);

    /**
     * creates a tenant with the required base data (users, employees, etc.)
     * @param tenantId the tenant ID of the new tenant
     */
    @Authenticated
    void createTenant(String tenantId);

    /**
     * generates full set of random test data for a given tenant (customers, orders, products, etc.)
     * @param tenantId the tenant ID of the tenant to generate testdata for
     */
    @Authenticated
    void generateRandomTestdata(String tenantId);

    /**
     * generates a random tenant ID that can be used for a new user using the system
     * @return the random tenant ID in the form: 'test-b39b7f' where the last part is random 6 character string
     */
    String generateRandomTenantId();

}

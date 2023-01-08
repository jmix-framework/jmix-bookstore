package io.jmix.bookstore.screen.multitenancy;

import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import io.jmix.multitenancyui.screen.tenant.TenantBrowse;

@UiController("mten_Tenant.browse")
@UiDescriptor("test-environment-tenant-browse.xml")
public class TestEnvironmentTenantBrowse extends TenantBrowse {
}

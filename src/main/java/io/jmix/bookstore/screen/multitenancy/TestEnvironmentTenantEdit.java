package io.jmix.bookstore.screen.multitenancy;

import io.jmix.multitenancyui.screen.tenant.TenantEdit;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import io.jmix.ui.screen.StandardEditor;

@UiController("mten_Tenant.edit")
@UiDescriptor("test-environment-tenant-edit.xml")
public class TestEnvironmentTenantEdit extends TenantEdit {
}

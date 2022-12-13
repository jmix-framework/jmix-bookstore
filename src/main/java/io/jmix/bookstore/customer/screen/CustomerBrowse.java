package io.jmix.bookstore.customer.screen;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.entity.Address;
import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@UiController("bookstore_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
@Route(value = "customers")
public class CustomerBrowse extends StandardLookup<Customer> {
    @Autowired
    private GeoMap customersMap;
    @Autowired
    private ScreenBuilders screenBuilders;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        VectorLayer<Customer> customersLayer = customersMap.getLayer("customersLayer");

        customersLayer.addGeoObjectClickListener(addressGeoObjectClickEvent ->
            screenBuilders.editor(Customer.class, this)
                .editEntity(addressGeoObjectClickEvent.getItem())
                .show()
        );
    }


    @Install(to = "customersMap.customersLayer", subject = "tooltipContentProvider")
    private String mapAddressLayerTooltipContentProvider(Customer customer) {
        return customer.getFullName();
    }
}

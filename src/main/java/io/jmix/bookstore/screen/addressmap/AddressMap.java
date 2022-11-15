package io.jmix.bookstore.screen.addressmap;

import io.jmix.bookstore.entity.Address;
import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.ui.model.DataComponents;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("bookstore_AddressMap")
@UiDescriptor("address-map.xml")
public class AddressMap extends Screen {
    @Autowired
    private GeoMap map;

    private Address address;
    @Autowired
    private DataComponents dataComponents;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        VectorLayer<Address> addressLayer = new VectorLayer<>("addressLayer");
        InstanceContainer<Address> instanceContainer = dataComponents.createInstanceContainer(Address.class);
        instanceContainer.setItem(address);
        addressLayer.setDataContainer(instanceContainer);
        map.addLayer(addressLayer);
        map.selectLayer(addressLayer);
    }


    public void setAddress(Address address) {
        this.address = address;
    }
}

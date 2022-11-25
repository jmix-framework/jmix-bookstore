package io.jmix.bookstore.screen.addressmap;

import io.jmix.bookstore.entity.Address;
import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.ui.model.DataComponents;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("bookstore_AddressMap")
@UiDescriptor("address-map.xml")
public class AddressMap extends Screen {

    @Autowired
    private DataComponents dataComponents;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private GeoMap map;

    private Address address;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        bindAddressToMap();
        initScreenCaption();
    }

    private void bindAddressToMap() {
        VectorLayer<Address> addressLayer = new VectorLayer<>("addressLayer");
        InstanceContainer<Address> instanceContainer = dataComponents.createInstanceContainer(Address.class);
        instanceContainer.setItem(address);
        addressLayer.setDataContainer(instanceContainer);
        map.addLayer(addressLayer);
        map.selectLayer(addressLayer);

        if (address.getPosition() != null) {
            map.zoomToGeometry(address.getPosition());
        }
    }


    private void initScreenCaption() {
        getWindow().setCaption(
                messageBundle.formatMessage(
                        "addressMap.caption",
                        "%s, %s, %s".formatted(address.getStreet(), address.getCity(), address.getPostCode())
                )
        );
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}

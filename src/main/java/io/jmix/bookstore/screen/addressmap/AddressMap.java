package io.jmix.bookstore.screen.addressmap;

import io.jmix.bookstore.entity.Address;
import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.ui.component.NotificationFacet;
import io.jmix.ui.model.DataComponents;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Autowired
    private NotificationFacet addressNotFoundWarningNotification;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        bindAddressToMap();
        initScreenCaption();
    }

    private void bindAddressToMap() {

        if (address.getPosition() == null) {
            addressNotFoundWarningNotification.show();
            return;
        }

        VectorLayer<Address> addressLayer = new VectorLayer<>("addressLayer");
        InstanceContainer<Address> instanceContainer = dataComponents.createInstanceContainer(Address.class);
        instanceContainer.setItem(address);
        addressLayer.setDataContainer(instanceContainer);
        map.addLayer(addressLayer);
        map.selectLayer(addressLayer);

        map.zoomToGeometry(address.getPosition());

    }


    private void initScreenCaption() {

        String addressString = Stream.of(
                        address.getStreet(),
                        address.getCity(),
                        address.getPostCode()
                )
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));

        getWindow().setCaption(
                messageBundle.formatMessage(
                        "addressMap.caption",
                        addressString
                )
        );
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}

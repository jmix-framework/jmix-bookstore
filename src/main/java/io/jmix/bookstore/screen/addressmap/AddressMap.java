package io.jmix.bookstore.screen.addressmap;

import io.jmix.bookstore.directions.LocationIqClient;
import io.jmix.bookstore.entity.Address;
import io.jmix.mapsui.component.CanvasLayer;
import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.ui.model.DataComponents;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@UiController("bookstore_AddressMap")
@UiDescriptor("address-map.xml")
public class AddressMap extends Screen {

    @Autowired
    private DataComponents dataComponents;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private GeoMap map;
    @Autowired
    private LocationIqClient locationIqClient;

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
        map.zoomToGeometry(address.getPosition());

        GeometryFactory geometryFactory = new GeometryFactory();
        Point fifthAvenue = geometryFactory.createPoint(new Coordinate(-73.9849336, 40.7487727));
        Point empireStateBuilding = geometryFactory.createPoint(new Coordinate(-73.9905353, 40.7484404));


        locationIqClient.calculateRoute(fifthAvenue, empireStateBuilding)
                .ifPresent(route -> {
                    CanvasLayer canvas = map.getCanvas();
                    canvas.addPolyline(route);
                    map.zoomToGeometry(route);
                });

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

package io.jmix.bookstore.order.screen;

import io.jmix.bookstore.directions.DirectionsProvider;
import io.jmix.bookstore.entity.Address;
import io.jmix.mapsui.component.CanvasLayer;
import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.model.DataComponents;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

@UiController("bookstore_TrackDeliveryMap")
@UiDescriptor("track-delivery-map.xml")
public class TrackDeliveryMap extends Screen {

    @Autowired
    private DataComponents dataComponents;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private GeoMap map;
    @Autowired
    private DirectionsProvider directionsProvider;

    private Address end;
    private Address start;
    @Autowired
    private GeometryStyles geometryStyles;


    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        bindAddressToMap();
        initScreenCaption();
    }

    private void bindAddressToMap() {

        VectorLayer<Address> customerLayer = new VectorLayer<>("customerLayer");
        customerLayer.setStyleProvider(address -> geometryStyles.point()
                .withFontIcon(JmixIcon.USER)
                .setIconPathFillColor("#3288ff"));
        InstanceContainer<Address> customerDc = dataComponents.createInstanceContainer(Address.class);
        customerDc.setItem(end);
        customerLayer.setDataContainer(customerDc);


        VectorLayer<Address> fulfillmentCenterLayer = new VectorLayer<>("fulfillmentCenterLayer");
        fulfillmentCenterLayer.setStyleProvider(address -> geometryStyles.point()
                .withFontIcon(JmixIcon.BUILDING_O)
                .setIconPathFillColor("#2db83d"));
        InstanceContainer<Address> fulfillmentCenterDc = dataComponents.createInstanceContainer(Address.class);


        fulfillmentCenterDc.setItem(start);
        fulfillmentCenterLayer.setDataContainer(fulfillmentCenterDc);


        map.addLayer(customerLayer);
        map.addLayer(fulfillmentCenterLayer);
        map.selectLayer(customerLayer);


        directionsProvider.calculateRoute(start.getPosition(), end.getPosition())
                .ifPresent(route -> {
                    CanvasLayer canvas = map.getCanvas();
                    canvas.addPolyline(route.lineString());
                    map.selectLayer(canvas);
                    Point truckPosition = route.lineString().getPointN(new Random().nextInt(route.lineString().getNumPoints()));
                    CanvasLayer.Point truckPositionPoint = canvas.addPoint(truckPosition);
                    truckPositionPoint.setStyle(geometryStyles.point()
                            .withFontIcon(JmixIcon.TRUCK)
                            .setIconPathFillColor("#ff0000"));
                    map.zoomToGeometry(route.lineString());
                });

    }


    private void initScreenCaption() {
        getWindow().setCaption(
                messageBundle.formatMessage(
                        "trackDeliveryMap.caption",
                        "%s, %s, %s".formatted(end.getStreet(), end.getCity(), end.getPostCode())
                )
        );
    }


    public void setEnd(Address end) {
        this.end = end;
    }

    public void setStart(Address start) {
        this.start = start;
    }
}

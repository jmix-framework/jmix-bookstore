package io.jmix.bookstore.order.screen;

import io.jmix.bookstore.directions.CalculatedRoute;
import io.jmix.bookstore.directions.Geocoding;
import io.jmix.bookstore.directions.RouteAccuracy;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import io.jmix.bookstore.order.entity.OrderStatus;
import io.jmix.mapsui.component.CanvasLayer;
import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.mapsui.component.layer.style.GeometryStyle;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.TextField;
import io.jmix.ui.executor.BackgroundTask;
import io.jmix.ui.executor.TaskLifeCycle;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.order.entity.Order;
import org.locationtech.jts.geom.LineString;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@UiController("bookstore_ConfirmOrder")
@UiDescriptor("confirm-order.xml")
@EditedEntityContainer("orderDc")
@Route(value = "orders/confirm", parentPrefix = "owners")
public class ConfirmOrder extends StandardEditor<Order> {

    @Autowired
    private GeometryStyles geometryStyles;
    @Autowired
    private GeoMap fulfilledByMap;
    @Autowired
    private Geocoding geocoding;
    private CanvasLayer.Polyline drawnPolyline;
    @Autowired
    private TextField<String> durationField;
    @Autowired
    private TextField<String> distanceField;
    @Autowired
    private CollectionContainer<FulfillmentCenter> fulfillmentCentersDc;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private MessageBundle messageBundle;

    private Map<FulfillmentCenter, Optional<CalculatedRoute>> calculatedRoutes;
    @Autowired
    private Notifications notifications;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {

        tryToCalculateRoutesFromFulfillmentCenters();
    }

    private void tryToCalculateRoutesFromFulfillmentCenters() {
        Address shippingAddress = getEditedEntity().getShippingAddress();
        if (shippingAddress == null || shippingAddress.getPosition() == null) {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption(messageBundle.getMessage("noValidShippingAddressFound"))
                    .show();
            return;
        }

        calculateRoutesFromFulfillmentCenters(shippingAddress);
    }

    private void calculateRoutesFromFulfillmentCenters(Address shippingAddress) {
        CanvasLayer canvas = fulfilledByMap.getCanvas();
        CanvasLayer.Point point = canvas.addPoint(shippingAddress.getPosition());
        point.setStyle(
                geometryStyles.point()
                .withFontIcon(JmixIcon.USER)
                .setIconPathFillColor("#3288ff")
        );


        List<FulfillmentCenter> fulfillmentCenters = fulfillmentCentersDc.getItems();
        BackgroundTask<Integer, Map<FulfillmentCenter, Optional<CalculatedRoute>>> task = new CalculateRoutesTask(
                fulfillmentCenters,
                shippingAddress
        );
        dialogs.createBackgroundWorkDialog(this, task)
                .withCaption(messageBundle.getMessage("calculatingRoutesCaption"))
                .withMessage(messageBundle.getMessage("calculatingRoutesMessage"))
                .withCancelAllowed(true)
                .withTotal(fulfillmentCenters.size())
                .withShowProgressInPercentage(true)
                .show();
    }


    private class CalculateRoutesTask extends BackgroundTask<Integer, Map<FulfillmentCenter, Optional<CalculatedRoute>>> {
        private final List<FulfillmentCenter> fulfillmentCenters;
        private final Address shippingAddress;

        public CalculateRoutesTask(List<FulfillmentCenter> fulfillmentCenters, Address shippingAddress) {
            super(20, TimeUnit.SECONDS, ConfirmOrder.this);
            this.fulfillmentCenters = fulfillmentCenters;
            this.shippingAddress = shippingAddress;
        }

        @Override
        public Map<FulfillmentCenter, Optional<CalculatedRoute>> run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
            AtomicInteger index = new AtomicInteger();
            return fulfillmentCenters.stream()
                    .collect(Collectors.toMap(
                            fulfillmentCenter -> fulfillmentCenter,
                            fulfillmentCenter -> tryToCalculatedRouteFromFulfillmentCenter(fulfillmentCenter, taskLifeCycle, index.incrementAndGet())
                    ));
        }
        private Optional<CalculatedRoute> tryToCalculatedRouteFromFulfillmentCenter(FulfillmentCenter fulfillmentCenter, TaskLifeCycle<Integer> taskLifeCycle, int i) {
            try {
                taskLifeCycle.publish(i);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return geocoding.calculateRoute(
                    fulfillmentCenter.getAddress().getPosition(),
                    shippingAddress.getPosition(),
                    RouteAccuracy.LOW_ACCURACY);
        }

        @Override
        public void done(Map<FulfillmentCenter, Optional<CalculatedRoute>> result) {
            storeCalculatedRoutes(result);
        }
    }

    private void storeCalculatedRoutes(Map<FulfillmentCenter, Optional<CalculatedRoute>> calculatedRoutes) {
        this.calculatedRoutes = calculatedRoutes;
        selectNearestFulfillmentCenter();
    }

    private void selectNearestFulfillmentCenter() {
        Optional<CalculatedRoute> nearestRoute = calculatedRoutes.values()
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(Comparator.comparing(CalculatedRoute::distanceInMeters));

        nearestRoute.ifPresent(calculatedRoute ->
                getEditedEntity().setFulfilledBy(
                        calculatedRoutes.entrySet().stream()
                                .filter(fulfillmentCenterOptionalEntry -> fulfillmentCenterOptionalEntry.getValue().equals(nearestRoute))
                                .map(Map.Entry::getKey)
                                .findFirst()
                                .orElse(null)
                ));
    }


    @SuppressWarnings("unused")
    @Install(to = "fulfilledByMap.fulfillmentCentersLayer", subject = "styleProvider")
    private GeometryStyle fulfilledByMapFulfillmentCentersLayerStyleProvider(FulfillmentCenter fulfillmentCenter) {

        if (fulfillmentCenter.equals(getEditedEntity().getFulfilledBy())) {
            return geometryStyles.point()
                    .withFontIcon(JmixIcon.BUILDING_O)
                    .setIconPathFillColor("#ff0000");
        }
        else {
            return geometryStyles.point()
                    .withFontIcon(JmixIcon.BUILDING_O)
                    .setIconPathFillColor("#2db83d");
        }
    }

    @Subscribe("fulfilledByField")
    public void onFulfilledByFieldValueChange(HasValue.ValueChangeEvent<FulfillmentCenter> event) {
        VectorLayer<FulfillmentCenter> fulfillmentCentersLayer = fulfilledByMap.getLayer("fulfillmentCentersLayer");
        fulfillmentCentersLayer.refresh();

        calculatedRouteFor(getEditedEntity().getFulfilledBy())
                .ifPresent(route -> {
                    CanvasLayer canvas = fulfilledByMap.getCanvas();
                    if (drawnPolyline != null) {
                        canvas.removePolyline(drawnPolyline);
                    }
                    LineString potentialRoute = route.lineString();
                    drawnPolyline = canvas.addPolyline(potentialRoute);

                    durationField.setValue(route.duration().prettyPrint());
                    distanceField.setValue(route.distance().prettyPrint());
                });
    }

    private Optional<CalculatedRoute> calculatedRouteFor(FulfillmentCenter fulfillmentCenter) {
        return Optional.ofNullable(fulfillmentCenter)
                .flatMap(calculatedRoutes::get);
    }

    @Subscribe("fulfilledByMap.fulfillmentCentersLayer")
    public void onMapOrderLayerGeoObjectSelected(VectorLayer.GeoObjectSelectedEvent<FulfillmentCenter> event) {
        getEditedEntity().setFulfilledBy(event.getItem());
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        getEditedEntity().setStatus(OrderStatus.CONFIRMED);
    }
}

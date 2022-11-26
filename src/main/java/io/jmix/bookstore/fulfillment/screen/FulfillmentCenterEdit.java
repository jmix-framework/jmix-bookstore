package io.jmix.bookstore.fulfillment.screen;

import io.jmix.bookstore.directions.LocationIqClient;
import io.jmix.bookstore.entity.Address;
import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.Label;
import io.jmix.ui.model.InstancePropertyContainer;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Optional;

@UiController("bookstore_FulfillmentCenter.edit")
@UiDescriptor("fulfillment-center-edit.xml")
@EditedEntityContainer("fulfillmentCenterDc")
public class FulfillmentCenterEdit extends StandardEditor<FulfillmentCenter> {

    @Autowired
    private GeoMap addressMap;
    @Autowired
    private InstancePropertyContainer<Address> addressDc;
    @Autowired
    private Label<String> nameHeaderLabel;
    @Autowired
    private Label<String> emptyNameHeaderLabel;
    @Autowired
    private LocationIqClient locationIqClient;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;


    @Subscribe
    public void onAfterShow(AfterShowEvent event) {

        VectorLayer<Address> addressLayer = new VectorLayer<>("addressLayer");
        addressLayer.setEditable(true);

        addressLayer.setDataContainer(addressDc);

        addressMap.addLayer(addressLayer);
        addressMap.selectLayer(addressLayer);

        if (getEditedEntity().getAddress().getPosition() != null) {
            addressMap.zoomToGeometry(getEditedEntity().getAddress().getPosition());
        }

        String nameValue = getEditedEntity().getName();
        hideOrShowNameHeader(nameValue);
    }

    private void hideOrShowNameHeader(String nameValue) {
        if (nameValue != null) {
            emptyNameHeaderLabel.setVisible(false);
            nameHeaderLabel.setVisible(true);
        }
        else {
            emptyNameHeaderLabel.setVisible(true);
            nameHeaderLabel.setVisible(false);
        }
    }

    @Subscribe("nameField")
    public void onNameFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        hideOrShowNameHeader(event.getValue());
    }

    @Subscribe("locationLookupBtn")
    public void onLocationLookupBtnClick(Button.ClickEvent event) {
        Address address = getEditedEntity().getAddress();
        Optional<Point> possibleLocation = locationIqClient
                .forwardGeocoding(new LocationIqClient.AddressInformation(
                        address.getStreet(),
                        address.getPostCode(),
                        address.getCity(),
                        null,
                        null
                ));

        possibleLocation.ifPresent(it -> {
            address.setPosition(it);
            addressMap.zoomToGeometry(it);
            notifications
                    .create(Notifications.NotificationType.TRAY)
                    .withCaption(messageBundle.getMessage("locationLookupPerformed"))
                    .show();
        });
    }

}

package io.jmix.bookstore.fulfillment.screen;

import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;

@UiController("bookstore_FulfillmentCenter.browse")
@UiDescriptor("fulfillment-center-browse.xml")
@LookupComponent("fulfillmentCentersTable")
@Route(value = "fulfillment-centers")
public class FulfillmentCenterBrowse extends StandardLookup<FulfillmentCenter> {
}

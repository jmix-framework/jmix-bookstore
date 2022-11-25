package io.jmix.bookstore.fulfillment.screen;

import io.jmix.ui.screen.*;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;

@UiController("bookstore_FulfillmentCenter.browse")
@UiDescriptor("fulfillment-center-browse.xml")
@LookupComponent("fulfillmentCentersTable")
public class FulfillmentCenterBrowse extends StandardLookup<FulfillmentCenter> {
}

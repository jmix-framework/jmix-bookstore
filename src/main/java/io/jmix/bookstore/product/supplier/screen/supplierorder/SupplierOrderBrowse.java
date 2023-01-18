package io.jmix.bookstore.product.supplier.screen.supplierorder;

import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.core.TimeSource;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.HBoxLayout;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.Timer;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@UiController("bookstore_SupplierOrder.browse")
@UiDescriptor("supplier-order-browse.xml")
@LookupComponent("supplierOrdersTable")
@Route(value = "supplier-orders")
public class SupplierOrderBrowse extends StandardLookup<SupplierOrder> {

    @Autowired
    private TimeSource timeSource;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private Label<String> nextCalculationLabel;
    @Autowired
    private HBoxLayout nextCalculationLabelHBox;
    private LocalDateTime nextTriggerTime;
    @Autowired
    private Notifications notifications;

    public LocalDateTime nextTriggerTime() {

        LocalDateTime dateTime = timeSource.now().toLocalDateTime();
        int minutes = dateTime.getMinute();
        int mod = minutes % 5;

        return dateTime.plusMinutes(5 - mod)
                .truncatedTo(ChronoUnit.MINUTES);
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        nextTriggerTime = nextTriggerTime();
        refreshNextCalculationInLabel();
    }




    @Subscribe("remainingTimeTimer")
    public void onRemainingTimeTimerTimerAction(Timer.TimerActionEvent event) {
        refreshNextCalculationInLabel();
    }

    private void refreshNextCalculationInLabel() {

        LocalDateTime now = timeSource.now().toLocalDateTime();

        WaitingTime timeUntilNextTrigger = WaitingTime.fromNow(now, nextTriggerTime);

        if (now.isAfter(nextTriggerTime)) {
            getScreenData().loadAll();
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(messageBundle.getMessage("supplierOrdersRefreshed"))
                    .show();

            nextTriggerTime = nextTriggerTime();
        }

        if (timeUntilNextTrigger.isHigh()) {
            nextCalculationLabelHBox.setStyleName("position-badge position-red");
        }
        else if (timeUntilNextTrigger.isMedium()) {
            nextCalculationLabelHBox.setStyleName("position-badge position-yellow");
        }
        else if (timeUntilNextTrigger.isLow()) {
            nextCalculationLabelHBox.setStyleName("position-badge position-green");
        }

        nextCalculationLabel.setValue(messageBundle.formatMessage("nextCreationIn", timeUntilNextTrigger.prettyPrint()));


    }
}

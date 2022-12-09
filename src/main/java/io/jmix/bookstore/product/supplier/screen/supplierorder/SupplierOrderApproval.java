package io.jmix.bookstore.product.supplier.screen.supplierorder;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.bookstore.product.supplier.SupplierOrderLine;
import io.jmix.bookstore.product.supplier.SupplierOrderStatus;
import io.jmix.bpmui.processform.ProcessFormContext;
import io.jmix.bpmui.processform.annotation.Outcome;
import io.jmix.bpmui.processform.annotation.ProcessForm;
import io.jmix.bpmui.processform.annotation.ProcessVariable;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.list.RemoveAction;
import io.jmix.ui.component.*;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.inject.Named;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@UiController("bookstore_SupplierOrder.approval")
@UiDescriptor("supplier-order-approval.xml")
@EditedEntityContainer("supplierOrderDc")
@ProcessForm(
        outcomes = {
                @Outcome(id = SupplierOrderApproval.YES_OUTCOME),
                @Outcome(id = SupplierOrderApproval.NO_OUTCOME)
        }
)
@Route(value = "supplier-orders/approve", parentPrefix = "supplier-orders")
public class SupplierOrderApproval extends StandardEditor<SupplierOrder> {


    static final String YES_OUTCOME = "Yes";
    static final String NO_OUTCOME = "No";

    @ProcessVariable(name = "supplierOrder")
    protected SupplierOrder supplierOrder;

    @ProcessVariable(name = "changesRequiredComment")
    protected String changesRequiredComment;

    @Autowired
    protected ProcessFormContext processFormContext;
    @Autowired
    private Table<SupplierOrderLine> orderLinesTable;
    @Autowired
    private DateField<LocalDate> orderDateField;

    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private TextArea<String> changesRequiredCommentTextArea;
    @Autowired
    private Notifications notifications;


    @Subscribe
    protected void onInit(InitEvent event) {
        setEntityToEdit(supplierOrder);
    }


    @Subscribe("yesOutcomeBtn")
    public void onYesOutcomeBtnClick(Button.ClickEvent event) {

        processFormContext.taskCompletion()
                .withOutcome(YES_OUTCOME)
                .complete();

        closeWithDiscard();
    }

    @Subscribe("noOutcomeBtn")
    public void onNoOutcomeBtnClick(Button.ClickEvent event) {
        Map<String, Object> processVariables = new HashMap<>();
        String changesRequiredComment = changesRequiredCommentTextArea.getValue();

        if (!StringUtils.hasText(changesRequiredComment)) {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption("Please provide comment")
                    .show();

            return;
        }


        processVariables.put("changesRequiredComment", changesRequiredComment);
        processVariables.put("approver", currentAuthentication.getUser());

        processFormContext.taskCompletion()
                .withOutcome(NO_OUTCOME)
                .withProcessVariables(processVariables)
                .complete();

        closeWithDiscard();

    }

    @Subscribe("cancelBtn")
    public void onCancelBtnClick(Button.ClickEvent event) {
        closeWithDiscard();
    }

}

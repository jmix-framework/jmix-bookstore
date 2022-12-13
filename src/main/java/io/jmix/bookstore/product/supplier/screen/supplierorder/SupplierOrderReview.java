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
import io.jmix.ui.action.list.RemoveAction;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.DateField;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.Table;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.inject.Named;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@UiController("bookstore_SupplierOrder.review")
@UiDescriptor("supplier-order-review.xml")
@EditedEntityContainer("supplierOrderDc")
@ProcessForm(
        outcomes = {
                @Outcome(id = SupplierOrderReview.YES_OUTCOME),
                @Outcome(id = SupplierOrderReview.NO_OUTCOME)
        }
)
@Route(value = "supplier-orders/review", parentPrefix = "supplier-orders")
public class SupplierOrderReview extends StandardEditor<SupplierOrder> {


    static final String YES_OUTCOME = "Yes";
    static final String NO_OUTCOME = "No";

    @ProcessVariable(name = "supplierOrder")
    protected SupplierOrder supplierOrder;

    @ProcessVariable(name = "changesRequiredComment")
    protected String changesRequiredComment;
    @ProcessVariable(name = "approver")
    protected User approver;

    @Autowired
    protected ProcessFormContext processFormContext;
    @Autowired
    private Table<SupplierOrderLine> orderLinesTable;
    @Autowired
    private DateField<LocalDate> orderDateField;
    @Named("orderLinesTable.remove")
    private RemoveAction<SupplierOrderLine> orderLinesTableRemove;
    @Autowired
    private Label<String> changesRequiredCommentLabel;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private MessageBundle messageBundle;


    @Subscribe
    protected void onInit(InitEvent event) {
        setEntityToEdit(supplierOrder);
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {

        if (StringUtils.hasText(changesRequiredComment)) {
            changesRequiredCommentLabel.setValue(getChangesRequiredComment());
            changesRequiredCommentLabel.setVisible(true);
        }
    }

    private String getChangesRequiredComment() {
        return messageBundle.formatMessage("changesRequiredComment", approver.getDisplayName(), changesRequiredComment);
    }


    @Subscribe("yesOutcomeBtn")
    public void onYesOutcomeBtnClick(Button.ClickEvent event) {
        Map<String, Object> processVariables = new HashMap<>();
        processVariables.put("supplierOrder", getEditedEntity());

        User user = (User) currentAuthentication.getUser();
        processVariables.put("reviewedBy", user);

        processFormContext.taskCompletion()
                .withOutcome(YES_OUTCOME)
                .withProcessVariables(processVariables)
                .complete();

        closeWithDiscard();
    }

    @Subscribe("noOutcomeBtn")
    public void onNoOutcomeBtnClick(Button.ClickEvent event) {
        Map<String, Object> processVariables = new HashMap<>();
        processVariables.put("supplierOrder", getEditedEntity());


        User user = (User) currentAuthentication.getUser();
        processVariables.put("reviewedBy", user);

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

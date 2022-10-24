package io.jmix.bookstore.product.supplier.screen.supplierorder;

import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.bookstore.product.supplier.SupplierOrderLine;
import io.jmix.bookstore.product.supplier.SupplierOrderStatus;
import io.jmix.bpmui.processform.ProcessFormContext;
import io.jmix.bpmui.processform.annotation.Outcome;
import io.jmix.bpmui.processform.annotation.ProcessForm;
import io.jmix.bpmui.processform.annotation.ProcessVariable;
import io.jmix.ui.action.list.RemoveAction;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.DateField;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.Table;
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
public class SupplierOrderReview extends StandardEditor<SupplierOrder> {


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
    @Named("orderLinesTable.remove")
    private RemoveAction<SupplierOrderLine> orderLinesTableRemove;
    @Autowired
    private Label<String> changesRequiredCommentLabel;


    @Subscribe
    protected void onInit(InitEvent event) {
        setEntityToEdit(supplierOrder);
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {


        if (changesRequired()) {
            orderLinesTable.setEditable(false);
            orderDateField.setEditable(false);
            orderLinesTableRemove.setEnabled(false);

            if (StringUtils.hasText(changesRequiredComment)) {
                changesRequiredCommentLabel.setValue(changesRequiredComment);
                changesRequiredCommentLabel.setVisible(true);
            }
        }
    }

    private boolean changesRequired() {
        return getEditedEntity().getStatus().equals(SupplierOrderStatus.CHANGES_REQUIRED);
    }


    @Subscribe("yesOutcomeBtn")
    public void onYesOutcomeBtnClick(Button.ClickEvent event) {
        Map<String, Object> processVariables = new HashMap<>();
        processVariables.put("supplierOrder", getEditedEntity());

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

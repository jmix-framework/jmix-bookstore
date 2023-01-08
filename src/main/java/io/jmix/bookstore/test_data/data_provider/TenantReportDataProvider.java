package io.jmix.bookstore.test_data.data_provider;

import io.jmix.core.DataManager;
import io.jmix.core.MetadataTools;
import io.jmix.core.UuidProvider;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.reports.ReportsPersistence;
import io.jmix.reports.entity.Report;
import io.jmix.reports.entity.ReportGroup;
import io.jmix.reports.entity.ReportTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("bookstore_TenantReportDataProvider")
public class TenantReportDataProvider implements TestDataProvider<Report, TenantReportDataProvider.DataContext> {

    protected final DataManager dataManager;
    protected final SystemAuthenticator systemAuthenticator;
    private final MetadataTools metadataTools;
    private final ReportsPersistence reportsPersistence;


    public record DataContext(String reportCode, String tenantId){ }
    public TenantReportDataProvider(DataManager dataManager, SystemAuthenticator systemAuthenticator, MetadataTools metadataTools, ReportsPersistence reportsPersistence) {
        this.dataManager = dataManager;
        this.systemAuthenticator = systemAuthenticator;
        this.metadataTools = metadataTools;
        this.reportsPersistence = reportsPersistence;
    }

    @Override
    public List<Report> create(DataContext dataContext) {

        ReportGroup defaultReportGroup = dataManager.create(ReportGroup.class);
        defaultReportGroup.setTitle("General");
        ReportGroup savedDefaultReportGroup = dataManager.save(defaultReportGroup);

        Optional<Report> supplierOrderFormReportTemplate = systemAuthenticator.withSystem(() -> dataManager.load(Report.class)
                .query("select e from report_Report e where e.code = :code and e.sysTenantId is null")
                .parameter("code", "supplier-order-form")
                .fetchPlan("report.edit")
                .optional());

        return supplierOrderFormReportTemplate.stream()
                .map(it -> copyReport(it, savedDefaultReportGroup))
                .collect(Collectors.toList());
    }
    private Report copyReport(Report source, ReportGroup reportGroup) {
        Report copiedReport = metadataTools.deepCopy(source);
        copiedReport.setId(UuidProvider.createUuid());
        copiedReport.setGroup(reportGroup);
        for (ReportTemplate copiedTemplate : copiedReport.getTemplates()) {
            copiedTemplate.setId(UuidProvider.createUuid());
        }

        reportsPersistence.save(copiedReport);
        return copiedReport;
    }
}

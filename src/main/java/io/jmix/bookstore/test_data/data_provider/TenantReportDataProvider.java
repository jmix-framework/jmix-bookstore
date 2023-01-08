package io.jmix.bookstore.test_data.data_provider;

import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.MetadataTools;
import io.jmix.core.UuidProvider;
import io.jmix.core.querycondition.LogicalCondition;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.reports.ReportImportExport;
import io.jmix.reports.ReportsPersistence;
import io.jmix.reports.entity.Report;
import io.jmix.reports.entity.ReportGroup;
import io.jmix.reports.entity.ReportTemplate;
import io.jmix.reports.util.ReportsUtils;
import liquibase.pro.packaged.L;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("bookstore_TenantReportDataProvider")
public class TenantReportDataProvider implements TestDataProvider<Report, TenantReportDataProvider.DataContext> {

    protected final DataManager dataManager;
    protected final SystemAuthenticator systemAuthenticator;
    private final MetadataTools metadataTools;
    private final ReportsUtils reportsUtils;
    private final ReportsPersistence reportsPersistence;


    public record DataContext(String reportCode, String tenantId){ }
    public TenantReportDataProvider(DataManager dataManager, ReportImportExport reportImportExport, SystemAuthenticator systemAuthenticator, MetadataTools metadataTools, ReportsUtils reportsUtils, ReportsPersistence reportsPersistence) {
        this.dataManager = dataManager;
        this.systemAuthenticator = systemAuthenticator;
        this.metadataTools = metadataTools;
        this.reportsUtils = reportsUtils;
        this.reportsPersistence = reportsPersistence;
    }

    @Override
    public List<Report> create(DataContext dataContext) {

        ReportGroup defaultReportGroup = dataManager.create(ReportGroup.class);
        defaultReportGroup.setTitle("General");
        ReportGroup savedDefaultReportGroup = dataManager.save(defaultReportGroup);

        Report supplierOrderFormReportTemplate = systemAuthenticator.withSystem(() -> dataManager.load(Report.class)
                .query("select e from report_Report e where e.code = :code and e.sysTenantId is null")
                .parameter("code", "supplier-order-form")
                .fetchPlan("report.edit")
                .one());


        return List.of(
                copyReport(supplierOrderFormReportTemplate, savedDefaultReportGroup)
        );

    }
    protected Report copyReport(Report source, ReportGroup reportGroup) {
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

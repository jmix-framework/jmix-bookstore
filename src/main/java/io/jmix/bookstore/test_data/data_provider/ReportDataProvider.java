package io.jmix.bookstore.test_data.data_provider;

import io.jmix.core.DataManager;
import io.jmix.reports.ReportImportExport;
import io.jmix.reports.entity.Report;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component("bookstore_ReportDataProvider")
public class ReportDataProvider implements TestDataProvider<Report, ReportDataProvider.DataContext> {

    protected final DataManager dataManager;
    protected final ReportImportExport reportImportExport;
    protected final ResourceLoader resourceLoader;


    public record DataContext(String reportFileName){
        public String resourceName() {
            return "classpath:reports/%s".formatted(reportFileName);
        }
    }
    public ReportDataProvider(DataManager dataManager, ReportImportExport reportImportExport, ResourceLoader resourceLoader) {
        this.dataManager = dataManager;
        this.reportImportExport = reportImportExport;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Report> create(DataContext dataContext) {

        Resource supplierOrderForm = resourceLoader.getResource(dataContext.resourceName());
        try {
            byte[] zipBytes = supplierOrderForm.getInputStream().readAllBytes();
            Collection<Report> reports = reportImportExport.importReports(zipBytes);
            return reports
                    .stream().toList();
        } catch (IOException e) {
            throw new RuntimeException("Error while importing report", e);
        }
    }

}

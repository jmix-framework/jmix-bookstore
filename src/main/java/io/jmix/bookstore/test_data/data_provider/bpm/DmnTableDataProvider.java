package io.jmix.bookstore.test_data.data_provider.bpm;

import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import io.jmix.core.DataManager;
import io.jmix.reports.ReportImportExport;
import org.flowable.dmn.api.DmnRepositoryService;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("bookstore_DmnTableDataProvider")
public class DmnTableDataProvider implements TestDataProvider<Void, DmnTableDataProvider.DataContext> {

    protected final DataManager dataManager;
    protected final ReportImportExport reportImportExport;
    protected final ResourceLoader resourceLoader;
    protected final DmnRepositoryService dmnRepositoryService;


    public record DataContext(String processFileName, String tenantId){
        public String resourceName() {
            return "dmn/%s".formatted(processFileName);
        }
    }
    public DmnTableDataProvider(DataManager dataManager, ReportImportExport reportImportExport, ResourceLoader resourceLoader, DmnRepositoryService dmnRepositoryService) {
        this.dataManager = dataManager;
        this.reportImportExport = reportImportExport;
        this.resourceLoader = resourceLoader;
        this.dmnRepositoryService = dmnRepositoryService;
    }

    @Override
    public List<Void> create(DataContext dataContext) {

        try {
            dmnRepositoryService.createDeployment()
                    .tenantId(dataContext.tenantId())
                    .addClasspathResource(dataContext.resourceName())
                    .deploy();
        } catch (Exception e) {
            throw new RuntimeException("Error while importing the DMN Table", e);
        }
        return List.of();
    }

}

package io.jmix.bookstore.test_data.data_provider.bpm;

import io.jmix.bookstore.test_data.data_provider.TestDataProvider;
import io.jmix.core.DataManager;
import io.jmix.reports.ReportImportExport;
import io.jmix.reports.entity.Report;
import io.jmix.reports.entity.ReportGroup;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component("bookstore_BpmProcessDefinitionDataProvider")
public class BpmProcessDefinitionDataProvider implements TestDataProvider<Void, BpmProcessDefinitionDataProvider.DataContext> {

    protected final DataManager dataManager;
    protected final ReportImportExport reportImportExport;
    protected final ResourceLoader resourceLoader;
    protected final RepositoryService repositoryService;


    public record DataContext(String processFileName, String tenantId){
        public String resourceName() {
            return "processes/%s".formatted(processFileName);
        }
    }
    public BpmProcessDefinitionDataProvider(DataManager dataManager, ReportImportExport reportImportExport, ResourceLoader resourceLoader, RepositoryService repositoryService) {
        this.dataManager = dataManager;
        this.reportImportExport = reportImportExport;
        this.resourceLoader = resourceLoader;
        this.repositoryService = repositoryService;
    }

    @Override
    public List<Void> create(DataContext dataContext) {

        try {
            repositoryService.createDeployment()
                    .tenantId(dataContext.tenantId())
                    .addClasspathResource(dataContext.resourceName())
                    .deploy();
        } catch (Exception e) {
            throw new RuntimeException("Error while importing the BPM process", e);
        }
        return List.of();
    }

}

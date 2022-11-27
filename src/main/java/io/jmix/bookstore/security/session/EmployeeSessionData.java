package io.jmix.bookstore.security.session;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.employee.Territory;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.core.session.SessionData;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component("bookstore_EmployeeSessionData")
public class EmployeeSessionData {

    private final static String ASSOCIATED_REGIONS = "associatedRegions";
    private final static String EMPLOYEE = "employee";

    private final DataManager dataManager;
    private final CurrentAuthentication currentAuthentication;
    private final ObjectProvider<SessionData> sessionDataProvider;

    public EmployeeSessionData(DataManager dataManager, CurrentAuthentication currentAuthentication, ObjectProvider<SessionData> sessionDataProvider) {
        this.dataManager = dataManager;
        this.currentAuthentication = currentAuthentication;
        this.sessionDataProvider = sessionDataProvider;
    }


    public void initSession() {

        loadEmployee(currentAuthentication.getUser())
                .ifPresent(this::setSessionInformation);
    }

    private void setSessionInformation(Employee employee) {

        Set<Region> associatedRegions = employee.getTerritories().stream()
                .map(Territory::getRegion)
                .collect(Collectors.toSet());

        sessionDataProvider.getObject()
                .setAttribute(ASSOCIATED_REGIONS, associatedRegions);
        sessionDataProvider.getObject()
                .setAttribute(EMPLOYEE, employee);
    }

    private Optional<Employee> loadEmployee(UserDetails user) {
        return dataManager.load(Employee.class).condition(PropertyCondition.equal("user", user))
                .fetchPlan(employee -> {
                    employee.addFetchPlan(FetchPlan.BASE);
                    employee.add("territories", territory -> {
                        territory.addFetchPlan(FetchPlan.BASE);
                        territory.add("region", FetchPlan.BASE);
                    });
                })
                .optional();
    }

    public Optional<Employee> employee() {
        return Optional.ofNullable(sessionAttribute(EMPLOYEE, Employee.class));
    }

    public Set<Region> associatedRegions() {
        return (Set<Region>) sessionDataProvider.getObject().getAttribute(ASSOCIATED_REGIONS);
    }

    private <T> T sessionAttribute(String attributeKey, Class<T> attributeType) {
        return (T) sessionDataProvider.getObject().getAttribute(attributeKey);
    }
}

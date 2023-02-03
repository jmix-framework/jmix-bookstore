package io.jmix.bookstore.security.session;

import io.jmix.bookstore.employee.Employee;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.core.session.SessionData;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("bookstore_EmployeeSessionData")
public class BookstoreSessionData {

    private final static String EMPLOYEE = "employee";

    private final DataManager dataManager;
    private final CurrentAuthentication currentAuthentication;
    private final ObjectProvider<SessionData> sessionDataProvider;

    public BookstoreSessionData(DataManager dataManager, CurrentAuthentication currentAuthentication, ObjectProvider<SessionData> sessionDataProvider) {
        this.dataManager = dataManager;
        this.currentAuthentication = currentAuthentication;
        this.sessionDataProvider = sessionDataProvider;
    }


    public void initSession() {

        loadEmployee(currentAuthentication.getUser())
                .ifPresent(this::setSessionInformation);
    }

    private void setSessionInformation(Employee employee) {

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

    private <T> T sessionAttribute(String attributeKey, Class<T> attributeType) {
        return (T) sessionDataProvider.getObject().getAttribute(attributeKey);
    }
}

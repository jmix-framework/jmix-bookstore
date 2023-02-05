package io.jmix.bookstore.test_data.data_provider;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.test_data.AddressGenerator;
import io.jmix.bookstore.test_data.data_provider.territory.AvailableTerritories;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import net.datafaker.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomOfList;

@Component("bookstore_CustomerDataProvider")
public class CustomerDataProvider implements TestDataProvider<Customer, CustomerDataProvider.DataContext> {


    protected final DataManager dataManager;
    protected final AddressGenerator addressGenerator;

    public record DataContext(int amount,
                              AvailableTerritories territories){
    }

    public CustomerDataProvider(DataManager dataManager, AddressGenerator addressGenerator) {
        this.dataManager = dataManager;
        this.addressGenerator = addressGenerator;
    }

    @Override
    public List<Customer> create(DataContext dataContext) {
        return commit(createCustomer(dataContext.amount(), dataContext.territories()));
    }

    private List<Customer> createCustomer(int amount, AvailableTerritories territories) {
        Faker faker = new Faker();

        return Stream.generate(addressGenerator::generate).limit(amount)
                .map(address -> new CustomerData(address, faker.name(), faker.internet(), faker.phoneNumber())).limit(amount)
                .map(customerData -> toCustomer(customerData, territories))
                .collect(Collectors.groupingBy(customer -> customer.getAddress().getPosition().getX()))
                .values().stream()
                .map(customers -> customers.get(0))
                .collect(Collectors.toList());
    }


    public record CustomerData(Address address, Name name, Internet internet, PhoneNumber phoneNumber){}

    private Customer toCustomer(CustomerData customerData, AvailableTerritories territories) {
        Customer customer = dataManager.create(Customer.class);
        Name nameFaker = customerData.name();
        String firstName = nameFaker.firstName();
        String lastName = nameFaker.lastName();

        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        String email = generateEmail(customerData, firstName, lastName);
        customer.setEmail(email);

        customer.setAddress(customerData.address());

        territories.findTerritoryForPosition(customer.getAddress().getPosition())
                .map(Territory::getRegion)
                .ifPresent(customer::setAssociatedRegion);

        return customer;
    }

    private static String generateEmail(CustomerData customerData, String firstName, String lastName) {
        LocalDate birthday = new Faker().date().birthday(20, 70).toLocalDateTime().toLocalDate();
        Internet internetFaker = customerData.internet();
        int i = new Faker().number().randomDigit();
        String lowerCaseLastName = lastName.toLowerCase();
        String lowerCaseFirstName = firstName.toLowerCase();

        List<String> localParts = List.of(
                String.format("%s.%s", lowerCaseFirstName, lowerCaseLastName),
                String.format("%s.%s", lowerCaseLastName, lowerCaseFirstName),
                String.format("%s%s", lowerCaseLastName, lowerCaseFirstName),
                String.format("%s%s", lowerCaseFirstName, lowerCaseLastName),
                String.format("%s-%s", lowerCaseFirstName, lowerCaseLastName),
                String.format("%s-%s", lowerCaseLastName, lowerCaseFirstName),
                String.format("%s", lowerCaseLastName),
                String.format("%s", lowerCaseFirstName),
                String.format("%s%d", lowerCaseFirstName, birthday.getYear()),
                String.format("%s-%d", lowerCaseFirstName, birthday.getYear()),
                String.format("%s%d", lowerCaseLastName, birthday.getYear()),
                String.format("%s%d", lowerCaseFirstName, i),
                String.format("%s%d", lowerCaseLastName, i)
        );
        return internetFaker.emailAddress(randomOfList(localParts));
    }

    private <T> List<T> commit(List<T> entities) {
        SaveContext saveContext = new SaveContext();
        saveContext.setDiscardSaved(true);
        entities.forEach(saveContext::saving);
        dataManager.save(saveContext);

        return entities;
    }
}

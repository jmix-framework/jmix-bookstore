package io.jmix.bookstore.test_data.data_provider;

import io.jmix.bookstore.customer.Customer;
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
public class CustomerDataProvider implements TestDataProvider<Customer, CustomerDataProvider.Dependencies> {

    protected final DataManager dataManager;

    public record Dependencies(){}
    public CustomerDataProvider(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<Customer> create(int amount, Dependencies dependencies) {
        return commit(createCustomer(amount));
    }

    private List<Customer> createCustomer(int amount) {
        Faker faker = new Faker();

        return Stream.generate(() -> new CustomerData(faker.address(), faker.name(), faker.internet(), faker.phoneNumber())).limit(amount)
                .map(this::toCustomer)
                .collect(Collectors.toList());
    }

    public record CustomerData(Address address, Name name, Internet internet, PhoneNumber phoneNumber){}

    private Customer toCustomer(CustomerData customerData) {
        Customer customer = dataManager.create(Customer.class);
        Name nameFaker = customerData.name();
        String firstName = nameFaker.firstName();
        String lastName = nameFaker.lastName();

        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        String email = generateEmail(customerData, firstName, lastName);
        customer.setEmail(email);

        customer.setAddress(toAddress(customerData.address()));
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

    private io.jmix.bookstore.product.supplier.Address toAddress(Address address) {
        io.jmix.bookstore.product.supplier.Address addressEntity = dataManager.create(io.jmix.bookstore.product.supplier.Address.class);
        addressEntity.setCity(address.city());
        addressEntity.setStreet(String.format("%s %s", address.streetName(), address.buildingNumber()));
        addressEntity.setPostCode(address.postcode());
        return addressEntity;
    }

    private <T> List<T> commit(List<T> entities) {
        SaveContext saveContext = new SaveContext();
        entities.forEach(saveContext::saving);
        dataManager.save(saveContext);

        return entities;
    }

}

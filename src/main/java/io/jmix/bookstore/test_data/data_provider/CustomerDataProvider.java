package io.jmix.bookstore.test_data.data_provider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.product.Product;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import net.datafaker.*;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomOfList;

@Component("bookstore_CustomerDataProvider")
public class CustomerDataProvider implements TestDataProvider<Customer, CustomerDataProvider.DataContext> {

    private final Logger log = LoggerFactory.getLogger(CustomerDataProvider.class);

    protected final DataManager dataManager;
    protected final ResourceLoader resourceLoader;
    protected final ObjectMapper objectMapper;
    protected final RestTemplate restTemplate;

    public record DataContext(int amount, String addressesFileName){
    }

    /**
     *         {
     *             "address1": "1745 T Street Southeast",
     *             "address2": "",
     *             "city": "Washington",
     *             "state": "DC",
     *             "postalCode": "20020",
     *             "coordinates": {
     *                 "lat": 38.867033,
     *                 "lng": -76.979235
     *             }
     *         }
     */
    public record AddressEntryCoordinates(double lat, double lng){}
    public record AddressEntry(String address1, String city, String state, String postalCode, AddressEntryCoordinates coordinates){}
    public record AddressEntries(List<AddressEntry> addresses){}
    public CustomerDataProvider(DataManager dataManager, ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.dataManager = dataManager;
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<Customer> create(DataContext dataContext) {
        return commit(createCustomer(dataContext.amount(), dataContext.addressesFileName()));
    }

    private List<Customer> createCustomer(int amount, String addressesFileName) {
        Faker faker = new Faker();

        AddressEntries addresses = addresses(addressesFileName);


        return addresses.addresses().stream()
                .map(address -> new CustomerData(address, faker.name(), faker.internet(), faker.phoneNumber())).limit(amount)
                .map(this::toCustomer)
                .collect(Collectors.groupingBy(customer -> customer.getAddress().getPosition().getX()))
                .values().stream()
                .map(customers -> customers.get(0))
                .collect(Collectors.toList());

//        return Stream.generate(faker::book).limit(amount)
//                .map(book -> toProduct(book, productCategories, suppliers))
//                .collect(Collectors.groupingBy(Product::getName))
//                .values().stream()
//                .map(products -> products.get(0))
//                .collect(Collectors.toList());
    }

    private AddressEntries addresses(String addressesFileName) {
        Resource addressesFile = resourceLoader.getResource(addressesFileName);
        try {
            byte[] zipBytes = addressesFile.getInputStream().readAllBytes();
            return objectMapper.readValue(zipBytes, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error while importing report", e);
        }
    }

    public record CustomerData(AddressEntry address, Name name, Internet internet, PhoneNumber phoneNumber){}

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

    private io.jmix.bookstore.entity.Address toAddress(AddressEntry address) {
        io.jmix.bookstore.entity.Address addressEntity = dataManager.create(io.jmix.bookstore.entity.Address.class);
        addressEntity.setCity(address.city());
        addressEntity.setStreet(address.address1());
        addressEntity.setPostCode(address.postalCode());
        Point point = new GeometryFactory().createPoint(new Coordinate(address.coordinates().lng(), address.coordinates().lat()));
        addressEntity.setPosition(point);
//        Optional<AddressLocation> addressLocation = getAddressLocation(address);

//        try {
//            Thread.sleep(2000);
//            addressLocation.ifPresent(it -> {
//                Point point = new GeometryFactory().createPoint(new Coordinate(it.lon(), it.lat()));
//                addressEntity.setPosition(point);
//            });
//
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return addressEntity;
    }

    private <T> List<T> commit(List<T> entities) {
        SaveContext saveContext = new SaveContext();
        entities.forEach(saveContext::saving);
        dataManager.save(saveContext);

        return entities;
    }

//
//    public Optional<AddressLocation> getAddressLocation(AddressEntry addressEntry) {
//        try {
//            AddressLocation[] response = restTemplate.getForObject("https://us1.locationiq.com/v1/search.php" +
//                            "?key=pk.20ee504d36d6f478cfcfb100da13d68f" +
//                            "&street={street}" +
//                            "&postalcode={postalcode}" +
//                            "&state={state}" +
//                            "&format=json",
//                    AddressLocation[].class,
//                    addressEntry.address(), addressEntry.zip(), addressEntry.state());
//
//            List<AddressLocation> addressLocations = Arrays.asList(response);
//
//            log.info("Result for '{}': {}", addressEntry, addressLocations);
//
//            if (!CollectionUtils.isEmpty(addressLocations)) {
//                return Optional.ofNullable(addressLocations.get(0));
//            }
//
//            return Optional.empty();
//        } catch (Exception e) {
//            log.warn("Error getting Location information for Address '{}': {}", addressEntry, e);
//            return Optional.empty();
//        }
//    }

//    record AddressLocation(double lat, double lon) {
//    }

}

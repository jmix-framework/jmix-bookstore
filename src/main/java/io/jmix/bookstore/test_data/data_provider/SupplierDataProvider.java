package io.jmix.bookstore.test_data.data_provider;

import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.product.supplier.CooperationStatus;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import net.datafaker.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomEnum;
import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomOfList;

@Component("bookstore_SupplierDataProvider")
public class SupplierDataProvider implements TestDataProvider<Supplier, SupplierDataProvider.DataContext> {

    protected final DataManager dataManager;

    public record DataContext(int amount){}
    public SupplierDataProvider(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<Supplier> create(DataContext dataContext) {
        return commit(createSupplier(dataContext.amount()));
    }

    private List<Supplier> createSupplier(int amount) {
        Faker faker = new Faker();

        return Stream.generate(() -> new SupplierData(faker.company(), faker.address(), faker.name(), faker.internet(), faker.phoneNumber())).limit(amount)
                .map(this::toSupplier)
                .collect(Collectors.toList());
    }

    public record SupplierData(net.datafaker.Company company, Address address, Name name, Internet internet, PhoneNumber phoneNumber){}


    private Supplier toSupplier(SupplierData supplierData) {
        Supplier supplier = dataManager.create(Supplier.class);

        Name name = supplierData.name();

        List<Company> potentialCompanies = List.of(
                new Company(name.lastName(), null, supplierData.company().suffix(), "-", ".com"),
                new Company(name.lastName(), name.lastName(), supplierData.company().suffix(), "-", ".com"),
                new Company(name.lastName(), name.lastName(), null, "-", ".com"),
                new Company(name.lastName(), null, supplierData.company().suffix(), "-", ".co.uk"),
                new Company(name.lastName(), name.lastName(), supplierData.company().suffix(), "-", ".co.uk"),
                new Company(name.lastName(), name.lastName(), null, "-", ".co.uk"),
                new Company(name.lastName(), null, supplierData.company().suffix(), "-", ".de"),
                new Company(name.lastName(), name.lastName(), supplierData.company().suffix(), "-", ".de"),
                new Company(name.lastName(), name.lastName(), null, "-", ".de")
        );


        Company company = randomOfList(potentialCompanies);
        String firstName = name.firstName();
        String lastName = name.lastName();

        supplier.setName(company.name());
        supplier.setAddress(toAddress(supplierData.address()));
        supplier.setContactName(String.format("%s %s", firstName, lastName));
        supplier.setContactTitle(randomEnum(Title.values()));
        supplier.setWebsite(company.website());
        supplier.setEmail(randomOfList(
                company.email(),
                company.email(firstName, lastName),
                company.email(firstName, lastName)
        ));
        supplier.setCooperationStatus(randomEnum(CooperationStatus.values()));
        supplier.setPhone(supplierData.phoneNumber().phoneNumber());
        return supplier;
    }
    private io.jmix.bookstore.entity.Address toAddress(Address address) {
        io.jmix.bookstore.entity.Address addressEntity = dataManager.create(io.jmix.bookstore.entity.Address.class);
        addressEntity.setCity(address.city());
        addressEntity.setStreet(String.format("%s %s", address.streetName(), address.buildingNumber()));
        addressEntity.setPostCode(address.postcode());
        return addressEntity;
    }

    private <T> List<T> commit(List<T> entities) {
        SaveContext saveContext = new SaveContext();
        saveContext.setDiscardSaved(true);
        entities.forEach(saveContext::saving);
        dataManager.save(saveContext);

        return entities;
    }



    public record Company(String firstPart, String secondPart, String suffix, String domainSeparator, String tld) {

        public String domain() {

            if (secondPart == null) {
                if (suffix != null) {
                    return validInternetString(firstPart + domainSeparator + suffix);
                }
                else {
                    return validInternetString(firstPart);
                }
            }
            else {
                if (suffix == null) {
                    return validInternetString(firstPart + domainSeparator + secondPart);
                }
                else {
                    return validInternetString(firstPart + domainSeparator + secondPart + domainSeparator + suffix);
                }
            }
        }

        private String validInternetString(String string) {
            return string.toLowerCase().replaceAll(" ", "-") + tld;
        }

        public String name() {

            if (secondPart == null) {
                if (suffix != null) {
                    return firstPart + " " + suffix;
                }
                else {
                    return firstPart;
                }
            }
            else {
                if (suffix == null) {
                    return firstPart + "-" + secondPart;
                }
                else {
                    return firstPart + "-" + secondPart + " " + suffix;
                }
            }
        }

        public String email(String firstName, String lastName) {

            char firstNameCharacter = firstName.charAt(0);

            String result = randomOfList(List.of(
                    String.format("%s.%s@%s", firstName, lastName, domain()),
                    String.format("%s@%s", lastName, domain()),
                    String.format("%s%s@%s", firstNameCharacter, lastName, domain()),
                    String.format("%s.%s@%s", firstNameCharacter, lastName, domain()),
                    String.format("%s%s@%s", lastName, firstNameCharacter, domain()),
                    String.format("%s.%s@%s", lastName, firstNameCharacter, domain())
            ));
            return result.toLowerCase().replaceAll(" ", "-");
        }



        public String email() {
            String result = randomOfList(List.of(
                    String.format("contact@%s", domain()),
                    String.format("info@%s", domain())
            ));

            return result.toLowerCase().replaceAll(" ", "-");
        }

        public String website() {
            return randomOfList(List.of(
               String.format("https://%s", domain()),
               String.format("http://%s", domain()),
               String.format("http://www.%s", domain()),
               String.format("https://www.%s", domain())
            ));
        }
    }
}

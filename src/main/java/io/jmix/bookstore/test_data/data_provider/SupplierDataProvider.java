package io.jmix.bookstore.test_data.data_provider;

import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.product.Supplier;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import net.datafaker.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomEnum;

@Component("bookstore_SupplierDataProvider")
public class SupplierDataProvider implements TestDataProvider<Supplier, SupplierDataProvider.Dependencies> {

    protected final DataManager dataManager;

    public record Dependencies(){}
    public SupplierDataProvider(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<Supplier> create(int amount, Dependencies dependencies) {
        return commit(createSupplier(amount));
    }

    private List<Supplier> createSupplier(int amount) {
        Faker faker = new Faker();

        return Stream.generate(() -> new SupplierData(faker.company(), faker.address(), faker.name(), faker.internet(), faker.phoneNumber())).limit(amount)
                .map(this::toSupplier)
                .collect(Collectors.toList());
    }

    public record SupplierData(Company company, Address address, Name name, Internet internet, PhoneNumber phoneNumber){}

    private Supplier toSupplier(SupplierData supplierData) {
        Supplier supplier = dataManager.create(Supplier.class);
        supplier.setName(supplierData.company().name());
        supplier.setAddress(toAddress(supplierData.address()));
        supplier.setContactName(supplierData.name().fullName());
        supplier.setContactTitle(randomEnum(Title.values()));
        supplier.setWebsite(supplierData.internet().domainName());
        supplier.setEmail(supplierData.internet().emailAddress());
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
        entities.forEach(saveContext::saving);
        dataManager.save(saveContext);

        return entities;
    }
}

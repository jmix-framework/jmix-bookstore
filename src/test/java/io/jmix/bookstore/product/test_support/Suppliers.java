package io.jmix.bookstore.product.test_support;

import io.jmix.bookstore.product.supplier.Address;
import io.jmix.bookstore.product.supplier.Currency;
import io.jmix.bookstore.product.supplier.Money;
import io.jmix.bookstore.product.supplier.test_support.AddressData;
import io.jmix.bookstore.product.supplier.test_support.AddressMapper;
import io.jmix.bookstore.product.supplier.test_support.MoneyData;
import io.jmix.bookstore.product.supplier.test_support.MoneyMapper;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.bookstore.test_support.TestDataProvisioning;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Suppliers
        implements TestDataProvisioning<SupplierData, SupplierData.SupplierDataBuilder, Supplier> {

    @Autowired
    DataManager dataManager;

    @Autowired
    SupplierMapper productMapper;
    @Autowired
    AddressMapper addressMapper;

    @Autowired
    SupplierRepository productRepository;
    @Autowired
    private MoneyMapper moneyMapper;

    public static final String DEFAULT_EMAIL = "info@supplier_name.com";
    public static final String DEFAULT_PHONE = "+491928791279";
    public static final String DEFAULT_WEBSITE = "supplier_name.com";
    public static final String DEFAULT_STREET = "street";
    public static final String DEFAULT_POST_CODE = "postcode";
    public static final String DEFAULT_CITY = "city";

    public static final String DEFAULT_NAME = "supplier_name";
    public static final String DEFAULT_CONTACT_NAME = "Magda Supplier";
    public static final String DEFAULT_CONTACT_TITLE = "Mrs";

    @Override
    public SupplierData.SupplierDataBuilder defaultData() {
        return SupplierData.builder()
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .website(DEFAULT_WEBSITE)
                .contactName(DEFAULT_CONTACT_NAME)
                .contactTitle(DEFAULT_CONTACT_TITLE)
                .phone(DEFAULT_PHONE)
                .address(defaultAddress());
    }

    public Address defaultAddress() {
        return addressMapper.toEntity(AddressData.builder()
                .street(DEFAULT_STREET)
                .postCode(DEFAULT_POST_CODE)
                .city(DEFAULT_CITY)
                .build());
    }

    @Override
    public Supplier save(SupplierData productData)  {
        return productRepository.save(productData);
    }

    @Override
    public Supplier create(SupplierData productData) {
        return productMapper.toEntity(productData);
    }

    @Override
    public Supplier createDefault() {
        return create(defaultData().build());
    }

    @Override
    public Supplier saveDefault() {
        return save(defaultData().build());
    }

    public Money money(BigDecimal amount, Currency currency) {
        return moneyMapper.toEntity(MoneyData.builder()
                .amount(amount)
                .currency(currency)
                .build());
    }
}

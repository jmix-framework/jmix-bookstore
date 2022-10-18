package io.jmix.bookstore.product.test_support;


import io.jmix.bookstore.product.supplier.SupplierOrderRequest;
import io.jmix.bookstore.test_support.TestDataProvisioning;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SupplierOrderRequests
        implements TestDataProvisioning<SupplierOrderRequestData, SupplierOrderRequestData.SupplierOrderRequestDataBuilder, SupplierOrderRequest> {

    @Autowired
    DataManager dataManager;

    @Autowired
    SupplierOrderRequestMapper supplierOrderRequestMapper;

    @Autowired
    SupplierOrderRequestRepository supplierOrderRequestRepository;

    @Autowired
    Products products;

    private static final Integer DEFAULT_REQUESTED_AMOUNT = 10;
    public static final String DEFAULT_COMMENT = "supplierOrderRequest_comment";

    @Override
    public SupplierOrderRequestData.SupplierOrderRequestDataBuilder defaultData() {
        return SupplierOrderRequestData.builder()
                .product(products.saveDefault())
                .comment(DEFAULT_COMMENT)
                .requestedAmount(DEFAULT_REQUESTED_AMOUNT);
    }

    @Override
    public SupplierOrderRequest save(SupplierOrderRequestData supplierOrderRequestData)  {
        return supplierOrderRequestRepository.save(supplierOrderRequestData);
    }

    @Override
    public SupplierOrderRequest create(SupplierOrderRequestData supplierOrderRequestData) {
        return supplierOrderRequestMapper.toEntity(supplierOrderRequestData);
    }

    @Override
    public SupplierOrderRequest createDefault() {
        return create(defaultData().build());
    }

    @Override
    public SupplierOrderRequest saveDefault() {
        return save(defaultData().build());
    }
}

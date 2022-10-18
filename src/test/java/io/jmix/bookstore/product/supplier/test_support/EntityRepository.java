package io.jmix.bookstore.product.supplier.test_support;


public interface EntityRepository<DTO, Entity> {
    Entity save(DTO dto);
}

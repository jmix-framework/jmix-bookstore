package io.jmix.bookstore.entity.test_support;


public interface EntityRepository<DTO, Entity> {
    Entity save(DTO dto);
}

package io.jmix.bookstore.test_support;


public interface TestDataProvisioning<DTO, DTOBuilder, Entity> {

    DTOBuilder defaultData();

    Entity save(DTO dto);
    Entity saveDefault();

    Entity create(DTO dto);
    Entity createDefault();
}

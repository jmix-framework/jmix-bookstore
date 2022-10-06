package io.jmix.bookstore.test_support.test_data;


public interface TestDataProvisioning<DTO, DTOBuilder, Entity> {

    DTOBuilder defaultData();

    Entity save(DTO dto);
    Entity saveDefault();

    Entity create(DTO dto);
    Entity createDefault();
}

package io.jmix.bookstore.employee;


import io.jmix.bookstore.employee.test_support.Regions;
import io.jmix.bookstore.test_support.AuthenticatedAsAdmin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("integration-test")
@ExtendWith(AuthenticatedAsAdmin.class)
class RegionStorageTest {

    @Autowired
    private Regions regions;

    @Test
    void given_validRegion_when_save_then_regionIsSaved() {

        // when
        Region region = regions.save(
                regions.defaultData()
                        .name("Foo Region")
                        .build()
        );

        // then
        assertThat(region.getId())
                .isNotNull();
    }

}

package io.jmix.bookstore.employee;


import io.jmix.bookstore.employee.test_support.Regions;
import io.jmix.bookstore.employee.test_support.Territories;
import io.jmix.bookstore.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("integration-test")
@ExtendWith(AuthenticatedAsAdmin.class)
class TerritoryStorageTest {

    @Autowired
    private DataManager dataManager;
    @Autowired
    private Territories territories;
    @Autowired
    private Regions regions;

    @Test
    void given_validTerritory_when_save_then_territoryIsSaved() {

        // given
        Region region = regions.saveDefault();

        // when
        Territory territory = territories.save(
                territories.defaultData()
                        .name("Foo Territory")
                        .region(region)
                        .build()
        );

        // then
        assertThat(territory.getId())
                .isNotNull();
    }

    @Test
    void given_validTerritoryWithRegion_when_save_then_territoryAndRegionAssociationAreSaved() {

        // given
        Region region = regions.saveDefault();

        // when
        Territory territory = territories.save(
                territories.defaultData()
                        .region(region)
                        .build()
        );

        // then
        assertThat(loadTerritory(territory))
                .extracting(Territory::getRegion)
                .isEqualTo(region);
    }

    private Territory loadTerritory(Territory territory) {
        return dataManager.load(Id.of(territory)).one();
    }
}

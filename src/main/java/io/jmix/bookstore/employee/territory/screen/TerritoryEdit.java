package io.jmix.bookstore.employee.territory.screen;

import io.jmix.mapsui.component.layer.style.GeometryStyle;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.employee.Territory;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("bookstore_Territory.edit")
@UiDescriptor("territory-edit.xml")
@EditedEntityContainer("territoryDc")
@Route(value = "territories/edit", parentPrefix = "territories")
public class TerritoryEdit extends StandardEditor<Territory> {


    @Autowired
    private GeometryStyles geometryStyles;

    @Install(to = "territoryMap.otherTerritoriesLayer", subject = "styleProvider")
    private GeometryStyle mapTerritoryLayerStyleProvider(Territory territory) {
        return geometryStyles.polygon()
                .setFillColor("#BAF2D8")
                .setStrokeColor("#23BA75")
                .setFillOpacity(0.3)
                .setStrokeWeight(1);
    }

}

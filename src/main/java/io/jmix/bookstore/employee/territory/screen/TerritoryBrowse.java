package io.jmix.bookstore.employee.territory.screen;

import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.TooltipOptions;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.mapsui.component.layer.style.GeometryStyle;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.ui.component.CheckBox;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import io.jmix.bookstore.employee.Territory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Optional;

@UiController("bookstore_Territory.browse")
@UiDescriptor("territory-browse.xml")
@LookupComponent("territoriesTable")
public class TerritoryBrowse extends StandardLookup<Territory> {

    @Autowired
    private GeometryStyles geometryStyles;
    @Autowired
    private DataGrid<Territory> territoriesTable;
    @Autowired
    private CollectionContainer<Territory> selectedTerritoriesDc;
    @Autowired
    private GeoMap territoryMap;
    private boolean zoomOnSelection;
    @Autowired
    private CheckBox zoomOnSelectionCheckbox;


    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        initTooltipForSelectedTerritoriesLayer();

        zoomOnSelectionCheckbox.setValue(true);
    }

    private void initTooltipForSelectedTerritoriesLayer() {
        VectorLayer<Territory> selectedTerritoriesLayer = selectedTerritoriesLayer();
        TooltipOptions selectedTooltipOptions = new TooltipOptions();
        selectedTooltipOptions.setPermanent(true);
        selectedTerritoriesLayer.setTooltipOptions(selectedTooltipOptions);
        selectedTerritoriesLayer.setTooltipContentProvider(Territory::getName);
    }


    @Install(to = "territoryMap.selectedTerritoriesLayer", subject = "styleProvider")
    private GeometryStyle mapSelectedTerritoryLayerStyleProvider(Territory territory) {
        return geometryStyles.polygon()
                .setFillColor("#FFC8D0")
                .setStrokeColor("#DF6679")
                .setFillOpacity(0.3)
                .setStrokeWeight(1);
    }
    @Install(to = "territoryMap.territoriesLayer", subject = "styleProvider")
    private GeometryStyle mapTerritoryLayerStyleProvider(Territory territory) {
        return geometryStyles.polygon()
                .setFillColor("#BAF2D8")
                .setStrokeColor("#23BA75")
                .setFillOpacity(0.3)
                .setStrokeWeight(1);
    }

    @Subscribe("territoryMap.territoriesLayer")
    public void onMapOrderLayerGeoObjectSelected(VectorLayer.GeoObjectSelectedEvent<Territory> event) {
        territoriesTable.setSelected(event.getItem());
    }

    @Subscribe("territoriesTable")
    public void onTerritoriesTableSelection(DataGrid.SelectionEvent<Territory> event) {
        selectedTerritoriesDc.setItems(event.getSelected());
        if (zoomOnSelection) {
            Optional<Territory> territory = event.getSelected().stream().findFirst();

            territory.map(Territory::getGeographicalArea)
                    .filter(Objects::nonNull)
                    .ifPresent(territoryMap::zoomToGeometry);
        }
    }

    private VectorLayer<Territory> selectedTerritoriesLayer() {
        return territoryMap.getLayer("selectedTerritoriesLayer");
    }


    @Install(to = "territoryMap.territoriesLayer", subject = "tooltipContentProvider")
    private String mapTerritoryLayerTooltipContentProvider(Territory territory) {
        return territory.getName();
    }

    @Subscribe("zoomOnSelectionCheckbox")
    public void onZoomOnSelectionValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        zoomOnSelection = event.getValue();
    }
}

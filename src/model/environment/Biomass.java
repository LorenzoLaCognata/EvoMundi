package model.environment;

import model.enums.BiomassType;

import java.util.EnumMap;
import java.util.Map;

public class Biomass {

    private double quantity;

    public Biomass(double quantity) {
        this.quantity = quantity;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public static Map<BiomassType, Biomass> initializeBiomass() {

        Map<BiomassType, Biomass> biomassMap = new EnumMap<>(BiomassType.class);

        // TODO: add additional biomass
        Biomass biomass = new Biomass(100.0);

        biomassMap.put(BiomassType.NA, biomass);

        return biomassMap;

    }

}

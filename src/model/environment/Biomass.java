package model.environment;

import java.util.HashSet;
import java.util.Set;

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

    public static Set<Biomass> initializeBiomass() {

        Set<Biomass> biomassSet = new HashSet<>();

        // TODO: add additional biomass
        Biomass biomass = new Biomass(100.0);

        biomassSet.add(biomass);

        return biomassSet;

    }

}

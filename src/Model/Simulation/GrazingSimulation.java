package Model.Simulation;

import Model.Animals.Organism;
import Model.Animals.Species;
import Model.Enums.BiomassType;
import Model.Environment.Biomass;

import java.util.Map;

public class GrazingSimulation {

    public void speciesGraze(Map<BiomassType, Biomass> biomassMap, Species species) {

        for (Organism organism : species.getAliveOrganisms()) {

            // TODO: selection of the biomass to consume and not always the first one
            Biomass biomass = biomassMap.entrySet().iterator().next().getValue();

            if (biomass.getQuantity() > 0.0) {
                graze(biomass, organism);
            }

        }
    }

    public void graze(Biomass biomass, Organism organism) {

        // TODO: biomass consumption following a formula variable by species
        double quantityConsumed = Math.min(0.001, biomass.getQuantity());

        // TODO: energy gain following a formula variable by species
        double energyGained = Math.min(70.0, 100.0 - organism.getEnergy());

        biomass.setQuantity(biomass.getQuantity() - quantityConsumed);
        organism.setEnergy(organism.getEnergy() + energyGained);

    }

}

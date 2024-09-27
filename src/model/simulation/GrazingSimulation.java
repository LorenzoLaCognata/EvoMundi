package model.simulation;

import model.animals.Organism;
import model.animals.Species;
import model.environment.Biomass;

import java.util.Set;

public class GrazingSimulation {

    public void speciesGraze(Set<Biomass> biomassSet, Species species) {

        for (int i = 0; i < species.getOrganisms().size(); i++) {

            Organism organism = species.getOrganisms().get(i);

            // TODO: selection of the biomass to consume and not always the first one
            Biomass biomass = biomassSet.iterator().next();

            if (biomass.getQuantity() > 0.0) {
                graze(biomass, organism);
            }

        }
    }

    public void graze(Biomass biomass, Organism organism) {

        // TODO: biomass consumption following a formula variable by species (use variables for parameters)
        double quantityConsumed = Math.min(0.001, biomass.getQuantity());

        // TODO: energy gain following a formula variable by species (use variables for parameters)
        double energyGained = Math.min(70.0, 100.0 - organism.getEnergy());

        biomass.setQuantity(biomass.getQuantity() - quantityConsumed);
        organism.setEnergy(organism.getEnergy() + energyGained);

    }

}

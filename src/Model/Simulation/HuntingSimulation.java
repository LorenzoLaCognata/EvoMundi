package Model.Simulation;

import Model.Animals.Organism;
import Model.Animals.PreySpeciesType;
import Model.Animals.Species;
import Model.Enums.OrganismDeathReason;
import Model.Enums.OrganismStatus;
import Model.Enums.SimulationStatus;
import Model.Enums.SpeciesType;
import Utils.Log;
import Utils.RandomGenerator;

import java.util.List;
import java.util.Map;

public class HuntingSimulation {

    public Species choosePreySpecies(Map<SpeciesType, Species> speciesMap, Organism predatorOrganism) {

    if (!predatorOrganism.getPreySpecies().isEmpty()) {

        double preySpeciesTypeSelected = RandomGenerator.random.nextDouble();
        double preySpeciesTypeSelectorIndex = 0.0;

        for (PreySpeciesType preySpeciesType : predatorOrganism.getPreySpecies().values()) {

            if (preySpeciesTypeSelected >= preySpeciesTypeSelectorIndex && preySpeciesTypeSelected < preySpeciesTypeSelectorIndex + preySpeciesType.preferenceRate()) {
                return speciesMap.get(preySpeciesType.speciesType());
            }

            else {
                preySpeciesTypeSelectorIndex = preySpeciesTypeSelectorIndex + preySpeciesType.preferenceRate();
            }

        }

    }

    return null;

}

    public void speciesHunt(Map<SpeciesType, Species> speciesMap, Species predatorSpecies) {

        for (Organism predatorOrganism : predatorSpecies.getAliveOrganisms()) {

            if (!predatorOrganism.getPreySpecies().isEmpty()) {

                int huntingAttemptNumber = 0;

                while (predatorOrganism.getEnergy() < 1.0 && huntingAttemptNumber < predatorOrganism.getHuntingAttributes().huntAttempts())  {

                    huntingAttempt(speciesMap, predatorOrganism);

                    huntingAttemptNumber++;

                }

            }
        }
    }

    public void huntingAttempt(Map<SpeciesType, Species> speciesMap, Organism predatorOrganism) {
        Species preySpecies = choosePreySpecies(speciesMap, predatorOrganism);

        if (preySpecies != null) {

            List<Organism> preyOrganisms = preySpecies.getAliveOrganisms();
            int preySpeciesPopulation = preySpecies.getPopulation();

            if (preySpeciesPopulation > 0) {

                int preyOrganismSelected = RandomGenerator.random.nextInt(0, preySpeciesPopulation);

                Organism preyOrganism = preyOrganisms.get(preyOrganismSelected);
                double huntSuccessRate = RandomGenerator.generateGaussian(predatorOrganism.calculateHuntSuccessRate(preySpecies, preyOrganism), 0.2);

                if (RandomGenerator.random.nextDouble() <= huntSuccessRate) {
                    huntingSuccess(predatorOrganism, preyOrganism);
                }
            }
        }
    }

    public void huntingSuccess(Organism predatorOrganism, Organism preyOrganism) {
        if (predatorOrganism.isImpersonatedOrganism()) {
            Log.logln(predatorOrganism.getSpeciesType() + " " + predatorOrganism.getGender() + " hunts a " + preyOrganism.getSpeciesType());
        }

        preyOrganism.setOrganismStatus(OrganismStatus.DEAD);
        preyOrganism.setOrganismDeathReason(OrganismDeathReason.PREDATION);

        if (preyOrganism.isImpersonatedOrganism()) {
            SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
            Log.logln(preyOrganism.getSpeciesType() + " " + preyOrganism.getGender() + " is hunted by a " + predatorOrganism.getSpeciesType());
        }

        double preyKgEaten = Math.max(preyOrganism.getVitalsAttributes().weight(), predatorOrganism.getHuntingAttributes().preyEaten());
        double baseEnergyGain = predatorOrganism.getHuntingAttributes().energyGain() * preyKgEaten;
        double energyGain = Math.min(baseEnergyGain, 1.0 - predatorOrganism.getEnergy());
        predatorOrganism.setEnergy(predatorOrganism.getEnergy() + energyGain);
    }

}
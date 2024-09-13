package Model.Simulation;

import Model.Entities.*;
import Model.Enums.*;
import Utils.Log;

public class AgingSimulation {

    public void age(Species species) {

        for (Organism organism : species.getAliveOrganisms()) {
            organism.setAge(organism.getAge() + (SimulationSettings.SIMULATION_SPEED_WEEKS / 52.0));

            if (organism.getAge() >= organism.getVitalsAttributes().lifeSpan()) {
                organismDeathByAge(organism);
            }

            else {

                if (organism.getGender() == Gender.FEMALE && organism.getReproductionStatus() == ReproductionStatus.MATURE && organism.getAge() >= organism.getReproductionAttributes().sexualMaturityEnd()) {
                    organismMenopause(organism);
                }

                if (organism.getReproductionStatus() == ReproductionStatus.NOT_MATURE && organism.getAge() >= organism.getReproductionAttributes().sexualMaturityStart()) {
                    sexualMaturation(organism);
                }

                // TODO: waiting to implement plants and nutrition for herbivores, assuming no energy loss for them
                if (organism.getDiet() != Diet.HERBIVORE) {
                    energyLoss(organism);
                }
            }

        }

    }

    public void sexualMaturation(Organism organism) {
        organism.setReproductionStatus(ReproductionStatus.MATURE);

        if (organism.isImpersonatedOrganism()) {
            Log.logln(organism.getSpeciesType() + " " + organism.getGender() + " enters sexual maturity");
        }
    }

    public void organismMenopause(Organism organism) {

        organism.setReproductionStatus(ReproductionStatus.MENOPAUSE);

        if (organism.isImpersonatedOrganism()) {
            Log.logln(organism.getSpeciesType() + " " + organism.getGender() + " enters menopause");
        }

    }

    public void energyLoss(Organism organism) {
        organism.setEnergy(organism.getEnergy() - organism.getHuntingAttributes().energyLost());

        if (organism.getEnergy() <= 0.0) {
            starve(organism);
        }
    }

    public void starve(Organism organism) {

        organism.setOrganismStatus(OrganismStatus.DEAD);
        organism.setOrganismDeathReason(OrganismDeathReason.STARVATION);

        if (organism.isImpersonatedOrganism()) {
            SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
            Log.logln(organism.getSpeciesType() + " " + organism.getGender() + " dies by starvation");
        }

    }

    public void organismDeathByAge(Organism organism) {

        organism.setOrganismStatus(OrganismStatus.DEAD);
        organism.setOrganismDeathReason(OrganismDeathReason.AGE);

        if (organism.isImpersonatedOrganism()) {
            SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
            Log.logln(organism.getSpeciesType() + " " + organism.getGender() + " dies by age");
        }

    }

}
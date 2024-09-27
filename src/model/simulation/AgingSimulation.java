package model.simulation;

import model.animals.Organism;
import model.animals.Species;
import model.enums.*;
import utils.Log;

public class AgingSimulation {

    public void speciesAge(Species species) {

        for (int i = 0; i < species.getOrganisms().size(); i++) {

            Organism organism = species.getOrganisms().get(i);

            organism.setAge(organism.getAge() + (SimulationSettings.SIMULATION_SPEED_WEEKS / 52.0));

            if (organism.getAge() >= organism.getOrganismAttributes().vitalsAttributes().lifeSpan()) {
                organismDeathByAge(organism);
            }

            else {

                if (organism.getReproductionStatus() == ReproductionStatus.MATURE && organism.getAge() >= organism.getOrganismAttributes().reproductionAttributes().sexualMaturityEnd() && organism.getGender() == Gender.FEMALE) {
                    organismMenopause(organism);
                }

                else if (organism.getReproductionStatus() == ReproductionStatus.NOT_MATURE && organism.getAge() >= organism.getOrganismAttributes().reproductionAttributes().sexualMaturityStart()) {
                    sexualMaturation(organism);
                }

                energyLoss(organism);

            }

        }

    }

    public void sexualMaturation(Organism organism) {
        organism.setReproductionStatus(ReproductionStatus.MATURE);

        if (organism.isImpersonatedOrganism()) {
            Log.log7(organism.getSpeciesType() + " " + organism.getGender() + " enters sexual maturity");
        }
    }

    public void organismMenopause(Organism organism) {

        organism.setReproductionStatus(ReproductionStatus.MENOPAUSE);

        if (organism.isImpersonatedOrganism()) {
            Log.log7(organism.getSpeciesType() + " " + organism.getGender() + " enters menopause");
        }

    }

    public void energyLoss(Organism organism) {
        organism.setEnergy(organism.getEnergy() - organism.getOrganismAttributes().vitalsAttributes().energyLoss());

        if (organism.getEnergy() <= 0.0) {
            starve(organism);
        }
    }

    public void starve(Organism organism) {

        organism.setOrganismStatus(OrganismStatus.DEAD);
        organism.setOrganismDeathReason(OrganismDeathReason.STARVATION);

        if (organism.isImpersonatedOrganism()) {
            SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
            Log.log7(organism.getSpeciesType() + " " + organism.getGender() + " dies by starvation");
        }

    }

    public void organismDeathByAge(Organism organism) {

        organism.setOrganismStatus(OrganismStatus.DEAD);
        organism.setOrganismDeathReason(OrganismDeathReason.AGE);

        if (organism.isImpersonatedOrganism()) {
            SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
            Log.log7(organism.getSpeciesType() + " " + organism.getGender() + " dies by age");
        }

    }

}
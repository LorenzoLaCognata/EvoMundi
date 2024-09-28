package model.simulation;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.animals.enums.AnimalOrganismDeathReason;
import model.environment.animals.enums.Gender;
import model.environment.animals.enums.ReproductionStatus;
import model.environment.base.OrganismStatus;
import utils.Log;

public class AgingSimulation {

    public void speciesAge(AnimalSpecies animalSpecies) {

        for (int i = 0; i < animalSpecies.getOrganisms().size(); i++) {

            AnimalOrganism animalOrganism = animalSpecies.getOrganisms().get(i);

            animalOrganism.setAge(animalOrganism.getAge() + (SimulationSettings.SIMULATION_SPEED_WEEKS / 52.0));

            if (animalOrganism.getAge() >= animalOrganism.getOrganismAttributes().vitalsAttributes().lifeSpan()) {
                organismDeathByAge(animalOrganism);
            }

            else {

                if (animalOrganism.getReproductionStatus() == ReproductionStatus.MATURE && animalOrganism.getAge() >= animalOrganism.getOrganismAttributes().reproductionAttributes().sexualMaturityEnd() && animalOrganism.getGender() == Gender.FEMALE) {
                    organismMenopause(animalOrganism);
                }

                else if (animalOrganism.getReproductionStatus() == ReproductionStatus.NOT_MATURE && animalOrganism.getAge() >= animalOrganism.getOrganismAttributes().reproductionAttributes().sexualMaturityStart()) {
                    sexualMaturation(animalOrganism);
                }

                energyLoss(animalOrganism);

            }

        }

    }

    public void sexualMaturation(AnimalOrganism animalOrganism) {
        animalOrganism.setReproductionStatus(ReproductionStatus.MATURE);

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log7(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " enters sexual maturity");
        }
    }

    public void organismMenopause(AnimalOrganism animalOrganism) {

        animalOrganism.setReproductionStatus(ReproductionStatus.MENOPAUSE);

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log7(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " enters menopause");
        }

    }

    public void energyLoss(AnimalOrganism animalOrganism) {
        animalOrganism.setEnergy(animalOrganism.getEnergy() - animalOrganism.getOrganismAttributes().vitalsAttributes().energyLoss());

        if (animalOrganism.getEnergy() <= 0.0) {
            starve(animalOrganism);
        }
    }

    public void starve(AnimalOrganism animalOrganism) {

        animalOrganism.setOrganismStatus(OrganismStatus.DEAD);
        animalOrganism.setOrganismDeathReason(AnimalOrganismDeathReason.STARVATION);

        if (animalOrganism.isImpersonatedOrganism()) {
            SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
            Log.log7(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " dies by starvation");
        }

    }

    public void organismDeathByAge(AnimalOrganism animalOrganism) {

        animalOrganism.setOrganismStatus(OrganismStatus.DEAD);
        animalOrganism.setOrganismDeathReason(AnimalOrganismDeathReason.AGE);

        if (animalOrganism.isImpersonatedOrganism()) {
            SimulationSettings.setSimulationStatus(SimulationStatus.PAUSED);
            Log.log7(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getGender() + " dies by age");
        }

    }

}
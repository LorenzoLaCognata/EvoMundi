package model.simulation.animals;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.animals.enums.AnimalOrganismDeathReason;
import model.environment.animals.enums.Gender;
import model.environment.animals.enums.ReproductionStatus;
import model.environment.common.base.Ecosystem;
import model.environment.common.enums.OrganismStatus;
import model.simulation.base.SimulationSettings;
import utils.Log;

public class AnimalAgingSimulation {

    public void animalAge(Ecosystem ecosystem) {
        ecosystem.iterateAnimalOrganismsBiConsumer(Ecosystem.animalTruePredicate, this::animalAgeOrganism);
    }

    private void animalAgeOrganism(AnimalOrganism animalOrganism, AnimalSpecies ignored) {
        animalOrganism.setAge(animalOrganism.getAge() + (SimulationSettings.SIMULATION_SPEED_WEEKS / 52.0));

        if (animalOrganism.getAge() >= animalOrganism.getOrganismAttributes().animalVitalsAttributes().lifeSpan()) {
            organismDeathByAge(animalOrganism);
        } else {

            if (animalOrganism.getReproductionStatus() == ReproductionStatus.MATURE && animalOrganism.getAge() >= animalOrganism.getOrganismAttributes().animalReproductionAttributes().sexualMaturityEnd() && animalOrganism.getGender() == Gender.FEMALE) {
                organismMenopause(animalOrganism);
            } else if (animalOrganism.getReproductionStatus() == ReproductionStatus.NOT_MATURE && animalOrganism.getAge() >= animalOrganism.getOrganismAttributes().animalReproductionAttributes().sexualMaturityStart()) {
                sexualMaturation(animalOrganism);
            }

            energyLoss(animalOrganism);

        }
    }

    public void sexualMaturation(AnimalOrganism animalOrganism) {
        animalOrganism.setReproductionStatus(ReproductionStatus.MATURE);

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " enters sexual maturity");
        }
    }

    public void organismMenopause(AnimalOrganism animalOrganism) {

        animalOrganism.setReproductionStatus(ReproductionStatus.MENOPAUSE);

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " enters menopause");
        }

    }

    public void energyLoss(AnimalOrganism animalOrganism) {
        animalOrganism.setEnergy(animalOrganism.getEnergy() - animalOrganism.getOrganismAttributes().animalVitalsAttributes().energyLoss());

        if (animalOrganism.getEnergy() <= 0.0) {
            starve(animalOrganism);
        }
    }

    public void starve(AnimalOrganism animalOrganism) {

        animalOrganism.setOrganismStatus(OrganismStatus.DEAD);
        animalOrganism.setOrganismDeathReason(AnimalOrganismDeathReason.STARVATION);

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " dies by starvation");
            animalOrganism.setImpersonatedOrganism(false);
        }

    }

    public void organismDeathByAge(AnimalOrganism animalOrganism) {

        animalOrganism.setOrganismStatus(OrganismStatus.DEAD);
        animalOrganism.setOrganismDeathReason(AnimalOrganismDeathReason.AGE);

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " dies by age");
            animalOrganism.setImpersonatedOrganism(false);
        }

    }

}
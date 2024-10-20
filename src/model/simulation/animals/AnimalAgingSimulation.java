package model.simulation.animals;

import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import model.environment.animals.enums.AnimalOrganismDeathReason;
import model.environment.animals.enums.Gender;
import model.environment.animals.enums.ReproductionStatus;
import model.environment.common.base.Ecosystem;
import model.environment.common.base.IterationManager;
import model.environment.common.enums.OrganismStatus;
import model.simulation.base.SimulationSettings;
import utils.Log;

// TODO: review energy loss for herbivores

public class AnimalAgingSimulation {

    public void ecosystemAge(Ecosystem ecosystem) {
        ecosystem.getIterationManager().iterateAnimalOrganismsBiConsumer(ecosystem, IterationManager.animalTruePredicate, this::animalOrganismAge);
    }

    private void animalOrganismAge(AnimalOrganism animalOrganism, AnimalSpecies ignored) {
        animalOrganism.setAge(animalOrganism.getAge() + (SimulationSettings.SIMULATION_SPEED_WEEKS / 52.0));

        if (animalOrganism.getAge() >= animalOrganism.getOrganismAttributes().animalVitalsAttributes().lifeSpan()) {
            animalOrganismDeathByAge(animalOrganism);
        } else {

            if (animalOrganism.getReproductionStatus() == ReproductionStatus.MATURE && animalOrganism.getAge() >= animalOrganism.getOrganismAttributes().animalReproductionAttributes().sexualMaturityEnd() && animalOrganism.getGender() == Gender.FEMALE) {
                animalOrganismMenopause(animalOrganism);
            } else if (animalOrganism.getReproductionStatus() == ReproductionStatus.NOT_MATURE && animalOrganism.getAge() >= animalOrganism.getOrganismAttributes().animalReproductionAttributes().sexualMaturityStart()) {
                animalOrganismSexualMaturation(animalOrganism);
            }

            animalOrganismEnergyLoss(animalOrganism);

        }
    }

    public void animalOrganismSexualMaturation(AnimalOrganism animalOrganism) {
        animalOrganism.setReproductionStatus(ReproductionStatus.MATURE);

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " enters sexual maturity");
        }
    }

    public void animalOrganismMenopause(AnimalOrganism animalOrganism) {

        animalOrganism.setReproductionStatus(ReproductionStatus.MENOPAUSE);

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " enters menopause");
        }

    }

    public void animalOrganismEnergyLoss(AnimalOrganism animalOrganism) {
        animalOrganism.setEnergy(animalOrganism.getEnergy() - animalOrganism.getOrganismAttributes().animalVitalsAttributes().energyLoss());

        if (animalOrganism.getEnergy() <= 0.0) {
            animalOrganismStarvation(animalOrganism);
        }
    }

    public void animalOrganismStarvation(AnimalOrganism animalOrganism) {

        animalOrganism.setOrganismStatus(OrganismStatus.DEAD);
        animalOrganism.setOrganismDeathReason(AnimalOrganismDeathReason.STARVATION);

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " dies by starvation");
            animalOrganism.setImpersonatedOrganism(false);
        }

    }

    public void animalOrganismDeathByAge(AnimalOrganism animalOrganism) {

        animalOrganism.setOrganismStatus(OrganismStatus.DEAD);
        animalOrganism.setOrganismDeathReason(AnimalOrganismDeathReason.AGE);

        if (animalOrganism.isImpersonatedOrganism()) {
            Log.log6(animalOrganism.getAnimalSpecies() + " " + animalOrganism.getId() + " dies by age");
            animalOrganism.setImpersonatedOrganism(false);
        }

    }

}
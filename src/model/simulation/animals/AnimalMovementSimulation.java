package model.simulation.animals;

import main.View;
import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import utils.RandomGenerator;

public class AnimalMovementSimulation {

    public static final int STEPS_PER_DIRECTION = 25;

    public void speciesMove(AnimalSpecies animalSpecies) {

        for (int i = 0; i < animalSpecies.getOrganisms().size(); i++) {

            AnimalOrganism animalOrganism = animalSpecies.getOrganisms().get(i);

            if (animalOrganism.getOrganismAttributes().animalPositionAttributes().getDirectionSteps() % STEPS_PER_DIRECTION == 0) {
                animalOrganism.getOrganismAttributes().animalPositionAttributes().setSpeedX(RandomGenerator.random.nextDouble(-1.0, 1.0));
                animalOrganism.getOrganismAttributes().animalPositionAttributes().setSpeedY(RandomGenerator.random.nextDouble(-1.0, 1.0));
            }

            double newPosX = Math.clamp(animalOrganism.getOrganismAttributes().animalPositionAttributes().getPosX() + animalOrganism.getOrganismAttributes().animalPositionAttributes().getSpeedX(), 0.0, View.SCENE_WIDTH);
            double newPosY = Math.clamp(animalOrganism.getOrganismAttributes().animalPositionAttributes().getPosY() + animalOrganism.getOrganismAttributes().animalPositionAttributes().getSpeedY(), 0.0, View.SCENE_HEIGHT);

            animalOrganism.getOrganismAttributes().animalPositionAttributes().setPosX(newPosX);
            animalOrganism.getOrganismAttributes().animalPositionAttributes().setPosY(newPosY);

            animalOrganism.getOrganismIcons().getStackPane().setLayoutX(newPosX);
            animalOrganism.getOrganismIcons().getStackPane().setLayoutY(newPosY);

            animalOrganism.getOrganismAttributes().animalPositionAttributes().setDirectionSteps(animalOrganism.getOrganismAttributes().animalPositionAttributes().getDirectionSteps() + 1);
        }

    }

}
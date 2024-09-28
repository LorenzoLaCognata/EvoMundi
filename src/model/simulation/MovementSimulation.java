package model.simulation;

import main.View;
import model.environment.animals.base.AnimalOrganism;
import model.environment.animals.base.AnimalSpecies;
import utils.RandomGenerator;

public class MovementSimulation {

    public static final int STEPS_PER_DIRECTION = 25;

    public void speciesMove(AnimalSpecies animalSpecies) {

        for (int i = 0; i < animalSpecies.getOrganisms().size(); i++) {

            AnimalOrganism animalOrganism = animalSpecies.getOrganisms().get(i);

            if (animalOrganism.getOrganismAttributes().movementAttributes().getDirectionSteps() % STEPS_PER_DIRECTION == 0) {
                animalOrganism.getOrganismAttributes().movementAttributes().setSpeedX(RandomGenerator.random.nextDouble(-1.0, 1.0));
                animalOrganism.getOrganismAttributes().movementAttributes().setSpeedY(RandomGenerator.random.nextDouble(-1.0, 1.0));
            }

            double newPosX = Math.clamp(animalOrganism.getOrganismAttributes().movementAttributes().getPosX() + animalOrganism.getOrganismAttributes().movementAttributes().getSpeedX(), 0.0, View.SCENE_WIDTH);
            double newPosY = Math.clamp(animalOrganism.getOrganismAttributes().movementAttributes().getPosY() + animalOrganism.getOrganismAttributes().movementAttributes().getSpeedY(), 0.0, View.SCENE_HEIGHT);

            animalOrganism.getOrganismAttributes().movementAttributes().setPosX(newPosX);
            animalOrganism.getOrganismAttributes().movementAttributes().setPosY(newPosY);

            animalOrganism.getImageView().setX(newPosX);
            animalOrganism.getImageView().setY(newPosY);

            animalOrganism.getOrganismAttributes().movementAttributes().setDirectionSteps(animalOrganism.getOrganismAttributes().movementAttributes().getDirectionSteps() + 1);
        }

    }

}
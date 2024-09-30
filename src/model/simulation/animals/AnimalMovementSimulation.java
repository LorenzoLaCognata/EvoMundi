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

            if (animalOrganism.getOrganismAttributes().animalMovementAttributes().getDirectionSteps() % STEPS_PER_DIRECTION == 0) {
                animalOrganism.getOrganismAttributes().animalMovementAttributes().setSpeedX(RandomGenerator.random.nextDouble(-1.0, 1.0));
                animalOrganism.getOrganismAttributes().animalMovementAttributes().setSpeedY(RandomGenerator.random.nextDouble(-1.0, 1.0));
            }

            double newPosX = Math.clamp(animalOrganism.getOrganismAttributes().animalMovementAttributes().getPosX() + animalOrganism.getOrganismAttributes().animalMovementAttributes().getSpeedX(), 0.0, View.SCENE_WIDTH);
            double newPosY = Math.clamp(animalOrganism.getOrganismAttributes().animalMovementAttributes().getPosY() + animalOrganism.getOrganismAttributes().animalMovementAttributes().getSpeedY(), 0.0, View.SCENE_HEIGHT);

            animalOrganism.getOrganismAttributes().animalMovementAttributes().setPosX(newPosX);
            animalOrganism.getOrganismAttributes().animalMovementAttributes().setPosY(newPosY);

            animalOrganism.getImageView().setX(newPosX);
            animalOrganism.getImageView().setY(newPosY);

            animalOrganism.getOrganismAttributes().animalMovementAttributes().setDirectionSteps(animalOrganism.getOrganismAttributes().animalMovementAttributes().getDirectionSteps() + 1);
        }

    }

}
package model.simulation;

import main.View;
import model.animals.Organism;
import model.animals.Species;
import utils.RandomGenerator;

public class MovementSimulation {

    public static final int STEPS_PER_DIRECTION = 25;

    public void speciesMove(Species species) {

        for (int i = 0; i < species.getOrganisms().size(); i++) {

            Organism organism = species.getOrganisms().get(i);

            if (organism.getOrganismAttributes().movementAttributes().getDirectionSteps() % STEPS_PER_DIRECTION == 0) {
                organism.getOrganismAttributes().movementAttributes().setSpeedX(RandomGenerator.random.nextDouble(-1.0, 1.0));
                organism.getOrganismAttributes().movementAttributes().setSpeedY(RandomGenerator.random.nextDouble(-1.0, 1.0));
            }

            double newPosX = Math.clamp(organism.getOrganismAttributes().movementAttributes().getPosX() + organism.getOrganismAttributes().movementAttributes().getSpeedX(), 0.0, View.SCENE_WIDTH);
            double newPosY = Math.clamp(organism.getOrganismAttributes().movementAttributes().getPosY() + organism.getOrganismAttributes().movementAttributes().getSpeedY(), 0.0, View.SCENE_HEIGHT);

            organism.getOrganismAttributes().movementAttributes().setPosX(newPosX);
            organism.getOrganismAttributes().movementAttributes().setPosY(newPosY);

            organism.getImageView().setX(newPosX);
            organism.getImageView().setY(newPosY);

            organism.getOrganismAttributes().movementAttributes().setDirectionSteps(organism.getOrganismAttributes().movementAttributes().getDirectionSteps() + 1);
        }

    }

}
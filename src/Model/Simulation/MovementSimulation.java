package Model.Simulation;

import Main.View;
import Model.Animals.Organism;
import Model.Animals.Species;
import Utils.RandomGenerator;

public class MovementSimulation {

    public static final int STEPS_PER_DIRECTION = 25;

    public void speciesMove(Species species) {

        for (int i = 0; i < species.getOrganisms().size(); i++) {

            Organism organism = species.getOrganisms().get(i);

            if (organism.getMovementAttributes().getDirectionSteps() % STEPS_PER_DIRECTION == 0) {
                organism.getMovementAttributes().setSpeedX(RandomGenerator.random.nextDouble(-1.0, 1.0));
                organism.getMovementAttributes().setSpeedY(RandomGenerator.random.nextDouble(-1.0, 1.0));
            }

            double newPosX = Math.clamp(organism.getMovementAttributes().getPosX() + organism.getMovementAttributes().getSpeedX(), 0.0, View.SCENE_WIDTH);
            double newPosY = Math.clamp(organism.getMovementAttributes().getPosY() + organism.getMovementAttributes().getSpeedY(), 0.0, View.SCENE_HEIGHT);

            organism.getMovementAttributes().setPosX(newPosX);
            organism.getMovementAttributes().setPosY(newPosY);

            organism.getImageView().setX(newPosX);
            organism.getImageView().setY(newPosY);

            organism.getMovementAttributes().setDirectionSteps(organism.getMovementAttributes().getDirectionSteps() + 1);
        }

    }

}
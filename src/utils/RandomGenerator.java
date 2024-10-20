package utils;

import model.environment.animals.enums.Gender;

import java.util.Random;

public class RandomGenerator {

    public static final double GAUSSIAN_VARIANCE = 0.2;

    public static final Random random = new Random();

    private RandomGenerator() {
    }

    public static double generateGaussian(double average, double variance) {
        double gaussian = RandomGenerator.random.nextGaussian();
        return average + (gaussian * average * variance);
    }

    public static double generateGaussian(double male, double female, double variance) {
        double average = (female + male) / 2.0;
        return generateGaussian(average, variance);
    }

    public static double generateGaussian(Gender gender, double male, double female, double variance) {
        if (gender == Gender.FEMALE) {
            return generateGaussian(female, variance);
        }
        else {
            return generateGaussian(male, variance);
        }
    }

}
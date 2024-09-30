package model.environment.animals.enums;

public enum AnimalAttribute {
    CARRYING_CAPACITY, // Per 1000 Km2
    LIFESPAN, // In Years
    WEIGHT, // In Kilograms
    HEIGHT, // In Meters

    HUNT_ATTEMPTS, // Per Week
    ENERGY_LOSS, // Per Week
    ENERGY_GAIN, // Per Kilogram of Prey hunted or Kilogram of Plant eaten
    PREY_EATEN, // Per Kilogram of Prey per Week
    PLANT_CONSUMPTION_RATE, // Constant Number

    SEXUAL_MATURITY_START, // In Years
    SEXUAL_MATURITY_END, // In Years
    MATING_SEASON_START, // Week Number
    MATING_SEASON_END, // Week Number
    PREGNANCY_COOLDOWN, // In Weeks
    GESTATION_PERIOD, // In Weeks
    AVERAGE_OFFSPRING, // Per Birth
    JUVENILE_SURVIVAL_RATE, // Per Birth
    MATING_SUCCESS_RATE, // Per Attempt
    MATING_ATTEMPTS // Per Week
}
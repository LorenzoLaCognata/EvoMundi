package model.environment.animals.attributes;

public record AnimalReproductionAttributes(double sexualMaturityStart, double sexualMaturityEnd, double matingSeasonStart,
                                           double matingSeasonEnd, double matingCooldown, double gestationPeriod,
                                           double averageOffspring, double juvenileSurvivalRate, double matingSuccessRate,
                                           double matingAttempts) {

}
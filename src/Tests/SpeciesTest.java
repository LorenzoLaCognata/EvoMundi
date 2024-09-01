import static org.junit.jupiter.api.Assertions.*;

import Entities.Species;
import Enums.Diet;
import Enums.SpeciesType;

class SpeciesTest {

    Species species = new Species(SpeciesType.WHITE_TAILED_DEER, "White-Tailed Deer", "Odocoileus virginianus", "Mammalia", "Artiodactyla", "Cervidae", "Odocoileus", Diet.HERBIVORE);

    @org.junit.jupiter.api.Test
    void getSpeciesType() {
        assertEquals("WHITE_TAILED_DEER", species.getSpeciesType().toString());
    }

    @org.junit.jupiter.api.Test
    void getCommonName() {
        assertEquals("White-Tailed Deer", species.getCommonName());
    }

    @org.junit.jupiter.api.Test
    void getScientificName() {
        assertEquals("Odocoileus virginianus", species.getScientificName());
    }

    @org.junit.jupiter.api.Test
    void getTaxonomyClass() {
    }

    @org.junit.jupiter.api.Test
    void getTaxonomyOrder() {
    }

    @org.junit.jupiter.api.Test
    void getTaxonomyFamily() {
    }

    @org.junit.jupiter.api.Test
    void getTaxonomyGenus() {
    }

    @org.junit.jupiter.api.Test
    void getBaseDiet() {
    }

    @org.junit.jupiter.api.Test
    void getAttributes() {
    }

    @org.junit.jupiter.api.Test
    void getAttribute() {
    }

    @org.junit.jupiter.api.Test
    void getBasePreySpecies() {
    }

    @org.junit.jupiter.api.Test
    void getOrganisms() {
    }

    @org.junit.jupiter.api.Test
    void testGetOrganisms() {
    }

    @org.junit.jupiter.api.Test
    void getAliveOrganisms() {
    }

    @org.junit.jupiter.api.Test
    void getPopulation() {
    }

    @org.junit.jupiter.api.Test
    void testGetPopulation() {
    }

    @org.junit.jupiter.api.Test
    void testGetPopulation1() {
    }

    @org.junit.jupiter.api.Test
    void testGetPopulation2() {
    }

    @org.junit.jupiter.api.Test
    void getAverageAge() {
    }

    @org.junit.jupiter.api.Test
    void getAverageEnergy() {
    }

    @org.junit.jupiter.api.Test
    void addAttribute() {
    }

    @org.junit.jupiter.api.Test
    void addBasePreySpecies() {
    }

    @org.junit.jupiter.api.Test
    void removeBasePreySpecies() {
    }

    @org.junit.jupiter.api.Test
    void testToString() {
    }

    @org.junit.jupiter.api.Test
    void initializeOrganisms() {
    }
}
package Tests;

import Model.Entities.*;
import Model.Enums.Diet;
import Model.Enums.SpeciesType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpeciesTest {

    TaxonomyClass mammalia = new TaxonomyClass("Mammalia");
    TaxonomyOrder artiodactyla = new TaxonomyOrder("Artiodactyla");
    TaxonomyFamily cervidae = new TaxonomyFamily("Cervidae");
    TaxonomyGenus odocoileus = new TaxonomyGenus("Odocoileus");
    SpeciesTaxonomy odocoileusTaxonomy = new SpeciesTaxonomy(mammalia, artiodactyla, cervidae, odocoileus);
    Species species = new Species(odocoileusTaxonomy, SpeciesType.WHITE_TAILED_DEER, "White-Tailed Deer", "Odocoileus virginianus", Diet.HERBIVORE);

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

}
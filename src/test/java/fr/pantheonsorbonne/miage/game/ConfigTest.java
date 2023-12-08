package fr.pantheonsorbonne.miage.game;
import fr.pantheonsorbonne.miage.game.setting.Config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {

    @Test
    public void testGetBoatsConfigValidId() {
        // Test pour un ID valide
        String[] expected = {"1","Porte-avions", "5","false"};
        assertArrayEquals(expected, Config.getBoatsConfig(0), "La configuration pour l'ID 0 doit correspondre au Porte-avions");
    }

    @Test
    public void testGetBoatsConfigInvalidId() {
        // Test pour un ID invalide
        assertArrayEquals(new String[0], Config.getBoatsConfig(-1), "Un ID invalide doit retourner un tableau vide");
        assertArrayEquals(new String[0], Config.getBoatsConfig(7), "Un ID invalide doit retourner un tableau vide");
    }

    @Test
    public void testGetBoatsConfigArray() {
        // Test pour la configuration complète des bateaux
        String[][] boatsConfig = Config.getBoatsConfig();
        assertNotNull(boatsConfig, "La configuration des bateaux ne doit pas être null");
        assertEquals(7, boatsConfig.length, "Il doit y avoir 7 configurations de bateaux");
    }

    @Test
    public void testGetNbBoats() {
        // Test pour le nombre de configurations de bateaux
        assertEquals(7, Config.getNbBoats(), "Le nombre de bateaux doit être 7");
    }
}

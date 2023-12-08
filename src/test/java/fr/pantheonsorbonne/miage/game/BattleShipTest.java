package fr.pantheonsorbonne.miage.game;
import fr.pantheonsorbonne.miage.game.playerBots.*;
import fr.pantheonsorbonne.miage.game.setting.*;
import fr.pantheonsorbonne.miage.game.helpers.*;
import fr.pantheonsorbonne.miage.game.game.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BattleShipTest {

    @Test
    void testBoatPlacement() {

        // Vérifiez que les bateaux sont bien existant
        assertTrue(Config.getNbBoats() == 5);
    }

    @Test
    void testGetBoatsConfigValidId() {
        String[] expected = {"1", "Porte-avions", "5"};
        assertArrayEquals(expected, Config.getBoatsConfig(0), "Le bateau avec l'ID 0 devrait être le Porte-avions");
    }

    @Test
    void testGetBoatsConfigInvalidIdNegative() {
        String[] expected = new String[0];
        assertArrayEquals(expected, Config.getBoatsConfig(-1), "Un ID négatif devrait renvoyer un tableau vide");
    }

    @Test
    void testGetBoatsConfigInvalidIdTooHigh() {
        String[] expected = new String[0];
        assertArrayEquals(expected, Config.getBoatsConfig(5), "Un ID trop élevé devrait renvoyer un tableau vide");
    }

    @Test
    void testGetBoatsConfig() {
        String[][] expected = {
            {"1", "Porte-avions", "5"},
            {"2", "Croiseur", "4"},
            {"3", "Contre-torpilleurs", "3"},
            {"4", "Contre-torpilleurs", "3"},
            {"5", "Torpilleur", "2"}
        };
        assertArrayEquals(expected, Config.getBoatsConfig(), "La méthode devrait renvoyer la configuration de tous les bateaux");
    }

    @Test
    void testGetNbBoats() {
        assertEquals(5, Config.getNbBoats(), "La méthode devrait renvoyer le nombre correct de bateaux");
    }

}

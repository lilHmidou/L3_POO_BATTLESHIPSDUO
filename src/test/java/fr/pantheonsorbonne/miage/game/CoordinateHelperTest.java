package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.game.helpers.CoordinateHelper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoordinateHelperTest {

    @Test
    public void testLetterCoordinateToNumber() {
        assertEquals("0", CoordinateHelper.letterCoordinateToNumber("A"));
        assertEquals("1", CoordinateHelper.letterCoordinateToNumber("B"));
        // ... Tests pour les autres lettres
    }

    @Test
    public void testNumberCoordinateToLetter() {
        assertEquals("A", CoordinateHelper.numberCoordinateToLetter(0));
        assertEquals("B", CoordinateHelper.numberCoordinateToLetter(1));
        // ... Tests pour les autres numéros
    }

    @Test
    public void testIsValid() {
        assertTrue(CoordinateHelper.isValid(5, 5));
        assertFalse(CoordinateHelper.isValid(-1, 5));
        assertFalse(CoordinateHelper.isValid(5, -1));
        assertFalse(CoordinateHelper.isValid(10, 5));
        assertFalse(CoordinateHelper.isValid(5, 10));
    }
}


package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.game.setting.Cell;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    @Test
    public void testCellInitialization() {
        Cell cell = new Cell(5, 10);
        assertEquals(5, cell.getX());
        assertEquals(10, cell.getY());
        assertFalse(cell.isShot());
        assertTrue(cell.isPotential());
    }

    @Test
    public void testShoot() {
        Cell cell = new Cell(5, 10);
        cell.shoot();
        assertTrue(cell.isShot());
    }

    @Test
    public void testAddBoat() {
        Cell cell = new Cell(5, 10);
        cell.addBoat(1);
        assertEquals(1, cell.getId());
    }

    @Test
    public void testUpdatePotential() {
        Cell cell = new Cell(5, 10);
        cell.updatePotential(false);
        assertFalse(cell.isPotential());
    }

    @Test
    public void testCellBoatAssignment() {
        Cell cell = new Cell(5, 10);
        cell.addBoat(1); // ID du bateau
        assertEquals(1, cell.getId());
        // Tester la cohérence avec la configuration du bateau
    }
}

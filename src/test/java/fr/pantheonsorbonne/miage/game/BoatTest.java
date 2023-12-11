package fr.pantheonsorbonne.miage.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import fr.pantheonsorbonne.miage.game.setting.Boat;
import fr.pantheonsorbonne.miage.game.setting.Cell;
import fr.pantheonsorbonne.miage.game.setting.Config;

public class BoatTest {

    @Test
    public void testBoatInitialization() {
        Cell[] cells = { new Cell(1, 1), new Cell(1, 2) };
        String[] boatConfig = Config.getBoatsConfig(0);
        Boat boat = new Boat(cells, Integer.parseInt(boatConfig[0]), boatConfig[1],
                Boolean.parseBoolean(boatConfig[3]));

        assertEquals(Integer.parseInt(boatConfig[0]), boat.getId(),
                "L'ID du bateau doit correspondre à la configuration");
        assertEquals(boatConfig[1], boat.getName(), "Le nom du bateau doit correspondre à la configuration");
        assertEquals(cells.length, boat.getSize(), "La taille du bateau doit correspondre au nombre de cellules");
        assertEquals(Boolean.parseBoolean(boatConfig[3]), boat.isProtected(),
                "Le statut de protection du bateau doit correspondre à la configuration");
    }

    @Test
    public void testIsSunk() {
        Cell[] cells = { new Cell(1, 1), new Cell(1, 2) };
        Boat boat = new Boat(cells, 1, "Destroyer", true);
        assertFalse(boat.isSunk());
        cells[0].shoot();
        cells[1].shoot();
        assertTrue(boat.isSunk());
    }

    @Test
    public void testDesactivateDefenseSystem() {
        Cell[] cells = { new Cell(1, 1), new Cell(1, 2) };
        Boat boat = new Boat(cells, 1, "Destroyer", true);
        boat.desactivateDefenseSystem();
        assertFalse(boat.isProtected());
    }

    @Test
    public void testGetCells() {
        Cell[] cells = { new Cell(1, 1), new Cell(1, 2) };
        Boat boat = new Boat(cells, 1, "Destroyer", true);
        assertArrayEquals(cells, boat.getCells());
    }

    @Test
    public void testBoatWithConfig() {
        String[] boatConfig = Config.getBoatsConfig(0);
        Cell[] cells = {};
        Boat boat = new Boat(cells, Integer.parseInt(boatConfig[0]), boatConfig[1],
                Boolean.parseBoolean(boatConfig[3]));
        assertEquals(boatConfig[1], boat.getName());
        assertEquals(cells.length, boat.getSize());

    }

    @Test

    public void testGetCellByCoordinates() {
        Cell[] cells = { new Cell(1, 1), new Cell(1, 2) };
        Boat boat = new Boat(cells, 1, "Destroyer", true);

        Cell cell = boat.getCells(1, 1);
        assertEquals(cells[0], cell, "Doit retourner la première cellule pour les coordonnées (1,1)");

        cell = boat.getCells(1, 2);
        assertEquals(cells[1], cell, "Doit retourner la deuxième cellule pour les coordonnées (1,2)");
    }

}

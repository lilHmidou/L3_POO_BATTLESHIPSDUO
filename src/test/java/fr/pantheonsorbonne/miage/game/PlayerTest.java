package fr.pantheonsorbonne.miage.game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.playerBots.Player;
import fr.pantheonsorbonne.miage.game.setting.Boat;
import fr.pantheonsorbonne.miage.game.setting.Cell;
import fr.pantheonsorbonne.miage.game.helpers.CoordinateHelper;

public class PlayerTest {

    @Test
    public void testPlayerConstructor() {
        Player player = new Player();
        assertNotNull(player);

    }

    @Test
    public void testPlaceBoats() {
        Player player = new Player();
        player.placeBoats();

    }

    @Test
    public void testUseRadar() {
        TestablePlayer player = new TestablePlayer();
        Player enemy = new Player();
        enemy.placeBoats();

        player.publicUseRadar(enemy, 5, 5);

        assertTrue(player.hasUsedRadar());

    }

    @Test
    public void testUseNuclearBomb() {
        TestablePlayer player = new TestablePlayer();
        Player enemy = new Player();
        enemy.placeBoats();

        int targetX = 3;
        int targetY = 3;
        player.publicUseNuclearBomb(enemy, targetX, targetY);

        assertTrue(player.hasUsedNuclearBomb());

        int radius = 1;
        int startX = Math.max(0, targetX - radius);
        int startY = Math.max(0, targetY - radius);
        int endX = Math.min(enemy.getBoard().getCells().length - 1, targetX + radius);
        int endY = Math.min(enemy.getBoard().getCells()[0].length - 1, targetY + radius);

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                Cell targetCell = enemy.getBoard().getCells()[i][j];
                Boat boat = enemy.getBoard().getBoats(targetCell.getId());

                if (boat != null && boat.isProtected()) {

                    assertFalse(boat.isProtected(),
                            "Le bouclier de la cellule (" + i + ", " + j + ") devrait être désactivé");
                } else if (targetCell.isShot()) {

                    assertTrue(targetCell.isShot(),
                            "La cellule (" + i + ", " + j + ") devrait être touchée par la bombe nucléaire");
                }
            }
        }
    }

    @Test
    public void testUseAirStrike() {
        TestablePlayer player = new TestablePlayer();
        Player enemy = new Player();
        enemy.placeBoats();

        boolean isLine = true;
        int target = 2;
        player.publicUseAirStrike(enemy, isLine, target);

        assertTrue(player.hasUsedAirStrike());

        for (int i = 0; i < 10; i++) {
            Cell targetCell = isLine ? enemy.getBoard().getCells()[target][i] : enemy.getBoard().getCells()[i][target];
            Boat boat = enemy.getBoard().getBoats(targetCell.getId());
            if (boat != null && boat.isProtected()) {
                assertFalse(boat.isProtected(), "Le bouclier de la cellule (" + (isLine ? target : i) + ", "
                        + (isLine ? i : target) + ") devrait être désactivé");
            } else if (targetCell.isShot()) {
                assertTrue(targetCell.isShot(), "La cellule (" + (isLine ? target : i) + ", " + (isLine ? i : target)
                        + ") devrait être touchée par la frappe aérienne");
            }
        }
    }

    @Test
    public void testUseBurstFire() {
        TestablePlayer player = new TestablePlayer();
        Player enemy = new Player();
        enemy.placeBoats();

        List<Cell> targetCells = new ArrayList<>();
        targetCells.add(new Cell(2, 3));
        targetCells.add(new Cell(4, 5));
        targetCells.add(new Cell(6, 7));

        player.publicUseBurstFire(enemy, targetCells);

        assertTrue(player.hasUsedBurstFire());

        for (Cell targetCell : targetCells) {
            Boat boat = enemy.getBoard().getBoats(targetCell.getId());
            if (boat != null && boat.isProtected()) {
                assertFalse(boat.isProtected(), "Le bouclier de la cellule (" + targetCell.getX() + ", "
                        + targetCell.getY() + ") devrait être désactivé");
            } else if (targetCell.isShot()) {
                assertTrue(targetCell.isShot(), "La cellule (" + targetCell.getX() + ", " + targetCell.getY()
                        + ") devrait être touchée par le tir en rafale");
            }
        }
    }

    @Test
    public void testUseSubmarine() {
        TestablePlayer player = new TestablePlayer();
        Player enemy = new Player();
        enemy.placeBoats();
        player.publicUseSubmarine(enemy, true, 4);

        assertTrue(player.hasUsedSubmarine());

    }

    @Test
    public void testUpdateNonPotentialCells() {
        TestablePlayer player = new TestablePlayer();
        player.placeBoats();
        List<Cell> sunkBoatCells = new ArrayList<>();

        sunkBoatCells.add(new Cell(3, 3));
        sunkBoatCells.add(new Cell(3, 4));
        sunkBoatCells.add(new Cell(3, 5));

        player.publicUpdateNonPotentialCells(sunkBoatCells, player);

        boolean vertical = sunkBoatCells.get(0).getX() == sunkBoatCells.get(1).getX();
        int[] tempCoordinates = { -1, 1 };

        for (Cell c : sunkBoatCells) {
            for (int j : tempCoordinates) {
                int x = c.getX() + (vertical ? 0 : j);
                int y = c.getY() + (vertical ? j : 0);
                if (CoordinateHelper.isValid(x, y)) {
                    Cell adjacentCell = player.getBoard().getCell(x, y);

                    assertFalse(adjacentCell.isPotential(), "La cellule (" + x + ", " + y
                            + ") ne devrait pas être potentiel après avoir coulé un bateau");
                }
            }
        }
    }

}

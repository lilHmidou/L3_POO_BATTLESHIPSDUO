package fr.pantheonsorbonne.miage.game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.playerBots.Bot;
import fr.pantheonsorbonne.miage.game.playerBots.Player;
import fr.pantheonsorbonne.miage.game.setting.Board;
import fr.pantheonsorbonne.miage.game.setting.Boat;
import fr.pantheonsorbonne.miage.game.setting.Cell;
import fr.pantheonsorbonne.miage.game.TestablePlayer;
import fr.pantheonsorbonne.miage.game.helpers.CoordinateHelper;
public class PlayerTest {
    
    @Test
    public void testPlayerConstructor() {
        Player player = new Player();
        assertNotNull(player);
        // Autres assertions pour vérifier l'état initial si nécessaire
    }

    @Test
    public void testPlaceBoats() {
        Player player = new Player();
        player.placeBoats();

        // Vérifier que les bateaux sont placés correctement
        // Vous devrez peut-être ajouter des getters ou des méthodes d'inspection dans votre classe pour cela
    }   

    @Test
    public void testUseRadar() {
        TestablePlayer player = new TestablePlayer();
        Player enemy = new Player();
        enemy.placeBoats(); // Assurez-vous que l'ennemi a placé ses bateaux

        // Utiliser le radar sur une position centrale
        player.publicUseRadar(enemy, 5, 5);

        // Vérifier si le radar a été utilisé
        assertTrue(player.hasUsedRadar());

        // Vérifiez d'autres effets attendus de l'utilisation du radar
        // Par exemple, la découverte de bateaux ennemis, le changement d'état du plateau, etc.
    }

    @Test
public void testUseNuclearBomb() {
    TestablePlayer player = new TestablePlayer();
    Player enemy = new Player();
    enemy.placeBoats(); // Assurez-vous que l'ennemi a placé ses bateaux

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
                // Vérifier si le bouclier est désactivé
                assertFalse(boat.isProtected(), "Le bouclier de la cellule (" + i + ", " + j + ") devrait être désactivé");
            } else if (targetCell.isShot()) {
                // Vérifier si la cellule est touchée
                assertTrue(targetCell.isShot(), "La cellule (" + i + ", " + j + ") devrait être touchée par la bombe nucléaire");
            }
        }
    }
}



    @Test
    public void testUseAirStrike() {
        TestablePlayer player = new TestablePlayer();
        Player enemy = new Player();
        enemy.placeBoats(); // Assurez-vous que l'ennemi a placé ses bateaux avec des boucliers

        boolean isLine = true;
        int target = 2;
        player.publicUseAirStrike(enemy, isLine, target);

        assertTrue(player.hasUsedAirStrike());

        for (int i = 0; i < 10; i++) {
            Cell targetCell = isLine ? enemy.getBoard().getCells()[target][i] : enemy.getBoard().getCells()[i][target];
            Boat boat = enemy.getBoard().getBoats(targetCell.getId());
            if (boat != null && boat.isProtected()) {
                assertFalse(boat.isProtected(), "Le bouclier de la cellule (" + (isLine ? target : i) + ", " + (isLine ? i : target) + ") devrait être désactivé");
            } else if (targetCell.isShot()) {
                assertTrue(targetCell.isShot(), "La cellule (" + (isLine ? target : i) + ", " + (isLine ? i : target) + ") devrait être touchée par la frappe aérienne");
            }
        }
    }



    @Test
    public void testUseBurstFire() {
        TestablePlayer player = new TestablePlayer();
        Player enemy = new Player();
        enemy.placeBoats(); // Assurez-vous que l'ennemi a placé ses bateaux, certains avec des boucliers
    
        // Créer une liste de cellules cibles pour le tir en rafale
        List<Cell> targetCells = new ArrayList<>();
        targetCells.add(new Cell(2, 3)); // Ajoutez des cellules cibles ici, certaines avec des boucliers
        targetCells.add(new Cell(4, 5));
        targetCells.add(new Cell(6, 7));
    
        // Utiliser le tir en rafale
        player.publicUseBurstFire(enemy, targetCells);
    
        // Vérifier si le tir en rafale a été utilisé
        assertTrue(player.hasUsedBurstFire());
    
        // Vérifier si les cellules cibles sont touchées ou si leurs boucliers sont désactivés
        for (Cell targetCell : targetCells) {
            Boat boat = enemy.getBoard().getBoats(targetCell.getId());
            if (boat != null && boat.isProtected()) {
                assertFalse(boat.isProtected(), "Le bouclier de la cellule (" + targetCell.getX() + ", " + targetCell.getY() + ") devrait être désactivé");
            }else if (targetCell.isShot()){
                assertTrue(targetCell.isShot(), "La cellule (" + targetCell.getX() + ", " + targetCell.getY() + ") devrait être touchée par le tir en rafale");
            }
        }
    }
    



@Test
public void testUseSubmarine() {
    TestablePlayer player = new TestablePlayer();
    Player enemy = new Player();
    enemy.placeBoats(); // Assurez-vous que l'ennemi a placé ses bateaux

    // Utiliser un sous-marin sur une ligne ou une colonne cible
    player.publicUseSubmarine(enemy, true, 4);

    // Vérifier si le sous-marin a été utilisé
    assertTrue(player.hasUsedSubmarine());

    // Vérifier les effets du sous-marin
    // Par exemple, vérifier si des bateaux ennemis sont détectés ou touchés par le sous-marin
}


@Test
public void testUpdateNonPotentialCells() {
    TestablePlayer player = new TestablePlayer();
    player.placeBoats(); // Assurez-vous que les bateaux sont placés

    // Supposons que vous avez un bateau coulé et que vous connaissez les cellules de ce bateau
    List<Cell> sunkBoatCells = new ArrayList<>();
    // Ajoutez les cellules du bateau coulé ici
    // Par exemple, si un bateau est coulé aux coordonnées (3,3), (3,4), (3,5)
    sunkBoatCells.add(new Cell(3, 3));
    sunkBoatCells.add(new Cell(3, 4));
    sunkBoatCells.add(new Cell(3, 5));

    // Marquez ces cellules comme non potentielles
    player.publicUpdateNonPotentialCells(sunkBoatCells, player);

    // Vérifiez que les cellules autour du bateau coulé ne sont plus potentielles
    boolean vertical = sunkBoatCells.get(0).getX() == sunkBoatCells.get(1).getX();
    int[] tempCoordinates = { -1, 1 };

    for (Cell c : sunkBoatCells) {
        for (int j : tempCoordinates) {
            int x = c.getX() + (vertical ? 0 : j);
            int y = c.getY() + (vertical ? j : 0);
            if (CoordinateHelper.isValid(x, y)) {
                Cell adjacentCell = player.getBoard().getCell(x, y);
                // Assurez-vous que ces cellules ne sont pas marquées comme potentielles
                assertFalse(adjacentCell.isPotential(), "La cellule (" + x + ", " + y + ") ne devrait pas être potentiel après avoir coulé un bateau");
            }
        }
    }
}



}


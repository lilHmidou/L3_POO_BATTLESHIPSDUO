package fr.pantheonsorbonne.miage.game.setting;

public abstract class Config {

    private static final String[][] boats = {
            { "1", "Porte-avions", "5", "false" },
            { "2", "Croiseur", "4", "false" },
            { "3", "Contre-torpilleurs", "3", "false" },
            { "4", "Contre-torpilleurs", "3", "false" },
            { "5", "Torpilleur", "2", "false" },
            { "99", "Mine", "1", "false" },
            { "99", "Mine", "1", "false" },
    };

    public static String[] getBoatsConfig(int boatId) {
        if (boatId < 0 || boatId >= boats.length) {
            return new String[0];
        }
        return boats[boatId];
    }

    public static String[][] getBoatsConfig() {
        return boats;
    }

    public static int getNbBoats() {
        return boats.length;
    }
}
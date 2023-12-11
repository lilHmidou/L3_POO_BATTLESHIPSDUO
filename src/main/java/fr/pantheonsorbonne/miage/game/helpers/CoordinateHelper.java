package fr.pantheonsorbonne.miage.game.helpers;

public class CoordinateHelper {

    public static String letterCoordinateToNumber(String letterCoordinate) {
        return String.valueOf(new String("ABCDEFGHIJ").indexOf(letterCoordinate));
    }

    public static String numberCoordinateToLetter(int numberCoordinate) {
        String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
        return letters[numberCoordinate];
    }

    public static boolean isValid(int x, int y) {
        return (x >= 0 && x < 10 && y >= 0 && y < 10);
    }
}
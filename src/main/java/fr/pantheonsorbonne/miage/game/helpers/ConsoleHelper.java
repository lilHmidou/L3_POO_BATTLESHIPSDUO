package fr.pantheonsorbonne.miage.game.helpers;

public class ConsoleHelper {

    public static void eraseConsole(){
        try {
            String os = System.getProperty("os.name");
            if(os.contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else if (os.contains("Linux")) {
            new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
        } else {
            new ProcessBuilder("cmd", "/c", "clear").inheritIO().start().waitFor();

        }
    } catch (Exception e) {
        System.out.println("Erreur lors de l'effacement de la console" + e);
    }
}

    public static void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
}
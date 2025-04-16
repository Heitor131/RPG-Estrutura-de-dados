package Others;

//import java.io.IOException;

public class ClearConsole {
    public static void clear() {
//        try {
//            if (System.getProperty("os.name").contains("Windows")) {
//                // Comando para Windows
//                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//            } else {
//                // Comando para Linux e macOS
//                new ProcessBuilder("clear").inheritIO().start().waitFor();
//            }
//        } catch (IOException | InterruptedException ex) {
//            ex.printStackTrace();
//        }
           for (int i = 0; i < 50; i++) {
           System.out.println();
           }
    }
}

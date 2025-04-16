package presitence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.entities.User;
import java.util.LinkedList;

public class Persistencia {
    private static final String JOGADORES_FILE = "jogadores.dat";
    
    public static void salvarJogadores(LinkedList<User> jogadores) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(JOGADORES_FILE))) {
            oos.writeObject(jogadores);
        } catch (IOException e) {
            System.err.println("Erro ao salvar jogadores: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public static LinkedList<User> carregarJogadores() {
        File file = new File(JOGADORES_FILE);
        if (!file.exists()) return new LinkedList<>();
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(JOGADORES_FILE))) {
            return (LinkedList<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar jogadores: " + e.getMessage());
            return new LinkedList<>();
        }
    }
}
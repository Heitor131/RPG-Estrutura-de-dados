package model.entities;
import structures.*;

public class User {
    private int id;
    private String userName;
    private String password;
    private String email;
    private PlayerCharacter playerCharacter;
    private LinkedList personagens;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.email = ""; // Define um valor padrão para o email
        this.playerCharacter = null;
        personagens = new LinkedList();

    }

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.playerCharacter = null;
        personagens = new LinkedList();
    }

    public void criarPersonagem(String nome) {
        int id = personagens.getSize() + 1;
        int nivel = 1;
        int vidaBase = 50 + (nivel * 10);
        int manaBase = 30 + (nivel * 5);
        PlayerCharacter novoPersonagem = new PlayerCharacter(vidaBase, manaBase, id, vidaBase, nivel, nome, manaBase);
        personagens.addTail(novoPersonagem);
        
        if (personagens.getHead() == null) {
            playerCharacter= novoPersonagem;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public PlayerCharacter getPlayerCharacter() {
        return playerCharacter;
    }
    
    public void setPlayerCharacter(PlayerCharacter playerCharacter) {
        this.playerCharacter = playerCharacter;
    }

    public LinkedList getPersonagens() {
        return personagens;
    }

    public void setPersonagens(LinkedList personagens) {
        this.personagens = personagens;
    }

}

import model.Battle;
import model.entities.*;
import structures.*;
import ui.UserInterface;

public class Main {
    static final String EMAIL_REGEX = "^[a-zA-Z0-9._]+@gmail.com$";
    
    public static void main(String[] args) {
        

        // runGame();
        UserInterface menu =  new UserInterface();
        menu.Menu();
        
    }

    public static void runGame(){
        

    }
    
    public static boolean isValidPassword(String password) {
        return !(password == null || password.isEmpty());
    }
}

//import model.dao.UserDAO;
//import model.entities.User;
//
//public class Main {
//    public static void main(String[] args) {
//        UserDAO dao = new UserDAO();
//
//        try {
//            // Teste de inserção
//            User novo = new User("Fulano", "fulano@email.com", "1234");
//            dao.create(novo);
//
//            // Teste de login
//            boolean loginValido = dao.checkLogin("fulano@email.com", "1234");
//            System.out.println("Login válido? " + loginValido);
//
//            // Teste de busca por ID (id 1 como exemplo)
//            User u = dao.findById(1);
//            if (u != null) {
//                System.out.println("Usuário encontrado: " + u.getUserName());
//            } else {
//                System.out.println("Usuário não encontrado.");
//            }
//
//            // Teste de remoção
//            dao.delete(u); // cuidado: isso apaga o usuário mesmo
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}

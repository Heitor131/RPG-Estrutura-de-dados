package model.entities;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Battle;
import structures.LinkedList;
import structures.Node;

public class Enemie extends Entity{
    public Enemie(String name, int level, float maxHp,  float maxMana, float currentMana, int damage) {
        super(maxHp, maxMana, currentMana, level, name, damage);
    }
    
    @Override
    public void attack(Entity target) {
        if (target == null) {
            throw  new RuntimeException(getName() + " tried to attack a null target.");
        }

        System.out.println(getName() + " attacks " + target.getName());
        try {
            Thread.sleep(2300);
        } catch (InterruptedException ex) {
            Logger.getLogger(Enemie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean isAlive() {
        return getCurrentHp() > 0;
    }

//    @Override
//    public void takeTurn(Battle battle) {
//        Random random = new Random();
//        int enemyChoice = random.nextInt(2) + 1;
//        System.out.println(getName() + "'s turn!");
//        System.out.println(enemyChoice);
//        
//        try {
//            Thread.sleep(1500);
//            // Implement enemy AI logic here
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Enemie.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    @Override
    public void takeTurn(Battle battle) {
        Random random = new Random();
        System.out.println("\n" + getName() + "'s turn!");
        
        int action = random.nextInt(2) + 1;
        
        switch (action) {
            case 1 -> {
                Entity target = findAliveTarget(battle.getParticipants());
                if (target != null) {
                    attack(target);
                } else {
                    System.out.println(getName() + " looks around but finds no targets!");
                }
            }
            case 2 -> {
                System.out.println(getName() + " uses an AOE skill");
                affectAllTargets(battle.getParticipants());
            }
        }
    }
    
    private Entity findAliveTarget(LinkedList participants) {
        Node<Entity> current = participants.getHead();
        while (current != null) {
            if (current.data.isAlive() && current.data instanceof PlayerCharacter) {
                return current.data;
            }
            current = current.prev;
        }
        return null;
    }
    
    private void affectAllTargets(LinkedList participants) {
        Node<Entity> current = participants.getHead();
        while (current != null) {
            if (current.data.isAlive() && current.data instanceof PlayerCharacter) {
                System.out.println(getName() + " hits " + current.data.getName() + " with dark energy!");
                current.data.takeDamage(getDamage() / 2);
                try {
                    Thread.sleep(2300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Enemie.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            current = current.prev;
        }
    }
}

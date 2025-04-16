package model.entities;

import Others.ClearConsole;
import java.util.Scanner;
import model.Battle;
import structures.*;
public class PlayerCharacter extends Entity {
    private int id;

    public PlayerCharacter(int id, float maxHp, float maxMana, float currentMana, int level, String name, int damage) {
        super(maxHp, maxMana, currentMana, level, name, damage);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void attack(Entity target) {
        if (target == null) {
            throw  new RuntimeException(getName() + " tried to attack a null target.");
        }

        System.out.println(getName() + " attacks " + target.getName() );
        target.takeDamage(getDamage());
        System.out.println(target.getName() + " takes " + getDamage() + " damage!");
        
    }

    @Override
    public boolean isAlive() {
        return getCurrentHp() > 0;
    }

    @Override
    public void takeTurn(Battle battle) {
        System.out.println(getName() + "'s turn!");
        Queue enemies = battle.getTurnOrder();
        Scanner scanner = new Scanner(System.in);
        battle.showState();
        System.out.println("\nPlayer's HP: " + getCurrentHp() + "/" + getMaxHp());
        System.out.println("Choose an action to take:");
        System.out.println("1. Attack an enemy");
        System.out.println("2. Use a skill");
        System.out.println("3. Use an item");
        System.out.println("4. Run away");  
        int action = scanner.nextInt();
        scanner.nextLine();
        switch (action) {
            case 1 -> {
                ClearConsole.clear();
                System.out.println("Choose an enemy to attack:");
                battle.showState();
                Node<Entity> current = enemies.getHead();
                int targetIndex = scanner.nextInt() - 1;
                attack(enemies.getElementbyIndex(targetIndex));
            }
            case 2 -> System.out.println("Using a skill...");
            case 3 -> System.out.println("Using an item...");
            case 4 -> System.out.println("Running away...");
            default -> System.out.println("Invalid action. Try again.");
        }
        
        
    }


    public void levelUp() {
        setLevel(getLevel() + 1);
        setMaxHp(getMaxHp() * 1.1f); // Increase max HP by 10%
        setCurrentHp(getMaxHp()); // Restore current HP to max
        setMaxMana(getMaxMana() * 1.1f); // Increase max Mana by 10%
        setCurrentMana(getMaxMana()); // Restore current Mana to max
        setDamage(getDamage() + 5); // Increase damage by 5
        System.out.println(getName() + " leveled up to level " + getLevel() + "!");
    }
}

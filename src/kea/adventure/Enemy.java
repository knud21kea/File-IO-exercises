package kea.adventure;

import java.util.ArrayList;

public class Enemy {

    private ArrayList<Item> enemyInventory = new ArrayList<>();
    private String enemyName, weaponName;
    private int health;
    private boolean isDead = false;

    public Enemy(String enemyName, int health, Weapon weapon) {
        this.enemyName = enemyName;
        this.health = health;
        this.weaponName = weapon.getItemName();
        enemyInventory.add(weapon);
    }

    public String getEnemyName() {
        return this.enemyName;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public int getEnemyHealth() {
        return health;
    }

    public void changeInHealth(int healthPoint) {
        this.health += healthPoint;
    }

    @Override
    public String toString() {
        return "Other lifeforms on this planet: " + enemyName;
    }
}




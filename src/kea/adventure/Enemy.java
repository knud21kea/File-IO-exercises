package kea.adventure;

import java.util.ArrayList;

import java.util.ArrayList;

public class Enemy {

    public ArrayList<Item> enemyInventory;
    private String enemyName;
    public Room currentRoom;
    private int health;
    private String weaponName;
    private boolean isDead;

    public Enemy(String enemyName, int health, Weapon weapon) {
        this.isDead = isDead;
        isDead = false;
        this.enemyInventory = new ArrayList<>();
        enemyInventory.add(weapon);
        this.enemyName = enemyName;
        this.currentRoom = currentRoom;
        this.health = health;
        this.weaponName = weapon.getItemName();

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

    public int changeInHealth(int healthPoint) {
        this.health = health + healthPoint;

        return health;
    }


    public Enemy getEnemyCurrentRoom() {
        Enemy enemy = currentRoom.getEnemy();
        return enemy;
    }

    @Override
    public String toString() {
        return "\nOther lifeforms on this planet: " + enemyName;
    }
}




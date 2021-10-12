package kea.adventure;

public abstract class Weapon extends Item {

    private int damage;
    private int ammo;

    public Weapon(String itemName, int itemWeight, int damage, int ammo) {
        super(itemName, itemWeight);
        this.damage = damage;
        this.ammo = ammo;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public void shootWeapon() {

    }

    @Override
    public String toString() {
        return getItemName();

    }
}
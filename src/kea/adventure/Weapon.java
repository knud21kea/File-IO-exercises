package kea.adventure;

public abstract class Weapon extends Item {

    private int damage;
    private int ammo;
    protected boolean isMeleeWeapon;

    public Weapon(String itemName, int itemWeight, int damage, int ammo) {
        super(itemName, itemWeight);
        this.damage = damage;
        this.ammo = ammo;
        this.isMeleeWeapon = false;
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

    public abstract boolean isMeleeWeapon();

    public void shootWeapon() {
        ammo--;

    }

    @Override
    public String toString() {
        return getItemName();

    }
}
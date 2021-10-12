package kea.adventure;

public class shootingWeapon extends Weapon {

    private int damage;
    private int ammo;
    //private boolean isMelee = false;

    public shootingWeapon(String itemName, int itemWeight, int damage, int ammo) {
        super(itemName, itemWeight, damage, ammo);
        this.damage = damage;
        this.ammo = ammo;
    }

    @Override
    public boolean checkIfMelee() {
        return false;
    }

    @Override
    public void shootWeapon() {
        ammo--;
    }
}

package kea.adventure;

public class meleeWeapon extends Weapon {

    private int damage;
    private int ammo;
    //private boolean isMelee = true;

    public meleeWeapon(String itemName, int itemWeight, int damage, int ammo) {
        super(itemName, itemWeight, damage, ammo);
        this.damage = damage;
        this.ammo = ammo;
    }

    @Override
    public boolean checkIfMelee() {
        return true;
    }

    @Override
    public void shootWeapon() {
    }
}

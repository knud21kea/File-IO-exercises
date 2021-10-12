package kea.adventure;

public class shootingWeapon extends Weapon {

    public shootingWeapon(String itemName, int itemWeight, int damage, int ammo) {
        super(itemName, itemWeight, damage, ammo);
    }

    @Override
    public boolean checkIfMelee() {
        return false;
    }

    @Override
    public void shootWeapon() {
        this.ammo--;
    }
}

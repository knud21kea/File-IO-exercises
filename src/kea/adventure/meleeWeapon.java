package kea.adventure;
public class meleeWeapon extends Weapon {

    public meleeWeapon(String itemName, int itemWeight, int damage, int ammo) {
        super(itemName, itemWeight, damage, ammo);
    }

    @Override
    public boolean isMeleeWeapon() {
        if (isMeleeWeapon == false) {
            setAmmo(12);
        } else if (isMeleeWeapon == true) {
            setAmmo(99999);
        }
        return true;
    }


}

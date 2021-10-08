package kea.adventure;

public class Food extends Item {

    int foodValue;

    public Food(String itemName, int itemWeight, int foodValue) {
        super(itemName, itemWeight);
        this.foodValue = foodValue;
    }
}

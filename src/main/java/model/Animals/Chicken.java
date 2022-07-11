package model.Animals;

import model.*;
import model.Activities.Schedule;

public class Chicken extends Animal {

    // NOTE: FOR TESTING
    public Chicken () {
        this.maxCalo    = 100;
        this.maxHP      = 100;
        this.maxSleep   = 100;
        this.maxWater   = 100;

        this.setWater(100);
        this.setCalo(80);
        this.setHP(70);
        this.setSleep(60);
        this.setAge(0);

        this.neededFood = new FoodInventory(new Food("Chicken", 20), 20);
        this.activity = null;
        this.schedule = new Schedule();
    }

    public Chicken (Schedule s, Food f, int foodAmount) {
        this.maxCalo    = 100;
        this.maxHP      = 100;
        this.maxSleep   = 100;
        this.maxWater   = 100;

        this.setWater(100);
        this.setCalo(100);
        this.setHP(100);
        this.setSleep(100);
        this.setAge(0);

        this.neededFood = new FoodInventory(f, foodAmount);
        this.activity = null;
        this.schedule = s;
    }

    @Override
    public void growUp() {
        getNeededFood().setAmount(getNeededFood().getAmount() + 10);

        this.maxCalo    += 20;
        this.maxHP      += 15;
        this.maxSleep   += 10;
        this.maxWater   += 20;

        this.setAge(this.getAge() + 1);
    }
}

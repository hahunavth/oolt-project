package model.Animals;

import model.*;
import model.Activities.Schedule;

public class Pig extends Animal {

    // NOTE: FOR TESTING
    public Pig () {
        this.maxCalo    = 800;
        this.maxHP      = 300;
        this.maxSleep   = 300;
        this.maxWater   = 150;

        this.setWater(60);
        this.setCalo(600);
        this.setHP(250);
        this.setSleep(100);
        this.setAge(0);

        this.neededFood = new FoodInventory(new Food("Pig", 100), 30);
        this.activity = null;
        this.schedule = new Schedule();
    }

    public Pig (Schedule s, Food f, int foodAmount) {
        this.maxCalo    = 800;
        this.maxHP      = 300;
        this.maxSleep   = 200;
        this.maxWater   = 100;

        this.setWater(100);
        this.setCalo(800);
        this.setHP(300);
        this.setSleep(200);
        this.setAge(0);

        this.neededFood = new FoodInventory(f, foodAmount);
        this.activity = null;
        this.schedule = s;
    }

    @Override
    public void growUp() {
        getNeededFood().setAmount(getNeededFood().getAmount() + 40);

        this.maxCalo    += 100;
        this.maxHP      += 50;
        this.maxSleep   += 60;
        this.maxWater   += 40;

        this.setAge(this.getAge() + 1);
    }
}

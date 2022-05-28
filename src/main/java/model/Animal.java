package model;

import model.Activities.PlayActivity;
import model.Activities.SleepActivity;

import java.util.Random;

public abstract class Animal {
    private int HP;
    protected int maxHP;
    private int water;
    protected int maxWater;
    private int calo;
    protected int maxCalo;
    private int age;
    private int maxAge;
    private int sleep;
    protected int maxSleep;
    private boolean isDead = false;
    protected Activity activity;
    protected Schedule schedule;
    protected FoodAmount neededFood;

    // Getter and protected setter
    public int getHP() {
        return HP;
    }
    protected void setHP(int HP) {
        int min = Math.min(HP, maxHP);
        if (min > 0)
            this.HP = min;
        else {
            this.HP = 0;
            isDead = true;
        }
    }
    public int getWater() {
        return water;
    }
    protected void setWater(int water) {
        int min = Math.min(water, maxWater);
        if(min > 0) {
            this.water = min;
        } else {
            this.water = 0;
        }
    }
    public int getCalo() {
        return calo;
    }
    protected void setCalo(int calo) {
        int min = Math.min(calo, maxCalo);
        if (min > 0) {
            this.calo = min;
        } else {
            this.calo = 0;
            this.activity = new SleepActivity();
        }
    }
    public int getAge() {
        return age;
    }
    public int getMaxAge() {
        return maxAge;
    }
    protected void setAge(int age) {
        this.age = age;
    }
    public int getSleep() {
        return sleep;
    }
    protected void setSleep(int sleep) {
        int min = Math.min(sleep, maxSleep);
        if ( min > 0 ) {
            this.sleep = min;
        } else {
            this.sleep = 0;
            this.activity = new SleepActivity();
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public FoodAmount getNeededFood () {
        return new FoodAmount(neededFood.getFood(), neededFood.getAmount() - calo);
    }
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    // method to check animal status
    public boolean isThirsty () {
        return water < 40;
    }
    public boolean isHungry () {
        return calo < 40;
    }
    public boolean isSick () {
        return HP < 30;
    }

    // handle specific activity
    public void drink (int ml) {
        water += ml;
    }
    public int eat (Food food, int amount) {
        if(food.equals(neededFood.getFood())) {
            if(this.calo + amount * food.getCalo() <= neededFood.getCalo()) {
                this.calo += amount * food.getCalo();
                return amount;
            } else {
                int eatAmount = neededFood.getAmount() - this.calo / food.getCalo();
                this.calo = neededFood.getCalo();
                return eatAmount;
            }
        }
        return 0;
    };
    public void play () {
        this.activity = new PlayActivity();
    }

    //
    protected ActivityType getCurrentActivity() {
        return activity.getActivityType();
    }

    /**
     * Method: nextActivity
     * @brief Cập nhật trạng thái của con vật khi thực hiện hành động.
     * NOTE: Có thể override, và gọi super.updateState()
     */
    protected void updateState () {
        System.out.println(this.activity);
        if (this.activity != null) {
            // update animal state
            this.setHP(this.getHP() + this.activity.getDeltaHP());
            this.setCalo (this.getCalo() + this.activity.getDeltaCalo());
            this.setWater(this.getWater() + this.activity.getDeltaWater());;
            this.setSleep(this.getSleep() + this.activity.getDeltaSleep());
            System.out.println(
                    "Hp: " + this.activity.getDeltaHP()
            );

            // if specific activity
            if(this.activity.getActivityType() == ActivityType.eat) {
                this.eat(this.neededFood.getFood(), 10);
            } else if (this.activity.getActivityType() == ActivityType.drink) {
                this.drink(10);
            }

        }
    }

    /**
     * Method: nextActivity
     * @param hours: giờ hiện tại [0, 23]
     * @brief Thực hiện hành động tiếp thep.
     * NOTE: Có thể override, và gọi super.updateState()
     */
    protected void nextActivity(int hours) {
        // next activity
        try {
            Random r = new Random(100);
//            System.out.println(this.schedule.activityList[hours]);
            this.activity = this.schedule.activityList[hours];
            System.out.println(hours);
            System.out.println(" Set activity: " + this.schedule.activityList[hours]);
            if (isHungry() && r.nextInt() < 2) {
                this.activity = new SleepActivity();
            }
            if (isThirsty() && r.nextInt() < 3) {
                this.activity = new SleepActivity();
            }
            if (isSick() && r.nextInt() < 4) {
                this.activity = new SleepActivity();
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: growUp
     * @brief Cập nhật trạng thái của con vật khi lớn lên 1 tuổi.
     * TODO: OVERRIDE THIS
     */
    public abstract void growUp();

    /**
     * Method: life
     * @param day: [0, 15]
     * @param hours [0, 23]
     * @param minutes [0, 60]
     * NOTE: Không override.
     */
    public final void life(int day ,int hours, int minutes) {
        if(!isDead) {
            if(minutes == 0) {
                nextActivity(hours);    // Thay đổi hành động mỗi giờ
            }
            if (minutes % 15 == 0) {
                updateState();          // thực hiện hành động gây thay đổi trạng thái mỗi 15' 1 lần.
            }
            if(day == 0 && hours == 0 && minutes == 0) {
                growUp();               // Lớn lên mỗi 15 ngày.
            }
        }
        System.out.println("life" + day + " " + hours + " " + minutes + " " + this);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "HP=" + HP +
                ", maxHP=" + maxHP +
                ", water=" + water +
                ", maxWater=" + maxWater +
                ", calo=" + calo +
                ", maxCalo=" + maxCalo +
                ", age=" + age +
                ", sleep=" + sleep +
                ", maxSleep=" + maxSleep +
                ", activity=" + activity +
                ", schedule=" + schedule +
                ", neededFood=" + neededFood +
                '}';
    }
}

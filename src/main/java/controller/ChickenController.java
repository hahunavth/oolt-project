package controller;

import interfaces.controller.Behavior;
import model.Activities.Activity;
import model.Activities.IPrepareActivity;
import model.Animals.Animal;
import org.jetbrains.annotations.NotNull;
import view.main.GamePanel;
import view.main.KeyHandler;
import view.main.MouseHandler;
import view.math.Vector2f;
import view.object.OBJ_FoodTray;
import view.states.PlayState;

public class ChickenController extends AnimalController {

    private PlayState ps;
    private int counter;

    public ChickenController(GamePanel gp, PlayState ps, int x, int y, int w, int h, Animal animal) {
        super(gp, ps, x, y, w, h, animal);

        this.ps = ps;
        this.setSpeed(1);
    }

    public ChickenController(GamePanel gp, PlayState ps, Vector2f pos, int w, int h, Animal animal) {
        super(gp, ps, pos, w, h, animal);

        this.ps = ps;
    }

    public void goToFoodTray () {
        // find food tray
        OBJ_FoodTray foodTray = null;
        for (int i = 0; i < ps.foodTrays.size(); i++) {
            if (
                    ps.foodTrays.get(i).getFoodInventory().getFood().getName().
                            equals(this.getAnimal().getNeededFood().getFood().getName())
            ) {
                foodTray = ps.foodTrays.get(i);
                break;
            }
        }

        if (foodTray != null) {
            // if not null -> goto food tray
//            boolean found = goTo(foodTray);
            // mark is going to food tray = true
//            if (found) {
//                // add action event
//                this.setOnDoneListener(actionEvent -> {
////                    if (this.getAnimal().getActivity() instanceof IPrepareActivity) {
////                        IPrepareActivity activity = (IPrepareActivity) this.getAnimal().getActivity();
////                        activity.onPrepareDone(this.getAnimal().getActivity());
////                        this.removeOnDoneListener();
//                    }
//                });
//            }
        } else {
            throw new Error ("Not found foodTray!");
        }
    }

    @Override
    public void update () {
        super.update();

        // TODO: GEN
        if (counter == 120) {
            this.updateRandomDirection();
            counter = 0;
        }
        this.move();
        counter++;
    }

    @Override
    public Behavior getBehavior(Activity activity) {
        return null;
    }

    @Override
    public void input(KeyHandler keyHandler, MouseHandler mouseHandler) {

    }

}

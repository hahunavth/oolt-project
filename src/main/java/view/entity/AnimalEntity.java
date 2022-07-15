package view.entity;

import model.Activities.IPrepareActivity;
import model.Animals.Animal;
import org.jetbrains.annotations.NotNull;
import view.states.PlayState;
import view.effect.FocusableHandler;
import interfaces.IFocusable;
import view.main.GamePanel;
import view.object.OBJ_FoodTray;

import java.awt.*;

public abstract class AnimalEntity extends Entity implements IFocusable {

    // Kết tập animal
    protected Animal animal;
    public FocusableHandler fch;

    public AnimalEntity(GamePanel gp, PlayState ps) {
        super(gp, ps);

        fch = new FocusableHandler(ps, this);
    }

    /**
     * Lấy ra đối tượng animal
     */
    public Animal getAnimal() {
        return animal;
    }

    @Override
    public void setHovered(boolean hovered) {
        this.fch.setHovered(hovered);
    }
    @Override
    public void setFocused(boolean focused) {
        this.fch.setFocused(focused);
    }
    @Override
    public boolean getIsHovered() {
        return this.fch.getIsHovered();
    }
    @Override
    public boolean getIsFocused() {
        return this.fch.getIsFocused();
    }

    /**
     * Activity: Eat
     * <br/>
     * Tìm loại foodTray mà con vật cần, sau đó tìm đường đi đến đó.
     * <br/>
     * Sau khi đến sử dụng actionListener để trigger việc con vật đã có thể ăn
     * và tăng thuộc tính calo.
     */
    public void goToFoodTray () {
        // find food tray
        OBJ_FoodTray foodTray = null;
        for (int i = 0; i < ps.foodTrays.size(); i++) {
            if (
                    ps.foodTrays.get(i).getFoodInventory().getFood().getName().
                            equals(this.animal.getNeededFood().getFood().getName())
            ) {
                foodTray = ps.foodTrays.get(i);
                break;
            }
        }
        
        if (foodTray != null) {
            // if not null -> goto food tray
            boolean found = goTo(foodTray);
            // mark is going to food tray = true
            if (found) {
                // add action event
                this.setToGoalListener(actionEvent -> {
                    if (this.animal.getActivity() instanceof IPrepareActivity) {
                        IPrepareActivity activity = (IPrepareActivity) this.animal.getActivity();
                        activity.onPrepareDone(this.animal.getActivity());
                        this.removeToGoalListener();
                    }
                });
            }
        } else {
            throw new Error ("Not found foodTray!");
        }
    }

    /**
     * {@inheritDoc}
     *
     * AnimalEntity.update:
     * <ul>
     *     <li>
     *         Set action
     *     </li>
     *     <li>
     *         Check collision tiles
     *     </li>
     * </ul>
     */
    @Override
    public void update() {
        super.update();
//        setAction();
    }

    /**
     * {@inheritDoc}
     *
     * AnimalEntity.draw:
     * <ul>
     *     <li>
     *         Vẽ tam giác màu đỏ trên đầu con vật khi focus.
     *     </li>
     * </ul>
     */
    @Override
    public void draw(@NotNull Graphics2D g2) {
        super.draw(g2);
        fch.draw(g2, this);

        pathFinder.draw(g2);
    }
}

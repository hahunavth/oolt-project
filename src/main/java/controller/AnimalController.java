package controller;

import interfaces.controller.Behavior;
import interfaces.controller.IBehaviorMapper;
import model.Activities.Activity;
import model.Animals.Animal;
import org.jetbrains.annotations.NotNull;
import view.main.GamePanel;
import view.main.KeyHandler;
import view.main.MouseHandler;
import view.math.Vector2f;
import view.states.PlayState;

import java.awt.event.ActionEvent;

public abstract class AnimalController extends EntityController implements IBehaviorMapper {

    private final Animal animal;

    private Behavior behavior;

    private ActionEvent actionEvent;

    public AnimalController(GamePanel gp, PlayState ps, int x, int y, int w, int h, Animal animal) {
        super(gp, ps, x, y, w, h);

        this.animal = animal;
        this.setSpeed(100);
    }

    public AnimalController(@NotNull GamePanel gp, PlayState ps, Vector2f pos, int w, int h, Animal animal) {
        super(gp, ps, pos, w, h);

        this.animal = animal;
    }

    public Animal getAnimal() {
        return animal;
    }

    public ActionEvent getActionEvent() {
        return actionEvent;
    }

    @Override
    public void update () {
        super.update();

    }

    @Override
    public abstract Behavior getBehavior(Activity activity);

    @Override
    public Behavior getBehavior() {
        return behavior;
    }

    @Override
    public void setBehavior(Activity activity) {
        this.behavior = getBehavior(activity);
    }

    @Override
    public void setOnDoneListener(ActionEvent actionEvent) {
        this.actionEvent = actionEvent;
    }

    @Override
    public void removeOnDoneListener() {
        this.actionEvent = null;
    }
}

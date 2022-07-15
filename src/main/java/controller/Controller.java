package controller;

import interfaces.controller.ILocatable;
import interfaces.controller.IMovable;
import interfaces.controller.IUpdatable;
import org.jetbrains.annotations.NotNull;
import view.math.AABB;
import view.math.Vector2f;

public abstract class Controller implements ILocatable, IUpdatable {
    AABB bounds;

    public Controller (int x, int y, int w, int h) {
        Vector2f pos = new Vector2f(x, y);
        bounds = new AABB(pos, w, h);
    }

    public Controller (@NotNull Vector2f pos, int w, int h) {
        bounds = new AABB(pos, w, h);
    }

    @Override
    public Vector2f getPos() {
        return bounds.getPos();
    }

    @Override
    public int getWorldX() {
        return (int) bounds.getPos().getScreenX();
    }

    @Override
    public int getWorldY() {
        return (int) bounds.getPos().getScreenY();
    }

    @Override
    public AABB getBounds() {
        return bounds;
    }

}

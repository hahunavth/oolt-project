package view.renderer;

import controller.Controller;
import org.jetbrains.annotations.NotNull;
import interfaces.view.IDrawable;
import view.math.AABB;
import view.math.Vector2f;

import java.awt.*;

public class Renderer implements IDrawable {

    Controller controller;

    public Renderer (Controller controller) {
        this.controller = controller;
    }

    @Override
    public Controller getController() {
        return controller;
    }

    public void draw (Graphics2D g2) {
        int x = (int) (controller.getBounds().getPos().getScreenX() + controller.getBounds().getXOffset());
        int y = (int) (controller.getBounds().getPos().getScreenY() + controller.getBounds().getYOffset());
        int w = (int) (controller.getBounds().getWidth());
        int h = (int) (controller.getBounds().getHeight());

        g2.drawRect(x, y, w, h);
    }

}

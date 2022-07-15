package view.object;

import view.states.GameStateManager;
import view.states.PlayState;
import view.effect.FloatingHandler;
import view.effect.FocusableHandler;
import interfaces.IFocusable;
import view.entity.GameObject;
import view.main.GamePanel;
import view.utils.Tool;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Vật thể trong game
 *
 * todo:
 */
public class SuperObject extends GameObject implements IFocusable {

    public BufferedImage image;
    Tool tool = new Tool();

    private final FloatingHandler flh = new FloatingHandler();

    public FocusableHandler fch = new FocusableHandler(ps, this);

    // IFocusManager
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

    public SuperObject(GamePanel gp, PlayState ps) {
        super(gp, ps);
    }

    private int[] calculatePosition () {
        int screenX = (int) this.pos.getScreenX();
        int screenY = (int) this.pos.getScreenY();

        return new int[]{screenX, screenY};
    }

    private int[] calculatePosition (boolean isFloatingEnabled) {
        int[] p = calculatePosition();
        if(isFloatingEnabled) {
            flh.update();
            p[0] += flh.getOffsetX();
            p[1] += flh.getOffsetY();
        }
        return p;
    }

    public boolean checkInMap () {
        return GameStateManager.camera.getBounds().collides(
                this.bounds
        );
    }

    public void draw (Graphics2D g2) {
        int[] p = calculatePosition();

        if (checkInMap()) {
            g2.drawImage(image, p[0], p[1], gp.titleSize, gp.titleSize, null);
        }

        fch.draw(g2, this.bounds, this.name);
    }

    public void draw (Graphics2D g2, boolean isFloatingEnabled) {
        int[] p = calculatePosition(isFloatingEnabled);
        if (checkInMap()) {
            g2.drawImage(image, p[0], p[1], gp.titleSize, gp.titleSize, null);
        }
        fch.draw(g2, this.bounds, this.name);
    }
}

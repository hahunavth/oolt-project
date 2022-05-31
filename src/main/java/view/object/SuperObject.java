package view.object;

import states.PlayState;
import view.effect.FloatingHandler;
import view.entity.GameObject;
import view.main.GamePanel;
import view.utils.Tool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject extends GameObject {

    public BufferedImage image;
    Tool tool = new Tool();

    private final FloatingHandler flh = new FloatingHandler();

    public SuperObject(GamePanel gp, PlayState ps) {
        super(gp, ps);
    }

    private int[] calculatePosition () {
//        int screenX = getWorldX() - ps.player.getWorldX() + ps.player.screenX;
//        int screenY = getWorldY() - ps.player.getWorldY() + ps.player.screenY;
        int screenX = getScreenX();
        int screenY = getScreenY();

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
        int mapLeftEdge = ps.player.getWorldX() - ps.player.screenX - gp.titleSize;
        int mapRightEdge = ps.player.getWorldX() + ps.player.screenX + gp.titleSize;
        int mapTopEdge = ps.player.getWorldY() - ps.player.screenY - gp.titleSize;
        int mapBottomEdge = ps.player.getWorldY() + ps.player.screenY + gp.titleSize;
        return  ( getWorldX() > mapLeftEdge &&
                    getWorldX() < mapRightEdge &&
                    getWorldY() > mapTopEdge &&
                    getWorldY() < mapBottomEdge
        );
    }

    public void draw (Graphics2D g2) {
        int[] p = calculatePosition();

        if (checkInMap()) {
            g2.drawImage(image, p[0], p[1], gp.titleSize, gp.titleSize, null);
        }
    }

    public void draw (Graphics2D g2, boolean isFloatingEnabled) {
        int[] p = calculatePosition(isFloatingEnabled);
        if (checkInMap()) {
            g2.drawImage(image, p[0], p[1], gp.titleSize, gp.titleSize, null);
        }
    }
}
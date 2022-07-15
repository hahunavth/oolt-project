package view.entity;

import view.states.PlayState;
import view.main.GamePanel;
import view.math.AABB;
import view.math.Vector2f;

import java.awt.*;

public class GameObject {
    protected GamePanel gp;
    protected PlayState ps;

    protected Vector2f pos;

    public String name;

    protected AABB bounds;

    public GameObject(GamePanel gp, PlayState ps) {
        this.gp = gp;
        this.ps = ps;
        this.name = "Object?";

        this.pos = new Vector2f(0, 0);
        this.bounds = new AABB(this.pos, 48, 48);
    }

    public AABB getBounds() {
        return bounds;
    }
    public Vector2f getPos() {
        return pos;
    }

    public void draw(Graphics2D g2) {
        // NOTE: construct new draw new item
        System.out.println("Default drawer");
    }
}

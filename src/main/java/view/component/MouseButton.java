package view.component;

import interfaces.ICancelable;
import view.states.GameStateManager;
import view.math.AABB;
import view.math.Vector2f;

import java.awt.*;

public abstract class MouseButton extends MyButton implements ICancelable {

    private final AABB bound;
    private boolean markBoundSizeInitialized = false;

    public MouseButton(int x, int y, String text) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.paddingTop = 0;
        this.paddingBottom = 0;
        this.paddingLeft = 0;
        this.paddingRight = 0;

        this.bound = new AABB(new Vector2f(x, y), 100, 100 );

        GameStateManager.gp.getMouseH().addObserver(this);
    }

    public MouseButton(int x, int y, String text, int px, int py) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.paddingTop = px;
        this.paddingBottom = px;
        this.paddingLeft = py;
        this.paddingRight = py;

        this.bound = new AABB(new Vector2f(x, y), 100, 100 );

        GameStateManager.gp.getMouseH().addObserver(this);
    }

    public void handleMoved (int x, int y) {
        isActive = bound.inside(x, y);
    }

    public void handleDragged (int x, int y) {
        isActive = bound.inside(x, y);
    }

    public void handleReleased (int x, int y) {
        if (bound.inside(x, y)) {
            exec();
        }
    }

    public void hamdlePressed (int x, int y) {

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

    }

    @Override
    public void onCancel () {
        GameStateManager.gp.getMouseH().removeObserver(this);
    }

    @Override
    public void draw (Graphics2D g2) {
        super.draw(g2);

        if (!markBoundSizeInitialized) {
            FontMetrics metrics = g2.getFontMetrics();
            bound.setWidth(metrics.stringWidth(text));
            bound.setHeight(metrics.getHeight());
        }
    }
}

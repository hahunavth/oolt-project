package view.component;

import interfaces.ICancelable;
import view.states.GameStateManager;

import java.awt.*;

/**
 * Observer Pattern
 */
public abstract class KeyPressedButton extends MyButton implements ICancelable {
    private int keyCode;


    public KeyPressedButton(int x, int y, String text, int keyCode) {
        this.text = text;
        this.keyCode = keyCode;
        this.x = x;
        this.y = y;
        this.paddingTop = 0;
        this.paddingBottom = 0;
        this.paddingLeft = 0;
        this.paddingRight = 0;

        GameStateManager.gp.getKeyH().addObserver(this);
    }

    public KeyPressedButton(int x, int y, String text, int keyCode, int padding) {
        this(x, y, text, keyCode);
        this.paddingTop = padding;
        this.paddingBottom = padding;
        this.paddingRight = padding;
        this.paddingLeft = padding;
    }

    public KeyPressedButton(int x, int y, String text, int keyCode, Font font) {
        this(x, y, text, keyCode);
        this.font = font;
    }

    public KeyPressedButton(int x, int y, String text, int keyCode, Font font, int padding) {
        this(x, y, text, keyCode, font);
        this.paddingTop = padding;
        this.paddingBottom = padding;
        this.paddingRight = padding;
        this.paddingLeft = padding;
    }

    public abstract void exec ();

    public void handlePressed (int key) {
        if (key == keyCode) {
            this.onPressed();
        }
    }

    public void handleReleased (int key) {
        if (key == keyCode) {
            this.onReleased();
        }
    }

    @Override
    public void onCancel() {
        GameStateManager.gp.getKeyH().removeObserver(this);
    }
}

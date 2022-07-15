package adapter;

import model.ModelState;
import view.states.GameStateManager;
import view.states.MenuState;
import interfaces.ICancelable;
import view.component.KeyPressedButton;
import view.main.Camera;
import view.main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @deprecated
 */
public class Model2MenuStateAdapter extends MenuState implements ICancelable {

    private ModelState modelState;
    private KeyPressedButton incBtn, decBtn;

    public Model2MenuStateAdapter(Camera camera, ModelState modelState) {
        super(camera);
        this.modelState = modelState;

        GamePanel gp = GameStateManager.gp;
        incBtn = new KeyPressedButton(gp.screenWidth - 300, gp.screenHeight - 300, "+", KeyEvent.VK_1) {
            @Override
            public void exec() {
                modelState.setSimulationSpeed(modelState.getSimulationSpeed() + 1);
            }
        };

        decBtn = new KeyPressedButton(gp.screenWidth - 300, gp.screenHeight - 200, "-", KeyEvent.VK_2) {
            @Override
            public void exec() {
                modelState.setSimulationSpeed(modelState.getSimulationSpeed() + 1);
            }
        };
    }

    @Override
    public void onCancel() {
        incBtn.onCancel();
        decBtn.onCancel();
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);

        if (this.modelState != null)
            g2.drawString(String.valueOf(this.modelState.getSimulationSpeed()), 450, 300);
        incBtn.draw(g2);
        decBtn.draw(g2);
    }
}

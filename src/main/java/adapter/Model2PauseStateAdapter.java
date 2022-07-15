package adapter;

import model.ModelState;
import view.states.GameStateManager;
import view.states.PauseState;
import interfaces.ICancelable;
import view.component.MouseButton;
import view.main.Camera;
import view.main.GamePanel;

import java.awt.*;

public class Model2PauseStateAdapter extends PauseState implements ICancelable {

    private ModelState modelState;
    private MouseButton incBtn, decBtn;
    private int center;

    public Model2PauseStateAdapter(Camera camera, GameStateManager gsm, ModelState modelState) {
        super(camera, gsm);
        this.modelState = modelState;
        GamePanel gp = GameStateManager.gp;
        center = gp.screenWidth / 2;
    incBtn =
        new MouseButton(gp.screenWidth / 2 + 50, 300, "+", 5, 5) {
          @Override
          public void exec() {
              modelState.setSimulationSpeed(modelState.getSimulationSpeed() + 1);
          }
        };

        decBtn = new MouseButton(gp.screenWidth / 2 - 50, 300, "-", 5, 5) {
            @Override
            public void exec() {
                modelState.setSimulationSpeed(modelState.getSimulationSpeed() - 1);
            }
        };
    }

    @Override
    public void onCancel() {
        this.decBtn.onCancel();
        this.incBtn.onCancel();
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);

        g2.drawString(String.valueOf(this.modelState.getSimulationSpeed()), center - 5, 320);
        incBtn.draw(g2);
        decBtn.draw(g2);
    }

}

package adapter;

import model.ModelState;
import view.states.*;
import interfaces.ICancelable;
import view.main.Camera;
import view.main.GamePanel;

import java.awt.*;

/**
 * Adapter pattern
 *
 * @see <a href="https://refactoring.guru/design-patterns/adapter">Adapter</a>
 */
public class Model2GSMAdapter extends GameStateManager {
    public ModelState modelState;

    public Model2GSMAdapter(Graphics2D g2, GamePanel gp, ModelState modelState) {
        super(g2, gp);

        this.modelState = modelState;
    }

    @Override
    public void createAndSetupPlayState(Camera camera) {
        states[PLAY] = new Model2PlayStateAdapter(camera, modelState);
        this.setup();
    }

//    @Override
//    public void createMenuState(Camera camera) {
//        view.states[MENU] = new Model2MenuStateAdapter(camera, modelState);
//    }

    @Override
    public void pop(int state) {
        if (states[state] instanceof ICancelable)
            ((ICancelable) states[state]).onCancel();

        super.pop(state);
    }

    @Override
    public void createPauseState(Camera camera, GameStateManager gameStateManager) {
        states[PAUSE] = new Model2PauseStateAdapter(camera, gameStateManager, modelState);
    }
}

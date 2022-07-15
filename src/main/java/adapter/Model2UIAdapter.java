package adapter;

import model.ModelState;
import model.TimeManager;
import view.states.GameStateManager;
import view.main.GamePanel;
import view.main.UI;

import java.awt.*;

public class Model2UIAdapter extends UI {

    TimeManager timeManager;
    ModelState modelState;
    Font font;
    private final int MAX_DARK_OPACITY = 150;


    public Model2UIAdapter(GamePanel gp, ModelState modelState) {
        super(gp);
        this.timeManager = modelState.getTimeManager();
        this.modelState = modelState;
        this.font = new Font(Font.MONOSPACED, Font.BOLD, 12);
    }

    @Override
    public void draw (Graphics2D g2) {
        super.draw(g2);

        // draw time
        if (timeManager != null) {
            int screenHeight = GameStateManager.gp.screenHeight;
            int screenWidth = GameStateManager.gp.screenWidth;
            int speed = modelState.getSimulationSpeed() ;
            // calc time
            String time = null;
            int opacity = 0;
            if (timeManager.getHours() >= 21 || timeManager.getHours() < 5) {
                time = "[Night]: " + timeManager + " (" + speed + "x)";
                opacity = MAX_DARK_OPACITY;
            } else if (timeManager.getHours() < 12) {
                time = "[Morning]: " + timeManager + " (" + speed + "x)";
            } else {
                time = "[Afternoon]: " + timeManager + " (" + speed + "x)";
            }

            if (timeManager.getHours() >= 5 && timeManager.getHours() < 8) {
                int offset = (timeManager.getHours() - 5 ) * 60 + timeManager.getMinutes();
                int max = 3 * 60;
                opacity = (int) ((1 - 1.0 * offset / max) * MAX_DARK_OPACITY) + 1;
            } else if (timeManager.getHours() >= 18 && timeManager.getHours() < 21) {
                int offset = (timeManager.getHours() - 17 ) * 60 + timeManager.getMinutes();
                int max = 3 * 60;
                opacity = (int) (1.0 * offset / max * MAX_DARK_OPACITY) + 1;
            }

            // draw string
            g2.setFont(this.font);
            g2.setColor(Color.WHITE);
            g2.drawString(
                    time, screenWidth - 280,  screenHeight - 48
            );

            // mau toi khi troi dem
            g2.setColor(new Color(3, 3, 3, opacity));
            g2.fillRect(0, 0, screenWidth, screenHeight);
            g2.setColor(Color.WHITE);
        }
    }

}

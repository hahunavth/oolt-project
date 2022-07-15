package view.states;

import view.component.KeyPressedButton;
import view.main.Camera;
import view.main.GamePanel;
import view.main.KeyHandler;
import view.main.MouseHandler;
import view.utils.Animation;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Pause screen
 */
public class PauseState extends GameState{
    private final Font font;
    private final String[] options;
    private final int RESUME = 0;
    private final int EXIT = 1;
//    private boolean resumePressed;
//    private boolean exitPressed;

    private final Animation fadeAnim;
    private KeyPressedButton resumeButton;
    private KeyPressedButton exitButton;

    public PauseState (Camera camera, GameStateManager gsm) {
        // todo
        super(camera);

        font            = new Font("MeatMadness", Font.PLAIN, 48);
        options         = new String[2];
        options[RESUME] = "(Space) Resume";
        options[EXIT]   = "(X) Exit";
//        this.resumePressed = false;

        fadeAnim = new Animation()
                .setDelay(0)
                .setFrom(0)
                .setTo(100)
                .setNumFrames(30);
        fadeAnim.start();

        Font btnFont = new Font(Font.MONOSPACED, Font.PLAIN, 20);
        resumeButton = new KeyPressedButton(
                GameStateManager.gp.screenWidth- 500,
                GameStateManager.gp.screenHeight - 100,
                options[RESUME],
                KeyEvent.VK_SPACE,
                btnFont,
                10
        ) {
            @Override
            public void exec () {
                gsm.pop(GameStateManager.PAUSE);
            }
        };

        exitButton = new KeyPressedButton(
                GameStateManager.gp.screenWidth - 200,
                GameStateManager.gp.screenHeight - 100,
                options[EXIT],
                KeyEvent.VK_X,
                btnFont,
                10
        ) {
            @Override
            public void exec () {
                System.exit(0);
            }
        };
    }

    /**
     * Update:
     * <ul>
     *     <li>
     *         Hiệu ứng fade (tối dần)
     *     </li>
     * </ul>
     */
    @Override
    public void update(double time, GameStateManager gsm) {
        fadeAnim.update();
    }

    /**
     * Trong khi pause:
     * <ul>
     *     <li>Nhấn SPACE: Tiếp tục chơi</li>
     *     <li>Nhấn X:     Thoát game</li>
     * </ul>
     */
    @Override
    public void input(MouseHandler mouse, KeyHandler key, GameStateManager gsm) {
//        if (key.spacePressed) {
//            resumePressed = true;
//        } else if (key.xPressed) {
//            exitPressed = true;
//        } else if (resumePressed) {
//            gsm.pop(GameStateManager.PAUSE);
//        } else if (exitPressed) {
//            System.exit(0);
//        }
    }

    @Override
    public void draw(Graphics2D g2) {
        GamePanel gp = GameStateManager.gp;

        // Màu tối dần trên màn hình
        g2.setColor(new Color(3, 3, 3, fadeAnim.getValue()));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Text: pause
        g2.setColor(Color.WHITE);
        g2.setFont(font);
        g2.drawString("Pause", 100, 100);

        resumeButton.draw(g2);
        exitButton.draw(g2);

        // Button:
//        g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 24));
//        for (int i = 0; i < options.length; i++) {
//            FontMetrics metrics = g2.getFontMetrics(font);
//            int x = gp.screenWidth - metrics.stringWidth(options[i]);
//            int y = gp.screenHeight - metrics.getHeight();
//            if(resumePressed && i == RESUME) {
//                g2.fillRect(x - 100, y - 30, metrics.stringWidth(options[i]) - 120, metrics.getHeight());
//                g2.setColor(Color.BLACK);
//            } else if (exitPressed && i == EXIT) {
//                g2.fillRect(x , y - 30, metrics.stringWidth(options[i]) - 20, metrics.getHeight());
//                g2.setColor(Color.BLACK);
//            } else {
//                g2.setColor(Color.WHITE);
//            }
//            g2.drawString(options[i], x - 100 + 100 * i, y);
//        }
    }
}

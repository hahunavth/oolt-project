package view.states;

import view.component.KeyPressedButton;
import view.component.MouseButton;
import view.main.Camera;
import view.main.GamePanel;
import view.main.KeyHandler;
import view.main.MouseHandler;
import view.utils.Animation;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Help screen
 */
public class HelpState extends GameState {
    private boolean resumePressed;
    private final Animation fadeAnim;
    private Font font;
    private Font textFont;
    private KeyPressedButton button;
    private String[] keyTextList;

    // test
    private MouseButton mouseButton;

    public HelpState (Camera camera, GameStateManager gsm) {
        super(camera);
        font = new Font("MeatMadness", Font.PLAIN, 48);
        textFont = new Font("MeatMadness", Font.PLAIN, 24);

        keyTextList = new String[] {
                "W: up",
                "A: left",
                "S: down",
                "D: right",
                "Enter: select",
                "P: pause",
                "H: help"
        };

        fadeAnim = new Animation()
                .setDelay(0)
                .setFrom(0)
                .setTo(100)
                .setNumFrames(30);
        fadeAnim.start();

        button = new KeyPressedButton(gsm.gp.screenWidth - 300, gsm.gp.screenHeight - 100, "(SPACE) Exit", KeyEvent.VK_SPACE, 4) {
            @Override
            public void exec () {
                gsm.pop(GameStateManager.HELP);
            }
        };

    mouseButton =
        new MouseButton(800, 200, "Ka ka ka") {
          @Override
          public void exec() {
            System.out.println("Exec!");
          }
        };
    }

    /**
     * Update:
     * <ul>
     *     <li>
     *         Hiệu ứng
     *     </li>
     * </ul>
     */
    public void update(double time, GameStateManager gsm) {
        fadeAnim.update();
    }

  /**
   * Trong Màn hình help:
   *
   * <ul>
   *   <li>Nhấn SPACE: Tiếp tục chơi
   * </ul>
   */
    public void input(MouseHandler mouse, KeyHandler key, GameStateManager gsm) {
        if (key.spacePressed) {
            resumePressed = true;
        }
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
        g2.drawString("Help", 100, 100);

        FontMetrics fontMetrics = g2.getFontMetrics();
        int lineHeight = fontMetrics.getHeight();
        g2.setFont(textFont);
        for (int i = 0; i < keyTextList.length; i++) {
            g2.drawString(keyTextList[i], 200, 200 + i * lineHeight);
        }

        button.draw(g2);
        mouseButton.draw(g2);
    }
}

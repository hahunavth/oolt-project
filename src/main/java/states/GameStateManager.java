package states;

import view.main.GamePanel;
import view.main.KeyHandler;
import view.main.MouseHandler;
import view.utils.Fontf;

import java.awt.*;

public class GameStateManager {
    private GameState states[];

    public static final int MENU = 0;
    public static final int PLAY = 1;
    public static final int PAUSE = 2;
    public static final int GAME_OVER = 3;
    public static final int EDIT = 4;
    public static Font font;
    public static Fontf fontf;

    public static Graphics2D g2;
    public static GamePanel gp;

    public GameStateManager (Graphics2D g2,GamePanel gp) {
        GameStateManager.g2 = g2;
        GameStateManager.gp = gp;

        font = new Font("font/font.png", 10, 10);
        fontf = new Fontf();
        fontf.loadFont("font/Stackedpixel.ttf", "MeatMadness");
        fontf.loadFont("font/GravityBold8.ttf", "GravityBold8");

        states = new GameState[5];
//        states[PLAY] = new PlayState(this);
        states[MENU] = new MenuState(this);
    }

    /**
     * Setup game_object
     */
    public void setup() {
        if (isStateActive(PLAY))
            for (GameState state : states) {
                if (state != null) {
                    state.setup();
                }
            }
    }

    public boolean isStateActive(int state) {
        return states[state] != null;
    }

    public GameState getState(int state) {
        return states[state];
    }

    public void pop(int state) {
        states[state] = null;
    }

    public void add(int state) {
        if (states[state] != null)
            return;

        if (state == PLAY) {
//            cam = new Camera(new AABB(new Vector2f(0, 0), GamePanel.width + 64, GamePanel.height + 64));
            states[PLAY] = new PlayState(this);
            this.setup();
        }
        else if (state == MENU) {
            states[MENU] = new MenuState(this);
        }
        else if (state == PAUSE) {
            states[PAUSE] = new PauseState(this);
        }
        else if (state == GAME_OVER) {
//            states[GAMEOVER] = new GameOverState(this);
        }
        else if (state == EDIT) {
            if(states[PLAY] != null) {
//                states[EDIT] = new EditState(this, cam);
            }
        }
    }

    public void addAndpop(int state) {
        addAndpop(state, 0);
    }

    public void addAndpop(int state, int remove) {
        pop(remove);
        add(state);
    }

    public void update(double time) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null) {
                states[i].update(time);
            }
        }
    }

    public void input(MouseHandler mouse, KeyHandler key) {

        for (int i = 0; i < states.length; i++) {
            if (states[i] != null) {
                System.out.println(states[i]);
                states[i].input(mouse, key);
            }
        }
    }

    public void render(Graphics2D g2) {
//        g.setFont(GameStateManager.fontf.getFont("MeatMadness"));
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null) {
                states[i].draw(g2);
            }
        }
//        states[PLAY].draw(g2);
    }

}

package view.states;

import controller.ChickenController;
import view.entity.*;
import view.main.*;
import view.object.OBJ_FoodTray;
import view.renderer.ChickenRenderer;
import view.title.TileManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayState
 * @brief trạng thái của màn hình chơi game.
 *
 */
public class PlayState extends GameState {

    public TileManager tileM;               // draw ban do
    public UI ui;                           // hien thi text tren mh
    public CollisionChecker cChecker;       // check va cham
    public AssetSetter assetSetter;         // them cac con vat...
    Sound music = new Sound();              // nhac nen
    Sound se = new Sound();                 // am thanh khi cham vao con vat
    public GameObject[] obj = new GameObject[100];    // Danh sach
    public List<OBJ_FoodTray> foodTrays = new ArrayList<>();

    public Player player;

    ChickenRenderer chickenRenderer = new ChickenRenderer(GameStateManager.gp, this, new ChickenController(GameStateManager.gp, this, 20 * GameStateManager.gp.titleSize, 15 * GameStateManager.gp.titleSize, GameStateManager.gp.titleSize, GameStateManager.gp.titleSize, null));

    public PlayState (Camera camera) {
        // init
        this(camera, new UI(GameStateManager.gp));
    }

    public PlayState (Camera camera, UI ui) {
        super(camera);

        this.ui = ui;

        tileM       = new TileManager(GameStateManager.gp, this, camera);
        cChecker    = new CollisionChecker(GameStateManager.gp, this);
        assetSetter = new AssetSetter(GameStateManager.gp, this);

        player      = new Player(GameStateManager.gp, this, camera);

        camera.target(player);
    }

    /**
     * {@inheritDoc}
     * <ul>
     *     <li>Nếu truyền thêm modelState vào constructor GameState thì tạo con vật dựa trên ModelState </li>
     *     <li>Ngược lại, tạo con vật dựa trên AssetsSetter</li>
     * </ul>
     */
    @Override
    public void setup () {
        // default is use asset setter
        // but adapter us model state
        assetSetter.setObject();

        playMusic(0);
//        camera.target(obj[1]);
    }

    public boolean isUpdatable(GameStateManager gsm) {
        return !gsm.isStateActive(GameStateManager.PAUSE) && !gsm.isStateActive(GameStateManager.HELP);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void update(double time, GameStateManager gsm) {

        if(isUpdatable(gsm)) {
            player.update();
            ui.update();

            for (GameObject gameObject : obj) {
                if (gameObject instanceof Entity) {
                    ((Entity) gameObject).update();
                }
            }

            chickenRenderer.update();

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void input(MouseHandler mouse, KeyHandler key, GameStateManager gsm) {
    if (isUpdatable(gsm)) {
            player.input(mouse, key);

            if (key.pPressed) {
                gsm.addAndPop(GameStateManager.PAUSE);
            }

            if (key.hPressed)
                gsm.addAndPop(GameStateManager.HELP);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g2) {

        tileM.draw(g2);

        for (GameObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(g2);
            }
        }

        player.draw(g2);
        ui.draw(g2);

        chickenRenderer.draw(g2);
    }

    /**
     * Sound manager
     * @param i
     */
    public void playMusic (int i) {
//        music.setFile(i);
//        music.play();
//        music.loop();
    }
    public void stopMusic () {
        music.stop();
    }
    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}

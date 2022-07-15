package view.states;

import view.main.Camera;
import view.main.KeyHandler;
import view.main.MouseHandler;

import java.awt.*;


/**
 * GameState
 * @brief Quản lý trạng thái của 1 màn hình trong game
 *
 */
public abstract class GameState {
    protected Camera camera;

    public GameState(Camera camera) {
        this.camera = camera;
    }

    public abstract void update(double time, GameStateManager gsm);
    public abstract void input(MouseHandler mouse, KeyHandler key, GameStateManager gsm);
    public abstract void draw(Graphics2D g);

    /**
     * Setup:
     * <ul>
     *     <li>
     *         Chỉ override (nếu cần), chạy 1 lần khi khởi tạo GameState.
     *     </li>
     *     <li>
     *         EX: Thêm các con vật trước khi hiện bản đồ game.
     *     </li>
     * </ul>
     */
    public void setup() {}

    @Override
    public String toString() {
        return "GameState{" +
                this.getClass().getName() +
                '}';
    }
}

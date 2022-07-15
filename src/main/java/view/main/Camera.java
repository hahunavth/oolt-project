package view.main;

import view.states.GameStateManager;
import view.entity.Entity;
import view.entity.GameObject;
import view.math.AABB;
import view.math.Vector2f;

/**
 * <h1>Camera: 1 phần bản đồ được hiển thị lên màn hình.</h1>
 * <p>
 * Chứa các phương thức để đièu chỉnh vị trí camera.
 * Có thể đi theo mục tiêu băng cách sử dung target(player)
 * Phần ngoài màn hình ( quá 2 ô ) không render để tăng hiệu năng.
 * </p>
 *
 * @see <a href="https://www.youtube.com/watch?v=7Av3LVa3Ksg">Youtube</a>
 */
public class Camera {
    private final AABB collisionCam;
    private GameObject e;

    private int widthLimit;
    private int heightLimit;
    private int tileSize;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private float speed = 8f;

    public Camera(AABB collisionCam, int tileSize) {
        this.collisionCam = collisionCam;
        this.tileSize = tileSize;
    }

    public void setLimit(int widthLimit, int heightLimit) {
        this.widthLimit = widthLimit;
        this.heightLimit = heightLimit;
    }
    public GameObject getTarget() { return e; }
    public Vector2f getPos() {
        return collisionCam.getPos();
    }
    public AABB getBounds() {
        return collisionCam;
    }

    public void update() {
        if (e != null)
            move();
    }

    private void move() {
        if (e != null) {
            int centerScreenX = (GameStateManager.gp.screenWidth - GameStateManager.gp.titleSize) / 2;
            int centerScreenY = (GameStateManager.gp.screenHeight - GameStateManager.gp.titleSize) / 2;

            if (e.getPos().x > centerScreenX && e.getPos().x < GameStateManager.gp.worldWidth - centerScreenX - GameStateManager.gp.titleSize ) {
                collisionCam.getPos().x = e.getPos().x - centerScreenX;
                Vector2f.setWorldVar(collisionCam.getPos().x, collisionCam.getPos().y);
            }
            if (e.getPos().y > centerScreenY && e.getPos().y < GameStateManager.gp.worldHeight - centerScreenY - GameStateManager.gp.titleSize ) {
                collisionCam.getPos().y = e.getPos().y - centerScreenY;
                Vector2f.setWorldVar(collisionCam.getPos().x, collisionCam.getPos().y);
            }
        }

    }

    public void target(GameObject e) {
        this.e = e;
        int centerScreenX = (GameStateManager.gp.screenWidth - GameStateManager.gp.titleSize) / 2;
        int centerScreenY = (GameStateManager.gp.screenHeight - GameStateManager.gp.titleSize) / 2;
        float camX = e.getPos().x - centerScreenX;
        float camY = e.getPos().y - centerScreenY;
        int maxCamX = GameStateManager.gp.worldWidth - GameStateManager.gp.screenWidth;
        int maxCamY = GameStateManager.gp.worldHeight - GameStateManager.gp.screenHeight;
        if(camX >= 0 && camX <= maxCamX)
            collisionCam.getPos().x = camX;
        else if (camX < 0)
            collisionCam.getPos().x = 0;
        else if (camX > maxCamX)
            collisionCam.getPos().x = maxCamX;

        if(camY >= 0 && camY <= maxCamY)
            collisionCam.getPos().y = camY;
        else if (camY < 0)
            collisionCam.getPos().y = 0;
        else if (camY > maxCamX)
            collisionCam.getPos().y = maxCamY;

        Vector2f.setWorldVar(collisionCam.getPos().x, collisionCam.getPos().y);
        if(e != null) {
            if (e instanceof Entity)
                speed = ((Entity) e ).getSpeed();
        } else {
            speed = 8;
        }
    }

    public void setSpeed(int speed) {this.speed = speed; }

    public void input(MouseHandler mouse, KeyHandler key) {

        if (e != null) {
            up = key.upPressed;
            down = key.downPressed;
            left = key.leftPressed;
            right = key.rightPressed;
        }
    }

}

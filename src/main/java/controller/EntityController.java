package controller;

import interfaces.controller.ICanGoByPath;
import interfaces.controller.ILocatable;
import org.jetbrains.annotations.NotNull;
import view.ai.Node;
import view.ai.PathFinder;
import view.main.GamePanel;
import view.math.Vector2f;
import view.states.GameStateManager;
import view.states.PlayState;
import view.title.TileCollision;
import view.utils.Direction;

import java.awt.event.ActionListener;
import java.util.Random;

public abstract class EntityController extends Controller implements ICanGoByPath {

    protected PathFinder pathFinder;
    private Direction direction;
    private ILocatable followedEntity;
    private boolean isRunning;
    private int speed;
    int maxSpeed;

    protected TileCollision tc;

    private boolean isMoveMyPathRunning;
    protected ActionListener toGoalListener = null;



    public EntityController(GamePanel gp, PlayState ps, int x, int y, int w, int h) {
        super(x, y, w, h);

        pathFinder = new PathFinder(gp, ps);
        tc = new TileCollision(this);
        direction = Direction.DOWN;

        maxSpeed = 3;
        speed = maxSpeed;
    }

    public EntityController(@NotNull GamePanel gp, PlayState ps,  Vector2f pos, int w, int h) {
        super(pos, w, h);

        pathFinder = new PathFinder(gp, ps);
        tc = new TileCollision(this);
        direction = Direction.DOWN;

        maxSpeed = 3;
        speed = maxSpeed;
    }


    @Override
    public boolean follow(ILocatable iLocatable) {
        this.followedEntity = iLocatable;
        pathFinder.setNodes(
                this, iLocatable
        );
        return pathFinder.search();
    }

    @Override
    public boolean unfollow() {
        if (followedEntity != null) {
            followedEntity = null;
            this.pathFinder.getPathList().removeAll(this.pathFinder.getPathList());
            return true;
        }
        return false;
    }

    @Override
    public boolean goTo(ILocatable iLocatable) {
        this.followedEntity = null;
        pathFinder.setNodes(
                this, iLocatable
        );
        return pathFinder.search();
    }

    @Override
    public boolean goTo(int x, int y) {
        this.followedEntity = null;
        pathFinder.setNodes(
                this, x, y
        );
        return pathFinder.search();
    }

    @Override
    public boolean isRunning() {
        return speed > 0;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int getMaxSpeed() {
        return this.maxSpeed;
    }

    @Override
    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean move() {
        switch (direction) {
            case UP:
                if (!tc.collisionTile(0, - speed))
                    getPos().addY(-speed);
                else
                    return false;
                break;
            case DOWN:
                if (!tc.collisionTile(0, + speed))
                    getPos().addY(speed);
                else
                    return false;
                break;
            case RIGHT:
                if (!tc.collisionTile(speed, 0))
                    getPos().addX(speed);
                else
                    return false;
                break;
            case LEFT:
                if (!tc.collisionTile(-speed, 0))
                    getPos().addX(-speed);
                else
                    return false;
                break;
        }

        return true;
    }

    @Override
    public void stop() {
        this.speed = 0;
        unfollow();
    }

    private void moveByPath () {
        // run to goal
        GamePanel gp = GameStateManager.gp;

        if(pathFinder.getPathList().size() > 0) {
            isMoveMyPathRunning = true;
            Node next = pathFinder.getPathList().get(0);
            if (this.getPos().x > next.column * gp.titleSize) {
                this.getPos().x -= getSpeed();
                direction = Direction.LEFT;
            } else
            if (this.getPos().x < next.column * gp.titleSize) {
                this.getPos().x += getSpeed();
                direction = Direction.RIGHT;
            } else
            if (this.getPos().y > next.row * gp.titleSize) {
                this.getPos().y -= getSpeed();
                direction = Direction.UP;
            } else
            if (this.getPos().y < next.row * gp.titleSize) {
                this.getPos().y += getSpeed();
                direction = Direction.DOWN;
            } else {
                // remove node
                pathFinder.getPathList().remove(0);
            }
        } else {
            if (isMoveMyPathRunning) {
                isMoveMyPathRunning = false;
                if(this.toGoalListener != null)
                    this.toGoalListener.actionPerformed(null);
            }
        }
    }

    public void updateRandomDirection () {
        Random random = new Random();
        switch (random.nextInt() % 4) {
            case 0:
                if (!tc.collisionTile(0, - speed))
                    this.direction = Direction.UP;
                else
                    this.direction = Direction.DOWN;
                break;
            case 1:
                if (!tc.collisionTile(0, + speed))
                    this.direction = Direction.DOWN;
                else
                    this.direction = Direction.UP;
                break;
            case 2:
                if (!tc.collisionTile(speed, 0))
                    this.direction = Direction.RIGHT;
                else
                    this.direction = Direction.LEFT;
                break;
            case 3:
                if (!tc.collisionTile(-speed, 0))
                    this.direction = Direction.LEFT;
                else
                    this.direction = Direction.RIGHT;
                break;
        }
    }

    @Override
    public void update() {

        if (followedEntity != null) {
            follow(followedEntity);
            pathFinder.search();
        }

        this.moveByPath();

    }

}

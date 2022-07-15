package controller.gameAttr;

import interfaces.controller.IMovable;
import org.jetbrains.annotations.NotNull;
import view.math.AABB;
import view.math.Vector2f;
import view.title.TileCollision;
import view.utils.Direction;

public class Movement {
    private final Vector2f position;
    private final AABB bounds;
    private Direction direction;
    private int defaultSpeed;
    private int speed;
    private final TileCollision tc;

    public Movement (AABB bounds, Direction direction, TileCollision tc, int defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
        this.direction = direction;
        this.bounds = bounds;
        this.position = bounds.getPos();
        this.tc = tc;
    }

    public boolean isRunning() {
        return speed > 0;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(@NotNull Direction direction) {
        this.direction = direction;
    }

    private boolean move(TileCollision tc) {
        switch (direction) {
            case UP:
                if (!tc.collisionTile(0, - speed))
                    position.addY(-speed);
                else
                    return false;
                break;
            case DOWN:
                if (!tc.collisionTile(0, + speed))
                    position.addY(speed);
                else
                    return false;
                break;
            case RIGHT:
                if (!tc.collisionTile(speed, 0))
                    position.addX(speed);
                else
                    return false;
                break;
            case LEFT:
                if (!tc.collisionTile(-speed, 0))
                    position.addX(-speed);
                else
                    return false;
                break;
        }

        return true;
    }

    public boolean move () {
        if (this.tc != null) {
            return move(this.tc);
        } else {
            switch (direction) {
                case UP:
                        position.addY(-speed);
                    break;
                case DOWN:
                        position.addY(speed);
                    break;
                case RIGHT:
                        position.addX(speed);
                    break;
                case LEFT:
                        position.addX(-speed);
                    break;
            }
            return true;
        }
    }

    public void stop () {
        this.speed = 0;
    }

    public void run () {
        this.speed = defaultSpeed;
    }
}

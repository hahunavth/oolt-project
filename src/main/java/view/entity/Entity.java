package view.entity;

import interfaces.controller.ILocatable;
import org.jetbrains.annotations.NotNull;
import view.states.PlayState;
import view.ai.Node;
import view.ai.PathFinder;
import view.component.FlyUpNumber;
import view.graphics.Sprite;
import view.graphics.SpriteAnimation;
import view.graphics.SpriteSheet;
import view.main.GamePanel;
import view.title.TileCollision;
import view.utils.Direction;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.List;

@Deprecated
public abstract class Entity extends GameObject implements ILocatable {
    private int speed;
    BufferedImage image;

    public Direction direction;
    protected int currentAnimation;
    protected SpriteSheet sprite;
    protected SpriteAnimation ani;
    protected PathFinder pathFinder;
    protected TileCollision tc;

    protected Entity followedEntity;
    protected boolean searchedMark = false;

    protected final FlyUpNumber flyUpNumber;

    protected ActionListener toGoalListener = null;
    private boolean isGoingToGoal = false;

    /**
     * WARNING: DO NOT USE THIS
     * setImage();
     * call an overridable (public or protected) method in constructor can cause error.
     *
     * There are a few more restrictions that a class must obey to allow inheritance. Constructors must not invoke overridable methods, directly or indirectly. If you violate this rule, program failure will result. The superclass constructor runs before the subclass constructor, so the overriding method in the subclass will be invoked before the subclass constructor has run. If the overriding method depends on any initialization performed by the subclass constructor, the method will not behave as expected.
     * @see <a href="https://stackoverflow.com/questions/3404301/whats-wrong-with-overridable-method-calls-in-constructors">Stackoverflow</a>
     */
    public Entity (GamePanel gp, PlayState ps) {
        super(gp, ps);

        ani = new SpriteAnimation();
        pathFinder = new PathFinder(gp, ps);
        tc = new TileCollision(this);

        this.flyUpNumber = new FlyUpNumber(99999, Color.BLUE, this.bounds);

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

    /**
     * Entity.setImage:
     * <ul>
     *     <li>
     *         Override để set sprite sheet image.
     *     </li>
     *     <li>
     *         Gọi setImage ở constructor của lớp con. (KO GỌI Ở LỚP CHA (AnimalEntity) !!!)
     *     </li>
     * </ul>
     */
    public abstract void setImage();

    /**
     * Entity.setAction:
     * <ul><li>
     * random hươngs di chuyển và tư thế cho con vật
     * </li></ul>
     */
    public void setAction() {}

    // entity
    public void setAnimation(int i, Sprite[] frames, int delay) {
        currentAnimation = i;
        ani.setFrames(i, frames);
        ani.setDelay(delay);
    }

    public void setAnimation (int i, List<Sprite> spriteList, int delay) {
        Sprite[] sprites = new Sprite[spriteList.size()];
        spriteList.toArray(sprites);
        setAnimation(i, sprites, delay);
    }

    /**
     * Entity.anmage:
     * <ul>
     *     <li>
     *         gọi setAnimation khi thay đổi hướng chạy, tư thế (đứng, nằm)...
     *     </li>
     * </ul>
     * Ex:
     * <pre>
     * if (direction == Direction.DOWN) {
     *     setAnimation(RUN_DOWN, sprite.getSpriteArray(RUN_DOWN), 10);
     * }
     * </pre>
     * @see view.entity.Player#animate(boolean)
     */
    public abstract void animate(boolean isRunning);

    /**
     * AnimalEntity.checkCollisionAndMove:
     * <p>
     * Check va chạm, nếu ko va vòa tường thì di chuyển theo thược tính dierection.
     * </p>
     */
    public void checkCollisionAndMove(Direction direction, int speed) {
        if (speed > 0)
            if (direction == Direction.UP) {
                if (!tc.collisionTile(0, - speed)) {
                    pos.addY(-speed);
                }else {
                    Random random = new Random();
                    int i = random.nextInt(4);
                    switch (i) {
                        case 1:
                            this.direction = Direction.UP;
                            break;
                        case 2:
                            this.direction = Direction.DOWN;
                            break;
                        case 3:
                            this.direction = Direction.RIGHT;
                            break;
                        case 0:
                            this.direction = Direction.LEFT;
                            break;
                    }
                }
            }
            else if (direction == Direction.DOWN) {
                if (!tc.collisionTile(0, speed)) {
                    pos.addY(speed);
                }else {
                    Random random = new Random();
                    int i = random.nextInt(4);
                    switch (i) {
                        case 1:
                            this.direction = Direction.UP;
                            break;
                        case 2:
                            this.direction = Direction.DOWN;
                            break;
                        case 3:
                            this.direction = Direction.RIGHT;
                            break;
                        case 0:
                            this.direction = Direction.LEFT;
                            break;
                    }
                }
            }
            else if (direction == Direction.RIGHT) {
                if (!tc.collisionTile(speed, 0)) {
                    pos.addX(speed);
                }else {
                    Random random = new Random();
                    int i = random.nextInt(4);
                    switch (i) {
                        case 1:
                            this.direction = Direction.UP;
                            break;
                        case 2:
                            this.direction = Direction.DOWN;
                            break;
                        case 3:
                            this.direction = Direction.RIGHT;
                            break;
                        case 0:
                            this.direction = Direction.LEFT;
                            break;
                    }
                }
            }
            else if (direction == Direction.LEFT) {
                if (!tc.collisionTile(-speed, 0)) {
                    pos.addX(-speed);
                }else {
                    Random random = new Random();
                    int i = random.nextInt(4);
                    switch (i) {
                        case 1:
                            this.direction = Direction.UP;
                            break;
                        case 2:
                            this.direction = Direction.DOWN;
                            break;
                        case 3:
                            this.direction = Direction.RIGHT;
                            break;
                        case 0:
                            this.direction = Direction.LEFT;
                            break;
                    }
                }
            }
    }

    public void moveByPath () {
        // run to goal
        if(pathFinder.getPathList().size() > 0) {
            isGoingToGoal = true;
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
            if (isGoingToGoal) {
                isGoingToGoal = false;
                if(this.toGoalListener != null)
                    this.toGoalListener.actionPerformed(null);
            }
        }
    }

    public void setToGoalListener(ActionListener actionListener) {
        this.toGoalListener = actionListener;
    }

    public void removeToGoalListener () {
        this.toGoalListener = null;
    }

    public void follow (Entity entity) {
        this.followedEntity = entity;
        this.searchedMark = false;
        pathFinder.setNodes(
                this, (GameObject) entity
        );
    }

    public void unfollow () {
        this.followedEntity = null;
        this.searchedMark = true;
        this.pathFinder.getPathList().removeAll(this.pathFinder.getPathList());
    }

    public boolean goTo (@NotNull GameObject gameObject) {

        return this.goTo(
                (int) gameObject.getPos().x,
                (int) gameObject.getPos().y
        ) || this.getBounds().distance(gameObject.getBounds().getPos()) < 60;
    }

    public boolean goTo (int x, int y) {
        this.followedEntity = null;
        this.searchedMark = false;
        pathFinder.setNodes(
                (int) this.pos.x,
                (int) this.pos.y,
                x,
                y
        );
        searchedMark = true;

        return pathFinder.search();
    }


//    public abstract void input(MouseHandler mouseH, KeyHandler keyH);

    /**
     * Entity.update:
     * <ul>
     *     <li>
     *         update animation.
     *     </li>
     *     <li>
     *         Follow or goTo
     *     </li>
     * </ul>
     */
    public void update() {
        ani.update();
        flyUpNumber.update();

        if (!searchedMark) {
            if (followedEntity != null) {   // follow
                follow(ps.player);
                pathFinder.search();
            } else {                        // goto
                // search 1 time in goTo method
            }
        }

        this.moveByPath();

    };

    /**
     * Entity.draw:
     * <ul>
     *   <li>
     *     Vẽ hình con vật ( set thís.image )
     *   </li>
     *   <li>
     *       Khi override cần gọi super.draw(g2) để không mất hình con vật.
     *   </li>
     * </ul>
     */
    @Override
    public void draw (@NotNull Graphics2D g2) {

        // draw this.image
        g2.drawImage(image, (int) this.pos.getScreenX(), (int) this.pos.getScreenY(), gp.titleSize, gp.titleSize, null);

        flyUpNumber.draw(g2);

    }
}

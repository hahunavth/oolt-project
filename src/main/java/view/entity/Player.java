package view.entity;

import org.jetbrains.annotations.NotNull;
import view.states.PlayState;
import view.graphics.SpriteSheet;
import view.ai.Node;
import view.effect.FocusManager;
import view.main.*;
import view.math.Vector2f;
import view.object.SuperObject;
import view.utils.ImageSplitter;
import view.utils.Direction;

import java.awt.*;
import java.util.ArrayList;

public class Player extends Entity {

    private final FocusManager focusManager;

    private Direction prevDirection;
    private boolean isRunning;

    public int UP = 3;
    public int DOWN = 2;
    public int LEFT = 1;
    public int RIGHT = 0;

    public int RUN_UP = 7;
    public int RUN_DOWN = 6;
    public int RUN_LEFT = 5;
    public int RUN_RIGHT = 4;


    private AnimalEntity animalEntity;
    private final ArrayList<SuperObject> superObjects;
    private boolean isGoingToMousePosition;
    private int prevMouseBtn;
    private int mouseBtn;
    private Vector2f mousePos;


    public Player(GamePanel gp, PlayState ps, Camera camera) {
        super(gp, ps);
        setImage();

        this.focusManager   = new FocusManager(gp, ps);

        this.ps = ps;

        this.bounds.setXOffset(8);
        this.bounds.setYOffset(28);
        this.bounds.setWidth(48 - 16);
        this.bounds.setHeight(48 - 28);

        superObjects = new ArrayList<>();

        setDefaultValue();

    }

    public void setDefaultValue () {
        pos.setX ((gp.worldWidth - gp.titleSize) / 2.0f) ;
        pos.setY ((gp.worldHeight - gp.titleSize) / 2.0f + 100);
        setSpeed(4);
        direction = Direction.DOWN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setImage() {
        String image = "/sprout-lands-sprites/characters/basic-charakter-spritesheet.png";
        sprite = new SpriteSheet(8, 2);
        System.out.println("Load image: " + image);
        ImageSplitter ci = new ImageSplitter(gp, image, 48, 48, 32);
        System.out.println( "\t>> col: " + ci.getColumns() + ", rows: " + ci.getRows());

        for(int i = 0; i < 2; i++) {
            sprite.addSprite(UP, ci.getSubImage(1, i))
                    .addSprite(DOWN, ci.getSubImage(0, i))
                    .addSprite(LEFT, ci.getSubImage(2, i))
                    .addSprite(RIGHT, ci.getSubImage(3, i));
        }
        for(int i = 2; i < 4; i++) {
            sprite.addSprite(RUN_UP, ci.getSubImage(1, i))
                    .addSprite(RUN_DOWN, ci.getSubImage(0, i))
                    .addSprite(RUN_LEFT, ci.getSubImage(2, i))
                    .addSprite(RUN_RIGHT, ci.getSubImage(3, i));
        }

        setAnimation(RIGHT, sprite.getSpriteArray(DOWN), 10);   // DEFAULT ANIMATION
    }

    /**
     * {@inheritDoc}
     * Player.animate:
     * Custom player khi chạy và không chạy.
     */
    public void animate(boolean isRunning) {
        if (prevDirection == direction && this.isRunning == isRunning ) {
            return;
        } else {
            prevDirection = direction;
            this.isRunning = isRunning;
            prevMouseBtn = mouseBtn;
        }
        if ( sprite != null) // if setImage not error
            if (!isRunning && !isGoingToMousePosition)
            {
                if (direction == Direction.DOWN) {
                    setAnimation(DOWN, sprite.getSpriteArray(DOWN), 30);
                }
                else if (direction == Direction.UP) {
                    setAnimation(UP, sprite.getSpriteArray(UP), 30);
                }
                else if (direction == Direction.LEFT) {
                    setAnimation(LEFT, sprite.getSpriteArray(LEFT), 30);
                }
                else if (direction == Direction.RIGHT) {
                    setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 30);
                }
            } else {
                if (direction == Direction.DOWN) {
                    setAnimation(RUN_DOWN, sprite.getSpriteArray(RUN_DOWN), 10);
                }
                else if (direction == Direction.UP) {
                    setAnimation(RUN_UP, sprite.getSpriteArray(RUN_UP), 10);
                }
                else if (direction == Direction.LEFT) {
                    setAnimation(RUN_LEFT, sprite.getSpriteArray(RUN_LEFT), 10);
                }
                else if (direction == Direction.RIGHT) {
                    setAnimation(RUN_RIGHT, sprite.getSpriteArray(RUN_RIGHT), 10);
                }
            }
    }

    public void targetAnimal (AnimalEntity animal) {
        if (this.animalEntity != null)
            this.animalEntity.setFocused(false);

        animal.setFocused(true);
        this.animalEntity = animal;
    }

    public void targetSuperObject (SuperObject superObject) {
        if (superObjects.contains(superObject))
            superObjects.add(superObject);
    }

    public void input(MouseHandler mouseH, KeyHandler keyH) {


        animate((keyH.rightPressed || keyH.upPressed || keyH.downPressed || keyH.leftPressed || mouseH.getButton() != -1));

        mouseBtn = mouseH.getButton();
        if (mouseH.getButton() != -1) {
            pathFinder.setNodes(
                    (int) this.getBounds().getCenterX(),
                    (int) this.getBounds().getCenterY(),
                    (int) - Vector2f.getStaticScreenX(0) + mouseH.getX(),
                    (int) - Vector2f.getStaticScreenY(0) + mouseH.getY()
            );
            pathFinder.search();
            mousePos = new Vector2f(
                    (int) - Vector2f.getStaticScreenX(0) + mouseH.getX(),
                    (int) - Vector2f.getStaticScreenY(0) + mouseH.getY()
                    );
            isGoingToMousePosition = true;
        }

        if (!isGoingToMousePosition) {
            pathFinder.getPathList().clear();
        }

        // run or
        if (pathFinder.getPathList().size() > 0 ) {
            this.isRunning = true;
            Node node = pathFinder.getPathList().get(0);
            if (
                    this.getPos().x == node.column * gp.titleSize
                    && this.getPos().y == node.row * gp.titleSize
            ) {
                pathFinder.getPathList().remove(0);
                if (pathFinder.getPathList().size() == 0) {
                    if (isGoingToMousePosition) {
                        isGoingToMousePosition = false;
                        animate(false);
                    }
                }
            }
        }

        if(keyH.upPressed) {
            direction = Direction.UP;
            isRunning = true;
            isGoingToMousePosition = false;
        }
        else if(keyH.downPressed) {
            direction = Direction.DOWN;
            isRunning = true;
            isGoingToMousePosition = false;
        }
        else if(keyH.leftPressed) {
            direction = Direction.LEFT;
            isRunning = true;
            isGoingToMousePosition = false;
        }
        else if(keyH.rightPressed) {
            direction = Direction.RIGHT;
            isRunning = true;
            isGoingToMousePosition = false;
        } else {
            isRunning = false;
        }
        // run
        if(isRunning)
            checkCollisionAndMove(direction, getSpeed());

        // Check object collision
        int objIndex = ps.cChecker.checkObject(this, true);
        targetNewObject(objIndex);
        this.focusManager.checkAndHoverObject(objIndex);

        // Handle focus
        if (keyH.enterPressed) {
            this.focusManager.checkAndFocusObject();
        }
    }

    /**
     * Handle when player collision with object
     * @param index: index of object
     */
    public void targetNewObject(int index) {
        if( index != 999) {
            String objName = ps.obj[index].name;
//            System.out.println(objName);
            // remove item
            // gp.obj[index] = null;
            if(this.focusManager.getHoveredObjId() == index && this.focusManager.isNewHovered()) {
//                ps.playSE(2);
//                ps.ui.showMessage("Hover: " + this.focusManager.getHoveredObjId());
                this.focusManager.setNewHovered(false);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void draw (@NotNull Graphics2D g2) {

        // draw player
        g2.drawImage(ani.getImage().image, (int) pos.getScreenX(), (int) pos.getScreenY(), gp.titleSize, gp.titleSize, null);

        // TEST: draw character image frame
         g2.drawRect(
                 (int) this.getBounds().getPos().getScreenX() + (int) this.bounds.getXOffset(),
                 (int) this.getBounds().getPos().getScreenY() + (int) this.bounds.getYOffset(),
                 (int) this.getBounds().getWidth(),
                 (int) this.getBounds().getHeight());

         if (pathFinder.getPathList().size() > 0) {
             g2.drawRect( (int) mousePos.getScreenX() - gp.titleSize / 2, (int) mousePos.getScreenY() - gp.titleSize / 2, gp.titleSize, gp.titleSize);

             pathFinder.draw(g2);
         }

    }

    @Override
    public int getWorldX() {
        return (int) this.getPos().x;
    }

    @Override
    public int getWorldY() {
        return (int) this.getPos().y;
    }
}

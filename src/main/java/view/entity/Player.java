package view.entity;

import states.PlayState;
import view.graphics.SpriteSheet;
import view.ai.Node;
import view.ai.PathFinder;
import view.effect.FocusManager;
import view.main.*;
import view.math.Vector2f;
import view.object.SuperObject;
import view.title.TileCollision;
import view.utils.ImageSplitter;
import view.utils.Direction;

import java.awt.*;
import java.util.ArrayList;

public class Player extends Entity {

//    public final int screenX, screenY;
    private final FocusManager focusManager;

    // instead of BufferedImage
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
    private Camera camera;

//    private boolean xCollision;
//    private boolean yCollision;
    private TileCollision tc;

    private AnimalEntity animalEntity;
    private ArrayList<SuperObject> superObjects;
    private PathFinder pathFinder;
    private boolean isGoingToMousePosition;
    private Vector2f mousePos;

    public Player(GamePanel gp, PlayState ps, Camera camera) {
        super(gp, ps);
        this.camera = camera;
        this.focusManager   = new FocusManager(gp, ps);

        // collision
//        solidArea   = new Rectangle();
//        solidArea.x = 10;
//        solidArea.y = 24;
//        solidArea.height    = 24;
//        solidArea.width     = 28;

        this.ps = ps;
        // object collision
//        solidAreaDefaultX = solidArea.x;
//        solidAreaDefaultY = solidArea.y;

        this.bounds.setXOffset(8);
        this.bounds.setYOffset(28);
        this.bounds.setWidth(48 - 16);
        this.bounds.setHeight(48 - 28);

        tc = new TileCollision(this);

        superObjects = new ArrayList<>();

        pathFinder = new PathFinder(gp, ps);

        setDefaultValue();
        setImage();
    }

    public void setDefaultValue () {
//        setWorldX(gp.worldWidth / 2 - 2 * gp.titleSize);
//        setWorldY(gp.worldHeight / 2 - 4 * gp.titleSize);
        pos.setX ((gp.worldWidth - gp.titleSize) / 2.0f) ;
        pos.setY ((gp.worldHeight - gp.titleSize) / 2.0f + 100);
        setSpeed(4);
        direction = Direction.DOWN;
    }

    @Override
    public void setImage() {
        ImageSplitter ci = new ImageSplitter(gp,"/sprout-lands-sprites/characters/basic-charakter-spritesheet.png", 48, 48, 32);

        sprite = new SpriteSheet(8, 2);

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

        setAnimation(RIGHT, sprite.getSpriteArray(DOWN), 10);
    }

    public void animate(boolean isRunning) {
        if (prevDirection == direction && this.isRunning == isRunning) {
            return;
        } else {
            prevDirection = direction;
            this.isRunning = isRunning;
        }

        if (!isRunning)
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
//        if (!animalEntities.contains(animal))
//            animalEntities.add(animal);
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
        animate((keyH.rightPressed || keyH.upPressed || keyH.downPressed || keyH.leftPressed));

        if (mouseH.getButton() != -1 && pathFinder.getPathList().size() == 0) {
            pathFinder.setNodes(
                    (int) this.pos.x,
                    (int) this.pos.y,
                    (int) - Vector2f.getWorldVarX(0) + mouseH.getX(),
                    (int) - Vector2f.getWorldVarY(0) + mouseH.getY(),
                    this
            );
            pathFinder.search();
            mousePos = new Vector2f(
                    (int) - Vector2f.getWorldVarX(0) + mouseH.getX(),
                    (int) - Vector2f.getWorldVarY(0) + mouseH.getY()
                    );
            isGoingToMousePosition = true;
        }

        if (!isGoingToMousePosition) {
            pathFinder.getPathList().clear();
        }
//            System.out.println(pathFinder.getPathList());
            Node node;
            if (pathFinder.getPathList().size() > 0 ) {
                node = pathFinder.getPathList().get(0);
                if (this.getPos().x > node.column * gp.titleSize) {
                    this.getPos().x -= getSpeed();
                    camera.getPos().x -= getSpeed();
                    Vector2f.setWorldVar(camera.getPos().x, camera.getPos().y);
                    direction = Direction.LEFT;
                } else if (this.getPos().x < node.column * gp.titleSize) {
                    this.getPos().x += getSpeed();
                    camera.getPos().x += getSpeed();
                    Vector2f.setWorldVar(camera.getPos().x, camera.getPos().y);
                    direction = Direction.RIGHT;
                } else if (this.getPos().y > node.row * gp.titleSize) {
                    this.getPos().y -= getSpeed();
                    camera.getPos().y -= getSpeed();
                    Vector2f.setWorldVar(camera.getPos().x, camera.getPos().y);
                    direction = Direction.UP;
                } else if (this.getPos().y < node.row * gp.titleSize) {
                    this.getPos().y += getSpeed();
                    camera.getPos().y += getSpeed();
                    Vector2f.setWorldVar(camera.getPos().x, camera.getPos().y);
                    direction = Direction.DOWN;
                } else
                pathFinder.getPathList().remove(0);
        }

        if(keyH.upPressed) {
            direction = Direction.UP;
            isGoingToMousePosition = false;
        }
        else if(keyH.downPressed) {
            direction = Direction.DOWN;
            isGoingToMousePosition = false;
        }
        else if(keyH.leftPressed) {
            direction = Direction.LEFT;
            isGoingToMousePosition = false;
        }
        else if(keyH.rightPressed) {
            direction = Direction.RIGHT;
            isGoingToMousePosition = false;
        }

        // Check tile collision
        collisionOn = false;
        ps.cChecker.checkTile(this);
        // Check object collision
        int objIndex = ps.cChecker.checkObject(this, true);
        targetNewObject(objIndex);
//        targetAnimal();
        this.focusManager.checkAndHoverObject(objIndex);

//        System.out.println(tc.collisionTile(getSpeed(), 0));

        // if collision is false can move
//        if (!collisionOn) {
            /**
             * TODO: handle multi direction ( press multi key )
             */
            if (keyH.upPressed) {
                if (!tc.collisionTile(0, - getSpeed())) {
                    pos.addY(-getSpeed());
                    collisionOn = false;
                }
                else collisionOn = true;
            }
            else if (keyH.downPressed) {
                if (!tc.collisionTile(0, getSpeed())) {
                    pos.addY(getSpeed());
                    collisionOn = false;
                }
                else collisionOn = true;
            }
            else if (keyH.rightPressed) {
                if (!tc.collisionTile(getSpeed(), 0)) {
                    pos.addX(getSpeed());
                    collisionOn = false;
                }
                else collisionOn = true;
            }
            else if (keyH.leftPressed) {
                if (!tc.collisionTile(-getSpeed(), 0)) {
                    pos.addX(-getSpeed());
                    collisionOn = false;
                }
                else collisionOn = true;
            }
//        }

        // Handle focus
        if (keyH.enterPressed) {
            this.focusManager.checkAndFocusObject();
        }

        /**
         * NOTE: Player run animation
         */
//        if(keyH.rightPressed || keyH.upPressed || keyH.downPressed || keyH.leftPressed) {
//            spriteCounter++;
//            if(spriteCounter > 12) {
//                if(spriteNum == 1) {
//                    spriteNum = 2;
//                } else if (spriteNum == 2) {
//                    spriteNum = 1;
//                }
//                spriteCounter = 0;
//            }
//        } else {
//            spriteCounter++;
//            if(spriteCounter > 36) {
//                if(spriteNum == 1) {
//                    spriteNum = 2;
//                } else if (spriteNum == 2) {
//                    spriteNum = 1;
//                }
//                spriteCounter = 0;
//            }
//        }
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

    public void draw (Graphics2D g2) {
        /**
         * NOTE: Stop moving camera at the edge
         */
//        if (screenX > getWorldX()) {
//            x = getWorldX();
//        }
//        if (screenY > getWorldY() ) {
//            y = getWorldY();
////        }
//        int rightOffset = gp.screenWidth - (int) Vector2f.getWorldVarX(pos.x);
//        if (rightOffset > gp.worldWidth - ps.player.getWorldX()) {
//            x = gp.screenWidth - (gp.worldWidth - getWorldX());
//        }
//        int bottomOffset = gp.screenHeight - (int) Vector2f.getWorldVarY(pos.x);
//        if (bottomOffset > gp.worldHeight - ps.player.getWorldY()) {
//            y = gp.screenHeight - (gp.worldHeight - getWorldY());
//        }

//        g2.drawImage(image, x, y, gp.titleSize, gp.titleSize, null);
        g2.drawImage(ani.getImage().image, (int) pos.getWorldVar().x, (int) pos.getWorldVar().y, gp.titleSize, gp.titleSize, null);
//        System.out.println(this.focusManager.getFocusedObjId());
        if (this.focusManager.getFocusedObjId() != 999) {
            GameObject selectedAnimal = ps.obj[this.focusManager.getFocusedObjId()];
            if ( selectedAnimal instanceof  AnimalEntity) {
                ps.ui.showMessageList(
                        ((AnimalEntity) selectedAnimal).getAnimalStatus()
                );
            }
        }
        // TEST: draw character image frame
         g2.drawRect(
                 (int) this.getBounds().getPos().getWorldVar().x + (int) this.bounds.getXOffset(),
                 (int) this.getBounds().getPos().getWorldVar().y + (int) this.bounds.getYOffset(),
                 (int) this.getBounds().getWidth(),
                 (int) this.getBounds().getHeight());

         if (pathFinder.getPathList().size() > 0) {
             g2.drawRect( (int) mousePos.getWorldVar().x - gp.titleSize / 2, (int) mousePos.getWorldVar().y - gp.titleSize / 2, gp.titleSize, gp.titleSize);
         }
    }
}

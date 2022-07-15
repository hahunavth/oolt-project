package view.entity;
import model.Activities.*;
import model.Animals.Animal;
import view.states.PlayState;
import view.ai.Node;
import view.ai.PathFinder;
import view.graphics.SpriteSheet;
import view.main.GamePanel;
import view.utils.ImageSplitter;
import view.utils.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static basic.Params.*;
import static basic.Params.HOME;

public class CatEntity extends AnimalEntity{
    private int counter;
    private int lifeCounter;
    private int actionLockCounter;
    private int directionLockCounter;
    // Có flip ảnh hay không
    public static final int NOFLIP = 0;
    public static final int FLIP = 5;
    // Tư thế
    public static final int STAND = 0;
    public static final int EAT = 1;
    public static final int SIT = 2;
    public static final int LEAP = 3;
    public static final int SLEEP = 4;
    //Hành động
    public static final int DiChoi = 1;
    public static final int DiChoi2 = 2;
    public static final int LiemLong = 3;
    public static final int AnUong = 4;
    public static final int Ngu = 5;

    public int prevPosture;
    public int posture;

    private static int activity;

    private PathFinder pathFinder;

    /**
     * DOWN + STAND
     */

    public CatEntity (GamePanel gp, PlayState ps) {
        super(gp, ps);

        this.setSpeed(1);
        this.direction = Direction.DOWN;
        posture = STAND;
        activity = DiChoi;

        setImage();
        setAnimation(
                FLIP,
                sprite.getSpriteArray(FLIP + posture),
                12
        );
        pathFinder = new PathFinder(gp, ps);
    }

    public CatEntity (GamePanel gp, PlayState ps, Animal animal) {
        this(gp, ps);
        this.animal = animal;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setImage() {
        String image = "/cat/cat.png";
        System.out.println("Load Image: " + image + " >> " + sprite);
        ImageSplitter ci = new ImageSplitter(gp, image, 256/8, 320/10, 0);
        System.out.println( "\t>> col: " + ci.getColumns() + ", rows: " + ci.getRows());

        BufferedImage[] imgs = new BufferedImage[80];
        BufferedImage[] flipImgs = new BufferedImage[80];


        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 8; j++) {
                imgs[i*4+j] = ci.getSubImage(i, j);
                flipImgs[i*4+j] = ci.getFlipSubImage(i, j);
//                this.sprite.addSprite(UP, ci.getSubImage(i, j)) ;
//                this.sprite.addSprite(LEFT, ci.getSubImage(i, j)) ;
//                this.sprite.addSprite(DOWN, ci.getFlipSubImage(i, j)) ;
//                this.sprite.addSprite(RIGHT, ci.getFlipSubImage(i, j)) ;
            }
        }

        // Mảng gồm index của các ảnh trong 1 động tác.
        int[][] actIds = {
                {17,18,19,20,21,22,23,24,17,18,19,20,21,22,23,24},   // STAND
                {1,2,3,4},   // EAT
                {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16},    // SIT
                {33,34,35,36},   // LEAP
                {25,26,27,28} //SLEEP
        };

        // spritesheet
        this.sprite = new SpriteSheet(8, 16);
        for (int i = 0; i < actIds.length; i++) {
            int[] ids = actIds[i];
            for (int j : ids) {
                this.sprite.addSprite(
                        NOFLIP + i,
                        imgs[j-1]
                );
                this.sprite.addSprite(
                        FLIP + i,
                        imgs[j-1]
                );
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAction () {
        // TODO: GENERIC
        if(counter == 0) {
//            if (lifeCounter == 15 * 24 * 60) {
//                lifeCounter = 0;
//            }
//            if (this.animal != null)
//                this.animal.life(
//                        lifeCounter / (24 * 60),
//                        lifeCounter / (60) % 24,
//                        lifeCounter % 60
//                );
//            // NOTE: De counter xuat phat tu 0
//            lifeCounter++;
        }

        actionLockCounter++;

        if(actionLockCounter > 60*60*15 && !animal.isHungry() && !animal.isThirsty() && !animal.isSick()){
            assert animal != null;
            Activity randomAct = animal.getActivity();
            if (randomAct instanceof EatActivity)
            {
                activity = AnUong;
            }
            else if (randomAct instanceof DrinkActivity)
            {
                activity = AnUong;
            }
            else if (randomAct instanceof PlayActivity){
                double temp = rand.nextDouble();
                if(temp >0 && temp < 0.3)
                    activity = DiChoi;
                else if(temp >=0.3 && temp < 0.6)
                    activity = DiChoi2;
                else activity = LiemLong;
            }
            else if (randomAct instanceof SleepActivity)
            {
                activity = Ngu;
            }
            actionLockCounter=0;
        }

        directionLockCounter++;
        if(directionLockCounter > 120) {
            Random random = new Random();
            int i = random.nextInt(4);
            switch (i) {
                case 1:
                    direction = Direction.UP;
                    break;
                case 2:
                    direction = Direction.DOWN;
                    break;
                case 3:
                    direction = Direction.RIGHT;
                    break;
                case 0:
                    direction = Direction.LEFT;
                    break;
            }
            directionLockCounter = 0;
        }

        // random tu the
        counter++;
        if(counter>=120) {
            counter = 0;
//            Random random = new Random();
//
//            if (activity == EAT) posture = STAND;
//            else if (activity == SIT) posture = SIT;
//            else if (activity == STAND){
//                //hoạt động play
//                if(random.nextDouble()<0.5){
//                    posture = STAND;
//                }else {
//                    posture = LEAP;
//                }
//            }
//
//            if(posture == SIT) {
//                setSpeed(0);
//            } else if(posture == LEAP) {
//                setSpeed(2);
//            }else setSpeed(1);
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void animate(boolean isRunning) {

        if (posture == prevPosture)
            return;
        prevPosture = posture;

        switch (direction) {
            case UP:
            case LEFT:
                setAnimation(
                        FLIP,
                        sprite.getSpriteArray(FLIP + posture),
                        12
                );
                break;
            case DOWN:
            case RIGHT:
                setAnimation(
                        NOFLIP,
                        sprite.getSpriteArray(NOFLIP + posture),
                        12
                );
                break;
        }
    }

    public void update () {
        setAction();
        if(activity == DiChoi){
            posture = STAND;
            setSpeed(1);
            checkCollisionAndMove(this.direction, this.getSpeed());
        }else if(activity == DiChoi2){
            posture = LEAP;
            setSpeed(2);
            checkCollisionAndMove(this.direction, this.getSpeed());
        }else if(activity == LiemLong){
            posture = SIT;
            setSpeed(0);
        }else if(activity == AnUong){
            //đi ăn
            pathFinder.setNodes(
                    (int) this.getPos().x,
                    (int) this.getPos().y,
                    HOME[0],
                    HOME[1]
            );
            pathFinder.search();
            if(pathFinder.getPathList().size() > 0) {
                posture = STAND;
                setSpeed(1);
                Node next = pathFinder.getPathList().get(0);
                if (this.getPos().x > next.column * gp.titleSize) {
                    this.getPos().x -= getSpeed();
                } else
                if (this.getPos().x < next.column * gp.titleSize) {
                    this.getPos().x += getSpeed();
                } else
                if (this.getPos().y > next.row * gp.titleSize) {
                    this.getPos().y -= getSpeed();
                } else
                if (this.getPos().y < next.row * gp.titleSize) {
                    this.getPos().y += getSpeed();
                }else {
                    // remove node
                    pathFinder.getPathList().remove(0);
                }
            }else {
                posture = EAT;
                setSpeed(0);
            }
        }else if(activity == Ngu){
            //Đi ngủ
            pathFinder.setNodes(
                    (int) this.getPos().x,
                    (int) this.getPos().y,
                    THAM_TRONG_NHA[0],
                    THAM_TRONG_NHA[1]
            );
            pathFinder.search();
            if(pathFinder.getPathList().size() > 0) {
                posture = STAND;
                setSpeed(1);
                Node next = pathFinder.getPathList().get(0);
                if (this.getPos().x > next.column * gp.titleSize) {
                    this.getPos().x -= getSpeed();
                } else
                if (this.getPos().x < next.column * gp.titleSize) {
                    this.getPos().x += getSpeed();
                } else
                if (this.getPos().y > next.row * gp.titleSize) {
                    this.getPos().y -= getSpeed();
                } else
                if (this.getPos().y < next.row * gp.titleSize) {
                    this.getPos().y += getSpeed();
                }else {
                    // remove node
                    pathFinder.getPathList().remove(0);
                }
            }else {
                posture = SLEEP;
                setSpeed(0);
            }
        }else{
            posture = SIT;
            setSpeed(0);
        }
        animate(true);
        image = ani.getImage().image;
    }

    /**
     * {@inheritDoc}
     */
    public void draw (Graphics2D g2) {
        super.draw(g2);

        pathFinder.draw(g2);
    }

    @Override
    public int getWorldX() {
        return 0;
    }

    @Override
    public int getWorldY() {
        return 0;
    }
}

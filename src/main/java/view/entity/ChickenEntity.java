package view.entity;

import model.Activities.*;
import model.Animals.Animal;
import view.graphics.Sprite;
import view.states.PlayState;
import view.ai.PathFinder;
import view.graphics.SpriteSheet;
import view.main.GamePanel;
import view.utils.Direction;
import view.utils.ImageSplitter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.List;
public class ChickenEntity extends AnimalEntity {
    private int counter;
    private int lifeCounter;
    private int actionLockCounter;
    private int directionLockCounter;
    // Có flip ảnh hay không
    public static final int NOFLIP = 0;
    public static final int FLIP = 4;
    // TODO: 2. tăng flip lên 1 ( 4 + 1 = 5)

    // Tư thế
    public static final int STAND = 0;
    public static final int EAT = 1;
    public static final int SIT = 2;
    public static final int LEAP = 3;
    // TODO: 1. THÊM 1 TƯ THẾ Ở ĐÂY, CHO = 4

    //Hành động
    public static final int DiChoi = 1;
    public static final int AnUong = 2;
    public static final int Ngu = 3;

    public int prevPosture;
    public int posture;

    private static int activity;
    private Direction prevDirection;

    /**
     * DOWN + STAND
     */

    public ChickenEntity (GamePanel gp, PlayState ps) {
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

    public ChickenEntity (GamePanel gp, PlayState ps, Animal animal) {
        this(gp, ps);
        this.animal = animal;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setImage() {
        this.sprite = new SpriteSheet(8, 16);
        String image = "/chicken/chicken-sprite-sheet.png";
        System.out.println("Load Image: " + image);
        ImageSplitter ci = new ImageSplitter(gp, image, 32, 32, 0);
        System.out.println( "\t>> col: " + ci.getColumns() + ", rows: " + ci.getRows());

        BufferedImage[] imgs = new BufferedImage[36];
        BufferedImage[] flipImgs = new BufferedImage[36];

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                imgs[i*4+j] = ci.getSubImage(i, j);
                flipImgs[i*4+j] = ci.getFlipSubImage(i, j);
            }
        }

        // Mảng gồm index của các ảnh trong 1 động tác.
        int[][] actIds = {
//<<<<<<< HEAD
//                {1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4},   // STAND = 0
//                {5, 6, 7, 8, 5, 6, 7, 8, 5, 6, 7, 8},   // EAT
//                {9, 10, 11, 12, 12, 12, 12, 12, 12, 11, 10, 9 },    // SIT
//                {13, 13, 14, 14, 15, 15, 16, 16, 15, 14, 13, 12, 13}    // LEAP
//                // TODO: 3. Thêm index ảnh cho posture tương ứng ở đây
//=======
                {1,2,3,4,13,14,15,16},   // STAND
                {5,6,7,8},   // EAT
                {11,12,12,12,11,12,12,12,11,12,12,12},    // SIT
                {12,12,12,12,12,12,12,12,12,12}    // LEAP
        };

        // spritesheet
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
            if (lifeCounter == 15 * 24 * 60) {
                lifeCounter = 0;
            }
            if (this.animal != null)
                this.animal.life(
                        lifeCounter / (24 * 60),
                        lifeCounter / (60) % 24,
                        lifeCounter % 60
                );
            // NOTE: De counter xuat phat tu 0
            lifeCounter++;
        }

        if( animal.isHungry() || animal.isThirsty() || animal.isSick()){

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
                activity = DiChoi;
            }
            else if (randomAct instanceof SleepActivity)
            {
                activity = Ngu;
            }
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
//            if (activity == EAT) posture = EAT;
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
////                setSpeed(0);
//            } else {
//                setSpeed(1);
//            }

        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void animate(boolean isRunning) {

                // chet -> hien anh xam
        if (this.animal != null)
            if (this.animal.isDead()) {
                List<Sprite> spriteList = sprite.getGraySpriteArray(
                        FLIP + posture
                );

                setAnimation(
                        FLIP,
                        spriteList,
                        12
                );
            }


        if (direction == prevDirection && posture == prevPosture)
            return;
        prevPosture = posture;
        prevDirection = direction;

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

    @Override
    public void update () {

        // NOTE: Map Animal.activity -> AnimalEntity.Activity
        if (animal != null)
            if (animal.getActivity() instanceof EatActivity
                    || animal.getActivity() instanceof DrinkActivity) {
                activity = EAT;
            } else if (animal.getActivity() instanceof PlayActivity) {
                activity = STAND;
            } else {
                activity = SIT;
            }

        // NOTE: Handle for each AnimalEntity.Activity
        if(activity != EAT){
            checkCollisionAndMove(this.direction, this.getSpeed());
            unfollow();
            if (activity == SIT) {
                goTo(ps.obj[0]); // go to object key

            }
        } else {
            goToFoodTray();
        }
        animate(true);
        image = ani.getImage().image;
    }

//    public void update (double time) {
//        setAction();
//        if(activity == DiChoi){
//            posture = STAND;
//            setSpeed(1);
//            checkCollisionAndMove(this.direction, this.getSpeed());
//        }else if(activity == AnUong){
//            //đi ăn
//            pathFinder.setNodes(
//                    (int) this.getPos().x,
//                    (int) this.getPos().y,
//                    CHUONG_GA1[0],
//                    CHUONG_GA1[1]
//            );
//            pathFinder.search();
//            if(pathFinder.getPathList().size() > 0) {
//
//                posture = STAND;
//                setSpeed(1);
//                Node next = pathFinder.getPathList().get(0);
//                if (this.getPos().x > next.column * gp.titleSize) {
//                    this.getPos().x -= getSpeed();
//                } else
//                if (this.getPos().x < next.column * gp.titleSize) {
//                    this.getPos().x += getSpeed();
//                } else
//                if (this.getPos().y > next.row * gp.titleSize) {
//                    this.getPos().y -= getSpeed();
//                } else
//                if (this.getPos().y < next.row * gp.titleSize) {
//                    this.getPos().y += getSpeed();
//                }else {
//                    // remove node
//                    pathFinder.getPathList().remove(0);
//                }
//            }else {
//                posture = EAT;
//                setSpeed(0);
//            }
//        }else if(activity == Ngu){
//            pathFinder.setNodes(
//                    (int) this.getPos().x,
//                    (int) this.getPos().y,
//                    (int) (CHUONG_GA1[0]+Math.pow(-1,rand.nextInt(1)+1)*rand.nextInt(2)),
//                    (int) (CHUONG_GA1[1]+Math.pow(-1,rand.nextInt(1)+1)*rand.nextInt(2))
//            );
//            pathFinder.search();
//            if(pathFinder.getPathList().size() > 0) {
//                posture = STAND;
//                setSpeed(1);
//                Node next = pathFinder.getPathList().get(0);
//                if (this.getPos().x > next.column * gp.titleSize) {
//                    this.getPos().x -= getSpeed();
//                } else
//                if (this.getPos().x < next.column * gp.titleSize) {
//                    this.getPos().x += getSpeed();
//                } else
//                if (this.getPos().y > next.row * gp.titleSize) {
//                    this.getPos().y -= getSpeed();
//                } else
//                if (this.getPos().y < next.row * gp.titleSize) {
//                    this.getPos().y += getSpeed();
//                }else {
//                    // remove node
//                    pathFinder.getPathList().remove(0);
//                }
//            }else {
//                posture = SIT;
//                setSpeed(0);
//            }
//        }else{
//            posture = STAND;
//            setSpeed(1);
//            checkCollisionAndMove(this.direction, this.getSpeed());
//            unfollow();
//            if (activity == SIT) {
//                goTo(ps.obj[0]); // go to object key
//
//         } else {
//                    goToFoodTray();
//                }
//                animate(true);
//                image = ani.getImage().image;
//            }
//>>>>>>> aa70c1823a720d27824f2b176f8656410207ff7f


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

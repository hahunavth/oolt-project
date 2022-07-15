package view.renderer;

import controller.AnimalController;
import interfaces.controller.Behavior;
import view.graphics.SpriteSheet;
import view.main.GamePanel;
import view.states.GameStateManager;
import view.states.PlayState;
import view.utils.Direction;
import view.utils.ImageSplitter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ChickenRenderer extends AnimalRenderer{

    private BufferedImage image;
    private Behavior prevBehavior;
    private Direction prevDirection;
    public ChickenRenderer(GamePanel gp, PlayState ps, AnimalController animalController) {
        super(gp, ps, animalController);

        setImage();
    }

    @Override
    public void setImage() {
        this.spriteSheet = new SpriteSheet(8, 16);
        String image = "/chicken/chicken-sprite-sheet.png";
//        System.out.println("Load Image: " + image);
        ImageSplitter ci = new ImageSplitter(GameStateManager.gp, image, 32, 32, 0);

        BufferedImage[] imgs = new BufferedImage[36];

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                imgs[i*4+j] = ci.getSubImage(i, j);
            }
        }

        Map<Behavior, int[]> actBehaviorIds = new HashMap<>();
        actBehaviorIds.put(Behavior.EAT, new int[]{1,2,3,4,13,14,15,16});
        actBehaviorIds.put(Behavior.FLY, new int[]{1,2,3,4,13,14,15,16});
        actBehaviorIds.put(Behavior.LEAP, new int[]{1,2,3,4,13,14,15,16});
        actBehaviorIds.put(Behavior.PLAY, new int[]{1,2,3,4,13,14,15,16});
        actBehaviorIds.put(Behavior.RUN, new int[]{1,2,3,4,13,14,15,16});
        actBehaviorIds.put(Behavior.SIT, new int[]{1,2,3,4,13,14,15,16});
        actBehaviorIds.put(Behavior.SLEEP, new int[]{1,2,3,4,13,14,15,16});

        AtomicInteger j = new AtomicInteger();
        actBehaviorIds.forEach((behavior, ints) -> {
            for (int i : ints) {
                this.spriteSheet.addSprite(
                        j.get(),
                        imgs[i]
                );
            }
            j.getAndIncrement();
        });

        System.out.println(this.spriteSheet);

        setAnimation(0,
                spriteSheet.getSpriteArray(
                        0
                ),
                12
        );

    }

    @Override
    public void animate() {
        super.animate();

        setAnimation(1,
                spriteSheet.getSpriteArray(
                        1
                ),
                12
        );
    }

    public void update () {
        this.getAnimalController().update();

        if (ani.getImage() != null)
            image = ani.getImage().image;
        if (
                this.getAnimalController().getBehavior() != prevBehavior
                || prevDirection != this.getAnimalController().getDirection()
        ) {
            animate();
            prevBehavior = this.getAnimalController().getBehavior();
            prevDirection = this.getAnimalController().getDirection();
        }
    }

    public void draw (Graphics2D g2) {
        super.draw(g2);

        int x = (int) (controller.getBounds().getPos().getScreenX() + controller.getBounds().getXOffset());
        int y = (int) (controller.getBounds().getPos().getScreenY() + controller.getBounds().getYOffset());
        int w = (int) (controller.getBounds().getWidth());
        int h = (int) (controller.getBounds().getHeight());

        g2.drawImage(image, x, y, null);
    }


}

package view.renderer;

import controller.Controller;
import interfaces.view.IAnimatedImage;
import view.graphics.Sprite;
import view.graphics.SpriteAnimation;
import view.graphics.SpriteSheet;
import view.main.GamePanel;
import view.states.PlayState;

import java.util.List;

public abstract class EntityRenderer extends Renderer implements IAnimatedImage {

    protected SpriteSheet spriteSheet;
    protected SpriteAnimation ani;

    public EntityRenderer (GamePanel gp, PlayState ps, Controller controller) {
        super(controller);
        ani = new SpriteAnimation();
    }


    @Override
    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    @Override
    public SpriteAnimation getSpriteAnimation() {
        return ani;
    }

    @Override
    public abstract void setImage();

    public void update () {
        ani.update();
    }

    @Override
    public abstract void animate();

    public void setAnimation(int i, Sprite[] frames, int delay) {
        ani.setFrames(i, frames);
        ani.setDelay(delay);
    }

    public void setAnimation (int i, List<Sprite> spriteList, int delay) {
        Sprite[] sprites = new Sprite[spriteList.size()];
        spriteList.toArray(sprites);
        setAnimation(i, sprites, delay);
    }
}

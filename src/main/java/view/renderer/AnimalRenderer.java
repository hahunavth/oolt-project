package view.renderer;

import controller.AnimalController;
import view.effect.FocusableHandler;
import view.main.GamePanel;
import view.states.PlayState;

public abstract class AnimalRenderer extends EntityRenderer{

    AnimalController animalController;
//    public FocusableHandler fch;

    public AnimalRenderer(GamePanel gp, PlayState ps, AnimalController animalController) {
        super(gp, ps, animalController);

        this.animalController = animalController;
    }

    public AnimalController getAnimalController() {
        return animalController;
    }

    @Override
    public void animate() {
//        switch (animalController.getBehavior()) {
//            case EAT:
//                // get ea
//                // setAnimation(RUN_DOWN, sprite.getSpriteArray(RUN_DOWN), 10);
//                break;
//        }
    }

    @Override
    public abstract void setImage();

}

package interfaces.view;

import view.graphics.SpriteAnimation;
import view.graphics.SpriteSheet;

public interface IAnimatedImage {

    void setImage ();

    SpriteSheet getSpriteSheet ();

    SpriteAnimation getSpriteAnimation ();

    void animate();

}

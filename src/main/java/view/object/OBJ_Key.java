package view.object;

import view.states.PlayState;
import view.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Key extends SuperObject {

    public OBJ_Key (GamePanel gp, PlayState ps) {
        super (gp, ps);

        name = "Key";
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/view/object/key.png")));
            this.image = this.tool.scaleImage(image, gp.titleSize, gp.titleSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        this.draw(g2, true);
    }

}

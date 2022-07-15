package view.entity;

import model.Animals.Animal;
import org.jetbrains.annotations.NotNull;
import view.states.PlayState;
import view.graphics.SpriteSheet;
import view.main.GamePanel;
import view.math.AABB;
import view.utils.ImageSplitter;
import view.utils.Direction;

import java.awt.*;

public class FoxEntity extends AnimalEntity {

    protected AABB followRange;
    protected AABB unFollowRange;

    public FoxEntity(GamePanel gp, PlayState ps) {
        super(gp, ps);
        this.name = "Fox";

        this.direction = Direction.DOWN;

        this.setSpeed(2);

        this.followRange = new AABB(this.pos, 5*gp.titleSize);
        this.followRange.setXOffset( -2 * gp.titleSize);
        this.followRange.setYOffset( -2 * gp.titleSize);
        this.unFollowRange = new AABB(this.pos, 11*gp.titleSize);
        this.unFollowRange.setXOffset( -5 * gp.titleSize);
        this.unFollowRange.setYOffset( -5 * gp.titleSize);

        setImage();
    }

    public FoxEntity (GamePanel gp, PlayState ps, Animal animal) {
        this(gp, ps);
        this.animal = animal;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setAction() {
        super.setAction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void animate(boolean isRunning) {
//        setAnimation(0, sprite.getSpriteArray(0), 100);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setImage() {
        String image = "/fox-sprite-sheet.png";
        System.out.println("Load image: " + image + " >> " + sprite);
        ImageSplitter ci = new ImageSplitter(gp,
                image,
                32, 32, 12, 12, 6, 12
        );
        System.out.println( "/t>> col: " + ci.getColumns() + ", rows: " + ci.getRows());

        sprite = new SpriteSheet(7, 14);
        int[] framePerAction =  {5, 14, 8, 11, 5, 6, 7};
        int i = 0;
        for (int len : framePerAction) {
            for (int j = 0; j < len; j++) {
                sprite.addSprite(i, ci.getSubImage(i, j));
            }
            i++;
        }
        setAnimation(3, sprite.getSpriteArray(3), 10);
    }

    /**
     * Follow:
     * - Đi theo, đến vị trí của 1 entity ( 1 con vật khác, người chơi, vật thể khác ) trên bản đò.
     */
    public void follow (Entity entity) {
        super.follow(entity);

        if (currentAnimation != 4)
            setAnimation(4, sprite.getSpriteArray(4), 10);
    }

    public void unfollow () {
        super.unfollow();

        if (sprite != null) // if setImage not error
            setAnimation(3, sprite.getSpriteArray(3), 10);
    }


    /**
     * {@inheritDoc}
     *
     * FoxEntity.update:
     * <ul>
     *     <li>
     *         Update image
     *     </li>
     *     <li>
     *         Update follow and unfollow range collision
     *     </li>
     *     <li>
     *         If follow: find path and go to
     *     </li>
     * </ul>
     */
    @Override
    public void update () {

        image = ani.getImage().image;

        // follow player if in range
        if (followedEntity == null && this.followRange.colCircleBox(ps.player.getBounds()) ) {
            follow(ps.player);
        } else if (!this.unFollowRange.colCircleBox(ps.player.getBounds())) {
            unfollow();
        } else if (followedEntity != null) {
            follow(ps.player);
        }

//        if (followedEntity == null && this.followRange.colCircleBox(ps.obj[1].getBounds()) ) {
//            goTo(ps.obj[1]);
//        }

        super.update();
    }

    /**
     * {@inheritDoc}
     *
     * FoxEntity.draw:
     * <ul>
     *     <li>
     *         Vẽ vòng tròn follow range và unfollow range (debug).
     *     </li>
     * </ul>
     */
    @Override
    public void draw(@NotNull Graphics2D g2) {
        super.draw(g2);

        // Vẽ vòng tròn follow range và unfollow range.
        if (followedEntity == null) {
            g2.setColor(Color.RED);
            g2.drawOval(
                    (int) this.followRange.getPos().getScreenX() + (int) this.followRange.getXOffset(),
                    (int) this.followRange.getPos().getScreenY() + (int)  this.followRange.getXOffset(),
                    (int) this.followRange.getRadius(),
                    (int) this.followRange.getRadius()
                    );
            g2.setColor(Color.WHITE);
        } else {
            g2.drawOval(
                    (int) this.unFollowRange.getPos().getScreenX() + (int) this.unFollowRange.getXOffset(),
                    (int) this.unFollowRange.getPos().getScreenY() + (int)  this.unFollowRange.getXOffset(),
                    (int) this.unFollowRange.getRadius(),
                    (int) this.unFollowRange.getRadius()
            );
        }

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

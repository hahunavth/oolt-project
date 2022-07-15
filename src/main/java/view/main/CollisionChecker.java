package view.main;

import view.states.PlayState;
import view.entity.Entity;

/**
 * CollisionChecker:
 * Collision check with game object (entity, animal, key, ...)
 */
public class CollisionChecker {

    GamePanel gp;
    PlayState ps;

    public CollisionChecker (GamePanel gp, PlayState ps) {
        this.gp = gp; this.ps = ps;
    }

    public int checkObject (Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < ps.obj.length; i++) {
            if(ps.obj[i] != null) {

                if(entity.getBounds().collides(ps.obj[i].getBounds())) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

}

package controller.gameAttr;

import controller.Controller;
import interfaces.controller.ILocatable;
import org.jetbrains.annotations.NotNull;
import view.ai.Node;
import view.ai.PathFinder;
import view.main.GamePanel;
import view.math.AABB;
import view.states.GameStateManager;
import view.states.PlayState;
import view.utils.Direction;

public class PathFinderManager {
    private PathFinder pathFinder;

    private ILocatable followedEntity;
    private Controller owner;
    private boolean isMoveMyPathRunning;


    public PathFinderManager(@NotNull GamePanel gp, PlayState ps, Controller owner) {
        this.pathFinder = new PathFinder(gp, ps);
        this.owner = owner;
    }

    public PathFinder getPathFinder() {
        return pathFinder;
    }

    public boolean follow(ILocatable iLocatable) {
        this.followedEntity = iLocatable;
        pathFinder.setNodes(
                owner, iLocatable
        );
        return pathFinder.search();
    }

    public boolean unfollow() {
        if (followedEntity != null) {
            followedEntity = null;
            this.pathFinder.getPathList().removeAll(this.pathFinder.getPathList());
            return true;
        }
        return false;
    }

    public boolean goTo(ILocatable iLocatable) {
        this.followedEntity = null;
        pathFinder.setNodes(
                owner, iLocatable
        );
        return pathFinder.search();
    }

    public boolean goTo(int x, int y) {
        this.followedEntity = null;
        pathFinder.setNodes(
                owner, x, y
        );
        return pathFinder.search();
    }

    private void moveByPath () {
        // run to goal
        GamePanel gp = GameStateManager.gp;

        if(pathFinder.getPathList().size() > 0) {
            isMoveMyPathRunning = true;
            Node next = pathFinder.getPathList().get(0);
            if (owner.getBounds().getPos().x > next.column * gp.titleSize) {
                owner.getBounds().getPos().x -= owner.getSpeed();
                direction = Direction.LEFT;
            } else
            if (owner.getBounds().getPos().x < next.column * gp.titleSize) {
                owner.getBounds().getPos().x += owner.getSpeed();
                direction = Direction.RIGHT;
            } else
            if (owner.getBounds().getPos().y > next.row * gp.titleSize) {
                owner.getBounds().getPos().y -= owner.getSpeed();
                direction = Direction.UP;
            } else
            if (owner.getBounds().getPos().y < next.row * gp.titleSize) {
                owner.getBounds().getPos().y += owner.getSpeed();
                direction = Direction.DOWN;
            } else {
                // remove node
                pathFinder.getPathList().remove(0);
            }
        } else {
            if (isMoveMyPathRunning) {
                isMoveMyPathRunning = false;
                if(this.toGoalListener != null)
                    this.toGoalListener.actionPerformed(null);
            }
        }
    }


}

package view.title;

import interfaces.controller.ILocatable;
import view.entity.Entity;
import view.title.blocks.Block;

/**
 * Xử lý va chạm giữa người chơi với tile block.
 *
 */
public class TileCollision {
    private final ILocatable e;

    private int tileId;

    public TileCollision(Entity e) {
        this.e = e;
    }

    public TileCollision(ILocatable iLocatable) {
        this.e = iLocatable;
    }

    public boolean normalTile(float ax, float ay) {
        int xt;
        int yt;

        xt = (int) ( (e.getPos().x + ax) + e.getBounds().getXOffset()) / 64;
        yt = (int) ( (e.getPos().y + ay) + e.getBounds().getYOffset()) / 64;
        tileId = (xt + (yt * TileMapObj.height));

        if(tileId > TileMapObj.height * TileMapObj.width) tileId = (TileMapObj.height * TileMapObj.width) - 2;

        return false;
    }

    public boolean collisionTile(float ax, float ay) {
        if(TileMapObj.event_blocks != null) {
            int xt;
            int yt;

            for(int c = 0; c < 4; c++) {

                xt = (int) ( (e.getPos().x + ax) + (c % 2) * e.getBounds().getWidth() + e.getBounds().getXOffset()) / 48;
                yt = (int) ( (e.getPos().y + ay) + (c / 2) * e.getBounds().getHeight() + e.getBounds().getYOffset()) / 48;

                if(xt <= 0 || yt <= 0 || xt + (yt * TileMapObj.height) < 0 || xt + (yt * TileMapObj.height) > (TileMapObj.height * TileMapObj.width) - 2) {
                    return true;
                }

                if(TileMapObj.event_blocks[xt + (yt * TileMapObj.height)] instanceof Block) {
                    Block block = TileMapObj.event_blocks[xt + (yt * TileMapObj.height)];
//                    if(block instanceof HoleBlock) {
//                        return collisionHole(ax, ay, xt, yt, block);
//                    }
                    return block.update(e.getBounds());
                }
            }
        }

        return false;
    }

    public int getTile() { return tileId; }

}

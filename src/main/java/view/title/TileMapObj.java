package view.title;

import view.graphics.Sprite;
import view.math.AABB;
import view.math.Vector2f;
import view.title.blocks.Block;
import view.title.blocks.ObjBlock;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TileMapObj extends TileMap {

    public static Block[] event_blocks;

    private int tileWidth;
    private int tileHeight;

    public static int width;
    public static int height;

    public TileMapObj(String data, ArrayList<BufferedImage> imgList, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
        Block tempBlock;
        event_blocks = new Block[width * height];

        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        TileMapObj.width = width;
        TileMapObj.height = height;

        String[] block = data.split(",");
        for(int i = 0; i < (width * height); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));
             if(temp != 0) {
//                 if(temp == 172) {
                     // TODO: find edge and connect them to form one polygon
//                     tempBlock = new HoleBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ), new Vector2f((int) (i % width) * tileWidth, (int) (i / height) * tileHeight), tileWidth, tileHeight);
//                 } else {
                     tempBlock = new ObjBlock(
//                             sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ), new Vector2f((int) (i % width) * tileWidth, (int) (i / height) * tileHeight), tileWidth, tileHeight
                             new Sprite(imgList.get(temp - 1)),
                             new Vector2f((int) (i % width) * tileWidth, (int) (i / height) * tileHeight),
                             tileWidth,
                             tileHeight
                     );
//                 }
                 event_blocks[i] = tempBlock;
             }
        }
    }

    public Block[] getBlocks() { return event_blocks; }

//    @Override
//    public void render(Graphics2D g, AABB cam) {
//
//    }

    public void render(Graphics2D g, AABB cam) {
        int x = (int) ((cam.getPos().x) / tileWidth);
        int y = (int) ((cam.getPos().y) / tileHeight);

        for(int i = x; i < x + (cam.getWidth() / tileWidth); i++) {
            for(int j = y; j < y + (cam.getHeight() / tileHeight); j++) {
                if(i + (j * height) > -1 && i + (j * height) < event_blocks.length && event_blocks[i + (j * height)] != null)
                    event_blocks[i + (j * height)].render(g);
            }
        }
    }
}

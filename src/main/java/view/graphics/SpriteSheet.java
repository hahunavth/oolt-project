package view.graphics;

import interfaces.controller.Behavior;
import org.jetbrains.annotations.NotNull;
import view.states.GameStateManager;
import view.utils.ImageSplitter;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.*;

/**
 * 1 sheet các sprite
 * <p>
 * Cắt ảnh của nhân vật để tạo ra mảng 2 chiều
 * - Chiều thứ 1: chỉ hành động (VD: các ảnh nhân vật chạy tạo thành 1 mảng).
 * - Chiều thứ 2: các ảnh trong 1 hành động.
 * </p>
 *
 * <p>
 * Sprite sheet là gì?
 * Là tập hợp nhiều Sprite đơn lẻ thành 1 tập tin duy nhất,
 * giúp tăng tốc độ xử lý cho việc hiển thị hình ảnh lên màn hình.
 * Do chỉ hiển thị 1 phần của bức ảnh (load 1 lần lên bộ nhớ) sẽ tốt hơn nhiều với việc lấy nhiều bức ảnh và hiển thị chúng.
 * </p>
 */
public class SpriteSheet<T> {

    private List<List<Sprite>> spritesArray;
    private Map<T, List<Sprite>> spritesArrayIndexes;
    private List<List<Sprite>> spritesGrayArray;
    private Map<T, List<Sprite>> spritesGrayArrayIndexes;

//    private Sprite spritesGrayArray[][];
    private int spritesIndexCounter[];
    private int arrLen;
    private String file;

    public SpriteSheet (int sttNum, int len) {
        spritesArray = new ArrayList<>(100);
        spritesGrayArray = new ArrayList<>(100);
        spritesArrayIndexes = new HashMap<>(100);
        spritesGrayArrayIndexes = new HashMap<>(100);

        spritesIndexCounter = new int[sttNum];
        arrLen = len;

    }

    public SpriteSheet (String fileName, int w, int h) {
        ImageSplitter ci = new ImageSplitter(GameStateManager.gp, fileName, w, h, 0);

        spritesArray = new ArrayList<>();
        spritesGrayArray = new ArrayList<>();
        spritesArrayIndexes = new HashMap<>();
        spritesGrayArrayIndexes = new HashMap<>();

        for (int i = 0; i < ci.getRows(); i++) {
            for (int j = 0; j < ci.getColumns(); j++) {
                addSprite(null, i, new Sprite(ci.getSubImage(i, j)));
            }
        }
    }

    private void addSprite (T behavior, Integer key, Sprite sprite) {
        if (!spritesArray.contains(key)) {
            List<Sprite> spriteList = new ArrayList<>(100);
            spritesArray.add(spriteList);
            spritesArrayIndexes.put(behavior, spriteList);
            spritesArray.get(key).add(sprite);
        } else {
            spritesArray.get(key).add(sprite);
        }
    }

    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public SpriteSheet addSprite(int sttNum, BufferedImage image) {
        try {
            int currentIdx = spritesIndexCounter[sttNum];
            addSprite(null, sttNum, new Sprite(image));
            BufferedImage image1 = deepCopy(image);
//            spritesGrayArray[sttNum][currentIdx] = new Sprite(image1, true); // gray
            spritesIndexCounter[sttNum] += 1;
        }
        catch (NullPointerException e) {
            System.out.println(e);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public List<Sprite> getSpriteArray (int sttIdx) {
        return spritesArray.get(sttIdx);
    }
    public List<Sprite> getGraySpriteArray (int sttIdx) {
        return spritesGrayArray.get(sttIdx);
    }
    public Sprite getSprite (int sttIdx, int idx) {
        return spritesArray.get(sttIdx).get(idx);
    }

    @Override
    public String toString() {
        return "SpriteSheet{" +
//                "spritesArray=" + Arrays.toString(spritesArray) +
//                ", spritesGrayArray=" + Arrays.toString(spritesGrayArray) +
//                ", spritesIndexCounter=" + Arrays.toString(spritesIndexCounter) +
                ", arrLen=" + arrLen +
                ", file='" + file + '\'' +
                '}';
    }
}
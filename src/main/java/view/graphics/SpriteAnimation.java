package view.graphics;

/**
 * Tạo các animation di chuyển cho nhân vật.
 *
 * <ul>
 * <li>
 * Dựa trên file sprite sheet, cho hiển thị từng sprite chuyển tiếp nhau 1 cách liên tục sẽ tạo ra cảm giác đối tượng như đang chuyển động.
 * </li>
 * <li>
 * Khi thao tác với sprite animation thường gặp thêm khái niệm Tile, 1 sprite sheet lưu 1 bức ảnh là 1 chuỗi các Tile, mỗi Tile hiển thị 1 khung hình của chuỗi chuyển động, như vậy Tile cũng giống với Sprite/Frame.
 * </li>
 * <li>
 * Không chỉ cần sprite sheet để hiển thị ảnh lên màn hình mà còn cần 1 file đi kèm để lưu thông tin x, y, width, height của các tile trong sprite sheet.
 * </li>
 * </ul>
 */
public class SpriteAnimation {

    private Sprite[] frames;
    private int[] states;
    private int currentFrame;
    private int numFrames;

    private int count;
    private int delay;

    private int timesPlayed;

    public SpriteAnimation(Sprite[] frames) {
        setFrames(0, frames);
        timesPlayed = 0;
        states = new int[10];
    }

    public SpriteAnimation() {
        timesPlayed = 0;
        states = new int[10];
    }

    public void setFrames(int state, Sprite[] frames) {
        this.frames = frames;
        currentFrame = 0;
        count = 0;
        timesPlayed = 0;
        delay = 2;
        if(states[state] == 0) {
            numFrames = frames.length;
        } else {
            numFrames = states[state];
        }
    }

    public void setDelay(int i) { delay = i; }
    public void setFrame(int i) { currentFrame = i; }
    public void setNumFrames(int i, int state) { states[state] = i; }

    /**
     * gọi trong update của lớp chứa nó.
     */
    public void update() {
        if(delay == -1) return;

        count++;

        if(count == delay) {
            System.out.println("animate");

            currentFrame++;
            count = 0;
        }
        if(currentFrame == numFrames || frames[currentFrame] == null) {
            currentFrame = 0;
            timesPlayed++;
        }
    }

    public int getDelay() { return delay; }
    public int getFrame() { return currentFrame; }
    public int getCount() { return count; }

    /**
     * Trả về ảnh hiện tại.
     * Ảnh sẽ thay đổi sau vài frame để tạo thành animation.
     */
    public Sprite getImage() { return frames[currentFrame]; }
    public boolean hasPlayedOnce() { return timesPlayed > 0; }
    public boolean hasPlayed(int i) { return timesPlayed == i; }

}

package view.math;

import view.entity.GameObject;

import java.awt.*;
import java.util.ArrayList;

/**
 * AABB: Vị trí giữa 2 hình chữ nhật, hình tròn.
 *
 * TODO: Chia AABBCircle và AABBRectangle
 *
 * <p>
 * <div>AABB collisions:</div>
 * AABB stands for axis-aligned bounding box,
 * a rectangular collision shape aligned to the base axes of the scene,
 * which in 2D aligns to the x and y axis.
 * </p>
 * <p>
 * Being axis-aligned means the rectangular box has no rotation
 * and its edges are parallel to the base axes of the scene
 * (e.g. left and right edge are parallel to the y axis).
 * The fact that these boxes are always aligned to the axes of the scene makes calculations easier.
 * </p>
 *
 * @see <a href="https://learnopengl.com/In-Practice/2D-Game/Collisions/Collision-detection">Link</a>
 */
public class AABB {

    private Vector2f pos;       // Tọa độ góc phải bên trên của hình.
    private float xOffset = 0;  // offsetX
    private float yOffset = 0;  // offsetY
    private float w;            // c.rộng   -> hcn
    private float h;            // c.dài    -> hcn
    private float r;            // b.kính   -> h.tròn   -> chỉ dùng khi là h.tròn
    private int size;           // size = r || max(w, h)

    private float surfaceArea;

    public AABB(Vector2f pos, int w, int h) {
        this.pos = pos;
        this.w = w;
        this.h = h;
        this.surfaceArea = w * h;

        size = Math.max(w, h);
    }

    public AABB(Vector2f pos, int r) {
        this.pos = pos;
        this.r = r;
        this.surfaceArea = (float) Math.PI * (r * r);

        size = r;
    }

    /**
     * Getter
     */
    public Vector2f getPos() { return pos; }

    public float getRadius() { return r; }
    public float getSize() { return size; }
    public float getWidth() { return w; }
    public float getHeight() { return h; }
    public float getSurfaceArea() { return surfaceArea; }

    /**
     * Setter
     */
    public void setBox(Vector2f pos, int w, int h) {
        this.pos = pos;
        this.w = w;
        this.h = h;

        size = Math.max(w, h);
    }

    public void setCircle(Vector2f pos, int r) {
        this.pos = pos;
        this.r = r;

        size = r;
    }

    public void setWidth(float f) { w = f; }
    public void setHeight(float f) { h = f; }

    public void setXOffset(float f) { xOffset = f; }
    public void setYOffset(float f) { yOffset = f; }
    public float getXOffset() { return xOffset; }
    public float getYOffset() { return yOffset; }

    public float getCenterX () {
        return pos.x + xOffset + w / 2;
    }

    public float getCenterY () {
        return pos.y + yOffset + h / 2;
    }

    /**
     * collides: Trả về true nếu 2 hình chạm nhau.
     *
     */
    public boolean collides(AABB bBox) {
        return collides(0, 0, bBox);
    }

    public boolean collides(float dx, float dy, ArrayList<GameObject> go) {
        boolean collides = false;

        for(int i = 0; i < go.size(); i++) {
            collides = collides(dx, dy, go.get(i).getBounds());
            if(collides) {
//                go.get(i).getImage().restoreDefault();
                // do sth
                go.remove(i);
                return collides;
            }
        }

        return collides;
    }

    public boolean collides(float dx, float dy, AABB bBox) {
        float ax = ((pos.x + (xOffset)) + (this.w / 2)) + dx;
        float ay = ((pos.y + (yOffset)) + (this.h / 2)) + dy;
        float bx = ((bBox.getPos().x + (bBox.getXOffset())) + (bBox.getWidth() / 2));
        float by = ((bBox.getPos().y + (bBox.getYOffset())) + (bBox.getHeight() / 2));

        if (Math.abs(ax - bx) < (this.w / 2) + (bBox.getWidth() / 2)) {
            if (Math.abs(ay - by) < (this.h / 2) + (bBox.getHeight() / 2)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Inside: Trả về true nếu điểm ở trong hình.
     */
    public boolean inside(int xp, int yp) {
        if(xp == -1 || yp == - 1) return false;

        int wTemp = (int) this.w;
        int hTemp = (int) this.h;
        int x = (int) this.pos.x;
        int y = (int) this.pos.y;

        if(xp < x || yp < y) {
            return false;
        }

        wTemp += x;
        hTemp += y;
        return ((wTemp < x || wTemp > xp) && (hTemp < y || hTemp > yp));
    }

    /**
     * intersect: true nếu hình này chứa hình kia.
     */
    public boolean intersect(AABB aBox)
    {

        if((pos.x + xOffset > aBox.getPos().x + aBox.getXOffset() + aBox.getSize())
        || (aBox.getPos().x + xOffset > pos.x + aBox.getXOffset() + aBox.getSize()))
        {
            return false;
        }

        if((pos.y + yOffset > aBox.getPos().y + aBox.getYOffset() + aBox.getSize())
        || (aBox.getPos().y + yOffset > pos.y + aBox.getYOffset() + aBox.getSize()))
        {
            return false;
        }

        return true;
    }

    /**
     * colCircle: true nếu this=hcn chạm vào tham số: h.tròn
     */
    public boolean colCircle(AABB circle) {

        float totalRadius = r + circle.getRadius();
        totalRadius *= totalRadius;

        float dx = (pos.x + circle.getPos().x);
        float dy = (pos.y + circle.getPos().y);

        return totalRadius < (dx * dx) + (dy * dy);
    }

    /**
     * H.tròn chạm hcn.
     * this: circle
     * aBox: rectangle
     */
    public boolean colCircleBox(AABB aBox) {

        float dx = Math.max(aBox.getPos().x + aBox.getXOffset(), Math.min(pos.x + (r / 2), aBox.getPos().x + aBox.getXOffset() + aBox.getWidth()));
        float dy = Math.max(aBox.getPos().y + aBox.getYOffset(), Math.min(pos.y + (r / 2), aBox.getPos().y + aBox.getYOffset() + aBox.getHeight()));

        dx = pos.x + this.xOffset + (r / 2) - dx;
        dy = pos.y + this.yOffset +  (r / 2) - dy;

        if(Math.sqrt(dx * dx + dy * dy) < r / 2) {
            return true;
        }

        return false;
    }

    /**
     * Khoàng cách 2 hcn.
     */
    public float distance(Vector2f other) {
        float dx = pos.x - other.x;
        float dy = pos.y - other.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public AABB merge(AABB other) {
        float minX = Math.min(pos.x, other.getPos().x);
        float minY = Math.min(pos.y, other.getPos().y);

        int maxW = (int) Math.max(w, other.getWidth());
        int maxH = (int) Math.max(h, other.getHeight());

        Vector2f pos = new Vector2f(minX, minY);
        return new AABB(pos, maxW, maxH);
	} 
	
	public String toString() {

		String x = Float.toString(pos.x);
		String y = Float.toString(pos.y);
		String w = Float.toString(this.w);
		String h = Float.toString(this.h);

		return "{" + x + ", " + y + " : " + w + ", " + h + "}";
	}

    public void render(Graphics2D g2) {
        g2.drawRect(
                (int) (pos.x - xOffset),
                (int) (pos.y - yOffset),
                (int) w,
                (int) h
        );
    }

}

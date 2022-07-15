package view.object;

import model.FoodInventory;
import view.states.PlayState;
import view.main.GamePanel;
import view.utils.ImageSplitter;

import java.awt.*;

public class OBJ_FoodTray extends SuperObject{

    private final FoodInventory foodInventory;
    private final int maxFood;

    int barHeight = 6;
    String name;
    private final Font font;

    public OBJ_FoodTray(GamePanel gp, PlayState ps, FoodInventory foodInventory) {
        super(gp, ps);

        this.font = new Font("Arial", Font.BOLD, 12);
        this.name = foodInventory.getFood().getName().toUpperCase();
        this.foodInventory = foodInventory;

        String imagePath = "/food/stardew-valley-food.png";
        ImageSplitter is = new ImageSplitter(gp, imagePath, 16, 16, 0);

        if (foodInventory.getFood().getName().equals("meat"))
            image = is.getSubImage(0, 1);
        else if (foodInventory.getFood().getName().equals("egg"))
            image = is.getSubImage(0, 2);
        else
            image = is.getSubImage(0, 3);

        // NOTE: * 2 for testing
        // TODO: * 1 in production
        maxFood = foodInventory.getAmount() * 2;
        super.name = "";
    }

    public FoodInventory getFoodInventory() {
        return foodInventory;
    }

    public int getMaxFood() {
        return maxFood;
    }

    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     * @param g2
     */
    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
//        System.out.println("Food tray: " +  foodInventory.getAmount());
        String str = name + ":" + foodInventory.getAmount() + " / " + this.getMaxFood();
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect((int) pos.getScreenX() , (int) pos.getScreenY() - barHeight, gp.titleSize, barHeight, 0, 300);
        g2.setColor(Color.RED);
        int fillRedLen = (int) ((1.0 * this.foodInventory.getAmount() / maxFood) * (gp.titleSize - 2));
        g2.fillRoundRect((int) pos.getScreenX() + 1, (int) pos.getScreenY() - barHeight + 1, fillRedLen, barHeight - 2, 30, 0);
        g2.setColor(Color.WHITE);
        FontMetrics fontMetrics = g2.getFontMetrics();
        int width = fontMetrics.stringWidth(str);
        int padding = (gp.titleSize - width) / 2;
        g2.drawString(
                str
                , (int) pos.getScreenX() + padding, (int) pos.getScreenY() - 4 - barHeight);
    }
}

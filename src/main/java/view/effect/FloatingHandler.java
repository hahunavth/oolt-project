package view.effect;

import interfaces.IFloating;

public class FloatingHandler implements IFloating {
    private boolean fly;
    private int counter;
    private int offsetX = 0, offsetY = 0;

    @Override
    public int getOffsetX() {
        return offsetX;
    }
    @Override
    public int getOffsetY() {
        return offsetY;
    }
    @Override
    public void update () {
        if(fly) {
            if(counter > 10 && counter % 2 == 1) {
                offsetY += 1;
            }
            counter++;
            if(counter > 20) {
                fly = false;
            }
        } else {
            if(counter < 0 && (-counter % 2) == 1 ) {
                offsetY -= 1;
            }
            counter--;
            if(counter < -10) {
                fly = true;
            }
        }
    }
}

package view.effect;

import interfaces.IFocusable;
import view.states.PlayState;
import view.main.GamePanel;
import view.object.OBJ_FoodTray;

public class FocusManager {
    private final PlayState ps;
    private int hoveredObjId, focusedObjId;
    private boolean isNewHovered = false, isNewFocused = false;


    public FocusManager(GamePanel gp, PlayState ps) {
        this.ps = ps;
        this.focusedObjId = 999;
        this.hoveredObjId = 999;
    }

    public boolean isNewHovered() {
        return isNewHovered;
    }

    public boolean isNewFocused() {
        return isNewFocused;
    }

    public int getHoveredObjId() {
        return hoveredObjId;
    }

    public int getFocusedObjId() {
        return focusedObjId;
    }

    public void setNewHovered(boolean newHovered) {
        isNewHovered = newHovered;
    }

    public void setNewFocused(boolean newFocused) {
        isNewFocused = newFocused;
    }

    public void checkAndHoverObject(int nId) {
        if(hoveredObjId != nId) {
            if(hoveredObjId != 999) {
                if(ps.obj[hoveredObjId] instanceof IFocusable) {
//                    gp.obj[hoveredObjId].fch.setHovered(false);
                    IFocusable obj = (IFocusable) ps.obj[hoveredObjId];
                    obj.setHovered(false);
                }
            }
            if(nId != 999) {
                if(ps.obj[nId] instanceof IFocusable) {
//                    gp.obj[nId].fch.setHovered(true);
                    IFocusable obj = (IFocusable) ps.obj[nId];
                    obj.setHovered(true);
                }
            }
            hoveredObjId = nId;
            // to check sound play
            isNewHovered = true;
        }
    }

    public void checkAndFocusObject () {
        if(hoveredObjId != 999) {
            if(focusedObjId != hoveredObjId ) {
                if(focusedObjId != 999) {
                    if(ps.obj[focusedObjId] instanceof IFocusable) {
                        IFocusable obj = (IFocusable) ps.obj[focusedObjId];
                        obj.setFocused(false);
                    }
                }
                focusedObjId = hoveredObjId;
                isNewFocused = true;
                System.out.println("Hover" + focusedObjId);
                if(ps.obj[focusedObjId] instanceof IFocusable) {
                    IFocusable obj = (IFocusable) ps.obj[focusedObjId];
                    obj.setFocused(true);
                    if (ps.obj[focusedObjId] instanceof OBJ_FoodTray) {
                        OBJ_FoodTray foodTray = ((OBJ_FoodTray)ps.obj[focusedObjId]);
                        foodTray.getFoodInventory().setAmount(foodTray.getMaxFood());
                        System.out.println("Set max food");
                    }
                    System.out.println("checkAndFocusObject: " + "hover=" + hoveredObjId +
                            "focus=" + focusedObjId);
                }
            }
        }else {
            if(focusedObjId != 999) {
                if(ps.obj[focusedObjId] instanceof IFocusable) {
                    IFocusable obj = (IFocusable) ps.obj[focusedObjId];
                    obj.setFocused(false);
                }
            }
            focusedObjId = 999;
            isNewFocused = false;
        }
    }
}

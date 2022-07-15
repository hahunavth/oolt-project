package interfaces.view;

import controller.Controller;
import interfaces.controller.ILocatable;

import java.awt.*;

public interface IDrawable {

    Controller getController ();
    void draw (Graphics2D g2);
}

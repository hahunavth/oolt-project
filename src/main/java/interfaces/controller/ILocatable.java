package interfaces.controller;

import view.math.AABB;
import view.math.Vector2f;

public interface ILocatable {
    Vector2f getPos ();
    int getWorldX ();
    int getWorldY ();
    AABB getBounds ();
}

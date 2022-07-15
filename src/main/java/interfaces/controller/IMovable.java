package interfaces.controller;

import view.utils.Direction;

public interface IMovable extends ILocatable {

    boolean isRunning ();

    int getSpeed ();

    void setSpeed (int speed);

    int getMaxSpeed ();
    void setMaxSpeed (int maxSpeed);

    Direction getDirection ();

    void setDirection(Direction direction);

    boolean move ();

    void stop ();
}

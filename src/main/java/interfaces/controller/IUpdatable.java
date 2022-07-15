package interfaces.controller;

import view.main.KeyHandler;
import view.main.MouseHandler;

public interface IUpdatable {

    void input (KeyHandler keyHandler, MouseHandler mouseHandler);

    void update ();

}

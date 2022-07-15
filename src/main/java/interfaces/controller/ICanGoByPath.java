package interfaces.controller;

public interface ICanGoByPath extends IMovable {

    boolean follow (ILocatable iLocatable);

    boolean unfollow ();

    boolean goTo (ILocatable iLocatable);

    boolean goTo (int x, int y);

}

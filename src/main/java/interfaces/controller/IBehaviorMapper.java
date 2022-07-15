package interfaces.controller;

import model.Activities.Activity;

import java.awt.event.ActionEvent;

public interface IBehaviorMapper {

    Behavior getBehavior (Activity activity);

    Behavior getBehavior ();

    void setBehavior (Activity activity);

    void setOnDoneListener (ActionEvent actionEvent);

    void removeOnDoneListener ();

}

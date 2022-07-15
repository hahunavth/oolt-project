import model.Animals.*;
import model.Food;
import model.ModelState;
import view.main.GamePanel;

import javax.swing.*;
import java.util.List;

public class GameLauncher {
    public static void main (String[] args) {
        // setup window
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Game");

        // setup game model
        ModelState modelState = new ModelState(10);
        List<Food> foodList = modelState.getFoodList();
//        modelState.getAnimalList().add(
//                new Cat(
//                        modelState.getDefaultSchedule().get(0),
//                        modelState.getFoodList().get(0),
//                        10
//                )
//        );
//        modelState.getAnimalList().add(
//                new Chicken(
//                        modelState.getDefaultSchedule().get(0),
//                        modelState.getFoodInventoryList().get(0)
//                )
//        );
//        modelState.getAnimalList().add(
//                new Dog(
//                        modelState.getDefaultSchedule().get(0),
//                        modelState.getFoodInventoryList().get(0)
//                )
//        );
//        modelState.getAnimalList().add(
//                new Cat(
//                        modelState.getDefaultSchedule().get(0),
//                        modelState.getFoodInventoryList().get(1)
//                )
//        );
//        modelState.getAnimalList().add(
//                new Manatee(
//                        modelState.getDefaultSchedule().get(0),
//                        modelState.getFoodInventoryList().get(2)
//                )
//        );
//        modelState.getAnimalList().add(
//                new Kangaroo(
//                        modelState.getDefaultSchedule().get(0),
//                        modelState.getFoodInventoryList().get(2)
//                )
//        );
//        modelState.getAnimalList().add(
//                new Duck(
//                        modelState.getDefaultSchedule().get(0),
//                        modelState.getFoodList().get(0),
//                        10
//                )
//        );

        // todo: connect model state and game panel

        // setup game panel
        GamePanel gamePanel = new GamePanel(modelState);
        window.add(gamePanel);

        // ...
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}

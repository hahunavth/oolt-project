package adapter;

import model.Animals.*;
import model.ModelState;
import view.states.GameStateManager;
import view.states.PlayState;
import view.entity.*;
import view.main.Camera;
import view.main.GamePanel;
import view.object.OBJ_FoodTray;
import view.object.OBJ_Key;

import java.util.List;

public class Model2PlayStateAdapter extends PlayState {

    private int modelStateCounter;
    private ModelState modelState;

    public Model2PlayStateAdapter(Camera camera, ModelState modelState) {
        super(camera, new Model2UIAdapter(GameStateManager.gp, modelState));
        this.modelState = modelState;
    }

    @Override
    public void setup () {
        GamePanel gp = GameStateManager.gp;
        PlayState ps = this;
        // create key
        obj[0] = new OBJ_Key(gp, ps);
        obj[0].getBounds().getPos().x = 20 * gp.titleSize;
        obj[0].getBounds().getPos().y = 20 * gp.titleSize;
        // create food tray
        for (int j = 1; j <  this.modelState.getFoodInventoryList().size() + 1; j++) {
            int i = j - 1;
            OBJ_FoodTray foodTray = new OBJ_FoodTray(gp, ps, this.modelState.getFoodInventoryList().get(i));
            obj[j] = foodTray;
            obj[j].getPos().x = ((int)(30f * gp.titleSize));
            obj[j].getPos().y = ((int)((10f + 4 * j) * gp.titleSize));
            // foodTrays để con vật tìm loại thức ăn nó cần
            foodTrays.add(foodTray);
        }

        // create animal entity
        int len = foodTrays.size() + 1;;
        List<Animal> animals = this.modelState.getAnimalList();
        for (int j = len; j < animals.size() + len; j++) {
            int i = j - len;
            if (animals.get(i) instanceof Chicken) {
                obj[j] = new ChickenEntity(gp, ps, animals.get(i));
                obj[j].getBounds().getPos().x = ((int)(20f * gp.titleSize));
                obj[j].getBounds().getPos().y = ((int)((10f + i) * gp.titleSize));
                ChickenEntity chicken = (ChickenEntity) obj[j];
//                    chicken.goTo(player);
            } else if(animals.get(i) instanceof Dog){
            }else if(animals.get(i) instanceof Cat){
                obj[j] = new CatEntity(gp, ps, animals.get(i));
                obj[j].getBounds().getPos().x = ((int)(10f * gp.titleSize));
                obj[j].getBounds().getPos().y = ((int)((10f + i) * gp.titleSize));
            }else if(animals.get(i) instanceof Manatee){
            }else if(animals.get(i) instanceof Kangaroo){
            }
            else{
                obj[j] = new FoxEntity(gp, ps, animals.get(i));
                obj[j].getPos().x = ((int)(10f * gp.titleSize));
                obj[j].getPos().y = ((int)((10f + i) * gp.titleSize));
            }
        }

        playMusic(0);
    }

    @Override
    public void update (double time, GameStateManager gsm) {

        if (isUpdatable(gsm))
            if (this.modelState != null && modelStateCounter == 60 * 2 / this.modelState.getSimulationSpeed()) {
                this.modelState.run();
                modelStateCounter = 0;
            } else {
                modelStateCounter++;
            }
        super.update(time, gsm);

//        System.out.println(this.modelState.getSimulationSpeed());
    }

}

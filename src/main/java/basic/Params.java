package basic;

import java.util.Random;

import static states.GameStateManager.gp;

public class Params {
    public static Random rand = new Random();
    public static int[] CHUONG_GA1 = {((int)(10f * gp.titleSize)),((int)(10f * gp.titleSize))};
    public static int[] CHUONG_GA2 = {((int)(10f * gp.titleSize)),((int)(39f * gp.titleSize))};
    public static int[] CHUONG_KANGAROO = {((int)(39f * gp.titleSize)),((int)(39f * gp.titleSize))};
    public static int[] CHUONG_MANATEE = {((int)(39f * gp.titleSize)),((int)(10f * gp.titleSize))};
    public static int[] HOME = {((int)(25f * gp.titleSize)),((int)(25f * gp.titleSize))};

    public static int ACTION_Lock = 60*60*5;

}

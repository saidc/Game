
package simplejavagame.Game.tools;

import java.util.Comparator;
import simplejavagame.Object.Object;

public class SortbyLevel implements Comparator<simplejavagame.Object.Object>{
    @Override
    public int compare(simplejavagame.Object.Object obj1, simplejavagame.Object.Object obj2) {
        return obj1.getLevel() - obj2.getLevel() ;
    }
}

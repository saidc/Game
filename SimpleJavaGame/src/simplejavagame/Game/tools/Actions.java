
package simplejavagame.Game.tools;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import simplejavagame.Object.Type;

public class Actions {
    public static Map<String, String> mouseClicked(Point pos){
        Vector2i p = new Vector2i(pos.x,pos.y);
        
        Map<String, String> acc = new HashMap<>();
        acc.put("action-type", Integer.toString(Type.IsClicked));
        acc.put("position", p.toString());
        return acc;
    }
}

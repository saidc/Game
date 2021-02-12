
package simplejavagame.Game.tools;

import java.awt.Color;

public class tools {
    public static Color NewColor(Vector4i v){
        return new Color(v.X(),v.Y(),v.Z(),v.W());
    }
    public static boolean isNumber(String n){
        try {
            Integer.parseInt(n);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}

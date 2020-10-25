
package game.Build.Map;

import java.awt.Dimension;
import org.joml.Vector4f;


public class Map {
    
    private Dimension unitDimension = null;
    private Dimension MapDimension  = null;
    
    private static Vector4f Plain_Color     = new Vector4f( 21.0f  , 173.0f , 41.0f , 1f );
    private static Vector4f hill_Color      = new Vector4f( 197.0f , 202.0f , 25.0f , 1f );
    private static Vector4f Mountain_Color  = new Vector4f( 181.0f , 116.0f , 11.0f , 1f );
    
    public Map(){
        
    }
    public void init(Dimension unitDimension, Dimension MapDimension, int NumberOfMountain){
        this.unitDimension = unitDimension ;
        this.MapDimension  = MapDimension  ;
        
    }
    
}

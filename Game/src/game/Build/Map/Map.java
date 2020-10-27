
package game.Build.Map;

import game.Build.Component.SpriteRenderer;
import game.Build.GameObject;
import game.Build.Transform;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import org.joml.Random;
import org.joml.Vector2i;
import org.joml.Vector4f;
import org.joml.Vector4i;


public class Map {
    private static Map map = null;
    
    private Dimension unitDimension = null;
    private Dimension MapDimension  = null;
    private static final int Mountain_size = 36;
    private static final int Plain    = 0 ;
    private static final int hill     = 1 ;
    private static final int Mountain = 2 ;
    
    private static final Vector4i Plain_Color     = new Vector4i( 21  , 173 , 41 , 255 );
    private static final Vector4i hill_Color      = new Vector4i( 197 , 202 , 25 , 255 );
    private static final Vector4i Mountain_Color  = new Vector4i( 181 , 116 , 11 , 255 );
    
    private List<List<Integer>> Int_map;
    private List<List<GameObject>> MapOfGameObjects;
    private int NumberOfMountains = 0;
    
    //                          (10,10)                 (100,100)           (200)
    public boolean init(Dimension unitDimension, Dimension MapDimension, int NumberOfMountains){
        if(MapDimension.width*MapDimension.height > Mountain_size * NumberOfMountains ){
            this.unitDimension = unitDimension ; // size of a single square in pixels
            this.MapDimension  = MapDimension  ; // size of the map in number of squares
            this.NumberOfMountains = NumberOfMountains;
            this.GeneratePlainMap();
            this.GenerateMountains();
            this.GenarateGameObjectMap();
            return true;
        }else{
            Map.map = null;
            return false;
        }
    }
    public static Map get(){
        if(Map.map == null){
            Map.map = new Map();
        }
        return Map.map;
    }
    
    private void GenarateGameObjectMap(){
        
        this.MapOfGameObjects = new ArrayList<>();
        for (int y = 0; y < Int_map.size(); y++) {
            List<Integer> list = Int_map.get(y);
            List<GameObject> line = new ArrayList<>();
            for (int x = 0; x < list.size(); x++) {
                int level = 0;
                Vector4i color = null;
                
                switch(list.get(x)){
                    case Plain: // plain
                        level = Plain;
                        color = Plain_Color;
                        break;
                    case hill:
                        level = hill;
                        color = hill_Color;
                        break;
                    case Mountain:
                        level = Mountain;
                        color = Mountain_Color;
                        break;
                }
                GameObject obj = new GameObject("Object 3", new Transform(new Vector2i(x*unitDimension.width, y*unitDimension.height), new Vector2i(unitDimension.width, unitDimension.height)),level);
                SpriteRenderer objSpriteRender = new SpriteRenderer();
                
                objSpriteRender.setColor(color);
                obj.addComponent(objSpriteRender); // just a color 
                line.add(obj);
            }
            this.MapOfGameObjects.add(line);
        }
        System.out.println("GenarateGameObjectMap");
    }
    
    public List<List<GameObject>> getMap(){
        return this.MapOfGameObjects;
    }
    
    private void GeneratePlainMap(){
        this.Int_map = new ArrayList<>();
        for (int i = 0; i < this.MapDimension.height; i++) {
            List<Integer> line = new ArrayList<>();
            for (int j = 0; j < this.MapDimension.width; j++) {
                line.add(Plain);
            }
            this.Int_map.add(line);
        }
        System.out.println("GeneratePlainMap");
    }
    
    /**
     * the idea is to generate a list of position where
     * its located a Mountain, this is to know where i cant put 
     * another Mountain, an then whe draw the list of position of the 
     * mountains or put in to the map.
     */
    private boolean GenerateMountains(){
        int freeMovement = (int)Math.sqrt((this.MapDimension.width*this.MapDimension.height)/this.NumberOfMountains);
        if(freeMovement*freeMovement*NumberOfMountains > this.MapDimension.width*this.MapDimension.height){
            System.out.println("the number of mountaint exeed the capacity of the map");
            return false;
        }
        System.out.println("freeMovement: "+freeMovement);
        
        List<Vector2i> MountainPos = new ArrayList<>();
        int NumberOfMountainCreated = 0;
        int randomRange = freeMovement - 6;
        while(NumberOfMountainCreated < this.NumberOfMountains){ 
            //Vector2i NewPos = null;//getRandomMountainPosition(0,99,0,99);
            for (int y = 0; y < this.MapDimension.height - freeMovement; y+= freeMovement) {
                for (int x = 0; x < this.MapDimension.width - freeMovement; x+=freeMovement) {
                    Vector2i add_random = new Vector2i(0,0);
                    if(randomRange > 0){
                        add_random = getRandomMountainPosition(0,randomRange,0,randomRange);
                    }
                    MountainPos.add(new Vector2i(x+add_random.x+2,y+add_random.y+2));
                    NumberOfMountainCreated++;
                    if(!(NumberOfMountainCreated < this.NumberOfMountains)){
                        System.out.println("NumberOfMountainCreated "+NumberOfMountainCreated);
                        break;
                    }
                }
                if(!(NumberOfMountainCreated < this.NumberOfMountains)){
                    System.out.println("NumberOfMountainCreated "+NumberOfMountainCreated);
                    break;
                }
            }
        }
        
        for (Vector2i MountainPo : MountainPos) {
            addMountainAndHillInPos(MountainPo);
        }
        System.out.println("GenerateMountains");
        return true;
    }
    
    /**
     * Mountain has a size of 2x2 and
     * Hill has a size of 6x6
    */
    private void addMountainAndHillInPos(Vector2i pos){
        for (int i = -2; i <= 3; i++) {
            List<Integer> line = Int_map.get(pos.y+i);
            for (int j = -2; j <= 3; j++) {
                if((i >= 0 && i < 2)&& (j >= 0 && j < 2)){
                    line.set(pos.x+j, Mountain);
                }else{
                    line.set(pos.x+j, hill);
                }
            }
        }
    }
    
    private Vector2i getRandomMountainPosition(int xi,int xf, int yi,int yf){
        Random r1 = new Random();
        Random r2 = new Random();
        int low1  = xi;
        int high1 = xf+1;
        int low2  = yi;
        int high2 = yf+1;
        return new Vector2i(r1.nextInt(high1-low1) + low1 , r2.nextInt(high2-low2) + low2) ;
    }
    
    public void ShowMap(){
        ShowMap(this.Int_map);
    }
    
    private static void ShowMap(List<List<Integer>> map){
        for (List<Integer> line : map) {
            for (Integer integer : line) {
                System.out.print(integer + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
}

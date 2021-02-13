package simplejavagame.Scene.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import simplejavagame.Game.tools.Figures;
import simplejavagame.Game.tools.Vector2i;
import simplejavagame.Game.tools.Vector4i;

public class GameMap {
    private static final int Mountain_size = 36; // mountain size is W * H  = 6*6 = 36
    public static  final int Plain         = 0 ;
    public static  final int hill          = 1 ;
    public static  final int Mountain      = 2 ;
    
    public static final Vector4i Plain_Color     = new Vector4i( 21  , 173 , 41 , 255 );
    public static final Vector4i hill_Color      = new Vector4i( 197 , 202 , 25 , 255 );
    public static final Vector4i Mountain_Color  = new Vector4i( 181 , 116 , 11 , 255 );
    private static GameMap map = null;
    
    public static GameMap get(){
        if(GameMap.map == null){
            GameMap.map = new GameMap();
        }
        return GameMap.map;
    }
    
    private Vector2i unitDimension = null;
    private Vector2i MapDimension  = null;
    private int NumberOfMountains = 0;
    
    private List<List<simplejavagame.Object.Object>> MapOfGameObjects;
    private List<List<Integer>> Int_map;
    
    public boolean init(Vector2i unitDimension, Vector2i MapDimension, int NumberOfMountains){
        if( MapDimension.W()*MapDimension.H() > Mountain_size * NumberOfMountains ){
            this.unitDimension = unitDimension ; // size of a single square in pixels
            this.MapDimension  = MapDimension  ; // size of the map in number of squares
            this.NumberOfMountains = NumberOfMountains;
            this.GeneratePlainMap();
            this.GenerateMountains();
            this.GenarateGameObjectMap();
            /*
            this.GenerateMapConexion();
            */
            return true;
        }else{
            GameMap.map = null;
            return false;
        }
    }
    /**
     * @GeneratePlainMap 
     * Genarate a map of Plain (0) with W,H
     */
    private void GeneratePlainMap(){ 
        this.Int_map = new ArrayList<>();
        for (int i = 0; i < this.MapDimension.H(); i++) {
            List<Integer> line = new ArrayList<>();
            for (int j = 0; j < this.MapDimension.W(); j++) {
                line.add(Plain);
            }
            this.Int_map.add(line);
        }
    }
    /**
     * the idea is to generate a list of position where
     * its located a Mountain, this is to know where i cant put 
     * another Mountain, an then whe draw the list of position of the 
     * mountains or put in to the map.
     */
    private boolean GenerateMountains(){
        int freeMovement = (int)Math.sqrt((this.MapDimension.W()*this.MapDimension.H())/this.NumberOfMountains);
        if(freeMovement*freeMovement*NumberOfMountains > this.MapDimension.W()*this.MapDimension.H()*NumberOfMountains){
            System.out.println("the number of mountaint exeed the capacity of the map");
            return false;
        }
        List<Vector2i> MountainPos = new ArrayList<>();
        int NumberOfMountainCreated = 0;
        //int tryCount = 0;
        try {
            while(NumberOfMountainCreated < this.NumberOfMountains){ 

                        Vector2i NewPos = getRandomMountainPosition(2,this.MapDimension.W()-4,2,this.MapDimension.H()-4);
                        if(MountainPos.size() > 0 ){
                            boolean sw = true;

                            for (Vector2i MountainPo : MountainPos) {
                                Vector2i TopLeft     = new Vector2i(NewPos.X()    ,NewPos.Y()    );
                                Vector2i TopRight    = new Vector2i(NewPos.X() + 1,NewPos.Y()    );
                                Vector2i BottonLeft  = new Vector2i(NewPos.X()    ,NewPos.Y() + 1);
                                Vector2i BottonRight = new Vector2i(NewPos.X() + 1,NewPos.Y() + 1);
                                if(((TopLeft.X()      >= MountainPo.X()       && TopLeft.Y()      >= MountainPo.Y()    )&&
                                   ( TopLeft.X()      <= MountainPo.X() + 1   && TopLeft.Y()      <= MountainPo.Y() + 1))||
                                   ((TopRight.X()     >= MountainPo.X()       && TopRight.Y()     >= MountainPo.Y()    )&&
                                   ( TopRight.X()     <= MountainPo.X() + 1   && TopRight.Y()     <= MountainPo.Y() + 1))||
                                   ((BottonLeft.X()   >= MountainPo.X()       && BottonLeft.Y()   >= MountainPo.Y()    )&&
                                   ( BottonLeft.X()   <= MountainPo.X() + 1   && BottonLeft.Y()   <= MountainPo.Y() + 1))||
                                   ((BottonRight.X()  >= MountainPo.X()       && BottonRight.Y()  >= MountainPo.Y()    )&&
                                   ( BottonRight.X()  <= MountainPo.X() + 1   && BottonRight.Y()  <= MountainPo.Y() + 1))  
                                  ){
                                    sw = false;
                                }
                            }

                            if(sw ){
                                MountainPos.add(NewPos);
                                NumberOfMountainCreated++;
                            }

                        }else{
                            if(NewPos.X() >= 2 && NewPos.Y() >=2){
                                MountainPos.add(NewPos);
                                NumberOfMountainCreated++;
                            }
                        }

            }
            
            for (Vector2i MountainPo : MountainPos) {
                addHillInPos(MountainPo);
            }
            for (Vector2i MountainPo : MountainPos) {
                addMountainInPos(MountainPo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage() + " line: " + e.getLocalizedMessage());
        }
        
        System.out.println("GenerateMountains");
        return true;
    }
    private Vector2i getRandomMountainPosition(int xi,int xf, int yi,int yf){
        Random r1 = new Random();
        Random r2 = new Random();
        int low1  = xi;
        int high1 = xf;
        int low2  = yi;
        int high2 = yf;
        return new Vector2i(r1.nextInt(high1-low1) + low1 , r2.nextInt(high2-low2) + low2) ;
    }
    /**
     * Mountain has a size of 2x2 and
     * Hill has a size of 6x6
    */
    private void addHillInPos(Vector2i pos){
        for (int i = -2; i <= 3; i++) {
            List<Integer> line = Int_map.get(pos.Y()+i);
            for (int j = -2; j <= 3; j++) {
                if( !( (i >= 0 && i < 2)&& (j >= 0 && j < 2) )){
                    line.set(pos.X()+j, hill);
                }
            }
        }
    }
    private void addMountainInPos(Vector2i pos){
        for (int i = -2; i <= 3; i++) {
            List<Integer> line = Int_map.get(pos.Y()+i);
            for (int j = -2; j <= 3; j++) {
                if((i >= 0 && i < 2)&& (j >= 0 && j < 2)){
                    line.set(pos.X()+j, Mountain);
                }
            }
        }
    }
    private void GenarateGameObjectMap(){
        this.MapOfGameObjects = new ArrayList<>();
        for (int y = 0; y < Int_map.size(); y++) {
            List<Integer> list = Int_map.get(y);
            List<simplejavagame.Object.Object> line = new ArrayList<>();
            for (int x = 0; x < list.size(); x++) {
                int level = 0;
                Vector4i color = new Vector4i(0, 0, 0, 255);
                
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
                //simplejavagame.Object.Object obj = new simplejavagame.Object.Object("{'x':"+x+",'y':"+y+"}", new Transform(new Vector2i(x*unitDimension.width, y*unitDimension.height), new Vector2i(unitDimension.width, unitDimension.height)),level);
                simplejavagame.Object.Object obj = Figures.getSquare(new Vector2i(x*unitDimension.W(), y*unitDimension.H()), unitDimension, color, level);
                
                line.add(obj);
            }
            this.MapOfGameObjects.add(line);
        }
    }
    public List<List<Integer>> getInt_Map(){
        return Int_map;
    }
    public List<List<simplejavagame.Object.Object>> getMap(){
        return this.MapOfGameObjects;
    }
    // UNITS
    private static final int Unit = 3 ;
    private static final Vector4i Unit_Color          = new Vector4i( 4  , 47 , 102 , 255 );
    private static final Vector4i Unit_Selected_Color = new Vector4i( 25 , 94 , 131 , 255 );
    private static final Vector4i Unit_Execut_Color = new Vector4i( 25 , 94 , 255 , 255 );
    public static final int Sleep_Mode         = 4;
    public static final int Waiting_for_Orders = 5;
    public static final int Executing_Orders   = 6;
    
    public static List<simplejavagame.Object.Object> units = null;
    public static void GenerateRandomUnits(int NumberOfRandomUnits,Vector2i unitPixelSize, List<List<Integer>> Int_map , int Terrain_Int){
        if(units == null){
            units = new ArrayList<>();
        }else{
            units.clear();
        }
        // obtain the position of the plain terrain 
        List<Vector2i> TerrainPositionSelected = new ArrayList<>();
        for (int y = 0; y < Int_map.size(); y++) {
            List<Integer> list = Int_map.get(y);
            for (int x = 0; x < list.size(); x++) {
                Integer integer = list.get(x);
                if(integer == Terrain_Int){
                    TerrainPositionSelected.add(new Vector2i(x,y));
                }
            }
        }
        
        // generate the Number Of Random Units
        List<Integer> PositionsSelected = new ArrayList<>();
        int i = 0;
        while(i<NumberOfRandomUnits){
            boolean sw = false;
            int pos = getRandomPosition(0,TerrainPositionSelected.size());
            if(PositionsSelected.size() == 0){
                PositionsSelected.add(pos);
                i++;
            }else{
                for (Integer integer : PositionsSelected) { // prevent a repetitive position
                    if(integer == pos){
                        sw = true;
                    }
                }
            }
            if(!sw){
                PositionsSelected.add(pos);
                // generate unit
                Vector2i PixelPos = new Vector2i(unitPixelSize.W()*TerrainPositionSelected.get(pos).X() , unitPixelSize.H()*TerrainPositionSelected.get(pos).Y());

                simplejavagame.Object.Object u = Figures.getSquare(PixelPos, unitPixelSize, Unit_Color, Unit);
                u.setName("Unit-"+i);
                units.add(u);
                i++;
            }
        }
    }
    private static Integer getRandomPosition(int xi,int xf){
        Random r1 = new Random();
        int low1  = xi;
        int high1 = xf;
        return r1.nextInt(high1-low1) + low1  ;
    }
    public static void addClickListeners() {
        if(units == null){
            return;
        }
        
        for (simplejavagame.Object.Object unit : units) {
            if(unit.getName().indexOf("Unit") != -1){
                unit.setCallbackEvent(unit.getName());
            }
        }
        
    }
}

package game.Build.Map;

import game.Build.Component.SpriteRenderer;
import game.Build.GameObject;
import game.Build.Transform;
import game.Utils.GraphWeighted;
import game.Utils.NodeWeighted;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import org.joml.Random;
import org.joml.Vector2i;
import org.joml.Vector4i;

public class Map {
    private static Map map = null;
    
    private Dimension unitDimension = null;
    private Dimension MapDimension  = null;
    private int NumberOfMountains = 0;
    
    private static final int Mountain_size = 36;
    public static  final int Plain    = 0 ;
    public static  final int hill     = 1 ;
    public static  final int Mountain = 2 ;
    
    public static final Vector4i Plain_Color     = new Vector4i( 21  , 173 , 41 , 255 );
    public static final Vector4i hill_Color      = new Vector4i( 197 , 202 , 25 , 255 );
    public static final Vector4i Mountain_Color  = new Vector4i( 181 , 116 , 11 , 255 );
    
    private List<List<GameObject>> MapOfGameObjects;
    private List<List<Integer>> Int_map;
    private GraphWeighted graphWeighted ;
    private ArrayList<ArrayList<NodeWeighted>> matriz = new ArrayList();
    
    
    //                          (10,10)                 (100,100)           (200)
    public boolean init(Dimension unitDimension, Dimension MapDimension, int NumberOfMountains){
        if( MapDimension.width*MapDimension.height > Mountain_size * NumberOfMountains ){
            this.unitDimension = unitDimension ; // size of a single square in pixels
            this.MapDimension  = MapDimension  ; // size of the map in number of squares
            this.NumberOfMountains = NumberOfMountains;
            this.GeneratePlainMap();
            this.GenerateMountains();
            this.GenarateGameObjectMap();
            this.GenerateMapConexion();
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
    
    public List<List<Integer>> getInt_Map(){
        return Int_map;
    }
    public static void addLinesBetweenSquares(){
        SpriteRenderer.addLinesBetweenSquares();
    }
    public static void RemoveLinesBetweenSquares(){
        SpriteRenderer.RemoveLinesBetweenSquares();
    }
    public static boolean getLinesBetweenSquares(){
        return SpriteRenderer.getLinesBetweenSquares();
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
                GameObject obj = new GameObject("{'x':"+x+",'y':"+y+"}", new Transform(new Vector2i(x*unitDimension.width, y*unitDimension.height), new Vector2i(unitDimension.width, unitDimension.height)),level);
                SpriteRenderer objSpriteRender = new SpriteRenderer();
                
                objSpriteRender.setColor(color);
                obj.addComponent(objSpriteRender); // just a color 
                
                line.add(obj);
            }
            this.MapOfGameObjects.add(line);
        }
        System.out.println("GenarateGameObjectMap");
    }
    
    private void GenerateMapConexion(){
        this.graphWeighted = new GraphWeighted(true);
        
        this.matriz = new ArrayList<>();
        for (int y = 0; y < this.MapOfGameObjects.size(); y++) {
            List<GameObject> MOGO = this.MapOfGameObjects.get(y);
            ArrayList<NodeWeighted> line = new ArrayList<>();
            for (int x = 0; x < MOGO.size(); x++) {
                NodeWeighted node = new NodeWeighted( MOGO.get(x) );
                line.add(node);
            }
            matriz.add(line);
        }
        
        for (int y = 0; y < matriz.size(); y++) {
            ArrayList<NodeWeighted> line = matriz.get(y);
            
            boolean sw = ( y  < matriz.size() - 1 );
            ArrayList<NodeWeighted> linePlus = null;
            if(sw){ linePlus = matriz.get(y+1); }
            
            for (int x = 0; x < line.size()-1; x++) {
                
                NodeWeighted A = line.get(x)      ;
                NodeWeighted B = line.get(x + 1 ) ;
                
                this.graphWeighted.addEdge(A,B, 1);
                //System.out.print("[ "+A.getName()+" -> "+B.getName()+"]");
                
                if(sw){
                    NodeWeighted C =  linePlus.get(x);
                    this.graphWeighted.addEdge(A,C, 1);
                    //System.out.print(",["+A.getName()+" -> "+C.getName()+"]");
                    
                    if( x + 1 == line.size() -1 ){ 
                        NodeWeighted D = linePlus.get(x+1);
                        this.graphWeighted.addEdge(B,D, 1); 
                        //System.out.print(",["+B.getName()+" -> "+D.getName()+"]");
                    }
                    //System.out.println("");
                }
            }
        }
        
        System.out.println("GenerateMapConexion");
        this.graphWeighted.printNumberOfEdges();
    }
    
    public NodeWeighted getNodeWeighted(GameObject go){
        for (ArrayList<NodeWeighted> line : this.matriz) {
            for (NodeWeighted Node : line) {
                if(Node.getGmObj().equals(go)){
                    System.out.println(go.getname());
                    return Node;
                }
            }
        }
        return null;
    }
    
    public NodeWeighted getNodeWeighted(Vector2i position){
        for (ArrayList<NodeWeighted> line : this.matriz) {
            for (NodeWeighted Node : line) {
                if(Node.getGmObj().getPosition().equals(position)){
                    return Node;
                }
            }
        }
        return null;
    }
    
    public ArrayList<GameObject> getShortestPath( NodeWeighted A , NodeWeighted B ){
        ArrayList<GameObject> path = this.graphWeighted.DijkstraShortestPath(A, B);
        return path;
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
        if(freeMovement*freeMovement*NumberOfMountains > this.MapDimension.width*this.MapDimension.height*NumberOfMountains){
            System.out.println("the number of mountaint exeed the capacity of the map");
            return false;
        }
        //System.out.println("freeMovement: "+freeMovement);
        
        List<Vector2i> MountainPos = new ArrayList<>();
        int NumberOfMountainCreated = 0;
        //int tryCount = 0;
        try {
            while(NumberOfMountainCreated < this.NumberOfMountains){ 

                        Vector2i NewPos = getRandomMountainPosition(2,this.MapDimension.width-4,2,this.MapDimension.height-4);
                        if(MountainPos.size() > 0 ){
                            boolean sw = true;

                            for (Vector2i MountainPo : MountainPos) {
                                Vector2i TopLeft     = new Vector2i(NewPos.x    ,NewPos.y    );
                                Vector2i TopRight    = new Vector2i(NewPos.x + 1,NewPos.y    );
                                Vector2i BottonLeft  = new Vector2i(NewPos.x    ,NewPos.y + 1);
                                Vector2i BottonRight = new Vector2i(NewPos.x + 1,NewPos.y + 1);

                                if(((TopLeft.x      >= MountainPo.x       && TopLeft.y      >= MountainPo.y    )&&
                                   ( TopLeft.x      <= MountainPo.x + 1   && TopLeft.y      <= MountainPo.y + 1))||
                                   ((TopRight.x     >= MountainPo.x       && TopRight.y     >= MountainPo.y    )&&
                                   ( TopRight.x     <= MountainPo.x + 1   && TopRight.y     <= MountainPo.y + 1))||
                                   ((BottonLeft.x   >= MountainPo.x       && BottonLeft.y   >= MountainPo.y    )&&
                                   ( BottonLeft.x   <= MountainPo.x + 1   && BottonLeft.y   <= MountainPo.y + 1))||
                                   ((BottonRight.x  >= MountainPo.x       && BottonRight.y  >= MountainPo.y    )&&
                                   ( BottonRight.x  <= MountainPo.x + 1   && BottonRight.y  <= MountainPo.y + 1))  
                                  ){
                                    sw = false;
                                    //tryCount++;
                                }
                            }

                            if(sw ){//|| tryCount > 10){
                                MountainPos.add(NewPos);
                                NumberOfMountainCreated++;
                                //tryCount = 0;
                            }

                        }else{
                            if(NewPos.x >= 2 && NewPos.y >=2){
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
    
    /**
     * Mountain has a size of 2x2 and
     * Hill has a size of 6x6
    */
    private void addHillInPos(Vector2i pos){
        for (int i = -2; i <= 3; i++) {
            List<Integer> line = Int_map.get(pos.y+i);
            for (int j = -2; j <= 3; j++) {
                if( !( (i >= 0 && i < 2)&& (j >= 0 && j < 2) )){
                    line.set(pos.x+j, hill);
                }
            }
        }
    }
    private void addMountainInPos(Vector2i pos){
        for (int i = -2; i <= 3; i++) {
            List<Integer> line = Int_map.get(pos.y+i);
            for (int j = -2; j <= 3; j++) {
                if((i >= 0 && i < 2)&& (j >= 0 && j < 2)){
                    line.set(pos.x+j, Mountain);
                }
            }
        }
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

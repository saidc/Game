package game.Build.Map;

import game.Build.Component.Component;
import game.Build.Component.SpriteRenderer;
import game.Build.GameObject;
import game.Build.Transform;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.joml.Random;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4i;

public class Units extends Component{
    public static List<Units> units = null;
    private static boolean hasToken = true;
    
    private static final int Unit = 3 ;
    private static final Vector4i Unit_Color          = new Vector4i( 4  , 47 , 102 , 255 );
    private static final Vector4i Unit_Selected_Color = new Vector4i( 25 , 94 , 131 , 255 );
    private static final Vector4i Unit_Execut_Color = new Vector4i( 25 , 94 , 255 , 255 );
    public static final int Sleep_Mode         = 0;
    public static final int Waiting_for_Orders = 1;
    public static final int Executing_Orders   = 2;
    
    public static List<Units> get(){
        if(units == null){
            units = new ArrayList<>();
        }
        return units;
    }
    
    public static Units get(int i){
        if(units == null){
            units = new ArrayList<>();
            return null;
        }else if(units.size() == 0){
            return null;
        }
        return units.get(i);
    }
    
    public static List<GameObject> getGameObjects(){
        if(units == null){
            return null;
        }else if(units.size() == 0){
            return null;
        }
        List<GameObject> objs = new ArrayList<>();
        for (Units unit : units) {
            objs.add(unit.gameObject);
        }
        
        return objs;
    }
    
    public static void addClickListeners(Consumer<Boolean> ClickListener) {
        if(units == null){
            return;
        }
        
        for (Units unit : units) {
            unit.ClickListener = ClickListener;
            unit.gameObject.addClickListener(unit::ClickListener);
        }
        
    }
    
    public static void GenerateRandomUnits(int NumberOfRandomUnits,Dimension unitPixelSize, List<List<Integer>> Int_map , int Terrain_Int){
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
                //System.out.println("posi: "+ posi);
                PositionsSelected.add(pos);
                i++;
            }else{
                //int pos = getRandomPosition(0,TerrainPositionSelected.size());
                
                for (Integer integer : PositionsSelected) { // prevent a repetitive position
                    if(integer == pos){
                        sw = true;
                    }
                }
            }
            if(!sw){
                PositionsSelected.add(pos);
                // generate unit
                Units u = new Units();
                u.init(TerrainPositionSelected.get(pos), unitPixelSize);
                Vector2i PixelPos = new Vector2i(unitPixelSize.width*TerrainPositionSelected.get(pos).x , unitPixelSize.height*TerrainPositionSelected.get(pos).y);

                // generate a GameObject
                GameObject obj = new GameObject("Unit-"+i, new Transform(PixelPos, new Vector2i(unitPixelSize.width, unitPixelSize.height)),Unit);
                SpriteRenderer objSpriteRender = new SpriteRenderer();
                objSpriteRender.setColor(Unit_Color);
                obj.addComponent(objSpriteRender); // just a color
                obj.addComponent(u);
                u.gameObject = obj;
                
                units.add(u);
                i++;
            }
        }
        System.out.println("units.size: "+units.size());
    }
    
    private static Integer getRandomPosition(int xi,int xf){
        Random r1 = new Random();
        int low1  = xi;
        int high1 = xf;
        return r1.nextInt(high1-low1) + low1  ;
    }
    
    public static boolean getTokenState(){
        if(hasToken){
            return true;
        }
        return false;
    }
    
    private Vector2i    target          = null ; // its the square position not the pixel position
    private boolean unit_hasToken = false;
    private Dimension   unitDimension   = null ; // its the pixel dimension of the unit
    private Consumer<Boolean> ClickListener = null;
    private Timer time ;
    /**
     * 0:   Sleep Mode
     * 1:   Waiting for orders
     * 2:   Executing orders
     */
    private int State;
    private boolean move = false;
    private int previousOverTerrain = -1;
    public int getPreviousOverTerrain(){
        return this.previousOverTerrain;
    }
    
    public void setPreviousOverTerrain(int previousOverTerrain ){
        this.previousOverTerrain = previousOverTerrain;
    }
    
    public int getState(){
        return this.State;
    }
    
    public void init(Vector2i position,Dimension unitDimension){
        this.unitDimension = unitDimension;
        //this.position = position;
        this.State = Sleep_Mode;
        this.time = new Timer();  
    }
    
    public void Move(){
        this.move = true;
    }
    
    private void getToken(){
        if(hasToken){
            this.unit_hasToken = true;
            hasToken = false;
        }
    }
    
    private void releaseToken(){
        if(this.unit_hasToken){
            hasToken = true;
            this.unit_hasToken = false;
        }
    }
    
    public boolean getUnitTokenState(){
        if(this.unit_hasToken){
            return true;
        }
        return false;
    }
    
    private void setSleepMode(){
        this.State = Sleep_Mode;
        SpriteRenderer s = this.gameObject.getComponent(SpriteRenderer.class);
        s.setColor(Unit_Color);
        s.outline = null;
        releaseToken();
        deleteOrder();
    }
    
    private void WaitingForOrders(int state){
        getToken();
        if(this.unit_hasToken){
            SpriteRenderer s = this.gameObject.getComponent(SpriteRenderer.class);
            this.State = Waiting_for_Orders;
            if(state == 0){
                s.setColor(Unit_Selected_Color);
            }else if(state == 1){
                s.setColor(new Vector4i(255,0,255,255));
            }
            s.outline = new Vector4i(255,255,255,255);
        }
    }
    
    private void ExecutingOrders(){
        this.State = Executing_Orders;
        SpriteRenderer s = this.gameObject.getComponent(SpriteRenderer.class);
        s.setColor(new Vector4i(255,0,255,255));
        s.outline = null;
        releaseToken();
    }
    
    private void ClickListener (Dimension d){
        
        if(       this.State == Sleep_Mode         ){
            WaitingForOrders(0);
        }else if( this.State == Waiting_for_Orders ){
            setSleepMode();
        }else if( this.State == Executing_Orders   ){
            WaitingForOrders(1);
        }
        
    }
    
    public void deleteOrder(){
        System.out.println("Order Deleteds");
    }
    
    public void update(long time, int terrain){
        if(this.State == Executing_Orders){
            if( !this.time.isStart ){
                this.time.init(30);
                this.time.start();
                //Map.get().getShortestPath(A, B)
            }else{
                if(this.time.isEnd()){
                    if(this.move){
                        boolean canMove = canUnitMove(terrain);
                        Vector2i v = movementdirection(canMove,this.target );
                        //System.out.println("V.x: "+v.x+" ,V.y:"+v.y);
                        try {
                            this.gameObject.transform.position.x += v.x;
                            this.gameObject.transform.position.y += v.y;

                            int x = this.gameObject.transform.position.x ;
                            int y = this.gameObject.transform.position.y ;
                            int x1 =target.x- (this.gameObject.transform.scale.x /2), x2 =target.x+(this.gameObject.transform.scale.x /2);
                            int y1 =target.y- (this.gameObject.transform.scale.y /2), y2 =target.y+ (this.gameObject.transform.scale.y /2);

                            if( x > x1 && x < x2 && y > y1 && y < y2){
                                setSleepMode();
                            }
                            this.move = false;
                        }catch(Exception e){
                            e.printStackTrace();
                            System.out.println(" error: "+ e.getMessage());
                        }
                    }
                }
            }
        }
    }
    private boolean canUnitMove(int terrain){
        if(terrain == Map.Plain){
            return true;
        }else if(terrain == Map.hill){
            Random r1 = new Random();
            int low1  = 0;
            int high1 = 2;
            if(r1.nextInt(high1-low1) == 1){
                return true;
            }else{
                return false;
            }
        }else if(terrain == Map.Mountain){
            Random r1 = new Random();
            int low1  = 1;
            int high1 = 10;
            if(r1.nextInt(high1-low1) == 1){
                return true;
            }else{
                return false;
            }   
        }
        return false;
    }
    
    public void update(int GameState, GameObject temp) {
        if(this.State == Waiting_for_Orders && this.unit_hasToken && GameState == 1){
            ExecutingOrders();
            this.target = temp.getPosition();
        }
    }
    public boolean isWaiting_for_Orders(){
        return (this.State == Waiting_for_Orders);
    }
    public boolean hasToken(){
        return this.unit_hasToken;
    }
    
    private Vector2i movementdirection(boolean canMove, Vector2i target) {
        if (canMove){
            if(target.x > this.gameObject.transform.position.x ){
                return new Vector2i(this.gameObject.transform.scale.x,0);
            }else if (target.x < this.gameObject.transform.position.x){
                return new Vector2i(-this.gameObject.transform.scale.x,0);
            }else if (target.y > this.gameObject.transform.position.y){
                return new Vector2i(0,this.gameObject.transform.scale.y);
            }else if (target.y < this.gameObject.transform.position.y){
                return new Vector2i(0,-this.gameObject.transform.scale.y);
            }
        }
        return new Vector2i(0,0);
        
    }
    
    private class Timer {
        public boolean isStart = false;
        private long currentTime ;
        private int target;
        
        public void init(int time){
            setTimer(time);
            isStart = false;
        }
        public boolean isStart(){
            return this.isStart;
        }
        public void start(){
            this.currentTime = System.currentTimeMillis();
            this.isStart = true;
        }
        public boolean isEnd(){
            if(this.isStart){
                if(System.currentTimeMillis()-currentTime <= target){
                    return false;
                }
                this.isStart = false;
            }
            return true;
        }
        public void setTimer(int time){
            this.target = time;
        }   
    }
}


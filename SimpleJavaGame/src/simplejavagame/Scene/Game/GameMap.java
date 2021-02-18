package simplejavagame.Scene.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import simplejavagame.Game.tools.Figures;
import simplejavagame.Game.tools.Vector;
import simplejavagame.Game.tools.Vector2i;
import simplejavagame.Game.tools.Vector4i;

public class GameMap {
    private static final int Mountain_size = 36; // mountain size is W * H  = 6*6 = 36
    public static  final int Plain         = 0 ;
    public static  final int hill          = 1 ;
    public static  final int Mountain      = 2 ;
    public static  final int Village       = 3 ;
    
    public static final Vector4i Plain_Color     = new Vector4i( 21  , 173 , 41 , 255 );
    public static final Vector4i hill_Color      = new Vector4i( 197 , 202 , 25 , 255 );
    public static final Vector4i Mountain_Color  = new Vector4i( 181 , 116 , 11 , 255 );
    public static final Vector4i Village_Color   = new Vector4i( 200 , 191 , 231, 255 );
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
    private int NumberOfVillage = 0;
    private List<List<simplejavagame.Object.Object>> MapOfGameObjects;
    private List<List<Integer>> Int_map;
    
    public boolean init(Vector2i unitDimension, Vector2i MapDimension, int NumberOfMountains, int _NumberOfVillage){
        if( MapDimension.W()*MapDimension.H() > (Mountain_size * NumberOfMountains)+NumberOfVillage ){
            this.unitDimension = unitDimension ; // size of a single square in pixels
            this.MapDimension  = MapDimension  ; // size of the map in number of squares
            this.NumberOfMountains = NumberOfMountains;
            this.NumberOfVillage = _NumberOfVillage;
            this.GeneratePlainMap();
            this.GenerateMountains();
            this.GenerateVillages();
            this.GenarateGameObjectMap();
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
    private boolean GenerateVillages(){
        ArrayList<Vector2i> positions = new ArrayList<>();
        //  move in the int map looking for plain positions where to put a village
        for (int y = 0; y < this.Int_map.size(); y++) {
            List<Integer> line = this.Int_map.get(y);
            for (int x = 0; x < line.size(); x++) {
                if(line.get(x) == Plain){
                    positions.add(new Vector2i(x,y));
                }
            }
        }
        if(NumberOfVillage <= positions.size()){
            Random r = new Random();
            int low1  = 0;
            int high1 = positions.size();
            ArrayList<Integer> selected = new ArrayList<>();
            int i = 0 , timeout= 0;
            while( i < this.NumberOfVillage) {
                int pos = r.nextInt(high1-low1);
                if(pos < positions.size()){
                    if(!selected.contains(pos)){
                        selected.add(pos);
                        i++;
                    }
                }
                timeout++;
                if( timeout >= this.NumberOfVillage * 100 ){
                    break;
                }
            }
            for (Integer pos : selected) {
                this.Int_map.get(positions.get(pos).Y()).set(positions.get(pos).X(), Village);
            }
            return true;
        }
        return false;
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
        int Plain_count = 0 , hill_count = 0 , Mountain_count = 0 , village_count = 0;
        for (int y = 0; y < Int_map.size(); y++) {
            List<Integer> list = Int_map.get(y);
            List<simplejavagame.Object.Object> line = new ArrayList<>();
            for (int x = 0; x < list.size(); x++) {
                int level = 0;
                Vector4i color = new Vector4i(0, 0, 0, 255);
                String name = "";
                int index = 0;
                switch(list.get(x)){
                    case Plain: // plain
                        level = Plain;
                        color = Plain_Color;
                        name = "Plain";
                        Plain_count += 1;
                        index = Plain_count;
                        break;
                    case hill:
                        level = hill;
                        color = hill_Color;
                        name = "hill";
                        hill_count += 1;
                        index = hill_count;
                        break;
                    case Village:
                        level = Village;
                        color = Village_Color;
                        name = "Village";
                        village_count +=1;
                        index = village_count;
                        break;
                    case Mountain:
                        level = Mountain;
                        color = Mountain_Color;
                        name = "Mountain";
                        Mountain_count += 1;
                        index = Mountain_count;
                        break;
                }
                //simplejavagame.Object.Object obj = new simplejavagame.Object.Object("{'x':"+x+",'y':"+y+"}", new Transform(new Vector2i(x*unitDimension.width, y*unitDimension.height), new Vector2i(unitDimension.width, unitDimension.height)),level);
                simplejavagame.Object.Object obj = Figures.getSquare(new Vector2i(x*unitDimension.W(), y*unitDimension.H()), unitDimension, color, level);
                obj.setName(name+"-"+index);
                obj.setCallbackEvent(obj.getName());
                obj.setLineBetweenSquares_color(new Vector4i(191,63,63,255));
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
    public void update(){
        for (int i = 0; i < this.UnitActions.size(); i++) {
            this.UnitActions.get(i).update();
            if(this.UnitActions.get(i).isDone()){
                this.UnitActions.get(i).getLocal().SetColor(Unit_Color);
                this.UnitActions.remove(i);
            }
        }
    }
    public int getTypeofTerrain(Vector2i position){
        for (List<simplejavagame.Object.Object> line : MapOfGameObjects) {
            for (simplejavagame.Object.Object obj : line) {
                if(obj.getPosition().equals(position)){
                    if(obj.getName().indexOf("Plain") != -1){
                        return Plain;
                    }else if(obj.getName().indexOf("hill") != -1){
                        return hill;
                    }else if(obj.getName().indexOf("Mountain") != -1){
                        return Mountain;
                    }else if(obj.getName().indexOf("Village") != -1){
                        return Village;
                    }
                }
            }
        }
        return -1;
    }
    // UNITS
    private static final Vector4i Unit_Color          = new Vector4i( 4  , 47 , 102 , 255 ) ;
    private static final Vector4i Unit_Selected_Color = new Vector4i( 25 , 94 , 131 , 255 ) ;
    public  static final Vector4i Unit_Execut_Color   = new Vector4i( 25 , 94 , 255 , 255 ) ;
    private static final int Unit               = 4 ;
    public  static final int Sleep_Mode         = 5 ;
    public  static final int Waiting_for_Orders = 6 ;
    public  static final int Executing_Orders   = 7 ;
    
    public  static List<simplejavagame.Object.Object> units = null;
    public  static void GenerateRandomUnits(int NumberOfRandomUnits,Vector2i unitPixelSize, List<List<Integer>> Int_map , int Terrain_Int){
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
                u.setLineBetweenSquares_color(new Vector4i(191,63,63,255));
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
    public  static void addClickListeners() {
        if(units == null){
            return;
        }
        for (simplejavagame.Object.Object unit : units) {
            if(unit.getName().indexOf("Unit") != -1){
                unit.setCallbackEvent(unit.getName());
            }
        }
    }
    
    //Unit Actions
    private simplejavagame.Object.Object UnitPressed = null;
    //private Vector4i PressedColor = Unit_Selected_Color;
    //private Vector4i PressedColor = Unit_Color;
    private ArrayList<UnitAction> UnitActions = new ArrayList<>();
    public boolean hasUnitPressed(){ return (UnitPressed != null);}
    public void setUnitPressed(simplejavagame.Object.Object unit){
        this.UnitPressed = unit;
        this.UnitPressed.SetColor(Unit_Selected_Color);
    }
    public void setUnitRelease(){
        this.UnitPressed.SetColor(Unit_Color);
        if(hasUnitPressedAnAction()){
            this.UnitPressed.SetColor(Unit_Execut_Color);
        }
        this.UnitPressed = null;
    }
    public simplejavagame.Object.Object getUnitPressed(){
        return this.UnitPressed;
    }
    public UnitAction addNewTarget(simplejavagame.Object.Object target){
        if(this.UnitPressed != null){
            if(!hasUnitPressedAnAction()){ // if doesnt have an actual unit
                UnitAction n = new UnitAction(UnitPressed,target);
                n.getLocal().SetColor(Unit_Execut_Color);
                UnitActions.add(n);
                return n;
            }else{
                UnitAction u = getUnitPressedByUnitPressed();
                if(!u.hasTarget(target)){
                    u.addTarget(target);
                    u.getLocal().SetColor(Unit_Execut_Color);
                    return u;
                }
            }
        }
        /*
        if(hasUnitPressedAnAction()){
            removeActionByUnutPressed();
        }
        UnitAction n = new UnitAction(UnitPressed,target);
        setUnitRelease();
        n.getLocal().SetColor(Unit_Execut_Color);
        UnitActions.add(n);
        */
        return null;
    }
    public boolean hasUnitPressedAnAction(){
        for (UnitAction u : this.UnitActions) { // if the unit pressed is all ready executing an action
            if(u.getLocal().getName() == this.UnitPressed.getName()){
                return true;
            }
        }
        return false;
    }
    public UnitAction getUnitPressedByUnitPressed(){
        for (UnitAction u : this.UnitActions) { 
            if(u.getLocal().getName() == this.UnitPressed.getName()){
                return u;
            }
        }
        return null;
    }
    public void removeActionByUnutPressed(){
        if(this.UnitPressed != null){
            for (int i = 0; i < this.UnitActions.size(); i++) {
                if(this.UnitActions.get(i).Local.getName() == this.UnitPressed.getName()){
                    this.UnitActions.get(i).RemoveTargetLines();
                    this.UnitActions.remove(i);
                }
            }
        }
    }
    
    public class UnitAction{
        private ArrayList<simplejavagame.Object.Object> TargetLines = new ArrayList();
        private simplejavagame.Object.Object Local = null ;// Target = null;
        private ArrayList<simplejavagame.Object.Object> Targets = new ArrayList();
        private Consumer<simplejavagame.Object.Object> unitAction = null;
        private int actualTarget = 0;
        private boolean isDone = false;
        public int getNumberOfTargets(){
            return this.Targets.size();
        }
        public UnitAction(simplejavagame.Object.Object _Local , simplejavagame.Object.Object _Target){
            this.Local  = _Local;
            this.Targets.add(_Target);
        }
        public simplejavagame.Object.Object getLocal(){
            return this.Local;
        }
        public simplejavagame.Object.Object getActualTarget(){
            return this.Targets.get(this.actualTarget);
        }
        public void addTargetLine(simplejavagame.Object.Object _TargetLine){
            this.TargetLines.add(_TargetLine);
        }
        public void addDoneEvent(Consumer<simplejavagame.Object.Object> _unitAction ){
            this.unitAction = _unitAction;
        }
        public String getName(){
            return this.Local.getName()+"-"+this.Targets.get(this.actualTarget).getName();
        }
        public boolean addTarget(simplejavagame.Object.Object target){
            for (simplejavagame.Object.Object t : this.Targets) {
                if(t.getName() == target.getName()){
                    return false; // you can't add a same target
                }
            }
            this.isDone = false;
            this.Targets.add(this.Targets.size(), target);
            return true;
        }
        public void update(){
            int terrain = getTypeofTerrain(this.Local.getPosition());
            if(CanUnitMove(terrain)){
                int c = this.Local.getPosition().compare(this.Targets.get(this.actualTarget).getPosition());
                move(c);
                //this.TargetLines.get(this.actualTarget).setPosition(getLocalLinePosition()); // update line
                //System.out.println(this.Local.getName()+" move");
            }/*else{
                //System.out.println(this.Local.getName()+" do not move");
            }*/
        }
        public simplejavagame.Object.Object getTargetLine(){
            return this.TargetLines.get(this.actualTarget);
        }
        public Vector2i getLocalLinePosition(){
            int x = this.Local.getPosition().X();
            int y = this.Local.getPosition().Y();
            int w = this.Local.getDimension().W()/2;
            int h = this.Local.getDimension().H()/2;
            return new Vector2i(x+w,y+h);
        }
        public Vector2i getActualTargetLinePosition(){
            int x = this.Targets.get(this.actualTarget).getPosition().X();
            int y = this.Targets.get(this.actualTarget).getPosition().Y();
            int w = this.Targets.get(this.actualTarget).getDimension().W()/2;
            int h = this.Targets.get(this.actualTarget).getDimension().H()/2;
            return new Vector2i(x+w,y+h);
        }
        public Vector2i getPreviousTargetLinePosition(){
            int x = this.Targets.get(this.Targets.size()-2).getPosition().X();
            int y = this.Targets.get(this.Targets.size()-2).getPosition().Y();
            int w = this.Targets.get(this.Targets.size()-2).getDimension().W()/2;
            int h = this.Targets.get(this.Targets.size()-2).getDimension().H()/2;
            return new Vector2i(x+w,y+h);
        }
        public Vector2i getLastTargetLinePosition(){
            int x = this.Targets.get(this.Targets.size()-1).getPosition().X();
            int y = this.Targets.get(this.Targets.size()-1).getPosition().Y();
            int w = this.Targets.get(this.Targets.size()-1).getDimension().W()/2;
            int h = this.Targets.get(this.Targets.size()-1).getDimension().H()/2;
            return new Vector2i(x+w,y+h);
        }
        
        public boolean isDone(){
            return this.isDone;
        }
        public void moveRight(){ this.Local.setPosition(this.Local.getPosition().X()+this.Targets.get(this.actualTarget).getDimension().W(),this.Local.getPosition().Y());
            //System.out.println("moveRight");
        }
        public void moveLeft (){ this.Local.setPosition(this.Local.getPosition().X()-this.Targets.get(this.actualTarget).getDimension().W(),this.Local.getPosition().Y());
            //System.out.println("moveLeft");
        }
        public void moveUp   (){ this.Local.setPosition(this.Local.getPosition().X(),this.Local.getPosition().Y()-this.Targets.get(this.actualTarget).getDimension().H());
            //System.out.println("moveUp");
        }
        public void moveDown (){ this.Local.setPosition(this.Local.getPosition().X(),this.Local.getPosition().Y()+this.Targets.get(this.actualTarget).getDimension().H());
            //System.out.println("moveDown");
        }
        public boolean CanUnitMove(int terrain){
            if(terrain == Plain || terrain == Village){
                //I can move
                return true;
            }else if(terrain == hill){
                //System.out.println("terrain: Hill");
                Random r1 = new Random();
                int low1  = 0;
                int high1 = 2;

                if(r1.nextInt(high1-low1) == 1){
                    // I can move
                    return true;
                }else{
                    // I can't move
                    return false;
                }
            }else if(terrain == Mountain){
                //System.out.println("terrain: Mountain");
                Random r1 = new Random();
                int low1  = 1;
                int high1 = 10;

                if(r1.nextInt(high1-low1) == 1){
                    // I can move
                    return true;
                }else{
                    // I can't move
                    return false;
                }
            }
            return false;
        }
        public void move(int c){
            switch(c){              
                case 1:
                    moveRight();
                    break;                   
                case 2:
                    Vector v2 = new Vector(this.TargetLines.get(this.actualTarget).getPosition(),this.TargetLines.get(this.actualTarget).getTarget());
                    //System.out.println("v2 x: "+this.Local.getMiddlePosition().X()+ " , y1: "+v2.getY(this.Local.getMiddlePosition().X())+" , y0: "+(float)this.Local.getMiddlePosition().Y());
                    if(v2.getY(this.Local.getMiddlePosition().X()) > (float)this.Local.getMiddlePosition().Y() ){
                        moveRight();
                    }else if(v2.getY(this.Local.getMiddlePosition().X()) < (float)this.Local.getMiddlePosition().Y() ){
                        moveUp();
                    }else{
                        moveRight();
                        moveUp();
                    }
                    break;                   
                case 3: 
                    moveUp();
                    break;                   
                case 4: 
                    Vector v4 = new Vector(this.TargetLines.get(this.actualTarget).getPosition(),this.TargetLines.get(this.actualTarget).getTarget());
                    //System.out.println("v4 x: "+this.Local.getMiddlePosition().X()+ " , y1: "+v4.getY(this.Local.getMiddlePosition().X())+" , y0: "+(float)this.Local.getMiddlePosition().Y());
                    if(v4.getY(this.Local.getMiddlePosition().X()) > (float)this.Local.getMiddlePosition().Y() ){
                        moveLeft();
                    }else if(v4.getY(this.Local.getMiddlePosition().X()) < (float)this.Local.getMiddlePosition().Y() ){
                        moveUp();
                    }else{
                        moveLeft();
                        moveUp();
                    }
                    break;
                case 5: 
                    moveLeft();
                    break;     
                case 6: 
                    Vector v6 = new Vector(this.TargetLines.get(this.actualTarget).getPosition(),this.TargetLines.get(this.actualTarget).getTarget());
                    //System.out.println("v6 x: "+this.Local.getMiddlePosition().X()+ " , y1: "+v6.getY(this.Local.getMiddlePosition().X())+" , y0: "+(float)this.Local.getMiddlePosition().Y());
                    if(v6.getY(this.Local.getMiddlePosition().X()) > (float)this.Local.getMiddlePosition().Y()){
                        moveDown();
                    }else if(v6.getY(this.Local.getMiddlePosition().X()) < (float)this.Local.getMiddlePosition().Y() ){
                        moveLeft();
                    }else{
                        moveLeft();
                        moveDown();
                    }
                    break;                   
                case 7: 
                    moveDown();
                    break;                   
                case 8: 
                    Vector v8 = new Vector(this.TargetLines.get(this.actualTarget).getPosition(),this.TargetLines.get(this.actualTarget).getTarget());
                    //System.out.println("v8 x: "+this.Local.getMiddlePosition().X()+ " , y1: "+v8.getY(this.Local.getMiddlePosition().X())+" , y0: "+(float)this.Local.getMiddlePosition().Y());
                    if(v8.getY(this.Local.getMiddlePosition().X()) > this.Local.getMiddlePosition().Y() ){
                        moveDown();
                    }else if(v8.getY(this.Local.getMiddlePosition().X()) < this.Local.getMiddlePosition().Y() ){
                        moveRight();
                    }else{
                        moveRight();
                        moveDown();
                    }
                    break;      
                default:
                    System.out.println("direcction: "+ c);
            }
            //System.out.println("this.actualTarget: "+this.actualTarget);
            if((this.Local.getPosition().equals(this.Targets.get(this.actualTarget).getPosition()))){
                // execute Done Event
                if(this.actualTarget + 1 < this.Targets.size()){
                    if(unitAction != null){
                        unitAction.accept(this.TargetLines.get(this.actualTarget));
                    }
                    this.TargetLines.remove(this.actualTarget);
                    this.Targets.remove(this.actualTarget);
                    this.actualTarget = 0;
                    showActualTargetLine();
                }else{
                    if(unitAction != null){
                        RemoveTargetLines();
                        this.isDone = true;
                    }
                }
                
            }
        }
        public void RemoveTargetLines(){
            for (int i = 0; i < this.TargetLines.size(); i++) {
                unitAction.accept(this.TargetLines.get(i));
            }
        }
        public boolean hasTarget(simplejavagame.Object.Object _target){
            for (simplejavagame.Object.Object t : this.Targets) {
                if(_target.getName().equals(t.getName())){
                    return true;
                }
            }
            return false;
        }
        public void showActualTargetLine(){
            for (int i = 0; i < this.TargetLines.size(); i++) {
                if(this.actualTarget == i){
                    this.TargetLines.get(i).setVisible();
                }else{
                    this.TargetLines.get(i).setHide();
                }
            }
        }
        public void showAllTargetLines(){
            for (int i = 0; i < this.TargetLines.size(); i++) {
                this.TargetLines.get(i).setVisible();
            }
        }
        public void RemoveLastTarget(){
            if (this.Targets.size() > 1){
                int i = this.Targets.size() - 1;
                this.Targets.remove(i);
                if(this.unitAction != null){
                    unitAction.accept(this.TargetLines.get(i));
                }
                this.TargetLines.remove(i);
                showAllTargetLines();
            }
        }
    }
}

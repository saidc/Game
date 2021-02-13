
package simplejavagame.Scene.Game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import simplejavagame.Game.tools.Vector2i;
import simplejavagame.Object.Type;
import simplejavagame.Scene.Scene;
import simplejavagame.Game.tools.*;
public class Game extends Scene  {
    @Override
    public void init(){
        
        if(GameMap.get().init(new Vector2i(10,10), new Vector2i(100,100), 200)){
            
            for (List<simplejavagame.Object.Object> list : GameMap.get().getMap()) {
                for (simplejavagame.Object.Object obj : list) {
                    addObjectToScene(obj);
                    obj.addRootOfCallbacks(this::rootOfCallbacks);
                }
            }
            GameMap.GenerateRandomUnits(20, new Vector2i(10,10), GameMap.get().getInt_Map(),GameMap.Plain);
            GameMap.addClickListeners();
            if( GameMap.units != null ){
                for (simplejavagame.Object.Object unit : GameMap.units) {
                    addObjectToScene(unit);
                    unit.addRootOfCallbacks(this::rootOfCallbacks);
                }
            }
            
        }else{
            System.out.println("Error ");
        }
        System.out.println("++++++++++++++++++");
        ArrayList<simplejavagame.Object.Object> objs = Figures.getObjects(FileManage.localPath()+"/src/simplejavagame/Scene/Game/Game_Data.xml");
        for(simplejavagame.Object.Object obj : objs){
            addObjectToScene(obj);
            System.out.println(obj.getName());
            obj.addRootOfCallbacks(this::rootOfCallbacks);
        }
        System.out.println("*************************");
    }
    @Override
    public void logic() {
        //ACTIONS
        int actions_length = this.actions.size();
        for (int i = 0; i < actions_length; i++) {
            doAction(this.actions.get(i));
            this.actions.remove(this.actions.get(i)); // for every action that it's done, we remove it.
        }
    }
    @Override
    public void rootOfCallbacks(String callbackname) {
        
        if(callbackname.indexOf("Unit") != -1){
            if(!GameMap.get().hasUnitPressed()){ // ask if there is a unit all ready pressed
                simplejavagame.Object.Object unit = getObjectByName(callbackname); // get the unit pressed by the name
                if(unit != null){ // confirm that the return unit or objet are not null
                    GameMap.get().setUnitPressed(unit); // add to the Map unit actions a unit pressed
                }
            }else{
                simplejavagame.Object.Object unitPressed = GameMap.get().getUnitPressed();
                if(unitPressed.getName() == callbackname){
                    GameMap.get().setUnitRelease();
                }
            }
            //System.out.println("Unit Pressed");
        }else if(callbackname.indexOf("Plain") != -1){
            //System.out.println("Plain Pressed");
            TerrainPressed(callbackname);
        }else if(callbackname.indexOf("hill") != -1){
            //System.out.println("Hill pressed");
            TerrainPressed(callbackname);
        }else if(callbackname.indexOf("Mountain") != -1){
            //System.out.println("Mountain Pressed");
            TerrainPressed(callbackname);
        }
        
        // Buttons 
        switch(callbackname){
            case "Delete":
                simplejavagame.Object.Object DeleteButton = getObjectByName("DeleteButton");
                System.out.println("Delete");
                if(DeleteButton != null){
                }else{
                    System.out.println("delete null");
                }
                break;
            case "Cancel":
                simplejavagame.Object.Object CancelButton = getObjectByName("CancelButton");
                System.out.println("Cancel");
                if(CancelButton != null){
                }else{
                    System.out.println("cancel null");
                }
                break;
            case "OkOrder":
                simplejavagame.Object.Object OkButton = getObjectByName("OkButton");
                System.out.println("OkOrder");
                if(OkButton != null){
                }else{
                    System.out.println("ok null");
                }
                break;
            case "NextRound":
                GameMap.get().update();
                break;
            case "LineBetweenSquares":
                //System.out.println("LineBetweenSquares");
                List<simplejavagame.Object.Object> objs = this.getObjects();
                for (simplejavagame.Object.Object obj : objs) {
                    if(obj.getName().indexOf("Unit") != -1  || 
                       obj.getName().indexOf("Plain") != -1 ||
                       obj.getName().indexOf("hill") != -1  ||
                       obj.getName().indexOf("Mountain") != -1 ){
                       
                       obj.SwitchLineBetweenSquares_Visible();
                    }
                }
                break;
        }
    }
    public void TerrainPressed(String name){
        if(GameMap.get().hasUnitPressed()){ 
            simplejavagame.Object.Object terrain = getObjectByName(name); // get the Terrain pressed by the name
            simplejavagame.Object.Object unit    = GameMap.get().getUnitPressed(); // get the Unit pressed from GameMap
            if(!terrain.getPosition().equals(unit.getPosition())){
                System.out.println("agregar nuevo target para un unit");
                GameMap.UnitAction Nt = GameMap.get().addNewTarget(terrain); // here we generate a new target acction
                simplejavagame.Object.Object TargetLine = Figures.getLine(Nt.getLocalLinePosition(), Nt.getTargetLinePosition(), GameMap.Unit_Execut_Color, unit.getLevel());
                TargetLine.setName(Nt.getName());
                Nt.addTargetLine(TargetLine);
                Nt.addDoneEvent(this::TargetDoneEvent);
                addObjectToScene(TargetLine); // add to object list
            }
        }
    }
    public void TargetDoneEvent(GameMap.UnitAction Nt){
        //System.out.println("remove line: "+Nt.getTargetLine().getName());
        removeObjectByName(Nt.getTargetLine().getName());
    }
    @Override
    public void doAction(Map<String, String> acc) {
        String action = acc.get("action-type");
        int action_type = -1;
        try {
            action_type = Integer.parseInt(action);
            switch(action_type){
                case Type.IsClicked:
                    String pos_Stirng = acc.get("position");
                    Vector2i position = Vector2i.StringToVector2i(pos_Stirng);
                    isClicked(position);
                    break;
                case Type.HoverEvent:
                    break;
            }
        } catch (Exception e){
            System.out.println("error to get the acction-type");
        }
    }
    @Override
    public void mouseClicked(MouseEvent me) {
        this.actions.add(Actions.mouseClicked(me.getPoint()));
    }
    @Override
    public void keyTyped(KeyEvent ke) {        
    }
}

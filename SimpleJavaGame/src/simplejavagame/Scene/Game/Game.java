
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
        // load the objects form GameMap
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
        // load the objects from xml file
        ArrayList<simplejavagame.Object.Object> objs = Figures.getObjects(FileManage.localPath()+"/src/simplejavagame/Scene/Game/Game_Data.xml");
        for(simplejavagame.Object.Object obj : objs){
            obj.addRootOfCallbacks(this::rootOfCallbacks);
            addObjectToScene(obj);
        }
        
        // init some buttons as hide
        simplejavagame.Object.Object orderingMode = getObjectByName("OrderingMode");
        simplejavagame.Object.Object deleteButton = getObjectByName("DeleteButton");
        simplejavagame.Object.Object cancelButton = getObjectByName("CancelButton");
        simplejavagame.Object.Object okButton     = getObjectByName("OkButton");
        setHide(orderingMode);
        setHide(deleteButton);
        setHide(cancelButton);
        setHide(okButton    );
        
    }
    private void setHide(simplejavagame.Object.Object obj){
        if(obj != null){
            obj.setHide();
        }
    }
    private void setVisible(simplejavagame.Object.Object obj){
        if(obj != null){
            obj.setVisible();
        }
    }
    private void ReleaseMode(){
        simplejavagame.Object.Object orderingMode = getObjectByName("OrderingMode");
        simplejavagame.Object.Object NextRound = getObjectByName("NextRound");
        simplejavagame.Object.Object okButton = getObjectByName("OkButton");
        simplejavagame.Object.Object deleteButton = getObjectByName("DeleteButton");
        simplejavagame.Object.Object cancelButton = getObjectByName("CancelButton");
        this.setHide(orderingMode);
        this.setVisible(NextRound);
        this.setHide(okButton);
        this.setHide(deleteButton);
        this.setHide(cancelButton);
        if(GameMap.get().getUnitPressedByUnitPressed() != null){
            GameMap.get().getUnitPressedByUnitPressed().showActualTargetLine();
        }
        GameMap.get().setUnitRelease();
    }
    private void OrderingMode(){
        simplejavagame.Object.Object orderingMode = getObjectByName("OrderingMode");
        simplejavagame.Object.Object NextRound = getObjectByName("NextRound");
        simplejavagame.Object.Object okButton = getObjectByName("OkButton");
        simplejavagame.Object.Object deleteButton = getObjectByName("DeleteButton");
        simplejavagame.Object.Object cancelButton = getObjectByName("CancelButton");
        this.setVisible(okButton);
        this.setVisible(orderingMode);
        this.setVisible(deleteButton);
        this.setVisible(cancelButton);
        this.setHide(NextRound);
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
    public void rootOfCallbacks(String callbackname){
        if(callbackname.indexOf("Unit") != -1){
            if(!GameMap.get().hasUnitPressed()){ // ask if there is a unit all ready pressed
                simplejavagame.Object.Object unit = getObjectByName(callbackname); // get the unit pressed by the name
                if(unit != null){ // confirm that the return unit or objet are not null
                    GameMap.get().setUnitPressed(unit); // add to the Map unit actions a unit pressed
                    OrderingMode();
                    if(GameMap.get().hasUnitPressed()){
                        if(GameMap.get().hasUnitPressedAnAction()){
                            GameMap.get().getUnitPressedByUnitPressed().showAllTargetLines();
                        }
                    }
                }
            }else{
                simplejavagame.Object.Object unitPressed = GameMap.get().getUnitPressed();
                if(unitPressed.getName() == callbackname){
                    ReleaseMode();
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
                if(GameMap.get().hasUnitPressed()){
                    if(GameMap.get().hasUnitPressedAnAction()){
                        GameMap.UnitAction u =  GameMap.get().getUnitPressedByUnitPressed();
                        if(u.getNumberOfTargets() == 1){
                            GameMap.get().removeActionByUnutPressed();
                        }else{
                            u.RemoveLastTarget();
                        }
                    }
                }
                break;
            case "Cancel":
                if(GameMap.get().hasUnitPressed()){
                    GameMap.get().removeActionByUnutPressed();
                    this.ReleaseMode();
                }
                break;
            case "OkOrder":
                this.ReleaseMode();
                break;
            case "NextRound":
                GameMap.get().update();
                break;
            case "LineBetweenSquares":
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
                boolean sw = GameMap.get().hasUnitPressedAnAction();
                GameMap.UnitAction Nt = GameMap.get().addNewTarget(terrain); // here we generate a new target acction
                if(Nt != null){
                    simplejavagame.Object.Object TargetLine = null;
                    if(GameMap.get().getUnitPressed() != null){
                        if(sw){
                            TargetLine = Figures.getLine(Nt.getPreviousTargetLinePosition(), Nt.getLastTargetLinePosition(), GameMap.Unit_Execut_Color, unit.getLevel());
                        }else{
                            TargetLine = Figures.getLine(Nt.getLocalLinePosition(), Nt.getActualTargetLinePosition(), GameMap.Unit_Execut_Color, unit.getLevel());
                        }
                    }
                    if(TargetLine != null){
                        TargetLine.setName(Nt.getName()+"-"+Nt.getNumberOfTargets()); // adding the name of the new lineTarget
                        Nt.addTargetLine(TargetLine); // adding the targetLine to the New UnitAcction
                        Nt.addDoneEvent(this::RemoveTargetLine); // adding the callback function when the Action of the unit is Done
                        addObjectToScene(TargetLine); // add TargetLine to object list to be show 
                        Nt.showAllTargetLines();
                    }
                }
            }
        }
    }
    public void RemoveTargetLine(simplejavagame.Object.Object TargetLine){
        removeObjectByName(TargetLine.getName());
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


package simplejavagame.Scene.Game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import simplejavagame.Game.tools.Figures;
import simplejavagame.Game.tools.FileManage;
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
        // ArrayList<simplejavagame.Object.Object> objs = Figures.getObjects(FileManage.localPath()+"/src/simplejavagame/Scene/Game/Game_Data.xml");
        //simplejavagame.Object.Object obj = Figures.getSquare( new Vector2i(100,100), new Vector2i(30,30), new Vector4i(200,60,40,255));
        /*
        for(simplejavagame.Object.Object obj : objs){
            addObjectToScene(obj);
            obj.addRootOfCallbacks(this::rootOfCallbacks);
        }
        */
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
        switch(callbackname){
            case "Square1":
                System.out.println("Square1");
                break;
            case "Square2":
                break;
        }
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

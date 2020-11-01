
package game.Scene;

import game.Build.Component.Spritesheet;
import game.Build.GameObject;
import game.Build.Map.Map;
import game.Build.Map.Units;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import org.joml.Vector2i;

public class Game_Scene extends Scene{
   // private Spritesheet sprites;
    private boolean isClicked = false;
    private Vector2i ClickedPosition = null;
    private boolean asignNewPos = false;
    private GameObject asignNewClickedGameobject = null;
    
    @Override
    public void init() {
        //System.out.println("start init");
        
        if(Map.get().init(new Dimension(10,10), new Dimension(100,100), 200)){
            
            for (List<GameObject> list : Map.get().getMap()) {
                this.gameObjects.addAll(list);
            }
            
            Units.GenerateRandomUnits(20, new Dimension(10,10), Map.get().getInt_Map(),Map.Plain);
            Units.addClickListeners(this::UnitClicked);// obj4.addClickListener(this::QuitGame);
            this.gameObjects.addAll(Units.getGameObjects());
            
        }else{
            System.out.println("Error ");
        }
        
    }
    
    public void UnitClicked(boolean b){
        
        System.out.println("mouseClicked");
    }
    
    @Override
    public void update(long time) {
        
        for (GameObject go : this.gameObjects) {
            go.update(time); // update GameObject
            
            Units u = go.getComponent(Units.class);
            if(u != null ){ 
                u.update(time);
            }
        }
        
        if(this.isClicked){
            this.isClicked = false;
        }
        
        this.renderer.render();
    }
    private int GameState = 0;
    
    private void inspectClickEvent(Vector2i ClickedPosition){
        GameObject temp = null;
        int Case = 0;
        for (GameObject go : this.gameObjects) {
            Units u = go.getComponent(Units.class);
            if(      GameState == 0){
                if(go.isClicked(ClickedPosition)){
                    if(u != null){
                        temp = go;
                        Case = 1;
                    }
                }
            }else if(GameState == 1){
                if(go.isClicked(ClickedPosition)){
                    if(u == null){
                        temp = go;
                        Case = 2;
                    }
                }
            }
        }
        if(Case == 1){
            GameState = 1;
            asignNewClickedGameobject = temp;
            System.out.println("state = 0");
            
        }else if(Case == 2){
            Units asignNewClickedUnit = asignNewClickedGameobject.getComponent(Units.class);
            asignNewClickedUnit.update(GameState);
            GameState = 0;
            System.out.println("state = 1");
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
        this.isClicked = true;
        ClickedPosition = new Vector2i(me.getX(),me.getY());
        inspectClickEvent(ClickedPosition);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }
    
}
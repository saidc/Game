
package game.Scene;

import game.Build.Component.SpriteRenderer;
import game.Build.GameObject;
import game.Build.Map.Map;
import game.Build.Map.Units;
import game.Build.Transform;
import game.Build.Window;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector2i;
import org.joml.Vector4i;

public class Game_Scene extends Scene{
   // private Spritesheet sprites;
    private boolean isClicked = false;
    private Vector2i ClickedPosition = null;
    
    private boolean isUnitExecutingOrders = false;
    private GameObject asignNewClickedGameobject = null;
    private GameObject button1 = null;
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
        
        GameObject obj3 = new GameObject("Next Round Title", new Transform(new Vector2i(1040, 40 ), new Vector2i(100, 100)),5);
        SpriteRenderer obj3SpriteRender = new SpriteRenderer();
        obj3SpriteRender.setColor(new Vector4i(255,255,255,255));
        obj3SpriteRender.setText("Next Round");
        obj3SpriteRender.setFont(new Font ("TimesRoman", 1, 20));
        obj3.addComponent(obj3SpriteRender); // just a color 
        this.addGameObjectToScene(obj3);
        
        this.button1 = new GameObject("Next Round", new Transform(new Vector2i( 1010 , 10), new Vector2i(180, 50)),4);
        SpriteRenderer button1SpriteRender = new SpriteRenderer();
        button1SpriteRender.setColor(new Vector4i(23,54,100,255));
        button1.addComponent(button1SpriteRender); // just a color 
        button1.addClickListener(this::NextRound);
        this.addGameObjectToScene(button1);
        
    }
    public void NextRound(Dimension d){
        //System.out.println("next round");
        for (GameObject go : gameObjects) {
            Units u = go.getComponent(Units.class);
            if(u != null ){ 
                u.Move();
            }
        }
    }
    public void UnitClicked(boolean b){
        
        System.out.println("mouseClicked");
    }
    
    @Override
    public void update(long time) {
        int UnitExecutingOrders = 0;
        List<Units> UnitsToUpdate = null;
        
        for (GameObject go : this.gameObjects) {
            go.update(time); // update GameObject
            
            Units u = go.getComponent(Units.class);
            if(u != null ){ 
                if(u.getPreviousOverTerrain() == -1){
                    if(UnitsToUpdate == null){
                        UnitsToUpdate = new ArrayList<>();
                    }
                    UnitsToUpdate.add(u);
                }else if(u.getState() == Units.Executing_Orders){
                    if(UnitsToUpdate == null){
                        UnitsToUpdate = new ArrayList<>();
                    }
                    UnitsToUpdate.add(u);
                }
                u.update(time, u.getPreviousOverTerrain());
                
                if(u.getState() == Units.Executing_Orders ){
                    UnitExecutingOrders++;
                }
            }
        }
        
        if(UnitsToUpdate != null){
            for (GameObject go : this.gameObjects) {
                int x1 = go.transform.getPosition().x - go.transform.scale.x/2, x2 = go.transform.getPosition().x + go.transform.scale.x/2;
                int y1 = go.transform.getPosition().y - go.transform.scale.y/2, y2 = go.transform.getPosition().y + go.transform.scale.y/2;
                Units unit = go.getComponent(Units.class);
                if(unit == null){
                    for (Units u : UnitsToUpdate) {
                        int x = u.gameObject.transform.position.x,y = u.gameObject.transform.position.y;
                        if(x > x1 && x < x2 && y > y1 && y < y2){
                            u.setPreviousOverTerrain(go.zIndex()); // in this case the zIndex also its the same number of the terrain
                        }
                    }
                }
            }
            UnitsToUpdate.clear();
        }
        
        if(UnitExecutingOrders > 0 && !this.isUnitExecutingOrders ){
            SpriteRenderer obj3SpriteRender = this.button1.getComponent(SpriteRenderer.class);
            obj3SpriteRender.setColor(Map.Plain_Color);
        }else if(!(UnitExecutingOrders > 0) && this.isUnitExecutingOrders ){
            SpriteRenderer obj3SpriteRender = this.button1.getComponent(SpriteRenderer.class);
            obj3SpriteRender.setColor(new Vector4i(23,54,100,255));
        }
        
        this.isUnitExecutingOrders = (UnitExecutingOrders > 0);
        
        if(this.isClicked){
            this.isClicked = false;
        }
        
        this.renderer.render();
    }
    private int GameState = 0;
    
    private void inspectClickEvent(Vector2i ClickedPosition){
        List<GameObject> temp = new ArrayList<>();
        GameObject temp2 = null;
        int Case = 0;
        for (GameObject go : this.gameObjects) {
            Units u = go.getComponent(Units.class);
            if(      GameState == 0){
                if(go.isClicked(ClickedPosition)){
                    if(u != null){
                        temp.add(go);
                        Case = 1;
                    }
                }
            }else if(GameState == 1){
                if(go.isClicked(ClickedPosition)){
                    if(u == null){
                        temp2 = go;
                        Case = 2;
                    }
                }
            }
        }
        
        if(Case == 1){
            GameState = 1;
            for (GameObject gameObject : temp) {
                Units u = gameObject.getComponent(Units.class);
                if(u.getUnitTokenState()){
                    asignNewClickedGameobject = gameObject;
                }
            }
            System.out.println("state = 0");
        }else if(Case == 2){
            Units asignNewClickedUnit = asignNewClickedGameobject.getComponent(Units.class);
            asignNewClickedUnit.update(GameState,temp2);
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
        if(ClickedPosition.x > (Window.get().dimension.width - 200) ){
            //System.out.println(" outside the game ");
            button1.isClicked(ClickedPosition);
            
        }else{
            inspectClickEvent(ClickedPosition);
            
            //System.out.println(" inside the game ");
        }
        
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
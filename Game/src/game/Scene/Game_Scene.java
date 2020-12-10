
package game.Scene;

import game.Build.Component.SpriteRenderer  ;
import game.Build.GameObject      ;
import game.Build.Map.Map         ;
import game.Build.Map.Units       ;
import game.Build.Transform       ;
import game.Build.Window          ;
import java.awt.Dimension         ;
import java.awt.Font              ;
import java.awt.event.KeyEvent    ;
import java.awt.event.MouseEvent  ;
import java.util.ArrayList  ;
import java.util.List       ;
import java.util.function.Consumer ;
import org.joml.Vector2i    ;
import org.joml.Vector4i    ;

public class Game_Scene extends Scene{
   // private Spritesheet sprites;
    private boolean isClicked = false;
    private Vector2i ClickedPosition = null;
    
    private boolean isUnitExecutingOrders = false;
    private GameObject asignNewClickedGameobject = null;
    private int button1 = -1;
    
    private List<Integer> Buttons;
    
    @Override
    public void init() {
        // its a list of buttons id on the Game_scene 
        this.Buttons = new ArrayList<>();
        
        if(Map.get().init(new Dimension(10,10), new Dimension(100,100), 200)){
            
            for (List<GameObject> list : Map.get().getMap()) {
                this.addGameObjectToScene(list);
            }
            Units.GenerateRandomUnits(20, new Dimension(10,10), Map.get().getInt_Map(),Map.Plain);
            Units.addClickListeners(this::UnitClicked);// obj4.addClickListener(this::QuitGame);
            this.addGameObjectToScene(Units.getGameObjects());
            
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
        
        GameObject button = new GameObject("Next Round", new Transform(new Vector2i( 1010 , 10), new Vector2i(180, 50)),4);
        SpriteRenderer buttonSpriteRender = new SpriteRenderer();
        buttonSpriteRender.setColor(new Vector4i(23,54,100,255));
        button.addComponent(buttonSpriteRender); // just a color 
        button.addClickListener(this::NextRound);
        this.addGameObjectToScene(button);
        
        this.button1 = this.getGameObjectList().indexOf(button);
        
        createaButton("LinesBetweenSquares",new Vector2i( 1010 , 100),new Vector2i(180, 50),new Vector4i(23,54,100,255),"LinesBetweenSquares",this::LinesBetweenSquares,4);
    }
    
    private void createaButton(String idText, Vector2i Position , Vector2i Dimension ,Vector4i Color, String text,Consumer<Dimension> ClickListener, int level){
        
        GameObject Text = new GameObject(idText+"-text", new Transform(new Vector2i( Position.x,Position.y+(Dimension.y/2)+10), Dimension),level+1);
        SpriteRenderer TextSpriteRender = new SpriteRenderer();
        TextSpriteRender.setColor(new Vector4i(255,255,255,255));
        TextSpriteRender.setText(text);
        TextSpriteRender.setFont(new Font ("TimesRoman", 1, 20));
        Text.addComponent(TextSpriteRender); // just a color 
        this.addGameObjectToScene(Text);
        
        GameObject button = new GameObject(idText, new Transform(Position, Dimension),level);
        SpriteRenderer buttonSpriteRender = new SpriteRenderer();
        buttonSpriteRender.setColor(Color);
        button.addComponent(buttonSpriteRender); // just a color 
        button.addClickListener(ClickListener);
        this.addGameObjectToScene(button);
        this.Buttons.add(this.getGameObjectList().indexOf(button));
    }
    
    public void LinesBetweenSquares(Dimension d){
        if(Map.getLinesBetweenSquares()){
            Map.RemoveLinesBetweenSquares();
        }else{
            Map.addLinesBetweenSquares();
        }
        System.out.println("draw lines");
    }
    
    public void NextRound(Dimension d){
        //System.out.println("next round");
        for (GameObject go : this.getGameObjectList()) {
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
        
        for (GameObject go : this.getGameObjectList()) {
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
            for (GameObject go : this.getGameObjectList()) {
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
            GameObject goButton1 = this.getGameObjectList().get(button1);
            SpriteRenderer obj3SpriteRender = goButton1.getComponent(SpriteRenderer.class);
            obj3SpriteRender.setColor(Map.Plain_Color);
        }else if(!(UnitExecutingOrders > 0) && this.isUnitExecutingOrders ){
            GameObject goButton1 = this.getGameObjectList().get(button1);
            SpriteRenderer obj3SpriteRender = goButton1.getComponent(SpriteRenderer.class);
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
        for (GameObject go : this.getGameObjectList()) {
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
            GameObject goButton1 = this.getGameObjectList().get(button1);
            goButton1.isClicked(ClickedPosition);
            for (Integer Button : Buttons) {
                GameObject goButton = this.getGameObjectList().get(Button);
                goButton.isClicked(ClickedPosition);
            }
            
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
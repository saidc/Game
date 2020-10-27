package game.Scene;

import game.Build.Component.SpriteRenderer;
import game.Build.GameObject;
import game.Build.Transform;
import game.Build.Window;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import org.joml.Vector2i;
import org.joml.Vector4i;


public class Home_Scene extends Scene{
    private boolean isClicked = false;
    private Vector2i ClickedPosition = null;
    @Override
    public void init() {
        
        Vector2i CoS = new Vector2i(Window.get().dimension.width/2, Window.get().dimension.height/2);
        
        GameObject obj1 = new GameObject("Start Game Title", new Transform(new Vector2i((int)(CoS.x-(150/2))+10, CoS.y-(50+20) +30 ), new Vector2i(100, 100)),1);
        SpriteRenderer obj1SpriteRender = new SpriteRenderer();
        obj1SpriteRender.setColor(new Vector4i(255,255,255,255));
        obj1SpriteRender.setText("START GAME");
        obj1SpriteRender.setFont(new Font ("TimesRoman", 1, 20));
        obj1.addComponent(obj1SpriteRender); // just a color 
        this.addGameObjectToScene(obj1);
        
        GameObject obj2 = new GameObject("Start Game", new Transform(new Vector2i((int)(CoS.x-(150/2)), CoS.y-(50+20) ), new Vector2i(150, 50)),0);
        SpriteRenderer obj2SpriteRender = new SpriteRenderer();
        obj2SpriteRender.setColor(new Vector4i(23,54,100,255));
        obj2.addComponent(obj2SpriteRender); // just a color 
        obj2.addClickListener(this::StartGame);
        this.addGameObjectToScene(obj2);
        
        GameObject obj3 = new GameObject("Quit Game Title", new Transform(new Vector2i((int)CoS.x-20, CoS.y+(50+20) +30 ), new Vector2i(100, 100)),1);
        SpriteRenderer obj3SpriteRender = new SpriteRenderer();
        obj3SpriteRender.setColor(new Vector4i(255,255,255,255));
        obj3SpriteRender.setText("Quit");
        obj3SpriteRender.setFont(new Font ("TimesRoman", 1, 20));
        obj3.addComponent(obj3SpriteRender); // just a color 
        this.addGameObjectToScene(obj3);
        
        GameObject obj4 = new GameObject("Quit Game", new Transform(new Vector2i( (int) CoS.x-(150/2) , CoS.y+(50+20)), new Vector2i(150, 50)),0);
        SpriteRenderer obj4SpriteRender = new SpriteRenderer();
        obj4SpriteRender.setColor(new Vector4i(23,54,100,255));
        obj4.addComponent(obj4SpriteRender); // just a color 
        obj4.addClickListener(this::QuitGame);
        this.addGameObjectToScene(obj4);
        
    }
    public void StartGame(Dimension d){
        System.out.println("Start game");
        Window.changeScene(1);
    }
    public void QuitGame(Dimension d){
        System.out.println("Quit Game");
        System.exit(0);
    }
    
    @Override
    public void update() {
        
        for (GameObject go : this.gameObjects) {
            go.update(0);
            if(this.isClicked){
                go.isClicked(ClickedPosition);
            }
            
        }
        if(this.isClicked){
            this.isClicked = false;
        }
        
        this.renderer.render();
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        //System.out.println("keyTyped");
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        //System.out.println("keyPressed");
    }

    @Override
    public void keyReleased(KeyEvent ke) {
//        System.out.println("keyReleased");
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        this.isClicked = true;
        ClickedPosition = new Vector2i(me.getX(),me.getY());
        //System.out.println("mouseClicked");
    }

    @Override
    public void mousePressed(MouseEvent me) {
//        System.out.println("mousePressed");
    }

    @Override
    public void mouseReleased(MouseEvent me) {
//        System.out.println("mouseReleased");
    }

    @Override
    public void mouseEntered(MouseEvent me) {
//        System.out.println("mouseEntered");
    }

    @Override
    public void mouseExited(MouseEvent me) {
//        System.out.println("mouseExited");
    }
    
}

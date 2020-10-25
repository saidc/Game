
package game.Scene;


import game.Build.Component.SpriteRenderer;
import game.Build.GameObject;
import game.Build.Transform;
import game.Build.Window;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import org.joml.Vector2i;
import org.joml.Vector4f;
//import org.joml.Vector2i;
//import org.joml.Vector4f;


public class Home_Scene extends Scene{
    private boolean isClicked = false;
    private Vector2i ClickedPosition = null;
    @Override
    public void init() {
        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2i(410, 125), new Vector2i(100, 100)),1);
        SpriteRenderer obj1SpriteRender = new SpriteRenderer();
        obj1SpriteRender.setColor(new Vector4f(0.0f,0.0f,0.0f,1.0f));
        obj1SpriteRender.setText("START GAME");
        obj1SpriteRender.setFont(new Font ("TimesRoman", 1, 18));
        obj1.addComponent(obj1SpriteRender); // just a color 
        this.addGameObjectToScene(obj1);
        
        GameObject obj3 = new GameObject("Object 3", new Transform(new Vector2i(400, 100), new Vector2i(150, 50)),0);
        SpriteRenderer obj3SpriteRender = new SpriteRenderer();
        obj3SpriteRender.setColor(new Vector4f(1.0f,1.0f,0.0f,1.0f));
        obj3.addComponent(obj3SpriteRender); // just a color 
        obj3.addClickListener(this::StartGame);
        this.addGameObjectToScene(obj3);
        
    }
    public void StartGame(Dimension d){
        
        System.out.println("Start game");
        Window.changeScene(1);
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
        System.out.println("añlskdjfñlaksjdfñlakjsdñflkj");
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

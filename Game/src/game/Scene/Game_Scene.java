
package game.Scene;

import game.Build.Component.Sprite;
import game.Build.Component.SpriteRenderer;
import game.Build.Component.Spritesheet;
import game.Build.GameObject;
import game.Build.Transform;
import game.Utils.AssetPool;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import org.joml.Vector2i;
import org.joml.Vector4f;

public class Game_Scene extends Scene{
    private Spritesheet sprites;
    private GameObject obj1;
    
    @Override
    public void init() {
        GameObject obj3 = new GameObject("Object 3", new Transform(new Vector2i(400, 100), new Vector2i(150, 50)),0);
        SpriteRenderer obj3SpriteRender = new SpriteRenderer();
        obj3SpriteRender.setColor(new Vector4f(1.0f,1.0f,0.0f,1.0f));
        obj3.addComponent(obj3SpriteRender); // just a color 
        //obj3.addClickListener(this::StartGame);
        this.addGameObjectToScene(obj3);
    }
    
    @Override
    public void update() {
        for (GameObject go : this.gameObjects) {
            go.update(0);
        }
        
        this.renderer.render();
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
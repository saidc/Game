
package game.Scene;

import game.Build.Component.Sprite;
import game.Build.Component.SpriteRenderer;
import game.Build.Component.Spritesheet;
import game.Build.GameObject;
import game.Build.Map.Map;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

public class Game_Scene extends Scene{
    private Spritesheet sprites;
    //private List<List<GameObject>> Obj_Map;
    
    @Override
    public void init() {
        System.out.println("start init");
        
        if(Map.get().init(new Dimension(10,10), new Dimension(100,100), 200)){
            for (List<GameObject> list : Map.get().getMap()) {
                this.gameObjects.addAll(list);
            }
            System.out.println("finalize init");
        }else{
            System.out.println("Error ");
        }
        //Map.get().ShowMap();
        
//        GameObject obj3 = new GameObject("Object 3", new Transform(new Vector2i(10, 10), new Vector2i(150, 50)),0);
//        SpriteRenderer obj3SpriteRender = new SpriteRenderer();
//        obj3SpriteRender.setColor(new Vector4i(1,1,0,1));
//        obj3.addComponent(obj3SpriteRender); // just a color 
//        this.addGameObjectToScene(obj3);
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
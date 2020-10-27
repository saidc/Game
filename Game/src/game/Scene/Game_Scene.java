
package game.Scene;

import game.Build.Component.Spritesheet;
import game.Build.GameObject;
import game.Build.Map.Map;
import game.Build.Window;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

public class Game_Scene extends Scene{
    private Spritesheet sprites;
    
    @Override
    public void init() {
        System.out.println("start init");
        if(Map.get().init(new Dimension(10,10), new Dimension(100,100), 200)){ // the map has to be square map
            for (List<GameObject> list : Map.get().getMap()) {
                this.gameObjects.addAll(list);
            }
            System.out.println("finalize init");
        }else{
            System.out.println("Error ");
        }
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
        if(ke.getKeyChar() == 'q' || ke.getKeyChar() == 'Q'){
            System.out.println("quit");
            Window.changeScene(0);
        }
        //System.out.println("KeyTuped: "+ ke.getKeyChar() + " , "+ ke.getID());
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
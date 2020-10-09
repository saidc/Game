
package Jade.Scenes;

import Jade.Scene;
import Jade.Window;
import Jade.EventListener.keysListener;
import org.lwjgl.glfw.GLFW;

public class LevelEditoScene extends Scene {
    private float   timeToChangeScreen ;
    private boolean changeScreen ;
    public LevelEditoScene(){
        System.out.println("you are in LevelEditorScene");
        timeToChangeScreen = 2.0f;
        changeScreen = false;
    }
    
    @Override
    public void update(float dt) {
        if(keysListener.isKeyPress(GLFW.GLFW_KEY_SPACE)){
            System.out.println(" Space key is pressed in LevelEditorScene");
        }
        
        if(!this.changeScreen && keysListener.isKeyPress(GLFW.GLFW_KEY_A)){
            System.out.println(" A key is pressed");
            this.changeScreen = true;
        }
        
        if(changeScreen && (timeToChangeScreen -= dt) < 0){
            this.changeScreen = false;
            Window.changeScene(1);
        }
    }
}

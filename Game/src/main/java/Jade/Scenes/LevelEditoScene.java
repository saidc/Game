
package Jade.Scenes;

import Jade.Scene;
import Jade.Window;
import Jade.EventListener.keysListener;
import org.lwjgl.glfw.GLFW;

public class LevelEditoScene extends Scene {
    public LevelEditoScene(){
        System.out.println("you are in LevelEditorScene");
    }

    @Override
    public void update(float dt) {
        if(keysListener.isKeyPress(GLFW.GLFW_KEY_SPACE)){
            System.out.println(" Space key is pressed in LevelEditorScene");
        }
        if(keysListener.isKeyPress(GLFW.GLFW_KEY_A)){
            System.out.println(" A key is pressed");
            Window.changeScene(1);
        }
        
    }
    
}

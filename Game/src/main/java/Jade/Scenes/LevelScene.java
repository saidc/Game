
package Jade.Scenes;

import Jade.Scene;
import Jade.Window;
import Jade.EventListener.keysListener;
import org.lwjgl.glfw.GLFW;


public class LevelScene extends Scene{
    public LevelScene(){
        System.out.println(" You are in LevelScene");
    }

    @Override
    public void update(float dt) {
        if(keysListener.isKeyPress(GLFW.GLFW_KEY_SPACE)){
            System.out.println(" Space key is pressed in LecelScene");
        }
        if(keysListener.isKeyPress(GLFW.GLFW_KEY_D)){
            System.out.println(" D key is pressed");
            Window.changeScene(0);
        }
    }
}

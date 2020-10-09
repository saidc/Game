package Jade.EventListener;

import org.lwjgl.glfw.GLFW;

public class keysListener {
    private static keysListener instance;
    private boolean keyPressed[] = new boolean[350];
    
    private keysListener(){
    }
    public static keysListener get(){
        if( keysListener.instance == null){
            keysListener.instance = new keysListener();
        }
        return keysListener.instance;
    }
    public static void keyCallback(long window, int key, int scancode , int action , int mods){
        if( action == GLFW.GLFW_PRESS){
            get().keyPressed[key] = true;
        }else if( action ==  GLFW.GLFW_RELEASE){
            get().keyPressed[key] = false;
            
        }
    }
    public static boolean isKeyPress(int keyCode){
        if(keyCode < get().keyPressed.length){
            return get().keyPressed[keyCode];
        }else{
            return false;
        }
    }
}
 
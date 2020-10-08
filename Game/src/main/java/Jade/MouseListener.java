
package Jade;

public class MouseListener {
    private static MouseListener instance;
    private double scrollX,scrollY ;
    private double xPos, yPos, lastX,lastY;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isDragging ;
    
    private MouseListener(){
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos    = 0.0;
        this.yPos    = 0.0;
        this.lastX   = 0.0;
        this.lastY   = 0.0;
    }
    
    public static MouseListener get(){
        if(MouseListener.instance == null){
            MouseListener.instance = new MouseListener();
        }
        return MouseListener.instance;
    }
    public static void mousePosCallback(long window , double xPos, double yPos){
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xPos;
        get().yPos = yPos;
        
    }
    public static void mouseButtonCallback(long window, int button , int action , int mods ){
        //get().mouseButtonPressed[button] = true;
        
    }
}

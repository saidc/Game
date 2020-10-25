
package game.Build;

import java.awt.Frame;
import java.awt.Canvas;
import game.Scene.Game_Scene;
import game.Scene.Home_Scene;
import game.Scene.Scene;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class Window implements Runnable,WindowListener{//,KeyListener,MouseListener{
    private static Window window = null;
    
    public Dimension dimension;
    private String title;
    private int WindowID;

    public float r, g, b, a;
    
    public Frame frame ;
    public  Canvas canvas;
    
    private boolean running = true;
    
    private static Scene currentScene = null;
    
    public static Window get(){
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }
    
    private Window() {
        this.dimension = new Dimension(1000, 800);
        this.title = "Game";
        r = 0;
        b = 0;
        g = 0;
        a = 1;
        this.WindowID = this.hashCode();
    }
    
    public Frame getJFrame(){
        
        return this.frame;
    }
    @Override
    public void run() {
        init();
        loop();
    }
    
    public void init() {
        frame = new Frame(title);
        canvas = new Canvas();
        
        frame.setSize(dimension );
        frame.setResizable(false);
        
        frame.setLocationRelativeTo(null);
        
        this.canvas.setPreferredSize(dimension);
        this.canvas.setMaximumSize(dimension);
        this.canvas.setMinimumSize(dimension);
        this.canvas.setFocusable(false);
        
        this.canvas.setBackground(new Color(r,g,b,a)); //Red
        
        frame.add(this.canvas);
        frame.pack();
        
        // inicializacion de ventana antes de pasar los eventos de ventana
        this.changeScene(0);
        
        
        // Callback Events
        //frame.addKeyListener(  this);//.currentScene);
        
        //frame.addMouseListener(this);//.currentScene);
        
        frame.addWindowListener(this);
        // show window
        frame.setVisible(true);
        
        
    }

    public void loop() {
        
        long beginTime = System.currentTimeMillis();
        long endTime=beginTime;
        long dt = -1;

        while(this.running) {
            
            if (dt >= 10) {
                currentScene.update();
                beginTime = endTime;
                dt = 0;
            }else{
                endTime = System.currentTimeMillis();
                dt = endTime - beginTime;
            }
        }
        
        //currentScene.saveExit();
        System.out.println("Game Over");
    }
    public static void changeScene(int newScene) {
        if(currentScene != null){ // remove events from the actual scene 
         Window.get().frame.removeKeyListener(currentScene);
         Window.get().canvas.removeMouseListener(currentScene);           
        }
        switch (newScene) {
            case 0:
                currentScene = new Home_Scene();
                break;
            case 1:
                currentScene = new Game_Scene();
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'";
                break;
        }

        currentScene.load();
        currentScene.init();
        currentScene.start( window.dimension.width,window.dimension.height);
        Window.get().frame.addKeyListener  (currentScene);
        Window.get().canvas.addMouseListener(currentScene);
    }

    @Override
    public void windowOpened(WindowEvent we) {
        System.out.println("windowOpened");
    }

    @Override
    public void windowClosing(WindowEvent we) {
        this.running = false;
        System.exit(0);
        System.out.println("windowClosing");
    }

    @Override
    public void windowClosed(WindowEvent we) {
        
        System.out.println("windowClosed");
    }

    @Override
    public void windowIconified(WindowEvent we) {
        System.out.println("windowIconified");
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
        System.out.println("windowDeiconified");
    }

    @Override
    public void windowActivated(WindowEvent we) {
        System.out.println("windowActivated");
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        System.out.println("windowDeactivated");
    }
   
}


package simplejavagame.Game;

import simplejavagame.Scene.Game.Game;
import simplejavagame.Scene.Home.Home;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import simplejavagame.Game.tools.Vector4i;
import simplejavagame.Game.tools.tools;
import simplejavagame.Scene.*;

public class Window implements Runnable,WindowListener{
    
    // STATIC
    public static final int Home = 0;
    public static final int Game = 1;
    
    private static Window window = null;
    public static Window get(){
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }
    public static void changeScene(int newScene) {
        if(currentScene != null){ // remove events from the actual scene 
            Window.get().frame.removeKeyListener(currentScene);
            Window.get().canvas.removeMouseListener(currentScene);
            Window.get().canvas.removeMouseMotionListener(currentScene);
        }
        switch (newScene) {
            case 0:
                currentScene = new Home();
                break;
            case 1:
                currentScene = new Game();       
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'";
                break;
        }

        //currentScene.load(); // upload the previous configuration
        currentScene.init();
        //currentScene.start( window.dimension.width,window.dimension.height);
        Window.get().canvas.addMouseListener(currentScene);
        Window.get().canvas.addMouseMotionListener(currentScene);
        Window.get().frame.addKeyListener(currentScene);
    }
    public static Dimension getDimension(){
        return Window.dimension;
    }
    private static Dimension dimension = null;
    // PRIVATE
    private String tittle ;
    
    private boolean running = false;
    private static Scene currentScene = null;
    
    // PUBLIC
    public Frame frame ;
    public  Canvas canvas;
    
    public void init(){
        dimension = new Dimension(1310, 1000);//1200 ,1000
        this.tittle = "Game";
        this.frame = new Frame(this.tittle);
        this.canvas = new Canvas();
        this.frame.setSize(dimension );
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        this.canvas.setPreferredSize(dimension);
        this.canvas.setMaximumSize(dimension);
        this.canvas.setMinimumSize(dimension);
        this.canvas.setFocusable(false);
        Vector4i Background_color = new Vector4i(0,0,0,255);
        this.canvas.setBackground(tools.NewColor(Background_color)); 
        frame.add(this.canvas);
        frame.pack();
        this.running = true;
        changeScene(0);
        frame.addWindowListener(this);
        // show window
        frame.setVisible(true);
    }
    
    public void loop(){
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
        System.out.println("Game Over");
    }
    
    @Override
    public void run() {
        init();
        loop();
    }
    
    //EVENTS
    @Override
    public void windowOpened(WindowEvent we) {        
    }

    @Override
    public void windowClosing(WindowEvent we) {
        this.running = false;
        System.out.println("windowClosing");
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent we) {
    }

    @Override
    public void windowIconified(WindowEvent we) {
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    @Override
    public void windowActivated(WindowEvent we) {
    }

    @Override
    public void windowDeactivated(WindowEvent we) {   
    }
}